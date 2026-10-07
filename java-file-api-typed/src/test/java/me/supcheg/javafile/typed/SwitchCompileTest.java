package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Op;
import me.supcheg.javafile.typed.fixtures.Outer;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.fixtures.Words;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.IllegalStateException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Nothing_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Op_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Outer_Level_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Words_;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.function.Supplier;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.at;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.geInt;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.not;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/// Positive end-to-end tests of the `switch` expression over an enum (§6.3)
/// and of enum constants: what the construction-time checks accept renders
/// to code javac accepts under every lint — exhaustive, with a result, every
/// result of the type of the expression — and the running code takes the
/// case it reads.
class SwitchCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Switches");

    private static final Supplier_<String> STRINGS = new Supplier_<>(String_.TOKEN);

    private static CompiledClasses compiled;

    @BeforeAll
    static void compile() {
        compiled = CompiledClasses.of(
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        cases(cb);
                        places(cb);
                        blocks(cb);
                        enums(cb);
                        syntax(cb);
                    }
                }));
    }

    /// The shapes of the cases: values and blocks, one constant and several, with and without a default.
    private static <Self> void cases(TypedClassBuilder<Self> cb) {
        // return switch (s) { case RED -> "stop"; case AMBER -> "wait"; case GREEN -> "go"; };
        cb.staticMethod(
                "advice",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        String_.TOKEN,
                        c -> c.case_(Signal_.RED, literal("stop"))
                                .case_(Signal_.AMBER, literal("wait"))
                                .case_(Signal_.GREEN, literal("go")))));

        // return switch (s) { case RED, AMBER -> false; default -> true; };
        cb.staticMethod(
                "mayGo",
                PrimitiveToken.BOOLEAN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        PrimitiveToken.BOOLEAN,
                        c -> c.case_(List.of(Signal_.RED, Signal_.AMBER), literal(false))
                                .default_(literal(true)))));

        // return switch (s) { case GREEN -> { String v = "g"; yield v + "o"; } default -> { yield "no"; } };
        cb.staticMethod(
                "yielded",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        String_.TOKEN,
                        c -> c.case_(
                                        Signal_.GREEN,
                                        y -> y.let(String_.TOKEN, literal("g"), v -> y.yield_(concat(v, literal("o")))))
                                .default_(y -> y.yield_(literal("no"))))));

        // a block that throws has no result; the other cases have
        cb.staticMethod(
                "noAmber",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        String_.TOKEN,
                        c -> c.case_(
                                        Signal_.AMBER,
                                        y -> y.throw_(new_(IllegalStateException_.new_String, literal("amber"))))
                                .case_(List.of(Signal_.RED, Signal_.GREEN), y -> y.yield_(literal("fine"))))));

        // every result is of the type of the switch: `(Object) "red"`, `(Object) Integer.valueOf(1)`
        cb.staticMethod(
                "mixed",
                Object_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        Object_.TOKEN,
                        c -> c.case_(Signal_.RED, literal("red"))
                                .case_(Signal_.AMBER, y -> y.yield_(box(PrimitiveToken.INT, literal(1))))
                                .default_(s))));

        // static Signal next(Signal s): enum constants as values
        cb.staticMethod(
                "next",
                Signal_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        Signal_.TOKEN,
                        c -> c.case_(Signal_.RED, enumConstant(Signal_.GREEN))
                                .case_(Signal_.GREEN, enumConstant(Signal_.AMBER))
                                .case_(Signal_.AMBER, enumConstant(Signal_.RED)))));
        cb.staticMethod(
                "isRed",
                PrimitiveToken.BOOLEAN,
                Signal_.TOKEN,
                (b, s) -> b.return_(eqRef(s, enumConstant(Signal_.RED))));
    }

    /// The places a `switch` stands in: an operand, a receiver, a selector, a lambda, a field.
    private static <Self> void places(TypedClassBuilder<Self> cb) {
        // return (switch (s) { ... }).length() + switch (s) { ... };
        cb.staticMethod(
                "lengthPlusRank",
                PrimitiveToken.INT,
                Signal_.TOKEN,
                (b, s) -> b.return_(addInt(
                        call(
                                switch_(
                                        Signal_.TOKEN,
                                        s,
                                        String_.TOKEN,
                                        c -> c.case_(Signal_.RED, literal("stop"))
                                                .default_(literal("go"))),
                                String_.length),
                        switch_(
                                Signal_.TOKEN,
                                s,
                                PrimitiveToken.INT,
                                c -> c.case_(Signal_.RED, literal(10))
                                        .case_(Signal_.AMBER, literal(20))
                                        .case_(Signal_.GREEN, literal(30))))));

        // a switch whose selector is a switch, and whose case is one
        cb.staticMethod(
                "afterNext",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        switch_(
                                Signal_.TOKEN,
                                s,
                                Signal_.TOKEN,
                                c -> c.case_(Signal_.RED, enumConstant(Signal_.GREEN))
                                        .default_(enumConstant(Signal_.RED))),
                        String_.TOKEN,
                        c -> c.case_(Signal_.GREEN, literal("go"))
                                .default_(switch_(
                                        Signal_.TOKEN,
                                        s,
                                        String_.TOKEN,
                                        inner -> inner.case_(Signal_.GREEN, literal("was green"))
                                                .default_(literal("was amber")))))));

        // static Supplier<String> lazy(Signal s) { return () -> switch (s) { ... }; }
        cb.staticMethod(
                "lazy",
                STRINGS.token,
                Signal_.TOKEN,
                (b, s) -> b.return_(lambda(
                        STRINGS.sam,
                        () -> switch_(
                                Signal_.TOKEN,
                                s,
                                String_.TOKEN,
                                c -> c.case_(Signal_.RED, y -> y.yield_(literal("stop")))
                                        .default_(literal("go"))))));

        // a case that is a lambda: `case RED -> (Supplier<String>) () -> "stop"`
        cb.staticMethod(
                "supplier",
                STRINGS.token,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        STRINGS.token,
                        c -> c.case_(Signal_.RED, lambda(STRINGS.sam, () -> literal("stop")))
                                .default_(y -> y.yield_(lambda(STRINGS.sam, () -> literal("go")))))));

        // public final String initial = switch (Signal.valueOf("RED")) { case RED -> { yield "r"; } default -> "?"; };
        var initial = cb.field(
                "initial",
                String_.TOKEN,
                switch_(
                        Signal_.TOKEN,
                        staticCall(Signal_.valueOf_String, literal("RED")),
                        String_.TOKEN,
                        c -> c.case_(Signal_.RED, y -> y.yield_(literal("r"))).default_(literal("?"))));
        cb.method("initial", String_.TOKEN, (b, self) -> b.return_(Expressions.field(self, initial)));
    }

    /// What the block of a case may do: assign a variable of the code around it, loop, catch, throw.
    private static <Self> void blocks(TypedClassBuilder<Self> cb) {
        // int n = 0; String r = switch (s) { case RED -> { n = 1; yield "r"; } default -> { n = 2; yield "x"; } };
        cb.staticMethod(
                "counted",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.letVar(
                        PrimitiveToken.INT,
                        literal(0),
                        n -> b.let(
                                String_.TOKEN,
                                switch_(
                                        Signal_.TOKEN,
                                        s,
                                        String_.TOKEN,
                                        c -> c.case_(
                                                        Signal_.RED,
                                                        y -> y.exec(assign(n, literal(1)))
                                                                .yield_(literal("r")))
                                                .default_(y -> y.exec(assign(n, literal(2)))
                                                        .yield_(literal("x")))),
                                r -> b.return_(concat(r, call(box(PrimitiveToken.INT, n), Integer_.toString))))));

        // a loop of the block is broken and continued; both branches of an if yield
        cb.staticMethod(
                "looped",
                PrimitiveToken.INT,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        PrimitiveToken.INT,
                        c -> c.case_(
                                        Signal_.RED,
                                        y -> y.letVar(
                                                PrimitiveToken.INT,
                                                literal(0),
                                                i -> y.while_(
                                                                literal(true),
                                                                (loop, ctl) -> loop.if_(
                                                                                geInt(i, literal(3)),
                                                                                t -> t.break_(ctl))
                                                                        .exec(assign(i, addInt(i, literal(1)))))
                                                        .yield_(i)))
                                .default_(y -> y.ifElse(
                                        eqRef(s, enumConstant(Signal_.AMBER)),
                                        t -> t.yield_(literal(-1)),
                                        e -> e.yield_(literal(-2)))))));

        // a checked exception of a block is one of the method the switch is in
        cb.throwing(IOException_.TOKEN)
                .staticMethod(
                        "reading",
                        PrimitiveToken.INT,
                        Signal_.TOKEN,
                        (b, s) -> b.return_(switch_(
                                Signal_.TOKEN,
                                s,
                                PrimitiveToken.INT,
                                c -> c.case_(
                                                Signal_.RED,
                                                y -> y.yield_(call(
                                                        new_(StringReader_.new_String, literal("")),
                                                        StringReader_.read)))
                                        .case_(
                                                Signal_.AMBER,
                                                y -> y.throw_(new_(IOException_.new_String, literal("amber"))))
                                        .default_(call(
                                                new_(StringReader_.new_String, literal("g")), StringReader_.read)))));

        // ... or of a try in the block, or around the switch
        cb.staticMethod(
                "caught",
                PrimitiveToken.INT,
                Signal_.TOKEN,
                (b, s) -> b.tryTerminated(
                        h -> h.catch_(IOException_.TOKEN, (handler, _) -> handler.return_(literal(-1))),
                        t -> t.return_(switch_(
                                Signal_.TOKEN,
                                s,
                                PrimitiveToken.INT,
                                c -> c.case_(
                                                Signal_.RED,
                                                y -> y.tryTerminated(
                                                        h -> h.catch_(
                                                                IOException_.TOKEN,
                                                                (handler, _) -> handler.yield_(literal(-2))),
                                                        body -> body.throw_(
                                                                new_(IOException_.new_String, literal("red")))))
                                        .default_(y -> y.throw_(new_(IOException_.new_String, literal("other"))))))));
    }

    /// The enums a `switch` is over: constants with bodies, a nested enum, constants that are contextual
    /// keywords, an enum without a constant.
    private static <Self> void enums(TypedClassBuilder<Self> cb) {
        cb.staticMethod(
                "named",
                String_.TOKEN,
                Op_.TOKEN,
                (b, op) -> b.return_(switch_(
                        Op_.TOKEN,
                        op,
                        String_.TOKEN,
                        c -> c.case_(Op_.ADD, literal("add")).case_(Op_.SUB, literal("sub")))));
        cb.staticMethod(
                "rank",
                PrimitiveToken.INT,
                Outer_Level_.TOKEN,
                (b, level) -> b.return_(switch_(
                        Outer_Level_.TOKEN,
                        level,
                        PrimitiveToken.INT,
                        c -> c.case_(Outer_Level_.LOW, literal(0)).case_(Outer_Level_.HIGH, literal(1)))));
        cb.staticMethod(
                "word",
                String_.TOKEN,
                Words_.TOKEN,
                (b, w) -> b.return_(switch_(
                        Words_.TOKEN,
                        w,
                        String_.TOKEN,
                        c -> c.case_(Words_.when, literal("when"))
                                .case_(Words_.yield, y -> y.yield_(literal("yield")))
                                .case_(List.of(Words_.record, Words_.var), literal("record or var"))
                                .case_(Words_.sealed, literal("sealed"))
                                .case_(Words_.permits, literal("permits")))));
        cb.staticMethod(
                "ofNothing",
                String_.TOKEN,
                Nothing_.TOKEN,
                (b, n) -> b.return_(switch_(Nothing_.TOKEN, n, String_.TOKEN, c -> c.default_(literal("none")))));
    }

    /// Where the rendered code needs care: a `yield` before a parenthesis, and a `switch` as an index, an
    /// array, the target of an assignment and the operand of `!`.
    private static <Self> void syntax(TypedClassBuilder<Self> cb) {
        ArrayToken<int[], Prim.Int> ints = PrimitiveToken.INT.array();

        // yield (String) null;
        cb.staticMethod(
                "yieldsNull",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN, s, String_.TOKEN, c -> c.default_(y -> y.yield_(literalNull(String_.TOKEN))))));
        // yield ("a" + "b").length();
        cb.staticMethod(
                "yieldsLength",
                PrimitiveToken.INT,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        PrimitiveToken.INT,
                        c -> c.default_(y -> y.yield_(call(concat(literal("a"), literal("b")), String_.length))))));
        // yield (switch (s) { ... }).trim();
        cb.staticMethod(
                "yieldsOfSwitch",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        String_.TOKEN,
                        c -> c.default_(y -> y.yield_(call(
                                switch_(Signal_.TOKEN, s, String_.TOKEN, inner -> inner.default_(literal(" in "))),
                                String_.trim))))));
        // yield ((Supplier<String>) (() -> "l")).get();
        cb.staticMethod(
                "yieldsOfLambda",
                String_.TOKEN,
                Signal_.TOKEN,
                (b, s) -> b.return_(switch_(
                        Signal_.TOKEN,
                        s,
                        String_.TOKEN,
                        c -> c.default_(y -> y.yield_(call(lambda(STRINGS.sam, () -> literal("l")), STRINGS.get))))));

        // int[] a = new int[3]; a[switch ...] = 7; (switch (s) { default -> a; })[0] = 5;
        // return a[switch ...] + (switch (s) { default -> a; })[0];
        cb.staticMethod(
                "indexed",
                PrimitiveToken.INT,
                Signal_.TOKEN,
                (b, s) -> b.let(
                        ints,
                        newArray(ints, literal(3)),
                        a -> b.exec(assignAt(ints, a, rank(s), literal(7)))
                                .exec(assignAt(
                                        ints,
                                        switch_(Signal_.TOKEN, s, ints, c -> c.default_(a)),
                                        literal(0),
                                        literal(5)))
                                .return_(addInt(
                                        at(ints, a, rank(s)),
                                        at(ints, switch_(Signal_.TOKEN, s, ints, c -> c.default_(a)), literal(0))))));
        // return !switch (s) { case RED -> true; default -> false; };
        cb.staticMethod(
                "notRed",
                PrimitiveToken.BOOLEAN,
                Signal_.TOKEN,
                (b, s) -> b.return_(not(switch_(
                        Signal_.TOKEN,
                        s,
                        PrimitiveToken.BOOLEAN,
                        c -> c.case_(Signal_.RED, literal(true)).default_(literal(false))))));
    }

    /// `switch (s) { case RED -> 1; default -> 2; }`.
    private static Expr<Prim.Int> rank(Expr<Signal> s) {
        return switch_(
                Signal_.TOKEN,
                s,
                PrimitiveToken.INT,
                c -> c.case_(Signal_.RED, literal(1)).default_(literal(2)));
    }

    @Test
    void aSwitchIsOverAnEnumWhoseConstantsHaveBodiesANestedOneAndOneWithoutAConstant() throws Throwable {
        assertThat(compiled.invoke("named", Op.ADD)).isEqualTo("add");
        assertThat(compiled.invoke("named", Op.SUB)).isEqualTo("sub");
        assertThat(compiled.invoke("rank", Outer.Level.HIGH)).isEqualTo(1);
        assertThatNullPointerException().isThrownBy(() -> compiled.invoke("ofNothing", (Object) null));
        assertThat(compiled.source()).contains("import me.supcheg.javafile.typed.fixtures.Outer.Level;");
    }

    @Test
    void aConstantThatIsAContextualKeywordIsTheLabelOfACase() throws Throwable {
        assertThat(compiled.invoke("word", Words.when)).isEqualTo("when");
        assertThat(compiled.invoke("word", Words.yield)).isEqualTo("yield");
        assertThat(compiled.invoke("word", Words.var)).isEqualTo("record or var");
        assertThat(compiled.invoke("word", Words.sealed)).isEqualTo("sealed");
        assertThat(compiled.invoke("word", Words.permits)).isEqualTo("permits");
        assertThat(compiled.source()).contains("case yield -> {").contains("case record, var ->");
    }

    @Test
    void aYieldBeforeAParenthesisIsAYield() throws Throwable {
        assertThat(compiled.invoke("yieldsNull", Signal.RED)).isNull();
        assertThat(compiled.invoke("yieldsLength", Signal.RED)).isEqualTo(2);
        assertThat(compiled.invoke("yieldsOfSwitch", Signal.RED)).isEqualTo("in");
        assertThat(compiled.invoke("yieldsOfLambda", Signal.RED)).isEqualTo("l");
        assertThat(compiled.source())
                .contains("yield (String) null;")
                .contains("yield (\"a\" + \"b\").length();")
                .contains("yield (switch (v0) {")
                .contains("yield ((Supplier<String>) (() -> \"l\")).get();");
    }

    @Test
    void aSwitchIsAnIndexAnArrayTheTargetOfAnAssignmentAndTheOperandOfNot() throws Throwable {
        assertThat(compiled.invoke("indexed", Signal.RED)).isEqualTo(12);
        assertThat(compiled.invoke("indexed", Signal.GREEN)).isEqualTo(12);
        assertThat(compiled.invoke("notRed", Signal.RED)).isEqualTo(false);
        assertThat(compiled.invoke("notRed", Signal.GREEN)).isEqualTo(true);
        assertThat(compiled.source()).contains("})[0] = 5;").contains("return !switch (v0) {");
    }

    @Test
    void theCaseOfTheSelectorIsTaken() throws Throwable {
        assertThat(compiled.invoke("advice", Signal.RED)).isEqualTo("stop");
        assertThat(compiled.invoke("advice", Signal.AMBER)).isEqualTo("wait");
        assertThat(compiled.invoke("advice", Signal.GREEN)).isEqualTo("go");
    }

    @Test
    void aCaseOfSeveralConstantsAndTheDefault() throws Throwable {
        assertThat(compiled.invoke("mayGo", Signal.RED)).isEqualTo(false);
        assertThat(compiled.invoke("mayGo", Signal.AMBER)).isEqualTo(false);
        assertThat(compiled.invoke("mayGo", Signal.GREEN)).isEqualTo(true);
    }

    @Test
    void aBlockYieldsTheValue() throws Throwable {
        assertThat(compiled.invoke("yielded", Signal.GREEN)).isEqualTo("go");
        assertThat(compiled.invoke("yielded", Signal.RED)).isEqualTo("no");
    }

    @Test
    void aBlockMayThrowInsteadOfYielding() throws Throwable {
        assertThat(compiled.invoke("noAmber", Signal.GREEN)).isEqualTo("fine");
        assertThatIllegalStateException()
                .isThrownBy(() -> compiled.invoke("noAmber", Signal.AMBER))
                .withMessage("amber");
    }

    @Test
    void aSelectorThatIsNullIsANullPointerExceptionAsInJava() {
        assertThatNullPointerException().isThrownBy(() -> compiled.invoke("advice", (Object) null));
    }

    @Test
    void everyResultIsOfTheTypeOfTheSwitch() throws Throwable {
        assertThat(compiled.invoke("mixed", Signal.RED)).isEqualTo("red");
        assertThat(compiled.invoke("mixed", Signal.AMBER)).isEqualTo(1);
        assertThat(compiled.invoke("mixed", Signal.GREEN)).isEqualTo(Signal.GREEN);
        assertThat(compiled.source())
                .contains("case RED -> (Object) \"red\";")
                .contains("yield (Object) Integer.valueOf(1);")
                .contains("default -> (Object) v0;");
    }

    @Test
    void anEnumConstantIsAnExpression() throws Throwable {
        assertThat(compiled.invoke("next", Signal.RED)).isEqualTo(Signal.GREEN);
        assertThat(compiled.invoke("next", Signal.AMBER)).isEqualTo(Signal.RED);
        assertThat(compiled.invoke("isRed", Signal.RED)).isEqualTo(true);
        assertThat(compiled.invoke("isRed", Signal.GREEN)).isEqualTo(false);
        assertThat(compiled.source()).contains("return v0 == Signal.RED;");
    }

    @Test
    void aSwitchIsAnOperandAReceiverASelectorAndACase() throws Throwable {
        assertThat(compiled.invoke("lengthPlusRank", Signal.RED)).isEqualTo(14);
        assertThat(compiled.invoke("lengthPlusRank", Signal.GREEN)).isEqualTo(32);
        assertThat(compiled.invoke("afterNext", Signal.RED)).isEqualTo("go");
        assertThat(compiled.invoke("afterNext", Signal.GREEN)).isEqualTo("was green");
        assertThat(compiled.invoke("afterNext", Signal.AMBER)).isEqualTo("was amber");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aSwitchIsInALambdaAndALambdaInASwitch() throws Throwable {
        assertThat(((Supplier<String>) compiled.invoke("lazy", Signal.RED)).get())
                .isEqualTo("stop");
        assertThat(((Supplier<String>) compiled.invoke("supplier", Signal.RED)).get())
                .isEqualTo("stop");
        assertThat(((Supplier<String>) compiled.invoke("supplier", Signal.AMBER)).get())
                .isEqualTo("go");
    }

    @Test
    void aSwitchInitializesAField() throws Throwable {
        assertThat(compiled.invoke("initial")).isEqualTo("r");
    }

    @Test
    void aBlockAssignsAVariableOfTheCodeAroundTheSwitch() throws Throwable {
        assertThat(compiled.invoke("counted", Signal.RED)).isEqualTo("r1");
        assertThat(compiled.invoke("counted", Signal.GREEN)).isEqualTo("x2");
    }

    @Test
    void aBlockLoopsAndBranches() throws Throwable {
        assertThat(compiled.invoke("looped", Signal.RED)).isEqualTo(3);
        assertThat(compiled.invoke("looped", Signal.AMBER)).isEqualTo(-1);
        assertThat(compiled.invoke("looped", Signal.GREEN)).isEqualTo(-2);
    }

    @Test
    void aCheckedExceptionOfACaseIsDeclaredOrCaughtAroundTheSwitchOrInTheBlock() throws Throwable {
        assertThat(compiled.invoke("reading", Signal.RED)).isEqualTo(-1);
        assertThat(compiled.invoke("reading", Signal.GREEN)).isEqualTo((int) 'g');
        assertThatExceptionOfType(IOException.class)
                .isThrownBy(() -> compiled.invoke("reading", Signal.AMBER))
                .withMessage("amber");

        assertThat(compiled.invoke("caught", Signal.RED)).isEqualTo(-2);
        assertThat(compiled.invoke("caught", Signal.GREEN)).isEqualTo(-1);
    }
}
