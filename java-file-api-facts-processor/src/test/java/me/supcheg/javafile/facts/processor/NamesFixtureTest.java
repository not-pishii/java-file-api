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
    void aFactNamedLikeAClassTheMetamodelStartsAnExpressionWithIsEscaped() throws Exception {
        Compilation compilation = generate("p.Tk.class, p.Other.class", """
                package p;
                public class Tk {
                    public int UnsafeFacts;
                    public int MemberTraits;
                    public int Float;
                    public int Other_;
                    public Other other() { return null; }
                    public int fine;
                }
                """, "package p; public class Other {}");
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Tk_"))
                .containsExactly("Float", "MemberTraits_", "Other__", "UnsafeFacts_", "fine", "new_", "other");
        assertThat(((FieldRef<?, ?>) fact(loader, "gen.facts.p.Tk_", "UnsafeFacts_")).name())
                .isEqualTo("UnsafeFacts");
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void theNameOfAFactDoesNotDependOnWhichOtherMembersGetAFact() throws Exception {
        // m(java.util.List<String>) has no fact yet, and still m(java.awt.List) is told from it
        Compilation compilation = generate("p.Ov.class", """
                package p;
                public class Ov {
                    public void m(java.awt.List list) {}
                    public void m(java.util.List<String> list) {}
                    public void n(java.awt.List list) {}
                    public void k(int i) {}
                    public <T> void k(T t) {}
                    public int size;
                    public <T> T size() { return null; }
                    public Ov(java.awt.List list) {}
                    public Ov(java.util.List<String> list) {}
                }
                """);
        ClassLoader loader = load(compilation);

        assertThat(factNames(loader, "gen.facts.p.Ov_"))
                .containsExactly("k_int", "m_java_awt_List", "n_List", "new_java_awt_List", "size");
        assertThat(warnings(compilation))
                .containsExactlyInAnyOrder(
                        "p.Ov: no fact of method m(java.util.List<java.lang.String>), which mentions the parameterized"
                                + " type java.util.List, and facts of generic types are not supported yet",
                        "p.Ov: no fact of method <T>k(T), which is generic, and facts of generic members are not"
                                + " supported yet",
                        "p.Ov: no fact of method <T>size(), which is generic, and facts of generic members are not"
                                + " supported yet",
                        "p.Ov: no fact of constructor Ov(java.util.List<java.lang.String>), which mentions the"
                                + " parameterized type java.util.List, and facts of generic types are not supported"
                                + " yet");
    }

    @Test
    void aMemberThatSharesItsNameWithOneThatHasNoFactYetGetsNoneEither() throws Exception {
        Compilation compilation = generate("p.Sh.class", """
                package p;
                public class Sh {
                    public int x_;
                    public int x;
                    public <T> T x() { return null; }
                    public void other() {}
                }
                """);
        ClassLoader loader = load(compilation);

        // x() would be x_ once it gets a fact: the field x_ may not take the name now
        assertThat(factNames(loader, "gen.facts.p.Sh_")).containsExactly("new_", "other", "x");
        assertThat(warnings(compilation))
                .contains("p.Sh: no fact of field x_, method <T>x(), which would all be named x_");
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
