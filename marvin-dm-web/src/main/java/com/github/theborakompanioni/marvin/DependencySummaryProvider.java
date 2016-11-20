package com.github.theborakompanioni.marvin;


import com.github.theborakompanioni.marvin.model.DependencySummary;
import rx.Observable;

public interface DependencySummaryProvider {
    Observable<DependencySummary> fetchDependencySummary(String username, String repository);
}
