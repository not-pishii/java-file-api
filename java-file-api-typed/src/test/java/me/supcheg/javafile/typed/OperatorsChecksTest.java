package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import org.junit.jupiter.api.Test;

import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.bitNotByte;
import static me.supcheg.javafile.typed.Expressions.bitNotChar;
import static me.supcheg.javafile.typed.Expressions.bitNotShort;
import static me.supcheg.javafile.typed.Expressions.instanceOf;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.negByte;
import static me.supcheg.javafile.typed.Expressions.negChar;
import static me.supcheg.javafile.typed.Expressions.negFloat;
import static me.supcheg.javafile.typed.Expressions.negShort;
import static me.supcheg.javafile.typed.Expressions.postIncrement;
import static me.supcheg.javafile.typed.Expressions.preIncrement;
import static me.supcheg.javafile.typed.Expressions.unaryPlusByte;
import static me.supcheg.javafile.typed.Expressions.unaryPlusChar;
import static me.supcheg.javafile.typed.Expressions.unaryPlusFloat;
import static me.supcheg.javafile.typed.Expressions.unaryPlusShort;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The refusals of the operators: an increment of a place Java does not increment, a compound
/// assignment that would convert implicitly, and a plain `instanceof` that would be unchecked or
/// test a primitive.
class OperatorsChecksTest {

    private static <T> MutVar<T> place(me.supcheg.javafile.facts.TypeToken<T> type) {
        return new MutVar<>(type, Var.Role.Mutable.LOCAL, Body.root("body"));
    }

    @Test
    void anIncrementIsAnEffect() {
        MutVar<me.supcheg.javafile.facts.Prim.Int> x = place(PrimitiveToken.INT);

        assertThat(Assignment.nodeOf(preIncrement(x))).isInstanceOf(Node.Increment.class);
    }

    @Test
    void anIncrementOfABooleanOrAReferenceIsRefused() {
        assertThatThrownBy(() -> postIncrement(place(PrimitiveToken.BOOLEAN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("++ and -- are of a numeric primitive type");
        assertThatThrownBy(() -> postIncrement(place(String_.TOKEN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("++ and -- are of a numeric primitive type");
    }

    @Test
    void theUnaryOperatorsCoverEveryPrimitiveTypeJavaAllowsThemOn() {
        Expr<me.supcheg.javafile.facts.Prim.Byte> byteValue = value(PrimitiveToken.BYTE);
        Expr<me.supcheg.javafile.facts.Prim.Short> shortValue = value(PrimitiveToken.SHORT);
        Expr<me.supcheg.javafile.facts.Prim.Char> charValue = value(PrimitiveToken.CHAR);
        Expr<me.supcheg.javafile.facts.Prim.Float> floatValue = value(PrimitiveToken.FLOAT);

        assertThat(negByte(byteValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(negShort(shortValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(negChar(charValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(negFloat(floatValue).type()).isEqualTo(PrimitiveToken.FLOAT);
        assertThat(unaryPlusByte(byteValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(unaryPlusShort(shortValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(unaryPlusChar(charValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(unaryPlusFloat(floatValue).type()).isEqualTo(PrimitiveToken.FLOAT);
        assertThat(bitNotByte(byteValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(bitNotShort(shortValue).type()).isEqualTo(PrimitiveToken.INT);
        assertThat(bitNotChar(charValue).type()).isEqualTo(PrimitiveToken.INT);
    }

    private static <T> Expr<T> value(me.supcheg.javafile.facts.TypeToken<T> type) {
        return Unsafe.expr(me.supcheg.javafile.code.Exprs.literal(1), type);
    }

    @Test
    void aCompoundAssignmentOfThePlainKindIsRefused() {
        assertThatThrownBy(() -> assign(place(PrimitiveToken.INT), AssignOp.ASSIGN, literal(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("the plain assignment");
    }

    @Test
    void aCompoundAssignmentTheTypeHasNoOperatorForIsRefused() {
        assertThatThrownBy(() -> assign(place(String_.TOKEN), AssignOp.SUB_ASSIGN, literal("x")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("the type has no such compound assignment");
    }

    @Test
    void everyCompoundOperatorIsTakenByATypeThatHasIt() {
        MutVar<me.supcheg.javafile.facts.Prim.Int> integer = place(PrimitiveToken.INT);
        for (AssignOp op : AssignOp.values()) {
            if (op != AssignOp.ASSIGN) {
                assertThatCode(() -> assign(integer, op, literal(1))).doesNotThrowAnyException();
            }
        }
        MutVar<me.supcheg.javafile.facts.Prim.Bool> flag = place(PrimitiveToken.BOOLEAN);
        assertThatCode(() -> assign(flag, AssignOp.XOR_ASSIGN, literal(true))).doesNotThrowAnyException();
        assertThatThrownBy(() -> assign(flag, AssignOp.ADD_ASSIGN, literal(true)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("the type has no such compound assignment");
        assertThatThrownBy(() -> assign(place(Object_.TOKEN), AssignOp.ADD_ASSIGN, literalNull(Object_.TOKEN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("the type has no such compound assignment");
        assertThatCode(() -> assign(place(String_.TOKEN), AssignOp.ADD_ASSIGN, literal("x")))
                .doesNotThrowAnyException();
    }

    @Test
    void aPlainInstanceofOfAPrimitiveIsRefused() {
        assertThatThrownBy(() -> instanceOf(literal(1), Object_.TOKEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("instanceOf tests a reference");
    }

    @Test
    void aPlainInstanceofThatWouldBeUncheckedIsRefused() {
        assertThatThrownBy(() -> instanceOf(literalNull(Object_.TOKEN), new List_<>(String_.TOKEN).token))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unchecked cast");
    }
}
