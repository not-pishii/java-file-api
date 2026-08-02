package me.supcheg.javafile.transform;

import me.supcheg.javafile.model.EnumMember;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class EnumTransformTest {

    @Test
    void andThenInvokesBothTransformsInOrderAgainstTheSameMember() {
        List<String> callOrder = new ArrayList<>();
        List<EnumMember> sink = new ArrayList<>();
        Consumer<EnumMember> builder = sink::add;

        EnumTransform first = (b, member) -> {
            callOrder.add("first");
            b.accept(member);
        };
        EnumTransform second = (b, member) -> callOrder.add("second");

        EnumTransform combined = first.andThen(second);
        FieldDecl field =
                new FieldDecl("symbol", PrimitiveTypeRef.INT, List.of(), Set.of(Modifier.FINAL), Optional.empty());
        combined.accept(builder, field);

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(sink).containsExactly(field);
    }
}
