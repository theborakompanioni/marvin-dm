package com.github.theborakompanioni.marvin;


import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientRequest;
import io.vertx.rxjava.core.http.HttpClientResponse;
import rx.Observable;

import static java.util.Objects.requireNonNull;

public class PomFetcherImpl implements PomFetcher {

    private HttpClient httpClient;

    public PomFetcherImpl(HttpClient httpClient) {
        this.httpClient = requireNonNull(httpClient);
    }

    @Override
    public Observable<String> fetchPomFile(String username, String repository) {
        final String host = "raw.githubusercontent.com";
        final String path = String.format("/%s/%s/master/pom.xml", username, repository);
        final String pomUrl = "https://" + host + path;

        return get(httpClient, pomUrl);
    }

    private Observable<String> get(HttpClient client, String requestUrl) {
        return Observable.<HttpClientResponse>create(subscriber -> {
            HttpClientRequest req = client.getAbs(requestUrl);
            Observable<HttpClientResponse> resp = req.toObservable();
            resp.subscribe(subscriber);
            req.end();
        })
                .flatMap(HttpClientResponse::toObservable)
                .reduce(Buffer.buffer(), Buffer::appendBuffer)
                .map(response -> response.getString(0, response.length()));
    }
}
