package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.jdk.List_;
import me.supcheg.javafile.facts.jdk.Object_;
import me.supcheg.javafile.facts.jdk.String_;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// What cannot be rendered as the fact says is rejected when it is built
/// (§6.1): an unchecked cast, `instanceof` or generic array creation (B13),
/// a conditional mixing primitive and reference branches (M2), and `new` of
/// a wildcard parameterization.
class FactPinningChecksTest {

    private static final String REIFIABLE = "needs a reifiable type (JLS 4.7)";

    private final List_<String> strings = new List_<>(String_.TOKEN);

    @Test
    void castCheckedRejectsANonReifiableType() {
        Expr<Object> o = literalNull(Object_.TOKEN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> castChecked(strings.token, o))
                .withMessageContaining("castChecked " + REIFIABLE)
                .withMessageContaining("java.util.List<java.lang.String> is not");
    }

    @Test
    void newArrayRejectsAGenericArrayCreation() {
        TypeVarToken<Object> t = UnsafeFacts.typeVarToken(Types.typeVar("T"), ConstantDescs.CD_Object);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> newArray(ArrayToken.of(strings.token), literal(1)))
                .withMessageContaining("newArray " + REIFIABLE);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> newArray(ArrayToken.of(t), literal(1)))
                .withMessageContaining("newArray " + REIFIABLE);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> newArray(ArrayToken.of(ArrayToken.of(strings.token)), literal(1)))
                .withMessageContaining("newArray " + REIFIABLE);
    }

    @Test
    void anInstanceofPatternRejectsANonReifiableType() {
        Body<Prim.Int> body = Body.root("body");

        Scopes.within(body, () -> {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> body.ifInstanceOf(literalNull(Object_.TOKEN), strings.token, (_, _) -> {}))
                    .withMessageContaining("an instanceof pattern " + REIFIABLE);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> body.ifInstanceOfElse(
                            literalNull(Object_.TOKEN),
                            strings.token,
                            (t, _) -> t.return_(literal(1)),
                            e -> e.return_(literal(2))))
                    .withMessageContaining("an instanceof pattern " + REIFIABLE);
            return body.return_(literal(0));
        });
    }

    @Test
    void reifiableTypesAreAccepted() {
        var anyList = UnsafeFacts.<List<?>>interfaceToken(
                Types.parameterized(ConstantDescs.CD_List, Types.unbounded()), MethodTable.EMPTY);
        var rawList = UnsafeFacts.<List<?>>interfaceToken(Types.LIST, MethodTable.EMPTY);

        assertThat(Tokens.isReifiable(anyList)).isTrue();
        assertThat(Tokens.isReifiable(rawList)).isTrue();
        assertThat(Tokens.isReifiable(PrimitiveToken.INT.array())).isTrue();
        assertThat(Tokens.isReifiable(ArrayToken.of(ArrayToken.of(anyList)))).isTrue();
        assertThat(Tokens.isReifiable(strings.token)).isFalse();
    }

    @Test
    void castCheckedToTheOperandsOwnTypeCastsNothing() {
        Expr<String> s = literal("s");

        assertThat(castChecked(String_.TOKEN, s).node()).isSameAs(s.node());
        assertThat(castChecked(String_.TOKEN, literalNull(Object_.TOKEN)).node())
                .isInstanceOf(Node.Cast.class);
    }

    @Test
    void condRejectsMixingPrimitiveAndReferenceBranches() {
        // the review's probe H: `true ? 1 : 2.0` as an Object is 1.0
        assertThatIllegalArgumentException()
                .isThrownBy(() -> cond(literal(true), literal(1), literal(2.0), Object_.TOKEN))
                .withMessageContaining("cond does not mix primitive and reference branches");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> cond(literal(true), box(PrimitiveToken.INT, literal(1)), literal(2), Object_.TOKEN))
                .withMessageContaining("box or unbox the branch explicitly");
    }

    @Test
    void condCastsOnlyTheBranchesOfAnotherType() {
        Node.Cond same = (Node.Cond)
                cond(literal(true), literal("a"), literal("b"), String_.TOKEN).node();
        Node.Cond wider = (Node.Cond) cond(literal(true), literal("a"), literalNull(Object_.TOKEN), Object_.TOKEN)
                .node();
        Node.Cond primitive = (Node.Cond)
                cond(literal(true), literal(1), literal(2), PrimitiveToken.INT).node();

        assertThat(same.whenTrue()).isInstanceOf(Node.Lit.class);
        assertThat(wider.whenTrue()).isInstanceOf(Node.Cast.class);
        assertThat(((Node.Cast) wider.whenFalse()).operand()).isInstanceOf(Node.RawLit.class);
        assertThat(primitive.whenTrue()).isInstanceOf(Node.Lit.class);
    }

    @Test
    void newOfAWildcardParameterizationIsRejected() {
        OpenClassToken<ArrayList<?>> anyArrayList = UnsafeFacts.openClassToken(
                Types.parameterized(ClassDesc.of("java.util", "ArrayList"), Types.unbounded()),
                List.of(ConstantDescs.CD_Object),
                MethodTable.EMPTY);
        CtorRef0<ArrayList<?>> ctor = UnsafeFacts.ctor(anyArrayList, MemberTraits.DEFAULT);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new_(ctor))
                .withMessageContaining("`new` needs exact type arguments, not wildcards (JLS 15.9)");
    }
}
