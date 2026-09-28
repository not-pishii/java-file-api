package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// A call of a method with a result, or an instance creation: an expression
/// that is also an [Effect].
///
/// @param <T> the type of the result
public final class Invocation<T> extends Expr<T> implements Effect {
    Invocation(Node node, TypeToken<T> type) {
        super(node, type);
    }
}
