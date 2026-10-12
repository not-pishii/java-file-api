package me.supcheg.javafile.typed.fixtures;

/// An interface of methods of none, one and two parameters: abstract ones a class implements by a
/// method of its own, and default ones it overrides by the witness that it is the interface.
public interface Widening {
    Object made(String first);

    Object made(String first, String second);

    Widening self(String first);

    Widening self(String first, String second);

    void take(String first, String second);

    default Object given(String first) {
        return "given:" + first;
    }

    default Object given(String first, String second) {
        return "given:" + first + second;
    }

    default Object held(String first, String second) {
        return "held:" + first + second;
    }

    default Widening chained() {
        return this;
    }

    default Widening chained(String first, String second) {
        return this;
    }

    default void fill() {}

    default void fill(String first) {}

    default void fill(String first, String second) {}
}
