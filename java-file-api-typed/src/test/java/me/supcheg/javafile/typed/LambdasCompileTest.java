package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.TriConsumer;
import me.supcheg.javafile.typed.fixtures.TriFunction;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Iterable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.StringBuilder_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.typed.testfacts.java.util.Comparator_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.concurrent.Callable_;
import me.supcheg.javafile.typed.testfacts.java.util.function.BiConsumer_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Consumer_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Function_;
import me.supcheg.javafile.typed.testfacts.java.util.function.IntBinaryOperator_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.java.util.function.UnaryOperator_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tasks_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.TriConsumer_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.TriFunction_;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.lambdaBlock;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.unbox;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static org.assertj.core.api.Assertions.assertThat;

/// Positive end-to-end tests of lambdas (§6.4): a lambda typed by the `sam`
/// fact of a functional interface renders to code javac accepts under every
/// lint — no redundant cast, no raw type, no unchecked conversion — in
/// every place an expression of the interface may stand, and the running
/// code does what the lambda reads.
class LambdasCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Lambdas");

    private static final Function_<String, Integer> LENGTH_FN = new Function_<>(String_.TOKEN, Integer_.TOKEN);
    /// `UnaryOperator<String>` inherits its method from `Function<String, String>`.
    private static final Function_<String, String> STRING_FN = new Function_<>(String_.TOKEN, String_.TOKEN);

    private static final Supplier_<String> STRINGS = new Supplier_<>(String_.TOKEN);
    private static final Consumer_<String> STRING_SINK = new Consumer_<>(String_.TOKEN);
    private static final UnaryOperator_<String> STRING_OP = new UnaryOperator_<>(String_.TOKEN);
    private static final List_<String> STRING_LIST = new List_<>(String_.TOKEN);
    private static final Iterable_<String> STRING_ITERABLE = new Iterable_<>(String_.TOKEN);
    private static final Comparator_<String> STRING_ORDER = new Comparator_<>(String_.TOKEN);
    private static final Callable_<Integer> INT_CALL = new Callable_<>(Integer_.TOKEN);
    private static final BiConsumer_<StringBuilder, String> APPENDER =
            new BiConsumer_<>(StringBuilder_.TOKEN, String_.TOKEN);
    private static final TriFunction_<String, String, String, String> JOIN3 =
            new TriFunction_<>(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
    private static final TriConsumer_<StringBuilder, String, String> APPEND2 =
            new TriConsumer_<>(StringBuilder_.TOKEN, String_.TOKEN, String_.TOKEN);
    private static final Supplier_<Supplier<String>> SUPPLIERS = new Supplier_<>(STRINGS.token);
    private static final Function_<String, Supplier<String>> CURRIED = new Function_<>(String_.TOKEN, STRINGS.token);

    private static final ArrayList_<Supplier<String>> SUPPLIER_LIST = new ArrayList_<>(STRINGS.token);

    private static CompiledClasses compiled;

    @BeforeAll
    static void compile() {
        compiled = CompiledClasses.of(
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        forms(cb);
                        places(cb);
                        captures(cb);
                        exceptions(cb);
                    }
                }));
    }

    /// Expression and block bodies, with and without a result, of every arity.
    private static <Self> void forms(TypedClassBuilder<Self> cb) {
        // static int length(String s) { Function<String, Integer> f = (String v) -> Integer.valueOf(v.length()); ... }
        cb.staticMethod(
                "length",
                PrimitiveToken.INT,
                String_.TOKEN,
                (b, s) -> b.let(
                        LENGTH_FN.token,
                        lambda(LENGTH_FN.sam, v -> box(PrimitiveToken.INT, call(v, String_.length))),
                        f -> b.return_(unbox(PrimitiveToken.INT, call(f, LENGTH_FN.apply_T, s)))));

        // the same with a block body: (String v) -> { int n = v.length(); return Integer.valueOf(n); }
        cb.staticMethod(
                "blockLength",
                PrimitiveToken.INT,
                String_.TOKEN,
                (b, s) -> b.let(
                        LENGTH_FN.token,
                        lambdaBlock(
                                LENGTH_FN.sam,
                                (lb, v) -> lb.let(
                                        PrimitiveToken.INT,
                                        call(v, String_.length),
                                        n -> lb.return_(box(PrimitiveToken.INT, n)))),
                        f -> b.return_(unbox(PrimitiveToken.INT, call(f, LENGTH_FN.apply_T, s)))));

        // static Supplier<String> constant(String s) { return () -> s; }
        cb.staticMethod("constant", STRINGS.token, String_.TOKEN, (b, s) -> b.return_(lambda(STRINGS.sam, () -> s)));

        // static Supplier<String> blockConstant(String s) { return () -> { return s; }; }
        cb.staticMethod(
                "blockConstant",
                STRINGS.token,
                String_.TOKEN,
                (b, s) -> b.return_(lambdaBlock(STRINGS.sam, lb -> lb.return_(s))));

        // static String twice(StringBuilder sb) { Runnable r = () -> sb.append("x"); r.run(); r.run(); ... }
        cb.staticMethod(
                "twice",
                String_.TOKEN,
                StringBuilder_.TOKEN,
                (b, sb) -> b.let(
                        Runnable_.TOKEN,
                        lambda(Runnable_.sam, () -> call(sb, StringBuilder_.append_String, literal("x"))),
                        r -> b.exec(voidCall(r, Runnable_.run))
                                .exec(voidCall(r, Runnable_.run))
                                .return_(call(sb, StringBuilder_.toString))));

        // static String blockTwice(StringBuilder sb) { Runnable r = () -> { sb.append("x"); sb.append("y"); }; ... }
        cb.staticMethod(
                "blockTwice",
                String_.TOKEN,
                StringBuilder_.TOKEN,
                (b, sb) -> b.let(
                        Runnable_.TOKEN,
                        lambdaBlock(
                                Runnable_.sam,
                                lb -> lb.exec(call(sb, StringBuilder_.append_String, literal("x")))
                                        .exec(call(sb, StringBuilder_.append_String, literal("y")))
                                        .end()),
                        r -> b.exec(voidCall(r, Runnable_.run))
                                .exec(voidCall(r, Runnable_.run))
                                .return_(call(sb, StringBuilder_.toString))));

        // static Consumer<String> sink(StringBuilder sb) { return (String v) -> sb.append(v); }
        cb.staticMethod(
                "sink",
                STRING_SINK.token,
                StringBuilder_.TOKEN,
                (b, sb) -> b.return_(lambda(STRING_SINK.sam, v -> call(sb, StringBuilder_.append_String, v))));

        // static Consumer<String> nonEmptySink(StringBuilder sb) { return (String v) -> { if (...) { ... } }; }
        cb.staticMethod(
                "nonEmptySink",
                STRING_SINK.token,
                StringBuilder_.TOKEN,
                (b, sb) -> b.return_(lambdaBlock(
                        STRING_SINK.sam,
                        (lb, v) -> lb.if_(
                                        gtInt(call(v, String_.length), literal(0)),
                                        t -> t.exec(call(sb, StringBuilder_.append_String, v)))
                                .end())));

        // static IntBinaryOperator sum() { return (int a, int b) -> a + b; }
        cb.staticMethod(
                "sum",
                IntBinaryOperator_.TOKEN,
                b -> b.return_(lambda(IntBinaryOperator_.sam, (x, y) -> addInt(x, y))));

        // static IntBinaryOperator blockSum() { return (int a, int b) -> { return a + b; }; }
        cb.staticMethod(
                "blockSum",
                IntBinaryOperator_.TOKEN,
                b -> b.return_(lambdaBlock(IntBinaryOperator_.sam, (lb, x, y) -> lb.return_(addInt(x, y)))));

        // static BiConsumer<StringBuilder, String> appender() { return (StringBuilder sb, String v) -> sb.append(v); }
        cb.staticMethod(
                "appender",
                APPENDER.token,
                b -> b.return_(lambda(APPENDER.sam, (sb, v) -> call(sb, StringBuilder_.append_String, v))));
        cb.staticMethod(
                "blockAppender",
                APPENDER.token,
                b -> b.return_(lambdaBlock(
                        APPENDER.sam,
                        (lb, sb, v) -> lb.exec(call(sb, StringBuilder_.append_String, v))
                                .return_())));

        // static TriFunction<String, String, String, String> join() { return (a, b, c) -> a + b + c; }
        cb.staticMethod("join", JOIN3.token, b -> b.return_(lambda(JOIN3.sam, (x, y, z) -> concat(concat(x, y), z))));
        cb.staticMethod(
                "blockJoin",
                JOIN3.token,
                b -> b.return_(lambdaBlock(JOIN3.sam, (lb, x, y, z) -> lb.return_(concat(concat(x, y), z)))));

        // static TriConsumer<StringBuilder, String, String> append2() { return (sb, a, b) -> sb.append(a + b); }
        cb.staticMethod(
                "append2",
                APPEND2.token,
                b -> b.return_(
                        lambda(APPEND2.sam, (sb, x, y) -> call(sb, StringBuilder_.append_String, concat(x, y)))));
        cb.staticMethod(
                "blockAppend2",
                APPEND2.token,
                b -> b.return_(lambdaBlock(
                        APPEND2.sam,
                        (lb, sb, x, y) -> lb.exec(call(sb, StringBuilder_.append_String, x))
                                .exec(call(sb, StringBuilder_.append_String, y))
                                .end())));
    }

    /// The places a lambda stands in: an argument, a receiver, an operand, a field.
    private static <Self> void places(TypedClassBuilder<Self> cb) {
        // list.forEach((Consumer<String>) (String v) -> sb.append(v)): the parameter is a Consumer<? super String>
        cb.staticMethod(
                "all",
                String_.TOKEN,
                STRING_LIST.token,
                (b, list) -> b.let(
                        StringBuilder_.TOKEN,
                        new_(StringBuilder_.new_),
                        sb -> b.exec(voidCall(
                                        list,
                                        STRING_ITERABLE.forEach_Consumer,
                                        lambda(STRING_SINK.sam, v -> call(sb, StringBuilder_.append_String, v))))
                                .return_(call(sb, StringBuilder_.toString))));

        // list.sort((String a, String b) -> b.compareTo(a))
        cb.voidStaticMethod(
                "descending",
                STRING_LIST.token,
                (b, list) -> b.exec(voidCall(
                                list,
                                STRING_LIST.sort_Comparator,
                                lambda(STRING_ORDER.sam, (x, y) -> call(y, String_.compareTo_String, x))))
                        .end());

        // ((Function<String, Integer>) (String v) -> Integer.valueOf(v.length())).apply(s): a receiver
        cb.staticMethod(
                "received",
                Integer_.TOKEN,
                String_.TOKEN,
                (b, s) -> b.return_(call(
                        lambda(LENGTH_FN.sam, v -> box(PrimitiveToken.INT, call(v, String_.length))),
                        LENGTH_FN.apply_T,
                        s)));

        // Object o = (Runnable) () -> s.trim(); return Tasks.isRunnable(o) && Tasks.isRunnable((Runnable) () -> ...);
        cb.staticMethod(
                "asObject",
                PrimitiveToken.BOOLEAN,
                String_.TOKEN,
                (b, s) -> b.let(
                        Object_.TOKEN,
                        lambda(Runnable_.sam, () -> call(s, String_.trim)),
                        o -> b.return_(Expressions.and(
                                staticCall(Tasks_.isRunnable_Object, o),
                                staticCall(
                                        Tasks_.isRunnable_Object,
                                        lambda(Runnable_.sam, () -> call(s, String_.trim)))))));

        // the overloads of Tasks.which take a Runnable, a Supplier<String> and a Callable<String>
        cb.staticMethod(
                "whichRunnable",
                String_.TOKEN,
                String_.TOKEN,
                (b, s) -> b.return_(staticCall(
                        Tasks_.which_Runnable, lambda(Runnable_.sam, () -> call(s, String_.trim)))));
        cb.staticMethod(
                "whichSupplier",
                String_.TOKEN,
                String_.TOKEN,
                (b, s) ->
                        b.return_(staticCall(Tasks_.which_Supplier, lambda(STRINGS.sam, () -> call(s, String_.trim)))));
        cb.staticMethod(
                "whichCallable",
                String_.TOKEN,
                String_.TOKEN,
                (b, s) -> b.return_(staticCall(
                        Tasks_.which_Callable,
                        lambda(new Callable_<>(String_.TOKEN).sam, () -> call(s, String_.trim)))));

        // an operand of == and a branch of a conditional
        cb.staticMethod(
                "sameTwice",
                PrimitiveToken.BOOLEAN,
                PrimitiveToken.BOOLEAN,
                (b, flag) -> b.let(
                        Runnable_.TOKEN,
                        cond(
                                flag,
                                lambda(Runnable_.sam, () -> call(literal("a"), String_.trim)),
                                lambda(Runnable_.sam, () -> call(literal("b"), String_.trim)),
                                Runnable_.TOKEN),
                        r -> b.return_(eqRef(r, lambda(Runnable_.sam, () -> call(literal("c"), String_.trim))))));

        // a lambda that is the value of a lambda: (String v) -> () -> v
        cb.staticMethod(
                "curried", CURRIED.token, b -> b.return_(lambda(CURRIED.sam, v -> lambda(STRINGS.sam, () -> v))));
        cb.staticMethod(
                "nested",
                SUPPLIERS.token,
                String_.TOKEN,
                (b, s) -> b.return_(lambdaBlock(
                        SUPPLIERS.sam,
                        lb -> lb.let(
                                String_.TOKEN,
                                concat(s, literal("!")),
                                t -> lb.return_(lambdaBlock(STRINGS.sam, inner -> inner.return_(concat(t, s))))))));

        // public final UnaryOperator<String> upper = (String v) -> v.toUpperCase(); — its method is inherited
        var upper = cb.field("upper", STRING_OP.token, lambda(STRING_OP.sam, v -> call(v, String_.toUpperCase)));
        cb.method(
                "shout",
                String_.TOKEN,
                String_.TOKEN,
                (b, self, s) -> b.return_(call(field(self, upper), STRING_FN.apply_T, s)));
    }

    /// What a lambda captures: an immutable variable, a parameter of an enclosing lambda, `this`.
    private static <Self> void captures(TypedClassBuilder<Self> cb) {
        var name = cb.mutableField("name", String_.TOKEN, literal("none"));

        // Supplier<String> named() { return () -> this.name; }
        cb.method("named", STRINGS.token, (b, self) -> b.return_(lambda(STRINGS.sam, () -> field(self, name))));

        // Consumer<String> renamer() { return (String v) -> { this.name = v; }; }
        cb.method(
                "renamer",
                STRING_SINK.token,
                (b, self) -> b.return_(lambda(STRING_SINK.sam, v -> assignField(self, name, v))));

        // a variable of a for-each loop is captured, a lambda's own variable is assigned in its body
        cb.staticMethod(
                "suppliers",
                new List_<>(STRINGS.token).token,
                STRING_LIST.token,
                (b, list) -> b.let(
                        SUPPLIER_LIST.token,
                        new_(SUPPLIER_LIST.new_),
                        out -> b.forEach(
                                        String_.TOKEN,
                                        list,
                                        (loop, v, _) -> loop.exec(call(
                                                out,
                                                new List_<>(STRINGS.token).add_E,
                                                lambdaBlock(
                                                        STRINGS.sam,
                                                        lb -> lb.letVar(
                                                                String_.TOKEN,
                                                                v,
                                                                acc -> lb.exec(assign(acc, concat(acc, acc)))
                                                                        .return_(acc))))))
                                .return_(out)));
    }

    /// A checked exception of a lambda body is the business of the method of its functional interface.
    private static <Self> void exceptions(TypedClassBuilder<Self> cb) {
        // static int read(String s) {
        //     Callable<Integer> c = () -> Integer.valueOf(new StringReader(s).read());   // call() throws Exception
        //     try { return c.call().intValue(); } catch (Exception e) { return -1; }
        // }
        cb.staticMethod(
                "read",
                PrimitiveToken.INT,
                String_.TOKEN,
                (b, s) -> b.let(
                        INT_CALL.token,
                        lambda(
                                INT_CALL.sam,
                                () -> box(
                                        PrimitiveToken.INT,
                                        call(new_(StringReader_.new_String, s), StringReader_.read))),
                        c -> b.tryTerminated(
                                h -> h.catch_(Exception_.TOKEN, (handler, _) -> handler.return_(literal(-1))),
                                t -> t.return_(unbox(PrimitiveToken.INT, call(c, INT_CALL.call))))));

        // a Supplier declares nothing: the lambda catches what its body throws
        cb.staticMethod(
                "readOrDefault",
                new Supplier_<>(Integer_.TOKEN).token,
                String_.TOKEN,
                (b, s) -> b.return_(lambdaBlock(
                        new Supplier_<>(Integer_.TOKEN).sam,
                        lb -> lb.tryTerminated(
                                h -> h.catch_(
                                        IOException_.TOKEN,
                                        (handler, _) -> handler.return_(box(PrimitiveToken.INT, literal(-1)))),
                                t -> t.return_(box(
                                        PrimitiveToken.INT,
                                        call(new_(StringReader_.new_String, s), StringReader_.read)))))));
    }

    @Test
    void anExpressionAndABlockLambdaComputeTheSame() throws Throwable {
        assertThat(compiled.invoke("length", "four")).isEqualTo(4);
        assertThat(compiled.invoke("blockLength", "four")).isEqualTo(4);
    }

    @Test
    @SuppressWarnings("unchecked")
    void aLambdaIsReturnedAndCapturesAParameter() throws Throwable {
        assertThat(((Supplier<String>) compiled.invoke("constant", "a")).get()).isEqualTo("a");
        assertThat(((Supplier<String>) compiled.invoke("blockConstant", "b")).get())
                .isEqualTo("b");
    }

    @Test
    void aVoidLambdaRunsItsEffect() throws Throwable {
        assertThat(compiled.invoke("twice", new StringBuilder())).isEqualTo("xx");
        assertThat(compiled.invoke("blockTwice", new StringBuilder())).isEqualTo("xyxy");
    }

    @Test
    @SuppressWarnings("unchecked")
    void lambdasOfOneParameterWithoutAResult() throws Throwable {
        StringBuilder sb = new StringBuilder();
        ((Consumer<String>) compiled.invoke("sink", sb)).accept("a");
        Consumer<String> nonEmpty = (Consumer<String>) compiled.invoke("nonEmptySink", sb);
        nonEmpty.accept("");
        nonEmpty.accept("b");

        assertThat(sb).hasToString("ab");
    }

    @Test
    @SuppressWarnings("unchecked")
    void lambdasOfTwoAndThreeParameters() throws Throwable {
        assertThat(((IntBinaryOperator) compiled.invoke("sum")).applyAsInt(2, 3))
                .isEqualTo(5);
        assertThat(((IntBinaryOperator) compiled.invoke("blockSum")).applyAsInt(2, 3))
                .isEqualTo(5);

        StringBuilder sb = new StringBuilder();
        ((BiConsumer<StringBuilder, String>) compiled.invoke("appender")).accept(sb, "a");
        ((BiConsumer<StringBuilder, String>) compiled.invoke("blockAppender")).accept(sb, "b");
        ((TriConsumer<StringBuilder, String, String>) compiled.invoke("append2")).accept(sb, "c", "d");
        ((TriConsumer<StringBuilder, String, String>) compiled.invoke("blockAppend2")).accept(sb, "e", "f");
        assertThat(sb).hasToString("abcdef");

        assertThat(((TriFunction<String, String, String, String>) compiled.invoke("join")).apply("a", "b", "c"))
                .isEqualTo("abc");
        assertThat(((TriFunction<String, String, String, String>) compiled.invoke("blockJoin")).apply("a", "b", "c"))
                .isEqualTo("abc");
    }

    @Test
    void aLambdaIsAnArgument() throws Throwable {
        assertThat(compiled.invoke("all", List.of("a", "b"))).isEqualTo("ab");

        List<String> list = new ArrayList<>(List.of("a", "c", "b"));
        compiled.invoke("descending", list);
        assertThat(list).containsExactly("c", "b", "a");
    }

    @Test
    void aLambdaIsAReceiverAnObjectAnOperandAndABranch() throws Throwable {
        assertThat(compiled.invoke("received", "four")).isEqualTo(4);
        assertThat(compiled.invoke("asObject", "a")).isEqualTo(true);
        assertThat(compiled.invoke("sameTwice", true)).isEqualTo(false);
    }

    @Test
    void aLambdaIsOfTheFunctionalInterfaceOfItsFactWhateverTheOverloads() throws Throwable {
        // to javac `() -> s.trim()` alone is most specifically a Supplier or a Callable, an ambiguity
        assertThat(compiled.invoke("whichRunnable", "a")).isEqualTo("Runnable");
        assertThat(compiled.invoke("whichSupplier", "a")).isEqualTo("Supplier");
        assertThat(compiled.invoke("whichCallable", "a")).isEqualTo("Callable");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aLambdaIsTheValueOfALambda() throws Throwable {
        assertThat(((Function<String, Supplier<String>>) compiled.invoke("curried"))
                        .apply("a")
                        .get())
                .isEqualTo("a");
        assertThat(((Supplier<Supplier<String>>) compiled.invoke("nested", "a"))
                        .get()
                        .get())
                .isEqualTo("a!a");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aLambdaInitializesAFieldAndCapturesThis() throws Throwable {
        assertThat(compiled.invoke("shout", "a")).isEqualTo("A");

        Object instance = compiled.generated().getConstructor().newInstance();
        Supplier<String> named =
                (Supplier<String>) compiled.generated().getMethod("named").invoke(instance);
        Consumer<String> renamer =
                (Consumer<String>) compiled.generated().getMethod("renamer").invoke(instance);
        assertThat(named.get()).isEqualTo("none");
        renamer.accept("some");
        assertThat(named.get()).isEqualTo("some");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aLambdaCapturesTheVariableOfALoop() throws Throwable {
        List<Supplier<String>> suppliers = (List<Supplier<String>>) compiled.invoke("suppliers", List.of("a", "b"));

        assertThat(suppliers).extracting(Supplier::get).containsExactly("aa", "bb");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aCheckedExceptionOfTheBodyIsDeclaredByTheFunctionalInterfaceOrCaughtInTheBody() throws Throwable {
        assertThat(compiled.invoke("read", "a")).isEqualTo((int) 'a');
        assertThat(((Supplier<Integer>) compiled.invoke("readOrDefault", "a")).get())
                .isEqualTo((int) 'a');
    }

    @Test
    void aLambdaIsCastToItsFunctionalInterfaceOnlyWhereTheContextDoesNotGiveIt() {
        assertThat(compiled.source())
                // the initializer of a variable and a returned value of that very type: no cast
                .contains("Function<String, Integer> v2 = (String v1) -> Integer.valueOf(v1.length());")
                .contains("return () -> v0;")
                .contains("return (String v0) -> () -> v0;")
                // the argument of the one method of that name and arity, whose parameter is of that type
                .contains("v1.add(() -> {")
                // a receiver, an Object, an argument of a parameter of another type or of an overloaded method
                .contains("((Function<String, Integer>) ((String v1) -> Integer.valueOf(v1.length()))).apply(v0)")
                .contains("Object v1 = (Runnable) (() -> v0.trim());")
                .contains("Tasks.isRunnable((Object) (Runnable) (() -> v0.trim()))")
                .contains("v0.forEach((Consumer<String>) ((String v2) -> v1.append(v2)))")
                .contains("Tasks.which((Supplier<String>) (() -> v0.trim()))")
                // every parameter is typed
                .contains("(StringBuilder v0, String v1, String v2) -> v0.append(v1 + v2)");
    }
}
