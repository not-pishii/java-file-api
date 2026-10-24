package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExprRendererPrecedenceProperties {

    /// How many trees one class of the property holds: javac runs once for all of them.
    private static final int TREES_PER_CLASS = 50;

    @Property(tries = 1000 / TREES_PER_CLASS)
    void renderedExpressionsEvaluateToTheSameValueAsTheTreesTheyRepresent(
            @ForAll("batchesOfIntExprTrees") List<IntExprNode> trees) throws Exception {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "PrecedenceProbe"), cb -> {
            for (int i = 0; i < trees.size(); i++) {
                IntExprNode tree = trees.get(i);
                cb.withMethod(
                        "compute" + i,
                        PrimitiveTypeRef.INT,
                        mb -> mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .withBody(b -> b.return_(tree.toExpr())));
            }
        });

        Class<?> probeClass = CompiledClasses.of(file).generated();

        for (int i = 0; i < trees.size(); i++) {
            int actual = (int) probeClass.getMethod("compute" + i).invoke(null);
            assertThat(actual)
                    .as("compute%d: %s", i, trees.get(i))
                    .isEqualTo(trees.get(i).evaluate());
        }
    }

    @Provide
    Arbitrary<List<IntExprNode>> batchesOfIntExprTrees() {
        return intExprTrees().list().ofSize(TREES_PER_CLASS);
    }

    @Provide
    Arbitrary<IntExprNode> intExprTrees() {
        Arbitrary<IntExprNode> literals =
                Arbitraries.integers().between(-1000, 1000).map(IntExprNode.Lit::new);

        return Arbitraries.recursive(
                () -> literals,
                node -> Arbitraries.<IntExprNode>oneOf(
                        node.map(IntExprNode.Neg::new),
                        Arbitraries.of('+', '-', '*')
                                .flatMap(op ->
                                        node.flatMap(left -> node.map(right -> new IntExprNode.Bin(left, op, right))))),
                0,
                4);
    }
}
