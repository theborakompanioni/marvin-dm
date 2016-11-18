package com.github.theborakompanioni.marvin.model;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface DependencySummary {
    Map<String, Versions> getDependencies();

    Map<String, Versions> getDependencyManagement();

    default boolean isUpToDate() {
        return Optional.ofNullable(getDependencies())
                .map(Map::values)
                .map(Collection::stream)
                .map(versions -> versions.allMatch(Versions::isUpToDate))
                .orElse(false);
    }

    default boolean isOutOfDate() {
        return !isUpToDate();
    }
}
