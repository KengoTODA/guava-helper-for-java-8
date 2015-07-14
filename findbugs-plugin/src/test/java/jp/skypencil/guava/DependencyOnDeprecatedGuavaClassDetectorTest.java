package jp.skypencil.guava;

import org.junit.Test;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.*;

import edu.umd.cs.findbugs.BugReporter;

public class DependencyOnDeprecatedGuavaClassDetectorTest {

    @Test
    public void test() throws Exception {
        BugReporter bugReporter = bugReporterForTesting();
        DependencyOnDeprecatedGuavaClassDetector detector = new DependencyOnDeprecatedGuavaClassDetector(
                bugReporter);

        assertNoBugsReported(Java8OptionalField.class, detector, bugReporter);
        assertNoBugsReported(Java8OptionalFieldWithInitialization.class, detector, bugReporter);
        assertNoBugsReported(Java8OptionalMethod.class, detector, bugReporter);
        assertBugReported(GuavaOptionalField.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
        assertBugReported(GuavaOptionalFieldWithInitialization.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
        assertBugReported(GuavaOptionalMethod.class, detector, bugReporter,
                ofType("GUAVA_DEPEND_ON_DEPRECATED_CLASS"));
    }

}
