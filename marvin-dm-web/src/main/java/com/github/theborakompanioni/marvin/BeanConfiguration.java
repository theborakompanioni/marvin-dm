package com.github.theborakompanioni.marvin;

import io.vertx.core.http.HttpClientOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Optional;

@Configuration
class BeanConfiguration {

    private AppConfiguration appConfiguration;

    @Autowired
    public BeanConfiguration(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    @Order(2)
    public StaticServer staticServer() {
        return new StaticServer(appConfiguration);
    }

    @Bean
    @Order(1)
    public VersionsApiServer versionsApiServer() {
        return new VersionsApiServer(appConfiguration, dependencySummaryProvider());
    }

    @Bean
    public DependencySummaryProvider dependencySummaryProvider() {
        return new DependencySummaryProviderImpl(mavenVersionUpdateFinder(), pomFetcher());
    }

    @Bean
    public HttpClient httpClient() {
        HttpClientOptions options = new HttpClientOptions()
                .setSsl(true)
                .setKeepAlive(false)
                .setLogActivity(true);
        return vertx().createHttpClient(options);
    }

    @Bean
    public PomFileProvider pomFetcher() {
        return new GithubPomFileProvider(httpClient());
    }

    @Bean
    public Invoker invoker() {
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(mavenHome());
        return invoker;
    }

    @Bean
    public File mavenHome() {
        return Optional.of(new File(appConfiguration.mavenHome()))
                .filter(File::exists)
                .orElseThrow(() -> new IllegalStateException("Maven Home dir does not exist"));
    }

    @Bean
    public String webroot() {
        final String pathWhenInsideJarFile = "BOOT-INF/classes/webroot";
        boolean insideJarFile = new ClassPathResource(pathWhenInsideJarFile).exists();
        return insideJarFile ? pathWhenInsideJarFile : "webroot";
    }

    @Bean
    public MavenInvoker mavenInvoker() {
        return new MavenInvoker(invoker());
    }

    @Bean
    public MavenVersionUpdateFinder mavenVersionUpdateFinder() {
        return new MavenVersionUpdateFinder(mavenInvoker());
    }

}
