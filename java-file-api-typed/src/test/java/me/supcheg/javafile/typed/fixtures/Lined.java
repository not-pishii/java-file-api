package me.supcheg.javafile.typed.fixtures;

/// A class whose only constructor is of variable arity.
public class Lined {
    public final String told;

    public Lined(String... parts) {
        this.told = String.join("+", parts);
    }
}
