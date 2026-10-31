package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.Optional;

/// An element of an annotation type (§3.6): its name, its type, and the
/// default it declares, if it declares one.
///
/// The phantom `V` is the element's type as the generated builder of the
/// annotation takes it, unless the element is optional: a value of another
/// type is not given to it. An element without a default is required, and
/// the builder has no `build()` before it is given one.
///
/// @param <A> the annotation type
/// @param <V> the Java type of the element
public final class AnnotationElement<A, V> {
    private final ClassDesc annotation;
    private final String name;
    private final TypeRef type;
    private final Optional<me.supcheg.javafile.annotation.AnnotationValue> defaultValue;

    AnnotationElement(
            ClassDesc annotation,
            String name,
            TypeRef type,
            Optional<me.supcheg.javafile.annotation.AnnotationValue> defaultValue) {
        this.annotation = annotation;
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    /// The annotation type the element belongs to.
    ///
    /// @return the annotation type
    public ClassDesc annotation() {
        return annotation;
    }

    /// The element name.
    ///
    /// @return the name
    public String name() {
        return name;
    }

    /// The declared type of the element.
    ///
    /// @return the type
    public TypeRef type() {
        return type;
    }

    /// The default the element declares, if it declares one.
    ///
    /// @return the default value
    public Optional<me.supcheg.javafile.annotation.AnnotationValue> defaultValue() {
        return defaultValue;
    }

    /// Whether the element is required: it declares no default, so every use
    /// of the annotation gives it a value (JLS 9.6.2).
    ///
    /// @return `true` if the element has no default
    public boolean required() {
        return defaultValue.isEmpty();
    }

    @Override
    public String toString() {
        return TypeNames.describe(annotation) + "." + name;
    }
}
