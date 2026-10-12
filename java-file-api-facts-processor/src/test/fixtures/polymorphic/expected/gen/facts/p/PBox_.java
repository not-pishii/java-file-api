package gen.facts.p;

import gen.facts.p.PBox_.Canonical;
import gen.facts.p.PBox_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.PBox;

/// The full metamodel of [PBox], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PBox] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [PBox] by the proof its metamodel starts, with the stages of `PBox_Heritage`.
///
/// @param <T> a type argument of [PBox]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PBox.class, fingerprint = "46f376b820b3b831020eecfcbec5a93199dbd52d7bf44134d3152f4779bf49dc", complete = true, format = 14)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PBox_"), "46f376b820b3b831020eecfcbec5a93199dbd52d7bf44134d3152f4779bf49dc", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PBox"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("map", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("shadow", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("ofEnum", Param.fixed(ClassDesc.of("java.lang.Class")))), Set.of(Signature.of("PBox"))), false, new PBox_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PBox], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PBox].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.PBox open-class sealed=no nesting=top
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
        inherit ctor PBox() public () throws -
        inherit field public p.PBox t
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method map(java.util.function.Function) erased (java.util.function.Function) overrides - by class public concrete p.PBox <^0> (java.util.function.Function<? super #0, ? extends ^0>) -> p.PBox<^0> throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method of(java.lang.Object) erased (java.lang.Object) overrides - by class public static p.PBox <^0> (^0) -> p.PBox<^0> throws -
        inherit method ofEnum(java.lang.Class) erased (java.lang.Class) overrides - by class public static p.PBox <^0 extends java.lang.Enum<^0>> (java.lang.Class<^0>) -> p.PBox<^0> throws -
        inherit method put(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete p.PBox <^0 extends #0> (^0) -> void throws -
        inherit method shadow(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete p.PBox <^0> (^0) -> ^0 throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
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

    /// Starts the proof that a class being declared extends [PBox]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Complete<Self, PBox<T>> extend(RefToken<Self> self) {
        return new PBox_Heritage().extend(this.token, self);
    }
}
