package me.supcheg.javafile.typed.fixtures;

/// A class whose only constructor takes a parameter before the one of variable arity.
public class Pairing {
    public final String told;

    public Pairing(int first, String... parts) {
        this.told = first + ":" + String.join("+", parts);
    }
}
