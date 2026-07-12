package me.supcheg.javafile.annotation;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

/// A mutable builder for an [AnnotationUse].
///
/// Unlike the declaration builders, instances are created directly: nested
/// annotation values need standalone [AnnotationUse]s built outside any
/// declaration builder. [#build()] snapshots the accumulated state, so a
/// builder may be reused after building.
///
/// Instances are not thread-safe.
public final class AnnotationBuilder {

    private final ClassDesc type;
    private final List<AnnotationMember> members = new ArrayList<>();

    /// Creates a builder for a use of the given annotation type.
    ///
    /// @param type the annotation type
    public AnnotationBuilder(ClassDesc type) {
        this.type = type;
    }

    /// Adds a member assignment, e.g. `name = value`.
    ///
    /// @param name the member's name
    /// @param value the assigned value
    /// @return this builder
    public AnnotationBuilder withMember(String name, AnnotationValue value) {
        members.add(new AnnotationMember(name, value));
        return this;
    }

    /// Snapshots the accumulated state into an immutable [AnnotationUse].
    ///
    /// @return the finished annotation use
    public AnnotationUse build() {
        return new AnnotationUse(type, List.copyOf(members));
    }
}
