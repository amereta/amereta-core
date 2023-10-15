package tech.amereta.core.java.expression;

import tech.amereta.core.Expression;
import tech.amereta.core.java.JavaSourceCodeWriter;
import tech.amereta.core.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.java.expression.util.Operable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An invocation of a method.
 */
public class JavaMethodInvocationExpression extends Operable implements Expression {

    private List<JavaMethodInvoke> invokes = new LinkedList<>();
    private String target;

    public static JavaMethodInvocationExpression builder() {
        return new JavaMethodInvocationExpression();
    }

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + renderTarget()
                    + invokes.stream().map(invoke -> invoke.render(target)).collect(Collectors.joining());
        else
            return renderTarget()
                    + invokes.stream().map(invoke -> invoke.render(target)).collect(Collectors.joining()) + super.render();

    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.target)) imports.add(this.target);
        imports.addAll(this.invokes.stream().map(JavaMethodInvoke::imports).flatMap(Collection::stream).toList());
        return new LinkedHashSet<>(imports);
    }

    public List<JavaMethodInvoke> getInvokes() {
        return invokes;
    }

    public JavaMethodInvocationExpression invokes(List<JavaMethodInvoke> invokes) {
        setInvokes(invokes);
        return this;
    }

    public void setInvokes(List<JavaMethodInvoke> invokes) {
        this.invokes = invokes;
    }

    public String getTarget() {
        return target;
    }

    public JavaMethodInvocationExpression target(String target) {
        setTarget(target);
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private String renderTarget() {
        return "this".equals(this.target) ? "" : JavaSourceCodeWriter.getUnqualifiedName(this.target);
    }
}
