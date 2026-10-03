package gen.facts.java.lang;

import gen.facts.java.lang.Number_.Canonical;
import gen.facts.java.lang.Number_.Data;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Number.class, fingerprint = "2109f00f9d995c02841b62678a8ce65deaeb4de810a43f65b3f918c6c69e6fd3", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Number_ {
    public static final class Data {
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Number_"), "2109f00f9d995c02841b62678a8ce65deaeb4de810a43f65b3f918c6c69e6fd3", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("java.lang.Number"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("doubleValue"), Signature.of("floatValue"), Signature.of("intValue"), Signature.of("longValue")), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Number"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.Number abstract-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method abstract doubleValue() -> double throws -\nmember method abstract floatValue() -> float throws -\nmember method abstract intValue() -> int throws -\nmember method abstract longValue() -> long throws -\nmember method overridable byteValue() -> byte throws -\nmember method overridable shortValue() -> short throws -\ntable abstract doubleValue(); floatValue(); intValue(); longValue()\ntable concrete byteValue(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Number()\n";

        private Canonical() {
        }
    }

    public static final AbstractClassToken<Number> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    public static final AbstractCtorRef0<Number> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Number, Byte> byteValue = UnsafeFacts.method(TOKEN, "byteValue", PrimitiveToken.BYTE, MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Number, Double> doubleValue = UnsafeFacts.method(TOKEN, "doubleValue", PrimitiveToken.DOUBLE, MemberTraits.ABSTRACT);

    public static final MethodRef0<Number, Float> floatValue = UnsafeFacts.method(TOKEN, "floatValue", PrimitiveToken.FLOAT, MemberTraits.ABSTRACT);

    public static final MethodRef0<Number, Int> intValue = UnsafeFacts.method(TOKEN, "intValue", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    public static final MethodRef0<Number, Long> longValue = UnsafeFacts.method(TOKEN, "longValue", PrimitiveToken.LONG, MemberTraits.ABSTRACT);

    public static final MethodRef0<Number, Short> shortValue = UnsafeFacts.method(TOKEN, "shortValue", PrimitiveToken.SHORT, MemberTraits.OVERRIDABLE);

    private Number_() {
    }
}
