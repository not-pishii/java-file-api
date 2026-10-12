package me.supcheg.javafile.typed;

import org.jspecify.annotations.Nullable;

import java.lang.constant.ConstantDescs;
import java.util.function.BiFunction;

/// Constant folding of JLS 15.29, for reachability (JLS 14.22): a loop
/// condition that is a constant expression changes whether the loop can
/// complete normally, and javac's verdict must be reproduced exactly —
/// folding less than javac renders statements javac finds unreachable.
///
/// A constant expression is built from literals, constant variables
/// ([me.supcheg.javafile.facts.StaticFieldRef#constantValue()]), casts to a
/// primitive type or `String`, and the unary, binary, and conditional
/// operators, over operands that are all constant; one that would complete
/// abruptly (an integer division by zero) is not constant. Values are the
/// boxes of primitives and `String`s.
///
/// What is folded is what the typed layer has an expression for
/// ([Node.Operator], [Node.Conversion]), and all of it: an operator added to
/// the layer does not compile here until it is folded.
final class Constants {
    private Constants() {}

    /// Whether `node` is a constant expression with the value `value`.
    static boolean isConstant(Node node, boolean value) {
        return Boolean.valueOf(value).equals(fold(node));
    }

    private static @Nullable Object fold(Node node) {
        return switch (node) {
            case Node.Lit(var _, var value) -> value;
            case Node.StaticFieldGet(var field) -> field.constantValue().orElse(null);
            case Node.Not(var operand) -> fold(operand) instanceof Boolean value ? !value : null;
            case Node.Binary(var op, var left, var right) -> binary(op, fold(left), fold(right));
            case Node.Cond(var condition, var whenTrue, var whenFalse) ->
                cond(fold(condition), fold(whenTrue), fold(whenFalse));
            case Node.Convert(var conversion, var operand) ->
                fold(operand) instanceof Number number
                        ? switch (conversion) {
                            case TO_INT -> number.intValue();
                            case TO_LONG -> number.longValue();
                            case TO_DOUBLE -> number.doubleValue();
                        }
                        : null;
            // A cast to `String` alone is one of a constant expression, and of a `String`.
            case Node.Cast(var type, var operand) ->
                type.erasure().equals(ConstantDescs.CD_String) ? fold(operand) : null;
            // None of these is a constant expression (JLS 15.29): a call, a lambda and a switch never are.
            case Node.RawLit _,
                    Node.Local _,
                    Node.This _,
                    Node.Upcast _,
                    Node.Box _,
                    Node.Unbox _,
                    Node.Call _,
                    Node.SuperCall _,
                    Node.StaticCall _,
                    Node.New _,
                    Node.FieldGet _,
                    Node.EnumConst _,
                    Node.ArrayAt _,
                    Node.ArrayLength _,
                    Node.NewArray _,
                    Node.Lambda _,
                    Node.Switch _,
                    Node.Assign _,
                    Node.Raw _ -> null;
        };
    }

    /// A conditional is constant where its three operands are, not its condition and the chosen one alone.
    private static @Nullable Object cond(
            @Nullable Object condition, @Nullable Object whenTrue, @Nullable Object whenFalse) {
        return condition instanceof Boolean chosen && whenTrue != null && whenFalse != null
                ? (chosen ? whenTrue : whenFalse)
                : null;
    }

    /// An operator over constant operands, each of the type the operator is of; an integer division
    /// by zero completes abruptly, and is no constant.
    private static @Nullable Object binary(Node.Operator op, @Nullable Object left, @Nullable Object right) {
        return switch (op) {
            case ADD_INT -> both(Integer.class, left, right, (l, r) -> l + r);
            case SUB_INT -> both(Integer.class, left, right, (l, r) -> l - r);
            case MUL_INT -> both(Integer.class, left, right, (l, r) -> l * r);
            case DIV_INT -> both(Integer.class, left, right, (l, r) -> r == 0 ? null : l / r);
            case MOD_INT -> both(Integer.class, left, right, (l, r) -> r == 0 ? null : l % r);
            case EQ_INT -> both(Integer.class, left, right, (l, r) -> l.intValue() == r);
            case NEQ_INT -> both(Integer.class, left, right, (l, r) -> l.intValue() != r);
            case LT_INT -> both(Integer.class, left, right, (l, r) -> l < r);
            case LE_INT -> both(Integer.class, left, right, (l, r) -> l <= r);
            case GT_INT -> both(Integer.class, left, right, (l, r) -> l > r);
            case GE_INT -> both(Integer.class, left, right, (l, r) -> l >= r);
            case ADD_LONG -> both(Long.class, left, right, (l, r) -> l + r);
            case MUL_LONG -> both(Long.class, left, right, (l, r) -> l * r);
            case ADD_DOUBLE -> both(Double.class, left, right, (l, r) -> l + r);
            case MUL_DOUBLE -> both(Double.class, left, right, (l, r) -> l * r);
            case AND -> both(Boolean.class, left, right, (l, r) -> l && r);
            case OR -> both(Boolean.class, left, right, (l, r) -> l || r);
            // String constants are interned, so == compares their contents.
            case EQ_REF -> both(String.class, left, right, String::equals);
            case NEQ_REF -> both(String.class, left, right, (l, r) -> !l.equals(r));
            case CONCAT -> both(String.class, left, right, String::concat);
        };
    }

    /// What `operator` makes of two operands if both are constants, of `type`.
    private static <T> @Nullable Object both(
            Class<T> type, @Nullable Object left, @Nullable Object right, BiFunction<T, T, @Nullable Object> operator) {
        return type.isInstance(left) && type.isInstance(right)
                ? operator.apply(type.cast(left), type.cast(right))
                : null;
    }
}
