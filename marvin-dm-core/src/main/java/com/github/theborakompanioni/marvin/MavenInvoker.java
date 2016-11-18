package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DefaultSummaryInvocationResult;
import com.github.theborakompanioni.marvin.model.SummaryInvocationResult;
import com.google.common.collect.ImmutableList;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class MavenInvoker implements Function<InvocationRequest, SummaryInvocationResult> {
    private final Invoker invoker;

    public MavenInvoker(Invoker invoker) {
        this.invoker = requireNonNull(invoker);
    }

    @Override
    public SummaryInvocationResult apply(InvocationRequest request) {
        final ImmutableList.Builder<String> outputLinesBuilder = ImmutableList.builder();
        request.setOutputHandler(outputLinesBuilder::add);

        final InvocationResult invocationResult;
        try {
            invocationResult = invoker.execute(request);
        } catch (MavenInvocationException e) {
            throw new RuntimeException(e);
        }

        return DefaultSummaryInvocationResult.builder()
                .invocationResult(invocationResult)
                .outputLines(outputLinesBuilder.build())
                .build();
    }
}
