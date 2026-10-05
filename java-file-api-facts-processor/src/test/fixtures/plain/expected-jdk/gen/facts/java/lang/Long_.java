package gen.facts.java.lang;

import gen.facts.java.lang.Long_.Canonical;
import gen.facts.java.lang.Long_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [Long]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Long]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Long.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Long.class, fingerprint = "881450aab8dabd8f1a51e850a46221caa34712a6837deebdbb791597ab250558", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Long_ {
    /// The shape of [Long] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Long] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Long_"), "881450aab8dabd8f1a51e850a46221caa34712a6837deebdbb791597ab250558", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Long"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Long"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Long"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("bitCount", Param.fixed(ConstantDescs.CD_long)), Signature.of("compare", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("compress", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("divideUnsigned", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("expand", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("getLong", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getLong", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Long"))), Signature.of("getLong", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_long)), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_long)), Signature.of("highestOneBit", Param.fixed(ConstantDescs.CD_long)), Signature.of("lowestOneBit", Param.fixed(ConstantDescs.CD_long)), Signature.of("max", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("min", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("numberOfLeadingZeros", Param.fixed(ConstantDescs.CD_long)), Signature.of("numberOfTrailingZeros", Param.fixed(ConstantDescs.CD_long)), Signature.of("parseLong", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseLong", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseLong", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseUnsignedLong", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseUnsignedLong", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseUnsignedLong", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("remainderUnsigned", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("reverse", Param.fixed(ConstantDescs.CD_long)), Signature.of("reverseBytes", Param.fixed(ConstantDescs.CD_long)), Signature.of("rotateLeft", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("rotateRight", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("signum", Param.fixed(ConstantDescs.CD_long)), Signature.of("sum", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_long)), Signature.of("toBinaryString", Param.fixed(ConstantDescs.CD_long)), Signature.of("toHexString", Param.fixed(ConstantDescs.CD_long)), Signature.of("toOctalString", Param.fixed(ConstantDescs.CD_long)), Signature.of("toString", Param.fixed(ConstantDescs.CD_long)), Signature.of("toString", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedString", Param.fixed(ConstantDescs.CD_long)), Signature.of("toUnsignedString", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedString0", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_long))), Set.of(Signature.of("Long", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Long", Param.fixed(ConstantDescs.CD_long)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Long], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Long].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.lang.Long final-class sealed=no\ntparams -\nsuperclasses java.lang.Number; java.lang.Object\ninterfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable; java.lang.constant.ConstantDesc\nsupertypes java.lang.Comparable<java.lang.Long>\nenum -\nmembers none\ntable abstract -\ntable concrete byteValue(); clone(); compareTo(java.lang.Long); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); longValue(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static bitCount(long); compare(long, long); compareUnsigned(long, long); compress(long, long); decode(java.lang.String); divideUnsigned(long, long); expand(long, long); getLong(java.lang.String); getLong(java.lang.String, java.lang.Long); getLong(java.lang.String, long); hashCode(long); highestOneBit(long); lowestOneBit(long); max(long, long); min(long, long); numberOfLeadingZeros(long); numberOfTrailingZeros(long); parseLong(java.lang.CharSequence, int, int, int); parseLong(java.lang.String); parseLong(java.lang.String, int); parseUnsignedLong(java.lang.CharSequence, int, int, int); parseUnsignedLong(java.lang.String); parseUnsignedLong(java.lang.String, int); remainderUnsigned(long, long); reverse(long); reverseBytes(long); rotateLeft(long, int); rotateRight(long, int); signum(long); sum(long, long); toBinaryString(long); toHexString(long); toOctalString(long); toString(long); toString(long, int); toUnsignedString(long); toUnsignedString(long, int); toUnsignedString0(long, int); valueOf(java.lang.String); valueOf(java.lang.String, int); valueOf(long)\ntable ctor Long(java.lang.String); Long(long)\n";

        private Canonical() {
        }
    }

    /// The token of [Long].
    public static final FinalClassToken<Long> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Long_() {
    }
}
