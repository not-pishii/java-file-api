package gen.facts.p;

import gen.facts.p.Taken_.Canonical;
import gen.facts.p.Taken_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Taken;

/// The full metamodel of [Taken], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Taken] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Taken] by the proof its metamodel starts, with the stages of `Taken_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Taken.class, fingerprint = "3063a3ff2bd4406a1a4cdba245bd0a00135306f579faa546d375b7470327b9a5", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Taken_ {
    /// The shape of [Taken] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Taken] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Taken_"), "3063a3ff2bd4406a1a4cdba245bd0a00135306f579faa546d375b7470327b9a5", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Taken"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("extend"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("implement"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Taken"))), false, new Taken_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Taken], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Taken].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Taken open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable int Arity
        member field public instance mutable int Complete
        member field public instance mutable int Constructor
        member field public instance mutable int Dispatch
        member field public instance mutable int Extend
        member field public instance mutable int Held
        member field public instance mutable int Heritage
        member field public instance mutable int Implement
        member field public instance mutable int Implementation0
        member field public instance mutable int Implementation1
        member field public instance mutable int Method
        member field public instance mutable int Of
        member field public instance mutable int ParameterizedTypeRef
        member field public instance mutable int PrimitiveTypeRef
        member field public instance mutable int RefToken
        member field public instance mutable int Result
        member field public instance mutable int Self
        member field public instance mutable int Set
        member field public instance mutable int Supplier
        member field public instance mutable int Taken_Heritage
        member field public instance mutable int Told
        member field public instance mutable int Types
        member field public instance mutable int Visibility
        member field public instance mutable int VoidImplementation0
        member field public instance mutable int java
        member field public instance mutable int me
        member method public overridable extend() -> void throws -
        member method public overridable implement() -> void throws -
        inherit ctor Taken() public () throws -
        inherit field public p.Taken Arity
        inherit field public p.Taken Complete
        inherit field public p.Taken Constructor
        inherit field public p.Taken Dispatch
        inherit field public p.Taken Extend
        inherit field public p.Taken Held
        inherit field public p.Taken Heritage
        inherit field public p.Taken Implement
        inherit field public p.Taken Implementation0
        inherit field public p.Taken Implementation1
        inherit field public p.Taken Method
        inherit field public p.Taken Of
        inherit field public p.Taken ParameterizedTypeRef
        inherit field public p.Taken PrimitiveTypeRef
        inherit field public p.Taken RefToken
        inherit field public p.Taken Result
        inherit field public p.Taken Self
        inherit field public p.Taken Set
        inherit field public p.Taken Supplier
        inherit field public p.Taken Taken_Heritage
        inherit field public p.Taken Told
        inherit field public p.Taken Types
        inherit field public p.Taken Visibility
        inherit field public p.Taken VoidImplementation0
        inherit field public p.Taken java
        inherit field public p.Taken me
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method extend() erased () overrides - by class public concrete p.Taken () -> void throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method implement() erased () overrides - by class public concrete p.Taken () -> void throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        inherit type public p.Taken Abstract
        table abstract -
        table concrete clone(); equals(java.lang.Object); extend(); finalize(); getClass(); hashCode(); implement(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Taken()
        """;

        private Canonical() {
        }
    }

    /// The token of [Taken].
    public static final OpenClassToken<Taken> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Taken#Arity].
    public static final MutableFieldRef<Taken, Int> Arity = UnsafeFacts.mutableField(TOKEN, "Arity", PrimitiveToken.INT);

    /// The fact of [Taken#Complete].
    public static final MutableFieldRef<Taken, Int> Complete = UnsafeFacts.mutableField(TOKEN, "Complete", PrimitiveToken.INT);

    /// The fact of [Taken#Constructor].
    public static final MutableFieldRef<Taken, Int> Constructor = UnsafeFacts.mutableField(TOKEN, "Constructor", PrimitiveToken.INT);

    /// The fact of [Taken#Dispatch].
    public static final MutableFieldRef<Taken, Int> Dispatch = UnsafeFacts.mutableField(TOKEN, "Dispatch", PrimitiveToken.INT);

    /// The fact of [Taken#Extend].
    public static final MutableFieldRef<Taken, Int> Extend = UnsafeFacts.mutableField(TOKEN, "Extend", PrimitiveToken.INT);

    /// The fact of [Taken#Held].
    public static final MutableFieldRef<Taken, Int> Held = UnsafeFacts.mutableField(TOKEN, "Held", PrimitiveToken.INT);

    /// The fact of [Taken#Heritage].
    public static final MutableFieldRef<Taken, Int> Heritage = UnsafeFacts.mutableField(TOKEN, "Heritage", PrimitiveToken.INT);

    /// The fact of [Taken#Implement].
    public static final MutableFieldRef<Taken, Int> Implement = UnsafeFacts.mutableField(TOKEN, "Implement", PrimitiveToken.INT);

    /// The fact of [Taken#Implementation0].
    public static final MutableFieldRef<Taken, Int> Implementation0 = UnsafeFacts.mutableField(TOKEN, "Implementation0", PrimitiveToken.INT);

    /// The fact of [Taken#Implementation1].
    public static final MutableFieldRef<Taken, Int> Implementation1 = UnsafeFacts.mutableField(TOKEN, "Implementation1", PrimitiveToken.INT);

    /// The fact of [Taken#Method].
    public static final MutableFieldRef<Taken, Int> Method = UnsafeFacts.mutableField(TOKEN, "Method", PrimitiveToken.INT);

    /// The fact of [Taken#Of].
    public static final MutableFieldRef<Taken, Int> Of = UnsafeFacts.mutableField(TOKEN, "Of", PrimitiveToken.INT);

    /// The fact of [Taken#ParameterizedTypeRef].
    public static final MutableFieldRef<Taken, Int> ParameterizedTypeRef = UnsafeFacts.mutableField(TOKEN, "ParameterizedTypeRef", PrimitiveToken.INT);

    /// The fact of [Taken#PrimitiveTypeRef].
    public static final MutableFieldRef<Taken, Int> PrimitiveTypeRef = UnsafeFacts.mutableField(TOKEN, "PrimitiveTypeRef", PrimitiveToken.INT);

    /// The fact of [Taken#RefToken].
    public static final MutableFieldRef<Taken, Int> RefToken = UnsafeFacts.mutableField(TOKEN, "RefToken", PrimitiveToken.INT);

    /// The fact of [Taken#Result].
    public static final MutableFieldRef<Taken, Int> Result = UnsafeFacts.mutableField(TOKEN, "Result", PrimitiveToken.INT);

    /// The fact of [Taken#Self].
    public static final MutableFieldRef<Taken, Int> Self = UnsafeFacts.mutableField(TOKEN, "Self", PrimitiveToken.INT);

    /// The fact of [Taken#Set].
    public static final MutableFieldRef<Taken, Int> Set_ = UnsafeFacts.mutableField(TOKEN, "Set", PrimitiveToken.INT);

    /// The fact of [Taken#Supplier].
    public static final MutableFieldRef<Taken, Int> Supplier = UnsafeFacts.mutableField(TOKEN, "Supplier", PrimitiveToken.INT);

    /// The fact of [Taken#Taken_Heritage].
    public static final MutableFieldRef<Taken, Int> Taken_Heritage_ = UnsafeFacts.mutableField(TOKEN, "Taken_Heritage", PrimitiveToken.INT);

    /// The fact of [Taken#Told].
    public static final MutableFieldRef<Taken, Int> Told = UnsafeFacts.mutableField(TOKEN, "Told", PrimitiveToken.INT);

    /// The fact of [Taken#Types].
    public static final MutableFieldRef<Taken, Int> Types = UnsafeFacts.mutableField(TOKEN, "Types", PrimitiveToken.INT);

    /// The fact of [Taken#Visibility].
    public static final MutableFieldRef<Taken, Int> Visibility = UnsafeFacts.mutableField(TOKEN, "Visibility", PrimitiveToken.INT);

    /// The fact of [Taken#VoidImplementation0].
    public static final MutableFieldRef<Taken, Int> VoidImplementation0 = UnsafeFacts.mutableField(TOKEN, "VoidImplementation0", PrimitiveToken.INT);

    /// The fact of [Taken#java].
    public static final MutableFieldRef<Taken, Int> java = UnsafeFacts.mutableField(TOKEN, "java", PrimitiveToken.INT);

    /// The fact of [Taken#me].
    public static final MutableFieldRef<Taken, Int> me = UnsafeFacts.mutableField(TOKEN, "me", PrimitiveToken.INT);

    /// The fact of [Taken#Taken()].
    public static final CtorRef0<Taken> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Taken#extend()].
    public static final VoidMethodRef0<Taken> extend_ = UnsafeFacts.voidMethod(TOKEN, "extend", MemberTraits.OVERRIDABLE);

    /// The fact of [Taken#implement()].
    public static final VoidMethodRef0<Taken> implement_ = UnsafeFacts.voidMethod(TOKEN, "implement", MemberTraits.OVERRIDABLE);

    private Taken_() {
    }

    /// Starts the proof that a class being declared extends [Taken]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Taken> extend(RefToken<Self> self) {
        return new Taken_Heritage().extend(TOKEN, self);
    }
}
