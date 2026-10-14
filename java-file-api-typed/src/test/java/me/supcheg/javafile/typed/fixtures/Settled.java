package me.supcheg.javafile.typed.fixtures;

/// Constant variables of every type the typed layer folds, which javac takes for constant expressions
/// (JLS 4.12.4, 15.29).
public final class Settled {
    /// A `boolean` constant.
    public static final boolean ON = true;

    /// A `long` constant.
    public static final long BIG = 1L << 40;

    /// A `double` constant.
    public static final double RATIO = 0.5;

    /// A `String` constant.
    public static final String NAME = "settled";

    private Settled() {}
}
