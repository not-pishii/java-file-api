import gen.facts.java.io.IOException_;
import gen.facts.java.lang.Integer_;
import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.Both_;
import gen.facts.p.Box_;
import gen.facts.p.Fail_;
import gen.facts.p.Sorted_;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Both;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The type parameters of a metamodel keep the bounds of those of its
/// type: javac takes a token only of a type argument within them. What it
/// rejects is in `use-fails/`.
public final class Bounds {
    private Bounds() {}

    public static void aTokenOfATypeArgumentWithinBoundsIsTaken() {
        Sorted_<String> inferred = new Sorted_<>(String_.TOKEN);
        Sorted_<String> explicit = new Sorted_<String>(String_.TOKEN);
        Both_<Integer> both = new Both_<>(Integer_.TOKEN);
        Box_<Object> unbounded = new Box_<>(Object_.TOKEN);

        assertThat(inferred.token.typeRef()).isEqualTo(explicit.token.typeRef());
        assertThat(both.token.argumentErasures()).containsExactly(ClassDesc.of("java.lang.Integer"));
        assertThat(unbounded.token.argumentErasures()).containsExactly(ClassDesc.of("java.lang.Object"));
    }

    public static void theShapeHasTheBoundsOfTheTypeParameters() {
        assertThat(Both_.Data.SHAPE.typeParameters())
                .containsExactly(new TypeParam(
                        "T",
                        List.of(
                                Types.of(ClassDesc.of("java.lang.Number")),
                                Types.parameterized(ClassDesc.of("java.lang.Comparable"), Types.typeVar("T")))));
    }

    /// `Both` is abstract: its constructor is a fact for `super(…)` alone.
    public static void theFactsOfABoundedTypeAreInTermsOfItsArgument() {
        Both_<Integer> both = new Both_<>(Integer_.TOKEN);
        AbstractCtorRef0<Both<Integer>> none = both.super_;
        MethodRef0<Both<Integer>, Integer> pick = both.pick;

        assertThat(none.owner()).isSameAs(both.token);
        assertThat(pick.result()).isSameAs(Integer_.TOKEN);
        assertThat(pick.traits()).isEqualTo(MemberTraits.ABSTRACT);
    }

    /// `Fail<X extends Exception>` and `void run() throws X`.
    public static void aMethodOfAGenericTypeThrowsTheTokenOfItsTypeParameter() {
        Fail_<IOException> fail = new Fail_<>(IOException_.TOKEN);

        assertThat(fail.run.traits().throwsTypes()).containsExactly(IOException_.TOKEN);
    }
}
