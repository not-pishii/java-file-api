import gen.facts.p.Abs_;
import gen.facts.p.Day_;
import gen.facts.p.Fin_;
import gen.facts.p.Iface_;
import gen.facts.p.Open_;
import gen.facts.p.Outer_Inner_;
import gen.facts.p.Rec_;
import gen.facts.p.Shape_;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.type.Types;
import p.Abs;
import p.Day;
import p.Fin;
import p.Iface;
import p.Open;
import p.Outer;
import p.Rec;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;

import static org.assertj.core.api.Assertions.assertThat;

/// Every kind of type gets its token class: the family of the token tells the kind, and javac checks it here.
public final class Kinds {
    private Kinds() {}

    public static void everyKindGetsItsTokenClass() {
        FinalClassToken<Fin> fin = Fin_.TOKEN;
        OpenClassToken<Open> open = Open_.TOKEN;
        AbstractClassToken<Abs> abs = Abs_.TOKEN;
        InterfaceToken<Iface> iface = Iface_.TOKEN;
        EnumToken<Day> day = Day_.TOKEN;
        FinalClassToken<Rec> rec = Rec_.TOKEN;

        assertThat(fin.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Fin")));
        assertThat(open.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Open")));
        assertThat(abs.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Abs")));
        assertThat(iface.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Iface")));
        assertThat(day.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Day")));
        assertThat(rec.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Rec")));
    }

    public static void anEnumShapeHasItsConstantsAndItsSuperclasses() {
        assertThat(Day_.Data.SHAPE.enumConstants()).containsExactly("MON", "TUE");
        assertThat(Day_.Data.SHAPE.superclasses())
                .containsExactly(ClassDesc.of("java.lang.Enum"), ConstantDescs.CD_Object);
    }

    public static void aSealedInterfaceIsSealedAndAFinalClassIsNot() {
        assertThat(Shape_.Data.SHAPE.sealed()).isTrue();
        assertThat(Fin_.Data.SHAPE.sealed()).isFalse();
    }

    public static void aNestedClassIsTokenedByItsBinaryName() {
        OpenClassToken<Outer.Inner> inner = Outer_Inner_.TOKEN;

        assertThat(inner.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Outer$Inner")));
    }
}
