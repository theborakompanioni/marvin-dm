package com.github.theborakompanioni.marvin;

import com.github.theborakompanioni.marvin.model.DependencySummary;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.File;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr"));

        MavenInvoker mavenInvoker = new MavenInvoker(invoker);
        final MavenVersionUpdateFinder mavenVersionUpdateFinder = new MavenVersionUpdateFinder(mavenInvoker);

        try {
            final DependencySummary summary = mavenVersionUpdateFinder.accept(new File("pom.xml"));
            summary.getDependencies().forEach((key, value) -> {
                log.info("{} -> {}", key, value);
            });
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
    }
}
