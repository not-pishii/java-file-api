package gen.facts.p;

import gen.facts.p.Low_.Canonical;
import gen.facts.p.Low_.Data;
import gen.facts.p.Low_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
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
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef4;
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
import p.Low;

/// The full metamodel of [Low], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Low] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <gen_> a type argument of [Low]
/// @param <java_> a type argument of [Low]
/// @param <me_> a type argument of [Low]
/// @param <p_> a type argument of [Low]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Low.class, fingerprint = "8e2413fbb769b669bae63168d9f4749ac55492d92d88eaa0cb36869f751a1c12", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Low_<gen_, java_, me_, p_> {
    /// The shape of [Low] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Low] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Low_"), "8e2413fbb769b669bae63168d9f4749ac55492d92d88eaa0cb36869f751a1c12", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Low"), List.of(new TypeParam("gen", List.of()), new TypeParam("java", List.of()), new TypeParam("me", List.of()), new TypeParam("p", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("gen"), Types.typeVar("java"), Types.typeVar("me"), Types.typeVar("p")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.var(1), Param.var(2), Param.var(3)), Signature.of("clone"), Signature.of("each", Param.fixed(ClassDesc.of("java.util.Set"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.util.Set"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("Low"))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Low] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Low] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14()), List.of(c0()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Low"), Signature.of("all", Param.var(1), Param.var(2), Param.var(3)), List.of(), List.of(Types.typeVar("java"), Types.typeVar("me"), Types.typeVar("p")), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.Set"), List.of(Types.exact(Types.typeVar("gen"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Low"), Signature.of("each", Param.fixed(ClassDesc.of("java.util.Set"))), List.of(new TypeParam("gen", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.Set"), List.of(Types.exact(Types.typeVar("gen"))))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.util.Set"))), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Low"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("gen", List.of()), new TypeParam("java", List.of()), new TypeParam("me", List.of()), new TypeParam("p", List.of())), List.of(Types.typeVar("gen"), Types.typeVar("java"), Types.typeVar("me"), Types.typeVar("p")), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.Low"), List.of(Types.exact(Types.typeVar("gen")), Types.exact(Types.typeVar("java")), Types.exact(Types.typeVar("me")), Types.exact(Types.typeVar("p"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Low"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.util.Set"))), List.of(new TypeParam("java", List.of()), new TypeParam("me", List.of())), List.of(Types.typeVar("me"), new ParameterizedTypeRef(ClassDesc.of("java.util.Set"), List.of(Types.exact(Types.typeVar("p"))))), Arity.FIXED, new Of(Types.typeVar("java")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.util.Set"))), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Low"), List.of(), List.of(), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Low], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Low].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Low open-class sealed=no
        tparams #0; #1; #2; #3
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable #0 first
        member method public overridable <^0, ^1> pick(^1, java.util.Set<#3>) -> ^0 throws -
        member method public overridable <^0> each(java.util.Set<^0>) -> void throws -
        member method public overridable all(#1, #2, #3) -> java.util.Set<#0> throws -
        member method public static <^0, ^1, ^2, ^3> of(^0, ^1, ^2, ^3) -> p.Low<^0, ^1, ^2, ^3> throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Low <^0, ^1> pick(^1, java.util.Set<#3>) -> ^0 throws - erased (java.lang.Object, java.util.Set) overrides -
        inherit method public concrete p.Low <^0> each(java.util.Set<^0>) -> void throws - erased (java.util.Set) overrides -
        inherit method public concrete p.Low all(#1, #2, #3) -> java.util.Set<#0> throws - erased (java.lang.Object, java.lang.Object, java.lang.Object) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Low <^0, ^1, ^2, ^3> of(^0, ^1, ^2, ^3) -> p.Low<^0, ^1, ^2, ^3> throws - erased (java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object) overrides -
        table abstract -
        table concrete all(#1, #2, #3); clone(); each(java.util.Set); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); pick(java.lang.Object, java.util.Set); toString(); wait(); wait(long); wait(long, int)
        table static of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)
        table ctor Low()
        """;

        private Canonical() {
        }
    }

    /// The token of [Low] with a wildcard for every type argument.
    public static final OpenClassToken<Low<?, ?, ?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded(), TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Low] with the type arguments of this metamodel.
    public final OpenClassToken<Low<gen_, java_, me_, p_>> token;

    /// The fact of [Low#first].
    public final MutableFieldRef<Low<gen_, java_, me_, p_>, gen_> first;

    /// The fact of [Low#Low()].
    public final CtorRef0<Low<gen_, java_, me_, p_>> new_;

    /// The fact of [Low#all(Object, Object, Object)].
    public final MethodRef3<Low<gen_, java_, me_, p_>, Set<gen_>, java_, me_, p_> all_java_me_p;

    private final RefToken<gen_> gen_;

    private final RefToken<java_> java_;

    private final RefToken<me_> me_;

    private final RefToken<p_> p_;

    /// The metamodel of [Low] with the type arguments the tokens give.
    ///
    /// @param gen_ the token of the type argument `gen_`
    /// @param java_ the token of the type argument `java_`
    /// @param me_ the token of the type argument `me_`
    /// @param p_ the token of the type argument `p_`
    public Low_(RefToken<gen_> gen_, RefToken<java_> java_, RefToken<me_> me_, RefToken<p_> p_) {
        this.gen_ = gen_;
        this.java_ = java_;
        this.me_ = me_;
        this.p_ = p_;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(gen_), TokenArg.exact(java_), TokenArg.exact(me_), TokenArg.exact(p_));
        this.first = UnsafeFacts.mutableField(token, "first", gen_);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.all_java_me_p = UnsafeFacts.method(token, "all", UnsafeFacts.<Set<gen_>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(gen_)), UnsafeFacts.param(java_, Param.var(1)), UnsafeFacts.param(me_, Param.var(2)), UnsafeFacts.param(p_, Param.var(3)), MemberTraits.OVERRIDABLE);
    }

    /// The fact of [Low#each(Set)], for the type arguments the tokens give.
    ///
    /// @param <gen__> a type argument of the method
    /// @param gen__ the token of the type argument `gen__`
    /// @return the fact
    public <gen__> VoidMethodRef1<Low<gen_, java_, me_, p_>, Set<gen__>> each_Set(RefToken<gen__> gen__) {
        return UnsafeFacts.voidMethod(token, "each", UnsafeFacts.<Set<gen__>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(gen__)), MemberTraits.OVERRIDABLE.withTypeArgs(gen__));
    }

    /// The fact of [Low#of(Object, Object, Object, Object)], for the type arguments the tokens give.
    ///
    /// @param <gen_> a type argument of the method
    /// @param <java_> a type argument of the method
    /// @param <me_> a type argument of the method
    /// @param <p_> a type argument of the method
    /// @param gen_ the token of the type argument `gen_`
    /// @param java_ the token of the type argument `java_`
    /// @param me_ the token of the type argument `me_`
    /// @param p_ the token of the type argument `p_`
    /// @return the fact
    public static <gen_, java_, me_, p_> StaticMethodRef4<Low<gen_, java_, me_, p_>, gen_, java_, me_, p_> of_gen_java_me_p(RefToken<gen_> gen_, RefToken<java_> java_, RefToken<me_> me_, RefToken<p_> p_) {
        return UnsafeFacts.staticMethod(ANY, "of", UnsafeFacts.<Low<gen_, java_, me_, p_>>openClassToken(Data.SHAPE, TokenArg.exact(gen_), TokenArg.exact(java_), TokenArg.exact(me_), TokenArg.exact(p_)), UnsafeFacts.param(gen_, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(java_, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(me_, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(p_, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(gen_, java_, me_, p_));
    }

    /// The fact of [Low#pick(Object, Set)], for the type arguments the tokens give.
    ///
    /// @param <java__> a type argument of the method
    /// @param <me__> a type argument of the method
    /// @param java__ the token of the type argument `java__`
    /// @param me__ the token of the type argument `me__`
    /// @return the fact
    public <java__, me__> MethodRef2<Low<gen_, java_, me_, p_>, java__, me__, Set<p_>> pick_me_Set(RefToken<java__> java__, RefToken<me__> me__) {
        return UnsafeFacts.method(token, "pick", java__, UnsafeFacts.param(me__, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.<Set<p_>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(p_)), MemberTraits.OVERRIDABLE.withTypeArgs(java__, me__));
    }
}
