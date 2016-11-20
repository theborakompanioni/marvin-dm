package com.github.theborakompanioni.marvin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = DefaultVersions.class)
public interface Versions {
    String getRequired();

    String getLatest();

    @JsonIgnore
    default boolean isOutOfDate() {
        return getLatest() != null && !getLatest().equals(getRequired());
    }

    @JsonIgnore
    default boolean isUpToDate() {
        return !isOutOfDate();
    }
}
