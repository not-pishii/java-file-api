package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// A variable of the generated code: a local, a parameter, a lambda
/// parameter, a loop variable, or a pattern binding (§6.2).
///
/// A variable is only ever handed to the lambda that is its scope, so using
/// it before its declaration or outside its scope is a compile error of the
/// generator. A variable that escapes its lambda anyway — stored somewhere
/// and used later — is rejected by lowering. Names are assigned by lowering,
/// deterministically and without collisions.
///
/// A plain `Var` cannot be assigned; a [MutVar] can.
///
/// @param <T> the Java type of the variable
public sealed class Var<T> extends Expr<T> permits MutVar {
    private static final Node UNUSED = new Node.This();

    private final Node local;
    private final String role;

    Var(TypeToken<T> type, String role) {
        super(UNUSED, type);
        this.local = new Node.Local(this);
        this.role = role;
    }

    static <T> Var<T> param(TypeToken<T> type) {
        return new Var<>(type, "parameter");
    }

    @Override
    Node node() {
        return local;
    }

    @Override
    public String toString() {
        return role + " of type " + type();
    }
}
