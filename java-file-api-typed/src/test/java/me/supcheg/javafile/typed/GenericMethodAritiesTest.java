package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// The arity of the type parameters of a generic member: one class declares a generic method of one type
/// parameter, of two, and so on to sixteen, which renders, compiles under `-Xlint:all` and runs — each form
/// of [GenericMethodArities] and of `declareGenericMethod` is declared and used.
class GenericMethodAritiesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Ids");

    @Test
    void aMethodOfEveryArityOfTypeParametersRendersCompilesAndRuns() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(ids());

        for (int n = 1; n <= 16; n++) {
            Object ids = compiled.generated().getConstructor().newInstance();
            assertThat(compiled.generated().getMethod("id" + n, Object.class).invoke(ids, "held"))
                    .as("the method of %d type parameters", n)
                    .isEqualTo("held");
        }
    }

    private static JavaFile ids() {
        return TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        var id1 = cb.declareGenericMethod(
                                typeParam("T1"),
                                new GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t1) {
                                        return m.declareMethod("id1", t1, t1);
                                    }
                                });
                        var id2 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                new GenericMethodArities.MethodSpec2<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2) {
                                        return m.declareMethod("id2", t1, t1);
                                    }
                                });
                        var id3 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                new GenericMethodArities.MethodSpec3<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object, Object, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3) {
                                        return m.declareMethod("id3", t1, t1);
                                    }
                                });
                        var id4 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                new GenericMethodArities.MethodSpec4<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4) {
                                        return m.declareMethod("id4", t1, t1);
                                    }
                                });
                        var id5 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                new GenericMethodArities.MethodSpec5<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5) {
                                        return m.declareMethod("id5", t1, t1);
                                    }
                                });
                        var id6 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                new GenericMethodArities.MethodSpec6<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6) {
                                        return m.declareMethod("id6", t1, t1);
                                    }
                                });
                        var id7 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                typeParam("T7"),
                                new GenericMethodArities.MethodSpec7<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7) {
                                        return m.declareMethod("id7", t1, t1);
                                    }
                                });
                        var id8 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                typeParam("T7"),
                                typeParam("T8"),
                                new GenericMethodArities.MethodSpec8<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8) {
                                        return m.declareMethod("id8", t1, t1);
                                    }
                                });
                        var id9 = cb.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                typeParam("T7"),
                                typeParam("T8"),
                                typeParam("T9"),
                                new GenericMethodArities.MethodSpec9<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9) {
                                        return m.declareMethod("id9", t1, t1);
                                    }
                                });
                        var id10 = cb.declareGenericMethod(
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
                                new GenericMethodArities.MethodSpec10<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9,
                                            TypeVarToken<Object> t10) {
                                        return m.declareMethod("id10", t1, t1);
                                    }
                                });
                        var id11 = cb.declareGenericMethod(
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
                                new GenericMethodArities.MethodSpec11<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9,
                                            TypeVarToken<Object> t10,
                                            TypeVarToken<Object> t11) {
                                        return m.declareMethod("id11", t1, t1);
                                    }
                                });
                        var id12 = cb.declareGenericMethod(
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
                                typeParam("T12"),
                                new GenericMethodArities.MethodSpec12<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9,
                                            TypeVarToken<Object> t10,
                                            TypeVarToken<Object> t11,
                                            TypeVarToken<Object> t12) {
                                        return m.declareMethod("id12", t1, t1);
                                    }
                                });
                        var id13 = cb.declareGenericMethod(
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
                                typeParam("T12"),
                                typeParam("T13"),
                                new GenericMethodArities.MethodSpec13<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9,
                                            TypeVarToken<Object> t10,
                                            TypeVarToken<Object> t11,
                                            TypeVarToken<Object> t12,
                                            TypeVarToken<Object> t13) {
                                        return m.declareMethod("id13", t1, t1);
                                    }
                                });
                        var id14 = cb.declareGenericMethod(
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
                                typeParam("T12"),
                                typeParam("T13"),
                                typeParam("T14"),
                                new GenericMethodArities.MethodSpec14<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9,
                                            TypeVarToken<Object> t10,
                                            TypeVarToken<Object> t11,
                                            TypeVarToken<Object> t12,
                                            TypeVarToken<Object> t13,
                                            TypeVarToken<Object> t14) {
                                        return m.declareMethod("id14", t1, t1);
                                    }
                                });
                        var id15 = cb.declareGenericMethod(
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
                                typeParam("T12"),
                                typeParam("T13"),
                                typeParam("T14"),
                                typeParam("T15"),
                                new GenericMethodArities.MethodSpec15<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9,
                                            TypeVarToken<Object> t10,
                                            TypeVarToken<Object> t11,
                                            TypeVarToken<Object> t12,
                                            TypeVarToken<Object> t13,
                                            TypeVarToken<Object> t14,
                                            TypeVarToken<Object> t15) {
                                        return m.declareMethod("id15", t1, t1);
                                    }
                                });
                        var id16 = cb.declareGenericMethod(
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
                                typeParam("T12"),
                                typeParam("T13"),
                                typeParam("T14"),
                                typeParam("T15"),
                                typeParam("T16"),
                                new GenericMethodArities.MethodSpec16<
                                        Self,
                                        Object,
                                        MethodRef1<Self, Object, Object>,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object,
                                        Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t1,
                                            TypeVarToken<Object> t2,
                                            TypeVarToken<Object> t3,
                                            TypeVarToken<Object> t4,
                                            TypeVarToken<Object> t5,
                                            TypeVarToken<Object> t6,
                                            TypeVarToken<Object> t7,
                                            TypeVarToken<Object> t8,
                                            TypeVarToken<Object> t9,
                                            TypeVarToken<Object> t10,
                                            TypeVarToken<Object> t11,
                                            TypeVarToken<Object> t12,
                                            TypeVarToken<Object> t13,
                                            TypeVarToken<Object> t14,
                                            TypeVarToken<Object> t15,
                                            TypeVarToken<Object> t16) {
                                        return m.declareMethod("id16", t1, t1);
                                    }
                                });
                        var bodies = cb.bodies();
                        bodies.define(id1, (b, self, value) -> b.return_(value));
                        bodies.define(id2, (b, self, value) -> b.return_(value));
                        bodies.define(id3, (b, self, value) -> b.return_(value));
                        bodies.define(id4, (b, self, value) -> b.return_(value));
                        bodies.define(id5, (b, self, value) -> b.return_(value));
                        bodies.define(id6, (b, self, value) -> b.return_(value));
                        bodies.define(id7, (b, self, value) -> b.return_(value));
                        bodies.define(id8, (b, self, value) -> b.return_(value));
                        bodies.define(id9, (b, self, value) -> b.return_(value));
                        bodies.define(id10, (b, self, value) -> b.return_(value));
                        bodies.define(id11, (b, self, value) -> b.return_(value));
                        bodies.define(id12, (b, self, value) -> b.return_(value));
                        bodies.define(id13, (b, self, value) -> b.return_(value));
                        bodies.define(id14, (b, self, value) -> b.return_(value));
                        bodies.define(id15, (b, self, value) -> b.return_(value));
                        bodies.define(id16, (b, self, value) -> b.return_(value));
                        return bodies;
                    }
                });
    }
}
