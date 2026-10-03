import gen.facts.p.Outer_Deep_Deeper_;
import gen.facts.p.Outer_Inner_;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.type.Types;
import p.Outer;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// `Outer.Inner` is `Outer_Inner_`, `Outer.Deep.Deeper` is `Outer_Deep_Deeper_`: the chain of the names.
public final class Named {
    private Named() {}

    public static void aNestedStaticTypeIsNamedByItsChainAndTokenedByItsBinaryName() {
        OpenClassToken<Outer.Inner> inner = Outer_Inner_.TOKEN;
        OpenClassToken<Outer.Deep.Deeper> deeper = Outer_Deep_Deeper_.TOKEN;

        assertThat(inner.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Outer$Inner")));
        assertThat(deeper.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Outer$Deep$Deeper")));
    }

    public static void aNestedStaticTypeHasItsFacts() {
        CtorRef0<Outer.Inner> constructor = Outer_Inner_.new_;
        MethodRef0<Outer.Inner, String> s = Outer_Inner_.s;
        CtorRef0<Outer.Deep.Deeper> deeper = Outer_Deep_Deeper_.new_;

        assertThat(constructor.owner()).isSameAs(Outer_Inner_.TOKEN);
        assertThat(s.owner()).isEqualTo(Outer_Inner_.TOKEN);
        assertThat(deeper.owner()).isSameAs(Outer_Deep_Deeper_.TOKEN);
    }
}
