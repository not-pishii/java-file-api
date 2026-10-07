package gen.facts.java.lang;

import gen.facts.java.lang.Integer_.Canonical;
import gen.facts.java.lang.Integer_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Byte;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Float;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.Prim.Short;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Integer], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Integer] inherits has its fact in the metamodel of the supertype that declares it: [Comparable_], [Number_], [gen.facts.java.lang.constant.Constable_] and [gen.facts.java.lang.constant.ConstantDesc_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Integer.class, fingerprint = "098916a55e76e82a51db243f50865b4167dd36a6ed3c6d96cc767b9728774ef2", complete = true, format = 9)
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
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Integer_"), "098916a55e76e82a51db243f50865b4167dd36a6ed3c6d96cc767b9728774ef2", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Integer"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable"), ClassDesc.of("java.lang.constant.ConstantDesc")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Integer"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("bitCount", Param.fixed(ConstantDescs.CD_int)), Signature.of("compare", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("compress", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("divideUnsigned", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("expand", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("getInteger", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getInteger", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("getInteger", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_int)), Signature.of("highestOneBit", Param.fixed(ConstantDescs.CD_int)), Signature.of("lowestOneBit", Param.fixed(ConstantDescs.CD_int)), Signature.of("max", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("min", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("numberOfLeadingZeros", Param.fixed(ConstantDescs.CD_int)), Signature.of("numberOfTrailingZeros", Param.fixed(ConstantDescs.CD_int)), Signature.of("parseInt", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseInt", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseInt", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseUnsignedInt", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("parseUnsignedInt", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseUnsignedInt", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("remainderUnsigned", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("reverse", Param.fixed(ConstantDescs.CD_int)), Signature.of("reverseBytes", Param.fixed(ConstantDescs.CD_int)), Signature.of("rotateLeft", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("rotateRight", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("signum", Param.fixed(ConstantDescs.CD_int)), Signature.of("sum", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toBinaryString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toHexString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toOctalString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toString", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedLong", Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toUnsignedString", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("Integer", Param.fixed(ConstantDescs.CD_int)), Signature.of("Integer", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Integer], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Integer].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.lang.Integer final-class sealed=no
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable; java.lang.constant.ConstantDesc
        supertypes java.lang.Comparable<java.lang.Integer>
        enum -
        members declared-accessible
        member ctor public (int) throws -
        member ctor public (java.lang.String) throws java.lang.NumberFormatException
        member field public static constant int BYTES = 4
        member field public static constant int MAX_VALUE = 2147483647
        member field public static constant int MIN_VALUE = -2147483648
        member field public static constant int SIZE = 32
        member field public static final java.lang.Class<java.lang.Integer> TYPE
        member method public final byteValue() -> byte throws -
        member method public final compareTo(java.lang.Integer) -> int throws -
        member method public final describeConstable() -> java.util.Optional<java.lang.Integer> throws -
        member method public final doubleValue() -> double throws -
        member method public final equals(java.lang.Object) -> boolean throws -
        member method public final floatValue() -> float throws -
        member method public final hashCode() -> int throws -
        member method public final intValue() -> int throws -
        member method public final longValue() -> long throws -
        member method public final resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup) -> java.lang.Integer throws -
        member method public final shortValue() -> short throws -
        member method public final toString() -> java.lang.String throws -
        member method public static bitCount(int) -> int throws -
        member method public static compare(int, int) -> int throws -
        member method public static compareUnsigned(int, int) -> int throws -
        member method public static compress(int, int) -> int throws -
        member method public static decode(java.lang.String) -> java.lang.Integer throws java.lang.NumberFormatException
        member method public static divideUnsigned(int, int) -> int throws -
        member method public static expand(int, int) -> int throws -
        member method public static getInteger(java.lang.String) -> java.lang.Integer throws -
        member method public static getInteger(java.lang.String, int) -> java.lang.Integer throws -
        member method public static getInteger(java.lang.String, java.lang.Integer) -> java.lang.Integer throws -
        member method public static hashCode(int) -> int throws -
        member method public static highestOneBit(int) -> int throws -
        member method public static lowestOneBit(int) -> int throws -
        member method public static max(int, int) -> int throws -
        member method public static min(int, int) -> int throws -
        member method public static numberOfLeadingZeros(int) -> int throws -
        member method public static numberOfTrailingZeros(int) -> int throws -
        member method public static parseInt(java.lang.CharSequence, int, int, int) -> int throws java.lang.NumberFormatException
        member method public static parseInt(java.lang.String) -> int throws java.lang.NumberFormatException
        member method public static parseInt(java.lang.String, int) -> int throws java.lang.NumberFormatException
        member method public static parseUnsignedInt(java.lang.CharSequence, int, int, int) -> int throws java.lang.NumberFormatException
        member method public static parseUnsignedInt(java.lang.String) -> int throws java.lang.NumberFormatException
        member method public static parseUnsignedInt(java.lang.String, int) -> int throws java.lang.NumberFormatException
        member method public static remainderUnsigned(int, int) -> int throws -
        member method public static reverse(int) -> int throws -
        member method public static reverseBytes(int) -> int throws -
        member method public static rotateLeft(int, int) -> int throws -
        member method public static rotateRight(int, int) -> int throws -
        member method public static signum(int) -> int throws -
        member method public static sum(int, int) -> int throws -
        member method public static toBinaryString(int) -> java.lang.String throws -
        member method public static toHexString(int) -> java.lang.String throws -
        member method public static toOctalString(int) -> java.lang.String throws -
        member method public static toString(int) -> java.lang.String throws -
        member method public static toString(int, int) -> java.lang.String throws -
        member method public static toUnsignedLong(int) -> long throws -
        member method public static toUnsignedString(int) -> java.lang.String throws -
        member method public static toUnsignedString(int, int) -> java.lang.String throws -
        member method public static valueOf(int) -> java.lang.Integer throws -
        member method public static valueOf(java.lang.String) -> java.lang.Integer throws java.lang.NumberFormatException
        member method public static valueOf(java.lang.String, int) -> java.lang.Integer throws java.lang.NumberFormatException
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

    /// The fact of [Integer#BYTES].
    public static final StaticFieldRef<Int> BYTES = UnsafeFacts.constantField(TOKEN, "BYTES", PrimitiveToken.INT, 4);

    /// The fact of [Integer#MAX_VALUE].
    public static final StaticFieldRef<Int> MAX_VALUE = UnsafeFacts.constantField(TOKEN, "MAX_VALUE", PrimitiveToken.INT, 2147483647);

    /// The fact of [Integer#MIN_VALUE].
    public static final StaticFieldRef<Int> MIN_VALUE = UnsafeFacts.constantField(TOKEN, "MIN_VALUE", PrimitiveToken.INT, -2147483648);

    /// The fact of [Integer#SIZE].
    public static final StaticFieldRef<Int> SIZE = UnsafeFacts.constantField(TOKEN, "SIZE", PrimitiveToken.INT, 32);

    /// The fact of [Integer#TYPE].
    public static final StaticFieldRef<Class<Integer>> TYPE = UnsafeFacts.staticField(TOKEN, "TYPE", UnsafeFacts.<Class<Integer>>finalClassToken(Class_.Data.SHAPE, TokenArg.exact(TOKEN)));

    /// The fact of [Integer#Integer(String)].
    public static final CtorRef1<Integer, String> new_String = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#Integer(int)].
    public static final CtorRef1<Integer, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#bitCount(int)].
    public static final StaticMethodRef1<Int, Int> bitCount_int = UnsafeFacts.staticMethod(TOKEN, "bitCount", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#byteValue()].
    public static final MethodRef0<Integer, Byte> byteValue = UnsafeFacts.method(TOKEN, "byteValue", PrimitiveToken.BYTE, MemberTraits.FINAL);

    /// The fact of [Integer#compareTo(Integer)].
    public static final MethodRef1<Integer, Int, Integer> compareTo_Integer = UnsafeFacts.method(TOKEN, "compareTo", PrimitiveToken.INT, TOKEN, MemberTraits.FINAL);

    /// The fact of [Integer#compareUnsigned(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> compareUnsigned_int_int = UnsafeFacts.staticMethod(TOKEN, "compareUnsigned", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#compare(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> compare_int_int = UnsafeFacts.staticMethod(TOKEN, "compare", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#compress(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> compress_int_int = UnsafeFacts.staticMethod(TOKEN, "compress", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#decode(String)].
    public static final StaticMethodRef1<Integer, String> decode_String = UnsafeFacts.staticMethod(TOKEN, "decode", TOKEN, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#describeConstable()].
    public static final MethodRef0<Integer, Optional<Integer>> describeConstable = UnsafeFacts.method(TOKEN, "describeConstable", UnsafeFacts.<Optional<Integer>>finalClassToken(gen.facts.java.util.Optional_.Data.SHAPE, TokenArg.exact(TOKEN)), MemberTraits.FINAL);

    /// The fact of [Integer#divideUnsigned(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> divideUnsigned_int_int = UnsafeFacts.staticMethod(TOKEN, "divideUnsigned", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#doubleValue()].
    public static final MethodRef0<Integer, Double> doubleValue = UnsafeFacts.method(TOKEN, "doubleValue", PrimitiveToken.DOUBLE, MemberTraits.FINAL);

    /// The fact of [Integer#equals(Object)].
    public static final MethodRef1<Integer, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(Object_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Integer#expand(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> expand_int_int = UnsafeFacts.staticMethod(TOKEN, "expand", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#floatValue()].
    public static final MethodRef0<Integer, Float> floatValue = UnsafeFacts.method(TOKEN, "floatValue", PrimitiveToken.FLOAT, MemberTraits.FINAL);

    /// The fact of [Integer#getInteger(String)].
    public static final StaticMethodRef1<Integer, String> getInteger_String = UnsafeFacts.staticMethod(TOKEN, "getInteger", TOKEN, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Integer#getInteger(String, Integer)].
    public static final StaticMethodRef2<Integer, String, Integer> getInteger_String_Integer = UnsafeFacts.staticMethod(TOKEN, "getInteger", TOKEN, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), TOKEN, MemberTraits.FINAL);

    /// The fact of [Integer#getInteger(String, int)].
    public static final StaticMethodRef2<Integer, String, Int> getInteger_String_int = UnsafeFacts.staticMethod(TOKEN, "getInteger", TOKEN, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#hashCode()].
    public static final MethodRef0<Integer, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#hashCode(int)].
    public static final StaticMethodRef1<Int, Int> hashCode_int = UnsafeFacts.staticMethod(TOKEN, "hashCode", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#highestOneBit(int)].
    public static final StaticMethodRef1<Int, Int> highestOneBit_int = UnsafeFacts.staticMethod(TOKEN, "highestOneBit", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#intValue()].
    public static final MethodRef0<Integer, Int> intValue = UnsafeFacts.method(TOKEN, "intValue", PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#longValue()].
    public static final MethodRef0<Integer, Long> longValue = UnsafeFacts.method(TOKEN, "longValue", PrimitiveToken.LONG, MemberTraits.FINAL);

    /// The fact of [Integer#lowestOneBit(int)].
    public static final StaticMethodRef1<Int, Int> lowestOneBit_int = UnsafeFacts.staticMethod(TOKEN, "lowestOneBit", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#max(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> max_int_int = UnsafeFacts.staticMethod(TOKEN, "max", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#min(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> min_int_int = UnsafeFacts.staticMethod(TOKEN, "min", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#numberOfLeadingZeros(int)].
    public static final StaticMethodRef1<Int, Int> numberOfLeadingZeros_int = UnsafeFacts.staticMethod(TOKEN, "numberOfLeadingZeros", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#numberOfTrailingZeros(int)].
    public static final StaticMethodRef1<Int, Int> numberOfTrailingZeros_int = UnsafeFacts.staticMethod(TOKEN, "numberOfTrailingZeros", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#parseInt(CharSequence, int, int, int)].
    public static final StaticMethodRef4<Int, CharSequence, Int, Int, Int> parseInt_CharSequence_int_int_int = UnsafeFacts.staticMethod(TOKEN, "parseInt", PrimitiveToken.INT, UnsafeFacts.<CharSequence>interfaceToken(CharSequence_.Data.SHAPE), PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#parseInt(String)].
    public static final StaticMethodRef1<Int, String> parseInt_String = UnsafeFacts.staticMethod(TOKEN, "parseInt", PrimitiveToken.INT, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#parseInt(String, int)].
    public static final StaticMethodRef2<Int, String, Int> parseInt_String_int = UnsafeFacts.staticMethod(TOKEN, "parseInt", PrimitiveToken.INT, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#parseUnsignedInt(CharSequence, int, int, int)].
    public static final StaticMethodRef4<Int, CharSequence, Int, Int, Int> parseUnsignedInt_CharSequence_int_int_int = UnsafeFacts.staticMethod(TOKEN, "parseUnsignedInt", PrimitiveToken.INT, UnsafeFacts.<CharSequence>interfaceToken(CharSequence_.Data.SHAPE), PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#parseUnsignedInt(String)].
    public static final StaticMethodRef1<Int, String> parseUnsignedInt_String = UnsafeFacts.staticMethod(TOKEN, "parseUnsignedInt", PrimitiveToken.INT, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#parseUnsignedInt(String, int)].
    public static final StaticMethodRef2<Int, String, Int> parseUnsignedInt_String_int = UnsafeFacts.staticMethod(TOKEN, "parseUnsignedInt", PrimitiveToken.INT, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#remainderUnsigned(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> remainderUnsigned_int_int = UnsafeFacts.staticMethod(TOKEN, "remainderUnsigned", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#resolveConstantDesc(Lookup)].
    public static final MethodRef1<Integer, Integer, Lookup> resolveConstantDesc_MethodHandles_Lookup = UnsafeFacts.method(TOKEN, "resolveConstantDesc", TOKEN, UnsafeFacts.<Lookup>finalClassToken(gen.facts.java.lang.invoke.MethodHandles_Lookup_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Integer#reverseBytes(int)].
    public static final StaticMethodRef1<Int, Int> reverseBytes_int = UnsafeFacts.staticMethod(TOKEN, "reverseBytes", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#reverse(int)].
    public static final StaticMethodRef1<Int, Int> reverse_int = UnsafeFacts.staticMethod(TOKEN, "reverse", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#rotateLeft(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> rotateLeft_int_int = UnsafeFacts.staticMethod(TOKEN, "rotateLeft", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#rotateRight(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> rotateRight_int_int = UnsafeFacts.staticMethod(TOKEN, "rotateRight", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#shortValue()].
    public static final MethodRef0<Integer, Short> shortValue = UnsafeFacts.method(TOKEN, "shortValue", PrimitiveToken.SHORT, MemberTraits.FINAL);

    /// The fact of [Integer#signum(int)].
    public static final StaticMethodRef1<Int, Int> signum_int = UnsafeFacts.staticMethod(TOKEN, "signum", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#sum(int, int)].
    public static final StaticMethodRef2<Int, Int, Int> sum_int_int = UnsafeFacts.staticMethod(TOKEN, "sum", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toBinaryString(int)].
    public static final StaticMethodRef1<String, Int> toBinaryString_int = UnsafeFacts.staticMethod(TOKEN, "toBinaryString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toHexString(int)].
    public static final StaticMethodRef1<String, Int> toHexString_int = UnsafeFacts.staticMethod(TOKEN, "toHexString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toOctalString(int)].
    public static final StaticMethodRef1<String, Int> toOctalString_int = UnsafeFacts.staticMethod(TOKEN, "toOctalString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toString()].
    public static final MethodRef0<Integer, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Integer#toString(int)].
    public static final StaticMethodRef1<String, Int> toString_int = UnsafeFacts.staticMethod(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toString(int, int)].
    public static final StaticMethodRef2<String, Int, Int> toString_int_int = UnsafeFacts.staticMethod(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toUnsignedLong(int)].
    public static final StaticMethodRef1<Long, Int> toUnsignedLong_int = UnsafeFacts.staticMethod(TOKEN, "toUnsignedLong", PrimitiveToken.LONG, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toUnsignedString(int)].
    public static final StaticMethodRef1<String, Int> toUnsignedString_int = UnsafeFacts.staticMethod(TOKEN, "toUnsignedString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#toUnsignedString(int, int)].
    public static final StaticMethodRef2<String, Int, Int> toUnsignedString_int_int = UnsafeFacts.staticMethod(TOKEN, "toUnsignedString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Integer#valueOf(String)].
    public static final StaticMethodRef1<Integer, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#valueOf(String, int)].
    public static final StaticMethodRef2<Integer, String, Int> valueOf_String_int = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL.throwing(UnsafeFacts.<NumberFormatException>openClassToken(NumberFormatException_.Data.SHAPE)));

    /// The fact of [Integer#valueOf(int)].
    public static final StaticMethodRef1<Integer, Int> valueOf_int = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    private Integer_() {
    }
}
