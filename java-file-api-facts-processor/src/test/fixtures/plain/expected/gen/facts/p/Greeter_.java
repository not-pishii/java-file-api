package gen.facts.p;

import gen.facts.p.Greeter_.Canonical;
import gen.facts.p.Greeter_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Byte;
import me.supcheg.javafile.facts.Prim.Char;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Float;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.Prim.Short;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Greeter;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Greeter.class, fingerprint = "f7a4c0659eccb863f6862b56a203e4b7712c095934ed798cc9260777e3adb20f", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Greeter_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Greeter_"), "f7a4c0659eccb863f6862b56a203e4b7712c095934ed798cc9260777e3adb20f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Greeter"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("arr", Param.fixed(ClassDesc.ofDescriptor("[[I")), Param.fixed(ClassDesc.ofDescriptor("[Lp/Greeter;"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("greet"), Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("hashCode"), Signature.of("log", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("main", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/String;"))), Signature.of("parse", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Greeter"), Signature.of("Greeter", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.ofDescriptor("[Z"))), Signature.of("Greeter", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Greeter open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember ctor(int, long, boolean[]) throws -\nmember ctor(java.lang.String) throws -\nmember field instance final int count\nmember field instance final java.lang.String name\nmember field instance mutable int mutable\nmember field instance mutable int[] numbers\nmember field instance mutable java.lang.String text\nmember field instance mutable java.lang.String[][] grid\nmember field static constant boolean BOOL = true\nmember field static constant byte BYTE = 127\nmember field static constant char CHAR = '\\u0027'\nmember field static constant double DOUBLE = 0.1\nmember field static constant double INFINITY = Infinity\nmember field static constant double NAN = NaN\nmember field static constant double NEGATIVE_INFINITY = -Infinity\nmember field static constant double NEGATIVE_ZERO = -0.0\nmember field static constant float FLOAT = 1.1f\nmember field static constant float FLOAT_INFINITY = Infinityf\nmember field static constant float FLOAT_NAN = NaNf\nmember field static constant int INT = -2147483648\nmember field static constant java.lang.String DEFAULT = \"say \\u0022hi\\u0022\\u000a\\u0009\\u005c \\u00e9\\u0001\"\nmember field static constant long LONG = 9007199254740993L\nmember field static constant short SHORT = -3\nmember field static final java.lang.Object OBJECT\nmember field static final java.lang.String NOT_CONSTANT\nmember field static mutable java.lang.String label\nmember field static mutable long counter\nmember method overridable arr(int[][], p.Greeter[]) -> int[] throws -\nmember method overridable greet() -> java.lang.String throws -\nmember method overridable greet(java.lang.String) -> java.lang.String throws -\nmember method overridable greet(java.lang.String, java.util.Locale) -> java.lang.String throws -\nmember method overridable log(java.lang.String) -> void throws -\nmember method static main(java.lang.String[]) -> void throws -\nmember method static parse(java.lang.String) -> int throws -\ntable abstract -\ntable concrete arr(int[][], p.Greeter[]); clone(); equals(java.lang.Object); finalize(); getClass(); greet(); greet(java.lang.String); greet(java.lang.String, java.util.Locale); hashCode(); log(java.lang.String); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static main(java.lang.String[]); parse(java.lang.String)\ntable ctor Greeter(); Greeter(int, long, boolean[]); Greeter(java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Greeter> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final StaticFieldRef<Bool> BOOL = UnsafeFacts.constantField(TOKEN, "BOOL", PrimitiveToken.BOOLEAN, true);

    public static final StaticFieldRef<Byte> BYTE = UnsafeFacts.constantField(TOKEN, "BYTE", PrimitiveToken.BYTE, (byte) 127);

    public static final StaticFieldRef<Char> CHAR = UnsafeFacts.constantField(TOKEN, "CHAR", PrimitiveToken.CHAR, (char) 39);

    public static final StaticFieldRef<String> DEFAULT = UnsafeFacts.constantField(TOKEN, "DEFAULT", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "say \"hi\"\n\t\\ \u00e9\u0001");

    public static final StaticFieldRef<Double> DOUBLE = UnsafeFacts.constantField(TOKEN, "DOUBLE", PrimitiveToken.DOUBLE, 0.1);

    public static final StaticFieldRef<Float> FLOAT = UnsafeFacts.constantField(TOKEN, "FLOAT", PrimitiveToken.FLOAT, (float) 1.100000023841858);

    public static final StaticFieldRef<Float> FLOAT_INFINITY = UnsafeFacts.constantField(TOKEN, "FLOAT_INFINITY", PrimitiveToken.FLOAT, java.lang.Float.intBitsToFloat(2139095040));

    public static final StaticFieldRef<Float> FLOAT_NAN = UnsafeFacts.constantField(TOKEN, "FLOAT_NAN", PrimitiveToken.FLOAT, java.lang.Float.intBitsToFloat(2143289344));

    public static final StaticFieldRef<Double> INFINITY = UnsafeFacts.constantField(TOKEN, "INFINITY", PrimitiveToken.DOUBLE, java.lang.Double.longBitsToDouble(9218868437227405312L));

    public static final StaticFieldRef<Int> INT = UnsafeFacts.constantField(TOKEN, "INT", PrimitiveToken.INT, -2147483648);

    public static final StaticFieldRef<Long> LONG = UnsafeFacts.constantField(TOKEN, "LONG", PrimitiveToken.LONG, 9007199254740993L);

    public static final StaticFieldRef<Double> NAN = UnsafeFacts.constantField(TOKEN, "NAN", PrimitiveToken.DOUBLE, java.lang.Double.longBitsToDouble(9221120237041090560L));

    public static final StaticFieldRef<Double> NEGATIVE_INFINITY = UnsafeFacts.constantField(TOKEN, "NEGATIVE_INFINITY", PrimitiveToken.DOUBLE, java.lang.Double.longBitsToDouble(-4503599627370496L));

    public static final StaticFieldRef<Double> NEGATIVE_ZERO = UnsafeFacts.constantField(TOKEN, "NEGATIVE_ZERO", PrimitiveToken.DOUBLE, -0.0);

    public static final StaticFieldRef<String> NOT_CONSTANT = UnsafeFacts.staticField(TOKEN, "NOT_CONSTANT", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    public static final StaticFieldRef<Object> OBJECT = UnsafeFacts.staticField(TOKEN, "OBJECT", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE));

    public static final StaticFieldRef<Short> SHORT = UnsafeFacts.constantField(TOKEN, "SHORT", PrimitiveToken.SHORT, (short) -3);

    public static final FieldRef<Greeter, Int> count = UnsafeFacts.field(TOKEN, "count", PrimitiveToken.INT);

    public static final MutableStaticFieldRef<Long> counter = UnsafeFacts.mutableStaticField(TOKEN, "counter", PrimitiveToken.LONG);

    public static final MutableFieldRef<Greeter, String[][]> grid = UnsafeFacts.mutableField(TOKEN, "grid", ArrayToken.of(ArrayToken.of(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))));

    public static final MutableStaticFieldRef<String> label = UnsafeFacts.mutableStaticField(TOKEN, "label", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    public static final MutableFieldRef<Greeter, Int> mutable = UnsafeFacts.mutableField(TOKEN, "mutable", PrimitiveToken.INT);

    public static final FieldRef<Greeter, String> name = UnsafeFacts.field(TOKEN, "name", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    public static final MutableFieldRef<Greeter, int[]> numbers = UnsafeFacts.mutableField(TOKEN, "numbers", PrimitiveToken.INT.array());

    public static final MutableFieldRef<Greeter, String> text = UnsafeFacts.mutableField(TOKEN, "text", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    public static final CtorRef0<Greeter> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final CtorRef1<Greeter, String> new_String = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final CtorRef3<Greeter, Int, Long, boolean[]> new_int_long_booleanArray = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, PrimitiveToken.LONG, PrimitiveToken.BOOLEAN.array(), MemberTraits.FINAL);

    public static final MethodRef2<Greeter, int[], int[][], Greeter[]> arr_intArrayArray_GreeterArray = UnsafeFacts.method(TOKEN, "arr", PrimitiveToken.INT.array(), ArrayToken.of(PrimitiveToken.INT.array()), ArrayToken.of(TOKEN), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Greeter, String> greet = UnsafeFacts.method(TOKEN, "greet", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef1<Greeter, String, String> greet_String = UnsafeFacts.method(TOKEN, "greet", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef2<Greeter, String, String, Locale> greet_String_Locale = UnsafeFacts.method(TOKEN, "greet", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Locale>finalClassToken(gen.facts.java.util.Locale_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<Greeter, String> log_String = UnsafeFacts.voidMethod(TOKEN, "log", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final VoidStaticMethodRef1<String[]> main_StringArray = UnsafeFacts.voidStaticMethod(TOKEN, "main", ArrayToken.of(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)), MemberTraits.FINAL);

    public static final StaticMethodRef1<Int, String> parse_String = UnsafeFacts.staticMethod(TOKEN, "parse", PrimitiveToken.INT, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Greeter_() {
    }
}
