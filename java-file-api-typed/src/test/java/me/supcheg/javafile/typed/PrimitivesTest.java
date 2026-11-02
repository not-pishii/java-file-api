package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.Objects_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.ExpressionArities.staticCall;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.narrowCheckedLongToInt;
import static me.supcheg.javafile.typed.Expressions.narrowTruncatingLongToInt;
import static me.supcheg.javafile.typed.Expressions.neqRef;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.unbox;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// Primitives are typed by their `Prim` markers (§6.1). What javac cannot
/// reject — a marker is a subtype of `Object` — is checked when the
/// expression is built; what it accepts renders to code that compiles.
class PrimitivesTest {

    private static final String BOX_HINT = "box(PrimitiveToken.INT, expr)";

    @Test
    void primitiveReceiverOfACallIsRejectedWithABoxHint() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> literal(1).call(Object_.toString))
                .withMessageContaining("primitive type int")
                .withMessageContaining(BOX_HINT);
    }

    @Test
    void primitiveReceiverOfAVoidCallIsRejected() {
        VoidMethodRef0<Object> notify = UnsafeInvocables.voidMethod(Object_.TOKEN, "notify", MemberTraits.DEFAULT);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> literal(true).voidCall(notify))
                .withMessageContaining("primitive type boolean");
    }

    @Test
    void primitiveReceiverOfAFieldIsRejected() {
        FieldRef<Object, String> read = UnsafeFacts.field(Object_.TOKEN, "name", String_.TOKEN);
        MutableFieldRef<Object, String> write = UnsafeFacts.mutableField(Object_.TOKEN, "name", String_.TOKEN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> literal(1L).field(read))
                .withMessageContaining("primitive type long");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> assignField(literal(1.0), write, literal("x")))
                .withMessageContaining("primitive type double");
    }

    @Test
    void boxedReceiverIsAccepted() {
        box(PrimitiveToken.INT, literal(1)).call(Object_.toString);
    }

    @Test
    void referenceComparisonOfPrimitivesIsRejected() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> eqRef(literal(1), literal(2)))
                .withMessageContaining("eqInt");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> neqRef(literalNull(Object_.TOKEN), literal(2)))
                .withMessageContaining("primitive type int");
    }

    @Test
    void primitivesRenderExplicitlyAndCompile() {
        ArrayToken<int[], Prim.Int> ints = PrimitiveToken.INT.array();
        ArrayToken<String[], String> strings = ArrayToken.of(String_.TOKEN);

        JavaFile file = TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Primitives"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var sameAsObjects = cb.declareStaticMethod("sameAsObjects", PrimitiveToken.BOOLEAN);
                        var boxedMethod = cb.declareStaticMethod("boxed", Integer_.TOKEN);
                        var unboxed = cb.declareStaticMethod("unboxed", PrimitiveToken.INT, Integer_.TOKEN);
                        var intArray = cb.declareStaticMethod("intArray", PrimitiveToken.INT);
                        var stringArray = cb.declareStaticMethod("stringArray", PrimitiveToken.INT);
                        var checked = cb.declareStaticMethod("checked", PrimitiveToken.INT);
                        var truncated = cb.declareStaticMethod("truncated", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                sameAsObjects,
                                b -> b.return_(staticCall(
                                        Objects_.equals_Object_Object,
                                        box(PrimitiveToken.INT, literal(1)),
                                        box(PrimitiveToken.LONG, literal(1L)))));
                        bodies.define(boxedMethod, b -> b.return_(box(PrimitiveToken.INT, literal(1))));
                        bodies.define(unboxed, (b, boxed) -> b.return_(unbox(PrimitiveToken.INT, boxed)));
                        bodies.define(
                                intArray,
                                b -> b.let(
                                        ints,
                                        newArray(ints, literal(3)),
                                        array -> b.exec(assignAt(ints, array, literal(0), literal(7)))
                                                .return_(addInt(
                                                        array.arrayAccess(ints, literal(0)), array.length(ints)))));
                        bodies.define(
                                stringArray,
                                b -> b.let(
                                        strings,
                                        newArray(strings, literal(2)),
                                        array -> b.exec(assignAt(strings, array, literal(1), literal("abc")))
                                                .return_(array.arrayAccess(strings, literal(1))
                                                        .call(String_.length))));
                        bodies.define(checked, b -> b.return_(narrowCheckedLongToInt(literal(5L))));
                        bodies.define(truncated, b -> b.return_(narrowTruncatingLongToInt(literal(5L))));
                        return bodies;
                    }
                });

        String source = file.render();
        assertThat(source)
                .contains("Objects.equals((Object) Integer.valueOf(1), (Object) Long.valueOf(1L))")
                .contains("Integer.valueOf(1)")
                .contains(".intValue()")
                .contains("new int[3]")
                .contains(".length")
                .contains("new String[2]")
                .contains("Math.toIntExact(5L)")
                .contains("(int) 5L");
        RenderedJava.assertCompilesCleanly(file);
    }
}
