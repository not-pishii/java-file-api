package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// A mutable local variable, declared with an initializer by
/// [Block#letVar(TypeToken, Expr, java.util.function.Function)] or as the
/// variable of a `for` loop, and assigned with
/// [Expressions#assign(MutVar, Expr)].
///
/// Besides the scope rule of every [Var], a `MutVar` never crosses a lambda
/// boundary: a statement of a lambda body that reads or assigns a `MutVar`
/// declared outside that lambda is rejected when it is built, since javac
/// allows a lambda to capture only effectively final variables. Whether a
/// `MutVar` is ever assigned is not looked at: one that is not would be
/// effectively final to javac, and is rejected all the same — declare it by
/// `let`. The block of a case of a `switch` expression is no such boundary:
/// it reads and assigns the variables of the code around the `switch`.
///
/// @param <T> the Java type of the variable
public final class MutVar<T> extends Var<T> {
    MutVar(TypeToken<T> type, String role, Block<?, ?> owner) {
        super(type, role, owner);
    }
}
