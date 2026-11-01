package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;

/// The signature check of a constructor reference (6.4): the constructor the fact names must be one a
/// constructor reference of the functional interface can name — of the class written, which can be
/// instantiated — and its parameters and the class it constructs must be those of the single abstract
/// method, so that javac accepts the rendered `Type::new` without an adaptation the typed layer does
/// not model.
final class ConstructorReferences {
    private ConstructorReferences() {}

    /// Checks `type::new`, which implements the single abstract method `sam` of `samType`.
    ///
    /// @throws IllegalArgumentException if `ctor` is no constructor, `type` is not the class whose
    ///     constructor it is, `type` is an interface or an abstract class, or the parameters or result
    ///     are not those of `sam`
    static void check(DeclaredToken<?> type, Invocable ctor, InterfaceToken<?> samType, Invocable sam) {
        requireConstructor(ctor);
        requireInstantiable(type);
        if (!Tokens.sameType(type, ctor.owner())) {
            throw new IllegalArgumentException("a constructor reference writes " + type + "::new, and " + ctor
                    + " constructs " + ctor.owner() + ": pass the class the constructor belongs to");
        }
        MethodReferences.requireSamType(samType, sam);
        MethodReferences.requireParameters(ctor, sam, "a constructor reference");
        requireResult(type, sam);
    }

    private static void requireConstructor(Invocable ctor) {
        if (ctor.kind() != InvocableKind.CONSTRUCTOR) {
            throw new IllegalArgumentException(
                    "a constructor reference names a constructor, and " + ctor + " is of the kind " + ctor.kind());
        }
    }

    private static void requireInstantiable(DeclaredToken<?> type) {
        if (type instanceof InterfaceToken<?>) {
            throw new IllegalArgumentException("a constructor reference writes " + type
                    + "::new, and an interface is no class to construct: pass the class that implements it");
        }
        if (type instanceof AbstractClassToken<?>) {
            throw new IllegalArgumentException("a constructor reference writes " + type + "::new, and " + type
                    + " is abstract: an abstract class has no instance to construct; pass a concrete subclass");
        }
    }

    private static void requireResult(DeclaredToken<?> type, Invocable sam) {
        boolean same =
                sam.resultType().map(result -> Tokens.sameType(type, result)).orElse(false);
        if (!same) {
            throw new IllegalArgumentException("a constructor reference writes " + type + "::new, and the method of "
                    + sam.owner() + " has the result "
                    + sam.resultType().map(Object::toString).orElse("void") + ", not " + type + " (JLS 15.13.1)");
        }
    }
}
