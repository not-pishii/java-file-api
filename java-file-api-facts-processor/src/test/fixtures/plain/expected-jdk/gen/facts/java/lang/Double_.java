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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Double]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Double]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Double.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Double.class, fingerprint = "9b8a47ee8666a22a5e7b92735d1cf58893b5779e1b8d3d20347a08071ec66b99", complete = false, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Double_ {
    /// The shape of [Double] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Double] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Double_"), "9b8a47ee8666a22a5e7b92735d1cf58893b5779e1b8d3d20347a08071ec66b99", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Double"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable"), ClassDesc.of("java.lang.constant.ConstantDesc")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Double"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Double"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("isInfinite"), Signature.of("isNaN"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("doubleToLongBits", Param.fixed(ConstantDescs.CD_double)), Signature.of("doubleToRawLongBits", Param.fixed(ConstantDescs.CD_double)), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_double)), Signature.of("isFinite", Param.fixed(ConstantDescs.CD_double)), Signature.of("isInfinite", Param.fixed(ConstantDescs.CD_double)), Signature.of("isNaN", Param.fixed(ConstantDescs.CD_double)), Signature.of("longBitsToDouble", Param.fixed(ConstantDescs.CD_long)), Signature.of("max", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("min", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("parseDouble", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sum", Param.fixed(ConstantDescs.CD_double), Param.fixed(ConstantDescs.CD_double)), Signature.of("toHexString", Param.fixed(ConstantDescs.CD_double)), Signature.of("toString", Param.fixed(ConstantDescs.CD_double)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_double)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Double", Param.fixed(ConstantDescs.CD_double)), Signature.of("Double", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Double], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Double].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.Double final-class sealed=no
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable; java.lang.constant.ConstantDesc
        supertypes java.lang.Comparable<java.lang.Double>
        enum -
        members none
        table abstract -
        table concrete byteValue(); clone(); compareTo(java.lang.Double); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); isInfinite(); isNaN(); longValue(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); shortValue(); toString(); wait(); wait(long); wait(long, int)
        table static compare(double, double); doubleToLongBits(double); doubleToRawLongBits(double); hashCode(double); isFinite(double); isInfinite(double); isNaN(double); longBitsToDouble(long); max(double, double); min(double, double); parseDouble(java.lang.String); sum(double, double); toHexString(double); toString(double); valueOf(double); valueOf(java.lang.String)
        table ctor Double(double); Double(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Double].
    public static final FinalClassToken<Double> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Double_() {
    }
}
