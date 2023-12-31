package tech.amereta.core.java.statement;

import tech.amereta.core.Expression;
import tech.amereta.core.Statement;

import java.util.Set;

/**
 * A return statement.
 */
public final class JavaReturnStatement implements Statement {

    private Expression expression;

    public static JavaReturnStatement builder() {
        return new JavaReturnStatement();
    }

    @Override
    public String render() {
        return "return " + expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

    public Expression getExpression() {
        return expression;
    }

    public JavaReturnStatement expression(Expression expression) {
        setExpression(expression);
        return this;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
