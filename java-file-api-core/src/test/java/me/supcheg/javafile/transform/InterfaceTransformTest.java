package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.InterfaceMember;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InterfaceTransformTest {

    @Test
    void acceptCanPassAMemberThroughToTheBuilder() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("p", "I"));
        ConstantDecl constant = new ConstantDecl("MAX", PrimitiveTypeRef.INT, List.of(), new IntLiteral(1));

        InterfaceTransform passThrough = (b, member) -> b.accept(member);
        passThrough.accept(builder, constant);

        assertThat(builder.build().members()).containsExactly(constant);
    }

    @Test
    void andThenInvokesBothTransformsInOrderAgainstTheSameMember() {
        List<String> callOrder = new ArrayList<>();
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("p", "I"));
        ConstantDecl constant = new ConstantDecl("MAX", PrimitiveTypeRef.INT, List.of(), new IntLiteral(1));

        InterfaceTransform first = (b, member) -> {
            callOrder.add("first");
            b.accept(member);
        };
        InterfaceTransform second = (b, member) -> callOrder.add("second");

        InterfaceTransform combined = first.andThen(second);
        combined.accept(builder, constant);

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(builder.build().members()).containsExactly((InterfaceMember) constant);
    }
}
