package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A record deconstruction pattern, e.g. `Point(int x, int y)`. Component
/// patterns may themselves be [RecordPattern]s, matching JLS's recursive
/// deconstruction.
///
/// @param recordType the deconstructed record type
/// @param componentPatterns the per-component patterns, in declaration order; copied defensively
/// @throws IllegalArgumentException if a component is a [TypePattern] with no binding name — JLS
///         requires every record pattern component to bind a name, unlike the standalone
///         `instanceof` form, which allows an unnamed type test
public record RecordPattern(TypeRef recordType, List<Pattern> componentPatterns) implements Pattern {
    public RecordPattern {
        componentPatterns = List.copyOf(componentPatterns);
        for (Pattern componentPattern : componentPatterns) {
            if (componentPattern instanceof TypePattern typePattern
                    && typePattern.bindingName().isEmpty()) {
                throw new IllegalArgumentException("record pattern component must bind a name");
            }
        }
    }
}
