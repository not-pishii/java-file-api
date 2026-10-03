package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `type variables`: what a type parameter of the type does to the
/// metamodel beyond standing for a type — an array of one in a signature is
/// an array of the type argument in the method table, and the name of one
/// neither hides a package the metamodel writes a qualified name by nor
/// takes the name of a fact.
class TypeVariableFixtureTest extends FixtureSupport {
    private static final ClassDesc CD_INTEGER = ClassDesc.of("java.lang.Integer");

    private static RefToken<?> integer(ClassLoader loader) throws ReflectiveOperationException {
        return token(loader, "gen.facts.java.lang.Integer_");
    }

    private static List<ClassDesc> erasures(Object fact) {
        return ((Invocable) fact)
                .params().stream().<ClassDesc>map(TypeToken::erasure).toList();
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

    // ------------------------------------------------------------------
    // a type parameter named like a package
    // ------------------------------------------------------------------

    @Test
    void aTypeParameterNamedLikeTheFirstNameOfAPackageIsRenamed() throws Exception {
        Compilation compilation = generate("p.Low.class, p.Up.class, java.util.Set.class, Integer.class", """
                package p;
                import java.util.Set;
                public class Low<gen, java, me, p> {
                    public gen first;
                    public Set<gen> all(java j, me m, p pp) { return null; }
                    public <gen> void each(Set<gen> set) {}
                    public <java, me> java pick(me from, Set<p> among) { return null; }
                    public static <gen, java, me, p> Low<gen, java, me, p> of(gen g, java j, me m, p pp) { return null; }
                }
                """, """
                package p;
                public class Up<Gen, Java> {
                    public java.util.Set<Gen> all(Java j) { return null; }
                    public <Me> java.util.Set<Me> each(Me me) { return null; }
                }
                """);
        ClassLoader loader = load(compilation);
        RefToken<?> integer = integer(loader);

        // the token of a type parameter is named after it in lower case: it does not hide the
        // package a qualified name starts with either, and may be named as one that starts none
        assertThat(sources(compilation).get("gen.facts.p.Up_"))
                .contains("public Up_(RefToken<Gen> gen_, RefToken<Java> java) {")
                .contains("each_Me(RefToken<Me> me) {")
                .contains("gen.facts.java.util.Set_.Data.SHAPE");
        assertThat(sources(compilation).get("gen.facts.p.Low_"))
                .contains("public final class Low_<gen_, java_, me_, p_> {")
                .doesNotContain("<gen>")
                .doesNotContain("<java>")
                .doesNotContain("<java,")
                .doesNotContain("<gen,");
        Object low = instance(loader, "gen.facts.p.Low_", integer, integer, integer, integer);
        assertThat(erasures(fact(low, "all_java_me_p"))).containsExactly(CD_INTEGER, CD_INTEGER, CD_INTEGER);
        assertThat(memberNames(loader, "gen.facts.p.Low_"))
                .containsExactly("all_java_me_p", "each_Set", "first", "new_", "of_gen_java_me_p", "pick_me_Set");
        assertThat(warnings(compilation)).isEmpty();
    }

    // ------------------------------------------------------------------
    // a member named like a type parameter
    // ------------------------------------------------------------------

    @Test
    void aFactIsNamedAfterItsMemberWhateverTheTypeParametersAreCalled() throws Exception {
        Compilation compilation = generate("p.G.class, Integer.class", """
                package p;
                public class G<T, E> {
                    public static final int T = 1;
                    public static int E;
                    public T value;
                    public E U;
                    public G(T t, E e) {}
                    public T T(E e) { return null; }
                    public <U> U E(U u, T t) { return u; }
                    public static <T, E> G<T, E> of(T T, E E) { return null; }
                }
                """);
        ClassLoader loader = load(compilation);
        RefToken<?> integer = integer(loader);

        // T, E and U are the fields, as in a type whose type parameters are called otherwise
        assertThat(memberNames(loader, "gen.facts.p.G_"))
                .containsExactly("E", "E_U_T", "T", "T_E", "U", "new_T_E", "of_T_E", "value");
        assertThat(((StaticFieldRef<?>) fact(loader, "gen.facts.p.G_", "T")).name())
                .isEqualTo("T");
        assertThat(((StaticFieldRef<?>) fact(loader, "gen.facts.p.G_", "E"))
                        .type()
                        .erasure())
                .isEqualTo(ConstantDescs.CD_int);
        Object g = instance(loader, "gen.facts.p.G_", integer, integer);
        assertThat(((FieldRef<?, ?>) fact(g, "U")).type()).isSameAs(integer);
        assertThat(((Invocable) fact(g, "T_E")).name()).isEqualTo("T");
        assertThat(((Invocable) made(loader, "gen.facts.p.G_", g, "E_U_T", integer)).name())
                .isEqualTo("E");
        assertThat(sources(compilation).get("gen.facts.p.G_")).contains("public final class G_<T, E> {");
        assertThat(warnings(compilation)).isEmpty();
    }
}
