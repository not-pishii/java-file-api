package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// An expression of the generated code whose Java type is `T` (§6.1).
///
/// `T` is a phantom: javac checks every use of an expression against the
/// facts it is combined with, and lowering erases it. Subtyping comes for free
/// through wildcards — a combinator taking `Expr<? extends CharSequence>`
/// accepts an `Expr<String>`.
///
/// An expression also carries the token of its static type, from which
/// lowering learns what javac will: whether it is primitive, what exception
/// type a `throw` throws.
///
/// Expressions are created by [Expressions], by the invocation combinators
/// of `Invocations`, by the statements that bind variables, and — untyped —
/// by [Unsafe#expr(me.supcheg.javafile.code.Expr, TypeToken)]. There is no
/// public constructor.
///
/// @param <T> the Java type of the expression
public sealed class Expr<T> permits Var, Invocation {
    private final Node node;
    private final TypeToken<T> type;

    Expr(Node node, TypeToken<T> type) {
        this.node = node;
        this.type = type;
    }

    /// The token of the expression's static type.
    ///
    /// @return the type token
    public final TypeToken<T> type() {
        return type;
    }

    Node node() {
        return node;
    }

    static <T> Expr<T> of(Node node, TypeToken<T> type) {
        return new Expr<>(node, type);
    }
}
