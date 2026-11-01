package me.supcheg.javafile.typed.fixtures;

/// The final permitted implementation of [SealedShape].
public final class SealedDot implements SealedShape {
    /// Makes one.
    public SealedDot() {}

    @Override
    public int sides() {
        return 0;
    }
}
