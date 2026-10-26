package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// What javac could apply to arguments of given types is told by erasures alone, and conservatively: a method is
/// ruled out only where the facts say the arguments cannot be converted to its parameters.
class RivalsTest {
    private static final ClassDesc STRING = ConstantDescs.CD_String;
    private static final ClassDesc OBJECT = ConstantDescs.CD_Object;
    private static final ClassDesc NUMBER = ClassDesc.of("java.lang.Number");
    private static final ClassDesc LOCALE = ClassDesc.of("java.util.Locale");
    private static final ClassDesc INTS = ConstantDescs.CD_int.arrayType();
    private static final ClassDesc LONGS = ConstantDescs.CD_long.arrayType();

    private static final TypeToken<?> INT = PrimitiveToken.INT;
    private static final TypeToken<?> BOOLEAN = PrimitiveToken.BOOLEAN;
    private static final TypeToken<?> INTEGER = PrimitiveToken.INT.boxed();
    private static final TypeToken<?> STRING_TOKEN = String_.TOKEN;
    private static final TypeToken<?> INT_ARRAY = PrimitiveToken.INT.array();
    private static final TypeToken<?> STRING_ARRAY = ArrayToken.of(String_.TOKEN);
    private static final TypeToken<?> STRING_ARRAYS = ArrayToken.of(ArrayToken.of(String_.TOKEN));
    private static final TypeToken<?> TYPE_VARIABLE = UnsafeFacts.typeVarToken(Types.typeVar("T"), OBJECT);
    private static final InterfaceToken<Object> UNRECORDED =
            UnsafeFacts.interfaceToken(Types.of(ClassDesc.of("a.Unrecorded")), MethodTable.EMPTY);

    @Test
    void aPrimitiveIsConvertedByIdentityWideningAndBoxing() {
        assertThat(Rivals.mayConvert(INT, ConstantDescs.CD_int)).isTrue();
        assertThat(Rivals.mayConvert(INT, ConstantDescs.CD_long)).isTrue();
        assertThat(Rivals.mayConvert(INT, ConstantDescs.CD_short)).isFalse();
        assertThat(Rivals.mayConvert(BOOLEAN, ConstantDescs.CD_int)).isFalse();
        assertThat(Rivals.mayConvert(BOOLEAN, ConstantDescs.CD_boolean)).isTrue();
        // boxing, then widening of the reference: conservative
        assertThat(Rivals.mayConvert(INT, OBJECT)).isTrue();
        assertThat(Rivals.mayConvert(INT, STRING)).isTrue();
        assertThat(Rivals.mayConvert(INT, INTS)).isFalse();
    }

    @Test
    void anArrayIsConvertedToAnArrayOfItsComponentAndToTheSupertypesOfArrays() {
        assertThat(Rivals.mayConvert(INT_ARRAY, INTS)).isTrue();
        assertThat(Rivals.mayConvert(INT_ARRAY, LONGS)).isFalse();
        assertThat(Rivals.mayConvert(INT_ARRAY, OBJECT.arrayType())).isFalse();
        assertThat(Rivals.mayConvert(INT_ARRAY, OBJECT)).isTrue();
        assertThat(Rivals.mayConvert(INT_ARRAY, ClassDesc.of("java.lang.Cloneable")))
                .isTrue();
        assertThat(Rivals.mayConvert(INT_ARRAY, ClassDesc.of("java.io.Serializable")))
                .isTrue();
        assertThat(Rivals.mayConvert(INT_ARRAY, STRING)).isFalse();
        assertThat(Rivals.mayConvert(STRING_ARRAY, OBJECT.arrayType())).isTrue();
        assertThat(Rivals.mayConvert(STRING_ARRAY, NUMBER.arrayType())).isFalse();
        assertThat(Rivals.mayConvert(STRING_ARRAY, INTS)).isFalse();
        assertThat(Rivals.mayConvert(STRING_ARRAYS, OBJECT.arrayType())).isTrue();
    }

    @Test
    void aTypeVariableIsConvertedToAnythingButAnArray() {
        assertThat(Rivals.mayConvert(TYPE_VARIABLE, STRING)).isTrue();
        assertThat(Rivals.mayConvert(TYPE_VARIABLE, STRING.arrayType())).isFalse();
    }

    @Test
    void aClassOrInterfaceIsConvertedToItsSupertypesAndUnboxedToThePrimitiveOfABox() {
        assertThat(Rivals.mayConvert(STRING_TOKEN, STRING.arrayType())).isFalse();
        assertThat(Rivals.mayConvert(STRING_TOKEN, ConstantDescs.CD_int)).isFalse();
        assertThat(Rivals.mayConvert(INTEGER, ConstantDescs.CD_int)).isTrue();
        assertThat(Rivals.mayConvert(STRING_TOKEN, OBJECT)).isTrue();
        assertThat(Rivals.mayConvert(STRING_TOKEN, STRING)).isTrue();
        assertThat(Rivals.mayConvert(INTEGER, NUMBER)).isTrue();
        assertThat(Rivals.mayConvert(STRING_TOKEN, ClassDesc.of("java.lang.CharSequence")))
                .isTrue();
        assertThat(Rivals.mayConvert(STRING_TOKEN, NUMBER)).isFalse();
        assertThat(Rivals.mayConvert(STRING_TOKEN, LOCALE)).isFalse();
        // no interfaces recorded: it proves a subtype and never that there is none
        assertThat(Rivals.mayConvert(UNRECORDED, ConstantDescs.CD_List)).isTrue();
    }

    @Test
    void aMethodMayApplyAsOfFixedArityOrAsOfVariableArity() {
        List<TypeToken<?>> twoStrings = List.of(STRING_TOKEN, STRING_TOKEN);

        assertThat(Rivals.mayApply(List.of(STRING, OBJECT), twoStrings)).isTrue();
        assertThat(Rivals.mayApply(List.of(STRING, NUMBER), twoStrings)).isFalse();
        // of other arity, and not of variable arity
        assertThat(Rivals.mayApply(List.of(STRING), twoStrings)).isFalse();
        assertThat(Rivals.mayApply(List.of(), twoStrings)).isFalse();
        assertThat(Rivals.mayApply(List.of(STRING, OBJECT, OBJECT), twoStrings)).isFalse();
        // of variable arity: the elements, and the leading arguments
        assertThat(Rivals.mayApply(List.of(STRING, OBJECT.arrayType()), twoStrings))
                .isTrue();
        assertThat(Rivals.mayApply(List.of(OBJECT.arrayType()), twoStrings)).isTrue();
        assertThat(Rivals.mayApply(List.of(LOCALE, OBJECT.arrayType()), twoStrings))
                .isFalse();
        assertThat(Rivals.mayApply(List.of(STRING, NUMBER.arrayType()), twoStrings))
                .isFalse();
        assertThat(Rivals.mayApply(List.of(STRING, STRING, STRING, OBJECT.arrayType()), twoStrings))
                .isFalse();
    }
}
