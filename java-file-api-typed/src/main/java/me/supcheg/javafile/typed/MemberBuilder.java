package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.NativeMethodDecl;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/// The builder of the members of a class being declared (§6.5), branded
/// with `Self`: the stage of its declarations. What [TypedClassBuilder] — of
/// a class that extends `Object` — and [TypedSubclassBuilder] — of a class
/// with another superclass — have in common, which is everything but how the
/// stage ends.
///
/// **Every member is declared here, and none has a body.** A field — instance or static —, a
/// method, a constructor, an override is declared, which gives its fact; the stage ends in the
/// bodies of the class ([MemberBodies]), where each declared member is defined. The two are distinct
/// types, so no member is declared after a body was built: when the first body is, the methods of the
/// class and the names of its fields are all there, and the body is lowered where it is defined — what
/// is wrong with it is rejected by the call that gave it.
///
/// A stage is a value a generator may keep: this one used after it ended is
/// rejected, which the types cannot.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public abstract sealed class MemberBuilder<Self, S> extends Declarations<Self, S>
        permits TypedClassBuilder, TypedSubclassBuilderArities {

    private final String described;
    private final FinalClassToken<Self> self;
    private final Superclass superclass;
    private final SignatureRegistry registry;
    private final ClassDeclaration<Self> owner;
    private final Serial serial;
    private List<Member> members = List.of();

    MemberBuilder(ClassDeclaration<Self> declared, Inheritance inherited, Superclass superclass) {
        this.superclass = superclass;
        this.described = declared.described();
        this.self = declared.self();
        this.registry = new SignatureRegistry(described, inherited, declared.bounded());
        this.owner = declared;
        this.serial = Serial.of(inherited, declared.bounded());
    }

    /// The fact of the class being declared: a [FinalClassToken], usable as
    /// the type of expressions, variables and members of the class. Its
    /// [FinalClassToken#methods()] is known once the declarations of the class end.
    ///
    /// @return the self token
    public final FinalClassToken<Self> self() {
        return self;
    }

    // ------------------------------------------------------------------
    // Member types
    // ------------------------------------------------------------------

    /// Declares a `public static final` member class of the class, which the file of the class renders.
    ///
    /// The class is a declaration of its own: `spec` receives its header, branded with a `Self` of its
    /// own, and returns the bodies of its members. A member class that is `static` does not see the type
    /// parameters of the class it is declared in; [#declareInnerClass] declares one that does.
    ///
    /// @param name the simple name of the member class
    /// @param spec declares the member class, given the first stage of its header
    /// @param <N> the brand of the member class
    /// @return the token of the member class, which the body of the class can name
    /// @throws IllegalArgumentException if `name` cannot name a type
    /// @throws IllegalStateException if the declarations of the class have ended, or a member of the
    ///     member class is not defined
    public final <N> FinalClassToken<N> declareNestedClass(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        return nestedClass(name, List.of(), List.of(), false, new NestedDeclarations.ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h);
            }
        });
    }

    /// Declares an inner member class of the class: `public final class Inner`, which captures the
    /// instance of the class it is declared in and sees its type parameters (JLS 8.1.3).
    ///
    /// @param name the simple name of the member class
    /// @param spec declares the member class, given the first stage of its header
    /// @param <N> the brand of the member class
    /// @return the token of the member class
    public final <N> FinalClassToken<N> declareInnerClass(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        return nestedClass(name, List.of(), owner.scopeDeclarations(), true, new NestedDeclarations.ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h);
            }
        });
    }

    /// Declares a generic `public static final` member class with one type parameter, see
    /// [#declareNestedClass(String, TypedJavaFile.TypedClassHeaderSpec)].
    ///
    /// @param name the simple name of the member class
    /// @param p1 the type parameter
    /// @param spec declares the member class, given its header and the token of the type parameter
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member class
    public final <N, B1> FinalClassToken<N> declareNestedClass(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericClassSpec1<B1> spec) {
        return nestedClass(name, List.of(p1), List.of(), false, new NestedDeclarations.ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0));
            }
        });
    }

    /// Declares a generic `public static final` member class with two type parameters.
    ///
    /// @param name the simple name of the member class
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the member class, given its header and the tokens of its type parameters
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member class
    public final <N, B1, B2> FinalClassToken<N> declareNestedClass(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericClassSpec2<B1, B2> spec) {
        return nestedClass(name, List.of(p1, p2), List.of(), false, new NestedDeclarations.ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(
                        h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1));
            }
        });
    }

    /// Declares a generic `public static final` member class with three type parameters.
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
    public final <N, B1, B2, B3> FinalClassToken<N> declareNestedClass(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericClassSpec3<B1, B2, B3> spec) {
        return nestedClass(name, List.of(p1, p2, p3), List.of(), false, new NestedDeclarations.ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(
                        h,
                        NestedDeclarations.variable(declaration, 0),
                        NestedDeclarations.variable(declaration, 1),
                        NestedDeclarations.variable(declaration, 2));
            }
        });
    }

    /// Declares a `public` member interface of the class, implicitly `static`.
    ///
    /// @param name the simple name of the member interface
    /// @param spec declares the interface, given its builder
    /// @param <I> the brand of the member interface
    /// @return the token of the member interface
    public final <I> InterfaceToken<I> declareNestedInterface(String name, TypedJavaFile.TypedInterfaceSpec spec) {
        owner.requireDeclaring("a member interface of " + owner.described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(owner.target(), owner.desc(), name, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a `public` member record of the class, implicitly `static`.
    ///
    /// @param name the simple name of the member record
    /// @param spec declares the record, given its builder
    /// @param <R> the brand of the member record
    /// @return the token of the member record
    public final <R> FinalClassToken<R> declareNestedRecord(String name, TypedJavaFile.TypedRecordSpec spec) {
        owner.requireDeclaring("a member record of " + owner.described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(owner.target(), owner.desc(), name, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a `public` member enum of the class, implicitly `static`.
    ///
    /// @param name the simple name of the member enum
    /// @param spec declares the enum, given its builder
    /// @param <E> the brand of the member enum
    /// @return the token of the member enum
    public final <E> EnumToken<E> declareNestedEnum(String name, TypedJavaFile.TypedEnumSpec spec) {
        owner.requireDeclaring("a member enum of " + owner.described());
        NestedDeclarations.NestedType<EnumToken<E>> nested =
                NestedDeclarations.enum_(owner.target(), owner.desc(), name, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a `public` member annotation type of the class, implicitly `static`.
    ///
    /// @param name the simple name of the member annotation type
    /// @param spec declares the annotation type, given its builder
    /// @return the binary name of the member annotation type
    public final ClassDesc declareNestedAnnotationType(String name, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        owner.requireDeclaring("a member annotation type of " + owner.described());
        NestedDeclarations.NestedType<ClassDesc> nested =
                NestedDeclarations.annotationType(owner.target(), owner.desc(), name, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    private <N> FinalClassToken<N> nestedClass(
            String name,
            List<TypeParameter<?>> parameters,
            List<me.supcheg.javafile.facts.DeclarationIdentity> enclosing,
            boolean inner,
            NestedDeclarations.ClassBodies spec) {
        owner.requireDeclaring("a member class of " + owner.described());
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(owner.target(), owner.desc(), name, parameters, enclosing, inner, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    @Override
    final MemberBuilder<Self, S> builder() {
        return this;
    }

    @Override
    final List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }

    // ------------------------------------------------------------------
    // The throws clause
    // ------------------------------------------------------------------

    /// The declarations of the methods and constructors of this class
    /// that declare `exceptions` in their `throws` clause:
    ///
    /// ```java
    /// var read = cb.throwing(IOException_.TOKEN).declareMethod("read", PrimitiveToken.INT);
    /// var twice = cb.throwing(IOException_.TOKEN).declareMethod("twice", PrimitiveToken.INT);
    /// var bodies = cb.bodies();
    /// bodies.define(read, (b, self) -> b.return_(call(field(self, in), InputStream_.read)));
    /// bodies.define(twice, (b, self) -> b.return_(call(self, read)));
    /// ```
    ///
    /// The clause is part of the fact the declaration returns: a call of
    /// the member is accepted only where each of its checked exceptions is
    /// caught or declared, and the body the member is defined with may
    /// throw them and their subclasses. Unchecked exceptions may be
    /// declared, and change nothing.
    ///
    /// The result declares methods and constructors and nothing else — a
    /// field has no `throws` clause.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the declarations of the members that declare `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingDeclarations<Self, S> throwing(ClassToken<? extends Throwable>... exceptions) {
        return throwing(List.of(exceptions));
    }

    /// The declarations of the members that declare the exceptions of a list in their `throws` clause,
    /// which an element of a stream gives: [#throwing(ClassToken[])] renders the same. An empty list is no
    /// exception.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the declarations of the members that declare `exceptions`
    public final ThrowingDeclarations<Self, S> throwing(List<? extends ClassToken<? extends Throwable>> exceptions) {
        return new ThrowingDeclarations<>(this, List.copyOf(exceptions));
    }

    // ------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------

    /// Declares a `public final` field, to be given its initializer by the bodies of the class
    /// ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    public final <T> FieldRef<Self, T> declareField(String name, TypeToken<T> type) {
        return declareField(name, type, new AnnotationRef<?>[0]);
    }

    /// Declares a `public final` field that declares `annotations`, to be
    /// given its initializer by the bodies of the class
    /// ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, or an annotation
    ///     type cannot annotate a field
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> FieldRef<Self, T> declareField(String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return declared(
                UnsafeFacts.field(self, name, type), Set.of(Modifier.PUBLIC, Modifier.FINAL), List.of(annotations));
    }

    /// Declares a `public final` field that is `transient`, or a `public`
    /// field that is `transient` or `volatile`, by the exact modifiers it is
    /// rendered with:
    ///
    /// ```java
    /// var cache = cb.declareField("cache", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.TRANSIENT));
    /// var counter = cb.declareMutableField("counter", PrimitiveToken.INT,
    ///         Set.of(Modifier.PUBLIC, Modifier.VOLATILE));
    /// ```
    ///
    /// The set is held to what a field of the class may be: `public final`, with
    /// `transient` if the field is not serialized, or `public`, with `transient`
    /// or `volatile`. A `volatile` field is not `final` (JLS 8.3.1.4), and neither
    /// is `static`: a static field is declared by [#declareStaticField(String,
    /// TypeToken)].
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param modifiers the exact modifiers of the field
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and, where it is not
    ///     `final`, assigned
    /// @throws IllegalArgumentException if the modifiers are not those of a field
    ///     of the class, a field of that name is declared already, or an annotation
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    public final <T> FieldRef<Self, T> declareField(String name, TypeToken<T> type, Set<Modifier> modifiers) {
        boolean legal = modifiers.contains(Modifier.PUBLIC)
                && modifiers.contains(Modifier.FINAL)
                && modifiers.stream()
                        .allMatch(m -> m == Modifier.PUBLIC || m == Modifier.FINAL || m == Modifier.TRANSIENT);
        if (!legal) {
            throw new IllegalArgumentException("field " + name + " of " + described + " cannot be " + modifiers
                    + ": a field of the class is public final, with transient, and a mutable one is public, with"
                    + " transient or volatile");
        }
        return declared(UnsafeFacts.field(self, name, type), modifiers, List.of());
    }

    /// Declares a `public` field, not `final`, to be given its initializer by the bodies of the
    /// class ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    public final <T> MutableFieldRef<Self, T> declareMutableField(String name, TypeToken<T> type) {
        return declareMutableField(name, type, new AnnotationRef<?>[0]);
    }

    /// Declares a `public` field, not `final`, that declares `annotations`,
    /// to be given its initializer by the bodies of the class.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    public final <T> MutableFieldRef<Self, T> declareMutableField(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return declared(UnsafeFacts.mutableField(self, name, type), Set.of(Modifier.PUBLIC), List.of(annotations));
    }

    /// Declares a `public` field, not `final`, that is `transient` or
    /// `volatile` by the exact modifiers it is rendered with; see
    /// [#declareField(String, TypeToken, Set)].
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param modifiers the exact modifiers of the field: `public`, and `transient` or `volatile`
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if the modifiers are not those of a mutable field of the
    ///     class, or a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    public final <T> MutableFieldRef<Self, T> declareMutableField(
            String name, TypeToken<T> type, Set<Modifier> modifiers) {
        boolean legal = modifiers.contains(Modifier.PUBLIC)
                && !modifiers.contains(Modifier.FINAL)
                && modifiers.stream()
                        .allMatch(m -> m == Modifier.PUBLIC || m == Modifier.TRANSIENT || m == Modifier.VOLATILE);
        if (!legal) {
            throw new IllegalArgumentException("mutable field " + name + " of " + described + " cannot be " + modifiers
                    + ": a mutable field of the class is public, with transient or volatile");
        }
        return declared(UnsafeFacts.mutableField(self, name, type), modifiers, List.of());
    }

    /// Declares a `public static final` field, to be given its initializer by the bodies of the class
    /// ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter — of this class, which a static
    ///     member does not have in scope (JLS 8.1.2), or of another —, or the declarations of the class
    ///     have ended
    public final <T> StaticFieldRef<T> declareStaticField(String name, TypeToken<T> type) {
        return declareStaticField(name, type, new AnnotationRef<?>[0]);
    }

    /// Declares a `public static final` field that declares `annotations`, to be given its initializer
    /// by the bodies of the class ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, or an annotation
    ///     type cannot annotate a field
    /// @throws IllegalStateException if the type mentions a type parameter, see
    ///     [#declareStaticField(String, TypeToken)], or the declarations of the class have ended
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> StaticFieldRef<T> declareStaticField(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return declaredStatic(
                UnsafeFacts.staticField(self, name, type),
                Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
                List.of(annotations));
    }

    /// Declares a `public static` field, not `final`, to be given its initializer by the bodies of the
    /// class ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter, see
    ///     [#declareStaticField(String, TypeToken)], or the declarations of the class have ended
    public final <T> MutableStaticFieldRef<T> declareMutableStaticField(String name, TypeToken<T> type) {
        return declareMutableStaticField(name, type, new AnnotationRef<?>[0]);
    }

    /// Declares a `public static` field, not `final`, that declares `annotations`, to be given its
    /// initializer by the bodies of the class.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, or an annotation
    ///     type cannot annotate a field
    /// @throws IllegalStateException if the type mentions a type parameter, see
    ///     [#declareStaticField(String, TypeToken)], or the declarations of the class have ended
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> MutableStaticFieldRef<T> declareMutableStaticField(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return declaredStatic(
                UnsafeFacts.mutableStaticField(self, name, type),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(annotations));
    }

    private <F extends FieldRef<Self, ?>> F declared(
            F field, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations) {
        String name = field.name();
        Site site = Site.here();
        owner.requireDeclaring("field " + name + " of " + described);
        Tokens.requireNotRaw(field.type());
        owner.scope("the type of field " + name).require(field.type());
        serial.requireSerializable(field, described);
        registry.field(name);
        annotations.forEach(
                annotation -> owner.target().verify(annotation.type().token()));
        declared(new Member.Field(field, modifiers, annotations, site));
        return field;
    }

    /// Declares a static field of the class: a static member mentions no type parameter (JLS 8.1.2), and
    /// is serialized with no value of its own, so what it is of need not be `Serializable`.
    private <F extends StaticFieldRef<?>> F declaredStatic(
            F field, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations) {
        String name = field.name();
        Site site = Site.here();
        owner.requireDeclaring("static field " + name + " of " + described);
        Tokens.requireNotRaw(field.type());
        owner.staticScope("the type of static field " + name).require(field.type());
        registry.field(name);
        annotations.forEach(
                annotation -> owner.target().verify(annotation.type().token()));
        declared(new Member.StaticField(field, modifiers, annotations, site));
        return field;
    }

    // ------------------------------------------------------------------
    // Serialization
    // ------------------------------------------------------------------

    /// Declares the serial version of a class that is `Serializable` by a supertype — an exception
    /// among them —, `@Serial private static final long serialVersionUID = value;`. Such a class says
    /// its serial version, or javac warns of it (`[serial]`, JLS 8.1.1 and the
    /// `java.io.Serializable` contract), and its declarations do not end without it.
    ///
    /// @param value the serial version
    /// @throws IllegalArgumentException if the class is not `Serializable`, or declared its serial version
    ///     already, or a field of that name
    /// @throws IllegalStateException if the declarations of the class have ended
    public final void declareSerialVersionUID(long value) {
        owner.requireDeclaring("the serial version of " + described);
        serial.requireSerializable(described);
        if (members.stream().anyMatch(Member.SerialVersion.class::isInstance)) {
            throw new IllegalArgumentException(
                    described + " declares its serial version already: a class has one serialVersionUID");
        }
        registry.field(Member.SerialVersion.NAME);
        declared(new Member.SerialVersion(value));
    }

    // ------------------------------------------------------------------
    // What a class has of its superclass
    // ------------------------------------------------------------------

    /// The fact of a `protected` member of the superclass or of a class above it, taken out of
    /// its wrapper: to override the method, here; to call it or to reach the field, in a body
    /// ([MemberBodies#protected_]).
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if the class is no subclass of the owner of the member on the
    ///     target classpath
    public final <F extends MemberFact> F protected_(Protected<? super S, F> held) {
        return opened(self, described, held);
    }

    /// The fact of a `protected` member of a class `self` is a subclass of.
    static <F extends MemberFact> F opened(FinalClassToken<?> self, String described, Protected<?, F> held) {
        if (!self.isSubclassOf(held.owner().erasure())) {
            throw new IllegalArgumentException(described + " cannot reach " + held + ": it is no subclass of "
                    + held.owner() + ", and a protected member is reached by a subclass alone (JLS 6.6.2)");
        }
        return UnsafeFacts.open(held);
    }

    // ------------------------------------------------------------------
    // Escape hatch
    // ------------------------------------------------------------------

    /// Adds a raw, untyped member from [Unsafe#member(me.supcheg.javafile.model.ClassMember)].
    /// It is not checked against the signatures of the typed members.
    ///
    /// @param member the member
    /// @return this builder
    /// @throws IllegalStateException if the declarations of the class have ended
    public final MemberBuilder<Self, S> add(UnsafeMember member) {
        owner.requireDeclaring("an unsafe member of " + described);
        declared(new Member.Given(member.member()));
        return this;
    }

    // ------------------------------------------------------------------
    // Declarations
    // ------------------------------------------------------------------

    /// The scope of the types a member mentions: of an instance member, or of a `static` one (JLS 8.1.2).
    private TypeScope scope(InvocableKind kind, String where) {
        return switch (kind) {
            case CONSTRUCTOR, INSTANCE_METHOD -> owner.scope(where);
            case STATIC_METHOD -> owner.staticScope(where);
        };
    }

    /// Holds the types of the signature of a member to its scope, where it is declared.
    private void requireInScope(Invocable member) {
        TypeScope scope = scope(member.kind(), "the signature of " + member);
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(type -> {
            Tokens.requireNotRaw(type);
            scope.require(type);
        });
    }

    /// Declares a method or constructor of the class.
    ///
    /// @param member its fact
    /// @param thrown the exception classes of its `throws` clause, as its fact has them
    /// @return the fact, `member`
    /// Declares a method or constructor of the class, said at `site`, rendered
    /// with `modifiers` beside those its form fixes.
    <F extends Invocable> F declare(
            F member,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Set<Modifier> modifiers) {
        return declare(member, thrown, annotations, parameterAnnotations, modifiers, Site.here());
    }

    /// Declares a method or constructor of the class, said at `site`.
    <F extends Invocable> F declare(
            F member,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Set<Modifier> modifiers,
            Site site) {
        owner.requireDeclaring(member.toString());
        requireInScope(member);
        if (member.kind() == InvocableKind.CONSTRUCTOR && owner.isAbstract()) {
            throw new IllegalArgumentException(described + " cannot declare the constructor " + member
                    + ": the class is abstract, and the typed layer declares a class no subclass in the same"
                    + " generator instantiates");
        }
        annotations.forEach(
                annotation -> owner.target().verify(annotation.type().token()));
        List<List<AnnotationRef<?>>> parameters = java.util.stream.IntStream.range(
                        0, member.params().size())
                .mapToObj(index -> index < parameterAnnotations.size()
                        ? parameterAnnotations.get(index)
                        : List.<AnnotationRef<?>>of())
                .toList();
        Set<Modifier> rendered = requireModifiers(member, modifiers);
        registry.member(member);
        if (rendered.contains(Modifier.NATIVE)) {
            declared(new Member.Given(nativeMethod(member, thrown, annotations, parameters, rendered)));
            return member;
        }
        declared(new Member.Callable(
                member,
                thrown,
                annotations,
                parameters,
                Member.Kind.DECLARES,
                member.arity(),
                scope(member.kind(), "the body of " + member),
                rendered,
                site));
        return member;
    }

    /// The modifiers a member declared with `modifiers` is rendered with beside
    /// those its form fixes, held to what Java allows of the member.
    private static Set<Modifier> requireModifiers(Invocable member, Set<Modifier> modifiers) {
        if (modifiers.isEmpty()) {
            return Set.of();
        }
        if (member.kind() == InvocableKind.CONSTRUCTOR) {
            throw new IllegalArgumentException("a constructor cannot be " + modifiers + ": it is neither"
                    + " synchronized nor native, and has no other modifier to say here");
        }
        for (Modifier modifier : modifiers) {
            if (modifier != Modifier.SYNCHRONIZED && modifier != Modifier.NATIVE) {
                throw new IllegalArgumentException("a method cannot add the modifier " + modifier
                        + " here: the declarations render the methods synchronized or native, and the rest"
                        + " of a method's modifiers is that of its form");
            }
        }
        if (member.traits().overridability() == Overridability.ABSTRACT) {
            throw new IllegalArgumentException("the abstract method " + member + " cannot be " + modifiers
                    + ": an abstract method is neither synchronized nor native (JLS 8.4.3.6)");
        }
        return Set.copyOf(modifiers);
    }

    /// The native method `member`, rendered without a body.
    private NativeMethodDecl nativeMethod(
            Invocable member,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Set<Modifier> modifiers) {
        Set<Modifier> rendered = new java.util.LinkedHashSet<>();
        rendered.add(Modifier.PUBLIC);
        if (member.kind() == InvocableKind.STATIC_METHOD) {
            rendered.add(Modifier.STATIC);
        }
        rendered.addAll(modifiers);
        boolean variable = member.arity() instanceof Arity.Variable;
        List<Param> params = java.util.stream.IntStream.range(0, member.params().size())
                .mapToObj(index -> {
                    TypeToken<?> type = member.params().get(index);
                    boolean varargs = variable && index == member.params().size() - 1;
                    TypeRef ref = type.typeRef();
                    if (varargs) {
                        ref = ((ArrayTypeRef) ref).component();
                    }
                    return new Param(
                            "v" + index,
                            ref,
                            parameterAnnotations.get(index).stream()
                                    .map(AnnotationRef::toUse)
                                    .toList(),
                            varargs);
                })
                .toList();
        return new NativeMethodDecl(
                member.name(),
                member.resultType().map(TypeToken::typeRef),
                List.of(),
                params,
                annotations.stream().map(AnnotationRef::toUse).toList(),
                rendered,
                thrown.stream()
                        .<ClassOrInterfaceTypeRef>map(type -> type.typeRef())
                        .toList(),
                Optional.empty());
    }

    /// Declares a method of the class that overrides the methods of its signature the class
    /// inherits; rendered with `@Override`.
    ///
    /// @param own its fact
    /// @param thrown the exception classes of its `throws` clause, as its fact has them
    /// @return the fact, `own`
    /// @throws IllegalArgumentException if the class inherits no such method, or cannot override it
    <F extends Invocable> F declareOverride(F own, List<ClassToken<? extends Throwable>> thrown) {
        return declareOverride(own, thrown, Site.here());
    }

    /// Declares a method of the class that overrides, said at `site`.
    <F extends Invocable> F declareOverride(F own, List<ClassToken<? extends Throwable>> thrown, Site site) {
        owner.requireDeclaring(own.toString());
        requireInScope(own);
        // What a result is a subtype of is told by its metamodel, which has to hold on the target.
        own.resultType().ifPresent(owner.target()::verify);
        declared(new Member.Callable(
                own,
                thrown,
                List.of(),
                java.util.stream.IntStream.range(0, own.params().size())
                        .mapToObj(_ -> List.<AnnotationRef<?>>of())
                        .toList(),
                Member.Kind.OVERRIDES,
                registry.override(own),
                scope(own.kind(), "the body of " + own),
                Set.of(),
                site));
        return own;
    }

    private void declared(Member member) {
        members = Stream.concat(members.stream(), Stream.of(member)).toList();
    }

    // ------------------------------------------------------------------
    // The end of the declarations
    // ------------------------------------------------------------------

    /// The declarations end and the bodies begin: what only a method of the class can do is done
    /// by one it declared ([SignatureRegistry#requireComplete()]), and the methods of the class are
    /// known from here on.
    ///
    /// @param make makes the stage of the bodies, of what the class declared
    /// @param <B> the stage of the bodies
    /// @return the stage of the bodies
    /// @throws IllegalStateException if the declarations have ended already, or leave undone what
    ///     only a method of the class can do
    final <B extends MemberBodies<Self, S>> B begin(Function<MemberBodies.Declared<Self>, B> make) {
        return owner.bodies(
                "the bodies of " + described,
                () -> {
                    registry.requireComplete();
                    serial.requireVersion(members.stream().anyMatch(Member.SerialVersion.class::isInstance), described);
                    return registry.table();
                },
                written -> make.apply(new MemberBodies.Declared<>(owner, superclass, members, written)));
    }

    /// A member of the class as it is declared, in the order it was.
    sealed interface Member {

        /// Whether a method overrides those the class inherits: rendered with `@Override`.
        enum Kind {
            /// A method or constructor declared by its name.
            DECLARES,
            /// A method that overrides.
            OVERRIDES
        }

        /// A method or constructor, which the bodies of the class define.
        ///
        /// @param fact its fact
        /// @param thrown the exception classes of its `throws` clause
        /// @param annotations the annotations it declares
        /// @param parameterAnnotations the annotations of each of its parameters
        /// @param kind whether it overrides
        /// @param arity whether its last parameter is of variable arity, and whether it vouches for the array
        ///     (`@SafeVarargs`): as that of the method it overrides is, and for a member declared by its name,
        ///     what `variableArity()` derives of the type of its elements
        /// @param modifiers the modifiers it is rendered with beside those its form fixes, `synchronized` or
        ///     `native`
        /// @param scope the scope of the types its body mentions
        /// @param site where it was declared
        record Callable(
                Invocable fact,
                List<ClassToken<? extends Throwable>> thrown,
                List<AnnotationRef<?>> annotations,
                List<List<AnnotationRef<?>>> parameterAnnotations,
                Kind kind,
                Arity arity,
                TypeScope scope,
                Set<Modifier> modifiers,
                Site site)
                implements Member {
            public Callable {
                thrown = List.copyOf(thrown);
                annotations = List.copyOf(annotations);
                parameterAnnotations =
                        parameterAnnotations.stream().map(List::copyOf).toList();
                modifiers = Set.copyOf(modifiers);
            }
        }

        /// A field, which the bodies of the class give its initializer.
        ///
        /// @param fact its fact
        /// @param modifiers its modifiers
        /// @param annotations the annotations it declares
        /// @param site where it was declared
        record Field(FieldRef<?, ?> fact, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations, Site site)
                implements Member {
            public Field {
                modifiers = Set.copyOf(modifiers);
                annotations = List.copyOf(annotations);
            }
        }

        /// A static field, which the bodies of the class give its initializer.
        ///
        /// @param fact its fact
        /// @param modifiers its modifiers
        /// @param annotations the annotations it declares
        /// @param site where it was declared
        record StaticField(
                StaticFieldRef<?> fact, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations, Site site)
                implements Member {
            public StaticField {
                modifiers = Set.copyOf(modifiers);
                annotations = List.copyOf(annotations);
            }
        }

        /// A member that is there whole: an unsafe member.
        ///
        /// @param member the member
        record Given(ClassMember member) implements Member {}

        /// The serial version of a `Serializable` class, `@Serial private static final long
        /// serialVersionUID = value;`.
        ///
        /// @param value the serial version
        record SerialVersion(long value) implements Member {
            /// The name of the field.
            static final String NAME = "serialVersionUID";
        }
    }
}
