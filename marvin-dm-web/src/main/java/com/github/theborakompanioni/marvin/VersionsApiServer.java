package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.Objects.requireNonNull;


class VersionsApiServer extends AbstractVerticle {

    private final AppConfiguration configuration;
    private final MavenVersionUpdateFinder mavenVersionUpdateFinder;

    public VersionsApiServer(AppConfiguration configuration, MavenVersionUpdateFinder mavenVersionUpdateFinder) {
        this.configuration = requireNonNull(configuration);
        this.mavenVersionUpdateFinder = requireNonNull(mavenVersionUpdateFinder);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route("/:username/:repository").handler(routingContext -> {
            String username = routingContext.request().getParam("username");
            String repository = routingContext.request().getParam("repository");

            try {
                final DependencySummary dependencySummary = fetchDependencySummary(username, repository);

                routingContext.response()
                        .end("out of date: " + dependencySummary.isOutOfDate());

            } catch (IOException | MavenInvocationException e) {
                routingContext.fail(500);
            }
        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(configuration.httpPort());
    }

    private DependencySummary fetchDependencySummary(String username, String repository) throws IOException, MavenInvocationException {
        final String urlTemplate = "https://raw.githubusercontent.com/%s/%s/master/pom.xml";
        final String repositoryUrl = String.format(urlTemplate, username, repository);

        File tempPomFile = File.createTempFile("pom", ".tmp");
        URL url = new URL(repositoryUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempPomFile))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    bw.write(line);
                }
            }
        }

        final DependencySummary dependencySummary = mavenVersionUpdateFinder.accept(tempPomFile);

        if (!tempPomFile.delete()) {
            tempPomFile.deleteOnExit();
        }

        return dependencySummary;
    }
}
