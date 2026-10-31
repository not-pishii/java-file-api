package me.supcheg.javafile.langmodel.mirror;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;

/// An annotation type as [MirrorTranslator] reads it: its elements and
/// their defaults, its retention, the declaration contexts it may annotate,
/// and whether it is repeatable.
///
/// @param elements the elements, in declaration order
/// @param retention the retention; `CLASS` if the type declares none
/// @param targets the declaration contexts; empty if the type declares no target, which makes every one
/// @param repeatable whether the type is repeatable
public record AnnotationModel(
        List<AnnotationElementModel> elements,
        RetentionPolicy retention,
        Set<ElementType> targets,
        boolean repeatable) {
    public AnnotationModel {
        elements = List.copyOf(elements);
        targets = Set.copyOf(targets);
    }

    /// The elements without a default, in order.
    ///
    /// @return the required elements
    public List<AnnotationElementModel> required() {
        return elements.stream().filter(AnnotationElementModel::required).toList();
    }
}
