package tech.amereta.core.java.util;

import tech.amereta.core.java.JavaSourceCodeWriter;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An annotation.
 */
public final class JavaAnnotation {

    private List<Attribute> attributes = new LinkedList<>();
    private String name;

    public static JavaAnnotation builder() {
        return new JavaAnnotation();
    }

    public String render() {
        return render(true);
    }

    public String render(boolean newLine) {
        final StringBuilder annotation = new StringBuilder();
        annotation.append("@").append(JavaSourceCodeWriter.getUnqualifiedName(this.name));
        final List<JavaAnnotation.Attribute> attributes = this.getAttributes();
        if (!attributes.isEmpty()) {
            annotation.append("(");
            if (attributes.get(0).getName() != null) {
                if (attributes.size() == 1 && attributes.get(0).getName().equals("value")) {
                    annotation.append(attributes.get(0).render());
                } else {
                    annotation.append(attributes.stream()
                            .map((attribute) -> attribute.getName() + " = " + attribute.render())
                            .collect(Collectors.joining(", ")));
                }
            } else {
                annotation.append(attributes.get(0).render());
            }
            annotation.append(")");
        }
        if (newLine) {
            annotation.append(System.lineSeparator());
        }
        return annotation.toString();
    }

    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.name)) imports.add(this.name);
        this.attributes.forEach((attribute) -> {
            if (attribute.getDataType() == Class.class) {
                imports.addAll(attribute.getValues().stream().map(String::valueOf).toList());
            }
            if (Enum.class.isAssignableFrom(attribute.getDataType())) {
                imports.addAll(attribute.getValues().stream().map(String::valueOf).map((value) -> value.substring(0, value.lastIndexOf("."))).toList());
            }
            if (attribute.getDataType() == Annotation.class) {
                imports.addAll(attribute.getValues().stream().map(value -> ((JavaAnnotation) value).imports()).flatMap(Set::stream).toList());
            }
        });
        return new LinkedHashSet<>(imports);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public JavaAnnotation attributes(List<Attribute> attributes) {
        setAttributes(attributes);
        return this;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public JavaAnnotation name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Define an attribute of an annotation.
     */
    public static class Attribute {

        private List<Object> values = new LinkedList<>();
        private String name;
        private Class<?> dataType;

        public static Attribute builder() {
            return new Attribute();
        }

        public String render() {
            if (this.dataType.equals(Class.class)) {
                return formatValues(values, (value) -> String.format("%s.class", JavaSourceCodeWriter.getUnqualifiedName(String.valueOf(value))));
            }
            if (Enum.class.isAssignableFrom(this.dataType)) {
                return formatValues(values, (value) -> {
                    String enumValue = String.valueOf(value).substring(String.valueOf(value).lastIndexOf(".") + 1);
                    String enumClass = String.valueOf(value).substring(0, String.valueOf(value).lastIndexOf("."));
                    return String.format("%s.%s", JavaSourceCodeWriter.getUnqualifiedName(enumClass), enumValue);
                });
            }
            if (this.dataType.equals(String.class)) {
                return formatValues(values, (value) -> String.format("\"%s\"", value));
            }
            if (this.dataType.equals(Annotation.class)) {
                return formatValues(values, (value) -> ((JavaAnnotation) value).render().replaceAll("\n", ""));
            }
            return formatValues(values, (value) -> String.format("%s", value));
        }

        public List<Object> getValues() {
            return values;
        }

        public Attribute values(List<Object> values) {
            setValues(values);
            return this;
        }

        public void setValues(List<Object> values) {
            this.values = values;
        }

        public String getName() {
            return name;
        }

        public Attribute name(String name) {
            setName(name);
            return this;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<?> getDataType() {
            return dataType;
        }

        public Attribute dataType(Class<?> dataType) {
            setDataType(dataType);
            return this;
        }

        public void setDataType(Class<?> dataType) {
            this.dataType = dataType;
        }

        private String formatValues(final List<Object> values, final Function<Object, String> formatter) {
            final String result = values.stream().map(formatter).collect(Collectors.joining(", "));
            return (values.size() > 1) ? "{ " + result + " }" : result;
        }
    }
}
