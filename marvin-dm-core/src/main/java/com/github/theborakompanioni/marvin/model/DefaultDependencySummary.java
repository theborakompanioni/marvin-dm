package com.github.theborakompanioni.marvin.model;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class DefaultDependencySummary implements DependencySummary {
    private Map<String, Versions> dependencies;
    private Map<String, Versions> dependencyManagement;
}
