package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.AnnotationType;
import me.supcheg.javafile.facts.AnnotationValue;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.model.AnnotationElementDecl;
import me.supcheg.javafile.model.TypeDecl;

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
public final class TypedAnnotationTypeBuilder<Self> extends AnnotationTypeBuilderArities<Self> {
    private final TargetClasspath target;
    private final ClassDesc desc;
    private final String described;
    private final List<AnnotationRef<?>> annotations = new ArrayList<>();
    private final List<AnnotationElementDecl> elements = new ArrayList<>();
    private final List<TypeDecl> memberTypes = new ArrayList<>();
    private java.util.Optional<me.supcheg.javafile.doc.DocComment> doc = java.util.Optional.empty();
    private boolean complete;

    TypedAnnotationTypeBuilder(TargetClasspath target, ClassDesc desc) {
        this.target = target;
        this.desc = desc;
        this.described = "annotation type " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".")
                + desc.displayName();
    }

    /// Documents the annotation type, e.g. a sentence of what it is for.
    ///
    /// @param doc the comment
    /// @return this builder
    /// @throws IllegalArgumentException if the text of `doc` contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    public TypedAnnotationTypeBuilder<Self> withDoc(me.supcheg.javafile.doc.DocComment doc) {
        requireDeclaring("the documentation of " + described);
        this.doc = java.util.Optional.of(Docs.requireSafe(doc, described));
        return this;
    }

    /// Documents the annotation type with one paragraph of plain text.
    ///
    /// @param text the text
    /// @return this builder
    /// @throws IllegalArgumentException if `text` is empty, or contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    public TypedAnnotationTypeBuilder<Self> withDoc(String text) {
        return withDoc(Docs.of(text, described));
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

    /// Adds a member class to the annotation type, implicitly `static` and `public` as a member
    /// class of an interface is (JLS 9.5): `public @interface A { class B {} }` is legal Java.
    ///
    /// @param name the simple name of the member class
    /// @param spec declares the member class, given the first stage of its header
    /// @param <N> the brand of the member class
    /// @return the token of the member class
    /// @throws IllegalArgumentException if `name` cannot name a type
    /// @throws IllegalStateException if the declaration is complete
    public <N> FinalClassToken<N> declareNestedClass(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        requireDeclaring("a member class of " + described);
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(target, desc, name, List.of(), List.of(), spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member class to the annotation type with one type parameter, implicitly `static`
    /// and `public`; see [#declareNestedClass(String, TypedJavaFile.TypedClassHeaderSpec)].
    ///
    /// @param name the simple name of the member class
    /// @param p1 the type parameter
    /// @param spec declares the member class, given its header and the token of the type parameter
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member class
    public <N, B1> FinalClassToken<N> declareNestedClass(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericClassSpec1<B1> spec) {
        requireDeclaring("a member class of " + described);
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(target, desc, name, p1, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member class to the annotation type with two type parameters, see
    /// [#declareNestedClass(String, TypeParameter, TypedJavaFile.TypedGenericClassSpec1)].
    ///
    /// @param name the simple name of the member class
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the member class, given its header and the tokens of its type parameters
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member class
    public <N, B1, B2> FinalClassToken<N> declareNestedClass(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericClassSpec2<B1, B2> spec) {
        requireDeclaring("a member class of " + described);
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(target, desc, name, p1, p2, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member class to the annotation type with three type parameters, see
    /// [#declareNestedClass(String, TypeParameter, TypedJavaFile.TypedGenericClassSpec1)].
    ///
    /// @param name the simple name of the member class
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the member class, given its header and the tokens of its type parameters
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the token of the member class
    public <N, B1, B2, B3> FinalClassToken<N> declareNestedClass(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericClassSpec3<B1, B2, B3> spec) {
        requireDeclaring("a member class of " + described);
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(target, desc, name, p1, p2, p3, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a member interface to the annotation type, implicitly `static` and `public`.
    ///
    /// @param name the simple name of the member interface
    /// @param spec declares the interface, given its builder
    /// @param <I> the brand of the member interface
    /// @return the token of the member interface
    /// @throws IllegalStateException if the declaration is complete
    public <I> InterfaceToken<I> declareNestedInterface(String name, TypedJavaFile.TypedInterfaceSpec spec) {
        requireDeclaring("a member interface of " + described);
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target, desc, name, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member interface to the annotation type with one type parameter, see
    /// [#declareNestedInterface(String, TypedJavaFile.TypedInterfaceSpec)].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the type parameter
    /// @param spec declares the interface, given its builder and the token of the type parameter
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member interface
    public <I, B1> InterfaceToken<I> declareNestedInterface(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericInterfaceSpec1<B1> spec) {
        requireDeclaring("a member interface of " + described);
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target, desc, name, p1, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member interface to the annotation type with two type parameters, see
    /// [#declareNestedInterface(String, TypeParameter, TypedJavaFile.TypedGenericInterfaceSpec1)].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the interface, given its builder and the tokens of its type parameters
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member interface
    public <I, B1, B2> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericInterfaceSpec2<B1, B2> spec) {
        requireDeclaring("a member interface of " + described);
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target, desc, name, p1, p2, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member interface to the annotation type with three type parameters, see
    /// [#declareNestedInterface(String, TypeParameter, TypedJavaFile.TypedGenericInterfaceSpec1)].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the interface, given its builder and the tokens of its type parameters
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the token of the member interface
    public <I, B1, B2, B3> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericInterfaceSpec3<B1, B2, B3> spec) {
        requireDeclaring("a member interface of " + described);
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target, desc, name, p1, p2, p3, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a member record to the annotation type, implicitly `static` and `public`.
    ///
    /// @param name the simple name of the member record
    /// @param spec declares the record, given its builder
    /// @param <R> the brand of the member record
    /// @return the token of the member record
    /// @throws IllegalStateException if the declaration is complete
    public <R> FinalClassToken<R> declareNestedRecord(String name, TypedJavaFile.TypedRecordSpec spec) {
        requireDeclaring("a member record of " + described);
        NestedDeclarations.NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target, desc, name, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member record to the annotation type with one type parameter, see
    /// [#declareNestedRecord(String, TypedJavaFile.TypedRecordSpec)].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the type parameter
    /// @param spec declares the record, given its builder and the token of the type parameter
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member record
    public <R, B1> FinalClassToken<R> declareNestedRecord(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericRecordSpec1<B1> spec) {
        requireDeclaring("a member record of " + described);
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target, desc, name, p1, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member record to the annotation type with two type parameters, see
    /// [#declareNestedRecord(String, TypeParameter, TypedJavaFile.TypedGenericRecordSpec1)].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the record, given its builder and the tokens of its type parameters
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member record
    public <R, B1, B2> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericRecordSpec2<B1, B2> spec) {
        requireDeclaring("a member record of " + described);
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target, desc, name, p1, p2, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a generic member record to the annotation type with three type parameters, see
    /// [#declareNestedRecord(String, TypeParameter, TypedJavaFile.TypedGenericRecordSpec1)].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the record, given its builder and the tokens of its type parameters
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the token of the member record
    public <R, B1, B2, B3> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericRecordSpec3<B1, B2, B3> spec) {
        requireDeclaring("a member record of " + described);
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target, desc, name, p1, p2, p3, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a member class of the annotation type with the type parameters of a generated stage:
    /// what [AnnotationTypeBuilderArities] declares of four to sixteen.
    @Override
    final <N> FinalClassToken<N> nestedClassToken(
            String name, List<TypeParameter<?>> parameters, NestedDeclarations.ClassBodies spec) {
        requireDeclaring("a member class of " + described);
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(target, desc, name, parameters, List.of(), spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a member interface of the annotation type with the type parameters of a generated stage.
    @Override
    final <I> InterfaceToken<I> nestedInterfaceToken(
            String name, List<TypeParameter<?>> parameters, NestedDeclarations.InterfaceBodies spec) {
        requireDeclaring("a member interface of " + described);
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target, desc, name, parameters, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a member record of the annotation type with the type parameters of a generated stage.
    @Override
    final <R> FinalClassToken<R> nestedRecordToken(
            String name, List<TypeParameter<?>> parameters, NestedDeclarations.RecordBodies spec) {
        requireDeclaring("a member record of " + described);
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target, desc, name, parameters, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Adds a member enum to the annotation type, implicitly `static` and `public`.
    ///
    /// @param name the simple name of the member enum
    /// @param spec declares the enum, given its builder
    /// @param <E> the brand of the member enum
    /// @return the token of the member enum
    /// @throws IllegalStateException if the declaration is complete
    public <E> EnumToken<E> declareNestedEnum(String name, TypedJavaFile.TypedEnumSpec spec) {
        requireDeclaring("a member enum of " + described);
        NestedDeclarations.NestedType<EnumToken<E>> nested = NestedDeclarations.enum_(target, desc, name, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Refuses type parameters on a member enum: javac rejects `enum E<T>` (JLS 8.9).
    ///
    /// @param name the simple name of the member enum
    /// @param p1 the type parameter
    /// @param spec declares the enum, which is never called
    /// @param <E> the brand of the member enum
    /// @param <B1> the leftmost bound of the type parameter
    /// @return never
    /// @throws IllegalArgumentException always, naming the enum and the type parameter
    public <E, B1> EnumToken<E> declareNestedEnum(String name, TypeParameter<B1> p1, TypedJavaFile.TypedEnumSpec spec) {
        throw new IllegalArgumentException("cannot declare the member enum " + name + " of " + described
                + " with a type parameter: an enum has no type parameters (JLS 8.9), and the typed layer declares"
                + " only what javac accepts");
    }

    /// Adds a member annotation type to the annotation type, implicitly `static` and `public`.
    ///
    /// @param name the simple name of the member annotation type
    /// @param spec declares the annotation type, given its builder
    /// @return the binary name of the member annotation type
    /// @throws IllegalStateException if the declaration is complete
    public ClassDesc declareNestedAnnotationType(String name, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        requireDeclaring("a member annotation type of " + described);
        NestedDeclarations.NestedType<ClassDesc> nested = NestedDeclarations.annotationType(target, desc, name, spec);
        memberTypes.add(nested.type());
        return nested.token();
    }

    /// Refuses type parameters on a member annotation type: javac rejects `@interface A<T>` (JLS
    /// 9.6).
    ///
    /// @param name the simple name of the member annotation type
    /// @param p1 the type parameter
    /// @param spec declares the annotation type, which is never called
    /// @param <B1> the leftmost bound of the type parameter
    /// @return never
    /// @throws IllegalArgumentException always, naming the annotation type and the type parameter
    public <B1> ClassDesc declareNestedAnnotationType(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        throw new IllegalArgumentException("cannot declare the member annotation type " + name + " of " + described
                + " with a type parameter: an annotation type has no type parameters (JLS 9.6), and the typed"
                + " layer declares only what javac accepts");
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
        return element(name, type, List.of());
    }

    /// Adds an element that declares annotations, with no default value.
    ///
    /// @param name the element name
    /// @param type the element type
    /// @param annotations the annotations, in order
    /// @param <T> the element type
    /// @return this builder
    /// @throws IllegalArgumentException if the type is not one JLS 9.6.1 allows, or an annotation cannot
    ///     annotate a method
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> TypedAnnotationTypeBuilder<Self> element(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return element(name, type, List.of(annotations));
    }

    /// Adds a documented element that declares annotations, with no default value.
    ///
    /// @param name the element name
    /// @param type the element type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the element type
    /// @return this builder
    /// @throws IllegalArgumentException if the type is not one JLS 9.6.1 allows, an annotation cannot
    ///     annotate a method, or the text of `doc` contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> TypedAnnotationTypeBuilder<Self> element(
            String name, TypeToken<T> type, me.supcheg.javafile.doc.DocComment doc, AnnotationRef<?>... annotations) {
        requireElementType(name, type);
        return element(name, type.typeRef(), Optional.empty(), Optional.of(doc), List.of(annotations));
    }

    private <T> TypedAnnotationTypeBuilder<Self> element(
            String name, TypeToken<T> type, List<AnnotationRef<?>> annotations) {
        requireElementType(name, type);
        return element(name, type.typeRef(), Optional.empty(), Optional.empty(), annotations);
    }

    private <T> TypedAnnotationTypeBuilder<Self> element(
            String name, TypeToken<T> type, AnnotationValue<T> defaultValue, List<AnnotationRef<?>> annotations) {
        requireElementType(name, type);
        return element(name, type.typeRef(), Optional.of(defaultValue.toCore()), Optional.empty(), annotations);
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
        return element(name, type, defaultValue, List.of());
    }
    /// Adds an element that declares annotations, with a default value.
    ///
    /// @param name the element name
    /// @param type the element type
    /// @param defaultValue the default value
    /// @param annotations the annotations, in order
    /// @param <T> the element type
    /// @return this builder
    /// @throws IllegalArgumentException if the type is not one JLS 9.6.1 allows, the value is not of it,
    ///     or an annotation cannot annotate a method
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> TypedAnnotationTypeBuilder<Self> element(
            String name, TypeToken<T> type, AnnotationValue<T> defaultValue, AnnotationRef<?>... annotations) {
        return element(name, type, defaultValue, List.of(annotations));
    }

    /// Adds a documented element that declares annotations, with a default value.
    ///
    /// @param name the element name
    /// @param type the element type
    /// @param defaultValue the default value
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the element type
    /// @return this builder
    /// @throws IllegalArgumentException if the type is not one JLS 9.6.1 allows, the value is not of it,
    ///     an annotation cannot annotate a method, or the text of `doc` contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> TypedAnnotationTypeBuilder<Self> element(
            String name,
            TypeToken<T> type,
            AnnotationValue<T> defaultValue,
            me.supcheg.javafile.doc.DocComment doc,
            AnnotationRef<?>... annotations) {
        requireElementType(name, type);
        return element(
                name, type.typeRef(), Optional.of(defaultValue.toCore()), Optional.of(doc), List.of(annotations));
    }

    /// Adds an element whose type is an annotation type, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public <A> TypedAnnotationTypeBuilder<Self> annotationElement(String name, AnnotationType<A> type) {
        return annotationElement(name, type, Optional.empty(), Optional.empty(), List.of());
    }

    /// Adds an element of an annotation type that declares annotations, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationElement(
            String name, AnnotationType<A> type, AnnotationRef<?>... annotations) {
        return annotationElement(name, type, Optional.empty(), Optional.empty(), List.of(annotations));
    }

    /// Adds a documented element of an annotation type, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method, or the text of `doc`
    ///     contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationElement(
            String name,
            AnnotationType<A> type,
            me.supcheg.javafile.doc.DocComment doc,
            AnnotationRef<?>... annotations) {
        return annotationElement(name, type, Optional.empty(), Optional.of(doc), List.of(annotations));
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
        return annotationElement(name, type, Optional.of(defaultValue.toCore()), Optional.empty(), List.of());
    }

    /// Adds an element of an annotation type that declares annotations, with a default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param defaultValue the default value
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationElement(
            String name, AnnotationType<A> type, AnnotationValue<A> defaultValue, AnnotationRef<?>... annotations) {
        return annotationElement(
                name, type, Optional.of(defaultValue.toCore()), Optional.empty(), List.of(annotations));
    }

    /// Adds a documented element of an annotation type, with a default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param defaultValue the default value
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method, or the text of `doc`
    ///     contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationElement(
            String name,
            AnnotationType<A> type,
            AnnotationValue<A> defaultValue,
            me.supcheg.javafile.doc.DocComment doc,
            AnnotationRef<?>... annotations) {
        return annotationElement(
                name, type, Optional.of(defaultValue.toCore()), Optional.of(doc), List.of(annotations));
    }

    private <A> TypedAnnotationTypeBuilder<Self> annotationElement(
            String name,
            AnnotationType<A> type,
            Optional<me.supcheg.javafile.annotation.AnnotationValue> defaultValue,
            Optional<me.supcheg.javafile.doc.DocComment> doc,
            List<AnnotationRef<?>> annotations) {
        return element(name, type.token().typeRef(), defaultValue, doc, annotations);
    }

    /// Adds an element whose type is an array of an annotation type, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(String name, AnnotationType<A> type) {
        return annotationArrayElement(name, type, Optional.empty(), Optional.empty(), List.of());
    }

    /// Adds an array element of an annotation type that declares annotations, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(
            String name, AnnotationType<A> type, AnnotationRef<?>... annotations) {
        return annotationArrayElement(name, type, Optional.empty(), Optional.empty(), List.of(annotations));
    }

    /// Adds a documented array element of an annotation type, with no default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method, or the text of `doc`
    ///     contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(
            String name,
            AnnotationType<A> type,
            me.supcheg.javafile.doc.DocComment doc,
            AnnotationRef<?>... annotations) {
        return annotationArrayElement(name, type, Optional.empty(), Optional.of(doc), List.of(annotations));
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
        return annotationArrayElement(name, type, Optional.of(defaultValue.toCore()), Optional.empty(), List.of());
    }

    /// Adds an array element of an annotation type that declares annotations, with a default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param defaultValue the default value
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(
            String name, AnnotationType<A> type, AnnotationValue<A[]> defaultValue, AnnotationRef<?>... annotations) {
        return annotationArrayElement(
                name, type, Optional.of(defaultValue.toCore()), Optional.empty(), List.of(annotations));
    }

    /// Adds a documented array element of an annotation type, with a default value.
    ///
    /// @param name the element name
    /// @param type the annotation type
    /// @param defaultValue the default value
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <A> the annotation type
    /// @return this builder
    /// @throws IllegalArgumentException if an annotation cannot annotate a method, or the text of `doc`
    ///     contains `*/`
    /// @throws IllegalStateException if the declaration is complete
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(
            String name,
            AnnotationType<A> type,
            AnnotationValue<A[]> defaultValue,
            me.supcheg.javafile.doc.DocComment doc,
            AnnotationRef<?>... annotations) {
        return annotationArrayElement(
                name, type, Optional.of(defaultValue.toCore()), Optional.of(doc), List.of(annotations));
    }

    private <A> TypedAnnotationTypeBuilder<Self> annotationArrayElement(
            String name,
            AnnotationType<A> type,
            Optional<me.supcheg.javafile.annotation.AnnotationValue> defaultValue,
            Optional<me.supcheg.javafile.doc.DocComment> doc,
            List<AnnotationRef<?>> annotations) {
        return element(
                name, me.supcheg.javafile.facts.ArrayToken.of(type.token()).typeRef(), defaultValue, doc, annotations);
    }

    private TypedAnnotationTypeBuilder<Self> element(
            String name,
            me.supcheg.javafile.type.TypeRef type,
            Optional<me.supcheg.javafile.annotation.AnnotationValue> defaultValue,
            Optional<me.supcheg.javafile.doc.DocComment> doc,
            List<AnnotationRef<?>> annotations) {
        requireDeclaring("element " + name + " of " + described);
        elements.add(new AnnotationElementDecl(
                name,
                type,
                annotations.stream()
                        .map(annotation -> {
                            requireMethodTarget(name, annotation);
                            return annotation.toUse();
                        })
                        .toList(),
                defaultValue,
                doc.map(comment -> Docs.requireSafe(comment, "element " + name + " of " + described))));
        return this;
    }

    /// Holds an annotation of an element to what a method may carry: an annotation whose targets
    /// are none may annotate anything, and another must name `METHOD` (JLS 9.6.4.1).
    private void requireMethodTarget(String name, AnnotationRef<?> annotation) {
        if (!annotation.type().targets().isEmpty()
                && !annotation.type().targets().contains(java.lang.annotation.ElementType.METHOD)) {
            throw new IllegalArgumentException(
                    "the annotation @" + annotation.type().token() + " of element " + name
                            + " of " + described + " cannot annotate a method: its targets are "
                            + annotation.type().targets() + " (JLS 9.6.4.1)");
        }
        target.verify(annotation.type().token());
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
        requireIncomplete();
        return JavaFile.annotationType(desc, this::populate);
    }

    /// The declaration of a member annotation type, which its enclosing declaration renders.
    ///
    /// @return the declaration
    /// @throws IllegalStateException if the declaration is complete already
    me.supcheg.javafile.model.TypeDecl completeNested() {
        requireIncomplete();
        me.supcheg.javafile.builder.AnnotationTypeBuilder ab =
                new me.supcheg.javafile.builder.AnnotationTypeBuilder(desc);
        populate(ab);
        return ab.build();
    }

    private void requireIncomplete() {
        if (complete) {
            throw new IllegalStateException("the declaration of " + described + " is already complete");
        }
        complete = true;
    }

    private void populate(me.supcheg.javafile.builder.AnnotationTypeBuilder ab) {
        doc.ifPresent(ab::withDoc);
        annotations.forEach(annotation -> ab.withAnnotation(annotation.toUse()));
        elements.forEach(ab::accept);
        memberTypes.forEach(ab::withType);
    }
}
