package gen.facts.p;

import gen.facts.p.PubRaw_.Canonical;
import gen.facts.p.PubRaw_.Data;
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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubRaw;

/// The full metamodel of [PubRaw], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubRaw] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HG`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [PubRaw] by the proof its metamodel starts, with the stages of `PubRaw_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubRaw.class, fingerprint = "1fa6a4ed1c609408552b2040d646d426d7d15b7a5f9065e590374ee79fc00c9a", complete = true, format = 12)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class PubRaw_ {
    /// The shape of [PubRaw] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubRaw] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubRaw_"), "1fa6a4ed1c609408552b2040d646d426d7d15b7a5f9065e590374ee79fc00c9a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubRaw"), List.of(), List.of(ClassDesc.of("p.HG"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("all"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubRaw"))), false, new PubRaw_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubRaw], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubRaw].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.PubRaw open-class sealed=no
        tparams -
        superclasses p.HG; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable java.lang.Object value
        member method public overridable all() -> java.util.List throws -
        member method public overridable get() -> java.lang.Object throws -
        member method public overridable put(java.lang.Object) -> void throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.HG all() -> java.util.List throws - erased () overrides -
        inherit method public concrete p.HG get() -> java.lang.Object throws - erased () overrides -
        inherit method public concrete p.HG put(java.lang.Object) -> void throws - erased (java.lang.Object) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete all(); clone(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); notify(); notifyAll(); put(java.lang.Object); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor PubRaw()
        """;

        private Canonical() {
        }
    }

    /// The token of [PubRaw].
    public static final OpenClassToken<PubRaw> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubRaw#value], declared in `p.HG`, which is not `public`.
    public static final MutableFieldRef<PubRaw, Object> value = UnsafeFacts.mutableField(TOKEN, "value", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE));

    /// The fact of [PubRaw#PubRaw()].
    public static final CtorRef0<PubRaw> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [PubRaw#all()], declared in `p.HG`, which is not `public`.
    public static final MethodRef0<PubRaw, List> all = UnsafeFacts.method(TOKEN, "all", UnsafeFacts.<List>interfaceToken(gen.facts.java.util.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubRaw#get()], declared in `p.HG`, which is not `public`.
    public static final MethodRef0<PubRaw, Object> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubRaw#put(Object)], declared in `p.HG`, which is not `public`.
    public static final VoidMethodRef1<PubRaw, Object> put_Object = UnsafeFacts.voidMethod(TOKEN, "put", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private PubRaw_() {
    }

    /// Starts the proof that a class being declared extends [PubRaw]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, PubRaw> extend(RefToken<Self> self) {
        return new PubRaw_Heritage().extend(TOKEN, self);
    }
}
