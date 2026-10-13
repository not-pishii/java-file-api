package gen.facts.p;

import gen.facts.p.HeirCf_.Canonical;
import gen.facts.p.HeirCf_.Data;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.HeirCf;

/// The full metamodel of [HeirCf], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [HeirCf] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HiddenCf`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// These members have no fact:
///
/// - `method x() of p.HiddenCf, method x_() of p.HiddenCf`, which would all be named x_
///
/// A generated class extends or implements [HeirCf] by the proof its metamodel starts, with the stages of `HeirCf_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = HeirCf.class, fingerprint = "09b9b1927085212181dfa746aa3c114ed6369e82d6bfdfd03010d524fd5e42f9", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class HeirCf_ {
    /// The shape of [HeirCf] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [HeirCf] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.HeirCf_"), "09b9b1927085212181dfa746aa3c114ed6369e82d6bfdfd03010d524fd5e42f9", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.HeirCf"), List.of(), List.of(ClassDesc.of("p.HiddenCf"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x"), Signature.of("x_")), Set.of(), Set.of(Signature.of("HeirCf"))), false, new HeirCf_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [HeirCf], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [HeirCf].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.HeirCf open-class sealed=no nesting=top
        tparams -
        superclasses p.HiddenCf; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable int x
        member method public overridable x() -> void throws -
        member method public overridable x_() -> void throws -
        inherit ctor HeirCf() public () throws -
        inherit field public p.HiddenCf x
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
        inherit method x() erased () overrides - by class public concrete p.HiddenCf () -> void throws -
        inherit method x_() erased () overrides - by class public concrete p.HiddenCf () -> void throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x(); x_()
        table static -
        table ctor HeirCf()
        """;

        private Canonical() {
        }
    }

    /// The token of [HeirCf].
    public static final OpenClassToken<HeirCf> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [HeirCf#x], declared in `p.HiddenCf`, which is not `public`.
    public static final MutableFieldRef<HeirCf, Int> x = UnsafeFacts.mutableField(TOKEN, "x", PrimitiveToken.INT);

    /// The fact of [HeirCf#HeirCf()].
    public static final CtorRef0<HeirCf> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    private HeirCf_() {
    }

    /// Starts the proof that a class being declared extends [HeirCf]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, HeirCf> extend(RefToken<Self> self) {
        return new HeirCf_Heritage().extend(TOKEN, self);
    }
}
