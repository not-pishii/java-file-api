package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// What full metamodels leave to the generic ones (plan step 9) and to types
/// no metamodel can be made of: a member whose signature is generic, or
/// mentions a type without a metamodel, gets no fact, with a warning; a
/// requested type that is generic stays token-only.
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
        }
        """,
        "package p; public class Dol$lar {}",
        "package p; public @interface Marker {}",
        "package p; public class Box<T> { public T get() { return null; } public String label() { return null; } }"
    };

    @Test
    void aGenericSignatureGivesNoFactYet() throws Exception {
        Compilation compilation = generate("p.Mix.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Mix_")).containsExactly("plain");
        assertThat(warnings(compilation))
                .containsExactly(
                        "p.Mix: no fact of field field, which mentions the parameterized type"
                                + " java.util.function.Function, and facts of generic types are not supported yet",
                        "p.Mix: no fact of method names(), which mentions the parameterized type java.util.List, and"
                                + " facts of generic types are not supported yet",
                        "p.Mix: no fact of method raw(), which mentions the raw type java.util.List, and facts of"
                                + " generic types are not supported yet",
                        "p.Mix: no fact of method <T>id(T), which is generic, and facts of generic members are not"
                                + " supported yet",
                        "p.Mix: no fact of method wild(java.util.Map<?,? extends java.lang.Number>), which mentions"
                                + " the parameterized type java.util.Map, and facts of generic types are not"
                                + " supported yet",
                        "p.Mix: no fact of constructor Mix(java.util.Set<java.lang.String>), which mentions the"
                                + " parameterized type java.util.Set, and facts of generic types are not supported"
                                + " yet",
                        "p.Mix: no fact of constructor <T>Mix(T,int), which is generic, and facts of generic members"
                                + " are not supported yet",
                        "p.Mix: no fact of method array(), which mentions the parameterized type java.util.List, and"
                                + " facts of generic types are not supported yet",
                        "p.Mix: no fact of method dollar(), which mentions p.Dol$lar, which has no metamodel: a class"
                                + " with $ in its simple name is not supported yet",
                        "p.Mix: no fact of method marker(), which mentions p.Marker, which has no metamodel:"
                                + " annotation interface p.Marker is not supported yet");
    }

    @Test
    void theTypesTheSkippedSignaturesMentionStillGetTokenOnlyMetamodels() {
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

    @Test
    void aRequestedGenericTypeIsStillTokenOnly() throws Exception {
        Compilation compilation = generate("p.Box.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(sources(compilation).get("gen.facts.p.Box_")).contains("complete = false)");
        assertThat(ProcessorHarness.resources(compilation))
                .containsEntry("META-INF/javafile/metamodel/token/p.Box", "gen.facts.p.Box_\n")
                .doesNotContainKey("META-INF/javafile/metamodel/full/p.Box");
        assertThat(loader.loadClass("gen.facts.p.Box_").getFields())
                .extracting(f -> f.getName())
                .containsExactlyInAnyOrder("ANY", "token");
    }
}
