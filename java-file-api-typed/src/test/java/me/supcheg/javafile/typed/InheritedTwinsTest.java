package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.PairedDefaults_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.PairedSink_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Paired_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// Two methods one generic type declares are two methods, though the type arguments of a supertype make
/// them of one signature: `put(T)` and `put(String)` of a `Paired<String>`. A class has both and neither
/// overrides the other, so what it is without a method of its own is what javac makes of two methods
/// of one signature (JLS 8.4.8.4) — through its superclass, an interface, the bounds of a type parameter.
class InheritedTwinsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Paired_<String> PAIRED = new Paired_<>(String_.TOKEN);
    private static final PairedSink_<String> SINK = new PairedSink_<>(String_.TOKEN);
    private static final PairedDefaults_<String> DEFAULTS = new PairedDefaults_<>(String_.TOKEN);

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aClassDoesNotInheritTwoConcreteMethodsOfOneSignatureFromItsSuperclass() {
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(PAIRED.extend(h.self())).members();
                        var ctor = cb.declareConstructor();
                        return cb.bodies(ctor, c -> c.super_(PAIRED.new_, (b, self) -> b.end()));
                    }
                }),
                "are unrelated methods of the classes above, of one signature in the class",
                "extends Paired<String>",
                "",
                "are inherited with the same signature");
    }

    @Test
    void aClassThatOverridesBothHasOneMethodForThem() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extends_(PAIRED.extend(h.self())).members();
                var ctor = cb.declareConstructor();
                var put = cb.declareOverride(PAIRED.put_String);
                var bodies = cb.bodies(ctor, c -> c.super_(PAIRED.new_, (b, self) -> b.end()));
                bodies.define(put, (b, self, v) -> b.return_(concat(literal("mine:"), v)));
                return bodies;
            }
        }));

        assertThat(compiled.invoke("put", "x")).isEqualTo("mine:x");
    }

    @Test
    void oneMethodOfAClassImplementsTwoAbstractOnesOfOneSignature() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var put = h.overriding(SINK.put_String);
                var oh = h.extendsObject();
                oh.implements_(SINK.implement(h.self()).put_String(put).put_T(put));
                var bodies = oh.members().bodies();
                bodies.define(put.method(), (b, self, v) -> b.return_(concat(literal("mine:"), v)));
                return bodies;
            }
        }));

        assertThat(compiled.invoke("put", "x")).isEqualTo("mine:x");
    }

    @Test
    void aClassDoesNotImplementAnInterfaceThatGivesOneSignatureTwoBodies() {
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var oh = h.extendsObject();
                        oh.implements_(DEFAULTS.implement(h.self()));
                        return oh.members().bodies();
                    }
                }),
                "are unrelated methods with a body each",
                "implements PairedDefaults<String>",
                "",
                "inherits unrelated defaults for put(java.lang.String)");
    }

    @Test
    void theBoundsOfATypeParameterDoNotGiveOneSignatureTwoBodies() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Runnable_.TOKEN),
                        new TypedJavaFile.TypedGenericClassSpec1<Runnable>() {
                            @Override
                            public <Self, T extends Runnable> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h, TypeVarToken<T> t) {
                                h.bound(t, DEFAULTS.token);
                                return h.extendsObject().members().bodies();
                            }
                        }),
                "are unrelated methods with a body each",
                "<T extends Runnable & PairedDefaults<String>>",
                "",
                "inherits unrelated defaults for put(java.lang.String)");
    }

    @Test
    void theBoundsOfATypeParameterMayHaveTwoAbstractMethodsOfOneSignature() {
        JavacVerdict.asJavacAccepts(
                () -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Runnable_.TOKEN),
                        new TypedJavaFile.TypedGenericClassSpec1<Runnable>() {
                            @Override
                            public <Self, T extends Runnable> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h, TypeVarToken<T> t) {
                                h.bound(t, SINK.token);
                                return h.extendsObject().members().bodies();
                            }
                        }),
                "static <T extends Runnable & me.supcheg.javafile.typed.fixtures.PairedSink<String>> void m() {}");
    }
}
