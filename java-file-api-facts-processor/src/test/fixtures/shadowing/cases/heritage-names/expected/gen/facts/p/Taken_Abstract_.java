package gen.facts.p;

import gen.facts.p.Taken_Abstract_.Canonical;
import gen.facts.p.Taken_Abstract_.Data;
import gen.facts.p.Taken_Abstract_Heritage.Extend;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Taken.Abstract;

/// The full metamodel of [Abstract], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Abstract] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Abstract] by the proof its metamodel starts, with the stages of `Taken_Abstract_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Abstract.class, fingerprint = "e0506f5285fb7b3406ff1ff5172b15387d4d9ab458ff52fab0136b59bd04e55a", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Taken_Abstract_ {
    /// The shape of [Abstract] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Abstract] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Taken_Abstract_"), "e0506f5285fb7b3406ff1ff5172b15387d4d9ab458ff52fab0136b59bd04e55a", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Taken$Abstract"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("Method")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Taken$Abstract"))), false, new Taken_Abstract_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Abstract], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Abstract].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Taken$Abstract abstract-class sealed=no nesting=static
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable int Complete
        member field public instance mutable int Implementation0
        member method public abstract Method() -> int throws -
        inherit ctor Taken$Abstract() public () throws -
        inherit field public p.Taken$Abstract Complete
        inherit field public p.Taken$Abstract Implementation0
        inherit method Method() erased () overrides - by class public abstract p.Taken$Abstract () -> int throws -
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
        table abstract Method()
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Taken$Abstract()
        """;

        private Canonical() {
        }
    }

    /// The token of [Abstract].
    public static final AbstractClassToken<Abstract> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Abstract#Complete].
    public static final MutableFieldRef<Abstract, Int> Complete = UnsafeFacts.mutableField(TOKEN, "Complete", PrimitiveToken.INT);

    /// The fact of [Abstract#Implementation0].
    public static final MutableFieldRef<Abstract, Int> Implementation0 = UnsafeFacts.mutableField(TOKEN, "Implementation0", PrimitiveToken.INT);

    /// The fact of [Abstract#Abstract()].
    public static final SuperCtorRef0<Abstract> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Abstract#Method()].
    public static final MethodRef0<Abstract, Int> Method = UnsafeFacts.method(TOKEN, "Method", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    private Taken_Abstract_() {
    }

    /// Starts the proof that a class being declared extends [Abstract]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Extend<Self> extend(RefToken<Self> self) {
        return new Taken_Abstract_Heritage().extend(TOKEN, self);
    }
}
