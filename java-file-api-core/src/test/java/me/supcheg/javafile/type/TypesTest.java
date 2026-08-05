package me.supcheg.javafile.type;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        TypeRef ref = Types.parameterized(list, Types.of(string));

        assertThat(ref).isEqualTo(new ParameterizedTypeRef(list, List.of(new ExactTypeArg(new ClassTypeRef(string)))));
    }

    @Test
    void parameterizedTypeRefOverloadAcceptsMultipleArgs() {
        ClassDesc map = ClassDesc.of("java.util", "Map");
        ClassDesc string = ClassDesc.of("java.lang", "String");
        ClassDesc integer = ClassDesc.of("java.lang", "Integer");

        TypeRef ref = Types.parameterized(map, Types.of(string), Types.of(integer));

        assertThat(ref)
                .isEqualTo(new ParameterizedTypeRef(
                        map,
                        List.of(
                                new ExactTypeArg(new ClassTypeRef(string)),
                                new ExactTypeArg(new ClassTypeRef(integer)))));
    }

    @Test
    void parameterizedTypeArgOverloadAcceptsMultipleArgs() {
        ClassDesc map = ClassDesc.of("java.util", "Map");

        TypeRef ref = Types.parameterized(map, Types.unbounded(), Types.unbounded());

        assertThat(ref).isEqualTo(new ParameterizedTypeRef(map, List.of(Types.unbounded(), Types.unbounded())));
    }

    @Test
    void primitiveConstantsMatchPrimitiveTypeRef() {
        assertThat(Types.INT).isEqualTo(PrimitiveTypeRef.INT);
        assertThat(Types.LONG).isEqualTo(PrimitiveTypeRef.LONG);
        assertThat(Types.DOUBLE).isEqualTo(PrimitiveTypeRef.DOUBLE);
        assertThat(Types.FLOAT).isEqualTo(PrimitiveTypeRef.FLOAT);
        assertThat(Types.BOOLEAN).isEqualTo(PrimitiveTypeRef.BOOLEAN);
        assertThat(Types.BYTE).isEqualTo(PrimitiveTypeRef.BYTE);
        assertThat(Types.SHORT).isEqualTo(PrimitiveTypeRef.SHORT);
        assertThat(Types.CHAR).isEqualTo(PrimitiveTypeRef.CHAR);
    }

    @Test
    void commonTypeConstantsMatchExplicitDescriptors() {
        assertThat(Types.STRING).isEqualTo(Types.of(ClassDesc.of("java.lang", "String")));
        assertThat(Types.OBJECT).isEqualTo(Types.of(ClassDesc.of("java.lang", "Object")));
        assertThat(Types.LIST).isEqualTo(Types.of(ClassDesc.of("java.util", "List")));
        assertThat(Types.SET).isEqualTo(Types.of(ClassDesc.of("java.util", "Set")));
        assertThat(Types.MAP).isEqualTo(Types.of(ClassDesc.of("java.util", "Map")));
        assertThat(Types.COLLECTION).isEqualTo(Types.of(ClassDesc.of("java.util", "Collection")));
        assertThat(Types.OPTIONAL).isEqualTo(Types.of(ClassDesc.of("java.util", "Optional")));
    }

    @Test
    void ofClassMatchesOfClassDesc() {
        assertThat(Types.of(String.class)).isEqualTo(Types.of(ClassDesc.of("java.lang", "String")));
        assertThat(Types.of(Map.class)).isEqualTo(Types.of(ClassDesc.of("java.util", "Map")));
    }

    @Test
    void ofClassResolvesNestedClassesWithTheirEnclosingTypeName() {
        assertThat(Types.of(Nested.class))
                .isEqualTo(Types.of(ClassDesc.of("me.supcheg.javafile.type", "TypesTest$Nested")));
    }

    @Test
    void ofClassRejectsArrayTypes() {
        assertThatThrownBy(() -> Types.of(String[].class)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void ofClassRejectsPrimitiveTypes() {
        assertThatThrownBy(() -> Types.of(int.class)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void parameterizedAcceptsTypeRefsDirectly() {
        ClassDesc list = ClassDesc.of("java.util", "List");
        assertThat(Types.parameterized(list, Types.STRING))
                .isEqualTo(Types.parameterized(list, List.of(Types.exact(Types.STRING))));
    }

    @Test
    void parameterizedStillAcceptsWildcardArgs() {
        ClassDesc list = ClassDesc.of("java.util", "List");
        assertThat(Types.parameterized(list, Types.unbounded()))
                .isEqualTo(Types.parameterized(list, List.of(Types.unbounded())));
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

    private static class Nested {}
}
