package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Mid;
import me.supcheg.javafile.typed.fixtures.Ranked;
import me.supcheg.javafile.typed.fixtures.Tagged;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Rival_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tagged_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// A value of a type parameter has the methods of its bounds (JLS 4.9), and a member of a bound is reached
/// on it as it is, where no other bound has a method of its signature: the cast to the owner that tells
/// such a twin apart is one javac warns of as unchecked where the variable has several bounds and the
/// owner is parameterized, and is refused there.
class MultipleBoundsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Comparable_<String> COMPARABLE = new Comparable_<>(String_.TOKEN);

    @Test
    void aMethodOfABoundIsCalledOnAValueOfAVariableOfSeveralBoundsWithoutACast() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", COMPARABLE.token),
                new TypedJavaFile.TypedGenericClassSpec1<Comparable<String>>() {
                    @Override
                    public <Self, T extends Comparable<String>> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h, TypeVarToken<T> t) {
                        var asRunnable = h.bound(t, Runnable_.TOKEN);
                        var cb = h.extendsObject().members();
                        var rank = cb.declareMethod("rank", PrimitiveToken.INT, String_.TOKEN, t);
                        var go = cb.declareVoidMethod("go", t);
                        var bodies = cb.bodies();
                        bodies.define(rank, (b, self, x, y) -> b.return_(call(y, COMPARABLE.compareTo_T, x)));
                        bodies.define(
                                go,
                                (b, self, y) -> b.exec(voidCall(asRunnable.apply(y), Runnable_.run))
                                        .end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("return v1.compareTo(v0);").contains("v0.run();");
        Ranked ranked = new Ranked();
        assertThat(compiled.invoke("rank", "abc", ranked)).isEqualTo(3);
        compiled.invoke("go", ranked);
        assertThat(ranked.runs).isEqualTo(1);
    }

    @Test
    void aMethodOfABoundIsCalledOnAValueOfAVariableOfOneBoundWithoutACast() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", COMPARABLE.token),
                new TypedJavaFile.TypedGenericClassSpec1<Comparable<String>>() {
                    @Override
                    public <Self, T extends Comparable<String>> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h, TypeVarToken<T> t) {
                        var cb = h.extendsObject().members();
                        var rank = cb.declareMethod("rank", PrimitiveToken.INT, String_.TOKEN, t);
                        var bodies = cb.bodies();
                        bodies.define(rank, (b, self, x, y) -> b.return_(call(y, COMPARABLE.compareTo_T, x)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("return v1.compareTo(v0);");
        assertThat(compiled.invoke("rank", "abcd", new Ranked())).isEqualTo(4);
    }

    @Test
    void aMethodTwoBoundsHaveUnderOneSignatureIsNotCalled() {
        // compareTo(String) of Comparable<String> and of Rival: javac does not tell them apart, and the
        // cast to Comparable<String> that would is unchecked
        JavacVerdict.asJavac(
                () -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", COMPARABLE.token),
                        new TypedJavaFile.TypedGenericClassSpec1<Comparable<String>>() {
                            @Override
                            public <Self, T extends Comparable<String>> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h, TypeVarToken<T> t) {
                                h.bound(t, Rival_.TOKEN);
                                var cb = h.extendsObject().members();
                                var rank = cb.declareMethod("rank", PrimitiveToken.INT, String_.TOKEN, t);
                                var bodies = cb.bodies();
                                bodies.define(rank, (b, self, x, y) -> b.return_(call(y, COMPARABLE.compareTo_T, x)));
                                return bodies;
                            }
                        }),
                "is reached through a value of the type parameter T, of several bounds, which would be cast to"
                        + " java.lang.Comparable<java.lang.String> for it",
                "interface Rival { int compareTo(String other); }"
                        + " static <U extends Comparable<String> & Rival> int rank(String x, U y) {"
                        + " return y.compareTo(x); }",
                "reference to compareTo is ambiguous");
        JavacVerdict.javacWarns(
                "interface Rival { int compareTo(String other); }"
                        + " static <U extends Comparable<String> & Rival> int rank(String x, U y) {"
                        + " return ((Comparable<String>) y).compareTo(x); }",
                "unchecked cast");
    }

    @Test
    void aFieldOfAParameterizedBoundIsNotReachedOnAValueOfAVariableOfSeveralBounds() {
        Tagged_<String> tagged = new Tagged_<>(String_.TOKEN);
        JavacVerdict.unlikeJavac(
                () -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", tagged.token),
                        new TypedJavaFile.TypedGenericClassSpec1<Tagged<String>>() {
                            @Override
                            public <Self, T extends Tagged<String>> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h, TypeVarToken<T> t) {
                                h.bound(t, Runnable_.TOKEN);
                                var cb = h.extendsObject().members();
                                var tag = cb.declareMethod("tag", String_.TOKEN, t);
                                var bodies = cb.bodies();
                                bodies.define(tag, (b, self, y) -> b.return_(field(y, tagged.tag)));
                                return bodies;
                            }
                        }),
                "bind the value to a variable of me.supcheg.javafile.typed.fixtures.Tagged<java.lang.String> first",
                "static class Tagged<T> { public T tag; }"
                        + " static <U extends Tagged<String> & Runnable> String tag(U y) { return y.tag; }");
        JavacVerdict.javacWarns(
                "static class Tagged<T> { public T tag; }"
                        + " static <U extends Tagged<String> & Runnable> String tag(U y) {"
                        + " return ((Tagged<String>) y).tag; }",
                "unchecked cast");
    }

    @Test
    void aMethodOfASupertypeOfTheBoundIsCalledByItsFact() throws Throwable {
        // pick(Object) of Base on a T extends Mid: Mid has pick(String) beside it, which the cast argument rules out
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", Mid_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<Mid>() {
                    @Override
                    public <Self, T extends Mid> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var cb = h.extendsObject().members();
                        var picked = cb.declareMethod("picked", String_.TOKEN, t);
                        var go = cb.declareVoidMethod("go", t);
                        var bodies = cb.bodies();
                        bodies.define(picked, (b, self, y) -> b.return_(call(y, Base_.pick_Object, literal("x"))));
                        // run() of Runnable, which Mid implements
                        bodies.define(
                                go,
                                (b, self, y) ->
                                        b.exec(voidCall(y, Runnable_.run)).end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("return v0.pick((Object) \"x\");");
        assertThat(compiled.source()).contains("v0.run();");
        Mid mid = new Mid();
        assertThat(compiled.invoke("picked", mid)).isEqualTo("object");
        compiled.invoke("go", mid);
        assertThat(mid.runs).isEqualTo(1);
    }

    @Test
    void aFieldOfAParameterizedBoundIsReachedOnAValueOfAVariableOfOneBound() throws Throwable {
        Tagged_<String> tagged = new Tagged_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", tagged.token),
                new TypedJavaFile.TypedGenericClassSpec1<Tagged<String>>() {
                    @Override
                    public <Self, T extends Tagged<String>> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h, TypeVarToken<T> t) {
                        var cb = h.extendsObject().members();
                        var tag = cb.declareMethod("tag", String_.TOKEN, t);
                        var bodies = cb.bodies();
                        bodies.define(tag, (b, self, y) -> b.return_(field(y, tagged.tag)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("return ((Tagged<String>) v0).tag;");
        Tagged<String> value = new Tagged<>();
        value.tag = "t";
        assertThat(compiled.invoke("tag", value)).isEqualTo("t");
    }
}
