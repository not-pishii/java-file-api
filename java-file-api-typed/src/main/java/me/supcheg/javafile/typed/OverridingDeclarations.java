package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody0;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody1;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody2;

/// The overrides of the methods a class inherits from its superclass `S`,
/// named by their facts: a fact of `S` or of a superclass of it — which
/// javac holds the generator to, `S` being the type the class was declared
/// to extend — so `Object_.toString` for any class. A method the class has
/// from an interface is overridden by the witness of the interface
/// ([Declarations#override(Upcast, MethodRef0, InstanceBody0)]), and one a
/// supertype leaves abstract in the header of the class ([Overriding]).
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public sealed interface OverridingDeclarations<Self, S> permits TypedClassBuilder, ThrowingDeclarations {

    /// The declarations these are of.
    ///
    /// @return the declarations
    Declarations<Self> declarations();

    /// Overrides a method the class inherits from its superclass: declares it anew, `@Override public`, and defines it.
    ///
    /// @param method the fact of the overridden method
    /// @param body the body, given its block and `this`
    /// @param <R> the result type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    default <R> MethodRef0<Self, R> override(MethodRef0<? super S, R> method, InstanceBody0<Self, Body<R>, R> body) {
        return declarations().overridden(method, body);
    }

    /// Overrides a method with one parameter of `Object`: declares it anew, `@Override public`, and defines it.
    ///
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    default <R, A1> MethodRef1<Self, R, A1> override(
            MethodRef1<? super S, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        return declarations().overridden(method, body);
    }

    /// Overrides a method with two parameters of `Object`: declares it anew, `@Override public`, and defines it.
    ///
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    default <R, A1, A2> MethodRef2<Self, R, A1, A2> override(
            MethodRef2<? super S, R, A1, A2> method, InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        return declarations().overridden(method, body);
    }

    /// Overrides a `void` method of `Object`: declares it anew, `@Override public`, and defines it.
    ///
    /// @param method the fact of the overridden method
    /// @param body the body, given its block and `this`
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    default VoidMethodRef0<Self> override(VoidMethodRef0<? super S> method, InstanceBody0<Self, VoidBody, Void> body) {
        return declarations().overridden(method, body);
    }

    /// Overrides a `void` method with one parameter of `Object`: declares it anew, `@Override public`, and defines it.
    ///
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    default <A1> VoidMethodRef1<Self, A1> override(
            VoidMethodRef1<? super S, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        return declarations().overridden(method, body);
    }

    /// Overrides a `void` method with two parameters of `Object`: declares it anew, `@Override public`, and defines it.
    ///
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    default <A1, A2> VoidMethodRef2<Self, A1, A2> override(
            VoidMethodRef2<? super S, A1, A2> method, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        return declarations().overridden(method, body);
    }
}
