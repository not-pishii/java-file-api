package me.supcheg.javafile.facts;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.util.List;

/// A use of an annotation type (§3.6): the annotation, and the value of
/// every element the use gives.
///
/// It is made by the type-state builder the `@Facts` processor generates for
/// the annotation type, `Author_.annotation()`, which has no `build()` before
/// every element without a default is given a value, and which takes an
/// [AnnotationValue] of the type of each element. A use the facts do not
/// prove is not expressible; [UnsafeFacts#annotationRef(AnnotationType, List)]
/// holds the use to the same rule where it is made, for a generator that
/// does not go through the builder.
///
/// A declaration, a member, a parameter and a type use of the typed layer
/// take one, and render it as javac accepts it, nested and array values
/// included.
///
/// @param <A> the annotation type
public final class AnnotationRef<A> {
    private final AnnotationType<A> type;
    private final List<Annotations.Member> members;

    AnnotationRef(AnnotationType<A> type, List<Annotations.Member> members) {
        this.type = type;
        this.members = List.copyOf(members);
    }

    /// The annotation type.
    ///
    /// @return the type
    public AnnotationType<A> type() {
        return type;
    }

    /// The elements the use gives, in the order they were given.
    ///
    /// @return the members
    public List<me.supcheg.javafile.annotation.AnnotationMember> members() {
        return members.stream()
                .map(member -> new me.supcheg.javafile.annotation.AnnotationMember(
                        member.name(), member.value().toCore()))
                .toList();
    }

    /// The use as the core model renders it.
    ///
    /// @return the annotation use
    public AnnotationUse toUse() {
        return new AnnotationUse(type.token().erasure(), members());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AnnotationRef<?> other
                && type.token().equals(other.type.token())
                && members.equals(other.members);
    }

    @Override
    public int hashCode() {
        return type.token().hashCode() * 31 + members.hashCode();
    }

    @Override
    public String toString() {
        return "@" + type.token();
    }
}
