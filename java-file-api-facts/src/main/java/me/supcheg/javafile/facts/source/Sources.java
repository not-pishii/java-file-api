package me.supcheg.javafile.facts.source;

/// Helpers of the default lookups.
final class Sources {
    private Sources() {}

    @SuppressWarnings("unchecked")
    static <T> T constant(Object value) {
        // The source read the value of a field whose type the query named; a
        // constant variable's value has exactly that type or its box.
        return (T) value;
    }
}
