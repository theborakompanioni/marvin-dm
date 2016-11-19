package com.github.theborakompanioni.marvin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
class AppConfiguration {

    private Environment environment;

    @Autowired
    public AppConfiguration(Environment environment) {
        this.environment = environment;
    }

    int httpPort() {
        return environment.getProperty("http.port", Integer.class, 8080);
    }

    String mavenHome() {
        return environment.getProperty("maven.home", "/usr");
    }
}
