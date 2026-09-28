package me.supcheg.javafile.typed;

import org.jspecify.annotations.Nullable;

import java.util.Optional;

/// The block that is a `void` method body, constructor body, or `void`
/// lambda block body (§6.3): it ends with [#return_()] or [#end()].
public final class VoidBody extends Block<Void, VoidBody> {

    private VoidBody(@Nullable Block<?, ?> parent, boolean lambdaBoundary, String what) {
        super(parent, lambdaBoundary, what);
    }

    /// The body of a member: the root of a scope tree.
    static VoidBody root(String what) {
        return new VoidBody(null, false, what);
    }

    /// The block body of a `void` lambda of the generated code, nested in
    /// `enclosing`: a lambda boundary (§6.2), which a [MutVar] or [LoopCtl]
    /// of `enclosing` does not cross.
    static VoidBody lambdaBody(Block<?, ?> enclosing) {
        return new VoidBody(enclosing, true, "lambda body");
    }

    @Override
    VoidBody self() {
        return this;
    }

    @Override
    VoidBody child(String what) {
        return new VoidBody(this, false, what);
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
