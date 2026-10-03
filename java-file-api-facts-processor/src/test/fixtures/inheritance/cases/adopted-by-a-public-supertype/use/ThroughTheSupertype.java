import gen.facts.java.lang.String_;
import gen.facts.p.Mid_;
import gen.facts.p.X_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.X;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// A member `Mid` has adopted is called on `X` through `Mid_`: what the
/// typed layer renders, javac compiles and the JVM runs.
public final class ThroughTheSupertype {
    private ThroughTheSupertype() {}

    public static void aMethodOfTheHiddenInterface(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, X_.TOKEN, x -> call(x, Mid_.run), new X()))
                .isEqualTo("run");
    }

    public static void aConstantOfTheHiddenInterface(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, X_.TOKEN, x -> staticField(Mid_.K), new X()))
                .isEqualTo("k");
    }

    public static void aMethodTheSubtypeOverrides(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, X_.TOKEN, x -> call(x, X_.more), new X()))
                .isEqualTo("x");
    }
}
