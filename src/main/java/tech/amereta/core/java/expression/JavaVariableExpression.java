package tech.amereta.core.java.expression;

import tech.amereta.core.Expression;
import tech.amereta.core.java.expression.util.Operable;

import java.util.LinkedHashSet;
import java.util.Set;

public class JavaVariableExpression extends Operable implements Expression {

    private String variable;

    public static JavaVariableExpression builder() {
        return new JavaVariableExpression();
    }

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + variable;
        return variable + super.render();
    }

    @Override
    public Set<String> imports() {
        return new LinkedHashSet<>();
    }

    public String getVariable() {
        return variable;
    }

    public JavaVariableExpression variable(String variable) {
        setVariable(variable);
        return this;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
