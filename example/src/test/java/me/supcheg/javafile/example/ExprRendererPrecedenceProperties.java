package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import java.lang.constant.ClassDesc;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ExprRendererPrecedenceProperties {

    @Property
    void renderedExpressionEvaluatesToTheSameValueAsTheTreeItRepresents(@ForAll("intExprTrees") IntExprNode tree)
            throws Exception {
        ClassDesc classDesc = ClassDesc.of("me.supcheg.example", "PrecedenceProbe");
        JavaFile file = JavaFile.of(
                classDesc,
                cb -> cb.withMethod(
                        "compute",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .withBody(b -> b.return_(tree.toExpr(b)))));

        Class<?> probeClass = InMemoryCompiler.compileAndLoad(file.qualifiedName(), file.render());
        Method computeMethod = probeClass.getMethod("compute");

        int actual = (int) computeMethod.invoke(null);
        assertThat(actual).isEqualTo(tree.evaluate());
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
