package com.github.theborakompanioni.marvin;


import rx.Observable;

public interface PomFileProvider {
    Observable<String> fetchPomFile(String username, String repository);
}
