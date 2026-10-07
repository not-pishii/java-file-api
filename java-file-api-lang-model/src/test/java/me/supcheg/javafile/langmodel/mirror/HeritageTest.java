package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Heritage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The heritage of a type as [MirrorTranslator#heritage] reads it, told by
/// the lines of its canonical form ([Canonical#heritage]).
class HeritageTest {

    /// The lines of the heritage of a type, without those of the methods `Object` declares.
    private static List<String> heritage(String name, String... sources) {
        return Harness.run(
                env -> switch (Harness.ok(env.full(name)).heritage()) {
                    case Heritage.Told told ->
                        Canonical.heritage(told, Harness.ok(env.full(name)).typeParams()).stream()
                                .filter(line -> !line.contains(" java.lang.Object "))
                                .toList();
                    case Heritage.Untold _ -> List.of("untold");
                },
                sources);
    }

    @Test
    void aClassTellsItsConstructorsAndEveryMethodThatIsNotPrivateWhateverItsAccess() {
        assertThat(heritage("p.Base", """
                        package p;
                        public abstract class Base<T> {
                            public Base() {}
                            protected Base(T item, int... rest) throws java.io.IOException {}
                            Base(String hidden) {}
                            private Base(long none) {}
                            public abstract T get();
                            protected void hook(T value) throws java.io.IOException {}
                            void pack(Hidden hidden) {}
                            private void priv() {}
                            public final String fixed() { return ""; }
                            public static <X extends Number> X of(X x, String... more) { return x; }
                        }
                        class Hidden {}
                        """))
                .containsExactly(
                        "inherit ctor package (java.lang.String) throws -",
                        "inherit ctor protected (#0, int...) throws java.io.IOException",
                        "inherit ctor public () throws -",
                        "inherit method package concrete p.Base pack(p.Hidden) -> void throws - erased (p.Hidden)"
                                + " overrides -",
                        "inherit method protected concrete p.Base hook(#0) -> void throws java.io.IOException erased"
                                + " (java.lang.Object) overrides -",
                        "inherit method public abstract p.Base get() -> #0 throws - erased () overrides -",
                        "inherit method public final p.Base fixed() -> java.lang.String throws - erased () overrides -",
                        "inherit method public static p.Base <^0 extends java.lang.Number> of(^0, java.lang.String...)"
                                + " -> ^0 throws - erased (java.lang.Number, java.lang.String[]) overrides -");
    }

    @Test
    void aClassTellsTheMethodsOfObjectAsItHasThem() {
        List<String> lines = Harness.run(
                env -> Canonical.heritage(
                        (Heritage.Told) Harness.ok(env.full("p.Plain")).heritage(), List.of()),
                "package p; public class Plain { @Override public String toString() { return \"\"; } }");

        assertThat(lines)
                .contains(
                        "inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws"
                                + " java.lang.CloneNotSupportedException erased () overrides -",
                        "inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased"
                                + " () overrides -",
                        "inherit method public concrete p.Plain toString() -> java.lang.String throws - erased ()"
                                + " overrides java.lang.Object")
                .noneMatch(line -> line.contains("java.lang.Object toString()"));
    }

    @Test
    void anInheritedMethodIsAMemberOfTheTypeAndTellsWhatItOverridesAndTheErasuresOfThose() {
        assertThat(heritage(
                        "p.Impl",
                        """
                        package p;
                        public abstract class Impl extends Mid<String> implements Named {
                            public String name() { return ""; }
                        }
                        abstract class Mid<T> implements Source<T>, Comparable<Mid<T>> {
                            public T get() { return null; }
                            public int compareTo(Mid<T> other) { return 0; }
                            public abstract void put(T value);
                        }
                        """,
                        "package p; public interface Source<T> { T get(); default T orElse(T other) { return other; } }",
                        "package p; public interface Named { CharSequence name(); }"))
                .containsExactly(
                        "inherit ctor public () throws -",
                        // of the hidden Mid<String>, in terms of Impl: the bridge of Comparable takes an Object
                        "inherit method public abstract p.Mid put(java.lang.String) -> void throws - erased"
                                + " (java.lang.Object) overrides -",
                        "inherit method public concrete p.Impl name() -> java.lang.String throws - erased () overrides"
                                + " p.Named",
                        "inherit method public concrete p.Mid compareTo(p.Mid<java.lang.String>) -> int throws - erased"
                                + " (java.lang.Object); (p.Mid) overrides java.lang.Comparable",
                        "inherit method public concrete p.Mid get() -> java.lang.String throws - erased () overrides"
                                + " p.Source",
                        "inherit method public default p.Source orElse(java.lang.String) -> java.lang.String throws -"
                                + " erased (java.lang.Object) overrides -");
    }

    @Test
    void theMethodsATypeHasUnderOneSignatureAreOneWithABodyOrOfTheMostSpecificResult() {
        String object = "package p; public interface Objects { Object get(); }";
        String text = "package p; public interface Texts { CharSequence get(); }";

        assertThat(heritage("p.Both", "package p; public interface Both extends Objects, Texts {}", object, text))
                .containsExactly(
                        "inherit method public abstract p.Texts get() -> java.lang.CharSequence throws - erased"
                                + " () overrides p.Objects");
        assertThat(heritage(
                        "p.Half",
                        "package p; public abstract class Half extends Given implements Objects {}",
                        "package p; public class Given { public String get() { return \"\"; } }",
                        object))
                .contains(
                        "inherit method public concrete p.Given get() -> java.lang.String throws - erased () overrides"
                                + " p.Objects");
    }

    @Test
    void anInterfaceTellsNeitherTheMethodsOfObjectNorTheOnesItDeclaresAgain() {
        List<String> lines = Harness.run(
                env -> Canonical.heritage(
                        (Heritage.Told) Harness.ok(env.full("p.Api")).heritage(), List.of()),
                """
                package p;
                public interface Api {
                    void run();
                    boolean equals(Object other);
                    String toString();
                    default void twice() {}
                    static void helper() {}
                }
                """);

        assertThat(lines)
                .containsExactly(
                        "inherit method public abstract p.Api run() -> void throws - erased () overrides -",
                        "inherit method public default p.Api twice() -> void throws - erased () overrides -",
                        "inherit method public static p.Api helper() -> void throws - erased () overrides -");
    }

    @Test
    void aSealedTypeTellsItsHeritageAndATypeThatCannotBeExtendedTellsNone() {
        String sealed = "package p; public sealed interface Shape permits Dot { void draw(); }";
        String dot = "package p; public final class Dot implements Shape { public void draw() {} }";

        assertThat(heritage("p.Shape", sealed, dot))
                .containsExactly(
                        "inherit method public abstract p.Shape draw() -> void throws - erased () overrides -");
        assertThat(heritage("p.Dot", sealed, dot)).containsExactly("untold");
        assertThat(heritage("p.Color", "package p; public enum Color { RED }")).containsExactly("untold");
        assertThat(heritage("p.Point", "package p; public record Point(int x) {}"))
                .containsExactly("untold");
    }

    @Test
    void aModelWithoutMembersTellsNoHeritage() {
        TypeModel full = Harness.run(env -> Harness.ok(env.full("p.Open")), "package p; public class Open {}");

        assertThat(full.heritage()).isInstanceOf(Heritage.Told.class);
        assertThat(full.withoutMembers().heritage()).isSameAs(Heritage.UNTOLD);
    }

    @Test
    void aTypeWithAMemberNoTypeRefTellsHasNoHeritage() {
        // a member class of a parameterized type is not expressible
        assertThat(heritage("p.Holder", """
                        package p;
                        public class Holder<T> {
                            public class Inner {}
                            public void take(Holder<String>.Inner inner) {}
                        }
                        """)).containsExactly("untold");
    }
}
