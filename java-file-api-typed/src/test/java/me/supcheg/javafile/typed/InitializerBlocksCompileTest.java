package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.StringBuilder_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Set;

import static me.supcheg.javafile.typed.ExpressionArities.new_;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// Initializer blocks of the typed declarations of §6.5: an instance block runs before every constructor,
/// after its superclass constructor, in source order among the field initializers; a static block runs once,
/// when the type is initialized. Each is rendered, compiled under `-Xlint:all` and run.
class InitializerBlocksCompileTest {

    private static final ClassDesc ORDER = ClassDesc.of("me.supcheg.example", "Order");
    private static final ClassDesc PALETTE = ClassDesc.of("me.supcheg.example", "Palette");
    private static final ClassDesc ORIGIN = ClassDesc.of("me.supcheg.example", "Origin");
    private static final ClassDesc OUTER = ClassDesc.of("me.supcheg.example", "Outer");

    @Test
    void aClassRunsItsInstanceBlocksBeforeEachConstructorAndItsStaticBlockOnce() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), ORDER, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var staticTrace = cb.declareStaticField("STATIC_TRACE", StringBuilder_.TOKEN);
                        var trace = cb.declareField("trace", StringBuilder_.TOKEN);
                        var seen = cb.declareMutableField("seen", PrimitiveToken.INT);
                        var before = cb.declareField("before", PrimitiveToken.INT);
                        var staticBlock = cb.declareStaticInitializerBlock();
                        var first = cb.declareInitializerBlock();
                        var after = cb.declareField("after", PrimitiveToken.INT);
                        var second = cb.declareInitializerBlock();
                        var zero = cb.declareConstructor();
                        var one = cb.declareConstructor(PrimitiveToken.INT);
                        var traceText = cb.declareMethod("traceText", String_.TOKEN);
                        var seenText = cb.declareMethod("seen", PrimitiveToken.INT);
                        var staticText = cb.declareStaticMethod("staticText", String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.initialize(staticTrace, new_(StringBuilder_.new_));
                        bodies.initialize(trace, new_(StringBuilder_.new_));
                        bodies.initialize(seen, literal(0));
                        bodies.initialize(before, literal(1));
                        bodies.initialize(after, literal(2));
                        bodies.define(
                                staticBlock,
                                b -> b.exec(staticField(staticTrace).call(StringBuilder_.append_String, literal("S;")))
                                        .end());
                        bodies.define(
                                first,
                                (b, self) -> b.exec(self.field(trace).call(StringBuilder_.append_String, literal("A")))
                                        .exec(assignField(self, seen, self.field(before)))
                                        .end());
                        bodies.define(
                                second,
                                (b, self) -> b.exec(self.field(trace).call(StringBuilder_.append_String, literal("B")))
                                        .end());
                        bodies.define(
                                zero,
                                (b, self) -> b.exec(self.field(trace).call(StringBuilder_.append_String, literal("C")))
                                        .end());
                        bodies.define(
                                one,
                                (b, self, n) -> b.exec(
                                                self.field(trace).call(StringBuilder_.append_String, literal("C")))
                                        .end());
                        bodies.define(
                                traceText,
                                (b, self) -> b.return_(self.field(trace).call(StringBuilder_.toString)));
                        bodies.define(seenText, (b, self) -> b.return_(self.field(seen)));
                        bodies.define(
                                staticText,
                                b -> b.return_(staticField(staticTrace).call(StringBuilder_.toString)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("public static final StringBuilder STATIC_TRACE = new StringBuilder();")
                .contains("static {")
                .contains("public final int before = 1;")
                .contains("public final int after = 2;");
        assertThat(compiled.invoke("staticText")).isEqualTo("S;");
        Object first = compiled.generated().getConstructor().newInstance();
        Object second = compiled.generated().getConstructor(int.class).newInstance(7);
        assertThat(compiled.generated().getMethod("traceText").invoke(first)).isEqualTo("ABC");
        assertThat(compiled.generated().getMethod("traceText").invoke(second)).isEqualTo("ABC");
        assertThat(compiled.generated().getMethod("seen").invoke(first)).isEqualTo(1);
        assertThat(compiled.invoke("staticText")).isEqualTo("S;");
    }

    @Test
    void anEnumRunsItsInstanceBlockBeforeEachConstructorAndItsStaticBlockOnce() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.enum_(
                UnsafeFacts.unverifiedClasspath(), PALETTE, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant("RED", java.util.List.of(literal(1)));
                        eb.constant("GREEN", java.util.List.of(literal(2)));
                        var staticTrace = eb.declareStaticField("STATIC_TRACE", StringBuilder_.TOKEN);
                        var trace = eb.declareField("trace", StringBuilder_.TOKEN, Set.of(Modifier.PUBLIC));
                        var staticBlock = eb.declareStaticInitializerBlock();
                        var instanceBlock = eb.declareInitializerBlock();
                        var laterStatic = eb.declareStaticField("LATER_STATIC", PrimitiveToken.INT);
                        var laterInstance =
                                eb.declareField("laterInstance", PrimitiveToken.INT, Set.of(Modifier.PUBLIC));
                        eb.constructor(
                                PrimitiveToken.INT,
                                (b, self, value) -> b.exec(
                                                self.field(trace).call(StringBuilder_.append_String, literal("C")))
                                        .end());
                        var traceText = eb.declareMethod("traceText", String_.TOKEN);
                        var staticText = eb.declareStaticMethod("staticText", String_.TOKEN);
                        var bodies = eb.bodies();
                        bodies.initialize(staticTrace, new_(StringBuilder_.new_));
                        bodies.initialize(trace, new_(StringBuilder_.new_));
                        bodies.initialize(laterStatic, literal(3));
                        bodies.initialize(laterInstance, literal(4));
                        bodies.define(
                                traceText,
                                (b, self) -> b.return_(self.field(trace).call(StringBuilder_.toString)));
                        bodies.define(
                                staticText,
                                b -> b.return_(staticField(staticTrace).call(StringBuilder_.toString)));
                        bodies.define(
                                instanceBlock,
                                (b, self) -> b.exec(self.field(trace).call(StringBuilder_.append_String, literal("B")))
                                        .end());
                        bodies.define(
                                staticBlock,
                                b -> b.exec(staticField(staticTrace).call(StringBuilder_.append_String, literal("S")))
                                        .end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("static {").contains("public StringBuilder trace");
        assertThat(compiled.invoke("staticText")).isEqualTo("S");
        Object red = compiled.generated().getField("RED").get(null);
        Object green = compiled.generated().getField("GREEN").get(null);
        assertThat(compiled.generated().getMethod("traceText").invoke(red)).isEqualTo("BC");
        assertThat(compiled.generated().getMethod("traceText").invoke(green)).isEqualTo("BC");
        assertThat(compiled.invoke("staticText")).isEqualTo("S");
    }

    @Test
    void aRecordDeclaresAStaticInitializerBlock() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.record(
                UnsafeFacts.unverifiedClasspath(), ORIGIN, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.component("x", PrimitiveToken.INT);
                        var trace = rb.declareStaticField("TRACE", StringBuilder_.TOKEN);
                        var block = rb.declareStaticInitializerBlock();
                        var compact = rb.declareCompactConstructor(PrimitiveToken.INT);
                        var traceText = rb.declareStaticMethod("traceText", String_.TOKEN);
                        var bodies = rb.bodies();
                        bodies.initialize(trace, new_(StringBuilder_.new_));
                        bodies.define(
                                block,
                                b -> b.exec(staticField(trace).call(StringBuilder_.append_String, literal("S")))
                                        .end());
                        bodies.define(compact, (b, self, value) -> b.end());
                        bodies.define(
                                traceText, b -> b.return_(staticField(trace).call(StringBuilder_.toString)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("public record Origin(int x)").contains("static {");
        assertThat(compiled.invoke("traceText")).isEqualTo("S");
    }

    @Test
    void aMemberClassDeclaresBothInitializerBlocks() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), OUTER, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareNestedClass("Inner", new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <N> MemberBodies<N, ?> build(ClassHeader<N> h) {
                                var builder = h.extendsObject().members();
                                var staticTrace = builder.declareStaticField("STATIC_TRACE", StringBuilder_.TOKEN);
                                var trace = builder.declareField("trace", StringBuilder_.TOKEN);
                                var block = builder.declareInitializerBlock();
                                var staticBlock = builder.declareStaticInitializerBlock();
                                var ctor = builder.declareConstructor();
                                var text = builder.declareMethod("text", String_.TOKEN);
                                var bodies = builder.bodies();
                                bodies.initialize(staticTrace, new_(StringBuilder_.new_));
                                bodies.initialize(trace, new_(StringBuilder_.new_));
                                bodies.define(
                                        block,
                                        (b, self) -> b.exec(self.field(trace)
                                                        .call(StringBuilder_.append_String, literal("B")))
                                                .end());
                                bodies.define(
                                        staticBlock,
                                        b -> b.exec(staticField(staticTrace)
                                                        .call(StringBuilder_.append_String, literal("S")))
                                                .end());
                                bodies.define(
                                        ctor,
                                        (b, self) -> b.exec(self.field(trace)
                                                        .call(StringBuilder_.append_String, literal("C")))
                                                .end());
                                bodies.define(
                                        text,
                                        (b, self) -> b.return_(self.field(trace).call(StringBuilder_.toString)));
                                return bodies;
                            }
                        });
                        return cb.bodies();
                    }
                }));

        assertThat(compiled.source())
                .contains("public static final class Inner")
                .contains("static {");
        Class<?> inner = compiled.loader().loadClass("me.supcheg.example.Outer$Inner");
        Object instance = inner.getConstructor().newInstance();
        assertThat(inner.getMethod("text").invoke(instance)).isEqualTo("BC");
    }
}
