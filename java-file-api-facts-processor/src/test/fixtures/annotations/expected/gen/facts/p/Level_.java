package gen.facts.p;

import gen.facts.p.Level_.Canonical;
import gen.facts.p.Level_.Data;
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
import p.Level;

/// The full metamodel of [Level], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Level] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Enum_].
///
/// No generated class extends or implements [Level]: it is an enum class, which no class extends.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Level.class, fingerprint = "b6f073a7519201b6eec45a13a25dc288720607a0abbb3c6da97de6895302f53a", complete = true, format = 20)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Level_ {
    /// The shape of [Level] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Level] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Level_"), "b6f073a7519201b6eec45a13a25dc288720607a0abbb3c6da97de6895302f53a", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Level"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Level")))), List.of()), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Level")))), List.of()))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Level"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of("LOW", "HIGH"), false, List.of(), List.of(), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Level], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Level].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Level enum sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses java.lang.Enum; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<p.Level>; java.lang.Enum<p.Level>
        enum LOW; HIGH
        record -
        members declared-accessible
        member method public static valueOf(java.lang.String) -> p.Level throws -
        member method public static values() -> p.Level[] throws -
        table abstract -
        table concrete clone(); compareTo(p.Level); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)
        table static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Level].
    public static final EnumToken<Level> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    /// The fact of [Level#LOW].
    public static final EnumConstant<Level> LOW = TOKEN.constant("LOW");

    /// The fact of [Level#HIGH].
    public static final EnumConstant<Level> HIGH = TOKEN.constant("HIGH");

    /// The fact of [Level#valueOf(String)].
    public static final StaticMethodRef1<Level, String> valueOf_String = UnsafeInvocables.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Level#values()].
    public static final StaticMethodRef0<Level[]> values = UnsafeInvocables.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Level_() {
    }
}
