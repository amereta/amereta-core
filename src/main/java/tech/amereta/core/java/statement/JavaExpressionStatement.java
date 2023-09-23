package tech.amereta.core.java.statement;

import tech.amereta.core.Expression;
import tech.amereta.core.Statement;

import java.util.Set;

/**
 * A statement that contains a single expression.
 */
public final class JavaExpressionStatement implements Statement {

    private Expression expression;

    public static JavaExpressionStatement builder() {
        return new JavaExpressionStatement();
    }

    @Override
    public String render() {
        return expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

    public Expression getExpression() {
        return expression;
    }

    public JavaExpressionStatement expression(Expression expression) {
        setExpression(expression);
        return this;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
