import gen.facts.java.lang.String_;
import gen.facts.p.PubOver_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.PubOver;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// `PubOver` declares `take(String)` and adopts `take(Object)` of the
/// hidden `HOver`: both are facts of `PubOver_`, whose method table has
/// both, so the typed layer tells them apart for a `String`.
public final class Overload {
    private Overload() {}

    public static void theAdoptedOverloadIsCalledWithACast(Typed typed) {
        assertThat(typed.render(
                        String_.TOKEN, PubOver_.TOKEN, over -> call(over, PubOver_.take_Object, literal("x"))))
                .contains("v0.take((Object) \"x\")");
        assertThat(typed.apply(
                        String_.TOKEN,
                        PubOver_.TOKEN,
                        over -> call(over, PubOver_.take_Object, literal("x")),
                        new PubOver()))
                .isEqualTo("object");
    }

    public static void theDeclaredOverloadIsCalledAsIs(Typed typed) {
        assertThat(typed.apply(
                        String_.TOKEN,
                        PubOver_.TOKEN,
                        over -> call(over, PubOver_.take_String, literal("x")),
                        new PubOver()))
                .isEqualTo("string");
    }
}
