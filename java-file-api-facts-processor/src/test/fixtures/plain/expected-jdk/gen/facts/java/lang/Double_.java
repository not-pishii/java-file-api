package gen.facts.java.lang;

import gen.facts.java.lang.Double_.Canonical;
import gen.facts.java.lang.Double_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Double.class, fingerprint = "0ce2bedc5b699d97fe2ea330373a98b17557375d62c602cf09d63b586ca1eb28", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Double_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Double_"), "0ce2bedc5b699d97fe2ea330373a98b17557375d62c602cf09d63b586ca1eb28", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Double"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Double"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Double"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("isInfinite"), Signature.of("isNaN"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("doubleToLongBits", Param.fixed(ConstantDescs.CD_double)), Signature.of("doubleToRawLongBits", Param.fixed(ConstantDescs.CD_double)), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_double)), Signature.of("isFinite", Param.fixed(ConstantDescs.CD_double)), Signature.of("isInfinite", Param.fixed(ConstantDescs.CD_double)), Signature.of("isNaN", Param.fixed(ConstantDescs.CD_double)), Signature.of("longBitsToDouble", Param.fixed(ConstantDescs.CD_long)), Signature.of("max", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("min", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("parseDouble", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sum", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("toHexString", Param.fixed(ConstantDescs.CD_double)), Signature.of("toString", Param.fixed(ConstantDescs.CD_double)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_double)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Double", Param.fixed(ConstantDescs.CD_double)), Signature.of("Double", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.lang.Double final-class sealed=no\ntparams -\nsuperclasses java.lang.Number; java.lang.Object\nsupertypes java.lang.Comparable<java.lang.Double>\nenum -\nmembers none\ntable abstract -\ntable concrete byteValue(); clone(); compareTo(java.lang.Double); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); isInfinite(); isNaN(); longValue(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static compare(double, double); doubleToLongBits(double); doubleToRawLongBits(double); hashCode(double); isFinite(double); isInfinite(double); isNaN(double); longBitsToDouble(long); max(double, double); min(double, double); parseDouble(java.lang.String); sum(double, double); toHexString(double); toString(double); valueOf(double); valueOf(java.lang.String)\ntable ctor Double(double); Double(java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Double> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Double_() {
    }
}
