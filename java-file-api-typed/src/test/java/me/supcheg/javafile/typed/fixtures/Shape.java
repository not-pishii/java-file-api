package me.supcheg.javafile.typed.fixtures;

import java.io.IOException;

/// An abstract class a generated class extends: its constructor is `protected` and throws a checked
/// exception, one abstract method is `public` and one `protected`, and a method of it throws.
public abstract class Shape {
    private final String label;

    protected Shape(String label) throws IOException {
        if (label.isEmpty()) {
            throw new IOException("no label");
        }
        this.label = label;
    }

    public abstract int area();

    protected abstract String describe(String prefix);

    public String render() {
        return describe(label) + area();
    }

    protected void check() throws IOException {
        throw new IOException("checked");
    }
}
