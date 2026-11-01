package me.supcheg.javafile.typed;

/// A declared instance initializer block, `{ ... }`: the handle
/// [#MemberBuilder#declareInitializerBlock()] returns, which the bodies of the
/// class define exactly once ([MemberBodies#define(InstanceInitializerBlock,
/// MemberBodyArities.InstanceBody0)]). It is no fact: Java gives a block no
/// name and no type, so only its declaration and its body tell it.
///
/// The body is handed `this`, as the body of an instance method is, and runs
/// before every constructor of the class, after the constructor of its
/// superclass.
public final class InstanceInitializerBlock {

    InstanceInitializerBlock() {}
}
