package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.SubTwin;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.BaseTwin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.SubTwin_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static me.supcheg.javafile.typed.Subclasses.began;
import static org.assertj.core.api.Assertions.assertThat;

/// A superclass with overloads that erase alike under its type arguments
/// (§8e): `SubTwin<String>` has `m(T)` of `BaseTwin` and its own `m(String)`,
/// `p` likewise, and the generic `<U> g(U)` beside which the class declares a
/// `g(String)`. No cast of `super` or of a receiver the class reaches a
/// `protected` member through tells such a pair apart, so the calls are
/// rejected when the body is lowered — held here against javac on a real
/// superclass with its metamodel, where [OverloadTwinsTest] goes by shapes.
class SuperTwinsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final BaseTwin_<String> BASE = new BaseTwin_<>(String_.TOKEN);
    private static final SubTwin_<String> SUB = new SubTwin_<>(String_.TOKEN);

    /// The overrides without which the class would have each pair from two classes, see
    /// [#aClassThatOverridesNeitherOfAPairIsRejected].
    private static final String OVERRIDES = "@Override public String m(String v) { return \"mine:\" + v; }"
            + " @Override protected String p(String v) { return \"mine:\" + v; }";

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    /// The members of a class that extends `SubTwin<String>` and overrides both pairs.
    private static <Self> TypedSubclassBuilder<Self, SubTwin<String>> overriding(ClassHeader<Self> h) {
        var cb = began(h.extends_(SUB.extend(h.self())), ctor -> ctor.super_(SUB.new_, (b, self) -> b.end()));
        cb.override(BASE.m_T, (b, self, v) -> b.return_(concat(literal("mine:"), v)));
        cb.override(cb.protected_(BASE.p_T), (b, self, v) -> b.return_(concat(literal("mine:"), v)));
        return cb;
    }

    @Test
    void superDoesNotCallAMethodTheSuperclassHasATwinOf() {
        // m(T) of BaseTwin, by the fact of BaseTwin: SubTwin<String> adds m(String)
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = overriding(h);
                        cb.method(
                                "f", String_.TOKEN, (b, self) -> b.return_(cb.superCall(self, BASE.m_T, literal("x"))));
                    }
                }),
                "super.m(...) cannot name",
                "extends SubTwin<String>",
                OVERRIDES + " public String f() { return super.m(\"x\"); }",
                "reference to m is ambiguous");
        // and by a fact of SubTwin itself, whose two methods no call tells apart
        classAsJavac(
                FactLookupException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = overriding(h);
                        cb.method(
                                "f",
                                String_.TOKEN,
                                (b, self) -> b.return_(cb.superCall(self, SUB.m_String, literal("x"))));
                    }
                }),
                "as the overload declared m(#0) is",
                "extends SubTwin<String>",
                OVERRIDES + " public String f() { return super.m(\"x\"); }",
                "reference to m is ambiguous");
        // a protected one: super.p("x")
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = overriding(h);
                        var p = cb.protected_(BASE.p_T);
                        cb.method("f", String_.TOKEN, (b, self) -> b.return_(cb.superCall(self, p, literal("x"))));
                    }
                }),
                "super.p(...) cannot name",
                "extends SubTwin<String>",
                OVERRIDES + " public String f() { return super.p(\"x\"); }",
                "reference to p is ambiguous");
    }

    @Test
    void theOneMethodOfTheClassIsCalledByTheFactOfEitherOfThePair() throws Throwable {
        // the class overrides the pair by one method, and this.m("x") is that method to javac
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var cb = overriding(h);
                var p = cb.protected_(BASE.p_T);
                cb.method(
                        "f",
                        String_.TOKEN,
                        (b, self) -> b.return_(call(cb.asSuper().apply(self), p, literal("x"))));
                cb.method(
                        "g",
                        String_.TOKEN,
                        (b, self) -> b.return_(call(cb.asSuper().apply(self), BASE.m_T, literal("x"))));
            }
        }));

        assertThat(compiled.source()).contains("return this.p(\"x\");").contains("return this.m(\"x\");");
        assertThat(compiled.invoke("f")).isEqualTo("mine:x");
        assertThat(compiled.invoke("g")).isEqualTo("mine:x");
    }

    @Test
    void aClassThatOverridesNeitherOfAPairIsRejected() {
        // as javac: a method two classes of the superclass chain give a body each is overridden
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        began(h.extends_(SUB.extend(h.self())), ctor -> ctor.super_(SUB.new_, (b, self) -> b.end()));
                    }
                }),
                "unrelated methods with a body each, and overrides neither",
                "extends SubTwin<String>",
                "",
                "are inherited with the same signature");
    }

    @Test
    void aProtectedMethodIsNotCalledOnTheClassThatHasATwinOfIt() throws Throwable {
        // <U> String g(U) of BaseTwin with String for U, and g(String) the class declares: javac
        // takes this.<String>g("x") for the method of the class, which is not generic — silently
        classUnlikeJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = overriding(h);
                        cb.method("g", String_.TOKEN, String_.TOKEN, (b, self, v) -> b.return_(literal("own")));
                        var g = cb.protected_(BASE.g_U(String_.TOKEN));
                        cb.method(
                                "f",
                                String_.TOKEN,
                                (b, self) -> b.return_(call(cb.asSuper().apply(self), g, literal("x"))));
                    }
                }),
                "the class has another method that erases as it does, which javac would take for it — a"
                        + " generic method of a superclass is not called in a class that has such an overload",
                "extends SubTwin<String>",
                OVERRIDES + " public String g(String v) { return \"own\"; }"
                        + " public String f() { return this.<String>g(\"x\"); }");

        // without the overload it is the method of the superclass
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var cb = overriding(h);
                var g = cb.protected_(BASE.g_U(String_.TOKEN));
                cb.method(
                        "f",
                        String_.TOKEN,
                        (b, self) -> b.return_(call(cb.asSuper().apply(self), g, literal("x"))));
            }
        }));

        assertThat(compiled.source()).contains("return this.<String>g(\"x\");");
        assertThat(compiled.invoke("f")).isEqualTo("generic:x");
    }

    @Test
    void superDoesNotCallAGenericMethod() {
        // stricter than javac: the heritage of the superclass tells a generic method by its type
        // parameters, and the typed layer does not hold it against a fact of type arguments
        classUnlikeJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = overriding(h);
                        var g = cb.protected_(BASE.g_U(String_.TOKEN));
                        cb.method("f", String_.TOKEN, (b, self) -> b.return_(cb.superCall(self, g, literal("x"))));
                    }
                }),
                "the method is generic, and a generic method of a superclass is neither called as super.g(...)",
                "extends SubTwin<String>",
                OVERRIDES + " public String f() { return super.<String>g(\"x\"); }");
    }
}
