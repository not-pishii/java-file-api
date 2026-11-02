package gen.facts.p;

import gen.facts.p.Phase_.Canonical;
import gen.facts.p.Phase_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.EnumClass;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Phase;

/// The full metamodel of [Phase], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Phase] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Enum_].
///
/// No generated class extends or implements [Phase]: it is an enum class, which no class extends.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Phase.class, fingerprint = "ce7ed1949854d566512625064fb2c2115b9f48e8d3007ef0d4c9bc64e11ce0b5", complete = true, format = 20)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Phase_ {
    /// The shape of [Phase] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Phase] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Phase_"), "ce7ed1949854d566512625064fb2c2115b9f48e8d3007ef0d4c9bc64e11ce0b5", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Phase"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Phase")))), List.of()), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Phase")))), List.of()))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Phase"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of("NEW", "OLD"), false, List.of(), List.of(), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Phase], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Phase].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Phase enum sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses java.lang.Enum; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<p.Phase>; java.lang.Enum<p.Phase>
        enum NEW; OLD
        record -
        members declared-accessible
        member method public static valueOf(java.lang.String) -> p.Phase throws -
        member method public static values() -> p.Phase[] throws -
        table abstract -
        table concrete clone(); compareTo(p.Phase); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)
        table static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Phase].
    public static final EnumToken<Phase> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    /// The fact of [Phase#NEW].
    public static final EnumConstant<Phase> NEW = TOKEN.constant("NEW");

    /// The fact of [Phase#valueOf(String)].
    public static final StaticMethodRef1<Phase, String> valueOf_String = UnsafeInvocables.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Phase#values()].
    public static final StaticMethodRef0<Phase[]> values = UnsafeInvocables.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Phase_() {
    }
}
