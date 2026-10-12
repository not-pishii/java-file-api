package gen.facts.p;

import gen.facts.p.Twins_.Canonical;
import gen.facts.p.Twins_.Data;
import gen.facts.p.Twins_Heritage.Extend;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
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
import p.Twins;

/// The full metamodel of [Twins], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Twins] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.Tight`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [Twins] by the proof its metamodel starts, with the stages of `Twins_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Twins.class, fingerprint = "4252d295979e96f876a3a0db2c0b68c2d377be41c5df16201054000386044243", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Twins_ {
    /// The shape of [Twins] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Twins] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Twins_"), "4252d295979e96f876a3a0db2c0b68c2d377be41c5df16201054000386044243", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Twins"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.Loose"), ClassDesc.of("p.Tight")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("twin")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Twins"))), false, new Twins_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Twins], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Twins].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Twins abstract-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces p.Loose; p.Tight
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public abstract twin() -> java.lang.String throws -
        inherit ctor Twins() public () throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method twin() erased () overrides - by interface abstract p.Tight () -> java.lang.String throws - | interface abstract p.Loose () -> java.lang.Object throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract twin()
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Twins()
        """;

        private Canonical() {
        }
    }

    /// The token of [Twins].
    public static final AbstractClassToken<Twins> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Twins#Twins()].
    public static final SuperCtorRef0<Twins> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Twins#twin()], declared in `p.Tight`, which is not `public`.
    public static final MethodRef0<Twins, String> twin = UnsafeFacts.method(TOKEN, "twin", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    private Twins_() {
    }

    /// Starts the proof that a class being declared extends [Twins]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Extend<Self> extend(RefToken<Self> self) {
        return new Twins_Heritage().extend(TOKEN, self);
    }
}
