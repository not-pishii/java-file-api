package gen.facts.p;

import gen.facts.p.Poly_.Canonical;
import gen.facts.p.Poly_.Data;
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
import p.Poly;

/// The full metamodel of [Poly], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Poly] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `constructor <T>Poly(T)`, which is a generic constructor, whose type arguments a fact cannot give explicitly
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Poly.class, fingerprint = "16811f11f5e0128888e1745e506bb05041422042c92ccd2cd4f9aa930f92bd4d", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Poly_ {
    /// The shape of [Poly] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Poly] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Poly_"), "16811f11f5e0128888e1745e506bb05041422042c92ccd2cd4f9aa930f92bd4d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Poly"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clamp", Param.fixed(ClassDesc.of("java.lang.Number"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("none"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("odd", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("run", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("widen", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("listOf", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), Signature.of("max", Param.fixed(ClassDesc.of("java.util.Collection")))), Set.of(Signature.of("Poly"), Signature.of("Poly", Param.fixed(ClassDesc.of("java.lang.Object"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Poly], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Poly].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Poly open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor <^0>(^0) throws -\nmember ctor() throws -\nmember field instance mutable int t\nmember field static constant int T = 1\nmember method overridable <^0 extends java.lang.Exception> run(java.lang.Class<^0>) -> void throws ^0, java.io.IOException\nmember method overridable <^0 extends java.lang.Number & java.lang.Comparable<^0>> clamp(^0) -> ^0 throws -\nmember method overridable <^0, ^1 extends ^0> widen(^1) -> ^0 throws -\nmember method overridable <^0, ^1> odd(^0, ^1) -> void throws -\nmember method overridable <^0> id(^0) -> ^0 throws -\nmember method overridable <^0> none() -> void throws -\nmember method static <^0 extends java.lang.Comparable<? super ^0>> max(java.util.Collection<? extends ^0>) -> ^0 throws -\nmember method static <^0> listOf(^0[]) -> java.util.List<^0> throws -\ntable abstract -\ntable concrete clamp(java.lang.Number); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); id(java.lang.Object); none(); notify(); notifyAll(); odd(java.lang.Object, java.lang.Object); run(java.lang.Class); toString(); wait(); wait(long); wait(long, int); widen(java.lang.Object)\ntable static listOf(java.lang.Object[]); max(java.util.Collection)\ntable ctor Poly(); Poly(java.lang.Object)\n";

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
