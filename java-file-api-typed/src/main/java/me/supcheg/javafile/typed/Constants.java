package me.supcheg.javafile.typed;

import me.supcheg.javafile.Invariants;
import me.supcheg.javafile.code.BooleanLiteral;
import me.supcheg.javafile.code.DoubleLiteral;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LongLiteral;
import me.supcheg.javafile.code.StringLiteral;

import java.lang.constant.ConstantDescs;
import java.util.function.BiFunction;

/// Constant folding of JLS 15.29, for reachability (JLS 14.22) and for what javac warns of: a loop
/// condition that is a constant expression changes whether the loop can complete normally, and javac's
/// verdict must be reproduced exactly — folding less than javac renders statements javac finds
/// unreachable —, and a divisor that is a constant zero is one javac warns of (`[divzero]`).
///
/// A constant expression is built from literals, constant variables
/// ([me.supcheg.javafile.facts.StaticFieldRef#constantValue()]), casts to a
/// primitive type or `String`, and the unary, binary, and conditional
/// operators, over operands that are all constant; one that would complete
/// abruptly (an integer division by zero) is not constant.
///
/// What is folded is what the typed layer has an expression for
/// ([Node.Operator], [Node.Conversion]), and all of it: an operator or a kind of node added to the
/// layer does not compile here until it is folded, or said to be no constant.
final class Constants {
    private Constants() {}

    /// What an expression folds to: the value of a constant expression of its type, or that it is none.
    sealed interface Folded {
        /// Not a constant expression (JLS 15.29).
        record None() implements Folded {}

        /// An `int` constant.
        record OfInt(int value) implements Folded {}

        /// A `long` constant.
        record OfLong(long value) implements Folded {}

        /// A `double` constant.
        record OfDouble(double value) implements Folded {}

        /// A `boolean` constant.
        record OfBoolean(boolean value) implements Folded {}

        /// A `String` constant.
        record OfString(String value) implements Folded {}
    }

    private static final Folded NONE = new Folded.None();

    /// Whether `node` is a constant expression with the value `value`.
    static boolean isConstant(Node node, boolean value) {
        return fold(node) instanceof Folded.OfBoolean folded && folded.value() == value;
    }

    /// Whether `node` is a constant expression of the value zero: a divisor javac warns of.
    static boolean isZero(Node node) {
        return fold(node) instanceof Folded.OfInt folded && folded.value() == 0;
    }

    static Folded fold(Node node) {
        return switch (node) {
            case Node.Lit(var literal) ->
                switch (literal) {
                    case IntLiteral value -> new Folded.OfInt(value.value());
                    case LongLiteral value -> new Folded.OfLong(value.value());
                    case DoubleLiteral value -> new Folded.OfDouble(value.value());
                    case BooleanLiteral value -> new Folded.OfBoolean(value.value());
                    case StringLiteral value -> new Folded.OfString(value.value());
                };
            case Node.StaticFieldGet(var field) ->
                field.constantValue().map(Constants::ofValue).orElse(NONE);
            case Node.Not(var operand) ->
                fold(operand) instanceof Folded.OfBoolean value ? new Folded.OfBoolean(!value.value()) : NONE;
            case Node.Binary(var op, var left, var right) -> binary(op, fold(left), fold(right));
            case Node.Cond(var condition, var whenTrue, var whenFalse) ->
                cond(fold(condition), fold(whenTrue), fold(whenFalse));
            case Node.Convert(var conversion, var operand) -> converted(conversion, fold(operand));
            // A cast to `String` alone is one of a constant expression, and of a `String`.
            case Node.Cast(var type, var operand) ->
                type.erasure().equals(ConstantDescs.CD_String) ? fold(operand.node()) : NONE;
            // None of these is a constant expression (JLS 15.29): a name of a local variable is one of
            // a constant variable alone, which the typed layer declares none of, and a call, an
            // instance creation, an access of an array or of an instance field, a lambda and a
            // `switch` never are; nor is `null`.
            case Node.Null _,
                    Node.Local _,
                    Node.This _,
                    Node.Box _,
                    Node.Unbox _,
                    Node.Call _,
                    Node.SuperCall _,
                    Node.StaticCall _,
                    Node.New _,
                    Node.FieldGet _,
                    Node.EnumConst _,
                    Node.ArrayAt _,
                    Node.ArrayClone _,
                    Node.ArrayLength _,
                    Node.NewArray _,
                    Node.NewArrayOf _,
                    Node.Upcast _,
                    Node.Lambda _,
                    Node.Switch _,
                    Node.Assign _,
                    Node.Raw _ -> NONE;
        };
    }

    /// The value of a constant variable, which is of the type of the field: a box of a primitive or a `String`.
    private static Folded ofValue(Object value) {
        return switch (value) {
            case Integer v -> new Folded.OfInt(v);
            case Long v -> new Folded.OfLong(v);
            case Double v -> new Folded.OfDouble(v);
            case Boolean v -> new Folded.OfBoolean(v);
            case String v -> new Folded.OfString(v);
            // A byte, a short, a char or a float: of a type the typed layer has no operator of.
            default -> NONE;
        };
    }

    private static Folded converted(Node.Conversion conversion, Folded operand) {
        return switch (conversion) {
            case INT_TO_LONG -> operand instanceof Folded.OfInt value ? new Folded.OfLong(value.value()) : NONE;
            case INT_TO_DOUBLE -> operand instanceof Folded.OfInt value ? new Folded.OfDouble(value.value()) : NONE;
            case LONG_TO_DOUBLE -> operand instanceof Folded.OfLong value ? new Folded.OfDouble(value.value()) : NONE;
            case LONG_TO_INT -> operand instanceof Folded.OfLong value ? new Folded.OfInt((int) value.value()) : NONE;
            case DOUBLE_TO_INT ->
                operand instanceof Folded.OfDouble value ? new Folded.OfInt((int) value.value()) : NONE;
        };
    }

    /// A conditional is constant where its three operands are, not its condition and the chosen one alone.
    private static Folded cond(Folded condition, Folded whenTrue, Folded whenFalse) {
        return condition instanceof Folded.OfBoolean chosen
                        && !(whenTrue instanceof Folded.None)
                        && !(whenFalse instanceof Folded.None)
                ? (chosen.value() ? whenTrue : whenFalse)
                : NONE;
    }

    /// An operator over constant operands, each of the type the operator is of. An integer division by
    /// zero would complete abruptly, and is no constant; the typed layer writes none ([Expressions]).
    private static Folded binary(Node.Operator op, Folded left, Folded right) {
        return switch (op) {
            case ADD_INT -> ints(left, right, (l, r) -> new Folded.OfInt(l + r));
            case SUB_INT -> ints(left, right, (l, r) -> new Folded.OfInt(l - r));
            case MUL_INT -> ints(left, right, (l, r) -> new Folded.OfInt(l * r));
            case DIV_INT -> ints(left, right, (l, r) -> new Folded.OfInt(l / nonZero(r)));
            case MOD_INT -> ints(left, right, (l, r) -> new Folded.OfInt(l % nonZero(r)));
            case EQ_INT -> ints(left, right, (l, r) -> new Folded.OfBoolean(l.intValue() == r));
            case NEQ_INT -> ints(left, right, (l, r) -> new Folded.OfBoolean(l.intValue() != r));
            case LT_INT -> ints(left, right, (l, r) -> new Folded.OfBoolean(l < r));
            case LE_INT -> ints(left, right, (l, r) -> new Folded.OfBoolean(l <= r));
            case GT_INT -> ints(left, right, (l, r) -> new Folded.OfBoolean(l > r));
            case GE_INT -> ints(left, right, (l, r) -> new Folded.OfBoolean(l >= r));
            case ADD_LONG -> longs(left, right, (l, r) -> new Folded.OfLong(l + r));
            case MUL_LONG -> longs(left, right, (l, r) -> new Folded.OfLong(l * r));
            case ADD_DOUBLE -> doubles(left, right, (l, r) -> new Folded.OfDouble(l + r));
            case MUL_DOUBLE -> doubles(left, right, (l, r) -> new Folded.OfDouble(l * r));
            case AND -> booleans(left, right, (l, r) -> new Folded.OfBoolean(l && r));
            case OR -> booleans(left, right, (l, r) -> new Folded.OfBoolean(l || r));
            // String constants are interned, so == compares their contents.
            case EQ_REF -> strings(left, right, (l, r) -> new Folded.OfBoolean(l.equals(r)));
            case NEQ_REF -> strings(left, right, (l, r) -> new Folded.OfBoolean(!l.equals(r)));
            case CONCAT -> strings(left, right, (l, r) -> new Folded.OfString(l + r));
        };
    }

    /// A divisor, which [Expressions#divInt] and [Expressions#modInt] refuse to be a constant zero.
    private static int nonZero(int divisor) {
        Invariants.require(divisor != 0, "a constant divisor is no zero: divInt and modInt refuse one");
        return divisor;
    }

    private static Folded ints(Folded left, Folded right, BiFunction<Integer, Integer, Folded> operator) {
        return left instanceof Folded.OfInt l && right instanceof Folded.OfInt r
                ? operator.apply(l.value(), r.value())
                : NONE;
    }

    private static Folded longs(Folded left, Folded right, BiFunction<Long, Long, Folded> operator) {
        return left instanceof Folded.OfLong l && right instanceof Folded.OfLong r
                ? operator.apply(l.value(), r.value())
                : NONE;
    }

    private static Folded doubles(Folded left, Folded right, BiFunction<Double, Double, Folded> operator) {
        return left instanceof Folded.OfDouble l && right instanceof Folded.OfDouble r
                ? operator.apply(l.value(), r.value())
                : NONE;
    }

    private static Folded booleans(Folded left, Folded right, BiFunction<Boolean, Boolean, Folded> operator) {
        return left instanceof Folded.OfBoolean l && right instanceof Folded.OfBoolean r
                ? operator.apply(l.value(), r.value())
                : NONE;
    }

    private static Folded strings(Folded left, Folded right, BiFunction<String, String, Folded> operator) {
        return left instanceof Folded.OfString l && right instanceof Folded.OfString r
                ? operator.apply(l.value(), r.value())
                : NONE;
    }
}
