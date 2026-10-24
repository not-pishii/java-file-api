package me.supcheg.javafile.code;

import java.util.List;

/// An array initializer, `{e1, e2, ...}` (JLS 10.6): the elements of an array, each an expression or, for an array of
/// arrays, an initializer of the array it is.
///
/// It stands where the type of the array is known: the initializer of a typed local variable or field, the element of
/// another array initializer, and the body of an [ArrayInitializerExpr]. There it renders as `{e1, e2}`, and `{}` when
/// it has no elements.
///
/// @param elements the elements, in order
public record ArrayInitializer(List<VariableInitializer> elements) implements VariableInitializer {
    public ArrayInitializer {
        elements = List.copyOf(elements);
    }
}
