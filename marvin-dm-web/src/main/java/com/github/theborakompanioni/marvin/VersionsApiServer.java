package com.github.theborakompanioni.marvin;

import com.google.common.base.Strings;
import com.google.common.io.Resources;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.functions.Action1;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;

import static java.util.Objects.requireNonNull;


class VersionsApiServer extends AbstractVerticle {

    private final AppConfiguration configuration;
    private final DependencySummaryProvider dependencySummaryProvider;

    public VersionsApiServer(AppConfiguration configuration, DependencySummaryProvider dependencySummaryProvider) {
        this.configuration = requireNonNull(configuration);
        this.dependencySummaryProvider = requireNonNull(dependencySummaryProvider);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(RouteHandlers.loggerHandler());
        router.route().handler(RouteHandlers.timeoutHandler());
        router.route().handler(RouteHandlers.responseTimeHandler());
        router.route().failureHandler(RouteHandlers.failureHandler());

        router.routeWithRegex(".*\\.svg")
                .pathRegex("\\/([^\\/]+)\\/([^\\/]+)\\.svg")
                .produces("image/svg+xml")
                .handler(svgHandler());

        router.route("/:username/:repository")
                .handler(contextSetupHandler());

        router.route("/:username/:repository")
                .handler(jsonHandler());

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(configuration.httpPort());
    }

    private Handler<RoutingContext> contextSetupHandler() {
        return routingContext -> {
            String username = Optional.ofNullable(routingContext.request().getParam("username"))
                    .map(Strings::emptyToNull)
                    .orElseThrow(IllegalArgumentException::new);

            String repository = Optional.ofNullable(routingContext.request().getParam("repository"))
                    .map(Strings::emptyToNull)
                    .orElseThrow(IllegalArgumentException::new);

            routingContext.put("username", username);
            routingContext.put("repository", repository);

            routingContext.next();
        };
    }

    private Handler<RoutingContext> jsonHandler() {
        return routingContext -> {
            String username = routingContext.get("username");
            String repository = routingContext.get("repository");

            dependencySummaryProvider.fetchDependencySummary(username, repository)
                    .subscribe(dependencySummary -> {
                        final String json = Json.encodePrettily(dependencySummary);

                        routingContext.response()
                                .end(json);
                    }, errorHandler(routingContext));
        };
    }

    private Handler<RoutingContext> svgHandler() {
        final String pathToImages = configuration.webroot() + "/img/status/";
        final URL uptodate = Resources.getResource(pathToImages + "uptodate.svg");
        final URL outofdate = Resources.getResource(pathToImages + "outofdate.svg");

        return routingContext -> {
            final HttpServerRequest request = routingContext.request();

            String username = Optional.ofNullable(request.getParam("param0"))
                    .map(Strings::emptyToNull)
                    .orElseThrow(IllegalArgumentException::new);
            String repository = Optional.ofNullable(request.getParam("param1"))
                    .map(Strings::emptyToNull)
                    .orElseThrow(IllegalArgumentException::new);

            dependencySummaryProvider.fetchDependencySummary(username, repository)
                    .subscribe(dependencySummary -> {
                        String filename = (dependencySummary.isUpToDate() ?
                                uptodate : outofdate).getFile();

                        routingContext.response().sendFile(filename);
                    }, errorHandler(routingContext));
        };
    }

    private Action1<Throwable> errorHandler(RoutingContext routingContext) {
        return error -> {
            if (error.getCause() instanceof FileNotFoundException) {
                routingContext.fail(404);
            } else {
                routingContext.fail(error);
            }
        };
    }
}
