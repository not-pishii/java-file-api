package me.supcheg.javafile.typed.fixtures;

/// A class whose only constructor, `protected`, takes a parameter before the one of variable arity.
public class Numbering {
    public final String told;

    protected Numbering(int first, String... parts) {
        this.told = first + ":" + String.join("+", parts);
    }
}
