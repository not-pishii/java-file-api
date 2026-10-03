import gen.facts.p.Op_;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import p.Op;

import static org.assertj.core.api.Assertions.assertThat;

/// An enum with constant bodies is not final: its methods may be overridden, or are abstract.
public final class Bodies {
    private Bodies() {}

    public static void anEnumWithConstantBodiesIsStillAnEnumToken() {
        EnumToken<Op> token = Op_.TOKEN;

        assertThat(token.constants()).containsExactly("ADD", "SUB");
        assertThat(Op_.Data.SHAPE.enumConstants()).containsExactly("ADD", "SUB");
    }

    public static void anAbstractMethodIsAbstract() {
        assertThat(Op_.apply_int_int.traits()).isEqualTo(MemberTraits.ABSTRACT);
        assertThat(Op_.apply_int_int.resultType().orElseThrow()).isSameAs(PrimitiveToken.INT);
        assertThat(Op_.apply_int_int.params().get(1).typeRef()).isEqualTo(PrimitiveTypeRef.INT);
    }

    public static void aConcreteMethodMayBeOverridden() {
        assertThat(Op_.twice_int.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
    }
}
