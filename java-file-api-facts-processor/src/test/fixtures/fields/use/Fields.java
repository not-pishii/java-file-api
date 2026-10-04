import gen.facts.java.lang.String_;
import gen.facts.p.Impl_;
import gen.facts.p.PubA_;
import gen.facts.p.PubB_;
import gen.facts.p.Sub_;
import gen.facts.p.Super_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Impl;
import p.Sub;

import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// A field fact is of the type that declares the field, and lowering makes
/// javac look the field up there, whatever the type of the receiver has
/// under that name: nothing, as the name is ambiguous in it, or a field of
/// its own, which hides the one of the fact.
public final class Fields {
    private Fields() {}

    public static void anInstanceFieldAmbiguousInTheTypeOfTheReceiverIsReadThroughItsOwner(Typed typed) {
        // impl.k does not compile: k is a field of PubA and of PubB
        assertThat(typed.render(String_.TOKEN, Impl_.TOKEN, impl -> field(impl, PubA_.k)))
                .contains("return ((PubA) v0).k;");
        assertThat(typed.apply(String_.TOKEN, Impl_.TOKEN, impl -> field(impl, PubA_.k), new Impl()))
                .isEqualTo("PubA.k");
        assertThat(typed.apply(String_.TOKEN, String_.TOKEN, s -> field(new_(Impl_.new_), PubA_.k), "a"))
                .isEqualTo("PubA.k");
    }

    public static void aStaticFieldAmbiguousInASubtypeIsReadThroughItsOwner(Typed typed) {
        // Impl.s does not compile either
        assertThat(typed.render(String_.TOKEN, Impl_.TOKEN, impl -> staticField(PubA_.s)))
                .contains("return PubA.s;");
        assertThat(typed.apply(String_.TOKEN, Impl_.TOKEN, impl -> staticField(PubA_.s), new Impl()))
                .isEqualTo("PubA.s");
        assertThat(typed.apply(String_.TOKEN, Impl_.TOKEN, impl -> staticField(PubB_.s), new Impl()))
                .isEqualTo("PubB.s");
        assertThat(typed.apply(String_.TOKEN, Impl_.TOKEN, impl -> staticField(PubB_.k), new Impl()))
                .isEqualTo("PubB.k");
    }

    public static void anInstanceFieldHiddenInTheTypeOfTheReceiverIsReadThroughItsOwner(Typed typed) {
        // sub.f is the field of Sub
        assertThat(typed.render(String_.TOKEN, Sub_.TOKEN, sub -> field(sub, Super_.f)))
                .contains("return ((Super) v0).f;");
        assertThat(typed.apply(String_.TOKEN, Sub_.TOKEN, sub -> field(sub, Super_.f), new Sub()))
                .isEqualTo("Super.f");
        assertThat(typed.apply(String_.TOKEN, Sub_.TOKEN, sub -> field(sub, Sub_.f), new Sub()))
                .isEqualTo("Sub.f");
        assertThat(typed.render(String_.TOKEN, Sub_.TOKEN, sub -> field(sub, Sub_.f)))
                .contains("return v0.f;");
    }

    public static void aStaticFieldHiddenInASubtypeIsReadThroughItsOwner(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Sub_.TOKEN, sub -> staticField(Super_.g), new Sub()))
                .isEqualTo("Super.g");
        assertThat(typed.apply(String_.TOKEN, Sub_.TOKEN, sub -> staticField(Sub_.g), new Sub()))
                .isEqualTo("Sub.g");
    }
}
