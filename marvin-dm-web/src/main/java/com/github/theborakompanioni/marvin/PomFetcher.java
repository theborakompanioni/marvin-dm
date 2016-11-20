package com.github.theborakompanioni.marvin;


import rx.Observable;

public interface PomFetcher {
    Observable<String> fetchPomFile(String username, String repository);
}
