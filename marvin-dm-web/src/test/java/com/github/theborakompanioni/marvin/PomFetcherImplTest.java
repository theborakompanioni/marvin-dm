package com.github.theborakompanioni.marvin;

import io.vertx.core.http.HttpClientOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;


public class PomFetcherImplTest {
    private PomFetcherImpl sut;

    @Before
    public void setUp() {
        final HttpClientOptions httpClientOptions = new HttpClientOptions().setSsl(true);
        final HttpClient httpClient = Vertx.vertx().createHttpClient(httpClientOptions);
        this.sut = new PomFetcherImpl(httpClient);
    }
    
    @Test
    public void fetchPomFile() throws Exception {
        final Observable<String> pom = sut.fetchPomFile("theborakompanioni", "marvin-dm");

        final String pomContents = pom.toBlocking().single();
        assertThat(pomContents, is(notNullValue()));
        assertThat(pomContents, startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
        assertThat(pomContents, containsString("<groupId>com.github.theborakompanioni</groupId>"));
        assertThat(pomContents, containsString("<artifactId>marvin-dm-parent</artifactId>"));
    }
}
