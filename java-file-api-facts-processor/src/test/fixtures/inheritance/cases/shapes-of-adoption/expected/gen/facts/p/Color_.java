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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Color;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Color.class, fingerprint = "521ea371e20f45f60ede95eb914913a1c82317af48eac77d07385d98c779cb7b", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Color_ {
    public static final class Data {
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Color_"), "521ea371e20f45f60ede95eb914913a1c82317af48eac77d07385d98c779cb7b", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Color"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Color"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Color"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Color"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("label"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("RED", "GREEN"), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Color enum sealed=no\ntparams -\nsuperclasses java.lang.Enum; java.lang.Object\nsupertypes java.lang.Comparable<p.Color>; java.lang.Enum<p.Color>\nenum RED; GREEN\nmembers declared-public\nmember field static constant java.lang.String NAME = \"name\"\nmember method final label() -> java.lang.String throws -\nmember method static valueOf(java.lang.String) -> p.Color throws -\nmember method static values() -> p.Color[] throws -\ntable abstract -\ntable concrete clone(); compareTo(p.Color); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); label(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)\ntable static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final EnumToken<Color> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    public static final EnumConstant<Color> RED = TOKEN.constant("RED");

    public static final EnumConstant<Color> GREEN = TOKEN.constant("GREEN");

    public static final StaticFieldRef<String> NAME = UnsafeFacts.constantField(TOKEN, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "name");

    public static final MethodRef0<Color, String> label = UnsafeFacts.method(TOKEN, "label", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final StaticMethodRef1<Color, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final StaticMethodRef0<Color[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Color_() {
    }
}
