package gen.facts.java.lang;

import gen.facts.java.lang.Float_.Canonical;
import gen.facts.java.lang.Float_.Data;
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
@GeneratedMetamodel(of = Float.class, fingerprint = "19742bf390cb76d9da59aad23e68fd4cfec2c3f50b563b1985799e5760b859db", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Float_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Float_"), "19742bf390cb76d9da59aad23e68fd4cfec2c3f50b563b1985799e5760b859db", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Float"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Float"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Float"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("isInfinite"), Signature.of("isNaN"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("float16ToFloat", Param.fixed(ConstantDescs.CD_short)), Signature.of("floatToFloat16", Param.fixed(ConstantDescs.CD_float)), Signature.of("floatToIntBits", Param.fixed(ConstantDescs.CD_float)), Signature.of("floatToRawIntBits", Param.fixed(ConstantDescs.CD_float)), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_float)), Signature.of("intBitsToFloat", Param.fixed(ConstantDescs.CD_int)), Signature.of("isFinite", Param.fixed(ConstantDescs.CD_float)), Signature.of("isInfinite", Param.fixed(ConstantDescs.CD_float)), Signature.of("isNaN", Param.fixed(ConstantDescs.CD_float)), Signature.of("max", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("min", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("parseFloat", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sum", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("toHexString", Param.fixed(ConstantDescs.CD_float)), Signature.of("toString", Param.fixed(ConstantDescs.CD_float)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_float)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Float", Param.fixed(ConstantDescs.CD_double)), Signature.of("Float", Param.fixed(ConstantDescs.CD_float)), Signature.of("Float", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.lang.Float final-class sealed=no\ntparams -\nsuperclasses java.lang.Number; java.lang.Object\nsupertypes java.lang.Comparable<java.lang.Float>\nenum -\nmembers none\ntable abstract -\ntable concrete byteValue(); clone(); compareTo(java.lang.Float); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); isInfinite(); isNaN(); longValue(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static compare(float, float); float16ToFloat(short); floatToFloat16(float); floatToIntBits(float); floatToRawIntBits(float); hashCode(float); intBitsToFloat(int); isFinite(float); isInfinite(float); isNaN(float); max(float, float); min(float, float); parseFloat(java.lang.String); sum(float, float); toHexString(float); toString(float); valueOf(float); valueOf(java.lang.String)\ntable ctor Float(double); Float(float); Float(java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Float> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Float_() {
    }
}
