package jp.skypencil.guava;

import java.util.Map;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

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
        builder.put("com/google/common/io/BaseEncoding", "java/util/Base64");
        builder.put("com/google/common/base/Optional", "java/util/Optional");
        builder.put("com/google/common/base/Joiner", "java/util/StringJoiner");
        builder.put("com/google/common/base/Predicate", "java/util/function/Predicate");
        builder.put("com/google/common/base/Function", "java/util/function/Function");
        builder.put("com/google/common/base/Supplier", "java/util/function/Supplier");
        builder.put("com/google/common/collect/FluentIterable", "java/util/stream/Stream");
        builder.put("com/google/common/concurrent/SettableFuture", "java/util/concurrent/CompletableFuture");
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
        check(field.getType());

        super.visitField(field);
    }

    @Override
    public void visitMethod(Method method) {
        check(method.getReturnType());
        for (Type type : method.getArgumentTypes()) {
            check(type);
        }

        super.visitMethod(method);
    }

    private void check(Type type) {
        @DottedClassName
        String referenced = type.toString();
        @SlashedClassName
        String replacement = replacements.get(referenced.replace('.', '/'));

        if (replacement != null) {
            report(referenced, replacement.replace('/', '.'));
        }
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
