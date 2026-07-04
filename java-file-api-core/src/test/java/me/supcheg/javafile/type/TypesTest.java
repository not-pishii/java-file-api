package me.supcheg.javafile.type;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TypesTest {

    @Test
    void ofWrapsAClassDesc() {
        ClassDesc desc = ClassDesc.of("java.util", "List");
        TypeRef ref = Types.of(desc);

        assertThat(ref).isEqualTo(new ClassTypeRef(desc));
    }

    @Test
    void parameterizedCombinesRawAndArgs() {
        ClassDesc list = ClassDesc.of("java.util", "List");
        ClassDesc string = ClassDesc.of("java.lang", "String");

        TypeRef ref = Types.parameterized(list, Types.exact(Types.of(string)));

        assertThat(ref).isEqualTo(new ParameterizedTypeRef(list, List.of(new ExactTypeArg(new ClassTypeRef(string)))));
    }

    @Test
    void arrayWrapsComponentType() {
        TypeRef ref = Types.array(PrimitiveTypeRef.INT);

        assertThat(ref).isEqualTo(new ArrayTypeRef(PrimitiveTypeRef.INT));
    }

    @Test
    void wildcardFactoriesProduceExpectedTypeArgs() {
        TypeRef bound = Types.of(ClassDesc.of("java.lang", "Number"));

        assertThat(Types.extendsBound(bound)).isEqualTo(new ExtendsTypeArg(bound));
        assertThat(Types.superBound(bound)).isEqualTo(new SuperTypeArg(bound));
        assertThat(Types.unbounded()).isSameAs(UnboundedTypeArg.INSTANCE);
    }

    @Test
    void primitiveTypeRefExposesSourceName() {
        assertThat(PrimitiveTypeRef.INT.sourceName()).isEqualTo("int");
        assertThat(PrimitiveTypeRef.BOOLEAN.sourceName()).isEqualTo("boolean");
    }
}
