package com.github.theborakompanioni.marvin.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@JsonDeserialize(builder = DefaultDependencySummary.DefaultDependencySummaryBuilder.class)
public class DefaultDependencySummary implements DependencySummary {

    @JsonPOJOBuilder(withPrefix = "")
    public static final class DefaultDependencySummaryBuilder {
    }

    private Map<String, Versions> dependencies;
    private Map<String, Versions> dependencyManagement;
}
