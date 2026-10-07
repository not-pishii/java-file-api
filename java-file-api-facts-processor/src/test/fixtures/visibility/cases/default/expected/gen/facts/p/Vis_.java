package gen.facts.p;

import gen.facts.p.Vis_.Canonical;
import gen.facts.p.Vis_.Data;
import gen.facts.p.Vis_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Access;
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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.SuperCtorRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Outer.Pub;
import p.Vis;

/// The full metamodel of [Vis], which `@Facts` asks for: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// A member [Vis] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `field hf`, which mentions types that are not public: p.Hidden
/// - `field hl`, which mentions types that are not public: p.Hidden
/// - `constructor Vis(p.Hidden)`, which mentions types that are not public: p.Hidden
/// - `method hidden()`, which mentions types that are not public: p.Hidden
/// - `method takes(p.Hidden)`, which mentions types that are not public: p.Hidden
/// - `method arr()`, which mentions types that are not public: p.Hidden
/// - `method nested(p.Outer.PkgNested)`, which mentions types that are not public: p.Outer$PkgNested
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Vis.class, fingerprint = "2809b509e207f062382ddfaeefab2dbb155acb737941a8841d20353223c2b218", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Vis_ {
    /// The shape of [Vis] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Vis] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Vis_"), "2809b509e207f062382ddfaeefab2dbb155acb737941a8841d20353223c2b218", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Vis"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("arr"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("nested", Param.fixed(ClassDesc.of("p.Outer$PkgNested"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ok"), Signature.of("pkgM"), Signature.of("protM"), Signature.of("pubM"), Signature.of("takes", Param.fixed(ClassDesc.of("p.Hidden"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sPub")), Set.of(Signature.of("Vis"), Signature.of("Vis", Param.fixed(ConstantDescs.CD_int)), Signature.of("Vis", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Vis", Param.fixed(ClassDesc.of("p.Hidden"))))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Vis] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Vis] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18(), m19()), List.of(c0(), c1(), c2(), c3()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("arr"), List.of(), List.of(), Arity.FIXED, new Of(Types.array(Types.of(ClassDesc.of("p.Hidden")))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("hidden"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Hidden"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("nested", Param.fixed(ClassDesc.of("p.Outer$PkgNested"))), List.of(), List.of(Types.of(ClassDesc.of("p.Outer$PkgNested"))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("p.Outer$PkgNested"))), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("ok"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Outer$Pub"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PACKAGE, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("pkgM"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("protM"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("pubM"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Vis"), Signature.of("sPub"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Vis"), Signature.of("takes", Param.fixed(ClassDesc.of("p.Hidden"))), List.of(), List.of(Types.of(ClassDesc.of("p.Hidden"))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("p.Hidden"))), List.of());
            }

            private static Method m16() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m17() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m18() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m19() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Vis"), List.of(), List.of(), Arity.FIXED, List.of());
            }

            private static Constructor c1() {
                return new Constructor(Visibility.PROTECTED, Signature.of("Vis", Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.INT), Arity.FIXED, List.of());
            }

            private static Constructor c2() {
                return new Constructor(Visibility.PACKAGE, Signature.of("Vis", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, List.of());
            }

            private static Constructor c3() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Vis", Param.fixed(ClassDesc.of("p.Hidden"))), List.of(), List.of(Types.of(ClassDesc.of("p.Hidden"))), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Vis], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Vis].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Vis open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor protected (int) throws -
        member ctor public () throws -
        member field protected instance mutable int prot
        member field public instance mutable int pub
        member method protected overridable protM() -> void throws -
        member method public overridable ok() -> p.Outer$Pub throws -
        member method public overridable pubM() -> void throws -
        member method public static sPub() -> void throws -
        inherit ctor package (java.lang.String) throws -
        inherit ctor protected (int) throws -
        inherit ctor public () throws -
        inherit ctor public (p.Hidden) throws -
        inherit method package concrete p.Vis pkgM() -> void throws - erased () overrides -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method protected concrete p.Vis protM() -> void throws - erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Vis arr() -> p.Hidden[] throws - erased () overrides -
        inherit method public concrete p.Vis hidden() -> p.Hidden throws - erased () overrides -
        inherit method public concrete p.Vis nested(p.Outer$PkgNested) -> void throws - erased (p.Outer$PkgNested) overrides -
        inherit method public concrete p.Vis ok() -> p.Outer$Pub throws - erased () overrides -
        inherit method public concrete p.Vis pubM() -> void throws - erased () overrides -
        inherit method public concrete p.Vis takes(p.Hidden) -> void throws - erased (p.Hidden) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Vis sPub() -> void throws - erased () overrides -
        table abstract -
        table concrete arr(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); hidden(); nested(p.Outer$PkgNested); notify(); notifyAll(); ok(); pkgM(); protM(); pubM(); takes(p.Hidden); toString(); wait(); wait(long); wait(long, int)
        table static sPub()
        table ctor Vis(); Vis(int); Vis(java.lang.String); Vis(p.Hidden)
        """;

        private Canonical() {
        }
    }

    /// The token of [Vis].
    public static final OpenClassToken<Vis> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Vis#prot], which is `protected`: a subclass alone uses it.
    public static final Protected<Vis, MutableFieldRef<Vis, Int>> prot = UnsafeFacts.protected_(TOKEN, UnsafeFacts.mutableField(TOKEN, "prot", PrimitiveToken.INT, Access.PROTECTED));

    /// The fact of [Vis#pub].
    public static final MutableFieldRef<Vis, Int> pub = UnsafeFacts.mutableField(TOKEN, "pub", PrimitiveToken.INT);

    /// The fact of [Vis#Vis()].
    public static final CtorRef0<Vis> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Vis#Vis(int)], which is `protected`: a subclass alone uses it.
    public static final SuperCtorRef1<Vis, Int> super_int = UnsafeFacts.superCtor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL.with(Access.PROTECTED));

    /// The fact of [Vis#ok()].
    public static final MethodRef0<Vis, Pub> ok = UnsafeFacts.method(TOKEN, "ok", UnsafeFacts.<Pub>openClassToken(Outer_Pub_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Vis#protM()], which is `protected`: a subclass alone uses it.
    public static final Protected<Vis, VoidMethodRef0<Vis>> protM = UnsafeFacts.protected_(TOKEN, UnsafeFacts.voidMethod(TOKEN, "protM", MemberTraits.OVERRIDABLE.with(Access.PROTECTED)));

    /// The fact of [Vis#pubM()].
    public static final VoidMethodRef0<Vis> pubM = UnsafeFacts.voidMethod(TOKEN, "pubM", MemberTraits.OVERRIDABLE);

    /// The fact of [Vis#sPub()].
    public static final VoidStaticMethodRef0 sPub = UnsafeFacts.voidStaticMethod(TOKEN, "sPub", MemberTraits.FINAL);

    private Vis_() {
    }
}
