package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `nested` (mini-spec §9.2): `Outer.Inner` is `Outer_Inner_`; the
/// constructor of an inner class needs an enclosing instance, so it has no
/// fact; an inner class of a generic class is no metamodel.
class NestedFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Outer {
            public static class Inner {
                public Inner() {}
                public String s() { return null; }
            }
            public class NonStatic {
                public NonStatic() {}
                public NonStatic(int x) {}
                public int x() { return 0; }
            }
            public interface Api { void run(); }
            public enum E { A, B }
            public record R(int x) {}
            public Inner inner() { return null; }
            public NonStatic nonStatic() { return null; }
            public E e() { return null; }
            public static class Deep { public static class Deeper { public Deeper() {} } }
        }
        """,
        "package p; public class Gen<T> { public class In { public In() {} } public static class St { public St() {} } }"
    };

    @Test
    void aNestedStaticTypeIsNamedByItsChainAndHasItsFacts() throws Exception {
        Compilation compilation = generate("p.Outer.Inner.class, p.Outer.Deep.Deeper.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(sources(compilation).keySet())
                .contains("gen.facts.p.Outer_Inner_", "gen.facts.p.Outer_Deep_Deeper_");
        assertThat(token(loader, "gen.facts.p.Outer_Inner_")).isInstanceOf(OpenClassToken.class);
        assertThat(token(loader, "gen.facts.p.Outer_Inner_").typeRef())
                .isEqualTo(Types.of(ClassDesc.of("p.Outer$Inner")));
        assertThat(factNames(loader, "gen.facts.p.Outer_Inner_")).containsExactly("new_", "s");
        assertThat(factNames(loader, "gen.facts.p.Outer_Deep_Deeper_")).containsExactly("new_");
        assertThat(((Invocable) fact(loader, "gen.facts.p.Outer_Inner_", "s")).owner())
                .isEqualTo(token(loader, "gen.facts.p.Outer_Inner_"));
    }

    @Test
    void theConstructorOfAnInnerClassHasNoFact() throws Exception {
        Compilation compilation = generate("p.Outer.NonStatic.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Outer_NonStatic_")).containsExactly("x");
        assertThat(warnings(compilation))
                .containsExactly(
                        "p.Outer$NonStatic: no fact of constructor NonStatic(), which is the constructor of an inner"
                                + " class, which needs an enclosing instance",
                        "p.Outer$NonStatic: no fact of constructor NonStatic(int), which is the constructor of an"
                                + " inner class, which needs an enclosing instance");
    }

    @Test
    void nestedInterfacesEnumsAndRecordsAreTypesToo() throws Exception {
        ClassLoader loader = load(generate("p.Outer.Api.class, p.Outer.E.class, p.Outer.R.class", LIBRARY));

        assertThat(token(loader, "gen.facts.p.Outer_Api_")).isInstanceOf(InterfaceToken.class);
        assertThat(fact(loader, "gen.facts.p.Outer_Api_", "sam")).isNotNull();
        assertThat(token(loader, "gen.facts.p.Outer_E_")).isInstanceOf(EnumToken.class);
        assertThat(factNames(loader, "gen.facts.p.Outer_E_")).containsExactly("A", "B", "valueOf_String", "values");
        assertThat(factNames(loader, "gen.facts.p.Outer_R_")).contains("new_int", "x");
    }

    @Test
    void aMemberMentioningNestedTypesUsesTheirMetamodels() throws Exception {
        Compilation compilation = generate("p.Outer.class", LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Outer_")).containsExactly("e", "inner", "new_", "nonStatic");
        assertThat(((Invocable) fact(loader, "gen.facts.p.Outer_", "inner"))
                        .resultType()
                        .orElseThrow()
                        .typeRef())
                .isEqualTo(Types.of(ClassDesc.of("p.Outer$Inner")));
        assertThat(sources(compilation).keySet())
                .contains("gen.facts.p.Outer_Inner_", "gen.facts.p.Outer_NonStatic_", "gen.facts.p.Outer_E_");
    }

    @Test
    void anInnerClassOfAGenericClassIsNotAMetamodel() {
        Compilation compilation = attempt(java.util.List.of(), "p.Gen.In.class", LIBRARY);

        assertThat(errors(compilation))
                .containsExactly(
                        "no metamodel of p.Gen$In: inner class p.Gen.In of generic class p.Gen can mention its type"
                                + " parameters");
    }

    @Test
    void aStaticClassOfAGenericClassIsAMetamodel() throws Exception {
        ClassLoader loader = load(generate("p.Gen.St.class", LIBRARY));

        assertThat(factNames(loader, "gen.facts.p.Gen_St_")).containsExactly("new_");
    }
}
