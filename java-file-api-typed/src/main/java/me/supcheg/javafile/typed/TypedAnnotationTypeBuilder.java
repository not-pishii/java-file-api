package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.AnnotationType;
import me.supcheg.javafile.facts.AnnotationValue;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.model.AnnotationElementDecl;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/// Declares an annotation type (§6.5), branded with `Self`: its elements with
/// the types JLS 9.6.1 allows and their optional defaults, and its
/// meta-annotations through the annotation API — `@Retention`, `@Target`,
/// `@Repeatable` and user annotations.
///
/// ```java
/// JavaFile file = TypedJavaFile.annotationType(target, desc, new TypedJavaFile.TypedAnnotationTypeSpec() {
///     public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
///         ab.element("value", String_.TOKEN);
///         ab.element("priority", PrimitiveToken.INT, AnnotationValues.literal(0));
///         return ab;
///     }
/// });
/// ```
///
/// @param <Self> the brand of the annotation type being declared
public final class TypedAnnotationTypeBuilder<Self> {
    private final TargetClasspath target;
    private final ClassDesc desc;
    private final String described;
    private final List<AnnotationRef<?>> annotations = new ArrayList<>();
    private final List<AnnotationElementDecl> elements = new ArrayList<>();
    private boolean complete;

    TypedAnnotationTypeBuilder(TargetClasspath target, ClassDesc desc) {
        this.target = target;
        this.desc = desc;
        this.described = "annotation type " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".")
                + desc.displayName();
    }

    /// Adds an annotation to the declaration of the annotation type.
    ///
    /// @param annotation the annotation
    /// @return this builder
    /// @throws IllegalArgumentException if the annotation type cannot annotate a type
    /// @throws IllegalStateException if the declaration is complete
    public TypedAnnotationTypeBuilder<Self> withAnnotation(AnnotationRef<?> annotation) {
        requireDeclaring("an annotation of " + described);
        target.verify(annotation.type().token());
        annotations.add(annotation);
        return this;
    }

    /// Adds an element with no default value.
    ///
    /// @param name the element name
    /// @param type the element type
    /// @param <T> the element type
    /// @return this builder
    /// @throws IllegalArgumentException if the type is not one JLS 9.6.1 allows
    /// @throws IllegalStateException if the declaration is complete
    public <T> TypedAnnotationTypeBuilder<Self> element(String name, TypeToken<T> type) {
        requireDeclaring("element " + name + " of " + described);
        requireElementType(name, type);
        elements.add(new AnnotationElementDecl(name, type.typeRef(), Optional.empty(), Optional.empty()));
        return this;
    }

    /// Adds an element with a default value.
    ///
    /// @param name the element name
    /// @param type the element type
    /// @param defaultValue the default value
    /// @param <T> the element type
    /// @return this builder
    /// @throws IllegalArgumentException if the type is not one JLS 9.6.1 allows, or the value is not of it
    /// @throws IllegalStateException if the declaration is complete
    public <T> TypedAnnotationTypeBuilder<Self> element(
            String name, TypeToken<T> type, AnnotationValue<T> defaultValue) {
        requireDeclaring("element " + name + " of " + described);
        requireElementType(name, type);
        elements.add(
                new AnnotationElementDecl(name, type.typeRef(), Optional.of(defaultValue.toCore()), Optional.empty()));
        return this;
    }

    /// Adds an element whose type is an annotation type, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public <A> TypedAnnotationTypeBuilder<Self> annotationElement(String name, AnnotationType<A> type) {
        requireDeclaring("element " + name + " of " + described);
        elements.add(new AnnotationElementDecl(name, type.token().typeRef(), Optional.empty(), Optional.empty()));
        return this;
    }

    /// Adds an element whose type is an annotation type, with a default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param defaultValue the default value
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public <A> TypedAnnotationTypeBuilder<Self> annotationElement(
            String name, AnnotationType<A> type, AnnotationValue<A> defaultValue) {
        requireDeclaring("element " + name + " of " + described);
        elements.add(new AnnotationElementDecl(
                name, type.token().typeRef(), Optional.of(defaultValue.toCore()), Optional.empty()));
        return this;
    }

    /// Adds an element whose type is an array of an annotation type, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(String name, AnnotationType<A> type) {
        requireDeclaring("element " + name + " of " + described);
        elements.add(new AnnotationElementDecl(
                name,
                me.supcheg.javafile.facts.ArrayToken.of(type.token()).typeRef(),
                Optional.empty(),
                Optional.empty()));
        return this;
    }

    /// Adds an element whose type is an array of an annotation type, with a default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param defaultValue the default value
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(
            String name, AnnotationType<A> type, AnnotationValue<A[]> defaultValue) {
        requireDeclaring("element " + name + " of " + described);
        elements.add(new AnnotationElementDecl(
                name,
                me.supcheg.javafile.facts.ArrayToken.of(type.token()).typeRef(),
                Optional.of(defaultValue.toCore()),
                Optional.empty()));
        return this;
    }

    private void requireElementType(String name, TypeToken<?> type) {
        switch (type) {
            case me.supcheg.javafile.facts.PrimitiveToken<?, ?, ?> _ -> {}
            case EnumToken<?> _ -> {}
            case ArrayToken<?, ?> array -> requireElementType(name, array.component());
            case ClassToken<?> cls -> {
                if (!cls.erasure().equals(ConstantDescs.CD_String)
                        && !cls.erasure().equals(ConstantDescs.CD_Class)) {
                    throw unsupported(name, type);
                }
            }
            case InterfaceToken<?> _ ->
                throw new IllegalArgumentException("the element " + name + " of " + described
                        + " cannot be of " + type + ": an interface is not an annotation type, and the typed layer"
                        + " takes one by its AnnotationType fact (annotationElement)");
            default -> throw unsupported(name, type);
        }
    }

    private IllegalArgumentException unsupported(String name, TypeToken<?> type) {
        return new IllegalArgumentException("the element " + name + " of " + described + " cannot be of " + type
                + ": an annotation element is of a primitive type, String, Class, an enum, an annotation type,"
                + " or an array of one (JLS 9.6.1)");
    }

    private void requireDeclaring(String what) {
        if (complete) {
            throw new IllegalStateException(
                    "cannot declare " + what + ": the declaration of " + described + " is already complete");
        }
        Scopes.requireNoneOpen(what);
    }

    /// The declaration of the annotation type is complete.
    ///
    /// @return the file of the annotation type
    /// @throws IllegalStateException if the declaration is complete already
    JavaFile complete() {
        if (complete) {
            throw new IllegalStateException("the declaration of " + described + " is already complete");
        }
        complete = true;
        return JavaFile.annotationType(desc, ab -> {
            annotations.forEach(annotation -> ab.withAnnotation(annotation.toUse()));
            elements.forEach(ab::accept);
        });
    }
}
