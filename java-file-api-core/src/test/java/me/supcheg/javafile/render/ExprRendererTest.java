package me.supcheg.javafile.render;

import me.supcheg.javafile.code.ArrayAccessExpr;
import me.supcheg.javafile.code.AssertStmt;
import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.code.AssignStmt;
import me.supcheg.javafile.code.BlockCaseBody;
import me.supcheg.javafile.code.BreakStmt;
import me.supcheg.javafile.code.CatchClause;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.ConstantLabel;
import me.supcheg.javafile.code.ContinueStmt;
import me.supcheg.javafile.code.DefaultLabel;
import me.supcheg.javafile.code.DoWhileStmt;
import me.supcheg.javafile.code.EmptyStmt;
import me.supcheg.javafile.code.EnhancedForStmt;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.ExprCaseBody;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.FieldAccessExpr;
import me.supcheg.javafile.code.ForStmt;
import me.supcheg.javafile.code.IfStmt;
import me.supcheg.javafile.code.LabeledStmt;
import me.supcheg.javafile.code.LocalTypeDeclStmt;
import me.supcheg.javafile.code.LocalVarDeclStmt;
import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.code.Pattern;
import me.supcheg.javafile.code.PatternLabel;
import me.supcheg.javafile.code.RecordPattern;
import me.supcheg.javafile.code.Resource;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.code.StaticFieldAccessExpr;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.code.SwitchCase;
import me.supcheg.javafile.code.SwitchExpr;
import me.supcheg.javafile.code.SwitchStmt;
import me.supcheg.javafile.code.SynchronizedStmt;
import me.supcheg.javafile.code.ThrowCaseBody;
import me.supcheg.javafile.code.ThrowStmt;
import me.supcheg.javafile.code.TryStmt;
import me.supcheg.javafile.code.TypePattern;
import me.supcheg.javafile.code.WhileStmt;
import me.supcheg.javafile.code.YieldStmt;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static me.supcheg.javafile.code.Exprs.add;
import static me.supcheg.javafile.code.Exprs.bitAnd;
import static me.supcheg.javafile.code.Exprs.bitNot;
import static me.supcheg.javafile.code.Exprs.bitOr;
import static me.supcheg.javafile.code.Exprs.bitXor;
import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.cast;
import static me.supcheg.javafile.code.Exprs.classLiteral;
import static me.supcheg.javafile.code.Exprs.cond;
import static me.supcheg.javafile.code.Exprs.constructorRef;
import static me.supcheg.javafile.code.Exprs.eq;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.gt;
import static me.supcheg.javafile.code.Exprs.lambda;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.literalNull;
import static me.supcheg.javafile.code.Exprs.lt;
import static me.supcheg.javafile.code.Exprs.mul;
import static me.supcheg.javafile.code.Exprs.neg;
import static me.supcheg.javafile.code.Exprs.newAnonymous;
import static me.supcheg.javafile.code.Exprs.newArray;
import static me.supcheg.javafile.code.Exprs.newArrayOf;
import static me.supcheg.javafile.code.Exprs.newDiamond;
import static me.supcheg.javafile.code.Exprs.new_;
import static me.supcheg.javafile.code.Exprs.not;
import static me.supcheg.javafile.code.Exprs.postDecrement;
import static me.supcheg.javafile.code.Exprs.postIncrement;
import static me.supcheg.javafile.code.Exprs.preDecrement;
import static me.supcheg.javafile.code.Exprs.preIncrement;
import static me.supcheg.javafile.code.Exprs.shl;
import static me.supcheg.javafile.code.Exprs.shr;
import static me.supcheg.javafile.code.Exprs.staticField;
import static me.supcheg.javafile.code.Exprs.sub;
import static me.supcheg.javafile.code.Exprs.textBlock;
import static me.supcheg.javafile.code.Exprs.this_;
import static me.supcheg.javafile.code.Exprs.typedLambda;
import static me.supcheg.javafile.code.Exprs.unaryPlus;
import static me.supcheg.javafile.code.Exprs.ushr;
import static me.supcheg.javafile.render.SourceRenderer.standardFormat;
import static org.assertj.core.api.Assertions.assertThat;

class ExprRendererTest {

    @Test
    void fieldAccessWithoutTargetRendersBareName() {
        assertThat(ExprRenderer.renderExpr(field("bundle"), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("bundle");
    }

    @Test
    void fieldAccessWithTargetRendersDottedPath() {
        Expr expr = this_().field("bundle");
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("this.bundle");
    }

    @Test
    void methodCallRendersArgsCommaSeparated() {
        Expr call = field("bundle").call("getString", literal("greeting"));
        assertThat(ExprRenderer.renderExpr(call, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("bundle.getString(\"greeting\")");
    }

    @Test
    void staticFieldAccessRendersTypeDotName() {
        ClassDesc integerType = ClassDesc.of("java.lang", "Integer");
        Expr expr = staticField(integerType, "MAX_VALUE");

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("Integer.MAX_VALUE");
    }

    @Test
    void staticMethodCallRendersArgsCommaSeparated() {
        ClassDesc mathType = ClassDesc.of("java.lang", "Math");
        Expr expr = Exprs.staticCall(mathType, "max", field("a"), field("b"));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("Math.max(a, b)");
    }

    @Test
    void assignStatementWithStaticFieldAccessTargetRendersTargetEqualsValue() {
        ClassDesc counterType = ClassDesc.of("me.supcheg.example", "Counter");
        StaticFieldAccessExpr target = staticField(counterType, "total");
        Expr value = literal(1);

        String rendered = ExprRenderer.renderStmt(
                new AssignStmt(target, AssignOp.ASSIGN, value),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    Counter.total = 1;");
    }

    @Test
    void staticMethodCallAsBareStatementRendersSemicolonTerminated() {
        ClassDesc mathType = ClassDesc.of("java.lang", "Math");
        Stmt stmt = new ExprStmt(Exprs.staticCall(mathType, "max", literal(1), literal(2)));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    Math.max(1, 2);");
    }

    @Test
    void stringLiteralIsEscapedAndQuoted() {
        assertThat(ExprRenderer.renderExpr(literal("a\"b"), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("\"a\\\"b\"");
    }

    @Test
    void textBlockKeepsATrailingBlankLineForAValueEndingInNewline() {
        assertThat(ExprRenderer.renderExpr(
                        textBlock("line one\nline two\n"), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("\"\"\"\n" + "line one\n" + "line two\n" + "\n" + "\"\"\"");
    }

    @Test
    void numericAndBooleanAndNullLiteralsRenderVerbatim() {
        assertThat(ExprRenderer.renderExpr(literal(1), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("1");
        assertThat(ExprRenderer.renderExpr(literal(1L), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("1L");
        assertThat(ExprRenderer.renderExpr(literal(1.5), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("1.5");
        assertThat(ExprRenderer.renderExpr(literal(true), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("true");
        assertThat(ExprRenderer.renderExpr(literal(false), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("false");
        assertThat(ExprRenderer.renderExpr(literalNull(), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("null");
    }

    @Test
    void textBlockIsWrappedInTripleQuotesAndIndented() {
        String rendered = ExprRenderer.renderExpr(
                textBlock("line one\nline two"),
                Context.of(standardFormat(), new ImportManager("p"))
                        .withIncreasedPad()
                        .withIncreasedPad());

        assertThat(rendered).isEqualTo("\"\"\"\n        line one\n        line two\n        \"\"\"");
    }

    @Test
    void returnStatementWithValueRendersSemicolonTerminated() {
        String rendered = ExprRenderer.renderStmt(
                new ReturnStmt(Optional.of(literal(1))),
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
        FieldAccessExpr target = this_().field("bundle");
        Expr value = field("bundle");
        String rendered = ExprRenderer.renderStmt(
                new AssignStmt(target, AssignOp.ASSIGN, value),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());
        assertThat(rendered).isEqualTo("    this.bundle = bundle;");
    }

    @Test
    void assignStatementWithAddAssignRendersPlusEqualsOperator() {
        FieldAccessExpr target = field("total");
        Expr value = field("delta");
        String rendered = ExprRenderer.renderStmt(
                new AssignStmt(target, AssignOp.ADD_ASSIGN, value),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());
        assertThat(rendered).isEqualTo("    total += delta;");
    }

    @Test
    void assignStatementWithShlAssignRendersShiftLeftEqualsOperator() {
        FieldAccessExpr target = field("mask");
        Expr value = literal(1);
        String rendered = ExprRenderer.renderStmt(
                new AssignStmt(target, AssignOp.SHL_ASSIGN, value),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());
        assertThat(rendered).isEqualTo("    mask <<= 1;");
    }

    @Test
    void assignStatementWithUshrAssignRendersUnsignedShiftRightEqualsOperator() {
        FieldAccessExpr target = field("bits");
        Expr value = literal(2);
        String rendered = ExprRenderer.renderStmt(
                new AssignStmt(target, AssignOp.USHR_ASSIGN, value),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());
        assertThat(rendered).isEqualTo("    bits >>>= 2;");
    }

    @Test
    void binaryExprRendersInfixOperator() {
        Expr expr = lt(field("i"), literal(10));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("i < 10");
    }

    @Test
    void unaryExprRendersPrefixAndPostfixCorrectly() {
        assertThat(ExprRenderer.renderExpr(
                        postIncrement(field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("i++");
        assertThat(ExprRenderer.renderExpr(not(field("done")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("!done");
        assertThat(ExprRenderer.renderExpr(neg(field("x")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("-x");
    }

    @Test
    void unaryExprRendersPreIncrementPreDecrementAndPostDecrement() {
        assertThat(ExprRenderer.renderExpr(
                        preIncrement(field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("++i");
        assertThat(ExprRenderer.renderExpr(
                        preDecrement(field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("--i");
        assertThat(ExprRenderer.renderExpr(
                        postDecrement(field("i")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("i--");
    }

    @Test
    void bitwiseBinaryOperatorsRenderInfixSymbol() {
        assertThat(ExprRenderer.renderExpr(
                        bitAnd(field("a"), field("b")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a & b");
        assertThat(ExprRenderer.renderExpr(
                        bitOr(field("a"), field("b")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a | b");
        assertThat(ExprRenderer.renderExpr(
                        bitXor(field("a"), field("b")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a ^ b");
    }

    @Test
    void shiftOperatorsRenderInfixSymbol() {
        assertThat(ExprRenderer.renderExpr(
                        shl(field("a"), field("b")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a << b");
        assertThat(ExprRenderer.renderExpr(
                        shr(field("a"), field("b")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a >> b");
        assertThat(ExprRenderer.renderExpr(
                        ushr(field("a"), field("b")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a >>> b");
    }

    @Test
    void bitwiseNotAndUnaryPlusRenderPrefixSymbol() {
        assertThat(ExprRenderer.renderExpr(bitNot(field("a")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("~a");
        assertThat(ExprRenderer.renderExpr(unaryPlus(field("a")), Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("+a");
    }

    @Test
    void instanceOfWithBindingRendersTheBindingName() {
        TypeRef stringType = Types.of(ClassDesc.of("java.lang", "String"));
        Expr expr = field("obj").instanceOf(stringType, "s");

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("obj instanceof String s");
    }

    @Test
    void instanceOfWithoutBindingOmitsTheBindingName() {
        TypeRef stringType = Types.of(ClassDesc.of("java.lang", "String"));
        Expr expr = field("obj").instanceOf(stringType);

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("obj instanceof String");
    }

    @Test
    void instanceOfWithRecordPatternRendersDeconstructedComponents() {
        TypeRef pointType = Types.of(ClassDesc.of("geom", "Point"));
        Pattern pattern = new RecordPattern(
                pointType,
                List.of(
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("x")),
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("y"))));
        Expr expr = field("shape").instanceOfPattern(pattern);

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("shape instanceof Point(int x, int y)");
    }

    @Test
    void instanceOfWithNestedRecordPatternRendersRecursively() {
        TypeRef innerType = Types.of(ClassDesc.of("geom", "Inner"));
        TypeRef outerType = Types.of(ClassDesc.of("geom", "Outer"));
        Pattern pattern = new RecordPattern(
                outerType,
                List.of(
                        new RecordPattern(
                                innerType,
                                List.of(
                                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("a")),
                                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("b")))),
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("c"))));
        Expr expr = field("shape").instanceOfPattern(pattern);

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("shape instanceof Outer(Inner(int a, int b), int c)");
    }

    @Test
    void castExprRendersParenthesizedTargetTypeBeforeOperand() {
        Expr expr = cast(PrimitiveTypeRef.INT, literal(1.5));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(int) 1.5");
    }

    @Test
    void conditionalExprRendersConditionQuestionMarkWhenTrueColonWhenFalse() {
        Expr expr = cond(lt(field("x"), literal(0)), literal("negative"), literal("non-negative"));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("x < 0 ? \"negative\" : \"non-negative\"");
    }

    @Test
    void classLiteralExprRendersTypeDotClass() {
        ClassDesc stringType = ClassDesc.of("java.lang", "String");
        Expr expr = classLiteral(stringType);

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("String.class");
    }

    @Test
    void typeQualifiedMethodRefRendersTypeColonColonMethod() {
        ClassDesc integerType = ClassDesc.of("java.lang", "Integer");
        Expr expr = Exprs.methodRef(integerType, "parseInt");

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("Integer::parseInt");
    }

    @Test
    void instanceBoundMethodRefRendersExprColonColonMethod() {
        Expr expr = field("name").methodRef("trim");

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("name::trim");
    }

    @Test
    void constructorRefRendersTypeColonColonNew() {
        ClassDesc stringType = ClassDesc.of("java.lang", "String");
        Expr expr = constructorRef(stringType);

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("String::new");
    }

    @Test
    void newExprRendersTypeAndCommaSeparatedArguments() {
        ClassDesc exceptionType = ClassDesc.of("java.lang", "IllegalStateException");
        Expr expr = new_(exceptionType, literal("bad state"));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("new IllegalStateException(\"bad state\")");
    }

    @Test
    void newDiamondRendersEmptyTypeArgumentList() {
        Expr expr = newDiamond(ClassDesc.of("me.supcheg.example", "Impl"), field("renderer"));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("me.supcheg.example"))))
                .isEqualTo("new Impl<>(renderer)");
    }

    @Test
    void newExprWithoutAnonymousBodyRendersNoTrailingBraces() {
        ClassDesc objectType = ClassDesc.of("java.lang", "Object");
        Expr expr = new_(objectType);

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("new Object()");
    }

    @Test
    void newExprWithAnonymousBodyRendersBracesAndIndentedMembers() {
        ClassOrInterfaceTypeRef runnableType = Types.of(ClassDesc.of("java.lang", "Runnable"));
        Expr expr = newAnonymous(runnableType, List.of(), b -> b.withVoidMethod("run", mb -> {}));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("new Runnable() {\n" + "    public void run() {\n" + "    }\n" + "}");
    }

    @Test
    void arrayAccessRendersArrayBracketIndexBracket() {
        Expr expr = field("array").arrayAccess(field("index"));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("array[index]");
    }

    @Test
    void arrayCreationByDimensionRendersNewComponentTypeAndBracketedSize() {
        Expr expr = newArray(PrimitiveTypeRef.INT, literal(3));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("new int[3]");
    }

    @Test
    void arrayCreationByMultipleDimensionsRendersEachBracketedSize() {
        Expr expr = newArray(PrimitiveTypeRef.INT, literal(3), literal(4));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("new int[3][4]");
    }

    @Test
    void arrayInitializerRendersNewComponentTypeEmptyBracketsAndBracedElements() {
        Expr expr = newArrayOf(PrimitiveTypeRef.INT, literal(1), literal(2), literal(3));

        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("new int[] {1, 2, 3}");
    }

    @Test
    void assignStatementWithArrayAccessTargetRendersTargetEqualsValue() {
        ArrayAccessExpr target = field("values").arrayAccess(literal(0));
        Expr value = literal(1);

        String rendered = ExprRenderer.renderStmt(
                new AssignStmt(target, AssignOp.ASSIGN, value),
                Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    values[0] = 1;");
    }

    @Test
    void typedLocalVarDeclRendersTheDeclaredType() {
        Stmt stmt = new LocalVarDeclStmt.Typed(PrimitiveTypeRef.INT, "count", Optional.of(literal(0)));
        assertThat(ExprRenderer.renderStmt(
                        stmt,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    int count = 0;");
    }

    @Test
    void typedLocalVarDeclWithoutInitializerOmitsAssignment() {
        Stmt stmt = new LocalVarDeclStmt.Typed(PrimitiveTypeRef.INT, "count", Optional.empty());
        assertThat(ExprRenderer.renderStmt(
                        stmt,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    int count;");
    }

    @Test
    void untypedLocalVarDeclRendersVar() {
        Stmt stmt = new LocalVarDeclStmt.Inferred("name", literal("x"));
        assertThat(ExprRenderer.renderStmt(
                        stmt,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    var name = \"x\";");
    }

    @Test
    void ifStmtRendersBracesAndOptionalElseIfElseChain() {
        CodeBuilder body = new CodeBuilder();
        body.if_(
                lt(field("x"), literal(0)),
                ib -> ib.then(b -> b.return_(literal("negative")))
                        .elseIf(eq(field("x"), literal(0)), b -> b.return_(literal("zero")))
                        .else_(b -> b.return_(literal("positive"))));
        Stmt stmt = body.build().statements().get(0);

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
                lt(field("i"), literal(10)), new CodeBody(List.of(new ExprStmt(postIncrement(field("i"))))));

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
                new CodeBody(List.of(new ExprStmt(postIncrement(field("i"))))), lt(field("i"), literal(10)));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        do {
                            i++;
                        } while (i < 10);""".indent(4).stripTrailing());
    }

    @Test
    void classicForStmtRendersInitConditionUpdateAndBody() {
        LocalVarDeclStmt init = new LocalVarDeclStmt.Typed(PrimitiveTypeRef.INT, "i", Optional.of(literal(0)));
        ExprStmt update = new ExprStmt(postIncrement(field("i")));
        Stmt stmt = new ForStmt(
                Optional.of(init),
                Optional.of(lt(field("i"), literal(10))),
                Optional.of(update),
                new CodeBody(List.of(new ExprStmt(call("use")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        for (int i = 0; i < 10; i++) {
                            use();
                        }""".indent(4).stripTrailing());
    }

    @Test
    void enhancedForStmtRendersElementTypeAndIterable() {
        TypeRef stringType = Types.of(ClassDesc.of("java.lang", "String"));
        Stmt stmt = new EnhancedForStmt(
                stringType, "item", field("items"), new CodeBody(List.of(new ExprStmt(call("use")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        for (String item : items) {
                            use();
                        }""".indent(4).stripTrailing());
    }

    @Test
    void switchStmtRendersArrowCasesAndDefault() {
        Stmt stmt = new SwitchStmt(
                field("day"),
                List.of(
                        new SwitchCase(
                                new NonEmptyList<>(new ConstantLabel(literal("MON")), List.of()),
                                new ExprCaseBody(literal(1))),
                        new SwitchCase(
                                new NonEmptyList<>(new DefaultLabel(), List.of()), new ExprCaseBody(literal(0)))));

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
                field("day"),
                List.of(
                        new SwitchCase(
                                new NonEmptyList<>(new ConstantLabel(literal("MON")), List.of()),
                                new ExprCaseBody(literal(1))),
                        new SwitchCase(
                                new NonEmptyList<>(new DefaultLabel(), List.of()), new ExprCaseBody(literal(0)))));

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
                field("day"),
                List.of(new SwitchCase(
                        new NonEmptyList<>(new ConstantLabel(literal("MON")), List.of()),
                        new BlockCaseBody(new CodeBody(List.of(new YieldStmt(literal(1))))))));

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
        TypeRef stringType = Types.of(ClassDesc.of("java.lang", "String"));
        Stmt stmt = new SwitchStmt(
                field("obj"),
                List.of(new SwitchCase(
                        new NonEmptyList<>(
                                new PatternLabel(
                                        new TypePattern(stringType, Optional.of("s")),
                                        Optional.of(gt(field("s").call("length"), literal(0)))),
                                List.of()),
                        new ExprCaseBody(field("s")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (obj) {
                        case String s when s.length() > 0 -> s;
                    }""".indent(4).stripTrailing());
    }

    @Test
    void recordPatternCaseLabelRendersDeconstructedComponents() {
        TypeRef pointType = Types.of(ClassDesc.of("geom", "Point"));
        Pattern pattern = new RecordPattern(
                pointType,
                List.of(
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("x")),
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("y"))));
        Stmt stmt = new SwitchStmt(
                field("shape"),
                List.of(new SwitchCase(
                        new NonEmptyList<>(new PatternLabel(pattern, Optional.empty()), List.of()),
                        new ExprCaseBody(add(field("x"), field("y"))))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (shape) {
                        case Point(int x, int y) -> x + y;
                    }""".indent(4).stripTrailing());
    }

    @Test
    void switchCaseWithMultipleLabelsIncludingDefaultRendersEachLabel() {
        Stmt stmt = new SwitchStmt(
                field("day"),
                List.of(new SwitchCase(
                        new NonEmptyList<>(new ConstantLabel(literal("MON")), List.of(new DefaultLabel())),
                        new ExprCaseBody(literal(1)))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (day) {
                        case "MON", default -> 1;
                    }""".indent(4).stripTrailing());
    }

    @Test
    void switchCaseWithAThrowBodyRendersThrowKeywordAndException() {
        ClassDesc exceptionType = ClassDesc.of("java.lang", "IllegalStateException");
        Stmt stmt = new SwitchStmt(
                field("day"),
                List.of(new SwitchCase(
                        new NonEmptyList<>(new DefaultLabel(), List.of()),
                        new ThrowCaseBody(new_(exceptionType, literal("bad day"))))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                    switch (day) {
                        default -> throw new IllegalStateException("bad day");
                    }""".indent(4).stripTrailing());
    }

    @Test
    void forStmtUpdateNotEndingInSemicolonIsNotStripped() {
        Stmt update = new WhileStmt(literal(true), new CodeBody(List.of()));
        Stmt stmt = new ForStmt(Optional.empty(), Optional.empty(), Optional.of(update), new CodeBody(List.of()));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    for (; ; while (true) {\n}) {\n    }");
    }

    @Test
    void throwStmtRendersThrowKeywordAndException() {
        ClassDesc exceptionType = ClassDesc.of("java.lang", "IllegalStateException");
        Stmt stmt = new ThrowStmt(new_(exceptionType, literal("bad")));

        assertThat(ExprRenderer.renderStmt(
                        stmt,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    throw new IllegalStateException(\"bad\");");
    }

    @Test
    void breakAndContinueRenderAsBareKeywords() {
        assertThat(ExprRenderer.renderStmt(
                        new BreakStmt(Optional.empty()),
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    break;");
        assertThat(ExprRenderer.renderStmt(
                        new ContinueStmt(Optional.empty()),
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    continue;");
    }

    @Test
    void breakAndContinueRenderWithLabels() {
        assertThat(ExprRenderer.renderStmt(
                        new BreakStmt(Optional.of("label")),
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    break label;");
        assertThat(ExprRenderer.renderStmt(
                        new ContinueStmt(Optional.of("label")),
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("    continue label;");
    }

    @Test
    void labeledStmtWrappingASimpleStatementRendersLabelColonStatement() {
        Stmt stmt = new LabeledStmt("outer", new BreakStmt(Optional.empty()));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    outer: break;");
    }

    @Test
    void labeledStmtWrappingANestedBlockKeepsTheOriginalIndentation() {
        Stmt inner = new EnhancedForStmt(
                Types.of(ClassDesc.of("java.lang", "Integer")),
                "i",
                field("items"),
                new CodeBody(List.of(new IfStmt(
                        eq(field("i"), literal(1)),
                        new CodeBody(List.of(new BreakStmt(Optional.of("outer")))),
                        List.of(),
                        Optional.empty()))));
        Stmt stmt = new LabeledStmt("outer", inner);

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        outer: for (Integer i : items) {
                            if (i == 1) {
                                break outer;
                            }
                        }""".indent(4).stripTrailing());
    }

    @Test
    void synchronizedStmtRendersLockExpressionAndBracedBody() {
        Stmt stmt = new SynchronizedStmt(this_(), new CodeBody(List.of(new ExprStmt(call("notifyAll")))));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        synchronized (this) {
                            notifyAll();
                        }""".indent(4).stripTrailing());
    }

    @Test
    void assertStmtWithoutMessageRendersBareCondition() {
        Stmt stmt = new AssertStmt(gt(field("value"), literal(0)), Optional.empty());

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    assert value > 0;");
    }

    @Test
    void assertStmtWithMessageRendersConditionAndMessage() {
        Stmt stmt = new AssertStmt(gt(field("value"), literal(0)), Optional.of(literal("value must be positive")));

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    assert value > 0 : \"value must be positive\";");
    }

    @Test
    void emptyStmtRendersBareSemicolon() {
        Stmt stmt = new EmptyStmt();

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("    ;");
    }

    @Test
    void localTypeDeclStmtRendersTheNestedTypeIndentedRelativeToTheEnclosingMethod() {
        ClassDecl localCounter = new ClassDecl(
                ClassDesc.of("Counter"),
                List.of(),
                Set.of(Modifier.FINAL),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of(new FieldDecl("value", PrimitiveTypeRef.INT, List.of(), Set.of(), Optional.of(literal(0)))));
        Stmt stmt = new LocalTypeDeclStmt(localCounter);

        String rendered = ExprRenderer.renderStmt(
                stmt, Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad());

        assertThat(rendered).isEqualTo("""
                        final class Counter {
                            int value = 0;
                        }""".indent(4).stripTrailing());
    }

    @Test
    void lambdaWithExpressionBodyRendersParenthesizedParams() {
        Expr lambda = lambda(List.of("name"), field("name").call("toUpperCase"));

        assertThat(ExprRenderer.renderExpr(lambda, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(name) -> name.toUpperCase()");
    }

    @Test
    void lambdaWithBlockBodyRendersBracedBlock() {
        Expr lambda = lambda(List.of("a", "b"), body -> body.return_(add(field("a"), field("b"))));

        assertThat(ExprRenderer.renderExpr(
                        lambda,
                        Context.of(standardFormat(), new ImportManager("p")).withIncreasedPad()))
                .isEqualTo("(a, b) -> {\n        return a + b;\n    }");
    }

    @Test
    void lambdaWithNoParamsRendersEmptyParens() {
        Expr lambda = lambda(List.of(), literal(1));

        assertThat(ExprRenderer.renderExpr(lambda, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("() -> 1");
    }

    @Test
    void typedLambdaRendersParameterTypes() {
        ImportManager imports = new ImportManager("p");
        Expr lambda = typedLambda(
                List.of(new Param("name", Types.of(ClassDesc.of("java.lang", "String")))),
                field("name").call("length"));

        assertThat(ExprRenderer.renderExpr(lambda, Context.of(standardFormat(), imports)))
                .isEqualTo("(String name) -> name.length()");
    }

    @Test
    void typedLambdaWithBlockBodyRendersBracedBlock() {
        ImportManager imports = new ImportManager("p");
        Expr lambda = typedLambda(
                List.of(new Param("name", Types.of(ClassDesc.of("java.lang", "String")))),
                body -> body.return_(field("name").call("length")));

        assertThat(ExprRenderer.renderExpr(
                        lambda, Context.of(standardFormat(), imports).withIncreasedPad()))
                .isEqualTo("(String name) -> {\n        return name.length();\n    }");
    }

    @Test
    void tryFinallyRendersWithEmptyCatchesAndFinallyBlock() {
        TryStmt stmt = new TryStmt.WithFinally(
                List.of(),
                new CodeBody(List.of(new ExprStmt(field("resource").call("use")))),
                List.of(),
                new CodeBody(List.of(new ExprStmt(field("resource").call("close")))));

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
                new CodeBody(List.of(new ExprStmt(call("risky")))),
                NonEmptyList.copyOf(List.of(new CatchClause(
                        NonEmptyList.copyOf(List.of(ioException)),
                        "e",
                        new CodeBody(List.of(new ExprStmt(field("e").call("printStackTrace"))))))));

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
                List.of(new Resource.Declared(Optional.empty(), "r1", call("open")), new Resource.Existing("r2")),
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

    @Test
    void multiplicationOfAnAdditionParenthesizesTheAddition() {
        Expr expr = mul(add(field("a"), field("b")), field("c"));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(a + b) * c");
    }

    @Test
    void additionOfAMultiplicationNeedsNoParentheses() {
        Expr expr = add(field("a"), mul(field("b"), field("c")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a + b * c");
    }

    @Test
    void subtractionIsLeftAssociativeWithoutParenthesesOnTheLeft() {
        Expr expr = sub(sub(field("a"), field("b")), field("c"));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a - b - c");
    }

    @Test
    void subtractionOfASubtractionOnTheRightNeedsParentheses() {
        Expr expr = sub(field("a"), sub(field("b"), field("c")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a - (b - c)");
    }

    @Test
    void negationOfANegationParenthesizesTheInnerOperand() {
        Expr expr = neg(neg(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("-(-x)");
    }

    @Test
    void unaryPlusOfAUnaryPlusParenthesizesTheInnerOperand() {
        Expr expr = unaryPlus(unaryPlus(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("+(+x)");
    }

    @Test
    void negationOfAPreIncrementNeedsNoParenthesesBetweenDifferentSymbols() {
        Expr expr = neg(preIncrement(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("-++x");
    }

    @Test
    void castOfAnAdditionParenthesizesTheAddition() {
        Expr expr = cast(PrimitiveTypeRef.INT, add(field("a"), field("b")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(int) (a + b)");
    }

    @Test
    void nestedCastsNeedNoParenthesesBetweenEachOther() {
        Expr expr = cast(PrimitiveTypeRef.INT, cast(PrimitiveTypeRef.LONG, field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(int) (long) x");
    }

    @Test
    void conditionalExprUsedAsAnotherConditionalsConditionIsParenthesized() {
        Expr inner = cond(literal(true), literal(1), literal(2));
        Expr expr = cond(inner, literal(3), literal(4));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(true ? 1 : 2) ? 3 : 4");
    }

    @Test
    void conditionalExprNestedInTheWhenTrueBranchNeedsNoParentheses() {
        Expr inner = cond(literal(true), literal(1), literal(2));
        Expr expr = cond(lt(field("x"), literal(0)), inner, literal(4));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("x < 0 ? true ? 1 : 2 : 4");
    }

    @Test
    void instanceOfTargetThatIsARelationalExpressionNeedsNoExtraParentheses() {
        TypeRef stringType = Types.of(ClassDesc.of("java.lang", "String"));
        Expr expr = lt(field("a"), field("b")).instanceOf(stringType);
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("a < b instanceof String");
    }

    @Test
    void fieldAccessTargetThatIsAnAdditionParenthesizesTheAddition() {
        Expr expr = add(field("a"), field("b")).field("length");
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(a + b).length");
    }

    @Test
    void methodCallTargetThatIsACastParenthesizesTheCast() {
        TypeRef stringType = Types.of(ClassDesc.of("java.lang", "String"));
        Expr expr = cast(stringType, field("o")).call("trim");
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("((String) o).trim()");
    }

    @Test
    void arrayAccessTargetThatIsAConditionalParenthesizesTheConditional() {
        Expr expr = cond(field("c"), field("x"), field("y")).arrayAccess(literal(0));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(c ? x : y)[0]");
    }

    @Test
    void arrayAccessOfAFreshlyCreatedArrayParenthesizesTheCreation() {
        Expr expr = newArray(PrimitiveTypeRef.INT, literal(3)).arrayAccess(literal(0));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(new int[3])[0]");
    }

    @Test
    void arrayAccessOfAnArrayInitializerParenthesizesTheInitializer() {
        Expr expr = newArrayOf(PrimitiveTypeRef.INT, literal(1), literal(2)).arrayAccess(literal(0));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(new int[] {1, 2})[0]");
    }

    @Test
    void methodRefInstanceThatIsAnAdditionParenthesizesTheAddition() {
        Expr expr = add(field("a"), field("b")).methodRef("hashCode");
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(a + b)::hashCode");
    }

    @Test
    void referenceTypeCastOfUnaryMinusParenthesizesTheOperand() {
        TypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));
        Expr expr = cast(integerType, neg(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(Integer) (-x)");
    }

    @Test
    void primitiveTypeCastOfUnaryMinusNeedsNoExtraParentheses() {
        Expr expr = cast(PrimitiveTypeRef.INT, neg(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(int) -x");
    }

    @Test
    void referenceTypeCastOfBitwiseNotNeedsNoExtraParentheses() {
        TypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));
        Expr expr = cast(integerType, bitNot(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(Integer) ~x");
    }

    @Test
    void referenceTypeCastOfUnaryPlusParenthesizesTheOperand() {
        TypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));
        Expr expr = cast(integerType, unaryPlus(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(Integer) (+x)");
    }

    @Test
    void referenceTypeCastOfPreIncrementParenthesizesTheOperand() {
        TypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));
        Expr expr = cast(integerType, preIncrement(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(Integer) (++x)");
    }

    @Test
    void referenceTypeCastOfPreDecrementParenthesizesTheOperand() {
        TypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));
        Expr expr = cast(integerType, preDecrement(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(Integer) (--x)");
    }

    @Test
    void referenceTypeCastOfPostIncrementNeedsNoExtraParentheses() {
        TypeRef integerType = Types.of(ClassDesc.of("java.lang", "Integer"));
        Expr expr = cast(integerType, postIncrement(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(Integer) x++");
    }

    @Test
    void arrayTypeCastOfUnaryMinusIsTreatedAsAReferenceTypeCastAndParenthesizesTheOperand() {
        Expr expr = cast(new ArrayTypeRef(PrimitiveTypeRef.INT), neg(field("x")));
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("(int[]) (-x)");
    }

    @Test
    void conditionalExprNestedInTheWhenFalseBranchNeedsNoParentheses() {
        Expr inner = cond(literal(true), literal(1), literal(2));
        Expr expr = cond(lt(field("x"), literal(0)), literal(4), inner);
        assertThat(ExprRenderer.renderExpr(expr, Context.of(standardFormat(), new ImportManager("p"))))
                .isEqualTo("x < 0 ? 4 : true ? 1 : 2");
    }
}
