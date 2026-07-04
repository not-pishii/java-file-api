package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A mutable builder for a [CodeBody], accumulating statements in call order.
///
/// Each `Expr`-returning method constructs an expression value without
/// recording it; each `Stmt`-adding method (`return_`, `assign`, `if_`, ...)
/// appends to the body being built. [#build()] snapshots the accumulated
/// statements into an immutable [CodeBody], so a builder may be reused after
/// building.
///
/// Implements `Consumer<Stmt>` so that transforms and other producers can
/// feed pre-built statements directly via [#accept(Stmt)].
///
/// Instances are not thread-safe.
public final class CodeBuilder implements Consumer<Stmt> {

    private final List<Stmt> statements = new ArrayList<>();

    /// Appends the given statement to the body being built.
    ///
    /// @param stmt the statement to append
    @Override
    public void accept(Stmt stmt) {
        statements.add(stmt);
    }

    /// Appends a `return` statement with a value.
    ///
    /// @param value the returned expression
    /// @return this builder
    public CodeBuilder return_(Expr value) {
        statements.add(new ReturnStmt(Optional.of(value)));
        return this;
    }

    /// Appends a bare `return` statement with no value.
    ///
    /// @return this builder
    public CodeBuilder return_() {
        statements.add(new ReturnStmt(Optional.empty()));
        return this;
    }

    /// Appends a statement consisting of a bare expression evaluated for its
    /// side effects, e.g. a method call.
    ///
    /// @param expr the expression to evaluate
    /// @return this builder
    public CodeBuilder exprStatement(Expr expr) {
        statements.add(new ExprStmt(expr));
        return this;
    }

    /// Appends an assignment statement.
    ///
    /// @param target the assignment target
    /// @param value the assigned expression
    /// @return this builder
    public CodeBuilder assign(Expr target, Expr value) {
        statements.add(new AssignStmt(target, value));
        return this;
    }

    /// Creates an unqualified field access, e.g. `name`.
    ///
    /// @param name the field name
    /// @return a field access expression
    public Expr field(String name) {
        return new FieldAccessExpr(Optional.empty(), name);
    }

    /// Creates a field access qualified by a target expression, e.g. `target.name`.
    ///
    /// @param target the expression owning the field
    /// @param name the field name
    /// @return a field access expression
    public Expr field(Expr target, String name) {
        return new FieldAccessExpr(Optional.of(target), name);
    }

    /// Creates an unqualified method call, e.g. `method(args)`.
    ///
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    public Expr call(String method, Expr... args) {
        return new MethodCallExpr(Optional.empty(), method, List.of(args));
    }

    /// Creates a method call qualified by a target expression, e.g. `target.method(args)`.
    ///
    /// @param target the expression owning the method
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    public Expr call(Expr target, String method, Expr... args) {
        return new MethodCallExpr(Optional.of(target), method, List.of(args));
    }

    /// Creates a string literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public Expr literal(String value) {
        return new StringLiteral(value);
    }

    /// Creates an `int` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public Expr literal(int value) {
        return new IntLiteral(value);
    }

    /// Creates a `long` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public Expr literal(long value) {
        return new LongLiteral(value);
    }

    /// Creates a `double` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public Expr literal(double value) {
        return new DoubleLiteral(value);
    }

    /// Creates a `boolean` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public Expr literal(boolean value) {
        return new BooleanLiteral(value);
    }

    /// Creates the `null` literal.
    ///
    /// @return a literal expression
    public Expr literalNull() {
        return new NullLiteral();
    }

    /// Creates a text block literal, rendered with `"""` delimiters.
    ///
    /// @param value the text block content
    /// @return an expression
    public Expr textBlock(String value) {
        return new TextBlockExpr(value);
    }

    /// Creates an addition expression, `left + right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr add(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.ADD, right);
    }

    /// Creates a subtraction expression, `left - right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr sub(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.SUB, right);
    }

    /// Creates a multiplication expression, `left * right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr mul(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.MUL, right);
    }

    /// Creates a division expression, `left / right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr div(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.DIV, right);
    }

    /// Creates a modulo expression, `left % right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr mod(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.MOD, right);
    }

    /// Creates an equality expression, `left == right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr eq(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.EQ, right);
    }

    /// Creates an inequality expression, `left != right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr neq(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.NEQ, right);
    }

    /// Creates a less-than expression, `left < right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr lt(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.LT, right);
    }

    /// Creates a less-than-or-equal expression, `left <= right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr le(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.LE, right);
    }

    /// Creates a greater-than expression, `left > right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr gt(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.GT, right);
    }

    /// Creates a greater-than-or-equal expression, `left >= right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr ge(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.GE, right);
    }

    /// Creates a logical AND expression, `left && right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr and(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.AND, right);
    }

    /// Creates a logical OR expression, `left || right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr or(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.OR, right);
    }

    /// Creates a logical negation expression, `!operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr not(Expr operand) {
        return new UnaryExpr(UnaryOp.NOT, operand);
    }

    /// Creates an arithmetic negation expression, `-operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr neg(Expr operand) {
        return new UnaryExpr(UnaryOp.NEG, operand);
    }

    /// Creates a pre-increment expression, `++operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr preIncrement(Expr operand) {
        return new UnaryExpr(UnaryOp.PRE_INC, operand);
    }

    /// Creates a pre-decrement expression, `--operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr preDecrement(Expr operand) {
        return new UnaryExpr(UnaryOp.PRE_DEC, operand);
    }

    /// Creates a post-increment expression, `operand++`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr postIncrement(Expr operand) {
        return new UnaryExpr(UnaryOp.POST_INC, operand);
    }

    /// Creates a post-decrement expression, `operand--`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr postDecrement(Expr operand) {
        return new UnaryExpr(UnaryOp.POST_DEC, operand);
    }

    /// Creates an `instanceof` test with no pattern binding, e.g. `target instanceof type`.
    ///
    /// @param target the tested expression
    /// @param type the tested type
    /// @return an `instanceof` expression
    public Expr instanceOf(Expr target, TypeRef type) {
        return new InstanceOfExpr(target, type, Optional.empty());
    }

    /// Creates an `instanceof` pattern match that binds the matched value to a name,
    /// e.g. `target instanceof type bindingName`.
    ///
    /// @param target the tested expression
    /// @param type the tested type
    /// @param bindingName the name bound to the matched value
    /// @return an `instanceof` expression
    public Expr instanceOf(Expr target, TypeRef type, String bindingName) {
        return new InstanceOfExpr(target, type, Optional.of(bindingName));
    }

    /// Creates an object creation expression, `new type(args)`.
    ///
    /// @param type the instantiated type
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public Expr new_(TypeRef type, Expr... args) {
        return new NewExpr(type, List.of(args));
    }

    /// Appends a typed local variable declaration.
    ///
    /// @param name the variable name
    /// @param type the declared variable type
    /// @param initializer the initializer expression
    /// @return this builder
    public CodeBuilder localVar(String name, TypeRef type, Expr initializer) {
        statements.add(new LocalVarDeclStmt(Optional.of(type), name, initializer));
        return this;
    }

    /// Appends an inferred (`var`) local variable declaration.
    ///
    /// @param name the variable name
    /// @param initializer the initializer expression
    /// @return this builder
    public CodeBuilder localVar(String name, Expr initializer) {
        statements.add(new LocalVarDeclStmt(Optional.empty(), name, initializer));
        return this;
    }

    /// Appends an `if` statement, optionally followed by `else if` and `else` clauses.
    ///
    /// @param condition the `if` condition
    /// @param spec receives the builder to populate the `then`/`else if`/`else` bodies
    /// @return this builder
    public CodeBuilder if_(Expr condition, Consumer<IfBuilder> spec) {
        IfBuilder ib = new IfBuilder(condition);
        spec.accept(ib);
        statements.add(ib.build());
        return this;
    }

    /// Appends a `while` loop.
    ///
    /// @param condition the loop condition
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder while_(Expr condition, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new WhileStmt(condition, cb.build()));
        return this;
    }

    /// Appends a `do`-`while` loop.
    ///
    /// @param condition the loop condition, evaluated after the body
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder doWhile_(Expr condition, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new DoWhileStmt(cb.build(), condition));
        return this;
    }

    /// Appends a classic `for` loop. Any of `init`, `condition`, or `update` may be
    /// `null` to omit that clause.
    ///
    /// @param init the initializer statement, or `null` to omit it
    /// @param condition the loop condition, or `null` to omit it
    /// @param update the per-iteration update statement, or `null` to omit it
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder for_(LocalVarDeclStmt init, Expr condition, Stmt update, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new ForStmt(
                Optional.ofNullable(init), Optional.ofNullable(condition), Optional.ofNullable(update), cb.build()));
        return this;
    }

    /// Appends an enhanced `for` loop, e.g. `for (elementType varName : iterable)`.
    ///
    /// @param elementType the declared type of the loop variable
    /// @param varName the loop variable name
    /// @param iterable the iterated expression
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder forEach(TypeRef elementType, String varName, Expr iterable, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new EnhancedForStmt(elementType, varName, iterable, cb.build()));
        return this;
    }

    /// Appends a `switch` statement.
    ///
    /// @param selector the switch selector expression
    /// @param spec receives the builder to populate the switch cases
    /// @return this builder
    public CodeBuilder switch_(Expr selector, Consumer<SwitchBuilder> spec) {
        SwitchBuilder sb = new SwitchBuilder();
        spec.accept(sb);
        statements.add(new SwitchStmt(selector, sb.build()));
        return this;
    }

    /// Creates a `switch` expression.
    ///
    /// @param selector the switch selector expression
    /// @param spec receives the builder to populate the switch cases
    /// @return a switch expression
    public Expr switchExpr(Expr selector, Consumer<SwitchBuilder> spec) {
        SwitchBuilder sb = new SwitchBuilder();
        spec.accept(sb);
        return new SwitchExpr(selector, sb.build());
    }

    /// Appends a `yield` statement, for use inside a `switch` expression's block case.
    ///
    /// @param value the yielded expression
    /// @return this builder
    public CodeBuilder yield_(Expr value) {
        statements.add(new YieldStmt(value));
        return this;
    }

    /// Appends a `throw` statement.
    ///
    /// @param exception the thrown expression
    /// @return this builder
    public CodeBuilder throw_(Expr exception) {
        statements.add(new ThrowStmt(exception));
        return this;
    }

    /// Appends a `break` statement.
    ///
    /// @return this builder
    public CodeBuilder break_() {
        statements.add(new BreakStmt());
        return this;
    }

    /// Appends a `continue` statement.
    ///
    /// @return this builder
    public CodeBuilder continue_() {
        statements.add(new ContinueStmt());
        return this;
    }

    /// Snapshots the accumulated statements into an immutable [CodeBody].
    ///
    /// @return the finished body
    public CodeBody build() {
        return new CodeBody(List.copyOf(statements));
    }
}
