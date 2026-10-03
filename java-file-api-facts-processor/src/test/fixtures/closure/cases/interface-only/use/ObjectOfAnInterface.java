import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.IfaceOnly_;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Named;

import static me.supcheg.javafile.typed.Expressions.call;
import static org.assertj.core.api.Assertions.assertThat;

/// `Object` is a supertype of an interface too (JLS 9.2): a request for an
/// interface alone brings the full `Object_`, and its methods are called on
/// a value of the interface.
public final class ObjectOfAnInterface {
    private ObjectOfAnInterface() {}

    public static void toStringOfAnInterface(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, IfaceOnly_.TOKEN, value -> call(value, Object_.toString), new Named()))
                .isEqualTo("named");
    }

    public static void hashCodeOfAnInterface(Typed typed) {
        Named named = new Named();

        assertThat(typed.apply(PrimitiveToken.INT, IfaceOnly_.TOKEN, value -> call(value, Object_.hashCode), named))
                .isEqualTo(named.hashCode());
    }
}
