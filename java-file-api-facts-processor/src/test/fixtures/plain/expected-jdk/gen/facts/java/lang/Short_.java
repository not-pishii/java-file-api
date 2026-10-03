package gen.facts.java.lang;

import gen.facts.java.lang.Short_.Canonical;
import gen.facts.java.lang.Short_.Data;
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
@GeneratedMetamodel(of = Short.class, fingerprint = "e6b83115a95100c6b19439b774ce10943e4b6291a412e5d76393ee74c8e79dce", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Short_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Short_"), "e6b83115a95100c6b19439b774ce10943e4b6291a412e5d76393ee74c8e79dce", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Short"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Short"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Short"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_short), Param.fixed(ConstantDescs.CD_short)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_short), Param.fixed(ConstantDescs.CD_short)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_short)), Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("reverseBytes", Param.fixed(ConstantDescs.CD_short)), Signature.of("toString", Param.fixed(ConstantDescs.CD_short)), Signature.of("toUnsignedInt", Param.fixed(ConstantDescs.CD_short)), Signature.of("toUnsignedLong", Param.fixed(ConstantDescs.CD_short)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_short))), Set.of(Signature.of("Short", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Short", Param.fixed(ConstantDescs.CD_short)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.Short final-class sealed=no\ntparams -\nsuperclasses java.lang.Number; java.lang.Object\nsupertypes java.lang.Comparable<java.lang.Short>\nenum -\nmembers none\ntable abstract -\ntable concrete byteValue(); clone(); compareTo(java.lang.Short); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); longValue(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static compare(short, short); compareUnsigned(short, short); decode(java.lang.String); hashCode(short); parseShort(java.lang.String); parseShort(java.lang.String, int); reverseBytes(short); toString(short); toUnsignedInt(short); toUnsignedLong(short); valueOf(java.lang.String); valueOf(java.lang.String, int); valueOf(short)\ntable ctor Short(java.lang.String); Short(short)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Short> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Short_() {
    }
}
