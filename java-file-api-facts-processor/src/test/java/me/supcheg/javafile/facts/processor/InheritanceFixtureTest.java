package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `inheritance` (mini-spec §9.2, Q6(b), Q13): a metamodel has the
/// facts of the members its type declares, and an inherited member is a
/// fact of the metamodel of its supertype, which is generated in full
/// without being asked for; a supertype that is not `public` has no
/// metamodel, and its members are facts of the nearest `public` subtype;
/// but the method table holds every method a call on the type may resolve
/// to, or an overload would be cast away wrongly.
class InheritanceFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Base {
            public static int sbase() { return 0; }
            public static void hidden(String s) {}
            public void inherited() {}
            public void over() {}
            public int f() { return 0; }
            protected void prot() {}
            void pkg() {}
            private void priv() {}
        }
        """,
        """
        package p;
        public class Derived extends Base implements Api {
            public void over() {}
            public static void dstatic() {}
            public void hidden(Object o) {}
            public void own() {}
            public void abs() {}
        }
        """,
        "package p; public interface Api { static void iface() {} default void dflt() {} void abs(); }",
        """
        package p;
        public class WithObject {
            public String toString() { return "x"; }
            public boolean equals(Object o) { return false; }
            public int hashCode() { return 0; }
            public void plain() {}
        }
        """,
        "package p; public class Plain { public void plain() {} }"
    };

    @Test
    void aMetamodelHasOnlyTheMembersItsTypeDeclares() throws Exception {
        ClassLoader loader = load(generate("p.Derived.class, p.Base.class", LIBRARY));

        assertThat(factNames(loader, "gen.facts.p.Derived_"))
                .containsExactly("abs", "dstatic", "hidden_Object", "new_", "over", "own");
        assertThat(factNames(loader, "gen.facts.p.Base_"))
                .containsExactly("f", "hidden_String", "inherited", "new_", "over", "sbase");
        assertThat(((Invocable) fact(loader, "gen.facts.p.Derived_", "over")).owner())
                .isEqualTo(token(loader, "gen.facts.p.Derived_"));
        assertThat(((Invocable) fact(loader, "gen.facts.p.Derived_", "over")).traits())
                .isEqualTo(MemberTraits.OVERRIDABLE);
    }

    @Test
    void theMethodTableHasWhatTheTypeInheritsIncludingStatics() throws Exception {
        ClassLoader loader = load(generate("p.Derived.class", LIBRARY));
        var methods = token(loader, "gen.facts.p.Derived_").methods();
        ClassDesc object = ConstantDescs.CD_Object;

        assertThat(methods.concreteMethods())
                .contains(
                        new MethodSignature("inherited", List.of()),
                        new MethodSignature("over", List.of()),
                        new MethodSignature("f", List.of()),
                        new MethodSignature("own", List.of()),
                        new MethodSignature("abs", List.of()),
                        new MethodSignature("dflt", List.of()),
                        new MethodSignature("hidden", List.of(object)),
                        // what javac counts among the candidates when it is accessible
                        new MethodSignature("prot", List.of()),
                        new MethodSignature("pkg", List.of()),
                        // and those of Object
                        new MethodSignature("hashCode", List.of()),
                        new MethodSignature("toString", List.of()),
                        new MethodSignature("equals", List.of(object)))
                .doesNotContain(new MethodSignature("priv", List.of()));
        // implemented abstract methods are not abstract
        assertThat(methods.abstractMethods()).isEmpty();
        // statics of the type and of its superclass; not those of an interface it implements
        assertThat(methods.staticMethods())
                .containsExactlyInAnyOrder(
                        new MethodSignature("sbase", List.of()),
                        new MethodSignature("hidden", List.of(ConstantDescs.CD_String)),
                        new MethodSignature("dstatic", List.of()));
    }

    @Test
    void anObjectMethodIsAFactOnlyWhereTheTypeOverridesIt() throws Exception {
        ClassLoader loader = load(generate("p.WithObject.class, p.Plain.class", LIBRARY));

        assertThat(factNames(loader, "gen.facts.p.WithObject_"))
                .containsExactly("equals_Object", "hashCode", "new_", "plain", "toString");
        assertThat(factNames(loader, "gen.facts.p.Plain_")).containsExactly("new_", "plain");
        assertThat(token(loader, "gen.facts.p.Plain_").methods().concreteMethods())
                .contains(new MethodSignature("hashCode", List.of()));
    }

    // ------------------------------------------------------------------
    // supertypes get full metamodels without being asked for (Q13)
    // ------------------------------------------------------------------

    @Test
    void everySupertypeOfARequestedTypeGetsAFullMetamodel() throws Exception {
        Compilation compilation = generate("p.Derived.class", LIBRARY);
        ClassLoader loader = load(compilation);

        // Base, Api and Object are not asked for: Derived extends and implements them
        assertThat(sources(compilation).keySet())
                .containsExactlyInAnyOrderElementsOf(ProcessorHarness.withObject(
                        "gen.facts", "gen.facts.p.Derived_", "gen.facts.p.Base_", "gen.facts.p.Api_"));
        assertThat(ProcessorHarness.resources(compilation))
                .containsKeys(
                        "META-INF/javafile/metamodel/full/p.Derived",
                        "META-INF/javafile/metamodel/full/p.Base",
                        "META-INF/javafile/metamodel/full/p.Api",
                        "META-INF/javafile/metamodel/full/java.lang.Object",
                        "META-INF/javafile/metamodel/token/java.lang.String");
        assertThat(warnings(compilation)).isEmpty();
        // Derived_ still has only what Derived declares; the rest is in the metamodels of its supertypes
        assertThat(factNames(loader, "gen.facts.p.Derived_"))
                .containsExactly("abs", "dstatic", "hidden_Object", "new_", "over", "own");
        assertThat(factNames(loader, "gen.facts.p.Base_"))
                .containsExactly("f", "hidden_String", "inherited", "new_", "over", "sbase");
        assertThat(factNames(loader, "gen.facts.p.Api_")).containsExactly("abs", "dflt", "iface", "sam");
        assertThat(factNames(loader, "gen.facts.java.lang.Object_"))
                .containsExactly(
                        "equals_Object",
                        "getClass",
                        "hashCode",
                        "new_",
                        "notify",
                        "notifyAll",
                        "toString",
                        "wait",
                        "wait_long",
                        "wait_long_int");
        // a metamodel generated for a supertype is the one a request would give
        assertThat(sources(compilation).get("gen.facts.p.Base_"))
                .isEqualTo(sources(ProcessorHarness.succeeded(ProcessorHarness.process(List.of(lib), """
                        package gen;
                        @me.supcheg.javafile.facts.meta.Facts({p.Derived.class, p.Base.class})
                        class G {}
                        """)))
                        .get("gen.facts.p.Base_"));
    }

    @Test
    void theSignaturesOfASupertypeGetTokensOnly() throws Exception {
        Compilation compilation = generate(
                "p.Low.class",
                "package p; public class Low extends High {}",
                "package p; public class High { public Seen seen() { return null; } }",
                "package p; public class Seen extends Unseen { public Beyond beyond() { return null; } }",
                "package p; public class Unseen { public void unseen() {} }",
                "package p; public class Beyond {}");
        ClassLoader loader = load(compilation);

        // Seen is mentioned by High: a token, and neither what it mentions nor what it extends
        assertThat(sources(compilation).keySet())
                .containsExactlyInAnyOrderElementsOf(ProcessorHarness.withObject(
                        "gen.facts", "gen.facts.p.Low_", "gen.facts.p.High_", "gen.facts.p.Seen_"));
        assertThat(factNames(loader, "gen.facts.p.High_")).containsExactly("new_", "seen");
        assertThat(factNames(loader, "gen.facts.p.Seen_")).isEmpty();
        assertThat(ProcessorHarness.resources(compilation)).containsKey("META-INF/javafile/metamodel/token/p.Seen");
    }

    // ------------------------------------------------------------------
    // a supertype that is not public: its members are those of the nearest public subtype
    // ------------------------------------------------------------------

    private static final String[] HIDDEN = {
        """
        package p;
        public class Pub extends Near<String> {
            public String redeclared() { return "pub"; }
            public String api() { return "api"; }
            public String pub() { return "pub"; }
        }
        """,
        """
        package p;
        abstract class Near<T> extends Far<T> implements HiddenApi {
            public static final String HID = "near";
            public String overridden() { return "near"; }
            public String near() { return "near"; }
            public static String snear() { return "snear"; }
        }
        """,
        """
        package p;
        abstract class Far<T> {
            public static final String FAR = "far";
            public static final String HID = "far";
            public static int counter = 5;
            public T item;
            public T get() { return item; }
            public void set(T value) { item = value; }
            public Far<T> self() { return this; }
            public String overridden() { return "far"; }
            public String redeclared() { return "far"; }
            public static String sfar() { return "sfar"; }
            protected void prot() {}
            void pack() {}
        }
        """,
        """
        package p;
        interface HiddenApi extends PubApi {
            String CONST = "const";
            String api();
            default String dflt() { return "dflt"; }
            static void istatic() {}
        }
        """,
        "package p; public interface PubApi { String pub(); default String beyond() { return \"beyond\"; } }",
        """
        package p;
        public final class Gen<E> extends Near<E> {
            public String api() { return "api"; }
            public String pub() { return "pub"; }
        }
        """,
        """
        package p;
        public class Covariant extends Near<String> {
            public Covariant self() { return this; }
            public String api() { return "api"; }
            public String pub() { return "pub"; }
        }
        """
    };

    private static final String SELF_SKIPPED =
            "p.Pub: no fact of method self() of p.Far, which mentions types that are not public: p.Far";

    @Test
    void theMembersOfASupertypeThatIsNotPublicAreFactsOfTheNearestPublicSubtype() throws Exception {
        Compilation compilation = generate("p.Pub.class", HIDDEN);
        ClassLoader loader = load(compilation);
        String pub = "gen.facts.p.Pub_";

        // no metamodel of Near, Far and HiddenApi; PubApi, beyond them, is public and has its own
        assertThat(sources(compilation).keySet())
                .containsExactlyInAnyOrderElementsOf(
                        ProcessorHarness.withObject("gen.facts", pub, "gen.facts.p.PubApi_"));
        assertThat(factNames(loader, pub))
                .containsExactly(
                        // constants and a static field, through the chain and from the interface
                        "CONST",
                        "FAR",
                        "HID",
                        "api",
                        "counter",
                        "dflt",
                        "get",
                        "item",
                        "near",
                        "new_",
                        "overridden",
                        "pub",
                        "redeclared",
                        "set_String",
                        "sfar",
                        "snear");
        // not the members PubApi declares, which its own metamodel has
        assertThat(factNames(loader, "gen.facts.p.PubApi_")).containsExactly("beyond", "pub", "sam");
        // the nearer declaration of a hidden field and of an overridden method
        assertThat(((StaticFieldRef<?>) fact(loader, pub, "HID")).constantValue())
                .contains("near");
        assertThat(((StaticFieldRef<?>) fact(loader, pub, "CONST")).constantValue())
                .contains("const");
        // the owner is the subtype, and the signature is in its terms: T is String
        DeclaredToken<?> token = token(loader, pub);
        assertThat(Stream.of("near", "get", "set_String", "sfar", "snear", "dflt", "overridden")
                        .<Object>map(name -> ((Invocable) uncheckedFact(loader, pub, name)).owner()))
                .containsOnly(token);
        assertThat(((StaticFieldRef<?>) fact(loader, pub, "FAR")).owner()).isEqualTo(token);
        assertThat(((Invocable) fact(loader, pub, "get"))
                        .resultType()
                        .orElseThrow()
                        .typeRef())
                .isEqualTo(Types.STRING);
        assertThat(((Invocable) fact(loader, pub, "set_String")).params())
                .extracting(TypeToken::typeRef)
                .containsExactly(Types.STRING);
        assertThat(((FieldRef<?, ?>) fact(loader, pub, "item")).type().typeRef())
                .isEqualTo(Types.STRING);
        // a member that mentions the hidden type has no fact, as any member that mentions such a type
        assertThat(warnings(compilation)).containsExactly(SELF_SKIPPED);
        // the canonical form, and so the fingerprint, tells the adopted members
        assertThat(sources(compilation).get(pub))
                .contains("member method overridable near() -> java.lang.String throws -")
                .contains("member field static constant java.lang.String CONST = \\\"const\\\"")
                .contains("member method overridable set(java.lang.String) -> void throws -");
    }

    private static Object uncheckedFact(ClassLoader loader, String metamodel, String field) {
        try {
            return fact(loader, metamodel, field);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void underStrictAnAdoptedMemberWithoutAFactIsAnError() {
        Compilation compilation = attempt(List.of("-Ajavafile.facts.strict=true"), "p.Pub.class", HIDDEN);

        assertThat(errors(compilation)).containsExactly(SELF_SKIPPED);
    }

    @Test
    void aMemberTheSubtypeDeclaresAgainWithAPublicTypeIsItsOwn() throws Exception {
        Compilation compilation = generate("p.Covariant.class", HIDDEN);
        ClassLoader loader = load(compilation);

        assertThat(warnings(compilation)).isEmpty();
        assertThat(factNames(loader, "gen.facts.p.Covariant_")).contains("self", "near", "get");
        assertThat(((Invocable) fact(loader, "gen.facts.p.Covariant_", "self"))
                        .resultType()
                        .orElseThrow())
                .isEqualTo(token(loader, "gen.facts.p.Covariant_"));
    }

    @Test
    void theAdoptedMembersOfAGenericTypeAreInTermsOfItsTypeParameters() throws Exception {
        Compilation compilation = generate("p.Gen.class, String.class", HIDDEN);
        ClassLoader loader = load(compilation);
        RefToken<?> string = token(loader, "gen.facts.java.lang.String_");
        Object gen = instance(loader, "gen.facts.p.Gen_", string);

        assertThat(memberNames(loader, "gen.facts.p.Gen_"))
                .contains("get", "set_E", "item", "near", "overridden", "dflt", "FAR", "snear");
        assertThat(((Invocable) fact(gen, "get")).resultType().orElseThrow()).isSameAs(string);
        assertThat(((Invocable) fact(gen, "set_E")).signature())
                .isEqualTo(new MethodSignature("set", List.of(ConstantDescs.CD_String)));
        // of the final class that has them
        assertThat(((Invocable) fact(gen, "near")).traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) fact(gen, "get")).owner()).isSameAs(fact(gen, "token"));
        assertThat(warnings(compilation))
                .containsExactly(
                        "p.Gen: no fact of method self() of p.Far, which mentions types that are not public: p.Far");
    }

    @Test
    void aMemberThatComesInSeveralWaysIsOneFact() throws Exception {
        Compilation compilation = generate(
                "p.Diamond.class, p.Twins.class",
                "package p; public class Diamond implements Left, Right {}",
                "package p; interface Root { int ROOT = 1; default String root() { return \"root\"; } }",
                "package p; interface Left extends Root {}",
                "package p; interface Right extends Root {}",
                "package p; public abstract class Twins implements Loose, Tight {}",
                "package p; interface Loose { Object twin(); }",
                "package p; interface Tight { String twin(); }");
        ClassLoader loader = load(compilation);

        assertThat(warnings(compilation)).isEmpty();
        assertThat(factNames(loader, "gen.facts.p.Diamond_")).containsExactly("ROOT", "new_", "root");
        // of two abstract methods of one signature, the one of the more specific result
        assertThat(factNames(loader, "gen.facts.p.Twins_")).containsExactly("super_", "twin");
        Invocable twin = (Invocable) fact(loader, "gen.facts.p.Twins_", "twin");
        assertThat(twin.resultType().orElseThrow().typeRef()).isEqualTo(Types.STRING);
        assertThat(twin.traits()).isEqualTo(MemberTraits.ABSTRACT);
    }

    @Test
    void theSamOfAnInterfaceThatAdoptsItsMethodIsThatFact() throws Exception {
        Compilation compilation = generate(
                "p.Fn.class",
                "package p; public interface Fn extends HiddenFn {}",
                "package p; interface HiddenFn { String apply(String s); }");
        ClassLoader loader = load(compilation);

        assertThat(warnings(compilation)).isEmpty();
        assertThat(factNames(loader, "gen.facts.p.Fn_")).containsExactly("apply_String", "sam");
        assertThat(((Sam1<?, ?, ?>) fact(loader, "gen.facts.p.Fn_", "sam")).method())
                .isSameAs(fact(loader, "gen.facts.p.Fn_", "apply_String"));
    }

    @Test
    void adoptedMembersThatWouldShareANameHaveNoFact() {
        // javac does not tell K of one interface from K of the other either: Both.K is ambiguous
        Compilation compilation = generate(
                "p.Both.class",
                "package p; public class Both implements One, Other {}",
                "package p; interface One { int K = 1; }",
                "package p; interface Other { int K = 2; }");

        assertThat(warnings(compilation))
                .containsExactly("p.Both: no fact of field K of p.One, field K of p.Other, which would all be named K");
    }

    @Test
    void aPublicSupertypeNoFullMetamodelCanBeMadeOfIsToldAndDoesNotFailTheRequest() throws Exception {
        String[] library = {
            "package p; public class Over extends Bounded<Secret> { public Bounded<?> same() { return null; } }",
            "package p; public class Bounded<T extends Secret> { public void lost() {} }",
            "package p; class Secret {}"
        };
        Compilation compilation = generate("p.Over.class", library);
        ClassLoader loader = load(compilation);
        String declined = "p.Over: no facts of the public members inherited from p.Bounded, which has no full"
                + " metamodel: the bounds of the type parameters of p.Bounded mention types that are not public:"
                + " p.Secret";

        assertThat(warnings(compilation)).containsExactly(declined);
        assertThat(factNames(loader, "gen.facts.p.Over_")).containsExactly("new_", "same");
        // Bounded is mentioned too: it keeps its token-only metamodel
        assertThat(sources(compilation).get("gen.facts.p.Bounded_")).contains("complete = false");
        assertThat(ProcessorHarness.resources(compilation))
                .containsKey("META-INF/javafile/metamodel/token/p.Bounded")
                .doesNotContainKey("META-INF/javafile/metamodel/full/p.Bounded");

        // under strict it is an error, as any member without a fact
        assertThat(errors(ProcessorHarness.process(
                        List.of(lib), List.of("-Ajavafile.facts.strict=true"), List.of(), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Over.class)
                class G {}
                """)))
                .containsExactly(declined);
    }

    // ------------------------------------------------------------------
    // through the typed layer: what the facts render is what javac compiles and the JVM runs
    // ------------------------------------------------------------------

    @TempDir
    Path generator;

    @TempDir
    Path generated;

    private static final String USE = """
            package use;

            import gen.facts.java.lang.Object_;
            import gen.facts.java.lang.String_;
            import gen.facts.p.Api_;
            import gen.facts.p.Base_;
            import gen.facts.p.Derived_;
            import gen.facts.p.PubApi_;
            import gen.facts.p.Pub_;
            import java.lang.constant.ClassDesc;
            import java.util.function.Function;
            import me.supcheg.javafile.facts.PrimitiveToken;
            import me.supcheg.javafile.facts.TypeToken;
            import me.supcheg.javafile.typed.Expr;
            import me.supcheg.javafile.typed.TypedClassBuilder;
            import me.supcheg.javafile.typed.TypedJavaFile;

            import static me.supcheg.javafile.typed.Expressions.call;
            import static me.supcheg.javafile.typed.Expressions.field;
            import static me.supcheg.javafile.typed.Expressions.literal;
            import static me.supcheg.javafile.typed.Expressions.staticCall;
            import static me.supcheg.javafile.typed.Expressions.staticField;

            public final class Run {
                private static <R, P> String render(
                        TypeToken<R> result, TypeToken<P> param, Function<Expr<P>, Expr<R>> body) {
                    return TypedJavaFile.class_(ClassDesc.of("out", "Out"), new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> void build(TypedClassBuilder<Self> cb) {
                                    cb.staticMethod("go", result, param, (b, p) -> b.return_(body.apply(p)));
                                }
                            })
                            .render();
                }

                // a method Derived inherits, through the metamodel of the supertype that declares it

                public static String inheritedOfTheSuperclass() {
                    return render(PrimitiveToken.INT, Derived_.TOKEN, d -> call(d, Base_.f));
                }

                public static String inheritedOfTheInterface() {
                    return render(PrimitiveToken.INT, Derived_.TOKEN, d -> call(d, Object_.hashCode));
                }

                public static String staticOfTheSuperclass() {
                    return render(PrimitiveToken.INT, Derived_.TOKEN, d -> staticCall(Base_.sbase));
                }

                public static String defaultOfTheInterface() {
                    return render(String_.TOKEN, Derived_.TOKEN, d -> call(d, Object_.toString));
                }

                // the members Pub adopts from its supertypes that are not public

                public static String adoptedMethod() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> call(p, Pub_.near));
                }

                public static String adoptedOverriddenMethod() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> call(p, Pub_.overridden));
                }

                public static String adoptedMethodOfTheTypeArgument() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> call(p, Pub_.get));
                }

                public static String adoptedField() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> field(p, Pub_.item));
                }

                public static String adoptedDefaultMethod() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> call(p, Pub_.dflt));
                }

                public static String adoptedStaticMethod() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> staticCall(Pub_.snear));
                }

                public static String adoptedStaticMethodOfTheFartherSupertype() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> staticCall(Pub_.sfar));
                }

                public static String adoptedConstant() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> staticField(Pub_.HID));
                }

                public static String adoptedConstantOfAnInterface() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> staticField(Pub_.CONST));
                }

                public static String adoptedStaticField() {
                    return render(PrimitiveToken.INT, Pub_.TOKEN, p -> staticField(Pub_.counter));
                }

                public static String beyondThePublicSupertype() {
                    return render(String_.TOKEN, Pub_.TOKEN, p -> call(p, PubApi_.beyond));
                }
            }
            """;

    /// What `out.Out.go`, as a method of the generator renders it, returns for a new instance of
    /// `receiver`: the rendered source is compiled in a package of its own, against the library alone.
    private Object ranOn(String method, String receiver) throws Exception {
        ClassLoader metamodels = load(generate("p.Derived.class, p.Pub.class", concat(LIBRARY, HIDDEN)));
        Compilation use = use(USE);
        assertThat(use.status()).as("%s", use.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
        ProcessorHarness.write(use, generator);
        String source;
        try (URLClassLoader run =
                new URLClassLoader(new URL[] {generator.toUri().toURL()}, metamodels)) {
            source = (String) run.loadClass("use.Run").getMethod(method).invoke(null);
        }
        Compilation compiled = Compiler.javac()
                .withClasspath(List.of(lib.toFile()))
                .withOptions("-proc:none", "-Xlint:all", "-Werror")
                .compile(JavaFileObjects.forSourceString("out.Out", source));
        assertThat(compiled.status())
                .as("%s%n%s", compiled.diagnostics(), source)
                .isEqualTo(Compilation.Status.SUCCESS);
        Path directory = ProcessorHarness.write(compiled, generated.resolve(method));
        try (URLClassLoader loader = new URLClassLoader(
                new URL[] {directory.toUri().toURL(), lib.toUri().toURL()}, null)) {
            Class<?> type = loader.loadClass(receiver);
            return loader.loadClass("out.Out")
                    .getMethod("go", type)
                    .invoke(null, type.getConstructor().newInstance());
        }
    }

    private static String[] concat(String[] first, String[] second) {
        return Stream.concat(Stream.of(first), Stream.of(second)).toArray(String[]::new);
    }

    @Test
    void anInheritedMemberIsCalledThroughTheMetamodelOfTheSupertypeThatWasNotAskedFor() throws Exception {
        assertThat(ranOn("inheritedOfTheSuperclass", "p.Derived")).isEqualTo(0);
        assertThat(ranOn("staticOfTheSuperclass", "p.Derived")).isEqualTo(0);
        assertThat(ranOn("inheritedOfTheInterface", "p.Derived")).isInstanceOf(Integer.class);
        assertThat((String) ranOn("defaultOfTheInterface", "p.Derived")).startsWith("p.Derived@");
    }

    @Test
    void anAdoptedMemberIsReachedThroughThePublicSubtypeFromAnotherPackage() throws Exception {
        assertThat(ranOn("adoptedMethod", "p.Pub")).isEqualTo("near");
        assertThat(ranOn("adoptedOverriddenMethod", "p.Pub")).isEqualTo("near");
        assertThat(ranOn("adoptedMethodOfTheTypeArgument", "p.Pub")).isNull();
        assertThat(ranOn("adoptedField", "p.Pub")).isNull();
        assertThat(ranOn("adoptedDefaultMethod", "p.Pub")).isEqualTo("dflt");
        assertThat(ranOn("beyondThePublicSupertype", "p.Pub")).isEqualTo("beyond");
    }

    @Test
    void anAdoptedStaticMemberIsReachedThroughThePublicSubtypeFromAnotherPackage() throws Exception {
        // javac lets code of another package name them through Pub alone: Pub.snear(), Pub.HID
        assertThat(ranOn("adoptedStaticMethod", "p.Pub")).isEqualTo("snear");
        assertThat(ranOn("adoptedStaticMethodOfTheFartherSupertype", "p.Pub")).isEqualTo("sfar");
        assertThat(ranOn("adoptedConstant", "p.Pub")).isEqualTo("near");
        assertThat(ranOn("adoptedConstantOfAnInterface", "p.Pub")).isEqualTo("const");
        assertThat(ranOn("adoptedStaticField", "p.Pub")).isEqualTo(5);
    }
}
