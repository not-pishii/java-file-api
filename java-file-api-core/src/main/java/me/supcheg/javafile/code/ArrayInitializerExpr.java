package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// An array creation with an initializer, `new componentType[]{e1, e2, ...}`.
///
/// A "bare" initializer `{e1, e2, ...}` without the leading `new componentType[]`
/// is deliberately not modeled: JLS permits it only in a field, local-variable, or
/// array-element initializer position, which would require render-context tracking
/// to keep it from becoming representable where it's invalid (see [AssignTarget]).
///
/// @param componentType the array's component type
/// @param elements the initializer elements, in order; copied defensively
public record ArrayInitializerExpr(TypeRef componentType, List<Expr> elements) implements Expr {
    public ArrayInitializerExpr {
        elements = List.copyOf(elements);
    }
}
