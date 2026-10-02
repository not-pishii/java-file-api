package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// [Canonical]: the text, its stability under changes that do not change
/// the type, and the sensitivity of the fingerprint to those that do.
class CanonicalTest {

    private static final String BASE = """
            package p;
            public class T<E extends Number> extends Base implements Comparable<T<E>>, Runnable {
                public static final int K = 1;
                public E value;
                public T(E e) throws java.io.IOException, InterruptedException {}
                public E get(int i) throws Exception { return null; }
                public static <X extends CharSequence> X make(X x, int[] is) { return x; }
                public void fill(E[] es) {}
                public int compareTo(T<E> o) { return 0; }
                public void run() {}
                public void put(E e, java.util.List<? super E> lower, java.util.List<? extends E> upper, java.util.List<?> any) {}
                public Hidden hidden(int i) { return null; }
                void pack() {}
            }
            class Base {}
            class Hidden {}
            class Hidden2 {}
            """;

    private static Canonical canonical(String name, MemberFilter filter, String... sources) {
        return Harness.run(env -> Canonical.of(Harness.ok(env.translator().type(env.element(name), filter))), sources);
    }

    private static String fingerprint(String source) {
        return canonical("p.T", MemberFilter.DECLARED_PUBLIC, source).fingerprint();
    }

    @Test
    void textOfAType() {
        Canonical canonical = canonical("p.T", MemberFilter.DECLARED_PUBLIC, BASE);

        assertThat(canonical.text()).isEqualTo("""
                        javafile-facts-canonical 1
                        type p.T open-class sealed=no
                        tparams #0 extends java.lang.Number
                        superclasses p.Base; java.lang.Object
                        supertypes java.lang.Comparable<p.T<#0>>
                        enum -
                        members declared-public
                        member ctor(#0) throws java.io.IOException, java.lang.InterruptedException
                        member field instance mutable #0 value
                        member field static constant int K = 1
                        member method overridable compareTo(p.T<#0>) -> int throws -
                        member method overridable fill(#0[]) -> void throws -
                        member method overridable get(int) -> #0 throws java.lang.Exception
                        member method overridable put(#0, java.util.List<? super #0>, java.util.List<? extends #0>, \
                        java.util.List<?>) -> void throws -
                        member method overridable run() -> void throws -
                        member method static <^0 extends java.lang.CharSequence> make(^0, int[]) -> ^0 throws -
                        table abstract -
                        table concrete clone(); compareTo(p.T); equals(java.lang.Object); fill(java.lang.Number[]); \
                        finalize(); get(int); getClass(); hashCode(); hidden(int); notify(); notifyAll(); pack(); \
                        put(#0, java.util.List, java.util.List, java.util.List); run(); toString(); wait(); wait(long); wait(long, int)
                        table static make(java.lang.CharSequence, int[])
                        """);
    }

    @Test
    void textOfATokenOnlyModel() {
        Canonical canonical = canonical("p.Day", MemberFilter.NONE, """
                package p;
                public enum Day implements java.util.function.Supplier<String> {
                    MONDAY, SUNDAY;
                    public String get() { return ""; }
                }
                """);

        assertThat(canonical.text())
                .startsWith("""
                        javafile-facts-canonical 1
                        type p.Day enum sealed=no
                        tparams -
                        superclasses java.lang.Enum; java.lang.Object
                        supertypes java.lang.Comparable<p.Day>; java.lang.Enum<p.Day>; \
                        java.util.function.Supplier<java.lang.String>
                        enum MONDAY; SUNDAY
                        members none
                        table abstract -
                        """)
                .contains("\ntable static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); "
                        + "values()\n");
    }

    @Test
    void textOfConstantsIsAsciiAndExact() {
        Canonical canonical = canonical("p.Constants", MemberFilter.DECLARED_PUBLIC, """
                package p;
                public class Constants {
                    private Constants() {}
                    public static final boolean Z = true;
                    public static final byte B = -1;
                    public static final short S = 2;
                    public static final char C = '\\n';
                    public static final char Q = '\\'';
                    public static final int I = 3;
                    public static final long J = 4L;
                    public static final float F = 0.5f;
                    public static final double D = -0.0;
                    public static final double NAN = Double.NaN;
                    public static final double INF = Double.POSITIVE_INFINITY;
                    public static final float FNAN = Float.NaN;
                    public static final String STR = "a\\"b\\\\c\\u00e9\\t~";
                }
                """);

        assertThat(canonical.text())
                .contains(
                        "member field static constant boolean Z = true\n",
                        "member field static constant byte B = -1\n",
                        "member field static constant short S = 2\n",
                        "member field static constant char C = '\\u000a'\n",
                        "member field static constant char Q = '\\u0027'\n",
                        "member field static constant int I = 3\n",
                        "member field static constant long J = 4L\n",
                        "member field static constant float F = 0.5f\n",
                        "member field static constant double D = -0.0\n",
                        "member field static constant double NAN = NaN\n",
                        "member field static constant double INF = Infinity\n",
                        "member field static constant float FNAN = NaNf\n",
                        "member field static constant java.lang.String STR = \"a\\u0022b\\u005cc\\u00e9\\u0009~\"\n");
        assertThat(StandardCharsets.US_ASCII.newEncoder().canEncode(canonical.text()))
                .isTrue();
    }

    @Test
    void fingerprintIsTheSha256OfTheTextAsAMetamodelRecordsIt() throws NoSuchAlgorithmException {
        Canonical canonical = canonical("p.T", MemberFilter.DECLARED_PUBLIC, BASE);

        assertThat(canonical.fingerprint())
                .isEqualTo(HexFormat.of()
                        .formatHex(MessageDigest.getInstance("SHA-256")
                                .digest(canonical.text().getBytes(StandardCharsets.UTF_8))));
        ShapeOrigin.Metamodel origin =
                new ShapeOrigin.Metamodel(ClassDesc.of("q.T_"), canonical.fingerprint(), canonical::text);
        assertThat(origin.fingerprint()).isEqualTo(canonical.fingerprint());
    }

    @Test
    void equalityIsByText() {
        Canonical one = canonical("p.T", MemberFilter.DECLARED_PUBLIC, BASE);
        Canonical two = canonical("p.T", MemberFilter.DECLARED_PUBLIC, BASE);
        Canonical other = canonical("p.T", MemberFilter.NONE, BASE);

        assertThat(one)
                .isEqualTo(two)
                .hasSameHashCodeAs(two)
                .isNotEqualTo(other)
                .isNotEqualTo("text");
        assertThat(one).hasToString(one.text());
    }

    // ---- stability

    @Test
    void renamingTypeVariablesChangesNothing() {
        String renamed = BASE.replaceAll("\\bE\\b", "N")
                .replace("<X extends CharSequence> X make(X x", "<Y extends CharSequence> Y make(Y x");

        assertThat(renamed).isNotEqualTo(BASE);
        assertThat(fingerprint(renamed)).isEqualTo(fingerprint(BASE));
    }

    @Test
    void reorderingDeclarationsChangesNothing() {
        String reordered = """
                package p;
                public class T<E extends Number> extends Base implements Runnable, Comparable<T<E>> {
                    void pack() {}
                    public Hidden hidden(int i) { return null; }
                    public void put(E e, java.util.List<? super E> lower, java.util.List<? extends E> upper, java.util.List<?> any) {}
                    public void run() {}
                    public int compareTo(T<E> o) { return 0; }
                    public static <X extends CharSequence> X make(X x, int[] is) { return x; }
                    public void fill(E[] es) {}
                    public E get(int i) throws Exception { return null; }
                    public T(E e) throws InterruptedException, java.io.IOException {}
                    public E value;
                    public static final int K = 1;
                }
                class Hidden {}
                class Base {}
                """;

        assertThat(fingerprint(reordered)).isEqualTo(fingerprint(BASE));
    }

    @Test
    void skippedMembersAreNotHashed() {
        String changed = BASE.replace("public Hidden hidden(int i)", "public Hidden2 hidden(int i)");

        assertThat(fingerprint(changed)).isEqualTo(fingerprint(BASE));
    }

    @Test
    void theOrderOfSourcesAndRoundsChangesNothing() {
        String user = """
                package p;
                public class User extends gen.Made implements Comparable<User> {
                    public gen.Made made;
                    public int compareTo(User u) { return 0; }
                }
                """;
        String made = """
                package gen;
                public class Made { public void m() {} }
                """;
        Canonical together = canonical("p.User", MemberFilter.DECLARED_PUBLIC, user, made);
        Canonical reversed = canonical("p.User", MemberFilter.DECLARED_PUBLIC, made, user);
        List<Translation<TypeModel>> rounds =
                Harness.everyRound(env -> env.full("p.User"), List.of(new MirrorTranslatorTypeTest.Generator()), user);

        assertThat(reversed).isEqualTo(together);
        assertThat(rounds.getFirst()).isInstanceOf(Translation.Deferred.class);
        // the generator makes an empty gen.Made
        assertThat(Canonical.of(Harness.ok(rounds.getLast())).text())
                .isEqualTo(together.text().replace("m(); ", ""));
    }

    // ---- sensitivity

    @Test
    void everyChangeOfTheTypeOrItsMembersChangesTheFingerprint() {
        Map<String, String> variants = new LinkedHashMap<>();
        variants.put("base", BASE);
        variants.put("kind", BASE.replace("public class T", "public abstract class T"));
        variants.put("final kind", BASE.replace("public class T", "public final class T"));
        variants.put(
                "sealed",
                BASE.replace("public class T", "public sealed class T").replace("Runnable {", "Runnable permits Sub {")
                        + "final class Sub extends T<Integer> { Sub() throws Exception { super(0); } }\n");
        variants.put("type parameter bound", BASE.replace("<E extends Number>", "<E extends Integer>"));
        variants.put(
                "type parameter added",
                BASE.replace("<E extends Number>", "<E extends Number, F>").replace("T<E>", "T<E, F>"));
        variants.put("superclass", BASE.replace("extends Base", "extends Base2") + "class Base2 {}\n");
        variants.put(
                "supertype argument",
                BASE.replace("Comparable<T<E>>", "Comparable<Object>")
                        .replace("compareTo(T<E> o)", "compareTo(Object o)"));
        variants.put("constant value", BASE.replace("K = 1", "K = 2"));
        variants.put("constant type", BASE.replace("int K = 1", "long K = 1"));
        variants.put("constant made mutable", BASE.replace("static final int K", "static int K"));
        variants.put("field type", BASE.replace("public E value", "public Number value"));
        variants.put("field made final", BASE.replace("public E value", "public final E value = null"));
        variants.put("field made static", BASE.replace("public E value", "public static Object value"));
        variants.put("ctor parameter", BASE.replace("public T(E e)", "public T(Number e)"));
        variants.put(
                "ctor throws", BASE.replace("java.io.IOException, InterruptedException {}", "java.io.IOException {}"));
        variants.put("method parameter", BASE.replace("get(int i)", "get(long i)"));
        variants.put("method result", BASE.replace("public E get", "public Number get"));
        variants.put("method throws", BASE.replace("get(int i) throws Exception", "get(int i)"));
        variants.put("method made final", BASE.replace("public void run()", "public final void run()"));
        variants.put(
                "method made static",
                BASE.replace("public void run()", "public static void run2()")
                        .replace(", Runnable", "")
                        .replace("public static void run2()", "public static void run()"));
        variants.put("method type parameter bound", BASE.replace("<X extends CharSequence>", "<X>"));
        variants.put(
                "method added",
                BASE.replace("public void run() {}", "public void run() {}\n    public void more() {}"));
        variants.put("table only", BASE.replace("void pack() {}", "void pack(int x) {}"));

        Map<String, String> fingerprints = new LinkedHashMap<>();
        for (Map.Entry<String, String> variant : variants.entrySet()) {
            fingerprints.put(variant.getKey(), fingerprint(variant.getValue()));
        }

        assertThat(fingerprints.values()).doesNotHaveDuplicates();
    }

    @Test
    void enumConstantsAreHashedInOrder() {
        String base = """
                package p;
                public enum T { A, B }
                """;

        assertThat(List.of(
                        fingerprint(base),
                        fingerprint(base.replace("A, B", "B, A")),
                        fingerprint(base.replace("A, B", "A, B, C"))))
                .doesNotHaveDuplicates();
    }

    // ---- sam

    private static final String[] FUNCTIONAL = {
        "package p; public interface A { Object m() throws java.io.IOException; }",
        "package p; public interface B { Object m() throws java.io.IOException; }",
        "package p; public interface T extends A, B {}"
    };

    private static Canonical functional(MemberFilter filter, String a, String b) {
        return canonical("p.T", filter, a, b, FUNCTIONAL[2]);
    }

    @Test
    void theSamOfAFunctionalInterfaceIsALineOfItsOwn() {
        assertThat(functional(MemberFilter.NONE, FUNCTIONAL[0], FUNCTIONAL[1]).text())
                .isEqualTo("""
                        javafile-facts-canonical 1
                        type p.T interface sealed=no
                        tparams -
                        superclasses -
                        supertypes -
                        enum -
                        members none
                        sam m() -> java.lang.Object throws java.io.IOException
                        table abstract m()
                        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
                        table static -
                        """);
        assertThat(canonical(
                                "p.Op",
                                MemberFilter.DECLARED_PUBLIC,
                                "package p; public interface Op<A, B> { B apply(A a, int[] is) throws Exception, Error; }")
                        .text())
                .contains("member method abstract apply(#0, int[]) -> #1 throws java.lang.Error, java.lang.Exception\n"
                        + "sam apply(#0, int[]) -> #1 throws java.lang.Error, java.lang.Exception\n");
        assertThat(canonical("p.Two", MemberFilter.NONE, "package p; public interface Two { void a(); void b(); }")
                        .text())
                .doesNotContain("sam ");
    }

    @Test
    void theInheritedSamIsHashed() {
        // nothing of p.T itself changes: not its members, its supertypes or its method table
        for (MemberFilter filter : MemberFilter.values()) {
            assertThat(List.of(
                            functional(filter, FUNCTIONAL[0], FUNCTIONAL[1]).fingerprint(),
                            // the throws of the function type (JLS 9.9)
                            functional(filter, FUNCTIONAL[0].replace("IOException", "EOFException"), FUNCTIONAL[1])
                                    .fingerprint(),
                            functional(filter, FUNCTIONAL[0].replace(" throws java.io.IOException", ""), FUNCTIONAL[1])
                                    .fingerprint(),
                            functional(
                                            filter,
                                            FUNCTIONAL[0],
                                            FUNCTIONAL[1].replace("IOException", "FileNotFoundException"))
                                    .fingerprint(),
                            // the result
                            functional(filter, FUNCTIONAL[0].replace("Object m", "String m"), FUNCTIONAL[1])
                                    .fingerprint()))
                    .as("%s", filter)
                    .doesNotHaveDuplicates();
        }
    }

    @Test
    void aSamThatIsTheSameIsHashedTheSame() {
        assertThat(functional(MemberFilter.NONE, FUNCTIONAL[0], FUNCTIONAL[1]))
                .isEqualTo(functional(
                        MemberFilter.NONE, FUNCTIONAL[0].replace("java.io.IOException", "Exception"), FUNCTIONAL[1]));
    }

    @Test
    void theFilterIsHashed() {
        assertThat(canonical("p.T", MemberFilter.NONE, BASE).fingerprint())
                .isNotEqualTo(
                        canonical("p.T", MemberFilter.DECLARED_PUBLIC, BASE).fingerprint());
    }

    // ---- hand-made models

    @Test
    void aTypeVariableTheModelDoesNotDeclareIsRejected() {
        TypeModel model = new TypeModel(
                ClassDesc.of("p.T"),
                DeclaredKind.INTERFACE,
                List.of(new TypeParam("E", List.of())),
                List.of(),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false,
                Optional.empty(),
                MemberFilter.DECLARED_PUBLIC,
                List.of(new MethodModel(
                        "m",
                        false,
                        List.of(),
                        Optional.of(Types.typeVar("Q")),
                        List.of(),
                        List.of(),
                        Overridability.ABSTRACT)),
                List.of());

        assertThatThrownBy(() -> Canonical.of(model))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("type variable Q is not declared");
    }

    @Test
    void genericConstructorsAndShadowingTypeVariables() {
        TypeModel model = new TypeModel(
                ClassDesc.of("p.T"),
                DeclaredKind.OPEN_CLASS,
                List.of(new TypeParam("E", List.of())),
                List.of(),
                List.of(ClassDesc.of("java.lang.Object")),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false,
                Optional.empty(),
                MemberFilter.DECLARED_PUBLIC,
                List.of(
                        new CtorModel(
                                List.of(new TypeParam("E", List.of()), new TypeParam("F", List.of(Types.typeVar("E")))),
                                List.of(Types.typeVar("E"), Types.typeVar("F")),
                                List.of()),
                        new MethodModel(
                                "m",
                                false,
                                List.of(new TypeParam("E", List.of())),
                                Optional.of(Types.typeVar("E")),
                                List.of(),
                                List.of(),
                                Overridability.FINAL)),
                List.of());

        assertThat(Canonical.of(model).text())
                .contains(
                        "member ctor <^0, ^1 extends ^0>(^0, ^1) throws -\n",
                        "member method final <^0> m() -> ^0 throws -\n");
    }
}
