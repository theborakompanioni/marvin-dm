package com.github.theborakompanioni.marvin.model;

import org.apache.maven.shared.invoker.InvocationResult;

import java.util.List;

public interface SummaryInvocationResult {
    InvocationResult getInvocationResult();

    List<String> getOutputLines();
}
