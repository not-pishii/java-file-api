package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `polymorphic` (mini-spec §9.2): the fact of a generic method is
/// made by a method of the metamodel from a token per type parameter — a
/// witness, under the bounds the generic method declares — and has the
/// witnesses as its explicit type arguments.
class PolymorphicFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Poly {
            public int t;
            public static final int T = 1;
            public <T> Poly(T t) {}
            public Poly() {}
            public <T> T id(T t) { return t; }
            @SafeVarargs
            public static <T> java.util.List<T> listOf(T... items) { return null; }
            public static <T extends Comparable<? super T>> T max(java.util.Collection<? extends T> items) { return null; }
            public <A, B extends A> A widen(B b) { return b; }
            public <X extends Exception> void run(Class<X> type) throws X, java.io.IOException {}
            public <T extends Number & Comparable<T>> T clamp(T value) { return value; }
            public <TOKEN, Gen> void odd(TOKEN a, Gen b) {}
            public <T> void none() {}
        }
        """, """
        package p;
        public class PBox<T> {
            public T t;
            public <R> PBox<R> map(java.util.function.Function<? super T, ? extends R> f) { return null; }
            public <T> T shadow(T other) { return other; }
            public static <T> PBox<T> of(T value) { return null; }
            public <U extends T> void put(U u) {}
            public static <E extends Enum<E>> PBox<E> ofEnum(Class<E> type) { return null; }
        }
        """, "package p; public interface PolyFn { <T> T apply(T t); }",
    };

    private static final String ALL =
            "p.Poly.class, p.PBox.class, p.PolyFn.class, String.class, Integer.class, Object.class";

    private static final String POLY = "gen.facts.p.Poly_";
    private static final String PBOX = "gen.facts.p.PBox_";
    private static final ClassDesc CD_PBOX = ClassDesc.of("p.PBox");

    private static RefToken<?> string(ClassLoader loader) throws ReflectiveOperationException {
        return token(loader, "gen.facts.java.lang.String_");
    }

    private static RefToken<?> integer(ClassLoader loader) throws ReflectiveOperationException {
        return token(loader, "gen.facts.java.lang.Integer_");
    }

    private static TypeRef result(Invocable fact) {
        return fact.resultType().orElseThrow().typeRef();
    }

    private static List<TypeRef> params(Invocable fact) {
        return fact.params().stream().<TypeRef>map(TypeToken::typeRef).toList();
    }

    private static TypeRef applied(ClassDesc type, TypeArg... args) {
        return new ParameterizedTypeRef(type, List.of(args));
    }

    private static Method method(ClassLoader loader, String metamodel, String name) throws ClassNotFoundException {
        return Arrays.stream(loader.loadClass(metamodel).getDeclaredMethods())
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    /// The type parameters of a method of a metamodel, as `T extends A & B`.
    private static List<String> typeParameters(Method method) {
        return Arrays.stream(method.getTypeParameters())
                .map(variable -> variable.getName() + " extends "
                        + String.join(
                                " & ",
                                Arrays.stream(variable.getBounds())
                                        .map(Type::getTypeName)
                                        .toList()))
                .toList();
    }

    @Test
    void aGenericMethodIsAFactMadeFromAWitnessPerTypeParameter() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        RefToken<?> string = string(loader);

        Invocable id = (Invocable) made(loader, POLY, null, "id_T", string);

        assertThat(id.name()).isEqualTo("id");
        assertThat(id.owner()).isSameAs(token(loader, POLY));
        assertThat(id.resultType().orElseThrow()).isSameAs(string);
        assertThat(id.params()).containsExactly(string);
        assertThat(id.traits()).isEqualTo(MemberTraits.OVERRIDABLE.withTypeArgs(string));
        // the type is not generic, so nothing but the witness is needed: the method is static
        assertThat(Modifier.isStatic(method(loader, POLY, "id_T").getModifiers()))
                .isTrue();
        assertThat(sources(compilation).get(POLY))
                .contains("public static <T> MethodRef1<Poly, T, T> id_T(RefToken<T> t) {")
                // the parameter is declared by the type parameter of the method: it erases to Object
                .contains("return UnsafeFacts.method(TOKEN, \"id\", t, "
                        + "UnsafeFacts.param(t, Param.fixed(ClassDesc.of(\"java.lang.Object\"))), "
                        + "MemberTraits.OVERRIDABLE.withTypeArgs(t));");
        // a type parameter that no parameter mentions is given explicitly all the same
        Invocable none = (Invocable) made(loader, POLY, null, "none", string);
        assertThat(none.params()).isEmpty();
        assertThat(none.traits().typeArgs()).containsExactly(string);
    }

    @Test
    void aStaticGenericMethodIsAStaticFact() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));
        RefToken<?> string = string(loader);

        Invocable listOf = (Invocable) made(loader, POLY, null, "listOf_TArray", string);

        assertThat(listOf.traits()).isEqualTo(MemberTraits.FINAL.withTypeArgs(string));
        assertThat(result(listOf)).isEqualTo(applied(ConstantDescs.CD_List, Types.exact(Types.STRING)));
        // a variable-arity parameter is its array
        assertThat(params(listOf)).containsExactly(Types.array(Types.STRING));
        Invocable max = (Invocable) made(loader, POLY, null, "max_Collection", string);
        assertThat(max.resultType().orElseThrow()).isSameAs(string);
        assertThat(params(max)).containsExactly(applied(ConstantDescs.CD_Collection, Types.extendsBound(Types.STRING)));
    }

    @Test
    void theWitnessesAreUnderTheBoundsOfTheTypeParameters() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));

        assertThat(typeParameters(method(loader, POLY, "max_Collection")))
                .containsExactly("T extends java.lang.Comparable<? super T>");
        assertThat(typeParameters(method(loader, POLY, "clamp_T")))
                .containsExactly("T extends java.lang.Number & java.lang.Comparable<T>");
        assertThat(typeParameters(method(loader, POLY, "widen_B")))
                .containsExactly("A extends java.lang.Object", "B extends A");
        assertThat(typeParameters(method(loader, POLY, "run_Class"))).containsExactly("X extends java.lang.Exception");
        assertThat(typeParameters(method(loader, PBOX, "ofEnum_Class"))).containsExactly("E extends java.lang.Enum<E>");
        // a bound may be a type parameter of the type
        assertThat(typeParameters(method(loader, PBOX, "put_U"))).containsExactly("U extends T");
        Invocable widen = (Invocable)
                made(loader, POLY, null, "widen_B", token(loader, "gen.facts.java.lang.Object_"), string(loader));
        assertThat(widen.traits().typeArgs())
                .containsExactly(token(loader, "gen.facts.java.lang.Object_"), string(loader));
        assertThat(result(widen)).isEqualTo(Types.OBJECT);
        assertThat(params(widen)).containsExactly(Types.STRING);
    }

    @Test
    void aWitnessOutOfBoundsDoesNotCompile() {
        load(generate(ALL, LIBRARY));
        String imports = """
                package u;
                import gen.facts.p.Poly_;
                import gen.facts.p.PBox_;
                import gen.facts.java.lang.String_;
                import gen.facts.java.lang.Integer_;
                import gen.facts.java.lang.Object_;
                """;

        // the control: the same code with witnesses within bounds
        assertThat(errors(use(imports + """
                class Use {
                    Object max = Poly_.max_Collection(String_.TOKEN);
                    Object clamp = Poly_.clamp_T(Integer_.TOKEN);
                    Object widen = Poly_.widen_B(Object_.TOKEN, String_.TOKEN);
                    Object put = new PBox_<>(Object_.TOKEN).put_U(String_.TOKEN);
                    Object id = Poly_.id_T(Object_.TOKEN);
                }
                """))).isEmpty();
        assertThat(errors(use(imports + "class Use { Object o = Poly_.max_Collection(Object_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("has incompatible bounds");
        assertThat(errors(use(imports + "class Use { Object o = Poly_.<Object>max_Collection(Object_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("explicit type argument java.lang.Object does not conform to declared bound(s)");
        // Comparable<String>, but not a Number
        assertThat(errors(use(imports + "class Use { Object o = Poly_.clamp_T(String_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("has incompatible bounds");
        // B extends A
        assertThat(errors(use(imports + "class Use { Object o = Poly_.widen_B(String_.TOKEN, Integer_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("has incompatible bounds");
        // U extends T, the type argument of the instance
        assertThat(errors(use(imports + "class Use { Object o = new PBox_<>(String_.TOKEN).put_U(Integer_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("has incompatible bounds");
        // a witness is a token: a value of the type is none
        assertThat(errors(use(imports + "class Use { Object o = Poly_.id_T(\"text\"); }")))
                .singleElement()
                .asString()
                .contains("java.lang.String cannot be converted to me.supcheg.javafile.facts.RefToken<T>");
    }

    @Test
    void aGenericMethodThrowsTheWitnessOfItsTypeParameter() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        RefToken<?> ioException = token(loader, "gen.facts.java.io.IOException_");

        Invocable run = (Invocable) made(loader, POLY, null, "run_Class", ioException);

        assertThat(List.<Object>copyOf(run.traits().throwsTypes())).containsExactly(ioException, ioException);
        assertThat(run.traits().throwsTypes().getFirst()).isSameAs(ioException);
        assertThat(run.traits().typeArgs()).containsExactly(ioException);
        assertThat(run.traits().overridability()).isEqualTo(Overridability.OVERRIDABLE);
        assertThat(params(run))
                .containsExactly(applied(ClassDesc.of("java.lang.Class"), Types.exact(ioException.typeRef())));
        assertThat(sources(compilation).get(POLY))
                .contains("public static <X extends Exception> VoidMethodRef1<Poly, Class<X>> run_Class("
                        + "RefToken<X> x) {")
                .contains("MemberTraits.OVERRIDABLE.throwing(x, UnsafeFacts.<IOException>openClassToken(");
    }

    @Test
    void aGenericConstructorHasNoFact() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);

        // new gives a constructor no explicit type arguments, and a fact leaves none to inference
        assertThat(warnings(compilation))
                .containsExactly("p.Poly: no fact of constructor <T>Poly(T), which is a generic constructor, whose"
                        + " type arguments a fact cannot give explicitly");
        assertThat(memberNames(loader, POLY))
                .containsExactly(
                        "T",
                        "clamp_T",
                        "id_T",
                        "listOf_TArray",
                        "max_Collection",
                        "new_",
                        "none",
                        "odd_TOKEN_Gen",
                        "run_Class",
                        "t",
                        "widen_B");
    }

    @Test
    void whatIsLocalToAFactoryTakesNoNameFromAFact() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        RefToken<?> string = string(loader);
        RefToken<?> integer = integer(loader);

        // the fields t and T keep their names beside the type parameter T and its witness t
        assertThat(fact(loader, POLY, "t")).isNotNull();
        assertThat(fact(loader, POLY, "T")).isNotNull();
        // a witness is not named like what the factory reads: TOKEN is the token, gen a package
        assertThat(sources(compilation).get(POLY))
                .contains("public static <TOKEN, Gen> VoidMethodRef2<Poly, TOKEN, Gen> odd_TOKEN_Gen("
                        + "RefToken<TOKEN> tOKEN, RefToken<Gen> gen_) {")
                .contains("return UnsafeFacts.voidMethod(TOKEN, \"odd\", UnsafeFacts.param(tOKEN, ")
                .contains("UnsafeFacts.param(gen_, ");
        Invocable odd = (Invocable) made(loader, POLY, null, "odd_TOKEN_Gen", string, integer);
        assertThat(odd.owner()).isSameAs(token(loader, POLY));
        assertThat(odd.params()).containsExactly(string, integer);
        // in a generic metamodel the field t is a fact, so the token of T is kept as t_
        assertThat(sources(compilation).get(PBOX))
                .contains("public final MutableFieldRef<PBox<T>, T> t;")
                .contains("private final RefToken<T> t_;")
                .contains("public PBox_(RefToken<T> t_) {");
    }

    @Test
    void aGenericMethodOfAGenericTypeIsMadeByAnInstance() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        RefToken<?> string = string(loader);
        RefToken<?> integer = integer(loader);
        Object box = instance(loader, PBOX, string);

        Invocable map = (Invocable) made(loader, PBOX, box, "map_Function", integer);

        assertThat(Modifier.isStatic(method(loader, PBOX, "map_Function").getModifiers()))
                .isFalse();
        assertThat(map.owner()).isSameAs(fact(box, "token"));
        assertThat(result(map)).isEqualTo(applied(CD_PBOX, Types.exact(integer.typeRef())));
        assertThat(((DeclaredToken<?>) map.resultType().orElseThrow()).shape()).isSameAs(shape(loader, PBOX));
        assertThat(params(map))
                .containsExactly(applied(
                        ClassDesc.of("java.util.function.Function"),
                        Types.superBound(Types.STRING),
                        Types.extendsBound(integer.typeRef())));
        assertThat(map.traits().typeArgs()).containsExactly(integer);
        assertThat(sources(compilation).get(PBOX))
                .contains("public <R> MethodRef1<PBox<T>, PBox<R>, Function<? super T, ? extends R>> map_Function("
                        + "RefToken<R> r) {");
    }

    @Test
    void aTypeParameterOfAMethodThatHidesOneOfTheTypeIsRenamed() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        RefToken<?> string = string(loader);
        RefToken<?> integer = integer(loader);
        Object box = instance(loader, PBOX, string);

        // <T> T shadow(T) in PBox<T>: the fact is of PBox<T> of the type, and takes the T of the method
        assertThat(sources(compilation).get(PBOX))
                .contains("public <T_> MethodRef1<PBox<T>, T_, T_> shadow_T(RefToken<T_> t__) {");
        Invocable shadow = (Invocable) made(loader, PBOX, box, "shadow_T", integer);
        assertThat(shadow.owner()).isSameAs(fact(box, "token"));
        assertThat(shadow.params()).containsExactly(integer);
        assertThat(shadow.resultType().orElseThrow()).isSameAs(integer);
        // a static method does not see the type parameters of the type: nothing to rename
        assertThat(sources(compilation).get(PBOX))
                .contains("public static <T> StaticMethodRef1<PBox<T>, T> of_T(RefToken<T> t) {");
        Invocable of = (Invocable) made(loader, PBOX, null, "of_T", integer);
        assertThat(of.owner()).isSameAs(fact(loader, PBOX, "ANY"));
        assertThat(result(of)).isEqualTo(applied(CD_PBOX, Types.exact(integer.typeRef())));
        assertThat(of.traits()).isEqualTo(MemberTraits.FINAL.withTypeArgs(integer));
    }

    @Test
    void anInterfaceWhoseSingleAbstractMethodIsGenericHasTheFactButNoSam() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);

        // no lambda implements a generic method (JLS 15.27.3)
        assertThat(memberNames(loader, "gen.facts.p.PolyFn_")).containsExactly("apply_T");
        Invocable apply = (Invocable) made(loader, "gen.facts.p.PolyFn_", null, "apply_T", string(loader));
        assertThat(apply.traits()).isEqualTo(MemberTraits.ABSTRACT.withTypeArgs(string(loader)));
    }

    @Test
    void theTypeVariablesOfAFactoryAreItsOwn() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));

        for (String name : List.of("id_T", "max_Collection", "widen_B", "odd_TOKEN_Gen")) {
            Method factory = method(loader, POLY, name);
            assertThat(factory.getParameterCount()).isEqualTo(factory.getTypeParameters().length);
            for (TypeVariable<Method> variable : factory.getTypeParameters()) {
                assertThat(variable.getGenericDeclaration()).isEqualTo(factory);
            }
        }
    }
}
