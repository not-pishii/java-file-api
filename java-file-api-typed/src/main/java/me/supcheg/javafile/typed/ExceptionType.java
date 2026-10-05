package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

/// The type of an exception, as [Exceptions] reasons about it: a class, or
/// a type variable bounded by one. A token of anything else — an interface,
/// an array, a primitive — is no exception type, and is turned away where
/// it enters ([#of(TypeToken)]), so nothing further in has a case for it.
sealed interface ExceptionType {
    ClassDesc CD_RUNTIME_EXCEPTION = ClassDesc.of("java.lang.RuntimeException");
    ClassDesc CD_ERROR = ClassDesc.of("java.lang.Error");

    /// The token of the type.
    ///
    /// @return the token
    RefToken<?> token();

    /// Whether the type is a checked exception type (JLS 11.1.1).
    ///
    /// @return `true` for a checked exception type
    boolean isChecked();

    /// Whether a `catch` or a `throws` of this type covers `thrown`:
    /// `thrown` is this type or a subtype of it.
    ///
    /// @param thrown the type of an exception that is thrown
    /// @return `true` if this type covers it
    boolean covers(ExceptionType thrown);

    /// An exception class.
    ///
    /// @param token its token, which knows its superclasses
    record OfClass(ClassToken<?> token) implements ExceptionType {
        @Override
        public boolean isChecked() {
            return token.isCheckedException();
        }

        @Override
        public boolean covers(ExceptionType thrown) {
            return switch (thrown) {
                case OfClass(var cls) -> cls.isSubclassOf(token.erasure());
                // A type variable is a subtype of its bound, and of nothing else that is known.
                case OfVariable(var variable) ->
                    token.erasure().equals(variable.erasure())
                            || token.erasure().equals(ConstantDescs.CD_Throwable);
            };
        }

        @Override
        public String toString() {
            return token.toString();
        }
    }

    /// A type variable in the place of an exception: `<X extends
    /// Throwable> ... throws X`, with a type variable given for `X`. It is
    /// known by the class that is its bound alone: checked unless the bound
    /// is `RuntimeException` or `Error` itself, and covering nothing but
    /// itself.
    ///
    /// @param token its token
    record OfVariable(TypeVarToken<?> token) implements ExceptionType {
        @Override
        public boolean isChecked() {
            return !token.erasure().equals(CD_RUNTIME_EXCEPTION)
                    && !token.erasure().equals(CD_ERROR);
        }

        @Override
        public boolean covers(ExceptionType thrown) {
            return switch (thrown) {
                case OfClass _ -> false;
                case OfVariable(var variable) -> Tokens.sameType(token, variable);
            };
        }

        @Override
        public String toString() {
            return token.toString();
        }
    }

    /// The exception type `type` is the token of.
    ///
    /// @param type the token of the static type of a thrown expression, or of a `throws` clause
    /// @return the exception type
    /// @throws IllegalArgumentException if `type` is no class and no type variable: only untyped code of
    ///     `Unsafe` or a fact vouched for by hand gives such a token for a `Throwable`
    static ExceptionType of(TypeToken<?> type) {
        return switch (type) {
            case ClassToken<?> cls -> new OfClass(cls);
            case TypeVarToken<?> variable -> new OfVariable(variable);
            case InterfaceToken<?> _, ArrayToken<?, ?> _, PrimitiveToken<?, ?, ?> _ ->
                throw new IllegalArgumentException("not an exception type: " + type
                        + " is neither a class nor a type variable, and only those extend Throwable");
        };
    }

    /// The exception types of a `throws` clause.
    ///
    /// @param types the tokens of the clause
    /// @return the exception types, in order
    static List<ExceptionType> ofAll(List<? extends RefToken<? extends Throwable>> types) {
        return types.stream().map(ExceptionType::of).toList();
    }
}
