package com.github.theborakompanioni.marvin;

import io.vertx.core.http.HttpClientOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.File;

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
    @DependsOn("versionsApiServer")
    public StaticServer staticServer() {
        return new StaticServer(appConfiguration);
    }

    @Bean
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
        invoker.setMavenHome(new File(appConfiguration.mavenHome()));
        return invoker;
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
