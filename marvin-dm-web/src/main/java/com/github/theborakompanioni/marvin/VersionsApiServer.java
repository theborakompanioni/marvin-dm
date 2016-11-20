package com.github.theborakompanioni.marvin;

import com.google.common.base.Strings;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;

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

        router.route("/:username/:repository").handler(routingContext -> {
            String username = Optional.ofNullable(routingContext.request().getParam("username"))
                    .map(Strings::emptyToNull)
                    .orElseThrow(IllegalArgumentException::new);

            String repository = Optional.ofNullable(routingContext.request().getParam("repository"))
                    .map(Strings::emptyToNull)
                    .orElseThrow(IllegalArgumentException::new);

            dependencySummaryProvider.fetchDependencySummary(username, repository)
                    .subscribe(dependencySummary -> routingContext.response()
                            .end("out of date: " + dependencySummary.isOutOfDate()));

        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(configuration.httpPort());
    }
}
