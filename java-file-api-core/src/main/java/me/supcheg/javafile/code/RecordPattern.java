package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A record deconstruction pattern, e.g. `Point(int x, int y)`. Component
/// patterns may themselves be [RecordPattern]s, matching JLS's recursive
/// deconstruction.
///
/// @param recordType the deconstructed record type
/// @param componentPatterns the per-component patterns, in declaration order; copied defensively
public record RecordPattern(TypeRef recordType, List<Pattern> componentPatterns) implements Pattern {
    public RecordPattern {
        componentPatterns = List.copyOf(componentPatterns);
    }
}
