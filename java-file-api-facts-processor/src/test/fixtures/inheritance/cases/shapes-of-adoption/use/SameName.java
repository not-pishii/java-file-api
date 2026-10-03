import gen.facts.java.lang.String_;
import gen.facts.p.PubSame_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.PubSame;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// The hidden `HSame` has a `static tag(int)`, an instance `tag()` and a
/// constant `TAG`: `PubSame` adopts each as what it is.
public final class SameName {
    private SameName() {}

    public static void aStaticAndAnInstanceMethodOfOneNameAreTwoFacts(Typed typed) {
        StaticMethodRef1<String, Prim.Int> ofTheClass = PubSame_.tag_int;
        MethodRef0<PubSame, String> ofAnInstance = PubSame_.tag;

        assertThat(typed.apply(
                        String_.TOKEN, PubSame_.TOKEN, same -> staticCall(ofTheClass, literal(1)), new PubSame()))
                .isEqualTo("static");
        assertThat(typed.apply(String_.TOKEN, PubSame_.TOKEN, same -> call(same, ofAnInstance), new PubSame()))
                .isEqualTo("instance");
    }

    public static void theConstantIsReadThroughTheSubtype(Typed typed) {
        StaticFieldRef<String> constant = PubSame_.TAG;

        assertThat(typed.render(String_.TOKEN, PubSame_.TOKEN, same -> staticField(constant)))
                .contains("PubSame.TAG");
        assertThat(typed.apply(String_.TOKEN, PubSame_.TOKEN, same -> staticField(constant), new PubSame()))
                .isEqualTo("field");
    }
}
