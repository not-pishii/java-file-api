package me.supcheg.javafile.facts;

/// The phantom markers of the primitive types (§6.1).
///
/// A primitive value of the generated code is typed by its marker, never by
/// its box: `int` is `Prim.Int`, while `Integer` is `Integer`. So an
/// `Expr<Prim.Int>` and an `Expr<Integer>` are different types to javac, and
/// boxing or unboxing happens only where the generator asks for it
/// explicitly — a `null` box can never be unboxed silently.
///
/// The markers are final and cannot be instantiated: they exist only as type
/// arguments. Refer to them qualified, `Prim.Long`, rather than importing
/// them, since `Long`, `Double`, `Float`, `Short` and `Byte` would shadow
/// `java.lang`.
public final class Prim {
    private Prim() {}

    /// The marker of `int`.
    public static final class Int {
        private Int() {}
    }

    /// The marker of `long`.
    public static final class Long {
        private Long() {}
    }

    /// The marker of `double`.
    public static final class Double {
        private Double() {}
    }

    /// The marker of `float`.
    public static final class Float {
        private Float() {}
    }

    /// The marker of `boolean`.
    public static final class Bool {
        private Bool() {}
    }

    /// The marker of `char`.
    public static final class Char {
        private Char() {}
    }

    /// The marker of `byte`.
    public static final class Byte {
        private Byte() {}
    }

    /// The marker of `short`.
    public static final class Short {
        private Short() {}
    }
}
