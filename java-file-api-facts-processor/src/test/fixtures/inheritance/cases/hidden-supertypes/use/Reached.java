import gen.facts.java.lang.String_;
import gen.facts.p.PubApi_;
import gen.facts.p.Pub_;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Pub;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// An adopted member is reached through `Pub` from another package, where
/// `Near`, `Far` and `HiddenApi` cannot be named: what the typed layer
/// renders, javac compiles and the JVM runs.
public final class Reached {
    private Reached() {}

    public static void anAdoptedMethod(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> call(pub, Pub_.near), new Pub()))
                .isEqualTo("near");
    }

    public static void anAdoptedMethodTheNearerSupertypeOverrides(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> call(pub, Pub_.overridden), new Pub()))
                .isEqualTo("near");
    }

    public static void anAdoptedMethodOfTheTypeArgument(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> call(pub, Pub_.get), new Pub()))
                .isNull();
    }

    public static void anAdoptedField(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> field(pub, Pub_.item), new Pub()))
                .isNull();
    }

    public static void anAdoptedDefaultMethod(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> call(pub, Pub_.dflt), new Pub()))
                .isEqualTo("dflt");
    }

    /// `PubApi`, beyond the hidden supertypes, is `public` and has a metamodel of its own.
    public static void aMethodOfThePublicSupertypeBeyond(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> call(pub, PubApi_.beyond), new Pub()))
                .isEqualTo("beyond");
    }

    // javac lets code of another package name the static ones through Pub alone: Pub.snear(), Pub.HID

    public static void anAdoptedStaticMethod(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> staticCall(Pub_.snear), new Pub()))
                .isEqualTo("snear");
    }

    public static void anAdoptedStaticMethodOfTheFartherSupertype(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> staticCall(Pub_.sfar), new Pub()))
                .isEqualTo("sfar");
    }

    public static void anAdoptedConstant(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> staticField(Pub_.HID), new Pub()))
                .isEqualTo("near");
    }

    public static void anAdoptedConstantOfAnInterface(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> staticField(Pub_.CONST), new Pub()))
                .isEqualTo("const");
    }

    public static void anAdoptedStaticField(Typed typed) {
        assertThat(typed.apply(PrimitiveToken.INT, Pub_.TOKEN, pub -> staticField(Pub_.counter), new Pub()))
                .isEqualTo(5);
    }
}
