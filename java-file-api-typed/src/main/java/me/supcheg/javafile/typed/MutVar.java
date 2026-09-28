package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// A mutable local variable, declared with an initializer by
/// [Block#letVar(TypeToken, Expr, java.util.function.Function)] or as the
/// variable of a `for` loop, and assigned with
/// [Expressions#assign(MutVar, Expr)].
///
/// A lambda cannot capture a variable that is assigned anywhere; lowering
/// rejects that, as javac would.
///
/// @param <T> the Java type of the variable
public final class MutVar<T> extends Var<T> {
    MutVar(TypeToken<T> type, String role) {
        super(type, role);
    }
}
