import gen.facts.p.Diamond_;
import gen.facts.p.Twins_;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.type.Types;
import p.Diamond;
import p.Twins;

import static org.assertj.core.api.Assertions.assertThat;

/// A member that comes to a type in several ways is one fact.
public final class SeveralWays {
    private SeveralWays() {}

    /// `Root`, through `Left` and through `Right`.
    public static void aMemberOfADiamondIsOneFact() {
        StaticFieldRef<Prim.Int> constant = Diamond_.ROOT;
        MethodRef0<Diamond, String> root = Diamond_.root;

        assertThat(constant.constantValue()).contains(1);
        assertThat(root.owner()).isEqualTo(Diamond_.TOKEN);
    }

    /// `Object twin()` of `Loose` and `String twin()` of `Tight`.
    public static void ofTwoAbstractMethodsOfOneSignatureTheFactIsTheOneOfTheMoreSpecificResult() {
        MethodRef0<Twins, String> twin = Twins_.twin;

        assertThat(twin.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(twin.traits()).isEqualTo(MemberTraits.ABSTRACT);
    }
}
