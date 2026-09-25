package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// An array creation with an initializer, `new componentType[]{e1, e2, ...}`.
///
/// The short form `{e1, e2, ...}` is not supported; the `new componentType[]`
/// prefix is always written.
///
/// @param componentType the array's component type
/// @param elements the initializer elements, in order
public record ArrayInitializerExpr(TypeRef componentType, List<Expr> elements) implements Expr {
    public ArrayInitializerExpr {
        elements = List.copyOf(elements);
    }
}
