package b.facts.java.lang;

import b.facts.java.lang.Integer_.Canonical;
import b.facts.java.lang.Integer_.Data;
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
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Integer]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Integer]: it is only mentioned in the signatures of [p.Uses]. For the facts of its members add `Integer.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Integer.class, fingerprint = "6805a372a43e344c0d1b693cca3172c0ffb70d2d36a98cf05d006717a0fb0e03", complete = false, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Integer_ {
    /// The shape of [Integer] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Integer] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("b.facts.java.lang.Integer_"), "6805a372a43e344c0d1b693cca3172c0ffb70d2d36a98cf05d006717a0fb0e03", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Integer"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable"), ClassDesc.of("java.lang.constant.ConstantDesc")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Integer"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("bitCount", Param.fixed(ConstantDescs.CD_int)), Signature.of("compare", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("compress", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("divideUnsigned", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("expand", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("getInteger", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getInteger", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("getInteger", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_int)), Signature.of("highestOneBit", Param.fixed(ConstantDescs.CD_int)), Signature.of("lowestOneBit", Param.fixed(ConstantDescs.CD_int)), Signature.of("max", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("min", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("numberOfLeadingZeros", Param.fixed(ConstantDescs.CD_int)), Signature.of("numberOfTrailingZeros", Param.fixed(ConstantDescs.CD_int)), Signature.of("parseInt", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseInt", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseInt", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseUnsignedInt", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseUnsignedInt", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseUnsignedInt", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("remainderUnsigned", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("reverse", Param.fixed(ConstantDescs.CD_int)), Signature.of("reverseBytes", Param.fixed(ConstantDescs.CD_int)), Signature.of("rotateLeft", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("rotateRight", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("signum", Param.fixed(ConstantDescs.CD_int)), Signature.of("sum", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toBinaryString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toHexString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toOctalString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toString", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedLong", Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedString", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("Integer", Param.fixed(ConstantDescs.CD_int)), Signature.of("Integer", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Integer], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Integer].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.Integer final-class sealed=no
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable; java.lang.constant.ConstantDesc
        supertypes java.lang.Comparable<java.lang.Integer>
        enum -
        members none
        table abstract -
        table concrete byteValue(); clone(); compareTo(java.lang.Integer); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); longValue(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); shortValue(); toString(); wait(); wait(long); wait(long, int)
        table static bitCount(int); compare(int, int); compareUnsigned(int, int); compress(int, int); decode(java.lang.String); divideUnsigned(int, int); expand(int, int); getInteger(java.lang.String); getInteger(java.lang.String, int); getInteger(java.lang.String, java.lang.Integer); hashCode(int); highestOneBit(int); lowestOneBit(int); max(int, int); min(int, int); numberOfLeadingZeros(int); numberOfTrailingZeros(int); parseInt(java.lang.CharSequence, int, int, int); parseInt(java.lang.String); parseInt(java.lang.String, int); parseUnsignedInt(java.lang.CharSequence, int, int, int); parseUnsignedInt(java.lang.String); parseUnsignedInt(java.lang.String, int); remainderUnsigned(int, int); reverse(int); reverseBytes(int); rotateLeft(int, int); rotateRight(int, int); signum(int); sum(int, int); toBinaryString(int); toHexString(int); toOctalString(int); toString(int); toString(int, int); toUnsignedLong(int); toUnsignedString(int); toUnsignedString(int, int); valueOf(int); valueOf(java.lang.String); valueOf(java.lang.String, int)
        table ctor Integer(int); Integer(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Integer].
    public static final FinalClassToken<Integer> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Integer_() {
    }
}
