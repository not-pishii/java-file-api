import gen.facts.java.lang.String_;
import gen.facts.p.Impl2_;
import gen.facts.p.Impl_;
import gen.facts.p.PubBase_;
import gen.facts.p.PubConst_;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Impl;
import p.Impl2;

import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// `Impl extends HConst implements PubConst`, and both declare `K` and
/// `name`: `Impl.K` is ambiguous to javac, so `Impl_` has no such fact. The
/// field of the `public` supertype is read through its own metamodel.
public final class Unambiguous {
    private Unambiguous() {}

    public static void aFieldOnlyTheHiddenSupertypeHasIsAdopted(Typed typed) {
        StaticFieldRef<String> only = Impl_.ONLY;

        assertThat(typed.apply(String_.TOKEN, Impl_.TOKEN, impl -> staticField(only), new Impl()))
                .isEqualTo("only");
    }

    public static void theFieldOfThePublicInterfaceIsReadThroughIt(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Impl_.TOKEN, impl -> staticField(PubConst_.K), new Impl()))
                .isEqualTo("public");
    }

    /// `Impl2 extends PubBase implements HIface`.
    public static void theFieldOfThePublicSuperclassIsReadThroughIt(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Impl2_.TOKEN, impl -> staticField(PubBase_.K), new Impl2()))
                .isEqualTo("public");
    }
}
