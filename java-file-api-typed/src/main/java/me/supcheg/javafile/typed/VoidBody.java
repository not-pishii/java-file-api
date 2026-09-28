package me.supcheg.javafile.typed;

import java.util.Optional;

/// The block that is a `void` method body, constructor body, or `void`
/// lambda block body (§6.3): it ends with [#return_()] or [#end()].
public final class VoidBody extends Block<Void, VoidBody> {

    VoidBody() {}

    @Override
    VoidBody self() {
        return this;
    }

    @Override
    VoidBody child() {
        return new VoidBody();
    }

    /// Appends a bare `return;` and ends this block.
    ///
    /// @return the proof that this block ended
    public Terminated<Void> return_() {
        return appendFinal(new Instr.Return(Optional.empty()));
    }

    /// Ends this block without an explicit `return;`, e.g. when the body
    /// simply falls off the end.
    ///
    /// @return the proof that this block ended
    public Terminated<Void> end() {
        return endHere();
    }
}
