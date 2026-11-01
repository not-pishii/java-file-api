package me.supcheg.javafile.typed;

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

import java.util.Set;

/// The declarations of the methods of an interface, a record or an enum that are rendered with
/// `synchronized` or `native` ([TypeMemberDeclarations#modified]): a record and an enum have both,
/// and an interface has neither.
///
/// A `synchronized` method has a body, which the bodies define; a `native` method has none, and is
/// there whole when it is declared.
///
/// @param <Self> the brand of the type being declared
public final class TypeModifiedDeclarations<Self> {
    private final TypeMemberDeclarations<Self> builder;
    private final Set<Modifier> modifiers;

    TypeModifiedDeclarations(TypeMemberDeclarations<Self> builder, Set<Modifier> modifiers) {
        this.builder = builder;
        this.modifiers = Set.copyOf(modifiers);
    }

    /// Declares a `public` instance method of a record or an enum, rendered with the modifiers.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return builder.declare(
                UnsafeInvocables.method(builder.self(), name, result, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public void` instance method of a record or an enum, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @return the fact of the method
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return builder.declare(
                UnsafeInvocables.voidMethod(builder.self(), name, builder.instanceTraits()), Kind.METHOD, modifiers);
    }

    /// Declares a `public` instance method with one parameter, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return builder.declare(
                UnsafeInvocables.method(builder.self(), name, result, p1, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public void` instance method with one parameter, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return builder.declare(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public` instance method with two parameters, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return builder.declare(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public void` instance method with two parameters, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return builder.declare(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public` instance method with three parameters, see
    /// [#declareMethod(String, TypeToken)].
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
    /// @return the fact of the method
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return builder.declare(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public void` instance method with three parameters, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return builder.declare(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public` instance method with four parameters, see
    /// [#declareMethod(String, TypeToken)].
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
    /// @return the fact of the method
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return builder.declare(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, p4, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public void` instance method with four parameters, see
    /// [#declareMethod(String, TypeToken)].
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
    /// @return the fact of the method
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return builder.declare(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, p4, builder.instanceTraits()),
                Kind.METHOD,
                modifiers);
    }

    /// Declares a `public static` method of a record or an enum, rendered with the modifiers.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return builder.declare(
                UnsafeInvocables.staticMethod(builder.self(), name, result, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static void` method of a record or an enum, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @return the fact of the method
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return builder.declare(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, MemberTraits.FINAL), Kind.STATIC, modifiers);
    }

    /// Declares a `public static` method with one parameter, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return builder.declare(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static void` method with one parameter, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return builder.declare(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static` method with two parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <R, A1, A2> StaticMethodRef2<R, A1, A2> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return builder.declare(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, p2, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static void` method with two parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return builder.declare(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, p2, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static` method with three parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
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
    /// @return the fact of the method
    public <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return builder.declare(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, p2, p3, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static void` method with three parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return builder.declare(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, p2, p3, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static` method with four parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
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
    /// @return the fact of the method
    public <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return builder.declare(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, p2, p3, p4, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }

    /// Declares a `public static void` method with four parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
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
    /// @return the fact of the method
    public <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return builder.declare(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, p2, p3, p4, MemberTraits.FINAL),
                Kind.STATIC,
                modifiers);
    }
}
