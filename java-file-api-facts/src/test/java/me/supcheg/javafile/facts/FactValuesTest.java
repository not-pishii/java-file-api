package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.source.MemberKind;
import me.supcheg.javafile.facts.source.MemberQuery;
import me.supcheg.javafile.facts.testfacts.java.lang.Integer_;
import me.supcheg.javafile.facts.testfacts.java.lang.String_;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The facts as values: what two of them are equal by, how each is
/// described, and what `UnsafeFacts` refuses to vouch for.
class FactValuesTest {
    private static final EnumToken<TimeUnit> STATE =
            UnsafeFacts.enumToken(Types.of(TimeUnit.class), List.of("SECONDS", "MINUTES"), MethodTable.EMPTY);

    private static final InterfaceToken<Runnable> RUNNABLE =
            UnsafeFacts.interfaceToken(Types.of(Runnable.class), MethodTable.EMPTY);

    @Test
    void anEnumConstantIsItsEnumAndItsName() {
        EnumToken<Object> other = UnsafeFacts.enumToken(
                Types.of(ClassDesc.of("fixtures", "Other")), List.of("SECONDS"), MethodTable.EMPTY);

        assertThat(STATE.constant("SECONDS"))
                .isEqualTo(STATE.constant("SECONDS"))
                .hasSameHashCodeAs(STATE.constant("SECONDS"))
                .isNotEqualTo(STATE.constant("MINUTES"))
                .isNotEqualTo(other.constant("SECONDS"))
                .isNotEqualTo("SECONDS");
        assertThat(STATE.methods()).isEqualTo(MethodTable.EMPTY);
    }

    @Test
    void anArrayTokenIsItsComponent() {
        assertThat(ArrayToken.of(String_.TOKEN))
                .isEqualTo(ArrayToken.of(String_.TOKEN))
                .hasSameHashCodeAs(ArrayToken.of(String_.TOKEN))
                .isNotEqualTo(ArrayToken.of(Integer_.TOKEN))
                .isNotEqualTo(String_.TOKEN);
    }

    @Test
    void aDeclaredTokenIsItsTypeAndItsKind() {
        FinalClassToken<String> string =
                UnsafeFacts.finalClassToken(Types.STRING, List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
        FinalClassToken<String> same =
                UnsafeFacts.finalClassToken(Types.STRING, List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
        OpenClassToken<String> open =
                UnsafeFacts.openClassToken(Types.STRING, List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
        AbstractClassToken<String> abstractOne =
                UnsafeFacts.abstractClassToken(Types.STRING, List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);

        assertThat(string)
                .isEqualTo(same)
                .hasSameHashCodeAs(same)
                .isNotEqualTo(open)
                .isNotEqualTo(UnsafeFacts.finalClassToken(
                        Types.of(Integer.class), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY))
                .isNotEqualTo(null);
        assertThat(abstractOne.methods()).isEqualTo(MethodTable.EMPTY);
    }

    @Test
    void aFactIsDescribedAsJavaWritesIt() {
        assertThat(TokenArg.unbounded()).hasToString("?");
        assertThat(UnsafeFacts.declarationIdentity("class Probe")).hasToString("class Probe");
        assertThat(MemberQuery.method("length", PrimitiveToken.INT)).hasToString("method int length()");
        assertThat(MemberQuery.staticMethod("valueOf", String_.TOKEN, PrimitiveToken.INT))
                .hasToString("static method java.lang.String valueOf(int)");
        assertThat(MemberQuery.voidStaticMethod("exit", PrimitiveToken.INT))
                .hasToString("static method void exit(int)");
        assertThat(MemberQuery.constructor(String_.TOKEN, PrimitiveToken.INT))
                .hasToString("constructor(java.lang.String, int)");
        assertThat(MemberQuery.field(MemberKind.FIELD, "count", PrimitiveToken.INT))
                .hasToString("field int count");
        assertThat(MemberQuery.field(MemberKind.MUTABLE_FIELD, "count", PrimitiveToken.INT))
                .hasToString("non-final field int count");
        assertThat(MemberQuery.field(MemberKind.STATIC_FIELD, "count", PrimitiveToken.INT))
                .hasToString("static field int count");
        assertThat(MemberQuery.field(MemberKind.MUTABLE_STATIC_FIELD, "count", PrimitiveToken.INT))
                .hasToString("non-final static field int count");
    }

    @Test
    void aFailedLookupTellsWhatWasSoughtWhereAndWhatIsLikeIt() {
        FactLookupException failed = new FactLookupException("field size", "the test", List.of("length"));

        assertThat(failed).hasMessage("no field size in the test; similar: length");
        assertThat(failed.sought()).isEqualTo("field size");
        assertThat(failed.origin()).isEqualTo("the test");
        assertThat(failed.similar()).containsExactly("length");
        assertThat(new FactLookupException("field size", "the test", List.of()))
                .hasMessage("no field size in the test");
    }

    @Test
    @SuppressWarnings("unchecked")
    void noMemberThrowsWhatIsNoThrowable() {
        // the type parameter of `throwing` rules it out; an unchecked cast gets past it
        RefToken<? extends Throwable> string = (RefToken<? extends Throwable>) (RefToken<?>) String_.TOKEN;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> MemberTraits.DEFAULT.throwing(string))
                .withMessage("not a Throwable: java.lang.String");
    }

    @Test
    void aSamIsTheSingleAbstractMethodOfAnInterface() {
        MethodRef0<String, Prim.Int> length =
                UnsafeInvocables.method(String_.TOKEN, "length", PrimitiveToken.INT, MemberTraits.DEFAULT);
        VoidMethodRef0<Runnable> run = UnsafeInvocables.voidMethod(RUNNABLE, "run", MemberTraits.DEFAULT);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeInvocables.sam(length))
                .withMessage("int java.lang.String.length() is not a method of an interface");
        // the table of the interface tells no abstract method
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeInvocables.voidSam(run))
                .withMessage("void java.lang.Runnable.run() is not the single abstract method of java.lang.Runnable,"
                        + " whose abstract methods are []");
    }

    @Test
    void aDeclaredTypeIsAClassAndAGenericOneHasATokenForEachOfItsTypeParameters() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.finalClassToken(
                        Types.typeVar("T"), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY))
                .withMessage("a declared type token needs a class or parameterized type, got type variable T");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.declaredClassToken(
                        ClassDesc.of("fixtures", "Box"),
                        List.of(),
                        List.of(),
                        () -> List.of(ConstantDescs.CD_Object),
                        () -> MethodTable.EMPTY))
                .withMessageStartingWith("a generic class has a token for each of its type parameters: ");
    }
}
