package jp.skypencil.guava;

import org.junit.Test;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.*;
import edu.umd.cs.findbugs.BugReporter;

public class DependencyOnDeprecatedGuavaClassDetectorTest {

    @Test
    public void detectInvoking() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalMethodInvoking.class, detector, bugReporter);
        assertBugReported(GuavaOptionalInvoking.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }

    @Test
    public void detectField() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalField.class, detector, bugReporter);
        assertBugReported(GuavaOptionalField.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }

    @Test
    public void detectFieldWithInitialization() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalFieldWithInitialization.class, detector, bugReporter);
        assertBugReported(GuavaOptionalFieldWithInitialization.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }

    @Test
    public void detectMethodArgument() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalMethodArgument.class, detector, bugReporter);
        assertBugReported(GuavaOptionalMethodArgument.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }

    @Test
    public void detectMethodArgumentInInterface() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalInterfaceMethodArgument.class, detector, bugReporter);
        assertBugReported(GuavaOptionalInterfaceMethodArgument.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }

    @Test
    public void detectReturnedType() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalReturn.class, detector, bugReporter);
        assertBugReported(GuavaOptionalReturn.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }

    @Test
    public void detectReturnedTypeInInterface() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalInterfaceReturn.class, detector, bugReporter);
        assertBugReported(GuavaOptionalInterfaceReturn.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }
}
