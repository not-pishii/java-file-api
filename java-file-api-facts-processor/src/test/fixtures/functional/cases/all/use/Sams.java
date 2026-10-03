import gen.facts.java.lang.String_;
import gen.facts.p.Comp_;
import gen.facts.p.Fn2_;
import gen.facts.p.Fn_;
import gen.facts.p.Gen_;
import gen.facts.p.Redecl_;
import gen.facts.p.Run_;
import gen.facts.p.Sub3_;
import gen.facts.p.WithDefault_;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.VoidSam3;
import me.supcheg.javafile.type.Types;
import p.Comp;
import p.Fn;
import p.Fn2;
import p.Gen;
import p.Run;
import p.Sub3;
import p.WithDefault;

import static org.assertj.core.api.Assertions.assertThat;

/// An interface with a single abstract method (JLS 9.8) has a `sam` fact, declared by it or not.
public final class Sams {
    private Sams() {}

    /// `equals(Object)` among the abstract methods does not count.
    public static void equalsOfObjectAmongTheAbstractMethodsDoesNotCount() {
        Sam1<Fn, String, String> sam = Fn_.sam;
        MethodRef1<Fn, Prim.Bool, Object> equals = Fn_.equals_Object;

        assertThat(sam.method()).isSameAs(Fn_.apply_String);
        assertThat(sam.owner()).isEqualTo(Fn_.TOKEN);
        assertThat(sam.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(sam.param1().typeRef()).isEqualTo(Types.STRING);
        assertThat(equals.name()).isEqualTo("equals");
    }

    /// `toString()` and `hashCode()` do not count either.
    public static void theMethodsOfObjectDoNotCountEither() {
        Sam1<Comp, Prim.Int, Comp> sam = Comp_.sam;

        assertThat(sam.method()).isSameAs(Comp_.compareTo_Comp);
    }

    public static void aVoidMethodMakesAVoidSam() {
        VoidSam0<Run> sam = Run_.sam;
        InterfaceToken<Run> token = Run_.TOKEN;

        assertThat(sam.method()).isSameAs(Run_.run);
        assertThat(sam.owner()).isSameAs(token);
    }

    public static void theDefaultAndStaticMethodsOfAnInterfaceDoNotCount() {
        Sam1<WithDefault, Prim.Int, Prim.Int> sam = WithDefault_.sam;

        assertThat(sam.method()).isSameAs(WithDefault_.f_int);
        assertThat(WithDefault_.g_int.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
    }

    public static void aSamHasAParameterPerParameterOfTheMethod() {
        VoidSam3<Fn2, String, Prim.Int, Prim.Long> sam = Fn2_.sam;

        assertThat(sam.method()).isSameAs(Fn2_.call_String_int_long);
    }

    /// The result is the most specific one, not the one of the first supertype.
    public static void aCovariantResultIsTheMostSpecificOne() {
        Sam0<Sub3, String> covariant = Sub3_.sam;

        assertThat(covariant.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(covariant.method()).isSameAs(Sub3_.get);
    }

    public static void anInterfaceThatRedeclaresTheMethodHasTheSamOfThatFact() {
        Sam1<?, ?, ?> sam = Redecl_.sam;

        assertThat(sam.method()).isSameAs(Redecl_.apply_String);
    }

    /// A generic method has a fact too, which a method reference or a class may implement.
    public static void aGenericMethodHasAFactAMethodOfTheTokensOfItsTypeArguments() {
        MethodRef1<Gen, String, String> id = Gen_.id_T(String_.TOKEN);

        assertThat(id.name()).isEqualTo("id");
    }
}
