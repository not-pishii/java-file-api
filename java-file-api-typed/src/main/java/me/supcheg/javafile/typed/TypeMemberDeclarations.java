package me.supcheg.javafile.typed;

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
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

import java.util.List;

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

    /// The traits of an instance method declared by name: `FINAL` for a record, whose methods no
    /// subclass can override, `DEFAULT` for an enum, whose constant bodies may.
    MemberTraits instanceTraits() {
        return MemberTraits.DEFAULT;
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
