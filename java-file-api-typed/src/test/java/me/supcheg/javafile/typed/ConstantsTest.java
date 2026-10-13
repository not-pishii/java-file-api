package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.fixtures.Wide;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.addDouble;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.addLong;
import static me.supcheg.javafile.typed.Expressions.and;
import static me.supcheg.javafile.typed.Expressions.at;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.divInt;
import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.eqInt;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.geInt;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.leInt;
import static me.supcheg.javafile.typed.Expressions.length;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.modInt;
import static me.supcheg.javafile.typed.Expressions.mulDouble;
import static me.supcheg.javafile.typed.Expressions.mulInt;
import static me.supcheg.javafile.typed.Expressions.mulLong;
import static me.supcheg.javafile.typed.Expressions.narrowTruncatingDoubleToInt;
import static me.supcheg.javafile.typed.Expressions.narrowTruncatingLongToInt;
import static me.supcheg.javafile.typed.Expressions.neqInt;
import static me.supcheg.javafile.typed.Expressions.neqRef;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.not;
import static me.supcheg.javafile.typed.Expressions.or;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.subInt;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.Expressions.unbox;
import static me.supcheg.javafile.typed.Expressions.widenIntToDouble;
import static me.supcheg.javafile.typed.Expressions.widenIntToLong;
import static me.supcheg.javafile.typed.Expressions.widenLongToDouble;
import static org.assertj.core.api.Assertions.assertThat;

/// Constant folding (JLS 15.29) reproduces javac's verdict on loop
/// conditions: each condition is told against javac, by the Java it stands
/// for — a constant `true` leaves what follows the loop unreachable, a
/// constant `false` its body.
class ConstantsTest {
    private static final Body<Prim.Int> ROOT = Body.root("root");

    /// The variables of the probe: no expression of one is a constant.
    private static final Var<Prim.Bool> FLAG = Var.param(PrimitiveToken.BOOLEAN, ROOT);

    private static final Var<Prim.Int> N = Var.param(PrimitiveToken.INT, ROOT);
    private static final Var<Prim.Long> BIG = Var.param(PrimitiveToken.LONG, ROOT);
    private static final Var<Prim.Double> REAL = Var.param(PrimitiveToken.DOUBLE, ROOT);
    private static final Var<String> TEXT = Var.param(String_.TOKEN, ROOT);

    /// `@Nullable String`: a `String` whose token is not that of `String`, so that a `String` is cast to it.
    private static final FinalClassToken<String> ANNOTATED_STRING = UnsafeFacts.finalClassToken(
            Types.of(
                    ConstantDescs.CD_String,
                    new AnnotationUse(ClassDesc.of("org.jspecify.annotations", "Nullable"), List.of())),
            List.of(ConstantDescs.CD_Object),
            MethodTable.EMPTY);

    /// What javac makes of `condition` as the condition of a loop: the constant it is, or none.
    private static Optional<Boolean> verdictOfJavac(String condition) {
        String parameters = "(boolean flag, int n, long big, double real, String text)";
        // the body of a loop of a constant `false` is unreachable, whatever follows the loop
        if (unreachable("void probe" + parameters + " { while (" + condition + ") { n++; } }")) {
            return Optional.of(false);
        }
        if (unreachable("int probe" + parameters + " { while (" + condition + ") { } return 0; }")) {
            return Optional.of(true);
        }
        return Optional.empty();
    }

    private static boolean unreachable(String method) {
        Compilation compilation =
                javac().compile(JavaFileObjects.forSourceString("Probe", "class Probe {\n" + method + "\n}\n"));
        List<String> errors = compilation.errors().stream()
                .map(error -> error.getMessage(Locale.ROOT))
                .toList();
        assertThat(errors).allMatch(error -> error.contains("unreachable statement"));
        return !errors.isEmpty();
    }

    private static Optional<Boolean> folded(Expr<Prim.Bool> condition) {
        return Stream.of(true, false)
                .filter(value -> Constants.isConstant(condition.node(), value))
                .findFirst();
    }

    private static Arguments of(String java, Expr<Prim.Bool> typed) {
        return Arguments.of(java, typed);
    }

    static Stream<Arguments> conditions() {
        return Stream.of(booleans(), ints(), longsAndDoubles(), strings(), conditionals(), others(), nonConstants())
                .flatMap(conditions -> conditions);
    }

    private static Stream<Arguments> booleans() {
        return Stream.of(
                of("true", literal(true)),
                of("false", literal(false)),
                of("flag", FLAG),
                of("!true", not(literal(true))),
                of("!false", not(literal(false))),
                of("!flag", not(FLAG)),
                of("true && true", and(literal(true), literal(true))),
                of("true && false", and(literal(true), literal(false))),
                of("false && true", and(literal(false), literal(true))),
                of("flag && true", and(FLAG, literal(true))),
                of("false && flag", and(literal(false), FLAG)),
                of("false || true", or(literal(false), literal(true))),
                of("true || false", or(literal(true), literal(false))),
                of("false || false", or(literal(false), literal(false))),
                of("flag || true", or(FLAG, literal(true))),
                of("true || flag", or(literal(true), FLAG)),
                of("true && (false || true)", and(literal(true), or(literal(false), literal(true)))));
    }

    private static Stream<Arguments> ints() {
        return Stream.of(
                of("1 + 2 == 3", eqInt(addInt(literal(1), literal(2)), literal(3))),
                of("5 - 2 == 3", eqInt(subInt(literal(5), literal(2)), literal(3))),
                of("2 * 3 == 6", eqInt(mulInt(literal(2), literal(3)), literal(6))),
                of("7 / 2 == 3", eqInt(divInt(literal(7), literal(2)), literal(3))),
                of("7 % 2 == 1", eqInt(modInt(literal(7), literal(2)), literal(1))),
                // an expression that completes abruptly is no constant: the loops of both compile
                of("1 / 0 == 0", eqInt(divInt(literal(1), literal(0)), literal(0))),
                of("1 % 0 == 0", eqInt(modInt(literal(1), literal(0)), literal(0))),
                of("1 == 2", eqInt(literal(1), literal(2))),
                of("1 != 2", neqInt(literal(1), literal(2))),
                of("1 != 1", neqInt(literal(1), literal(1))),
                of("1 < 2", ltInt(literal(1), literal(2))),
                of("2 < 2", ltInt(literal(2), literal(2))),
                of("2 <= 2", leInt(literal(2), literal(2))),
                of("3 <= 2", leInt(literal(3), literal(2))),
                of("3 > 2", gtInt(literal(3), literal(2))),
                of("2 > 2", gtInt(literal(2), literal(2))),
                of("2 >= 2", geInt(literal(2), literal(2))),
                of("2 >= 3", geInt(literal(2), literal(3))),
                of("n == 1", eqInt(N, literal(1))),
                of("1 == n", eqInt(literal(1), N)),
                of("n + 1 == 2", eqInt(addInt(N, literal(1)), literal(2))),
                of("1 - n != 2", neqInt(subInt(literal(1), N), literal(2))),
                of("n * 0 < 1", ltInt(mulInt(N, literal(0)), literal(1))),
                of("0 / n <= 1", leInt(divInt(literal(0), N), literal(1))),
                of("n % 1 > 1", gtInt(modInt(N, literal(1)), literal(1))),
                of("n >= n", geInt(N, N)));
    }

    private static Stream<Arguments> longsAndDoubles() {
        return Stream.of(
                of(
                        "(int) (1L + 2L) == 3",
                        eqInt(narrowTruncatingLongToInt(addLong(literal(1L), literal(2L))), literal(3))),
                of(
                        "(int) (2L * 3L) == 6",
                        eqInt(narrowTruncatingLongToInt(mulLong(literal(2L), literal(3L))), literal(6))),
                of("(int) (big + 1L) == 1", eqInt(narrowTruncatingLongToInt(addLong(BIG, literal(1L))), literal(1))),
                of("(int) (2L * big) == 1", eqInt(narrowTruncatingLongToInt(mulLong(literal(2L), BIG)), literal(1))),
                of(
                        "(int) (1.5 + 1.5) == 3",
                        eqInt(narrowTruncatingDoubleToInt(addDouble(literal(1.5), literal(1.5))), literal(3))),
                of(
                        "(int) (1.5 * 2.0) == 3",
                        eqInt(narrowTruncatingDoubleToInt(mulDouble(literal(1.5), literal(2.0))), literal(3))),
                of(
                        "(int) (real + 1.0) == 1",
                        eqInt(narrowTruncatingDoubleToInt(addDouble(REAL, literal(1.0))), literal(1))),
                of(
                        "(int) (2.0 * real) == 1",
                        eqInt(narrowTruncatingDoubleToInt(mulDouble(literal(2.0), REAL)), literal(1))),
                // a narrowing truncates
                of("(int) 2.9 == 2", eqInt(narrowTruncatingDoubleToInt(literal(2.9)), literal(2))),
                of("(int) 4294967297L == 1", eqInt(narrowTruncatingLongToInt(literal(4294967297L)), literal(1))),
                of("(int) (long) 3 == 3", eqInt(narrowTruncatingLongToInt(widenIntToLong(literal(3))), literal(3))),
                of(
                        "(int) (double) 3 == 3",
                        eqInt(narrowTruncatingDoubleToInt(widenIntToDouble(literal(3))), literal(3))),
                of(
                        "(int) (double) 3L == 3",
                        eqInt(narrowTruncatingDoubleToInt(widenLongToDouble(literal(3L))), literal(3))),
                of("(int) (long) n == 3", eqInt(narrowTruncatingLongToInt(widenIntToLong(N)), literal(3))));
    }

    private static Stream<Arguments> strings() {
        return Stream.of(
                // String constants are interned, so == compares their contents
                of("\"a\" == \"a\"", eqRef(literal("a"), literal("a"))),
                of("\"a\" == \"b\"", eqRef(literal("a"), literal("b"))),
                of("\"a\" != \"b\"", neqRef(literal("a"), literal("b"))),
                of("\"a\" != \"a\"", neqRef(literal("a"), literal("a"))),
                of("\"a\" + \"1\" == \"a1\"", eqRef(concat(literal("a"), literal("1")), literal("a1"))),
                of("text == \"a\"", eqRef(TEXT, literal("a"))),
                of("\"a\" != text", neqRef(literal("a"), TEXT)),
                of("text + \"a\" == \"a\"", eqRef(concat(TEXT, literal("a")), literal("a"))),
                of("\"a\" + text == \"a\"", eqRef(concat(literal("a"), TEXT), literal("a"))),
                // `(String) null` is no constant
                of("(String) null == (String) null", eqRef(literalNull(String_.TOKEN), literalNull(String_.TOKEN))),
                // a cast to `String` is one of a constant expression
                of(
                        "(@org.jspecify.annotations.Nullable String) \"a\" == \"a\"",
                        eqRef(castChecked(ANNOTATED_STRING, literal("a")), literal("a"))),
                of(
                        "(@org.jspecify.annotations.Nullable String) text == \"a\"",
                        eqRef(castChecked(ANNOTATED_STRING, TEXT), literal("a"))));
    }

    private static Stream<Arguments> conditionals() {
        return Stream.of(
                of("true ? true : false", cond(literal(true), literal(true), literal(false), PrimitiveToken.BOOLEAN)),
                of("false ? false : true", cond(literal(false), literal(false), literal(true), PrimitiveToken.BOOLEAN)),
                of("false ? true : false", cond(literal(false), literal(true), literal(false), PrimitiveToken.BOOLEAN)),
                // a conditional is constant where its three operands are
                of("flag ? true : true", cond(FLAG, literal(true), literal(true), PrimitiveToken.BOOLEAN)),
                of("true ? flag : true", cond(literal(true), FLAG, literal(true), PrimitiveToken.BOOLEAN)),
                of("true ? true : flag", cond(literal(true), literal(true), FLAG, PrimitiveToken.BOOLEAN)),
                // branches of the type of the conditional are not cast
                of(
                        "(true ? \"a\" : \"b\") == \"a\"",
                        eqRef(cond(literal(true), literal("a"), literal("b"), String_.TOKEN), literal("a"))),
                // a cast to a type other than `String` or a primitive is not one of a constant expression
                of(
                        "(true ? (CharSequence) \"a\" : (CharSequence) \"b\") == \"a\"",
                        eqRef(cond(literal(true), literal("a"), literal("b"), CharSequence_.TOKEN), literal("a"))));
    }

    private static Stream<Arguments> others() {
        return Stream.of(
                // a constant variable
                of("Integer.MAX_VALUE > 0", gtInt(staticField(Integer_.MAX_VALUE), literal(0))),
                // a `static final` field that is not one
                of("Integer.TYPE == Integer.TYPE", eqRef(staticField(Integer_.TYPE), staticField(Integer_.TYPE))));
    }

    /// What is no constant expression whatever its operands are (JLS 15.29): a call, an instance
    /// creation, an access of a field that is no constant variable or of an array, a lambda, a `switch`.
    private static Stream<Arguments> nonConstants() {
        var supplier = new Supplier_<>(String_.TOKEN);
        var ints = PrimitiveToken.INT.array();
        String signal = Signal.class.getCanonicalName();
        String supplied = "((java.util.function.Supplier<String>) () -> \"a\")";
        return Stream.of(
                of("text.length() == 1", eqInt(call(TEXT, String_.length), literal(1))),
                of("Integer.parseInt(text) == 1", eqInt(staticCall(Integer_.parseInt_String, TEXT), literal(1))),
                of("new Object() == new Object()", eqRef(new_(Object_.new_), new_(Object_.new_))),
                of(
                        "Integer.valueOf(1) == Integer.valueOf(1)",
                        eqRef(box(PrimitiveToken.INT, literal(1)), box(PrimitiveToken.INT, literal(1)))),
                of(
                        "Integer.valueOf(1).intValue() == 1",
                        eqInt(unbox(PrimitiveToken.INT, box(PrimitiveToken.INT, literal(1))), literal(1))),
                of(signal + ".RED == " + signal + ".RED", eqRef(enumConstant(Signal_.RED), enumConstant(Signal_.RED))),
                of(
                        "new " + Wide.class.getCanonicalName() + "().told == \"\"",
                        eqRef(field(new_(Wide_.new_), Wide_.told), literal(""))),
                of("new int[1].length == 1", eqInt(length(ints, newArray(ints, literal(1))), literal(1))),
                of("(new int[1])[0] == 0", eqInt(at(ints, newArray(ints, literal(1)), literal(0)), literal(0))),
                of(
                        supplied + " == " + supplied,
                        eqRef(lambda(supplier.sam, () -> literal("a")), lambda(supplier.sam, () -> literal("a")))),
                of(
                        "(switch (" + signal + ".RED) { default -> 1; }) == 1",
                        eqInt(
                                switch_(
                                        Signal_.TOKEN,
                                        enumConstant(Signal_.RED),
                                        PrimitiveToken.INT,
                                        cases -> cases.default_(literal(1))),
                                literal(1))));
    }

    @Test
    void anUnsafeExpressionIsTakenForNoConstant() {
        // what the untyped core wrote is not looked into: `true` of it is a constant to javac alone
        assertThat(folded(Unsafe.expr(me.supcheg.javafile.code.Exprs.literal(true), PrimitiveToken.BOOLEAN)))
                .isEmpty();
        assertThat(verdictOfJavac("true")).contains(true);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("conditions")
    void aConditionFoldsAsJavacFoldsIt(String java, Expr<Prim.Bool> typed) {
        assertThat(folded(typed)).isEqualTo(verdictOfJavac(java));
    }
}
