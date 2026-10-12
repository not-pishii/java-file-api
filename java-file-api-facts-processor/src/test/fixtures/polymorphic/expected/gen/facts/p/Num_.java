package gen.facts.p;

import gen.facts.p.Num_.Canonical;
import gen.facts.p.Num_.Data;
import gen.facts.p.Num_Heritage.Extend;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Num;

/// The full metamodel of [Num], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Num] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Comparable_] and [gen.facts.java.lang.Number_].
///
/// A generated class extends or implements [Num] by the proof its metamodel starts, with the stages of `Num_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Num.class, fingerprint = "9347c8e08e409350d3e85a4249d572f3334327946327fe79aa58e3616998a6cb", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Num_ {
    /// The shape of [Num] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Num] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Num_"), "9347c8e08e409350d3e85a4249d572f3334327946327fe79aa58e3616998a6cb", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Num"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Num"))))))), new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Num"))), Signature.of("doubleValue"), Signature.of("floatValue"), Signature.of("intValue"), Signature.of("longValue")), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Num"))), false, new Num_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Num], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Num].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Num abstract-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable
        supertypes java.lang.Comparable<p.Num>
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor Num() public () throws -
        inherit method byteValue() erased () overrides - by class public concrete java.lang.Number () -> byte throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method compareTo(p.Num) erased (java.lang.Object) overrides - by interface abstract java.lang.Comparable (p.Num) -> int throws -
        inherit method doubleValue() erased () overrides - by class public abstract java.lang.Number () -> double throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method floatValue() erased () overrides - by class public abstract java.lang.Number () -> float throws -
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method intValue() erased () overrides - by class public abstract java.lang.Number () -> int throws -
        inherit method longValue() erased () overrides - by class public abstract java.lang.Number () -> long throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method shortValue() erased () overrides - by class public concrete java.lang.Number () -> short throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract compareTo(p.Num); doubleValue(); floatValue(); intValue(); longValue()
        table concrete byteValue(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Num()
        """;

        private Canonical() {
        }
    }

    /// The token of [Num].
    public static final AbstractClassToken<Num> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Num#Num()].
    public static final SuperCtorRef0<Num> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL);

    private Num_() {
    }

    /// Starts the proof that a class being declared extends [Num]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Extend<Self> extend(RefToken<Self> self) {
        return new Num_Heritage().extend(TOKEN, self);
    }
}
