package me.supcheg.javafile.typed;

/// Defines the members of a typed `final` class that extends `Object`
/// (§6.5), branded with `Self`: the stage [TypedClassBuilder#bodies()] gives.
/// Everything [MemberBodies] defines, and the constructors: one of a class
/// that extends `Object` begins with the implicit `super()` and has `this`
/// from its first statement, `define(constructor, body)`.
///
/// @param <Self> the brand of the class being declared
public final class TypedClassBodies<Self> extends TypedClassBodyArities<Self> {

    TypedClassBodies(Declared<Self> declared) {
        super(declared);
    }
}
