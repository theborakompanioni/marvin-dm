package com.github.theborakompanioni.marvin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static java.util.Objects.requireNonNull;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
class AppConfiguration {

    private final Environment environment;
    private final AppProperties appProperties;

    @Autowired
    public AppConfiguration(Environment environment, AppProperties appProperties) {
        this.environment = requireNonNull(environment);
        this.appProperties = requireNonNull(appProperties);
    }

    int httpPort() {
        return environment.getProperty("server.port", Integer.class, 8080);
    }

    String mavenHome() {
        return appProperties.getMavenHome();
    }
}
