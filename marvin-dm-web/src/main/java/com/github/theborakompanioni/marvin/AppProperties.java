package com.github.theborakompanioni.marvin;


import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

@Data
@ConfigurationProperties("marvin")
public class AppProperties {
    private String mavenHome;
    private String webroot;

    public String getMavenHome() {
        return Optional.ofNullable(mavenHome)
                .map(Strings::emptyToNull)
                .map(String::trim)
                .orElseThrow(() -> new IllegalStateException("maven-home not set"));
    }

    public String getWebroot() {
        return Optional.ofNullable(webroot)
                .map(Strings::emptyToNull)
                .map(String::trim)
                .orElse("webroot");
    }
}
