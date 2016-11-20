package com.github.theborakompanioni.marvin;


import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

@Data
@ConfigurationProperties("marvin")
public class AppProperties {
    private String mavenHome;

    public String getMavenHome() {
        return Optional.ofNullable(mavenHome)
                .map(String::trim)
                .map(Strings::emptyToNull)
                .orElseThrow(() -> new IllegalStateException("maven-home not set"));
    }
}
