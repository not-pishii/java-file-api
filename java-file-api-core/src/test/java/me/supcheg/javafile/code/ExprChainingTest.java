package me.supcheg.javafile.code;

import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ExprChainingTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");

    @Test
    void chainReadsLeftToRightAndMatchesNestedConstruction() {
        Expr chained = Exprs.this_().field("bundle").call("getString", Exprs.literal("greeting"));

        Expr nested = new MethodCallExpr(
                Optional.of(new FieldAccessExpr(Optional.of(new ThisExpr()), "bundle")),
                "getString",
                List.of(new StringLiteral("greeting")));
        assertThat(chained).isEqualTo(nested);
    }

    @Test
    void callAcceptsBothVarargsAndList() {
        Expr target = Exprs.field("list");
        assertThat(target.call("of", Exprs.literal(1))).isEqualTo(target.call("of", List.of(Exprs.literal(1))));
    }

    @Test
    void fieldAccessIsUsableAsAnAssignTarget() {
        AssignTarget target = Exprs.this_().field("count");
        assertThat(target).isEqualTo(new FieldAccessExpr(Optional.of(new ThisExpr()), "count"));
    }

    @Test
    void arrayAccessIsUsableAsAnAssignTarget() {
        AssignTarget target = Exprs.field("data").arrayAccess(Exprs.literal(0));
        assertThat(target)
                .isEqualTo(new ArrayAccessExpr(new FieldAccessExpr(Optional.empty(), "data"), new IntLiteral(0)));
    }

    @Test
    void callIsUsableAsAStatementExpr() {
        StatementExpr stmt = Exprs.field("bundle").call("close");
        assertThat(stmt)
                .isEqualTo(new MethodCallExpr(
                        Optional.of(new FieldAccessExpr(Optional.empty(), "bundle")), "close", List.of()));
    }

    @Test
    void instanceOfProducesPatternForms() {
        Expr subject = Exprs.field("value");
        assertThat(subject.instanceOf(Types.of(STRING)))
                .isEqualTo(new InstanceOfExpr(subject, new TypePattern(Types.of(STRING), Optional.empty())));
        assertThat(subject.instanceOf(Types.of(STRING), "s"))
                .isEqualTo(new InstanceOfExpr(subject, new TypePattern(Types.of(STRING), Optional.of("s"))));
    }

    @Test
    void instanceOfPatternUsesGivenPatternDirectly() {
        Expr subject = Exprs.field("value");
        Pattern pattern = new TypePattern(Types.of(STRING), Optional.of("s"));
        assertThat(subject.instanceOfPattern(pattern)).isEqualTo(new InstanceOfExpr(subject, pattern));
    }

    @Test
    void methodRefBindsTheInstance() {
        Expr instance = Exprs.field("bundle");
        assertThat(instance.methodRef("getString"))
                .isEqualTo(new MethodRefExpr(new ExprMethodRefTarget(instance), "getString"));
    }
}
