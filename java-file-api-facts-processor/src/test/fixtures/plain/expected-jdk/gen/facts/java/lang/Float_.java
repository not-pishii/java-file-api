package gen.facts.java.lang;

import gen.facts.java.lang.Float_.Canonical;
import gen.facts.java.lang.Float_.Data;
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

/// The token-only metamodel of [Float]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Float]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Float.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Float.class, fingerprint = "551807d9ba33e0d2d4f16198953a70446d9eb141324799c44dfe9eaddccf0a58", complete = false, format = 19)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Float_ {
    /// The shape of [Float] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Float] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Float_"), "551807d9ba33e0d2d4f16198953a70446d9eb141324799c44dfe9eaddccf0a58", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Float"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable"), ClassDesc.of("java.lang.constant.ConstantDesc")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Float")))), List.of()))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Float"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("isInfinite"), Signature.of("isNaN"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("float16ToFloat", Param.fixed(ConstantDescs.CD_short)), Signature.of("floatToFloat16", Param.fixed(ConstantDescs.CD_float)), Signature.of("floatToIntBits", Param.fixed(ConstantDescs.CD_float)), Signature.of("floatToRawIntBits", Param.fixed(ConstantDescs.CD_float)), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_float)), Signature.of("intBitsToFloat", Param.fixed(ConstantDescs.CD_int)), Signature.of("isFinite", Param.fixed(ConstantDescs.CD_float)), Signature.of("isInfinite", Param.fixed(ConstantDescs.CD_float)), Signature.of("isNaN", Param.fixed(ConstantDescs.CD_float)), Signature.of("max", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("min", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("parseFloat", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sum", Param.fixed(ConstantDescs.CD_float), Param.fixed(ConstantDescs.CD_float)), Signature.of("toHexString", Param.fixed(ConstantDescs.CD_float)), Signature.of("toString", Param.fixed(ConstantDescs.CD_float)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_float)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Float", Param.fixed(ConstantDescs.CD_double)), Signature.of("Float", Param.fixed(ConstantDescs.CD_float)), Signature.of("Float", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(), Map.ofEntries(Map.entry(Signature.of("Float", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("finalize"), List.of(new Thrown(ClassDesc.of("java.lang.Throwable"), List.of(ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("parseFloat", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(new AnnotationUse(ClassDesc.of("jdk.internal.ValueBased"), List.of())));

        private Data() {
        }
    }

    /// The canonical form of [Float], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Float].
        static final String TEXT = """
        javafile-facts-canonical 12
        type java.lang.Float final-class sealed=no nesting=top
        annotations @jdk.internal.ValueBased
        annotation -
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable; java.lang.constant.ConstantDesc
        supertypes java.lang.Comparable<java.lang.Float>
        enum -
        members none
        table abstract -
        table concrete byteValue(); clone(); compareTo(java.lang.Float); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); isInfinite(); isNaN(); longValue(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); shortValue(); toString(); wait(); wait(long); wait(long, int)
        table static compare(float, float); float16ToFloat(short); floatToFloat16(float); floatToIntBits(float); floatToRawIntBits(float); hashCode(float); intBitsToFloat(int); isFinite(float); isInfinite(float); isNaN(float); max(float, float); min(float, float); parseFloat(java.lang.String); sum(float, float); toHexString(float); toString(float); valueOf(float); valueOf(java.lang.String)
        table ctor Float(double); Float(float); Float(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Float].
    public static final FinalClassToken<Float> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Float_() {
    }
}
