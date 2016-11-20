package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import io.vertx.core.json.Json;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


public class DependencySummaryCache {

    private final Map<String, String> cache;

    public DependencySummaryCache(Map<String, String> cache) {
        this.cache = cache;
    }

    public Observable<DependencySummary> get(final String request) {
        return Observable.fromCallable(() -> cache.get(createCacheKey(request)))
                .filter(next -> next != null)
                .map(json -> Json.decodeValue(json, DependencySummary.class));
    }

    public Observable<DependencySummary> put(final String request, final DependencySummary weather) {
        return Observable.create(subscriber -> {
            final String cacheKey = createCacheKey(request);

            Optional.ofNullable(cache.put(cacheKey, Json.encode(weather)))
                    .map(formerValue -> Json.decodeValue(formerValue, DependencySummary.class))
                    .ifPresent(subscriber::onNext);

            subscriber.onCompleted();
        });
    }

    private String createCacheKey(final String request) {
        final LocalDateTime now = LocalDateTime.now();
        return request + "/" + now.getDayOfYear() + "/" + now.getHour();
    }
}
