package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// The type parameters of a generic method of an interface reach sixteen, as those of a class do:
/// each stage of [GenericMethodArities.TypeMethodSpec1] to `TypeMethodSpec16` declares the member, and
/// the interface renders, compiles under `-Xlint:all` and runs.
class TypeGenericMethodAritiesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    @Test
    void aGenericMethodOfAnInterfaceReachesSixteenTypeParameters() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.declareGenericMethod(typeParam("T1"), (GenericMethodArities.TypeMethodSpec1<
                                        Self, MethodRef0<Self, Object>, Object>)
                                (m, t1) -> m.declareAbstractMethod("id1", Object_.TOKEN));
                        ib.declareGenericMethod(typeParam("T1"), typeParam("T2"), (GenericMethodArities.TypeMethodSpec2<
                                        Self, MethodRef0<Self, Object>, Object, Object>)
                                (m, t1, t2) -> m.declareAbstractMethod("id2", Object_.TOKEN));
                        ib.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                (GenericMethodArities.TypeMethodSpec3<
                                                Self, MethodRef0<Self, Object>, Object, Object, Object>)
                                        (m, t1, t2, t3) -> m.declareAbstractMethod("id3", Object_.TOKEN));
                        ib.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                (GenericMethodArities.TypeMethodSpec4<
                                                Self, MethodRef0<Self, Object>, Object, Object, Object, Object>)
                                        (m, t1, t2, t3, t4) -> m.declareAbstractMethod("id4", Object_.TOKEN));
                        ib.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                (GenericMethodArities.TypeMethodSpec5<
                                                Self, MethodRef0<Self, Object>, Object, Object, Object, Object, Object>)
                                        (m, t1, t2, t3, t4, t5) -> m.declareAbstractMethod("id5", Object_.TOKEN));
                        ib.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                (GenericMethodArities.TypeMethodSpec6<
                                                Self,
                                                MethodRef0<Self, Object>,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6) -> m.declareAbstractMethod("id6", Object_.TOKEN));
                        ib.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                typeParam("T7"),
                                (GenericMethodArities.TypeMethodSpec7<
                                                Self,
                                                MethodRef0<Self, Object>,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7) ->
                                                m.declareAbstractMethod("id7", Object_.TOKEN));
                        ib.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                typeParam("T7"),
                                typeParam("T8"),
                                (GenericMethodArities.TypeMethodSpec8<
                                                Self,
                                                MethodRef0<Self, Object>,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8) ->
                                                m.declareAbstractMethod("id8", Object_.TOKEN));
                        ib.declareGenericMethod(
                                typeParam("T1"),
                                typeParam("T2"),
                                typeParam("T3"),
                                typeParam("T4"),
                                typeParam("T5"),
                                typeParam("T6"),
                                typeParam("T7"),
                                typeParam("T8"),
                                typeParam("T9"),
                                (GenericMethodArities.TypeMethodSpec9<
                                                Self,
                                                MethodRef0<Self, Object>,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9) ->
                                                m.declareAbstractMethod("id9", Object_.TOKEN));
                        ib.declareGenericMethod(
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
                                (GenericMethodArities.TypeMethodSpec10<
                                                Self,
                                                MethodRef0<Self, Object>,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object,
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) ->
                                                m.declareAbstractMethod("id10", Object_.TOKEN));
                        ib.declareGenericMethod(
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
                                (GenericMethodArities.TypeMethodSpec11<
                                                Self,
                                                MethodRef0<Self, Object>,
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
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) ->
                                                m.declareAbstractMethod("id11", Object_.TOKEN));
                        ib.declareGenericMethod(
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
                                (GenericMethodArities.TypeMethodSpec12<
                                                Self,
                                                MethodRef0<Self, Object>,
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
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) ->
                                                m.declareAbstractMethod("id12", Object_.TOKEN));
                        ib.declareGenericMethod(
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
                                (GenericMethodArities.TypeMethodSpec13<
                                                Self,
                                                MethodRef0<Self, Object>,
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
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13) ->
                                                m.declareAbstractMethod("id13", Object_.TOKEN));
                        ib.declareGenericMethod(
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
                                (GenericMethodArities.TypeMethodSpec14<
                                                Self,
                                                MethodRef0<Self, Object>,
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
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14) ->
                                                m.declareAbstractMethod("id14", Object_.TOKEN));
                        ib.declareGenericMethod(
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
                                (GenericMethodArities.TypeMethodSpec15<
                                                Self,
                                                MethodRef0<Self, Object>,
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
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) ->
                                                m.declareAbstractMethod("id15", Object_.TOKEN));
                        ib.declareGenericMethod(
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
                                (GenericMethodArities.TypeMethodSpec16<
                                                Self,
                                                MethodRef0<Self, Object>,
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
                                                Object>)
                                        (m, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16) ->
                                                m.declareAbstractMethod("id16", Object_.TOKEN));
                        return ib.bodies();
                    }
                }));

        assertThat(compiled.source())
                .contains("<T1> Object id1();")
                .contains("<T1, T2> Object id2();")
                .contains(
                        "<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>" + " Object id16();");
    }
}
