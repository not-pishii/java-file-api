package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.TargetType;
import me.supcheg.javafile.facts.TargetType.Difference;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// What a change of a type between the version a metamodel was generated
/// from and the version of the target classpath comes to: the metamodel
/// holds as it is, holds with the method table of the target, or does not
/// hold. Each row is a type `p.T` in two versions.
class ConformanceTest {

    /// A change of `p.T` and what it comes to.
    ///
    /// @param change what changed
    /// @param filter the members of the metamodel: those of a full one, or none
    /// @param before the sources the metamodel was generated from
    /// @param after the sources of the target classpath
    /// @param expected `unchanged`, `changed`, or the lines that tell the differences
    private record Row(
            String change, MemberFilter filter, List<String> before, List<String> after, List<String> expected) {}

    private static Row full(String change, String before, String after, String... expected) {
        return new Row(change, MemberFilter.DECLARED_ACCESSIBLE, List.of(before), List.of(after), List.of(expected));
    }

    private static Row full(String change, List<String> before, List<String> after, String... expected) {
        return new Row(change, MemberFilter.DECLARED_ACCESSIBLE, before, after, List.of(expected));
    }

    private static Row tokenOnly(String change, String before, String after, String... expected) {
        return new Row(change, MemberFilter.NONE, List.of(before), List.of(after), List.of(expected));
    }

    private static final String UNCHANGED = "unchanged";
    private static final String CHANGED = "changed";

    private static final String T = """
            package p;
            public class T {
                public static final int K = 1;
                public int f;
                public T() {}
                public String m(String s) { return s; }
                public void n() {}
            }
            """;

    private static String t(String from, String to) {
        assertThat(T).contains(from);
        return T.replace(from, to);
    }

    private static final String ENUM = "package p; public enum T { A, B }";
    private static final String FN = "package p; public interface T { String m(); }";
    private static final String BASE = "package p; public class Base { public void inherited() {} }";
    private static final String MARKER = "package p; public interface Marker {}";
    private static final String HIDDEN = "package p; class Hidden { public int adopted() { return 0; } }";

    private static final List<Row> ROWS = List.of(
            // ---- the same type: the fast path
            full("nothing", T, T, UNCHANGED),
            full(
                    "the order of the members",
                    T,
                    t("public void n() {}\n", "").replace("public T() {}", "public void n() {}\n    public T() {}"),
                    UNCHANGED),
            full(
                    "a private member is added",
                    T,
                    t("public void n() {}", "public void n() {} private void p() {} private int q;"),
                    UNCHANGED),
            full("the body of a method", T, t("{ return s; }", "{ return s + s; }"), UNCHANGED),
            full(
                    "a type parameter is renamed",
                    "package p; public class T<E> { public E get() { return null; } }",
                    "package p; public class T<X> { public X get() { return null; } }",
                    UNCHANGED),
            // ---- additions: every fact holds, the table is that of the target
            full(
                    "a method of another name is added",
                    T,
                    t("public void n() {}", "public void n() {} public void o() {}"),
                    CHANGED),
            full(
                    "an overload is added",
                    T,
                    t("public void n() {}", "public void n() {} public String m(Object o) { return \"\"; }"),
                    CHANGED),
            full(
                    "a method that is not public is added",
                    T,
                    t("public void n() {}", "public void n() {} String m(Object o) { return \"\"; }"),
                    CHANGED),
            full("a constructor is added", T, t("public T() {}", "public T() {} public T(int i) {}"), CHANGED),
            full("a field is added", T, t("public int f;", "public int f; public int g;"), CHANGED),
            // ---- a method the metamodel has a fact of
            full(
                    "a method is removed",
                    T,
                    t("public void n() {}", ""),
                    "missing: method public overridable n() -> void throws -"),
            full(
                    "an overload is removed and another stays",
                    t("public void n() {}", "public void n() {} public void n(int i) {}"),
                    T,
                    "missing: method public overridable n(int) -> void throws -",
                    "  similar: method public overridable n() -> void throws -"),
            full(
                    "a parameter is of another type",
                    T,
                    t("m(String s) { return s; }", "m(CharSequence s) { return \"\"; }"),
                    "changed: method public overridable m(java.lang.String) -> java.lang.String throws -",
                    "  found: method public overridable m(java.lang.CharSequence) -> java.lang.String throws -"),
            full(
                    "the result is of another type",
                    T,
                    t("public String m(String s)", "public CharSequence m(String s)"),
                    "changed: method public overridable m(java.lang.String) -> java.lang.String throws -",
                    "  found: method public overridable m(java.lang.String) -> java.lang.CharSequence throws -"),
            full(
                    "the result is void",
                    T,
                    t("public String m(String s) { return s; }", "public void m(String s) {}"),
                    "changed: method public overridable m(java.lang.String) -> java.lang.String throws -",
                    "  found: method public overridable m(java.lang.String) -> void throws -"),
            full(
                    "a method is static",
                    T,
                    t("public void n() {}", "public static void n() {}"),
                    "changed: method public overridable n() -> void throws -",
                    "  found: method public static n() -> void throws -"),
            full(
                    "a method is final",
                    T,
                    t("public void n() {}", "public final void n() {}"),
                    "changed: method public overridable n() -> void throws -",
                    "  found: method public final n() -> void throws -"),
            full(
                    "a method throws a checked exception",
                    T,
                    t("public void n() {}", "public void n() throws java.io.IOException {}"),
                    "changed: method public overridable n() -> void throws -",
                    "  found: method public overridable n() -> void throws java.io.IOException"),
            full(
                    "a method no longer throws an exception",
                    t("public void n() {}", "public void n() throws java.io.IOException {}"),
                    T,
                    "changed: method public overridable n() -> void throws java.io.IOException",
                    "  found: method public overridable n() -> void throws -"),
            full(
                    "a method throws a subclass of its exception",
                    t("public void n() {}", "public void n() throws java.io.IOException {}"),
                    t("public void n() {}", "public void n() throws java.io.FileNotFoundException {}"),
                    "changed: method public overridable n() -> void throws java.io.IOException",
                    "  found: method public overridable n() -> void throws java.io.FileNotFoundException"),
            full(
                    "a method is no longer public",
                    T,
                    t("public void n() {}", "protected void n() {}"),
                    "changed: method public overridable n() -> void throws -",
                    "  found: method protected overridable n() -> void throws -"),
            full(
                    "a protected method is public",
                    t("public void n() {}", "protected void n() {}"),
                    T,
                    "changed: method protected overridable n() -> void throws -",
                    "  found: method public overridable n() -> void throws -"),
            full(
                    "a method is no longer accessible",
                    T,
                    t("public void n() {}", "void n() {}"),
                    "missing: method public overridable n() -> void throws -"),
            full(
                    "a protected method is added",
                    T,
                    t("public void n() {}", "public void n() {} protected void p() {}"),
                    CHANGED),
            full(
                    "a protected constructor is public",
                    t("public T() {}", "protected T() {}"),
                    T,
                    "changed: ctor protected () throws -",
                    "  found: ctor public () throws -"),
            full(
                    "the bound of a type parameter of a method",
                    t("public void n() {}", "public <X> void n() {}"),
                    t("public void n() {}", "public <X extends Number> void n() {}"),
                    "changed: method public overridable <^0> n() -> void throws -",
                    "  found: method public overridable <^0 extends java.lang.Number> n() -> void throws -"),
            full(
                    "two overloads are others",
                    t("public void n() {}", "public void n(int i) {} public void n(long l) {}"),
                    t("public void n() {}", "public void n(short i) {} public void n(byte l) {}"),
                    "missing: method public overridable n(int) -> void throws -",
                    "  similar: method public overridable n(byte) -> void throws -; method public overridable n(short) -> void throws -",
                    "missing: method public overridable n(long) -> void throws -",
                    "  similar: method public overridable n(byte) -> void throws -; method public overridable n(short) -> void throws -"),
            full(
                    "an abstract method gets a body",
                    "package p; public interface T { void n(); void o(); }",
                    "package p; public interface T { default void n() {} void o(); }",
                    "changed: method public abstract n() -> void throws -",
                    "  found: method public overridable n() -> void throws -"),
            full(
                    "a method moves up to a public supertype",
                    List.of("package p; public class T extends Base { public void up() {} }", BASE),
                    List.of(
                            "package p; public class T extends Base {}",
                            "package p; public class Base { public void inherited() {} public void up() {} }"),
                    "missing: method public overridable up() -> void throws -"),
            full(
                    "a member the type adopts from a hidden supertype",
                    List.of("package p; public class T extends Hidden {}", HIDDEN),
                    List.of(
                            "package p; public class T extends Hidden {}",
                            "package p; class Hidden { public long adopted() { return 0; } }"),
                    "changed: method public overridable adopted() -> int throws -",
                    "  found: method public overridable adopted() -> long throws -"),
            // ---- a constructor
            full(
                    "a constructor takes another parameter",
                    T,
                    t("public T() {}", "public T(int i) {}"),
                    "changed: ctor public () throws -",
                    "  found: ctor public (int) throws -"),
            full(
                    "a constructor throws a checked exception",
                    T,
                    t("public T() {}", "public T() throws Exception {}"),
                    "changed: ctor public () throws -",
                    "  found: ctor public () throws java.lang.Exception"),
            full(
                    "a constructor is removed and another stays",
                    t("public T() {}", "public T() {} public T(int i) {}"),
                    t("public T() {}", "public T(int i) {}"),
                    "missing: ctor public () throws -",
                    "  similar: ctor public (int) throws -"),
            // ---- a field
            full("a field is removed", T, t("public int f;", ""), "missing: field public instance mutable int f"),
            full(
                    "a field is of another type",
                    T,
                    t("public int f;", "public long f;"),
                    "changed: field public instance mutable int f",
                    "  found: field public instance mutable long f"),
            full(
                    "a field is final",
                    T,
                    t("public int f;", "public final int f = 1;"),
                    "changed: field public instance mutable int f",
                    "  found: field public instance final int f"),
            full(
                    "a field is static",
                    T,
                    t("public int f;", "public static int f;"),
                    "changed: field public instance mutable int f",
                    "  found: field public static mutable int f"),
            full(
                    "a constant has another value",
                    T,
                    t("K = 1;", "K = 2;"),
                    "changed: field public static constant int K = 1",
                    "  found: field public static constant int K = 2"),
            full(
                    "a constant is no longer one",
                    T,
                    t("K = 1;", "K = Integer.parseInt(\"1\");"),
                    "changed: field public static constant int K = 1",
                    "  found: field public static final int K"),
            full(
                    "a constant of a text with the sign of a value",
                    "package p; public class T { public static final String S = \"a = b\"; }",
                    "package p; public class T { public static final String S = \"a = c\"; }",
                    "changed: field public static constant java.lang.String S = \"a = b\"",
                    "  found: field public static constant java.lang.String S = \"a = c\""),
            // ---- the data of the shape
            full(
                    "the class is abstract",
                    T,
                    t("public class T", "public abstract class T"),
                    "changed: type",
                    "  generated against: p.T open-class sealed=no",
                    "  target: p.T abstract-class sealed=no"),
            full(
                    "the class is an interface",
                    "package p; public abstract class T { public abstract void n(); }",
                    "package p; public interface T { void n(); }",
                    "changed: type",
                    "  generated against: p.T abstract-class sealed=no",
                    "  target: p.T interface sealed=no",
                    "changed: superclasses",
                    "  generated against: java.lang.Object",
                    "  target: -",
                    "missing: ctor public () throws -"),
            full(
                    "the class is final, and so are its methods",
                    "package p; public class T { public void n() {} }",
                    "package p; public final class T { public void n() {} }",
                    "changed: type",
                    "  generated against: p.T open-class sealed=no",
                    "  target: p.T final-class sealed=no",
                    "changed: method public overridable n() -> void throws -",
                    "  found: method public final n() -> void throws -"),
            full(
                    "the class is sealed",
                    List.of("package p; public abstract class T {}", "package p; final class Only extends T {}"),
                    List.of(
                            "package p; public abstract sealed class T permits Only {}",
                            "package p; final class Only extends T {}"),
                    "changed: type",
                    "  generated against: p.T abstract-class sealed=no",
                    "  target: p.T abstract-class sealed=yes"),
            full(
                    "the type has a type parameter",
                    "package p; public class T {}",
                    "package p; public class T<E> {}",
                    "changed: tparams",
                    "  generated against: -",
                    "  target: #0"),
            full(
                    "the bound of a type parameter",
                    "package p; public class T<E> {}",
                    "package p; public class T<E extends Number> {}",
                    "changed: tparams",
                    "  generated against: #0",
                    "  target: #0 extends java.lang.Number"),
            full(
                    "the superclass",
                    List.of("package p; public class T {}", BASE),
                    List.of("package p; public class T extends Base {}", BASE),
                    "changed: superclasses",
                    "  generated against: java.lang.Object",
                    "  target: p.Base; java.lang.Object"),
            full(
                    "a superclass is put between the type and its superclass",
                    List.of("package p; public class T extends Base {}", BASE),
                    List.of(
                            "package p; public class T extends Mid {}",
                            "package p; public class Mid extends Base {}",
                            BASE),
                    "changed: superclasses",
                    "  generated against: p.Base; java.lang.Object",
                    "  target: p.Mid; p.Base; java.lang.Object"),
            // ---- the supertypes: those of the metamodel are among those of the type
            full(
                    "an interface without type arguments is no longer implemented",
                    List.of("package p; public class T implements Marker {}", MARKER),
                    List.of("package p; public class T {}", MARKER),
                    "missing: interface p.Marker"),
            full(
                    "an interface is implemented",
                    List.of("package p; public class T {}", MARKER),
                    List.of("package p; public class T implements Marker {}", MARKER),
                    CHANGED),
            full(
                    "an interface with type arguments is implemented",
                    "package p; public abstract class T {}",
                    "package p; public abstract class T implements Comparable<T> {}",
                    CHANGED),
            full(
                    "an interface is implemented beside another",
                    List.of("package p; public class T implements Marker {}", MARKER),
                    List.of("package p; public class T implements Marker, java.io.Serializable {}", MARKER),
                    CHANGED),
            full(
                    "an interface is implemented instead of another",
                    List.of("package p; public class T implements Marker {}", MARKER),
                    List.of("package p; public class T implements java.io.Serializable {}", MARKER),
                    "missing: interface p.Marker"),
            full(
                    "an interface the type implemented itself is that of another interface it implements",
                    List.of("package p; public class T implements Marker {}", MARKER),
                    List.of(
                            "package p; public class T implements Sub {}",
                            "package p; public interface Sub extends Marker {}",
                            MARKER),
                    CHANGED),
            full(
                    "an interface the type implemented itself is that of its superclass",
                    List.of("package p; public class T extends Base implements Marker {}", BASE, MARKER),
                    List.of(
                            "package p; public class T extends Base {}",
                            "package p; public class Base implements Marker { public void inherited() {} }",
                            MARKER),
                    UNCHANGED),
            full(
                    "an interface of an interface the type implements is no longer one of it",
                    List.of(
                            "package p; public class T implements Sub {}",
                            "package p; public interface Sub extends Marker {}",
                            MARKER),
                    List.of(
                            "package p; public class T implements Sub {}",
                            "package p; public interface Sub {}",
                            MARKER),
                    "missing: interface p.Marker"),
            full(
                    "the type argument of an interface",
                    "package p; public abstract class T implements java.util.function.Supplier<String> {}",
                    "package p; public abstract class T implements java.util.function.Supplier<Object> {}",
                    "changed: supertype java.util.function.Supplier<java.lang.String>",
                    "  found: java.util.function.Supplier<java.lang.Object>"),
            full(
                    "the type argument of an interface, of a type with type parameters",
                    "package p; public abstract class T<E> implements java.util.Map<E, java.util.List<E>> {}",
                    "package p; public abstract class T<E> implements java.util.Map<E, java.util.Set<E>> {}",
                    "changed: supertype java.util.Map<#0, java.util.List<#0>>",
                    "  found: java.util.Map<#0, java.util.Set<#0>>"),
            full(
                    "an interface is implemented without its type arguments",
                    "package p; public abstract class T implements java.util.function.Supplier<String> {}",
                    "package p; @SuppressWarnings(\"rawtypes\")"
                            + " public abstract class T implements java.util.function.Supplier {}",
                    "changed: supertype java.util.function.Supplier<java.lang.String>",
                    "  found: java.util.function.Supplier"),
            full(
                    "an interface with type arguments is no longer implemented",
                    "package p; public abstract class T implements java.util.function.Supplier<String> {}",
                    "package p; public abstract class T {}",
                    "missing: interface java.util.function.Supplier"),
            full(
                    "the type argument of the superclass",
                    List.of("package p; public class T extends Of<String> {}", "package p; public class Of<E> {}"),
                    List.of("package p; public class T extends Of<Object> {}", "package p; public class Of<E> {}"),
                    "changed: supertype p.Of<java.lang.String>",
                    "  found: p.Of<java.lang.Object>"),
            full(
                    "the superclass with type arguments is no longer one, and its interface with it",
                    List.of(
                            "package p; public class T extends Of<String> {}",
                            "package p; public abstract class Of<E> implements Comparable<E> {"
                                    + " public int compareTo(E e) { return 0; } }"),
                    List.of(
                            "package p; public class T {}",
                            "package p; public abstract class Of<E> implements Comparable<E> {"
                                    + " public int compareTo(E e) { return 0; } }"),
                    "changed: superclasses",
                    "  generated against: p.Of; java.lang.Object",
                    "  target: java.lang.Object",
                    "missing: interface java.lang.Comparable"),
            full(
                    "an enum implements an interface",
                    List.of(ENUM, MARKER),
                    List.of("package p; public enum T implements Marker { A, B }", MARKER),
                    CHANGED),
            full(
                    "an enum constant is added",
                    ENUM,
                    "package p; public enum T { A, B, C }",
                    "changed: enum",
                    "  generated against: A; B",
                    "  target: A; B; C"),
            full(
                    "an enum constant is removed",
                    ENUM,
                    "package p; public enum T { A }",
                    "changed: enum",
                    "  generated against: A; B",
                    "  target: A"),
            full(
                    "the enum constants are in another order",
                    ENUM,
                    "package p; public enum T { B, A }",
                    "changed: enum",
                    "  generated against: A; B",
                    "  target: B; A"),
            // ---- the single abstract method
            full(
                    "a functional interface gets another abstract method",
                    FN,
                    "package p; public interface T { String m(); void other(); }",
                    "missing: sam m() -> java.lang.String throws -"),
            full(
                    "the single abstract method is another",
                    List.of("package p; public interface T extends Fn {}", "package p; interface Fn { String m(); }"),
                    List.of("package p; public interface T extends Fn {}", "package p; interface Fn { Object m(); }"),
                    "changed: method public abstract m() -> java.lang.String throws -",
                    "  found: method public abstract m() -> java.lang.Object throws -",
                    "changed: sam m() -> java.lang.String throws -",
                    "  found: sam m() -> java.lang.Object throws -"),
            full(
                    "a functional interface gets an interface with an abstract method",
                    List.of(FN, "package p; public interface Other { void other(); }"),
                    List.of(
                            "package p; public interface T extends Other { String m(); }",
                            "package p; public interface Other { void other(); }"),
                    "missing: sam m() -> java.lang.String throws -"),
            full(
                    "a functional interface gets an interface without an abstract method",
                    List.of(FN, MARKER),
                    List.of("package p; public interface T extends Marker { String m(); }", MARKER),
                    CHANGED),
            // ---- a token-only metamodel: no facts of members
            tokenOnly("nothing, of a token-only metamodel", T, T, UNCHANGED),
            tokenOnly(
                    "a public member is added, of a token-only metamodel",
                    T,
                    t("public int f;", "public int f; public int g;"),
                    UNCHANGED),
            tokenOnly("a method is removed, of a token-only metamodel", T, t("public void n() {}", ""), CHANGED),
            tokenOnly(
                    "a functional interface gets another abstract method, of a token-only metamodel",
                    FN,
                    "package p; public interface T { String m(); void other(); }",
                    CHANGED),
            new Row(
                    "an interface is implemented, of a token-only metamodel",
                    MemberFilter.NONE,
                    List.of("package p; public class T {}", MARKER),
                    List.of("package p; public class T implements Marker {}", MARKER),
                    List.of(CHANGED)),
            new Row(
                    "an interface is no longer implemented, of a token-only metamodel",
                    MemberFilter.NONE,
                    List.of("package p; public class T implements Marker {}", MARKER),
                    List.of("package p; public class T {}", MARKER),
                    List.of("missing: interface p.Marker")),
            tokenOnly(
                    "the class is abstract, of a token-only metamodel",
                    T,
                    t("public class T", "public abstract class T"),
                    "changed: type",
                    "  generated against: p.T open-class sealed=no",
                    "  target: p.T abstract-class sealed=no"));

    @TestFactory
    Stream<DynamicTest> aChangeOfATypeAndWhatItComesTo() {
        return ROWS.stream()
                .map(row -> DynamicTest.dynamicTest(row.change(), () -> {
                    Canonical generated = Harness.run(
                            env -> Canonical.of(Harness.ok(env.translator().type(env.element("p.T"), row.filter()))),
                            row.before().toArray(String[]::new));

                    TargetType found = Harness.run(
                            env -> Conformance.of(
                                    generated.fingerprint(), generated::text, Harness.ok(env.full("p.T"))),
                            row.after().toArray(String[]::new));

                    assertThat(told(found)).containsExactlyElementsOf(row.expected());
                }));
    }

    private static List<String> told(TargetType found) {
        return switch (found) {
            case TargetType.Unchanged _ -> List.of(UNCHANGED);
            case TargetType.Changed _ -> List.of(CHANGED);
            case TargetType.Mismatched(List<Difference> differences) ->
                differences.stream()
                        .flatMap(difference -> difference.lines().stream())
                        .toList();
        };
    }

    @Test
    void theRowsAreToldApartByTheirNames() {
        assertThat(ROWS).extracting(Row::change).doesNotHaveDuplicates();
    }

    @Test
    void theMethodsOfAChangedTypeAreThoseOfTheTarget() {
        Canonical generated = Harness.run(env -> Canonical.of(Harness.ok(env.full("p.T"))), T);

        TargetType found = Harness.run(
                env -> Conformance.of(generated.fingerprint(), generated::text, Harness.ok(env.full("p.T"))),
                t("public void n() {}", "public void n() {} public String m(Object o) { return \"\"; } T(long l) {}"));

        assertThat(found).isInstanceOfSatisfying(TargetType.Changed.class, changed -> {
            assertThat(changed.methods().concreteMethods())
                    .contains(
                            Signature.of("m", Param.fixed(ConstantDescs.CD_String)),
                            Signature.of("m", Param.fixed(ConstantDescs.CD_Object)));
            assertThat(changed.methods().constructors())
                    .containsExactlyInAnyOrder(
                            Signature.of("T"), Signature.of("T", Param.fixed(ConstantDescs.CD_long)));
        });
    }

    @Test
    void theHeritageOfAChangedTypeIsThatOfTheTargetAndATokenOnlyMetamodelHasNone() {
        String target = BASE_OF_HERITAGE.replace("void hook() {}", "final void hook() {} void added() {}");
        Canonical full = Harness.run(env -> Canonical.of(Harness.ok(env.full("p.T"))), BASE_OF_HERITAGE);
        Canonical tokenOnly =
                Harness.run(env -> Canonical.of(Harness.ok(env.full("p.T")).withoutMembers()), BASE_OF_HERITAGE);

        // the lines of the heritage are not compared: no fact tells a method of package access, and one that
        // is final now is the business of whoever declares a method of its signature
        TargetType ofFull =
                Harness.run(env -> Conformance.of(full.fingerprint(), full::text, Harness.ok(env.full("p.T"))), target);
        TargetType ofTokenOnly = Harness.run(
                env -> Conformance.of(tokenOnly.fingerprint(), tokenOnly::text, Harness.ok(env.full("p.T"))), target);

        assertThat(ofFull)
                .isInstanceOfSatisfying(
                        TargetType.Changed.class,
                        changed -> assertThat(changed.heritage())
                                .isInstanceOfSatisfying(
                                        Heritage.Told.class,
                                        told -> assertThat(told.methods())
                                                .filteredOn(method ->
                                                        method.declaredBy().equals(ClassDesc.of("p", "T")))
                                                .extracting(method -> method.name() + " " + method.dispatch())
                                                .containsExactly("added CONCRETE", "hook FINAL", "n CONCRETE")));
        assertThat(ofTokenOnly)
                .isInstanceOfSatisfying(
                        TargetType.Changed.class,
                        changed -> assertThat(changed.heritage()).isSameAs(Heritage.UNTOLD));
    }

    private static final String BASE_OF_HERITAGE =
            "package p; public class T { public T() {} public void n() {} void hook() {} }";

    @Test
    void theCanonicalFormIsReadOnlyWhereTheFingerprintDiffers() {
        Canonical generated = Harness.run(env -> Canonical.of(Harness.ok(env.full("p.T"))), T);
        AtomicInteger read = new AtomicInteger();

        TargetType same = Harness.run(
                env -> Conformance.of(
                        generated.fingerprint(),
                        () -> {
                            read.incrementAndGet();
                            return generated.text();
                        },
                        Harness.ok(env.full("p.T"))),
                T);

        assertThat(same).isSameAs(TargetType.UNCHANGED);
        assertThat(read).hasValue(0);
    }

    @Test
    void aMetamodelOfAnotherFormatIsNotCompared() {
        String other = "javafile-facts-canonical 1\ntype p.T open-class sealed=no\n";

        TargetType found =
                Harness.run(env -> Conformance.of("0".repeat(64), () -> other, Harness.ok(env.full("p.T"))), T);

        assertThat(found)
                .isEqualTo(new TargetType.Mismatched(
                        List.of(new Difference.OtherFormat("javafile-facts-canonical 1", Canonical.HEADER))));
        TargetType ofNoForm =
                Harness.run(env -> Conformance.of("0".repeat(64), () -> "", Harness.ok(env.full("p.T"))), T);
        assertThat(ofNoForm)
                .isEqualTo(new TargetType.Mismatched(List.of(new Difference.OtherFormat("", Canonical.HEADER))));
    }

    @Test
    void aTypeIsComparedWithItsMembers() {
        TypeModel tokenOnly = Harness.run(env -> Harness.ok(env.tokenOnly("p.T")), T);

        assertThatIllegalArgumentException().isThrownBy(() -> Conformance.of("0".repeat(64), () -> "", tokenOnly));
    }

    @Test
    void theFastPathIsTheFingerprintOfTheModelOfTheMetamodel() {
        Canonical full = Harness.run(env -> Canonical.of(Harness.ok(env.full("p.T"))), T);

        boolean same = Harness.run(env -> Conformance.unchanged(full.fingerprint(), Harness.ok(env.full("p.T"))), T);
        // a full metamodel is not the token-only one of its type
        boolean tokenOnly =
                Harness.run(env -> Conformance.unchanged(full.fingerprint(), Harness.ok(env.tokenOnly("p.T"))), T);
        boolean other = Harness.run(
                env -> Conformance.unchanged(full.fingerprint(), Harness.ok(env.full("p.T"))),
                t("public void n() {}", ""));

        assertThat(List.of(same, tokenOnly, other)).containsExactly(true, false, false);
    }

    @Test
    void theNameOfAMemberIsReadOffItsLine() {
        Canonical canonical = Harness.run(env -> Canonical.of(Harness.ok(env.full("p.Names"))), """
                package p;
                public interface Names<E> {
                    String S = "a = b (c) <d>";
                    java.util.Map<String, ? extends java.util.List<Object>> map = null;
                    <X extends Comparable<X>> X max(X[] xs, java.util.List<? super X> into);
                    static <A, B extends java.util.List<A>> void of(A a, B b) {}
                }
                """);

        assertThat(Conformance.names(canonical)).containsExactly("field S", "field map", "method max", "method of");
        assertThat(Conformance.names(Harness.run(env -> Canonical.of(Harness.ok(env.full("p.T"))), FN)))
                .containsExactly("method m", "sam");
        assertThat(Conformance.name("member ctor public <^0>(^0) throws -")).isEqualTo("ctor");
        assertThat(Conformance.name("member ctor public () throws -")).isEqualTo("ctor");
    }
}
