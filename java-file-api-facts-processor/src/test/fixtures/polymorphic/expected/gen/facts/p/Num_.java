package gen.facts.p;

import gen.facts.p.Num_.Canonical;
import gen.facts.p.Num_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Num;

/// The full metamodel of [Num], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Num] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Comparable_] and [gen.facts.java.lang.Number_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Num.class, fingerprint = "f0d27c06f1b467924b4d5e21807b1c91d04cd838d8c57b9d4239212ce75861b9", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Num_ {
    /// The shape of [Num] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Num] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Num_"), "f0d27c06f1b467924b4d5e21807b1c91d04cd838d8c57b9d4239212ce75861b9", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Num"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Num"))))))), new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Num"))), Signature.of("doubleValue"), Signature.of("floatValue"), Signature.of("intValue"), Signature.of("longValue")), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Num"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Num], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Num].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Num abstract-class sealed=no\ntparams -\nsuperclasses java.lang.Number; java.lang.Object\ninterfaces java.io.Serializable; java.lang.Comparable\nsupertypes java.lang.Comparable<p.Num>\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract compareTo(p.Num); doubleValue(); floatValue(); intValue(); longValue()\ntable concrete byteValue(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Num()\n";

        private Canonical() {
        }
    }

    /// The token of [Num].
    public static final AbstractClassToken<Num> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Num#Num()].
    public static final AbstractCtorRef0<Num> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    private Num_() {
    }
}
