package me.supcheg.javafile.typed.fixtures;

import java.io.IOException;

/// A resource whose [#close()] declares a checked exception, for the
/// try-with-resources tests.
public final class PickyClose implements AutoCloseable {
    private final boolean failing;

    /// @param failing whether [#close()] throws
    public PickyClose(boolean failing) {
        this.failing = failing;
    }

    /// Closes the resource.
    ///
    /// @throws IOException if this resource was made failing
    @Override
    public void close() throws IOException {
        if (failing) {
            throw new IOException("close");
        }
    }
}
