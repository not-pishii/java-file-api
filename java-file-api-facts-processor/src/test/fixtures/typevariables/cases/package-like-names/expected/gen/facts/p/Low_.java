package gen.facts.p;

import gen.facts.p.Low_.Canonical;
import gen.facts.p.Low_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
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
@GeneratedMetamodel(of = Low.class, fingerprint = "c0ef4df04b17a75161e440affdc6561077b0d1c6e53f17b59a83961f4a7bb6d4", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Low_<gen_, java_, me_, p_> {
    /// The shape of [Low] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Low] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Low_"), "c0ef4df04b17a75161e440affdc6561077b0d1c6e53f17b59a83961f4a7bb6d4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Low"), List.of(new TypeParam("gen", List.of()), new TypeParam("java", List.of()), new TypeParam("me", List.of()), new TypeParam("p", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("gen"), Types.typeVar("java"), Types.typeVar("me"), Types.typeVar("p")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.var(1), Param.var(2), Param.var(3)), Signature.of("clone"), Signature.of("each", Param.fixed(ClassDesc.of("java.util.Set"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.util.Set"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("Low"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Low], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Low].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Low open-class sealed=no\ntparams #0; #1; #2; #3\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable #0 first\nmember method overridable <^0, ^1> pick(^1, java.util.Set<#3>) -> ^0 throws -\nmember method overridable <^0> each(java.util.Set<^0>) -> void throws -\nmember method overridable all(#1, #2, #3) -> java.util.Set<#0> throws -\nmember method static <^0, ^1, ^2, ^3> of(^0, ^1, ^2, ^3) -> p.Low<^0, ^1, ^2, ^3> throws -\ntable abstract -\ntable concrete all(#1, #2, #3); clone(); each(java.util.Set); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); pick(java.lang.Object, java.util.Set); toString(); wait(); wait(long); wait(long, int)\ntable static of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)\ntable ctor Low()\n";

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
