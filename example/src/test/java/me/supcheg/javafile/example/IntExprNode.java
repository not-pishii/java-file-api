package me.supcheg.javafile.example;

import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;

/// A tiny arithmetic expression tree used only to property-test
/// {@code ExprRenderer}'s parenthesization: it can both evaluate itself in
/// plain Java and render itself through {@link CodeBuilder}, so the two
/// results can be compared after compiling and running the rendered form.
sealed interface IntExprNode {

    int evaluate();

    Expr toExpr(CodeBuilder cb);

    record Lit(int value) implements IntExprNode {
        @Override
        public int evaluate() {
            return value;
        }

        @Override
        public Expr toExpr(CodeBuilder cb) {
            return cb.literal(value);
        }
    }

    record Neg(IntExprNode operand) implements IntExprNode {
        @Override
        public int evaluate() {
            return -operand.evaluate();
        }

        @Override
        public Expr toExpr(CodeBuilder cb) {
            return cb.neg(operand.toExpr(cb));
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
        public Expr toExpr(CodeBuilder cb) {
            Expr l = left.toExpr(cb);
            Expr r = right.toExpr(cb);
            return switch (op) {
                case '+' -> cb.add(l, r);
                case '-' -> cb.sub(l, r);
                case '*' -> cb.mul(l, r);
                default -> throw new IllegalStateException("Unknown op: " + op);
            };
        }
    }
}
