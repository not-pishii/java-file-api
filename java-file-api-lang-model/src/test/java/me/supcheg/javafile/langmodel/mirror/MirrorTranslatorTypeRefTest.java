package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVariable;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/// The rows of the translation table of [MirrorTranslator], one mirror
/// kind at a time, through [MirrorTranslator#typeRef].
class MirrorTranslatorTypeRefTest {
    private static final ClassDesc LIST = ClassDesc.of("java.util.List");
    private static final ClassDesc ENTRY = ClassDesc.of("java.util.Map$Entry");

    private static final String KINDS = """
            package p;
            import java.util.List;
            import java.util.Map;
            public class Kinds<T> {
                boolean z; byte b; short s; int i; long j; char c; float f; double d;
                int[][] grid; String[] names; T[] ts;
                String string; List raw; Map.Entry<String, Integer> entry;
                List<? extends Number> upper; List<? super Integer> lower; List<?> any; List<? extends Object> object;
                List<int[]> arrays;
                T t; List<T> ts2;
                Outer<String>.Inner inner; Outer.Inner rawInner; Outer.Nested<String> nested; Plain.Inner plainInner;
                @Use String annotated;
                void run() {}
            }
            """;
    private static final String OUTER = """
            package p;
            public class Outer<T> {
                public class Inner {}
                public static class Nested<N> {}
            }
            """;
    private static final String PLAIN = """
            package p;
            public class Plain {
                public class Inner {}
            }
            """;
    private static final String USE = """
            package p;
            import java.lang.annotation.ElementType;
            import java.lang.annotation.Target;
            @Target(ElementType.TYPE_USE)
            public @interface Use {}
            """;

    private static Map<String, Translation<TypeRef>> fields(String... names) {
        return Harness.run(
                env -> {
                    Map<String, Translation<TypeRef>> result = new java.util.LinkedHashMap<>();
                    for (String name : names) {
                        result.put(name, env.fieldType("p.Kinds", name));
                    }
                    return result;
                },
                KINDS,
                OUTER,
                PLAIN,
                USE);
    }

    private static Translation<TypeRef> ok(TypeRef type) {
        return new Translation.Ok<>(type);
    }

    @Test
    void primitivesAreTranslatedByKind() {
        assertThat(fields("z", "b", "s", "i", "j", "c", "f", "d"))
                .containsExactly(
                        Map.entry("z", ok(PrimitiveTypeRef.BOOLEAN)),
                        Map.entry("b", ok(PrimitiveTypeRef.BYTE)),
                        Map.entry("s", ok(PrimitiveTypeRef.SHORT)),
                        Map.entry("i", ok(PrimitiveTypeRef.INT)),
                        Map.entry("j", ok(PrimitiveTypeRef.LONG)),
                        Map.entry("c", ok(PrimitiveTypeRef.CHAR)),
                        Map.entry("f", ok(PrimitiveTypeRef.FLOAT)),
                        Map.entry("d", ok(PrimitiveTypeRef.DOUBLE)));
    }

    @Test
    void arraysAreTranslatedWithTheirComponents() {
        assertThat(fields("grid", "names", "ts"))
                .containsExactly(
                        Map.entry("grid", ok(Types.array(Types.array(Types.INT)))),
                        Map.entry("names", ok(Types.array(Types.STRING))),
                        Map.entry("ts", ok(Types.array(Types.typeVar("T")))));
    }

    @Test
    void declaredTypesAreByBinaryNameAndGenericTypesWithoutArgumentsStayRaw() {
        assertThat(fields("string", "raw", "entry", "arrays"))
                .containsExactly(
                        Map.entry("string", ok(Types.STRING)),
                        Map.entry("raw", ok(Types.of(LIST))),
                        Map.entry("entry", ok(Types.parameterized(ENTRY, Types.STRING, Types.of(Integer.class)))),
                        Map.entry("arrays", ok(Types.parameterized(LIST, Types.array(Types.INT)))));
    }

    @Test
    void wildcardsAreTypeArgumentsAndExtendsObjectIsUnbounded() {
        assertThat(fields("upper", "lower", "any", "object"))
                .containsExactly(
                        Map.entry("upper", ok(Types.parameterized(LIST, Types.extendsBound(Types.of(Number.class))))),
                        Map.entry("lower", ok(Types.parameterized(LIST, Types.superBound(Types.of(Integer.class))))),
                        Map.entry("any", ok(Types.parameterized(LIST, Types.unbounded()))),
                        Map.entry("object", ok(Types.parameterized(LIST, Types.unbounded()))));
    }

    @Test
    void typeVariablesInScopeAreByName() {
        assertThat(fields("t", "ts2"))
                .containsExactly(
                        Map.entry("t", ok(Types.typeVar("T"))),
                        Map.entry("ts2", ok(Types.parameterized(LIST, Types.typeVar("T")))));
    }

    @Test
    void typeVariablesOutOfScopeAreUnrepresentable() {
        Translation<TypeRef> translation = Harness.run(
                env -> env.translator().typeRef(env.field("p.Kinds", "t").asType(), VarScope.EMPTY),
                KINDS,
                OUTER,
                PLAIN,
                USE);

        assertThat(translation)
                .isEqualTo(new Translation.Unrepresentable<>("type variable T of Kinds is out of scope"));
    }

    @Test
    void membersOfParameterizedTypesAreUnrepresentableButRawAndStaticNestedOnesAreNot() {
        assertThat(fields("inner", "rawInner", "nested", "plainInner"))
                .containsExactly(
                        Map.entry(
                                "inner",
                                new Translation.Unrepresentable<>(
                                        "member class p.Outer.Inner of parameterized type p.Outer<java.lang.String>")),
                        Map.entry("rawInner", ok(Types.of(ClassDesc.of("p.Outer$Inner")))),
                        Map.entry("nested", ok(Types.parameterized(ClassDesc.of("p.Outer$Nested"), Types.STRING))),
                        Map.entry("plainInner", ok(Types.of(ClassDesc.of("p.Plain$Inner")))));
    }

    @Test
    void typeUseAnnotationsAreLeftOut() {
        assertThat(fields("annotated")).containsExactly(Map.entry("annotated", ok(Types.STRING)));
    }

    @Test
    void notGeneratedYetIsDeferred() {
        Translation<TypeRef> translation = Harness.runUnresolved(env -> env.fieldType("p.Late", "late"), """
                package p;
                public class Late {
                    java.util.Map<Outer<String>.Inner, gen.Missing> late;
                }
                """, OUTER);

        assertThat(translation).isEqualTo(new Translation.Deferred<>("gen.Missing"));
    }

    @Test
    void mirrorsThatAreNotTypesOfValuesAreUnrepresentable() {
        List<Translation<TypeRef>> translations = Harness.run(
                env -> {
                    var translator = env.translator();
                    var types = env.types();
                    var elements = env.elements();
                    var max = env.method("java.util.Collections", "max");
                    TypeVariable t =
                            (TypeVariable) max.getTypeParameters().getFirst().asType();
                    return List.of(
                            translator.typeRef(env.method("p.Kinds", "run").getReturnType(), VarScope.EMPTY),
                            translator.typeRef(types.getWildcardType(null, null), VarScope.EMPTY),
                            translator.typeRef(t.getUpperBound(), VarScope.of(max)),
                            translator.typeRef(types.getNullType(), VarScope.EMPTY),
                            translator.typeRef(types.getNoType(TypeKind.NONE), VarScope.EMPTY),
                            translator.typeRef(max.asType(), VarScope.EMPTY),
                            translator.typeRef(
                                    elements.getPackageElement("java.util").asType(), VarScope.EMPTY),
                            translator.typeRef(
                                    elements.getModuleElement("java.base").asType(), VarScope.EMPTY));
                },
                KINDS,
                OUTER,
                PLAIN,
                USE);

        assertThat(translations)
                .map(t -> ((Translation.Unrepresentable<TypeRef>) t).reason())
                .containsExactly(
                        "VOID void is not a type of a value",
                        "wildcard ? outside type arguments",
                        "intersection type java.lang.Object&java.lang.Comparable<? super T> outside the bounds of a type parameter",
                        "NULL <nulltype> is not a type of a value",
                        "NONE none is not a type of a value",
                        "EXECUTABLE <T>(java.util.Collection<? extends T>)T is not a type of a value",
                        "PACKAGE java.util is not a type of a value",
                        "MODULE java.base is not a type of a value");
    }

    @Test
    void everyUnrepresentablePartIsReported() {
        Translation<TypeRef> translation = Harness.run(env -> env.fieldType("p.Two", "two"), """
                package p;
                public class Two {
                    java.util.Map<Outer<String>.Inner, Outer<Integer>.Inner> two;
                }
                """, OUTER);

        assertThat(translation)
                .isEqualTo(new Translation.Unrepresentable<>(
                        "member class p.Outer.Inner of parameterized type p.Outer<java.lang.String>; "
                                + "member class p.Outer.Inner of parameterized type p.Outer<java.lang.Integer>"));
    }
}
