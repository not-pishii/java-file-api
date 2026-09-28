package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.BinaryOp;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.jdk.String_;
import me.supcheg.javafile.type.TypeRef;
import org.jspecify.annotations.Nullable;

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
final class Constants {
    private Constants() {}

    /// Whether `node` is a constant expression with the value `value`.
    static boolean isConstant(Node node, boolean value) {
        return Boolean.valueOf(value).equals(fold(node));
    }

    private static @Nullable Object fold(Node node) {
        return switch (node) {
            case Node.Lit(var ignored, var value) -> value;
            case Node.StaticFieldGet(var field) -> field.constantValue().orElse(null);
            case Node.Unary(var op, var operand, var ignored) -> unary(op, fold(operand));
            case Node.Binary(var op, var left, var right, var ignored) -> binary(op, fold(left), fold(right));
            case Node.Cond(var condition, var whenTrue, var whenFalse) ->
                cond(fold(condition), fold(whenTrue), fold(whenFalse));
            case Node.Cast(var type, var operand) -> cast(type, fold(operand));
            default -> null;
        };
    }

    private static @Nullable Object cond(
            @Nullable Object condition, @Nullable Object whenTrue, @Nullable Object whenFalse) {
        if (!(condition instanceof Boolean c) || whenTrue == null || whenFalse == null) {
            return null;
        }
        return c ? whenTrue : whenFalse;
    }

    private static @Nullable Object unary(UnaryOp op, @Nullable Object operand) {
        if (operand == null) {
            return null;
        }
        if (operand instanceof Boolean b) {
            return op == UnaryOp.NOT ? !b : null;
        }
        Number n = numeric(operand);
        if (n == null) {
            return null;
        }
        return switch (op) {
            case NOT -> null;
            case UNARY_PLUS ->
                switch (n) {
                    case Double d -> d;
                    case Float f -> f;
                    case Long l -> l;
                    default -> n.intValue();
                };
            case NEG ->
                switch (n) {
                    case Double d -> -d;
                    case Float f -> -f;
                    case Long l -> -l;
                    default -> -n.intValue();
                };
            case BIT_NOT ->
                switch (n) {
                    case Long l -> ~l;
                    case Double ignored -> null;
                    case Float ignored -> null;
                    default -> ~n.intValue();
                };
        };
    }

    private static @Nullable Object binary(BinaryOp op, @Nullable Object left, @Nullable Object right) {
        if (left == null || right == null) {
            return null;
        }
        if (op == BinaryOp.ADD && (left instanceof String || right instanceof String)) {
            return string(left) + string(right);
        }
        if (left instanceof Boolean l && right instanceof Boolean r) {
            return switch (op) {
                case AND, BIT_AND -> l & r;
                case OR, BIT_OR -> l | r;
                case BIT_XOR, NEQ -> l ^ r;
                case EQ -> l.equals(r);
                default -> null;
            };
        }
        if (left instanceof String l && right instanceof String r) {
            // String constants are interned, so == compares their contents.
            return switch (op) {
                case EQ -> l.equals(r);
                case NEQ -> !l.equals(r);
                default -> null;
            };
        }
        Number l = numeric(left);
        Number r = numeric(right);
        if (l == null || r == null) {
            return null;
        }
        return switch (op) {
            case SHL, SHR, USHR -> shift(op, l, r);
            default -> arithmetic(op, l, r);
        };
    }

    private static @Nullable Object shift(BinaryOp op, Number left, Number right) {
        if (left instanceof Double || left instanceof Float || right instanceof Double || right instanceof Float) {
            return null;
        }
        long distance = right.longValue();
        if (left instanceof Long l) {
            return switch (op) {
                case SHL -> l << distance;
                case SHR -> l >> distance;
                default -> l >>> distance;
            };
        }
        int i = left.intValue();
        return switch (op) {
            case SHL -> i << distance;
            case SHR -> i >> distance;
            default -> i >>> distance;
        };
    }

    /// Binary numeric promotion (JLS 5.6), then the operator.
    private static @Nullable Object arithmetic(BinaryOp op, Number left, Number right) {
        if (left instanceof Double || right instanceof Double) {
            double l = left.doubleValue();
            double r = right.doubleValue();
            return switch (op) {
                case ADD -> l + r;
                case SUB -> l - r;
                case MUL -> l * r;
                case DIV -> l / r;
                case MOD -> l % r;
                default -> compare(op, Double.compare(l, r), l == r, Double.isNaN(l) || Double.isNaN(r));
            };
        }
        if (left instanceof Float || right instanceof Float) {
            float l = left.floatValue();
            float r = right.floatValue();
            return switch (op) {
                case ADD -> l + r;
                case SUB -> l - r;
                case MUL -> l * r;
                case DIV -> l / r;
                case MOD -> l % r;
                default -> compare(op, Float.compare(l, r), l == r, Float.isNaN(l) || Float.isNaN(r));
            };
        }
        if (left instanceof Long || right instanceof Long) {
            long l = left.longValue();
            long r = right.longValue();
            return switch (op) {
                case ADD -> l + r;
                case SUB -> l - r;
                case MUL -> l * r;
                case DIV -> r == 0 ? null : l / r;
                case MOD -> r == 0 ? null : l % r;
                case BIT_AND -> l & r;
                case BIT_OR -> l | r;
                case BIT_XOR -> l ^ r;
                default -> compare(op, Long.compare(l, r), l == r, false);
            };
        }
        int l = left.intValue();
        int r = right.intValue();
        return switch (op) {
            case ADD -> l + r;
            case SUB -> l - r;
            case MUL -> l * r;
            case DIV -> r == 0 ? null : l / r;
            case MOD -> r == 0 ? null : l % r;
            case BIT_AND -> l & r;
            case BIT_OR -> l | r;
            case BIT_XOR -> l ^ r;
            default -> compare(op, Integer.compare(l, r), l == r, false);
        };
    }

    /// A numeric comparison; with a NaN operand only `!=` holds (JLS 15.20.1, 15.21.1).
    private static @Nullable Object compare(BinaryOp op, int comparison, boolean equal, boolean nan) {
        return switch (op) {
            case EQ -> equal;
            case NEQ -> !equal;
            case LT -> !nan && comparison < 0;
            case LE -> !nan && comparison <= 0;
            case GT -> !nan && comparison > 0;
            case GE -> !nan && comparison >= 0;
            default -> null;
        };
    }

    private static @Nullable Object cast(TypeRef type, @Nullable Object value) {
        if (value == null) {
            return null;
        }
        if (type.equals(String_.TOKEN.typeRef())) {
            return value instanceof String ? value : null;
        }
        if (type.equals(PrimitiveToken.BOOLEAN.typeRef())) {
            return value instanceof Boolean ? value : null;
        }
        Number n = numeric(value);
        if (n == null) {
            return null;
        }
        if (type.equals(PrimitiveToken.INT.typeRef())) {
            return n.intValue();
        } else if (type.equals(PrimitiveToken.LONG.typeRef())) {
            return n.longValue();
        } else if (type.equals(PrimitiveToken.DOUBLE.typeRef())) {
            return n.doubleValue();
        } else if (type.equals(PrimitiveToken.FLOAT.typeRef())) {
            return n.floatValue();
        } else if (type.equals(PrimitiveToken.SHORT.typeRef())) {
            return n.shortValue();
        } else if (type.equals(PrimitiveToken.BYTE.typeRef())) {
            return n.byteValue();
        } else if (type.equals(PrimitiveToken.CHAR.typeRef())) {
            return (char) n.intValue();
        }
        return null;
    }

    /// A numeric operand, with a `char` promoted to its code (JLS 5.6).
    private static @Nullable Number numeric(Object value) {
        return switch (value) {
            case Character c -> (int) c;
            case Number n -> n;
            default -> null;
        };
    }

    private static String string(Object value) {
        return String.valueOf(value);
    }
}
