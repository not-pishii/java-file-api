package me.supcheg.javafile.typed.fixtures;

/// A resource that records its close and may throw from it, for the try-with-resources tests.
public final class Closing implements AutoCloseable {
    private static int closes;
    private final boolean failing;

    /// @param failing whether [#close()] throws
    public Closing(boolean failing) {
        this.failing = failing;
    }

    /// Forgets the closes of the resources made before.
    public static void reset() {
        closes = 0;
    }

    /// @return how many resources were closed
    public static int closes() {
        return closes;
    }

    /// Closes the resource, recording it.
    ///
    /// @throws IllegalStateException if this resource was made failing
    @Override
    public void close() {
        closes++;
        if (failing) {
            throw new IllegalStateException("close");
        }
    }
}
