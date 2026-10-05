package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.RefToken;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/// The block that is a `void` method body, constructor body, or `void`
/// lambda block body (§6.3): it ends with [#return_()] or [#end()].
public final class VoidBody extends Block<Void, VoidBody> {

    /// What the author can do about a checked exception in the body of a member, besides catching it.
    static final String DECLARE_ON_MEMBER =
            "declare it on the member: cb.throwing(...).declareMethod(...), and so for every other declaration";

    /// What the author can do about a checked exception in the body of a lambda: nothing but catch it.
    static final String CATCH_IN_LAMBDA = "catch it inside the lambda: the method of its functional interface does"
            + " not declare it, and the code around a lambda catches nothing thrown in it";

    private VoidBody(@Nullable Block<?, ?> parent, boolean lambdaBoundary, String what, ExceptionScope exceptionScope) {
        super(parent, lambdaBoundary, what, exceptionScope);
    }

    /// The body of a member that declares no exception: the root of a scope tree.
    static VoidBody root(String what) {
        return root(what, List.of());
    }

    /// The body of a member: the root of a scope tree.
    ///
    /// @param what the member, for diagnostics
    /// @param declared the exception types of the `throws` clause of the member
    static VoidBody root(String what, List<RefToken<? extends Throwable>> declared) {
        return root(what, new ExceptionScope.Declares(declared, DECLARE_ON_MEMBER));
    }

    /// The root of a scope tree that is the body of no member.
    ///
    /// @param what where it is, for diagnostics
    /// @param exceptionScope what may be thrown there
    static VoidBody root(String what, ExceptionScope.Declares exceptionScope) {
        return new VoidBody(null, false, what, exceptionScope);
    }

    /// The block body of a `void` lambda of the generated code, nested in
    /// `enclosing`: a lambda boundary (§6.2), which a [MutVar] or [LoopCtl]
    /// of `enclosing` does not cross.
    static VoidBody lambdaBody(Block<?, ?> enclosing) {
        return lambdaBody(enclosing, List.of());
    }

    /// The block body of a `void` lambda of the generated code, nested in
    /// `enclosing`, that may throw what the method of its functional
    /// interface declares, and nothing `enclosing` catches or declares.
    ///
    /// @param enclosing the block the lambda is built in
    /// @param declared the exception types of the `throws` clause of the functional interface's method
    static VoidBody lambdaBody(Block<?, ?> enclosing, List<RefToken<? extends Throwable>> declared) {
        return new VoidBody(enclosing, true, "lambda body", new ExceptionScope.Declares(declared, CATCH_IN_LAMBDA));
    }

    @Override
    VoidBody self() {
        return this;
    }

    @Override
    VoidBody child(String what, ExceptionScope exceptionScope) {
        return new VoidBody(this, false, what, exceptionScope);
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
