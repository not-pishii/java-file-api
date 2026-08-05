package me.supcheg.javafile.code;

import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.annotation.SingleAnnotationValue;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListOverloadsTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc ARRAY_LIST = ClassDesc.of("java.util", "ArrayList");

    @Test
    void listAndVarargsFormsAgreeForCalls() {
        List<Expr> args = List.of(Exprs.literal(1), Exprs.literal(2));
        assertThat(Exprs.staticCall(STRING, "join", args))
                .isEqualTo(Exprs.staticCall(STRING, "join", Exprs.literal(1), Exprs.literal(2)));
        assertThat(Exprs.field("x").call("m", args))
                .isEqualTo(Exprs.field("x").call("m", Exprs.literal(1), Exprs.literal(2)));
    }

    @Test
    void listAndVarargsFormsAgreeForCreation() {
        List<Expr> args = List.of(Exprs.literal(3));
        assertThat(Exprs.new_(ARRAY_LIST, args)).isEqualTo(Exprs.new_(ARRAY_LIST, Exprs.literal(3)));
        assertThat(Exprs.newDiamond(ARRAY_LIST, args)).isEqualTo(Exprs.newDiamond(ARRAY_LIST, Exprs.literal(3)));
        assertThat(Exprs.newArrayOf(Types.of(STRING), args))
                .isEqualTo(Exprs.newArrayOf(Types.of(STRING), Exprs.literal(3)));
    }

    @Test
    void annotationArrayAcceptsAList() {
        List<SingleAnnotationValue> values = List.of(AnnotationValues.literal("a"));
        assertThat(AnnotationValues.array(values)).isEqualTo(AnnotationValues.array(AnnotationValues.literal("a")));
    }

    @Test
    void exprsCallAcceptsAList() {
        List<Expr> args = List.of(Exprs.literal(1), Exprs.literal(2));
        assertThat(Exprs.call("m", args)).isEqualTo(Exprs.call("m", Exprs.literal(1), Exprs.literal(2)));
    }
}
