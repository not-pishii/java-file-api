package gen.facts.p;

import gen.facts.p.Outer_E_.Canonical;
import gen.facts.p.Outer_E_.Data;
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
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
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
import p.Outer.E;

/// The full metamodel of [E], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [E] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Enum_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = E.class, fingerprint = "82597aed2e94d7df9f7978a7ec10f9380b0b40953fa4e7c36abdbddb40ca7f21", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_E_ {
    /// The shape of [E] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [E] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_E_"), "82597aed2e94d7df9f7978a7ec10f9380b0b40953fa4e7c36abdbddb40ca7f21", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Outer$E"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Outer$E"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Outer$E"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Outer$E"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("A", "B"), false);

        private Data() {
        }
    }

    /// The canonical form of [E], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [E].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Outer$E enum sealed=no\ntparams -\nsuperclasses java.lang.Enum; java.lang.Object\ninterfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable\nsupertypes java.lang.Comparable<p.Outer$E>; java.lang.Enum<p.Outer$E>\nenum A; B\nmembers declared-public\nmember method static valueOf(java.lang.String) -> p.Outer$E throws -\nmember method static values() -> p.Outer$E[] throws -\ntable abstract -\ntable concrete clone(); compareTo(p.Outer$E); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)\ntable static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [E].
    public static final EnumToken<E> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    /// The fact of [E#A].
    public static final EnumConstant<E> A = TOKEN.constant("A");

    /// The fact of [E#B].
    public static final EnumConstant<E> B = TOKEN.constant("B");

    /// The fact of [E#valueOf(String)].
    public static final StaticMethodRef1<E, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [E#values()].
    public static final StaticMethodRef0<E[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Outer_E_() {
    }
}
