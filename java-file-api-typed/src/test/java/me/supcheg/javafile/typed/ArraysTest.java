package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Picked_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.at;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.length;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static org.assertj.core.api.Assertions.assertThat;

/// Arrays of arrays are created with the length on the outer dimension, and an element of an array is of
/// the component of exactly the type of the array: an array is never seen as one of a supertype of its
/// component, which would take a store javac rejects and an element it types narrower.
class ArraysTest {

    private static final ArrayToken<int[], Prim.Int> INTS = PrimitiveToken.INT.array();
    private static final ArrayToken<int[][], int[]> INTS_2 = ArrayToken.of(INTS);
    private static final ArrayToken<int[][][], int[][]> INTS_3 = ArrayToken.of(INTS_2);
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);
    private static final ArrayToken<String[][], String[]> STRINGS_2 = ArrayToken.of(STRINGS);

    private static JavaFile file(String name, TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    @Test
    void anArrayOfArraysHasTheLengthOnItsOuterDimension() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Nested", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var ints = cb.declareStaticMethod("ints", PrimitiveToken.INT);
                var strings = cb.declareStaticMethod("strings", PrimitiveToken.INT);
                var deep = cb.declareStaticMethod("deep", PrimitiveToken.INT);
                var bodies = cb.bodies();
                // new int[2][]: two rows, each null until one is stored
                bodies.define(
                        ints,
                        b -> b.let(
                                INTS_2,
                                newArray(INTS_2, literal(2)),
                                rows -> b.exec(assignAt(INTS_2, rows, literal(1), newArray(INTS, literal(3))))
                                        .return_(length(INTS, at(INTS_2, rows, literal(1))))));
                bodies.define(
                        strings,
                        b -> b.let(
                                STRINGS_2,
                                newArray(STRINGS_2, literal(4)),
                                rows -> b.return_(length(STRINGS_2, rows))));
                bodies.define(
                        deep,
                        b -> b.let(INTS_3, newArray(INTS_3, literal(5)), cube -> b.return_(length(INTS_3, cube))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("new int[2][]")
                .contains("new String[4][]")
                .contains("new int[5][][]");
        assertThat(compiled.invoke("ints")).isEqualTo(3);
        assertThat(compiled.invoke("strings")).isEqualTo(4);
        assertThat(compiled.invoke("deep")).isEqualTo(5);
    }

    @Test
    void anElementOfAnArrayIsOfItsComponentAndPinsTheOverloadOfItsType() throws Throwable {
        // `new Picked(Object)` of an element of a `String[]`: the element is a String to javac, and the
        // argument is cast to the parameter of the fact, so the constructor of an Object is the one called.
        CompiledClasses compiled = CompiledClasses.of(file("Elements", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var picked = cb.declareStaticMethod("picked", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(
                        picked,
                        b -> b.let(
                                STRINGS,
                                newArray(STRINGS, literal(1)),
                                array -> b.exec(assignAt(STRINGS, array, literal(0), literal("x")))
                                        .return_(field(
                                                new_(Picked_.new_Object, at(STRINGS, array, literal(0))),
                                                Picked_.which))));
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("new Picked((Object) v0[0])");
        assertThat(compiled.invoke("picked")).isEqualTo("object");
    }

    @Test
    void anArrayOfASupertypeIsOneByItsOwnToken() throws Throwable {
        ArrayToken<Object[], Object> objects = ArrayToken.of(Object_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(file("Views", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var stored = cb.declareStaticMethod("stored", Object_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(
                        stored,
                        b -> b.let(
                                objects,
                                newArray(objects, literal(1)),
                                array -> b.exec(assignAt(objects, array, literal(0), literal("x")))
                                        .return_(at(objects, array, literal(0)))));
                return bodies;
            }
        }));

        assertThat(compiled.invoke("stored")).isEqualTo("x");
    }
}
