import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.Base_;
import gen.facts.p.Derived_;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Derived;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;

/// A member `Derived` inherits is called on it through the metamodel of
/// the supertype that declares it, which nobody asked for: what the typed
/// layer renders, javac compiles and the JVM runs.
public final class Inherited {
    private Inherited() {}

    public static void aMethodOfTheSuperclass(Typed typed) {
        assertThat(typed.apply(PrimitiveToken.INT, Derived_.TOKEN, derived -> call(derived, Base_.f), new Derived()))
                .isEqualTo(0);
    }

    public static void aStaticMethodOfTheSuperclass(Typed typed) {
        assertThat(typed.apply(PrimitiveToken.INT, Derived_.TOKEN, derived -> staticCall(Base_.sbase), new Derived()))
                .isEqualTo(0);
    }

    public static void aMethodOfObject(Typed typed) {
        assertThat(typed.apply(
                        PrimitiveToken.INT, Derived_.TOKEN, derived -> call(derived, Object_.hashCode), new Derived()))
                .isInstanceOf(Integer.class);
    }

    public static void aMethodOfObjectThatNothingOverrides(Typed typed) {
        assertThat(typed.apply(
                        String_.TOKEN, Derived_.TOKEN, derived -> call(derived, Object_.toString), new Derived()))
                .asString()
                .startsWith("p.Derived@");
    }
}
