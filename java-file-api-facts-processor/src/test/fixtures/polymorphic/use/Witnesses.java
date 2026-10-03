import gen.facts.java.io.IOException_;
import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.Poly_;
import gen.facts.p.PolyFn_;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Poly;
import p.PolyFn;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The fact of a generic method is made by a method of the metamodel from a token per type parameter — a witness — and
/// has the witnesses as its explicit type arguments.
public final class Witnesses {
    private Witnesses() {}

    public static void aGenericMethodIsAFactMadeFromAWitnessPerTypeParameter() {
        MethodRef1<Poly, String, String> id = Poly_.id_T(String_.TOKEN);

        assertThat(id.name()).isEqualTo("id");
        assertThat(id.owner()).isSameAs(Poly_.TOKEN);
        assertThat(id.resultType().orElseThrow()).isSameAs(String_.TOKEN);
        assertThat(id.params()).containsExactly(String_.TOKEN);
        assertThat(id.traits()).isEqualTo(MemberTraits.OVERRIDABLE.withTypeArgs(String_.TOKEN));
    }

    /// A type parameter that no parameter mentions is given explicitly all the same.
    public static void aTypeParameterNoParameterMentionsIsGivenAllTheSame() {
        VoidMethodRef0<Poly> none = Poly_.none(String_.TOKEN);

        assertThat(none.params()).isEmpty();
        assertThat(none.traits().typeArgs()).containsExactly(String_.TOKEN);
    }

    public static void aStaticGenericMethodIsAStaticFact() {
        StaticMethodRef1<List<String>, String[]> listOf = Poly_.listOf_TArray(String_.TOKEN);

        assertThat(listOf.traits()).isEqualTo(MemberTraits.FINAL.withTypeArgs(String_.TOKEN));
        assertThat(listOf.resultType().orElseThrow().typeRef())
                .isEqualTo(new ParameterizedTypeRef(ConstantDescs.CD_List, List.of(Types.exact(Types.STRING))));
        // a variable-arity parameter is its array
        assertThat(listOf.params().stream().map(TypeToken::typeRef)).containsExactly(Types.array(Types.STRING));
    }

    public static void aStaticGenericMethodMayReturnItsTypeParameter() {
        StaticMethodRef1<String, Collection<? extends String>> max = Poly_.max_Collection(String_.TOKEN);

        assertThat(max.resultType().orElseThrow()).isSameAs(String_.TOKEN);
        assertThat(max.params().stream().map(TypeToken::typeRef))
                .containsExactly(new ParameterizedTypeRef(
                        ConstantDescs.CD_Collection, List.of(Types.extendsBound(Types.STRING))));
    }

    /// `B extends A`: the type arguments are in the order of the type parameters.
    public static void aTypeParameterMayBeBoundedByAnother() {
        MethodRef1<Poly, Object, String> widen = Poly_.widen_B(Object_.TOKEN, String_.TOKEN);

        assertThat(widen.traits().typeArgs()).containsExactly(Object_.TOKEN, String_.TOKEN);
        assertThat(widen.resultType().orElseThrow().typeRef()).isEqualTo(Types.OBJECT);
        assertThat(widen.params().stream().map(TypeToken::typeRef)).containsExactly(Types.STRING);
    }

    public static void aGenericMethodThrowsTheWitnessOfItsTypeParameter() {
        VoidMethodRef1<Poly, Class<IOException>> run = Poly_.run_Class(IOException_.TOKEN);

        assertThat(List.<Object>copyOf(run.traits().throwsTypes())).containsExactly(IOException_.TOKEN, IOException_.TOKEN);
        assertThat(run.traits().throwsTypes().getFirst()).isSameAs(IOException_.TOKEN);
        assertThat(run.traits().typeArgs()).containsExactly(IOException_.TOKEN);
        assertThat(run.traits().overridability()).isEqualTo(Overridability.OVERRIDABLE);
        assertThat(run.params().stream().map(TypeToken::typeRef))
                .containsExactly(new ParameterizedTypeRef(
                        ClassDesc.of("java.lang.Class"), List.of(Types.exact(IOException_.TOKEN.typeRef()))));
    }

    public static void anInterfaceWhoseSingleAbstractMethodIsGenericHasTheFact() {
        MethodRef1<PolyFn, String, String> apply = PolyFn_.apply_T(String_.TOKEN);

        assertThat(apply.traits()).isEqualTo(MemberTraits.ABSTRACT.withTypeArgs(String_.TOKEN));
    }
}
