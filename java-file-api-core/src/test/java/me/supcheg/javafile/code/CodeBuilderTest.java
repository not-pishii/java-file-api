package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static me.supcheg.javafile.code.Exprs.and;
import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.div;
import static me.supcheg.javafile.code.Exprs.eq;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.ge;
import static me.supcheg.javafile.code.Exprs.gt;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.literalNull;
import static me.supcheg.javafile.code.Exprs.lt;
import static me.supcheg.javafile.code.Exprs.mod;
import static me.supcheg.javafile.code.Exprs.mul;
import static me.supcheg.javafile.code.Exprs.neq;
import static me.supcheg.javafile.code.Exprs.newDiamond;
import static me.supcheg.javafile.code.Exprs.new_;
import static me.supcheg.javafile.code.Exprs.not;
import static me.supcheg.javafile.code.Exprs.or;
import static me.supcheg.javafile.code.Exprs.postDecrement;
import static me.supcheg.javafile.code.Exprs.postIncrement;
import static me.supcheg.javafile.code.Exprs.preDecrement;
import static me.supcheg.javafile.code.Exprs.preIncrement;
import static me.supcheg.javafile.code.Exprs.sub;
import static me.supcheg.javafile.code.Exprs.switchExpr;
import static me.supcheg.javafile.code.Exprs.textBlock;
import static me.supcheg.javafile.code.Exprs.this_;
import static me.supcheg.javafile.code.Patterns.recordPattern;
import static me.supcheg.javafile.code.Patterns.typePattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CodeBuilderTest {

    @Test
    void returnOfAFieldGetterCall() {
        CodeBuilder cb = new CodeBuilder();
        cb.return_(field("bundle").call("getString", literal("greeting")));

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
        cb.assign(this_().field("bundle"), field("bundle"));

        FieldAccessExpr expectedTarget = new FieldAccessExpr(Optional.of(new ThisExpr()), "bundle");
        Expr expectedValue = new FieldAccessExpr(Optional.empty(), "bundle");
        assertThat(cb.build().statements())
                .containsExactly(new AssignStmt(expectedTarget, AssignOp.ASSIGN, expectedValue));
    }

    @Test
    void assignWithOpAddsAssignStmtWithGivenOperator() {
        CodeBuilder cb = new CodeBuilder();
        cb.assign(field("total"), AssignOp.ADD_ASSIGN, field("delta"));

        FieldAccessExpr expectedTarget = new FieldAccessExpr(Optional.empty(), "total");
        Expr expectedValue = new FieldAccessExpr(Optional.empty(), "delta");
        assertThat(cb.build().statements())
                .containsExactly(new AssignStmt(expectedTarget, AssignOp.ADD_ASSIGN, expectedValue));
    }

    @Test
    void twoArgAssignIsASynonymForAssignOpAssign() {
        CodeBuilder cb1 = new CodeBuilder();
        cb1.assign(field("x"), literal(1));

        CodeBuilder cb2 = new CodeBuilder();
        cb2.assign(field("x"), AssignOp.ASSIGN, literal(1));

        assertThat(cb1.build()).isEqualTo(cb2.build());
    }

    @Test
    void literalsCoverEachSupportedType() {
        assertThat(literal("s")).isEqualTo(new StringLiteral("s"));
        assertThat(literal(1)).isEqualTo(new IntLiteral(1));
        assertThat(literal(1L)).isEqualTo(new LongLiteral(1L));
        assertThat(literal(1.5)).isEqualTo(new DoubleLiteral(1.5));
        assertThat(literal(true)).isEqualTo(new BooleanLiteral(true));
        assertThat(literalNull()).isEqualTo(new NullLiteral());
        assertThat(textBlock("line")).isEqualTo(new TextBlockExpr("line"));
    }

    @Test
    void everyLiteralExceptNullIsAConstantLiteral() {
        assertThat(literal("s")).isInstanceOf(ConstantLiteral.class);
        assertThat(literal(1)).isInstanceOf(ConstantLiteral.class);
        assertThat(literal(1L)).isInstanceOf(ConstantLiteral.class);
        assertThat(literal(1.5)).isInstanceOf(ConstantLiteral.class);
        assertThat(literal(true)).isInstanceOf(ConstantLiteral.class);
        assertThat(literalNull()).isNotInstanceOf(ConstantLiteral.class);
    }

    @Test
    void emptyBodyEqualsCodeBodyEmpty() {
        CodeBuilder cb = new CodeBuilder();

        assertThat(cb.build()).isEqualTo(CodeBody.EMPTY);
    }

    @Test
    void binaryOperatorHelpersProduceBinaryExpr() {
        assertThat(lt(field("i"), literal(10)))
                .isEqualTo(new BinaryExpr(new FieldAccessExpr(Optional.empty(), "i"), BinaryOp.LT, new IntLiteral(10)));
        assertThat(and(literal(true), literal(false)))
                .isEqualTo(new BinaryExpr(new BooleanLiteral(true), BinaryOp.AND, new BooleanLiteral(false)));
    }

    @Test
    void unaryOperatorHelpersProduceUnaryExpr() {
        assertThat(postIncrement(field("i")))
                .isEqualTo(new IncDecExpr(IncDecOp.POST_INC, new FieldAccessExpr(Optional.empty(), "i")));
        assertThat(not(literal(true))).isEqualTo(new UnaryExpr(UnaryOp.NOT, new BooleanLiteral(true)));
    }

    @Test
    void remainingBinaryOperatorHelpersProduceBinaryExpr() {
        Expr left = field("a");
        Expr right = field("b");

        assertThat(sub(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.SUB, right));
        assertThat(mul(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.MUL, right));
        assertThat(div(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.DIV, right));
        assertThat(mod(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.MOD, right));
        assertThat(neq(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.NEQ, right));
        assertThat(ge(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.GE, right));
        assertThat(or(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.OR, right));
    }

    @Test
    void remainingUnaryOperatorHelpersProduceUnaryExpr() {
        Expr operand = field("i");

        assertThat(preIncrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.PRE_INC, operand));
        assertThat(preDecrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.PRE_DEC, operand));
        assertThat(postDecrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.POST_DEC, operand));
    }

    @Test
    void instanceOfWithoutBindingProducesInstanceOfExprWithEmptyBinding() {
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        Expr expr = field("obj").instanceOf(stringType);

        assertThat(expr)
                .isEqualTo(new InstanceOfExpr(
                        new FieldAccessExpr(Optional.empty(), "obj"), new TypePattern(stringType, Optional.empty())));
    }

    @Test
    void instanceOfWithBindingProducesInstanceOfExprWithBindingName() {
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        Expr expr = field("obj").instanceOf(stringType, "s");

        assertThat(expr)
                .isEqualTo(new InstanceOfExpr(
                        new FieldAccessExpr(Optional.empty(), "obj"), new TypePattern(stringType, Optional.of("s"))));
    }

    @Test
    void typePatternProducesTypePatternWithBindingName() {
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        Pattern pattern = typePattern(stringType, "s");

        assertThat(pattern).isEqualTo(new TypePattern(stringType, Optional.of("s")));
    }

    @Test
    void recordPatternProducesRecordPatternWithComponentPatterns() {
        me.supcheg.javafile.type.TypeRef pointType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("com.example", "Point"));
        me.supcheg.javafile.type.TypeRef intType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "Integer"));
        Pattern xPattern = typePattern(intType, "x");
        Pattern yPattern = typePattern(intType, "y");

        Pattern pattern = recordPattern(pointType, xPattern, yPattern);

        assertThat(pattern).isEqualTo(new RecordPattern(pointType, java.util.List.of(xPattern, yPattern)));
    }

    @Test
    void newExprCarriesTypeAndArguments() {
        me.supcheg.javafile.type.TypeRef exceptionType = me.supcheg.javafile.type.Types.of(
                java.lang.constant.ClassDesc.of("java.lang", "IllegalStateException"));

        Expr expr = new_(exceptionType, literal("bad state"));

        assertThat(expr)
                .isEqualTo(new NewExpr(
                        new TypedNewTarget(exceptionType), java.util.List.of(new StringLiteral("bad state"))));
    }

    @Test
    void newDiamondCarriesRawClassAndArguments() {
        java.lang.constant.ClassDesc rawType = java.lang.constant.ClassDesc.of("java.util", "ArrayList");

        Expr expr = newDiamond(rawType, literal("seed"));

        assertThat(expr)
                .isEqualTo(new NewExpr(new DiamondNewTarget(rawType), java.util.List.of(new StringLiteral("seed"))));
    }

    @Test
    void localVarWithExplicitTypeAddsATypedDeclaration() {
        CodeBuilder cb = new CodeBuilder();
        cb.localVar("count", me.supcheg.javafile.type.PrimitiveTypeRef.INT, literal(0));

        assertThat(cb.build().statements())
                .containsExactly(new LocalVarDeclStmt.Typed(
                        me.supcheg.javafile.type.PrimitiveTypeRef.INT, "count", Optional.of(new IntLiteral(0))));
    }

    @Test
    void localVarWithExplicitTypeAndNoInitializerOmitsIt() {
        CodeBuilder cb = new CodeBuilder();
        cb.localVar("count", me.supcheg.javafile.type.PrimitiveTypeRef.INT);

        assertThat(cb.build().statements())
                .containsExactly(new LocalVarDeclStmt.Typed(
                        me.supcheg.javafile.type.PrimitiveTypeRef.INT, "count", Optional.empty()));
    }

    @Test
    void localVarWithoutTypeInfersVar() {
        CodeBuilder cb = new CodeBuilder();
        cb.localVar("name", literal("x"));

        assertThat(cb.build().statements())
                .containsExactly(new LocalVarDeclStmt.Inferred("name", new StringLiteral("x")));
    }

    @Test
    void ifWithoutElseProducesIfStmtWithEmptyElseAndNoElseIfClauses() {
        CodeBuilder cb = new CodeBuilder();
        cb.if_(eq(field("x"), literalNull()), ib -> ib.then(b -> b.return_()));

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
                lt(field("x"), literal(0)),
                ib -> ib.then(b -> b.return_(literal("negative")))
                        .elseIf(eq(field("x"), literal(0)), b -> b.return_(literal("zero")))
                        .else_(b -> b.return_(literal("positive"))));

        IfStmt stmt = (IfStmt) cb.build().statements().get(0);
        assertThat(stmt.elseIfClauses()).hasSize(1);
        assertThat(stmt.elseBody()).isPresent();
    }

    @Test
    void whileAddsAWhileStmtWithConditionAndBody() {
        CodeBuilder cb = new CodeBuilder();
        cb.while_(lt(field("i"), literal(10)), b -> b.exprStatement(postIncrement(field("i"))));

        WhileStmt stmt = (WhileStmt) cb.build().statements().get(0);
        assertThat(stmt.condition())
                .isEqualTo(new BinaryExpr(new FieldAccessExpr(Optional.empty(), "i"), BinaryOp.LT, new IntLiteral(10)));
        assertThat(stmt.body().statements()).hasSize(1);
    }

    @Test
    void doWhileAddsADoWhileStmtWithBodyAndCondition() {
        CodeBuilder cb = new CodeBuilder();
        cb.doWhile_(lt(field("i"), literal(10)), b -> b.exprStatement(postIncrement(field("i"))));

        DoWhileStmt stmt = (DoWhileStmt) cb.build().statements().get(0);
        assertThat(stmt.body().statements()).hasSize(1);
    }

    @Test
    void forAddsAForStmtWithInitConditionAndUpdate() {
        CodeBuilder cb = new CodeBuilder();
        LocalVarDeclStmt init =
                new LocalVarDeclStmt.Typed(me.supcheg.javafile.type.PrimitiveTypeRef.INT, "i", Optional.of(literal(0)));
        ExprStmt update = new ExprStmt(postIncrement(field("i")));

        cb.for_(init, lt(field("i"), literal(10)), update, b -> b.exprStatement(call("use")));

        ForStmt stmt = (ForStmt) cb.build().statements().get(0);
        assertThat(stmt.init()).contains(init);
        assertThat(stmt.update()).contains(update);
    }

    @Test
    void forEachAddsAnEnhancedForStmt() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        cb.forEach(stringType, "item", field("items"), b -> b.exprStatement(call("use")));

        EnhancedForStmt stmt = (EnhancedForStmt) cb.build().statements().get(0);
        assertThat(stmt.varName()).isEqualTo("item");
        assertThat(stmt.iterable()).isEqualTo(new FieldAccessExpr(Optional.empty(), "items"));
    }

    @Test
    void switchAddsASwitchStmtWithConstantCasesAndDefault() {
        CodeBuilder cb = new CodeBuilder();
        cb.switch_(
                field("day"),
                sb -> sb.case_(literal("MON"), b -> b.return_(literal(1))).default_(b -> b.return_(literal(0))));

        SwitchStmt stmt = (SwitchStmt) cb.build().statements().get(0);
        assertThat(stmt.cases()).hasSize(2);
        assertThat(stmt.cases().get(0).labels().toList()).containsExactly(new ConstantLabel(new StringLiteral("MON")));
        assertThat(stmt.cases().get(1).labels().toList()).containsExactly(new DefaultLabel());
    }

    @Test
    void switchExprReturnsASwitchExprUsableAsAValue() {
        Expr expr = switchExpr(
                field("day"), sb -> sb.caseValue(literal("MON"), literal(1)).defaultValue(literal(0)));

        assertThat(expr).isInstanceOf(SwitchExpr.class);
        SwitchExpr switchExprResult = (SwitchExpr) expr;
        assertThat(switchExprResult.cases().get(0).body()).isEqualTo(new ExprCaseBody(new IntLiteral(1)));
    }

    @Test
    void switchSupportsTypePatternCasesWithAnOptionalGuard() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));

        cb.switch_(
                field("obj"),
                sb -> sb.caseTypeWithGuard(
                                stringType, "s", gt(field("s").call("length"), literal(0)), b -> b.return_(field("s")))
                        .default_(b -> b.return_(literalNull())));

        SwitchStmt stmt = (SwitchStmt) cb.build().statements().get(0);
        PatternLabel label = (PatternLabel) stmt.cases().get(0).labels().head();
        TypePattern pattern = (TypePattern) label.pattern();
        assertThat(pattern.bindingName()).isEqualTo(Optional.of("s"));
        assertThat(label.guard()).isPresent();
    }

    @Test
    void switchSupportsPatternCasesWithoutAGuard() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef pointType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("com.example", "Point"));
        me.supcheg.javafile.type.TypeRef intType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "Integer"));
        Pattern pattern = recordPattern(pointType, typePattern(intType, "x"), typePattern(intType, "y"));

        cb.switch_(
                field("obj"),
                sb -> sb.casePattern(pattern, b -> b.return_(literal(1))).default_(b -> b.return_(literalNull())));

        SwitchStmt stmt = (SwitchStmt) cb.build().statements().get(0);
        PatternLabel label = (PatternLabel) stmt.cases().get(0).labels().head();
        assertThat(label.pattern()).isEqualTo(pattern);
        assertThat(label.guard()).isEmpty();
    }

    @Test
    void switchSupportsPatternCasesWithAGuard() {
        CodeBuilder cb = new CodeBuilder();
        me.supcheg.javafile.type.TypeRef pointType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("com.example", "Point"));
        me.supcheg.javafile.type.TypeRef intType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "Integer"));
        Pattern pattern = recordPattern(pointType, typePattern(intType, "x"), typePattern(intType, "y"));
        Expr guard = gt(field("x"), literal(0));

        cb.switch_(
                field("obj"),
                sb -> sb.casePatternWithGuard(pattern, guard, b -> b.return_(literal(1)))
                        .default_(b -> b.return_(literalNull())));

        SwitchStmt stmt = (SwitchStmt) cb.build().statements().get(0);
        PatternLabel label = (PatternLabel) stmt.cases().get(0).labels().head();
        assertThat(label.pattern()).isEqualTo(pattern);
        assertThat(label.guard()).isEqualTo(Optional.of(guard));
    }

    @Test
    void yieldAddsAYieldStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.yield_(literal(1));

        assertThat(cb.build().statements()).containsExactly(new YieldStmt(new IntLiteral(1)));
    }

    @Test
    void throwAddsAThrowStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.throw_(new_(
                me.supcheg.javafile.type.Types.of(
                        java.lang.constant.ClassDesc.of("java.lang", "IllegalStateException")),
                literal("bad")));

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
    void labeledContinueAddsAContinueStmtTargetingTheLabel() {
        CodeBuilder cb = new CodeBuilder();
        cb.continue_("outer");

        assertThat(cb.build().statements()).containsExactly(new ContinueStmt(Optional.of("outer")));
    }

    @Test
    void assertAddEnAssertStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.assert_(call("call"));

        assertThat(cb.build().statements())
                .containsExactly(
                        new AssertStmt(new MethodCallExpr(Optional.empty(), "call", List.of()), Optional.empty()));
    }

    @Test
    void assertWithMessageAddEnAssertStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.assert_(call("call"), new StringLiteral("message"));

        assertThat(cb.build().statements())
                .containsExactly(new AssertStmt(
                        new MethodCallExpr(Optional.empty(), "call", List.of()),
                        Optional.of(new StringLiteral("message"))));
    }

    @Test
    void emptyAddsAnEmptyStmt() {
        CodeBuilder cb = new CodeBuilder();
        cb.empty();

        assertThat(cb.build().statements()).containsExactly(new EmptyStmt());
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
                b -> b.exprStatement(call("risky")),
                tb -> tb.catch_(
                        List.of(ioException),
                        "e",
                        b -> b.exprStatement(field("e").call("printStackTrace"))));

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

        cb2.try_(b -> {}, tb -> tb.finally_(b -> b.exprStatement(call("cleanup"))));

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
                tb -> tb.resource_("r1", Types.of(ClassDesc.of("java.io", "Reader")), call("openReader"))
                        .resource_("r2", call("openWriter"))
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
