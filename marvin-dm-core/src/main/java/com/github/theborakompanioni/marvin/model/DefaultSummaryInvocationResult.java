package com.github.theborakompanioni.marvin.model;

import lombok.Builder;
import lombok.Value;
import org.apache.maven.shared.invoker.InvocationResult;

import java.util.List;

@Value
@Builder
public class DefaultSummaryInvocationResult implements SummaryInvocationResult {

    InvocationResult invocationResult;

    List<String> outputLines;
}
