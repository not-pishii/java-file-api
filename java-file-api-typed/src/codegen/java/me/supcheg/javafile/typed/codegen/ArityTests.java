package me.supcheg.javafile.typed.codegen;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.StaticFieldAccessExpr;
import me.supcheg.javafile.code.StaticMethodCallExpr;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.supcheg.javafile.code.Exprs.cast;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.lambda;
import static me.supcheg.javafile.code.Exprs.newArrayOf;
import static me.supcheg.javafile.code.Exprs.new_;
import static me.supcheg.javafile.code.Exprs.staticCall;
import static me.supcheg.javafile.code.Exprs.staticField;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.ASSERTJ_ASSERTIONS;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.EXPRESSIONS;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.LIST;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.TEST;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.applied;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.exact;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.exactType;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.facts;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.generated;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.typed;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.unbounded;

/// The tests of the arity families, a class for an arity, and the classes they stand on.
///
/// For each arity `n` the tests declare three classes with the typed layer, over the fixtures of that arity
/// ([FixtureFiles]), so that every form the generator makes is used:
///
/// - `Plain{n}`, which extends `Object` and implements `Widening{n}`: calls (`call`, `voidCall`, `staticCall`,
///   `voidStaticCall`, `new_`, also with the arguments of a last parameter of variable arity one by one), lambdas,
///   declarations of every kind (by name, of variable arity, of overrides by a witness), the intentions of the
///   header (`overriding`), and the bodies of all of them;
/// - `Sub{n}`, which extends `Wide{n}`: the overrides of its methods, `superCall`, `inherited`, `super_` by a
///   `CtorRefN`;
/// - `Abs{n}`, which extends `Abstract{n}`: `super_` by a `SuperCtorRefN`.
///
/// The classes of all arities are rendered and compiled together, in one run of javac, under `-Xlint:all`, and
/// run: what each member tells is what the test of its arity expects.
final class ArityTests {
    private ArityTests() {}

    private static final String PROBES = "me.supcheg.example.arity";
    private static final String FIXTURE_FACTS =
            "me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures";

    private static final ClassDesc JAVA_FILE = ClassDesc.of("me.supcheg.javafile", "JavaFile");
    private static final ClassDesc CLASS_DESC = ClassDesc.of("java.lang.constant", "ClassDesc");
    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang", "Override");
    private static final ClassDesc THROWABLE = ClassDesc.of("java.lang", "Throwable");
    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc TYPED_JAVA_FILE = typed("TypedJavaFile");
    private static final ClassDesc HEADER_SPEC = TYPED_JAVA_FILE.nested("TypedClassHeaderSpec");
    private static final ClassDesc CLASS_HEADER = typed("ClassHeader");
    private static final ClassDesc MEMBER_BODIES = typed("MemberBodies");
    private static final ClassDesc UNSAFE_FACTS = facts("UnsafeFacts");
    private static final ClassDesc ARRAY_TOKEN = facts("ArrayToken");
    private static final ClassDesc PRIMITIVE_TOKEN = facts("PrimitiveToken");
    private static final ClassDesc STRING_FACTS =
            ClassDesc.of("me.supcheg.javafile.typed.testfacts.java.lang", "String_");
    private static final ClassDesc INTEGER_FACTS =
            ClassDesc.of("me.supcheg.javafile.typed.testfacts.java.lang", "Integer_");
    private static final ClassDesc ARITY_FORMS = typed("ArityForms");
    private static final ClassDesc ARITY_FILES = typed("ArityFiles");
    private static final ClassDesc EXECUTION = ClassDesc.of("org.junit.jupiter.api.parallel", "Execution");
    private static final ClassDesc EXECUTION_MODE = ClassDesc.of("org.junit.jupiter.api.parallel", "ExecutionMode");

    /// Every file of the tests: the fixtures, the tests of each arity, and what lists them.
    static List<JavaFile> files(int max) {
        return Stream.of(
                        IntStream.rangeClosed(0, max)
                                .boxed()
                                .flatMap(n -> Stream.of(
                                        FixtureFiles.wide(n),
                                        FixtureFiles.abstractClass(n),
                                        FixtureFiles.widening(n),
                                        FixtureFiles.fn(n),
                                        FixtureFiles.doing(n))),
                        Stream.of(FixtureFiles.factsHolder(max), arityFiles(max)),
                        IntStream.rangeClosed(0, max).mapToObj(ArityTests::arityTest))
                .flatMap(files -> files)
                .toList();
    }

    // ------------------------------------------------------------------
    // Names: the facts of the fixtures
    // ------------------------------------------------------------------

    private static ClassDesc factsOf(String fixture, int n) {
        return ClassDesc.of(FIXTURE_FACTS, fixture + n + "_");
    }

    private static List<String> types(int strings, String... more) {
        return Stream.concat(Collections.nCopies(strings, "String").stream(), Stream.of(more))
                .toList();
    }

    /// The name of the fact of a method: its name and the simple names of its parameter types.
    private static String methodFact(String name, int strings, String... more) {
        List<String> types = types(strings, more);
        return types.isEmpty() ? name : name + "_" + String.join("_", types);
    }

    private static String ctorFact(int strings, String... more) {
        return "new_" + String.join("_", types(strings, more));
    }

    /// The name of the fact of a constructor of an abstract class, which only a subclass calls.
    private static String superFact(int strings, String... more) {
        return "super_" + String.join("_", types(strings, more));
    }

    private static Expr fact(String fixture, int n, String member) {
        return staticField(factsOf(fixture, n), member);
    }

    private static Expr token(String fixture, int n) {
        return fact(fixture, n, "TOKEN");
    }

    // ------------------------------------------------------------------
    // Expressions of the typed layer, written as the code of a generated class
    // ------------------------------------------------------------------

    private static Expr x(String method, Expr... args) {
        return staticCall(EXPRESSIONS, method, args);
    }

    private static Expr lit(String value) {
        return x("literal", Exprs.literal(value));
    }

    private static Expr concat(Expr left, Expr right) {
        return x("concat", left, right);
    }

    /// `left + "/" + right`.
    private static Expr slash(Expr left, Expr right) {
        return concat(concat(left, lit("/")), right);
    }

    private static List<Expr> ps(String prefix, int from, int to) {
        return IntStream.rangeClosed(from, to)
                .mapToObj(i -> (Expr) field(prefix + i))
                .toList();
    }

    private static List<String> psNames(String prefix, int from, int to) {
        return IntStream.rangeClosed(from, to).mapToObj(i -> prefix + i).toList();
    }

    /// `tag`, or `tag:` and the last argument: what a member of a probe tells of its arguments.
    private static Expr tagged(String tag, int n) {
        return n == 0 ? lit(tag) : concat(lit(tag + ":"), field("p" + n));
    }

    private static Expr taggedBy(String tag, String prefix, int n) {
        return n == 0 ? lit(tag) : concat(lit(tag + ":"), field(prefix + n));
    }

    private static String expectedTag(String tag, int n) {
        return n == 0 ? tag : tag + ":a" + n;
    }

    /// `b.exec(e1).exec(e2)`.
    private static Expr execs(String block, List<Expr> effects) {
        Expr chain = field(block);
        for (Expr effect : effects) {
            chain = chain.call("exec", effect);
        }
        return chain;
    }

    private static Expr ending(String block, List<Expr> effects) {
        return execs(block, effects).call("end");
    }

    private static Expr returning(String block, List<Expr> effects, Expr value) {
        return execs(block, effects).call("return_", value);
    }

    /// `b.let(token, init, name -> rest)`.
    private static Expr let(String block, Expr token, Expr init, String name, Expr rest) {
        return field(block).call("let", token, init, lambda(List.of(name), rest));
    }

    private static Expr stringToken() {
        return staticField(STRING_FACTS, "TOKEN");
    }

    private static List<Expr> stringTokens(int n) {
        return Collections.nCopies(n, stringToken());
    }

    private static Expr stringsToken() {
        return staticCall(ARRAY_TOKEN, "of", stringToken());
    }

    private static Expr integersToken() {
        return staticCall(ARRAY_TOKEN, "of", staticField(INTEGER_FACTS, "TOKEN"));
    }

    /// `varargs(STRINGS, "x", "y")`: the arguments of a last parameter of variable arity, one by one.
    private static Expr xy() {
        return x("varargs", stringsToken(), lit("x"), lit("y"));
    }

    private static Expr oneTwo() {
        return x(
                "varargs",
                integersToken(),
                x("box", staticField(PRIMITIVE_TOKEN, "INT"), x("literal", Exprs.literal(1))),
                x("box", staticField(PRIMITIVE_TOKEN, "INT"), x("literal", Exprs.literal(2))));
    }

    private static List<Expr> join(List<? extends Expr> first, List<? extends Expr> second) {
        return Stream.<Expr>concat(first.stream(), second.stream()).toList();
    }

    private static List<Expr> with(List<Expr> leading, Expr last) {
        return Stream.concat(leading.stream(), Stream.of(last)).toList();
    }

    private static Expr call(String method, Expr... args) {
        return x(method, args);
    }

    private static Expr callAll(String method, List<Expr> args) {
        return x(method, args.toArray(Expr[]::new));
    }

    /// `Expressions.<method>(fact, args...)`.
    private static Expr calling(String method, Expr fact, List<Expr> args) {
        return callAll(method, Stream.concat(Stream.of(fact), args.stream()).toList());
    }

    /// `Expressions.<method>(target, fact, args...)`.
    private static Expr callingOn(String method, Expr target, Expr fact, List<Expr> args) {
        return callAll(
                method, Stream.concat(Stream.of(target, fact), args.stream()).toList());
    }

    // ------------------------------------------------------------------
    // The classes the tests declare
    // ------------------------------------------------------------------

    /// `Probe::build` as the code of a nested class `name` that implements `TypedClassHeaderSpec`.
    private static void specification(ClassBuilder cb, ClassDesc owner, String name, Consumer<CodeBuilder> body) {
        cb.withNestedClass(owner.nested(name), nb -> {
            nb.withExactModifiers(Set.of(Modifier.STATIC, Modifier.FINAL));
            nb.withInterface(HEADER_SPEC);
            nb.withMethod("build", applied(MEMBER_BODIES, exact("Self"), unbounded()), mb -> {
                mb.withAnnotation(OVERRIDE);
                mb.withTypeParam("Self");
                mb.withParam("h", applied(CLASS_HEADER, exact("Self")));
                mb.withBody(body);
            });
        });
    }

    /// `static JavaFile name() { return TypedJavaFile.declareClass(..., desc, new Spec()); }`.
    private static void factory(ClassBuilder cb, ClassDesc owner, String name, String probe, String specification) {
        cb.withMethod(name, Types.of(JAVA_FILE), mb -> {
            mb.withModifiers(Modifier.STATIC);
            mb.withBody(b -> b.return_(staticCall(
                    TYPED_JAVA_FILE,
                    "declareClass",
                    staticCall(UNSAFE_FACTS, "unverifiedClasspath"),
                    staticCall(CLASS_DESC, "of", Exprs.literal(PROBES), Exprs.literal(probe)),
                    new_(owner.nested(specification)))));
        });
    }

    private static void define(CodeBuilder b, Expr member, Expr body) {
        b.exprStatement(field("bodies").call("define", member, body));
    }

    private static Expr method(String variable) {
        return field(variable).call("method");
    }

    private static Expr bodyOf(List<String> params, Expr result) {
        return lambda(params, result);
    }

    /// `(b, self, p1, ..., pn) -> ...`.
    private static List<String> instanceParams(int n) {
        return Stream.concat(Stream.of("b", "self"), psNames("p", 1, n).stream())
                .toList();
    }

    /// `(b, p1, ..., pn) -> ...`.
    private static List<String> staticParams(int n) {
        return Stream.concat(Stream.of("b"), psNames("p", 1, n).stream()).toList();
    }

    /// The static note of the fixture: what a body that does nothing else tells.
    private static Expr note(int n, List<Expr> args) {
        return calling("voidStaticCall", fact("Wide", n, methodFact("note", n)), args);
    }

    // ------------------------------------------------------------------
    // Plain{n}
    // ------------------------------------------------------------------

    private static void plain(CodeBuilder b, int n) {
        Expr h = field("h");
        List<Expr> p = ps("p", 1, n);
        b.localVar("becoming", h.call("becoming", token("Widening", n)));
        b.localVar("inhO", h.call("overriding", fact("Widening", n, methodFact("inh", n))));
        b.localVar("inhVoidO", h.call("overriding", fact("Widening", n, methodFact("inhVoid", n))));
        b.localVar("narrowO", h.call("overriding", fact("Widening", n, methodFact("narrow", n)), stringToken()));
        b.localVar("plainO", h.call("overriding", fact("Widening", n, methodFact("plain", n))));
        b.localVar("selfO", h.call("overriding", fact("Widening", n, methodFact("self", n)), field("becoming")));
        b.localVar("takeO", h.call("overriding", fact("Widening", n, methodFact("take", n))));
        b.localVar("oh", h.call("extendsObject"));
        b.localVar(
                "asWidening",
                field("oh")
                        .call(
                                "implements_",
                                implementation(n, "inhO", "inhVoidO", "narrowO", "plainO", "selfO", "takeO", h)));
        b.localVar("cb", field("oh").call("members"));
        b.localVar(
                "givenM",
                field("cb").call("declareOverride", field("asWidening"), fact("Widening", n, methodFact("given", n))));
        b.localVar(
                "chosenM",
                field("cb")
                        .call(
                                "declareOverride",
                                field("asWidening"),
                                fact("Widening", n, methodFact("chosen", n)),
                                stringToken()));
        b.localVar(
                "chainedM",
                field("cb")
                        .call(
                                "declareOverride",
                                field("asWidening"),
                                fact("Widening", n, methodFact("chained", n)),
                                field("asWidening")));
        b.localVar(
                "heldM",
                field("cb").call("declareOverride", field("asWidening"), fact("Widening", n, methodFact("held", n))));
        b.localVar(
                "ctorM", field("cb").call("declareConstructor", stringTokens(n).toArray(Expr[]::new)));
        b.localVar(
                "applyM",
                field("cb")
                        .call(
                                "declareMethod",
                                join(List.of(Exprs.literal("apply"), stringToken()), stringTokens(n))
                                        .toArray(Expr[]::new)));
        b.localVar(
                "runM",
                field("cb")
                        .call(
                                "declareVoidMethod",
                                join(List.of(Exprs.literal("run")), stringTokens(n))
                                        .toArray(Expr[]::new)));
        b.localVar(
                "makeM",
                field("cb")
                        .call(
                                "declareStaticMethod",
                                join(List.of(Exprs.literal("make"), stringToken()), stringTokens(n))
                                        .toArray(Expr[]::new)));
        b.localVar(
                "logM",
                field("cb")
                        .call(
                                "declareVoidStaticMethod",
                                join(List.of(Exprs.literal("log")), stringTokens(n))
                                        .toArray(Expr[]::new)));
        b.localVar(
                "lamM",
                field("cb")
                        .call(
                                "declareMethod",
                                join(List.of(Exprs.literal("lam"), stringToken()), stringTokens(n))
                                        .toArray(Expr[]::new)));
        if (n >= 1) {
            Expr variable = field("cb").call("variableArity");
            List<Expr> before = stringTokens(n - 1);
            b.localVar(
                    "vctorM",
                    variable.call(
                            "declareConstructor", with(before, stringsToken()).toArray(Expr[]::new)));
            b.localVar(
                    "vapplyM",
                    variable.call(
                            "declareMethod",
                            join(List.of(Exprs.literal("applyAll"), stringToken()), with(before, stringsToken()))
                                    .toArray(Expr[]::new)));
            b.localVar(
                    "vrunM",
                    variable.call(
                            "declareVoidMethod",
                            join(List.of(Exprs.literal("runAll")), with(before, stringsToken()))
                                    .toArray(Expr[]::new)));
            b.localVar(
                    "vmakeM",
                    variable.call(
                            "declareStaticMethod",
                            join(List.of(Exprs.literal("makeAll"), stringToken()), with(before, stringsToken()))
                                    .toArray(Expr[]::new)));
            b.localVar(
                    "vlogM",
                    variable.call(
                            "declareVoidStaticMethod",
                            join(List.of(Exprs.literal("logAll")), with(before, stringsToken()))
                                    .toArray(Expr[]::new)));
        }
        b.localVar("bodies", field("cb").call("bodies"));

        define(b, method("inhO"), bodyOf(instanceParams(n), field("b").call("return_", tagged("inh", n))));
        define(b, method("inhVoidO"), bodyOf(instanceParams(n), ending("b", List.of(note(n, p)))));
        define(b, method("narrowO"), bodyOf(instanceParams(n), field("b").call("return_", tagged("narrow", n))));
        define(b, method("plainO"), bodyOf(instanceParams(n), field("b").call("return_", tagged("plain", n))));
        define(b, method("selfO"), bodyOf(instanceParams(n), field("b").call("return_", field("self"))));
        define(b, method("takeO"), bodyOf(instanceParams(n), ending("b", List.of(note(n, p)))));
        define(b, field("givenM"), bodyOf(instanceParams(n), field("b").call("return_", tagged("given", n))));
        define(b, field("chosenM"), bodyOf(instanceParams(n), field("b").call("return_", tagged("chosen", n))));
        define(b, field("chainedM"), bodyOf(instanceParams(n), field("b").call("return_", field("self"))));
        define(b, field("heldM"), bodyOf(instanceParams(n), ending("b", List.of(note(n, p)))));
        define(b, field("ctorM"), bodyOf(instanceParams(n), ending("b", List.of(note(n, p)))));
        define(b, field("applyM"), bodyOf(instanceParams(n), apply(n)));
        define(b, field("runM"), bodyOf(instanceParams(n), ending("b", List.of(note(n, p)))));
        define(
                b,
                field("makeM"),
                bodyOf(
                        staticParams(n),
                        field("b").call("return_", calling("staticCall", fact("Wide", n, methodFact("join", n)), p))));
        define(b, field("logM"), bodyOf(staticParams(n), ending("b", List.of(note(n, p)))));
        define(b, field("lamM"), bodyOf(instanceParams(n), lambdas(n)));
        if (n >= 1) {
            List<Expr> lead = ps("p", 1, n - 1);
            List<String> withRest = Stream.concat(instanceParams(n - 1).stream(), Stream.of("rest"))
                    .toList();
            List<String> staticWithRest = Stream.concat(staticParams(n - 1).stream(), Stream.of("rest"))
                    .toList();
            Expr noteAll = calling(
                    "voidStaticCall",
                    fact("Wide", n, methodFact("noteAll", n - 1, "StringArray")),
                    with(lead, field("rest")));
            define(b, field("vctorM"), bodyOf(withRest, ending("b", List.of(noteAll))));
            define(b, field("vapplyM"), bodyOf(withRest, applyAll(n)));
            define(b, field("vrunM"), bodyOf(withRest, ending("b", List.of(noteAll))));
            define(
                    b,
                    field("vmakeM"),
                    bodyOf(
                            staticWithRest,
                            field("b")
                                    .call(
                                            "return_",
                                            calling(
                                                    "staticCall",
                                                    fact("Wide", n, methodFact("joinAll", n - 1, "StringArray")),
                                                    with(lead, field("rest"))))));
            define(b, field("vlogM"), bodyOf(staticWithRest, ending("b", List.of(noteAll))));
        }
        b.return_(field("bodies"));
    }

    /// `Widening{n}_.implement(h.self()).inh_..(inhO)...take_..(takeO)`: the steps of the proof, one for each abstract
    /// method, in the order of the names of their facts.
    private static Expr implementation(
            int n, String inh, String inhVoid, String narrow, String plain, String self, String take, Expr h) {
        Map<String, String> how = Map.of(
                methodFact("inh", n), inh,
                methodFact("inhVoid", n), inhVoid,
                methodFact("narrow", n), narrow,
                methodFact("plain", n), plain,
                methodFact("self", n), self,
                methodFact("take", n), take);
        return how.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .reduce(
                        (Expr) staticCall(factsOf("Widening", n), "implement", h.call("self")),
                        (proof, step) -> proof.call(step.getKey(), field(step.getValue())),
                        (_, last) -> last);
    }

    /// The body of `apply`: every form of a call, by the fact of a method, a static method, a constructor.
    private static Expr apply(int n) {
        List<Expr> p = ps("p", 1, n);
        Expr w = field("w");
        Expr told = x("field", w, fact("Wide", n, "told"));
        Expr result = slash(
                slash(
                        callingOn("call", w, fact("Wide", n, methodFact("pick", n)), p),
                        calling("staticCall", fact("Wide", n, methodFact("join", n)), p)),
                told);
        Expr effects = returning(
                "b", List.of(callingOn("voidCall", w, fact("Wide", n, methodFact("put", n)), p), note(n, p)), result);
        return let("b", token("Wide", n), calling("new_", fact("Wide", n, ctorFact(n)), p), "w", effects);
    }

    /// The body of `applyAll`: the same, the arguments of the last parameter of variable arity one by one.
    private static Expr applyAll(int n) {
        List<Expr> lead = ps("p", 1, n - 1);
        Expr w = field("w");
        Expr told = x("field", w, fact("Wide", n, "told"));
        Expr result = slash(
                slash(
                        callingOn(
                                "call",
                                w,
                                fact("Wide", n, methodFact("pickAll", n - 1, "StringArray")),
                                with(lead, xy())),
                        calling(
                                "staticCall",
                                fact("Wide", n, methodFact("joinAll", n - 1, "StringArray")),
                                with(lead, xy()))),
                told);
        Expr effects = returning(
                "b",
                List.of(
                        callingOn(
                                "voidCall",
                                w,
                                fact("Wide", n, methodFact("putAll", n - 1, "StringArray")),
                                with(lead, xy())),
                        calling(
                                "voidStaticCall",
                                fact("Wide", n, methodFact("noteAll", n - 1, "StringArray")),
                                with(lead, xy()))),
                result);
        return let(
                "b",
                token("Wide", n),
                calling("new_", fact("Wide", n, ctorFact(n - 1, "IntegerArray")), with(lead, oneTwo())),
                "w",
                effects);
    }

    /// The body of `lam`: the four lambdas, an expression and a block one of a result and of none.
    private static Expr lambdas(int n) {
        List<Expr> p = ps("p", 1, n);
        List<String> q = psNames("q", 1, n);
        List<Expr> qs = ps("q", 1, n);
        List<String> blockQ = Stream.concat(Stream.of("lb"), q.stream()).toList();
        Expr fn = calling("lambda", fact("Fn", n, "sam"), List.of(lambda(q, taggedBy("fn", "q", n))));
        Expr blockFn = calling(
                "lambdaBlock",
                fact("Fn", n, "sam"),
                List.of(lambda(blockQ, field("lb").call("return_", taggedBy("blk", "q", n)))));
        Expr doing = calling(
                "lambda",
                fact("Do", n, "sam"),
                List.of(lambda(q, calling("voidStaticCall", fact("Wide", n, methodFact("note", n)), qs))));
        Expr blockDoing = calling(
                "lambdaBlock",
                fact("Do", n, "sam"),
                List.of(lambda(
                        blockQ,
                        ending("lb", List.of(calling("voidStaticCall", fact("Wide", n, methodFact("note", n)), qs))))));
        Expr result = returning(
                "b",
                List.of(
                        callingOn("voidCall", field("d"), fact("Do", n, methodFact("accept", n)), p),
                        callingOn("voidCall", field("e"), fact("Do", n, methodFact("accept", n)), p)),
                slash(
                        callingOn("call", field("f"), fact("Fn", n, methodFact("apply", n)), p),
                        callingOn("call", field("g"), fact("Fn", n, methodFact("apply", n)), p)));
        return let(
                "b",
                token("Fn", n),
                fn,
                "f",
                let(
                        "b",
                        token("Fn", n),
                        blockFn,
                        "g",
                        let("b", token("Do", n), doing, "d", let("b", token("Do", n), blockDoing, "e", result))));
    }

    // ------------------------------------------------------------------
    // Sub{n}
    // ------------------------------------------------------------------

    private static void sub(CodeBuilder b, int n) {
        Expr h = field("h");
        List<Expr> p = ps("p", 1, n);
        b.localVar("sh", h.call("extends_", staticCall(factsOf("Wide", n), "extend", h.call("self"))));
        Expr sh = field("sh");
        b.localVar("becoming", sh.call("becoming", token("Widening", n)));
        b.localVar("inhI", sh.call("inherited", fact("Wide", n, methodFact("inh", n))));
        b.localVar("inhVoidI", sh.call("inherited", fact("Wide", n, methodFact("inhVoid", n))));
        b.localVar("narrowO", sh.call("overriding", fact("Widening", n, methodFact("narrow", n)), stringToken()));
        b.localVar("plainO", sh.call("overriding", fact("Widening", n, methodFact("plain", n))));
        b.localVar("selfO", sh.call("overriding", fact("Widening", n, methodFact("self", n)), field("becoming")));
        b.localVar("takeO", sh.call("overriding", fact("Widening", n, methodFact("take", n))));
        b.localVar(
                "asWidening",
                sh.call(
                        "implements_",
                        implementation(n, "inhI", "inhVoidI", "narrowO", "plainO", "selfO", "takeO", h)));
        b.localVar("cb", sh.call("members"));
        Expr cb = field("cb");
        b.localVar("asWide", cb.call("asSuper"));
        b.localVar("ctorA", cb.call("declareConstructor", stringTokens(n).toArray(Expr[]::new)));
        if (n >= 1) {
            b.localVar(
                    "ctorB",
                    cb.call(
                            "declareConstructor",
                            with(stringTokens(n - 1), staticField(INTEGER_FACTS, "TOKEN"))
                                    .toArray(Expr[]::new)));
        }
        b.localVar("pickM", cb.call("declareOverride", fact("Wide", n, methodFact("pick", n))));
        b.localVar("csM", cb.call("declareOverride", fact("Wide", n, methodFact("cs", n)), stringToken()));
        b.localVar("sameM", cb.call("declareOverride", fact("Wide", n, methodFact("same", n)), field("asWide")));
        b.localVar("putM", cb.call("declareOverride", fact("Wide", n, methodFact("put", n))));
        if (n >= 1) {
            b.localVar(
                    "viaAllM",
                    cb.call(
                            "declareMethod",
                            join(List.of(Exprs.literal("viaAll"), stringToken()), stringTokens(n - 1))
                                    .toArray(Expr[]::new)));
            b.localVar(
                    "viaAllVoidM",
                    cb.call(
                            "declareVoidMethod",
                            join(List.of(Exprs.literal("viaAllVoid")), stringTokens(n - 1))
                                    .toArray(Expr[]::new)));
        }
        b.localVar(
                "bodies",
                cb.call(
                        "bodies",
                        field("ctorA"),
                        lambda(
                                Stream.concat(Stream.of("c"), psNames("p", 1, n).stream())
                                        .toList(),
                                field("c")
                                        .call(
                                                "super_",
                                                join(
                                                                join(List.of(fact("Wide", n, ctorFact(n))), p),
                                                                List.of(lambda(
                                                                        List.of("b", "self"),
                                                                        field("b")
                                                                                .call("end"))))
                                                        .toArray(Expr[]::new)))));
        if (n >= 1) {
            List<Expr> lead = ps("p", 1, n - 1);
            define(
                    b,
                    field("ctorB"),
                    lambda(
                            Stream.concat(Stream.of("c"), psNames("p", 1, n).stream())
                                    .toList(),
                            field("c")
                                    .call(
                                            "super_",
                                            join(
                                                            join(
                                                                    List.of(fact(
                                                                            "Wide",
                                                                            n,
                                                                            ctorFact(n - 1, "IntegerArray"))),
                                                                    lead),
                                                            List.of(
                                                                    x(
                                                                            "varargs",
                                                                            integersToken(),
                                                                            field("p" + n),
                                                                            field("p" + n)),
                                                                    lambda(
                                                                            List.of("b", "self"),
                                                                            field("b")
                                                                                    .call("end"))))
                                                    .toArray(Expr[]::new))));
        }
        define(b, method("narrowO"), bodyOf(instanceParams(n), field("b").call("return_", tagged("narrow", n))));
        define(b, method("plainO"), bodyOf(instanceParams(n), field("b").call("return_", tagged("plain", n))));
        define(b, method("selfO"), bodyOf(instanceParams(n), field("b").call("return_", field("self"))));
        define(b, method("takeO"), bodyOf(instanceParams(n), ending("b", List.of())));
        define(
                b,
                field("pickM"),
                bodyOf(
                        instanceParams(n),
                        field("b")
                                .call(
                                        "return_",
                                        concat(
                                                lit("sub:"),
                                                field("bodies")
                                                        .call(
                                                                "superCall",
                                                                join(
                                                                                List.of(
                                                                                        field("self"),
                                                                                        fact(
                                                                                                "Wide",
                                                                                                n,
                                                                                                methodFact("pick", n))),
                                                                                p)
                                                                        .toArray(Expr[]::new))))));
        define(b, field("csM"), bodyOf(instanceParams(n), field("b").call("return_", tagged("cs", n))));
        define(b, field("sameM"), bodyOf(instanceParams(n), field("b").call("return_", field("self"))));
        define(
                b,
                field("putM"),
                bodyOf(
                        instanceParams(n),
                        ending(
                                "b",
                                List.of(field("bodies")
                                        .call(
                                                "superCall",
                                                join(List.of(field("self"), fact("Wide", n, methodFact("put", n))), p)
                                                        .toArray(Expr[]::new))))));
        if (n >= 1) {
            List<Expr> lead = ps("p", 1, n - 1);
            define(
                    b,
                    field("viaAllM"),
                    bodyOf(
                            instanceParams(n - 1),
                            field("b")
                                    .call(
                                            "return_",
                                            field("bodies")
                                                    .call(
                                                            "superCall",
                                                            join(
                                                                            List.of(
                                                                                    field("self"),
                                                                                    fact(
                                                                                            "Wide",
                                                                                            n,
                                                                                            methodFact(
                                                                                                    "pickAll",
                                                                                                    n - 1,
                                                                                                    "StringArray"))),
                                                                            with(lead, xy()))
                                                                    .toArray(Expr[]::new)))));
            define(
                    b,
                    field("viaAllVoidM"),
                    bodyOf(
                            instanceParams(n - 1),
                            ending(
                                    "b",
                                    List.of(field("bodies")
                                            .call(
                                                    "superCall",
                                                    join(
                                                                    List.of(
                                                                            field("self"),
                                                                            fact(
                                                                                    "Wide",
                                                                                    n,
                                                                                    methodFact(
                                                                                            "putAll",
                                                                                            n - 1,
                                                                                            "StringArray"))),
                                                                    with(lead, xy()))
                                                            .toArray(Expr[]::new))))));
        }
        b.return_(field("bodies"));
    }

    // ------------------------------------------------------------------
    // Abs{n}
    // ------------------------------------------------------------------

    private static void abstractSub(CodeBuilder b, int n) {
        Expr h = field("h");
        List<Expr> p = ps("p", 1, n);
        b.localVar("sh", h.call("extends_", staticCall(factsOf("Abstract", n), "extend", h.call("self"))));
        b.localVar("cb", field("sh").call("members"));
        Expr cb = field("cb");
        b.localVar("ctorA", cb.call("declareConstructor", stringTokens(n).toArray(Expr[]::new)));
        if (n >= 1) {
            b.localVar(
                    "ctorB",
                    cb.call(
                            "declareConstructor",
                            with(stringTokens(n - 1), staticField(INTEGER_FACTS, "TOKEN"))
                                    .toArray(Expr[]::new)));
        }
        b.localVar(
                "bodies",
                cb.call(
                        "bodies",
                        field("ctorA"),
                        lambda(
                                Stream.concat(Stream.of("c"), psNames("p", 1, n).stream())
                                        .toList(),
                                field("c")
                                        .call(
                                                "super_",
                                                join(
                                                                join(List.of(fact("Abstract", n, superFact(n))), p),
                                                                List.of(lambda(
                                                                        List.of("b", "self"),
                                                                        field("b")
                                                                                .call("end"))))
                                                        .toArray(Expr[]::new)))));
        if (n >= 1) {
            List<Expr> lead = ps("p", 1, n - 1);
            define(
                    b,
                    field("ctorB"),
                    lambda(
                            Stream.concat(Stream.of("c"), psNames("p", 1, n).stream())
                                    .toList(),
                            field("c")
                                    .call(
                                            "super_",
                                            join(
                                                            join(
                                                                    List.of(fact(
                                                                            "Abstract",
                                                                            n,
                                                                            superFact(n - 1, "IntegerArray"))),
                                                                    lead),
                                                            List.of(
                                                                    x(
                                                                            "varargs",
                                                                            integersToken(),
                                                                            field("p" + n),
                                                                            field("p" + n)),
                                                                    lambda(
                                                                            List.of("b", "self"),
                                                                            field("b")
                                                                                    .call("end"))))
                                                    .toArray(Expr[]::new))));
        }
        b.return_(field("bodies"));
    }

    // ------------------------------------------------------------------
    // The test of an arity
    // ------------------------------------------------------------------

    private static ClassDesc test(int n) {
        return typed("Arity" + n + "FormsTest");
    }

    private static JavaFile arityTest(int n) {
        ClassDesc owner = test(n);
        return JavaFile.class_(owner, cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of());
            cb.withAnnotation(
                    EXECUTION, ab -> ab.withMember("value", AnnotationValues.enumValue(EXECUTION_MODE, "SAME_THREAD")));
            cb.withDoc(d -> d.paragraph("The forms of the typed layer of arity " + n
                    + ": a class that extends `Object`, one that extends a class and one that extends an abstract"
                    + " class, declared with them, are compiled with the classes of the other arities in one run of"
                    + " javac, and run."));
            specification(cb, owner, "PlainSpec", b -> plain(b, n));
            specification(cb, owner, "SubSpec", b -> sub(b, n));
            specification(cb, owner, "AbsSpec", b -> abstractSub(b, n));
            factory(cb, owner, "plain", "Plain" + n, "PlainSpec");
            factory(cb, owner, "sub", "Sub" + n, "SubSpec");
            factory(cb, owner, "abs", "Abs" + n, "AbsSpec");
            testMethod(cb, "plain", b -> plainTest(b, n));
            testMethod(cb, "sub", b -> subTest(b, n));
            testMethod(cb, "abs", b -> absTest(b, n));
        });
    }

    private static void testMethod(ClassBuilder cb, String name, Consumer<CodeBuilder> body) {
        cb.withVoidMethod(name + "Forms", mb -> {
            mb.withAnnotation(TEST);
            mb.withThrows(THROWABLE);
            mb.withBody(body);
        });
    }

    private static JavaFile arityFiles(int max) {
        return JavaFile.class_(ARITY_FILES, cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of(Modifier.FINAL));
            cb.withMethod("all", applied(LIST, exactType(Types.of(JAVA_FILE))), mb -> {
                mb.withModifiers(Modifier.STATIC);
                mb.withBody(b -> b.return_(staticCall(
                        LIST,
                        "of",
                        IntStream.rangeClosed(0, max)
                                .boxed()
                                .flatMap(n ->
                                        Stream.of("plain", "sub", "abs").map(name -> (Expr) staticCall(test(n), name)))
                                .toList())));
            });
        });
    }

    // ------------------------------------------------------------------
    // What the classes tell
    // ------------------------------------------------------------------

    /// `a1,...,an` and its like: what the fixtures tell of the arguments `a1` to `an`.
    private static String told(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(i -> "a" + i).collect(Collectors.joining(","));
    }

    /// The arguments of the tests: the strings `"a1"` to `"an"`.
    private static List<Expr> arguments(int from, int to) {
        return IntStream.rangeClosed(from, to)
                .mapToObj(i -> (Expr) Exprs.literal("a" + i))
                .toList();
    }

    private static Expr listOf(List<Expr> elements) {
        return staticCall(LIST, "of", elements);
    }

    private static StaticFieldAccessExpr noted(int n) {
        return staticField(FixtureFiles.fixture("Wide", n), "noted");
    }

    private static void check(CodeBuilder b, Expr actual, String expected) {
        b.exprStatement(
                staticCall(ASSERTJ_ASSERTIONS, "assertThat", actual).call("isEqualTo", Exprs.literal(expected)));
    }

    private static void same(CodeBuilder b, Expr actual, Expr expected) {
        b.exprStatement(staticCall(ASSERTJ_ASSERTIONS, "assertThat", actual).call("isSameAs", expected));
    }

    private static Expr create(String type, List<Expr> args) {
        return staticCall(ARITY_FORMS, "create", Exprs.literal(type), listOf(args));
    }

    private static StaticMethodCallExpr invoke(String type, Expr target, String name, List<Expr> args) {
        return staticCall(ARITY_FORMS, "invoke", Exprs.literal(type), target, Exprs.literal(name), listOf(args));
    }

    private static Expr nothing() {
        return Exprs.literalNull();
    }

    private static void reset(CodeBuilder b, int n) {
        b.assign(noted(n), Exprs.literal(""));
    }

    /// `a1,...,an` for the tests of arity `n`, and its first `n - 1` for the members of variable arity.
    private static String note(String prefix, String told) {
        return prefix + told + ";";
    }

    private static void plainTest(CodeBuilder b, int n) {
        String plain = "Plain" + n;
        List<Expr> a = arguments(1, n);
        String j = told(n);
        TypeRef widening = Types.of(FixtureFiles.fixture("Widening", n));
        reset(b, n);
        b.localVar("plain", Types.of(OBJECT), create(plain, a));
        check(b, noted(n), note("note:", j));
        reset(b, n);
        check(
                b,
                invoke(plain, field("plain"), "apply", a),
                "pick:" + j + "/join:" + j + "/new:" + j + "put:" + j + ";");
        check(b, noted(n), note("note:", j));
        reset(b, n);
        b.exprStatement(invoke(plain, field("plain"), "run", a));
        check(b, noted(n), note("note:", j));
        reset(b, n);
        b.exprStatement(invoke(plain, nothing(), "log", a));
        check(b, noted(n), note("note:", j));
        check(b, invoke(plain, nothing(), "make", a), "join:" + j);
        reset(b, n);
        check(b, invoke(plain, field("plain"), "lam", a), expectedTag("fn", n) + "/" + expectedTag("blk", n));
        check(b, noted(n), note("note:", j) + note("note:", j));
        b.localVar("widening", widening, cast(widening, field("plain")));
        Expr w = field("widening");
        check(b, w.call("inh", a.toArray(Expr[]::new)), expectedTag("inh", n));
        check(b, w.call("narrow", a.toArray(Expr[]::new)), expectedTag("narrow", n));
        check(b, w.call("plain", a.toArray(Expr[]::new)), expectedTag("plain", n));
        same(b, w.call("self", a.toArray(Expr[]::new)), field("plain"));
        check(b, w.call("given", a.toArray(Expr[]::new)), expectedTag("given", n));
        check(b, w.call("chosen", a.toArray(Expr[]::new)), expectedTag("chosen", n));
        same(b, w.call("chained", a.toArray(Expr[]::new)), field("plain"));
        reset(b, n);
        b.exprStatement(w.call("inhVoid", a.toArray(Expr[]::new)));
        b.exprStatement(w.call("take", a.toArray(Expr[]::new)));
        b.exprStatement(w.call("held", a.toArray(Expr[]::new)));
        check(b, noted(n), note("note:", j) + note("note:", j) + note("note:", j));
        if (n >= 1) {
            String lead = told(n - 1);
            // cast: a lone array in `List.of(array)` would be the elements
            Expr rest = cast(Types.OBJECT, newArrayOf(Types.STRING, List.of(Exprs.literal("r0"), Exprs.literal("r1"))));
            List<Expr> variable = with(arguments(1, n - 1), rest);
            reset(b, n);
            b.localVar("variable", Types.of(OBJECT), create(plain, variable));
            check(b, noted(n), "noteAll:" + lead + "|[r0, r1];");
            reset(b, n);
            check(
                    b,
                    invoke(plain, field("variable"), "applyAll", variable),
                    "pickAll:" + lead + "|[x, y]/joinAll:" + lead + "|[x, y]/newAll:" + lead + "|[1, 2]putAll:" + lead
                            + "|[x, y];");
            check(b, noted(n), "noteAll:" + lead + "|[x, y];");
            reset(b, n);
            b.exprStatement(invoke(plain, field("variable"), "runAll", variable));
            check(b, noted(n), "noteAll:" + lead + "|[r0, r1];");
            reset(b, n);
            b.exprStatement(invoke(plain, nothing(), "logAll", variable));
            check(b, noted(n), "noteAll:" + lead + "|[r0, r1];");
            check(b, invoke(plain, nothing(), "makeAll", variable), "joinAll:" + lead + "|[r0, r1]");
        }
    }

    private static void subTest(CodeBuilder b, int n) {
        String sub = "Sub" + n;
        List<Expr> a = arguments(1, n);
        String j = told(n);
        TypeRef wide = Types.of(FixtureFiles.fixture("Wide", n));
        TypeRef widening = Types.of(FixtureFiles.fixture("Widening", n));
        b.localVar("sub", Types.of(OBJECT), create(sub, a));
        b.localVar("wide", wide, cast(wide, field("sub")));
        Expr w = field("wide");
        check(b, w.field("told"), "new:" + j);
        check(b, w.call("pick", a.toArray(Expr[]::new)), "sub:pick:" + j);
        check(b, w.call("cs", a.toArray(Expr[]::new)), expectedTag("cs", n));
        same(b, w.call("same", a.toArray(Expr[]::new)), field("sub"));
        b.assign(w.field("told"), Exprs.literal(""));
        b.exprStatement(w.call("put", a.toArray(Expr[]::new)));
        check(b, w.field("told"), "put:" + j + ";");
        b.localVar("widening", widening, cast(widening, field("sub")));
        Expr g = field("widening");
        check(b, g.call("inh", a.toArray(Expr[]::new)), "inh:" + j);
        b.assign(w.field("told"), Exprs.literal(""));
        b.exprStatement(g.call("inhVoid", a.toArray(Expr[]::new)));
        check(b, w.field("told"), "inhVoid:" + j + ";");
        check(b, g.call("narrow", a.toArray(Expr[]::new)), expectedTag("narrow", n));
        check(b, g.call("plain", a.toArray(Expr[]::new)), expectedTag("plain", n));
        same(b, g.call("self", a.toArray(Expr[]::new)), field("sub"));
        if (n >= 1) {
            String lead = told(n - 1);
            List<Expr> before = arguments(1, n - 1);
            b.assign(w.field("told"), Exprs.literal(""));
            b.exprStatement(invoke(sub, field("sub"), "viaAllVoid", before));
            check(b, w.field("told"), "putAll:" + lead + "|[x, y];");
            check(b, invoke(sub, field("sub"), "viaAll", before), "pickAll:" + lead + "|[x, y]");
            b.localVar("other", Types.of(OBJECT), create(sub, with(before, Exprs.literal(7))));
            check(b, cast(wide, field("other")).field("told"), "newAll:" + lead + "|[7, 7]");
        }
    }

    private static void absTest(CodeBuilder b, int n) {
        String abs = "Abs" + n;
        TypeRef base = Types.of(FixtureFiles.fixture("Abstract", n));
        b.localVar("abs", Types.of(OBJECT), create(abs, arguments(1, n)));
        check(b, cast(base, field("abs")).field("told"), "abs:" + told(n));
        if (n >= 1) {
            b.localVar("other", Types.of(OBJECT), create(abs, with(arguments(1, n - 1), Exprs.literal(7))));
            check(b, cast(base, field("other")).field("told"), "absAll:" + told(n - 1) + "|[7, 7]");
        }
    }
}
