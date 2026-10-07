package gen.facts.p;

import gen.facts.p.ProtG_.Canonical;
import gen.facts.p.ProtG_.Data;
import gen.facts.p.ProtG_.Data.Inherited;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.CtorRef0;
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
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.SuperCtorRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.ProtG;

/// The full metamodel of [ProtG], which `@Facts` asks for: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// A member [ProtG] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.ProtBase`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// @param <T> a type argument of [ProtG]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ProtG.class, fingerprint = "46fd481128cc13123c29565cc7e97df536e21ac0a3a15a51c26efd68ef9cd8ad", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class ProtG_<T> {
    /// The shape of [ProtG] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [ProtG] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.ProtG_"), "46fd481128cc13123c29565cc7e97df536e21ac0a3a15a51c26efd68ef9cd8ad", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.ProtG"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("p.ProtBase"), ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("adopted"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("fixed"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object")), Param.var(0)), Signature.of("pkg"), Signature.of("pub"), Signature.of("set", Param.var(0)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("adoptedStatic"), Signature.of("reset"), Signature.of("spick", Param.fixed(ClassDesc.of("java.lang.Number")))), Set.of(Signature.of("ProtG", Param.var(0)), Signature.of("ProtG"))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [ProtG] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [ProtG] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18(), m19(), m20()), List.of(c0(), c1()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("p.ProtBase"), Signature.of("adopted"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.STATIC, ClassDesc.of("p.ProtBase"), Signature.of("adoptedStatic"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PROTECTED, Dispatch.FINAL, ClassDesc.of("p.ProtG"), Signature.of("fixed"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("p.ProtG"), Signature.of("get"), List.of(), List.of(), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("p.ProtG"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object")), Param.var(0)), List.of(new TypeParam("X", List.of())), List.of(Types.typeVar("X"), Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("X")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PACKAGE, Dispatch.CONCRETE, ClassDesc.of("p.ProtBase"), Signature.of("pkg"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.ProtG"), Signature.of("pub"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PROTECTED, Dispatch.STATIC, ClassDesc.of("p.ProtG"), Signature.of("reset"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("p.ProtG"), Signature.of("set", Param.var(0)), List.of(), List.of(Types.typeVar("T")), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.io.IOException"))), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m16() {
                return new Method(Visibility.PROTECTED, Dispatch.STATIC, ClassDesc.of("p.ProtG"), Signature.of("spick", Param.fixed(ClassDesc.of("java.lang.Number"))), List.of(new TypeParam("X", List.of(Types.of(ClassDesc.of("java.lang.Number"))))), List.of(Types.typeVar("X")), Arity.FIXED, new Of(Types.typeVar("X")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Number"))), List.of());
            }

            private static Method m17() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m18() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m19() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m20() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PROTECTED, Signature.of("ProtG", Param.var(0)), List.of(), List.of(Types.typeVar("T")), Arity.FIXED, List.of(Types.of(ClassDesc.of("java.io.IOException"))));
            }

            private static Constructor c1() {
                return new Constructor(Visibility.PUBLIC, Signature.of("ProtG"), List.of(), List.of(), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [ProtG], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [ProtG].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.ProtG open-class sealed=no
        tparams #0
        superclasses p.ProtBase; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor protected (#0) throws java.io.IOException
        member ctor public () throws -
        member field protected instance final int fin
        member field protected instance mutable #0 item
        member field protected instance mutable int adoptedField
        member field protected static constant int LIMIT = 3
        member field protected static constant java.lang.String NAME = "g"
        member field protected static final java.lang.Object LOCK
        member field protected static mutable int count
        member method protected final fixed() -> void throws -
        member method protected overridable <^0> pick(^0, #0) -> ^0 throws -
        member method protected overridable adopted() -> void throws -
        member method protected overridable get() -> #0 throws -
        member method protected overridable set(#0) -> void throws java.io.IOException
        member method protected static <^0 extends java.lang.Number> spick(^0) -> ^0 throws -
        member method protected static adoptedStatic() -> void throws -
        member method protected static reset() -> void throws -
        member method public overridable pub() -> void throws -
        inherit ctor protected (#0) throws java.io.IOException
        inherit ctor public () throws -
        inherit method package concrete p.ProtBase pkg() -> void throws - erased () overrides -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method protected concrete p.ProtBase adopted() -> void throws - erased () overrides -
        inherit method protected concrete p.ProtG <^0> pick(^0, #0) -> ^0 throws - erased (java.lang.Object, java.lang.Object) overrides -
        inherit method protected concrete p.ProtG get() -> #0 throws - erased () overrides -
        inherit method protected concrete p.ProtG set(#0) -> void throws java.io.IOException erased (java.lang.Object) overrides -
        inherit method protected final p.ProtG fixed() -> void throws - erased () overrides -
        inherit method protected static p.ProtBase adoptedStatic() -> void throws - erased () overrides -
        inherit method protected static p.ProtG <^0 extends java.lang.Number> spick(^0) -> ^0 throws - erased (java.lang.Number) overrides -
        inherit method protected static p.ProtG reset() -> void throws - erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.ProtG pub() -> void throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete adopted(); clone(); equals(java.lang.Object); finalize(); fixed(); get(); getClass(); hashCode(); notify(); notifyAll(); pick(java.lang.Object, #0); pkg(); pub(); set(#0); toString(); wait(); wait(long); wait(long, int)
        table static adoptedStatic(); reset(); spick(java.lang.Number)
        table ctor ProtG(#0); ProtG()
        """;

        private Canonical() {
        }
    }

    /// The token of [ProtG] with a wildcard for every type argument.
    public static final OpenClassToken<ProtG<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The fact of [ProtG#LIMIT], which is `protected`: a subclass alone uses it.
    public static final Protected<ProtG<?>, StaticFieldRef<Int>> LIMIT = UnsafeFacts.protected_(ANY, UnsafeFacts.constantField(ANY, "LIMIT", PrimitiveToken.INT, 3, Access.PROTECTED));

    /// The fact of [ProtG#LOCK], which is `protected`: a subclass alone uses it.
    public static final Protected<ProtG<?>, StaticFieldRef<Object>> LOCK = UnsafeFacts.protected_(ANY, UnsafeFacts.staticField(ANY, "LOCK", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), Access.PROTECTED));

    /// The fact of [ProtG#NAME], which is `protected`: a subclass alone uses it.
    public static final Protected<ProtG<?>, StaticFieldRef<String>> NAME = UnsafeFacts.protected_(ANY, UnsafeFacts.constantField(ANY, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "g", Access.PROTECTED));

    /// The fact of [ProtG#count], which is `protected`: a subclass alone uses it.
    public static final Protected<ProtG<?>, MutableStaticFieldRef<Int>> count = UnsafeFacts.protected_(ANY, UnsafeFacts.mutableStaticField(ANY, "count", PrimitiveToken.INT, Access.PROTECTED));

    /// The fact of [ProtG#adoptedStatic()], declared in `p.ProtBase`, which is not `public`, which is `protected`: a subclass alone uses it.
    public static final Protected<ProtG<?>, VoidStaticMethodRef0> adoptedStatic = UnsafeFacts.protected_(ANY, UnsafeFacts.voidStaticMethod(ANY, "adoptedStatic", MemberTraits.FINAL.with(Access.PROTECTED)));

    /// The fact of [ProtG#reset()], which is `protected`: a subclass alone uses it.
    public static final Protected<ProtG<?>, VoidStaticMethodRef0> reset = UnsafeFacts.protected_(ANY, UnsafeFacts.voidStaticMethod(ANY, "reset", MemberTraits.FINAL.with(Access.PROTECTED)));

    /// The token of [ProtG] with the type arguments of this metamodel.
    public final OpenClassToken<ProtG<T>> token;

    /// The fact of [ProtG#adoptedField], declared in `p.ProtBase`, which is not `public`, which is `protected`: a subclass alone uses it.
    public final Protected<ProtG<T>, MutableFieldRef<ProtG<T>, Int>> adoptedField;

    /// The fact of [ProtG#fin], which is `protected`: a subclass alone uses it.
    public final Protected<ProtG<T>, FieldRef<ProtG<T>, Int>> fin;

    /// The fact of [ProtG#item], which is `protected`: a subclass alone uses it.
    public final Protected<ProtG<T>, MutableFieldRef<ProtG<T>, T>> item;

    /// The fact of [ProtG#ProtG()].
    public final CtorRef0<ProtG<T>> new_;

    /// The fact of [ProtG#ProtG(Object)], which is `protected`: a subclass alone uses it.
    public final SuperCtorRef1<ProtG<T>, T> super_T;

    /// The fact of [ProtG#adopted()], declared in `p.ProtBase`, which is not `public`, which is `protected`: a subclass alone uses it.
    public final Protected<ProtG<T>, VoidMethodRef0<ProtG<T>>> adopted;

    /// The fact of [ProtG#fixed()], which is `protected`: a subclass alone uses it.
    public final Protected<ProtG<T>, VoidMethodRef0<ProtG<T>>> fixed;

    /// The fact of [ProtG#get()], which is `protected`: a subclass alone uses it.
    public final Protected<ProtG<T>, MethodRef0<ProtG<T>, T>> get;

    /// The fact of [ProtG#pub()].
    public final VoidMethodRef0<ProtG<T>> pub;

    /// The fact of [ProtG#set(Object)], which is `protected`: a subclass alone uses it.
    public final Protected<ProtG<T>, VoidMethodRef1<ProtG<T>, T>> set_T;

    private final RefToken<T> t;

    /// The metamodel of [ProtG] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public ProtG_(RefToken<T> t) {
        this.t = t;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.adoptedField = UnsafeFacts.protected_(token, UnsafeFacts.mutableField(token, "adoptedField", PrimitiveToken.INT, Access.PROTECTED));
        this.fin = UnsafeFacts.protected_(token, UnsafeFacts.field(token, "fin", PrimitiveToken.INT, Access.PROTECTED));
        this.item = UnsafeFacts.protected_(token, UnsafeFacts.mutableField(token, "item", t, Access.PROTECTED));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.super_T = UnsafeFacts.superCtor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)).with(Access.PROTECTED));
        this.adopted = UnsafeFacts.protected_(token, UnsafeFacts.voidMethod(token, "adopted", MemberTraits.OVERRIDABLE.with(Access.PROTECTED)));
        this.fixed = UnsafeFacts.protected_(token, UnsafeFacts.voidMethod(token, "fixed", MemberTraits.FINAL.with(Access.PROTECTED)));
        this.get = UnsafeFacts.protected_(token, UnsafeFacts.method(token, "get", t, MemberTraits.OVERRIDABLE.with(Access.PROTECTED)));
        this.pub = UnsafeFacts.voidMethod(token, "pub", MemberTraits.OVERRIDABLE);
        this.set_T = UnsafeFacts.protected_(token, UnsafeFacts.voidMethod(token, "set", UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)).with(Access.PROTECTED)));
    }

    /// The fact of [ProtG#pick(Object, Object)], which is `protected`: a subclass alone uses it, for the type arguments the tokens give.
    ///
    /// @param <X> a type argument of the method
    /// @param x the token of the type argument `X`
    /// @return the fact
    public <X> Protected<ProtG<T>, MethodRef2<ProtG<T>, X, X, T>> pick_X_T(RefToken<X> x) {
        return UnsafeFacts.protected_(token, UnsafeFacts.method(token, "pick", x, UnsafeFacts.param(x, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE.withTypeArgs(x).with(Access.PROTECTED)));
    }

    /// The fact of [ProtG#spick(Number)], which is `protected`: a subclass alone uses it, for the type arguments the tokens give.
    ///
    /// @param <X> a type argument of the method
    /// @param x the token of the type argument `X`
    /// @return the fact
    public static <X extends Number> Protected<ProtG<?>, StaticMethodRef1<X, X>> spick_X(RefToken<X> x) {
        return UnsafeFacts.protected_(ANY, UnsafeFacts.staticMethod(ANY, "spick", x, UnsafeFacts.param(x, Param.fixed(ClassDesc.of("java.lang.Number"))), MemberTraits.FINAL.withTypeArgs(x).with(Access.PROTECTED)));
    }
}
