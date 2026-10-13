package gen.facts.p;

import gen.facts.p.Box_.Canonical;
import gen.facts.p.Box_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.FieldRef;
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
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Box;

/// The full metamodel of [Box], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Box] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Box] by the proof its metamodel starts, with the stages of `Box_Heritage`.
///
/// @param <T> a type argument of [Box]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Box.class, fingerprint = "7cd8ff176d753e8c7c6c834a1669b42dc9b37ef58c464bc22156fbaa7e5712a2", complete = true, format = 14)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Box_"), "7cd8ff176d753e8c7c6c834a1669b42dc9b37ef58c464bc22156fbaa7e5712a2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Box"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("asList"), Signature.of("clone"), Signature.of("drainTo", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("ints"), Signature.of("nested"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("raw", Param.fixed(ClassDesc.of("java.util.Map"))), Signature.of("rawSelf"), Signature.of("sameAs", Param.fixed(ClassDesc.of("p.Box"))), Signature.of("self"), Signature.of("set", Param.var(0)), Signature.of("toArray", Param.var(0, 1)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("ofString"), Signature.of("size", Param.fixed(ClassDesc.of("p.Box")))), Set.of(Signature.of("Box", Param.var(0)), Signature.of("Box"))), false, new Box_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Box], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Box].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Box open-class sealed=no nesting=top
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
        inherit ctor Box(#0) public (#0) throws -
        inherit ctor Box() public () throws -
        inherit field public p.Box NAME
        inherit field public p.Box count
        inherit field public p.Box initial
        inherit field public p.Box value
        inherit method addAll(java.util.Collection) erased (java.util.Collection) overrides - by class public concrete p.Box (java.util.Collection<? extends #0>) -> void throws -
        inherit method asList() erased () overrides - by class public concrete p.Box () -> java.util.List<#0> throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method drainTo(java.util.Collection) erased (java.util.Collection) overrides - by class public concrete p.Box (java.util.Collection<? super #0>) -> void throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method get() erased () overrides - by class public concrete p.Box () -> #0 throws -
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method ints() erased () overrides - by class public concrete p.Box () -> int[] throws -
        inherit method nested() erased () overrides - by class public concrete p.Box () -> p.Box<p.Box<#0>> throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method ofString() erased () overrides - by class public static p.Box () -> p.Box<java.lang.String> throws -
        inherit method raw(java.util.Map) erased (java.util.Map) overrides - by class public concrete p.Box (java.util.Map) -> java.util.List throws -
        inherit method rawSelf() erased () overrides - by class public concrete p.Box () -> p.Box throws -
        inherit method sameAs(p.Box) erased (p.Box) overrides - by class public concrete p.Box (p.Box<?>) -> boolean throws -
        inherit method self() erased () overrides - by class public concrete p.Box () -> p.Box<#0> throws -
        inherit method set(#0) erased (java.lang.Object) overrides - by class public concrete p.Box (#0) -> void throws -
        inherit method size(p.Box) erased (p.Box) overrides - by class public static p.Box (p.Box<?>) -> int throws -
        inherit method toArray(#0[]) erased (java.lang.Object[]) overrides - by class public concrete p.Box (#0[]) -> #0[] throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
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
    public static final StaticMethodRef0<Box<String>> ofString = UnsafeInvocables.staticMethod(ANY, "ofString", UnsafeFacts.<Box<String>>openClassToken(Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.FINAL);

    /// The fact of [Box#size(Box)].
    public static final StaticMethodRef1<Int, Box<?>> size_Box = UnsafeInvocables.staticMethod(ANY, "size", PrimitiveToken.INT, UnsafeFacts.<Box<?>>openClassToken(Data.SHAPE, TokenArg.unbounded()), MemberTraits.FINAL);

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
        this.new_ = UnsafeInvocables.ctor(token, MemberTraits.FINAL);
        this.new_T = UnsafeInvocables.ctor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL);
        this.addAll_Collection = UnsafeInvocables.voidMethod(token, "addAll", UnsafeFacts.<Collection<? extends T>>interfaceToken(gen.facts.java.util.Collection_.Data.SHAPE, TokenArg.extendsBound(t)), MemberTraits.OVERRIDABLE);
        this.asList = UnsafeInvocables.method(token, "asList", UnsafeFacts.<List<T>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(t)), MemberTraits.OVERRIDABLE);
        this.drainTo_Collection = UnsafeInvocables.voidMethod(token, "drainTo", UnsafeFacts.<Collection<? super T>>interfaceToken(gen.facts.java.util.Collection_.Data.SHAPE, TokenArg.superBound(t)), MemberTraits.OVERRIDABLE);
        this.get = UnsafeInvocables.method(token, "get", t, MemberTraits.OVERRIDABLE);
        this.ints = UnsafeInvocables.method(token, "ints", PrimitiveToken.INT.array(), MemberTraits.OVERRIDABLE);
        this.nested = UnsafeInvocables.method(token, "nested", UnsafeFacts.<Box<Box<T>>>openClassToken(Data.SHAPE, TokenArg.exact(token)), MemberTraits.OVERRIDABLE);
        this.rawSelf = UnsafeInvocables.method(token, "rawSelf", UnsafeFacts.<Box>openClassToken(Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.raw_Map = UnsafeInvocables.method(token, "raw", UnsafeFacts.<List>interfaceToken(gen.facts.java.util.List_.Data.SHAPE), UnsafeFacts.<Map>interfaceToken(gen.facts.java.util.Map_.Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.sameAs_Box = UnsafeInvocables.method(token, "sameAs", PrimitiveToken.BOOLEAN, UnsafeFacts.<Box<?>>openClassToken(Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);
        this.self = UnsafeInvocables.method(token, "self", token, MemberTraits.OVERRIDABLE);
        this.set_T = UnsafeInvocables.voidMethod(token, "set", UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
        this.toArray_TArray = UnsafeInvocables.method(token, "toArray", ArrayToken.of(t), UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.OVERRIDABLE);
    }

    /// Starts the proof that a class being declared extends [Box]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Complete<Self, Box<T>> extend(RefToken<Self> self) {
        return new Box_Heritage().extend(this.token, self);
    }
}
