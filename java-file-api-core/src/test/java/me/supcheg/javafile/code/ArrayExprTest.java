package me.supcheg.javafile.code;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayExprTest {

    @Test
    void arrayAccessIsAValidAssignTarget() {
        AssignTarget target = new ArrayAccessExpr(new FieldAccessExpr(Optional.empty(), "arr"), new IntLiteral(0));
        AssignStmt stmt = new AssignStmt(target, new IntLiteral(1));
        assertThat(stmt.target()).isEqualTo(target);
    }

    @Test
    void arrayCreationHoldsComponentTypeAndDimensions() {
        ArrayCreationExpr expr =
                new ArrayCreationExpr(PrimitiveTypeRef.INT, new NonEmptyList<>(new IntLiteral(3), List.of()));
        assertThat(expr.dimensions().toList()).containsExactly(new IntLiteral(3));
    }

    @Test
    void arrayInitializerHoldsElements() {
        ArrayInitializerExpr expr =
                new ArrayInitializerExpr(PrimitiveTypeRef.INT, List.of(new IntLiteral(1), new IntLiteral(2)));
        assertThat(expr.elements()).containsExactly(new IntLiteral(1), new IntLiteral(2));
    }
}
