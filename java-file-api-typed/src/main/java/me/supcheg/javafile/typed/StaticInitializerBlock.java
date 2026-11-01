package me.supcheg.javafile.typed;

/// A declared static initializer block, `static { ... }`: the handle
/// [#MemberBuilder#declareStaticInitializerBlock()] returns, which the bodies
/// of the class define exactly once ([MemberBodies#define(StaticInitializerBlock,
/// java.util.function.Function)]). It is no fact: Java gives a block no name
/// and no type, so only its declaration and its body tell it.
///
/// The body has no `this`, as the body of a static method has none, and runs
/// once, when the class is initialized.
public final class StaticInitializerBlock {

    StaticInitializerBlock() {}
}
