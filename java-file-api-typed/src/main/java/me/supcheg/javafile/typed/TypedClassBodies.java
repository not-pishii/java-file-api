package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;

/// Defines the members of a typed `final` class that extends `Object`
/// (§6.5), branded with `Self`: the stage [TypedClassBuilder#bodies()] gives.
/// Everything [MemberBodies] defines, and the constructors: one of a class
/// that extends `Object` begins with the implicit `super()` and has `this`
/// from its first statement.
///
/// @param <Self> the brand of the class being declared
public final class TypedClassBodies<Self> extends MemberBodies<Self, Object> {

    TypedClassBodies(Declared<Self> declared) {
        super(declared);
    }

    /// Defines a declared constructor; the body gets `this`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block and `this`
    /// @throws IllegalStateException as a definition of a method does ([MemberBodies])
    public void define(CtorRef0<Self> ctor, InstanceBody0<Self, VoidBody, Void> body) {
        defineConstructor(ctor, body);
    }

    /// Defines a declared constructor; the body gets `this` and the parameter.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @throws IllegalStateException as a definition of a method does ([MemberBodies])
    public <A1> void define(CtorRef1<Self, A1> ctor, InstanceBody1<Self, VoidBody, Void, A1> body) {
        defineConstructor(ctor, body);
    }

    /// Defines a declared constructor; the body gets `this` and the parameters.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @throws IllegalStateException as a definition of a method does ([MemberBodies])
    public <A1, A2> void define(CtorRef2<Self, A1, A2> ctor, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        defineConstructor(ctor, body);
    }
}
