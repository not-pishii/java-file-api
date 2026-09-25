package me.supcheg.javafile.code;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// Adds the branches of an `if` statement: `then`, `else if`, and `else`.
///
/// Obtained from [CodeBuilder#if_(Expr,Consumer)]:
///
/// ```java
/// b.if_(lt(field("x"), literal(0)), ib -> ib
///         .then(t -> t.return_(literal("negative")))
///         .else_(e -> e.return_(literal("non-negative"))));
/// ```
///
/// Instances are not thread-safe.
public final class IfBuilder {

    private final Expr condition;
    private CodeBody thenBody = CodeBody.EMPTY;
    private final List<ElseIfClause> elseIfClauses = new ArrayList<>();
    private @Nullable CodeBody elseBody;

    IfBuilder(Expr condition) {
        this.condition = condition;
    }

    /// Sets the body executed when the `if` condition is true.
    ///
    /// @param spec receives the builder to populate the `then` body
    /// @return this builder
    public IfBuilder then(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        this.thenBody = cb.build();
        return this;
    }

    /// Appends an `else if` clause.
    ///
    /// @param condition the clause's condition
    /// @param spec receives the builder to populate the clause's body
    /// @return this builder
    public IfBuilder elseIf(Expr condition, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        elseIfClauses.add(new ElseIfClause(condition, cb.build()));
        return this;
    }

    /// Sets the trailing `else` body.
    ///
    /// @param spec receives the builder to populate the `else` body
    /// @return this builder
    public IfBuilder else_(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        this.elseBody = cb.build();
        return this;
    }

    IfStmt build() {
        return new IfStmt(condition, thenBody, List.copyOf(elseIfClauses), Optional.ofNullable(elseBody));
    }
}
