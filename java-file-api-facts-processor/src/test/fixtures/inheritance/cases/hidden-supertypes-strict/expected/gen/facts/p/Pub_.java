package gen.facts.p;

import gen.facts.p.Pub_.Canonical;
import gen.facts.p.Pub_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Pub;

/// The full metamodel of [Pub], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Pub] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PubApi_].
///
/// `p.Far`, `p.HiddenApi` and `p.Near`, supertypes that are not `public`, have no metamodels: the `public` members inherited from them are facts of this one.
///
/// These members have no fact:
///
/// - `method self() of p.Far`, which mentions types that are not public: p.Far
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Pub.class, fingerprint = "f312c4364705582e9d7e651ae5ff371e877291e804308ce3241e5c194affe1d4", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Pub_ {
    /// The shape of [Pub] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Pub] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Pub_"), "f312c4364705582e9d7e651ae5ff371e877291e804308ce3241e5c194affe1d4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Pub"), List.of(), List.of(ClassDesc.of("p.Near"), ClassDesc.of("p.Far"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Far"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))), new ParameterizedTypeRef(ClassDesc.of("p.Near"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("api"), Signature.of("beyond"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("near"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("overridden"), Signature.of("pack"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("prot"), Signature.of("pub"), Signature.of("redeclared"), Signature.of("self"), Signature.of("set", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sfar"), Signature.of("snear")), Set.of(Signature.of("Pub"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Pub], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Pub].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Pub open-class sealed=no\ntparams -\nsuperclasses p.Near; p.Far; java.lang.Object\ninterfaces p.HiddenApi; p.PubApi\nsupertypes p.Far<java.lang.String>; p.Near<java.lang.String>\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable java.lang.String item\nmember field static constant java.lang.String CONST = \"const\"\nmember field static constant java.lang.String FAR = \"far\"\nmember field static constant java.lang.String HID = \"near\"\nmember field static mutable int counter\nmember method overridable <^0> pick(^0) -> ^0 throws -\nmember method overridable api() -> java.lang.String throws -\nmember method overridable dflt() -> java.lang.String throws -\nmember method overridable get() -> java.lang.String throws -\nmember method overridable near() -> java.lang.String throws -\nmember method overridable overridden() -> java.lang.String throws -\nmember method overridable pub() -> java.lang.String throws -\nmember method overridable redeclared() -> java.lang.String throws -\nmember method overridable set(java.lang.String) -> void throws -\nmember method static sfar() -> java.lang.String throws -\nmember method static snear() -> java.lang.String throws -\ntable abstract -\ntable concrete api(); beyond(); clone(); dflt(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); near(); notify(); notifyAll(); overridden(); pack(); pick(java.lang.Object); prot(); pub(); redeclared(); self(); set(java.lang.String); toString(); wait(); wait(long); wait(long, int)\ntable static sfar(); snear()\ntable ctor Pub()\n";

        private Canonical() {
        }
    }

    /// The token of [Pub].
    public static final OpenClassToken<Pub> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Pub#CONST], declared in `p.HiddenApi`, which is not `public`.
    public static final StaticFieldRef<String> CONST = UnsafeFacts.constantField(TOKEN, "CONST", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "const");

    /// The fact of [Pub#FAR], declared in `p.Far`, which is not `public`.
    public static final StaticFieldRef<String> FAR = UnsafeFacts.constantField(TOKEN, "FAR", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "far");

    /// The fact of [Pub#HID], declared in `p.Near`, which is not `public`.
    public static final StaticFieldRef<String> HID = UnsafeFacts.constantField(TOKEN, "HID", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "near");

    /// The fact of [Pub#counter], declared in `p.Far`, which is not `public`.
    public static final MutableStaticFieldRef<Int> counter = UnsafeFacts.mutableStaticField(TOKEN, "counter", PrimitiveToken.INT);

    /// The fact of [Pub#item], declared in `p.Far`, which is not `public`.
    public static final MutableFieldRef<Pub, String> item = UnsafeFacts.mutableField(TOKEN, "item", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Pub#Pub()].
    public static final CtorRef0<Pub> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Pub#api()].
    public static final MethodRef0<Pub, String> api = UnsafeFacts.method(TOKEN, "api", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#dflt()], declared in `p.HiddenApi`, which is not `public`.
    public static final MethodRef0<Pub, String> dflt = UnsafeFacts.method(TOKEN, "dflt", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#get()], declared in `p.Far`, which is not `public`.
    public static final MethodRef0<Pub, String> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#near()], declared in `p.Near`, which is not `public`.
    public static final MethodRef0<Pub, String> near = UnsafeFacts.method(TOKEN, "near", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#overridden()], declared in `p.Near`, which is not `public`.
    public static final MethodRef0<Pub, String> overridden = UnsafeFacts.method(TOKEN, "overridden", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#pub()].
    public static final MethodRef0<Pub, String> pub = UnsafeFacts.method(TOKEN, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#redeclared()].
    public static final MethodRef0<Pub, String> redeclared = UnsafeFacts.method(TOKEN, "redeclared", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#set(Object)], declared in `p.Far`, which is not `public`.
    public static final VoidMethodRef1<Pub, String> set_String = UnsafeFacts.voidMethod(TOKEN, "set", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub#sfar()], declared in `p.Far`, which is not `public`.
    public static final StaticMethodRef0<String> sfar = UnsafeFacts.staticMethod(TOKEN, "sfar", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Pub#snear()], declared in `p.Near`, which is not `public`.
    public static final StaticMethodRef0<String> snear = UnsafeFacts.staticMethod(TOKEN, "snear", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Pub_() {
    }

    /// The fact of [Pub#pick(Object)], declared in `p.Far`, which is not `public`, for the type arguments the tokens give.
    ///
    /// @param <R> a type argument of the method
    /// @param r the token of the type argument `R`
    /// @return the fact
    public static <R> MethodRef1<Pub, R, R> pick_R(RefToken<R> r) {
        return UnsafeFacts.method(TOKEN, "pick", r, UnsafeFacts.param(r, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(r));
    }
}
