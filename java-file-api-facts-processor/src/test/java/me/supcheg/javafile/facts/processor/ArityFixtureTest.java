package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.VoidMethodRef12;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `arity` (mini-spec §9.2): the families of facts end at 12
/// parameters; a member with more has no fact, with a warning.
class ArityFixtureTest extends FixtureSupport {
    private static final String P12 =
            "int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, int a12";
    private static final String P13 = P12 + ", int a13";
    private static final String LIBRARY = """
            package p;
            public class Big {
                public Big(%s) {}
                public Big(%s) {}
                public void twelve(%s) {}
                public void thirteen(%s) {}
                public static int staticThirteen(%s) { return 0; }
                public void ok() {}
            }
            """.formatted(P12, P13, P12, P13, P13);

    @Test
    void twelveParametersHaveAFactAndThirteenHaveNone() throws Exception {
        Compilation compilation = generate("p.Big.class", LIBRARY);
        ClassLoader loader = load(compilation);
        String twelve = "int_int_int_int_int_int_int_int_int_int_int_int";

        assertThat(factNames(loader, "gen.facts.p.Big_")).containsExactly("new_" + twelve, "ok", "twelve_" + twelve);
        assertThat(fact(loader, "gen.facts.p.Big_", "new_" + twelve)).isInstanceOf(CtorRef12.class);
        assertThat(fact(loader, "gen.facts.p.Big_", "twelve_" + twelve)).isInstanceOf(VoidMethodRef12.class);
        List<String> thirteen = warnings(compilation);
        assertThat(thirteen).hasSize(3);
        assertThat(thirteen.get(0))
                .startsWith("p.Big: no fact of constructor Big(int,int,")
                .endsWith(", which has 13 parameters, more than 12");
        assertThat(thirteen.get(1))
                .startsWith("p.Big: no fact of method thirteen(int,")
                .endsWith(", which has 13 parameters, more than 12");
        assertThat(thirteen.get(2)).startsWith("p.Big: no fact of method staticThirteen(int,");
    }

    @Test
    void underStrictThirteenParametersAreAnError() {
        Compilation compilation = attempt(List.of("-Ajavafile.facts.strict=true"), "p.Big.class", LIBRARY);

        assertThat(errors(compilation)).hasSize(3).allSatisfy(e -> assertThat(e).contains("more than 12"));
    }
}
