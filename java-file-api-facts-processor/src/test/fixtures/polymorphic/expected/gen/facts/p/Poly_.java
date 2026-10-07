package gen.facts.p;

import gen.facts.p.Poly_.Canonical;
import gen.facts.p.Poly_.Data;
import gen.facts.p.Poly_.Data.Inherited;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Poly;

/// The full metamodel of [Poly], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Poly] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `constructor <T>Poly(T)`, which is a generic constructor, whose type arguments a fact cannot give explicitly
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Poly.class, fingerprint = "0833f539cb6fd5107a1ece85b397b989c6eac413cfa94d026b04166e2e773328", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Poly_ {
    /// The shape of [Poly] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Poly] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Poly_"), "0833f539cb6fd5107a1ece85b397b989c6eac413cfa94d026b04166e2e773328", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Poly"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clamp", Param.fixed(ClassDesc.of("java.lang.Number"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("none"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("odd", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("run", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("widen", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("listOf", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), Signature.of("max", Param.fixed(ClassDesc.of("java.util.Collection")))), Set.of(Signature.of("Poly"), Signature.of("Poly", Param.fixed(ClassDesc.of("java.lang.Object"))))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Poly] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Poly] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18()), List.of(c0(), c1()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("clamp", Param.fixed(ClassDesc.of("java.lang.Number"))), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("java.lang.Number")), new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("T"))))))), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Number"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Poly"), Signature.of("listOf", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), List.of(new TypeParam("T", List.of())), List.of(Types.array(Types.typeVar("T"))), Arity.VARIABLE, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.List"), List.of(Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Poly"), Signature.of("max", Param.fixed(ClassDesc.of("java.util.Collection"))), List.of(new TypeParam("T", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.superBound(Types.typeVar("T"))))))), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.Collection"), List.of(Types.extendsBound(Types.typeVar("T"))))), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.util.Collection"))), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("none"), List.of(new TypeParam("T", List.of())), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("odd", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("TOKEN", List.of()), new TypeParam("Gen", List.of())), List.of(Types.typeVar("TOKEN"), Types.typeVar("Gen")), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("run", Param.fixed(ClassDesc.of("java.lang.Class"))), List.of(new TypeParam("X", List.of(Types.of(ClassDesc.of("java.lang.Exception"))))), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.exact(Types.typeVar("X"))))), Arity.FIXED, Result.NOTHING, List.of(Types.typeVar("X"), Types.of(ClassDesc.of("java.io.IOException"))), Set.of(List.of(ClassDesc.of("java.lang.Class"))), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m16() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m17() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Method m18() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("widen", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("A", List.of()), new TypeParam("B", List.of(Types.typeVar("A")))), List.of(Types.typeVar("B")), Arity.FIXED, new Of(Types.typeVar("A")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Poly"), List.of(), List.of(), Arity.FIXED, List.of());
            }

            private static Constructor c1() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Poly", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Poly], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Poly].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Poly open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member ctor public <^0>(^0) throws -
        member field public instance mutable int t
        member field public static constant int T = 1
        member method public overridable <^0 extends java.lang.Exception> run(java.lang.Class<^0>) -> void throws ^0, java.io.IOException
        member method public overridable <^0 extends java.lang.Number & java.lang.Comparable<^0>> clamp(^0) -> ^0 throws -
        member method public overridable <^0, ^1 extends ^0> widen(^1) -> ^0 throws -
        member method public overridable <^0, ^1> odd(^0, ^1) -> void throws -
        member method public overridable <^0> id(^0) -> ^0 throws -
        member method public overridable <^0> none() -> void throws -
        member method public static <^0 extends java.lang.Comparable<? super ^0>> max(java.util.Collection<? extends ^0>) -> ^0 throws -
        member method public static <^0> listOf(^0[]) -> java.util.List<^0> throws -
        inherit ctor public () throws -
        inherit ctor public <^0>(^0) throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Poly <^0 extends java.lang.Exception> run(java.lang.Class<^0>) -> void throws ^0, java.io.IOException erased (java.lang.Class) overrides -
        inherit method public concrete p.Poly <^0 extends java.lang.Number & java.lang.Comparable<^0>> clamp(^0) -> ^0 throws - erased (java.lang.Number) overrides -
        inherit method public concrete p.Poly <^0, ^1 extends ^0> widen(^1) -> ^0 throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Poly <^0, ^1> odd(^0, ^1) -> void throws - erased (java.lang.Object, java.lang.Object) overrides -
        inherit method public concrete p.Poly <^0> id(^0) -> ^0 throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Poly <^0> none() -> void throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Poly <^0 extends java.lang.Comparable<? super ^0>> max(java.util.Collection<? extends ^0>) -> ^0 throws - erased (java.util.Collection) overrides -
        inherit method public static p.Poly <^0> listOf(^0...) -> java.util.List<^0> throws - erased (java.lang.Object[]) overrides -
        table abstract -
        table concrete clamp(java.lang.Number); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); id(java.lang.Object); none(); notify(); notifyAll(); odd(java.lang.Object, java.lang.Object); run(java.lang.Class); toString(); wait(); wait(long); wait(long, int); widen(java.lang.Object)
        table static listOf(java.lang.Object[]); max(java.util.Collection)
        table ctor Poly(); Poly(java.lang.Object)
        """;

        private Canonical() {
        }
    }

    /// The token of [Poly].
    public static final OpenClassToken<Poly> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Poly#T].
    public static final StaticFieldRef<Int> T = UnsafeFacts.constantField(TOKEN, "T", PrimitiveToken.INT, 1);

    /// The fact of [Poly#t].
    public static final MutableFieldRef<Poly, Int> t = UnsafeFacts.mutableField(TOKEN, "t", PrimitiveToken.INT);

    /// The fact of [Poly#Poly()].
    public static final CtorRef0<Poly> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Poly_() {
    }

    /// The fact of [Poly#clamp(Number)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T extends Number & Comparable<T>> MethodRef1<Poly, T, T> clamp_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "clamp", t, UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Number"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Poly#id(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef1<Poly, T, T> id_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "id", t, UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Poly#listOf(Object\[\])], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> StaticMethodRef1<List<T>, T[]> listOf_TArray(RefToken<T> t) {
        return UnsafeFacts.staticMethod(TOKEN, "listOf", UnsafeFacts.<List<T>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(t)), UnsafeFacts.param(ArrayToken.of(t), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), MemberTraits.FINAL.withTypeArgs(t));
    }

    /// The fact of [Poly#max(Collection)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T extends Comparable<? super T>> StaticMethodRef1<T, Collection<? extends T>> max_Collection(RefToken<T> t) {
        return UnsafeFacts.staticMethod(TOKEN, "max", t, UnsafeFacts.<Collection<? extends T>>interfaceToken(gen.facts.java.util.Collection_.Data.SHAPE, TokenArg.extendsBound(t)), MemberTraits.FINAL.withTypeArgs(t));
    }

    /// The fact of [Poly#none()], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> VoidMethodRef0<Poly> none(RefToken<T> t) {
        return UnsafeFacts.voidMethod(TOKEN, "none", MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Poly#odd(Object, Object)], for the type arguments the tokens give.
    ///
    /// @param <TOKEN> a type argument of the method
    /// @param <Gen> a type argument of the method
    /// @param tOKEN the token of the type argument `TOKEN`
    /// @param gen_ the token of the type argument `Gen`
    /// @return the fact
    public static <TOKEN, Gen> VoidMethodRef2<Poly, TOKEN, Gen> odd_TOKEN_Gen(RefToken<TOKEN> tOKEN, RefToken<Gen> gen_) {
        return UnsafeFacts.voidMethod(TOKEN, "odd", UnsafeFacts.param(tOKEN, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(gen_, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(tOKEN, gen_));
    }

    /// The fact of [Poly#run(Class)], for the type arguments the tokens give.
    ///
    /// @param <X> a type argument of the method
    /// @param x the token of the type argument `X`
    /// @return the fact
    public static <X extends Exception> VoidMethodRef1<Poly, Class<X>> run_Class(RefToken<X> x) {
        return UnsafeFacts.voidMethod(TOKEN, "run", UnsafeFacts.<Class<X>>finalClassToken(gen.facts.java.lang.Class_.Data.SHAPE, TokenArg.exact(x)), MemberTraits.OVERRIDABLE.throwing(x, UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)).withTypeArgs(x));
    }

    /// The fact of [Poly#widen(Object)], for the type arguments the tokens give.
    ///
    /// @param <A> a type argument of the method
    /// @param <B> a type argument of the method
    /// @param a the token of the type argument `A`
    /// @param b the token of the type argument `B`
    /// @return the fact
    public static <A, B extends A> MethodRef1<Poly, A, B> widen_B(RefToken<A> a, RefToken<B> b) {
        return UnsafeFacts.method(TOKEN, "widen", a, UnsafeFacts.param(b, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(a, b));
    }
}
