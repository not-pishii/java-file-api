import gen.facts.java.lang.String_;
import gen.facts.p.Day_;
import gen.facts.p.Mode_;
import gen.facts.p.Plan_;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.typed.Expr;
import p.Day;
import p.Mode;

import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A `switch` of the typed layer over an enum is exhaustive by the facts of the enum, and the facts are those of
/// the target classpath or the `switch` is not rendered: an enum that has got a constant, lost one or has them in
/// another order than the metamodel fails lowering, whether the `switch` has a `default` or not.
public final class Switches {
    private Switches() {}

    /// `switch (Day.valueOf(name)) { case MON -> "m"; case TUE -> "t"; case WED -> "w"; }`: every constant of the facts.
    private static Expr<String> exhaustive(Expr<String> name) {
        return switch_(Day_.TOKEN, staticCall(Day_.valueOf_String, name), String_.TOKEN, c -> c.case_(
                        Day_.MON, literal("m"))
                .case_(Day_.TUE, literal("t"))
                .case_(Day_.WED, literal("w")));
    }

    /// `switch (Day.valueOf(name)) { case MON -> "m"; default -> "other"; }`.
    private static Expr<String> withDefault(Expr<String> name) {
        return switch_(Day_.TOKEN, staticCall(Day_.valueOf_String, name), String_.TOKEN, c -> c.case_(
                        Day_.MON, literal("m"))
                .default_(literal("other")));
    }

    public static void aSwitchIsExhaustiveByTheConstantsOfTheFacts(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, String_.TOKEN, Switches::exhaustive, "TUE"))
                .isEqualTo("t");
        assertThat(typed.apply(String_.TOKEN, String_.TOKEN, Switches::withDefault, "WED"))
                .isEqualTo("other");
        assertThat(typed.verified(String_.TOKEN, String_.TOKEN, Switches::exhaustive))
                .contains("p.Day: unchanged");
        assertThat(typed.render(String_.TOKEN, String_.TOKEN, Switches::exhaustive))
                .contains("case MON -> \"m\";")
                .doesNotContain("default");
    }

    public static void aSwitchOverTheParameterTakesItsCase(Typed typed) {
        assertThat(typed.apply(
                        PrimitiveToken.INT,
                        Day_.TOKEN,
                        day -> switch_(Day_.TOKEN, day, PrimitiveToken.INT, c -> c.case_(Day_.MON, literal(1))
                                .case_(Day_.TUE, y -> y.yield_(literal(2)))
                                .default_(literal(3))),
                        Day.TUE))
                .isEqualTo(2);
        // the Day of the rendered code is of the loader it is run with
        assertThat(typed.apply(Day_.TOKEN, String_.TOKEN, _ -> enumConstant(Day_.WED), "any"))
                .hasToString("WED");
    }

    public static void aConstantTheTargetHasGotFailsTheSwitchThatWasExhaustive(Typed typed) {
        // to javac the switch would no longer cover every value of `enum Day { MON, TUE, WED, THU }`
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() ->
                        typed.against("added-constant").render(String_.TOKEN, String_.TOKEN, Switches::exhaustive))
                .withMessageContaining("metamodel gen.facts.p.Day_ does not match p.Day on the target classpath")
                .withMessageContaining(
                        """
                          changed: enum
                            generated against: MON; TUE; WED
                            target: MON; TUE; WED; THU
                        """);
    }

    public static void aConstantTheTargetHasLostFailsTheSwitch(Typed typed) {
        // to javac `case WED` would be a label of no constant
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() ->
                        typed.against("removed-constant").render(String_.TOKEN, String_.TOKEN, Switches::exhaustive))
                .withMessageContaining("    target: MON; TUE\n");
    }

    public static void constantsInAnotherOrderFailTheSwitch(Typed typed) {
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> typed.against("reordered-constants")
                        .render(String_.TOKEN, String_.TOKEN, Switches::exhaustive))
                .withMessageContaining("    target: TUE; MON; WED\n");
    }

    public static void aSwitchWithADefaultIsOfTheFactsOfTheTargetToo(Typed typed) {
        // the facts of the enum are those of the target or none, whether the switch leans on its constants or not
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() ->
                        typed.against("added-constant").render(String_.TOKEN, String_.TOKEN, Switches::withDefault))
                .withMessageContaining("changed: enum");
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> typed.against("added-constant")
                        .render(Day_.TOKEN, String_.TOKEN, _ -> enumConstant(Day_.WED)))
                .withMessageContaining("changed: enum");
    }

    // ---- an enum that is only mentioned: its metamodel has the token, and the token has the constants

    /// `switch (Plan.mode(true)) { case FAST -> "f"; case SLOW -> "s"; }`.
    private static Expr<String> ofMode(Expr<String> ignored) {
        EnumToken<Mode> mode = Mode_.TOKEN;
        return switch_(mode, staticCall(Plan_.mode_boolean, literal(true)), String_.TOKEN, c -> c.case_(
                        mode.constant("FAST"), literal("f"))
                .case_(mode.constant("SLOW"), literal("s")));
    }

    public static void aTokenOnlyMetamodelOfAnEnumHasItsConstantsByName(Typed typed) {
        assertThat(Mode_.TOKEN.constants()).containsExactly("FAST", "SLOW");
        assertThat(typed.apply(String_.TOKEN, String_.TOKEN, Switches::ofMode, "any"))
                .isEqualTo("f");
        assertThatExceptionOfType(FactLookupException.class).isThrownBy(() -> Mode_.TOKEN.constant("IDLE"));
    }

    public static void aSwitchOverATokenOnlyEnumIsExhaustiveByItsFactsToo(Typed typed) {
        assertThatIllegalStateException()
                .isThrownBy(() -> typed.render(
                        String_.TOKEN,
                        String_.TOKEN,
                        _ -> switch_(
                                Mode_.TOKEN,
                                staticCall(Plan_.mode_boolean, literal(true)),
                                String_.TOKEN,
                                c -> c.case_(Mode_.TOKEN.constant("FAST"), literal("f")))))
                .withMessageContaining("is not exhaustive: p.Mode has the constants SLOW, which have no case_");
    }

    public static void aTokenOnlyEnumIsHeldAgainstTheTargetAsAnyOther(Typed typed) {
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> typed.against("added-mode").render(String_.TOKEN, String_.TOKEN, Switches::ofMode))
                .withMessageContaining("metamodel gen.facts.p.Mode_ does not match p.Mode on the target classpath")
                .withMessageContaining("    target: FAST; SLOW; IDLE\n");
    }
}
