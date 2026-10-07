package me.supcheg.javafile.typed.fixtures;

/// An enum whose constants have bodies: each is of a class of its own.
public enum Op {
    ADD {
        @Override
        public int apply(int a, int b) {
            return a + b;
        }
    },
    SUB {
        @Override
        public int apply(int a, int b) {
            return a - b;
        }
    };

    /// Applies the operation.
    ///
    /// @param a the left operand
    /// @param b the right operand
    /// @return the result
    public abstract int apply(int a, int b);
}
