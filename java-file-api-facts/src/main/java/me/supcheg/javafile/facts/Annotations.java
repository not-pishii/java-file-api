package me.supcheg.javafile.facts;

/// The one place annotation uses are assembled from their members: the
/// generated builders call it, as the typed declarations do, so the rules a
/// use is held to are the same wherever it is made.
final class Annotations {
    private Annotations() {}

    /// A member of a use, as it is held before the use is made.
    ///
    /// @param name the element name
    /// @param value the value
    record Member(String name, AnnotationValue<?> value) {}
}
