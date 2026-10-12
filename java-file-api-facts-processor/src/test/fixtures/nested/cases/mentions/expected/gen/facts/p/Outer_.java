package gen.facts.p;

import gen.facts.p.Outer_.Canonical;
import gen.facts.p.Outer_.Data;
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
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Outer;
import p.Outer.E;
import p.Outer.Inner;
import p.Outer.NonStatic;

/// The full metamodel of [Outer], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Outer] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Outer] by the proof its metamodel starts, with the stages of `Outer_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Outer.class, fingerprint = "f160748de085daf54609415a5f6433681dfc5ca9e960a67151eb636831a4e0a9", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Outer_ {
    /// The shape of [Outer] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Outer] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_"), "f160748de085daf54609415a5f6433681dfc5ca9e960a67151eb636831a4e0a9", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Outer"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("e"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("inner"), Signature.of("nonStatic"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Outer"))), false, new Outer_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Outer], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Outer].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Outer open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable e() -> p.Outer$E throws -
        member method public overridable inner() -> p.Outer$Inner throws -
        member method public overridable nonStatic() -> p.Outer$NonStatic throws -
        inherit ctor Outer() public () throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method e() erased () overrides - by class public concrete p.Outer () -> p.Outer$E throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method inner() erased () overrides - by class public concrete p.Outer () -> p.Outer$Inner throws -
        inherit method nonStatic() erased () overrides - by class public concrete p.Outer () -> p.Outer$NonStatic throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        inherit type public p.Outer Api
        inherit type public p.Outer Deep
        inherit type public p.Outer E
        inherit type public p.Outer Inner
        inherit type public p.Outer NonStatic
        inherit type public p.Outer R
        table abstract -
        table concrete clone(); e(); equals(java.lang.Object); finalize(); getClass(); hashCode(); inner(); nonStatic(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Outer()
        """;

        private Canonical() {
        }
    }

    /// The token of [Outer].
    public static final OpenClassToken<Outer> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Outer#Outer()].
    public static final CtorRef0<Outer> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Outer#e()].
    public static final MethodRef0<Outer, E> e = UnsafeFacts.method(TOKEN, "e", UnsafeFacts.<E>enumToken(Outer_E_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Outer#inner()].
    public static final MethodRef0<Outer, Inner> inner = UnsafeFacts.method(TOKEN, "inner", UnsafeFacts.<Inner>openClassToken(Outer_Inner_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Outer#nonStatic()].
    public static final MethodRef0<Outer, NonStatic> nonStatic = UnsafeFacts.method(TOKEN, "nonStatic", UnsafeFacts.<NonStatic>openClassToken(Outer_NonStatic_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Outer_() {
    }

    /// Starts the proof that a class being declared extends [Outer]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Outer> extend(RefToken<Self> self) {
        return new Outer_Heritage().extend(TOKEN, self);
    }
}
