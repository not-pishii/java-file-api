package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.model.RecordMember;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecordTransformTest {

    @Test
    void acceptCanPassAMemberThroughToTheBuilder() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("p", "R"));
        StaticFieldDecl field = new StaticFieldDecl("MAX", PrimitiveTypeRef.INT, new IntLiteral(1));

        RecordTransform passThrough = (b, member) -> b.accept(member);
        passThrough.accept(builder, field);

        assertThat(builder.build().members()).containsExactly(field);
    }

    @Test
    void andThenInvokesBothTransformsInOrderAgainstTheSameMember() {
        List<String> callOrder = new ArrayList<>();
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("p", "R"));
        StaticFieldDecl field = new StaticFieldDecl("MAX", PrimitiveTypeRef.INT, new IntLiteral(1));

        RecordTransform first = (b, member) -> {
            callOrder.add("first");
            b.accept(member);
        };
        RecordTransform second = (b, member) -> callOrder.add("second");

        RecordTransform combined = first.andThen(second);
        combined.accept(builder, field);

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(builder.build().members()).containsExactly((RecordMember) field);
    }
}
