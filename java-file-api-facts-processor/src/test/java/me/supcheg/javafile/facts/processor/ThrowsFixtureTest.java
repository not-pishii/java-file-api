package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `throws` (mini-spec §9.2): the `throws` clause of a member is a
/// token per exception type, in declaration order.
class ThrowsFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Io {
            public Io() throws java.io.IOException {}
            public Io(int x) {}
            public void read() throws java.io.IOException {}
            public void multi() throws java.io.IOException, InterruptedException, IllegalStateException {}
            public int unchecked() throws IllegalArgumentException { return 0; }
            public static void util() throws Exception {}
            public void custom() throws Failure {}
            public final void locked() throws Failure {}
            public <X extends Throwable> void generic() throws X {}
            public void hidden() throws Secret {}
            public Io(String s) throws Secret {}
        }
        """, "package p; public class Failure extends Exception {}", "package p; class Secret extends Exception {}"
    };

    private static List<Object> thrown(ClassLoader loader, String name) throws ReflectiveOperationException {
        return ((Invocable) fact(loader, "gen.facts.p.Io_", name))
                .traits().throwsTypes().stream().<Object>map(RefToken::typeRef).toList();
    }

    private static ClassDesc desc(String name) {
        return ClassDesc.of(name);
    }

    @Test
    void theThrowsClauseIsATokenPerExceptionInDeclarationOrder() throws Exception {
        ClassLoader loader = load(generate("p.Io.class", LIBRARY));

        assertThat(thrown(loader, "new_")).containsExactly(Types.of(desc("java.io.IOException")));
        assertThat(thrown(loader, "new_int")).isEmpty();
        assertThat(thrown(loader, "read")).containsExactly(Types.of(desc("java.io.IOException")));
        assertThat(thrown(loader, "multi"))
                .containsExactly(
                        Types.of(desc("java.io.IOException")),
                        Types.of(desc("java.lang.InterruptedException")),
                        Types.of(desc("java.lang.IllegalStateException")));
        assertThat(thrown(loader, "unchecked")).containsExactly(Types.of(desc("java.lang.IllegalArgumentException")));
        assertThat(thrown(loader, "util")).containsExactly(Types.of(desc("java.lang.Exception")));
        assertThat(thrown(loader, "custom")).containsExactly(Types.of(desc("p.Failure")));
    }

    @Test
    void theThrowsClauseKeepsTheOverridabilityOfTheMember() throws Exception {
        ClassLoader loader = load(generate("p.Io.class", LIBRARY));

        assertThat(((Invocable) fact(loader, "gen.facts.p.Io_", "custom"))
                        .traits()
                        .overridability())
                .isEqualTo(MemberTraits.OVERRIDABLE.overridability());
        assertThat(((Invocable) fact(loader, "gen.facts.p.Io_", "locked"))
                        .traits()
                        .overridability())
                .isEqualTo(MemberTraits.FINAL.overridability());
        assertThat(((Invocable) fact(loader, "gen.facts.p.Io_", "util"))
                        .traits()
                        .overridability())
                .isEqualTo(MemberTraits.FINAL.overridability());
        assertThat(((Invocable) fact(loader, "gen.facts.p.Io_", "new_"))
                        .traits()
                        .overridability())
                .isEqualTo(MemberTraits.FINAL.overridability());
    }

    @Test
    void anExceptionThatIsNotPublicOrIsATypeVariableGivesNoFact() throws Exception {
        Compilation compilation = generate("p.Io.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Io_"))
                .containsExactly("custom", "locked", "multi", "new_", "new_int", "read", "unchecked", "util");
        assertThat(warnings(compilation))
                .containsExactly(
                        "p.Io: no fact of method <X>generic(), which is generic, and facts of generic members are not"
                                + " supported yet",
                        "p.Io: no fact of method hidden(), which mentions types that are not public: p.Secret",
                        "p.Io: no fact of constructor Io(java.lang.String), which mentions types that are not public:"
                                + " p.Secret");
    }
}
