package me.supcheg.javafile.typed;

import com.google.testing.compile.CompilationSubject;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.Objects_;
import me.supcheg.javafile.typed.testfacts.java.util.function.IntSupplier_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.at;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.cloneArray;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.length;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.newArrayOf;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.varargs;
import static org.assertj.core.api.Assertions.assertThat;

/// A member of variable arity is declared by `variableArity()`: javac warns of a declaration whose elements are not
/// reifiable (possible heap pollution), accepts `@SafeVarargs` of a `static` method, a constructor and a `final`
/// method, and warns of the annotation where the elements are reifiable. The member is declared as javac wants it,
/// and the array of its parameter is held to what the annotation vouches for.
class VariableArityDeclarationsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final InterfaceToken<List<String>> STRING_LIST = new List_<>(String_.TOKEN).token;
    private static final ArrayToken<List<String>[], List<String>> LISTS = ArrayToken.of(STRING_LIST);
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);
    private static final ArrayToken<Object[], Object> OBJECTS = ArrayToken.of(Object_.TOKEN);
    private static final List_<String> STRINGS_LIST = new List_<>(String_.TOKEN);
    private static final String HEAP_POLLUTION = "parameter of variable arity of a @SafeVarargs member";

    private static JavaFile file(String name, TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    /// Declares `Probe` with a `static` `@SafeVarargs` method `m` of `xs` and the given body.
    private static <R> JavaFile staticOf(
            me.supcheg.javafile.facts.TypeToken<R> result,
            java.util.function.BiFunction<Body<R>, Var<List<String>[]>, Terminated<R>> body) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var m = cb.variableArity().declareStaticMethod("m", result, LISTS);
                var bodies = cb.bodies();
                bodies.define(m, body);
                return bodies;
            }
        });
    }

    @Test
    void membersOfNonReifiableElementsAreSafeVarargsAndFinalAndOnesOfReifiableElementsAreNot() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Declared", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var constructor = cb.declareConstructor();
                var size = cb.variableArity().declareStaticMethod("size", PrimitiveToken.INT, LISTS);
                var first = cb.variableArity().declareMethod("first", PrimitiveToken.INT, LISTS);
                var swap = cb.variableArity().declareStaticMethod("swap", PrimitiveToken.INT, LISTS);
                var total = cb.variableArity().declareStaticMethod("total", PrimitiveToken.INT, LISTS);
                var supplied = cb.variableArity().declareStaticMethod("supplied", PrimitiveToken.INT, LISTS);
                var head = cb.variableArity().declareStaticMethod("head", String_.TOKEN, STRINGS);
                var tail = cb.variableArity().declareMethod("tail", String_.TOKEN, PrimitiveToken.INT, STRINGS);
                var note = cb.variableArity().declareVoidStaticMethod("note", STRINGS);
                var noteOn = cb.variableArity().declareVoidMethod("noteOn", STRINGS);
                var noted = cb.variableArity().declareVoidMethod("noted", PrimitiveToken.INT, STRINGS);
                var viaSpread = cb.declareStaticMethod("viaSpread", PrimitiveToken.INT);
                var viaArray = cb.declareStaticMethod("viaArray", String_.TOKEN);
                var risky = cb.throwing(IOException_.TOKEN)
                        .variableArity()
                        .declareStaticMethod("risky", PrimitiveToken.INT, STRINGS);
                var bodies = cb.bodies();
                bodies.define(constructor, (b, self) -> b.end());
                bodies.define(size, (b, lists) -> b.return_(length(LISTS, lists)));
                bodies.define(first, (b, self, lists) -> b.return_(length(LISTS, lists)));
                bodies.define(
                        swap,
                        (b, lists) -> b.exec(assignAt(LISTS, lists, literal(0), at(LISTS, lists, literal(1))))
                                .return_(length(LISTS, lists)));
                bodies.define(
                        total,
                        (b, lists) -> b.letVar(
                                PrimitiveToken.INT,
                                literal(0),
                                n -> b.forEach(
                                                LISTS,
                                                lists,
                                                (loop, each, _) ->
                                                        loop.exec(assign(n, addInt(n, call(each, STRINGS_LIST.size)))))
                                        .return_(n)));
                bodies.define(
                        supplied,
                        (b, lists) -> b.let(
                                IntSupplier_.TOKEN,
                                lambda(IntSupplier_.sam, () -> length(LISTS, lists)),
                                f -> b.return_(call(f, IntSupplier_.getAsInt))));
                bodies.define(head, (b, parts) -> b.return_(at(STRINGS, parts, literal(0))));
                bodies.define(tail, (b, self, count, parts) -> b.return_(at(STRINGS, parts, count)));
                bodies.define(note, (b, parts) -> b.end());
                bodies.define(noteOn, (b, self, parts) -> b.end());
                bodies.define(noted, (b, self, count, parts) -> b.end());
                // the arguments one by one, then an array the code has
                bodies.define(
                        viaSpread,
                        b -> b.return_(
                                staticCall(size, varargs(LISTS, literalNull(STRING_LIST), literalNull(STRING_LIST)))));
                bodies.define(
                        viaArray, b -> b.return_(staticCall(head, newArrayOf(STRINGS, literal("x"), literal("y")))));
                bodies.define(
                        risky,
                        (b, parts) ->
                                b.return_(call(new_(StringReader_.new_String, literal("a")), StringReader_.read)));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("@SafeVarargs\n    public static int size(List<String>... v0) {")
                .contains("@SafeVarargs\n    public final int first(List<String>... v0) {")
                .contains("@SafeVarargs\n    public static int swap(List<String>... v0) {")
                .contains("v0[0] = v0[1];")
                .contains("for (List<String> v")
                .contains("public static String head(String... v0) {")
                .contains("public String tail(int v0, String... v1) {")
                .contains("public static void note(String... v0) {")
                .contains("public void noteOn(String... v0) {")
                .contains("public void noted(int v0, String... v1) {")
                .contains("public static int risky(String... v0) throws IOException {")
                .contains("Declared.size((List<String>) null, (List<String>) null)")
                .contains("Declared.head(new String[] {\"x\", \"y\"})");
        assertThat(compiled.source()).doesNotContain("@SafeVarargs\n    public static String head");
        assertThat(compiled.invoke("viaSpread")).isEqualTo(2);
        assertThat(compiled.invoke("viaArray")).isEqualTo("x");
        assertThat(compiled.invoke("size", (Object) new List<?>[] {List.of(), List.of()}))
                .isEqualTo(2);
        assertThat(compiled.invoke("supplied", (Object) new List<?>[] {List.of()}))
                .isEqualTo(1);
        List<String>[] pair = newPair();
        assertThat(compiled.invoke("swap", (Object) pair)).isEqualTo(2);
        assertThat(pair[0]).isSameAs(pair[1]);
    }

    @SuppressWarnings("unchecked")
    private static List<String>[] newPair() {
        return (List<String>[]) new List<?>[] {new ArrayList<String>(), List.of("b")};
    }

    @Test
    void aConstructorOfVariableArityIsSafeVarargsForNonReifiableElementsAlone() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Constructed", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var lists = cb.variableArity().declareConstructor(LISTS);
                var texts = cb.variableArity().declareConstructor(PrimitiveToken.INT, STRINGS);
                var bodies = cb.bodies();
                bodies.define(lists, (b, self, each) -> b.end());
                bodies.define(texts, (b, self, count, each) -> b.end());
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("@SafeVarargs\n    public Constructed(List<String>... v0) {")
                .contains("    public Constructed(int v0, String... v1) {")
                .doesNotContain("@SafeVarargs\n    public Constructed(int");
    }

    @Test
    void theFactOfAMemberOfVariableArityTellsItsArity() {
        JavaFile file = file("Arities", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var safe = cb.variableArity().declareStaticMethod("safe", PrimitiveToken.INT, LISTS);
                var plain = cb.variableArity().declareStaticMethod("plain", PrimitiveToken.INT, STRINGS);
                assertThat(safe.arity()).isEqualTo(Arity.SAFE_VARARGS);
                assertThat(plain.arity()).isEqualTo(Arity.VARIABLE);
                var bodies = cb.bodies();
                bodies.define(safe, (b, each) -> b.return_(literal(0)));
                bodies.define(plain, (b, each) -> b.return_(literal(0)));
                return bodies;
            }
        });
        assertThat(file.render()).contains("@SafeVarargs");
    }

    @Test
    void theArrayOfASafeVarargsParameterIsNoValueAsJavacWarnsOfHeapPollutionWhereItLeavesTheMember() {
        // return xs;
        JavacVerdict.asJavacWarns(
                () -> staticOf(LISTS, (b, xs) -> b.return_(xs)),
                HEAP_POLLUTION,
                "@SafeVarargs static List<String>[] m(List<String>... xs) { return xs; }",
                "Varargs method could cause heap pollution");
        // List<String>[] copy = xs;
        JavacVerdict.asJavacWarns(
                () -> staticOf(PrimitiveToken.INT, (b, xs) -> b.let(LISTS, xs, copy -> b.return_(length(LISTS, copy)))),
                HEAP_POLLUTION,
                "@SafeVarargs static int m(List<String>... xs) { List<String>[] copy = xs; return copy.length; }",
                "Varargs method could cause heap pollution");
        // Objects.hash((Object[]) xs)
        JavacVerdict.asJavacWarns(
                () -> staticOf(PrimitiveToken.INT, (b, xs) -> b.return_(staticCall(Objects_.hash_ObjectArray, xs))),
                HEAP_POLLUTION,
                "@SafeVarargs static int m(List<String>... xs) { return Objects.hash((Object[]) xs); }",
                "Varargs method could cause heap pollution");
        // xs.clone()
        JavacVerdict.asJavacWarns(
                () -> staticOf(LISTS, (b, xs) -> b.return_(cloneArray(LISTS, xs))),
                HEAP_POLLUTION,
                "@SafeVarargs static List<String>[] m(List<String>... xs) { return xs.clone(); }",
                "Varargs method could cause heap pollution");
        // () -> xs
        Supplier_<List<String>[]> supplier = new Supplier_<>(LISTS);
        JavacVerdict.asJavacWarns(
                () -> staticOf(supplier.token, (b, xs) -> b.return_(lambda(supplier.sam, () -> xs))),
                HEAP_POLLUTION,
                "@SafeVarargs static Supplier<List<String>[]> m(List<String>... xs) { return () -> xs; }",
                "Varargs method could cause heap pollution");
    }

    @Test
    void theArrayIsNeitherComparedNorAskedItsHashCodeThoughJavacAcceptsBoth() {
        JavacVerdict.unlikeJavac(
                () -> staticOf(PrimitiveToken.BOOLEAN, (b, xs) -> b.return_(eqRef(xs, literalNull(LISTS)))),
                HEAP_POLLUTION,
                "@SafeVarargs static boolean m(List<String>... xs) { return xs == null; }");
        JavacVerdict.unlikeJavac(
                () -> staticOf(PrimitiveToken.INT, (b, xs) -> b.return_(call(xs, Object_.hashCode))),
                HEAP_POLLUTION,
                "@SafeVarargs static int m(List<String>... xs) { return xs.hashCode(); }");
    }

    @Test
    void javacTakesSafeVarargsOfAFinalOrStaticMethodAndOfNoOtherAndWarnsOfItWhereItIsRedundant() {
        // what the declarations above are written to: the annotation on a method that can be overridden is an error
        CompilationSubject.assertThat(
                        JavacVerdict.compiled("@SafeVarargs int m(List<String>... xs) { return xs.length; }"))
                .hadErrorContaining("is neither final nor private");
        CompilationSubject.assertThat(
                        JavacVerdict.compiled("@SafeVarargs final int m(List<String>... xs) { return xs.length; }"))
                .succeededWithoutWarnings();
        CompilationSubject.assertThat(
                        JavacVerdict.compiled("@SafeVarargs static int m(List<String>... xs) { return xs.length; }"))
                .succeededWithoutWarnings();
        // and the same member with no annotation is the warning the declaration is made to avoid
        JavacVerdict.javacWarns("static int m(List<String>... xs) { return xs.length; }", "Possible heap pollution");
        // reifiable elements: the annotation is the warning
        JavacVerdict.javacWarns(
                "@SafeVarargs static int m(String... xs) { return xs.length; }",
                "Varargs element type java.lang.String is reifiable");
    }
}
