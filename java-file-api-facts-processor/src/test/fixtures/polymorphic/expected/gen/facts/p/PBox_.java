package gen.facts.p;

import gen.facts.p.PBox_.Canonical;
import gen.facts.p.PBox_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.PBox;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PBox.class, fingerprint = "2b3e20203cdc72c93d9cd136acab208ac8246b585dcce7f433f7148fd46a2ef2", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PBox_<T> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PBox_"), "2b3e20203cdc72c93d9cd136acab208ac8246b585dcce7f433f7148fd46a2ef2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PBox"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("map", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("shadow", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("ofEnum", Param.fixed(ClassDesc.of("java.lang.Class")))), Set.of(Signature.of("PBox"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.PBox open-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable #0 t\nmember method overridable <^0 extends #0> put(^0) -> void throws -\nmember method overridable <^0> map(java.util.function.Function<? super #0, ? extends ^0>) -> p.PBox<^0> throws -\nmember method overridable <^0> shadow(^0) -> ^0 throws -\nmember method static <^0 extends java.lang.Enum<^0>> ofEnum(java.lang.Class<^0>) -> p.PBox<^0> throws -\nmember method static <^0> of(^0) -> p.PBox<^0> throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); map(java.util.function.Function); notify(); notifyAll(); put(java.lang.Object); shadow(java.lang.Object); toString(); wait(); wait(long); wait(long, int)\ntable static of(java.lang.Object); ofEnum(java.lang.Class)\ntable ctor PBox()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<PBox<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    public final OpenClassToken<PBox<T>> token;

    public final MutableFieldRef<PBox<T>, T> t;

    public final CtorRef0<PBox<T>> new_;

    private final RefToken<T> t_;

    public PBox_(RefToken<T> t_) {
        this.t_ = t_;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t_));
        this.t = UnsafeFacts.mutableField(token, "t", t_);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
    }

    public <R> MethodRef1<PBox<T>, PBox<R>, Function<? super T, ? extends R>> map_Function(RefToken<R> r) {
        return UnsafeFacts.method(token, "map", UnsafeFacts.<PBox<R>>openClassToken(Data.SHAPE, TokenArg.exact(r)), UnsafeFacts.<Function<? super T, ? extends R>>interfaceToken(gen.facts.java.util.function.Function_.Data.SHAPE, TokenArg.superBound(t_), TokenArg.extendsBound(r)), MemberTraits.OVERRIDABLE.withTypeArgs(r));
    }

    public static <E extends Enum<E>> StaticMethodRef1<PBox<E>, Class<E>> ofEnum_Class(RefToken<E> e) {
        return UnsafeFacts.staticMethod(ANY, "ofEnum", UnsafeFacts.<PBox<E>>openClassToken(Data.SHAPE, TokenArg.exact(e)), UnsafeFacts.<Class<E>>finalClassToken(gen.facts.java.lang.Class_.Data.SHAPE, TokenArg.exact(e)), MemberTraits.FINAL.withTypeArgs(e));
    }

    public static <T> StaticMethodRef1<PBox<T>, T> of_T(RefToken<T> t) {
        return UnsafeFacts.staticMethod(ANY, "of", UnsafeFacts.<PBox<T>>openClassToken(Data.SHAPE, TokenArg.exact(t)), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(t));
    }

    public <U extends T> VoidMethodRef1<PBox<T>, U> put_U(RefToken<U> u) {
        return UnsafeFacts.voidMethod(token, "put", UnsafeFacts.param(u, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(u));
    }

    public <T_> MethodRef1<PBox<T>, T_, T_> shadow_T(RefToken<T_> t__) {
        return UnsafeFacts.method(token, "shadow", t__, UnsafeFacts.param(t__, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t__));
    }
}
