package me.supcheg.javafile.typed;

/// Builds the body of a `for`/`for`-each loop, given the block, the loop
/// variable, and the loop's `break`/`continue` capability (§6.3).
///
/// @param <B> the block type
/// @param <V> the loop variable type
@FunctionalInterface
public interface LoopBody<B, V> {
    /// Populates the loop body.
    ///
    /// @param block the block to append statements to
    /// @param var the loop variable
    /// @param ctl the loop's `break`/`continue` capability
    void accept(B block, V var, LoopCtl ctl);
}
