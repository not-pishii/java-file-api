package me.supcheg.javafile.transform;

import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class ClassTransformTest {

    @Test
    void andThenInvokesBothTransformsInOrderAgainstTheSameMember() {
        List<String> callOrder = new ArrayList<>();
        List<ClassMember> sink = new ArrayList<>();
        Consumer<ClassMember> builder = sink::add;

        ClassTransform first = (b, member) -> {
            callOrder.add("first");
            b.accept(member);
        };
        ClassTransform second = (b, member) -> callOrder.add("second");

        ClassTransform combined = first.andThen(second);
        FieldDecl field = new FieldDecl(
                "count", PrimitiveTypeRef.INT, List.of(), Set.of(Modifier.FINAL), java.util.Optional.empty());
        combined.accept(builder, field);

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(sink).containsExactly(field);
    }
}
