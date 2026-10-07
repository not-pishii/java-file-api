package gen.facts.p;

import gen.facts.p.PubAbs_.Canonical;
import gen.facts.p.PubAbs_.Data;
import gen.facts.p.PubAbs_Heritage.Extend;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubAbs;

/// The full metamodel of [PubAbs], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubAbs] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PStr_].
///
/// A generated class extends or implements [PubAbs] by the proof its metamodel starts, with the stages of `PubAbs_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubAbs.class, fingerprint = "13661a9f1af59d9810365b673cd28376044de2f6c71f2b2d5d6a5b0312735d2f", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubAbs_ {
    /// The shape of [PubAbs] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubAbs] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubAbs_"), "13661a9f1af59d9810365b673cd28376044de2f6c71f2b2d5d6a5b0312735d2f", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.PubAbs"), List.of(), List.of(ClassDesc.of("p.HAbs"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.PStr")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubAbs"))), false, new PubAbs_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubAbs], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubAbs].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.PubAbs abstract-class sealed=no
        tparams -
        superclasses p.HAbs; java.lang.Object
        interfaces p.PStr
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public abstract p.PStr get() -> java.lang.String throws - erased () overrides p.HAbs
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract get()
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor PubAbs()
        """;

        private Canonical() {
        }
    }

    /// The token of [PubAbs].
    public static final AbstractClassToken<PubAbs> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [PubAbs#PubAbs()].
    public static final SuperCtorRef0<PubAbs> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL);

    private PubAbs_() {
    }

    /// Starts the proof that a class being declared extends [PubAbs]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Extend<Self> extend(RefToken<Self> self) {
        return new PubAbs_Heritage().extend(TOKEN, self);
    }
}
