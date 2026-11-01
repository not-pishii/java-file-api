package me.supcheg.javafile.typed.codegen;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.builder.AbstractMethodBuilder;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.MethodBuilder;
import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.supcheg.javafile.code.Exprs.add;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.staticCall;
import static me.supcheg.javafile.code.Exprs.this_;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.generated;
import static me.supcheg.javafile.typed.codegen.TypedCodegen.param;

/// The fixtures of the tests of the arity families: for each arity `n`, classes and interfaces whose members take `n`
/// parameters, which the classes the tests generate call, override, extend and implement. A member tells what it was
/// called with in what it returns, or in a field.
///
/// - `Wide{n}`, an open class: a constructor, instance methods, static methods, each also with a last parameter of
///   variable arity;
/// - `Abstract{n}`, an abstract class whose constructors are `protected`;
/// - `Widening{n}`, an interface of abstract and default methods;
/// - `Fn{n}` and `Do{n}`, functional interfaces of a result and of none;
/// - `Make{n}`, a functional interface whose method constructs a `Wide{n}`.
///
/// What a member tells is what the tests expect of it ([ArityTests]): the same text, made by the same functions.
final class FixtureFiles {
    private FixtureFiles() {}

    static final String PACKAGE = "me.supcheg.javafile.typed.fixtures";

    static ClassDesc fixture(String name, int n) {
        return ClassDesc.of(PACKAGE, name + n);
    }

    /// `String a1, ..., String an`.
    private static List<Param> strings(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> param("a" + i, Types.STRING))
                .toList();
    }

    private static List<String> names(int from, int to) {
        return IntStream.rangeClosed(from, to).mapToObj(i -> "a" + i).toList();
    }

    /// `prefix + a1 + "," + ... + an`: what a member tells of its arguments.
    private static Expr told(String prefix, int n) {
        return told(prefix, n, List.of());
    }

    /// `prefix + a1 + "," + ... + an` and then `suffixes`.
    private static Expr told(String prefix, int n, List<Expr> suffixes) {
        return Stream.concat(
                        Stream.of((Expr) literal(prefix)),
                        Stream.concat(
                                IntStream.rangeClosed(1, n)
                                        .boxed()
                                        .flatMap(i -> i == 1
                                                ? Stream.of((Expr) field("a1"))
                                                : Stream.of((Expr) literal(","), field("a" + i))),
                                suffixes.stream()))
                .reduce((left, right) -> add(left, right))
                .orElseThrow();
    }

    /// `"|" + Arrays.toString(rest)`.
    private static List<Expr> restTold() {
        return List.of(literal("|"), staticCall(ClassDesc.of("java.util", "Arrays"), "toString", field("rest")));
    }

    private static Consumer<CodeBuilder> returning(Expr value) {
        return b -> b.return_(value);
    }

    private static Consumer<CodeBuilder> telling(String field, Expr value) {
        return b -> b.assign(field(field), AssignOp.ADD_ASSIGN, value);
    }

    private static void method(ClassBuilder cb, String name, TypeRef result, int n, Consumer<CodeBuilder> body) {
        method(cb, name, result, strings(n), false, body);
    }

    private static void method(
            ClassBuilder cb,
            String name,
            TypeRef result,
            List<Param> params,
            boolean isStatic,
            Consumer<CodeBuilder> body) {
        cb.withMethod(name, result, mb -> {
            declare(mb, params, isStatic);
            mb.withBody(body);
        });
    }

    private static void voidMethod(
            ClassBuilder cb, String name, List<Param> params, boolean isStatic, Consumer<CodeBuilder> body) {
        cb.withVoidMethod(name, mb -> {
            declare(mb, params, isStatic);
            mb.withBody(body);
        });
    }

    private static void declare(MethodBuilder mb, List<Param> params, boolean isStatic) {
        mb.withModifiers(
                isStatic ? new Modifier[] {Modifier.PUBLIC, Modifier.STATIC} : new Modifier[] {Modifier.PUBLIC});
        params.forEach(mb::withParam);
    }

    /// `String a1, ..., String a(n-1), String... rest`.
    private static List<Param> variable(int n, TypeRef element) {
        return Stream.concat(strings(n - 1).stream(), Stream.of(new Param("rest", element, List.of(), true)))
                .toList();
    }

    /// The text a member of variable arity tells: its first arguments, then its last.
    private static Expr toldAll(String prefix, int n) {
        return told(prefix, n - 1, restTold());
    }

    static JavaFile wide(int n) {
        ClassDesc self = fixture("Wide", n);
        TypeRef selfType = Types.of(self);
        return JavaFile.class_(self, cb -> {
            generated(cb);
            cb.withField(
                    "noted",
                    Types.STRING,
                    fb -> fb.withModifiers(Modifier.PUBLIC, Modifier.STATIC).withInitializer(literal("")));
            cb.withField("told", Types.STRING, literal(""));
            cb.withConstructor(ctor -> {
                strings(n).forEach(ctor::withParam);
                ctor.withBody(b -> b.assign(this_().field("told"), told("new:", n)));
            });
            if (n >= 1) {
                cb.withConstructor(ctor -> {
                    variable(n, Types.of(ClassDesc.of("java.lang", "Integer"))).forEach(ctor::withParam);
                    ctor.withBody(b -> b.assign(this_().field("told"), toldAll("newAll:", n)));
                });
            }
            method(cb, "pick", Types.STRING, n, returning(told("pick:", n)));
            method(cb, "cs", Types.of(ClassDesc.of("java.lang", "CharSequence")), n, returning(told("cs:", n)));
            method(cb, "same", selfType, n, b -> b.return_(this_()));
            voidMethod(cb, "put", strings(n), false, telling("told", told("put:", n, List.of(literal(";")))));
            method(cb, "inh", Types.STRING, n, returning(told("inh:", n)));
            voidMethod(cb, "inhVoid", strings(n), false, telling("told", told("inhVoid:", n, List.of(literal(";")))));
            method(cb, "join", Types.STRING, strings(n), true, returning(told("join:", n)));
            voidMethod(cb, "note", strings(n), true, telling("noted", told("note:", n, List.of(literal(";")))));
            if (n >= 1) {
                List<Param> variable = variable(n, Types.STRING);
                method(cb, "pickAll", Types.STRING, variable, false, returning(toldAll("pickAll:", n)));
                voidMethod(cb, "putAll", variable, false, telling("told", add(toldAll("putAll:", n), literal(";"))));
                method(cb, "joinAll", Types.STRING, variable, true, returning(toldAll("joinAll:", n)));
                voidMethod(cb, "noteAll", variable, true, telling("noted", add(toldAll("noteAll:", n), literal(";"))));
            }
        });
    }

    static JavaFile abstractClass(int n) {
        return JavaFile.class_(fixture("Abstract", n), cb -> {
            generated(cb);
            cb.withModifiers(Modifier.ABSTRACT);
            cb.withField("told", Types.STRING, literal(""));
            cb.withConstructor(ctor -> {
                ctor.withModifiers(Modifier.PROTECTED);
                strings(n).forEach(ctor::withParam);
                ctor.withBody(b -> b.assign(this_().field("told"), told("abs:", n)));
            });
            if (n >= 1) {
                cb.withConstructor(ctor -> {
                    ctor.withModifiers(Modifier.PROTECTED);
                    variable(n, Types.of(ClassDesc.of("java.lang", "Integer"))).forEach(ctor::withParam);
                    ctor.withBody(b -> b.assign(this_().field("told"), toldAll("absAll:", n)));
                });
            }
        });
    }

    static JavaFile fn(int n) {
        return JavaFile.interface_(fixture("Fn", n), ib -> {
            generated(ib);
            ib.withAbstractMethod("apply", Types.STRING, amb -> strings(n).forEach(amb::withParam));
        });
    }

    static JavaFile doing(int n) {
        return JavaFile.interface_(fixture("Do", n), ib -> {
            generated(ib);
            ib.withVoidAbstractMethod("accept", amb -> strings(n).forEach(amb::withParam));
        });
    }

    /// A functional interface whose method constructs a `Wide{n}`, for the constructor references of the tests.
    static JavaFile make(int n) {
        return JavaFile.interface_(fixture("Make", n), ib -> {
            generated(ib);
            ib.withAbstractMethod(
                    "make", Types.of(fixture("Wide", n)), amb -> strings(n).forEach(amb::withParam));
        });
    }

    /// The names of the abstract methods of `Widening{n}`, in the order of the steps of the proof that implements it.
    static final List<String> ABSTRACT = List.of("inh", "inhVoid", "narrow", "plain", "self", "take");

    static JavaFile widening(int n) {
        ClassDesc self = fixture("Widening", n);
        TypeRef selfType = Types.of(self);
        TypeRef charSequence = Types.of(ClassDesc.of("java.lang", "CharSequence"));
        return JavaFile.interface_(self, ib -> {
            generated(ib);
            abstractMethod(ib, "inh", Optional.of(Types.STRING), n);
            abstractMethod(ib, "inhVoid", Optional.empty(), n);
            abstractMethod(ib, "narrow", Optional.of(charSequence), n);
            abstractMethod(ib, "plain", Optional.of(Types.STRING), n);
            abstractMethod(ib, "self", Optional.of(selfType), n);
            abstractMethod(ib, "take", Optional.empty(), n);
            ib.withDefaultMethod("given", Types.STRING, mb -> {
                strings(n).forEach(mb::withParam);
                mb.withBody(returning(told("given:", n)));
            });
            ib.withDefaultMethod("chosen", charSequence, mb -> {
                strings(n).forEach(mb::withParam);
                mb.withBody(returning(told("chosen:", n)));
            });
            ib.withDefaultMethod("chained", selfType, mb -> {
                strings(n).forEach(mb::withParam);
                mb.withBody(b -> b.return_(this_()));
            });
            // a `default void` method, which the builder of the interface has no form for
            ib.accept(new DefaultMethodDecl(
                    "held",
                    Optional.empty(),
                    List.of(),
                    List.of(),
                    strings(n),
                    CodeBody.EMPTY,
                    List.of(),
                    Optional.empty()));
        });
    }

    private static void abstractMethod(InterfaceBuilder ib, String name, Optional<TypeRef> result, int n) {
        Consumer<AbstractMethodBuilder> spec = amb -> strings(n).forEach(amb::withParam);
        result.ifPresentOrElse(
                type -> ib.withAbstractMethod(name, type, spec), () -> ib.withVoidAbstractMethod(name, spec));
    }

    /// `@Facts` of every fixture: the metamodels the generated classes of the tests are declared against.
    static JavaFile factsHolder(int max) {
        return JavaFile.class_(ClassDesc.of(PACKAGE, "ArityFixtures"), cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of(Modifier.FINAL));
            cb.withAnnotation(
                    ClassDesc.of("me.supcheg.javafile.facts.meta", "Facts"),
                    ab -> ab.withMember(
                            "value",
                            AnnotationValues.array(IntStream.rangeClosed(0, max)
                                    .boxed()
                                    .flatMap(n -> Stream.of("Wide", "Abstract", "Widening", "Fn", "Do", "Make")
                                            .map(name -> AnnotationValues.classValue(fixture(name, n))))
                                    .toList())));
        });
    }
}
