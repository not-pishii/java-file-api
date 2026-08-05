package me.supcheg.javafile.code;

import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ExprsTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");

    @Test
    void literalsProduceTheMatchingLiteralExpr() {
        assertThat(Exprs.literal("a")).isEqualTo(new StringLiteral("a"));
        assertThat(Exprs.literal(1)).isEqualTo(new IntLiteral(1));
        assertThat(Exprs.literal(1L)).isEqualTo(new LongLiteral(1L));
        assertThat(Exprs.literal(1.5)).isEqualTo(new DoubleLiteral(1.5));
        assertThat(Exprs.literal(true)).isEqualTo(new BooleanLiteral(true));
        assertThat(Exprs.literalNull()).isEqualTo(new NullLiteral());
    }

    @Test
    void unqualifiedFieldAndCallHaveNoTarget() {
        assertThat(Exprs.field("bundle")).isEqualTo(new FieldAccessExpr(Optional.empty(), "bundle"));
        assertThat(Exprs.call("size")).isEqualTo(new MethodCallExpr(Optional.empty(), "size", List.of()));
    }

    @Test
    void staticCallCarriesTheDeclaringType() {
        assertThat(Exprs.staticCall(Types.of(STRING), "valueOf", Exprs.literal(1)))
                .isEqualTo(new StaticMethodCallExpr(Types.of(STRING), "valueOf", List.of(new IntLiteral(1))));
    }

    @Test
    void binaryAndUnaryOperatorsWrapTheirOperands() {
        assertThat(Exprs.add(Exprs.literal(1), Exprs.literal(2)))
                .isEqualTo(new BinaryExpr(new IntLiteral(1), BinaryOp.ADD, new IntLiteral(2)));
        assertThat(Exprs.not(Exprs.literal(true))).isEqualTo(new UnaryExpr(UnaryOp.NOT, new BooleanLiteral(true)));
    }

    @Test
    void newDiamondKeepsTheRawTypeOnly() {
        ClassDesc list = ClassDesc.of("java.util", "ArrayList");
        assertThat(Exprs.newDiamond(list)).isEqualTo(new NewExpr(new DiamondNewTarget(list), List.of()));
    }

    @Test
    void factoriesReturnConcreteTypesUsableInStatementPositions() {
        StatementExpr call = Exprs.call("run");
        AssignTarget target = Exprs.field("x");
        assertThat(call).isNotNull();
        assertThat(target).isNotNull();
    }

    @Test
    void textBlockThisAndSuperProduceTheirRespectiveExpr() {
        assertThat(Exprs.textBlock("line")).isEqualTo(new TextBlockExpr("line"));
        assertThat(Exprs.this_()).isEqualTo(new ThisExpr());
        assertThat(Exprs.super_()).isEqualTo(new SuperExpr());
    }

    @Test
    void staticFieldCarriesTheDeclaringType() {
        assertThat(Exprs.staticField(Types.of(STRING), "CASE_INSENSITIVE_ORDER"))
                .isEqualTo(new StaticFieldAccessExpr(Types.of(STRING), "CASE_INSENSITIVE_ORDER"));
    }

    @Test
    void newExprCarriesTypeAndArguments() {
        assertThat(Exprs.new_(Types.of(STRING), Exprs.literal("a")))
                .isEqualTo(new NewExpr(new TypedNewTarget(Types.of(STRING)), List.of(new StringLiteral("a"))));
    }

    @Test
    void newAnonymousCarriesTheAnonymousBodyMembers() {
        FieldDecl field =
                new FieldDecl("seen", PrimitiveTypeRef.INT, List.of(), Set.of(Modifier.PRIVATE), Optional.empty());

        assertThat(Exprs.newAnonymous(Types.of(STRING), List.of(), b -> b.accept(field)))
                .isEqualTo(new NewExpr(new TypedNewTarget(Types.of(STRING)), List.of(), Optional.of(List.of(field))));
    }

    @Test
    void newArrayAndNewArrayOfProduceTheirRespectiveExpr() {
        assertThat(Exprs.newArray(PrimitiveTypeRef.INT, Exprs.literal(3)))
                .isEqualTo(
                        new ArrayCreationExpr(PrimitiveTypeRef.INT, new NonEmptyList<>(new IntLiteral(3), List.of())));
        assertThat(Exprs.newArrayOf(PrimitiveTypeRef.INT, Exprs.literal(1), Exprs.literal(2)))
                .isEqualTo(
                        new ArrayInitializerExpr(PrimitiveTypeRef.INT, List.of(new IntLiteral(1), new IntLiteral(2))));
    }

    @Test
    void classLiteralMethodRefAndConstructorRefProduceTheirRespectiveExpr() {
        assertThat(Exprs.classLiteral(Types.of(STRING))).isEqualTo(new ClassLiteralExpr(Types.of(STRING)));
        assertThat(Exprs.methodRef(Types.of(STRING), "valueOf"))
                .isEqualTo(new MethodRefExpr(new TypeMethodRefTarget(Types.of(STRING)), "valueOf"));
        assertThat(Exprs.constructorRef(Types.of(STRING))).isEqualTo(new ConstructorRefExpr(Types.of(STRING)));
    }

    @Test
    void castAndCondProduceTheirRespectiveExpr() {
        assertThat(Exprs.cast(Types.of(STRING), Exprs.field("x")))
                .isEqualTo(new CastExpr(Types.of(STRING), new FieldAccessExpr(Optional.empty(), "x")));
        assertThat(Exprs.cond(Exprs.literal(true), Exprs.literal(1), Exprs.literal(2)))
                .isEqualTo(new ConditionalExpr(new BooleanLiteral(true), new IntLiteral(1), new IntLiteral(2)));
    }

    @Test
    void remainingBinaryOperatorsWrapTheirOperands() {
        Expr left = Exprs.field("a");
        Expr right = Exprs.field("b");

        assertThat(Exprs.sub(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.SUB, right));
        assertThat(Exprs.mul(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.MUL, right));
        assertThat(Exprs.div(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.DIV, right));
        assertThat(Exprs.mod(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.MOD, right));
        assertThat(Exprs.eq(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.EQ, right));
        assertThat(Exprs.neq(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.NEQ, right));
        assertThat(Exprs.lt(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.LT, right));
        assertThat(Exprs.le(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.LE, right));
        assertThat(Exprs.gt(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.GT, right));
        assertThat(Exprs.ge(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.GE, right));
        assertThat(Exprs.and(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.AND, right));
        assertThat(Exprs.or(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.OR, right));
        assertThat(Exprs.bitAnd(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.BIT_AND, right));
        assertThat(Exprs.bitOr(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.BIT_OR, right));
        assertThat(Exprs.bitXor(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.BIT_XOR, right));
        assertThat(Exprs.shl(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.SHL, right));
        assertThat(Exprs.shr(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.SHR, right));
        assertThat(Exprs.ushr(left, right)).isEqualTo(new BinaryExpr(left, BinaryOp.USHR, right));
    }

    @Test
    void remainingUnaryOperatorsWrapTheirOperand() {
        Expr operand = Exprs.field("i");

        assertThat(Exprs.neg(operand)).isEqualTo(new UnaryExpr(UnaryOp.NEG, operand));
        assertThat(Exprs.bitNot(operand)).isEqualTo(new UnaryExpr(UnaryOp.BIT_NOT, operand));
        assertThat(Exprs.unaryPlus(operand)).isEqualTo(new UnaryExpr(UnaryOp.UNARY_PLUS, operand));
        assertThat(Exprs.preIncrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.PRE_INC, operand));
        assertThat(Exprs.preDecrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.PRE_DEC, operand));
        assertThat(Exprs.postIncrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.POST_INC, operand));
        assertThat(Exprs.postDecrement(operand)).isEqualTo(new IncDecExpr(IncDecOp.POST_DEC, operand));
    }

    @Test
    void switchExprProducesASwitchExprUsableAsAValue() {
        Expr expr = Exprs.switchExpr(
                Exprs.field("day"),
                sb -> sb.caseValue(Exprs.literal("MON"), Exprs.literal(1)).defaultValue(Exprs.literal(0)));

        assertThat(expr).isInstanceOf(SwitchExpr.class);
        SwitchExpr switchExpr = (SwitchExpr) expr;
        assertThat(switchExpr.cases().get(0).body()).isEqualTo(new ExprCaseBody(new IntLiteral(1)));
    }

    @Test
    void lambdaWithInferredParamsSupportsExprAndBlockBodies() {
        Expr exprBody = Exprs.lambda(List.of("x"), Exprs.field("x"));
        assertThat(exprBody)
                .isEqualTo(new LambdaExpr(
                        new InferredLambdaParams(List.of("x")),
                        new ExprLambdaBody(new FieldAccessExpr(Optional.empty(), "x"))));

        Expr blockBody = Exprs.lambda(List.of("x"), b -> b.return_(Exprs.field("x")));
        assertThat(blockBody)
                .isEqualTo(new LambdaExpr(
                        new InferredLambdaParams(List.of("x")),
                        new BlockLambdaBody(new CodeBody(
                                List.of(new ReturnStmt(Optional.of(new FieldAccessExpr(Optional.empty(), "x"))))))));
    }

    @Test
    void typedLambdaSupportsExprAndBlockBodies() {
        Param param = new Param("x", Types.of(STRING));

        Expr exprBody = Exprs.typedLambda(List.of(param), Exprs.field("x"));
        assertThat(exprBody)
                .isEqualTo(new LambdaExpr(
                        new TypedLambdaParams(List.of(param)),
                        new ExprLambdaBody(new FieldAccessExpr(Optional.empty(), "x"))));

        Expr blockBody = Exprs.typedLambda(List.of(param), b -> b.return_(Exprs.field("x")));
        assertThat(blockBody)
                .isEqualTo(new LambdaExpr(
                        new TypedLambdaParams(List.of(param)),
                        new BlockLambdaBody(new CodeBody(
                                List.of(new ReturnStmt(Optional.of(new FieldAccessExpr(Optional.empty(), "x"))))))));
    }
}
