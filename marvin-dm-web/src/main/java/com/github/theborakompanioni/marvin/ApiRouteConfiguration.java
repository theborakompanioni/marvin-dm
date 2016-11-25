package com.github.theborakompanioni.marvin;

import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.google.common.net.HttpHeaders;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.functions.Action1;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Function;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_MODIFIED;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static java.util.Objects.requireNonNull;


class ApiRouteConfiguration {

    private final AppConfiguration configuration;
    private final DependencySummaryProvider dependencySummaryProvider;
    private final Vertx vertx;

    public ApiRouteConfiguration(AppConfiguration configuration,
                                 Vertx vertx,
                                 DependencySummaryProvider dependencySummaryProvider) {
        this.configuration = requireNonNull(configuration);
        this.vertx = requireNonNull(vertx);
        this.dependencySummaryProvider = requireNonNull(dependencySummaryProvider);
    }

    public Router router() {
        Router router = Router.router(vertx);

        //router.route().handler(RouteHandlers.loggerHandler());
        router.route().handler(RouteHandlers.timeoutHandler());
        router.route().handler(RouteHandlers.responseTimeHandler());
        router.route().failureHandler(RouteHandlers.failureHandler());


        final int maxAgeSeconds = configuration.revalidateInSeconds();
        router.routeWithRegex(".*\\.svg")
                .pathRegex("\\/([^\\/]+)\\/([^\\/]+)\\.svg")
                .handler(serveFromCache(maxAgeSeconds));

        router.routeWithRegex(".*\\.svg")
                .pathRegex("\\/([^\\/]+)\\/([^\\/]+)\\.svg")
                .handler(addCacheHeaders(maxAgeSeconds));

        router.routeWithRegex(".*\\.svg")
                .pathRegex("\\/([^\\/]+)\\/([^\\/]+)\\.svg")
                .produces("image/svg+xml")
                .handler(svgHandler());


        router.route("/:username/:repository")
                .handler(contextSetupHandler());

        router.route("/:username/:repository")
                .handler(jsonHandler());

        return router;
    }

    private Handler<RoutingContext> serveFromCache(int maxAgeSeconds) {
        final ZoneId gmt = ZoneId.of("GMT");
        final Function<RoutingContext, Boolean> shouldUseCache = routingContext -> {
            String ifModifiedSince = routingContext.request().headers().get(
                    HttpHeaders.IF_MODIFIED_SINCE
            );
            if (ifModifiedSince == null) {
                // Not a conditional request
                return false;
            }
            ZonedDateTime ifModifiedSinceDate = ZonedDateTime.parse(ifModifiedSince, RFC_1123_DATE_TIME);
            // TODO: use real last modified date of resource (fetching time)
            final ZonedDateTime lastModified = ZonedDateTime.now(gmt).minus(maxAgeSeconds, ChronoUnit.SECONDS);
            boolean modifiedSince = lastModified.isAfter(ifModifiedSinceDate);
            return !modifiedSince;
        };
        return routingContext -> {
            if (shouldUseCache.apply(routingContext)) {
                routingContext.response().setStatusCode(NOT_MODIFIED.code()).end();
            } else {
                routingContext.next();
            }
        };
    }

    private Handler<RoutingContext> addCacheHeaders(int maxAgeSeconds) {
        return routingContext -> {
            final String now = RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
            MultiMap headers = routingContext.response().headers();
            headers.set(HttpHeaders.CACHE_CONTROL, "public, max-age=" + maxAgeSeconds);
            headers.set(HttpHeaders.LAST_MODIFIED, now);
            headers.set(HttpHeaders.DATE, now);
            routingContext.next();
        };
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
