package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// A pre- or post-increment or decrement of a place, `++x`, `x--`, `++this.f`
/// or `++array[i]`. Its type is that of the place, and it is an [Effect] as
/// well as an expression: Java allows it as a statement (JLS 14.8).
///
/// @param <T> the type of the place
public final class Increment<T> extends Expr<T> implements Effect {
    private final Node.Increment node;

    Increment(Node.Increment node, TypeToken<T> type) {
        super(type);
        this.node = node;
    }

    @Override
    Node node() {
        return node;
    }
}
