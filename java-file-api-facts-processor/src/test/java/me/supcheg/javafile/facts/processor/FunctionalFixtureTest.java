package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `functional` (mini-spec §9.2): the single abstract method of an
/// interface by JLS 9.8 has a `sam` fact — declared by the interface or
/// inherited from a superinterface — and what is not functional has none.
class FunctionalFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        "package p; public interface Fn { String apply(String s); boolean equals(Object o); }",
        "package p; public interface Sub extends Fn {}",
        "package p; public interface Sub2 extends Sub { default String twice(String s) { return apply(apply(s)); } }",
        "package p; public interface Redecl extends Fn { String apply(String s); }",
        "package p; public interface Run { void run(); }",
        "package p; public interface Two { void a(); void b(); }",
        "package p; public interface Wide extends Two { default void a() {} }",
        "package p; public interface Empty {}",
        "package p; public interface Gen { <T> T id(T t); }",
        "package p; public interface Def extends Fn { default String apply(String s) { return s; } }",
        "package p; public interface Comp { int compareTo(Comp o); String toString(); int hashCode(); }",
        "package p; public interface WithDefault { int f(int x); default int g(int x) { return f(x); } static WithDefault id() { return x -> x; } }",
        "package p; public interface StrFn extends java.util.function.Function<String, String> {}",
        "package p; public interface StrOp extends StrFn {}",
        "package p; class Secret {}",
        "package p; public interface Hid { Secret s(); }",
        "package p; public interface Hid2 extends Hid {}",
        "package p; public interface Base { Object get(); }",
        "package p; public interface Sub3 extends Base { String get(); }",
        "package p; public interface Fn2 { void call(String a, int b, long c); }",
    };

    private static final String ALL =
            "p.Fn.class, p.Sub.class, p.Sub2.class, p.Redecl.class, p.Run.class, p.Two.class, p.Wide.class,"
                    + " p.Empty.class, p.Gen.class, p.Def.class, p.Comp.class, p.WithDefault.class, p.StrFn.class,"
                    + " p.StrOp.class, p.Hid.class, p.Hid2.class, p.Sub3.class, p.Fn2.class";

    private static boolean hasSam(ClassLoader loader, String metamodel) {
        try {
            loader.loadClass(metamodel).getField("sam");
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    @Test
    void anInterfaceWithASingleAbstractMethodHasASam() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);

        // equals(Object) among the abstract methods does not count (JLS 9.8)
        Sam1<?, ?, ?> fn = (Sam1<?, ?, ?>) fact(loader, "gen.facts.p.Fn_", "sam");
        assertThat(fn.method()).isSameAs(fact(loader, "gen.facts.p.Fn_", "apply_String"));
        assertThat(fn.owner()).isEqualTo(token(loader, "gen.facts.p.Fn_"));
        assertThat(fn.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(fn.param1().typeRef()).isEqualTo(Types.STRING);
        assertThat(fact(loader, "gen.facts.p.Fn_", "equals_Object")).isInstanceOf(MethodRef1.class);

        // toString() and hashCode() do not count either
        assertThat(fact(loader, "gen.facts.p.Comp_", "sam")).isInstanceOf(Sam1.class);

        assertThat(fact(loader, "gen.facts.p.Run_", "sam")).isInstanceOf(VoidSam0.class);
        assertThat(fact(loader, "gen.facts.p.WithDefault_", "sam")).isInstanceOf(Sam1.class);
        assertThat(((Invocable) fact(loader, "gen.facts.p.WithDefault_", "g_int")).traits())
                .isEqualTo(MemberTraits.OVERRIDABLE);
        assertThat(fact(loader, "gen.facts.p.Fn2_", "sam")).isNotNull();
        assertThat(token(loader, "gen.facts.p.Run_")).isInstanceOf(InterfaceToken.class);
    }

    @Test
    void aSamIsThereWhetherTheAbstractMethodIsDeclaredOrInherited() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);

        // Q6(b): only declared members are facts, but sam is always there
        for (String inheriting : new String[] {"Sub", "Sub2", "StrFn", "StrOp"}) {
            String metamodel = "gen.facts.p." + inheriting + "_";
            assertThat(fact(loader, metamodel, "sam")).as(inheriting).isInstanceOf(Sam1.class);
            Sam1<?, ?, ?> sam = (Sam1<?, ?, ?>) fact(loader, metamodel, "sam");
            assertThat(sam.method().name()).isEqualTo("apply");
            assertThat(sam.owner()).isEqualTo(token(loader, metamodel));
            assertThat(sam.method().owner()).isEqualTo(token(loader, metamodel));
            assertThat(sam.method().traits()).isEqualTo(MemberTraits.ABSTRACT);
            assertThat(sam.result().typeRef()).isEqualTo(Types.STRING);
            assertThat(sam.param1().typeRef()).isEqualTo(Types.STRING);
        }
        assertThat(factNames(loader, "gen.facts.p.Sub_")).containsExactly("sam");
        assertThat(factNames(loader, "gen.facts.p.StrFn_")).containsExactly("sam");
        assertThat(factNames(loader, "gen.facts.p.Sub2_")).containsExactly("sam", "twice_String");
        // an inherited abstract method that is void
        assertThat(fact(loader, "gen.facts.p.Wide_", "sam")).isInstanceOf(VoidSam0.class);
        assertThat(((VoidSam0<?>) fact(loader, "gen.facts.p.Wide_", "sam"))
                        .method()
                        .name())
                .isEqualTo("b");
        assertThat(factNames(loader, "gen.facts.p.Wide_")).containsExactly("a", "sam");
        // the interface redeclares the method: the sam is that fact
        assertThat(((Sam1<?, ?, ?>) fact(loader, "gen.facts.p.Redecl_", "sam")).method())
                .isSameAs(fact(loader, "gen.facts.p.Redecl_", "apply_String"));
        // the result is the most specific one, not the one of the first supertype
        Sam0<?, ?> covariant = (Sam0<?, ?>) fact(loader, "gen.facts.p.Sub3_", "sam");
        assertThat(covariant.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(sources(compilation).get("gen.facts.p.Sub_"))
                .contains("Sam1<Sub, String, String> sam = UnsafeFacts.sam(UnsafeFacts.method(TOKEN, \"apply\"");
    }

    @Test
    void whatIsNotFunctionalHasNoSam() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));

        assertThat(hasSam(loader, "gen.facts.p.Two_"))
                .as("two abstract methods")
                .isFalse();
        assertThat(hasSam(loader, "gen.facts.p.Empty_")).as("none").isFalse();
        assertThat(hasSam(loader, "gen.facts.p.Def_"))
                .as("the method is overridden by a default")
                .isFalse();
        assertThat(hasSam(loader, "gen.facts.p.Gen_"))
                .as("generic: a lambda cannot implement it")
                .isFalse();
        assertThat(factNames(loader, "gen.facts.p.Def_")).containsExactly("apply_String");
        assertThat(((Invocable) fact(loader, "gen.facts.p.Def_", "apply_String")).traits())
                .isEqualTo(MemberTraits.OVERRIDABLE);
    }

    @Test
    void aSamWhoseSignatureHasNoFactIsReportedOnce() {
        Compilation compilation = generate(ALL, LIBRARY);

        assertThat(warnings(compilation))
                .contains(
                        "p.Gen: no fact of method <T>id(T), which is generic, and facts of generic members are not"
                                + " supported yet",
                        "p.Hid: no fact of method s(), which mentions types that are not public: p.Secret",
                        "p.Hid2: no fact of the single abstract method, which mentions types that are not public:"
                                + " p.Secret")
                .hasSize(3);
    }

    @Test
    void theTypesOfAnInheritedSamAreInTheClosureOfTheInterface() throws Exception {
        Compilation compilation = generate("p.StrFn.class", LIBRARY);
        ClassLoader loader = load(compilation);

        // nothing of StrFn declares String, but its sam does
        assertThat(sources(compilation).keySet()).contains("gen.facts.p.StrFn_", "gen.facts.java.lang.String_");
        assertThat(fact(loader, "gen.facts.p.StrFn_", "sam")).isInstanceOf(Sam1.class);
        assertThat(warnings(compilation)).isEmpty();
    }
}
