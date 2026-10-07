package gen.facts.p;

import gen.facts.p.Mix_.Canonical;
import gen.facts.p.Mix_.Data;
import gen.facts.p.Mix_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef1;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
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
import p.Mix;

/// The full metamodel of [Mix], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Mix] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `constructor <T>Mix(T,int)`, which is a generic constructor, whose type arguments a fact cannot give explicitly
/// - `method dollar()`, which mentions p.Dol$lar, which has no metamodel: a class with $ in its simple name is not supported yet
/// - `method marker()`, which mentions p.Marker, which has no metamodel: annotation interface p.Marker is not supported yet
/// - `method markers()`, which mentions p.Marker, which has no metamodel: annotation interface p.Marker is not supported yet
/// - `method <T>marked(T)`, which mentions p.Marker, which has no metamodel: annotation interface p.Marker is not supported yet
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mix.class, fingerprint = "6b415661b0bea738a1614fc32936a4c173cae87474e7e75050d30fce52aecada", complete = true, format = 9)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Mix_ {
    /// The shape of [Mix] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Mix] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mix_"), "6b415661b0bea738a1614fc32936a4c173cae87474e7e75050d30fce52aecada", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mix"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("array"), Signature.of("clone"), Signature.of("dollar"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("marked", Param.fixed(ClassDesc.of("p.Marker"))), Signature.of("marker"), Signature.of("markers"), Signature.of("names"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("raw"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("wild", Param.fixed(ClassDesc.of("java.util.Map")))), Set.of(), Set.of(Signature.of("Mix", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ConstantDescs.CD_int)), Signature.of("Mix", Param.fixed(ClassDesc.of("java.util.Set"))))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Mix] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Mix] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18(), m19(), m20()), List.of(c0(), c1()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("array"), List.of(), List.of(), Arity.FIXED, new Of(Types.array(new ParameterizedTypeRef(ClassDesc.of("java.util.List"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("dollar"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Dol$lar"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("marked", Param.fixed(ClassDesc.of("p.Marker"))), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Marker"))))), List.of(Types.typeVar("T")), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("p.Marker"))), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("marker"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Marker"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("markers"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.List"), List.of(Types.exact(Types.of(ClassDesc.of("p.Marker")))))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("names"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.List"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String")))))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("plain"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("raw"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.util.List"))), List.of(), Set.of(List.of()), List.of());
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

            private static Method m20() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Mix"), Signature.of("wild", Param.fixed(ClassDesc.of("java.util.Map"))), List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.Map"), List.of(Types.unbounded(), Types.extendsBound(Types.of(ClassDesc.of("java.lang.Number")))))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.util.Map"))), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Mix", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ConstantDescs.CD_int)), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T"), PrimitiveTypeRef.INT), Arity.FIXED, List.of());
            }

            private static Constructor c1() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Mix", Param.fixed(ClassDesc.of("java.util.Set"))), List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.Set"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String")))))), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Mix], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Mix].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Mix open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (java.util.Set<java.lang.String>) throws -
        member ctor public <^0>(^0, int) throws -
        member field public instance mutable java.util.function.Function<java.lang.String, java.lang.String> field
        member method public overridable <^0 extends p.Marker> marked(^0) -> void throws -
        member method public overridable <^0> id(^0) -> ^0 throws -
        member method public overridable array() -> java.util.List<java.lang.String>[] throws -
        member method public overridable dollar() -> p.Dol$lar throws -
        member method public overridable marker() -> p.Marker throws -
        member method public overridable markers() -> java.util.List<p.Marker> throws -
        member method public overridable names() -> java.util.List<java.lang.String> throws -
        member method public overridable plain() -> java.lang.String throws -
        member method public overridable raw() -> java.util.List throws -
        member method public overridable wild(java.util.Map<?, ? extends java.lang.Number>) -> void throws -
        inherit ctor public (java.util.Set<java.lang.String>) throws -
        inherit ctor public <^0>(^0, int) throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Mix <^0 extends p.Marker> marked(^0) -> void throws - erased (p.Marker) overrides -
        inherit method public concrete p.Mix <^0> id(^0) -> ^0 throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Mix array() -> java.util.List<java.lang.String>[] throws - erased () overrides -
        inherit method public concrete p.Mix dollar() -> p.Dol$lar throws - erased () overrides -
        inherit method public concrete p.Mix marker() -> p.Marker throws - erased () overrides -
        inherit method public concrete p.Mix markers() -> java.util.List<p.Marker> throws - erased () overrides -
        inherit method public concrete p.Mix names() -> java.util.List<java.lang.String> throws - erased () overrides -
        inherit method public concrete p.Mix plain() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Mix raw() -> java.util.List throws - erased () overrides -
        inherit method public concrete p.Mix wild(java.util.Map<?, ? extends java.lang.Number>) -> void throws - erased (java.util.Map) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete array(); clone(); dollar(); equals(java.lang.Object); finalize(); getClass(); hashCode(); id(java.lang.Object); marked(p.Marker); marker(); markers(); names(); notify(); notifyAll(); plain(); raw(); toString(); wait(); wait(long); wait(long, int); wild(java.util.Map)
        table static -
        table ctor Mix(java.lang.Object, int); Mix(java.util.Set)
        """;

        private Canonical() {
        }
    }

    /// The token of [Mix].
    public static final OpenClassToken<Mix> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Mix#field].
    public static final MutableFieldRef<Mix, Function<String, String>> field = UnsafeFacts.mutableField(TOKEN, "field", UnsafeFacts.<Function<String, String>>interfaceToken(gen.facts.java.util.function.Function_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)), TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))));

    /// The fact of [Mix#Mix(Set)].
    public static final CtorRef1<Mix, Set<String>> new_Set = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<Set<String>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.FINAL);

    /// The fact of [Mix#array()].
    public static final MethodRef0<Mix, List<String>[]> array = UnsafeFacts.method(TOKEN, "array", ArrayToken.of(UnsafeFacts.<List<String>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)))), MemberTraits.OVERRIDABLE);

    /// The fact of [Mix#names()].
    public static final MethodRef0<Mix, List<String>> names = UnsafeFacts.method(TOKEN, "names", UnsafeFacts.<List<String>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    /// The fact of [Mix#plain()].
    public static final MethodRef0<Mix, String> plain = UnsafeFacts.method(TOKEN, "plain", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Mix#raw()].
    public static final MethodRef0<Mix, List> raw = UnsafeFacts.method(TOKEN, "raw", UnsafeFacts.<List>interfaceToken(gen.facts.java.util.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Mix#wild(Map)].
    public static final VoidMethodRef1<Mix, Map<?, ? extends Number>> wild_Map = UnsafeFacts.voidMethod(TOKEN, "wild", UnsafeFacts.<Map<?, ? extends Number>>interfaceToken(gen.facts.java.util.Map_.Data.SHAPE, TokenArg.unbounded(), TokenArg.extendsBound(UnsafeFacts.<Number>abstractClassToken(gen.facts.java.lang.Number_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    private Mix_() {
    }

    /// The fact of [Mix#id(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef1<Mix, T, T> id_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "id", t, UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }
}
