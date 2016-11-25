package com.github.theborakompanioni.marvin;

import io.vertx.core.http.HttpClientOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
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
    public HttpServer webServer(Router router) {
        return new HttpServer(appConfiguration, router);
    }

    @Bean
    public ApiRouteConfiguration apiRouteConfiguration(DependencySummaryProvider dependencySummaryProvider) {
        return new ApiRouteConfiguration(appConfiguration, vertx(), dependencySummaryProvider);
    }

    @Bean
    public Router router(ApiRouteConfiguration apiRouteConfiguration) {
        Router router = Router.router(vertx());
        router.mountSubRouter("/api", apiRouteConfiguration.router());
        router.route().handler(staticHandler());

        return router;
    }

    @Bean
    public StaticHandler staticHandler() {
        String webroot = appConfiguration.webroot();
        log.info("Using '{}' as static webroot", webroot);

        return StaticHandler.create(webroot);
    }
/*
    @Bean
    @Order(2)
    public StaticServer staticServer() {
        return new StaticServer(appConfiguration);
    }

    @Bean
    @Order(1)
    public VersionsApiServer versionsApiServer(DependencySummaryProvider dependencySummaryProvider) {
        return new VersionsApiServer(appConfiguration, dependencySummaryProvider);
    }*/

    @Bean
    public DependencySummaryProvider dependencySummaryProvider(DependencySummaryCache dependencySummaryCache) {
        return new DependencySummaryProviderImpl(mavenVersionUpdateFinder(), pomFetcher(), dependencySummaryCache);
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
    @Profile("production")
    public DB productionCache() {
        Path file = Paths.get("dependency-summary.db");
        return DBMaker.fileDB(file.toString())
                .closeOnJvmShutdown()
                .make();
    }

    @Bean
    @ConditionalOnMissingBean(DB.class)
    public DB developmentCache() {
        Path file = Paths.get("dependency-summary-dev.db");
        return DBMaker.fileDB(file.toString())
                .fileDeleteAfterClose()
                .closeOnJvmShutdown()
                .make();
    }

    @Bean
    public HTreeMap<String, String> cacheMap(DB db) {
        return db.hashMap("dependency-summary-cache")
                .expireAfterCreate(appConfiguration.cacheTimeInSeconds(), TimeUnit.SECONDS)
                .expireMaxSize(32 * 1024 * 1024)
                .expireExecutor(Executors.newScheduledThreadPool(2))
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.STRING)
                .createOrOpen();
    }

    @Bean
    public DependencySummaryCache dependencySummaryCache(HTreeMap<String, String> cacheMap) {
        return new DependencySummaryCache(cacheMap);
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
