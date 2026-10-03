import gen.facts.java.lang.String_;
import gen.facts.p.Op_;
import gen.facts.p.Source_;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.type.Types;
import p.Op;
import p.Source;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// A generic functional interface has its `sam`, in terms of the token
/// its metamodel is made with.
public final class Sams {
    private Sams() {}

    public static void aGenericFunctionalInterfaceHasASam() {
        Source_<String> source = new Source_<>(String_.TOKEN);
        Sam0<Source<String>, String> sam = source.sam;

        assertThat(sam.method()).isSameAs(source.next);
        assertThat(sam.owner()).isSameAs(source.token);
        assertThat(sam.result()).isSameAs(String_.TOKEN);
        assertThat(sam.method().traits().throwsTypes())
                .extracting(RefToken::typeRef)
                .containsExactly(Types.of(ClassDesc.of("java.io.IOException")));
    }

    /// `Op<T> extends Function<T, T>` declares nothing: its `sam` is
    /// `Function.apply` as a member of `Op<T>`, with `T` for both of its
    /// type arguments.
    public static void theSamOfAnInterfaceThatInheritsItsMethodIsInItsOwnTerms() {
        Op_<String> op = new Op_<>(String_.TOKEN);
        Sam1<Op<String>, String, String> sam = op.sam;

        assertThat(sam.method().name()).isEqualTo("apply");
        assertThat(sam.owner()).isSameAs(op.token);
        assertThat(sam.result()).isSameAs(String_.TOKEN);
        assertThat(sam.param1()).isSameAs(String_.TOKEN);
        assertThat(sam.method().traits()).isEqualTo(MemberTraits.ABSTRACT);
    }
}
