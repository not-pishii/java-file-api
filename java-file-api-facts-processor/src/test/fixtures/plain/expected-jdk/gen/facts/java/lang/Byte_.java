package gen.facts.java.lang;

import gen.facts.java.lang.Byte_.Canonical;
import gen.facts.java.lang.Byte_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Byte]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Byte]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Byte.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Byte.class, fingerprint = "9c3e074c8f3820babaa6d1478c6feb485daf38be8af616def7f1403691659b6e", complete = false, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Byte_ {
    /// The shape of [Byte] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Byte] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Byte_"), "9c3e074c8f3820babaa6d1478c6feb485daf38be8af616def7f1403691659b6e", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Byte"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Byte")))), List.of()))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Byte"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ConstantDescs.CD_byte)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ConstantDescs.CD_byte)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_byte)), Signature.of("parseByte", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseByte", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("toString", Param.fixed(ConstantDescs.CD_byte)), Signature.of("toUnsignedInt", Param.fixed(ConstantDescs.CD_byte)), Signature.of("toUnsignedLong", Param.fixed(ConstantDescs.CD_byte)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_byte)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("Byte", Param.fixed(ConstantDescs.CD_byte)), Signature.of("Byte", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(), Map.ofEntries(Map.entry(Signature.of("Byte", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("finalize"), List.of(new Thrown(ClassDesc.of("java.lang.Throwable"), List.of(ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("parseByte", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("parseByte", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(new AnnotationUse(ClassDesc.of("jdk.internal.ValueBased"), List.of())), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Byte], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Byte].
        static final String TEXT = """
        javafile-facts-canonical 13
        type java.lang.Byte final-class sealed=no nesting=top
        annotations @jdk.internal.ValueBased
        annotation -
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<java.lang.Byte>
        enum -
        record -
        members none
        table abstract -
        table concrete byteValue(); clone(); compareTo(java.lang.Byte); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); longValue(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)
        table static compare(byte, byte); compareUnsigned(byte, byte); decode(java.lang.String); hashCode(byte); parseByte(java.lang.String); parseByte(java.lang.String, int); toString(byte); toUnsignedInt(byte); toUnsignedLong(byte); valueOf(byte); valueOf(java.lang.String); valueOf(java.lang.String, int)
        table ctor Byte(byte); Byte(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Byte].
    public static final FinalClassToken<Byte> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Byte_() {
    }
}
