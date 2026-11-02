package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.io.Serializable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// A class that is `Serializable` by a supertype — an exception among them — declares its serial version
/// and no instance field of a type that is not serializable, of which javac warns (`[serial]`); a class
/// that is not declares no serial version. Each against javac.
class SerialTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void anExceptionDeclaresItsSerialVersion() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extends_(Exception_.extend(h.self())).members();
                cb.declareSerialVersionUID(42L);
                var constructor = cb.declareConstructor();
                var code = cb.declareField("code", PrimitiveToken.INT);
                var name = cb.declareField("name", String_.TOKEN);
                var names = cb.declareField("names", ArrayToken.of(String_.TOKEN));
                var bodies = cb.bodies(constructor, ctor -> ctor.super_(Exception_.new_, (b, self) -> b.end()));
                bodies.initialize(code, literal(7));
                bodies.initialize(name, literal("n"));
                bodies.initialize(names, Expressions.newArray(ArrayToken.of(String_.TOKEN), literal(1)));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("import java.io.Serial;")
                .contains("@Serial\n    private static final long serialVersionUID = 42L;");
        assertThat(java.io.ObjectStreamClass.lookup(compiled.generated()).getSerialVersionUID())
                .isEqualTo(42L);
    }

    @Test
    void anExceptionWithoutItsSerialVersionIsRefused() {
        JavacVerdict.asJavacWarns(
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Exception_.extend(h.self())).members();
                        var constructor = cb.declareConstructor();
                        return cb.bodies(constructor, ctor -> ctor.super_(Exception_.new_, (b, self) -> b.end()));
                    }
                }),
                "class me.supcheg.example.Probe is Serializable, by java.lang.Exception, and declares no serial"
                        + " version",
                "static final class Failure extends Exception {}",
                "serializable class Probe.Failure has no definition of serialVersionUID");
    }

    @Test
    void aClassThatImplementsSerializableDeclaresItsSerialVersion() {
        JavacVerdict.asJavacWarns(
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var oh = h.extendsObject();
                        oh.implements_(Serializable_.implement(h.self()));
                        return oh.members().bodies();
                    }
                }),
                "is Serializable, by java.io.Serializable, and declares no serial version",
                "static final class Kept implements Serializable {}",
                "serializable class Probe.Kept has no definition of serialVersionUID");
    }

    @Test
    void aClassThatIsNotSerializableDeclaresNoSerialVersion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareSerialVersionUID(1L);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("class me.supcheg.example.Probe is not Serializable: no serialization reads"
                        + " its serial version");
    }

    @Test
    void aClassDeclaresItsSerialVersionOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Exception_.extend(h.self())).members();
                        cb.declareSerialVersionUID(1L);
                        cb.declareSerialVersionUID(2L);
                        throw new AssertionError("a second serial version was declared");
                    }
                }))
                .withMessageContaining("declares its serial version already");
    }

    @Test
    void aFieldOfASerializableClassIsOfASerializableType() {
        // Object and List<String> are not Serializable: a serialization of the class cannot write them
        JavacVerdict.asJavacWarns(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Exception_.extend(h.self())).members();
                        cb.declareField("value", Object_.TOKEN);
                        throw new AssertionError("a field of a type that is not serializable was declared");
                    }
                }),
                "field value of class me.supcheg.example.Probe is of java.lang.Object, which is not Serializable",
                "static final class Failure extends Exception { private static final long serialVersionUID = 1L;"
                        + " public final Object value = null; }",
                "non-transient instance field of a serializable class declared with a non-serializable type");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Exception_.extend(h.self())).members();
                        cb.declareMutableField("values", new List_<>(String_.TOKEN).token);
                        throw new AssertionError("a field of a type that is not serializable was declared");
                    }
                }))
                .withMessageContaining("is of java.util.List<java.lang.String>, which is not Serializable");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Exception_.extend(h.self())).members();
                        cb.declareField("values", ArrayToken.of(Object_.TOKEN));
                        throw new AssertionError("a field of a type that is not serializable was declared");
                    }
                }))
                .withMessageContaining("is of java.lang.Object[], which is not Serializable");
    }

    @Test
    void aFieldOfATypeParameterIsSerializableByABound() throws Throwable {
        // <T extends Comparable<String> & Serializable>: javac warns of no field of it
        var comparable = new Comparable_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", comparable.token),
                new GenericDeclarationArities.TypedGenericClassSpec1<Comparable<String>>() {
                    @Override
                    public <Self, T extends Comparable<String>> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h, TypeVarToken<T> t) {
                        h.bound(t, Serializable_.TOKEN);
                        var oh = h.extendsObject();
                        oh.implements_(Serializable_.implement(h.self()));
                        var cb = oh.members();
                        cb.declareSerialVersionUID(1L);
                        var value = cb.declareMutableField("value", t);
                        var bodies = cb.bodies();
                        bodies.initialize(value, Expressions.literalNull(t));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("public T value = (T) null;");
        // of the second type parameter, bounded by String, which is Serializable
        CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("A"),
                typeParam("B", String_.TOKEN),
                new GenericDeclarationArities.TypedGenericClassSpec2<Object, String>() {
                    @Override
                    public <Self, A, B extends String> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h, TypeVarToken<A> a, TypeVarToken<B> b) {
                        var oh = h.extendsObject();
                        oh.implements_(Serializable_.implement(h.self()));
                        var cb = oh.members();
                        cb.declareSerialVersionUID(1L);
                        var value = cb.declareMutableField("value", b);
                        var bodies = cb.bodies();
                        bodies.initialize(value, Expressions.literalNull(b));
                        return bodies;
                    }
                }));
        // and one bounded by what is not serializable alone is refused
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T", Runnable_.TOKEN),
                        new GenericDeclarationArities.TypedGenericClassSpec1<Runnable>() {
                            @Override
                            public <Self, T extends Runnable> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h, TypeVarToken<T> t) {
                                var oh = h.extendsObject();
                                oh.implements_(Serializable_.implement(h.self()));
                                var cb = oh.members();
                                cb.declareMutableField("value", t);
                                throw new AssertionError("a field of a type that is not serializable was declared");
                            }
                        }))
                .withMessageContaining(
                        "field value of class me.supcheg.example.Probe is of T, which is not Serializable");
    }
}
