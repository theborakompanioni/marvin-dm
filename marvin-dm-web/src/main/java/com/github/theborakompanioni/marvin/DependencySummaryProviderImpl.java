package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.jetbrains.annotations.NotNull;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

@Slf4j
public class DependencySummaryProviderImpl implements DependencySummaryProvider {
    private final MavenVersionUpdateFinder mavenVersionUpdateFinder;
    private final PomFileProvider pomFileProvider;
    private final DependencySummaryCache cache;

    public DependencySummaryProviderImpl(MavenVersionUpdateFinder mavenVersionUpdateFinder, PomFileProvider pomFileProvider, DependencySummaryCache cache) {
        this.mavenVersionUpdateFinder = requireNonNull(mavenVersionUpdateFinder);
        this.pomFileProvider = requireNonNull(pomFileProvider);
        this.cache = requireNonNull(cache);
    }

    @Override
    public Observable<DependencySummary> fetchDependencySummary(String username, String repository) {
        final String cacheKey = username + "/" + repository;
        return cache.get(cacheKey)
                .doOnNext(next -> log.debug("returning from cache {}", next.getClass().getSimpleName()))
                .switchIfEmpty(pomFileProvider.fetchPomFile(username, repository)
                        .observeOn(Schedulers.io())
                        .map(writeToFile())
                        .map(findDependenciesAndDeleteFile())
                        .doOnNext(summary -> cache.put(cacheKey, summary)
                                .doOnSubscribe(() -> log.debug("Caching {}", cacheKey))
                                .subscribe())
                        .subscribeOn(Schedulers.trampoline()))
                .doOnNext(next -> log.debug("returning {}: {}", next.getClass().getSimpleName(), cacheKey));
    }

    @NotNull
    private Func1<File, DependencySummary> findDependenciesAndDeleteFile() {
        return file -> {
            try {
                return mavenVersionUpdateFinder.accept(file);
            } catch (MavenInvocationException e) {
                throw new RuntimeException(e);
            } finally {
                log.debug("attempt to delete file {}", file.getPath());
                if (!file.delete()) {
                    log.warn("Could not delete file {}. Attempt to delete on jvm exit.", file.getPath());
                    file.deleteOnExit();
                }
            }
        };
    }

    private Func1<String, File> writeToFile() {
        return contents -> {
            try {
                File tempPomFile = File.createTempFile("pom", ".tmp");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempPomFile))) {
                    bw.write(contents);
                }
                return tempPomFile;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        };
    }
}
