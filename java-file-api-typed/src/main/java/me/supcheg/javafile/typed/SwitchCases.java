package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.TypeToken;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// Collects the cases of a `switch` expression over an enum,
/// [Expressions#switch_(Expr, EnumToken, TypeToken, java.util.function.Consumer)],
/// in the order of the code.
///
/// A case is of one constant or of several, `case A, B ->`, and is a value,
/// `-> value`, or a block that yields one, `-> { ...; yield value; }`
/// ([YieldBody]). What Java types cannot say of the cases is checked where
/// each is added, and of all of them where the `switch` is made:
///
/// - a constant has one case: a second one is rejected at its `case_`
///   (javac: *duplicate case label*);
/// - a constant is one of the enum of the `switch`, by the facts of that
///   enum;
/// - the `switch` is exhaustive: without a `default_`, every constant of the
///   enum has a case, or the `switch` is rejected when its cases are
///   complete (javac: *the switch expression does not cover all possible
///   input values*). The constants are those of the facts of the enum, which
///   lowering holds against the target classpath: an enum that has got a
///   constant since is a
///   [me.supcheg.javafile.facts.TargetClasspathMismatchException], not a
///   `switch` that is exhaustive no longer;
/// - the `switch` has a result: a case that is a value, or a block that
///   yields (javac: *switch expression does not have any result
///   expressions*);
/// - a case is added by the cases of the `switch` itself, one after another:
///   not from inside the block of another case, not after the `default_`,
///   which is the last, and not once the `switch` is made. The handle is in
///   the hands of the generator's lambdas, so this is checked where it is
///   called, as for the clauses of a `try` ([Handlers]).
///
/// **Stricter than javac.** A `default` case may stand anywhere among the
/// cases of Java; here it is the last.
///
/// @param <E> the enum
/// @param <R> the type of the `switch` expression
public final class SwitchCases<E, R> {
    private final EnumToken<E> enumType;
    private final TypeToken<R> type;
    private final @Nullable Block<?, ?> enclosing;
    private Phase phase = new Phase.Open(List.of());

    private sealed interface Phase {
        /// A case may be added: `cases` are the ones added so far.
        record Open(List<Node.Case> cases) implements Phase {}

        /// The block of `label` is being built.
        record Building(String label) implements Phase {}

        /// The `default` case, which is the last, was added.
        record Complete(List<Node.Case> cases, Node.Arm otherwise) implements Phase {}

        /// The `switch` was made of the cases.
        record Made() implements Phase {}
    }

    /// @param enumType the enum of the selector
    /// @param type the type of the `switch`
    /// @param enclosing the block the `switch` is built in, or `null` outside of any body
    SwitchCases(EnumToken<E> enumType, TypeToken<R> type, @Nullable Block<?, ?> enclosing) {
        this.enumType = enumType;
        this.type = type;
        this.enclosing = enclosing;
    }

    /// Adds `case constant -> value`.
    ///
    /// @param constant the constant
    /// @param value the value of the `switch` for it, cast to the type of the `switch` where its own
    ///     type is another
    /// @return this
    /// @throws IllegalStateException if the constant has a case already; if this is not the time to add
    ///     a case
    /// @throws IllegalArgumentException if the constant is not one of the enum of the `switch`; if `value`
    ///     is primitive and the type of the `switch` is not, or the reverse
    public SwitchCases<E, R> case_(EnumConstant<E> constant, Expr<? extends R> value) {
        return case_(List.of(constant), value);
    }

    /// Adds `case A, B -> value`.
    ///
    /// @param constants the constants, at least one
    /// @param value the value of the `switch` for them, cast to the type of the `switch` where its own
    ///     type is another
    /// @return this
    /// @throws IllegalStateException if a constant has a case already; if this is not the time to add a
    ///     case
    /// @throws IllegalArgumentException if there is no constant, or one is not of the enum of the
    ///     `switch`; if `value` is primitive and the type of the `switch` is not, or the reverse
    public SwitchCases<E, R> case_(List<EnumConstant<E>> constants, Expr<? extends R> value) {
        String label = label(constants);
        List<Node.Case> before = open(label, constants);
        phase = new Phase.Open(with(before, new Node.Case(List.copyOf(constants), value(value))));
        return this;
    }

    /// Adds `case constant -> { body }`.
    ///
    /// @param constant the constant
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @return this
    /// @throws IllegalStateException if the constant has a case already; if this is not the time to add
    ///     a case
    /// @throws IllegalArgumentException if the constant is not one of the enum of the `switch`
    public SwitchCases<E, R> case_(EnumConstant<E> constant, Function<? super YieldBody<R>, Terminated<R>> body) {
        return case_(List.of(constant), body);
    }

    /// Adds `case A, B -> { body }`.
    ///
    /// @param constants the constants, at least one
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @return this
    /// @throws IllegalStateException if a constant has a case already; if this is not the time to add a
    ///     case
    /// @throws IllegalArgumentException if there is no constant, or one is not of the enum of the `switch`
    public SwitchCases<E, R> case_(
            List<EnumConstant<E>> constants, Function<? super YieldBody<R>, Terminated<R>> body) {
        String label = label(constants);
        List<Node.Case> before = open(label, constants);
        phase = new Phase.Building(label);
        phase = new Phase.Open(with(before, new Node.Case(List.copyOf(constants), block(label, body))));
        return this;
    }

    /// Adds `default -> value`, the last case: it is the value of the
    /// `switch` for every constant without a case, those the enum may get
    /// later among them.
    ///
    /// @param value the value, cast to the type of the `switch` where its own type is another
    /// @throws IllegalStateException if this is not the time to add a case
    /// @throws IllegalArgumentException if `value` is primitive and the type of the `switch` is not, or
    ///     the reverse
    public void default_(Expr<? extends R> value) {
        phase = new Phase.Complete(open("default_", List.of()), value(value));
    }

    /// Adds `default -> { body }`, the last case.
    ///
    /// @param body builds the block, which must end: by `yield_`, or without completing normally
    /// @throws IllegalStateException if this is not the time to add a case
    public void default_(Function<? super YieldBody<R>, Terminated<R>> body) {
        List<Node.Case> before = open("default_", List.of());
        phase = new Phase.Building("default_");
        phase = new Phase.Complete(before, block("default_", body));
    }

    /// The `switch` of `selector`, its cases complete.
    ///
    /// @throws IllegalStateException if the cases are not exhaustive, or none has a result
    Node.Switch close(Node.Operand selector) {
        Node.Switch made =
                switch (phase) {
                    case Phase.Open(var cases) -> new Node.Switch(selector, enumType, cases, Optional.empty());
                    case Phase.Complete(var cases, var otherwise) ->
                        new Node.Switch(selector, enumType, cases, Optional.of(otherwise));
                    case Phase.Building(var label) ->
                        throw new IllegalStateException(
                                "the " + statement() + " is made while its " + label + " is being built");
                    case Phase.Made _ -> throw new IllegalStateException("the " + statement() + " is made twice");
                };
        phase = new Phase.Made();
        List<String> missing = enumType.constants().stream()
                .filter(name -> covered(made.cases()).noneMatch(name::equals))
                .toList();
        if (made.otherwise().isEmpty() && !missing.isEmpty()) {
            throw new IllegalStateException("the " + statement() + " is not exhaustive: " + enumType
                    + " has the constants " + String.join(", ", missing) + ", which have no case_, and there"
                    + " is no default_; a switch expression covers every value of its selector (JLS 15.28.1)");
        }
        if (made.arms().noneMatch(SwitchCases::hasResult)) {
            throw new IllegalStateException("the " + statement() + " has no result: every case is a block that"
                    + " never yields; a switch expression has at least one result expression (JLS 15.28.1)");
        }
        return made;
    }

    private static boolean hasResult(Node.Arm arm) {
        return switch (arm) {
            case Node.Arm.Value _ -> true;
            case Node.Arm.Block(var block) -> Reachability.yields(block);
        };
    }

    private Node.Arm value(Expr<? extends R> value) {
        return new Node.Arm.Value(Expressions.result(value, type, Expressions.Choice.SWITCH));
    }

    private Node.Arm block(String label, Function<? super YieldBody<R>, Terminated<R>> body) {
        YieldBody<R> block = YieldBody.ofCase(enclosing, type, "block of " + label + " of switch_");
        Block.build(block, body);
        return new Node.Arm.Block(block);
    }

    /// The cases added so far, if a case of `constants` may come now.
    private List<Node.Case> open(String label, List<EnumConstant<E>> constants) {
        List<Node.Case> before =
                switch (phase) {
                    case Phase.Open(var added) -> added;
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
        constants.forEach(constant -> {
            if (!Tokens.sameType(constant.owner(), enumType)) {
                throw new IllegalArgumentException(label + " of the " + statement() + ": " + constant
                        + " is not a constant of the " + enumType + " this fact of the enum has ("
                        + String.join(", ", enumType.constants()) + ")");
            }
        });
        List<String> names = constants.stream().map(EnumConstant::name).toList();
        Optional<String> duplicate = Stream.concat(
                        names.stream().filter(name -> covered(before).anyMatch(name::equals)),
                        names.stream().filter(name -> names.indexOf(name) != names.lastIndexOf(name)))
                .findFirst();
        if (duplicate.isPresent()) {
            throw new IllegalStateException(label + " of the " + statement() + ": the constant " + duplicate.get()
                    + " has a case already; a constant is the label of one case (JLS 14.11.1)");
        }
        return before;
    }

    private String label(List<EnumConstant<E>> constants) {
        if (constants.isEmpty()) {
            throw new IllegalArgumentException(
                    "a case_ of the " + statement() + " without a constant: a case has at least one");
        }
        return "case_ " + constants.stream().map(EnumConstant::name).collect(Collectors.joining(", "));
    }

    private static Stream<String> covered(List<Node.Case> cases) {
        return cases.stream().flatMap(c -> c.constants().stream()).map(EnumConstant::name);
    }

    private static List<Node.Case> with(List<Node.Case> before, Node.Case added) {
        return Stream.concat(before.stream(), Stream.of(added)).toList();
    }

    /// The expression and where it is, for messages: `switch_ over p.Day in the body of method m`.
    private String statement() {
        return "switch_ over " + enumType + (enclosing == null ? "" : " in the " + enclosing.path());
    }
}
