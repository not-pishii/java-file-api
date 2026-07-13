package me.supcheg.javafile.render;

import me.supcheg.javafile.code.AssignStmt;
import me.supcheg.javafile.code.BlockCaseBody;
import me.supcheg.javafile.code.CatchClause;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.ConstantLabel;
import me.supcheg.javafile.code.DefaultLabel;
import me.supcheg.javafile.code.DoWhileStmt;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.ExprCaseBody;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.code.Resource;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.code.SwitchCase;
import me.supcheg.javafile.code.SwitchExpr;
import me.supcheg.javafile.code.SwitchStmt;
import me.supcheg.javafile.code.TryStmt;
import me.supcheg.javafile.code.TypePatternLabel;
import me.supcheg.javafile.code.WhileStmt;
import me.supcheg.javafile.code.YieldStmt;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static me.supcheg.javafile.render.SourceRenderer.standardFormat;
import static org.assertj.core.api.Assertions.assertThat;

class ExprRendererTest {

    private final CodeBuilder cb = new CodeBuilder();

    @Test
    void fieldAccessWithoutTargetRendersBareName() {
        assertThat(ExprRenderer.renderExpr(cb.field("bundle"), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("bundle");
    }

    @Test
    void fieldAccessWithTargetRendersDottedPath() {
        Expr expr = cb.field(cb.field("this"), "bundle");
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("this.bundle");
    }

    @Test
    void methodCallRendersArgsCommaSeparated() {
        Expr call = cb.call(cb.field("bundle"), "getString", cb.literal("greeting"));
        assertThat(ExprRenderer.renderExpr(call, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("bundle.getString(\"greeting\")");
    }

    @Test
    void stringLiteralIsEscapedAndQuoted() {
        assertThat(ExprRenderer.renderExpr(cb.literal("a\"b"), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("\"a\\\"b\"");
    }

    @Test
    void textBlockKeepsATrailingBlankLineForAValueEndingInNewline() {
        assertThat(ExprRenderer.renderExpr(
                        cb.textBlock("line one\nline two\n"), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("\"\"\"\n" + "line one\n" + "line two\n" + "\n" + "\"\"\"");
    }

    @Test
    void numericAndBooleanAndNullLiteralsRenderVerbatim() {
        assertThat(ExprRenderer.renderExpr(cb.literal(1), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("1");
        assertThat(ExprRenderer.renderExpr(cb.literal(1L), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("1L");
        assertThat(ExprRenderer.renderExpr(cb.literal(1.5), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("1.5");
        assertThat(ExprRenderer.renderExpr(cb.literal(true), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("true");
        assertThat(ExprRenderer.renderExpr(cb.literal(false), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("false");
        assertThat(ExprRenderer.renderExpr(cb.literalNull(), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("null");
    }

    @Test
    void textBlockIsWrappedInTripleQuotesAndIndented() {
        String rendered = ExprRenderer.renderExpr(
                cb.textBlock("line one\nline two"),
                Context.of(standardFormat(), new ImportManager("p"))
                        .withIncreasedPad()
                        .withIncreasedPad());

        assertThat(rendered).isEqualTo("\"\"\"\n        line one\n        line two\n        \"\"\"");
    }

    @Test
    void returnStatementWithValueRendersSemicolonTerminated() {
        String rendered = ExprRenderer.renderStmt(
                new ReturnStmt(Optional.of(cb.literal(1))),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());
        assertThat(rendered).isEqualTo("    return 1;");
    }

    @Test
    void voidReturnStatementHasNoValue() {
        String rendered = ExprRenderer.renderStmt(
                new ReturnStmt(Optional.empty()),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());
        assertThat(rendered).isEqualTo("    return;");
    }

    @Test
    void assignStatementRendersTargetEqualsValue() {
        Expr target = cb.field(cb.field("this"), "bundle");
        Expr value = cb.field("bundle");
        String rendered = ExprRenderer.renderStmt(
                new AssignStmt(target, value),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());
        assertThat(rendered).isEqualTo("    this.bundle = bundle;");
    }

    @Test
    void binaryExprRendersInfixOperator() {
        Expr expr = cb.lt(cb.field("i"), cb.literal(10));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("i < 10");
    }

    @Test
    void unaryExprRendersPrefixAndPostfixCorrectly() {
        assertThat(ExprRenderer.renderExpr(
                        cb.postIncrement(cb.field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("i++");
        assertThat(ExprRenderer.renderExpr(
                        cb.not(cb.field("done")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("!done");
        assertThat(ExprRenderer.renderExpr(cb.neg(cb.field("x")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("-x");
    }

    @Test
    void unaryExprRendersPreIncrementPreDecrementAndPostDecrement() {
        assertThat(ExprRenderer.renderExpr(
                        cb.preIncrement(cb.field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("++i");
        assertThat(ExprRenderer.renderExpr(
                        cb.preDecrement(cb.field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("--i");
        assertThat(ExprRenderer.renderExpr(
                        cb.postDecrement(cb.field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("i--");
    }

    @Test
    void instanceOfWithBindingRendersTheBindingName() {
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));
        Expr expr = cb.instanceOf(cb.field("obj"), stringType, "s");

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("obj instanceof String s");
    }

    @Test
    void instanceOfWithoutBindingOmitsTheBindingName() {
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));
        Expr expr = cb.instanceOf(cb.field("obj"), stringType);

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("obj instanceof String");
    }

    @Test
    void newExprRendersTypeAndCommaSeparatedArguments() {
        me.supcheg.javafile.type.TypeRef exceptionType = me.supcheg.javafile.type.Types.of(
                java.lang.constant.ClassDesc.of("java.lang", "IllegalStateException"));
        Expr expr = cb.new_(exceptionType, cb.literal("bad state"));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("new IllegalStateException(\"bad state\")");
    }

    @Test
    void newDiamondRendersEmptyTypeArgumentList() {
        Expr expr = cb.newDiamond(java.lang.constant.ClassDesc.of("me.supcheg.example", "Impl"), cb.field("renderer"));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("me.supcheg.example"))))
                .isEqualTo("new Impl<>(renderer)");
    }

    @Test
    void typedLocalVarDeclRendersTheDeclaredType() {
        me.supcheg.javafile.code.Stmt stmt = new me.supcheg.javafile.code.LocalVarDeclStmt(
                Optional.of(me.supcheg.javafile.type.PrimitiveTypeRef.INT), "count", cb.literal(0));
        assertThat(ExprRenderer.renderStmt(
                        stmt,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    int count = 0;");
    }

    @Test
    void untypedLocalVarDeclRendersVar() {
        me.supcheg.javafile.code.Stmt stmt =
                new me.supcheg.javafile.code.LocalVarDeclStmt(Optional.empty(), "name", cb.literal("x"));
        assertThat(ExprRenderer.renderStmt(
                        stmt,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    var name = \"x\";");
    }

    @Test
    void ifStmtRendersBracesAndOptionalElseIfElseChain() {
        CodeBuilder body = new CodeBuilder();
        body.if_(body.lt(body.field("x"), body.literal(0)), ib -> ib.then(b -> b.return_(b.literal("negative")))
                .elseIf(body.eq(body.field("x"), body.literal(0)), b -> b.return_(b.literal("zero")))
                .else_(b -> b.return_(b.literal("positive"))));
        me.supcheg.javafile.code.Stmt stmt = body.build().statements().get(0);

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        if (x < 0) {
                            return "negative";
                        } else if (x == 0) {
                            return "zero";
                        } else {
                            return "positive";
                        }""".indent(4).stripTrailing());
    }

    @Test
    void whileStmtRendersConditionAndBracedBody() {
        Stmt stmt = new WhileStmt(
                cb.lt(cb.field("i"), cb.literal(10)),
                new CodeBody(java.util.List.of(new ExprStmt(cb.postIncrement(cb.field("i"))))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        while (i < 10) {
                            i++;
                        }""".indent(4).stripTrailing());
    }

    @Test
    void doWhileStmtRendersDoBraceBodyThenWhileCondition() {
        Stmt stmt = new DoWhileStmt(
                new CodeBody(java.util.List.of(new ExprStmt(cb.postIncrement(cb.field("i"))))),
                cb.lt(cb.field("i"), cb.literal(10)));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        do {
                            i++;
                        } while (i < 10);""".indent(4).stripTrailing());
    }

    @Test
    void classicForStmtRendersInitConditionUpdateAndBody() {
        me.supcheg.javafile.code.LocalVarDeclStmt init = new me.supcheg.javafile.code.LocalVarDeclStmt(
                Optional.of(me.supcheg.javafile.type.PrimitiveTypeRef.INT), "i", cb.literal(0));
        ExprStmt update = new ExprStmt(cb.postIncrement(cb.field("i")));
        Stmt stmt = new me.supcheg.javafile.code.ForStmt(
                Optional.of(init),
                Optional.of(cb.lt(cb.field("i"), cb.literal(10))),
                Optional.of(update),
                new CodeBody(java.util.List.of(new ExprStmt(cb.field("i")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        for (int i = 0; i < 10; i++) {
                            i;
                        }""".indent(4).stripTrailing());
    }

    @Test
    void enhancedForStmtRendersElementTypeAndIterable() {
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));
        Stmt stmt = new me.supcheg.javafile.code.EnhancedForStmt(
                stringType, "item", cb.field("items"), new CodeBody(java.util.List.of(new ExprStmt(cb.field("item")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        for (String item : items) {
                            item;
                        }""".indent(4).stripTrailing());
    }

    @Test
    void switchStmtRendersArrowCasesAndDefault() {
        Stmt stmt = new SwitchStmt(
                cb.field("day"),
                java.util.List.of(
                        new SwitchCase(
                                java.util.List.of(new ConstantLabel(cb.literal("MON"))),
                                new ExprCaseBody(cb.literal(1))),
                        new SwitchCase(java.util.List.of(new DefaultLabel()), new ExprCaseBody(cb.literal(0)))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (day) {
                        case "MON" -> 1;
                        default -> 0;
                    }""".indent(4).stripTrailing());
    }

    @Test
    void switchExprRendersInlineAsAValueProducingExpression() {
        Expr expr = new SwitchExpr(
                cb.field("day"),
                java.util.List.of(
                        new SwitchCase(
                                java.util.List.of(new ConstantLabel(cb.literal("MON"))),
                                new ExprCaseBody(cb.literal(1))),
                        new SwitchCase(java.util.List.of(new DefaultLabel()), new ExprCaseBody(cb.literal(0)))));

        String rendered = ExprRenderer.renderStmt(
                new ReturnStmt(Optional.of(expr)),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    return switch (day) {
                        case "MON" -> 1;
                        default -> 0;
                    };""".indent(4).stripTrailing());
    }

    @Test
    void switchCaseWithABlockBodyAndYieldRendersBracesAndYieldStatement() {
        Expr expr = new SwitchExpr(
                cb.field("day"),
                java.util.List.of(new SwitchCase(
                        java.util.List.of(new ConstantLabel(cb.literal("MON"))),
                        new BlockCaseBody(new CodeBody(java.util.List.of(new YieldStmt(cb.literal(1))))))));

        String rendered = ExprRenderer.renderStmt(
                new ReturnStmt(Optional.of(expr)),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    return switch (day) {
                        case "MON" -> {
                            yield 1;
                        }
                    };""".indent(4).stripTrailing());
    }

    @Test
    void typePatternLabelWithGuardRendersTypeBindingAndWhenClause() {
        me.supcheg.javafile.type.TypeRef stringType =
                me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String"));
        Stmt stmt = new SwitchStmt(
                cb.field("obj"),
                java.util.List.of(new SwitchCase(
                        java.util.List.of(new TypePatternLabel(
                                stringType, "s", Optional.of(cb.gt(cb.call(cb.field("s"), "length"), cb.literal(0))))),
                        new ExprCaseBody(cb.field("s")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (obj) {
                        case String s when s.length() > 0 -> s;
                    }""".indent(4).stripTrailing());
    }

    @Test
    void switchCaseWithMultipleLabelsIncludingDefaultRendersEachLabel() {
        Stmt stmt = new SwitchStmt(
                cb.field("day"),
                java.util.List.of(new SwitchCase(
                        java.util.List.of(new ConstantLabel(cb.literal("MON")), new DefaultLabel()),
                        new ExprCaseBody(cb.literal(1)))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (day) {
                        case "MON", default -> 1;
                    }""".indent(4).stripTrailing());
    }

    @Test
    void switchCaseWithAThrowBodyRendersThrowKeywordAndException() {
        me.supcheg.javafile.type.TypeRef exceptionType = me.supcheg.javafile.type.Types.of(
                java.lang.constant.ClassDesc.of("java.lang", "IllegalStateException"));
        Stmt stmt = new SwitchStmt(
                cb.field("day"),
                java.util.List.of(new SwitchCase(
                        java.util.List.of(new DefaultLabel()),
                        new me.supcheg.javafile.code.ThrowCaseBody(cb.new_(exceptionType, cb.literal("bad day"))))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (day) {
                        default -> throw new IllegalStateException("bad day");
                    }""".indent(4).stripTrailing());
    }

    @Test
    void forStmtUpdateNotEndingInSemicolonIsNotStripped() {
        Stmt update = new WhileStmt(cb.literal(true), new CodeBody(java.util.List.of()));
        Stmt stmt = new me.supcheg.javafile.code.ForStmt(
                Optional.empty(), Optional.empty(), Optional.of(update), new CodeBody(java.util.List.of()));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    for (; ; while (true) {\n}) {\n    }");
    }

    @Test
    void throwStmtRendersThrowKeywordAndException() {
        me.supcheg.javafile.type.TypeRef exceptionType = me.supcheg.javafile.type.Types.of(
                java.lang.constant.ClassDesc.of("java.lang", "IllegalStateException"));
        Stmt stmt = new me.supcheg.javafile.code.ThrowStmt(cb.new_(exceptionType, cb.literal("bad")));

        assertThat(ExprRenderer.renderStmt(
                        stmt,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    throw new IllegalStateException(\"bad\");");
    }

    @Test
    void breakAndContinueRenderAsBareKeywords() {
        assertThat(ExprRenderer.renderStmt(
                        new me.supcheg.javafile.code.BreakStmt(),
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    break;");
        assertThat(ExprRenderer.renderStmt(
                        new me.supcheg.javafile.code.ContinueStmt(),
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    continue;");
    }

    @Test
    void lambdaWithExpressionBodyRendersParenthesizedParams() {
        Expr lambda = cb.lambda(java.util.List.of("name"), cb.call(cb.field("name"), "toUpperCase"));

        assertThat(ExprRenderer.renderExpr(lambda, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(name) -> name.toUpperCase()");
    }

    @Test
    void lambdaWithBlockBodyRendersBracedBlock() {
        Expr lambda =
                cb.lambda(java.util.List.of("a", "b"), body -> body.return_(cb.add(cb.field("a"), cb.field("b"))));

        assertThat(ExprRenderer.renderExpr(
                        lambda,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("(a, b) -> {\n        return a + b;\n    }");
    }

    @Test
    void lambdaWithNoParamsRendersEmptyParens() {
        Expr lambda = cb.lambda(java.util.List.of(), cb.literal(1));

        assertThat(ExprRenderer.renderExpr(lambda, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("() -> 1");
    }

    @Test
    void typedLambdaRendersParameterTypes() {
        ImportManager imports = new ImportManager("p");
        Expr lambda = cb.typedLambda(
                java.util.List.of(new me.supcheg.javafile.model.Param(
                        "name",
                        me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String")))),
                cb.call(cb.field("name"), "length"));

        assertThat(ExprRenderer.renderExpr(lambda, Context.of(standardFormat(), imports)))
                .isEqualTo("(String name) -> name.length()");
    }

    @Test
    void typedLambdaWithBlockBodyRendersBracedBlock() {
        ImportManager imports = new ImportManager("p");
        Expr lambda = cb.typedLambda(
                java.util.List.of(new me.supcheg.javafile.model.Param(
                        "name",
                        me.supcheg.javafile.type.Types.of(java.lang.constant.ClassDesc.of("java.lang", "String")))),
                body -> body.return_(cb.call(cb.field("name"), "length")));

        assertThat(ExprRenderer.renderExpr(
                        lambda, Context.of(standardFormat(), imports).withIncreasedPad()))
                .isEqualTo("(String name) -> {\n        return name.length();\n    }");
    }

    @Test
    void tryFinallyRendersWithEmptyCatchesAndFinallyBlock() {
        TryStmt stmt = new TryStmt.WithFinally(
                List.of(),
                new CodeBody(List.of(new ExprStmt(cb.call(cb.field("resource"), "use")))),
                List.of(),
                new CodeBody(List.of(new ExprStmt(cb.call(cb.field("resource"), "close")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        try {
                            resource.use();
                        } finally {
                            resource.close();
                        }""".indent(4).stripTrailing());
    }

    @Test
    void tryCatchRendersSingleCatchClauseWithoutFinally() {
        ClassOrInterfaceTypeRef ioException = Types.of(ClassDesc.of("java.io", "IOException"));
        TryStmt stmt = new TryStmt.CatchOnly(
                List.of(),
                new CodeBody(List.of(new ExprStmt(cb.call("risky")))),
                NonEmptyList.copyOf(List.of(new CatchClause(
                        NonEmptyList.copyOf(List.of(ioException)),
                        "e",
                        new CodeBody(List.of(new ExprStmt(cb.call(cb.field("e"), "printStackTrace"))))))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        try {
                            risky();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }""".indent(4).stripTrailing());
    }

    @Test
    void multiCatchJoinsExceptionTypesWithPipe() {
        ClassOrInterfaceTypeRef ioException = Types.of(ClassDesc.of("java.io", "IOException"));
        ClassOrInterfaceTypeRef sqlException = Types.of(ClassDesc.of("java.sql", "SQLException"));
        TryStmt stmt = new TryStmt.CatchOnly(
                List.of(),
                CodeBody.EMPTY,
                NonEmptyList.copyOf(List.of(new CatchClause(
                        NonEmptyList.copyOf(List.of(ioException, sqlException)), "e", CodeBody.EMPTY))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        try {
                        } catch (IOException | SQLException e) {
                        }""".indent(4).stripTrailing());
    }

    @Test
    void tryCatchFinallyRendersBothCatchAndFinally() {
        ClassOrInterfaceTypeRef ioException = Types.of(ClassDesc.of("java.io", "IOException"));
        TryStmt stmt = new TryStmt.WithFinally(
                List.of(),
                CodeBody.EMPTY,
                List.of(new CatchClause(NonEmptyList.copyOf(List.of(ioException)), "e", CodeBody.EMPTY)),
                CodeBody.EMPTY);

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        try {
                        } catch (IOException e) {
                        } finally {
                        }""".indent(4).stripTrailing());
    }

    @Test
    void tryWithResourcesRendersDeclaredAndExistingForms() {
        ClassOrInterfaceTypeRef ioException = Types.of(ClassDesc.of("java.io", "IOException"));
        TryStmt stmt = new TryStmt.CatchOnly(
                List.of(new Resource.Declared(Optional.empty(), "r1", cb.call("open")), new Resource.Existing("r2")),
                CodeBody.EMPTY,
                NonEmptyList.copyOf(
                        List.of(new CatchClause(NonEmptyList.copyOf(List.of(ioException)), "e", CodeBody.EMPTY))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        try (var r1 = open(); r2) {
                        } catch (IOException e) {
                        }""".indent(4).stripTrailing());
    }
}
