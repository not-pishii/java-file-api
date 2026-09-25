package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A record pattern, e.g. `Point(int x, int y)`. Components can be record
/// patterns too, e.g. `Line(Point(var x1, var y1), Point p2)`.
///
/// Every type pattern component must declare a variable; otherwise the
/// constructor throws `IllegalArgumentException`.
///
/// @param recordType the deconstructed record type
/// @param componentPatterns the per-component patterns, in declaration order
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
