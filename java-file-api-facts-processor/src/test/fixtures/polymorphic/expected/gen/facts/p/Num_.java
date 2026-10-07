package gen.facts.p;

import gen.facts.p.Num_.Canonical;
import gen.facts.p.Num_.Data;
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
/// A generated class extends or implements [Num] by the proof `Num_Heritage` starts.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Num.class, fingerprint = "b8689ad13df0569b0ab1f6d8f8b60d2f67bf4e81c9c0999800380a622ccaa7a9", complete = true, format = 11)
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
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Num_"), "b8689ad13df0569b0ab1f6d8f8b60d2f67bf4e81c9c0999800380a622ccaa7a9", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Num"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Num"))))))), new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Num"))), Signature.of("doubleValue"), Signature.of("floatValue"), Signature.of("intValue"), Signature.of("longValue")), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Num"))), false, new Num_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Num], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Num].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Num abstract-class sealed=no
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable
        supertypes java.lang.Comparable<p.Num>
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public abstract java.lang.Comparable compareTo(p.Num) -> int throws - erased (java.lang.Object) overrides -
        inherit method public abstract java.lang.Number doubleValue() -> double throws - erased () overrides -
        inherit method public abstract java.lang.Number floatValue() -> float throws - erased () overrides -
        inherit method public abstract java.lang.Number intValue() -> int throws - erased () overrides -
        inherit method public abstract java.lang.Number longValue() -> long throws - erased () overrides -
        inherit method public concrete java.lang.Number byteValue() -> byte throws - erased () overrides -
        inherit method public concrete java.lang.Number shortValue() -> short throws - erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
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
}
