package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Math_;
import me.supcheg.javafile.typed.testfacts.java.lang.StringBuilder_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.BiFunction_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Consumer_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Function_;
import me.supcheg.javafile.typed.testfacts.java.util.function.IntUnaryOperator_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.methodRef;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The refusals of method references (6.4): the typed layer, not javac, rejects a reference whose
/// receiver, parameters or result the method of the functional interface does not take.
class MethodRefChecksTest {

    private static final Supplier_<String> SUPPLIER = new Supplier_<>(String_.TOKEN);
    private static final Function_<String, String> FUNCTION = new Function_<>(String_.TOKEN, String_.TOKEN);
    private static final BiFunction_<String, Integer, Integer> BIFUNCTION =
            new BiFunction_<>(String_.TOKEN, Integer_.TOKEN, Integer_.TOKEN);

    @Test
    void aBoundReferenceToAStaticMethodOrOfAPrimitiveReceiverIsRefused() {
        refused(
                () -> methodRef(literal(1), Math_.abs_int, IntUnaryOperator_.TOKEN, IntUnaryOperator_.sam.method()),
                "primitive type");
        refused(
                () -> methodRef(literal("x"), Math_.abs_int, IntUnaryOperator_.TOKEN, IntUnaryOperator_.sam.method()),
                "calls an instance method");
    }

    @Test
    void aBoundReferenceOfAnotherTypeOrWithOtherParametersOrResultIsRefused() {
        refused(
                () -> methodRef(literal("x"), StringBuilder_.append_String, FUNCTION.token, FUNCTION.sam.method()),
                "no subtype of its owner");
        refused(
                () -> methodRef(
                        literal("x"), String_.replace_CharSequence_CharSequence, FUNCTION.token, FUNCTION.sam.method()),
                "passes");
        refused(() -> methodRef(literal("x"), String_.length, SUPPLIER.token, SUPPLIER.sam.method()), "has the result");
    }

    @Test
    void theInterfaceOfTheReferenceMustBeThatOfItsFact() {
        refused(
                () -> methodRef(String_.TOKEN, String_.trim, SUPPLIER.token, FUNCTION.sam.method()),
                "the method of the functional interface is");
    }

    @Test
    void aConstructorIsNoMethodReference() {
        refused(
                () -> methodRef(String_.TOKEN, String_.new_String, FUNCTION.token, FUNCTION.sam.method()),
                "is a constructor");
    }

    @Test
    void anUnboundReferenceOfAnotherTypeOrWithAnotherReceiverOrParametersIsRefused() {
        refused(
                () -> methodRef(StringBuilder_.TOKEN, String_.trim, FUNCTION.token, FUNCTION.sam.method()),
                "no subtype of its owner");
        refused(
                () -> methodRef(String_.TOKEN, String_.trim, SUPPLIER.token, SUPPLIER.sam.method()),
                "takes 1 parameters");
        refused(
                () -> methodRef(String_.TOKEN, String_.length, IntUnaryOperator_.TOKEN, IntUnaryOperator_.sam.method()),
                "the first parameter");
        refused(
                () -> methodRef(String_.TOKEN, String_.compareTo_String, BIFUNCTION.token, BIFUNCTION.sam.method()),
                "passes");
    }

    @Test
    void aStaticReferenceOfAnotherTypeOrWithOtherParametersOrResultIsRefused() {
        refused(
                () -> methodRef(
                        StringBuilder_.TOKEN, Math_.abs_int, IntUnaryOperator_.TOKEN, IntUnaryOperator_.sam.method()),
                "no subtype of its owner");
        refused(() -> methodRef(Math_.TOKEN, Math_.abs_int, SUPPLIER.token, SUPPLIER.sam.method()), "passes");
        refused(
                () -> methodRef(
                        String_.TOKEN, String_.valueOf_int, IntUnaryOperator_.TOKEN, IntUnaryOperator_.sam.method()),
                "has the result");
    }

    @Test
    void aResultWhereTheInterfaceTakesNoneOrTheReverseIsRefused() {
        Consumer_<String> consumer = new Consumer_<>(String_.TOKEN);
        refused(
                () -> methodRef(literal("x"), String_.concat_String, consumer.token, consumer.sam.method()),
                "has the result");
        Function_<StringBuilder, Integer> builderFunction = new Function_<>(StringBuilder_.TOKEN, Integer_.TOKEN);
        refused(
                () -> methodRef(
                        StringBuilder_.TOKEN,
                        StringBuilder_.trimToSize,
                        builderFunction.token,
                        builderFunction.sam.method()),
                "has the result");
    }

    @Test
    void theSubtypeRelationOfTheFactsKnowsClassesInterfacesArraysAndTheRest() {
        assertThat(Tokens.isSubtypeOf(String_.TOKEN, ConstantDescs.CD_Object)).isTrue();
        assertThat(Tokens.isSubtypeOf(String_.TOKEN, ClassDesc.of("java.lang", "CharSequence")))
                .isTrue();
        assertThat(Tokens.isSubtypeOf(String_.TOKEN, Integer_.TOKEN.erasure())).isFalse();
        assertThat(Tokens.isSubtypeOf(ArrayToken.of(String_.TOKEN), ConstantDescs.CD_Object))
                .isTrue();
        assertThat(Tokens.isSubtypeOf(ArrayToken.of(String_.TOKEN), ClassDesc.of("java.lang", "Cloneable")))
                .isTrue();
        assertThat(Tokens.isSubtypeOf(ArrayToken.of(String_.TOKEN), ClassDesc.of("java.io", "Serializable")))
                .isTrue();
        assertThat(Tokens.isSubtypeOf(ArrayToken.of(String_.TOKEN), String_.TOKEN.erasure()))
                .isFalse();
        assertThat(Tokens.isSubtypeOf(PrimitiveToken.INT, ConstantDescs.CD_Object))
                .isFalse();
    }

    private static void refused(Runnable call, String message) {
        assertThatThrownBy(call::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(message);
    }
}
