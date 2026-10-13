package gen.facts.p;

import gen.facts.p.Tk_.Canonical;
import gen.facts.p.Tk_.Data;
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
import p.Other;
import p.Tk;

/// The full metamodel of [Tk], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Tk] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Tk] by the proof its metamodel starts, with the stages of `Tk_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Tk.class, fingerprint = "5921853b86296d5ec6b9d19b8a70f8044c9b745704b53751e09d7067f3147c58", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Tk_ {
    /// The shape of [Tk] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Tk] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Tk_"), "5921853b86296d5ec6b9d19b8a70f8044c9b745704b53751e09d7067f3147c58", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Tk"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Tk"))), false, new Tk_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Tk], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Tk].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Tk open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable int Float
        member field public instance mutable int MemberTraits
        member field public instance mutable int Other_
        member field public instance mutable int UnsafeFacts
        member field public instance mutable int fine
        member method public overridable other() -> p.Other throws -
        inherit ctor Tk() public () throws -
        inherit field public p.Tk Float
        inherit field public p.Tk MemberTraits
        inherit field public p.Tk Other_
        inherit field public p.Tk UnsafeFacts
        inherit field public p.Tk fine
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method other() erased () overrides - by class public concrete p.Tk () -> p.Other throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); other(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Tk()
        """;

        private Canonical() {
        }
    }

    /// The token of [Tk].
    public static final OpenClassToken<Tk> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Tk#Float].
    public static final MutableFieldRef<Tk, Int> Float = UnsafeFacts.mutableField(TOKEN, "Float", PrimitiveToken.INT);

    /// The fact of [Tk#MemberTraits].
    public static final MutableFieldRef<Tk, Int> MemberTraits_ = UnsafeFacts.mutableField(TOKEN, "MemberTraits", PrimitiveToken.INT);

    /// The fact of [Tk#Other_].
    public static final MutableFieldRef<Tk, Int> Other__ = UnsafeFacts.mutableField(TOKEN, "Other_", PrimitiveToken.INT);

    /// The fact of [Tk#UnsafeFacts].
    public static final MutableFieldRef<Tk, Int> UnsafeFacts_ = UnsafeFacts.mutableField(TOKEN, "UnsafeFacts", PrimitiveToken.INT);

    /// The fact of [Tk#fine].
    public static final MutableFieldRef<Tk, Int> fine = UnsafeFacts.mutableField(TOKEN, "fine", PrimitiveToken.INT);

    /// The fact of [Tk#Tk()].
    public static final CtorRef0<Tk> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Tk#other()].
    public static final MethodRef0<Tk, Other> other = UnsafeInvocables.method(TOKEN, "other", UnsafeFacts.<Other>openClassToken(Other_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Tk_() {
    }

    /// Starts the proof that a class being declared extends [Tk]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Tk> extend(RefToken<Self> self) {
        return new Tk_Heritage().extend(TOKEN, self);
    }
}
