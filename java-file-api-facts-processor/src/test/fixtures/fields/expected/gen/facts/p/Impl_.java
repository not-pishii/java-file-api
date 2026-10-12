package gen.facts.p;

import gen.facts.p.Impl_.Canonical;
import gen.facts.p.Impl_.Data;
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
import p.Impl;

/// The full metamodel of [Impl], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Impl] inherits has its fact in the metamodel of the supertype that declares it: [PubA_] and [PubB_].
///
/// A generated class extends or implements [Impl] by the proof its metamodel starts, with the stages of `Impl_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Impl.class, fingerprint = "425693add63e4830fc32534ed363d0e8e6796e117e44e398904f1fc87477d11c", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Impl_ {
    /// The shape of [Impl] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Impl] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Impl_"), "425693add63e4830fc32534ed363d0e8e6796e117e44e398904f1fc87477d11c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Impl"), List.of(), List.of(ClassDesc.of("p.PubA"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.PubB")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Impl"))), false, new Impl_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Impl], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Impl].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Impl open-class sealed=no nesting=top
        tparams -
        superclasses p.PubA; java.lang.Object
        interfaces p.PubB
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor Impl() public () throws -
        inherit field public p.PubA k
        inherit field public p.PubA s
        inherit field public p.PubB k
        inherit field public p.PubB s
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
        table ctor Impl()
        """;

        private Canonical() {
        }
    }

    /// The token of [Impl].
    public static final OpenClassToken<Impl> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Impl#Impl()].
    public static final CtorRef0<Impl> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Impl_() {
    }

    /// Starts the proof that a class being declared extends [Impl]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Impl> extend(RefToken<Self> self) {
        return new Impl_Heritage().extend(TOKEN, self);
    }
}
