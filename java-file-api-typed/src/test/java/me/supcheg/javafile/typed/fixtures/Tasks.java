package me.supcheg.javafile.typed.fixtures;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/// Overloads that a lambda alone does not tell apart: `() -> s.trim()` is a
/// `Runnable`, a `Supplier` and a `Callable` to javac, which picks by the
/// shape of the body unless the lambda says what it is.
public final class Tasks {
    private Tasks() {}

    /// @param task the task
    /// @return `"Runnable"`
    public static String which(Runnable task) {
        return "Runnable";
    }

    /// @param task the task
    /// @return `"Supplier"`
    public static String which(Supplier<String> task) {
        return "Supplier";
    }

    /// @param task the task
    /// @return `"Callable"`
    public static String which(Callable<String> task) {
        return "Callable";
    }

    /// @param value any object
    /// @return whether it is a `Runnable`
    public static boolean isRunnable(Object value) {
        return value instanceof Runnable;
    }
}
