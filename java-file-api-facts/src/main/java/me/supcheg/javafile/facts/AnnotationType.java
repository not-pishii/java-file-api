package me.supcheg.javafile.facts;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/// The fact of an annotation type (§3.6): its token, its elements with their
/// defaults, its retention, the declaration contexts it may annotate, and
/// whether it is repeatable.
///
/// A metamodel of an annotation type has one as `ANNOTATION`, made by
/// [UnsafeFacts#annotationType(InterfaceToken, List, RetentionPolicy, Set,
/// boolean)]. The generated builder of the annotation reads the required
/// elements from it: an element without a default is one every use gives.
/// A value of the type the facts do not prove — an element the type does not
/// have, a value of another type — is not expressible through the builder.
///
/// @param <A> the annotation type
public final class AnnotationType<A> {
    private final InterfaceToken<A> token;
    private final List<AnnotationElement<A, ?>> elements;
    private final RetentionPolicy retention;
    private final Set<ElementType> targets;
    private final boolean repeatable;

    AnnotationType(
            InterfaceToken<A> token,
            List<AnnotationElement<A, ?>> elements,
            RetentionPolicy retention,
            Set<ElementType> targets,
            boolean repeatable) {
        this.token = token;
        this.elements = List.copyOf(elements);
        this.retention = retention;
        this.targets = Set.copyOf(targets);
        this.repeatable = repeatable;
    }

    /// The token of the annotation type.
    ///
    /// @return the token
    public InterfaceToken<A> token() {
        return token;
    }

    /// The elements of the annotation type, in declaration order.
    ///
    /// @return the elements
    public List<AnnotationElement<A, ?>> elements() {
        return elements;
    }

    /// The element of a name, if the annotation type declares one.
    ///
    /// @param name the element name
    /// @return the element
    public Optional<AnnotationElement<A, ?>> element(String name) {
        return elements.stream().filter(element -> element.name().equals(name)).findFirst();
    }

    /// The element of a name: the one the generated builder of the annotation
    /// takes, and nothing else.
    ///
    /// @param name the element name
    /// @return the element
    /// @throws FactLookupException if the annotation type declares no element of the name
    public AnnotationElement<A, ?> requireElement(String name) {
        return element(name)
                .orElseThrow(() -> new FactLookupException(
                        "element " + TypeNames.describe(token.erasure()) + "." + name,
                        "the annotation type",
                        elements.stream().map(AnnotationElement::name).toList()));
    }

    /// The required elements: those without a default.
    ///
    /// @return the required elements, in declaration order
    public List<AnnotationElement<A, ?>> required() {
        return elements.stream().filter(AnnotationElement::required).toList();
    }

    /// The retention of the annotation type; `CLASS` if it declares none
    /// (JLS 9.6.4.2).
    ///
    /// @return the retention
    public RetentionPolicy retention() {
        return retention;
    }

    /// The declaration contexts the annotation type may annotate; empty if
    /// it declares no target, which makes every context one (JLS 9.6.4.1).
    ///
    /// @return the targets
    public Set<ElementType> targets() {
        return targets;
    }

    /// Whether the annotation type is repeatable (JLS 9.6.3).
    ///
    /// @return `true` if it is repeatable
    public boolean repeatable() {
        return repeatable;
    }

    @Override
    public String toString() {
        return "@" + TypeNames.describe(token.erasure())
                + elements.stream()
                        .map(element -> element.name() + (element.required() ? "" : "=?"))
                        .collect(Collectors.joining(", ", "(", ")"));
    }
}
