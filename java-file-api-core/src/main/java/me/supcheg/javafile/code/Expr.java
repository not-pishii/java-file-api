package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// An expression: a literal, a variable, a call, an operator, a lambda, and so on.
///
/// Start an expression with a static method of [Exprs], then continue it with
/// the methods of this interface, which read left to right like Java:
///
/// ```java
/// this_().field("items").call("get", literal(0))   // this.items.get(0)
/// field("value").instanceOf(Types.STRING, "s")      // value instanceof String s
/// ```
///
/// To use an expression as a statement, pass it to
/// [CodeBuilder#exprStatement(StatementExpr)].
public sealed interface Expr
        permits FieldAccessExpr,
                StaticFieldAccessExpr,
                MethodCallExpr,
                StaticMethodCallExpr,
                LiteralExpr,
                TextBlockExpr,
                BinaryExpr,
                UnaryExpr,
                IncDecExpr,
                InstanceOfExpr,
                NewExpr,
                SwitchExpr,
                LambdaExpr,
                StatementExpr,
                ConstantExpr,
                ThisExpr,
                SuperExpr,
                CastExpr,
                ConditionalExpr,
                ClassLiteralExpr,
                MethodRefExpr,
                ConstructorRefExpr,
                ArrayAccessExpr,
                ArrayCreationExpr,
                ArrayInitializerExpr {

    /// Creates a field access qualified by this expression, e.g. `this.name`.
    ///
    /// @param name the field name
    /// @return a field access expression
    default FieldAccessExpr field(String name) {
        return new FieldAccessExpr(Optional.of(this), name);
    }

    /// Creates a method call on this expression, e.g. `this.method(args)`.
    ///
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    default MethodCallExpr call(String method, Expr... args) {
        return new MethodCallExpr(Optional.of(this), method, List.of(args));
    }

    /// Creates a method call on this expression, e.g. `this.method(args)`.
    ///
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    default MethodCallExpr call(String method, List<Expr> args) {
        return new MethodCallExpr(Optional.of(this), method, List.copyOf(args));
    }

    /// Creates an array access on this expression, e.g. `array[index]`.
    ///
    /// @param index the index expression
    /// @return an array access expression
    default ArrayAccessExpr arrayAccess(Expr index) {
        return new ArrayAccessExpr(this, index);
    }

    /// Creates an `instanceof` test with no pattern binding, e.g. `expr instanceof Type`.
    ///
    /// @param type the tested type
    /// @return an `instanceof` expression
    default InstanceOfExpr instanceOf(TypeRef type) {
        return new InstanceOfExpr(this, new TypePattern(type, Optional.empty()));
    }

    /// Creates an `instanceof` pattern match binding the matched value to a name,
    /// e.g. `expr instanceof Type name`.
    ///
    /// @param type the tested type
    /// @param bindingName the name bound to the matched value
    /// @return an `instanceof` expression
    default InstanceOfExpr instanceOf(TypeRef type, String bindingName) {
        return new InstanceOfExpr(this, new TypePattern(type, Optional.of(bindingName)));
    }

    /// Creates an `instanceof` test against an arbitrary pattern, e.g. a record
    /// deconstruction.
    ///
    /// @param pattern the matched pattern
    /// @return an `instanceof` expression
    default InstanceOfExpr instanceOfPattern(Pattern pattern) {
        return new InstanceOfExpr(this, pattern);
    }

    /// Creates a method reference bound to this expression, e.g. `expr::method`.
    ///
    /// @param method the referenced method name
    /// @return a method reference expression
    default MethodRefExpr methodRef(String method) {
        return new MethodRefExpr(new ExprMethodRefTarget(this), method);
    }
}
