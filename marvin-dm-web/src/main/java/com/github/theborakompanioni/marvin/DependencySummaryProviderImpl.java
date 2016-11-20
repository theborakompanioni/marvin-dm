package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import org.apache.maven.shared.invoker.MavenInvocationException;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class DependencySummaryProviderImpl implements DependencySummaryProvider {
    private final MavenVersionUpdateFinder mavenVersionUpdateFinder;
    private final PomFileProvider pomFileProvider;

    public DependencySummaryProviderImpl(MavenVersionUpdateFinder mavenVersionUpdateFinder, PomFileProvider pomFileProvider) {
        this.mavenVersionUpdateFinder = requireNonNull(mavenVersionUpdateFinder);
        this.pomFileProvider = requireNonNull(pomFileProvider);
    }

    @Override
    public Observable<DependencySummary> fetchDependencySummary(String username, String repository) {
        return pomFileProvider.fetchPomFile(username, repository)
                .observeOn(Schedulers.io())
                .map(contents -> {
                    try {
                        File tempPomFile = File.createTempFile("pom", ".tmp");
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempPomFile))) {
                            bw.write(contents);
                        }
                        return tempPomFile;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                })
                .map(file -> {
                    try {
                        return mavenVersionUpdateFinder.accept(file);
                    } catch (MavenInvocationException e) {
                        throw new RuntimeException(e);
                    } finally {
                        if (!file.delete()) {
                            file.deleteOnExit();
                        }
                    }
                })
                .subscribeOn(Schedulers.trampoline());
    }
}
