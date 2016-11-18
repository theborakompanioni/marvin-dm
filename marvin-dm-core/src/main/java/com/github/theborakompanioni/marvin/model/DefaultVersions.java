package com.github.theborakompanioni.marvin.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DefaultVersions implements Versions {
    private String required;
    private String latest;
}
