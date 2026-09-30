package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The names of facts in full metamodels (mini-spec §2.2, §2.3): the rule of
/// [MetamodelNames] applied to a real type, and what happens where a name
/// cannot be given.
class NamesFixtureTest extends FixtureSupport {
    @Test
    void aMemberNamedLikeAReservedNameOrAFieldIsEscaped() throws Exception {
        Compilation compilation = generate("p.Res.class", """
                package p;
                public class Res {
                    public int sam;
                    public int TOKEN;
                    public int token;
                    public int ANY;
                    public int switch_;
                    public int Data;
                    public int Canonical;
                    public int count;
                    public int count() { return 0; }
                    public void sam(int x) {}
                }
                """);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Res_"))
                .containsExactly(
                        "ANY_",
                        "Canonical_",
                        "Data_",
                        "TOKEN_",
                        "count",
                        "count_",
                        "new_",
                        "sam_",
                        "sam_int",
                        "switch__",
                        "token_");
        assertThat(((FieldRef<?, ?>) fact(loader, "gen.facts.p.Res_", "TOKEN_")).name())
                .isEqualTo("TOKEN");
        assertThat(((FieldRef<?, ?>) fact(loader, "gen.facts.p.Res_", "count")).name())
                .isEqualTo("count");
        assertThat(((Invocable) fact(loader, "gen.facts.p.Res_", "count_")).name())
                .isEqualTo("count");
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void membersThatWouldShareANameGetNoFactAndAWarning() throws Exception {
        Compilation compilation = generate("p.Cf.class", """
                package p;
                public class Cf {
                    public int x;
                    public void x() {}
                    public void x_() {}
                    public void other() {}
                }
                """);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Cf_")).containsExactly("new_", "other", "x");
        assertThat(warnings(compilation))
                .containsExactly("p.Cf: no fact of method x(), method x_(), which would all be named x_");
    }

    @Test
    void underStrictMembersThatWouldShareANameAreAnError() {
        Compilation compilation = attempt(List.of("-Ajavafile.facts.strict=true"), "p.Cf.class", """
                package p;
                public class Cf {
                    public int x;
                    public void x() {}
                    public void x_() {}
                }
                """);

        assertThat(errors(compilation))
                .containsExactly("p.Cf: no fact of method x(), method x_(), which would all be named x_");
    }

    @Test
    void aFactNamedLikeAClassTheMetamodelStartsAnExpressionWithIsLeftOut() throws Exception {
        Compilation compilation = generate("p.Tk.class, p.Other.class", """
                package p;
                public class Tk {
                    public int UnsafeFacts;
                    public int MemberTraits;
                    public int Float;
                    public int gen;
                    public int Other_;
                    public Other other() { return null; }
                    public int fine;
                }
                """, "package p; public class Other {}");
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Tk_")).containsExactly("fine", "new_", "other");
        assertThat(warnings(compilation))
                .containsExactly(
                        "p.Tk: no fact of field UnsafeFacts, which would be named UnsafeFacts, which is a class the"
                                + " metamodel refers to",
                        "p.Tk: no fact of field MemberTraits, which would be named MemberTraits, which is a class the"
                                + " metamodel refers to",
                        "p.Tk: no fact of field Float, which would be named Float, which is a class the metamodel"
                                + " refers to",
                        "p.Tk: no fact of field gen, which would be named gen, which is a class the metamodel refers"
                                + " to",
                        "p.Tk: no fact of field Other_, which would be named Other_, which is a class the metamodel"
                                + " refers to");
    }

    @Test
    void aDeprecatedTypeInASignatureDoesNotMakeTheMetamodelWarn() throws Exception {
        Compilation compilation = generate("p.Uses.class", """
                package p;
                public class Uses {
                    public Old old() { return null; }
                    public void take(Old o) {}
                }
                """, """
                package p;
                @Deprecated public class Old {}
                """);

        assertThat(load(compilation)).isNotNull();
        assertThat(sources(compilation).get("gen.facts.p.Uses_")).contains("@SuppressWarnings({");
    }
}
