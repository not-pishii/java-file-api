package gen.facts.p;

import gen.facts.p.Mode_.Canonical;
import gen.facts.p.Mode_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.EnumClass;
import me.supcheg.javafile.facts.EnumToken;
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
import p.Mode;

/// The token-only metamodel of [Mode]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Mode]: it is only mentioned in the signatures of [p.Plan]. For the facts of its members add `Mode.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mode.class, fingerprint = "2eb996189a530ecafef6314ad9b3479b1a57025ffeae5f690851c0c3c962f98f", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Mode_ {
    /// The shape of [Mode] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Mode] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mode_"), "2eb996189a530ecafef6314ad9b3479b1a57025ffeae5f690851c0c3c962f98f", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Mode"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Mode"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Mode"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Mode"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("FAST", "SLOW"), false);

        private Data() {
        }
    }

    /// The canonical form of [Mode], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Mode].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Mode enum sealed=no
        tparams -
        superclasses java.lang.Enum; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<p.Mode>; java.lang.Enum<p.Mode>
        enum FAST; SLOW
        members none
        table abstract -
        table concrete clone(); compareTo(p.Mode); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)
        table static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Mode].
    public static final EnumToken<Mode> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    private Mode_() {
    }
}
