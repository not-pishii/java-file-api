package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.ArrayInitializerExpr;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.code.Exprs.arrayInitializer;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.literalNull;
import static me.supcheg.javafile.code.Exprs.newArrayOf;
import static org.assertj.core.api.Assertions.assertThat;

class ArrayInitializerCompileTest {
    private static final ArrayTypeRef INTS = new ArrayTypeRef(PrimitiveTypeRef.INT);
    private static final ArrayTypeRef INTS_2 = new ArrayTypeRef(INTS);

    @Test
    void theShortFormStandsWhereTheTypeIsKnownAndCompilesAndRuns() throws Throwable {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Initializers"),
                cb -> cb.withField("TABLE", INTS_2, arrayInitializer(arrayInitializer(literal(7)), arrayInitializer()))
                        .withMethod(
                                "second",
                                PrimitiveTypeRef.INT,
                                mb -> mb.withBody(b -> {
                                    b.localVar("a", INTS, arrayInitializer(literal(1), literal(2)));
                                    b.return_(field("a").arrayAccess(literal(1)));
                                }))
                        .withMethod(
                                "rows",
                                PrimitiveTypeRef.INT,
                                mb -> mb.withBody(b -> {
                                    b.localVar(
                                            "b",
                                            INTS_2,
                                            arrayInitializer(
                                                    arrayInitializer(literal(1)),
                                                    arrayInitializer(literal(2), literal(3)),
                                                    literalNull()));
                                    b.return_(field("b").field("length"));
                                }))
                        .withMethod(
                                "lengthOfCreated",
                                PrimitiveTypeRef.INT,
                                mb -> mb.withBody(b -> b.return_(newArrayOf(PrimitiveTypeRef.INT, literal(1))
                                        .field("length"))))
                        .withMethod(
                                "cloned",
                                INTS_2,
                                mb -> mb.withBody(b -> b.return_(newArrayOf(
                                                INTS,
                                                newArrayOf(PrimitiveTypeRef.INT),
                                                newArrayOf(PrimitiveTypeRef.INT))
                                        .call("clone"))))
                        .withMethod(
                                "created",
                                INTS_2,
                                mb -> mb.withBody(b -> b.return_(new ArrayInitializerExpr(
                                        INTS,
                                        arrayInitializer(
                                                arrayInitializer(literal(1)),
                                                arrayInitializer(literal(2), literal(3))))))));

        CompiledClasses compiled = CompiledClasses.of(file);

        assertThat(compiled.source())
                .contains("int[][] TABLE = {{7}, {}};")
                .contains("int[] a = {1, 2};")
                .contains("int[][] b = {{1}, {2, 3}, null};")
                .contains("new int[] {1}.length")
                .contains("new int[][] {new int[] {}, new int[] {}}.clone()")
                .contains("return new int[][] {{1}, {2, 3}};");
        assertThat(compiled.invoke("second")).isEqualTo(2);
        assertThat(compiled.invoke("rows")).isEqualTo(3);
        assertThat(compiled.invoke("lengthOfCreated")).isEqualTo(1);
        assertThat((int[][]) compiled.invoke("cloned")).hasDimensions(2, 0);
        assertThat((int[][]) compiled.invoke("created")).isEqualTo(new int[][] {{1}, {2, 3}});
    }

    @Test
    void theShortFormStandsInTheConstantOfAnInterfaceAndTheStaticFieldOfARecord() {
        JavaFile iface = JavaFile.interface_(
                ClassDesc.of("me.supcheg.example", "Constants_"),
                ib -> ib.withConstant("PRIMES", INTS, arrayInitializer(literal(2), literal(3))));
        JavaFile record = JavaFile.record(
                ClassDesc.of("me.supcheg.example", "Pair_"),
                rb -> rb.withComponent("a", PrimitiveTypeRef.INT)
                        .withStaticField("NESTED", INTS_2, arrayInitializer(arrayInitializer(literal(1)))));

        Compilation compilation = RenderedJava.compile(List.of(iface, record));

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(iface.render()).contains("int[] PRIMES = {2, 3};");
        assertThat(record.render()).contains("int[][] NESTED = {{1}};");
    }
}
