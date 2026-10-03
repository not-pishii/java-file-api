import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.Color_;
import gen.facts.p.Num_;
import gen.facts.p.PBox_;
import gen.facts.p.Poly_;
import gen.facts.p.Witness_;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.PBox;
import p.Poly;
import p.Witness;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/// The factory of a generic method is a method of the metamodel: `static` where the type has no type parameters to
/// take a token for, an instance method of the metamodel of a generic type. What is local to a factory — the
/// witnesses and the type variables — takes no name from a fact (see the source in `expected/`).
public final class Factories {
    private Factories() {}

    private static final ClassDesc CD_PBOX = ClassDesc.of("p.PBox");

    /// The fields `t` and `T` keep their names beside the type parameter `T` of a method and its witness `t`.
    public static void aFieldNamedLikeATypeParameterOrItsWitnessKeepsItsFact() {
        MutableFieldRef<Poly, Prim.Int> t = Poly_.t;
        StaticFieldRef<Prim.Int> upper = Poly_.T;

        assertThat(t.name()).isEqualTo("t");
        assertThat(upper.name()).isEqualTo("T");
        assertThat(upper.constantValue()).contains(1);
    }

    /// A witness is not named like what the factory reads: `TOKEN` is the token, `gen` a package.
    public static void aTypeParameterNamedLikeWhatTheFactoryReadsTakesAWitnessOfAnotherName() {
        VoidMethodRef2<Poly, String, Witness> odd = Poly_.odd_TOKEN_Gen(String_.TOKEN, Witness_.TOKEN);

        assertThat(odd.owner()).isSameAs(Poly_.TOKEN);
        assertThat(odd.params()).containsExactly(String_.TOKEN, Witness_.TOKEN);
    }

    public static void aGenericMethodOfAGenericTypeIsMadeByAnInstance() {
        PBox_<String> box = new PBox_<>(String_.TOKEN);

        MethodRef1<PBox<String>, PBox<Witness>, Function<? super String, ? extends Witness>> map =
                box.map_Function(Witness_.TOKEN);

        assertThat(map.owner()).isSameAs(box.token);
        assertThat(map.resultType().orElseThrow().typeRef())
                .isEqualTo(new ParameterizedTypeRef(CD_PBOX, List.of(Types.exact(Witness_.TOKEN.typeRef()))));
        assertThat(((DeclaredToken<?>) map.resultType().orElseThrow()).shape()).isSameAs(PBox_.Data.SHAPE);
        assertThat(map.params().stream().map(TypeToken::typeRef))
                .containsExactly(new ParameterizedTypeRef(
                        ClassDesc.of("java.util.function.Function"),
                        List.of(Types.superBound(Types.STRING), Types.extendsBound(Witness_.TOKEN.typeRef()))));
        assertThat(map.traits().typeArgs()).containsExactly(Witness_.TOKEN);
    }

    /// `<T> T shadow(T)` in `PBox<T>`: the fact is of `PBox<T>` of the type, and takes the `T` of the method.
    public static void aTypeParameterOfAMethodThatHidesOneOfTheTypeTakesTheWitnessOfTheMethod() {
        PBox_<String> box = new PBox_<>(String_.TOKEN);

        MethodRef1<PBox<String>, Witness, Witness> shadow = box.shadow_T(Witness_.TOKEN);

        assertThat(shadow.owner()).isSameAs(box.token);
        assertThat(shadow.params()).containsExactly(Witness_.TOKEN);
        assertThat(shadow.resultType().orElseThrow()).isSameAs(Witness_.TOKEN);
    }

    /// A static method does not see the type parameters of the type: it is of the type for any argument.
    public static void aStaticMethodOfAGenericTypeIsOfTheTypeForAnyArgument() {
        StaticMethodRef1<PBox<Witness>, Witness> of = PBox_.of_T(Witness_.TOKEN);

        assertThat(of.owner()).isSameAs(PBox_.ANY);
        assertThat(of.resultType().orElseThrow().typeRef())
                .isEqualTo(new ParameterizedTypeRef(CD_PBOX, List.of(Types.exact(Witness_.TOKEN.typeRef()))));
        assertThat(of.traits()).isEqualTo(MemberTraits.FINAL.withTypeArgs(Witness_.TOKEN));
    }

    /// The same code with witnesses within the bounds of the type parameters compiles, which `use-fails/` has not.
    public static void witnessesWithinTheBoundsCompile() {
        assertThat(Poly_.max_Collection(String_.TOKEN)).isNotNull();
        assertThat(Poly_.clamp_T(Num_.TOKEN)).isNotNull();
        assertThat(Poly_.widen_B(Object_.TOKEN, String_.TOKEN)).isNotNull();
        assertThat(new PBox_<>(Object_.TOKEN).put_U(String_.TOKEN)).isNotNull();
        assertThat(Poly_.id_T(Object_.TOKEN)).isNotNull();
        assertThat(PBox_.ofEnum_Class(Color_.TOKEN)).isNotNull();
    }
}
