package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.TypeNames;
import me.supcheg.javafile.facts.TypeToken;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/// Collects the cases of a `switch` expression over a sealed type, or over
/// an enum, [Expressions#switchPattern(DeclaredToken, Expr, TypeToken, java.util.function.Consumer)]:
/// a type pattern, a record pattern, `null`, and an enum constant where the
/// selector is an enum, in the order of the code.
///
/// A case is a pattern of the typed layer ([Patterns]) or `null` — or a
/// constant of the enum of the selector — and a block that yields the value
/// of the case ([YieldBody]):
///
/// ```java
/// switchPattern(Figure_.TOKEN, figure, String_.TOKEN, c -> c
///         .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")))
///         .case_(Patterns.record(Square_.TOKEN, side), body -> body.yield_(literal("square")))
///         .default_(body -> body.yield_(literal("other"))));
/// ```
///
/// A pattern case is a block: the bindings of the pattern are in scope in the
/// block alone, and the block is what owns them. A case whose value depends
/// on no binding — `null`, an enum constant, `default_` — is a value or a
/// block.
///
/// What Java types cannot say of the cases is checked where each is added
/// and where the `switch` is made:
///
/// - the type of a pattern is a subtype of the selector's type (javac:
///   *incompatible types*);
/// - no case is dominated by one before it: a case of a pattern whose every
///   value a preceding pattern matches is rejected at its call (javac: *this
///   case label is dominated by a preceding case label*), and so is a second
///   `case null` or constant, and a case after an unconditional pattern;
/// - a `default_` is the last case, and a `switch` with an unconditional
///   pattern has no `default_` (javac: *switch has both an unconditional
///   pattern and a default label*);
/// - the `switch` is exhaustive: a `default_` covers what the cases do not;
///   without one, an unconditional pattern covers every value, and otherwise
///   every permitted subtype of a sealed selector — as the facts of its type
///   name them, [me.supcheg.javafile.facts.TypeShape#permitted()] — has a case
///   whose pattern covers it, and every constant of an enum selector has one
///   (javac: *the switch expression does not cover all possible input
///   values*);
/// - the `switch` has a result: a case that is a value, or a block that
///   yields (javac: *switch expression does not have any result expressions*);
/// - a case is added while the cases of the `switch` are being added: not
///   while the block of another case is being built, not after the
///   `default_`, which is the last, and not once the `switch` is made.
///
/// **Stricter than javac.** Each of these rejects code javac accepts; none
/// accepts code javac rejects.
///
/// - *A pattern case is a block.* The bindings of a pattern are in scope in
///   the block that yields, and a value that uses them has no block to own
///   them.
/// - *The selector is of a sealed type, or an enum.* A `switch` over another
///   type is exhaustive only through an unconditional pattern or a `default`,
///   which the typed layer does not express without a sealed hierarchy to
///   prove it.
/// - *A permitted subtype is covered by a case whose pattern type is its own,
///   or by the sealed type that permits it.* A pattern of a supertype of the
///   selector is unconditional, and covers every value. A union of subtypes of
///   a permitted subtype is not proven: the facts of a permitted subtype are
///   not at hand to tell its own permitted subtypes.
/// - *The `default` case is the last*, and primitive and reference results do
///   not mix, as for a `switch` over an enum ([EnumSwitchCases]).
///
/// @param <T> the sealed type or enum of the selector
/// @param <R> the type of the `switch` expression
public final class SealedSwitchCases<T, R> {
    private final DeclaredToken<T> type;
    private final TypeToken<R> result;
    private final Block.Enclosing enclosing;
    private Phase phase = new Phase.Open(List.of());

    private sealed interface Phase {
        /// A case may be added: `cases` are the ones added so far.
        record Open(List<Node.PatternCase> cases) implements Phase {}

        /// The block of `label` is being built.
        record Building(String label) implements Phase {}

        /// The `default` case, which is the last, was added.
        record Complete(List<Node.PatternCase> cases, Node.Arm otherwise) implements Phase {}

        /// The `switch` was made of the cases.
        record Made() implements Phase {}
    }

    /// @param type the sealed type or enum of the selector
    /// @param result the type of the `switch`
    /// @param enclosing what the `switch` is built in: a block, or nothing outside of any body
    /// @throws IllegalArgumentException if `type` is neither sealed nor an enum
    SealedSwitchCases(DeclaredToken<T> type, TypeToken<R> result, Block.Enclosing enclosing) {
        if (!type.shape().sealed() && !(type instanceof EnumToken<?>)) {
            throw new IllegalArgumentException("a switch_ over " + type + " is not expressed: its type is neither"
                    + " sealed nor an enum, so the cases cannot be proven to cover every value of the selector;"
                    + " switch over a sealed type, whose facts name the subtypes it permits, or over an enum");
        }
        this.type = type;
        this.result = result;
        this.enclosing = enclosing;
    }

    /// Adds `case T t -> { body }`, the body given the binding of the pattern.
    ///
    /// @param caseType the type of the pattern, a subtype of the selector's type
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @param <C> the type of the pattern
    /// @return this
    /// @throws IllegalArgumentException if `caseType` is no subtype of the selector's type
    /// @throws IllegalStateException if the case is dominated by one before it; if this is not the time to
    ///     add a case
    public <C> SealedSwitchCases<T, R> case_(
            DeclaredToken<C> caseType,
            java.util.function.BiFunction<? super YieldBody<R>, ? super Var<C>, Terminated<R>> body) {
        PatternBinding<C> binding = Patterns.binding(caseType);
        return add(Patterns.type(binding), "case_ " + caseType, null, block -> body.apply(block, binding.var()));
    }

    /// Adds `case T t when guard -> { body }`.
    ///
    /// @param caseType the type of the pattern, a subtype of the selector's type
    /// @param guard builds the `when` condition, given a block it is in scope of and the binding
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @param <C> the type of the pattern
    /// @return this
    /// @throws IllegalArgumentException if `caseType` is no subtype of the selector's type
    /// @throws IllegalStateException if the case is dominated by one before it; if this is not the time to
    ///     add a case
    public <C> SealedSwitchCases<T, R> case_(
            DeclaredToken<C> caseType,
            java.util.function.BiFunction<? super YieldBody<R>, ? super Var<C>, ? extends Expr<Prim.Bool>> guard,
            java.util.function.BiFunction<? super YieldBody<R>, ? super Var<C>, Terminated<R>> body) {
        PatternBinding<C> binding = Patterns.binding(caseType);
        return add(
                Patterns.type(binding),
                "case_ " + caseType,
                block -> guard.apply(block, binding.var()),
                block -> body.apply(block, binding.var()));
    }

    /// Adds `case pattern -> { body }`: a type pattern, or a record
    /// deconstruction whose bindings are in scope in `body` ([Patterns]).
    ///
    /// @param pattern the pattern
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @return this
    /// @throws IllegalArgumentException if the pattern's type is no subtype of the selector's type
    /// @throws IllegalStateException if the case is dominated by one before it; if this is not the time to
    ///     add a case
    public SealedSwitchCases<T, R> case_(TypedPattern<?> pattern, Function<? super YieldBody<R>, Terminated<R>> body) {
        return add(pattern, "case_ " + pattern.matched(), null, body);
    }

    /// Adds `case pattern when guard -> { body }`.
    ///
    /// @param pattern the pattern
    /// @param guard builds the `when` condition, given the block its bindings are in scope in
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @return this
    /// @throws IllegalArgumentException if the pattern's type is no subtype of the selector's type
    /// @throws IllegalStateException if the case is dominated by one before it; if this is not the time to
    ///     add a case
    public SealedSwitchCases<T, R> case_(
            TypedPattern<?> pattern,
            Function<? super YieldBody<R>, ? extends Expr<Prim.Bool>> guard,
            Function<? super YieldBody<R>, Terminated<R>> body) {
        return add(pattern, "case_ " + pattern.matched(), guard, body);
    }

    /// Adds `case null -> value`, which is the value of the `switch` where the selector is `null`.
    ///
    /// @param value the value, cast to the type of the `switch` where its own type is another
    /// @return this
    /// @throws IllegalArgumentException if `value` is primitive and the type of the `switch` is not, or
    ///     the reverse
    /// @throws IllegalStateException if `null` has a case already; if this is not the time to add a case
    public SealedSwitchCases<T, R> caseNull(Expr<? extends R> value) {
        List<Node.PatternCase> before = open("case null", new Node.PatternCase.Label.Null());
        phase = new Phase.Open(
                with(before, new Node.PatternCase(new Node.PatternCase.Label.Null(), Optional.empty(), value(value))));
        return this;
    }
    /// Adds `case null -> { body }`.
    ///
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @return this
    /// @throws IllegalStateException if `null` has a case already; if this is not the time to add a case
    public SealedSwitchCases<T, R> caseNull(Function<? super YieldBody<R>, Terminated<R>> body) {
        List<Node.PatternCase> before = open("case null", new Node.PatternCase.Label.Null());
        phase = new Phase.Building("case null");
        phase = new Phase.Open(with(
                before,
                new Node.PatternCase(new Node.PatternCase.Label.Null(), Optional.empty(), block("case null", body))));
        return this;
    }

    /// Adds `case constant -> value` over an enum selector.
    ///
    /// @param constant the constant
    /// @param value the value, cast to the type of the `switch` where its own type is another
    /// @param <E> the enum
    /// @return this
    /// @throws IllegalArgumentException if the selector is no enum, or the constant is not one of its
    ///     enum's fact; if `value` is primitive and the type of the `switch` is not
    /// @throws IllegalStateException if the constant has a case already; if this is not the time to add
    ///     a case
    public <E> SealedSwitchCases<T, R> case_(EnumConstant<E> constant, Expr<? extends R> value) {
        List<Node.PatternCase> before = openConstant("case_ " + constant.name(), constant);
        phase = new Phase.Open(with(
                before,
                new Node.PatternCase(new Node.PatternCase.Label.Constant(constant), Optional.empty(), value(value))));
        return this;
    }

    /// Adds `case constant -> { body }` over an enum selector.
    ///
    /// @param constant the constant
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @param <E> the enum
    /// @return this
    /// @throws IllegalArgumentException if the selector is no enum, or the constant is not one of its
    ///     enum's fact
    /// @throws IllegalStateException if the constant has a case already; if this is not the time to add
    ///     a case
    public <E> SealedSwitchCases<T, R> case_(
            EnumConstant<E> constant, Function<? super YieldBody<R>, Terminated<R>> body) {
        List<Node.PatternCase> before = openConstant("case_ " + constant.name(), constant);
        String label = "case_ " + constant.name();
        phase = new Phase.Building(label);
        phase = new Phase.Open(with(
                before,
                new Node.PatternCase(
                        new Node.PatternCase.Label.Constant(constant), Optional.empty(), block(label, body))));
        return this;
    }

    /// Adds `default -> value`, the last case: it is the value of the
    /// `switch` for every value no case matches.
    ///
    /// @param value the value, cast to the type of the `switch` where its own type is another
    /// @throws IllegalArgumentException if `value` is primitive and the type of the `switch` is not, or
    ///     the reverse
    /// @throws IllegalStateException if this is not the time to add a case; if a case is unconditional
    public void default_(Expr<? extends R> value) {
        complete("default_", value(value));
    }

    /// Adds `default -> { body }`, the last case.
    ///
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @throws IllegalStateException if this is not the time to add a case; if a case is unconditional
    public void default_(Function<? super YieldBody<R>, Terminated<R>> body) {
        List<Node.PatternCase> before = open("default_", null);
        phase = new Phase.Building("default_");
        complete(before, block("default_", body));
    }

    private void complete(String label, Node.Arm otherwise) {
        complete(open(label, null), otherwise);
    }

    private void complete(List<Node.PatternCase> before, Node.Arm otherwise) {
        unconditional(before).ifPresent(case_ -> {
            throw new IllegalStateException("the " + statement() + " has both an unconditional pattern and a"
                    + " default_: the case " + describe(case_) + " matches every value of the selector, so the"
                    + " default_ matches nothing (JLS 15.28.1)");
        });
        phase = new Phase.Complete(before, otherwise);
    }

    /// The `switch` of `selector`, its cases complete.
    ///
    /// @param selector the selector, with its static type
    /// @return the `switch`
    /// @throws IllegalStateException if the cases are not exhaustive, or none has a result
    Node.PatternSwitch close(Node.Operand selector) {
        Node.PatternSwitch made =
                switch (phase) {
                    case Phase.Open(var cases) ->
                        new Node.PatternSwitch(selector, type, cases, new Node.Default.None());
                    case Phase.Complete(var cases, var otherwise) ->
                        new Node.PatternSwitch(selector, type, cases, new Node.Default.Of(otherwise));
                    case Phase.Building(var label) ->
                        throw new IllegalStateException(
                                "the " + statement() + " is made while its " + label + " is being built");
                    case Phase.Made _ -> throw new IllegalStateException("the " + statement() + " is made twice");
                };
        phase = new Phase.Made();
        if (made.arms().findAny().isEmpty()) {
            throw new IllegalStateException("the " + statement() + " has no case: add a pattern case of every"
                    + " value of the selector, or a default_; a switch expression has at least one case"
                    + " (JLS 15.28)");
        }
        switch (made.otherwise()) {
            case Node.Default.Of _ -> {}
            case Node.Default.None _ -> requireExhaustive(made.cases());
        }
        if (made.arms().noneMatch(SealedSwitchCases::hasResult)) {
            throw new IllegalStateException("the " + statement() + " has no result: every case is a block that"
                    + " never yields; a switch expression has at least one result expression (JLS 15.28.1)");
        }
        return made;
    }

    /// Refuses the cases of a `switch` without a `default_` unless they cover
    /// every value of the selector: an unconditional pattern, every permitted
    /// subtype of a sealed selector, or every constant of an enum.
    private void requireExhaustive(List<Node.PatternCase> cases) {
        if (unconditional(cases).isPresent()) {
            return;
        }
        if (type instanceof EnumToken<?> enumType) {
            List<String> missing = enumType.constants().stream()
                    .filter(name -> cases.stream()
                            .flatMap(case_ ->
                                    case_.label() instanceof Node.PatternCase.Label.Constant(EnumConstant<?> constant)
                                            ? Stream.of(constant.name())
                                            : Stream.empty())
                            .noneMatch(name::equals))
                    .toList();
            if (!missing.isEmpty()) {
                throw new IllegalStateException("the " + statement() + " is not exhaustive: " + enumType
                        + " has the constants " + String.join(", ", missing) + ", which have no case_, and"
                        + " there is no default_; a switch expression covers every value of its selector"
                        + " (JLS 15.28.1)");
            }
            return;
        }
        List<ClassDesc> permitted = type.shape().permitted();
        if (permitted.isEmpty()) {
            throw new IllegalStateException("the " + statement() + " is not exhaustive: the facts of " + type
                    + " tell no subtype it permits, and there is no default_; a switch expression covers every"
                    + " value of its selector (JLS 15.28.1)");
        }
        List<String> missing = permitted.stream()
                .filter(subtype -> cases.stream()
                        .flatMap(case_ -> case_.label() instanceof Node.PatternCase.Label.Of(TypedPattern<?> pattern)
                                        && case_.guard().isEmpty()
                                ? Stream.of(pattern)
                                : Stream.empty())
                        .noneMatch(pattern -> Patterns.coversPermitted(pattern.matched(), subtype)))
                .map(TypeNames::describe)
                .toList();
        if (!missing.isEmpty()) {
            throw new IllegalStateException("the " + statement() + " is not exhaustive: " + type + " permits "
                    + String.join(", ", missing) + ", which no case_ covers, and there is no default_; a switch"
                    + " expression covers every value of its selector (JLS 15.28.1)");
        }
    }

    /// The case of an unguarded pattern the selector is a subtype of, if there is one: a case no value of
    /// the selector escapes.
    private Optional<Node.PatternCase> unconditional(List<Node.PatternCase> cases) {
        return cases.stream()
                .filter(case_ -> case_.label() instanceof Node.PatternCase.Label.Of(TypedPattern<?> pattern)
                        && case_.guard().isEmpty()
                        && Patterns.isSubtype(type, pattern.matched()))
                .findFirst();
    }

    private static boolean hasResult(Node.Arm arm) {
        return switch (arm) {
            case Node.Arm.Value _ -> true;
            case Node.Arm.Block(var block) -> Reachability.yields(block);
        };
    }

    private Node.Arm value(Expr<? extends R> value) {
        return new Node.Arm.Value(Expressions.result(value, result, Expressions.Choice.SWITCH));
    }

    private Node.Arm block(String label, Function<? super YieldBody<R>, Terminated<R>> body) {
        YieldBody<R> block = YieldBody.ofCase(enclosing, result, "block of " + label + " of switch_");
        Block.build(block, body);
        return new Node.Arm.Block(block);
    }

    /// Adds a pattern case: the block is made first, so the bindings of the
    /// pattern are in scope in `guard` and `body`, and the cases are complete
    /// only once the block is built.
    private SealedSwitchCases<T, R> add(
            TypedPattern<?> pattern,
            String label,
            @Nullable Function<? super YieldBody<R>, ? extends Expr<Prim.Bool>> guard,
            Function<? super YieldBody<R>, Terminated<R>> body) {
        requireApplicable(pattern, label);
        List<Node.PatternCase> before = open(label, new Node.PatternCase.Label.Of(pattern));
        phase = new Phase.Building(label);
        YieldBody<R> block = YieldBody.ofCase(enclosing, result, "block of " + label + " of switch_");
        Patterns.bind(pattern, block);
        Optional<Node> condition = Optional.empty();
        if (guard != null) {
            Expr<Prim.Bool> built = Scopes.within(block, () -> guard.apply(block));
            condition = Optional.of(built.node());
        }
        Block.build(block, body);
        phase = new Phase.Open(with(
                before,
                new Node.PatternCase(new Node.PatternCase.Label.Of(pattern), condition, new Node.Arm.Block(block))));
        return this;
    }

    /// The cases added so far, if a case of `label` may come now; a case of
    /// `added` is refused where a case before it dominates it, and `default_`,
    /// which is `null` added, is not.
    private List<Node.PatternCase> open(String label, Node.PatternCase.@Nullable Label added) {
        List<Node.PatternCase> before =
                switch (phase) {
                    case Phase.Open(var cases) -> cases;
                    case Phase.Building(var building) ->
                        throw new IllegalStateException(label + " of the " + statement() + " while its " + building
                                + " is being built: a case is added by the cases of the switch itself, not"
                                + " from inside the block of another case");
                    case Phase.Complete _ ->
                        throw new IllegalStateException(label + " of the " + statement() + " after its default_:"
                                + " the default_ case is the last");
                    case Phase.Made _ ->
                        throw new IllegalStateException(label + " of the " + statement() + " after the switch was"
                                + " made: the cases of a switch are added by its cases, which run when it is"
                                + " built, and nowhere else");
                };
        if (added != null) {
            requireNotDominated(label, added, before);
        }
        return before;
    }

    private <E> List<Node.PatternCase> openConstant(String label, EnumConstant<E> constant) {
        if (!(type instanceof EnumToken<?> enumType)) {
            throw new IllegalArgumentException(label + " of the " + statement() + ": " + constant + " is a"
                    + " constant, and the selector of a pattern switch that takes one is over an enum; it is"
                    + " over " + type);
        }
        if (constant.owner().shape() != enumType.shape()) {
            throw new IllegalArgumentException(label + " of the " + statement() + ": " + constant + " is not a"
                    + " constant of the fact of " + enumType + " the switch is over, whose constants are "
                    + String.join(", ", enumType.constants()) + ": the cases are exhaustive by one fact of the"
                    + " enum, the one that is held against the target classpath; take the constant from the"
                    + " token given to switch_");
        }
        return open(label, new Node.PatternCase.Label.Constant(constant));
    }

    /// Refuses a case every value of which a case before it matches: what an
    /// unconditional pattern precedes, and a pattern of a subtype of a
    /// pattern before it.
    private void requireNotDominated(String label, Node.PatternCase.Label added, List<Node.PatternCase> before) {
        unconditional(before).ifPresent(earlier -> {
            throw new IllegalStateException(label + " of the " + statement() + " is dominated by "
                    + describe(earlier) + ": the case is unconditional, so every value the selector has is"
                    + " matched by it (JLS 15.28.1)");
        });
        for (Node.PatternCase earlier : before) {
            boolean dominated =
                    switch (earlier.label()) {
                        case Node.PatternCase.Label.Of(TypedPattern<?> pattern) ->
                            earlier.guard().isEmpty()
                                    && added instanceof Node.PatternCase.Label.Of(TypedPattern<?> mine)
                                    && Patterns.isSubtype(mine.matched(), pattern.matched());
                        case Node.PatternCase.Label.Null _ -> added instanceof Node.PatternCase.Label.Null;
                        case Node.PatternCase.Label.Constant(EnumConstant<?> constant) ->
                            added instanceof Node.PatternCase.Label.Constant(EnumConstant<?> mine)
                                    && mine.name().equals(constant.name());
                    };
            if (dominated) {
                throw new IllegalStateException(label + " of the " + statement() + " is dominated by "
                        + describe(earlier) + ": every value it matches that case matches too (JLS 15.28.1)");
            }
        }
    }

    /// Refuses a pattern the selector type does not allow: javac has no case that is no pattern of a subtype
    /// of the selector's type.
    private void requireApplicable(TypedPattern<?> pattern, String label) {
        if (!Patterns.isSubtype(pattern.matched(), type)) {
            throw new IllegalArgumentException(label + " of the " + statement() + " matches " + pattern.matched()
                    + ", which is no subtype of the selector's type " + type + ": javac converts the selector to"
                    + " the type of the pattern, and " + type + " cannot be converted to " + pattern.matched()
                    + " (JLS 15.28.1)");
        }
    }

    private static String describe(Node.PatternCase case_) {
        return switch (case_.label()) {
            case Node.PatternCase.Label.Of(TypedPattern<?> pattern) -> "the case_ of the pattern " + pattern.matched();
            case Node.PatternCase.Label.Null _ -> "the case_ null";
            case Node.PatternCase.Label.Constant(EnumConstant<?> constant) ->
                "the case_ of the constant " + constant.name();
        };
    }

    private static List<Node.PatternCase> with(List<Node.PatternCase> before, Node.PatternCase added) {
        return Stream.concat(before.stream(), Stream.of(added)).toList();
    }

    /// The expression and where it is, for messages: `switch_ over p.Figure in the body of method m`.
    private String statement() {
        return "switch_ over "
                + type
                + switch (enclosing) {
                    case Block.Enclosing.Root _ -> "";
                    case Block.Enclosing.In(var block) -> " in the " + block.path();
                };
    }
}
