package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.fixtures.Figure;
import me.supcheg.javafile.typed.testfacts.java.lang.IllegalStateException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.annotation.RetentionPolicy_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Disc_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Figure_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Piece_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Square_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.function.Function;

import static me.supcheg.javafile.typed.ExpressionArities.new_;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.switchPattern;
import static me.supcheg.javafile.typed.JavacVerdict.asJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks of the pattern `switch` (JLS 15.28): the cases
/// are subtypes of the selector's type, no case is dominated by one before
/// it, and the cases cover a sealed hierarchy — or an enum — without a
/// `default_`. Each is rejected where the case or the `switch` is built, and
/// told against javac ([JavacVerdict]).
class PatternSwitchChecksTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile declare(Function<TypedClassBuilder<Object>, TypedClassBodies<Object>> members) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(members));
    }

    /// Declares `static String describe(Figure f)` whose value is the `switch` `cases` adds.
    private static JavaFile describe(java.util.function.Consumer<SealedSwitchCases<Figure, String>> cases) {
        return declare(cb -> {
            var describe = cb.declareStaticMethod("describe", String_.TOKEN, Figure_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(
                    describe, (b, figure) -> b.return_(switchPattern(Figure_.TOKEN, figure, String_.TOKEN, cases)));
            return bodies;
        });
    }

    @Test
    void aSwitchCoversEveryPermittedSubtypeOrHasADefault() {
        classAsJavac(
                IllegalStateException.class,
                () -> describe(c -> c.case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")))
                        .case_(Square_.TOKEN, (body, square) -> body.yield_(literal("square")))),
                "is not exhaustive: me.supcheg.javafile.typed.fixtures.Figure permits"
                        + " me.supcheg.javafile.typed.fixtures.Piece, which no case_ covers, and there is no"
                        + " default_",
                "",
                "static String describe(Figure f) { return switch (f) { case Disc d -> \"disc\";"
                        + " case Square s -> \"square\"; }; }",
                "the switch expression does not cover all possible input values");
    }

    @Test
    void aCaseAfterABroaderOneIsDominated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> describe(c -> c.case_(Figure_.TOKEN, (body, figure) -> body.yield_(literal("f")))
                        .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")))))
                .withMessageContaining(
                        "is dominated by the case_ of the pattern" + " me.supcheg.javafile.typed.fixtures.Figure");
    }

    @Test
    void aTypePatternOfTheSameTypeIsDominated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> describe(c -> c.case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")))
                        .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("another disc")))))
                .withMessageContaining(
                        "is dominated by the case_ of the pattern" + " me.supcheg.javafile.typed.fixtures.Disc");
    }

    @Test
    void anUnconditionalPatternAndADefaultAreRefusedTogether() {
        classAsJavac(
                IllegalStateException.class,
                () -> describe(c -> c.case_(Figure_.TOKEN, (body, figure) -> body.yield_(literal("f")))
                        .default_(literal("other"))),
                "has both an unconditional pattern and a default_",
                "",
                "static String describe(Figure f) { return switch (f) { case Figure x -> \"f\";"
                        + " default -> \"other\"; }; }",
                "switch has both an unconditional pattern and a default label");
    }

    @Test
    void aCaseIsNoPatternOfTheSelectorItDoesNotHave() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> describe(c -> c.case_(String_.TOKEN, (body, text) -> body.yield_(literal("text")))),
                "is no subtype of the selector's type me.supcheg.javafile.typed.fixtures.Figure",
                "",
                "static String describe(Figure f) { return switch (f) { case String s -> \"text\";"
                        + " default -> \"other\"; }; }",
                "incompatible types: me.supcheg.javafile.typed.fixtures.Figure cannot be converted to"
                        + " java.lang.String");
    }

    @Test
    void aCaseAfterTheDefaultIsRefused() {
        classAsJavac(
                IllegalStateException.class,
                () -> describe(c -> {
                    c.default_(literal("other"));
                    c.case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")));
                }),
                "after its default_: the default_ case is the last",
                "",
                "static String describe(Figure f) { return switch (f) { default -> \"other\";"
                        + " case Disc d -> \"disc\"; }; }",
                "this case label is dominated by a preceding case label");
    }

    @Test
    void aConstantIsACaseOfAnEnumSelector() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> describe(c -> c.case_(RetentionPolicy_.SOURCE, literal("source"))))
                .withMessageContaining(
                        "is a constant, and the selector of a pattern switch that takes one is" + " over an enum");
    }

    @Test
    void aConstantIsOfTheFactOfTheEnumTheSwitchIsOver() {
        EnumToken<Object> other = UnsafeFacts.enumToken(
                Types.of(ClassDesc.of("me.supcheg.javafile.typed.fixtures", "Signal")),
                List.of("RED", "AMBER", "GREEN"),
                MethodTable.EMPTY);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> declare(cb -> {
                    var value = cb.declareStaticMethod("value", String_.TOKEN, Signal_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(
                            value,
                            (b, signal) -> b.return_(switchPattern(
                                    Signal_.TOKEN,
                                    signal,
                                    String_.TOKEN,
                                    c -> c.case_(other.constant("RED"), literal("stop"))
                                            .default_(literal("go")))));
                    return bodies;
                }))
                .withMessageContaining("is not a constant of the fact of"
                        + " me.supcheg.javafile.typed.fixtures.Signal the switch is over");
    }

    @Test
    void aSwitchOverATypeThatIsNeitherSealedNorAnEnumNeedsADefault() {
        classAsJavac(
                IllegalStateException.class,
                () -> declare(cb -> {
                    var value = cb.declareStaticMethod("value", String_.TOKEN, Object_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(
                            value,
                            (b, object) -> b.return_(switchPattern(
                                    Object_.TOKEN,
                                    object,
                                    String_.TOKEN,
                                    c -> c.case_(String_.TOKEN, (body, text) -> body.yield_(literal("text"))))));
                    return bodies;
                }),
                "the type java.lang.Object of its selector is neither sealed nor an enum",
                "",
                "static String value(Object o) { return switch (o) { case String s -> \"text\"; }; }",
                "the switch expression does not cover all possible input values");
    }

    @Test
    void aSwitchWithoutAResultIsRefused() {
        classAsJavac(
                IllegalStateException.class,
                () -> describe(c -> c.case_(Disc_.TOKEN, (body, disc) -> body.throw_(new_(IllegalStateException_.new_)))
                        .case_(Square_.TOKEN, (body, square) -> body.throw_(new_(IllegalStateException_.new_)))
                        .case_(Piece_.TOKEN, (body, piece) -> body.throw_(new_(IllegalStateException_.new_)))),
                "has no result: every case is a block that never yields",
                "",
                "static String describe(Figure f) { return switch (f) { case Disc d ->"
                        + " throw new IllegalStateException(); case Square s ->"
                        + " throw new IllegalStateException(); case Piece p ->"
                        + " throw new IllegalStateException(); }; }",
                "switch expression does not have any result expressions");
    }

    @Test
    void aCaseIsAddedByTheCasesOfTheSwitchOnly() {
        assertThatIllegalStateException()
                .isThrownBy(() -> describe(c -> c.case_(Disc_.TOKEN, (body, disc) -> {
                    c.case_(Square_.TOKEN, (inner, square) -> inner.yield_(literal("square")));
                    return body.yield_(literal("disc"));
                })))
                .withMessageContaining("while its case_ me.supcheg.javafile.typed.fixtures.Disc is being built");
    }

    @Test
    void aSwitchIsMadeOnce() {
        java.util.concurrent.atomic.AtomicReference<SealedSwitchCases<Figure, String>> kept =
                new java.util.concurrent.atomic.AtomicReference<>();
        java.util.concurrent.atomic.AtomicReference<Node.Operand> selector =
                new java.util.concurrent.atomic.AtomicReference<>();
        describe(c -> {
            selector.set(Expressions.operand(Expressions.enumConstant(
                    me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_.RED)));
            c.default_(literal("other"));
            kept.set(c);
        });
        assertThatIllegalStateException()
                .isThrownBy(() -> kept.get().close(selector.get()))
                .withMessageContaining("is made twice");
    }

    @Test
    void aSwitchWithNoCaseIsRefused() {
        assertThatIllegalStateException()
                .isThrownBy(() -> describe(c -> {}))
                .withMessageContaining("has no case: add a pattern case of every value of the selector");
    }

    @Test
    void aSwitchIsNotMadeWhileItsCaseIsBuilt() {
        assertThatIllegalStateException()
                .isThrownBy(() -> describe(c -> c.case_(Disc_.TOKEN, (body, disc) -> {
                    c.close(Expressions.operand(disc));
                    return body.yield_(literal("disc"));
                })))
                .withMessageContaining(
                        "is made while its case_ me.supcheg.javafile.typed.fixtures.Disc is being" + " built");
    }

    @Test
    void aCaseIsNotAddedAfterTheSwitchIsMade() {
        java.util.concurrent.atomic.AtomicReference<SealedSwitchCases<Figure, String>> kept =
                new java.util.concurrent.atomic.AtomicReference<>();
        describe(c -> {
            c.default_(literal("other"));
            kept.set(c);
        });
        assertThatIllegalStateException()
                .isThrownBy(() -> kept.get().case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc"))))
                .withMessageContaining("after the switch was made");
    }

    @Test
    void aSecondNullCaseIsDominated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> describe(c -> c.caseNull(literal("nothing")).caseNull(literal("nothing again"))))
                .withMessageContaining("is dominated by the case_ null");
    }

    @Test
    void aSecondConstantCaseIsDominated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> declare(cb -> {
                    var value = cb.declareStaticMethod("value", String_.TOKEN, Signal_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(
                            value,
                            (b, signal) -> b.return_(switchPattern(
                                    Signal_.TOKEN,
                                    signal,
                                    String_.TOKEN,
                                    c -> c.case_(Signal_.RED, literal("stop"))
                                            .case_(Signal_.RED, literal("halt"))
                                            .default_(literal("go")))));
                    return bodies;
                }))
                .withMessageContaining("is dominated by the case_ of the constant RED");
    }

    @Test
    void aSwitchOfAFieldInitializerIsToldWithoutABodyItIsIn() {
        asJavac(
                () -> declare(cb -> {
                    var f = cb.declareStaticField("f", String_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.initialize(
                            f,
                            switchPattern(
                                    Signal_.TOKEN,
                                    Expressions.enumConstant(Signal_.RED),
                                    String_.TOKEN,
                                    c -> c.case_(Signal_.RED, literal("stop"))
                                            .case_(Signal_.RED, literal("halt"))
                                            .default_(literal("go"))));
                    return bodies;
                }),
                "case_ RED of the switch_ over me.supcheg.javafile.typed.fixtures.Signal is dominated by the"
                        + " case_ of the constant RED",
                "String f = switch (Signal.RED) { case RED -> \"stop\"; case RED -> \"halt\";"
                        + " default -> \"go\"; };",
                "duplicate case label");
    }

    @Test
    void aSealedTypeThatTellsNoPermittedSubtypeIsProvenByNoCase() {
        TypeShape<DeclaredKind.Interface> shape = UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("me.supcheg.example", "Empty_"), "0".repeat(64), () -> ""),
                DeclaredKind.INTERFACE,
                ClassDesc.of("me.supcheg.example", "Empty"),
                List.of(),
                List.of(),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                true,
                List.of(),
                List.of());
        InterfaceToken<Object> empty = UnsafeFacts.interfaceToken(shape);
        assertThatIllegalStateException()
                .isThrownBy(() -> declare(cb -> {
                    var value = cb.declareStaticMethod("value", String_.TOKEN, empty);
                    var bodies = cb.bodies();
                    bodies.define(
                            value,
                            (b, it) -> b.return_(
                                    switchPattern(empty, it, String_.TOKEN, c -> c.caseNull(literal("nothing")))));
                    return bodies;
                }))
                .withMessageContaining("tell no subtype it permits");
    }

    @Test
    void anEnumWithoutADefaultHasACaseOfEveryConstant() {
        assertThatIllegalStateException()
                .isThrownBy(() -> declare(cb -> {
                    var value = cb.declareStaticMethod("value", String_.TOKEN, Signal_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(
                            value,
                            (b, signal) -> b.return_(switchPattern(
                                    Signal_.TOKEN,
                                    signal,
                                    String_.TOKEN,
                                    c -> c.caseNull(literal("nothing")).case_(Signal_.RED, literal("stop")))));
                    return bodies;
                }))
                .withMessageContaining("has the constants AMBER, GREEN, which have no case_");
    }

    @Test
    void aPermittedSubtypeIsCoveredByAPatternCaseAlone() {
        assertThatIllegalStateException()
                .isThrownBy(() -> describe(c -> c.caseNull(literal("nothing"))
                        .case_(Disc_.TOKEN, (body, disc) -> body.yield_(literal("disc")))
                        .case_(Square_.TOKEN, (body, square) -> body.yield_(literal("square")))
                        .case_(
                                Piece_.TOKEN,
                                (body, piece) -> literal(true),
                                (body, piece) -> body.yield_(literal("piece")))))
                .withMessageContaining("permits me.supcheg.javafile.typed.fixtures.Piece");
    }

    @Test
    void aPrimitivePatternIsNoSubtypeOfASelector() {
        var number = Patterns.binding(PrimitiveToken.INT);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> describe(c -> c.case_(Patterns.type(number), body -> body.yield_(literal("int")))))
                .withMessageContaining("is no subtype of the selector's type");
    }
}
