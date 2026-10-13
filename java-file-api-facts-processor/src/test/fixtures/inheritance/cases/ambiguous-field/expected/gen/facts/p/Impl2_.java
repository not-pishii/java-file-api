package gen.facts.p;

import gen.facts.p.Impl2_.Canonical;
import gen.facts.p.Impl2_.Data;
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
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Impl2;

/// The full metamodel of [Impl2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Impl2] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PubBase_].
///
/// `p.HIface`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// These members have no fact:
///
/// - `field K of p.HIface`, which is ambiguous in p.Impl2 with field K of p.PubBase
///
/// A generated class extends or implements [Impl2] by the proof its metamodel starts, with the stages of `Impl2_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Impl2.class, fingerprint = "5e496e15cff7e5aff8467f373b463664a24535b50034f95a23570615b7f36581", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Impl2_ {
    /// The shape of [Impl2] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Impl2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Impl2_"), "5e496e15cff7e5aff8467f373b463664a24535b50034f95a23570615b7f36581", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Impl2"), List.of(), List.of(ClassDesc.of("p.PubBase"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.HIface")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Impl2"))), false, new Impl2_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Impl2], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Impl2].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Impl2 open-class sealed=no nesting=top
        tparams -
        superclasses p.PubBase; java.lang.Object
        interfaces p.HIface
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor Impl2() public () throws -
        inherit field public p.HIface K
        inherit field public p.PubBase K
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Impl2()
        """;

        private Canonical() {
        }
    }

    /// The token of [Impl2].
    public static final OpenClassToken<Impl2> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Impl2#Impl2()].
    public static final CtorRef0<Impl2> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    private Impl2_() {
    }

    /// Starts the proof that a class being declared extends [Impl2]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Impl2> extend(RefToken<Self> self) {
        return new Impl2_Heritage().extend(TOKEN, self);
    }
}
