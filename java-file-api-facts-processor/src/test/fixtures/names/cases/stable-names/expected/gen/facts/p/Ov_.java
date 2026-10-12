package gen.facts.p;

import gen.facts.p.Ov_.Canonical;
import gen.facts.p.Ov_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef1;
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
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Ov;

/// The full metamodel of [Ov], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Ov] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method m(java.util.List<p.Hidden>)`, which mentions types that are not public: p.Hidden
/// - `constructor Ov(java.util.List<p.Hidden>)`, which mentions types that are not public: p.Hidden
///
/// A generated class extends or implements [Ov] by the proof its metamodel starts, with the stages of `Ov_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ov.class, fingerprint = "fd7f57b29f36e2ef79cfe42b20f3c92f36d14917e621aec920382454229a2646", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Ov_ {
    /// The shape of [Ov] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Ov] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ov_"), "fd7f57b29f36e2ef79cfe42b20f3c92f36d14917e621aec920382454229a2646", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ov"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("k", Param.fixed(ConstantDescs.CD_int)), Signature.of("k", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("m", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("m", Param.fixed(ClassDesc.of("java.util.List"))), Signature.of("n", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("size"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Ov", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("Ov", Param.fixed(ClassDesc.of("java.util.List"))))), false, new Ov_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Ov], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Ov].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Ov open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (java.awt.List) throws -
        member field public instance mutable int size
        member method public overridable <^0> k(^0) -> void throws -
        member method public overridable <^0> size() -> ^0 throws -
        member method public overridable k(int) -> void throws -
        member method public overridable m(java.awt.List) -> void throws -
        member method public overridable n(java.awt.List) -> void throws -
        inherit ctor Ov(java.awt.List) public (java.awt.List) throws -
        inherit ctor Ov(java.util.List) public (java.util.List<p.Hidden>) throws -
        inherit field public p.Ov size
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method k(int) erased (int) overrides - by class public concrete p.Ov (int) -> void throws -
        inherit method k(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete p.Ov <^0> (^0) -> void throws -
        inherit method m(java.awt.List) erased (java.awt.List) overrides - by class public concrete p.Ov (java.awt.List) -> void throws -
        inherit method m(java.util.List) erased (java.util.List) overrides - by class public concrete p.Ov (java.util.List<p.Hidden>) -> void throws -
        inherit method n(java.awt.List) erased (java.awt.List) overrides - by class public concrete p.Ov (java.awt.List) -> void throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method size() erased () overrides - by class public concrete p.Ov <^0> () -> ^0 throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); k(int); k(java.lang.Object); m(java.awt.List); m(java.util.List); n(java.awt.List); notify(); notifyAll(); size(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Ov(java.awt.List); Ov(java.util.List)
        """;

        private Canonical() {
        }
    }

    /// The token of [Ov].
    public static final OpenClassToken<Ov> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Ov#size].
    public static final MutableFieldRef<Ov, Int> size = UnsafeFacts.mutableField(TOKEN, "size", PrimitiveToken.INT);

    /// The fact of [Ov#Ov(java.awt.List)].
    public static final CtorRef1<Ov, java.awt.List> new_java_awt_List = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Ov#k(int)].
    public static final VoidMethodRef1<Ov, Int> k_int = UnsafeFacts.voidMethod(TOKEN, "k", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#m(java.awt.List)].
    public static final VoidMethodRef1<Ov, java.awt.List> m_java_awt_List = UnsafeFacts.voidMethod(TOKEN, "m", UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#n(java.awt.List)].
    public static final VoidMethodRef1<Ov, java.awt.List> n_List = UnsafeFacts.voidMethod(TOKEN, "n", UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Ov_() {
    }

    /// The fact of [Ov#k(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> VoidMethodRef1<Ov, T> k_T(RefToken<T> t) {
        return UnsafeFacts.voidMethod(TOKEN, "k", UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Ov#size()], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef0<Ov, T> size_(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "size", t, MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// Starts the proof that a class being declared extends [Ov]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Ov> extend(RefToken<Self> self) {
        return new Ov_Heritage().extend(TOKEN, self);
    }
}
