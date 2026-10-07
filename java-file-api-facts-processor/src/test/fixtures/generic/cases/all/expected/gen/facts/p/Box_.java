package gen.facts.p;

import gen.facts.p.Box_.Canonical;
import gen.facts.p.Box_.Data;
import gen.facts.p.Box_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
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
import p.Box;

/// The full metamodel of [Box], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Box] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Box]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Box.class, fingerprint = "d8126ca3995b5fbc5fc59fdfbb4a01b691118d2aa317db08324a20d2ab22b7e7", complete = true, format = 9)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Box_<T> {
    /// The shape of [Box] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Box] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Box_"), "d8126ca3995b5fbc5fc59fdfbb4a01b691118d2aa317db08324a20d2ab22b7e7", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Box"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("asList"), Signature.of("clone"), Signature.of("drainTo", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("ints"), Signature.of("nested"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("raw", Param.fixed(ClassDesc.of("java.util.Map"))), Signature.of("rawSelf"), Signature.of("sameAs", Param.fixed(ClassDesc.of("p.Box"))), Signature.of("self"), Signature.of("set", Param.var(0)), Signature.of("toArray", Param.var(0, 1)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("ofString"), Signature.of("size", Param.fixed(ClassDesc.of("p.Box")))), Set.of(Signature.of("Box", Param.var(0)), Signature.of("Box"))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Box] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Box] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18(), m19(), m20(), m21(), m22(), m23(), m24()), List.of(c0(), c1()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.Collection"), List.of(Types.extendsBound(Types.typeVar("T"))))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.util.Collection"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("asList"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.List"), List.of(Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("drainTo", Param.fixed(ClassDesc.of("java.util.Collection"))), List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.Collection"), List.of(Types.superBound(Types.typeVar("T"))))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.util.Collection"))), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("get"), List.of(), List.of(), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("ints"), List.of(), List.of(), Arity.FIXED, new Of(Types.array(PrimitiveTypeRef.INT)), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("nested"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.exact(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.exact(Types.typeVar("T")))))))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Box"), Signature.of("ofString"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String")))))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("raw", Param.fixed(ClassDesc.of("java.util.Map"))), List.of(), List.of(Types.of(ClassDesc.of("java.util.Map"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.util.List"))), List.of(), Set.of(List.of(ClassDesc.of("java.util.Map"))), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("rawSelf"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Box"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m16() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("sameAs", Param.fixed(ClassDesc.of("p.Box"))), List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.unbounded()))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("p.Box"))), List.of());
            }

            private static Method m17() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("self"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m18() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("set", Param.var(0)), List.of(), List.of(Types.typeVar("T")), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m19() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Box"), Signature.of("size", Param.fixed(ClassDesc.of("p.Box"))), List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.unbounded()))), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of(ClassDesc.of("p.Box"))), List.of());
            }

            private static Method m20() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Box"), Signature.of("toArray", Param.var(0, 1)), List.of(), List.of(Types.array(Types.typeVar("T"))), Arity.FIXED, new Of(Types.array(Types.typeVar("T"))), List.of(), Set.of(List.of(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), List.of());
            }

            private static Method m21() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m22() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m23() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m24() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Box", Param.var(0)), List.of(), List.of(Types.typeVar("T")), Arity.FIXED, List.of());
            }

            private static Constructor c1() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Box"), List.of(), List.of(), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Box], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Box].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Box open-class sealed=no
        tparams #0
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (#0) throws -
        member ctor public () throws -
        member field public instance final #0 initial
        member field public instance mutable #0 value
        member field public static constant java.lang.String NAME = "box"
        member field public static mutable int count
        member method public overridable addAll(java.util.Collection<? extends #0>) -> void throws -
        member method public overridable asList() -> java.util.List<#0> throws -
        member method public overridable drainTo(java.util.Collection<? super #0>) -> void throws -
        member method public overridable get() -> #0 throws -
        member method public overridable ints() -> int[] throws -
        member method public overridable nested() -> p.Box<p.Box<#0>> throws -
        member method public overridable raw(java.util.Map) -> java.util.List throws -
        member method public overridable rawSelf() -> p.Box throws -
        member method public overridable sameAs(p.Box<?>) -> boolean throws -
        member method public overridable self() -> p.Box<#0> throws -
        member method public overridable set(#0) -> void throws -
        member method public overridable toArray(#0[]) -> #0[] throws -
        member method public static ofString() -> p.Box<java.lang.String> throws -
        member method public static size(p.Box<?>) -> int throws -
        inherit ctor public (#0) throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Box addAll(java.util.Collection<? extends #0>) -> void throws - erased (java.util.Collection) overrides -
        inherit method public concrete p.Box asList() -> java.util.List<#0> throws - erased () overrides -
        inherit method public concrete p.Box drainTo(java.util.Collection<? super #0>) -> void throws - erased (java.util.Collection) overrides -
        inherit method public concrete p.Box get() -> #0 throws - erased () overrides -
        inherit method public concrete p.Box ints() -> int[] throws - erased () overrides -
        inherit method public concrete p.Box nested() -> p.Box<p.Box<#0>> throws - erased () overrides -
        inherit method public concrete p.Box raw(java.util.Map) -> java.util.List throws - erased (java.util.Map) overrides -
        inherit method public concrete p.Box rawSelf() -> p.Box throws - erased () overrides -
        inherit method public concrete p.Box sameAs(p.Box<?>) -> boolean throws - erased (p.Box) overrides -
        inherit method public concrete p.Box self() -> p.Box<#0> throws - erased () overrides -
        inherit method public concrete p.Box set(#0) -> void throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Box toArray(#0[]) -> #0[] throws - erased (java.lang.Object[]) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Box ofString() -> p.Box<java.lang.String> throws - erased () overrides -
        inherit method public static p.Box size(p.Box<?>) -> int throws - erased (p.Box) overrides -
        table abstract -
        table concrete addAll(java.util.Collection); asList(); clone(); drainTo(java.util.Collection); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); ints(); nested(); notify(); notifyAll(); raw(java.util.Map); rawSelf(); sameAs(p.Box); self(); set(#0); toArray(#0[]); toString(); wait(); wait(long); wait(long, int)
        table static ofString(); size(p.Box)
        table ctor Box(#0); Box()
        """;

        private Canonical() {
        }
    }

    /// The token of [Box] with a wildcard for every type argument.
    public static final OpenClassToken<Box<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The fact of [Box#NAME].
    public static final StaticFieldRef<String> NAME = UnsafeFacts.constantField(ANY, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "box");

    /// The fact of [Box#count].
    public static final MutableStaticFieldRef<Int> count = UnsafeFacts.mutableStaticField(ANY, "count", PrimitiveToken.INT);

    /// The fact of [Box#ofString()].
    public static final StaticMethodRef0<Box<String>> ofString = UnsafeFacts.staticMethod(ANY, "ofString", UnsafeFacts.<Box<String>>openClassToken(Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.FINAL);

    /// The fact of [Box#size(Box)].
    public static final StaticMethodRef1<Int, Box<?>> size_Box = UnsafeFacts.staticMethod(ANY, "size", PrimitiveToken.INT, UnsafeFacts.<Box<?>>openClassToken(Data.SHAPE, TokenArg.unbounded()), MemberTraits.FINAL);

    /// The token of [Box] with the type arguments of this metamodel.
    public final OpenClassToken<Box<T>> token;

    /// The fact of [Box#initial].
    public final FieldRef<Box<T>, T> initial;

    /// The fact of [Box#value].
    public final MutableFieldRef<Box<T>, T> value;

    /// The fact of [Box#Box()].
    public final CtorRef0<Box<T>> new_;

    /// The fact of [Box#Box(Object)].
    public final CtorRef1<Box<T>, T> new_T;

    /// The fact of [Box#addAll(Collection)].
    public final VoidMethodRef1<Box<T>, Collection<? extends T>> addAll_Collection;

    /// The fact of [Box#asList()].
    public final MethodRef0<Box<T>, List<T>> asList;

    /// The fact of [Box#drainTo(Collection)].
    public final VoidMethodRef1<Box<T>, Collection<? super T>> drainTo_Collection;

    /// The fact of [Box#get()].
    public final MethodRef0<Box<T>, T> get;

    /// The fact of [Box#ints()].
    public final MethodRef0<Box<T>, int[]> ints;

    /// The fact of [Box#nested()].
    public final MethodRef0<Box<T>, Box<Box<T>>> nested;

    /// The fact of [Box#rawSelf()].
    public final MethodRef0<Box<T>, Box> rawSelf;

    /// The fact of [Box#raw(Map)].
    public final MethodRef1<Box<T>, List, Map> raw_Map;

    /// The fact of [Box#sameAs(Box)].
    public final MethodRef1<Box<T>, Bool, Box<?>> sameAs_Box;

    /// The fact of [Box#self()].
    public final MethodRef0<Box<T>, Box<T>> self;

    /// The fact of [Box#set(Object)].
    public final VoidMethodRef1<Box<T>, T> set_T;

    /// The fact of [Box#toArray(Object\[\])].
    public final MethodRef1<Box<T>, T[], T[]> toArray_TArray;

    /// The metamodel of [Box] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Box_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.initial = UnsafeFacts.field(token, "initial", t);
        this.value = UnsafeFacts.mutableField(token, "value", t);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.new_T = UnsafeFacts.ctor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL);
        this.addAll_Collection = UnsafeFacts.voidMethod(token, "addAll", UnsafeFacts.<Collection<? extends T>>interfaceToken(gen.facts.java.util.Collection_.Data.SHAPE, TokenArg.extendsBound(t)), MemberTraits.OVERRIDABLE);
        this.asList = UnsafeFacts.method(token, "asList", UnsafeFacts.<List<T>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(t)), MemberTraits.OVERRIDABLE);
        this.drainTo_Collection = UnsafeFacts.voidMethod(token, "drainTo", UnsafeFacts.<Collection<? super T>>interfaceToken(gen.facts.java.util.Collection_.Data.SHAPE, TokenArg.superBound(t)), MemberTraits.OVERRIDABLE);
        this.get = UnsafeFacts.method(token, "get", t, MemberTraits.OVERRIDABLE);
        this.ints = UnsafeFacts.method(token, "ints", PrimitiveToken.INT.array(), MemberTraits.OVERRIDABLE);
        this.nested = UnsafeFacts.method(token, "nested", UnsafeFacts.<Box<Box<T>>>openClassToken(Data.SHAPE, TokenArg.exact(token)), MemberTraits.OVERRIDABLE);
        this.rawSelf = UnsafeFacts.method(token, "rawSelf", UnsafeFacts.<Box>openClassToken(Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.raw_Map = UnsafeFacts.method(token, "raw", UnsafeFacts.<List>interfaceToken(gen.facts.java.util.List_.Data.SHAPE), UnsafeFacts.<Map>interfaceToken(gen.facts.java.util.Map_.Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.sameAs_Box = UnsafeFacts.method(token, "sameAs", PrimitiveToken.BOOLEAN, UnsafeFacts.<Box<?>>openClassToken(Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);
        this.self = UnsafeFacts.method(token, "self", token, MemberTraits.OVERRIDABLE);
        this.set_T = UnsafeFacts.voidMethod(token, "set", UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
        this.toArray_TArray = UnsafeFacts.method(token, "toArray", ArrayToken.of(t), UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.OVERRIDABLE);
    }
}
