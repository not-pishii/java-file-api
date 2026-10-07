import gen.facts.p.Abs_;
import gen.facts.p.Fin_;
import gen.facts.p.Greeter_;
import gen.facts.p.Iface_;
import gen.facts.p.Open_;
import gen.facts.p.Rec_;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.SuperCtorRef2;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.type.Types;
import p.Abs;
import p.Fin;
import p.Greeter;
import p.Iface;
import p.Open;
import p.Rec;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// The token of a type is of the family of its kind, and so are the facts
/// of its constructors.
public final class Kinds {
    private Kinds() {}

    public static void typesOfEveryKindGetTheirTokens() {
        FinalClassToken<Fin> fin = Fin_.TOKEN;
        OpenClassToken<Open> open = Open_.TOKEN;
        OpenClassToken<Greeter> greeter = Greeter_.TOKEN;
        AbstractClassToken<Abs> abs = Abs_.TOKEN;
        InterfaceToken<Iface> iface = Iface_.TOKEN;
        FinalClassToken<Rec> rec = Rec_.TOKEN;

        assertThat(fin.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Fin")));
        assertThat(open.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Open")));
        assertThat(greeter.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Greeter")));
        assertThat(abs.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Abs")));
        assertThat(iface.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Iface")));
        assertThat(rec.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Rec")));
    }

    public static void theMethodsOfAFinalClassAreFinal() {
        MethodRef0<Fin, Prim.Int> x = Fin_.x;
        CtorRef0<Fin> none = Fin_.new_;

        assertThat(x.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(none.owner()).isEqualTo(Fin_.TOKEN);
    }

    /// An abstract class has no `CtorRef`: its constructors are facts for
    /// `super(…)` alone, named `super_…`.
    public static void theConstructorsOfAnAbstractClassAreForSuperAlone() {
        SuperCtorRef0<Abs> none = Abs_.super_;
        SuperCtorRef2<Abs, String, Prim.Int> two = Abs_.super_String_int;

        assertThat(none.owner()).isEqualTo(Abs_.TOKEN);
        assertThat(two.owner()).isEqualTo(Abs_.TOKEN);
        assertThat(Abs_.run.traits()).isEqualTo(MemberTraits.ABSTRACT);
        assertThat(Abs_.done.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
    }

    public static void aDefaultMethodOfAnInterfaceIsOverridable() {
        MethodRef0<Iface, Prim.Int> size = Iface_.size;

        assertThat(size.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
    }

    public static void theAccessorsOfARecordAreMethods() {
        MethodRef0<Rec, Prim.Int> x = Rec_.x;
        MethodRef0<Rec, String> label = Rec_.label;

        assertThat(x.result()).isSameAs(PrimitiveToken.INT);
        assertThat(label.name()).isEqualTo("label");
    }
}
