package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.AbstractCtorRef2;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `plain` (mini-spec §9.2): a final, an open and an abstract class,
/// an interface and a record; fields of every kind; constants.
class PlainFixtureTest extends FixtureSupport {
    private static final String GREETER = """
            package p;
            public class Greeter {
                public static final String DEFAULT = "say \\"hi\\"\\n\\t\\\\ \\u00e9\\u0001";
                public static final int INT = -2147483648;
                public static final long LONG = 9007199254740993L;
                public static final short SHORT = -3;
                public static final byte BYTE = 127;
                public static final char CHAR = '\\'';
                public static final boolean BOOL = true;
                public static final float FLOAT = 1.1f;
                public static final double DOUBLE = 0.1;
                public static final double NAN = 0.0 / 0.0;
                public static final double INFINITY = 1.0 / 0.0;
                public static final double NEGATIVE_INFINITY = -1.0 / 0.0;
                public static final double NEGATIVE_ZERO = -0.0;
                public static final float FLOAT_NAN = 0.0f / 0.0f;
                public static final float FLOAT_INFINITY = 1.0f / 0.0f;
                public static final String NOT_CONSTANT = new String("x");
                public static final Object OBJECT = null;
                public static long counter;
                public static String label;
                public final int count = 1;
                public final String name = "n";
                public int mutable;
                public String text;
                public int[] numbers;
                public String[][] grid;
                public Greeter() {}
                public Greeter(String prefix) {}
                public Greeter(int a, long b, boolean[] c) {}
                public String greet() { return null; }
                public String greet(String name) { return null; }
                public String greet(String name, java.util.Locale locale) { return null; }
                public void log(String message) {}
                public static void main(String[] args) {}
                public static int parse(String s) { return 0; }
                public int[] arr(int[][] a, Greeter[] g) { return null; }
            }
            """;

    private static final List<String> ALL_FIELDS = List.of(
            "BOOL",
            "BYTE",
            "CHAR",
            "DEFAULT",
            "DOUBLE",
            "FLOAT",
            "FLOAT_INFINITY",
            "FLOAT_NAN",
            "INFINITY",
            "INT",
            "LONG",
            "NAN",
            "NEGATIVE_INFINITY",
            "NEGATIVE_ZERO",
            "NOT_CONSTANT",
            "OBJECT",
            "SHORT");

    @Test
    void aClassGetsAFactPerPublicField() throws Exception {
        ClassLoader loader = load(generate("p.Greeter.class", GREETER));
        String greeter = "gen.facts.p.Greeter_";

        assertThat(token(loader, greeter)).isInstanceOf(OpenClassToken.class);
        StaticFieldRef<?> notConstant = (StaticFieldRef<?>) fact(loader, greeter, "NOT_CONSTANT");
        assertThat(notConstant.constantValue()).isEmpty();
        assertThat(notConstant.name()).isEqualTo("NOT_CONSTANT");
        assertThat(notConstant.type().typeRef()).isEqualTo(Types.STRING);
        StaticFieldRef<?> object = (StaticFieldRef<?>) fact(loader, greeter, "OBJECT");
        assertThat(object.constantValue()).isEmpty();
        assertThat(object.type().typeRef()).isEqualTo(Types.OBJECT);
        MutableStaticFieldRef<?> counter = (MutableStaticFieldRef<?>) fact(loader, greeter, "counter");
        assertThat(counter.name()).isEqualTo("counter");
        assertThat(counter.type()).isSameAs(PrimitiveToken.LONG);
        assertThat(fact(loader, greeter, "label")).isInstanceOf(MutableStaticFieldRef.class);
        FieldRef<?, ?> count = (FieldRef<?, ?>) fact(loader, greeter, "count");
        assertThat(count).isNotInstanceOf(MutableFieldRef.class);
        assertThat(count.type()).isSameAs(PrimitiveToken.INT);
        assertThat(count.owner()).isEqualTo(token(loader, greeter));
        assertThat(fact(loader, greeter, "name")).isInstanceOf(FieldRef.class).isNotInstanceOf(MutableFieldRef.class);
        assertThat(fact(loader, greeter, "mutable")).isInstanceOf(MutableFieldRef.class);
        assertThat(fact(loader, greeter, "text")).isInstanceOf(MutableFieldRef.class);
        assertThat(((MutableFieldRef<?, ?>) fact(loader, greeter, "numbers"))
                        .type()
                        .typeRef())
                .isEqualTo(Types.array(PrimitiveTypeRef.INT));
        assertThat(((MutableFieldRef<?, ?>) fact(loader, greeter, "grid"))
                        .type()
                        .typeRef())
                .isEqualTo(Types.array(Types.array(Types.STRING)));
    }

    @Test
    void aConstantHoldsItsValueExactly() throws Exception {
        ClassLoader loader = load(generate("p.Greeter.class", GREETER));
        String greeter = "gen.facts.p.Greeter_";
        Map<String, Object> expected = new LinkedHashMap<>();
        expected.put("DEFAULT", "say \"hi\"\n\t\\ é\u0001");
        expected.put("INT", Integer.MIN_VALUE);
        expected.put("LONG", 9007199254740993L);
        expected.put("SHORT", (short) -3);
        expected.put("BYTE", (byte) 127);
        expected.put("CHAR", '\'');
        expected.put("BOOL", true);
        expected.put("FLOAT", 1.1f);
        expected.put("DOUBLE", 0.1);
        expected.put("NAN", Double.NaN);
        expected.put("INFINITY", Double.POSITIVE_INFINITY);
        expected.put("NEGATIVE_INFINITY", Double.NEGATIVE_INFINITY);
        expected.put("NEGATIVE_ZERO", -0.0);
        expected.put("FLOAT_NAN", Float.NaN);
        expected.put("FLOAT_INFINITY", Float.POSITIVE_INFINITY);
        for (Map.Entry<String, Object> entry : expected.entrySet()) {
            StaticFieldRef<?> field = (StaticFieldRef<?>) fact(loader, greeter, entry.getKey());
            assertThat(field.constantValue()).as(entry.getKey()).contains(entry.getValue());
        }
        assertThat(((StaticFieldRef<?>) fact(loader, greeter, "INT")).type()).isSameAs(PrimitiveToken.INT);
        assertThat(((StaticFieldRef<?>) fact(loader, greeter, "SHORT")).type()).isSameAs(PrimitiveToken.SHORT);
        assertThat(((StaticFieldRef<?>) fact(loader, greeter, "BYTE")).type()).isSameAs(PrimitiveToken.BYTE);
        assertThat(((StaticFieldRef<?>) fact(loader, greeter, "CHAR")).type()).isSameAs(PrimitiveToken.CHAR);
        assertThat(((StaticFieldRef<?>) fact(loader, greeter, "BOOL")).type()).isSameAs(PrimitiveToken.BOOLEAN);
        assertThat(((StaticFieldRef<?>) fact(loader, greeter, "FLOAT")).type()).isSameAs(PrimitiveToken.FLOAT);
        assertThat(((StaticFieldRef<?>) fact(loader, greeter, "DEFAULT")).type().typeRef())
                .isEqualTo(Types.STRING);
        assertThat(ALL_FIELDS)
                .allSatisfy(name -> assertThat(fact(loader, greeter, name)).isInstanceOf(StaticFieldRef.class));
    }

    @Test
    void constructorsAreNamedByTheirParameters() throws Exception {
        ClassLoader loader = load(generate("p.Greeter.class", GREETER));
        String greeter = "gen.facts.p.Greeter_";

        CtorRef0<?> none = (CtorRef0<?>) fact(loader, greeter, "new_");
        assertThat(none.owner()).isEqualTo(token(loader, greeter));
        assertThat(none.traits()).isEqualTo(MemberTraits.FINAL);
        CtorRef1<?, ?> string = (CtorRef1<?, ?>) fact(loader, greeter, "new_String");
        assertThat(string.param1().typeRef()).isEqualTo(Types.STRING);
        assertThat(string.traits().overridability()).isEqualTo(Overridability.FINAL);
        Invocable three = (Invocable) fact(loader, greeter, "new_int_long_booleanArray");
        assertThat(three.params().stream().map(p -> p.typeRef()).toList())
                .containsExactly(PrimitiveTypeRef.INT, PrimitiveTypeRef.LONG, Types.array(PrimitiveTypeRef.BOOLEAN));
    }

    @Test
    void methodsAreNamedByTheirParametersAndAreOfTheFamilyOfTheirKind() throws Exception {
        ClassLoader loader = load(generate("p.Greeter.class", GREETER));
        String greeter = "gen.facts.p.Greeter_";

        MethodRef0<?, ?> greet = (MethodRef0<?, ?>) fact(loader, greeter, "greet");
        assertThat(greet.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(greet.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
        assertThat(greet.name()).isEqualTo("greet");
        assertThat(fact(loader, greeter, "greet_String")).isNotNull();
        MethodRef2<?, ?, ?, ?> two = (MethodRef2<?, ?, ?, ?>) fact(loader, greeter, "greet_String_Locale");
        assertThat(two.param1().typeRef()).isEqualTo(Types.STRING);
        assertThat(two.param2().typeRef()).isEqualTo(Types.of(ClassDesc.of("java.util.Locale")));
        VoidMethodRef1<?, ?> log = (VoidMethodRef1<?, ?>) fact(loader, greeter, "log_String");
        assertThat(log.resultType()).isEmpty();
        assertThat(log.name()).isEqualTo("log");
        assertThat(fact(loader, greeter, "main_StringArray")).isInstanceOf(VoidStaticMethodRef1.class);
        StaticMethodRef1<?, ?> parse = (StaticMethodRef1<?, ?>) fact(loader, greeter, "parse_String");
        assertThat(parse.result()).isSameAs(PrimitiveToken.INT);
        assertThat(parse.traits()).isEqualTo(MemberTraits.FINAL);
        MethodRef2<?, ?, ?, ?> arr = (MethodRef2<?, ?, ?, ?>) fact(loader, greeter, "arr_intArrayArray_GreeterArray");
        assertThat(arr.result().typeRef()).isEqualTo(Types.array(PrimitiveTypeRef.INT));
        assertThat(arr.param1().typeRef()).isEqualTo(Types.array(Types.array(PrimitiveTypeRef.INT)));
        assertThat(arr.param2().typeRef()).isEqualTo(Types.array(Types.of(ClassDesc.of("p.Greeter"))));
    }

    @Test
    void everyFactOfAFullMetamodelIsInTheOrderFieldsConstructorsMethods() throws Exception {
        ClassLoader loader = load(generate("p.Greeter.class", GREETER));
        String source = sources(generate("p.Greeter.class", GREETER)).get("gen.facts.p.Greeter_");

        assertThat(source.indexOf("StaticFieldRef<Int> INT"))
                .isLessThan(source.indexOf("CtorRef0<Greeter> new_"))
                .isLessThan(source.indexOf("MethodRef0<Greeter, String> greet"));
        assertThat(source.indexOf("CtorRef0<Greeter> new_"))
                .isLessThan(source.indexOf("MethodRef0<Greeter, String> greet"));
        assertThat(factNames(loader, "gen.facts.p.Greeter_")).hasSize(ALL_FIELDS.size() + 18);
    }

    @Test
    void aFullMetamodelIsMarkedAsFullAndListedAsFull() {
        Compilation compilation = generate("p.Greeter.class", GREETER);
        assertThat(sources(compilation).get("gen.facts.p.Greeter_"))
                .contains("complete = true, format = 4)")
                .contains("public final class Greeter_ {")
                .contains("@SuppressWarnings({")
                .contains(
                        "public static final OpenClassToken<Greeter> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);");
        assertThat(ProcessorHarness.resources(compilation))
                .containsEntry("META-INF/javafile/metamodel/full/p.Greeter", "gen.facts.p.Greeter_\n")
                .doesNotContainKey("META-INF/javafile/metamodel/token/p.Greeter");
        assertThat(sources(compilation).get("gen.facts.java.lang.String_")).contains("complete = false, format = 4)");
    }

    @Test
    void theTokenOfAnotherTypeIsMadeFromItsShapeNeverFromItsTokenField() {
        Compilation compilation = generate("p.Greeter.class, p.Other.class", GREETER, """
                package p;
                public class Other {
                    public Greeter greeter() { return null; }
                    public Other other(Greeter g) { return null; }
                }
                """);
        assertNoTokenOfAnotherMetamodel(sources(compilation));
        assertThat(sources(compilation).get("gen.facts.p.Other_"))
                .contains("Greeter_.Data.SHAPE")
                .doesNotContain("Other_.Data.SHAPE")
                .contains("UnsafeFacts.<Greeter>openClassToken(");
    }

    @Test
    void typesOfEveryKindGetTheirTokens() throws Exception {
        ClassLoader loader = load(
                generate(
                        "p.Fin.class, p.Open.class, p.Abs.class, p.Iface.class, p.Rec.class",
                        "package p; public final class Fin { public Fin() {} public Fin(int x) {} public int x() { return 0; } }",
                        "package p; public class Open { public Open(String s) {} }",
                        "package p; public abstract class Abs { public Abs() {} public Abs(String s, int i) {} protected Abs(long l) {} public abstract void run(); public void done() {} public static Abs make() { return null; } }",
                        "package p; public interface Iface { int LIMIT = 10; String NAME = \"n\"; void run(); default int size() { return 0; } static Iface empty() { return null; } }",
                        "package p; public record Rec(int x, String label) { public Rec(int x) { this(x, \"l\"); } public static Rec of() { return null; } }"));

        assertThat(token(loader, "gen.facts.p.Fin_")).isInstanceOf(FinalClassToken.class);
        assertThat(token(loader, "gen.facts.p.Open_")).isInstanceOf(OpenClassToken.class);
        assertThat(token(loader, "gen.facts.p.Abs_")).isInstanceOf(AbstractClassToken.class);
        assertThat(token(loader, "gen.facts.p.Iface_")).isInstanceOf(InterfaceToken.class);
        assertThat(token(loader, "gen.facts.p.Rec_")).isInstanceOf(FinalClassToken.class);

        // the methods of a final class are final
        assertThat(((MethodRef0<?, ?>) fact(loader, "gen.facts.p.Fin_", "x")).traits())
                .isEqualTo(MemberTraits.FINAL);
        assertThat(fact(loader, "gen.facts.p.Fin_", "new_")).isInstanceOf(CtorRef0.class);

        // an abstract class has no CtorRef, but an AbstractCtorRef, named super_…; a protected one has none
        assertThat(factNames(loader, "gen.facts.p.Abs_"))
                .containsExactly("done", "make", "run", "super_", "super_String_int");
        assertThat(fact(loader, "gen.facts.p.Abs_", "super_")).isInstanceOf(AbstractCtorRef0.class);
        assertThat(fact(loader, "gen.facts.p.Abs_", "super_String_int")).isInstanceOf(AbstractCtorRef2.class);
        assertThat(((Invocable) fact(loader, "gen.facts.p.Abs_", "run")).traits())
                .isEqualTo(MemberTraits.ABSTRACT);
        assertThat(((Invocable) fact(loader, "gen.facts.p.Abs_", "done")).traits())
                .isEqualTo(MemberTraits.OVERRIDABLE);

        // an interface has constants, abstract, default and static methods
        assertThat(factNames(loader, "gen.facts.p.Iface_"))
                .containsExactly("LIMIT", "NAME", "empty", "run", "sam", "size");
        assertThat(((StaticFieldRef<?>) fact(loader, "gen.facts.p.Iface_", "LIMIT")).constantValue())
                .contains(10);
        assertThat(((MethodRef0<?, ?>) fact(loader, "gen.facts.p.Iface_", "size")).traits())
                .isEqualTo(MemberTraits.OVERRIDABLE);

        // a record is a final class; its accessors and its constructors are members
        assertThat(factNames(loader, "gen.facts.p.Rec_"))
                .containsExactly(
                        "equals_Object", "hashCode", "label", "new_int", "new_int_String", "of", "toString", "x");
        assertThat(((MethodRef0<?, ?>) fact(loader, "gen.facts.p.Rec_", "x")).result())
                .isSameAs(PrimitiveToken.INT);
    }

    @Test
    void aMemberThatMentionsATypeMentionsItsMetamodelOnlyThroughItsShape() throws Exception {
        Compilation compilation = generate(
                "p.A.class, p.B.class",
                "package p; public class A { public B b() { return null; } public A self() { return null; } }",
                "package p; public class B { public A a() { return null; } }");
        ClassLoader loader = load(compilation);

        assertThat(((MethodRef0<?, ?>) fact(loader, "gen.facts.p.A_", "b"))
                        .result()
                        .typeRef())
                .isEqualTo(Types.of(ClassDesc.of("p.B")));
        assertThat(((MethodRef0<?, ?>) fact(loader, "gen.facts.p.A_", "self")).result())
                .isEqualTo(token(loader, "gen.facts.p.A_"));
        assertThat(((MethodRef0<?, ?>) fact(loader, "gen.facts.p.B_", "a")).result())
                .isEqualTo(token(loader, "gen.facts.p.A_"));
        // the metamodels refer to each other only through Data: both initialize, whichever first
        assertThat(sources(compilation).get("gen.facts.p.A_")).contains("B_.Data.SHAPE");
        assertThat(sources(compilation).get("gen.facts.p.B_")).contains("A_.Data.SHAPE");
    }

    @Test
    void theNamesOfPrimitiveMarkersDoNotHideTheClassesOfJavaLang() throws Exception {
        ClassLoader loader = load(generate("p.Boxes.class", """
                package p;
                public class Boxes {
                    public static final double D = 1.0;
                    public Double box(double d, Long l, long k, Float f, float g, Byte b, byte c, Short s, short t, Integer i, int j, Character ch) { return null; }
                    public Boolean flags(char cc, Boolean bo, boolean bb) { return null; }
                }
                """));
        assertThat(fact(
                        loader,
                        "gen.facts.p.Boxes_",
                        "box_double_Long_long_Float_float_Byte_byte_Short_short_Integer_int_Character"))
                .isInstanceOfSatisfying(Invocable.class, m -> {
                    assertThat(m.resultType().orElseThrow().typeRef())
                            .isEqualTo(Types.of(ClassDesc.of("java.lang.Double")));
                    assertThat(m.params().get(1).typeRef()).isEqualTo(Types.of(ClassDesc.of("java.lang.Long")));
                    assertThat(m.params().get(0).typeRef()).isEqualTo(PrimitiveTypeRef.DOUBLE);
                });
    }

    @Test
    void theOutputIsDeterministic() {
        Map<String, String> first = sources(generate("p.Greeter.class", GREETER));
        Map<String, String> second = sources(ProcessorHarness.succeeded(ProcessorHarness.process(List.of(lib), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Greeter.class)
                class G {}
                """)));
        assertThat(second).isEqualTo(first);
    }
}
