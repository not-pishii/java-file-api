package me.supcheg.javafile.typed;

import org.jspecify.annotations.Nullable;

import java.util.Optional;

/// The block that is a non-`void` method body, constructor tail, or lambda
/// block body (§6.3): it must end with [#return_(Expr)] (directly, or
/// through a construct that cannot complete normally, such as
/// [Block#ifElse]) to hand back its [Terminated] token.
///
/// @param <R> the result type of the enclosing method or lambda
public final class Body<R> extends Block<R, Body<R>> {

    private Body(@Nullable Block<?, ?> parent, boolean lambdaBoundary, String what) {
        super(parent, lambdaBoundary, what);
    }

    /// The body of a member: the root of a scope tree.
    static <R> Body<R> root(String what) {
        return new Body<>(null, false, what);
    }

    /// The block body of a lambda of the generated code, nested in
    /// `enclosing`: a lambda boundary (§6.2), which a [MutVar] or [LoopCtl]
    /// of `enclosing` does not cross.
    static <R> Body<R> lambdaBody(Block<?, ?> enclosing) {
        return new Body<>(enclosing, true, "lambda body");
    }

    @Override
    Body<R> self() {
        return this;
    }

    @Override
    Body<R> child(String what) {
        return new Body<>(this, false, what);
    }

    /// Appends `return value;` and ends this block.
    ///
    /// @param value the returned expression
    /// @return the proof that this block ended
    public Terminated<R> return_(Expr<? extends R> value) {
        return appendFinal(new Instr.Return(Optional.of(value.node())), "return_");
    }
}
