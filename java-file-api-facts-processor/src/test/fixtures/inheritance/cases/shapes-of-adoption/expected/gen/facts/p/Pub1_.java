package gen.facts.p;

import gen.facts.p.Pub1_.Canonical;
import gen.facts.p.Pub1_.Data;
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
import p.Pub1;

/// The full metamodel of [Pub1]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Pub1]: it is here as a supertype of [p.Pub2], whose inherited members are called through this metamodel.
///
/// A member [Pub1] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.H0`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [Pub1] by the proof its metamodel starts, with the stages of `Pub1_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Pub1.class, fingerprint = "7993353b20bfed6820fdb150b8bee19d6968e015568590cef217ec24697daa49", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Pub1_ {
    /// The shape of [Pub1] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Pub1] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Pub1_"), "7993353b20bfed6820fdb150b8bee19d6968e015568590cef217ec24697daa49", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Pub1"), List.of(), List.of(ClassDesc.of("p.H0"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("one"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("zero")), Set.of(), Set.of(Signature.of("Pub1"))), false, new Pub1_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Pub1], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Pub1].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Pub1 open-class sealed=no nesting=top
        tparams -
        superclasses p.H0; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable one() -> java.lang.String throws -
        member method public overridable zero() -> java.lang.String throws -
        inherit ctor Pub1() public () throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method one() erased () overrides - by class public concrete p.Pub1 () -> java.lang.String throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        inherit method zero() erased () overrides - by class public concrete p.H0 () -> java.lang.String throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); one(); toString(); wait(); wait(long); wait(long, int); zero()
        table static -
        table ctor Pub1()
        """;

        private Canonical() {
        }
    }

    /// The token of [Pub1].
    public static final OpenClassToken<Pub1> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Pub1#Pub1()].
    public static final CtorRef0<Pub1> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Pub1#one()].
    public static final MethodRef0<Pub1, String> one = UnsafeFacts.method(TOKEN, "one", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub1#zero()], declared in `p.H0`, which is not `public`.
    public static final MethodRef0<Pub1, String> zero = UnsafeFacts.method(TOKEN, "zero", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Pub1_() {
    }

    /// Starts the proof that a class being declared extends [Pub1]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Pub1> extend(RefToken<Self> self) {
        return new Pub1_Heritage().extend(TOKEN, self);
    }
}
