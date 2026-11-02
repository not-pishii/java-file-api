package gen.facts.java.lang;

import gen.facts.java.lang.Short_.Canonical;
import gen.facts.java.lang.Short_.Data;
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

/// The token-only metamodel of [Short]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Short]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Short.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Short.class, fingerprint = "1ba3b731d2116f9cc7c6a43d00fd18ca053ea50a72a86dbd19e84f22099f48fa", complete = false, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Short_ {
    /// The shape of [Short] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Short] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Short_"), "1ba3b731d2116f9cc7c6a43d00fd18ca053ea50a72a86dbd19e84f22099f48fa", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Short"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Short")))), List.of()))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Short"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_short), Param.fixed(ConstantDescs.CD_short)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_short), Param.fixed(ConstantDescs.CD_short)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_short)), Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("reverseBytes", Param.fixed(ConstantDescs.CD_short)), Signature.of("toString", Param.fixed(ConstantDescs.CD_short)), Signature.of("toUnsignedInt", Param.fixed(ConstantDescs.CD_short)), Signature.of("toUnsignedLong", Param.fixed(ConstantDescs.CD_short)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_short))), Set.of(Signature.of("Short", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Short", Param.fixed(ConstantDescs.CD_short))), Set.of(), Map.ofEntries(Map.entry(Signature.of("Short", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("finalize"), List.of(new Thrown(ClassDesc.of("java.lang.Throwable"), List.of(ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.NumberFormatException"), List.of(ClassDesc.of("java.lang.IllegalArgumentException"), ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(new AnnotationUse(ClassDesc.of("jdk.internal.ValueBased"), List.of())), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Short], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Short].
        static final String TEXT = """
        javafile-facts-canonical 13
        type java.lang.Short final-class sealed=no nesting=top
        annotations @jdk.internal.ValueBased
        annotation -
        tparams -
        superclasses java.lang.Number; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<java.lang.Short>
        enum -
        record -
        members none
        table abstract -
        table concrete byteValue(); clone(); compareTo(java.lang.Short); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); longValue(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)
        table static compare(short, short); compareUnsigned(short, short); decode(java.lang.String); hashCode(short); parseShort(java.lang.String); parseShort(java.lang.String, int); reverseBytes(short); toString(short); toUnsignedInt(short); toUnsignedLong(short); valueOf(java.lang.String); valueOf(java.lang.String, int); valueOf(short)
        table ctor Short(java.lang.String); Short(short)
        """;

        private Canonical() {
        }
    }

    /// The token of [Short].
    public static final FinalClassToken<Short> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Short_() {
    }
}
