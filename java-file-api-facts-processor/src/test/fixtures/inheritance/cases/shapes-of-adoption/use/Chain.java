import gen.facts.java.lang.String_;
import gen.facts.p.Pub1_;
import gen.facts.p.Pub2_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Pub2;

import static me.supcheg.javafile.typed.Expressions.call;
import static org.assertj.core.api.Assertions.assertThat;

/// `Pub2 extends HBetween extends Pub1 extends H0`, where `HBetween` and
/// `H0` are not `public`: each `public` type tells the members of the
/// hidden supertypes up to the next `public` one, and no further.
public final class Chain {
    private Chain() {}

    public static void theMembersOfTheNearerHiddenSupertypeAreOfTheSubtype(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub2_.TOKEN, pub -> call(pub, Pub2_.between), new Pub2()))
                .isEqualTo("between");
        assertThat(typed.apply(String_.TOKEN, Pub2_.TOKEN, pub -> call(pub, Pub2_.two), new Pub2()))
                .isEqualTo("two");
    }

    public static void theMembersBeyondThePublicSupertypeAreOfThatSupertype(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Pub2_.TOKEN, pub -> call(pub, Pub1_.zero), new Pub2()))
                .isEqualTo("zero");
        assertThat(typed.apply(String_.TOKEN, Pub2_.TOKEN, pub -> call(pub, Pub1_.one), new Pub2()))
                .isEqualTo("one");
    }
}
