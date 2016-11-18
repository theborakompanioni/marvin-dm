package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import com.github.theborakompanioni.marvin.model.Versions;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.hamcrest.CustomMatcher;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Slf4j
public class MavenVersionUpdateFinderTest {
    @Test
    public void accept() throws Exception {
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));

        MavenInvoker mavenInvoker = new MavenInvoker(invoker);
        final MavenVersionUpdateFinder mavenVersionUpdateFinder = new MavenVersionUpdateFinder(mavenInvoker);

        final String fileName = Resources.getResource("test-pom.xml").getFile();
        final File pomFile = new File(fileName);
        final DependencySummary summary = mavenVersionUpdateFinder.accept(pomFile);

        assertThat(summary, is(notNullValue()));
        assertThat(summary.getDependencies(), is(notNullValue()));
        assertThat(summary.getDependencies(), hasEntry(equalTo("junit:junit"),
                new CustomMatcher<Versions>("") {
                    @Override
                    public boolean matches(Object item) {
                        Versions value = (Versions) item;
                        return "4.11".equals(value.getRequired()) &&
                                value.isOutOfDate();
                    }
                }));
    }

}