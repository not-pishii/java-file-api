package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;

import java.util.List;

/// The arguments of a parameter of variable arity, `m(a, e1, e2, e3)`: the elements of the array the
/// last parameter takes (JLS 15.12.4.2), passed one by one. It is no expression, for no expression is
/// the arguments of a parameter: it stands as the last argument of a call of a member of variable arity,
/// and nowhere else. Made by [Expressions#varargs(ArrayToken, Expr[])] or, for the elements of a list, by
/// [Expressions#varargs(ArrayToken, List)].
///
/// An array the code has is passed as it is, `m(a, array)`, as an argument of the type of the parameter.
///
/// @param <A> the array type of the parameter
public final class Varargs<A> {
    /// The array of the parameter.
    final ArrayToken<A, ?> type;

    /// The elements, in order.
    final List<Expr<?>> elements;

    Varargs(ArrayToken<A, ?> type, List<Expr<?>> elements) {
        this.type = type;
        this.elements = List.copyOf(elements);
    }
}
