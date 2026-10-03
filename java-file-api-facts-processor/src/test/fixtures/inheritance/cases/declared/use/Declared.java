import gen.facts.p.Derived_;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.VoidMethodRef0;
import p.Derived;

import static org.assertj.core.api.Assertions.assertThat;

/// A metamodel has the facts of the members its type declares.
public final class Declared {
    private Declared() {}

    public static void aMethodTheTypeOverridesIsAFactOfItsOwn() {
        VoidMethodRef0<Derived> over = Derived_.over;

        assertThat(over.owner()).isEqualTo(Derived_.TOKEN);
        assertThat(over.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
    }
}
