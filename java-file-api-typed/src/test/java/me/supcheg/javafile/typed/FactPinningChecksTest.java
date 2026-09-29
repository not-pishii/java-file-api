package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.jdk.ArrayList_;
import me.supcheg.javafile.facts.jdk.Integer_;
import me.supcheg.javafile.facts.jdk.List_;
import me.supcheg.javafile.facts.jdk.Object_;
import me.supcheg.javafile.facts.jdk.String_;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// What cannot be rendered as the fact says is rejected when it is built
/// (§6.1): an unchecked cast or `instanceof` (JLS 5.1.6.2) and a generic
/// array creation (B13), a conditional mixing primitive and reference
/// branches (M2), and `new` of a wildcard parameterization.
class FactPinningChecksTest {

    private static final String REIFIABLE = "needs a reifiable type (JLS 4.7)";
    private static final String UNCHECKED = "would be an unchecked cast (JLS 5.1.6.2)";
    private static final ClassDesc ARRAY_LIST = ClassDesc.of("java.util", "ArrayList");

    private final List_<String> strings = new List_<>(String_.TOKEN);
    private final ArrayList_<String> arrayOfStrings = new ArrayList_<>(String_.TOKEN);
    private final InterfaceToken<Collection<String>> collectionOfStrings = UnsafeFacts.interfaceToken(
            Types.parameterized(ConstantDescs.CD_Collection, Types.STRING), MethodTable.EMPTY);
    private final TypeVarToken<Object> x = UnsafeFacts.typeVarToken(Types.typeVar("X"), ConstantDescs.CD_Object);

    @Test
    void castCheckedRejectsAnUncheckedCast() {
        Expr<Object> o = literalNull(Object_.TOKEN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> castChecked(strings.token, o))
                .withMessageContaining(
                        "castChecked from java.lang.Object to java.util.List<java.lang.String> " + UNCHECKED)
                .withMessageContaining("the operand's type java.lang.Object is not a parameterization");
    }

    @Test
    void castCheckedAcceptsACastToASubtypeWhoseTypeArgumentsTheOperandDetermines() {
        Expr<List<String>> list = literalNull(strings.token);
        Expr<Collection<String>> collection = literalNull(collectionOfStrings);

        assertThat(castChecked(arrayOfStrings.token, list).node()).isInstanceOf(Node.Cast.class);
        assertThat(castChecked(strings.token, collection).node()).isInstanceOf(Node.Cast.class);
        assertThat(castChecked(strings.token, list).node()).isInstanceOf(Node.Cast.class);
    }

    @Test
    void aCastToAParameterizedSubtypeIsCheckedOnlyWhenTheOperandDeterminesItsTypeArguments() {
        List_<Object> listOfX = new List_<>(x);
        var listOfExtendsString = UnsafeFacts.<List<? extends String>>interfaceToken(
                Types.parameterized(ConstantDescs.CD_List, Types.extendsBound(Types.STRING)), MethodTable.EMPTY);
        var arrayOfExtendsString = UnsafeFacts.<ArrayList<? extends String>>openClassToken(
                Types.parameterized(ARRAY_LIST, Types.extendsBound(Types.STRING)), List.of(), MethodTable.EMPTY);

        // checked: accepted
        assertCheckedCast(strings.token, arrayOfStrings.token);
        assertCheckedCast(collectionOfStrings, strings.token);
        assertCheckedCast(collectionOfStrings, arrayOfStrings.token);
        assertCheckedCast(listOfX.token, new ArrayList_<>(x).token);
        assertCheckedCast(Object_.TOKEN, anyList());
        // unchecked: rejected, with the reason
        assertUncheckedCast(
                strings.token,
                new ArrayList_<>(Integer_.TOKEN).token,
                "java.util.ArrayList<java.lang.Integer> is not a subtype of java.util.List<java.lang.String>:"
                        + " its supertype is java.util.List<java.lang.Integer>");
        assertUncheckedCast(Object_.TOKEN, arrayOfStrings.token, "the operand's type java.lang.Object is not a");
        assertUncheckedCast(x, arrayOfStrings.token, "the operand's type X is not a parameterization");
        assertUncheckedCast(
                listOfExtendsString,
                arrayOfStrings.token,
                "java.util.ArrayList<java.lang.String> is not a subtype of java.util.List<? extends java.lang.String>");
        assertUncheckedCast(strings.token, arrayOfExtendsString, "has wildcard type arguments");
        assertUncheckedCast(Object_.TOKEN, x, "X is not reifiable and not a parameterized class or interface type");
        assertUncheckedCast(
                Object_.TOKEN,
                ArrayToken.of(strings.token),
                "is not reifiable and not a parameterized class or interface type");
        assertUncheckedCast(
                listOfX.token, arrayOfStrings.token, "java.util.ArrayList<java.lang.String> is not a subtype of");
    }

    @Test
    void aCastToATypeWhoseFactsRecordNoSupertypesOrLeaveATypeArgumentOpenIsRejected() {
        // class Pair<A, B> implements List<A>
        var pairOfStringInteger = UnsafeFacts.openClassToken(
                Types.parameterized(ClassDesc.of("fixtures", "Pair"), Types.STRING, Types.of(ConstantDescs.CD_Integer)),
                List.of(ConstantDescs.CD_Object),
                new Supertypes(
                        List.of(Types.typeVar("A"), Types.typeVar("B")),
                        List.of(Types.parameterized(ConstantDescs.CD_List, Types.typeVar("A")))),
                MethodTable.EMPTY);
        var unrecorded =
                UnsafeFacts.openClassToken(Types.parameterized(ARRAY_LIST, Types.STRING), List.of(), MethodTable.EMPTY);

        assertUncheckedCast(
                strings.token,
                pairOfStringInteger,
                "java.util.List<java.lang.String> does not determine the type argument for B of"
                        + " fixtures.Pair<java.lang.String, java.lang.Integer>");
        assertUncheckedCast(
                strings.token, unrecorded, "the facts of java.util.ArrayList<java.lang.String> record no supertypes");
    }

    private static void assertCheckedCast(TypeToken<?> from, TypeToken<?> to) {
        assertThatCode(() -> Tokens.requireCheckedCast(from, to, "a cast")).doesNotThrowAnyException();
    }

    private static void assertUncheckedCast(TypeToken<?> from, TypeToken<?> to, String reason) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Tokens.requireCheckedCast(from, to, "a cast"))
                .withMessageContaining("a cast from " + from + " to " + to + " " + UNCHECKED + ": ")
                .withMessageContaining(reason);
    }

    private static InterfaceToken<List<?>> anyList() {
        return UnsafeFacts.interfaceToken(
                Types.parameterized(ConstantDescs.CD_List, Types.unbounded()), MethodTable.EMPTY);
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
    void anInstanceofPatternRejectsAnUncheckedTest() {
        Body<Prim.Int> body = Body.root("body");
        String message = "an instanceof pattern from java.lang.Object to java.util.List<java.lang.String> " + UNCHECKED;

        Scopes.within(body, () -> {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> body.ifInstanceOf(literalNull(Object_.TOKEN), strings.token, (_, _) -> {}))
                    .withMessageContaining(message);
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> body.ifInstanceOfElse(
                            literalNull(Object_.TOKEN),
                            strings.token,
                            (t, _) -> t.return_(literal(1)),
                            e -> e.return_(literal(2))))
                    .withMessageContaining(message);
            return body.return_(literal(0));
        });
    }

    @Test
    void anInstanceofPatternAcceptsASubtypeWhoseTypeArgumentsTheOperandDetermines() {
        Body<Prim.Int> body = Body.root("body");

        Scopes.within(
                body,
                () -> body.ifInstanceOfElse(
                        literalNull(strings.token),
                        arrayOfStrings.token,
                        (t, _) -> t.return_(literal(1)),
                        e -> e.return_(literal(2))));
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
