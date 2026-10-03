import gen.facts.p.A_;
import gen.facts.p.B_;
import gen.facts.p.Boxes_;
import gen.facts.p.Greeter_;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import p.A;
import p.B;
import p.Greeter;

import java.lang.constant.ClassDesc;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/// The facts of constructors and methods: named by their parameters, of
/// the family of their kind and arity, with the tokens of their types.
public final class Members {
    private Members() {}

    public static void constructorsAreNamedByTheirParameters() {
        CtorRef0<Greeter> none = Greeter_.new_;
        assertThat(none.owner()).isEqualTo(Greeter_.TOKEN);
        assertThat(none.traits()).isEqualTo(MemberTraits.FINAL);

        CtorRef1<Greeter, String> string = Greeter_.new_String;
        assertThat(string.param1().typeRef()).isEqualTo(Types.STRING);
        assertThat(string.traits().overridability()).isEqualTo(Overridability.FINAL);

        CtorRef3<Greeter, Prim.Int, Prim.Long, boolean[]> three = Greeter_.new_int_long_booleanArray;
        assertThat(three.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(PrimitiveTypeRef.INT, PrimitiveTypeRef.LONG, Types.array(PrimitiveTypeRef.BOOLEAN));
    }

    public static void methodsAreNamedByTheirParametersAndAreOfTheFamilyOfTheirKind() {
        MethodRef0<Greeter, String> greet = Greeter_.greet;
        assertThat(greet.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(greet.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
        assertThat(greet.name()).isEqualTo("greet");
        assertThat(Greeter_.greet_String.name()).isEqualTo("greet");

        MethodRef2<Greeter, String, String, Locale> two = Greeter_.greet_String_Locale;
        assertThat(two.param1().typeRef()).isEqualTo(Types.STRING);
        assertThat(two.param2().typeRef()).isEqualTo(Types.of(ClassDesc.of("java.util.Locale")));

        VoidMethodRef1<Greeter, String> log = Greeter_.log_String;
        assertThat(log.resultType()).isEmpty();
        assertThat(log.name()).isEqualTo("log");

        VoidStaticMethodRef1<String[]> main = Greeter_.main_StringArray;
        assertThat(main.name()).isEqualTo("main");

        StaticMethodRef1<Prim.Int, String> parse = Greeter_.parse_String;
        assertThat(parse.result()).isSameAs(PrimitiveToken.INT);
        assertThat(parse.traits()).isEqualTo(MemberTraits.FINAL);

        MethodRef2<Greeter, int[], int[][], Greeter[]> arr = Greeter_.arr_intArrayArray_GreeterArray;
        assertThat(arr.result().typeRef()).isEqualTo(Types.array(PrimitiveTypeRef.INT));
        assertThat(arr.param1().typeRef()).isEqualTo(Types.array(Types.array(PrimitiveTypeRef.INT)));
        assertThat(arr.param2().typeRef()).isEqualTo(Types.array(Types.of(ClassDesc.of("p.Greeter"))));
    }

    /// `A_` and `B_` mention each other through `Data.SHAPE` alone: both
    /// initialize, whichever is touched first.
    public static void aMemberThatMentionsATypeHasTheTokenOfThatType() {
        MethodRef0<A, B> b = A_.b;
        MethodRef0<A, A> self = A_.self;
        MethodRef0<B, A> a = B_.a;

        assertThat(b.result().typeRef()).isEqualTo(Types.of(ClassDesc.of("p.B")));
        assertThat(b.result()).isEqualTo(B_.TOKEN);
        assertThat(self.result()).isEqualTo(A_.TOKEN);
        assertThat(a.result()).isEqualTo(A_.TOKEN);
    }

    public static void theNamesOfPrimitiveMarkersDoNotHideTheClassesOfJavaLang() {
        var box = Boxes_.box_double_Long_long_Float_float_Byte_byte_Short_short_Integer_int_Character;

        assertThat(box.resultType().orElseThrow().typeRef()).isEqualTo(Types.of(ClassDesc.of("java.lang.Double")));
        assertThat(box.params().get(0).typeRef()).isEqualTo(PrimitiveTypeRef.DOUBLE);
        assertThat(box.params().get(1).typeRef()).isEqualTo(Types.of(ClassDesc.of("java.lang.Long")));
    }
}
