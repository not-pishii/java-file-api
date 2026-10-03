import gen.facts.java.lang.Integer_;
import gen.facts.java.lang.String_;
import gen.facts.p.Box_;
import gen.facts.p.Pair_;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import p.Box;
import p.Pair;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The metamodel of a generic type is generic: an instance of it, made
/// with a token per type parameter, has the facts of the members in terms
/// of those tokens.
public final class Instances {
    private static final ClassDesc BOX = ClassDesc.of("p.Box");
    private static final ClassDesc PAIR = ClassDesc.of("p.Pair");
    private static final TypeRef BOX_OF_STRING = Types.parameterized(BOX, Types.exact(Types.STRING));

    private Instances() {}

    public static void theTokenOfAnInstanceIsTheTypeAppliedToItsArguments() {
        Box_<String> box = new Box_<>(String_.TOKEN);
        OpenClassToken<Box<String>> token = box.token;

        assertThat(token.typeRef()).isEqualTo(BOX_OF_STRING);
        assertThat(token.shape()).isSameAs(Box_.Data.SHAPE);
    }

    public static void theFactsOfAnInstanceAreInTermsOfTheTokenItIsMadeWith() {
        Box_<String> box = new Box_<>(String_.TOKEN);
        MethodRef0<Box<String>, String> get = box.get;
        VoidMethodRef1<Box<String>, String> set = box.set_T;
        MutableFieldRef<Box<String>, String> value = box.value;
        FieldRef<Box<String>, String> initial = box.initial;
        CtorRef0<Box<String>> none = box.new_;
        CtorRef1<Box<String>, String> one = box.new_T;
        MethodRef1<Box<String>, String[], String[]> toArray = box.toArray_TArray;
        MethodRef0<Box<String>, int[]> ints = box.ints;

        assertThat(get.owner()).isSameAs(box.token);
        assertThat(get.result()).isSameAs(String_.TOKEN);
        assertThat(get.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
        assertThat(set.params()).extracting(TypeToken::typeRef).containsExactly(Types.STRING);
        assertThat(set.param1()).isSameAs(String_.TOKEN);
        assertThat(value.type()).isSameAs(String_.TOKEN);
        assertThat(initial.type()).isSameAs(String_.TOKEN);
        assertThat(initial.owner()).isSameAs(box.token);
        assertThat(none.owner()).isSameAs(box.token);
        assertThat(one.params()).extracting(TypeToken::typeRef).containsExactly(Types.STRING);
        assertThat(toArray.result().typeRef()).isEqualTo(Types.array(Types.STRING));
        assertThat(toArray.params()).extracting(TypeToken::typeRef).containsExactly(Types.array(Types.STRING));
        assertThat(ints.result()).isEqualTo(PrimitiveToken.INT.array());
    }

    public static void theTypeItselfAppliedToItsOwnTypeParametersIsTheTokenOfTheInstance() {
        Box_<String> box = new Box_<>(String_.TOKEN);
        MethodRef0<Box<String>, Box<String>> self = box.self;
        MethodRef0<Box<String>, Box<Box<String>>> nested = box.nested;

        assertThat(self.result()).isSameAs(box.token);
        assertThat(nested.result().typeRef()).isEqualTo(Types.parameterized(BOX, Types.exact(BOX_OF_STRING)));
    }

    /// The method table of the token is erased with its argument, as the signature of the fact is.
    public static void theMethodTableOfAnInstanceIsThatOfItsTypeArguments() {
        Box_<String> box = new Box_<>(String_.TOKEN);

        assertThat(box.token.methods().concreteMethods())
                .contains(new MethodSignature("set", List.of(ConstantDescs.CD_String)))
                .contains(box.set_T.signature());
    }

    public static void everyTypeParameterTakesAToken() {
        Pair_<String, Integer> pair = new Pair_<>(String_.TOKEN, Integer_.TOKEN);
        FinalClassToken<Pair<String, Integer>> token = pair.token;
        FieldRef<Pair<String, Integer>, String> first = pair.first;
        FieldRef<Pair<String, Integer>, Integer> second = pair.second;
        CtorRef2<Pair<String, Integer>, String, Integer> two = pair.new_A_B;
        TypeRef integer = Integer_.TOKEN.typeRef();

        assertThat(token.typeRef()).isEqualTo(Types.parameterized(PAIR, Types.exact(Types.STRING), Types.exact(integer)));
        assertThat(first.type()).isSameAs(String_.TOKEN);
        assertThat(second.type()).isSameAs(Integer_.TOKEN);
        assertThat(two.params()).extracting(TypeToken::typeRef).containsExactly(Types.STRING, integer);
        assertThat(pair.entry.result().typeRef())
                .isEqualTo(Types.parameterized(
                        ClassDesc.of("java.util.Map$Entry"), Types.exact(Types.STRING), Types.exact(integer)));
        assertThat(pair.swap.traits()).isEqualTo(MemberTraits.FINAL);
    }

    public static void anotherParameterizationOfTheTypeItselfIsMadeFromItsOwnShape() {
        Pair_<String, Integer> pair = new Pair_<>(String_.TOKEN, Integer_.TOKEN);
        MethodRef0<Pair<String, Integer>, Pair<Integer, String>> swap = pair.swap;
        DeclaredToken<Pair<Integer, String>> swapped = (DeclaredToken<Pair<Integer, String>>) swap.result();

        assertThat(swapped.typeRef())
                .isEqualTo(Types.parameterized(
                        PAIR, Types.exact(Integer_.TOKEN.typeRef()), Types.exact(Types.STRING)));
        assertThat(swapped.shape()).isSameAs(Pair_.Data.SHAPE);
    }
}
