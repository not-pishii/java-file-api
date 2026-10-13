package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.MethodTableTemplate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The heritage of a type as [MirrorTranslator#heritage] reads it, told by
/// the lines of its canonical form ([Canonical#heritage]).
class HeritageTest {

    /// The lines of the heritage of a type, without those of the methods `Object` alone declares.
    private static List<String> heritage(String name, String... sources) {
        return Harness.run(
                env -> switch (Harness.ok(env.full(name)).heritage()) {
                    case Heritage.Told told ->
                        Canonical.heritage(told).stream()
                                .filter(line -> !line.contains(" by class public concrete java.lang.Object ")
                                        && !line.contains(" by class public final java.lang.Object ")
                                        && !line.contains(" by class protected concrete java.lang.Object "))
                                .toList();
                    case Heritage.Untold _ -> List.of("untold");
                },
                sources);
    }

    /// What the method table of a type says of a method without parameters.
    private static String inTable(String type, String method, String... sources) {
        MethodTableTemplate table =
                Harness.run(env -> Harness.ok(env.full(type)).methods(), sources);
        MethodTableTemplate.Signature signature = MethodTableTemplate.Signature.of(method);
        return table.abstractMethods().contains(signature)
                ? "abstract"
                : table.concreteMethods().contains(signature)
                        ? "concrete"
                        : table.staticMethods().contains(signature) ? "static" : "none";
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
                        "inherit ctor Base(#0, int[]) protected (#0, int...) throws java.io.IOException",
                        "inherit ctor Base() public () throws -",
                        "inherit ctor Base(java.lang.String) package (java.lang.String) throws -",
                        "inherit method fixed() erased () overrides - by class public final p.Base () ->"
                                + " java.lang.String throws -",
                        "inherit method get() erased () overrides - by class public abstract p.Base () -> #0 throws -",
                        "inherit method hook(#0) erased (java.lang.Object) overrides - by class protected concrete"
                                + " p.Base (#0) -> void throws java.io.IOException",
                        "inherit method of(java.lang.Number, java.lang.String[]) erased (java.lang.Number,"
                                + " java.lang.String[]) overrides - by class public static p.Base <^0 extends"
                                + " java.lang.Number> (^0, java.lang.String...) -> ^0 throws -",
                        "inherit method pack(p.Hidden) erased (p.Hidden) overrides - by class package concrete p.Base"
                                + " (p.Hidden) -> void throws -");
    }

    @Test
    void aFieldAndAMemberTypeOfOneNameAreToldEach() {
        Heritage.Told told = Harness.run(
                env -> (Heritage.Told) Harness.ok(env.full("p.Twice")).heritage(),
                "package p; public class Twice { public int Both; public static class Both {} }");

        java.lang.constant.ClassDesc twice = java.lang.constant.ClassDesc.of("p.Twice");
        assertThat(told.named())
                .containsExactly(
                        new Heritage.Named.Field("Both", twice, Heritage.Visibility.PUBLIC),
                        new Heritage.Named.MemberType("Both", twice, Heritage.Visibility.PUBLIC));
    }

    @Test
    void aClassTellsTheMethodsOfObjectAsItHasThem() {
        List<String> lines = Harness.run(
                env -> Canonical.heritage(
                        (Heritage.Told) Harness.ok(env.full("p.Plain")).heritage()),
                "package p; public class Plain { @Override public String toString() { return \"\"; } }");

        assertThat(lines)
                .contains(
                        "inherit method clone() erased () overrides - by class protected concrete java.lang.Object ()"
                                + " -> java.lang.Object throws java.lang.CloneNotSupportedException",
                        "inherit method getClass() erased () overrides - by class public final java.lang.Object () ->"
                                + " java.lang.Class<?> throws -",
                        "inherit method toString() erased () overrides java.lang.Object by class public concrete"
                                + " p.Plain () -> java.lang.String throws -")
                .noneMatch(line -> line.contains("java.lang.Object () -> java.lang.String"));
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
                        "inherit ctor Impl() public () throws -",
                        // of the hidden Mid<String>, in terms of Impl: the bridge of Comparable takes an Object
                        "inherit method compareTo(p.Mid) erased (java.lang.Object); (p.Mid) overrides"
                                + " java.lang.Comparable by class public concrete p.Mid (p.Mid<java.lang.String>) ->"
                                + " int throws -",
                        "inherit method get() erased () overrides p.Source by class public concrete p.Mid () ->"
                                + " java.lang.String throws -",
                        "inherit method name() erased () overrides p.Named by class public concrete p.Impl () ->"
                                + " java.lang.String throws -",
                        "inherit method orElse(java.lang.String) erased (java.lang.Object) overrides - by interface"
                                + " default p.Source (java.lang.String) -> java.lang.String throws -",
                        "inherit method put(java.lang.String) erased (java.lang.Object) overrides - by class public"
                                + " abstract p.Mid (java.lang.String) -> void throws -");
    }

    @Test
    void theMethodsATypeHasUnderOneSignatureAreOneMethodWithADeclarationEach() {
        String object = "package p; public interface Objects { Object get(); }";
        String text = "package p; public interface Texts { CharSequence get(); }";

        // the declaration whose result suits the others is the first
        assertThat(heritage("p.Both", "package p; public interface Both extends Objects, Texts {}", object, text))
                .containsExactly("inherit method get() erased () overrides - by interface abstract p.Texts () ->"
                        + " java.lang.CharSequence throws - | interface abstract p.Objects () -> java.lang.Object"
                        + " throws -");
        // the method of a superclass implements that of an interface the superclass does not implement
        assertThat(heritage(
                        "p.Half",
                        "package p; public abstract class Half extends Given implements Objects {}",
                        "package p; public class Given { public String get() { return \"\"; } }",
                        object))
                .contains("inherit method get() erased () overrides p.Objects by class public concrete p.Given () ->"
                        + " java.lang.String throws -");
    }

    @Test
    void anAbstractMethodWithPackageAccessOfASuperclassOfAnotherPackageIsTold() {
        String a = "package p1; public abstract class A { abstract void pkg(); public A() {} }";

        // B does not inherit pkg(), and javac asks a subclass of B for it all the same
        assertThat(heritage("p2.B", "package p2; public abstract class B extends p1.A { public B() {} }", a))
                .contains("inherit method pkg() erased () overrides - by class package abstract p1.A () -> void"
                        + " throws -");
        // a class of its package between the two implements it, though the type reaches that one through
        // another package
        assertThat(heritage(
                        "p1.Far",
                        "package p1; public class Far extends p2.Mid { public Far() {} void pkg() {} }",
                        "package p2; public abstract class Mid extends p1.A { public Mid() {} }",
                        a))
                .contains("inherit method pkg() erased () overrides p1.A by class package concrete p1.Far () -> void"
                        + " throws -")
                .noneMatch(line -> line.contains("abstract p1.A"));
        // a public method of the name in between does not override it: both are told
        assertThat(heritage(
                        "p2.Twin",
                        "package p2; public abstract class Twin extends p1.A { public Twin() {} public void pkg() {} }",
                        a))
                .contains("inherit method pkg() erased () overrides - by class public concrete p2.Twin () -> void"
                        + " throws - | class package abstract p1.A () -> void throws -");
    }

    @Test
    void theMethodOfAClassStandsBesideTheDefaultOneOfAnInterface() {
        String i = "package p; public interface I { default void m() {} }";
        String abstractBase = "package p; public abstract class AbsBase { public abstract void m(); }";
        String finalBase = "package p; public class FinBase { public final void m() {} }";
        String x = "package p; public abstract class X extends AbsBase implements I {}";
        String y = "package p; public class Y extends FinBase implements I {}";

        // an abstract method of a superclass is not implemented by a default one (JLS 8.4.8.4)
        assertThat(heritage("p.X", x, abstractBase, i))
                .contains("inherit method m() erased () overrides - by class public abstract p.AbsBase () -> void"
                        + " throws - | interface default p.I () -> void throws -");
        assertThat(inTable("p.X", "m", x, abstractBase, i)).isEqualTo("abstract");
        // a method of a superclass with a body overrides the default one, and is what it is: final
        assertThat(heritage("p.Y", y, finalBase, i))
                .contains("inherit method m() erased () overrides p.I by class public final p.FinBase () -> void"
                        + " throws -");
        assertThat(inTable("p.Y", "m", y, finalBase, i)).isEqualTo("concrete");
    }

    @Test
    void everyDeclarationTellsItsOwnThrowsClauseAndAccess() {
        assertThat(heritage(
                        "p.TC",
                        "package p; public interface TC extends TA, TB {}",
                        "package p; public interface TA { void t() throws java.io.IOException; }",
                        "package p; public interface TB { void t() throws java.sql.SQLException; }"))
                .containsExactly("inherit method t() erased () overrides - by interface abstract p.TA () -> void throws"
                        + " java.io.IOException | interface abstract p.TB () -> void throws java.sql.SQLException");
        assertThat(heritage(
                        "p.PX",
                        "package p; public abstract class PX extends PA implements PI {}",
                        "package p; public abstract class PA { protected abstract void q(); }",
                        "package p; public interface PI { void q() throws java.io.IOException; }"))
                .contains("inherit method q() erased () overrides - by class protected abstract p.PA () -> void"
                        + " throws - | interface abstract p.PI () -> void throws java.io.IOException");
    }

    @Test
    void aStaticMethodIsTheOneThatHidesAndNoneOfAnInterface() {
        assertThat(heritage(
                        "p.SC",
                        "package p; public class SC extends SB implements Tools {}",
                        "package p; public class SB extends SA { public static String s() { return \"\"; } }",
                        "package p; public class SA { public static Object s() throws Exception { return \"\"; } }",
                        "package p; public interface Tools { static void tool() {} }"))
                .containsExactly(
                        "inherit ctor SC() public () throws -",
                        "inherit method s() erased () overrides - by class public static p.SB () -> java.lang.String"
                                + " throws -");
    }

    @Test
    void aMethodAClassDeclaresAbstractAgainIsAbstract() {
        assertThat(heritage(
                        "p.Again",
                        "package p; public abstract class Again extends Given { public abstract String get(); }",
                        "package p; public class Given { public String get() { return \"\"; } }"))
                .contains("inherit method get() erased () overrides p.Given by class public abstract p.Again () ->"
                        + " java.lang.String throws -");
    }

    @Test
    void aMethodReachedByTwoPathsIsToldOnce() {
        assertThat(heritage(
                        "p.Both",
                        "package p; public interface Both extends Left, Right {}",
                        "package p; public interface Left extends Sink<String> {}",
                        "package p; public interface Right extends Sink<String> {}",
                        "package p; public interface Sink<T> { void put(T value); }"))
                .containsExactly("inherit method put(java.lang.String) erased (java.lang.Object) overrides - by"
                        + " interface abstract p.Sink (java.lang.String) -> void throws -");
    }

    @Test
    void aTypeParameterOfAMethodIsNotTheOneOfTheTypeWhateverTheyAreCalled() {
        assertThat(
                        heritage(
                                "p.GX",
                                "package p; public class GX<T> extends GBase<T> {}",
                                "package p; public class GBase<E> { public <T extends E> void g(E e, T t, java.util.List<? super T> l) {} }"))
                .contains("inherit method g(#0, java.lang.Object, java.util.List) erased (java.lang.Object,"
                        + " java.lang.Object, java.util.List) overrides - by class public concrete p.GBase <^0 extends"
                        + " #0> (#0, ^0, java.util.List<? super ^0>) -> void throws -");
    }

    @Test
    void aTypeWhoseTypeParametersAreRenamedTellsTheSameHeritage() {
        String e = "package p; public interface Foo<E> { E get(); void put(java.util.List<E[]> e); <X> X to(E e); }";
        String t = "package p; public interface Foo<T> { T get(); void put(java.util.List<T[]> t); <E> E to(T t); }";

        Heritage ofE = Harness.run(env -> Harness.ok(env.full("p.Foo")).heritage(), e);
        Heritage ofT = Harness.run(env -> Harness.ok(env.full("p.Foo")).heritage(), t);

        assertThat(ofT).isInstanceOf(Heritage.Told.class).isEqualTo(ofE);
    }

    @Test
    void aTypeTellsTheNamesItsFieldsAndMemberTypesTakeInASubclass() {
        assertThat(heritage(
                        "p.Sub", """
                        package p;
                        public class Sub extends Base implements Consts {
                            public int own;
                            private int secret;
                            public static class Builder {}
                            private static class Hidden {}
                        }
                        """, """
                        package p;
                        public class Base {
                            protected String Op;
                            int packed;
                            protected interface Entry {}
                            enum State { ON }
                        }
                        """, "package p; public interface Consts { int LIMIT = 1; interface Kind {} }"))
                .filteredOn(line -> line.startsWith("inherit field") || line.startsWith("inherit type"))
                .containsExactly(
                        "inherit field package p.Base packed",
                        "inherit field protected p.Base Op",
                        "inherit field public p.Consts LIMIT",
                        "inherit field public p.Sub own",
                        "inherit type package p.Base State",
                        "inherit type protected p.Base Entry",
                        "inherit type public p.Consts Kind",
                        "inherit type public p.Sub Builder");
    }

    @Test
    void anInterfaceTellsNeitherTheMethodsOfObjectNorTheOnesItDeclaresAgainNorItsStaticOnes() {
        List<String> lines = Harness.run(
                env -> Canonical.heritage(
                        (Heritage.Told) Harness.ok(env.full("p.Api")).heritage()),
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
                        "inherit method run() erased () overrides - by interface abstract p.Api () -> void throws -",
                        "inherit method twice() erased () overrides - by interface default p.Api () -> void throws -");
    }

    @Test
    void aSealedTypeTellsItsHeritageAndATypeThatCannotBeExtendedTellsNone() {
        String sealed = "package p; public sealed interface Shape permits Dot { void draw(); }";
        String dot = "package p; public final class Dot implements Shape { public void draw() {} }";

        assertThat(heritage("p.Shape", sealed, dot))
                .containsExactly(
                        "inherit method draw() erased () overrides - by interface abstract p.Shape () -> void throws -");
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
