package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;

/// What the body of a class reaches of its superclass `S` because it is a
/// subclass: the `protected` members, and `super.m()`. Of a class that
/// extends `Object`, `S` is `Object` — `clone` and `finalize`.
///
/// **`protected` members.** The fact of a `protected` member of `S`, or of a
/// class above it, is held back by its metamodel ([Protected]);
/// [#protected_] takes it out. With it the method is overridden
/// ([OverridingDeclarations]), called as `super.m()`, or used as any fact —
/// where Java lets a subclass (JLS 6.6.2), which lowering holds every use
/// to, in whatever body the fact ends up:
///
/// - an instance method is called on an expression of the class being
///   declared — `this` or another value of it, seen as an `S` by
///   [TypedSubclassBuilder#asSuper()] — and rendered `this.m(...)`. The
///   receiver cannot be cast to the owner of the method, where the member
///   would not be accessible, so a call that needs the cast is rejected: of
///   a method that declares a checked exception, of one whose result has to
///   be of exactly its type where it stands, of one the class has an overload
///   of that erases alike. Call such a method as `super.m()`, by the own
///   fact of its override, or bind its result to a variable first;
/// - an instance field is read and assigned on `this` alone, and of the
///   direct superclass alone, rendered `super.f`: so neither a field of the
///   class nor a constant of an interface of it is taken for it;
/// - a `static` member is used anywhere in the class, qualified by its owner.
///
/// Access from the same package, which Java allows too, is not offered: the
/// typed layer does not tell packages.
///
/// **`super.m()`** calls the method the superclass has — the body the class
/// inherits or overrides. It is called on `this` alone, which the type of
/// its first argument says ([This]); the method is one of `S` or of a class
/// above it; and where it is called the superclass is checked to have it on
/// the target classpath, with a body (JLS 15.12.3) and as the fact tells it.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public sealed interface SuperAccess<Self, S> permits TypedClassBuilder, TypedSubclassBuilder {

    /// The declarations these are of.
    ///
    /// @return the declarations
    Declarations<Self> declarations();

    /// Takes the fact of a `protected` member of the superclass, or of a class above it, out of
    /// its wrapper.
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact, for this class to use
    /// @throws IllegalArgumentException if on the target classpath the class is no subclass of the owner
    ///     of the member
    default <F extends MemberFact> F protected_(Protected<? super S, F> held) {
        return declarations().builder().opened(held);
    }

    /// `super.method()`.
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param <R> the result type
    /// @return the call
    /// @throws IllegalArgumentException if `self` is `this` of another class; or if on the target classpath
    ///     the superclass has no such method, has it abstract (JLS 15.12.3) or `static`, or otherwise
    ///     than the fact tells
    default <R> Invocation<R> superCall(This<Self> self, MethodRef0<? super S, R> method) {
        return new Invocation<>(
                declarations().builder().superCall(self, method, Expressions.arguments()), method.result());
    }

    /// `super.method(a1)`.
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the argument
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    default <R, A1> Invocation<R> superCall(
            This<Self> self, MethodRef1<? super S, R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(
                declarations().builder().superCall(self, method, Expressions.arguments(a1)), method.result());
    }

    /// `super.method(a1, a2)`.
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    default <R, A1, A2> Invocation<R> superCall(
            This<Self> self, MethodRef2<? super S, R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(
                declarations().builder().superCall(self, method, Expressions.arguments(a1, a2)), method.result());
    }

    /// `super.method()` of a `void` method.
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    default VoidInvocation superCall(This<Self> self, VoidMethodRef0<? super S> method) {
        return new VoidInvocation(declarations().builder().superCall(self, method, Expressions.arguments()));
    }

    /// `super.method(a1)` of a `void` method.
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the argument
    /// @param <A1> the parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    default <A1> VoidInvocation superCall(
            This<Self> self, VoidMethodRef1<? super S, A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(declarations().builder().superCall(self, method, Expressions.arguments(a1)));
    }

    /// `super.method(a1, a2)` of a `void` method.
    ///
    /// @param self `this`
    /// @param method the fact of the method
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the call
    /// @throws IllegalArgumentException as [#superCall(This, MethodRef0)]
    default <A1, A2> VoidInvocation superCall(
            This<Self> self, VoidMethodRef2<? super S, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(declarations().builder().superCall(self, method, Expressions.arguments(a1, a2)));
    }
}
