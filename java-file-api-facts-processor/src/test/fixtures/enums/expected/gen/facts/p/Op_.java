package gen.facts.p;

import gen.facts.p.Op_.Canonical;
import gen.facts.p.Op_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.EnumClass;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Op;

/// The full metamodel of [Op], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Op] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Enum_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Op.class, fingerprint = "2fac11873342d6b8297a0bb4690d5342adc04be715db1726281e5059da86dd55", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Op_ {
    /// The shape of [Op] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Op] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Op_"), "2fac11873342d6b8297a0bb4690d5342adc04be715db1726281e5059da86dd55", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Op"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Op"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Op"))))))), new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Op"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("twice", Param.fixed(ConstantDescs.CD_int)), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("ADD", "SUB"), true);

        private Data() {
        }
    }

    /// The canonical form of [Op], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Op].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Op enum sealed=yes
        tparams -
        superclasses java.lang.Enum; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<p.Op>; java.lang.Enum<p.Op>
        enum ADD; SUB
        members declared-accessible
        member method public abstract apply(int, int) -> int throws -
        member method public overridable twice(int) -> int throws -
        member method public static valueOf(java.lang.String) -> p.Op throws -
        member method public static values() -> p.Op[] throws -
        table abstract apply(int, int)
        table concrete clone(); compareTo(p.Op); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); twice(int); wait(); wait(long); wait(long, int)
        table static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Op].
    public static final EnumToken<Op> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    /// The fact of [Op#ADD].
    public static final EnumConstant<Op> ADD = TOKEN.constant("ADD");

    /// The fact of [Op#SUB].
    public static final EnumConstant<Op> SUB = TOKEN.constant("SUB");

    /// The fact of [Op#apply(int, int)].
    public static final MethodRef2<Op, Int, Int, Int> apply_int_int = UnsafeFacts.method(TOKEN, "apply", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [Op#twice(int)].
    public static final MethodRef1<Op, Int, Int> twice_int = UnsafeFacts.method(TOKEN, "twice", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [Op#valueOf(String)].
    public static final StaticMethodRef1<Op, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Op#values()].
    public static final StaticMethodRef0<Op[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Op_() {
    }
}
