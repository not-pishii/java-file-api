package me.supcheg.javafile.render;

import me.supcheg.javafile.code.ArrayAccessExpr;
import me.supcheg.javafile.code.ArrayCreationExpr;
import me.supcheg.javafile.code.ArrayInitializerExpr;
import me.supcheg.javafile.code.BinaryExpr;
import me.supcheg.javafile.code.BinaryOp;
import me.supcheg.javafile.code.BooleanLiteral;
import me.supcheg.javafile.code.CastExpr;
import me.supcheg.javafile.code.ClassLiteralExpr;
import me.supcheg.javafile.code.ConditionalExpr;
import me.supcheg.javafile.code.ConstructorRefExpr;
import me.supcheg.javafile.code.DoubleLiteral;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.FieldAccessExpr;
import me.supcheg.javafile.code.IncDecExpr;
import me.supcheg.javafile.code.IncDecOp;
import me.supcheg.javafile.code.InstanceOfExpr;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LambdaExpr;
import me.supcheg.javafile.code.LongLiteral;
import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.MethodRefExpr;
import me.supcheg.javafile.code.NewExpr;
import me.supcheg.javafile.code.NullLiteral;
import me.supcheg.javafile.code.StaticFieldAccessExpr;
import me.supcheg.javafile.code.StaticMethodCallExpr;
import me.supcheg.javafile.code.StringLiteral;
import me.supcheg.javafile.code.SuperExpr;
import me.supcheg.javafile.code.SwitchExpr;
import me.supcheg.javafile.code.TextBlockExpr;
import me.supcheg.javafile.code.ThisExpr;
import me.supcheg.javafile.code.UnaryExpr;
import me.supcheg.javafile.code.UnaryOp;

/// Operator-precedence levels used by [ExprRenderer] to decide when an
/// operand needs parentheses. Higher numbers bind tighter, per JLS 15.
final class Precedence {

    static final int LAMBDA_LEVEL = 1;
    static final int TERNARY_LEVEL = 2;
    static final int LOGICAL_OR_LEVEL = 3;
    static final int LOGICAL_AND_LEVEL = 4;
    static final int BITWISE_OR_LEVEL = 5;
    static final int BITWISE_XOR_LEVEL = 6;
    static final int BITWISE_AND_LEVEL = 7;
    static final int EQUALITY_LEVEL = 8;
    static final int RELATIONAL_LEVEL = 9;
    static final int SHIFT_LEVEL = 10;
    static final int ADDITIVE_LEVEL = 11;
    static final int MULTIPLICATIVE_LEVEL = 12;
    static final int UNARY_LEVEL = 13;
    static final int INC_DEC_LEVEL = 14;
    static final int PRIMARY_LEVEL = 15;

    /// Below [#LAMBDA_LEVEL]; passing this as `minPrecedence` never wraps its operand in parentheses.
    static final int NO_PARENS_REQUIRED = 0;

    private Precedence() {}

    static int level(Expr expr) {
        return switch (expr) {
            case BinaryExpr(var ignoredLeft, var op, var ignoredRight) -> binaryLevel(op);
            case UnaryExpr ignored -> UNARY_LEVEL;
            case CastExpr ignored -> UNARY_LEVEL;
            case IncDecExpr ignored -> INC_DEC_LEVEL;
            case InstanceOfExpr ignored -> RELATIONAL_LEVEL;
            case ConditionalExpr ignored -> TERNARY_LEVEL;
            case LambdaExpr ignored -> LAMBDA_LEVEL;
            case FieldAccessExpr ignored -> PRIMARY_LEVEL;
            case StaticFieldAccessExpr ignored -> PRIMARY_LEVEL;
            case MethodCallExpr ignored -> PRIMARY_LEVEL;
            case StaticMethodCallExpr ignored -> PRIMARY_LEVEL;
            case StringLiteral ignored -> PRIMARY_LEVEL;
            case IntLiteral ignored -> PRIMARY_LEVEL;
            case LongLiteral ignored -> PRIMARY_LEVEL;
            case DoubleLiteral ignored -> PRIMARY_LEVEL;
            case BooleanLiteral ignored -> PRIMARY_LEVEL;
            case NullLiteral ignored -> PRIMARY_LEVEL;
            case TextBlockExpr ignored -> PRIMARY_LEVEL;
            case NewExpr ignored -> PRIMARY_LEVEL;
            case SwitchExpr ignored -> PRIMARY_LEVEL;
            case ThisExpr ignored -> PRIMARY_LEVEL;
            case SuperExpr ignored -> PRIMARY_LEVEL;
            case ClassLiteralExpr ignored -> PRIMARY_LEVEL;
            case MethodRefExpr ignored -> PRIMARY_LEVEL;
            case ConstructorRefExpr ignored -> PRIMARY_LEVEL;
            case ArrayAccessExpr ignored -> PRIMARY_LEVEL;
            case ArrayCreationExpr ignored -> PRIMARY_LEVEL;
            case ArrayInitializerExpr ignored -> PRIMARY_LEVEL;
        };
    }

    private static int binaryLevel(BinaryOp op) {
        return switch (op) {
            case OR -> LOGICAL_OR_LEVEL;
            case AND -> LOGICAL_AND_LEVEL;
            case BIT_OR -> BITWISE_OR_LEVEL;
            case BIT_XOR -> BITWISE_XOR_LEVEL;
            case BIT_AND -> BITWISE_AND_LEVEL;
            case EQ, NEQ -> EQUALITY_LEVEL;
            case LT, LE, GT, GE -> RELATIONAL_LEVEL;
            case SHL, SHR, USHR -> SHIFT_LEVEL;
            case ADD, SUB -> ADDITIVE_LEVEL;
            case MUL, DIV, MOD -> MULTIPLICATIVE_LEVEL;
        };
    }

    /// Whether concatenating `operatorSymbol` directly before `renderedOperand`
    /// would let the lexer's maximal-munch rule glue them into a different
    /// token (e.g. `-` followed by `-x` becoming `--x`, decrement instead of
    /// double negation). Only `+`/`-` adjacency is ambiguous in Java; `~~x`
    /// and `!!x` are not real tokens, so they are left unparenthesized to
    /// keep the minimal-parentheses policy.
    static boolean needsParensAroundUnaryOperand(String operatorSymbol, String renderedOperand) {
        if (renderedOperand.isEmpty()) {
            return false;
        }
        char operatorLastChar = operatorSymbol.charAt(operatorSymbol.length() - 1);
        char operandFirstChar = renderedOperand.charAt(0);
        return (operatorLastChar == '+' || operatorLastChar == '-') && operandFirstChar == operatorLastChar;
    }

    /// Whether `expr`, as a reference-type cast's operand, needs parentheses.
    /// JLS 15.16 restricts a reference-type cast's operand to
    /// `UnaryExpressionNotPlusMinus`, which excludes unary `+`/`-` and
    /// pre-increment/decrement — unlike a primitive-type cast's operand
    /// (`UnaryExpression`), which allows them directly.
    static boolean needsParensAsReferenceTypeCastOperand(Expr expr) {
        return switch (expr) {
            case UnaryExpr(var op, var ignored) -> op == UnaryOp.NEG || op == UnaryOp.UNARY_PLUS;
            case IncDecExpr(var op, var ignored) -> op == IncDecOp.PRE_INC || op == IncDecOp.PRE_DEC;
            default -> false;
        };
    }

    /// Whether `expr`, as an array-access expression's array operand, needs
    /// parentheses. JLS 15.10.3 restricts the array operand to
    /// `ExpressionName | PrimaryNoNewArray`, which excludes array-creation
    /// expressions — `new int[3][0]` parses as a 2D array creation, not as
    /// indexing into `new int[3]`.
    static boolean needsParensAsArrayAccessTarget(Expr expr) {
        return expr instanceof ArrayCreationExpr || expr instanceof ArrayInitializerExpr;
    }
}
