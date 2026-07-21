package me.supcheg.javafile.code;

import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AnonymousBodyTest {

    @Test
    void plainNewExprHasNoAnonymousBody() {
        NewExpr expr = new NewExpr(new TypedNewTarget(Types.of(ClassDesc.of("java.lang", "Object"))), List.of());
        assertThat(expr.anonymousBody()).isEmpty();
    }

    @Test
    void newExprCanCarryAnAnonymousBody() {
        FieldDecl field = new FieldDecl(
                "seen",
                PrimitiveTypeRef.INT,
                List.of(),
                Set.of(me.supcheg.javafile.model.Modifier.PRIVATE),
                Optional.empty());
        NewExpr expr = new NewExpr(
                new TypedNewTarget(Types.of(ClassDesc.of("java.lang", "Object"))),
                List.of(),
                Optional.of(List.of(field)));
        assertThat(expr.anonymousBody()).contains(List.of(field));
    }
}
