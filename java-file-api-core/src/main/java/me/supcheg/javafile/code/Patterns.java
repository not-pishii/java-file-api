package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// Creates patterns for `instanceof` and `switch`.
///
/// ```java
/// Patterns.recordPattern(Types.of(POINT),
///         Patterns.typePattern(Types.INT, "x"),
///         Patterns.typePattern(Types.INT, "y"))   // Point(int x, int y)
/// ```
public final class Patterns {

    private Patterns() {}

    /// Creates a flat type pattern, e.g. `Type bindingName`.
    ///
    /// @param type the matched type
    /// @param bindingName the name bound to the matched value
    /// @return a type pattern
    public static Pattern typePattern(TypeRef type, String bindingName) {
        return new TypePattern(type, Optional.of(bindingName));
    }

    /// Creates a record deconstruction pattern, e.g. `Point(int x, int y)`.
    ///
    /// @param recordType the deconstructed record type
    /// @param componentPatterns the per-component patterns, in declaration order
    /// @return a record pattern
    public static Pattern recordPattern(TypeRef recordType, Pattern... componentPatterns) {
        return new RecordPattern(recordType, List.of(componentPatterns));
    }
}
