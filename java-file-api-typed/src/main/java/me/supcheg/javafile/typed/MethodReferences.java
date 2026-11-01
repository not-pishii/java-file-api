package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.TypeToken;

import java.util.List;

/// The signature check of a method reference (6.4): the method the fact names must be one a method
/// reference of the functional interface can name, and its parameters and result must be those of the
/// single abstract method, so that javac accepts the rendered `expr::method` or `Type::method` without
/// an adaptation the typed layer does not model.
final class MethodReferences {
    private MethodReferences() {}

    /// Checks `receiver::method`, which implements the single abstract method `sam` of `samType`.
    ///
    /// @throws IllegalArgumentException if `method` is not an instance method of the receiver's type,
    ///     or its parameters or result are not those of `sam`
    static void bound(TypeToken<?> receiver, Invocable method, InterfaceToken<?> samType, Invocable sam) {
        requireSamType(samType, sam);
        requireInstance(method, "a bound method reference");
        if (!Tokens.isSubtypeOf(receiver, method.owner().erasure())) {
            throw new IllegalArgumentException("a bound method reference calls " + method + " on " + receiver
                    + ", which is no subtype of its owner " + method.owner() + " (JLS 15.13.1)");
        }
        requireParameters(method, sam, "a bound method reference");
        requireResult(method, sam, "a bound method reference");
    }

    /// Checks `type::method`, the unbound instance reference (`Type::instanceMethod`) or the static one
    /// (`Type::staticMethod`), which implements `sam` of `samType`.
    ///
    /// @throws IllegalArgumentException if `method` is a constructor, `type` is not the owner or a subtype
    ///     of it, or the parameters or result are not those of `sam`
    static void onType(DeclaredToken<?> type, Invocable method, InterfaceToken<?> samType, Invocable sam) {
        requireSamType(samType, sam);
        if (method.kind() == InvocableKind.CONSTRUCTOR) {
            throw new IllegalArgumentException(
                    "a method reference names a method, and " + method + " is a constructor: use constructorRef");
        }
        if (method.kind() == InvocableKind.STATIC_METHOD) {
            if (!Tokens.isSubtypeOf(type, method.owner().erasure())) {
                throw new IllegalArgumentException("a static method reference names " + method + " through " + type
                        + ", which is no subtype of its owner " + method.owner() + " (JLS 15.13.1)");
            }
            requireParameters(method, sam, "a static method reference");
            requireResult(method, sam, "a static method reference");
            return;
        }
        if (!Tokens.isSubtypeOf(type, method.owner().erasure())) {
            throw new IllegalArgumentException("an unbound method reference names " + method + " through " + type
                    + ", which is no subtype of its owner " + method.owner() + " (JLS 15.13.1)");
        }
        List<TypeToken<?>> params = sam.params();
        if (params.size() != method.params().size() + 1) {
            throw new IllegalArgumentException("an unbound method reference to " + method + " takes "
                    + (method.params().size() + 1) + " parameters, the receiver first, and " + sam + " has "
                    + params.size() + " (JLS 15.13.1)");
        }
        TypeToken<?> receiver = params.getFirst();
        if (!Tokens.isSubtypeOf(receiver, method.owner().erasure())) {
            throw new IllegalArgumentException("an unbound method reference to " + method + " takes its receiver"
                    + " as a value of a subtype of " + method.owner() + ", and the first parameter of " + sam + " is "
                    + receiver + " (JLS 15.13.1)");
        }
        if (!params.subList(1, params.size()).equals(method.params())) {
            throw new IllegalArgumentException("an unbound method reference to " + method + " passes "
                    + describe(params, 1) + " to its parameters " + describe(method.params(), 0) + ", which " + sam
                    + " does not (JLS 15.13.1)");
        }
        requireResult(method, sam, "an unbound method reference");
    }

    private static void requireSamType(InterfaceToken<?> samType, Invocable sam) {
        if (!Tokens.sameType(samType, sam.owner())) {
            throw new IllegalArgumentException("the method of the functional interface is " + sam + " of "
                    + sam.owner() + ", and the method reference is used as " + samType
                    + ": a method reference is of the interface whose method it implements");
        }
    }

    private static void requireInstance(Invocable method, String form) {
        if (method.kind() != InvocableKind.INSTANCE_METHOD) {
            throw new IllegalArgumentException(
                    form + " calls an instance method, and " + method + " is of the kind " + method.kind());
        }
    }

    private static void requireParameters(Invocable method, Invocable sam, String form) {
        if (!method.params().equals(sam.params())) {
            throw new IllegalArgumentException(form + " to " + method + " passes " + describe(method.params(), 0)
                    + ", and the method of " + sam.owner() + " takes " + describe(sam.params(), 0)
                    + " (JLS 15.13.1)");
        }
    }

    private static void requireResult(Invocable method, Invocable sam, String form) {
        boolean same = method.resultType()
                .map(result -> sam.resultType()
                        .map(other -> Tokens.sameType(result, other))
                        .orElse(false))
                .orElseGet(() -> sam.resultType().isEmpty());
        if (!same) {
            throw new IllegalArgumentException(form + " to " + method + " has the result "
                    + method.resultType().map(Object::toString).orElse("void")
                    + ", and the method of " + sam.owner() + " has "
                    + sam.resultType().map(Object::toString).orElse("void") + " (JLS 15.13.1)");
        }
    }

    private static String describe(List<TypeToken<?>> params, int from) {
        return params.subList(from, params.size()).stream()
                .map(Object::toString)
                .reduce((left, right) -> left + ", " + right)
                .orElse("nothing");
    }
}
