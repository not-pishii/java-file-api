package gen.facts.p;

import gen.facts.p.Kept_.Canonical;
import gen.facts.p.Kept_.Data;
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
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Kept;

/// The full metamodel of [Kept], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Kept] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// The members inherited from `p.BadMid`, which has no full metamodel, have no facts: the bounds of the type parameters of p.BadMid mention types that are not public: p.Secret.
///
/// `p.HiddenI`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Kept.class, fingerprint = "11ce6538ba14219b78b7a5a405809a6717327f8f6e069ebf54c449e8e9b01cdc", complete = true, format = 10)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Kept_ {
    /// The shape of [Kept] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Kept] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Kept_"), "11ce6538ba14219b78b7a5a405809a6717327f8f6e069ebf54c449e8e9b01cdc", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Kept"), List.of(), List.of(ClassDesc.of("p.BadMid"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.HiddenI")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.BadMid"), List.of(Types.exact(Types.of(ClassDesc.of("p.Secret"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("bad"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("more"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Kept"))), false, new Kept_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Kept], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Kept].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Kept open-class sealed=no
        tparams -
        superclasses p.BadMid; java.lang.Object
        interfaces p.HiddenI
        supertypes p.BadMid<p.Secret>
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant java.lang.String K = "k"
        member method public overridable more() -> java.lang.String throws -
        member method public overridable run() -> java.lang.String throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.BadMid bad() -> void throws - erased () overrides -
        inherit method public default p.HiddenI more() -> java.lang.String throws - erased () overrides -
        inherit method public default p.HiddenI run() -> java.lang.String throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete bad(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); more(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Kept()
        """;

        private Canonical() {
        }
    }

    /// The token of [Kept].
    public static final OpenClassToken<Kept> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Kept#K], declared in `p.HiddenI`, which is not `public`.
    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "k");

    /// The fact of [Kept#Kept()].
    public static final CtorRef0<Kept> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Kept#more()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Kept, String> more = UnsafeFacts.method(TOKEN, "more", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Kept#run()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Kept, String> run = UnsafeFacts.method(TOKEN, "run", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Kept_() {
    }
}
