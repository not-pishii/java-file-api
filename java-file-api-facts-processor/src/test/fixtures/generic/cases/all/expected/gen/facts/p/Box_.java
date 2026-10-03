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
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Box;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Box.class, fingerprint = "f70550717d12d61289088abcf7a1c58ca5fd50ff5dd54cb31a52436f910c3bd9", complete = true, format = 4)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class Box_<T> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Box_"), "f70550717d12d61289088abcf7a1c58ca5fd50ff5dd54cb31a52436f910c3bd9", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Box"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("asList"), Signature.of("clone"), Signature.of("drainTo", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("ints"), Signature.of("nested"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("raw", Param.fixed(ClassDesc.of("java.util.Map"))), Signature.of("rawSelf"), Signature.of("sameAs", Param.fixed(ClassDesc.of("p.Box"))), Signature.of("self"), Signature.of("set", Param.var(0)), Signature.of("toArray", Param.var(0, 1)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("ofString"), Signature.of("size", Param.fixed(ClassDesc.of("p.Box")))), Set.of(Signature.of("Box", Param.var(0)), Signature.of("Box"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Box open-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(#0) throws -\nmember ctor() throws -\nmember field instance final #0 initial\nmember field instance mutable #0 value\nmember field static constant java.lang.String NAME = \"box\"\nmember field static mutable int count\nmember method overridable addAll(java.util.Collection<? extends #0>) -> void throws -\nmember method overridable asList() -> java.util.List<#0> throws -\nmember method overridable drainTo(java.util.Collection<? super #0>) -> void throws -\nmember method overridable get() -> #0 throws -\nmember method overridable ints() -> int[] throws -\nmember method overridable nested() -> p.Box<p.Box<#0>> throws -\nmember method overridable raw(java.util.Map) -> java.util.List throws -\nmember method overridable rawSelf() -> p.Box throws -\nmember method overridable sameAs(p.Box<?>) -> boolean throws -\nmember method overridable self() -> p.Box<#0> throws -\nmember method overridable set(#0) -> void throws -\nmember method overridable toArray(#0[]) -> #0[] throws -\nmember method static ofString() -> p.Box<java.lang.String> throws -\nmember method static size(p.Box<?>) -> int throws -\ntable abstract -\ntable concrete addAll(java.util.Collection); asList(); clone(); drainTo(java.util.Collection); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); ints(); nested(); notify(); notifyAll(); raw(java.util.Map); rawSelf(); sameAs(p.Box); self(); set(#0); toArray(#0[]); toString(); wait(); wait(long); wait(long, int)\ntable static ofString(); size(p.Box)\ntable ctor Box(#0); Box()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Box<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    public static final StaticFieldRef<String> NAME = UnsafeFacts.constantField(ANY, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "box");

    public static final MutableStaticFieldRef<Int> count = UnsafeFacts.mutableStaticField(ANY, "count", PrimitiveToken.INT);

    public static final StaticMethodRef0<Box<String>> ofString = UnsafeFacts.staticMethod(ANY, "ofString", UnsafeFacts.<Box<String>>openClassToken(Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.FINAL);

    public static final StaticMethodRef1<Int, Box<?>> size_Box = UnsafeFacts.staticMethod(ANY, "size", PrimitiveToken.INT, UnsafeFacts.<Box<?>>openClassToken(Data.SHAPE, TokenArg.unbounded()), MemberTraits.FINAL);

    public final OpenClassToken<Box<T>> token;

    public final FieldRef<Box<T>, T> initial;

    public final MutableFieldRef<Box<T>, T> value;

    public final CtorRef0<Box<T>> new_;

    public final CtorRef1<Box<T>, T> new_T;

    public final VoidMethodRef1<Box<T>, Collection<? extends T>> addAll_Collection;

    public final MethodRef0<Box<T>, List<T>> asList;

    public final VoidMethodRef1<Box<T>, Collection<? super T>> drainTo_Collection;

    public final MethodRef0<Box<T>, T> get;

    public final MethodRef0<Box<T>, int[]> ints;

    public final MethodRef0<Box<T>, Box<Box<T>>> nested;

    public final MethodRef0<Box<T>, Box> rawSelf;

    public final MethodRef1<Box<T>, List, Map> raw_Map;

    public final MethodRef1<Box<T>, Bool, Box<?>> sameAs_Box;

    public final MethodRef0<Box<T>, Box<T>> self;

    public final VoidMethodRef1<Box<T>, T> set_T;

    public final MethodRef1<Box<T>, T[], T[]> toArray_TArray;

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
