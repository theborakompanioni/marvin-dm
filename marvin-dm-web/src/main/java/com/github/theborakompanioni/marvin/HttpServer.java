package com.github.theborakompanioni.marvin;

import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.requireNonNull;

@Slf4j
class HttpServer extends AbstractVerticle {

    private AppConfiguration configuration;
    private Router router;

    public HttpServer(AppConfiguration configuration, Router router) {
        this.configuration = requireNonNull(configuration);
        this.router = requireNonNull(router);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(configuration.httpPort());
    }
}
