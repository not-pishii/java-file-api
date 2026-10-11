package me.supcheg.javafile.typed.fixtures;

/** A generic class a generated generic class extends with its own type parameter for the type argument. */
public class Carrier<T> {
    private final T carried;

    public Carrier(T carried) {
        this.carried = carried;
    }

    public T carried() {
        return carried;
    }

    public String described(T other) {
        return carried + "/" + other;
    }
}
