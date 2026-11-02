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
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef10;
import me.supcheg.javafile.facts.MethodRef11;
import me.supcheg.javafile.facts.MethodRef12;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodRef5;
import me.supcheg.javafile.facts.MethodRef6;
import me.supcheg.javafile.facts.MethodRef7;
import me.supcheg.javafile.facts.MethodRef8;
import me.supcheg.javafile.facts.MethodRef9;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef10;
import me.supcheg.javafile.facts.VoidMethodRef11;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidMethodRef5;
import me.supcheg.javafile.facts.VoidMethodRef6;
import me.supcheg.javafile.facts.VoidMethodRef7;
import me.supcheg.javafile.facts.VoidMethodRef8;
import me.supcheg.javafile.facts.VoidMethodRef9;
import me.supcheg.javafile.model.AbstractMethodDecl;
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
public abstract sealed class MemberBuilder<Self, S> extends MemberBuilderArities<Self, S>
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

    /// Documents the class.
    ///
    /// @param doc the comment
    /// @throws IllegalArgumentException if the text of `doc` contains `*/`
    final void doc(me.supcheg.javafile.doc.DocComment doc) {
        owner.doc(doc);
    }

    /// The name of the class, for messages.
    ///
    /// @return the class as the declaration describes it
    final String described() {
        return described;
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
        return nestedClass(name, List.of(), List.of(), new NestedDeclarations.ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h);
            }
        });
    }

    /// Declares an inner member class of the class: `public final class Inner`, which captures the
    /// instance of the class it is declared in and sees its type parameters (JLS 8.1.3). The handle
    /// it returns ties its body to that instance: `inner.outerThis()` is `Outer.this`, and
    /// `inner.new_(outer)` is `outer.new Inner()`.
    ///
    /// @param name the simple name of the member class
    /// @param spec declares the member class, given the first stage of its header
    /// @param <N> the brand of the member class
    /// @return the handle of the inner class
    public final <N> InnerClass<Self, N> declareInnerClass(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        return declareInnerClass(name, new TypedJavaFile.TypedInnerClassSpec<Self>() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, InnerClass<Self, N2> inner) {
                return spec.build(h);
            }
        });
    }

    /// Declares an inner member class whose specification receives the handle of the inner class,
    /// see [#declareInnerClass(String, TypedJavaFile.TypedClassHeaderSpec)].
    ///
    /// @param name the simple name of the member class
    /// @param spec declares the member class, given its header and the handle tied to the enclosing class
    /// @param <N> the brand of the member class
    /// @return the handle of the inner class
    public final <N> InnerClass<Self, N> declareInnerClass(String name, TypedJavaFile.TypedInnerClassSpec<Self> spec) {
        owner.requireDeclaring("an inner class of " + owner.described());
        NestedDeclarations.NestedType<InnerClass<Self, N>> nested = NestedDeclarations.innerClass(
                owner.target(), owner.desc(), name, self, owner.scopeDeclarations(), spec);
        owner.declareNested(nested.type());
        return nested.token();
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
            String name, TypeParameter<B1> p1, GenericDeclarationArities.TypedGenericClassSpec1<B1> spec) {
        return nestedClass(name, List.of(p1), List.of(), new NestedDeclarations.ClassBodies() {
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
            GenericDeclarationArities.TypedGenericClassSpec2<B1, B2> spec) {
        return nestedClass(name, List.of(p1, p2), List.of(), new NestedDeclarations.ClassBodies() {
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
            GenericDeclarationArities.TypedGenericClassSpec3<B1, B2, B3> spec) {
        return nestedClass(name, List.of(p1, p2, p3), List.of(), new NestedDeclarations.ClassBodies() {
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

    /// Declares a generic `public` member interface of the class with one type parameter, see
    /// [#declareNestedInterface(String, TypedJavaFile.TypedInterfaceSpec)].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the type parameter
    /// @param spec declares the interface, given its builder and the token of the type parameter
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member interface
    public final <I, B1> InterfaceToken<I> declareNestedInterface(
            String name, TypeParameter<B1> p1, GenericDeclarationArities.TypedGenericInterfaceSpec1<B1> spec) {
        owner.requireDeclaring("a member interface of " + owner.described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(owner.target(), owner.desc(), name, p1, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member interface of the class with two type parameters, see
    /// [#declareNestedInterface].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the interface, given its builder and the tokens of its type parameters
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member interface
    public final <I, B1, B2> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            GenericDeclarationArities.TypedGenericInterfaceSpec2<B1, B2> spec) {
        owner.requireDeclaring("a member interface of " + owner.described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(owner.target(), owner.desc(), name, p1, p2, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member interface of the class with three type parameters, see
    /// [#declareNestedInterface].
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
    public final <I, B1, B2, B3> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            GenericDeclarationArities.TypedGenericInterfaceSpec3<B1, B2, B3> spec) {
        owner.requireDeclaring("a member interface of " + owner.described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(owner.target(), owner.desc(), name, p1, p2, p3, spec);
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

    /// Declares a generic `public` member record of the class with one type parameter, see
    /// [#declareNestedRecord(String, TypedJavaFile.TypedRecordSpec)].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the type parameter
    /// @param spec declares the record, given its builder and the token of the type parameter
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member record
    public final <R, B1> FinalClassToken<R> declareNestedRecord(
            String name, TypeParameter<B1> p1, GenericDeclarationArities.TypedGenericRecordSpec1<B1> spec) {
        owner.requireDeclaring("a member record of " + owner.described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(owner.target(), owner.desc(), name, p1, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member record of the class with two type parameters, see
    /// [#declareNestedRecord].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the record, given its builder and the tokens of its type parameters
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member record
    public final <R, B1, B2> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            GenericDeclarationArities.TypedGenericRecordSpec2<B1, B2> spec) {
        owner.requireDeclaring("a member record of " + owner.described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(owner.target(), owner.desc(), name, p1, p2, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member record of the class with three type parameters, see
    /// [#declareNestedRecord].
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
    public final <R, B1, B2, B3> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            GenericDeclarationArities.TypedGenericRecordSpec3<B1, B2, B3> spec) {
        owner.requireDeclaring("a member record of " + owner.described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(owner.target(), owner.desc(), name, p1, p2, p3, spec);
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

    /// Refuses type parameters on a member enum: javac rejects `enum E<T>` (JLS 8.9).
    ///
    /// @param name the simple name of the member enum
    /// @param p1 the type parameter
    /// @param spec declares the enum, which is never called
    /// @param <E> the brand of the member enum
    /// @param <B1> the leftmost bound of the type parameter
    /// @return never
    /// @throws IllegalArgumentException always, naming the enum and the type parameter
    // an overload that refuses a type parameter at compile time: the tests call it to read the message it throws
    @SuppressWarnings("DoNotCallSuggester")
    public final <E, B1> EnumToken<E> declareNestedEnum(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedEnumSpec spec) {
        throw new IllegalArgumentException("cannot declare the member enum " + name + " of " + owner.described()
                + " with a type parameter: an enum has no type parameters (JLS 8.9), and the typed layer declares"
                + " only what javac accepts");
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

    /// Refuses type parameters on a member annotation type: javac rejects `@interface A<T>` (JLS
    /// 9.6).
    ///
    /// @param name the simple name of the member annotation type
    /// @param p1 the type parameter
    /// @param spec declares the annotation type, which is never called
    /// @param <B1> the leftmost bound of the type parameter
    /// @return never
    /// @throws IllegalArgumentException always, naming the annotation type and the type parameter
    // an overload that refuses a type parameter at compile time: the tests call it to read the message it throws
    @SuppressWarnings("DoNotCallSuggester")
    public final <B1> ClassDesc declareNestedAnnotationType(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        throw new IllegalArgumentException("cannot declare the member annotation type " + name + " of "
                + owner.described() + " with a type parameter: an annotation type has no type parameters (JLS"
                + " 9.6), and the typed layer declares only what javac accepts");
    }

    private <N> FinalClassToken<N> nestedClass(
            String name,
            List<TypeParameter<?>> parameters,
            List<me.supcheg.javafile.facts.DeclarationIdentity> enclosing,
            NestedDeclarations.ClassBodies spec) {
        owner.requireDeclaring("a member class of " + owner.described());
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(owner.target(), owner.desc(), name, parameters, enclosing, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a member class of the class with the `parameters` of a generated stage, which
    /// specifies it by a specification of four to sixteen type parameters.
    @Override
    final <N> FinalClassToken<N> nestedClassToken(
            String name, List<TypeParameter<?>> parameters, NestedDeclarations.ClassBodies spec) {
        return nestedClass(name, parameters, List.of(), spec);
    }

    /// Declares a member interface of the class with the `parameters` of a generated stage.
    @Override
    final <I> InterfaceToken<I> nestedInterfaceToken(
            String name, List<TypeParameter<?>> parameters, NestedDeclarations.InterfaceBodies spec) {
        owner.requireDeclaring("a member interface of " + owner.described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(owner.target(), owner.desc(), name, parameters, spec);
        owner.declareNested(nested.type());
        return nested.token();
    }

    /// Declares a member record of the class with the `parameters` of a generated stage.
    @Override
    final <R> FinalClassToken<R> nestedRecordToken(
            String name, List<TypeParameter<?>> parameters, NestedDeclarations.RecordBodies spec) {
        owner.requireDeclaring("a member record of " + owner.described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(owner.target(), owner.desc(), name, parameters, spec);
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
    /// bodies.define(read, (b, self) -> b.return_(self.field(in).call(InputStream_.read)));
    /// bodies.define(twice, (b, self) -> b.return_(self.call(read)));
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

    /// Declares a `public final` documented field that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field
    /// @throws IllegalArgumentException if a field of that name is declared already, an annotation type
    ///     cannot annotate a field, or the text of `doc` contains `*/`
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> FieldRef<Self, T> declareField(
            String name, TypeToken<T> type, me.supcheg.javafile.doc.DocComment doc, AnnotationRef<?>... annotations) {
        return declared(
                UnsafeFacts.field(self, name, type),
                Set.of(Modifier.PUBLIC, Modifier.FINAL),
                List.of(annotations),
                Optional.of(Docs.requireSafe(doc, "field " + name + " of " + described)));
    }

    /// Declares a documented field by the exact modifiers it is rendered with.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param modifiers the exact modifiers of the field
    /// @param doc the documentation comment
    /// @param <T> the field type
    /// @return the fact of the field
    /// @throws IllegalArgumentException if the modifiers are not those of a field of the class, a field
    ///     of that name is declared already, or the text of `doc` contains `*/`
    public final <T> FieldRef<Self, T> declareField(
            String name, TypeToken<T> type, Set<Modifier> modifiers, me.supcheg.javafile.doc.DocComment doc) {
        boolean legal = modifiers.contains(Modifier.PUBLIC)
                && modifiers.contains(Modifier.FINAL)
                && modifiers.stream()
                        .allMatch(m -> m == Modifier.PUBLIC || m == Modifier.FINAL || m == Modifier.TRANSIENT);
        if (!legal) {
            throw new IllegalArgumentException("field " + name + " of " + described + " cannot be " + modifiers
                    + ": a field of the class is public final, with transient, and a mutable one is public, with"
                    + " transient or volatile");
        }
        return declared(
                UnsafeFacts.field(self, name, type),
                modifiers,
                List.of(),
                Optional.of(Docs.requireSafe(doc, "field " + name + " of " + described)));
    }

    /// Declares a documented `public` field, not `final`, that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, an annotation type
    ///     cannot annotate a field, or the text of `doc` contains `*/`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> MutableFieldRef<Self, T> declareMutableField(
            String name, TypeToken<T> type, me.supcheg.javafile.doc.DocComment doc, AnnotationRef<?>... annotations) {
        return declared(
                UnsafeFacts.mutableField(self, name, type),
                Set.of(Modifier.PUBLIC),
                List.of(annotations),
                Optional.of(Docs.requireSafe(doc, "field " + name + " of " + described)));
    }

    /// Declares a documented `public` field, not `final`, by the exact modifiers it is rendered with.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param modifiers the exact modifiers of the field
    /// @param doc the documentation comment
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if the modifiers are not those of a mutable field of the class,
    ///     a field of that name is declared already, or the text of `doc` contains `*/`
    public final <T> MutableFieldRef<Self, T> declareMutableField(
            String name, TypeToken<T> type, Set<Modifier> modifiers, me.supcheg.javafile.doc.DocComment doc) {
        boolean legal = modifiers.contains(Modifier.PUBLIC)
                && !modifiers.contains(Modifier.FINAL)
                && modifiers.stream()
                        .allMatch(m -> m == Modifier.PUBLIC || m == Modifier.TRANSIENT || m == Modifier.VOLATILE);
        if (!legal) {
            throw new IllegalArgumentException("mutable field " + name + " of " + described + " cannot be " + modifiers
                    + ": a mutable field of the class is public, with transient or volatile");
        }
        return declared(
                UnsafeFacts.mutableField(self, name, type),
                modifiers,
                List.of(),
                Optional.of(Docs.requireSafe(doc, "field " + name + " of " + described)));
    }

    /// Declares a documented `public static final` field that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, an annotation type
    ///     cannot annotate a field, or the text of `doc` contains `*/`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> StaticFieldRef<T> declareStaticField(
            String name, TypeToken<T> type, me.supcheg.javafile.doc.DocComment doc, AnnotationRef<?>... annotations) {
        return declaredStatic(
                UnsafeFacts.staticField(self, name, type),
                Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
                List.of(annotations),
                Optional.of(Docs.requireSafe(doc, "static field " + name + " of " + described)));
    }

    /// Declares a documented `public static` field, not `final`, that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, an annotation type
    ///     cannot annotate a field, or the text of `doc` contains `*/`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> MutableStaticFieldRef<T> declareMutableStaticField(
            String name, TypeToken<T> type, me.supcheg.javafile.doc.DocComment doc, AnnotationRef<?>... annotations) {
        return declaredStatic(
                UnsafeFacts.mutableStaticField(self, name, type),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(annotations),
                Optional.of(Docs.requireSafe(doc, "static field " + name + " of " + described)));
    }

    // ------------------------------------------------------------------
    // Initializer blocks
    // ------------------------------------------------------------------

    /// Declares an instance initializer block, `{ ... }`, which the bodies of the class define exactly once
    /// with `define`. The block runs before every constructor of the class, after the constructor of its
    /// superclass, in source order among the field initializers and the other blocks: `this` is in scope in it,
    /// so it reads and writes the instance fields and calls the instance methods, but no constructor's
    /// parameters.
    ///
    /// The body is handed `this`, as the body of an instance method is. It may throw a checked exception only
    /// when every constructor of the class declares that exception (JLS 11.2.3).
    ///
    /// @return the handle of the declared block
    /// @throws IllegalStateException if the declarations of the class have ended
    public final InstanceInitializerBlock declareInitializerBlock() {
        owner.requireDeclaring("an instance initializer block of " + described);
        InstanceInitializerBlock block = new InstanceInitializerBlock();
        declared(new Member.Initializer(false, block, Site.here()));
        return block;
    }

    /// Declares a static initializer block, `static { ... }`, which the bodies of the class define exactly once
    /// ([MemberBodies#define(StaticInitializerBlock, java.util.function.Function)]). The block runs once, when
    /// the class is initialized, in source order among the static field initializers and the other blocks. It
    /// has no `this` and no instance member, and may touch the static fields and methods; it throws no checked
    /// exception, caught inside or not at all (JLS 11.2.3).
    ///
    /// @return the handle of the declared block
    /// @throws IllegalStateException if the declarations of the class have ended
    public final StaticInitializerBlock declareStaticInitializerBlock() {
        owner.requireDeclaring("a static initializer block of " + described);
        StaticInitializerBlock block = new StaticInitializerBlock();
        declared(new Member.Initializer(true, block, Site.here()));
        return block;
    }

    private <F extends FieldRef<Self, ?>> F declared(
            F field, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations) {
        return declared(field, modifiers, annotations, Optional.empty());
    }

    private <F extends FieldRef<Self, ?>> F declared(
            F field,
            Set<Modifier> modifiers,
            List<AnnotationRef<?>> annotations,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        String name = field.name();
        Site site = Site.here();
        owner.requireDeclaring("field " + name + " of " + described);
        Tokens.requireNotRaw(field.type());
        owner.scope("the type of field " + name).require(field.type());
        serial.requireSerializable(field, described);
        registry.field(name);
        annotations.forEach(
                annotation -> owner.target().verify(annotation.type().token()));
        declared(new Member.Field(field, modifiers, annotations, doc, site));
        return field;
    }

    /// Declares a static field of the class: a static member mentions no type parameter (JLS 8.1.2), and
    /// is serialized with no value of its own, so what it is of need not be `Serializable`.
    private <F extends StaticFieldRef<?>> F declaredStatic(
            F field, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations) {
        return declaredStatic(field, modifiers, annotations, Optional.empty());
    }

    private <F extends StaticFieldRef<?>> F declaredStatic(
            F field,
            Set<Modifier> modifiers,
            List<AnnotationRef<?>> annotations,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        String name = field.name();
        Site site = Site.here();
        owner.requireDeclaring("static field " + name + " of " + described);
        Tokens.requireNotRaw(field.type());
        owner.staticScope("the type of static field " + name).require(field.type());
        registry.field(name);
        annotations.forEach(
                annotation -> owner.target().verify(annotation.type().token()));
        declared(new Member.StaticField(field, modifiers, annotations, doc, site));
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
            Set<Modifier> modifiers,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        return declare(member, thrown, annotations, parameterAnnotations, modifiers, doc, Site.here());
    }

    /// Declares a method or constructor of the class, said at `site`.
    <F extends Invocable> F declare(
            F member,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Set<Modifier> modifiers,
            Optional<me.supcheg.javafile.doc.DocComment> doc,
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
            declared(new Member.Given(nativeMethod(member, thrown, annotations, parameters, rendered, doc)));
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
                doc,
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
    private static NativeMethodDecl nativeMethod(
            Invocable member,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Set<Modifier> modifiers,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
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
                doc);
    }

    // ------------------------------------------------------------------
    // Abstract methods
    // ------------------------------------------------------------------

    /// Declares an abstract method of the class, `public abstract R name(...)`, which has no body:
    /// it is there whole when it is declared, and the bodies neither define it nor wait for it. An
    /// abstract method is legal in an abstract class alone (JLS 8.4.3.1): a class that is not
    /// abstract implements every abstract method it inherits.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the abstract method
    /// @throws IllegalArgumentException if the class is not abstract, or javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public final <R> MethodRef0<Self, R> declareAbstractMethod(String name, TypeToken<R> result) {
        return declaredAbstract(UnsafeInvocables.method(self, name, result, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method, see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @return the fact of the abstract method
    /// @throws IllegalArgumentException if the class is not abstract, or javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public final VoidMethodRef0<Self> declareVoidAbstractMethod(String name) {
        return declaredAbstract(UnsafeInvocables.voidMethod(self, name, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with one parameter, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the abstract method
    public final <R, A1> MethodRef1<Self, R, A1> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declaredAbstract(UnsafeInvocables.method(self, name, result, p1, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with one parameter, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the abstract method
    public final <A1> VoidMethodRef1<Self, A1> declareVoidAbstractMethod(String name, TypeToken<A1> p1) {
        return declaredAbstract(UnsafeInvocables.voidMethod(self, name, p1, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with two parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declaredAbstract(UnsafeInvocables.method(self, name, result, p1, p2, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with two parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the abstract method
    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declaredAbstract(UnsafeInvocables.voidMethod(self, name, p1, p2, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with three parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declaredAbstract(UnsafeInvocables.method(self, name, result, p1, p2, p3, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with three parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the abstract method
    public final <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declaredAbstract(UnsafeInvocables.voidMethod(self, name, p1, p2, p3, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with four parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declaredAbstract(UnsafeInvocables.method(self, name, result, p1, p2, p3, p4, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with four parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the abstract method
    public final <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declaredAbstract(UnsafeInvocables.voidMethod(self, name, p1, p2, p3, p4, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with five parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareAbstractMethod(
            String name,
            TypeToken<R> result,
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            TypeToken<A3> p3,
            TypeToken<A4> p4,
            TypeToken<A5> p5) {
        return declaredAbstract(UnsafeInvocables.method(self, name, result, p1, p2, p3, p4, p5, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with five parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @return the fact of the abstract method
    public final <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declaredAbstract(UnsafeInvocables.voidMethod(self, name, p1, p2, p3, p4, p5, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with six parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareAbstractMethod(
            String name,
            TypeToken<R> result,
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            TypeToken<A3> p3,
            TypeToken<A4> p4,
            TypeToken<A5> p5,
            TypeToken<A6> p6) {
        return declaredAbstract(
                UnsafeInvocables.method(self, name, result, p1, p2, p3, p4, p5, p6, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with six parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @return the fact of the abstract method
    public final <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareVoidAbstractMethod(
            String name,
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            TypeToken<A3> p3,
            TypeToken<A4> p4,
            TypeToken<A5> p5,
            TypeToken<A6> p6) {
        return declaredAbstract(UnsafeInvocables.voidMethod(self, name, p1, p2, p3, p4, p5, p6, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with seven parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareAbstractMethod(
            String name,
            TypeToken<R> result,
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            TypeToken<A3> p3,
            TypeToken<A4> p4,
            TypeToken<A5> p5,
            TypeToken<A6> p6,
            TypeToken<A7> p7) {
        return declaredAbstract(
                UnsafeInvocables.method(self, name, result, p1, p2, p3, p4, p5, p6, p7, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with seven parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @return the fact of the abstract method
    public final <A1, A2, A3, A4, A5, A6, A7>
            VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareVoidAbstractMethod(
                    String name,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7) {
        return declaredAbstract(
                UnsafeInvocables.voidMethod(self, name, p1, p2, p3, p4, p5, p6, p7, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with eight parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8>
            MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareAbstractMethod(
                    String name,
                    TypeToken<R> result,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8) {
        return declaredAbstract(
                UnsafeInvocables.method(self, name, result, p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with eight parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <A1, A2, A3, A4, A5, A6, A7, A8>
            VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareVoidAbstractMethod(
                    String name,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8) {
        return declaredAbstract(
                UnsafeInvocables.voidMethod(self, name, p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with nine parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9>
            MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareAbstractMethod(
                    String name,
                    TypeToken<R> result,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9) {
        return declaredAbstract(
                UnsafeInvocables.method(self, name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with nine parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9>
            VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidAbstractMethod(
                    String name,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9) {
        return declaredAbstract(
                UnsafeInvocables.voidMethod(self, name, p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with ten parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param p10 the tenth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @param <A10> the tenth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>
            MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareAbstractMethod(
                    String name,
                    TypeToken<R> result,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9,
                    TypeToken<A10> p10) {
        return declaredAbstract(UnsafeInvocables.method(
                self, name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with ten parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param p10 the tenth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @param <A10> the tenth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>
            VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidAbstractMethod(
                    String name,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9,
                    TypeToken<A10> p10) {
        return declaredAbstract(UnsafeInvocables.voidMethod(
                self, name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with eleven parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param p10 the tenth parameter type
    /// @param p11 the eleventh parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @param <A10> the tenth parameter type
    /// @param <A11> the eleventh parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>
            MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareAbstractMethod(
                    String name,
                    TypeToken<R> result,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9,
                    TypeToken<A10> p10,
                    TypeToken<A11> p11) {
        return declaredAbstract(UnsafeInvocables.method(
                self, name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with eleven parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param p10 the tenth parameter type
    /// @param p11 the eleventh parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @param <A10> the tenth parameter type
    /// @param <A11> the eleventh parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>
            VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidAbstractMethod(
                    String name,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9,
                    TypeToken<A10> p10,
                    TypeToken<A11> p11) {
        return declaredAbstract(UnsafeInvocables.voidMethod(
                self, name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.ABSTRACT));
    }

    /// Declares an abstract method with sixteen parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param p10 the tenth parameter type
    /// @param p11 the eleventh parameter type
    /// @param p12 the twelfth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @param <A10> the tenth parameter type
    /// @param <A11> the eleventh parameter type
    /// @param <A12> the twelfth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>
            MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareAbstractMethod(
                    String name,
                    TypeToken<R> result,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9,
                    TypeToken<A10> p10,
                    TypeToken<A11> p11,
                    TypeToken<A12> p12) {
        return declaredAbstract(UnsafeInvocables.method(
                self, name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.ABSTRACT));
    }

    /// Declares a `public abstract void` method with sixteen parameters, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param p5 the fifth parameter type
    /// @param p6 the sixth parameter type
    /// @param p7 the seventh parameter type
    /// @param p8 the eighth parameter type
    /// @param p9 the ninth parameter type
    /// @param p10 the tenth parameter type
    /// @param p11 the eleventh parameter type
    /// @param p12 the twelfth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @param <A5> the fifth parameter type
    /// @param <A6> the sixth parameter type
    /// @param <A7> the seventh parameter type
    /// @param <A8> the eighth parameter type
    /// @param <A9> the ninth parameter type
    /// @param <A10> the tenth parameter type
    /// @param <A11> the eleventh parameter type
    /// @param <A12> the twelfth parameter type
    /// @return the fact of the abstract method
    // one parameter per type token of the method it declares: the arity is the declared signature's
    @SuppressWarnings("TooManyParameters")
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>
            VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidAbstractMethod(
                    String name,
                    TypeToken<A1> p1,
                    TypeToken<A2> p2,
                    TypeToken<A3> p3,
                    TypeToken<A4> p4,
                    TypeToken<A5> p5,
                    TypeToken<A6> p6,
                    TypeToken<A7> p7,
                    TypeToken<A8> p8,
                    TypeToken<A9> p9,
                    TypeToken<A10> p10,
                    TypeToken<A11> p11,
                    TypeToken<A12> p12) {
        return declaredAbstract(UnsafeInvocables.voidMethod(
                self, name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.ABSTRACT));
    }

    private <F extends Invocable> F declaredAbstract(F method) {
        owner.requireDeclaring(method.toString());
        requireInScope(method);
        if (!owner.isAbstract()) {
            throw new IllegalArgumentException("cannot declare the abstract method " + method + " of " + described
                    + ": the class is not abstract, and a class that is not abstract implements every abstract"
                    + " method it inherits (JLS 8.4.3.1) — declare the class abstract_() first");
        }
        registry.member(method);
        declared(new Member.Given(abstractMethod(method)));
        return method;
    }

    /// The core declaration of an abstract method of the class, without a body.
    private static AbstractMethodDecl abstractMethod(Invocable method) {
        List<Param> params = java.util.stream.IntStream.range(0, method.params().size())
                .mapToObj(index ->
                        new Param("v" + index, method.params().get(index).typeRef()))
                .toList();
        return new AbstractMethodDecl(
                method.name(),
                method.resultType().map(TypeToken::typeRef),
                List.of(),
                params,
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                List.of(),
                Optional.empty());
    }

    /// Declares a method of the class that overrides the methods of its signature the class
    /// inherits; rendered with `@Override`.
    ///
    /// @param own its fact
    /// @param thrown the exception classes of its `throws` clause, as its fact has them
    /// @return the fact, `own`
    /// @throws IllegalArgumentException if the class inherits no such method, or cannot override it
    /// Declares a documented method of the class that overrides.
    <F extends Invocable> F declareOverride(
            F own, List<ClassToken<? extends Throwable>> thrown, Optional<me.supcheg.javafile.doc.DocComment> doc) {
        return declareOverride(own, thrown, doc, Site.here());
    }

    /// Declares a method of the class that overrides, said at `site`.
    <F extends Invocable> F declareOverride(F own, List<ClassToken<? extends Throwable>> thrown, Site site) {
        return declareOverride(own, thrown, Optional.empty(), site);
    }

    private <F extends Invocable> F declareOverride(
            F own,
            List<ClassToken<? extends Throwable>> thrown,
            Optional<me.supcheg.javafile.doc.DocComment> doc,
            Site site) {
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
                doc,
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
            /// An abstract method declared by its name, which has no body.
            ABSTRACT,
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
                Optional<me.supcheg.javafile.doc.DocComment> doc,
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
        /// @param doc the documentation comment, if any
        /// @param site where it was declared
        record Field(
                FieldRef<?, ?> fact,
                Set<Modifier> modifiers,
                List<AnnotationRef<?>> annotations,
                Optional<me.supcheg.javafile.doc.DocComment> doc,
                Site site)
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
        /// @param doc the documentation comment, if any
        /// @param site where it was declared
        record StaticField(
                StaticFieldRef<?> fact,
                Set<Modifier> modifiers,
                List<AnnotationRef<?>> annotations,
                Optional<me.supcheg.javafile.doc.DocComment> doc,
                Site site)
                implements Member {
            public StaticField {
                modifiers = Set.copyOf(modifiers);
                annotations = List.copyOf(annotations);
            }
        }

        /// An initializer block, which the bodies of the class define.
        ///
        /// @param isStatic whether it is a `static { ... }` block
        /// @param handle the handle the declaration returned
        /// @param site where it was declared
        record Initializer(boolean isStatic, Object handle, Site site) implements Member {
            /// The block, for messages.
            ///
            /// @return `instance initializer block` or `static initializer block`
            String described() {
                return (isStatic ? "static" : "instance") + " initializer block";
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
