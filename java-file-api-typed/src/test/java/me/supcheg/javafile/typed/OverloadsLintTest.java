package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.concurrent.Callable_;
import me.supcheg.javafile.typed.testfacts.java.util.function.BiFunction_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Consumer_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Function_;
import me.supcheg.javafile.typed.testfacts.java.util.function.UnaryOperator_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Applying_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.JavacVerdict.asJavacAccepts;
import static me.supcheg.javafile.typed.JavacVerdict.asJavacWarns;
import static me.supcheg.javafile.typed.JavacVerdict.unlikeJavac;

/// javac warns of two methods of one name and number of parameters that take, at one position,
/// unrelated functional interfaces whose methods have as many parameters (`[overloads]`): an implicitly
/// typed lambda would not tell them apart. The typed layer declares no such pair.
class OverloadsLintTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Function_<String, String> FUNCTION = new Function_<>(String_.TOKEN, String_.TOKEN);
    private static final Consumer_<String> CONSUMER = new Consumer_<>(String_.TOKEN);
    private static final BiFunction_<String, String, String> BI_FUNCTION =
            new BiFunction_<>(String_.TOKEN, String_.TOKEN, String_.TOKEN);

    private static JavaFile declaring(TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void twoMethodsOfUnrelatedFunctionalInterfacesAreNotDeclaredTogether() {
        asJavacWarns(
                IllegalArgumentException.class,
                () -> declaring(new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareVoidMethod("m", FUNCTION.token);
                        cb.declareVoidMethod("m", CONSUMER.token);
                        throw new AssertionError("a potentially ambiguous pair was declared");
                    }
                }),
                "is potentially ambiguous with",
                "public void m(Function<String, String> f) {} public void m(Consumer<String> c) {}",
                "is potentially ambiguous with");
    }

    @Test
    void relatedFunctionalInterfacesAndOnesWithoutParametersAreDeclaredTogether() {
        var operator = new UnaryOperator_<>(String_.TOKEN);
        var callable = new Callable_<>(String_.TOKEN);
        asJavacAccepts(
                () -> declaring(new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var function = cb.declareVoidMethod("m", FUNCTION.token);
                        var unary = cb.declareVoidMethod("m", operator.token);
                        var runnable = cb.declareVoidMethod("n", Runnable_.TOKEN);
                        var calls = cb.declareVoidMethod("n", callable.token);
                        var text = cb.declareVoidMethod("o", String_.TOKEN);
                        var consumer = cb.declareVoidMethod("o", CONSUMER.token);
                        var unaryFirst = cb.declareVoidMethod("q", operator.token);
                        var functionThen = cb.declareVoidMethod("q", FUNCTION.token);
                        var withText = cb.declareVoidMethod("r", FUNCTION.token, String_.TOKEN);
                        var withNumber = cb.declareVoidMethod("r", FUNCTION.token, Integer_.TOKEN);
                        var oneParameter = cb.declareVoidMethod("s", FUNCTION.token);
                        var twoParameters = cb.declareVoidMethod("s", BI_FUNCTION.token);
                        var list = cb.declareVoidMethod("t", new List_<>(String_.TOKEN).token);
                        var listThen = cb.declareVoidMethod("t", CONSUMER.token);
                        var bodies = cb.bodies();
                        bodies.define(function, (b, self, f) -> b.end());
                        bodies.define(unary, (b, self, f) -> b.end());
                        bodies.define(runnable, (b, self, r) -> b.end());
                        bodies.define(calls, (b, self, c) -> b.end());
                        bodies.define(text, (b, self, t) -> b.end());
                        bodies.define(consumer, (b, self, c) -> b.end());
                        bodies.define(unaryFirst, (b, self, f) -> b.end());
                        bodies.define(functionThen, (b, self, f) -> b.end());
                        bodies.define(withText, (b, self, f, t) -> b.end());
                        bodies.define(withNumber, (b, self, f, n) -> b.end());
                        bodies.define(oneParameter, (b, self, f) -> b.end());
                        bodies.define(twoParameters, (b, self, f) -> b.end());
                        bodies.define(list, (b, self, l) -> b.end());
                        bodies.define(listThen, (b, self, c) -> b.end());
                        return bodies;
                    }
                }),
                "public void m(Function<String, String> f) {} public void m(UnaryOperator<String> f) {}"
                        + " public void n(Runnable r) {} public void n(Callable<String> c) {}"
                        + " public void o(String s) {} public void o(Consumer<String> c) {}"
                        + " public void q(UnaryOperator<String> f) {} public void q(Function<String, String> f) {}"
                        + " public void r(Function<String, String> f, String t) {}"
                        + " public void r(Function<String, String> f, Integer n) {}"
                        + " public void s(Function<String, String> f) {}"
                        + " public void s(BiFunction<String, String, String> f) {}"
                        + " public void t(List<String> l) {} public void t(Consumer<String> c) {}");
    }

    @Test
    void aMethodOfAFunctionalInterfaceIsNotDeclaredBesideAnInheritedOneOfAnother() {
        // apply(Function) of Applying, apply(Consumer) of the class
        JavacVerdict.classAsJavacWarns(
                IllegalArgumentException.class,
                () -> subclass("apply", CONSUMER.token),
                "is potentially ambiguous with apply(java.util.function.Function)",
                "extends Applying",
                "public void apply(java.util.function.Consumer<String> c) {}",
                "is potentially ambiguous with");
        // take(int) of Applying is no functional interface
        subclass("take", CONSUMER.token);
        // pair(Function, int) of Applying, pair(Function, String) of the class: one interface, and no other
        subclass("pair", FUNCTION.token, String_.TOKEN);
    }

    @Test
    void anInheritedMethodIsHeldToTakeAFunctionalInterfaceWhereItsErasureIsAClass() {
        // the erasure alone is known of an inherited parameter: String is held to be one, which javac is not
        unlikeJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                var cb = h.extendsObject().members();
                                cb.declareVoidMethod("equals", FUNCTION.token);
                                throw new AssertionError("a pair held to be ambiguous was declared");
                            }
                        }),
                "is potentially ambiguous with equals(java.lang.Object)",
                "public void equals(Function<String, String> f) {}");
    }

    private static <A> JavaFile subclass(String name, TypeToken<A> param) {
        return TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Applying_.extend(h.self())).members();
                        var ctor = cb.declareConstructor();
                        var m = cb.declareVoidMethod(name, param);
                        var bodies = cb.bodies(ctor, c -> c.super_(Applying_.new_, (b, self) -> b.end()));
                        bodies.define(m, (b, self, c) -> b.end());
                        return bodies;
                    }
                });
    }

    private static <A1, A2> void subclass(String name, TypeToken<A1> first, TypeToken<A2> second) {
        CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Applying_.extend(h.self())).members();
                        var ctor = cb.declareConstructor();
                        var m = cb.declareVoidMethod(name, first, second);
                        var bodies = cb.bodies(ctor, c -> c.super_(Applying_.new_, (b, self) -> b.end()));
                        bodies.define(m, (b, self, x, y) -> b.end());
                        return bodies;
                    }
                }));
    }
}
