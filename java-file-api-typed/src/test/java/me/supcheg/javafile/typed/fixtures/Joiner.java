package me.supcheg.javafile.typed.fixtures;

/// An interface with a default method of variable arity and a generic one.
public interface Joiner {
    /// Joins.
    ///
    /// @param parts the parts
    /// @return the parts, joined
    default String join(String... parts) {
        return String.join("", parts);
    }

    /// Picks.
    ///
    /// @param picked what is picked
    /// @param <T> its type
    /// @return `picked`
    default <T> T pick(T picked) {
        return picked;
    }
}
