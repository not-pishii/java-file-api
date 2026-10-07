package gen.facts.p;

import gen.facts.p.PBox_.Canonical;
import gen.facts.p.PBox_.Data;
import gen.facts.p.PBox_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.PBox;

/// The full metamodel of [PBox], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PBox] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [PBox]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PBox.class, fingerprint = "5a7b9b2d5818f3553b4e9ab535ee3898d005294c21a9e22270011a0060b815bf", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PBox_<T> {
    /// The shape of [PBox] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PBox] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PBox_"), "5a7b9b2d5818f3553b4e9ab535ee3898d005294c21a9e22270011a0060b815bf", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PBox"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("map", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("shadow", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("ofEnum", Param.fixed(ClassDesc.of("java.lang.Class")))), Set.of(Signature.of("PBox"))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [PBox] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [PBox] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15()), List.of(c0()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.PBox"), Signature.of("map", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("R", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("T")), Types.extendsBound(Types.typeVar("R"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.PBox"), List.of(Types.exact(Types.typeVar("R"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.PBox"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.PBox"), List.of(Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.PBox"), Signature.of("ofEnum", Param.fixed(ClassDesc.of("java.lang.Class"))), List.of(new TypeParam("E", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.typeVar("E"))))))), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.exact(Types.typeVar("E"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.PBox"), List.of(Types.exact(Types.typeVar("E"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Class"))), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.PBox"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("U", List.of(Types.typeVar("T")))), List.of(Types.typeVar("U")), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.PBox"), Signature.of("shadow", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("PBox"), List.of(), List.of(), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [PBox], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PBox].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.PBox open-class sealed=no
        tparams #0
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable #0 t
        member method public overridable <^0 extends #0> put(^0) -> void throws -
        member method public overridable <^0> map(java.util.function.Function<? super #0, ? extends ^0>) -> p.PBox<^0> throws -
        member method public overridable <^0> shadow(^0) -> ^0 throws -
        member method public static <^0 extends java.lang.Enum<^0>> ofEnum(java.lang.Class<^0>) -> p.PBox<^0> throws -
        member method public static <^0> of(^0) -> p.PBox<^0> throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.PBox <^0 extends #0> put(^0) -> void throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.PBox <^0> map(java.util.function.Function<? super #0, ? extends ^0>) -> p.PBox<^0> throws - erased (java.util.function.Function) overrides -
        inherit method public concrete p.PBox <^0> shadow(^0) -> ^0 throws - erased (java.lang.Object) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.PBox <^0 extends java.lang.Enum<^0>> ofEnum(java.lang.Class<^0>) -> p.PBox<^0> throws - erased (java.lang.Class) overrides -
        inherit method public static p.PBox <^0> of(^0) -> p.PBox<^0> throws - erased (java.lang.Object) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); map(java.util.function.Function); notify(); notifyAll(); put(java.lang.Object); shadow(java.lang.Object); toString(); wait(); wait(long); wait(long, int)
        table static of(java.lang.Object); ofEnum(java.lang.Class)
        table ctor PBox()
        """;

        private Canonical() {
        }
    }

    /// The token of [PBox] with a wildcard for every type argument.
    public static final OpenClassToken<PBox<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [PBox] with the type arguments of this metamodel.
    public final OpenClassToken<PBox<T>> token;

    /// The fact of [PBox#t].
    public final MutableFieldRef<PBox<T>, T> t;

    /// The fact of [PBox#PBox()].
    public final CtorRef0<PBox<T>> new_;

    private final RefToken<T> t_;

    /// The metamodel of [PBox] with the type arguments the tokens give.
    ///
    /// @param t_ the token of the type argument `T`
    public PBox_(RefToken<T> t_) {
        this.t_ = t_;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t_));
        this.t = UnsafeFacts.mutableField(token, "t", t_);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
    }

    /// The fact of [PBox#map(Function)], for the type arguments the tokens give.
    ///
    /// @param <R> a type argument of the method
    /// @param r the token of the type argument `R`
    /// @return the fact
    public <R> MethodRef1<PBox<T>, PBox<R>, Function<? super T, ? extends R>> map_Function(RefToken<R> r) {
        return UnsafeFacts.method(token, "map", UnsafeFacts.<PBox<R>>openClassToken(Data.SHAPE, TokenArg.exact(r)), UnsafeFacts.<Function<? super T, ? extends R>>interfaceToken(gen.facts.java.util.function.Function_.Data.SHAPE, TokenArg.superBound(t_), TokenArg.extendsBound(r)), MemberTraits.OVERRIDABLE.withTypeArgs(r));
    }

    /// The fact of [PBox#ofEnum(Class)], for the type arguments the tokens give.
    ///
    /// @param <E> a type argument of the method
    /// @param e the token of the type argument `E`
    /// @return the fact
    public static <E extends Enum<E>> StaticMethodRef1<PBox<E>, Class<E>> ofEnum_Class(RefToken<E> e) {
        return UnsafeFacts.staticMethod(ANY, "ofEnum", UnsafeFacts.<PBox<E>>openClassToken(Data.SHAPE, TokenArg.exact(e)), UnsafeFacts.<Class<E>>finalClassToken(gen.facts.java.lang.Class_.Data.SHAPE, TokenArg.exact(e)), MemberTraits.FINAL.withTypeArgs(e));
    }

    /// The fact of [PBox#of(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> StaticMethodRef1<PBox<T>, T> of_T(RefToken<T> t) {
        return UnsafeFacts.staticMethod(ANY, "of", UnsafeFacts.<PBox<T>>openClassToken(Data.SHAPE, TokenArg.exact(t)), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(t));
    }

    /// The fact of [PBox#put(Object)], for the type arguments the tokens give.
    ///
    /// @param <U> a type argument of the method
    /// @param u the token of the type argument `U`
    /// @return the fact
    public <U extends T> VoidMethodRef1<PBox<T>, U> put_U(RefToken<U> u) {
        return UnsafeFacts.voidMethod(token, "put", UnsafeFacts.param(u, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(u));
    }

    /// The fact of [PBox#shadow(Object)], for the type arguments the tokens give.
    ///
    /// @param <T_> a type argument of the method
    /// @param t__ the token of the type argument `T_`
    /// @return the fact
    public <T_> MethodRef1<PBox<T>, T_, T_> shadow_T(RefToken<T_> t__) {
        return UnsafeFacts.method(token, "shadow", t__, UnsafeFacts.param(t__, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t__));
    }
}
