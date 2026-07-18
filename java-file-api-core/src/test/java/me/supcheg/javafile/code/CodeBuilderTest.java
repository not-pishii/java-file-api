package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CodeBuilderTest {

    @Test
    void returnOfAFieldGetterCall() {
        CodeBuilder cb = new CodeBuilder();
        cb.return_(cb.call(cb.field("bundle"), "getString", cb.literal("greeting")));

        CodeBody body = cb.build();

        Expr expectedCall = new MethodCallExpr(
                Optional.of(new FieldAccessExpr(Optional.empty(), "bundle")),
                "getString",
                java.util.List.of(new StringLiteral("greeting")));
        assertThat(body.statements()).containsExactly(new ReturnStmt(Optional.of(expectedCall)));
    }

    @Test
    void voidReturnProducesEmptyReturnStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.return_();

        assertThat(cb.build().statements()).containsExactly(new ReturnStmt(Optional.empty()));
    }

    @Test
    void assignAddsAssignStmtWithTargetAndValue() {
        CodeBuilder cb = new CodeBuilder();
        cb.assign(cb.field(cb.this_(), "bundle"), cb.field("bundle"));

        FieldAccessExpr expectedTarget = new FieldAccessExpr(Optional.of(new ThisExpr()), "bundle");
        Expr expectedValue = new FieldAccessExpr(Optional.empty(), "bundle");
        assertThat(cb.build().statements()).containsExactly(new AssignStmt(expectedTarget, expectedValue));
    }

    @Test
    void literalsCoverEachSupportedType() {
        CodeBuilder cb = new CodeBuilder();

        assertThat(cb.literal("s")).isEqualTo(new StringLiteral("s"));
        assertThat(cb.literal(1)).isEqualTo(new IntLiteral(1));
        assertThat(cb.literal(1L)).isEqualTo(new LongLiteral(1L));
        assertThat(cb.literal(1.5)).isEqualTo(new DoubleLiteral(1.5));
        assertThat(cb.literal(true)).isEqualTo(new BooleanLiteral(true));
        assertThat(cb.literalNull()).isEqualTo(new NullLiteral());
        assertThat(cb.textBlock("line")).isEqualTo(new TextBlockExpr("line"));
    }

    @Test
    void everyLiteralExceptNullIsAConstantLiteral() {
        CodeBuilder cb = new CodeBuilder();

        assertThat(cb.literal("s")).isInstanceOf(ConstantLiteral.class);
        assertThat(cb.literal(1)).isInstanceOf(ConstantLiteral.class);
        assertThat(cb.literal(1L)).isInstanceOf(ConstantLiteral.class);
        assertThat(cb.literal(1.5)).isInstanceOf(ConstantLiteral.class);
        assertThat(cb.literal(true)).isInstanceOf(ConstantLiteral.class);
        assertThat(cb.literalNull()).isNotInstanceOf(ConstantLiteral.class);
    }

    @Test
    void emptyBodyEqualsCodeBodyEmpty() {
        CodeBuilder cb = new CodeBuilder();

        assertThat(cb.build()).isEqualTo(CodeBody.EMPTY);
    }

    @Test
    void binaryOperatorHelpersProduceBinaryExpr() {
        CodeBuilder cb = new CodeBuilder();

        assertThat(cb.lt(cb.field("i"), cb.literal(10)))
                .isEqualTo(new BinaryExpr(new FieldAccessExpr(Optional.empty(), "i"), BinaryOp.LT, new IntLiteral(10)));
        assertThat(cb.and(cb.literal(true), cb.literal(false)))
                .isEqualTo(new BinaryExpr(new BooleanLiteral(true), BinaryOp.AND, new BooleanLiteral(false)));
    }

    @Test
    void unaryOperatorHelpersProduceUnaryExpr() {
        CodeBuilder cb = new CodeBuilder();

        assertThat(cb.postIncrement(cb.field("i")))
                .isEqualTo(new IncDecExpr(IncDecOp.POST_INC, new FieldAccessExpr(Optional.empty(), "i")));
        assertThat(cb.not(cb.literal(true))).isEqualTo(new UnaryExpr(UnaryOp.NOT, new BooleanLiteral(true)));
    }

    @Test
    void remainingBinaryOperatorHelpersProduceBinaryExpr() {
        CodeBuilder cb = new CodeBuilder();
        Expr left = cb.field("a");
        Expr right = cb.field("b");

        assertThat(cb.sub(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.SUB, right));
        assertThat(cb.mul(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.MUL, right));
        assertThat(cb.div(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.DIV, right));
        assertThat(cb.mod(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.MOD, right));
        assertThat(cb.neq(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.NEQ, right));
        assertThat(cb.ge(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.GE, right));
        assertThat(cb.or(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.OR, right));
    }

    @Test
    void remainingUnaryOperatorHelpersProduceUnaryExpr() {
        CodeBuilder cb = new CodeBuilder();
        Expr operand = cb.field("i");

        assertThat(cb.preIncrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.PRE_INC, operand));
        assertThat(cb.preDecrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.PRE_DEC, operand));
        assertThat(cb.postDecrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.POST_DEC, operand));
    }

    @Test
    void instanceOfWithoutBindingProducesInstanceOfExprWithEmptyBinding() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        Expr expr = cb.instanceOf(cb.field("obj"), stringType);

        assertThat(expr)
                .isEqualTo(
                        new InstanceOfExpr(new FieldAccessExpr(Optional.empty(), "obj"), stringType, Optional.empty()));
    }

    @Test
    void instanceOfWithBindingProducesInstanceOfExprWithBindingName() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        Expr expr = cb.instanceOf(cb.field("obj"), stringType, "s");

        assertThat(expr)
                .isEqualTo(
                        new InstanceOfExpr(new FieldAccessExpr(Optional.empty(), "obj"), stringType, Optional.of("s")));
    }

    @Test
    void newExprCarriesTypeAndArguments() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef exceptionType = me.supcheg.javafile.type.Types.of(
                java.lang.constant.ClassDesc.of("java.lang", "IllegalStateException"));

        Expr expr = cb.new_(exceptionType, cb.literal("bad state"));

        assertThat(expr)
                .isEqualTo(new NewExpr(
                        new TypedNewTarget(exceptionType), java.util.List.of(new StringLiteral("bad state"))));
    }

    @Test
    void newDiamondCarriesRawClassAndArguments() {
        CodeBuilder cb = new CodeBuilder();
        java.lang.constant.ClassDesc rawType = java.lang.constant.ClassDesc.of("java.util", "ArrayList");

        Expr expr = cb.newDiamond(rawType, cb.literal("seed"));

        assertThat(expr)
                .isEqualTo(new NewExpr(new DiamondNewTarget(rawType), java.util.List.of(new StringLiteral("seed"))));
    }

    @Test
    void localVarWithExplicitTypeAddsATypedDeclaration() {
        CodeBuilder cb = new CodeBuilder();
        cb.localVar("count", me.supcheg.javafile.type.PrimitiveTypeRef.INT, cb.literal(0));

        assertThat(cb.build().statements())
                .containsExactly(new LocalVarDeclStmt(
                        Optional.of(me.supcheg.javafile.type.PrimitiveTypeRef.INT), "count", new IntLiteral(0)));
    }

    @Test
    void localVarWithoutTypeInfersVar() {
        CodeBuilder cb = new CodeBuilder();
        cb.localVar("name", cb.literal("x"));

        assertThat(cb.build().statements())
                .containsExactly(new LocalVarDeclStmt(Optional.empty(), "name", new StringLiteral("x")));
    }

    @Test
    void ifWithoutElseProducesIfStmtWithEmptyElseAndNoElseIfClauses() {
        CodeBuilder cb = new CodeBuilder();
        cb.if_(cb.eq(cb.field("x"), cb.literalNull()), ib -> ib.then(b -> b.return_()));

        assertThat(cb.build().statements())
                .containsExactly(new IfStmt(
                        new BinaryExpr(new FieldAccessExpr(Optional.empty(), "x"), BinaryOp.EQ, new NullLiteral()),
                        new CodeBody(java.util.List.of(new ReturnStmt(Optional.empty()))),
                        java.util.List.of(),
                        Optional.empty()));
    }

    @Test
    void ifWithElseIfAndElseProducesAllClauses() {
        CodeBuilder cb = new CodeBuilder();
        cb.if_(
                cb.lt(cb.field("x"), cb.literal(0)),
                ib -> ib.then(b -> b.return_(b.literal("negative")))
                        .elseIf(cb.eq(cb.field("x"), cb.literal(0)), b -> b.return_(b.literal("zero")))
                        .else_(b -> b.return_(b.literal("positive"))));

        IfStmt stmt = (IfStmt) cb.build().statements().get(0);
        assertThat(stmt.elseIfClauses()).hasSize(1);
        assertThat(stmt.elseBody()).isPresent();
    }

    @Test
    void whileAddsAWhileStmtWithConditionAndBody() {
        CodeBuilder cb = new CodeBuilder();
        cb.while_(cb.lt(cb.field("i"), cb.literal(10)), b -> b.exprStatement(b.postIncrement(b.field("i"))));

        WhileStmt stmt = (WhileStmt) cb.build().statements().get(0);
        assertThat(stmt.condition())
                .isEqualTo(new BinaryExpr(new FieldAccessExpr(Optional.empty(), "i"), BinaryOp.LT, new IntLiteral(10)));
        assertThat(stmt.body().statements()).hasSize(1);
    }

    @Test
    void doWhileAddsADoWhileStmtWithBodyAndCondition() {
        CodeBuilder cb = new CodeBuilder();
        cb.doWhile_(cb.lt(cb.field("i"), cb.literal(10)), b -> b.exprStatement(b.postIncrement(b.field("i"))));

        DoWhileStmt stmt = (DoWhileStmt) cb.build().statements().get(0);
        assertThat(stmt.body().statements()).hasSize(1);
    }

    @Test
    void forAddsAForStmtWithInitConditionAndUpdate() {
        CodeBuilder cb = new CodeBuilder();
        LocalVarDeclStmt init =
                new LocalVarDeclStmt(Optional.of(me.supcheg.javafile.type.PrimitiveTypeRef.INT), "i", cb.literal(0));
        ExprStmt update = new ExprStmt(cb.postIncrement(cb.field("i")));

        cb.for_(init, cb.lt(cb.field("i"), cb.literal(10)), update, b -> b.exprStatement(b.call("use")));

        ForStmt stmt = (ForStmt) cb.build().statements().get(0);
        assertThat(stmt.init()).contains(init);
        assertThat(stmt.update()).contains(update);
    }

    @Test
    void forEachAddsAnEnhancedForStmt() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        cb.forEach(stringType, "item", cb.field("items"), b -> b.exprStatement(b.call("use")));

        EnhancedForStmt stmt = (EnhancedForStmt) cb.build().statements().get(0);
        assertThat(stmt.varName()).isEqualTo("item");
        assertThat(stmt.iterable()).isEqualTo(new FieldAccessExpr(Optional.empty(), "items"));
    }

    @Test
    void switchAddsASwitchStmtWithConstantCasesAndDefault() {
        CodeBuilder cb = new CodeBuilder();
        cb.switch_(
                cb.field("day"),
                sb -> sb.case_(cb.literal("MON"), b -> b.return_(cb.literal(1)))
                        .default_(b -> b.return_(cb.literal(0))));

        SwitchStmt stmt = (SwitchStmt) cb.build().statements().get(0);
        assertThat(stmt.cases()).hasSize(2);
        assertThat(stmt.cases().get(0).labels().toList()).containsExactly(new ConstantLabel(new StringLiteral("MON")));
        assertThat(stmt.cases().get(1).labels().toList()).containsExactly(new DefaultLabel());
    }

    @Test
    void switchExprReturnsASwitchExprUsableAsAValue() {
        CodeBuilder cb = new CodeBuilder();

        Expr expr = cb.switchExpr(
                cb.field("day"),
                sb -> sb.caseValue(cb.literal("MON"), cb.literal(1)).defaultValue(cb.literal(0)));

        assertThat(expr).isInstanceOf(SwitchExpr.class);
        SwitchExpr switchExpr = (SwitchExpr) expr;
        assertThat(switchExpr.cases().get(0).body()).isEqualTo(new ExprCaseBody(new IntLiteral(1)));
    }

    @Test
    void switchSupportsTypePatternCasesWithAnOptionalGuard() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        cb.switch_(
                cb.field("obj"),
                sb -> sb.caseTypeWithGuard(
                                stringType,
                                "s",
                                cb.gt(cb.call(cb.field("s"), "length"), cb.literal(0)),
                                b -> b.return_(cb.field("s")))
                        .default_(b -> b.return_(cb.literalNull())));

        SwitchStmt stmt = (SwitchStmt) cb.build().statements().get(0);
        TypePatternLabel label = (TypePatternLabel) stmt.cases().get(0).labels().head();
        assertThat(label.bindingName()).isEqualTo("s");
        assertThat(label.guard()).isPresent();
    }

    @Test
    void yieldAddsAYieldStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.yield_(cb.literal(1));

        assertThat(cb.build().statements()).containsExactly(new YieldStmt(new IntLiteral(1)));
    }

    @Test
    void throwAddsAThrowStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.throw_(cb.new_(
                me.supcheg.javafile.type.Types.of(
                        java.lang.constant.ClassDesc.of("java.lang", "IllegalStateException")),
                cb.literal("bad")));

        assertThat(cb.build().statements()).hasSize(1);
        assertThat(cb.build().statements().get(0)).isInstanceOf(ThrowStmt.class);
    }

    @Test
    void breakAndContinueAddTheirRespectiveStmts() {
        CodeBuilder cb = new CodeBuilder();
        cb.break_();
        cb.continue_();

        assertThat(cb.build().statements())
                .containsExactly(new BreakStmt(Optional.empty()), new ContinueStmt(Optional.empty()));
    }

    @Test
    void labeledWrapsExactlyTheOneStatementAppendedBySpec() {
        CodeBuilder cb = new CodeBuilder();
        cb.labeled("outer", b -> b.break_());

        assertThat(cb.build().statements()).containsExactly(new LabeledStmt("outer", new BreakStmt(Optional.empty())));
    }

    @Test
    void labeledThrowsWhenSpecAppendsZeroOrMoreThanOneStatement() {
        CodeBuilder cb1 = new CodeBuilder();
        assertThatThrownBy(() -> cb1.labeled("outer", b -> {})).isInstanceOf(IllegalArgumentException.class);

        CodeBuilder cb2 = new CodeBuilder();
        assertThatThrownBy(() -> cb2.labeled("outer", b -> {
                    b.break_();
                    b.continue_();
                }))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void tryWithSingleCatchProducesCatchOnlyTryStmt() {
        ClassOrInterfaceTypeRef ioException = Types.of(ClassDesc.of("java.io", "IOException"));
        CodeBuilder cb2 = new CodeBuilder();

        cb2.try_(
                b -> b.exprStatement(b.call("risky")),
                tb -> tb.catch_(
                        List.of(ioException), "e", b -> b.exprStatement(b.call(b.field("e"), "printStackTrace"))));

        assertThat(cb2.build().statements())
                .containsExactly(new TryStmt.CatchOnly(
                        List.of(),
                        new CodeBody(List.of(new ExprStmt(new MethodCallExpr(Optional.empty(), "risky", List.of())))),
                        NonEmptyList.copyOf(List.of(new CatchClause(
                                NonEmptyList.copyOf(List.of(ioException)),
                                "e",
                                new CodeBody(List.of(new ExprStmt(new MethodCallExpr(
                                        Optional.of(new FieldAccessExpr(Optional.empty(), "e")),
                                        "printStackTrace",
                                        List.of())))))))));
    }

    @Test
    void tryWithMultiCatchAccumulatesAllExceptionTypes() {
        ClassOrInterfaceTypeRef ioException = Types.of(ClassDesc.of("java.io", "IOException"));
        ClassOrInterfaceTypeRef sqlException = Types.of(ClassDesc.of("java.sql", "SQLException"));
        CodeBuilder cb2 = new CodeBuilder();

        cb2.try_(b -> {}, tb -> tb.catch_(List.of(ioException, sqlException), "e", b -> {}));

        TryStmt.CatchOnly stmt = (TryStmt.CatchOnly) cb2.build().statements().get(0);
        assertThat(stmt.catches().head().exceptionTypes().toList()).containsExactly(ioException, sqlException);
    }

    @Test
    void tryWithFinallyAndNoCatchProducesWithFinallyWithEmptyCatches() {
        CodeBuilder cb2 = new CodeBuilder();

        cb2.try_(b -> {}, tb -> tb.finally_(b -> b.exprStatement(b.call("cleanup"))));

        assertThat(cb2.build().statements())
                .containsExactly(new TryStmt.WithFinally(
                        List.of(),
                        CodeBody.EMPTY,
                        List.of(),
                        new CodeBody(
                                List.of(new ExprStmt(new MethodCallExpr(Optional.empty(), "cleanup", List.of()))))));
    }

    @Test
    void tryWithCatchAndFinallyProducesWithFinallyWithNonEmptyCatches() {
        ClassOrInterfaceTypeRef ioException = Types.of(ClassDesc.of("java.io", "IOException"));
        CodeBuilder cb2 = new CodeBuilder();

        cb2.try_(b -> {}, tb -> tb.catch_(List.of(ioException), "e", b -> {}).finally_(b -> {}));

        TryStmt.WithFinally stmt =
                (TryStmt.WithFinally) cb2.build().statements().get(0);
        assertThat(stmt.catches()).hasSize(1);
        assertThat(stmt.finallyBody()).isEqualTo(CodeBody.EMPTY);
    }

    @Test
    void tryWithResourcesAcceptsDeclaredInferredAndExistingForms() {
        CodeBuilder cb2 = new CodeBuilder();

        cb2.try_(
                b -> {},
                tb -> tb.resource_("r1", Types.of(ClassDesc.of("java.io", "Reader")), cb2.call("openReader"))
                        .resource_("r2", cb2.call("openWriter"))
                        .resource_("r3")
                        .finally_(b -> {}));

        TryStmt.WithFinally stmt =
                (TryStmt.WithFinally) cb2.build().statements().get(0);
        assertThat(stmt.resources())
                .containsExactly(
                        new Resource.Declared(
                                Optional.of(Types.of(ClassDesc.of("java.io", "Reader"))),
                                "r1",
                                new MethodCallExpr(Optional.empty(), "openReader", List.of())),
                        new Resource.Declared(
                                Optional.empty(), "r2", new MethodCallExpr(Optional.empty(), "openWriter", List.of())),
                        new Resource.Existing("r3"));
    }

    @Test
    void tryWithNeitherCatchNorFinallyThrows() {
        CodeBuilder cb2 = new CodeBuilder();

        assertThatThrownBy(() -> cb2.try_(b -> {}, tb -> {})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void tryWithCatchOfEmptyExceptionTypesThrows() {
        CodeBuilder cb2 = new CodeBuilder();

        assertThatThrownBy(() -> cb2.try_(b -> {}, tb -> tb.catch_(List.of(), "e", b -> {})))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
