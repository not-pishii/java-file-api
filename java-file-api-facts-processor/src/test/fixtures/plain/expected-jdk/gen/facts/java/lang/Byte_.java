package gen.facts.java.lang;

import gen.facts.java.lang.Byte_.Canonical;
import gen.facts.java.lang.Byte_.Data;
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
@GeneratedMetamodel(of = Byte.class, fingerprint = "8daf316f8ffd31ea5cf8df446fd0267d9fef88c24eefa39b5b90437a080ea427", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Byte_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Byte_"), "8daf316f8ffd31ea5cf8df446fd0267d9fef88c24eefa39b5b90437a080ea427", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Byte"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Byte"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Byte"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ConstantDescs.CD_byte)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ConstantDescs.CD_byte)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_byte)), Signature.of("parseByte", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseByte", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("toString", Param.fixed(ConstantDescs.CD_byte)), Signature.of("toUnsignedInt", Param.fixed(ConstantDescs.CD_byte)), Signature.of("toUnsignedLong", Param.fixed(ConstantDescs.CD_byte)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_byte)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("Byte", Param.fixed(ConstantDescs.CD_byte)), Signature.of("Byte", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.Byte final-class sealed=no\ntparams -\nsuperclasses java.lang.Number; java.lang.Object\nsupertypes java.lang.Comparable<java.lang.Byte>\nenum -\nmembers none\ntable abstract -\ntable concrete byteValue(); clone(); compareTo(java.lang.Byte); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); longValue(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static compare(byte, byte); compareUnsigned(byte, byte); decode(java.lang.String); hashCode(byte); parseByte(java.lang.String); parseByte(java.lang.String, int); toString(byte); toUnsignedInt(byte); toUnsignedLong(byte); valueOf(byte); valueOf(java.lang.String); valueOf(java.lang.String, int)\ntable ctor Byte(byte); Byte(java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Byte> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Byte_() {
    }
}
