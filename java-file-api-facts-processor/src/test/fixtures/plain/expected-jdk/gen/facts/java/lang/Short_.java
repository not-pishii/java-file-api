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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [Short]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Short]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Short.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Short.class, fingerprint = "3b557b1cd940de1f8a406949ec7eebcd53dea984d501d1d879e32ae76b416a75", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Short_ {
    /// The shape of [Short] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Short] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Short_"), "3b557b1cd940de1f8a406949ec7eebcd53dea984d501d1d879e32ae76b416a75", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Short"), List.of(), List.of(ClassDesc.of("java.lang.Number"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Short"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("byteValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Short"))), Signature.of("describeConstable"), Signature.of("doubleValue"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("floatValue"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("intValue"), Signature.of("longValue"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("shortValue"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_short), Param.fixed(ConstantDescs.CD_short)), Signature.of("compareUnsigned", Param.fixed(ConstantDescs.CD_short), Param.fixed(ConstantDescs.CD_short)), Signature.of("decode", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_short)), Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("parseShort", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("reverseBytes", Param.fixed(ConstantDescs.CD_short)), Signature.of("toString", Param.fixed(ConstantDescs.CD_short)), Signature.of("toUnsignedInt", Param.fixed(ConstantDescs.CD_short)), Signature.of("toUnsignedLong", Param.fixed(ConstantDescs.CD_short)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_short))), Set.of(Signature.of("Short", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Short", Param.fixed(ConstantDescs.CD_short)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Short], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Short].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.lang.Short final-class sealed=no\ntparams -\nsuperclasses java.lang.Number; java.lang.Object\ninterfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable\nsupertypes java.lang.Comparable<java.lang.Short>\nenum -\nmembers none\ntable abstract -\ntable concrete byteValue(); clone(); compareTo(java.lang.Short); describeConstable(); doubleValue(); equals(java.lang.Object); finalize(); floatValue(); getClass(); hashCode(); intValue(); longValue(); notify(); notifyAll(); shortValue(); toString(); wait(); wait(long); wait(long, int)\ntable static compare(short, short); compareUnsigned(short, short); decode(java.lang.String); hashCode(short); parseShort(java.lang.String); parseShort(java.lang.String, int); reverseBytes(short); toString(short); toUnsignedInt(short); toUnsignedLong(short); valueOf(java.lang.String); valueOf(java.lang.String, int); valueOf(short)\ntable ctor Short(java.lang.String); Short(short)\n";

        private Canonical() {
        }
    }

    /// The token of [Short].
    public static final FinalClassToken<Short> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Short_() {
    }
}
