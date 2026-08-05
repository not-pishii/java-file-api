package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A mutable builder for a [CodeBody], accumulating statements in call order.
///
/// Expressions are built with [Exprs] and the chaining methods on [Expr];
/// this builder only appends statements. Each `Stmt`-adding method
/// (`return_`, `assign`, `if_`, ...) appends to the body being built.
/// [#build()] snapshots the accumulated statements into an immutable
/// [CodeBody], so a builder may be reused after building.
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
    public CodeBuilder exprStatement(StatementExpr expr) {
        statements.add(new ExprStmt(expr));
        return this;
    }

    /// Appends a plain assignment statement, a convenient synonym for
    /// [#assign(AssignTarget,AssignOp,Expr)] with [AssignOp#ASSIGN].
    ///
    /// @param target the assignment target
    /// @param value the assigned expression
    /// @return this builder
    public CodeBuilder assign(AssignTarget target, Expr value) {
        return assign(target, AssignOp.ASSIGN, value);
    }

    /// Appends an assignment statement using the given operator, e.g. `target += value`.
    ///
    /// @param target the assignment target
    /// @param op the assignment operator
    /// @param value the assigned expression
    /// @return this builder
    public CodeBuilder assign(AssignTarget target, AssignOp op, Expr value) {
        statements.add(new AssignStmt(target, op, value));
        return this;
    }

    /// Appends a typed local variable declaration with an initializer.
    ///
    /// @param name the variable name
    /// @param type the declared variable type
    /// @param initializer the initializer expression
    /// @return this builder
    public CodeBuilder localVar(String name, TypeRef type, Expr initializer) {
        statements.add(new LocalVarDeclStmt.Typed(type, name, Optional.of(initializer)));
        return this;
    }

    /// Appends a typed local variable declaration with no initializer.
    ///
    /// @param name the variable name
    /// @param type the declared variable type
    /// @return this builder
    public CodeBuilder localVar(String name, TypeRef type) {
        statements.add(new LocalVarDeclStmt.Typed(type, name, Optional.empty()));
        return this;
    }

    /// Appends an inferred (`var`) local variable declaration.
    ///
    /// @param name the variable name
    /// @param initializer the initializer expression
    /// @return this builder
    public CodeBuilder localVar(String name, Expr initializer) {
        statements.add(new LocalVarDeclStmt.Inferred(name, initializer));
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
    public CodeBuilder for_(
            @Nullable LocalVarDeclStmt init,
            @Nullable Expr condition,
            @Nullable Stmt update,
            Consumer<CodeBuilder> spec) {
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

    /// Appends a `try` statement — `try`-`catch`, `try`-`finally`,
    /// `try`-`catch`-`finally`, or a try-with-resources variant of any of
    /// those.
    ///
    /// @param block receives the builder to populate the try block's body
    /// @param spec receives the builder to populate resources, `catch` clauses, and the `finally` block
    /// @return this builder
    /// @throws IllegalArgumentException if `spec` calls neither `catch_` nor `finally_`
    public CodeBuilder try_(Consumer<CodeBuilder> block, Consumer<TryBuilder> spec) {
        CodeBuilder blockBuilder = new CodeBuilder();
        block.accept(blockBuilder);
        TryBuilder tb = new TryBuilder();
        spec.accept(tb);
        statements.add(tb.build(blockBuilder.build()));
        return this;
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
        statements.add(new BreakStmt(Optional.empty()));
        return this;
    }

    /// Appends a `break` statement targeting an enclosing [LabeledStmt].
    ///
    /// @param label the targeted label
    /// @return this builder
    public CodeBuilder break_(String label) {
        statements.add(new BreakStmt(Optional.of(label)));
        return this;
    }

    /// Appends a `continue` statement.
    ///
    /// @return this builder
    public CodeBuilder continue_() {
        statements.add(new ContinueStmt(Optional.empty()));
        return this;
    }

    /// Appends a `continue` statement targeting an enclosing [LabeledStmt].
    ///
    /// @param label the targeted label
    /// @return this builder
    public CodeBuilder continue_(String label) {
        statements.add(new ContinueStmt(Optional.of(label)));
        return this;
    }

    /// Appends a labeled statement, `label: statement`, wrapping exactly the
    /// single statement `spec` appends.
    ///
    /// @param label the statement's label
    /// @param spec receives the builder to append exactly one statement to label
    /// @return this builder
    /// @throws IllegalArgumentException if `spec` appends zero or more than one statement
    public CodeBuilder labeled(String label, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        List<Stmt> built = cb.build().statements();
        if (built.size() != 1) {
            throw new IllegalArgumentException(
                    "labeled statement must wrap exactly one statement, got " + built.size());
        }
        statements.add(new LabeledStmt(label, built.get(0)));
        return this;
    }

    /// Appends a `synchronized (lock) { ... }` block.
    ///
    /// @param lock the monitor expression
    /// @param spec receives the builder to populate the synchronized block's body
    /// @return this builder
    public CodeBuilder synchronized_(Expr lock, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new SynchronizedStmt(lock, cb.build()));
        return this;
    }

    /// Appends an `assert` statement with no diagnostic message.
    ///
    /// @param condition the asserted condition
    /// @return this builder
    public CodeBuilder assert_(Expr condition) {
        statements.add(new AssertStmt(condition, Optional.empty()));
        return this;
    }

    /// Appends an `assert` statement with a diagnostic message.
    ///
    /// @param condition the asserted condition
    /// @param message the diagnostic message expression
    /// @return this builder
    public CodeBuilder assert_(Expr condition, Expr message) {
        statements.add(new AssertStmt(condition, Optional.of(message)));
        return this;
    }

    /// Appends the empty statement, `;`.
    ///
    /// @return this builder
    public CodeBuilder empty() {
        statements.add(new EmptyStmt());
        return this;
    }

    /// Appends a local class/interface/record/enum declaration used as a statement.
    ///
    /// @param decl the declared local type
    /// @return this builder
    public CodeBuilder localType(me.supcheg.javafile.model.TypeDecl decl) {
        statements.add(new LocalTypeDeclStmt(decl));
        return this;
    }

    /// Snapshots the accumulated statements into an immutable [CodeBody].
    ///
    /// @return the finished body
    public CodeBody build() {
        return new CodeBody(List.copyOf(statements));
    }
}
