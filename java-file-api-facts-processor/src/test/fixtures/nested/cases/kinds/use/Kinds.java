import gen.facts.p.Outer_Api_;
import gen.facts.p.Outer_E_;
import gen.facts.p.Outer_R_;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.VoidSam0;
import p.Outer;

import static org.assertj.core.api.Assertions.assertThat;

/// Nested interfaces, enums and records are types too: tokens of their own kind.
public final class Kinds {
    private Kinds() {}

    public static void aNestedInterfaceIsAnInterfaceWithItsSam() {
        InterfaceToken<Outer.Api> token = Outer_Api_.TOKEN;
        VoidSam0<Outer.Api> sam = Outer_Api_.sam;

        assertThat(sam.owner()).isSameAs(token);
    }

    public static void aNestedEnumIsAnEnumWithItsConstants() {
        EnumToken<Outer.E> token = Outer_E_.TOKEN;

        assertThat(token.constants()).containsExactly("A", "B");
        assertThat(Outer_E_.A.owner()).isSameAs(token);
    }

    public static void aNestedRecordIsAFinalClassWithItsConstructorAndComponents() {
        FinalClassToken<Outer.R> token = Outer_R_.TOKEN;
        CtorRef1<Outer.R, Prim.Int> constructor = Outer_R_.new_int;
        MethodRef0<Outer.R, Prim.Int> x = Outer_R_.x;

        assertThat(constructor.owner()).isSameAs(token);
        assertThat(x.name()).isEqualTo("x");
    }
}
