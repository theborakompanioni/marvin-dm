package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import com.google.common.base.Strings;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import org.apache.maven.shared.invoker.MavenInvocationException;
import rx.schedulers.Schedulers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import static java.util.Objects.requireNonNull;


class VersionsApiServer extends AbstractVerticle {

    private final AppConfiguration configuration;
    private final MavenVersionUpdateFinder mavenVersionUpdateFinder;
    private final PomFetcher pomFetcher;

    public VersionsApiServer(AppConfiguration configuration, MavenVersionUpdateFinder mavenVersionUpdateFinder, PomFetcher pomFetcher) {
        this.configuration = requireNonNull(configuration);
        this.mavenVersionUpdateFinder = requireNonNull(mavenVersionUpdateFinder);
        this.pomFetcher = requireNonNull(pomFetcher);
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

            pomFetcher.fetchPomFile(username, repository)
                    .observeOn(Schedulers.io())
                    .map(contents -> {
                        try {
                            File tempPomFile = File.createTempFile("pom", ".tmp");
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempPomFile))) {
                                bw.write(contents);
                            }
                            return tempPomFile;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    })
                    .map(file -> {
                        try {
                            return mavenVersionUpdateFinder.accept(file);
                        } catch (MavenInvocationException e) {
                            throw new RuntimeException(e);
                        } finally {
                            if (!file.delete()) {
                                file.deleteOnExit();
                            }
                        }
                    })
                    .subscribeOn(Schedulers.trampoline())
                    .subscribe(dependencySummary -> routingContext.response()
                                    .end("out of date: " + dependencySummary.isOutOfDate()));

        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(configuration.httpPort());
    }
}
