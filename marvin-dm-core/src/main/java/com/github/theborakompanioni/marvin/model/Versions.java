package com.github.theborakompanioni.marvin.model;

public interface Versions {
    String getRequired();

    String getLatest();

    default boolean isOutOfDate() {
        return getLatest() != null && !getLatest().equals(getRequired());
    }

    default boolean isUpToDate() {
        return !isOutOfDate();
    }
}
