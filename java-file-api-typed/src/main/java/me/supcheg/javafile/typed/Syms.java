package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.FieldAccessExpr;
import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.NewExpr;
import me.supcheg.javafile.code.StaticFieldAccessExpr;
import me.supcheg.javafile.code.StaticMethodCallExpr;

import java.util.List;
import java.util.Optional;

/// Creates core expressions from proven symbols, checking arity and static-ness.
///
/// ```java
/// import static me.supcheg.javafile.typed.Syms.*;
///
/// call(param, charAt, Exprs.literal(0))      // s.charAt(0)
/// ```
///
/// The target's static type is not checked.
public final class Syms {
    private Syms() {}

    /// Calls an instance method on `target`, e.g. `target.m(args)`.
    ///
    /// @param target the receiver
    /// @param method the instance method
    /// @param args the arguments, one per parameter
    /// @return the call expression
    /// @throws IllegalArgumentException if `method` is static or the argument count differs
    public static MethodCallExpr call(Expr target, MethodSym method, Expr... args) {
        requireStatic(method.isStatic(), false, method);
        return new MethodCallExpr(Optional.of(target), method.name(), args(method, args));
    }

    /// Calls a method without a qualifier, e.g. `m(args)` from within its type.
    ///
    /// @param method the method
    /// @param args the arguments, one per parameter
    /// @return the call expression
    /// @throws IllegalArgumentException if the argument count differs
    public static MethodCallExpr unqualified(MethodSym method, Expr... args) {
        return new MethodCallExpr(Optional.empty(), method.name(), args(method, args));
    }

    /// Calls a static method, e.g. `Owner.m(args)`.
    ///
    /// @param method the static method
    /// @param args the arguments, one per parameter
    /// @return the call expression
    /// @throws IllegalArgumentException if `method` is not static or the argument count differs
    public static StaticMethodCallExpr staticCall(MethodSym method, Expr... args) {
        requireStatic(method.isStatic(), true, method);
        return Exprs.staticCall(method.owner().desc(), method.name(), args(method, args));
    }

    /// Reads an instance field of `target`, e.g. `target.f`.
    ///
    /// @param target the receiver
    /// @param field the instance field
    /// @return the field access expression
    /// @throws IllegalArgumentException if `field` is static
    public static FieldAccessExpr field(Expr target, FieldSym field) {
        requireStatic(field.isStatic(), false, field);
        return target.field(field.name());
    }

    /// Reads a static field, e.g. `Owner.f`.
    ///
    /// @param field the static field
    /// @return the field access expression
    /// @throws IllegalArgumentException if `field` is not static
    public static StaticFieldAccessExpr staticField(FieldSym field) {
        requireStatic(field.isStatic(), true, field);
        return Exprs.staticField(field.owner().desc(), field.name());
    }

    /// Creates an object, e.g. `new Owner(args)`.
    ///
    /// @param ctor the constructor
    /// @param args the arguments, one per parameter
    /// @return the object creation expression
    /// @throws IllegalArgumentException if the argument count differs
    public static NewExpr new_(CtorSym ctor, Expr... args) {
        requireArity(ctor.type().parameterCount(), args, ctor);
        return Exprs.new_(ctor.owner().desc(), args);
    }

    private static List<Expr> args(MethodSym method, Expr[] args) {
        requireArity(method.type().parameterCount(), args, method);
        return List.of(args);
    }

    private static void requireArity(int expected, Expr[] args, Object symbol) {
        if (args.length != expected) {
            throw new IllegalArgumentException(symbol + " takes " + expected + " argument(s), got " + args.length);
        }
    }

    private static void requireStatic(boolean actual, boolean expected, Object symbol) {
        if (actual != expected) {
            throw new IllegalArgumentException(symbol + (actual ? " is static" : " is not static"));
        }
    }
}
