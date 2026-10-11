package gen.facts.p;

import gen.facts.p.Both_.Canonical;
import gen.facts.p.Both_.Data;
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
import p.Both;

/// The full metamodel of [Both], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Both] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.One` and `p.Other`, supertypes that are not `public`, have no metamodels: the `public` members inherited from them are facts of this one.
///
/// These members have no fact:
///
/// - `field K of p.One`, which is ambiguous in p.Both with field K of p.Other
/// - `field K of p.Other`, which is ambiguous in p.Both with field K of p.One
///
/// A generated class extends or implements [Both] by the proof its metamodel starts, with the stages of `Both_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Both.class, fingerprint = "a3eeda83c9ccbbd945ffc15c4c38a71d57b03537e229dc83fbfb6183ebde5b9f", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Both_ {
    /// The shape of [Both] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Both] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Both_"), "a3eeda83c9ccbbd945ffc15c4c38a71d57b03537e229dc83fbfb6183ebde5b9f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Both"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.One"), ClassDesc.of("p.Other")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Both"))), false, new Both_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Both], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Both].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Both open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces p.One; p.Other
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Both()
        """;

        private Canonical() {
        }
    }

    /// The token of [Both].
    public static final OpenClassToken<Both> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Both#Both()].
    public static final CtorRef0<Both> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Both_() {
    }

    /// Starts the proof that a class being declared extends [Both]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Both> extend(RefToken<Self> self) {
        return new Both_Heritage().extend(TOKEN, self);
    }
}
