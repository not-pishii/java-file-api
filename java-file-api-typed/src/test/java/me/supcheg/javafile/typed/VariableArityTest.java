package me.supcheg.javafile.typed;

import com.google.testing.compile.CompilationSubject;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Joiner;
import me.supcheg.javafile.typed.fixtures.Scattering;
import me.supcheg.javafile.typed.fixtures.Spreading;
import me.supcheg.javafile.typed.fixtures.Taking;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.Objects_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Gathered_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Joiner_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Scattering_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Spreading_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Taking_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.JavacVerdict.compiledClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A method of variable arity is overridden by one of variable arity (JLS
/// 8.4.1): the last parameter of the override is written `T... v`, and is
/// the array `T[]` to its body and to its fact. What javac warns of is
/// rejected — an override beside a method of the signature that takes the
/// array, an element type that is not reifiable. The rendered classes are
/// compiled under `-Xlint:all` and run.
class VariableArityTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aDefaultMethodOfVariableArityIsOverriddenByOne() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var oh = h.extendsObject();
                Upcast<Self, Joiner> asJoiner = oh.implements_(Joiner_.implement(h.self()));
                var cb = oh.members();
                var constructor = cb.declareConstructor();
                var join = cb.declareOverride(asJoiner, Joiner_.join_StringArray);
                var bodies = cb.bodies();
                bodies.define(join, (b, self, parts) -> b.return_(literal("mine")));
                bodies.define(constructor, (b, self) -> b.end());
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("@Override\n    public String join(String... v0) {");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(((Joiner) probe).join("a", "b")).isEqualTo("mine");
    }

    @Test
    void anAbstractMethodOfVariableArityIsImplementedAndCalledByItsFactWithAnArray() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var spread = h.overriding(Spreading_.spread_StringArray);
                var oh = h.extendsObject();
                oh.implements_(Spreading_.implement(h.self()).spread_StringArray(spread));
                var cb = oh.members();
                var constructor = cb.declareConstructor();
                var go = cb.declareMethod("go", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(spread.method(), (b, self, parts) -> b.return_(literal("spread")));
                bodies.define(constructor, (b, self) -> b.end());
                bodies.define(
                        go,
                        (b, self) -> b.return_(
                                call(self, spread.method(), newArray(ArrayToken.of(String_.TOKEN), literal(2)))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("@Override\n    public String spread(String... v0) {")
                .contains("return this.spread(new String[2]);");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(((Spreading) probe).spread()).isEqualTo("spread");
        assertThat(compiled.invoke("go")).isEqualTo("spread");
    }

    @Test
    void aMethodOfVariableArityOfAnElementTypeThatIsNotReifiableIsNotOverridden() {
        // int scatter(T... all) of Scattering<T>, with List<String> for T: javac warns of heap pollution where
        // the override is declared
        Scattering_<List<String>> scattering = new Scattering_<>(new List_<>(String_.TOKEN).token);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var oh = h.extendsObject();
                        Upcast<Self, Scattering<List<String>>> asScattering =
                                oh.implements_(scattering.implement(h.self()));
                        var cb = oh.members();
                        var scatter = cb.declareOverride(asScattering, scattering.scatter_TArray);
                        var bodies = cb.bodies();
                        bodies.define(scatter, (b, self, all) -> b.return_(literal(0)));
                        return bodies;
                    }
                }))
                .withMessageContaining("of variable arity and of the element type")
                .withMessageContaining("possible heap pollution");
        CompilationSubject.assertThat(compiledClass(
                        "implements Scattering<List<String>>",
                        "@Override public int scatter(List<String>... all) { return 0; }"))
                .hadWarningContaining("Possible heap pollution from parameterized vararg type");
    }

    @Test
    void aMethodOfVariableArityOfATypeParameterIsOverriddenWhereItsArgumentIsReifiable() throws Throwable {
        // int scatter(T... all) of Scattering<T>, with String for T
        Scattering_<String> scattering = new Scattering_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var oh = h.extendsObject();
                Upcast<Self, Scattering<String>> asScattering = oh.implements_(scattering.implement(h.self()));
                var cb = oh.members();
                var constructor = cb.declareConstructor();
                var scatter = cb.declareOverride(asScattering, scattering.scatter_TArray);
                var bodies = cb.bodies();
                bodies.define(scatter, (b, self, all) -> b.return_(literal(7)));
                bodies.define(constructor, (b, self) -> b.end());
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("@Override\n    public int scatter(String... v0) {");
        @SuppressWarnings("unchecked")
        Scattering<String> probe =
                (Scattering<String>) compiled.generated().getConstructor().newInstance();
        assertThat(probe.scatter("a", "b")).isEqualTo(7);
    }

    @Test
    void anArrayOfASubtypeGivenForTheParameterOfVariableArityIsCastToItsType() throws Throwable {
        // Objects.hash(Object...) with a String[]: javac warns of the call it cannot tell the kind of
        JavacVerdict.javacWarns(
                "static int m() { return Objects.hash(new String[1]); }",
                "non-varargs call of varargs method with inexact argument type for last parameter");
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var m = cb.declareStaticMethod("m", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(
                        m,
                        b -> b.return_(staticCall(
                                Objects_.hash_ObjectArray, newArray(ArrayToken.of(String_.TOKEN), literal(1)))));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("return Objects.hash((Object[]) new String[1]);");
        assertThat(compiled.invoke("m")).isEqualTo(java.util.Objects.hash(new Object[1]));
    }

    @Test
    void aMethodOfVariableArityAndOneThatTakesTheArrayAreNotOverriddenTogether() {
        // String join(String...) of Joiner, String join(String[]) of Taking
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var oh = h.extendsObject();
                        Upcast<Self, Joiner> asJoiner = oh.implements_(Joiner_.implement(h.self()));
                        Upcast<Self, Taking> asTaking = oh.implements_(Taking_.implement(h.self()));
                        assertThat(asTaking.supertype()).isEqualTo(Taking_.TOKEN);
                        var cb = oh.members();
                        var join = cb.declareOverride(asJoiner, Joiner_.join_StringArray);
                        var bodies = cb.bodies();
                        bodies.define(join, (b, self, parts) -> b.return_(literal("")));
                        return bodies;
                    }
                }))
                .withMessageContaining("cannot override both")
                .withMessageContaining("the last parameter of one is of variable arity and that of the other an array");
        CompilationSubject.assertThat(compiledClass(
                        "implements Joiner, Taking", "@Override public String join(String... parts) { return \"\"; }"))
                .hadWarningContaining("overridden method has no '...'");
    }

    @Test
    void aMethodOfTheSuperclassThatTakesTheArrayDoesNotStandForOneOfVariableArity() {
        // String join(String[]) of Gathered beside the default String join(String...) of Joiner: javac warns
        assertThatIllegalStateException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var sh = h.extends_(Gathered_.extend(h.self()));
                        sh.implements_(Joiner_.implement(h.self()));
                        var cb = sh.members();
                        var constructor = cb.declareConstructor();
                        return cb.bodies(constructor, ctor -> ctor.super_(Gathered_.new_, (b, self) -> b.end()));
                    }
                }))
                .withMessageContaining("cannot stand for join(java.lang.String[]) of"
                        + " me.supcheg.javafile.typed.fixtures.Joiner: the last parameter of one is of variable arity");
        CompilationSubject.assertThat(compiledClass("extends Gathered implements Joiner", ""))
                .hadWarningContaining("overriding method is missing '...'");
    }
}
