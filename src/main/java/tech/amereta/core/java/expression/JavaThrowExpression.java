package tech.amereta.core.java.expression;

public class JavaThrowExpression extends JavaNewInstanceExpression {

    public static JavaThrowExpression builder() {
        return new JavaThrowExpression();
    }

    @Override
    public String render() {
        return "throw " + super.render();
    }
}
