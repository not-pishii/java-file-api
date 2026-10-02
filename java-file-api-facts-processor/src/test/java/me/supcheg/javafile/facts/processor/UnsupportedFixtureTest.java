package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// What a full metamodel leaves out for good (Q10): a member whose signature
/// mentions a type no metamodel can be made of, and a generic constructor,
/// get no fact, with a warning. A signature that is generic is no reason any
/// more.
class UnsupportedFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Mix {
            public String plain() { return null; }
            public java.util.List<String> names() { return null; }
            public java.util.List raw() { return null; }
            public <T> T id(T t) { return null; }
            public void wild(java.util.Map<?, ? extends Number> m) {}
            public Mix(java.util.Set<String> s) {}
            public <T> Mix(T t, int x) {}
            public java.util.List<String>[] array() { return null; }
            public java.util.function.Function<String, String> field;
            public Dol$lar dollar() { return null; }
            public Marker marker() { return null; }
            public java.util.List<Marker> markers() { return null; }
            public <T extends Marker> void marked(T t) {}
        }
        """, "package p; public class Dol$lar {}", "package p; public @interface Marker {}"
    };

    @Test
    void aGenericSignatureGivesAFact() throws Exception {
        Compilation compilation = generate("p.Mix.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(memberNames(loader, "gen.facts.p.Mix_"))
                .containsExactly("array", "field", "id_T", "names", "new_Set", "plain", "raw", "wild_Map");
    }

    @Test
    void aGenericConstructorAndATypeWithoutAMetamodelGiveNoFact() {
        Compilation compilation = generate("p.Mix.class", LIBRARY);

        assertThat(warnings(compilation))
                .containsExactly(
                        "p.Mix: no fact of constructor <T>Mix(T,int), which is a generic constructor, whose type"
                                + " arguments a fact cannot give explicitly",
                        "p.Mix: no fact of method dollar(), which mentions p.Dol$lar, which has no metamodel: a class"
                                + " with $ in its simple name is not supported yet",
                        "p.Mix: no fact of method marker(), which mentions p.Marker, which has no metamodel:"
                                + " annotation interface p.Marker is not supported yet",
                        // as a type argument and as a bound of a type parameter too
                        "p.Mix: no fact of method markers(), which mentions p.Marker, which has no metamodel:"
                                + " annotation interface p.Marker is not supported yet",
                        "p.Mix: no fact of method <T>marked(T), which mentions p.Marker, which has no metamodel:"
                                + " annotation interface p.Marker is not supported yet");
    }

    @Test
    void theTypesTheSignaturesMentionGetTokenOnlyMetamodels() {
        Compilation compilation = generate("p.Mix.class", LIBRARY);

        assertThat(sources(compilation).keySet())
                .contains("gen.facts.java.util.List_", "gen.facts.java.util.Map_", "gen.facts.java.lang.String_");
    }

    @Test
    void underStrictATypeWithoutAMetamodelInASignatureIsAnError() {
        Compilation compilation = attempt(List.of("-Ajavafile.facts.strict=true"), "p.Mix.class", LIBRARY);

        assertThat(errors(compilation))
                .contains("p.Mix: no fact of method dollar(), which mentions p.Dol$lar, which has no metamodel: a class"
                        + " with $ in its simple name is not supported yet");
    }
}
