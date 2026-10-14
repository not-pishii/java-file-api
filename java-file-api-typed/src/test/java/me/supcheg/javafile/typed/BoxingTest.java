package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.Function;

import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.JavacVerdict.unlikeJavac;

/// A value of a primitive type is refused where a reference is taken: Java would box it implicitly, and the
/// typed layer boxes only where the generator asks for it (`box`). Each is javac's implicit boxing, which
/// the typed layer is stricter than.
class BoxingTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile staticMethod(Function<Body<Object>, Terminated<Object>> body) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var m = cb.declareStaticMethod("m", Object_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(m, body);
                return bodies;
            }
        });
    }

    @Test
    void aVariableOfAReferenceTypeIsNotInitializedByAPrimitive() {
        unlikeJavac(
                IllegalArgumentException.class,
                () -> staticMethod(b -> b.let(Object_.TOKEN, literal(1), b::return_)),
                "let takes a java.lang.Object and is given a value of the primitive type int, which Java would box"
                        + " implicitly; box it explicitly, e.g. box(PrimitiveToken.INT, expr)",
                "static Object m() { Object v = 1; return v; }");
        unlikeJavac(
                IllegalArgumentException.class,
                () -> staticMethod(b -> b.letVar(Object_.TOKEN, literal(1L), b::return_)),
                "letVar takes a java.lang.Object",
                "static Object m() { Object v = 1L; return v; }");
        unlikeJavac(
                IllegalArgumentException.class,
                () -> staticMethod(b -> b.for_(
                                Object_.TOKEN,
                                literal(1),
                                v -> eqRef(v, literalNull(Object_.TOKEN)),
                                v -> assign(v, v),
                                (_, _, _) -> {})
                        .return_(literal("x"))),
                "for_ takes a java.lang.Object",
                "static Object m() { for (Object v = 1; v == null; v = v) {} return \"x\"; }");
    }

    @Test
    void aReferenceIsNotAssignedAPrimitive() {
        ArrayToken<Object[], Object> objects = ArrayToken.of(Object_.TOKEN);
        unlikeJavac(
                IllegalArgumentException.class,
                () -> staticMethod(b -> b.letVar(
                        Object_.TOKEN,
                        literal("x"),
                        v -> b.exec(assign(v, literal(true))).return_(v))),
                "assign takes a java.lang.Object and is given a value of the primitive type boolean",
                "static Object m() { Object v = \"x\"; v = true; return v; }");
        unlikeJavac(
                IllegalArgumentException.class,
                () -> staticMethod(b -> b.let(
                        objects,
                        newArray(objects, literal(1)),
                        a -> b.exec(assignAt(objects, a, literal(0), literal(1.5)))
                                .return_(a))),
                "assignAt takes a java.lang.Object and is given a value of the primitive type double",
                "static Object m() { Object[] a = new Object[1]; a[0] = 1.5; return a; }");
    }

    @Test
    void aReferenceIsNotReturnedOrPassedAPrimitive() {
        unlikeJavac(
                IllegalArgumentException.class,
                () -> staticMethod(b -> b.return_(literal(1))),
                "return_ takes a java.lang.Object and is given a value of the primitive type int",
                "static Object m() { return 1; }");
        unlikeJavac(
                () -> staticMethod(b -> b.return_(staticCall(String_.valueOf_Object, literal(1)))),
                "takes a java.lang.Object and is given a value of the primitive type int, which Java would box"
                        + " implicitly; box it explicitly",
                "static Object m() { return String.valueOf((Object) 1); }");
    }

    @Test
    void aLambdaOfAReferenceResultDoesNotGiveAPrimitive() {
        var supplier = new Supplier_<>(Object_.TOKEN);
        unlikeJavac(
                IllegalArgumentException.class,
                () -> lambda(supplier.sam, () -> literal(1)),
                "the lambda of java.lang.Object java.util.function.Supplier<java.lang.Object>.get() takes a"
                        + " java.lang.Object and is given a value of the primitive type int",
                "static Supplier<Object> m() { return () -> 1; }");
    }

    @Test
    void aFieldOfAReferenceTypeIsNotInitializedByAPrimitive() {
        unlikeJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var f = cb.declareField("f", Object_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.initialize(f, literal(1));
                        return bodies;
                    }
                }),
                "the initializer of field f takes a java.lang.Object",
                "final Object f = 1;");
    }
}
