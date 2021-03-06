package com.github.theborakompanioni.marvin;

import com.google.common.primitives.Ints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

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

    String webroot() {
        final String pathWhenInsideJarFile = "BOOT-INF/classes/" + appProperties.getWebroot();
        boolean insideJarFile = new ClassPathResource(pathWhenInsideJarFile).exists();
        return insideJarFile ? pathWhenInsideJarFile : appProperties.getWebroot();
    }

    int cacheTimeInSeconds() {
        return Ints.max(1, appProperties.getCacheTimeInSeconds());
    }

    int revalidateInSeconds() {
        return Ints.max(1, appProperties.getCacheTimeInSeconds() / 10);
    }
}
