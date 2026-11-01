package me.supcheg.javafile.code;

import java.lang.constant.ClassDesc;

/// The `this` of an enclosing instance, `Outer.this` (JLS 15.8.4).
///
/// @param owner the enclosing class whose `this` is named
public record QualifiedThisExpr(ClassDesc owner) implements Expr {}
