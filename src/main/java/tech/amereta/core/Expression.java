package tech.amereta.core;

import java.util.Set;

/**
 * A Java expression.
 */
public interface Expression {

    String render();

    Set<String> imports();

}
