package com.github.theborakompanioni.marvin;

import io.vertx.core.Vertx;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public StaticServer staticServer() {
        return new StaticServer(appConfiguration);
    }

    @Bean
    public VersionsApiServer versionsApiServer() {
        return new VersionsApiServer(appConfiguration, mavenVersionUpdateFinder());
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
