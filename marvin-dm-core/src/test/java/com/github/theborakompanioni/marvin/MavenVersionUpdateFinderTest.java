package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import com.github.theborakompanioni.marvin.model.Versions;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.hamcrest.CustomMatcher;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Slf4j
public class MavenVersionUpdateFinderTest {
    private MavenVersionUpdateFinder sut;

    private File testPomFile;

    @Before
    public void init() {

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));

        MavenInvoker mavenInvoker = new MavenInvoker(invoker);
        this.sut = new MavenVersionUpdateFinder(mavenInvoker);

        final String fileName = Resources.getResource("test-pom.xml").getFile();
        this.testPomFile = new File(fileName);
        assertThat(this.testPomFile.exists(), is(true));
    }

    @Test
    public void itShouldFindUpdatesForJunit() throws Exception {
        final String junit = "junit:junit";
        final String requiredVersion = "4.11";

        final DependencySummary summary = sut.accept(testPomFile);

        assertThat(summary, is(notNullValue()));
        assertThat(summary.getDependencies(), is(notNullValue()));
        assertThat(summary.getDependencies(), hasEntry(equalTo(junit),
                new CustomMatcher<Versions>("") {
                    @Override
                    public boolean matches(Object item) {
                        Versions value = (Versions) item;
                        return requiredVersion.equals(value.getRequired()) &&
                                value.isOutOfDate();
                    }
                }));
    }

}