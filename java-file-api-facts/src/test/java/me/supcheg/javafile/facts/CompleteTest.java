package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.facts.testfacts.java.lang.String_;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The proof that a class has every abstract method of a type: the steps
/// it is made of, and what a step takes.
class CompleteTest {

    /// Phantom of the class being declared, `fixtures.Self`.
    interface SelfP {}

    private static final FinalClassToken<SelfP> SELF = UnsafeFacts.finalClassToken(
            Types.of(ClassDesc.of("fixtures", "Self")), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
    private static final Comparable_<String> COMPARABLE = new Comparable_<>(String_.TOKEN);
    private static final MethodRef1<SelfP, Prim.Int, String> OWN =
            UnsafeInvocables.method(SELF, "compareTo", PrimitiveToken.INT, String_.TOKEN, MemberTraits.FINAL);
    private static final Signature COMPARE_TO = Signature.of("compareTo", Param.var(0));

    @Test
    void theProofHasTheStepsTakenInTheirOrder() {
        Implementation1<SelfP, Prim.Int, String> how =
                UnsafeInvocables.overriding1(new Implemented.ByOverride(COMPARABLE.compareTo_T, OWN));

        Complete<SelfP, Comparable<String>> complete =
                UnsafeFacts.steps(COMPARABLE.token, SELF).with(COMPARE_TO, how).complete();

        assertThat(complete.supertype()).isSameAs(COMPARABLE.token);
        assertThat(complete.self()).isSameAs(SELF);
        assertThat(complete.steps()).containsExactly(new Complete.Step<>(COMPARE_TO, how));
        assertThat(complete)
                .hasToString("fixtures.Self has every abstract method of java.lang.Comparable<java.lang.String>");
        assertThat(UnsafeFacts.steps(COMPARABLE.token, SELF).complete().steps()).isEmpty();
    }

    @Test
    void anImplementationTellsHowTheClassHasTheMethod() {
        Implemented.ByOverride overriding = new Implemented.ByOverride(COMPARABLE.compareTo_T, OWN);
        Implemented.ByInheritance inheriting = new Implemented.ByInheritance(String_.length);
        Overriding1<SelfP, Prim.Int, String> own = UnsafeInvocables.overriding1(overriding);
        Implementation0<SelfP, Prim.Int> inherited = UnsafeInvocables.inherited0(inheriting);
        VoidImplementation0<SelfP> ofVoid = UnsafeInvocables.voidInherited0(inheriting);

        assertThat(own.how()).isSameAs(overriding);
        assertThat(own.method()).isSameAs(OWN);
        assertThat(overriding.satisfies()).isSameAs(COMPARABLE.compareTo_T);
        assertThat(inheriting.satisfies()).isSameAs(String_.length);
        assertThat(ofVoid.how()).isSameAs(inheriting);
        assertThat(own)
                .hasToString("int fixtures.Self.compareTo(java.lang.String) overriding"
                        + " int java.lang.Comparable<java.lang.String>.compareTo(java.lang.String)");
        assertThat(inherited).hasToString("inherited int java.lang.String.length()");
    }

    @Test
    void aStepTakesAnImplementationOfItsMethodAlone() {
        Implementation0<SelfP, Prim.Int> ofAnother =
                UnsafeInvocables.inherited0(new Implemented.ByInheritance(String_.length));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.steps(COMPARABLE.token, SELF).with(COMPARE_TO, ofAnother))
                .withMessage("the step compareTo(java.lang.String) of java.lang.Comparable<java.lang.String> takes an"
                        + " implementation of that method, got one of int java.lang.String.length()");
    }

    @Test
    void theFamiliesAreAllTheImplementationsThereAre() {
        assertThat(Implementation.class.getPermittedSubclasses())
                .hasSize(26)
                .allSatisfy(family -> assertThat(family.getConstructors()).isEmpty());
        assertThat(Complete.class.getConstructors()).isEmpty();
        assertThat(Steps.class.getConstructors()).isEmpty();
    }
}
