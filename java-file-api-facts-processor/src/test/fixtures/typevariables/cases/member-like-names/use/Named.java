import gen.facts.java.lang.String_;
import gen.facts.p.Gt_;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.StaticFieldRef;
import p.Gt;

import java.lang.constant.ConstantDescs;

import static org.assertj.core.api.Assertions.assertThat;

/// A fact is named after its member whatever the type parameters are called: the members `T`, `E` and `U` are the
/// fields, as in a type whose type parameters are called otherwise.
public final class Named {
    private Named() {}

    public static void aStaticFieldNamedLikeATypeParameterIsAFact() {
        StaticFieldRef<Prim.Int> t = Gt_.T;
        MutableStaticFieldRef<Prim.Int> e = Gt_.E;

        assertThat(t.name()).isEqualTo("T");
        assertThat(t.constantValue()).contains(1);
        assertThat(e.type().erasure()).isEqualTo(ConstantDescs.CD_int);
    }

    public static void anInstanceFieldNamedLikeATypeParameterIsAFactOfTheTypeArgument() {
        Gt_<String, String> gt = new Gt_<>(String_.TOKEN, String_.TOKEN);
        MutableFieldRef<Gt<String, String>, String> u = gt.U;

        assertThat(u.type()).isSameAs(String_.TOKEN);
        assertThat(u.name()).isEqualTo("U");
    }

    public static void aMethodNamedLikeATypeParameterIsAFact() {
        Gt_<String, String> gt = new Gt_<>(String_.TOKEN, String_.TOKEN);
        MethodRef1<Gt<String, String>, String, String> t = gt.T_E;
        MethodRef2<Gt<String, String>, String, String, String> generic = gt.E_U_T(String_.TOKEN);

        assertThat(t.name()).isEqualTo("T");
        assertThat(generic.name()).isEqualTo("E");
        assertThat(generic.traits()).isEqualTo(MemberTraits.OVERRIDABLE.withTypeArgs(String_.TOKEN));
    }
}
