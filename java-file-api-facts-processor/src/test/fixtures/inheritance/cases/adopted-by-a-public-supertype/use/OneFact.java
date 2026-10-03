import gen.facts.p.X_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodSignature;
import p.X;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// `X extends Mid implements HiddenI`, and `Mid implements HiddenI` too:
/// `Mid_` has adopted the members of `HiddenI`, and `X_` does not adopt
/// them again — one member, one fact. `more()` is declared by `X` itself.
public final class OneFact {
    private OneFact() {}

    public static void aMemberTheSubtypeOverridesIsItsOwn() {
        MethodRef0<X, String> more = X_.more;

        assertThat(more.owner()).isSameAs(X_.TOKEN);
    }

    public static void theMethodTableOfTheSubtypeHasEveryMethodACallOnItMayResolveTo() {
        assertThat(X_.TOKEN.methods().concreteMethods())
                .contains(
                        new MethodSignature("run", List.of()),
                        new MethodSignature("more", List.of()),
                        new MethodSignature("mid", List.of()),
                        new MethodSignature("own", List.of()));
    }
}
