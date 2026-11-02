package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Every form of [TypeGenericMethodDeclaration]: the interface's abstract, `default` and `private`
/// methods, and the method and `static` method of a record, each with zero to four parameters, is
/// declared, so that the arity family of the generic methods of a type is exercised as that of a
/// class is. The members are left undefined: the entry reports them, and the declaration of each
/// form has been made by then.
class TypeGenericMethodCoverageTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef0<Self>, Object> void0() {
        return (d, t) -> d.declareVoidMethod("v0");
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef1<Self, String>, Object> void1() {
        return (d, t) -> d.declareVoidMethod("v1", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef2<Self, String, String>, Object> void2() {
        return (d, t) -> d.declareVoidMethod("v2", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef3<Self, String, String, String>, Object> void3() {
        return (d, t) -> d.declareVoidMethod("v3", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef4<Self, String, String, String, String>, Object>
                    void4() {
        return (d, t) -> d.declareVoidMethod("v4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, MethodRef0<Self, String>, Object> get0() {
        return (d, t) -> d.declareMethod("g0", String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, MethodRef1<Self, String, String>, Object> get1() {
        return (d, t) -> d.declareMethod("g1", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef2<Self, String, String, String>, Object> get2() {
        return (d, t) -> d.declareMethod("g2", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef3<Self, String, String, String, String>, Object>
                    get3() {
        return (d, t) -> d.declareMethod("g3", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef4<Self, String, String, String, String, String>, Object>
                    get4() {
        return (d, t) ->
                d.declareMethod("g4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, StaticMethodRef0<String>, Object> static0() {
        return (d, t) -> d.declareStaticMethod("s0", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, StaticMethodRef1<String, String>, Object> static1() {
        return (d, t) -> d.declareStaticMethod("s1", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, StaticMethodRef2<String, String, String>, Object> static2() {
        return (d, t) -> d.declareStaticMethod("s2", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, StaticMethodRef3<String, String, String, String>, Object>
                    static3() {
        return (d, t) -> d.declareStaticMethod("s3", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, StaticMethodRef4<String, String, String, String, String>, Object>
                    static4() {
        return (d, t) ->
                d.declareStaticMethod("s4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, VoidStaticMethodRef0, Object> voidStatic0() {
        return (d, t) -> d.declareVoidStaticMethod("vs0");
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidStaticMethodRef1<String>, Object> voidStatic1() {
        return (d, t) -> d.declareVoidStaticMethod("vs1", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidStaticMethodRef2<String, String>, Object> voidStatic2() {
        return (d, t) -> d.declareVoidStaticMethod("vs2", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidStaticMethodRef3<String, String, String>, Object>
                    voidStatic3() {
        return (d, t) -> d.declareVoidStaticMethod("vs3", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidStaticMethodRef4<String, String, String, String>, Object>
                    voidStatic4() {
        return (d, t) -> d.declareVoidStaticMethod("vs4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, MethodRef0<Self, String>, Object> abstract0() {
        return (d, t) -> d.declareAbstractMethod("a0", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef1<Self, String, String>, Object> abstract1() {
        return (d, t) -> d.declareAbstractMethod("a1", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef2<Self, String, String, String>, Object> abstract2() {
        return (d, t) -> d.declareAbstractMethod("a2", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef3<Self, String, String, String, String>, Object>
                    abstract3() {
        return (d, t) -> d.declareAbstractMethod("a3", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef4<Self, String, String, String, String, String>, Object>
                    abstract4() {
        return (d, t) -> d.declareAbstractMethod(
                "a4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef0<Self>, Object> voidAbstract0() {
        return (d, t) -> d.declareVoidAbstractMethod("va0");
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef1<Self, String>, Object> voidAbstract1() {
        return (d, t) -> d.declareVoidAbstractMethod("va1", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef2<Self, String, String>, Object> voidAbstract2() {
        return (d, t) -> d.declareVoidAbstractMethod("va2", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef3<Self, String, String, String>, Object>
                    voidAbstract3() {
        return (d, t) -> d.declareVoidAbstractMethod("va3", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef4<Self, String, String, String, String>, Object>
                    voidAbstract4() {
        return (d, t) -> d.declareVoidAbstractMethod("va4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, MethodRef0<Self, String>, Object> default0() {
        return (d, t) -> d.declareDefaultMethod("d0", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef1<Self, String, String>, Object> default1() {
        return (d, t) -> d.declareDefaultMethod("d1", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef2<Self, String, String, String>, Object> default2() {
        return (d, t) -> d.declareDefaultMethod("d2", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef0<Self>, Object> voidDefault0() {
        return (d, t) -> d.declareVoidDefaultMethod("vd0");
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef1<Self, String>, Object> voidDefault1() {
        return (d, t) -> d.declareVoidDefaultMethod("vd1", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef2<Self, String, String>, Object> voidDefault2() {
        return (d, t) -> d.declareVoidDefaultMethod("vd2", String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, MethodRef0<Self, String>, Object> private0() {
        return (d, t) -> d.declarePrivateMethod("p0", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef1<Self, String, String>, Object> private1() {
        return (d, t) -> d.declarePrivateMethod("p1", String_.TOKEN, String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, MethodRef2<Self, String, String, String>, Object> private2() {
        return (d, t) -> d.declarePrivateMethod("p2", String_.TOKEN, String_.TOKEN, String_.TOKEN);
    }

    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef0<Self>, Object> voidPrivate0() {
        return (d, t) -> d.declareVoidPrivateMethod("vp0");
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef1<Self, String>, Object> voidPrivate1() {
        return (d, t) -> d.declareVoidPrivateMethod("vp1", String_.TOKEN);
    }

    private static <Self>
            GenericMethodArities.TypeMethodSpec1<Self, VoidMethodRef2<Self, String, String>, Object> voidPrivate2() {
        return (d, t) -> d.declareVoidPrivateMethod("vp2", String_.TOKEN, String_.TOKEN);
    }

    @Test
    void everyFormOfAGenericMethodOfARecordIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                var t = typeParam("T");
                                rb.declareGenericMethod(t, void0());
                                rb.declareGenericMethod(t, void1());
                                rb.declareGenericMethod(t, void2());
                                rb.declareGenericMethod(t, void3());
                                rb.declareGenericMethod(t, void4());
                                rb.declareGenericMethod(t, get0());
                                rb.declareGenericMethod(t, get1());
                                rb.declareGenericMethod(t, get2());
                                rb.declareGenericMethod(t, get3());
                                rb.declareGenericMethod(t, get4());
                                rb.declareGenericMethod(t, static0());
                                rb.declareGenericMethod(t, static1());
                                rb.declareGenericMethod(t, static2());
                                rb.declareGenericMethod(t, static3());
                                rb.declareGenericMethod(t, static4());
                                rb.declareGenericMethod(t, voidStatic0());
                                rb.declareGenericMethod(t, voidStatic1());
                                rb.declareGenericMethod(t, voidStatic2());
                                rb.declareGenericMethod(t, voidStatic3());
                                rb.declareGenericMethod(t, voidStatic4());
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("never defined");
    }

    @Test
    void everyFormOfAGenericMethodOfAnInterfaceIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var t = typeParam("T");
                                ib.declareGenericMethod(t, abstract0());
                                ib.declareGenericMethod(t, abstract1());
                                ib.declareGenericMethod(t, abstract2());
                                ib.declareGenericMethod(t, abstract3());
                                ib.declareGenericMethod(t, abstract4());
                                ib.declareGenericMethod(t, voidAbstract0());
                                ib.declareGenericMethod(t, voidAbstract1());
                                ib.declareGenericMethod(t, voidAbstract2());
                                ib.declareGenericMethod(t, voidAbstract3());
                                ib.declareGenericMethod(t, voidAbstract4());
                                ib.declareGenericMethod(t, default0());
                                ib.declareGenericMethod(t, default1());
                                ib.declareGenericMethod(t, default2());
                                ib.declareGenericMethod(t, voidDefault0());
                                ib.declareGenericMethod(t, voidDefault1());
                                ib.declareGenericMethod(t, voidDefault2());
                                ib.declareGenericMethod(t, private0());
                                ib.declareGenericMethod(t, private1());
                                ib.declareGenericMethod(t, private2());
                                ib.declareGenericMethod(t, voidPrivate0());
                                ib.declareGenericMethod(t, voidPrivate1());
                                ib.declareGenericMethod(t, voidPrivate2());
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("never defined");
    }

    @Test
    void everyFormOfAnAbstractGenericMethodOfAnAbstractClassIsDeclared() {
        var file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.abstract_();
                        var cb = h.extendsObject().members();
                        var t = typeParam("T");
                        cb.declareGenericMethod(
                                t, (GenericMethodArities.MethodSpec1<Self, Object, MethodRef0<Self, String>, Object>)
                                        (m, x) -> m.declareAbstractMethod("a0", String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef1<Self, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod("a1", String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef2<Self, String, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod("a2", String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef3<Self, String, String, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod(
                                        "a3", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef4<Self, String, String, String, String, String>, Object>)
                                (m, x) -> m.declareAbstractMethod(
                                        "a4",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN));
                        cb.declareGenericMethod(
                                t, (GenericMethodArities.MethodSpec1<Self, Object, VoidMethodRef0<Self>, Object>)
                                        (m, x) -> m.declareVoidAbstractMethod("va0"));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef1<Self, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod("va1", String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef2<Self, String, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod("va2", String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef3<Self, String, String, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod(
                                        "va3", String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        cb.declareGenericMethod(t, (GenericMethodArities.MethodSpec1<
                                        Self, Object, VoidMethodRef4<Self, String, String, String, String>, Object>)
                                (m, x) -> m.declareVoidAbstractMethod(
                                        "va4", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN));
                        return cb.bodies();
                    }
                });
        assertThat(file.render())
                .contains("public abstract <T> String a0();")
                .contains("public abstract <T> String a4(String v0, String v1, String v2, String v3);")
                .contains("public abstract <T> void va0();")
                .contains("public abstract <T> void va4(String v0, String v1, String v2, String v3);");
    }
}
