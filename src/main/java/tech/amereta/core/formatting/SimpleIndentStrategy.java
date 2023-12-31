package tech.amereta.core.formatting;


import java.util.function.Function;

/**
 * A simple indenting strategy that uses a configurable {@code indent} value.
 */
public record SimpleIndentStrategy(String indent) implements Function<Integer, String> {

    /**
     * Create a new instance with the indent style to apply.
     *
     * @param indent the indent to apply for each indent level
     */
    public SimpleIndentStrategy {
        assert indent != null;
    }

    @Override
    public String apply(Integer level) {
        if (level < 0)
            throw new IllegalArgumentException("Indent level must not be negative, got" + level);

        return String.valueOf(this.indent).repeat(level);
    }

}
