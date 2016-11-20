package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class MavenVersionUpdateFinder {
    private static final List<String> GOALS = Collections.singletonList("versions:display-dependency-updates");
    private static final Pattern versionFromLogLinePattern = Pattern.compile("\\[INFO\\] (.*) (.*) -> (.*)");
    private static final Pattern nameFromLogLinePattern = Pattern.compile("\\[INFO\\]   (.*) \\..*");
    private static final String dependencyManagementStartPhrase = "[INFO] The following dependencies in Dependency Management have newer versions:";
    private static final String dependenciesStartPhrase = "[INFO] The following dependencies in Dependencies have newer versions:";
    private static final String stopPhrase = "[INFO]";
    private static final Function<String, Boolean> isStopPhrase = test -> test != null && stopPhrase.equals(test.trim());

    private final MavenInvoker invoker;

    public MavenVersionUpdateFinder(MavenInvoker invoker) {
        this.invoker = requireNonNull(invoker);
    }

    public DependencySummary accept(File pomFile) throws MavenInvocationException {
        requireNonNull(pomFile);

        InvocationRequest request = createInvocationRequest(pomFile);
        final SummaryInvocationResult invocationResult = invoker.apply(request);
        final List<String> outputLines = invocationResult.getOutputLines();

        final List<String> dependenciesLines = findLogLinesWithDependenciesUpdates(outputLines);
        Map<String, Versions> dependencyUpdates = findUpdates(dependenciesLines);

        final List<String> dependencyManagementLines = findLogLinesWithDependencyManagementUpdates(outputLines);
        Map<String, Versions> dependencyManagementUpdates = findUpdates(dependencyManagementLines);

        return DefaultDependencySummary.builder()
                .dependencies(dependencyUpdates)
                .dependencyManagement(dependencyManagementUpdates)
                .build();
    }

    private Map<String, Versions> findUpdates(List<String> versionLines) {
        requireNonNull(versionLines);

        final ImmutableMap.Builder<String, Versions> builder = ImmutableMap.builder();
        final Iterator<String> versionLinesIterator = versionLines.iterator();
        String next;
        while (versionLinesIterator.hasNext()) {
            next = versionLinesIterator.next();

            final String dependencyName = extractNameFromLogLine(next)
                    .orElseThrow(IllegalStateException::new);

            final Optional<Versions> versions = extractVersionFromLogLine(next);
            if (versions.isPresent()) {
                builder.put(dependencyName, versions.get());
            } else if (!versions.isPresent() && versionLinesIterator.hasNext()) {
                next = versionLinesIterator.next();
                final Versions versions2 = extractVersionFromLogLine(next)
                        .orElseThrow(IllegalStateException::new);

                builder.put(dependencyName, versions2);
            } else {
                throw new IllegalStateException("Cannot extract version for '" + dependencyName + "'");
            }
        }

        return builder.build();
    }

    private List<String> findLogLinesWithDependencyManagementUpdates(List<String> outputLines) {
        requireNonNull(outputLines);

        return extractLines(outputLines, dependencyManagementStartPhrase, isStopPhrase);
    }

    private List<String> findLogLinesWithDependenciesUpdates(List<String> outputLines) {
        requireNonNull(outputLines);

        return extractLines(outputLines, dependenciesStartPhrase, isStopPhrase);
    }

    private List<String> extractLines(List<String> lines, String startPhrase, Function<String, Boolean> isStopPhrase) {
        requireNonNull(lines);
        requireNonNull(startPhrase);
        requireNonNull(isStopPhrase);

        final int size = lines.size();
        final int startIndex = lines.indexOf(startPhrase) + 1;
        if (startIndex <= 0 || startIndex >= size) {
            return Collections.emptyList();
        }
        final List<String> strings = lines.subList(startIndex, lines.size());
        final Iterator<String> iterator = strings.iterator();

        final ImmutableList.Builder<String> output = ImmutableList.builder();

        String next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (isStopPhrase.apply(next)) {
                break;
            }
            output.add(next);
        }

        return output.build();
    }

    private Optional<Versions> extractVersionFromLogLine(String line) {
        final Matcher matcher = versionFromLogLinePattern.matcher(line);
        if (!matcher.matches()) {
            return Optional.empty();
        }

        final String required = matcher.group(2);
        final String latest = matcher.group(3);
        return Optional.of(DefaultVersions.builder()
                .required(required)
                .latest(latest)
                .build());
    }

    private Optional<String> extractNameFromLogLine(String line) {
        final Matcher matcher = nameFromLogLinePattern.matcher(line);
        if (!matcher.matches()) {
            return Optional.empty();
        }

        return Optional.of(matcher.group(1))
                .map(String::trim);
    }

    private InvocationRequest createInvocationRequest(File pomFile) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pomFile);
        request.setGoals(GOALS);
        return request;
    }
}
