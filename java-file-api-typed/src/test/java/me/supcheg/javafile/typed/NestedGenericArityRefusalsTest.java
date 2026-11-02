package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The refusals of the member types of four to twelve type parameters: a type parameter repeated and
/// one whose bound is a raw type are rejected by the declaration of the member type, as they are at
/// one to three, before its specification is called.
class NestedGenericArityRefusalsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Nested");
    private static final TargetClasspath TARGET = UnsafeFacts.unverifiedClasspath();

    @Test
    void aNestedClassOfFourTypeParametersRefusesARepeatedName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.declareClass(TARGET, DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.extendsObject()
                                .members()
                                .declareNestedClass(
                                        "Box",
                                        typeParam("T1"),
                                        typeParam("T1"),
                                        typeParam("T3"),
                                        typeParam("T4"),
                                        classSpec4());
                        return Unreached.here();
                    }
                }))
                .withMessageContaining("has two type parameters T1");
    }

    @Test
    void aNestedInterfaceOfTwelveTypeParametersRefusesARepeatedName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(TARGET, DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.declareNestedInterface(
                                "One",
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                typeParam("T7"),
                                typeParam("T8"),
                                typeParam("T9"),
                                typeParam("T10"),
                                typeParam("T1"),
                                typeParam("T12"),
                                interfaceSpec12());
                        return Unreached.here();
                    }
                }))
                .withMessageContaining("has two type parameters T1");
    }

    @Test
    void aNestedRecordOfFourTypeParametersRefusesARawBound() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.annotationType(TARGET, DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.declareNestedRecord(
                                        "Pair",
                                        typeParam("T1"),
                                        typeParam("T2"),
                                        typeParam("T3"),
                                        typeParam("T4", rawComparable()),
                                        recordSpec4());
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("cannot have the raw bound java.lang.Comparable");
    }

    @Test
    void aNestedClassOfTwelveTypeParametersRefusesARawBound() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.enum_(TARGET, DESC, new TypedJavaFile.TypedEnumSpec() {
                    @Override
                    public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                        eb.constant(
                                "A",
                                body -> body.declareNestedClass(
                                        "Box",
                                        typeParam("T1"),
                                        typeParam("T2"),
                                        typeParam("T3"),
                                        typeParam("T4"),
                                        typeParam("T5"),
                                        typeParam("T6"),
                                        typeParam("T7"),
                                        typeParam("T8"),
                                        typeParam("T9"),
                                        typeParam("T10"),
                                        typeParam("T11"),
                                        typeParam("T12", rawComparable()),
                                        classSpec12()));
                        return eb.bodies();
                    }
                }))
                .withMessageContaining("cannot have the raw bound java.lang.Comparable");
    }

    private static <T> me.supcheg.javafile.facts.DeclaredToken<T> rawComparable() {
        return UnsafeFacts.<T>interfaceToken(Comparable_.Data.SHAPE);
    }

    private static GenericDeclarationArities.TypedGenericClassSpec4<Object, Object, Object, Object> classSpec4() {
        return new GenericDeclarationArities.TypedGenericClassSpec4<>() {
            @Override
            public <Box, T1, T2, T3, T4> MemberBodies<Box, ?> build(
                    ClassHeader<Box> h,
                    TypeVarToken<T1> t1,
                    TypeVarToken<T2> t2,
                    TypeVarToken<T3> t3,
                    TypeVarToken<T4> t4) {
                return Unreached.here();
            }
        };
    }

    private static GenericDeclarationArities.TypedGenericClassSpec12<
                    Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>
            classSpec12() {
        return new GenericDeclarationArities.TypedGenericClassSpec12<>() {
            @Override
            public <Box, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MemberBodies<Box, ?> build(
                    ClassHeader<Box> h,
                    TypeVarToken<T1> t1,
                    TypeVarToken<T2> t2,
                    TypeVarToken<T3> t3,
                    TypeVarToken<T4> t4,
                    TypeVarToken<T5> t5,
                    TypeVarToken<T6> t6,
                    TypeVarToken<T7> t7,
                    TypeVarToken<T8> t8,
                    TypeVarToken<T9> t9,
                    TypeVarToken<T10> t10,
                    TypeVarToken<T11> t11,
                    TypeVarToken<T12> t12) {
                return Unreached.here();
            }
        };
    }

    private static GenericDeclarationArities.TypedGenericInterfaceSpec12<
                    Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>
            interfaceSpec12() {
        return new GenericDeclarationArities.TypedGenericInterfaceSpec12<>() {
            @Override
            public <One, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> TypedInterfaceBodies<One> build(
                    TypedInterfaceBuilder<One> ib,
                    TypeVarToken<T1> t1,
                    TypeVarToken<T2> t2,
                    TypeVarToken<T3> t3,
                    TypeVarToken<T4> t4,
                    TypeVarToken<T5> t5,
                    TypeVarToken<T6> t6,
                    TypeVarToken<T7> t7,
                    TypeVarToken<T8> t8,
                    TypeVarToken<T9> t9,
                    TypeVarToken<T10> t10,
                    TypeVarToken<T11> t11,
                    TypeVarToken<T12> t12) {
                return Unreached.here();
            }
        };
    }

    private static GenericDeclarationArities.TypedGenericRecordSpec4<Object, Object, Object, Object> recordSpec4() {
        return new GenericDeclarationArities.TypedGenericRecordSpec4<>() {
            @Override
            public <Pair, T1, T2, T3, T4> TypedRecordBodies<Pair> build(
                    TypedRecordBuilder<Pair> rb,
                    TypeVarToken<T1> t1,
                    TypeVarToken<T2> t2,
                    TypeVarToken<T3> t3,
                    TypeVarToken<T4> t4) {
                return Unreached.here();
            }
        };
    }
}
