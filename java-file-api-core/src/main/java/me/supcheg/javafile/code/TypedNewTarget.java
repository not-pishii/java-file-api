package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// A `new` target with an explicit type, e.g. `new Foo(...)` or `new Foo<T>(...)`.
///
/// @param type the instantiated type
public record TypedNewTarget(TypeRef type) implements NewTarget {}
