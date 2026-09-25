package me.supcheg.javafile.code;

import java.lang.constant.ClassDesc;

/// The type in `new Foo<>(...)`, with type arguments left to inference.
///
/// Create the expression with [Exprs#newDiamond(ClassDesc,Expr...)].
///
/// @param raw the instantiated generic class, without type arguments
public record DiamondNewTarget(ClassDesc raw) implements NewTarget {}
