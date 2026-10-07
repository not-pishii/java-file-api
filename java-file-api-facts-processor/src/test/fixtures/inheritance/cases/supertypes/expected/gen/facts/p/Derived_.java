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
@GeneratedMetamodel(of = Derived.class, fingerprint = "122568e3f40d40d8103d1920018e5e7e991d61b88f86a6fc49f5c2841d0cdb19", complete = true, format = 12)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Derived_"), "122568e3f40d40d8103d1920018e5e7e991d61b88f86a6fc49f5c2841d0cdb19", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Derived"), List.of(), List.of(ClassDesc.of("p.Base"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.Api")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("abs"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("f"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("inherited"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("over"), Signature.of("own"), Signature.of("pkg"), Signature.of("prot"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("dstatic"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sbase")), Set.of(Signature.of("Derived"))), false, new Derived_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Derived], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Derived].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Derived open-class sealed=no
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
        inherit ctor public () throws -
        inherit method package concrete p.Base pkg() -> void throws - erased () overrides -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method protected concrete p.Base prot() -> void throws - erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Base f() -> int throws - erased () overrides -
        inherit method public concrete p.Base inherited() -> void throws - erased () overrides -
        inherit method public concrete p.Derived abs() -> void throws - erased () overrides p.Api
        inherit method public concrete p.Derived hidden(java.lang.Object) -> void throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Derived over() -> void throws - erased () overrides p.Base
        inherit method public concrete p.Derived own() -> void throws - erased () overrides -
        inherit method public default p.Api dflt() -> void throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Base hidden(java.lang.String) -> void throws - erased (java.lang.String) overrides -
        inherit method public static p.Base sbase() -> int throws - erased () overrides -
        inherit method public static p.Derived dstatic() -> void throws - erased () overrides -
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
