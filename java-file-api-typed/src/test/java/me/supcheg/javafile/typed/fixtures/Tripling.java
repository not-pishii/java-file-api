package me.supcheg.javafile.typed.fixtures;

/// A class whose only constructor takes two parameters before the one of variable arity.
public class Tripling {
    public final String told;

    public Tripling(int first, int second, String... parts) {
        this.told = first + "," + second + ":" + String.join("+", parts);
    }
}
