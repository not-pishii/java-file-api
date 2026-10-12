package gen.facts.p;

import gen.facts.p.Derived_.Canonical;
import gen.facts.p.Derived_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Derived;

/// The full metamodel of [Derived], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Derived] inherits has its fact in the metamodel of the supertype that declares it: [Api_] and [Base_].
///
/// A generated class extends or implements [Derived] by the proof its metamodel starts, with the stages of `Derived_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Derived.class, fingerprint = "4c87ac5dd09c50fef8163a494bd5dd719b653a1b592afe485845720e25125cbd", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Derived_ {
    /// The shape of [Derived] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Derived] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Derived_"), "4c87ac5dd09c50fef8163a494bd5dd719b653a1b592afe485845720e25125cbd", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Derived"), List.of(), List.of(ClassDesc.of("p.Base"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.Api")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("abs"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("f"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("inherited"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("over"), Signature.of("own"), Signature.of("pkg"), Signature.of("prot"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("dstatic"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sbase")), Set.of(Signature.of("Derived"))), false, new Derived_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Derived], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Derived].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Derived open-class sealed=no nesting=top
        tparams -
        superclasses p.Base; java.lang.Object
        interfaces p.Api
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable abs() -> void throws -
        member method public overridable hidden(java.lang.Object) -> void throws -
        member method public overridable over() -> void throws -
        member method public overridable own() -> void throws -
        member method public static dstatic() -> void throws -
        inherit ctor Derived() public () throws -
        inherit method abs() erased () overrides p.Api by class public concrete p.Derived () -> void throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method dflt() erased () overrides - by interface default p.Api () -> void throws -
        inherit method dstatic() erased () overrides - by class public static p.Derived () -> void throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method f() erased () overrides - by class public concrete p.Base () -> int throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method hidden(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete p.Derived (java.lang.Object) -> void throws -
        inherit method hidden(java.lang.String) erased (java.lang.String) overrides - by class public static p.Base (java.lang.String) -> void throws -
        inherit method inherited() erased () overrides - by class public concrete p.Base () -> void throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method over() erased () overrides p.Base by class public concrete p.Derived () -> void throws -
        inherit method own() erased () overrides - by class public concrete p.Derived () -> void throws -
        inherit method pkg() erased () overrides - by class package concrete p.Base () -> void throws -
        inherit method prot() erased () overrides - by class protected concrete p.Base () -> void throws -
        inherit method sbase() erased () overrides - by class public static p.Base () -> int throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete abs(); clone(); dflt(); equals(java.lang.Object); f(); finalize(); getClass(); hashCode(); hidden(java.lang.Object); inherited(); notify(); notifyAll(); over(); own(); pkg(); prot(); toString(); wait(); wait(long); wait(long, int)
        table static dstatic(); hidden(java.lang.String); sbase()
        table ctor Derived()
        """;

        private Canonical() {
        }
    }

    /// The token of [Derived].
    public static final OpenClassToken<Derived> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Derived#Derived()].
    public static final CtorRef0<Derived> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Derived#abs()].
    public static final VoidMethodRef0<Derived> abs = UnsafeFacts.voidMethod(TOKEN, "abs", MemberTraits.OVERRIDABLE);

    /// The fact of [Derived#dstatic()].
    public static final VoidStaticMethodRef0 dstatic = UnsafeFacts.voidStaticMethod(TOKEN, "dstatic", MemberTraits.FINAL);

    /// The fact of [Derived#hidden(Object)].
    public static final VoidMethodRef1<Derived, Object> hidden_Object = UnsafeFacts.voidMethod(TOKEN, "hidden", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Derived#over()].
    public static final VoidMethodRef0<Derived> over = UnsafeFacts.voidMethod(TOKEN, "over", MemberTraits.OVERRIDABLE);

    /// The fact of [Derived#own()].
    public static final VoidMethodRef0<Derived> own = UnsafeFacts.voidMethod(TOKEN, "own", MemberTraits.OVERRIDABLE);

    private Derived_() {
    }

    /// Starts the proof that a class being declared extends [Derived]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Derived> extend(RefToken<Self> self) {
        return new Derived_Heritage().extend(TOKEN, self);
    }
}
