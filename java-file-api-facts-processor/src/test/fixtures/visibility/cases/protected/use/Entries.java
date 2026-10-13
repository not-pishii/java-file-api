import gen.facts.java.lang.Integer_;
import gen.facts.java.lang.String_;
import gen.facts.p.ProtAbs_;
import gen.facts.p.ProtAbs_Heritage;
import gen.facts.p.ProtG_;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.Implementation1;
import me.supcheg.javafile.facts.Implemented;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.UnsafeInvocables;
import p.ProtAbs;
import p.ProtG;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The entry of a type that can be extended, in the class of its heritage beside the metamodel: a step per
/// abstract method, each taking how the class being declared has the method, and the proof after the last.
/// The implementations are vouched for by hand here, as the typed layer makes them.
public final class Entries {
    private Entries() {}

    public static void theProofOfAnAbstractClassTakesAStepPerAbstractMethod() {
        MethodRef1<String, String, String> own =
                UnsafeInvocables.method(String_.TOKEN, "hook", String_.TOKEN, String_.TOKEN, MemberTraits.FINAL);
        MethodRef1<ProtAbs, String, String> hook =
                UnsafeInvocables.method(ProtAbs_.TOKEN, "hook", String_.TOKEN, String_.TOKEN, MemberTraits.ABSTRACT);
        Implementation1<String, String, String> how =
                UnsafeInvocables.overriding1(new Implemented.ByOverride(hook, own));

        Complete<String, ProtAbs> complete =
                ProtAbs_.extend(String_.TOKEN).hook_String(how);

        assertThat(complete.supertype()).isSameAs(ProtAbs_.TOKEN);
        assertThat(complete.self()).isSameAs(String_.TOKEN);
        assertThat(complete.steps()).singleElement().satisfies(step -> assertThat(step.how()).isSameAs(how));
    }

    public static void aStepTakesTheImplementationOfItsMethodAlone() {
        MethodRef1<ProtAbs, String, String> other =
                UnsafeInvocables.method(ProtAbs_.TOKEN, "other", String_.TOKEN, String_.TOKEN, MemberTraits.ABSTRACT);
        Implementation1<String, String, String> ofAnother =
                UnsafeInvocables.inherited1(new Implemented.ByInheritance(other));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> ProtAbs_.extend(String_.TOKEN).hook_String(ofAnother))
                .withMessageContaining("the step hook(java.lang.String) of p.ProtAbs takes an implementation of that"
                        + " method");
    }

    public static void theProofOfATypeWithoutAbstractMethodsIsCompleteAtOnce() {
        ProtG_<Integer> ints = new ProtG_<>(Integer_.TOKEN);

        Complete<String, ProtG<Integer>> complete = ints.extend(String_.TOKEN);

        assertThat(complete.supertype()).isSameAs(ints.token);
        assertThat(complete.steps()).isEmpty();
    }

    public static void theHeritageOfTheShapeIsTheOneOfItsClass() {
        assertThat(ProtAbs_.TOKEN.shape().heritage()).isSameAs(new ProtAbs_Heritage().get());
        MethodRef0<ProtAbs, String> unused = UnsafeInvocables.method(ProtAbs_.TOKEN, "x", String_.TOKEN, MemberTraits.FINAL);
        assertThat(unused.name()).isEqualTo("x");
    }
}
