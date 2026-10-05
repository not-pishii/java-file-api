package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.RefToken;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/// The block that is a non-`void` method body, constructor tail, or lambda
/// block body (§6.3): it must end with [#return_(Expr)] (directly, or
/// through a construct that cannot complete normally, such as
/// [Block#ifElse]) to hand back its [Terminated] token.
///
/// @param <R> the result type of the enclosing method or lambda
public final class Body<R> extends Block<R, Body<R>> {

    private Body(@Nullable Block<?, ?> parent, boolean lambdaBoundary, String what, ExceptionScope exceptionScope) {
        super(parent, lambdaBoundary, what, exceptionScope);
    }

    /// The body of a member that declares no exception: the root of a scope tree.
    static <R> Body<R> root(String what) {
        return root(what, List.of());
    }

    /// The body of a member: the root of a scope tree.
    ///
    /// @param what the member, for diagnostics
    /// @param declared the exception types of the `throws` clause of the member
    static <R> Body<R> root(String what, List<RefToken<? extends Throwable>> declared) {
        return new Body<>(null, false, what, new ExceptionScope.Declares(declared, VoidBody.DECLARE_ON_MEMBER));
    }

    /// The block body of a lambda of the generated code, nested in
    /// `enclosing`: a lambda boundary (§6.2), which a [MutVar] or [LoopCtl]
    /// of `enclosing` does not cross.
    static <R> Body<R> lambdaBody(Block<?, ?> enclosing) {
        return lambdaBody(enclosing, List.of());
    }

    /// The block body of a lambda of the generated code, nested in
    /// `enclosing`, that may throw what the method of its functional
    /// interface declares, and nothing `enclosing` catches or declares.
    ///
    /// @param enclosing the block the lambda is built in
    /// @param declared the exception types of the `throws` clause of the functional interface's method
    static <R> Body<R> lambdaBody(Block<?, ?> enclosing, List<RefToken<? extends Throwable>> declared) {
        return new Body<>(
                enclosing, true, "lambda body", new ExceptionScope.Declares(declared, VoidBody.CATCH_IN_LAMBDA));
    }

    @Override
    Body<R> self() {
        return this;
    }

    @Override
    Body<R> child(String what, ExceptionScope exceptionScope) {
        return new Body<>(this, false, what, exceptionScope);
    }

    /// Appends `return value;` and ends this block.
    ///
    /// @param value the returned expression
    /// @return the proof that this block ended
    public Terminated<R> return_(Expr<? extends R> value) {
        return appendFinal(new Instr.Return(Optional.of(value.node())), "return_");
    }
}
