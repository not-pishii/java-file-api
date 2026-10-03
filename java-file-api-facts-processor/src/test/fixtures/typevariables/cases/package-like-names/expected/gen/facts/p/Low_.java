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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Low.class, fingerprint = "5c7b8a433886f2eeb8ca60a119fb604710221e9b62e9fd0217d47c305431415c", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Low_<gen_, java_, me_, p_> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Low_"), "5c7b8a433886f2eeb8ca60a119fb604710221e9b62e9fd0217d47c305431415c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Low"), List.of(new TypeParam("gen", List.of()), new TypeParam("java", List.of()), new TypeParam("me", List.of()), new TypeParam("p", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("gen"), Types.typeVar("java"), Types.typeVar("me"), Types.typeVar("p")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.var(1), Param.var(2), Param.var(3)), Signature.of("clone"), Signature.of("each", Param.fixed(ClassDesc.of("java.util.Set"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.util.Set"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("Low"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Low open-class sealed=no\ntparams #0; #1; #2; #3\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable #0 first\nmember method overridable <^0, ^1> pick(^1, java.util.Set<#3>) -> ^0 throws -\nmember method overridable <^0> each(java.util.Set<^0>) -> void throws -\nmember method overridable all(#1, #2, #3) -> java.util.Set<#0> throws -\nmember method static <^0, ^1, ^2, ^3> of(^0, ^1, ^2, ^3) -> p.Low<^0, ^1, ^2, ^3> throws -\ntable abstract -\ntable concrete all(#1, #2, #3); clone(); each(java.util.Set); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); pick(java.lang.Object, java.util.Set); toString(); wait(); wait(long); wait(long, int)\ntable static of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)\ntable ctor Low()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Low<?, ?, ?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded(), TokenArg.unbounded(), TokenArg.unbounded());

    public final OpenClassToken<Low<gen_, java_, me_, p_>> token;

    public final MutableFieldRef<Low<gen_, java_, me_, p_>, gen_> first;

    public final CtorRef0<Low<gen_, java_, me_, p_>> new_;

    public final MethodRef3<Low<gen_, java_, me_, p_>, Set<gen_>, java_, me_, p_> all_java_me_p;

    private final RefToken<gen_> gen_;

    private final RefToken<java_> java_;

    private final RefToken<me_> me_;

    private final RefToken<p_> p_;

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

    public <gen__> VoidMethodRef1<Low<gen_, java_, me_, p_>, Set<gen__>> each_Set(RefToken<gen__> gen__) {
        return UnsafeFacts.voidMethod(token, "each", UnsafeFacts.<Set<gen__>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(gen__)), MemberTraits.OVERRIDABLE.withTypeArgs(gen__));
    }

    public static <gen_, java_, me_, p_> StaticMethodRef4<Low<gen_, java_, me_, p_>, gen_, java_, me_, p_> of_gen_java_me_p(RefToken<gen_> gen_, RefToken<java_> java_, RefToken<me_> me_, RefToken<p_> p_) {
        return UnsafeFacts.staticMethod(ANY, "of", UnsafeFacts.<Low<gen_, java_, me_, p_>>openClassToken(Data.SHAPE, TokenArg.exact(gen_), TokenArg.exact(java_), TokenArg.exact(me_), TokenArg.exact(p_)), UnsafeFacts.param(gen_, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(java_, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(me_, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(p_, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(gen_, java_, me_, p_));
    }

    public <java__, me__> MethodRef2<Low<gen_, java_, me_, p_>, java__, me__, Set<p_>> pick_me_Set(RefToken<java__> java__, RefToken<me__> me__) {
        return UnsafeFacts.method(token, "pick", java__, UnsafeFacts.param(me__, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.<Set<p_>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(p_)), MemberTraits.OVERRIDABLE.withTypeArgs(java__, me__));
    }
}
