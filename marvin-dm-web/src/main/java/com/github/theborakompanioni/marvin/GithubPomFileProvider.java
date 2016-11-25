package com.github.theborakompanioni.marvin;


import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientRequest;
import io.vertx.rxjava.core.http.HttpClientResponse;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;

import java.io.FileNotFoundException;

import static java.util.Objects.requireNonNull;

@Slf4j
public class GithubPomFileProvider implements PomFileProvider {

    private HttpClient httpClient;

    public GithubPomFileProvider(HttpClient httpClient) {
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
            log.debug("fetching {}", requestUrl);
            HttpClientRequest req = client.getAbs(requestUrl);
            Observable<HttpClientResponse> resp = req.toObservable();
            resp.subscribe(subscriber);
            req.end();
        })
                .map(response -> {
                    if (response.statusCode() == 404) {
                        throw new RuntimeException(new FileNotFoundException());
                    } else if (response.statusCode() != 200) {
                        throw new RuntimeException("response.status != 200");
                    }
                    return response;
                })
                .flatMap(HttpClientResponse::toObservable)
                .reduce(Buffer.buffer(), Buffer::appendBuffer)
                .map(response -> response.getString(0, response.length()));
    }
}
