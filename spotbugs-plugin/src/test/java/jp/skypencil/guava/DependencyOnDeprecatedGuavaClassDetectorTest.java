package jp.skypencil.guava;

import static edu.umd.cs.findbugs.test.CountMatcher.containsExactly;
import static org.junit.Assert.assertThat;

import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.test.SpotBugsRule;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcher;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcherBuilder;

public class DependencyOnDeprecatedGuavaClassDetectorTest {
    private final BugInstanceMatcher MATCHER = new BugInstanceMatcherBuilder().bugType("GUAVA_DEPEND_ON_DEPRECATED_CLASS").build();

    @Rule
    public SpotBugsRule spotbugs = new SpotBugsRule();

    @Test
    public void detectInvoking() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "Java8OptionalMethodInvoking.class"));
        assertThat(detected, containsExactly(0, MATCHER));
    }

    @Test
    public void detectInvokingGuava() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "GuavaOptionalField.class"));
        assertThat(detected, containsExactly(1, MATCHER));
    }

    @Test
    public void detectField() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "Java8OptionalField.class"));
        assertThat(detected, containsExactly(0, MATCHER));
    }

    @Test
    public void detectFieldGuava() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "GuavaOptionalField.class"));
        assertThat(detected, containsExactly(1, MATCHER));
    }

    @Test
    public void detectFieldWithInitialization() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "Java8OptionalFieldWithInitialization.class"));
        assertThat(detected, containsExactly(0, MATCHER));
    }

    @Test
    public void detectFieldWithInitializationGuava() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "GuavaOptionalFieldWithInitialization.class"));
        assertThat(detected, containsExactly(1, MATCHER));
    }

    @Test
    public void detectMethodArgument() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "Java8OptionalMethodArgument.class"));
        assertThat(detected, containsExactly(0, MATCHER));
    }

    @Test
    public void detectMethodArgumentGuava() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "GuavaOptionalMethodArgument.class"));
        assertThat(detected, containsExactly(1, MATCHER));
    }

    @Test
    public void detectMethodArgumentInInterface() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "Java8OptionalInterfaceMethodArgument.class"));
        assertThat(detected, containsExactly(0, MATCHER));
    }

    @Test
    public void detectMethodArgumentInInterfaceGuava() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "GuavaOptionalInterfaceMethodArgument.class"));
        assertThat(detected, containsExactly(1, MATCHER));
    }

    @Test
    public void detectReturnedType() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "Java8OptionalReturn.class"));
        assertThat(detected, containsExactly(0, MATCHER));
    }

    @Test
    public void detectReturnedTypeGuava() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "GuavaOptionalReturn.class"));
        assertThat(detected, containsExactly(1, MATCHER));
    }

    @Test
    public void detectReturnedTypeInInterface() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "Java8OptionalInterfaceReturn.class"));
        assertThat(detected, containsExactly(0, MATCHER));
    }

    @Test
    public void detectReturnedTypeInInterfaceGuava() {
        spotbugs.addAuxClasspathEntry(Paths.get(System.getProperty("user.home"), ".m2/repository/com/google/guava/guava/21.0/guava-21.0.jar"));
        BugCollection detected = spotbugs.performAnalysis(Paths.get("target", "test-classes", "jp", "skypencil", "guava", "GuavaOptionalInterfaceReturn.class"));
        assertThat(detected, containsExactly(1, MATCHER));
    }
}
