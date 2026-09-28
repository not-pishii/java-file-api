package me.supcheg.javafile.typed;

import java.io.Serial;

/// Thrown when a type or member asked for does not exist, so no symbol can prove it.
public final class NoSuchSymbolException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /// Creates an exception with the given message.
    ///
    /// @param message names the missing type or member
    public NoSuchSymbolException(String message) {
        super(message);
    }
}
