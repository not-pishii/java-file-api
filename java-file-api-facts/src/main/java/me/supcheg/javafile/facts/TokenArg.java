package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.Types;

/// A type argument of a token built from a [TypeShape]: a type given by its
/// token, or a wildcard bounded by one — `String`, `? extends T`,
/// `? super T`, `?`.
///
/// The argument is a token rather than a bare [TypeArg] because the token
/// knows the erasure: the [MethodTableTemplate] of the shape is instantiated
/// with the erasures of the arguments, and the erasure of a type variable
/// `T extends Comparable<T>` cannot be read off its name.
public sealed interface TokenArg permits TokenArg.Exact, TokenArg.Extends, TokenArg.Super, TokenArg.Unbounded {

    /// The type argument as written in generated code.
    ///
    /// @return the core type argument
    TypeArg typeArg();

    /// The type argument `type`.
    ///
    /// @param type the type
    /// @return the type argument
    static TokenArg exact(RefToken<?> type) {
        return new Exact(type);
    }

    /// The wildcard `? extends bound`.
    ///
    /// @param bound the upper bound
    /// @return the type argument
    static TokenArg extendsBound(RefToken<?> bound) {
        return new Extends(bound);
    }

    /// The wildcard `? super bound`.
    ///
    /// @param bound the lower bound
    /// @return the type argument
    static TokenArg superBound(RefToken<?> bound) {
        return new Super(bound);
    }

    /// The wildcard `?`.
    ///
    /// @return the type argument
    static TokenArg unbounded() {
        return Unbounded.INSTANCE;
    }

    /// A type as a type argument.
    ///
    /// @param type the type
    record Exact(RefToken<?> type) implements TokenArg {
        @Override
        public TypeArg typeArg() {
            return Types.exact(type.typeRef());
        }
    }

    /// A wildcard with an upper bound.
    ///
    /// @param bound the upper bound
    record Extends(RefToken<?> bound) implements TokenArg {
        @Override
        public TypeArg typeArg() {
            return Types.extendsBound(bound.typeRef());
        }
    }

    /// A wildcard with a lower bound.
    ///
    /// @param bound the lower bound
    record Super(RefToken<?> bound) implements TokenArg {
        @Override
        public TypeArg typeArg() {
            return Types.superBound(bound.typeRef());
        }
    }

    /// The unbounded wildcard.
    final class Unbounded implements TokenArg {
        private static final Unbounded INSTANCE = new Unbounded();

        private Unbounded() {}

        @Override
        public TypeArg typeArg() {
            return Types.unbounded();
        }

        @Override
        public String toString() {
            return "?";
        }
    }
}
