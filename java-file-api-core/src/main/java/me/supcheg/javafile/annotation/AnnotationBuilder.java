package me.supcheg.javafile.annotation;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    /// Adds a member whose value is a nested annotation, populated via an
    /// [AnnotationBuilder].
    ///
    /// @param name the member name
    /// @param type the nested annotation type
    /// @param spec receives the builder to populate the nested annotation's members
    /// @return this builder
    public AnnotationBuilder withNestedMember(String name, ClassDesc type, Consumer<AnnotationBuilder> spec) {
        return withMember(name, AnnotationValues.nested(type, spec));
    }

    /// Adds a member whose value is an array, populated via an [ArrayValueBuilder].
    ///
    /// @param name the member name
    /// @param spec receives the builder to populate the array's elements
    /// @return this builder
    public AnnotationBuilder withArrayMember(String name, Consumer<ArrayValueBuilder> spec) {
        ArrayValueBuilder avb = new ArrayValueBuilder();
        spec.accept(avb);
        return withMember(name, avb.build());
    }

    /// Snapshots the accumulated state into an immutable [AnnotationUse].
    ///
    /// @return the finished annotation use
    public AnnotationUse build() {
        return new AnnotationUse(type, List.copyOf(members));
    }
}
