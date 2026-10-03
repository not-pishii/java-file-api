import gen.facts.java.lang.String_;
import gen.facts.p.Kept_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Kept;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// `Kept extends BadMid<Secret> implements HiddenI`, and `BadMid`
/// implements `HiddenI` too, but has no full metamodel — a bound of its
/// type parameter is not `public` — so it tells no member of `HiddenI`:
/// they stay facts of `Kept`, or nothing would reach them.
public final class KeptByTheSubtype {
    private KeptByTheSubtype() {}

    public static void aMethodOfTheHiddenInterface(Typed typed) {
        MethodRef0<Kept, String> run = Kept_.run;

        assertThat(typed.apply(String_.TOKEN, Kept_.TOKEN, kept -> call(kept, run), new Kept()))
                .isEqualTo("run");
    }

    public static void aConstantOfTheHiddenInterface(Typed typed) {
        StaticFieldRef<String> k = Kept_.K;

        assertThat(typed.apply(String_.TOKEN, Kept_.TOKEN, kept -> staticField(k), new Kept()))
                .isEqualTo("k");
    }
}
