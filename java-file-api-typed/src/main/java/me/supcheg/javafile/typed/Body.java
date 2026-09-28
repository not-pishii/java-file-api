package me.supcheg.javafile.typed;

import java.util.Optional;

/// The block that is a non-`void` method body, constructor tail, or lambda
/// block body (§6.3): it must end with [#return_(Expr)] (directly, or
/// through an exhaustive construct such as [Block#ifElse]) to hand back its
/// [Terminated] token.
///
/// @param <R> the result type of the enclosing method or lambda
public final class Body<R> extends Block<R, Body<R>> {

    Body() {}

    @Override
    Body<R> self() {
        return this;
    }

    @Override
    Body<R> child() {
        return new Body<>();
    }

    /// Appends `return value;` and ends this block.
    ///
    /// @param value the returned expression
    /// @return the proof that this block ended
    public Terminated<R> return_(Expr<? extends R> value) {
        return appendFinal(new Instr.Return(Optional.of(value.node())));
    }
}
