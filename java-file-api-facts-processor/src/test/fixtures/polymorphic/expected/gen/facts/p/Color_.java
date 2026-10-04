package gen.facts.p;

import gen.facts.p.Color_.Canonical;
import gen.facts.p.Color_.Data;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Color;

/// The full metamodel of [Color], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Color] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Enum_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Color.class, fingerprint = "3e244f7af59ee3b45b8edc46f22aba87e15d8d337bd94e054f965c201078f331", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Color_ {
    /// The shape of [Color] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Color] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Color_"), "3e244f7af59ee3b45b8edc46f22aba87e15d8d337bd94e054f965c201078f331", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Color"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Color"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Color"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Color"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("RED"), false);

        private Data() {
        }
    }

    /// The canonical form of [Color], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Color].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Color enum sealed=no\ntparams -\nsuperclasses java.lang.Enum; java.lang.Object\nsupertypes java.lang.Comparable<p.Color>; java.lang.Enum<p.Color>\nenum RED\nmembers declared-public\nmember method static valueOf(java.lang.String) -> p.Color throws -\nmember method static values() -> p.Color[] throws -\ntable abstract -\ntable concrete clone(); compareTo(p.Color); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)\ntable static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Color].
    public static final EnumToken<Color> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    /// The fact of [Color#RED].
    public static final EnumConstant<Color> RED = TOKEN.constant("RED");

    /// The fact of [Color#valueOf(String)].
    public static final StaticMethodRef1<Color, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Color#values()].
    public static final StaticMethodRef0<Color[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Color_() {
    }
}
