package gen.facts.p;

import gen.facts.p.Mix_.Canonical;
import gen.facts.p.Mix_.Data;
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
@GeneratedMetamodel(of = Mix.class, fingerprint = "916e68c194d831fac8cc43ee2934d31aa5a59ebba33e7f0e3aa795be6fede327", complete = true, format = 8)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mix_"), "916e68c194d831fac8cc43ee2934d31aa5a59ebba33e7f0e3aa795be6fede327", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mix"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("array"), Signature.of("clone"), Signature.of("dollar"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("marked", Param.fixed(ClassDesc.of("p.Marker"))), Signature.of("marker"), Signature.of("markers"), Signature.of("names"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("raw"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("wild", Param.fixed(ClassDesc.of("java.util.Map")))), Set.of(), Set.of(Signature.of("Mix", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ConstantDescs.CD_int)), Signature.of("Mix", Param.fixed(ClassDesc.of("java.util.Set"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Mix], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Mix].
        static final String TEXT = """
        javafile-facts-canonical 6
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
