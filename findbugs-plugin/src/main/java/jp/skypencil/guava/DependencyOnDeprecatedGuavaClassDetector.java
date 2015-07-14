package jp.skypencil.guava;

import java.util.Map;

import org.apache.bcel.classfile.Field;

import com.google.common.collect.ImmutableMap;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.internalAnnotations.DottedClassName;
import edu.umd.cs.findbugs.internalAnnotations.SlashedClassName;

public class DependencyOnDeprecatedGuavaClassDetector extends BytecodeScanningDetector {
    private Map<String, String> replacements = listUpTarget();
    private final BugReporter bugReporter;

    public DependencyOnDeprecatedGuavaClassDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    private Map<String, String> listUpTarget() {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("com/google/common/base/Optional", "java/util/Optional");
        return builder.build();
    }

    @Override
    public boolean beforeOpcode(int seen) {
        if ((seen == INVOKESPECIAL) || (seen == INVOKESTATIC)
                || (seen == INVOKEVIRTUAL) || (seen == INVOKEDYNAMIC)) {
            @SlashedClassName
            String referenced = getClassConstantOperand();
            @SlashedClassName
            String replacement = replacements.get(referenced);
            if (replacement != null) {
                report(referenced.replace('/', '.'), replacement.replace('/', '.'));
            }
        }

        return super.beforeOpcode(seen);
    }

    @Override
    public void visitField(Field field) {
        @DottedClassName
        String referenced = field.getType().toString();
        @SlashedClassName
        String replacement = replacements.get(referenced.replace('.', '/'));

        if (replacement != null) {
            report(referenced, replacement.replace('/', '.'));
        }
        super.visitField(field);
    }

    private void report(@DottedClassName String referenced, @DottedClassName String replacement) {
        BugInstance bug = new BugInstance(this, "GUAVA_DEPEND_ON_DEPRECATED_CLASS", NORMAL_PRIORITY);
        bug.addString(referenced);
        bug.addString(replacement);
        if (this.visitingMethod()) {
            bug.addSourceLine(this);
            bug.addClassAndMethod(this);
        } else if (this.visitingField()) {
            bug.addField(this);
        }

        bugReporter.reportBug(bug);
    }
}
