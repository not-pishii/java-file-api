package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

import java.util.List;
import java.util.Set;

/// The declarations of the methods of a type that is not a `final` class (§6.5): an interface, a record
/// or an enum. Each returns the fact of the member it declares; the bodies ([TypedTypeBodies]) define
/// them.
///
/// The methods reach arity 4 for the kinds of §6.5; their constructors reach arity 12, as the forms of
/// the class builder do (`RecordConstructorArities`, `EnumConstructorArities`).
///
/// @param <Self> the brand of the type being declared
abstract class TypeMemberDeclarations<Self> extends TypeDeclarationSupport<Self> {

    TypeMemberDeclarations(
            me.supcheg.javafile.facts.TargetClasspath target, java.lang.constant.ClassDesc desc, String kind) {
        super(target, desc, kind);
    }

    // ------------------------------------------------------------------
    // Member types
    // ------------------------------------------------------------------

    /// Declares a `public static final` member class of the type: a member class of an interface, a
    /// record or an enum is implicitly `static`, and sees no type parameter of the type it is in.
    ///
    /// @param name the simple name of the member class
    /// @param spec declares the member class, given the first stage of its header
    /// @param <N> the brand of the member class
    /// @return the token of the member class
    public final <N> FinalClassToken<N> declareNestedClass(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        requireDeclaring("a member class of " + described());
        NestedDeclarations.NestedType<FinalClassToken<N>> nested =
                NestedDeclarations.class_(target(), desc(), name, java.util.List.of(), java.util.List.of(), spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a `public` member interface of the type, implicitly `static`.
    ///
    /// @param name the simple name of the member interface
    /// @param spec declares the interface, given its builder
    /// @param <I> the brand of the member interface
    /// @return the token of the member interface
    public final <I> InterfaceToken<I> declareNestedInterface(String name, TypedJavaFile.TypedInterfaceSpec spec) {
        requireDeclaring("a member interface of " + described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target(), desc(), name, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member interface of the type with one type parameter, implicitly
    /// `static`; see [#declareNestedInterface(String, TypedJavaFile.TypedInterfaceSpec)].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the type parameter
    /// @param spec declares the interface, given its builder and the token of the type parameter
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member interface
    public final <I, B1> InterfaceToken<I> declareNestedInterface(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericInterfaceSpec1<B1> spec) {
        requireDeclaring("a member interface of " + described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target(), desc(), name, p1, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member interface of the type with two type parameters, see
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
    public final <I, B1, B2> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericInterfaceSpec2<B1, B2> spec) {
        requireDeclaring("a member interface of " + described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target(), desc(), name, p1, p2, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member interface of the type with three type parameters, see
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
    public final <I, B1, B2, B3> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericInterfaceSpec3<B1, B2, B3> spec) {
        requireDeclaring("a member interface of " + described());
        NestedDeclarations.NestedType<InterfaceToken<I>> nested =
                NestedDeclarations.interface_(target(), desc(), name, p1, p2, p3, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a `public` member record of the type, implicitly `static`.
    ///
    /// @param name the simple name of the member record
    /// @param spec declares the record, given its builder
    /// @param <R> the brand of the member record
    /// @return the token of the member record
    public final <R> FinalClassToken<R> declareNestedRecord(String name, TypedJavaFile.TypedRecordSpec spec) {
        requireDeclaring("a member record of " + described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target(), desc(), name, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member record of the type with one type parameter, implicitly
    /// `static`; see [#declareNestedRecord(String, TypedJavaFile.TypedRecordSpec)].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the type parameter
    /// @param spec declares the record, given its builder and the token of the type parameter
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member record
    public final <R, B1> FinalClassToken<R> declareNestedRecord(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericRecordSpec1<B1> spec) {
        requireDeclaring("a member record of " + described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target(), desc(), name, p1, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member record of the type with two type parameters, see
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
    public final <R, B1, B2> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericRecordSpec2<B1, B2> spec) {
        requireDeclaring("a member record of " + described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target(), desc(), name, p1, p2, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a generic `public` member record of the type with three type parameters, see
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
    public final <R, B1, B2, B3> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericRecordSpec3<B1, B2, B3> spec) {
        requireDeclaring("a member record of " + described());
        NestedDeclarations.NestedType<FinalClassToken<R>> nested =
                NestedDeclarations.record(target(), desc(), name, p1, p2, p3, spec);
        declaredNested(nested.type());
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
    public final <E, B1> EnumToken<E> declareNestedEnum(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedEnumSpec spec) {
        throw genericNested("enum", name, "an enum has no type parameters (JLS 8.9)");
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
    public final <B1> java.lang.constant.ClassDesc declareNestedAnnotationType(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        throw genericNested("annotation type", name, "an annotation type has no type parameters (JLS 9.6)");
    }

    private IllegalArgumentException genericNested(String kind, String name, String why) {
        return new IllegalArgumentException("cannot declare the member " + kind + " " + name + " of " + described()
                + " with a type parameter: " + why + ", and the typed layer declares only what javac accepts");
    }

    /// Declares a `public` member enum of the type, implicitly `static`.
    ///
    /// @param name the simple name of the member enum
    /// @param spec declares the enum, given its builder
    /// @param <E> the brand of the member enum
    /// @return the token of the member enum
    public final <E> EnumToken<E> declareNestedEnum(String name, TypedJavaFile.TypedEnumSpec spec) {
        requireDeclaring("a member enum of " + described());
        NestedDeclarations.NestedType<EnumToken<E>> nested = NestedDeclarations.enum_(target(), desc(), name, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// Declares a `public` member annotation type of the type, implicitly `static`.
    ///
    /// @param name the simple name of the member annotation type
    /// @param spec declares the annotation type, given its builder
    /// @return the binary name of the member annotation type
    public final java.lang.constant.ClassDesc declareNestedAnnotationType(
            String name, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        requireDeclaring("a member annotation type of " + described());
        NestedDeclarations.NestedType<java.lang.constant.ClassDesc> nested =
                NestedDeclarations.annotationType(target(), desc(), name, spec);
        declaredNested(nested.type());
        return nested.token();
    }

    /// The traits of an instance method declared by name: `FINAL` for a record, whose methods no
    /// subclass can override, `DEFAULT` for an enum, whose constant bodies may.
    MemberTraits instanceTraits() {
        return MemberTraits.DEFAULT;
    }

    /// Whether the kind has `synchronized` and `native` methods: an enum has both, an interface
    /// neither, and a record has `synchronized` alone — javac rejects a native method of a record
    /// (JLS 8.10.3).
    boolean allowSynchronizedAndNative() {
        return true;
    }

    /// Whether the kind has `native` methods: a record does not (JLS 8.10.3).
    boolean allowNativeMethod() {
        return true;
    }

    /// Declares methods that are rendered with `modifiers` beside those their form fixes:
    ///
    /// ```java
    /// var spin = rb.modified(Modifier.SYNCHRONIZED).declareVoidMethod("spin");
    /// var read = eb.modified(Modifier.NATIVE).declareMethod("read", PrimitiveToken.INT);
    /// ```
    ///
    /// A `synchronized` method has a body, which the bodies define; a `native` method has none, and
    /// is there whole when it is declared. An interface has neither, and a constructor is neither.
    ///
    /// @param modifiers the modifiers to add, in any order
    /// @return the declarations that render the methods they declare with them
    /// @throws IllegalArgumentException if the kind or the method cannot be one, naming it
    /// @throws IllegalStateException if the declarations have ended
    public final TypeModifiedDeclarations<Self> modified(Modifier... modifiers) {
        requireDeclaring("the modifiers of a method of " + described());
        if (!allowSynchronizedAndNative()) {
            throw new IllegalArgumentException("cannot declare a method of " + described() + " " + Set.of(modifiers)
                    + ": an interface has no synchronized or native method — its methods are abstract,"
                    + " default, static or private (JLS 9.4)");
        }
        boolean native_ = false;
        for (Modifier modifier : modifiers) {
            if (modifier != Modifier.SYNCHRONIZED && modifier != Modifier.NATIVE) {
                throw new IllegalArgumentException("a method of " + described() + " cannot add the modifier "
                        + modifier + " here: the declarations render the methods synchronized or native, and the"
                        + " rest of a method's modifiers is that of its form");
            }
            native_ |= modifier == Modifier.NATIVE;
        }
        if (native_ && !allowNativeMethod()) {
            throw new IllegalArgumentException("cannot declare a method of " + described() + " native: javac"
                    + " rejects a native method of a record (JLS 8.10.3), and the typed layer declares only what"
                    + " javac accepts");
        }
        return new TypeModifiedDeclarations<>(this, Set.of(modifiers));
    }

    final <F extends Invocable> F declare(F fact, Kind kind, Set<Modifier> modifiers) {
        return declare(fact, kind, List.of(), List.of(), List.of(), modifiers);
    }

    private static MemberTraits abstractTraits() {
        return MemberTraits.ABSTRACT;
    }

    private static MemberTraits defaultTraits() {
        return MemberTraits.DEFAULT;
    }

    private static MemberTraits staticTraits() {
        return MemberTraits.FINAL;
    }

    private static MemberTraits privateTraits() {
        return MemberTraits.FINAL;
    }

    private <F extends Invocable> F declared(F fact, Kind kind) {
        return declare(fact, kind, List.of(), List.of(), List.of());
    }

    // ------------------------------------------------------------------
    // Overrides
    // ------------------------------------------------------------------

    public final <O, R> MethodRef0<Self, R> declareOverride(Upcast<Self, ? extends O> up, MethodRef0<O, R> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, R, A1> MethodRef1<Self, R, A1> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, R, A1, A2> MethodRef2<Self, R, A1, A2> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef2<O, R, A1, A2> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef3<O, R, A1, A2, A3> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef4<O, R, A1, A2, A3, A4> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O> VoidMethodRef0<Self> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef0<O> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, A1> VoidMethodRef1<Self, A1> declareOverride(
            Upcast<Self, ? extends O> up, VoidMethodRef1<O, A1> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, A1, A2> VoidMethodRef2<Self, A1, A2> declareOverride(
            Upcast<Self, ? extends O> up, VoidMethodRef2<O, A1, A2> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareOverride(
            Upcast<Self, ? extends O> up, VoidMethodRef3<O, A1, A2, A3> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    public final <O, A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareOverride(
            Upcast<Self, ? extends O> up, VoidMethodRef4<O, A1, A2, A3, A4> method) {
        return declareOverride(up, own(method), Kind.METHOD);
    }

    private <F extends Invocable> F declareOverride(Upcast<Self, ?> up, F own, Kind kind) {
        return declareOverride(up, own, kind, List.of(), List.of(), List.of());
    }

    private <O, R> MethodRef0<Self, R> own(MethodRef0<O, R> method) {
        return UnsafeInvocables.method(self(), method.name(), method.result(), Overrides.traits(method, List.of()));
    }

    private <O, R, A1> MethodRef1<Self, R, A1> own(MethodRef1<O, R, A1> method) {
        return UnsafeInvocables.method(
                self(), method.name(), method.result(), method.param1(), Overrides.traits(method, List.of()));
    }

    private <O, R, A1, A2> MethodRef2<Self, R, A1, A2> own(MethodRef2<O, R, A1, A2> method) {
        return UnsafeInvocables.method(
                self(),
                method.name(),
                method.result(),
                method.param1(),
                method.param2(),
                Overrides.traits(method, List.of()));
    }

    private <O, R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> own(MethodRef3<O, R, A1, A2, A3> method) {
        return UnsafeInvocables.method(
                self(),
                method.name(),
                method.result(),
                method.param1(),
                method.param2(),
                method.param3(),
                Overrides.traits(method, List.of()));
    }

    private <O, R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> own(MethodRef4<O, R, A1, A2, A3, A4> method) {
        return UnsafeInvocables.method(
                self(),
                method.name(),
                method.result(),
                method.param1(),
                method.param2(),
                method.param3(),
                method.param4(),
                Overrides.traits(method, List.of()));
    }

    private <O> VoidMethodRef0<Self> own(VoidMethodRef0<O> method) {
        return UnsafeInvocables.voidMethod(self(), method.name(), Overrides.traits(method, List.of()));
    }

    private <O, A1> VoidMethodRef1<Self, A1> own(VoidMethodRef1<O, A1> method) {
        return UnsafeInvocables.voidMethod(self(), method.name(), method.param1(), Overrides.traits(method, List.of()));
    }

    private <O, A1, A2> VoidMethodRef2<Self, A1, A2> own(VoidMethodRef2<O, A1, A2> method) {
        return UnsafeInvocables.voidMethod(
                self(), method.name(), method.param1(), method.param2(), Overrides.traits(method, List.of()));
    }

    private <O, A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> own(VoidMethodRef3<O, A1, A2, A3> method) {
        return UnsafeInvocables.voidMethod(
                self(),
                method.name(),
                method.param1(),
                method.param2(),
                method.param3(),
                Overrides.traits(method, List.of()));
    }

    private <O, A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> own(VoidMethodRef4<O, A1, A2, A3, A4> method) {
        return UnsafeInvocables.voidMethod(
                self(),
                method.name(),
                method.param1(),
                method.param2(),
                method.param3(),
                method.param4(),
                Overrides.traits(method, List.of()));
    }

    /// Declares a constant of an interface: implicitly `public static final`.
    ///
    /// @param name the constant name
    /// @param type the constant type
    /// @param <T> the constant type
    /// @return the fact of the constant
    /// @throws IllegalArgumentException if a field of the name is declared already
    /// @throws IllegalStateException if the declarations have ended
    public final <T> me.supcheg.javafile.facts.StaticFieldRef<T> declareConstant(String name, TypeToken<T> type) {
        return declareConstant(name, type, List.of());
    }

    /// Declares an instance field of a record or an enum.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param modifiers the modifiers of the field
    /// @param <T> the field type
    /// @return the fact of the field
    /// @throws IllegalArgumentException if a field of the name is declared already
    /// @throws IllegalStateException if the declarations have ended
    public final <T> me.supcheg.javafile.facts.FieldRef<Self, T> declareField(
            String name, TypeToken<T> type, java.util.Set<me.supcheg.javafile.model.Modifier> modifiers) {
        return declareField(name, type, modifiers, List.of());
    }

    // ------------------------------------------------------------------
    // Abstract methods
    // ------------------------------------------------------------------

    public final <R> MethodRef0<Self, R> declareAbstractMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(self(), name, result, abstractTraits()), Kind.ABSTRACT);
    }

    public final VoidMethodRef0<Self> declareVoidAbstractMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(self(), name, abstractTraits()), Kind.ABSTRACT);
    }

    public final <R, A1> MethodRef1<Self, R, A1> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, abstractTraits()), Kind.ABSTRACT);
    }

    public final <A1> VoidMethodRef1<Self, A1> declareVoidAbstractMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, abstractTraits()), Kind.ABSTRACT);
    }

    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, abstractTraits()), Kind.ABSTRACT);
    }

    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, abstractTraits()), Kind.ABSTRACT);
    }

    public final <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, abstractTraits()), Kind.ABSTRACT);
    }

    public final <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, abstractTraits()), Kind.ABSTRACT);
    }

    public final <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, abstractTraits()), Kind.ABSTRACT);
    }

    public final <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, abstractTraits()), Kind.ABSTRACT);
    }

    // ------------------------------------------------------------------
    // Default methods
    // ------------------------------------------------------------------

    public final <R> MethodRef0<Self, R> declareDefaultMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(self(), name, result, defaultTraits()), Kind.DEFAULT);
    }

    public final VoidMethodRef0<Self> declareVoidDefaultMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(self(), name, defaultTraits()), Kind.DEFAULT);
    }

    public final <R, A1> MethodRef1<Self, R, A1> declareDefaultMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, defaultTraits()), Kind.DEFAULT);
    }

    public final <A1> VoidMethodRef1<Self, A1> declareVoidDefaultMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, defaultTraits()), Kind.DEFAULT);
    }

    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declareDefaultMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, defaultTraits()), Kind.DEFAULT);
    }

    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidDefaultMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, defaultTraits()), Kind.DEFAULT);
    }

    public final <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareDefaultMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, defaultTraits()), Kind.DEFAULT);
    }

    public final <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidDefaultMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, defaultTraits()), Kind.DEFAULT);
    }

    public final <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareDefaultMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, defaultTraits()), Kind.DEFAULT);
    }

    public final <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidDefaultMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, defaultTraits()), Kind.DEFAULT);
    }

    // ------------------------------------------------------------------
    // Static methods
    // ------------------------------------------------------------------

    public final <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.staticMethod(self(), name, result, staticTraits()), Kind.STATIC);
    }

    public final VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, staticTraits()), Kind.STATIC);
    }

    public final <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.staticMethod(self(), name, result, p1, staticTraits()), Kind.STATIC);
    }

    public final <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, p1, staticTraits()), Kind.STATIC);
    }

    public final <R, A1, A2> StaticMethodRef2<R, A1, A2> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, staticTraits()), Kind.STATIC);
    }

    public final <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, staticTraits()), Kind.STATIC);
    }

    public final <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, staticTraits()), Kind.STATIC);
    }

    public final <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, staticTraits()), Kind.STATIC);
    }

    public final <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(
                UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, staticTraits()), Kind.STATIC);
    }

    public final <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, staticTraits()), Kind.STATIC);
    }

    // ------------------------------------------------------------------
    // Private methods
    // ------------------------------------------------------------------

    public final <R> MethodRef0<Self, R> declarePrivateMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(self(), name, result, privateTraits()), Kind.PRIVATE);
    }

    public final VoidMethodRef0<Self> declareVoidPrivateMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(self(), name, privateTraits()), Kind.PRIVATE);
    }

    public final <R, A1> MethodRef1<Self, R, A1> declarePrivateMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, privateTraits()), Kind.PRIVATE);
    }

    public final <A1> VoidMethodRef1<Self, A1> declareVoidPrivateMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, privateTraits()), Kind.PRIVATE);
    }

    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declarePrivateMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, privateTraits()), Kind.PRIVATE);
    }

    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidPrivateMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, privateTraits()), Kind.PRIVATE);
    }

    public final <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declarePrivateMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, privateTraits()), Kind.PRIVATE);
    }

    public final <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidPrivateMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, privateTraits()), Kind.PRIVATE);
    }

    public final <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declarePrivateMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, privateTraits()), Kind.PRIVATE);
    }

    public final <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidPrivateMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, privateTraits()), Kind.PRIVATE);
    }

    // ------------------------------------------------------------------
    // Private static methods
    // ------------------------------------------------------------------

    public final <R> StaticMethodRef0<R> declarePrivateStaticMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.staticMethod(self(), name, result, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final VoidStaticMethodRef0 declareVoidPrivateStaticMethod(String name) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final <R, A1> StaticMethodRef1<R, A1> declarePrivateStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.staticMethod(self(), name, result, p1, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final <A1> VoidStaticMethodRef1<A1> declareVoidPrivateStaticMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, p1, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final <R, A1, A2> StaticMethodRef2<R, A1, A2> declarePrivateStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.staticMethod(self(), name, result, p1, p2, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidPrivateStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declarePrivateStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(
                UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidPrivateStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(
                UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, privateTraits()), Kind.PRIVATE_STATIC);
    }

    public final <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declarePrivateStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(
                UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, privateTraits()),
                Kind.PRIVATE_STATIC);
    }

    public final <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidPrivateStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(
                UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, privateTraits()), Kind.PRIVATE_STATIC);
    }

    // ------------------------------------------------------------------
    // Instance methods of a record or enum
    // ------------------------------------------------------------------

    public final <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(self(), name, result, instanceTraits()), Kind.METHOD);
    }

    public final VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(self(), name, instanceTraits()), Kind.METHOD);
    }

    public final <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, instanceTraits()), Kind.METHOD);
    }

    public final <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, instanceTraits()), Kind.METHOD);
    }

    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, instanceTraits()), Kind.METHOD);
    }

    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, instanceTraits()), Kind.METHOD);
    }

    public final <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, instanceTraits()), Kind.METHOD);
    }

    public final <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, instanceTraits()), Kind.METHOD);
    }

    public final <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, instanceTraits()), Kind.METHOD);
    }

    public final <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, instanceTraits()), Kind.METHOD);
    }
}
