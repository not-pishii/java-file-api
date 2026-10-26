package me.supcheg.javafile.typed.fixtures;

/// A class whose only constructor, `protected`, is of variable arity: a subclass alone calls it.
public class Packed {
    public final String told;

    protected Packed(String... parts) {
        this.told = String.join("+", parts);
    }
}
