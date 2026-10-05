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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Day;

/// The full metamodel of [Day], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Day] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Enum_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Day.class, fingerprint = "c360ace331d7fb2ac48457558024e3f74846680d3436b4b1878e2482295ccf37", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Day_ {
    /// The shape of [Day] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Day] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Day_"), "c360ace331d7fb2ac48457558024e3f74846680d3436b4b1878e2482295ccf37", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Day"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Day"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Day"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Day"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("next"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("MON", "TUE", "WED"), false);

        private Data() {
        }
    }

    /// The canonical form of [Day], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Day].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Day enum sealed=no\ntparams -\nsuperclasses java.lang.Enum; java.lang.Object\ninterfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable\nsupertypes java.lang.Comparable<p.Day>; java.lang.Enum<p.Day>\nenum MON; TUE; WED\nmembers declared-public\nmember field static constant int COUNT = 3\nmember method final next() -> p.Day throws -\nmember method static of(int) -> p.Day throws -\nmember method static valueOf(java.lang.String) -> p.Day throws -\nmember method static values() -> p.Day[] throws -\ntable abstract -\ntable concrete clone(); compareTo(p.Day); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); next(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)\ntable static of(int); valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Day].
    public static final EnumToken<Day> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    /// The fact of [Day#MON].
    public static final EnumConstant<Day> MON = TOKEN.constant("MON");

    /// The fact of [Day#TUE].
    public static final EnumConstant<Day> TUE = TOKEN.constant("TUE");

    /// The fact of [Day#WED].
    public static final EnumConstant<Day> WED = TOKEN.constant("WED");

    /// The fact of [Day#COUNT].
    public static final StaticFieldRef<Int> COUNT = UnsafeFacts.constantField(TOKEN, "COUNT", PrimitiveToken.INT, 3);

    /// The fact of [Day#next()].
    public static final MethodRef0<Day, Day> next = UnsafeFacts.method(TOKEN, "next", TOKEN, MemberTraits.FINAL);

    /// The fact of [Day#of(int)].
    public static final StaticMethodRef1<Day, Int> of_int = UnsafeFacts.staticMethod(TOKEN, "of", TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Day#valueOf(String)].
    public static final StaticMethodRef1<Day, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Day#values()].
    public static final StaticMethodRef0<Day[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Day_() {
    }
}
