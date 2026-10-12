package gen.facts.p;

import gen.facts.p.Uses_.Canonical;
import gen.facts.p.Uses_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
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
import p.Box;
import p.Pair;
import p.Sorted;
import p.Uses;

/// The full metamodel of [Uses], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Uses] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Uses] by the proof its metamodel starts, with the stages of `Uses_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Uses.class, fingerprint = "dc139498b1d06174c971e1b8c45d3e2545ce2f248a4695d256cc1534177416f1", complete = true, format = 14)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Uses_ {
    /// The shape of [Uses] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Uses] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Uses_"), "dc139498b1d06174c971e1b8c45d3e2545ce2f248a4695d256cc1534177416f1", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Uses"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("any"), Signature.of("arrays"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("lists"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("raw"), Signature.of("strings"), Signature.of("take", Param.fixed(ClassDesc.of("p.Sorted"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Uses"))), false, new Uses_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Uses], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Uses].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Uses open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable java.util.Map<java.lang.String, ? extends java.lang.Number> wild
        member method public overridable any() -> java.util.List<?> throws -
        member method public overridable arrays() -> p.Pair<int[], java.lang.String[]> throws -
        member method public overridable lists() -> java.util.List<? super java.lang.Integer>[] throws -
        member method public overridable raw() -> p.Box throws -
        member method public overridable strings() -> p.Box<java.lang.String> throws -
        member method public overridable take(p.Sorted<java.lang.Integer>) -> void throws -
        inherit ctor Uses() public () throws -
        inherit field public p.Uses wild
        inherit method any() erased () overrides - by class public concrete p.Uses () -> java.util.List<?> throws -
        inherit method arrays() erased () overrides - by class public concrete p.Uses () -> p.Pair<int[], java.lang.String[]> throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method lists() erased () overrides - by class public concrete p.Uses () -> java.util.List<? super java.lang.Integer>[] throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method raw() erased () overrides - by class public concrete p.Uses () -> p.Box throws -
        inherit method strings() erased () overrides - by class public concrete p.Uses () -> p.Box<java.lang.String> throws -
        inherit method take(p.Sorted) erased (p.Sorted) overrides - by class public concrete p.Uses (p.Sorted<java.lang.Integer>) -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete any(); arrays(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); lists(); notify(); notifyAll(); raw(); strings(); take(p.Sorted); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Uses()
        """;

        private Canonical() {
        }
    }

    /// The token of [Uses].
    public static final OpenClassToken<Uses> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Uses#wild].
    public static final MutableFieldRef<Uses, Map<String, ? extends Number>> wild = UnsafeFacts.mutableField(TOKEN, "wild", UnsafeFacts.<Map<String, ? extends Number>>interfaceToken(gen.facts.java.util.Map_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)), TokenArg.extendsBound(UnsafeFacts.<Number>abstractClassToken(gen.facts.java.lang.Number_.Data.SHAPE))));

    /// The fact of [Uses#Uses()].
    public static final CtorRef0<Uses> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Uses#any()].
    public static final MethodRef0<Uses, List<?>> any = UnsafeFacts.method(TOKEN, "any", UnsafeFacts.<List<?>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#arrays()].
    public static final MethodRef0<Uses, Pair<int[], String[]>> arrays = UnsafeFacts.method(TOKEN, "arrays", UnsafeFacts.<Pair<int[], String[]>>finalClassToken(Pair_.Data.SHAPE, TokenArg.exact(PrimitiveToken.INT.array()), TokenArg.exact(ArrayToken.of(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)))), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#lists()].
    public static final MethodRef0<Uses, List<? super Integer>[]> lists = UnsafeFacts.method(TOKEN, "lists", ArrayToken.of(UnsafeFacts.<List<? super Integer>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.superBound(UnsafeFacts.<Integer>finalClassToken(gen.facts.java.lang.Integer_.Data.SHAPE)))), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#raw()].
    public static final MethodRef0<Uses, Box> raw = UnsafeFacts.method(TOKEN, "raw", UnsafeFacts.<Box>openClassToken(Box_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#strings()].
    public static final MethodRef0<Uses, Box<String>> strings = UnsafeFacts.method(TOKEN, "strings", UnsafeFacts.<Box<String>>openClassToken(Box_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#take(Sorted)].
    public static final VoidMethodRef1<Uses, Sorted<Integer>> take_Sorted = UnsafeFacts.voidMethod(TOKEN, "take", UnsafeFacts.<Sorted<Integer>>openClassToken(Sorted_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<Integer>finalClassToken(gen.facts.java.lang.Integer_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    private Uses_() {
    }

    /// Starts the proof that a class being declared extends [Uses]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Uses> extend(RefToken<Self> self) {
        return new Uses_Heritage().extend(TOKEN, self);
    }
}
