package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `visibility` (mini-spec §9.2, Q3, Q10): only `public` members get
/// facts; a `public` member with a type in its signature that is not
/// `public` gets none, with a warning — an error under `strict`.
class VisibilityFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Vis {
            public int pub;
            protected int prot;
            int pkg;
            private int priv;
            public Hidden hf;
            public java.util.List<Hidden> hl;
            public Vis() {}
            protected Vis(int x) {}
            Vis(String s) {}
            private Vis(long l) {}
            public Vis(Hidden h) {}
            public void pubM() {}
            protected void protM() {}
            void pkgM() {}
            private void privM() {}
            public Hidden hidden() { return null; }
            public void takes(Hidden h) {}
            public Hidden[] arr() { return null; }
            public void nested(Outer.PkgNested n) {}
            public Outer.Pub ok() { return null; }
            public static void sPub() {}
        }
        """,
        "package p; class Hidden {}",
        "package p; public class Outer { static class PkgNested {} public static class Pub {} }"
    };

    private static final List<String> WARNINGS = List.of(
            "p.Vis: no fact of field hf, which mentions types that are not public: p.Hidden",
            "p.Vis: no fact of field hl, which mentions types that are not public: p.Hidden",
            "p.Vis: no fact of constructor Vis(p.Hidden), which mentions types that are not public: p.Hidden",
            "p.Vis: no fact of method hidden(), which mentions types that are not public: p.Hidden",
            "p.Vis: no fact of method takes(p.Hidden), which mentions types that are not public: p.Hidden",
            "p.Vis: no fact of method arr(), which mentions types that are not public: p.Hidden",
            "p.Vis: no fact of method nested(p.Outer.PkgNested), which mentions types that are not public:"
                    + " p.Outer$PkgNested");

    @Test
    void onlyPublicMembersGetFacts() throws Exception {
        Compilation compilation = generate("p.Vis.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Vis_")).containsExactly("new_", "ok", "pub", "pubM", "sPub");
        assertThat(sources(compilation).keySet())
                .doesNotContain("gen.facts.p.Hidden_", "gen.facts.p.Outer_PkgNested_")
                .contains("gen.facts.p.Outer_Pub_");
    }

    @Test
    void protectedAndPackagePrivateMembersAreLeftOutWithoutAWarning() {
        Compilation compilation = generate("p.Vis.class", LIBRARY);

        assertThat(warnings(compilation)).noneMatch(w -> w.contains("prot") || w.contains("pkg") || w.contains("priv"));
        assertThat(warnings(compilation)).containsExactlyElementsOf(WARNINGS);
    }

    @Test
    void protectedMembersAreInTheMethodTableAllTheSame() throws Exception {
        ClassLoader loader = load(generate("p.Vis.class", LIBRARY));
        var methods = token(loader, "gen.facts.p.Vis_").methods();

        assertThat(methods.concreteMethods())
                .extracting(m -> m.name())
                .contains("protM", "pkgM")
                .doesNotContain("privM");
    }

    @Test
    void underStrictAMemberWithoutAFactIsAnError() {
        Compilation compilation = attempt(List.of("-Ajavafile.facts.strict=true"), "p.Vis.class", LIBRARY);

        assertThat(errors(compilation)).containsExactlyElementsOf(WARNINGS);
        assertThat(warnings(compilation)).isEmpty();
    }
}
