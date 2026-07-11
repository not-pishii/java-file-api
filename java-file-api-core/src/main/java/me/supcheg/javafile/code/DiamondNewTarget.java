package me.supcheg.javafile.code;

import java.lang.constant.ClassDesc;

/// A `new` target using the diamond operator, e.g. `new Foo<>(...)`, leaving
/// the type arguments to be inferred. Holds the raw class, so explicit type
/// arguments cannot be combined with the diamond.
///
/// @param raw the instantiated generic class, without type arguments
public record DiamondNewTarget(ClassDesc raw) implements NewTarget {}
