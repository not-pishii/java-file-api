package me.supcheg.javafile.example.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.example.typed.facts.java.lang.CharSequence_;
import me.supcheg.javafile.example.typed.facts.java.lang.Comparable_;
import me.supcheg.javafile.example.typed.facts.java.lang.Double_;
import me.supcheg.javafile.example.typed.facts.java.lang.Integer_;
import me.supcheg.javafile.example.typed.facts.java.lang.Math_;
import me.supcheg.javafile.example.typed.facts.java.lang.NumberFormatException_;
import me.supcheg.javafile.example.typed.facts.java.lang.Object_;
import me.supcheg.javafile.example.typed.facts.java.lang.String_;
import me.supcheg.javafile.example.typed.facts.java.util.List_;
import me.supcheg.javafile.example.typed.facts.java.util.function.Consumer_;
import me.supcheg.javafile.example.typed.facts.java.util.function.Function_;
import me.supcheg.javafile.example.typed.facts.java.util.function.Predicate_;
import me.supcheg.javafile.example.typed.facts.me.supcheg.javafile.example.typed.Signal_;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.ClassHeader;
import me.supcheg.javafile.typed.Expr;
import me.supcheg.javafile.typed.MemberBodies;
import me.supcheg.javafile.typed.TypedClassBodies;
import me.supcheg.javafile.typed.TypedClassBuilder;
import me.supcheg.javafile.typed.TypedJavaFile;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignStaticField;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.divInt;
import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulDouble;
import static me.supcheg.javafile.typed.Expressions.newArrayOf;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.subInt;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.Expressions.unbox;
import static me.supcheg.javafile.typed.Expressions.varargs;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;

/// The generator code of the typed examples of the README. The README shows the code between the `region` and the
/// `endregion` markers of each example as it stands here, and [ReadmeTypedExamplesTest] holds it to that, and holds
/// what each example renders to what the README shows.
final class ReadmeTypedExamples {
    private ReadmeTypedExamples() {}

    static JavaFile greeter() {
        // region greeter
        JavaFile file = TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("com.example", "Greeter"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var greeting = cb.declareField("greeting", String_.TOKEN);
                        var greet = cb.declareMethod("greet", String_.TOKEN, String_.TOKEN);

                        var bodies = cb.bodies();
                        bodies.initialize(greeting, literal("Hello"));
                        bodies.define(
                                greet,
                                (b, self, name) -> b.return_(concat(
                                        concat(concat(self.field(greeting), literal(", ")), name), literal("!"))));
                        return bodies;
                    }
                });
        // endregion
        return file;
    }

    static JavaFile staticFields() {
        // region staticFields
        JavaFile file = TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("com.example", "Counter"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                        var total = cb.declareMutableStaticField("total", PrimitiveToken.INT);
                        var bump = cb.declareVoidStaticMethod("bump");

                        var bodies = cb.bodies();
                        bodies.initialize(one, literal(1));
                        bodies.initialize(total, literal(0));
                        bodies.define(
                                bump,
                                b -> b.exec(assignStaticField(total, addInt(staticField(total), staticField(one))))
                                        .end());
                        return bodies;
                    }
                });
        // endregion
        return file;
    }

    static JavaFile circle() {
        // region circle
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("com.example.shapes", "Circle"),
                new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var comparable = new Comparable_<>(h.self());
                        var compareTo = h.overriding(comparable.compareTo_T);
                        var header = h.extendsObject();
                        header.implements_(comparable.implement(h.self()).compareTo_T(compareTo));

                        var cb = header.members();
                        var radius = cb.declareField("radius", PrimitiveToken.DOUBLE);
                        var area = cb.declareMethod("area", PrimitiveToken.DOUBLE);

                        var bodies = cb.bodies();
                        bodies.initialize(radius, literal(1.5));
                        bodies.define(
                                area,
                                (b, self) -> b.return_(mulDouble(
                                        mulDouble(staticField(Math_.PI), self.field(radius)), self.field(radius))));
                        bodies.define(
                                compareTo.method(),
                                (b, self, other) -> b.return_(
                                        staticCall(Double_.compare_double_double, self.call(area), other.call(area))));
                        return bodies;
                    }
                });
        // endregion
        return file;
    }

    static JavaFile collections() {
        // region collections
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("com.example", "Collections2"),
                typeParam("T"),
                new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var comparable = new Comparable_<>(t);
                        var asComparable = h.bound(t, comparable.token);
                        var list = new List_<>(t);

                        var cb = h.extendsObject().members();
                        var max = cb.declareMethod("max", t, list.token);

                        var bodies = cb.bodies();
                        bodies.define(
                                max,
                                (b, self, items) -> b.letVar(
                                        t,
                                        items.call(list.getFirst),
                                        best -> b.forEach(
                                                        t,
                                                        items,
                                                        (loop, item, _) -> loop.if_(
                                                                gtInt(
                                                                        asComparable
                                                                                .apply(item)
                                                                                .call(comparable.compareTo_T, best),
                                                                        literal(0)),
                                                                then -> then.exec(assign(best, item))))
                                                .return_(best)));
                        return bodies;
                    }
                });
        // endregion
        return file;
    }

    static JavaFile signals() {
        // region signals
        JavaFile file = TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("com.example", "Signals"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var strings = new Predicate_<>(String_.TOKEN);
                        var advice = cb.declareStaticMethod("advice", String_.TOKEN, Signal_.TOKEN);
                        var parse = cb.declareStaticMethod("parse", PrimitiveToken.INT, String_.TOKEN);
                        var describe = cb.declareStaticMethod("describe", String_.TOKEN, Object_.TOKEN);
                        var startingWith = cb.declareStaticMethod("startingWith", strings.token, String_.TOKEN);

                        var bodies = cb.bodies();
                        bodies.define(
                                advice,
                                (b, signal) -> b.return_(switch_(
                                        Signal_.TOKEN,
                                        signal,
                                        String_.TOKEN,
                                        c -> c.case_(Signal_.RED, literal("stop"))
                                                .case_(Signal_.AMBER, literal("wait"))
                                                .case_(Signal_.GREEN, literal("go")))));
                        bodies.define(
                                parse,
                                (b, text) -> b.tryTerminated(
                                        h -> h.catch_(NumberFormatException_.TOKEN, (c, e) -> c.return_(literal(-1))),
                                        t -> t.return_(staticCall(Integer_.parseInt_String, text))));
                        bodies.define(
                                describe,
                                (b, value) -> b.ifInstanceOf(
                                                value,
                                                Integer_.TOKEN,
                                                (then, i) -> then.if_(
                                                        gtInt(unbox(PrimitiveToken.INT, i), literal(0)),
                                                        positive -> positive.return_(literal("positive int"))))
                                        .ifInstanceOfElse(
                                                value,
                                                String_.TOKEN,
                                                (then, s) -> then.return_(concat(
                                                        literal("string of "),
                                                        staticCall(String_.valueOf_int, s.call(String_.length)))),
                                                other -> other.return_(literal("something else"))));
                        bodies.define(
                                startingWith,
                                (b, prefix) -> b.return_(
                                        lambda(strings.sam, text -> text.call(String_.startsWith_String, prefix))));
                        return bodies;
                    }
                });
        // endregion
        return file;
    }

    static JavaFile arrays() {
        // region arrays
        JavaFile file = TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("com.example", "Arrays2"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var strings = ArrayToken.of(String_.TOKEN);
                        var chars = ArrayToken.of(CharSequence_.TOKEN);
                        var countAll = cb.variableArity().declareStaticMethod("count", PrimitiveToken.INT, strings);
                        var countTwo =
                                cb.declareStaticMethod("count", PrimitiveToken.INT, String_.TOKEN, String_.TOKEN);
                        var csv = cb.declareStaticMethod("csv", String_.TOKEN);
                        var one = cb.declareStaticMethod("one", PrimitiveToken.INT);
                        var two = cb.declareStaticMethod("two", PrimitiveToken.INT);
                        var second = cb.declareStaticMethod("second", String_.TOKEN);

                        var bodies = cb.bodies();
                        bodies.define(countAll, (b, parts) -> b.return_(parts.length(strings)));
                        bodies.define(countTwo, (b, first, last) -> b.return_(literal(0)));
                        bodies.define(
                                csv,
                                b -> b.return_(staticCall(
                                        String_.join_CharSequence_CharSequenceArray,
                                        literal(", "),
                                        varargs(chars, literal("a"), literal("b")))));
                        bodies.define(one, b -> b.return_(staticCall(countAll, varargs(strings, literal("a")))));
                        bodies.define(
                                two,
                                b -> b.return_(staticCall(countAll, varargs(strings, literal("a"), literal("b")))));
                        bodies.define(
                                second,
                                b -> b.let(
                                        strings,
                                        newArrayOf(strings, literal("a"), literal("b")),
                                        names -> b.return_(names.arrayAccess(strings, literal(1)))));
                        return bodies;
                    }
                });
        // endregion
        return file;
    }

    static void division() {
        // region division
        Expr<Prim.Int> ratio = divInt(literal(10), subInt(literal(1), literal(1)));
        // endregion
    }

    static void overloads() {
        // region overloads
        TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("com.example", "Handlers"),
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var function = new Function_<>(String_.TOKEN, String_.TOKEN);
                        var consumer = new Consumer_<>(String_.TOKEN);
                        cb.declareVoidMethod("handle", function.token);
                        cb.declareVoidMethod("handle", consumer.token);
                        return cb.bodies();
                    }
                });
        // endregion
    }
}
