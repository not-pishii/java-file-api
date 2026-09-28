package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;

/// A static method call, e.g. `target.method(args)` for `Math.max(a, b)`, or
/// `List.<String>of()` with explicit type arguments.
///
/// @param target the type declaring the method
/// @param method the method name
/// @param args the call arguments, in order
/// @param typeArgs the explicit type arguments of a generic method, in order; empty to let
///                 the compiler infer them. Java allows only reference types
public record StaticMethodCallExpr(
        ClassOrInterfaceTypeRef target, String method, List<Expr> args, List<TypeRef> typeArgs)
        implements Expr, StatementExpr {
    public StaticMethodCallExpr {
        method = Identifiers.requireValid(method);
        args = List.copyOf(args);
        typeArgs = MethodCallExpr.TypeArguments.requireValid(List.copyOf(typeArgs));
    }

    /// Creates a call without explicit type arguments.
    ///
    /// @param target the type declaring the method
    /// @param method the method name
    /// @param args the call arguments, in order
    public StaticMethodCallExpr(ClassOrInterfaceTypeRef target, String method, List<Expr> args) {
        this(target, method, args, List.of());
    }
}
