package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.newArray;
import static me.supcheg.javafile.code.Exprs.newArrayOf;
import static org.assertj.core.api.Assertions.assertThat;

class ArrayDimensionsCompileTest {

    @Test
    void theDimensionsWithoutALengthComeAfterThoseWithOne() throws Exception {
        // JLS 15.10.1: an array of two `int[]` is `new int[2][]`; `new int[][2]` is no Java
        ClassDesc string = ClassDesc.of("java.lang", "String");
        JavaFile file = JavaFile.interface_(
                ClassDesc.of("me.supcheg.example", "Jagged"),
                ib -> ib.withConstant(
                                "ROWS",
                                new ArrayTypeRef(new ArrayTypeRef(PrimitiveTypeRef.INT)),
                                newArray(new ArrayTypeRef(PrimitiveTypeRef.INT), literal(2)))
                        .withConstant(
                                "CUBE",
                                Types.array(Types.array(Types.array(Types.array(Types.of(string))))),
                                newArray(Types.array(Types.array(Types.of(string))), literal(2), literal(3)))
                        .withConstant(
                                "INITIALIZED",
                                Types.array(Types.array(Types.INT)),
                                newArrayOf(Types.array(Types.INT), newArray(Types.INT, literal(1)))));

        String source = file.render();
        Class<?> jagged = InMemoryCompiler.compileAndLoad(file.qualifiedName(), source);

        assertThat(source)
                .contains("int[][] ROWS = new int[2][];")
                .contains("String[][][][] CUBE = new String[2][3][][];")
                .contains("int[][] INITIALIZED = new int[][] {new int[1]};");
        assertThat((int[][]) jagged.getField("ROWS").get(null)).isEqualTo(new int[2][]);
        assertThat((String[][][][]) jagged.getField("CUBE").get(null)).isEqualTo(new String[2][3][][]);
        assertThat((int[][]) jagged.getField("INITIALIZED").get(null)).isEqualTo(new int[][] {new int[1]});
    }
}
