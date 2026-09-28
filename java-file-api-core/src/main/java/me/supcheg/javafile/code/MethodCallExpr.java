package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// A method call, e.g. `target.method(args)`, `target.<T>method(args)` with
/// explicit type arguments, or, when unqualified, `method(args)`.
///
/// @param target the expression owning the method, or empty for an unqualified call
/// @param method the method name
/// @param args the call arguments, in order
/// @param typeArgs the explicit type arguments of a generic method, in order; empty to let
///                 the compiler infer them. Java allows them only on a qualified call and
///                 only as reference types
public record MethodCallExpr(Optional<Expr> target, String method, List<Expr> args, List<TypeRef> typeArgs)
        implements Expr, StatementExpr {
    public MethodCallExpr {
        method = Identifiers.requireValid(method);
        args = List.copyOf(args);
        typeArgs = TypeArguments.requireValid(List.copyOf(typeArgs));
        if (target.isEmpty() && !typeArgs.isEmpty()) {
            throw new IllegalArgumentException("explicit type arguments require a qualified call: " + method);
        }
    }

    /// Creates a call without explicit type arguments.
    ///
    /// @param target the expression owning the method, or empty for an unqualified call
    /// @param method the method name
    /// @param args the call arguments, in order
    public MethodCallExpr(Optional<Expr> target, String method, List<Expr> args) {
        this(target, method, args, List.of());
    }

    /// Validates explicit method type arguments.
    static final class TypeArguments {
        private TypeArguments() {}

        static List<TypeRef> requireValid(List<TypeRef> typeArgs) {
            for (TypeRef typeArg : typeArgs) {
                if (typeArg instanceof PrimitiveTypeRef primitive) {
                    throw new IllegalArgumentException(
                            "a type argument must be a reference type, got: " + primitive.sourceName());
                }
            }
            return typeArgs;
        }
    }
}
