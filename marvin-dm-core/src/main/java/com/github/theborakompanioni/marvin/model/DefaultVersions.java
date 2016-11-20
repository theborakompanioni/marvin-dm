package com.github.theborakompanioni.marvin.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = DefaultVersions.DefaultVersionsBuilder.class)
public class DefaultVersions implements Versions {

    @JsonPOJOBuilder(withPrefix = "")
    public static final class DefaultVersionsBuilder {
    }

    private String required;
    private String latest;
}
