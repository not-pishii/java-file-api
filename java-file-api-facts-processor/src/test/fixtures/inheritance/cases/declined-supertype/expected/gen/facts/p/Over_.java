package gen.facts.p;

import gen.facts.p.Over_.Canonical;
import gen.facts.p.Over_.Data;
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
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Bounded;
import p.Over;

/// The full metamodel of [Over], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// The members inherited from `p.Bounded`, which has no full metamodel, have no facts: the bounds of the type parameters of p.Bounded mention types that are not public: p.Secret.
///
/// A generated class extends or implements [Over] by the proof its metamodel starts, with the stages of `Over_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Over.class, fingerprint = "6e19fdd0ef607660326a7e1f06b454e49961f43663f5c50cb23b302e761f36c1", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Over_ {
    /// The shape of [Over] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Over] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Over_"), "6e19fdd0ef607660326a7e1f06b454e49961f43663f5c50cb23b302e761f36c1", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Over"), List.of(), List.of(ClassDesc.of("p.Bounded"), ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Bounded"), List.of(Types.exact(Types.of(ClassDesc.of("p.Secret"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("lost"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("same"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Over"))), false, new Over_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Over], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Over].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Over open-class sealed=no nesting=top
        tparams -
        superclasses p.Bounded; java.lang.Object
        interfaces -
        supertypes p.Bounded<p.Secret>
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable same() -> p.Bounded<?> throws -
        inherit ctor Over() public () throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method lost() erased () overrides - by class public concrete p.Bounded () -> void throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method same() erased () overrides - by class public concrete p.Over () -> p.Bounded<?> throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); lost(); notify(); notifyAll(); same(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Over()
        """;

        private Canonical() {
        }
    }

    /// The token of [Over].
    public static final OpenClassToken<Over> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Over#Over()].
    public static final CtorRef0<Over> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Over#same()].
    public static final MethodRef0<Over, Bounded<?>> same = UnsafeInvocables.method(TOKEN, "same", UnsafeFacts.<Bounded<?>>openClassToken(Bounded_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    private Over_() {
    }

    /// Starts the proof that a class being declared extends [Over]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Over> extend(RefToken<Self> self) {
        return new Over_Heritage().extend(TOKEN, self);
    }
}
