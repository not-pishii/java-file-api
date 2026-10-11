package gen.facts.p;

import gen.facts.p.PubOver_.Canonical;
import gen.facts.p.PubOver_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubOver;

/// The full metamodel of [PubOver], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubOver] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HOver`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [PubOver] by the proof its metamodel starts, with the stages of `PubOver_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubOver.class, fingerprint = "c63f2ee360f99f2777e173f41b20111f7ee95636aac92b2d35a2b8de18656bc2", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubOver_ {
    /// The shape of [PubOver] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubOver] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubOver_"), "c63f2ee360f99f2777e173f41b20111f7ee95636aac92b2d35a2b8de18656bc2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubOver"), List.of(), List.of(ClassDesc.of("p.HOver"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("take", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("take", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubOver"))), false, new PubOver_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubOver], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubOver].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.PubOver open-class sealed=no
        tparams -
        superclasses p.HOver; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable take(java.lang.Object) -> java.lang.String throws -
        member method public overridable take(java.lang.String) -> java.lang.String throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.HOver take(java.lang.Object) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.PubOver take(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); take(java.lang.Object); take(java.lang.String); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor PubOver()
        """;

        private Canonical() {
        }
    }

    /// The token of [PubOver].
    public static final OpenClassToken<PubOver> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubOver#PubOver()].
    public static final CtorRef0<PubOver> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [PubOver#take(Object)], declared in `p.HOver`, which is not `public`.
    public static final MethodRef1<PubOver, String, Object> take_Object = UnsafeFacts.method(TOKEN, "take", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubOver#take(String)].
    public static final MethodRef1<PubOver, String, String> take_String = UnsafeFacts.method(TOKEN, "take", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private PubOver_() {
    }

    /// Starts the proof that a class being declared extends [PubOver]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, PubOver> extend(RefToken<Self> self) {
        return new PubOver_Heritage().extend(TOKEN, self);
    }
}
