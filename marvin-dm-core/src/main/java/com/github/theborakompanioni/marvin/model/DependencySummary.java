package com.github.theborakompanioni.marvin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@JsonDeserialize(as = DefaultDependencySummary.class)
public interface DependencySummary {
    Map<String, Versions> getDependencies();

    Map<String, Versions> getDependencyManagement();

    @JsonIgnore
    default boolean isUpToDate() {
        return Optional.ofNullable(getDependencies())
                .map(Map::values)
                .map(Collection::stream)
                .map(versions -> versions.allMatch(Versions::isUpToDate))
                .orElse(false);
    }

    @JsonIgnore
    default boolean isOutOfDate() {
        return !isUpToDate();
    }
}
