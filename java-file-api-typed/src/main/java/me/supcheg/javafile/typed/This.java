package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// `this` of the class being declared, as the body of an instance method or
/// of a constructor receives it (§6.5): an expression of the class as any
/// other, and the one that is known to be `this` — what `super.m()` is called
/// on ([MemberBodies#superCall]).
///
/// It is in scope in the body it was handed to, lambdas in it included, and
/// nowhere else.
///
/// @param <T> the class being declared
public final class This<T> extends Expr<T> {
    This(Block<?, ?> root, TypeToken<T> type) {
        super(new Node.This(root), type);
    }
}
