import gen.facts.p.Outer_;
import gen.facts.p.Outer_E_;
import gen.facts.p.Outer_Inner_;
import gen.facts.p.Outer_NonStatic_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.type.Types;
import p.Outer;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// A member that mentions nested types refers to their metamodels, which are there without being asked for.
public final class Mentions {
    private Mentions() {}

    public static void aMemberMentioningANestedTypeIsOfItsMetamodel() {
        MethodRef0<Outer, Outer.Inner> inner = Outer_.inner;
        MethodRef0<Outer, Outer.NonStatic> nonStatic = Outer_.nonStatic;
        MethodRef0<Outer, Outer.E> e = Outer_.e;

        assertThat(inner.resultType().orElseThrow().typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Outer$Inner")));
        assertThat(inner.resultType().orElseThrow()).isEqualTo(Outer_Inner_.TOKEN);
        assertThat(nonStatic.resultType().orElseThrow()).isEqualTo(Outer_NonStatic_.TOKEN);
        assertThat(e.resultType().orElseThrow()).isEqualTo(Outer_E_.TOKEN);
    }
}
