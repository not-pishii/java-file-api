import gen.facts.p.StrFn_;
import gen.facts.p.StrOp_;
import gen.facts.p.Sub2_;
import gen.facts.p.Sub_;
import gen.facts.p.Wide_;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.type.Types;

import static org.assertj.core.api.Assertions.assertThat;

/// Only the declared members of a type are facts, but `sam` is there whether its method is declared or inherited.
public final class Inherited {
    private Inherited() {}

    public static void aSamIsThereWhenTheAbstractMethodIsInherited() {
        samOfApply(Sub_.sam, Sub_.TOKEN);
        samOfApply(Sub2_.sam, Sub2_.TOKEN);
        samOfApply(StrFn_.sam, StrFn_.TOKEN);
        samOfApply(StrOp_.sam, StrOp_.TOKEN);
    }

    private static <T> void samOfApply(Sam1<T, String, String> sam, InterfaceToken<T> owner) {
        assertThat(sam.method().name()).isEqualTo("apply");
        assertThat(sam.owner()).isEqualTo(owner);
        assertThat(sam.method().owner()).isEqualTo(owner);
        assertThat(sam.method().traits()).isEqualTo(MemberTraits.ABSTRACT);
        assertThat(sam.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(sam.param1().typeRef()).isEqualTo(Types.STRING);
    }

    /// `a` is a default method, so the abstract one is `b`, inherited.
    public static void anInheritedAbstractMethodMayBeVoid() {
        VoidSam0<?> sam = Wide_.sam;

        assertThat(sam.method().name()).isEqualTo("b");
    }
}
