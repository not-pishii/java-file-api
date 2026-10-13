package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// Of which types a metamodel is made at all ([MirrorTranslator#refusal]), of which a full one
/// ([MirrorTranslator#rejection]), and what a type that waits for another makes of those above it.
class AdmissionTest {
    private static final String DOLLAR = "package p; public class Outer { public static class In$ {} }";

    private static Admission rejection(Harness.Env env, String name) {
        return env.translator().rejection(env.element(name), Harness.ok(env.tokenOnly(name)));
    }

    @Test
    void aTypeOfTheUnnamedPackageHasNoMetamodel() {
        assertThat(Harness.<Optional<String>>run(
                        env -> MirrorTranslator.refusal(env.element("Top")), "public class Top {}"))
                .hasValue("Top is in the unnamed package, which a metamodel in a named package cannot refer to");
    }

    @Test
    void aBoundMayMentionWhatAMetamodelRefersTo() {
        assertThat(Harness.<Admission>run(env -> rejection(env, "p.G"), """
                package p;
                public class G<T extends java.util.List<? extends String[]>, U extends Comparable<? super U>> {}
                """)).isEqualTo(new Admission.Admitted());
    }

    @Test
    void aBoundThatMentionsATypeWithoutAMetamodelRejectsTheType() {
        // in a type argument, under a wildcard, as the component of an array: the type is mentioned all the same
        assertThat(Harness.<Admission>run(env -> rejection(env, "p.G"), """
                package p;
                public class G<T extends java.util.List<? extends Outer.In$[]>> {}
                """, DOLLAR))
                .isInstanceOfSatisfying(
                        Admission.Rejected.class,
                        rejected -> assertThat(rejected.reason())
                                .startsWith("the bounds of the type parameters of p.G mention p.Outer$In$, which has no"
                                        + " metamodel: "));
    }

    @Test
    void aBoundThatMentionsATypeOfTheUnnamedPackageRejectsTheType() {
        assertThat(Harness.<Admission>run(
                        env -> rejection(env, "G"), "public class G<T extends Top> {}", "public class Top {}"))
                .isEqualTo(new Admission.Rejected("the bounds of the type parameters of G mention Top, which has no"
                        + " metamodel: a metamodel in a named package cannot refer to it"));
    }

    @Test
    void aBoundThatMentionsATypeThatWaitsDefersTheVerdict() {
        // the model of p.G is there; whether p.Waiting has one is not known before gen.Missing is generated
        assertThat(Harness.<Admission>runUnresolved(env -> rejection(env, "p.G"), """
                package p;
                public class G<T extends Waiting<?>> {}
                """, """
                package p;
                public class Waiting<X extends gen.Missing> {}
                """))
                .isEqualTo(new Admission.Deferred("gen.Missing"));
    }

    @Test
    void aTypeWaitsForWhatTheSupertypesAboveTheOnesItAdoptsOfWaitFor() {
        // p.Low adopts between() of the hidden p.Between; whether the public p.Mid above it tells a member too is
        // not known before gen.Missing is there
        String low = "package p; public class Low extends Between {}";
        String between = "package p; class Between extends Mid<String> { public void between() {} }";
        assertThat(Harness.<Translation<TypeModel>>runUnresolved(
                        env -> env.full("p.Low"),
                        low,
                        between,
                        "package p; public class Mid<X extends gen.Missing> {}"))
                .isEqualTo(new Translation.Deferred<TypeModel>("gen.Missing"));
        assertThat(Harness.<Translation<TypeModel>>runUnresolved(
                        env -> env.full("p.Low"),
                        low,
                        between,
                        "package p; public class Mid<X extends Waiting<?>> {}",
                        "package p; public class Waiting<X extends gen.Missing> {}"))
                .isEqualTo(new Translation.Deferred<TypeModel>("gen.Missing"));
    }

    @Test
    void aTypeWithAMemberThatMentionsATypeVariableOutOfScopeTellsNoHeritage() {
        // the T of get() is of p.Outer, and no type parameter of p.Outer.Inner or of the method
        assertThat(Harness.<Heritage>run(
                        env -> env.translator().heritage(env.element("p.Outer.Inner")),
                        "package p; public class Outer<T> { public class Inner { public T get() { return null; } } }"))
                .isEqualTo(Heritage.UNTOLD);
    }

    @Test
    void aWildcardBoundedByObjectIsToldUnbounded() {
        assertThat(Harness.<String>run(
                        env -> env.translator().heritage(env.element("p.W")).toString(),
                        "package p; public class W { public void m(java.util.List<? extends Object> all) {} }"))
                .contains("params=[Fixed[type=" + Types.parameterized(Types.LIST, Types.unbounded()) + "]]");
    }

    @Test
    void theHeritageOfATypeIsNotAskedForWhileItWaits() {
        assertThat(Harness.<Boolean>run(
                        env -> env.translator()
                                .heritage(env.element("p.Plain"))
                                .equals(Harness.ok(env.full("p.Plain")).heritage()),
                        "package p; public class Plain { public void run() {} }"))
                .isTrue();
        assertThatThrownBy(() -> Harness.runUnresolved(
                        env -> env.translator().heritage(env.element("p.Member")),
                        "package p; public class Member { public gen.Missing m() { return null; } }"))
                .cause()
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("a member of p.Member mentions gen.Missing, which is not generated yet");
    }
}
