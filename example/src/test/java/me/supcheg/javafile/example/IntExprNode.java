package me.supcheg.javafile.example;

import me.supcheg.javafile.code.Expr;

import static me.supcheg.javafile.code.Exprs.add;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.mul;
import static me.supcheg.javafile.code.Exprs.neg;
import static me.supcheg.javafile.code.Exprs.sub;

/// A tiny arithmetic expression tree used only to property-test
/// {@code ExprRenderer}'s parenthesization: it can both evaluate itself in
/// plain Java and render itself through {@link me.supcheg.javafile.code.Exprs},
/// so the two results can be compared after compiling and running the
/// rendered form.
sealed interface IntExprNode {

    int evaluate();

    Expr toExpr();

    record Lit(int value) implements IntExprNode {
        @Override
        public int evaluate() {
            return value;
        }

        @Override
        public Expr toExpr() {
            return literal(value);
        }
    }

    record Neg(IntExprNode operand) implements IntExprNode {
        @Override
        public int evaluate() {
            return -operand.evaluate();
        }

        @Override
        public Expr toExpr() {
            return neg(operand.toExpr());
        }
    }

    record Bin(IntExprNode left, char op, IntExprNode right) implements IntExprNode {
        @Override
        public int evaluate() {
            int l = left.evaluate();
            int r = right.evaluate();
            return switch (op) {
                case '+' -> l + r;
                case '-' -> l - r;
                case '*' -> l * r;
                default -> throw new IllegalStateException("Unknown op: " + op);
            };
        }

        @Override
        public Expr toExpr() {
            Expr l = left.toExpr();
            Expr r = right.toExpr();
            return switch (op) {
                case '+' -> add(l, r);
                case '-' -> sub(l, r);
                case '*' -> mul(l, r);
                default -> throw new IllegalStateException("Unknown op: " + op);
            };
        }
    }
}
