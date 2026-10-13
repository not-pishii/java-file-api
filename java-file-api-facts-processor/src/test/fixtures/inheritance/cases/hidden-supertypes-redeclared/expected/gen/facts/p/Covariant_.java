package gen.facts.p;

import gen.facts.p.Covariant_.Canonical;
import gen.facts.p.Covariant_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
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
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Covariant;

/// The full metamodel of [Covariant], which `@Facts` asks for: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// A member [Covariant] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PubApi_].
///
/// `p.Far`, `p.HiddenApi` and `p.Near`, supertypes that are not `public`, have no metamodels: the `public` members inherited from them are facts of this one.
///
/// A generated class extends or implements [Covariant] by the proof its metamodel starts, with the stages of `Covariant_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Covariant.class, fingerprint = "25d06f9eeda24b71c5824902dd63298c716faaa74f4a663876a973e6360a794a", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Covariant_ {
    /// The shape of [Covariant] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Covariant] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Covariant_"), "25d06f9eeda24b71c5824902dd63298c716faaa74f4a663876a973e6360a794a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Covariant"), List.of(), List.of(ClassDesc.of("p.Near"), ClassDesc.of("p.Far"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.HiddenApi"), ClassDesc.of("p.PubApi")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Far"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))), new ParameterizedTypeRef(ClassDesc.of("p.Near"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("api"), Signature.of("beyond"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("near"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("overridden"), Signature.of("pack"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("prot"), Signature.of("pub"), Signature.of("redeclared"), Signature.of("self"), Signature.of("set", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sfar"), Signature.of("snear")), Set.of(Signature.of("Covariant"))), false, new Covariant_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Covariant], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Covariant].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Covariant open-class sealed=no nesting=top
        tparams -
        superclasses p.Near; p.Far; java.lang.Object
        interfaces p.HiddenApi; p.PubApi
        supertypes p.Far<java.lang.String>; p.Near<java.lang.String>
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable java.lang.String item
        member field public static constant java.lang.String CONST = "const"
        member field public static constant java.lang.String FAR = "far"
        member field public static constant java.lang.String HID = "near"
        member field public static mutable int counter
        member method protected overridable prot() -> void throws -
        member method public overridable <^0> pick(^0) -> ^0 throws -
        member method public overridable api() -> java.lang.String throws -
        member method public overridable dflt() -> java.lang.String throws -
        member method public overridable get() -> java.lang.String throws -
        member method public overridable near() -> java.lang.String throws -
        member method public overridable overridden() -> java.lang.String throws -
        member method public overridable pub() -> java.lang.String throws -
        member method public overridable redeclared() -> java.lang.String throws -
        member method public overridable self() -> p.Covariant throws -
        member method public overridable set(java.lang.String) -> void throws -
        member method public static sfar() -> java.lang.String throws -
        member method public static snear() -> java.lang.String throws -
        inherit ctor Covariant() public () throws -
        inherit field public p.Far FAR
        inherit field public p.Far HID
        inherit field public p.Far counter
        inherit field public p.Far item
        inherit field public p.HiddenApi CONST
        inherit field public p.Near HID
        inherit method api() erased () overrides p.HiddenApi by class public concrete p.Covariant () -> java.lang.String throws -
        inherit method beyond() erased () overrides - by interface default p.PubApi () -> java.lang.String throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method dflt() erased () overrides - by interface default p.HiddenApi () -> java.lang.String throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method get() erased () overrides - by class public concrete p.Far () -> java.lang.String throws -
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method near() erased () overrides - by class public concrete p.Near () -> java.lang.String throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method overridden() erased () overrides p.Far by class public concrete p.Near () -> java.lang.String throws -
        inherit method pack() erased () overrides - by class package concrete p.Far () -> void throws -
        inherit method pick(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete p.Far <^0> (^0) -> ^0 throws -
        inherit method prot() erased () overrides - by class protected concrete p.Far () -> void throws -
        inherit method pub() erased () overrides p.PubApi by class public concrete p.Covariant () -> java.lang.String throws -
        inherit method redeclared() erased () overrides - by class public concrete p.Far () -> java.lang.String throws -
        inherit method self() erased () overrides p.Far by class public concrete p.Covariant () -> p.Covariant throws -
        inherit method set(java.lang.String) erased (java.lang.Object) overrides - by class public concrete p.Far (java.lang.String) -> void throws -
        inherit method sfar() erased () overrides - by class public static p.Far () -> java.lang.String throws -
        inherit method snear() erased () overrides - by class public static p.Near () -> java.lang.String throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete api(); beyond(); clone(); dflt(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); near(); notify(); notifyAll(); overridden(); pack(); pick(java.lang.Object); prot(); pub(); redeclared(); self(); set(java.lang.String); toString(); wait(); wait(long); wait(long, int)
        table static sfar(); snear()
        table ctor Covariant()
        """;

        private Canonical() {
        }
    }

    /// The token of [Covariant].
    public static final OpenClassToken<Covariant> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Covariant#CONST], declared in `p.HiddenApi`, which is not `public`.
    public static final StaticFieldRef<String> CONST = UnsafeFacts.constantField(TOKEN, "CONST", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "const");

    /// The fact of [Covariant#FAR], declared in `p.Far`, which is not `public`.
    public static final StaticFieldRef<String> FAR = UnsafeFacts.constantField(TOKEN, "FAR", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "far");

    /// The fact of [Covariant#HID], declared in `p.Near`, which is not `public`.
    public static final StaticFieldRef<String> HID = UnsafeFacts.constantField(TOKEN, "HID", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "near");

    /// The fact of [Covariant#counter], declared in `p.Far`, which is not `public`.
    public static final MutableStaticFieldRef<Int> counter = UnsafeFacts.mutableStaticField(TOKEN, "counter", PrimitiveToken.INT);

    /// The fact of [Covariant#item], declared in `p.Far`, which is not `public`.
    public static final MutableFieldRef<Covariant, String> item = UnsafeFacts.mutableField(TOKEN, "item", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Covariant#Covariant()].
    public static final CtorRef0<Covariant> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Covariant#api()].
    public static final MethodRef0<Covariant, String> api = UnsafeInvocables.method(TOKEN, "api", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#dflt()], declared in `p.HiddenApi`, which is not `public`.
    public static final MethodRef0<Covariant, String> dflt = UnsafeInvocables.method(TOKEN, "dflt", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#get()], declared in `p.Far`, which is not `public`.
    public static final MethodRef0<Covariant, String> get = UnsafeInvocables.method(TOKEN, "get", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#near()], declared in `p.Near`, which is not `public`.
    public static final MethodRef0<Covariant, String> near = UnsafeInvocables.method(TOKEN, "near", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#overridden()], declared in `p.Near`, which is not `public`.
    public static final MethodRef0<Covariant, String> overridden = UnsafeInvocables.method(TOKEN, "overridden", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#prot()], declared in `p.Far`, which is not `public`, which is `protected`: a subclass alone uses it.
    public static final Protected<Covariant, VoidMethodRef0<Covariant>> prot = UnsafeFacts.protected_(TOKEN, UnsafeInvocables.voidMethod(TOKEN, "prot", MemberTraits.OVERRIDABLE.with(Access.PROTECTED)));

    /// The fact of [Covariant#pub()].
    public static final MethodRef0<Covariant, String> pub = UnsafeInvocables.method(TOKEN, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#redeclared()], declared in `p.Far`, which is not `public`.
    public static final MethodRef0<Covariant, String> redeclared = UnsafeInvocables.method(TOKEN, "redeclared", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#self()].
    public static final MethodRef0<Covariant, Covariant> self = UnsafeInvocables.method(TOKEN, "self", TOKEN, MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#set(Object)], declared in `p.Far`, which is not `public`.
    public static final VoidMethodRef1<Covariant, String> set_String = UnsafeInvocables.voidMethod(TOKEN, "set", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#sfar()], declared in `p.Far`, which is not `public`.
    public static final StaticMethodRef0<String> sfar = UnsafeInvocables.staticMethod(TOKEN, "sfar", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Covariant#snear()], declared in `p.Near`, which is not `public`.
    public static final StaticMethodRef0<String> snear = UnsafeInvocables.staticMethod(TOKEN, "snear", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Covariant_() {
    }

    /// The fact of [Covariant#pick(Object)], declared in `p.Far`, which is not `public`, for the type arguments the tokens give.
    ///
    /// @param <R> a type argument of the method
    /// @param r the token of the type argument `R`
    /// @return the fact
    public static <R> MethodRef1<Covariant, R, R> pick_R(RefToken<R> r) {
        return UnsafeInvocables.method(TOKEN, "pick", r, UnsafeFacts.param(r, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(r));
    }

    /// Starts the proof that a class being declared extends [Covariant]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Covariant> extend(RefToken<Self> self) {
        return new Covariant_Heritage().extend(TOKEN, self);
    }
}
