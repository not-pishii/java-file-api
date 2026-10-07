package gen.facts.p;

import gen.facts.p.Greeter_.Canonical;
import gen.facts.p.Greeter_.Data;
import gen.facts.p.Greeter_.Data.Inherited;
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
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Greeter;

/// The full metamodel of [Greeter], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Greeter] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Greeter.class, fingerprint = "76dca1b1dbaa7ae26c70a7d42159ed8099ff74cd8bc119800bb758f3e703ed5d", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Greeter_ {
    /// The shape of [Greeter] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Greeter] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Greeter_"), "76dca1b1dbaa7ae26c70a7d42159ed8099ff74cd8bc119800bb758f3e703ed5d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Greeter"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("arr", Param.fixed(ClassDesc.ofDescriptor("[[I")), Param.fixed(ClassDesc.ofDescriptor("[Lp/Greeter;"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("greet"), Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("hashCode"), Signature.of("log", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("main", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/String;"))), Signature.of("parse", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Greeter"), Signature.of("Greeter", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.ofDescriptor("[Z"))), Signature.of("Greeter", Param.fixed(ClassDesc.of("java.lang.String"))))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Greeter] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Greeter] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17()), List.of(c0(), c1(), c2()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Greeter"), Signature.of("arr", Param.fixed(ClassDesc.ofDescriptor("[[I")), Param.fixed(ClassDesc.ofDescriptor("[Lp/Greeter;"))), List.of(), List.of(Types.array(Types.array(PrimitiveTypeRef.INT)), Types.array(Types.of(ClassDesc.of("p.Greeter")))), Arity.FIXED, new Of(Types.array(PrimitiveTypeRef.INT)), List.of(), Set.of(List.of(ClassDesc.ofDescriptor("[[I"), ClassDesc.ofDescriptor("[Lp/Greeter;"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Greeter"), Signature.of("greet"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Greeter"), Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Greeter"), Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.util.Locale"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String")), Types.of(ClassDesc.of("java.util.Locale"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.String"), ClassDesc.of("java.util.Locale"))), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Greeter"), Signature.of("log", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Greeter"), Signature.of("main", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/String;"))), List.of(), List.of(Types.array(Types.of(ClassDesc.of("java.lang.String")))), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.ofDescriptor("[Ljava/lang/String;"))), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Greeter"), Signature.of("parse", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m16() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m17() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Greeter"), List.of(), List.of(), Arity.FIXED, List.of());
            }

            private static Constructor c1() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Greeter", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.ofDescriptor("[Z"))), List.of(), List.of(PrimitiveTypeRef.INT, PrimitiveTypeRef.LONG, Types.array(PrimitiveTypeRef.BOOLEAN)), Arity.FIXED, List.of());
            }

            private static Constructor c2() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Greeter", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Greeter], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Greeter].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Greeter open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member ctor public (int, long, boolean[]) throws -
        member ctor public (java.lang.String) throws -
        member field public instance final int count
        member field public instance final java.lang.String name
        member field public instance mutable int mutable
        member field public instance mutable int[] numbers
        member field public instance mutable java.lang.String text
        member field public instance mutable java.lang.String[][] grid
        member field public static constant boolean BOOL = true
        member field public static constant byte BYTE = 127
        member field public static constant char CHAR = '\\u0027'
        member field public static constant double DOUBLE = 0.1
        member field public static constant double INFINITY = Infinity
        member field public static constant double NAN = NaN
        member field public static constant double NEGATIVE_INFINITY = -Infinity
        member field public static constant double NEGATIVE_ZERO = -0.0
        member field public static constant float FLOAT = 1.1f
        member field public static constant float FLOAT_INFINITY = Infinityf
        member field public static constant float FLOAT_NAN = NaNf
        member field public static constant int INT = -2147483648
        member field public static constant java.lang.String DEFAULT = "say \\u0022hi\\u0022\\u000a\\u0009\\u005c \\u00e9\\u0001"
        member field public static constant long LONG = 9007199254740993L
        member field public static constant short SHORT = -3
        member field public static final java.lang.Object OBJECT
        member field public static final java.lang.String NOT_CONSTANT
        member field public static mutable java.lang.String label
        member field public static mutable long counter
        member method public overridable arr(int[][], p.Greeter[]) -> int[] throws -
        member method public overridable greet() -> java.lang.String throws -
        member method public overridable greet(java.lang.String) -> java.lang.String throws -
        member method public overridable greet(java.lang.String, java.util.Locale) -> java.lang.String throws -
        member method public overridable log(java.lang.String) -> void throws -
        member method public static main(java.lang.String[]) -> void throws -
        member method public static parse(java.lang.String) -> int throws -
        inherit ctor public () throws -
        inherit ctor public (int, long, boolean[]) throws -
        inherit ctor public (java.lang.String) throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Greeter arr(int[][], p.Greeter[]) -> int[] throws - erased (int[][], p.Greeter[]) overrides -
        inherit method public concrete p.Greeter greet() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Greeter greet(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        inherit method public concrete p.Greeter greet(java.lang.String, java.util.Locale) -> java.lang.String throws - erased (java.lang.String, java.util.Locale) overrides -
        inherit method public concrete p.Greeter log(java.lang.String) -> void throws - erased (java.lang.String) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Greeter main(java.lang.String[]) -> void throws - erased (java.lang.String[]) overrides -
        inherit method public static p.Greeter parse(java.lang.String) -> int throws - erased (java.lang.String) overrides -
        table abstract -
        table concrete arr(int[][], p.Greeter[]); clone(); equals(java.lang.Object); finalize(); getClass(); greet(); greet(java.lang.String); greet(java.lang.String, java.util.Locale); hashCode(); log(java.lang.String); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static main(java.lang.String[]); parse(java.lang.String)
        table ctor Greeter(); Greeter(int, long, boolean[]); Greeter(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Greeter].
    public static final OpenClassToken<Greeter> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Greeter#BOOL].
    public static final StaticFieldRef<Bool> BOOL = UnsafeFacts.constantField(TOKEN, "BOOL", PrimitiveToken.BOOLEAN, true);

    /// The fact of [Greeter#BYTE].
    public static final StaticFieldRef<Byte> BYTE = UnsafeFacts.constantField(TOKEN, "BYTE", PrimitiveToken.BYTE, (byte) 127);

    /// The fact of [Greeter#CHAR].
    public static final StaticFieldRef<Char> CHAR = UnsafeFacts.constantField(TOKEN, "CHAR", PrimitiveToken.CHAR, (char) 39);

    /// The fact of [Greeter#DEFAULT].
    public static final StaticFieldRef<String> DEFAULT = UnsafeFacts.constantField(TOKEN, "DEFAULT", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "say \"hi\"\n\t\\ \u00e9\u0001");

    /// The fact of [Greeter#DOUBLE].
    public static final StaticFieldRef<Double> DOUBLE = UnsafeFacts.constantField(TOKEN, "DOUBLE", PrimitiveToken.DOUBLE, 0.1);

    /// The fact of [Greeter#FLOAT].
    public static final StaticFieldRef<Float> FLOAT = UnsafeFacts.constantField(TOKEN, "FLOAT", PrimitiveToken.FLOAT, (float) 1.100000023841858);

    /// The fact of [Greeter#FLOAT_INFINITY].
    public static final StaticFieldRef<Float> FLOAT_INFINITY = UnsafeFacts.constantField(TOKEN, "FLOAT_INFINITY", PrimitiveToken.FLOAT, java.lang.Float.intBitsToFloat(2139095040));

    /// The fact of [Greeter#FLOAT_NAN].
    public static final StaticFieldRef<Float> FLOAT_NAN = UnsafeFacts.constantField(TOKEN, "FLOAT_NAN", PrimitiveToken.FLOAT, java.lang.Float.intBitsToFloat(2143289344));

    /// The fact of [Greeter#INFINITY].
    public static final StaticFieldRef<Double> INFINITY = UnsafeFacts.constantField(TOKEN, "INFINITY", PrimitiveToken.DOUBLE, java.lang.Double.longBitsToDouble(9218868437227405312L));

    /// The fact of [Greeter#INT].
    public static final StaticFieldRef<Int> INT = UnsafeFacts.constantField(TOKEN, "INT", PrimitiveToken.INT, -2147483648);

    /// The fact of [Greeter#LONG].
    public static final StaticFieldRef<Long> LONG = UnsafeFacts.constantField(TOKEN, "LONG", PrimitiveToken.LONG, 9007199254740993L);

    /// The fact of [Greeter#NAN].
    public static final StaticFieldRef<Double> NAN = UnsafeFacts.constantField(TOKEN, "NAN", PrimitiveToken.DOUBLE, java.lang.Double.longBitsToDouble(9221120237041090560L));

    /// The fact of [Greeter#NEGATIVE_INFINITY].
    public static final StaticFieldRef<Double> NEGATIVE_INFINITY = UnsafeFacts.constantField(TOKEN, "NEGATIVE_INFINITY", PrimitiveToken.DOUBLE, java.lang.Double.longBitsToDouble(-4503599627370496L));

    /// The fact of [Greeter#NEGATIVE_ZERO].
    public static final StaticFieldRef<Double> NEGATIVE_ZERO = UnsafeFacts.constantField(TOKEN, "NEGATIVE_ZERO", PrimitiveToken.DOUBLE, -0.0);

    /// The fact of [Greeter#NOT_CONSTANT].
    public static final StaticFieldRef<String> NOT_CONSTANT = UnsafeFacts.staticField(TOKEN, "NOT_CONSTANT", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Greeter#OBJECT].
    public static final StaticFieldRef<Object> OBJECT = UnsafeFacts.staticField(TOKEN, "OBJECT", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE));

    /// The fact of [Greeter#SHORT].
    public static final StaticFieldRef<Short> SHORT = UnsafeFacts.constantField(TOKEN, "SHORT", PrimitiveToken.SHORT, (short) -3);

    /// The fact of [Greeter#count].
    public static final FieldRef<Greeter, Int> count = UnsafeFacts.field(TOKEN, "count", PrimitiveToken.INT);

    /// The fact of [Greeter#counter].
    public static final MutableStaticFieldRef<Long> counter = UnsafeFacts.mutableStaticField(TOKEN, "counter", PrimitiveToken.LONG);

    /// The fact of [Greeter#grid].
    public static final MutableFieldRef<Greeter, String[][]> grid = UnsafeFacts.mutableField(TOKEN, "grid", ArrayToken.of(ArrayToken.of(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))));

    /// The fact of [Greeter#label].
    public static final MutableStaticFieldRef<String> label = UnsafeFacts.mutableStaticField(TOKEN, "label", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Greeter#mutable].
    public static final MutableFieldRef<Greeter, Int> mutable = UnsafeFacts.mutableField(TOKEN, "mutable", PrimitiveToken.INT);

    /// The fact of [Greeter#name].
    public static final FieldRef<Greeter, String> name = UnsafeFacts.field(TOKEN, "name", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Greeter#numbers].
    public static final MutableFieldRef<Greeter, int[]> numbers = UnsafeFacts.mutableField(TOKEN, "numbers", PrimitiveToken.INT.array());

    /// The fact of [Greeter#text].
    public static final MutableFieldRef<Greeter, String> text = UnsafeFacts.mutableField(TOKEN, "text", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Greeter#Greeter()].
    public static final CtorRef0<Greeter> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Greeter#Greeter(String)].
    public static final CtorRef1<Greeter, String> new_String = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Greeter#Greeter(int, long, boolean\[\])].
    public static final CtorRef3<Greeter, Int, Long, boolean[]> new_int_long_booleanArray = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, PrimitiveToken.LONG, PrimitiveToken.BOOLEAN.array(), MemberTraits.FINAL);

    /// The fact of [Greeter#arr(int\[\]\[\], Greeter\[\])].
    public static final MethodRef2<Greeter, int[], int[][], Greeter[]> arr_intArrayArray_GreeterArray = UnsafeFacts.method(TOKEN, "arr", PrimitiveToken.INT.array(), ArrayToken.of(PrimitiveToken.INT.array()), ArrayToken.of(TOKEN), MemberTraits.OVERRIDABLE);

    /// The fact of [Greeter#greet()].
    public static final MethodRef0<Greeter, String> greet = UnsafeFacts.method(TOKEN, "greet", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Greeter#greet(String)].
    public static final MethodRef1<Greeter, String, String> greet_String = UnsafeFacts.method(TOKEN, "greet", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Greeter#greet(String, Locale)].
    public static final MethodRef2<Greeter, String, String, Locale> greet_String_Locale = UnsafeFacts.method(TOKEN, "greet", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Locale>finalClassToken(gen.facts.java.util.Locale_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Greeter#log(String)].
    public static final VoidMethodRef1<Greeter, String> log_String = UnsafeFacts.voidMethod(TOKEN, "log", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Greeter#main(String\[\])].
    public static final VoidStaticMethodRef1<String[]> main_StringArray = UnsafeFacts.voidStaticMethod(TOKEN, "main", ArrayToken.of(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)), MemberTraits.FINAL);

    /// The fact of [Greeter#parse(String)].
    public static final StaticMethodRef1<Int, String> parse_String = UnsafeFacts.staticMethod(TOKEN, "parse", PrimitiveToken.INT, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Greeter_() {
    }
}
