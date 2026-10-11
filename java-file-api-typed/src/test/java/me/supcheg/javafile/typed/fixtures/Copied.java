package me.supcheg.javafile.typed.fixtures;

/** An abstract class whose methods have the class itself for their result, one abstract and one with a body. */
public abstract class Copied {
    public abstract Copied copy();

    public Copied same() {
        return this;
    }

    public Copied other(String label) {
        return this;
    }
}
