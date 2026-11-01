package me.supcheg.javafile.facts;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

/// A type a member's `throws` clause names, as a method table records it:
/// its erasure, the superclass chain of that erasure, and the name of the
/// type variable where it is one.
///
/// A type variable is known by its bound alone, which is its erasure; a
/// class is known by the chain the facts of its metamodel recorded, so that
/// a `catch` can tell whether it covers the thrown type without a token of
/// it.
///
/// @param erasure the erasure of the thrown type, a class
/// @param superclasses the erasure's superclass chain, the direct superclass first and
///     `java.lang.Object` last; empty for `java.lang.Object`
/// @param variable the name of the type variable the clause names, or empty for a class
public record Thrown(ClassDesc erasure, List<ClassDesc> superclasses, Optional<String> variable) {

    /// @throws IllegalArgumentException if `erasure` is not a class, or the chain does not end in
    ///     `java.lang.Object`
    public Thrown {
        if (!erasure.isClassOrInterface()) {
            throw new IllegalArgumentException("a thrown type is a class, got " + erasure.displayName());
        }
        superclasses = List.copyOf(superclasses);
        if (!erasure.equals(ClassDesc.of("java.lang", "Object"))
                && (superclasses.isEmpty() || !superclasses.getLast().equals(ClassDesc.of("java.lang", "Object")))) {
            throw new IllegalArgumentException(
                    "the superclass chain of " + erasure.displayName() + " must end in java.lang.Object");
        }
    }

    /// Whether the thrown type is a type variable rather than a class.
    ///
    /// @return `true` for a type variable
    public boolean isVariable() {
        return variable.isPresent();
    }
}
