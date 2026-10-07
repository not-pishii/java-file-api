package gen.facts.java.lang;

import gen.facts.java.lang.Boolean_.Canonical;
import gen.facts.java.lang.Boolean_.Data;
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

/// The token-only metamodel of [Boolean]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Boolean]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Boolean.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Boolean.class, fingerprint = "ee6f9a927c06f65d485074f2f21aca51879c70e566050b98cff42290af8710e4", complete = false, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Boolean_ {
    /// The shape of [Boolean] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Boolean] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Boolean_"), "ee6f9a927c06f65d485074f2f21aca51879c70e566050b98cff42290af8710e4", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Boolean"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Boolean"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("booleanValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Boolean"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("getBoolean", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("logicalAnd", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("logicalOr", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("logicalXor", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("parseBoolean", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Boolean", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("Boolean", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Boolean], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Boolean].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.lang.Boolean final-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<java.lang.Boolean>
        enum -
        members none
        table abstract -
        table concrete booleanValue(); clone(); compareTo(java.lang.Boolean); describeConstable(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static compare(boolean, boolean); getBoolean(java.lang.String); hashCode(boolean); logicalAnd(boolean, boolean); logicalOr(boolean, boolean); logicalXor(boolean, boolean); parseBoolean(java.lang.String); toString(boolean); valueOf(boolean); valueOf(java.lang.String)
        table ctor Boolean(boolean); Boolean(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Boolean].
    public static final FinalClassToken<Boolean> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Boolean_() {
    }
}
