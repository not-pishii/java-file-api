package me.supcheg.javafile.code;

import java.util.ArrayList;
import java.util.List;

/// A list guaranteed to hold at least one element.
///
/// Used where Java's grammar requires cardinality `>= 1` but a plain
/// `List` would make the empty case representable: multi-catch exception
/// types, and the `catch` clauses of a `try` with no `finally`.
///
/// @param head the first element
/// @param tail the remaining elements, in order; copied defensively
/// @param <T> the element type
public record NonEmptyList<T>(T head, List<T> tail) {

    public NonEmptyList {
        tail = List.copyOf(tail);
    }

    /// Builds a [NonEmptyList] from a plain list.
    ///
    /// @param list the source list, in order
    /// @param <T> the element type
    /// @return a non-empty list holding `list`'s elements in order
    /// @throws IllegalArgumentException if `list` is empty
    public static <T> NonEmptyList<T> copyOf(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list must not be empty");
        }
        return new NonEmptyList<>(list.get(0), list.subList(1, list.size()));
    }

    /// Returns all elements, `head` followed by `tail`, as an immutable list.
    ///
    /// @return the combined elements, in order
    public List<T> toList() {
        List<T> result = new ArrayList<>(tail.size() + 1);
        result.add(head);
        result.addAll(tail);
        return List.copyOf(result);
    }
}
