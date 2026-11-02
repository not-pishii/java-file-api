package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.fixtures.Settled;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.fixtures.Wide;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Settled_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.tools.Diagnostic;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.ExpressionArities.lambda;
import static me.supcheg.javafile.typed.ExpressionArities.new_;
import static me.supcheg.javafile.typed.ExpressionArities.staticCall;
import static me.supcheg.javafile.typed.Expressions.addDouble;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.addLong;
import static me.supcheg.javafile.typed.Expressions.and;
import static me.supcheg.javafile.typed.Expressions.bitAndInt;
import static me.supcheg.javafile.typed.Expressions.bitAndLong;
import static me.supcheg.javafile.typed.Expressions.bitNotInt;
import static me.supcheg.javafile.typed.Expressions.bitNotLong;
import static me.supcheg.javafile.typed.Expressions.bitOrInt;
import static me.supcheg.javafile.typed.Expressions.bitOrLong;
import static me.supcheg.javafile.typed.Expressions.bitXorInt;
import static me.supcheg.javafile.typed.Expressions.bitXorLong;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.divInt;
import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.eqInt;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.geInt;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.leInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.modInt;
import static me.supcheg.javafile.typed.Expressions.mulDouble;
import static me.supcheg.javafile.typed.Expressions.mulInt;
import static me.supcheg.javafile.typed.Expressions.mulLong;
import static me.supcheg.javafile.typed.Expressions.narrowTruncatingDoubleToInt;
import static me.supcheg.javafile.typed.Expressions.narrowTruncatingLongToInt;
import static me.supcheg.javafile.typed.Expressions.negDouble;
import static me.supcheg.javafile.typed.Expressions.negInt;
import static me.supcheg.javafile.typed.Expressions.negLong;
import static me.supcheg.javafile.typed.Expressions.neqInt;
import static me.supcheg.javafile.typed.Expressions.neqRef;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.not;
import static me.supcheg.javafile.typed.Expressions.or;
import static me.supcheg.javafile.typed.Expressions.shlInt;
import static me.supcheg.javafile.typed.Expressions.shlLong;
import static me.supcheg.javafile.typed.Expressions.shrInt;
import static me.supcheg.javafile.typed.Expressions.shrLong;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.subInt;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.Expressions.unaryPlusDouble;
import static me.supcheg.javafile.typed.Expressions.unaryPlusInt;
import static me.supcheg.javafile.typed.Expressions.unaryPlusLong;
import static me.supcheg.javafile.typed.Expressions.unbox;
import static me.supcheg.javafile.typed.Expressions.ushrInt;
import static me.supcheg.javafile.typed.Expressions.ushrLong;
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

    /// What javac makes of each condition of the cases as the condition of a loop, found by one run of it.
    private static final class Verdicts {
        static final Map<String, Optional<Boolean>> OF_JAVAC = of(conditions()
                .map(arguments -> (String) arguments.get()[0])
                .distinct()
                .toList());

        /// What javac makes of each condition as the condition of a loop: the constant it is, or none.
        /// The body of a loop of a constant `false` is unreachable, and so is what follows a loop of a
        /// constant `true` that nothing leaves. Each condition has two methods, a line each: the lines of
        /// the errors tell in which method javac found an unreachable statement.
        static Map<String, Optional<Boolean>> of(List<String> conditions) {
            String parameters = "(boolean flag, int n, long big, double real, String text)";
            StringBuilder source = new StringBuilder("class Probe {\n");
            for (int i = 0; i < conditions.size(); i++) {
                String condition = conditions.get(i);
                source.append("void whenFalse")
                        .append(i)
                        .append(parameters)
                        .append(" { while (")
                        .append(condition)
                        .append(") { n++; } }\n");
                source.append("int whenTrue")
                        .append(i)
                        .append(parameters)
                        .append(" { while (")
                        .append(condition)
                        .append(") { } return 0; }\n");
            }
            Compilation compilation = javac().compile(JavaFileObjects.forSourceString("Probe", source + "}\n"));
            assertThat(compilation.errors())
                    .allMatch(error -> error.getMessage(Locale.ROOT).contains("unreachable statement"));
            Set<Long> unreachable =
                    compilation.errors().stream().map(Diagnostic::getLineNumber).collect(Collectors.toSet());
            Map<String, Optional<Boolean>> verdicts = new HashMap<>();
            for (int i = 0; i < conditions.size(); i++) {
                long whenFalse = 2 + 2L * i;
                verdicts.put(
                        conditions.get(i),
                        unreachable.contains(whenFalse)
                                ? Optional.of(false)
                                : unreachable.contains(whenFalse + 1) ? Optional.of(true) : Optional.empty());
            }
            return verdicts;
        }
    }

    private static Optional<Boolean> verdictOfJavac(String condition) {
        return Verdicts.OF_JAVAC.containsKey(condition)
                ? Verdicts.OF_JAVAC.get(condition)
                : Verdicts.of(List.of(condition)).get(condition);
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
                of("n >= n", geInt(N, N)),
                of("-1 == -1", eqInt(negInt(literal(1)), literal(-1))),
                of("+1 == 1", eqInt(unaryPlusInt(literal(1)), literal(1))),
                of("~1 == -2", eqInt(bitNotInt(literal(1)), literal(-2))),
                of("(1 & 3) == 1", eqInt(bitAndInt(literal(1), literal(3)), literal(1))),
                of("(1 | 2) == 3", eqInt(bitOrInt(literal(1), literal(2)), literal(3))),
                of("(1 ^ 3) == 2", eqInt(bitXorInt(literal(1), literal(3)), literal(2))),
                of("(1 << 2) == 4", eqInt(shlInt(literal(1), literal(2)), literal(4))),
                of("(1 << 33) == 2", eqInt(shlInt(literal(1), literal(33L)), literal(2))),
                of("(8 >> 2) == 2", eqInt(shrInt(literal(8), literal(2)), literal(2))),
                of("(-1 >>> 1) == 2147483647", eqInt(ushrInt(literal(-1), literal(1)), literal(Integer.MAX_VALUE))));
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
                of("(int) (long) n == 3", eqInt(narrowTruncatingLongToInt(widenIntToLong(N)), literal(3))),
                of("(int) (double) n == 3", eqInt(narrowTruncatingDoubleToInt(widenIntToDouble(N)), literal(3))),
                of("(int) (double) big == 3", eqInt(narrowTruncatingDoubleToInt(widenLongToDouble(BIG)), literal(3))),
                // what is not a number and the infinities are written as constant expressions, and fold so
                of("(int) (0.0 / 0.0) == 0", eqInt(narrowTruncatingDoubleToInt(literal(Double.NaN)), literal(0))),
                of(
                        "(int) (0.0 / 0.0 + 1.0) == 0",
                        eqInt(narrowTruncatingDoubleToInt(addDouble(literal(Double.NaN), literal(1.0))), literal(0))),
                of(
                        "(int) (1.0 / 0.0) == 2147483647",
                        eqInt(
                                narrowTruncatingDoubleToInt(literal(Double.POSITIVE_INFINITY)),
                                literal(Integer.MAX_VALUE))),
                of(
                        "(int) (-1.0 / 0.0) == -2147483648",
                        eqInt(
                                narrowTruncatingDoubleToInt(literal(Double.NEGATIVE_INFINITY)),
                                literal(Integer.MIN_VALUE))),
                of(
                        "(int) (1.0 / 0.0 * 0.0) == 1",
                        eqInt(
                                narrowTruncatingDoubleToInt(mulDouble(literal(Double.POSITIVE_INFINITY), literal(0.0))),
                                literal(1))),
                of("(int) -1.5 == -1", eqInt(narrowTruncatingDoubleToInt(literal(-1.5)), literal(-1))),
                of("-1 + 1 == 0", eqInt(addInt(literal(-1), literal(1)), literal(0))),
                of("(int) -2L == -2", eqInt(narrowTruncatingLongToInt(negLong(literal(2L))), literal(-2))),
                of("(int) +2L == 2", eqInt(narrowTruncatingLongToInt(unaryPlusLong(literal(2L))), literal(2))),
                of("(int) -1.5 == -1", eqInt(narrowTruncatingDoubleToInt(negDouble(literal(1.5))), literal(-1))),
                of("(int) +1.5 == 1", eqInt(narrowTruncatingDoubleToInt(unaryPlusDouble(literal(1.5))), literal(1))),
                of("(int) ~1L == -2", eqInt(narrowTruncatingLongToInt(bitNotLong(literal(1L))), literal(-2))),
                of(
                        "(int) (1L & 3L) == 1",
                        eqInt(narrowTruncatingLongToInt(bitAndLong(literal(1L), literal(3L))), literal(1))),
                of(
                        "(int) (1L | 2L) == 3",
                        eqInt(narrowTruncatingLongToInt(bitOrLong(literal(1L), literal(2L))), literal(3))),
                of(
                        "(int) (1L ^ 3L) == 2",
                        eqInt(narrowTruncatingLongToInt(bitXorLong(literal(1L), literal(3L))), literal(2))),
                of(
                        "(int) (1L << 2) == 4",
                        eqInt(narrowTruncatingLongToInt(shlLong(literal(1L), literal(2))), literal(4))),
                of(
                        "(int) (1L << 66) == 4",
                        eqInt(narrowTruncatingLongToInt(shlLong(literal(1L), literal(66L))), literal(4))),
                of(
                        "(int) (8L >> 2) == 2",
                        eqInt(narrowTruncatingLongToInt(shrLong(literal(8L), literal(2))), literal(2))),
                of(
                        "(int) (-1L >>> 1) == -1",
                        eqInt(narrowTruncatingLongToInt(ushrLong(literal(-1L), literal(1))), literal(-1))));
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
                        eqRef(literal("a").cast(ANNOTATED_STRING), literal("a"))),
                of(
                        "(@org.jspecify.annotations.Nullable String) text == \"a\"",
                        eqRef(TEXT.cast(ANNOTATED_STRING), literal("a"))));
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

    private static final String SETTLED = Settled.class.getCanonicalName();

    private static Stream<Arguments> others() {
        return Stream.of(
                // a constant variable
                of("Integer.MAX_VALUE > 0", gtInt(staticField(Integer_.MAX_VALUE), literal(0))),
                of(SETTLED + ".ON", staticField(Settled_.ON)),
                of(SETTLED + ".NAME == \"settled\"", eqRef(staticField(Settled_.NAME), literal("settled"))),
                of(
                        "(int) (" + SETTLED + ".BIG + 1L) == 1",
                        eqInt(narrowTruncatingLongToInt(addLong(staticField(Settled_.BIG), literal(1L))), literal(1))),
                of(
                        "(int) (" + SETTLED + ".RATIO * 4.0) == 2",
                        eqInt(
                                narrowTruncatingDoubleToInt(mulDouble(staticField(Settled_.RATIO), literal(4.0))),
                                literal(2))),
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
                of("text.length() == 1", eqInt(TEXT.call(String_.length), literal(1))),
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
                        eqRef(new_(Wide_.new_).field(Wide_.told), literal(""))),
                of("new int[1].length == 1", eqInt(newArray(ints, literal(1)).length(ints), literal(1))),
                of("(new int[1])[0] == 0", eqInt(newArray(ints, literal(1)).arrayAccess(ints, literal(0)), literal(0))),
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
    void aConstantZeroIsNoDivisor() {
        // javac takes `1 / 0` for no constant, for it completes abruptly, and warns of it
        JavacVerdict.asJavacWarns(
                IllegalArgumentException.class,
                () -> divInt(N, literal(0)),
                "divInt by a constant expression of the value zero",
                "int probe(int n) { return n / 0; }",
                "division by zero");
        JavacVerdict.asJavacWarns(
                IllegalArgumentException.class,
                () -> modInt(literal(1), subInt(literal(1), literal(1))),
                "modInt by a constant expression of the value zero",
                "int probe() { return 1 % (1 - 1); }",
                "division by zero");
        JavacVerdict.asJavacWarns(
                IllegalArgumentException.class,
                () -> divInt(N, narrowTruncatingDoubleToInt(literal(Double.NaN))),
                "divInt by a constant expression of the value zero",
                "int probe(int n) { return n / (int) (0.0 / 0.0); }",
                "division by zero");
        // a divisor that is no constant is one
        assertThat(folded(eqInt(divInt(literal(1), N), literal(0)))).isEmpty();
    }

    @Test
    void aConstantOfATypeTheTypedLayerHasNoOperatorOfIsFoldedToNone() {
        // `Character.MIN_VALUE` is a constant to javac; no operator of the typed layer takes a char
        var minValue = UnsafeFacts.constantField(Integer_.TOKEN, "MIN_VALUE", PrimitiveToken.CHAR, (char) 0);
        assertThat(Constants.fold(staticField(minValue).node())).isEqualTo(new Constants.Folded.None());
    }

    @Test
    void aNewOperatorOverAnOperandItDoesNotTakeIsNoConstant() {
        Constants.Folded none = new Constants.Folded.None();

        assertThat(Constants.fold(new Node.Unary(UnaryOp.NOT, literal(true).node())))
                .isEqualTo(none);
        assertThat(Constants.fold(new Node.Unary(UnaryOp.NEG, literal(true).node())))
                .isEqualTo(none);
        assertThat(Constants.fold(new Node.Unary(UnaryOp.BIT_NOT, literal(1.5).node())))
                .isEqualTo(none);
        assertThat(Constants.fold(new Node.Binary(
                        Node.Operator.SHL_INT, literal(1L).node(), literal(1).node())))
                .isEqualTo(none);
        assertThat(Constants.fold(new Node.Binary(
                        Node.Operator.SHL_INT, literal(1).node(), literal(1.5).node())))
                .isEqualTo(none);
        assertThat(Constants.fold(new Node.Binary(
                        Node.Operator.SHL_LONG, literal(1).node(), literal(1).node())))
                .isEqualTo(none);
        assertThat(Constants.fold(new Node.Binary(
                        Node.Operator.SHL_LONG, literal(1L).node(), literal(1.5).node())))
                .isEqualTo(none);
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
