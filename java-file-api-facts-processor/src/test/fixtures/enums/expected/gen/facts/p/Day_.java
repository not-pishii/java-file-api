package gen.facts.p;

import gen.facts.p.Day_.Canonical;
import gen.facts.p.Day_.Data;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
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
import p.Day;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Day.class, fingerprint = "1a6af4911516bda3b0fdaeb7024927b8a86e44a35a3326e3690189f18321f9b2", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Day_ {
    public static final class Data {
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Day_"), "1a6af4911516bda3b0fdaeb7024927b8a86e44a35a3326e3690189f18321f9b2", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Day"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Day"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Day"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Day"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("next"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("MON", "TUE", "WED"), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Day enum sealed=no\ntparams -\nsuperclasses java.lang.Enum; java.lang.Object\nsupertypes java.lang.Comparable<p.Day>; java.lang.Enum<p.Day>\nenum MON; TUE; WED\nmembers declared-public\nmember field static constant int COUNT = 3\nmember method final next() -> p.Day throws -\nmember method static of(int) -> p.Day throws -\nmember method static valueOf(java.lang.String) -> p.Day throws -\nmember method static values() -> p.Day[] throws -\ntable abstract -\ntable concrete clone(); compareTo(p.Day); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); next(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)\ntable static of(int); valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final EnumToken<Day> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    public static final EnumConstant<Day> MON = TOKEN.constant("MON");

    public static final EnumConstant<Day> TUE = TOKEN.constant("TUE");

    public static final EnumConstant<Day> WED = TOKEN.constant("WED");

    public static final StaticFieldRef<Int> COUNT = UnsafeFacts.constantField(TOKEN, "COUNT", PrimitiveToken.INT, 3);

    public static final MethodRef0<Day, Day> next = UnsafeFacts.method(TOKEN, "next", TOKEN, MemberTraits.FINAL);

    public static final StaticMethodRef1<Day, Int> of_int = UnsafeFacts.staticMethod(TOKEN, "of", TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    public static final StaticMethodRef1<Day, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final StaticMethodRef0<Day[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Day_() {
    }
}
