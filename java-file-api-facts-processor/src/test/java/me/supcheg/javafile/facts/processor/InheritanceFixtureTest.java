package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `inheritance` (mini-spec §9.2, Q6(b)): a metamodel has the facts
/// of the members its type declares, and an inherited member is a fact of
/// the metamodel of its supertype; but the method table holds every method a
/// call on the type may resolve to, or an overload would be cast away
/// wrongly.
class InheritanceFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Base {
            public static int sbase() { return 0; }
            public static void hidden(String s) {}
            public void inherited() {}
            public void over() {}
            public int f() { return 0; }
            protected void prot() {}
            void pkg() {}
            private void priv() {}
        }
        """,
        """
        package p;
        public class Derived extends Base implements Api {
            public void over() {}
            public static void dstatic() {}
            public void hidden(Object o) {}
            public void own() {}
            public void abs() {}
        }
        """,
        "package p; public interface Api { static void iface() {} default void dflt() {} void abs(); }",
        """
        package p;
        public class WithObject {
            public String toString() { return "x"; }
            public boolean equals(Object o) { return false; }
            public int hashCode() { return 0; }
            public void plain() {}
        }
        """,
        "package p; public class Plain { public void plain() {} }"
    };

    @Test
    void aMetamodelHasOnlyTheMembersItsTypeDeclares() throws Exception {
        ClassLoader loader = load(generate("p.Derived.class, p.Base.class", LIBRARY));

        assertThat(factNames(loader, "gen.facts.p.Derived_"))
                .containsExactly("abs", "dstatic", "hidden_Object", "new_", "over", "own");
        assertThat(factNames(loader, "gen.facts.p.Base_"))
                .containsExactly("f", "hidden_String", "inherited", "new_", "over", "sbase");
        assertThat(((Invocable) fact(loader, "gen.facts.p.Derived_", "over")).owner())
                .isEqualTo(token(loader, "gen.facts.p.Derived_"));
        assertThat(((Invocable) fact(loader, "gen.facts.p.Derived_", "over")).traits())
                .isEqualTo(MemberTraits.OVERRIDABLE);
    }

    @Test
    void theMethodTableHasWhatTheTypeInheritsIncludingStatics() throws Exception {
        ClassLoader loader = load(generate("p.Derived.class", LIBRARY));
        var methods = token(loader, "gen.facts.p.Derived_").methods();
        ClassDesc object = ConstantDescs.CD_Object;

        assertThat(methods.concreteMethods())
                .contains(
                        new MethodSignature("inherited", List.of()),
                        new MethodSignature("over", List.of()),
                        new MethodSignature("f", List.of()),
                        new MethodSignature("own", List.of()),
                        new MethodSignature("abs", List.of()),
                        new MethodSignature("dflt", List.of()),
                        new MethodSignature("hidden", List.of(object)),
                        // what javac counts among the candidates when it is accessible
                        new MethodSignature("prot", List.of()),
                        new MethodSignature("pkg", List.of()),
                        // and those of Object
                        new MethodSignature("hashCode", List.of()),
                        new MethodSignature("toString", List.of()),
                        new MethodSignature("equals", List.of(object)))
                .doesNotContain(new MethodSignature("priv", List.of()));
        // implemented abstract methods are not abstract
        assertThat(methods.abstractMethods()).isEmpty();
        // statics of the type and of its superclass; not those of an interface it implements
        assertThat(methods.staticMethods())
                .containsExactlyInAnyOrder(
                        new MethodSignature("sbase", List.of()),
                        new MethodSignature("hidden", List.of(ConstantDescs.CD_String)),
                        new MethodSignature("dstatic", List.of()));
    }

    @Test
    void anObjectMethodIsAFactOnlyWhereTheTypeOverridesIt() throws Exception {
        ClassLoader loader = load(generate("p.WithObject.class, p.Plain.class", LIBRARY));

        assertThat(factNames(loader, "gen.facts.p.WithObject_"))
                .containsExactly("equals_Object", "hashCode", "new_", "plain", "toString");
        assertThat(factNames(loader, "gen.facts.p.Plain_")).containsExactly("new_", "plain");
        assertThat(token(loader, "gen.facts.p.Plain_").methods().concreteMethods())
                .contains(new MethodSignature("hashCode", List.of()));
    }

    @Test
    void anInheritedMethodIsReachedThroughTheMetamodelOfTheSupertype() throws Exception {
        Compilation compilation = generate("p.Derived.class", LIBRARY);
        ClassLoader loader = load(compilation);

        // Derived_ does not mention the metamodel of Base or of Api: it is not asked for
        assertThat(sources(compilation).keySet()).doesNotContain("gen.facts.p.Base_", "gen.facts.p.Api_");
        assertThat(factNames(loader, "gen.facts.p.Derived_")).doesNotContain("inherited", "f", "dflt", "sbase");
    }
}
