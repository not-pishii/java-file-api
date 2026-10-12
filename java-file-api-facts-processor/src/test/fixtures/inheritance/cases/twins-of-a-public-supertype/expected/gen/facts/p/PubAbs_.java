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
@GeneratedMetamodel(of = PubAbs.class, fingerprint = "bd48f148314a5fa4c8fe98f1199297e129df9879acf6e5a6af3e2d9e9df5927d", complete = true, format = 14)
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
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubAbs_"), "bd48f148314a5fa4c8fe98f1199297e129df9879acf6e5a6af3e2d9e9df5927d", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.PubAbs"), List.of(), List.of(ClassDesc.of("p.HAbs"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.PStr")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubAbs"))), false, new PubAbs_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubAbs], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubAbs].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.PubAbs abstract-class sealed=no nesting=top
        tparams -
        superclasses p.HAbs; java.lang.Object
        interfaces p.PStr
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor PubAbs() public () throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method get() erased () overrides - by interface abstract p.PStr () -> java.lang.String throws - | class public abstract p.HAbs () -> java.lang.Object throws -
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
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
