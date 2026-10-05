package gen.facts.p;

import gen.facts.p.Covariant_.Canonical;
import gen.facts.p.Covariant_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Covariant;

/// The full metamodel of [Covariant], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Covariant] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PubApi_].
///
/// `p.Far`, `p.HiddenApi` and `p.Near`, supertypes that are not `public`, have no metamodels: the `public` members inherited from them are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Covariant.class, fingerprint = "2bf8748a70a81e6f4651a59a633d90c5f8267633ad6b527f55f83dda018a9de9", complete = true, format = 7)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Covariant_"), "2bf8748a70a81e6f4651a59a633d90c5f8267633ad6b527f55f83dda018a9de9", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Covariant"), List.of(), List.of(ClassDesc.of("p.Near"), ClassDesc.of("p.Far"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Far"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))), new ParameterizedTypeRef(ClassDesc.of("p.Near"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("api"), Signature.of("beyond"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("near"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("overridden"), Signature.of("pack"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("prot"), Signature.of("pub"), Signature.of("redeclared"), Signature.of("self"), Signature.of("set", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sfar"), Signature.of("snear")), Set.of(Signature.of("Covariant"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Covariant], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Covariant].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Covariant open-class sealed=no
        tparams -
        superclasses p.Near; p.Far; java.lang.Object
        interfaces p.HiddenApi; p.PubApi
        supertypes p.Far<java.lang.String>; p.Near<java.lang.String>
        enum -
        members declared-public
        member ctor() throws -
        member field instance mutable java.lang.String item
        member field static constant java.lang.String CONST = "const"
        member field static constant java.lang.String FAR = "far"
        member field static constant java.lang.String HID = "near"
        member field static mutable int counter
        member method overridable <^0> pick(^0) -> ^0 throws -
        member method overridable api() -> java.lang.String throws -
        member method overridable dflt() -> java.lang.String throws -
        member method overridable get() -> java.lang.String throws -
        member method overridable near() -> java.lang.String throws -
        member method overridable overridden() -> java.lang.String throws -
        member method overridable pub() -> java.lang.String throws -
        member method overridable redeclared() -> java.lang.String throws -
        member method overridable self() -> p.Covariant throws -
        member method overridable set(java.lang.String) -> void throws -
        member method static sfar() -> java.lang.String throws -
        member method static snear() -> java.lang.String throws -
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
    public static final CtorRef0<Covariant> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Covariant#api()].
    public static final MethodRef0<Covariant, String> api = UnsafeFacts.method(TOKEN, "api", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#dflt()], declared in `p.HiddenApi`, which is not `public`.
    public static final MethodRef0<Covariant, String> dflt = UnsafeFacts.method(TOKEN, "dflt", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#get()], declared in `p.Far`, which is not `public`.
    public static final MethodRef0<Covariant, String> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#near()], declared in `p.Near`, which is not `public`.
    public static final MethodRef0<Covariant, String> near = UnsafeFacts.method(TOKEN, "near", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#overridden()], declared in `p.Near`, which is not `public`.
    public static final MethodRef0<Covariant, String> overridden = UnsafeFacts.method(TOKEN, "overridden", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#pub()].
    public static final MethodRef0<Covariant, String> pub = UnsafeFacts.method(TOKEN, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#redeclared()], declared in `p.Far`, which is not `public`.
    public static final MethodRef0<Covariant, String> redeclared = UnsafeFacts.method(TOKEN, "redeclared", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#self()].
    public static final MethodRef0<Covariant, Covariant> self = UnsafeFacts.method(TOKEN, "self", TOKEN, MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#set(Object)], declared in `p.Far`, which is not `public`.
    public static final VoidMethodRef1<Covariant, String> set_String = UnsafeFacts.voidMethod(TOKEN, "set", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Covariant#sfar()], declared in `p.Far`, which is not `public`.
    public static final StaticMethodRef0<String> sfar = UnsafeFacts.staticMethod(TOKEN, "sfar", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Covariant#snear()], declared in `p.Near`, which is not `public`.
    public static final StaticMethodRef0<String> snear = UnsafeFacts.staticMethod(TOKEN, "snear", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Covariant_() {
    }

    /// The fact of [Covariant#pick(Object)], declared in `p.Far`, which is not `public`, for the type arguments the tokens give.
    ///
    /// @param <R> a type argument of the method
    /// @param r the token of the type argument `R`
    /// @return the fact
    public static <R> MethodRef1<Covariant, R, R> pick_R(RefToken<R> r) {
        return UnsafeFacts.method(TOKEN, "pick", r, UnsafeFacts.param(r, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(r));
    }
}
