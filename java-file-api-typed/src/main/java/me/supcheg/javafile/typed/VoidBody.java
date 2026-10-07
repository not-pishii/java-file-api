package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.RefToken;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/// The block that is a `void` method body, constructor body, or `void`
/// lambda block body (§6.3): it ends with [#return_()] or [#end()].
public final class VoidBody extends Block<Void, VoidBody> {

    private VoidBody(@Nullable Block<?, ?> parent, Nesting nesting, String what, ExceptionScope exceptionScope) {
        super(parent, nesting, what, exceptionScope);
    }

    /// The body of a method that declares no exception: the root of a scope tree.
    static VoidBody root(String what) {
        return root(what, new ExceptionScope.Declares(ExceptionScope.Boundary.METHOD, List.of()));
    }

    /// The body of a member: the root of a scope tree.
    ///
    /// @param what the member, for diagnostics
    /// @param declares the `throws` clause of the member
    static VoidBody root(String what, ExceptionScope.Declares declares) {
        return new VoidBody(null, Nesting.PLAIN, what, declares);
    }

    /// The block body of a `void` lambda of the generated code, nested in
    /// `enclosing`: a lambda boundary (§6.2), which a [MutVar] or [LoopCtl]
    /// of `enclosing` does not cross. The method of its functional
    /// interface declares no exception.
    static VoidBody lambdaBody(@Nullable Block<?, ?> enclosing) {
        return lambdaBody(enclosing, List.of());
    }

    /// The block body of a `void` lambda of the generated code, nested in
    /// `enclosing`, that may throw what the method of its functional
    /// interface declares, and nothing `enclosing` catches or declares.
    ///
    /// @param enclosing the block the lambda is built in, or `null` outside of any body
    /// @param declared the exception types of the `throws` clause of the functional interface's method
    static VoidBody lambdaBody(
            @Nullable Block<?, ?> enclosing, List<? extends RefToken<? extends Throwable>> declared) {
        return new VoidBody(
                enclosing,
                Nesting.LAMBDA_BODY,
                "lambda body",
                new ExceptionScope.Declares(ExceptionScope.Boundary.LAMBDA, ExceptionType.ofAll(declared)));
    }

    @Override
    VoidBody self() {
        return this;
    }

    @Override
    VoidBody child(String what, ExceptionScope exceptionScope) {
        return new VoidBody(this, Nesting.PLAIN, what, exceptionScope);
    }

    /// Appends a bare `return;` and ends this block.
    ///
    /// @return the proof that this block ended
    public Terminated<Void> return_() {
        return appendFinal(new Instr.Return(Optional.empty()), "return_");
    }

    /// Ends this block without an explicit `return;`, e.g. when the body
    /// simply falls off the end. Unlike the other ways to end a block, the
    /// block still completes normally: in a nested block this just closes
    /// it, and the enclosing construct is continued or ended as usual.
    ///
    /// @return the proof that this block ended
    public Terminated<Void> end() {
        return endHere("end()");
    }
}
