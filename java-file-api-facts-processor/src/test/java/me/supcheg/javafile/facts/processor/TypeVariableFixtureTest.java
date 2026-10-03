package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `type variables`: what a type parameter of the type does to the
/// metamodel beyond standing for a type — an array of one in a signature is
/// an array of the type argument in the method table.
class TypeVariableFixtureTest extends FixtureSupport {
    private static final ClassDesc CD_INTEGER = ClassDesc.of("java.lang.Integer");

    private static RefToken<?> integer(ClassLoader loader) throws ReflectiveOperationException {
        return token(loader, "gen.facts.java.lang.Integer_");
    }

    // ------------------------------------------------------------------
    // an array of a type parameter in the single abstract method
    // ------------------------------------------------------------------

    @Test
    void theSamOfAGenericInterfaceTakesAndReturnsArraysOfItsTypeParameter() throws Exception {
        Compilation compilation = generate(
                "p.Arr.class, p.Spread.class, p.Grid.class, Integer.class",
                "package p; public interface Arr<T> { T[] m(T[] ts); }",
                "package p; public interface Spread<T> { @SuppressWarnings(\"unchecked\") void accept(T... ts); }",
                "package p; public interface Grid<A, B> { A[] row(B[][] cells); }");
        ClassLoader loader = load(compilation);
        RefToken<?> integer = integer(loader);

        // the method of the fact is the abstract method of the table of Arr<Integer>: m(Integer[])
        Object arr = instance(loader, "gen.facts.p.Arr_", integer);
        Sam1<?, ?, ?> sam = (Sam1<?, ?, ?>) fact(arr, "sam");
        assertThat(sam.method()).isSameAs(fact(arr, "m_TArray"));
        assertThat(sam.method().declared()).isEqualTo(Signature.of("m", Param.var(0, 1)));
        assertThat(sam.method().signature()).isEqualTo(new MethodSignature("m", List.of(CD_INTEGER.arrayType())));
        assertThat(sam.result().typeRef()).isEqualTo(Types.array(integer.typeRef()));
        assertThat(sam.owner().methods().abstractMethods())
                .containsExactly(sam.method().signature());
        assertThat(shape(loader, "gen.facts.p.Arr_").methods().abstractMethods())
                .containsExactly(Signature.of("m", Param.var(0, 1)));
        assertThat(sources(compilation).get("gen.facts.p.Arr_"))
                .contains("Signature.of(\"m\", Param.var(0, 1))")
                .contains("UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1))")
                .contains("table abstract m(#0[])");

        // a variable-arity parameter is its array type
        Object spread = instance(loader, "gen.facts.p.Spread_", integer);
        VoidSam1<?, ?> accept = (VoidSam1<?, ?>) fact(spread, "sam");
        assertThat(accept.method().signature())
                .isEqualTo(new MethodSignature("accept", List.of(CD_INTEGER.arrayType())));

        // each type parameter by its own position, under its own dimensions
        Object grid = instance(loader, "gen.facts.p.Grid_", integer, integer);
        Sam1<?, ?, ?> row = (Sam1<?, ?, ?>) fact(grid, "sam");
        assertThat(row.method().declared()).isEqualTo(Signature.of("row", Param.var(1, 2)));
        assertThat(row.method().signature()).isEqualTo(new MethodSignature("row", List.of(CD_INTEGER.arrayType(2))));
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void anArrayOfATypeParameterOfTheMethodIsErasedAsTheMethodDeclaresIt() throws Exception {
        Compilation compilation = generate(
                "p.Lists.class, Integer.class",
                "package p; public class Lists<T> { public <U extends Number> void all(U[] us, T[] ts) {} }");
        ClassLoader loader = load(compilation);
        RefToken<?> integer = integer(loader);

        Object lists = instance(loader, "gen.facts.p.Lists_", integer);
        Invocable all = (Invocable) made(loader, "gen.facts.p.Lists_", lists, "all_UArray_TArray", integer);
        assertThat(all.declared())
                .isEqualTo(Signature.of(
                        "all", Param.fixed(ClassDesc.of("java.lang.Number").arrayType()), Param.var(0, 1)));
        assertThat(all.signature())
                .isEqualTo(new MethodSignature("all", List.of(CD_INTEGER.arrayType(), CD_INTEGER.arrayType())));
    }
}
