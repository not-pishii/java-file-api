import gen.facts.p.Derived_;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;

import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The method table of a token holds every method a call on the type may
/// resolve to, whoever declares it — or an overload would be cast away
/// wrongly.
public final class MethodTables {
    private MethodTables() {}

    public static void theMethodTableHasWhatTheTypeInherits() {
        MethodTable methods = Derived_.TOKEN.methods();

        assertThat(methods.concreteMethods())
                .contains(
                        new MethodSignature("inherited", List.of()),
                        new MethodSignature("over", List.of()),
                        new MethodSignature("f", List.of()),
                        new MethodSignature("own", List.of()),
                        new MethodSignature("abs", List.of()),
                        new MethodSignature("dflt", List.of()),
                        new MethodSignature("hidden", List.of(ConstantDescs.CD_Object)),
                        // what javac counts among the candidates when it is accessible
                        new MethodSignature("prot", List.of()),
                        new MethodSignature("pkg", List.of()),
                        // and those of Object
                        new MethodSignature("hashCode", List.of()),
                        new MethodSignature("toString", List.of()),
                        new MethodSignature("equals", List.of(ConstantDescs.CD_Object)))
                .doesNotContain(new MethodSignature("priv", List.of()));
    }

    public static void anAbstractMethodTheTypeImplementsIsNotAbstract() {
        assertThat(Derived_.TOKEN.methods().abstractMethods()).isEmpty();
    }

    /// Not those of an interface the type implements: a call on the type does not resolve to them.
    public static void theStaticMethodsAreThoseOfTheTypeAndOfItsSuperclasses() {
        assertThat(Derived_.TOKEN.methods().staticMethods())
                .containsExactlyInAnyOrder(
                        new MethodSignature("sbase", List.of()),
                        new MethodSignature("hidden", List.of(ConstantDescs.CD_String)),
                        new MethodSignature("dstatic", List.of()));
    }
}
