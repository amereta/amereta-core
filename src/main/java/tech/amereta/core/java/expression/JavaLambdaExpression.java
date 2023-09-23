package tech.amereta.core.java.expression;

import tech.amereta.core.Expression;
import tech.amereta.core.Statement;
import tech.amereta.core.formatting.IndentingWriter;
import tech.amereta.core.formatting.SimpleIndentStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaLambdaExpression implements Expression {

    private List<String> consumer = new LinkedList<>();
    private List<Statement> statements = new LinkedList<>();

    public static JavaLambdaExpression builder() {
        return new JavaLambdaExpression();
    }

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.indented(() -> {
            writer.println("(" + String.join(", ", consumer) + ") -> {");
            writer.indented(() -> {
                getStatements().forEach(statement -> writer.println(statement.render()));
            });
            writer.print("}");
        });
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        return statements.stream().map(Statement::imports).flatMap(Set::stream).collect(Collectors.toSet());
    }

    public List<String> getConsumer() {
        return consumer;
    }

    public JavaLambdaExpression consumer(List<String> consumer) {
        setConsumer(consumer);
        return this;
    }

    public void setConsumer(List<String> consumer) {
        this.consumer = consumer;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public JavaLambdaExpression statements(List<Statement> statements) {
        setStatements(statements);
        return this;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
}
