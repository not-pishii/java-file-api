package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.jdk.Integer_;
import me.supcheg.javafile.facts.jdk.Object_;
import me.supcheg.javafile.facts.jdk.Objects_;
import me.supcheg.javafile.facts.jdk.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.at;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.length;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.narrowCheckedLongToInt;
import static me.supcheg.javafile.typed.Expressions.narrowTruncatingLongToInt;
import static me.supcheg.javafile.typed.Expressions.neqRef;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.unbox;
import static me.supcheg.javafile.typed.Expressions.voidCall;
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
                .isThrownBy(() -> call(literal(1), Object_.toString))
                .withMessageContaining("primitive type int")
                .withMessageContaining(BOX_HINT);
    }

    @Test
    void primitiveReceiverOfAVoidCallIsRejected() {
        VoidMethodRef0<Object> notify = UnsafeFacts.voidMethod(Object_.TOKEN, "notify", MemberTraits.DEFAULT);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> voidCall(literal(true), notify))
                .withMessageContaining("primitive type boolean");
    }

    @Test
    void primitiveReceiverOfAFieldIsRejected() {
        FieldRef<Object, String> read = UnsafeFacts.field(Object_.TOKEN, "name", String_.TOKEN);
        MutableFieldRef<Object, String> write = UnsafeFacts.mutableField(Object_.TOKEN, "name", String_.TOKEN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> field(literal(1L), read))
                .withMessageContaining("primitive type long");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> assignField(literal(1.0), write, literal("x")))
                .withMessageContaining("primitive type double");
    }

    @Test
    void boxedReceiverIsAccepted() {
        call(box(PrimitiveToken.INT, literal(1)), Object_.toString);
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
                ClassDesc.of("me.supcheg.example", "Primitives"), new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        cb.method(
                                "sameAsObjects",
                                PrimitiveToken.BOOLEAN,
                                b -> b.return_(staticCall(Objects_.equals, literal(1), literal(1L))));
                        cb.method("boxed", Integer_.TOKEN, b -> b.return_(box(PrimitiveToken.INT, literal(1))));
                        cb.method(
                                "unboxed",
                                PrimitiveToken.INT,
                                Integer_.TOKEN,
                                (b, boxed) -> b.return_(unbox(PrimitiveToken.INT, boxed)));
                        cb.method(
                                "intArray",
                                PrimitiveToken.INT,
                                b -> b.let(
                                        ints,
                                        newArray(ints, literal(3)),
                                        array -> b.exec(assignAt(ints, array, literal(0), literal(7)))
                                                .return_(addInt(at(ints, array, literal(0)), length(ints, array)))));
                        cb.method(
                                "stringArray",
                                PrimitiveToken.INT,
                                b -> b.let(
                                        strings,
                                        newArray(strings, literal(2)),
                                        array -> b.exec(assignAt(strings, array, literal(1), literal("abc")))
                                                .return_(call(at(strings, array, literal(1)), String_.length))));
                        cb.method("checked", PrimitiveToken.INT, b -> b.return_(narrowCheckedLongToInt(literal(5L))));
                        cb.method(
                                "truncated",
                                PrimitiveToken.INT,
                                b -> b.return_(narrowTruncatingLongToInt(literal(5L))));
                    }
                });

        String source = file.render();
        assertThat(source)
                .contains("Objects.equals((Object) 1, (Object) 1L)")
                .contains("Integer.valueOf(1)")
                .contains(".intValue()")
                .contains("new int[3]")
                .contains(".length")
                .contains("new String[2]")
                .contains("Math.toIntExact(5L)")
                .contains("(int) 5L");
        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), source));
        assertThat(compilation).succeededWithoutWarnings();
    }
}
