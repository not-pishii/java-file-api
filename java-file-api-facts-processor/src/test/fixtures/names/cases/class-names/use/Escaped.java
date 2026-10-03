import gen.facts.p.Other_;
import gen.facts.p.Tk_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import p.Other;
import p.Tk;

import static org.assertj.core.api.Assertions.assertThat;

/// The metamodel starts expressions with the names of classes (`UnsafeFacts`, `MemberTraits`, `Other_`): a fact
/// named like one is escaped, and one that is named like no class used there (`Float`) is not.
public final class Escaped {
    private Escaped() {}

    public static void aFieldNamedLikeAClassTheMetamodelStartsAnExpressionWithIsEscaped() {
        MutableFieldRef<Tk, Prim.Int> unsafeFacts = Tk_.UnsafeFacts_;
        MutableFieldRef<Tk, Prim.Int> memberTraits = Tk_.MemberTraits_;
        MutableFieldRef<Tk, Prim.Int> metamodel = Tk_.Other__;
        MutableFieldRef<Tk, Prim.Int> float_ = Tk_.Float;

        assertThat(unsafeFacts.name()).isEqualTo("UnsafeFacts");
        assertThat(memberTraits.name()).isEqualTo("MemberTraits");
        assertThat(metamodel.name()).isEqualTo("Other_");
        assertThat(float_.name()).isEqualTo("Float");
    }

    public static void aMemberOfAnotherMetamodelIsReferredToWithoutTheClassOfTheMetamodelInTheWay() {
        MethodRef0<Tk, Other> other = Tk_.other;

        assertThat(other.resultType().orElseThrow()).isEqualTo(Other_.TOKEN);
    }
}
