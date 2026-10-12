package gen.facts.java.lang;

import gen.facts.java.lang.Number_.Canonical;
import gen.facts.java.lang.Number_.Data;
import gen.facts.java.lang.Number_Heritage.Extend;
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
import me.supcheg.javafile.facts.Prim.Byte;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Float;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.Prim.Short;
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

/// The full metamodel of [Number]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Number]: it is here as a supertype of [p.Num], whose inherited members are called through this metamodel.
///
/// A member [Number] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.io.Serializable_] and [Object_].
///
/// A generated class extends or implements [Number] by the proof its metamodel starts, with the stages of `Number_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Number.class, fingerprint = "cdeb747d55ff81e4445d76148c2c5db8aabfe943e908df051d36054ae377ecc7", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Number_ {
    /// The shape of [Number] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Number] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Number_"), "cdeb747d55ff81e4445d76148c2c5db8aabfe943e908df051d36054ae377ecc7", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("java.lang.Number"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("doubleValue"), Signature.of("floatValue"), Signature.of("intValue"), Signature.of("longValue")), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Number"))), false, new Number_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Number], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Number].
        static final String TEXT = """
        javafile-facts-canonical 8
        type java.lang.Number abstract-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces java.io.Serializable
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public abstract doubleValue() -> double throws -
        member method public abstract floatValue() -> float throws -
        member method public abstract intValue() -> int throws -
        member method public abstract longValue() -> long throws -
        member method public overridable byteValue() -> byte throws -
        member method public overridable shortValue() -> short throws -
        inherit ctor Number() public () throws -
        inherit method byteValue() erased () overrides - by class public concrete java.lang.Number () -> byte throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
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
        table abstract doubleValue(); floatValue(); intValue(); longValue()
        table concrete byteValue(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Number()
        """;

        private Canonical() {
        }
    }

    /// The token of [Number].
    public static final AbstractClassToken<Number> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Number#Number()].
    public static final SuperCtorRef0<Number> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Number#byteValue()].
    public static final MethodRef0<Number, Byte> byteValue = UnsafeFacts.method(TOKEN, "byteValue", PrimitiveToken.BYTE, MemberTraits.OVERRIDABLE);

    /// The fact of [Number#doubleValue()].
    public static final MethodRef0<Number, Double> doubleValue = UnsafeFacts.method(TOKEN, "doubleValue", PrimitiveToken.DOUBLE, MemberTraits.ABSTRACT);

    /// The fact of [Number#floatValue()].
    public static final MethodRef0<Number, Float> floatValue = UnsafeFacts.method(TOKEN, "floatValue", PrimitiveToken.FLOAT, MemberTraits.ABSTRACT);

    /// The fact of [Number#intValue()].
    public static final MethodRef0<Number, Int> intValue = UnsafeFacts.method(TOKEN, "intValue", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [Number#longValue()].
    public static final MethodRef0<Number, Long> longValue = UnsafeFacts.method(TOKEN, "longValue", PrimitiveToken.LONG, MemberTraits.ABSTRACT);

    /// The fact of [Number#shortValue()].
    public static final MethodRef0<Number, Short> shortValue = UnsafeFacts.method(TOKEN, "shortValue", PrimitiveToken.SHORT, MemberTraits.OVERRIDABLE);

    private Number_() {
    }

    /// Starts the proof that a class being declared extends [Number]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Extend<Self> extend(RefToken<Self> self) {
        return new Number_Heritage().extend(TOKEN, self);
    }
}
