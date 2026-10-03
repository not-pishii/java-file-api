import gen.facts.p.Plain_;
import me.supcheg.javafile.facts.MethodSignature;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// A method of `Object` is a fact only of a type that overrides it — see
/// `expected/` — but the method table of every type has it.
public final class ObjectMethods {
    private ObjectMethods() {}

    public static void theMethodTableHasTheMethodsOfObjectTheTypeDoesNotOverride() {
        assertThat(Plain_.TOKEN.methods().concreteMethods()).contains(new MethodSignature("hashCode", List.of()));
    }
}
