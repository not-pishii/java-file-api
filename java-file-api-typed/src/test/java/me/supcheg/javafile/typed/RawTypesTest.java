package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Rawly_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.JavacVerdict.asJavacWarns;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A fact of a member whose signature is raw has a raw token, and javac warns of code that writes a raw
/// type (`[rawtypes]`): the typed layer writes none — no variable, no field, no parameter of one.
class RawTypesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile declaring(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aVariableOfARawTypeIsNotDeclared() {
        asJavacWarns(
                () -> declaring(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        var m = cb.declareStaticMethod("m", Object_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(m, b -> b.let(Rawly_.raw.result(), staticCall(Rawly_.raw), v -> b.return_(v)));
                        return bodies;
                    }
                }),
                "is the raw type java.util.List: javac warns of it (-Xlint:rawtypes)",
                "static Object m() { List v = me.supcheg.javafile.typed.fixtures.Rawly.raw(); return v; }",
                "found raw type");
        // and one a type argument mentions
        assertThatIllegalStateException()
                .isThrownBy(() -> declaring(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        var m = cb.declareStaticMethod("m", Object_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(
                                m, b -> b.let(Rawly_.nested.result(), staticCall(Rawly_.nested), v -> b.return_(v)));
                        return bodies;
                    }
                }))
                .withMessageContaining("mentions the raw type java.util.List");
    }

    @Test
    void aFieldAndAMethodOfARawTypeAreNotDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> declaring(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.extendsObject().members().declareField("list", Rawly_.raw.result());
                        throw new AssertionError("a field of a raw type was declared");
                    }
                }))
                .withMessageContaining("is the raw type java.util.List");
        assertThatIllegalStateException()
                .isThrownBy(() -> declaring(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Rawly_.extend(h.self())).members();
                        cb.declareOverride(Rawly_.take_List);
                        throw new AssertionError("an override of a raw parameter was declared");
                    }
                }))
                .withMessageContaining("is the raw type java.util.List");
        assertThatIllegalStateException()
                .isThrownBy(() -> declaring(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.extendsObject().members().declareStaticMethod("m", PrimitiveToken.INT, Rawly_.raw.result());
                        throw new AssertionError("a parameter of a raw type was declared");
                    }
                }))
                .withMessageContaining("is the raw type java.util.List");
    }
}
