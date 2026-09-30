package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.langmodel.mirror.FieldModel.Mutability;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.supcheg.javafile.facts.MethodTableTemplate.Param.fixed;
import static me.supcheg.javafile.facts.MethodTableTemplate.Param.var;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// [MirrorTranslator#type(javax.lang.model.element.TypeElement, MemberFilter)]: the shape of a declared type and its
/// members.
class MirrorTranslatorTypeTest {
    private static final ClassDesc CD_NUMBER = ClassDesc.of("java.lang.Number");
    private static final ClassDesc CD_COMPARABLE = ClassDesc.of("java.lang.Comparable");

    private static TypeModel full(String name, String... sources) {
        return Harness.ok(Harness.run(env -> env.full(name), sources));
    }

    private static Translation<TypeModel> translate(String name, String... sources) {
        return Harness.run(env -> env.full(name), sources);
    }

    private static Map<String, MethodModel> methods(TypeModel model) {
        return model.members().stream()
                .filter(MethodModel.class::isInstance)
                .map(MethodModel.class::cast)
                .collect(Collectors.toMap(MethodModel::name, m -> m, (first, ignored) -> first));
    }

    // ---- kinds

    @Test
    void kindAndSealedFollowTheDeclaration() {
        Map<String, String> kinds = Harness.run(
                env -> {
                    Map<String, String> result = new java.util.LinkedHashMap<>();
                    for (String name : List.of(
                            "p.Final",
                            "p.Open",
                            "p.Abstract",
                            "p.Iface",
                            "p.Plain",
                            "p.Bodies",
                            "p.Rec",
                            "p.Sealed",
                            "p.SealedClass")) {
                        TypeModel model = Harness.ok(env.tokenOnly(name));
                        result.put(name, model.kind() + (model.sealed() ? " sealed" : ""));
                    }
                    return result;
                },
                """
                package p;
                public final class Final {}
                """,
                """
                package p;
                public class Open {}
                """,
                """
                package p;
                public abstract class Abstract {}
                """,
                """
                package p;
                public interface Iface {}
                """,
                """
                package p;
                public enum Plain { A }
                """,
                """
                package p;
                public enum Bodies { A { } }
                """,
                """
                package p;
                public record Rec(int x) {}
                """,
                """
                package p;
                public sealed interface Sealed permits Final2 {}
                final class Final2 implements Sealed {}
                """,
                """
                package p;
                public abstract sealed class SealedClass permits Sub {}
                final class Sub extends SealedClass {}
                """);

        assertThat(kinds)
                .containsExactly(
                        Map.entry("p.Final", "final class"),
                        Map.entry("p.Open", "open class"),
                        Map.entry("p.Abstract", "abstract class"),
                        Map.entry("p.Iface", "interface"),
                        Map.entry("p.Plain", "enum class"),
                        Map.entry("p.Bodies", "enum class sealed"),
                        Map.entry("p.Rec", "final class"),
                        Map.entry("p.Sealed", "interface sealed"),
                        Map.entry("p.SealedClass", "abstract class sealed"));
    }

    @Test
    void annotationInterfacesAreUnrepresentable() {
        assertThat(translate("p.Ann", """
                        package p;
                        public @interface Ann {}
                        """))
                .isEqualTo(new Translation.Unrepresentable<>("annotation interface p.Ann is not supported yet"));
    }

    @Test
    void innerClassesOfGenericClassesAreUnrepresentable() {
        List<Translation<TypeModel>> translations = Harness.run(
                env -> List.of(
                        env.full("p.Outer.Inner"),
                        env.full("p.Outer.Inner.Deeper"),
                        env.full("p.Outer.Nested"),
                        env.full("p.Outer.Nested.Inner")),
                """
                package p;
                public class Outer<T> {
                    public class Inner { public class Deeper {} }
                    public static class Nested { public class Inner {} }
                }
                """);

        assertThat(translations.get(0))
                .isEqualTo(new Translation.Unrepresentable<>(
                        "inner class p.Outer.Inner of generic class p.Outer can mention its type parameters"));
        assertThat(translations.get(1))
                .isEqualTo(new Translation.Unrepresentable<>(
                        "inner class p.Outer.Inner.Deeper of generic class p.Outer can mention its type parameters"));
        assertThat(Harness.ok(translations.get(2)).desc()).isEqualTo(ClassDesc.of("p.Outer$Nested"));
        assertThat(Harness.ok(translations.get(3)).desc()).isEqualTo(ClassDesc.of("p.Outer$Nested$Inner"));
    }

    // ---- type parameters

    @Test
    void typeParametersKeepTheirBoundsButALoneObject() {
        TypeModel model = full("p.Bounds", """
                package p;
                public class Bounds<A, B extends Number, C extends Object & Comparable<? super C>, D extends Enum<D>, E extends B> {}
                """);

        assertThat(model.typeParams())
                .containsExactly(
                        new TypeParam("A", List.of()),
                        new TypeParam("B", List.of(Types.of(CD_NUMBER))),
                        new TypeParam(
                                "C",
                                List.of(
                                        Types.OBJECT,
                                        Types.parameterized(CD_COMPARABLE, Types.superBound(Types.typeVar("C"))))),
                        new TypeParam(
                                "D", List.of(Types.parameterized(ClassDesc.of("java.lang.Enum"), Types.typeVar("D")))),
                        new TypeParam("E", List.of(Types.typeVar("B"))));
        assertThat(model.nonPublicBoundTypes()).isEmpty();
    }

    @Test
    void boundsMentioningTypesThatAreNotPublicAreRecorded() {
        TypeModel model = full("p.Hides", """
                package p;
                public class Hides<T extends Hidden, U extends java.util.List<Hidden2>> {}
                class Hidden {}
                class Hidden2 {}
                """);

        assertThat(model.nonPublicBoundTypes()).containsExactly("p.Hidden", "p.Hidden2");
    }

    @Test
    void unrepresentableBoundsMakeTheTypeUnrepresentable() {
        assertThat(translate("p.Bad", """
                        package p;
                        public class Bad<T extends Outer<String>.Inner> {}
                        """, """
                        package p;
                        public class Outer<T> { public class Inner {} }
                        """))
                .isEqualTo(new Translation.Unrepresentable<>(
                        "type p.Bad: member class p.Outer.Inner of parameterized " + "type p.Outer<java.lang.String>"));
    }

    // ---- superclasses and supertypes

    @Test
    void superclassesAreTheErasedChainEndingInObject() {
        List<List<ClassDesc>> chains = Harness.run(
                env -> List.of(
                        Harness.ok(env.tokenOnly("p.C")).superclasses(),
                        Harness.ok(env.tokenOnly("p.I")).superclasses(),
                        Harness.ok(env.tokenOnly("java.lang.Object")).superclasses()),
                """
                package p;
                public class C extends B<String> {}
                class B<T> extends A {}
                class A {}
                interface I {}
                """);

        assertThat(chains)
                .containsExactly(
                        List.of(ClassDesc.of("p.B"), ClassDesc.of("p.A"), ConstantDescs.CD_Object),
                        List.of(),
                        List.of());
    }

    @Test
    void supertypesAreEveryParameterizedSupertypeSortedInTermsOfTheTypeParameters() {
        TypeModel model = full("p.Pair", """
                package p;
                public abstract class Pair<K, V> extends Base<K> implements Comparable<Pair<K, V>>, java.io.Serializable {}
                abstract class Base<X> implements Iterable<X> {}
                """);

        assertThat(model.supertypes())
                .isEqualTo(new Supertypes(
                        List.of(Types.typeVar("K"), Types.typeVar("V")),
                        List.of(
                                Types.parameterized(
                                        CD_COMPARABLE,
                                        Types.parameterized(
                                                ClassDesc.of("p.Pair"), Types.typeVar("K"), Types.typeVar("V"))),
                                Types.parameterized(ClassDesc.of("java.lang.Iterable"), Types.typeVar("K")),
                                Types.parameterized(ClassDesc.of("p.Base"), Types.typeVar("K")))));
    }

    @Test
    void rawSupertypesAreNotRecorded() {
        TypeModel model = full("p.Raw", """
                package p;
                @SuppressWarnings("rawtypes")
                public class Raw extends java.util.ArrayList {}
                """);

        assertThat(model.supertypes()).isEqualTo(Supertypes.NONE);
    }

    @Test
    void unrepresentableSupertypesMakeTheTypeUnrepresentable() {
        assertThat(translate("p.Sub", """
                        package p;
                        public class Sub extends Outer<String>.Inner { public Sub() { new Outer<String>().super(); } }
                        """, """
                        package p;
                        public class Outer<T> { public class Inner {} }
                        """))
                .isEqualTo(new Translation.Unrepresentable<>(
                        "type p.Sub: member class p.Outer.Inner of parameterized " + "type p.Outer<java.lang.String>"));
    }

    // ---- the method table

    @Test
    void methodTableHasEveryNonPrivateMethodByKind() {
        MethodTableTemplate table = full("p.Impl", """
                package p;
                public abstract class Impl<E> extends Base implements Api<E> {
                    public abstract void abs(E e);
                    protected void prot(E[] es) {}
                    void pack(int a, long b, short c, byte d, char e, float f, double g, boolean h) {}
                    private void priv() {}
                    public <U> void poly(U u) {}
                    public <U extends Number> void bounded(U u) {}
                }
                abstract class Base {
                    public static void inheritedStatic() {}
                    public void inherited() {}
                }
                interface Api<T> {
                    void api(T t);
                    default void def(String s) {}
                    static void interfaceStatic() {}
                }
                """).methods();

        assertThat(table.abstractMethods())
                .contains(Signature.of("abs", var(0)), Signature.of("api", var(0)))
                .doesNotContain(Signature.of("def", fixed(ConstantDescs.CD_String)));
        assertThat(table.concreteMethods())
                .contains(
                        Signature.of("prot", fixed(ConstantDescs.CD_Object.arrayType())),
                        Signature.of(
                                "pack",
                                fixed(ConstantDescs.CD_int),
                                fixed(ConstantDescs.CD_long),
                                fixed(ConstantDescs.CD_short),
                                fixed(ConstantDescs.CD_byte),
                                fixed(ConstantDescs.CD_char),
                                fixed(ConstantDescs.CD_float),
                                fixed(ConstantDescs.CD_double),
                                fixed(ConstantDescs.CD_boolean)),
                        Signature.of("poly", fixed(ConstantDescs.CD_Object)),
                        Signature.of("bounded", fixed(CD_NUMBER)),
                        Signature.of("inherited"),
                        Signature.of("def", fixed(ConstantDescs.CD_String)),
                        Signature.of("toString"),
                        Signature.of("clone"));
        assertThat(table.staticMethods()).containsExactly(Signature.of("inheritedStatic"));
        assertThat(names(table)).doesNotContain("priv", "interfaceStatic");
    }

    @Test
    void methodTableOfAnInterfaceHasItsOwnStaticMethods() {
        MethodTableTemplate table = full("p.Api", """
                package p;
                public interface Api {
                    static void own() {}
                    private void hidden() {}
                }
                """).methods();

        assertThat(table.staticMethods()).containsExactly(Signature.of("own"));
        assertThat(names(table)).doesNotContain("hidden");
    }

    @Test
    void inheritedMethodsAreMembersOfTheType() {
        MethodTableTemplate table = full("p.Strings", """
                package p;
                public class Strings extends Box<String> {}
                class Box<T> { public void put(T t) {} }
                """).methods();

        assertThat(table.concreteMethods()).contains(Signature.of("put", fixed(ConstantDescs.CD_String)));
    }

    @Test
    void anInheritedConcreteMethodImplementsAnInheritedAbstractOne() {
        MethodTableTemplate table = full("p.C", """
                package p;
                public class C extends B implements I {}
                class B { public void m() {} }
                interface I { void m(); }
                """).methods();

        assertThat(table.concreteMethods()).contains(Signature.of("m"));
        assertThat(table.abstractMethods()).doesNotContain(Signature.of("m"));
    }

    private static Set<String> names(MethodTableTemplate table) {
        return java.util.stream.Stream.of(table.abstractMethods(), table.concreteMethods(), table.staticMethods())
                .flatMap(Set::stream)
                .map(Signature::name)
                .collect(Collectors.toSet());
    }

    // ---- enum constants

    @Test
    void enumConstantsAreInDeclarationOrderAndValuesAndValueOfAreStaticMethods() {
        TypeModel model = full("p.Day", """
                package p;
                public enum Day { MONDAY, TUESDAY, SUNDAY; public int x() { return 0; } }
                """);

        assertThat(model.enumConstants()).containsExactly("MONDAY", "TUESDAY", "SUNDAY");
        assertThat(methods(model).get("values"))
                .isEqualTo(new MethodModel(
                        "values",
                        true,
                        List.of(),
                        Optional.of(Types.array(Types.of(ClassDesc.of("p.Day")))),
                        List.of(),
                        List.of(),
                        Overridability.FINAL));
        assertThat(methods(model).get("valueOf").params()).containsExactly(Types.STRING);
        assertThat(methods(model).get("x").overridability()).isEqualTo(Overridability.FINAL);
    }

    // ---- members

    @Test
    void tokenOnlyModelsHaveNoMembers() {
        TypeModel model = Harness.ok(Harness.run(env -> env.tokenOnly("p.Svc"), """
                package p;
                public class Svc { public int x; public void m() {} public void hidden(Hidden h) {} }
                class Hidden {}
                """));

        assertThat(model.filter()).isEqualTo(MemberFilter.NONE);
        assertThat(model.members()).isEmpty();
        assertThat(model.skipped()).isEmpty();
    }

    @Test
    void fieldsAreMutableFinalOrConstant() {
        TypeModel model = full("p.Fields", """
                package p;
                public class Fields<T> {
                    public int mutable;
                    public final int instanceFinal = 5;
                    public static int staticMutable;
                    public static final Object staticFinal = new Object();
                    public static final int CONSTANT = 7;
                    public static final String TEXT = "hi";
                    public T value;
                    int packagePrivate;
                    protected int prot;
                    private int priv;
                }
                """);

        assertThat(model.members())
                .filteredOn(FieldModel.class::isInstance)
                .containsExactly(
                        new FieldModel("mutable", false, Types.INT, Mutability.MUTABLE),
                        new FieldModel("instanceFinal", false, Types.INT, Mutability.FINAL),
                        new FieldModel("staticMutable", true, Types.INT, Mutability.MUTABLE),
                        new FieldModel("staticFinal", true, Types.OBJECT, Mutability.FINAL),
                        new FieldModel("CONSTANT", true, Types.INT, new Mutability.Constant(7)),
                        new FieldModel("TEXT", true, Types.STRING, new Mutability.Constant("hi")),
                        new FieldModel("value", false, Types.typeVar("T"), Mutability.MUTABLE));
    }

    @Test
    void constructorsHaveTheirTypeParametersParametersAndExceptions() {
        TypeModel model = full("p.Ctors", """
                package p;
                public class Ctors<T> {
                    public Ctors() {}
                    public <X extends Number> Ctors(X x, T t) throws java.io.IOException {}
                    Ctors(int hidden) {}
                }
                """);

        assertThat(model.members())
                .containsExactly(
                        new CtorModel(List.of(), List.of(), List.of()),
                        new CtorModel(
                                List.of(new TypeParam("X", List.of(Types.of(CD_NUMBER)))),
                                List.of(Types.typeVar("X"), Types.typeVar("T")),
                                List.of(Types.of(IOException.class))));
    }

    @Test
    void methodsAreDeclaredPublicOnesWithOverridability() {
        TypeModel model = full("p.Methods", """
                package p;
                public abstract class Methods<T> extends Base {
                    public abstract T abs();
                    public final void fin() {}
                    public static <S> S stat(S s) { return s; }
                    public void open(int... xs) {}
                    public <X extends Exception> void rethrow() throws X, java.io.IOException {}
                    protected void prot() {}
                    void pack() {}
                    private void priv() {}
                }
                class Base { public void inherited() {} }
                """);

        assertThat(model.members())
                .filteredOn(MethodModel.class::isInstance)
                .containsExactly(
                        new MethodModel(
                                "abs",
                                false,
                                List.of(),
                                Optional.of(Types.typeVar("T")),
                                List.of(),
                                List.of(),
                                Overridability.ABSTRACT),
                        new MethodModel(
                                "fin", false, List.of(), Optional.empty(), List.of(), List.of(), Overridability.FINAL),
                        new MethodModel(
                                "stat",
                                true,
                                List.of(new TypeParam("S", List.of())),
                                Optional.of(Types.typeVar("S")),
                                List.of(Types.typeVar("S")),
                                List.of(),
                                Overridability.FINAL),
                        new MethodModel(
                                "open",
                                false,
                                List.of(),
                                Optional.empty(),
                                List.of(Types.array(Types.INT)),
                                List.of(),
                                Overridability.OVERRIDABLE),
                        new MethodModel(
                                "rethrow",
                                false,
                                List.of(new TypeParam("X", List.of(Types.of(Exception.class)))),
                                Optional.empty(),
                                List.of(),
                                List.of(Types.typeVar("X"), Types.of(IOException.class)),
                                Overridability.OVERRIDABLE));
    }

    @Test
    void methodsOfAFinalClassAndDefaultMethodsOfAnInterface() {
        List<Map<String, MethodModel>> models = Harness.run(
                env -> List.of(methods(Harness.ok(env.full("p.Fin"))), methods(Harness.ok(env.full("p.Api")))),
                """
                package p;
                public final class Fin { public void m() {} }
                """,
                """
                package p;
                public interface Api { void abs(); default void def() {} static void stat() {} }
                """);

        assertThat(models.get(0).get("m").overridability()).isEqualTo(Overridability.FINAL);
        assertThat(models.get(1))
                .extractingFromEntries(e -> e.getKey() + " " + e.getValue().overridability())
                .containsExactlyInAnyOrder("abs ABSTRACT", "def OVERRIDABLE", "stat FINAL");
    }

    @Test
    void membersMentioningTypesThatAreNotPublicAreSkipped() {
        TypeModel model = full("p.Leaky", """
                package p;
                public class Leaky {
                    public Hidden field;
                    public Leaky(Hidden h) {}
                    public Hidden result() { return null; }
                    public void param(java.util.List<Hidden> h) {}
                    public void fails() throws HiddenException {}
                    public <T extends Hidden> void bound() {}
                    public Public.Nested nested() { return null; }
                    public void fine() {}
                }
                class Hidden {}
                class HiddenException extends Exception {}
                """, """
                package p;
                public class Public { static class Nested {} }
                """);

        assertThat(model.members())
                .containsExactly(new MethodModel(
                        "fine", false, List.of(), Optional.empty(), List.of(), List.of(), Overridability.OVERRIDABLE));
        assertThat(model.skipped())
                .containsExactly(
                        new SkippedMember("field field", "mentions types that are not public: p.Hidden"),
                        new SkippedMember(
                                "constructor Leaky(p.Hidden)", "mentions types that are not public: p.Hidden"),
                        new SkippedMember("method result()", "mentions types that are not public: p.Hidden"),
                        new SkippedMember(
                                "method param(java.util.List<p.Hidden>)",
                                "mentions types that are not public: p.Hidden"),
                        new SkippedMember("method fails()", "mentions types that are not public: p.HiddenException"),
                        new SkippedMember("method <T>bound()", "mentions types that are not public: p.Hidden"),
                        new SkippedMember("method nested()", "mentions types that are not public: p.Public$Nested"));
    }

    @Test
    void unrepresentableMembersAndTooManyParametersAreSkipped() {
        TypeModel model = full("p.Odd", """
                package p;
                public class Odd {
                    public Outer<String>.Inner inner;
                    public void thirteen(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l, int m) {}
                    public Odd(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l, int m) {}
                    public void twelve(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l) {}
                }
                """, """
                package p;
                public class Outer<T> { public class Inner {} }
                """);

        assertThat(model.members()).hasSize(1);
        assertThat(model.skipped())
                .containsExactly(
                        new SkippedMember(
                                "field inner",
                                "member class p.Outer.Inner of parameterized type p.Outer<java.lang.String>"),
                        new SkippedMember(
                                "method thirteen(int,int,int,int,int,int,int,int,int,int,int,int,int)",
                                "has 13 parameters, more than 12"),
                        new SkippedMember(
                                "constructor Odd(int,int,int,int,int,int,int,int,int,int,int,int,int)",
                                "has 13 parameters, more than 12"));
    }

    // ---- deferred

    @Test
    void anyMentionOfATypeNotGeneratedYetDefersTheType() {
        Map<String, Translation<TypeModel>> translations = Harness.runUnresolved(
                env -> {
                    Map<String, Translation<TypeModel>> result = new java.util.LinkedHashMap<>();
                    for (String name : List.of("p.Member", "p.Super", "p.Iface", "p.TableOnly", "p.Bound", "p.Both")) {
                        result.put(name, env.full(name));
                    }
                    result.put("p.TableOnly token-only", env.tokenOnly("p.TableOnly"));
                    return result;
                },
                """
                package p;
                public class Member { public gen.Missing m() { return null; } }
                """,
                """
                package p;
                public class Super extends gen.Missing {}
                """,
                """
                package p;
                public abstract class Iface implements gen.Missing {}
                """,
                """
                package p;
                public class TableOnly { void hidden(gen.Missing m) {} }
                """,
                """
                package p;
                public class Bound<T extends gen.Missing> {}
                """,
                """
                package p;
                public class Both { public void m(Outer<String>.Inner i, gen.Missing m) {} }
                """,
                """
                package p;
                public class Outer<T> { public class Inner {} }
                """);

        assertThat(translations.values())
                .allSatisfy(t -> assertThat(t).isEqualTo(new Translation.Deferred<>("gen.Missing")));
        assertThat(translations).hasSize(7);
    }

    @Test
    void aTypeDeferredInOneRoundIsTranslatedOnceGenerated() {
        List<Translation<TypeModel>> rounds =
                Harness.everyRound(env -> env.full("p.User"), List.of(new Generator()), """
                package p;
                public class User { public gen.Made made; }
                """);

        assertThat(rounds).hasSize(2);
        assertThat(rounds.getFirst()).isEqualTo(new Translation.Deferred<>("gen.Made"));
        assertThat(Harness.ok(rounds.getLast()).members())
                .contains(new FieldModel("made", false, Types.of(ClassDesc.of("gen.Made")), Mutability.MUTABLE));
    }

    /// Generates `gen.Made` in the first round, as another processor would.
    static final class Generator extends AbstractProcessor {
        private boolean done;

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
            if (!done) {
                done = true;
                try (Writer writer =
                        processingEnv.getFiler().createSourceFile("gen.Made").openWriter()) {
                    writer.write("package gen; public class Made {}");
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
            return false;
        }
    }

    // ---- the JDK

    @Test
    void jdkTypes() {
        Map<String, TypeModel> jdk = Harness.run(env -> {
            Map<String, TypeModel> result = new java.util.LinkedHashMap<>();
            for (String name : List.of(
                    "java.lang.String",
                    "java.util.List",
                    "java.util.Map.Entry",
                    "java.util.Comparator",
                    "java.util.Collections",
                    "java.util.Optional",
                    "java.lang.Integer")) {
                result.put(name, Harness.ok(env.full(name)));
            }
            return result;
        });

        TypeModel string = jdk.get("java.lang.String");
        assertThat(string.kind()).isEqualTo(DeclaredKind.FINAL_CLASS);
        assertThat(string.superclasses()).containsExactly(ConstantDescs.CD_Object);
        assertThat(string.supertypes().supertypes()).contains(Types.parameterized(CD_COMPARABLE, Types.STRING));
        assertThat(string.methods().concreteMethods()).contains(Signature.of("charAt", fixed(ConstantDescs.CD_int)));
        assertThat(string.methods().staticMethods()).contains(Signature.of("valueOf", fixed(ConstantDescs.CD_int)));

        TypeModel list = jdk.get("java.util.List");
        assertThat(list.typeParams()).containsExactly(new TypeParam("E", List.of()));
        assertThat(list.supertypes().supertypes())
                .contains(Types.parameterized(ConstantDescs.CD_Collection, Types.typeVar("E")));
        assertThat(list.methods().abstractMethods())
                .contains(Signature.of("get", fixed(ConstantDescs.CD_int)), Signature.of("add", var(0)));
        assertThat(list.methods().staticMethods()).contains(Signature.of("of", fixed(ConstantDescs.CD_Object)));

        TypeModel entry = jdk.get("java.util.Map.Entry");
        assertThat(entry.desc()).isEqualTo(ClassDesc.of("java.util.Map$Entry"));
        assertThat(entry.kind()).isEqualTo(DeclaredKind.INTERFACE);

        // Comparator redeclares equals(Object) abstract; every class has it from Object
        assertThat(jdk.get("java.util.Comparator").methods().abstractMethods())
                .containsExactly(Signature.of("compare", var(0), var(0)));
        assertThat(jdk.get("java.util.Comparator").methods().concreteMethods())
                .contains(Signature.of("equals", fixed(ConstantDescs.CD_Object)));

        MethodModel max = methods(jdk.get("java.util.Collections")).get("max");
        assertThat(max.typeParams())
                .containsExactly(new TypeParam(
                        "T",
                        List.of(
                                Types.OBJECT,
                                Types.parameterized(CD_COMPARABLE, Types.superBound(Types.typeVar("T"))))));

        MethodModel orElseThrow = jdk.get("java.util.Optional").members().stream()
                .filter(m -> m instanceof MethodModel method
                        && method.name().equals("orElseThrow")
                        && !method.params().isEmpty())
                .map(MethodModel.class::cast)
                .findFirst()
                .orElseThrow();
        assertThat(orElseThrow.throwsTypes()).containsExactly(Types.typeVar("X"));
        assertThat(orElseThrow.result()).contains(Types.typeVar("T"));

        assertThat(jdk.get("java.lang.Integer").members())
                .contains(new FieldModel("MAX_VALUE", true, Types.INT, new Mutability.Constant(Integer.MAX_VALUE)));
    }

    @Test
    void theTranslationIsTheSameAsTypeRefForAMember() {
        Function<Harness.Env, List<Object>> action = env -> List.of(
                methods(Harness.ok(env.full("java.util.Optional")))
                        .get("get")
                        .result()
                        .orElseThrow(),
                ((Translation.Ok<TypeRef>) env.translator()
                                .typeRef(
                                        env.method("java.util.Optional", "get").getReturnType(),
                                        VarScope.of(env.method("java.util.Optional", "get"))))
                        .value());

        List<Object> results = Harness.run(action);

        assertThat(results.get(0)).isEqualTo(results.get(1));
    }

    // ---- member(TypeElement, Element)

    @Test
    void memberTranslatesOneMemberAsTypeDoes() {
        List<Translation<MemberModel>> results = Harness.run(
                env -> {
                    TypeElement type = env.element("p.M");
                    return type.getEnclosedElements().stream()
                            .filter(e -> e.getKind() != ElementKind.CONSTRUCTOR
                                    || e.getModifiers().contains(Modifier.PUBLIC))
                            .map(e -> env.translator().member(type, e))
                            .toList();
                },
                """
                package p;
                public class M {
                    public int field;
                    public M(String s) {}
                    public String method(long l) { return null; }
                    public Hidden hidden() { return null; }
                }
                """,
                "package p; class Hidden {}");

        assertThat(results).hasSize(4);
        assertThat(results.get(0))
                .isEqualTo(
                        new Translation.Ok<MemberModel>(new FieldModel("field", false, Types.INT, Mutability.MUTABLE)));
        assertThat(((Translation.Ok<MemberModel>) results.get(1)).value()).isInstanceOf(CtorModel.class);
        assertThat(((Translation.Ok<MemberModel>) results.get(2)).value())
                .isInstanceOfSatisfying(MethodModel.class, m -> {
                    assertThat(m.name()).isEqualTo("method");
                    assertThat(m.params()).containsExactly(PrimitiveTypeRef.LONG);
                });
        assertThat(results.get(3))
                .isEqualTo(
                        new Translation.Unrepresentable<MemberModel>("mentions types that are not public: p.Hidden"));
    }

    @Test
    void memberIsDeferredWhileASignatureMentionsATypeNotGeneratedYet() {
        Translation<MemberModel> result = Harness.runUnresolved(
                env -> env.translator().member(env.element("p.M"), env.method("p.M", "later")),
                "package p; public class M { public Missing later() { return null; } }");

        assertThat(result).isInstanceOf(Translation.Deferred.class);
    }

    @Test
    void memberRefusesWhatIsNotAFieldConstructorOrMethod() {
        Harness.run(
                env -> {
                    TypeElement type = env.element("p.M");
                    Element nested = type.getEnclosedElements().stream()
                            .filter(e -> e.getKind() == ElementKind.CLASS)
                            .findFirst()
                            .orElseThrow();
                    assertThatThrownBy(() -> env.translator().member(type, nested))
                            .isInstanceOf(IllegalArgumentException.class);
                    return 0;
                },
                "package p; public class M { public static class N {} }");
    }

    // ---- sam(TypeElement)

    private static Translation<Optional<SamModel>> sam(String name, String... sources) {
        return Harness.run(env -> env.translator().sam(env.element(name)), sources);
    }

    private static SamModel samOf(String name, String... sources) {
        Translation<Optional<SamModel>> result = sam(name, sources);
        assertThat(result).isInstanceOf(Translation.Ok.class);
        return ((Translation.Ok<Optional<SamModel>>) result).value().orElseThrow();
    }

    @Test
    void aSamIsTheSingleAbstractMethodWithoutTheMethodsOfObject() {
        SamModel sam = samOf(
                "p.Fn",
                "package p; public interface Fn { String apply(String s); boolean equals(Object o); int hashCode(); String toString(); }");

        assertThat(sam.declared()).isTrue();
        assertThat(sam.method().name()).isEqualTo("apply");
        assertThat(sam.method().params()).containsExactly(Types.STRING);
        assertThat(sam.method().result()).contains(Types.STRING);
        assertThat(sam.method().overridability()).isEqualTo(Overridability.ABSTRACT);
    }

    @Test
    void aSamMayBeInheritedAndIsAMemberOfTheInterface() {
        SamModel sam = samOf(
                "p.StrFn", "package p; public interface StrFn extends java.util.function.Function<String, String> {}");

        assertThat(sam.declared()).isFalse();
        assertThat(sam.method().name()).isEqualTo("apply");
        assertThat(sam.method().params()).containsExactly(Types.STRING);
        assertThat(sam.method().result()).contains(Types.STRING);
    }

    @Test
    void aSamOfAGenericInterfaceIsInTermsOfItsTypeParameters() {
        SamModel sam = samOf("p.Op", "package p; public interface Op<T> extends java.util.function.Function<T, T> {}");

        assertThat(sam.method().params()).containsExactly(Types.typeVar("T"));
        assertThat(sam.method().result()).contains(Types.typeVar("T"));
    }

    @Test
    void theSamOfSeveralOverrideEquivalentMethodsHasTheMostSpecificResult() {
        SamModel declared = samOf(
                "p.C",
                "package p; public interface A { Object get(); }",
                "package p; public interface B { CharSequence get(); }",
                "package p; public interface C extends A, B { String get(); }");
        assertThat(declared.method().result()).contains(Types.STRING);

        SamModel inherited = samOf(
                "p.D",
                "package p; public interface E { Object get(); }",
                "package p; public interface F { CharSequence get(); }",
                "package p; public interface G extends E, F {}",
                "package p; public interface D extends G {}");
        assertThat(inherited.method().result()).contains(Types.of(ClassDesc.of("java.lang.CharSequence")));

        SamModel primitive = samOf(
                "p.P",
                "package p; public interface Q { int get(); }",
                "package p; public interface R { int get(); }",
                "package p; public interface P extends Q, R {}");
        assertThat(primitive.method().result()).contains(Types.INT);
    }

    @Test
    void thereIsNoSamWhereAnInterfaceIsNotFunctional() {
        Translation<Optional<SamModel>> none = new Translation.Ok<>(Optional.empty());

        assertThat(sam("p.Two", "package p; public interface Two { void a(); void b(); }"))
                .isEqualTo(none);
        assertThat(sam("p.None", "package p; public interface None {}")).isEqualTo(none);
        assertThat(sam("p.Gen", "package p; public interface Gen { <T> T id(T t); }"))
                .isEqualTo(none);
        assertThat(sam("p.Cls", "package p; public abstract class Cls { public abstract void run(); }"))
                .isEqualTo(none);
        assertThat(sam("p.Dflt", "package p; public interface Dflt extends Runnable { default void run() {} }"))
                .isEqualTo(none);
    }

    @Test
    void aSamWithoutAModelIsReportedOnlyWhereItIsInherited() {
        String hidden = "package p; class Hidden {}";

        // declared: the skipped member of the type says why
        assertThat(sam("p.Own", "package p; public interface Own { Hidden run(); }", hidden))
                .isEqualTo(new Translation.Ok<Optional<SamModel>>(Optional.empty()));
        assertThat(sam(
                        "p.Sub",
                        "package p; public interface Base { Hidden run(); }",
                        "package p; public interface Sub extends Base {}",
                        hidden))
                .isEqualTo(new Translation.Unrepresentable<Optional<SamModel>>(
                        "mentions types that are not public: p.Hidden"));
    }

    @Test
    void aSamIsDeferredWhileItsSignatureMentionsATypeNotGeneratedYet() {
        Translation<Optional<SamModel>> result = Harness.runUnresolved(
                env -> env.translator().sam(env.element("p.Later")),
                "package p; public interface Later { Missing run(); }");

        assertThat(result).isInstanceOf(Translation.Deferred.class);
    }

    @Test
    void anInterfaceRedeclaringAnObjectMethodDoesNotAskForItButAnAbstractClassDoes() {
        Map<String, TypeModel> models = Harness.run(
                env -> Map.of(
                        "i", Harness.ok(env.tokenOnly("p.I")),
                        "c", Harness.ok(env.tokenOnly("p.C"))),
                "package p; public interface I { boolean equals(Object o); String toString(); void m(); }",
                "package p; public abstract class C { public abstract String toString(); public abstract void m(); }");

        assertThat(models.get("i").methods().abstractMethods()).containsExactly(Signature.of("m"));
        assertThat(models.get("i").methods().concreteMethods())
                .contains(Signature.of("equals", fixed(ConstantDescs.CD_Object)), Signature.of("toString"));
        assertThat(models.get("c").methods().abstractMethods())
                .containsExactlyInAnyOrder(Signature.of("toString"), Signature.of("m"));
    }
}
