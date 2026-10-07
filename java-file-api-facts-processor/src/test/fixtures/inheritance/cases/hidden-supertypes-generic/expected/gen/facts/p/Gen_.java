package gen.facts.p;

import gen.facts.p.Gen_.Canonical;
import gen.facts.p.Gen_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Gen;

/// The full metamodel of [Gen], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Gen] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PubApi_].
///
/// `p.Far`, `p.HiddenApi` and `p.Near`, supertypes that are not `public`, have no metamodels: the `public` members inherited from them are facts of this one.
///
/// These members have no fact:
///
/// - `method self() of p.Far`, which mentions types that are not public: p.Far
///
/// @param <E> a type argument of [Gen]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Gen.class, fingerprint = "729053d2dd9b4f85bd7d2d59f23af62c04eead3f8256e6904e31fa542abe449e", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Gen_<E> {
    /// The shape of [Gen] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Gen] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Gen_"), "729053d2dd9b4f85bd7d2d59f23af62c04eead3f8256e6904e31fa542abe449e", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Gen"), List.of(new TypeParam("E", List.of())), List.of(ClassDesc.of("p.Near"), ClassDesc.of("p.Far"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.HiddenApi"), ClassDesc.of("p.PubApi")), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Far"), List.of(Types.exact(Types.typeVar("E")))), new ParameterizedTypeRef(ClassDesc.of("p.Near"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("api"), Signature.of("beyond"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("near"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("overridden"), Signature.of("pack"), Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("prot"), Signature.of("pub"), Signature.of("redeclared"), Signature.of("self"), Signature.of("set", Param.var(0)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sfar"), Signature.of("snear")), Set.of(Signature.of("Gen"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Gen], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Gen].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Gen final-class sealed=no
        tparams #0
        superclasses p.Near; p.Far; java.lang.Object
        interfaces p.HiddenApi; p.PubApi
        supertypes p.Far<#0>; p.Near<#0>
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable #0 item
        member field public static constant java.lang.String CONST = "const"
        member field public static constant java.lang.String FAR = "far"
        member field public static constant java.lang.String HID = "near"
        member field public static mutable int counter
        member method public final <^0> pick(^0) -> ^0 throws -
        member method public final api() -> java.lang.String throws -
        member method public final dflt() -> java.lang.String throws -
        member method public final get() -> #0 throws -
        member method public final near() -> java.lang.String throws -
        member method public final overridden() -> java.lang.String throws -
        member method public final pub() -> java.lang.String throws -
        member method public final redeclared() -> java.lang.String throws -
        member method public final set(#0) -> void throws -
        member method public static sfar() -> java.lang.String throws -
        member method public static snear() -> java.lang.String throws -
        table abstract -
        table concrete api(); beyond(); clone(); dflt(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); near(); notify(); notifyAll(); overridden(); pack(); pick(java.lang.Object); prot(); pub(); redeclared(); self(); set(#0); toString(); wait(); wait(long); wait(long, int)
        table static sfar(); snear()
        table ctor Gen()
        """;

        private Canonical() {
        }
    }

    /// The token of [Gen] with a wildcard for every type argument.
    public static final FinalClassToken<Gen<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The fact of [Gen#CONST], declared in `p.HiddenApi`, which is not `public`.
    public static final StaticFieldRef<String> CONST = UnsafeFacts.constantField(ANY, "CONST", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "const");

    /// The fact of [Gen#FAR], declared in `p.Far`, which is not `public`.
    public static final StaticFieldRef<String> FAR = UnsafeFacts.constantField(ANY, "FAR", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "far");

    /// The fact of [Gen#HID], declared in `p.Near`, which is not `public`.
    public static final StaticFieldRef<String> HID = UnsafeFacts.constantField(ANY, "HID", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "near");

    /// The fact of [Gen#counter], declared in `p.Far`, which is not `public`.
    public static final MutableStaticFieldRef<Int> counter = UnsafeFacts.mutableStaticField(ANY, "counter", PrimitiveToken.INT);

    /// The fact of [Gen#sfar()], declared in `p.Far`, which is not `public`.
    public static final StaticMethodRef0<String> sfar = UnsafeFacts.staticMethod(ANY, "sfar", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Gen#snear()], declared in `p.Near`, which is not `public`.
    public static final StaticMethodRef0<String> snear = UnsafeFacts.staticMethod(ANY, "snear", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The token of [Gen] with the type arguments of this metamodel.
    public final FinalClassToken<Gen<E>> token;

    /// The fact of [Gen#item], declared in `p.Far`, which is not `public`.
    public final MutableFieldRef<Gen<E>, E> item;

    /// The fact of [Gen#Gen()].
    public final CtorRef0<Gen<E>> new_;

    /// The fact of [Gen#api()].
    public final MethodRef0<Gen<E>, String> api;

    /// The fact of [Gen#dflt()], declared in `p.HiddenApi`, which is not `public`.
    public final MethodRef0<Gen<E>, String> dflt;

    /// The fact of [Gen#get()], declared in `p.Far`, which is not `public`.
    public final MethodRef0<Gen<E>, E> get;

    /// The fact of [Gen#near()], declared in `p.Near`, which is not `public`.
    public final MethodRef0<Gen<E>, String> near;

    /// The fact of [Gen#overridden()], declared in `p.Near`, which is not `public`.
    public final MethodRef0<Gen<E>, String> overridden;

    /// The fact of [Gen#pub()].
    public final MethodRef0<Gen<E>, String> pub;

    /// The fact of [Gen#redeclared()], declared in `p.Far`, which is not `public`.
    public final MethodRef0<Gen<E>, String> redeclared;

    /// The fact of [Gen#set(Object)], declared in `p.Far`, which is not `public`.
    public final VoidMethodRef1<Gen<E>, E> set_E;

    private final RefToken<E> e;

    /// The metamodel of [Gen] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public Gen_(RefToken<E> e) {
        this.e = e;
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(e));
        this.item = UnsafeFacts.mutableField(token, "item", e);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.api = UnsafeFacts.method(token, "api", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.dflt = UnsafeFacts.method(token, "dflt", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.get = UnsafeFacts.method(token, "get", e, MemberTraits.FINAL);
        this.near = UnsafeFacts.method(token, "near", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.overridden = UnsafeFacts.method(token, "overridden", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.pub = UnsafeFacts.method(token, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.redeclared = UnsafeFacts.method(token, "redeclared", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.set_E = UnsafeFacts.voidMethod(token, "set", UnsafeFacts.param(e, Param.var(0)), MemberTraits.FINAL);
    }

    /// The fact of [Gen#pick(Object)], declared in `p.Far`, which is not `public`, for the type arguments the tokens give.
    ///
    /// @param <R> a type argument of the method
    /// @param r the token of the type argument `R`
    /// @return the fact
    public <R> MethodRef1<Gen<E>, R, R> pick_R(RefToken<R> r) {
        return UnsafeFacts.method(token, "pick", r, UnsafeFacts.param(r, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(r));
    }
}
