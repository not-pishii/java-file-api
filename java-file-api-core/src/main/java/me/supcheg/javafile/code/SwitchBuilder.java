package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A mutable builder for the cases of a `switch` statement or expression.
///
/// Instances are created by [CodeBuilder#switch_(Expr,Consumer)] and
/// [CodeBuilder#switchExpr(Expr,Consumer)] and are not meant to be
/// instantiated directly. Each `case*`/`default*` method appends exactly one
/// [SwitchCase] with a single label.
///
/// Instances are not thread-safe.
public final class SwitchBuilder {

    private final List<SwitchCase> cases = new ArrayList<>();

    SwitchBuilder() {}

    /// Appends a constant case with a block body, e.g. `case value -> { ... }`.
    ///
    /// @param value the matched constant expression
    /// @param spec receives the builder to populate the case body
    /// @return this builder
    public SwitchBuilder case_(Expr value, Consumer<CodeBuilder> spec) {
        cases.add(new SwitchCase(new NonEmptyList<>(new ConstantLabel(value), List.of()), blockBody(spec)));
        return this;
    }

    /// Appends a constant case with a single-expression body, e.g. `case value -> result`.
    ///
    /// @param value the matched constant expression
    /// @param result the case's result expression
    /// @return this builder
    public SwitchBuilder caseValue(Expr value, Expr result) {
        cases.add(new SwitchCase(new NonEmptyList<>(new ConstantLabel(value), List.of()), new ExprCaseBody(result)));
        return this;
    }

    /// Appends a type pattern case with a block body, e.g. `case type bindingName -> { ... }`.
    ///
    /// @param type the matched type
    /// @param bindingName the name bound to the matched value
    /// @param spec receives the builder to populate the case body
    /// @return this builder
    public SwitchBuilder caseType(TypeRef type, String bindingName, Consumer<CodeBuilder> spec) {
        cases.add(new SwitchCase(
                new NonEmptyList<>(new TypePatternLabel(type, bindingName, Optional.empty()), List.of()), blockBody(spec)));
        return this;
    }

    /// Appends a guarded type pattern case with a block body,
    /// e.g. `case type bindingName when guard -> { ... }`.
    ///
    /// @param type the matched type
    /// @param bindingName the name bound to the matched value
    /// @param guard the `when` guard condition
    /// @param spec receives the builder to populate the case body
    /// @return this builder
    public SwitchBuilder caseTypeWithGuard(TypeRef type, String bindingName, Expr guard, Consumer<CodeBuilder> spec) {
        cases.add(new SwitchCase(
                new NonEmptyList<>(new TypePatternLabel(type, bindingName, Optional.of(guard)), List.of()),
                blockBody(spec)));
        return this;
    }

    /// Appends a `default` case with a block body.
    ///
    /// @param spec receives the builder to populate the case body
    /// @return this builder
    public SwitchBuilder default_(Consumer<CodeBuilder> spec) {
        cases.add(new SwitchCase(new NonEmptyList<>(new DefaultLabel(), List.of()), blockBody(spec)));
        return this;
    }

    /// Appends a `default` case with a single-expression body, e.g. `default -> result`.
    ///
    /// @param result the case's result expression
    /// @return this builder
    public SwitchBuilder defaultValue(Expr result) {
        cases.add(new SwitchCase(new NonEmptyList<>(new DefaultLabel(), List.of()), new ExprCaseBody(result)));
        return this;
    }

    private static CaseBody blockBody(Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        return new BlockCaseBody(cb.build());
    }

    List<SwitchCase> build() {
        return List.copyOf(cases);
    }
}
