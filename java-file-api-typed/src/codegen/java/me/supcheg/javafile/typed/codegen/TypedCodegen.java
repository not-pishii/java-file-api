package me.supcheg.javafile.typed.codegen;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.MethodBuilder;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.ConstructorInvocation;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.doc.DocCommentBuilder;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.constant.ClassDesc;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.supcheg.javafile.code.Exprs.add;
import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.newDiamond;
import static me.supcheg.javafile.code.Exprs.new_;
import static me.supcheg.javafile.code.Exprs.staticCall;
import static me.supcheg.javafile.code.Exprs.staticField;

/// Generates the arity families of `java-file-api-typed` with `java-file-api-core` itself.
///
/// The combinators of the typed layer that exist once for each number of parameters of a member
/// (`call`, `new_`, `lambda`, the declarations, the overrides, the bodies, ...) are the same code
/// at every arity. Each is generated into a package-private `abstract sealed` superclass that
/// permits exactly the hand-written public class, which inherits the forms: the static methods of
/// the superclass are members of the subclass, so `Expressions.call` and an `import static` of it
/// keep working. The hand-written class holds what is not about the arity, and the hooks the
/// generated forms call.
///
/// Every generated type is marked `@Generated`.
///
/// Usage: `TypedCodegen <output dir> <test output dir> write|check`. `check` fails when the
/// sources on disk differ from what would be generated.
public final class TypedCodegen {
    /// The largest generated arity.
    public static final int MAX_ARITY = 12;

    /// How far each family of forms reaches.
    ///
    /// @param calls the arity of `call`, `voidCall`, `staticCall`, `voidStaticCall`, `new_`
    /// @param lambdas the arity of `lambda` and `lambdaBlock`
    /// @param declared the arity of the declarations of constructors, methods and overrides
    /// @param declaredStatic the arity of the declarations of static methods
    /// @param defined the arity of the bodies of constructors and instance methods, `super_`, `superCall`
    /// @param definedStatic the arity of the bodies of static methods
    /// @param variableArity the arity of the declarations of constructors and instance methods of variable arity
    /// @param variableArityStatic the arity of the declarations of static methods of variable arity
    record Reach(
            int calls,
            int lambdas,
            int declared,
            int declaredStatic,
            int defined,
            int definedStatic,
            int variableArity,
            int variableArityStatic) {
        static final Reach FULL =
                new Reach(MAX_ARITY, MAX_ARITY, MAX_ARITY, MAX_ARITY, MAX_ARITY, MAX_ARITY, MAX_ARITY, MAX_ARITY);
    }

    private static final Reach REACH = Reach.FULL;

    private static final String TYPED = "me.supcheg.javafile.typed";
    private static final String FACTS = "me.supcheg.javafile.facts";

    private static final ClassDesc GENERATED = ClassDesc.of("javax.annotation.processing.Generated");
    private static final ClassDesc FUNCTIONAL_INTERFACE = ClassDesc.of("java.lang", "FunctionalInterface");
    static final ClassDesc LIST = ClassDesc.of("java.util", "List");
    private static final ClassDesc SUPPLIER = ClassDesc.of("java.util.function", "Supplier");
    private static final ClassDesc FUNCTION = ClassDesc.of("java.util.function", "Function");
    private static final ClassDesc BI_FUNCTION = ClassDesc.of("java.util.function", "BiFunction");
    private static final ClassDesc ILLEGAL_ARGUMENT = ClassDesc.of("java.lang", "IllegalArgumentException");
    private static final ClassDesc ILLEGAL_STATE = ClassDesc.of("java.lang", "IllegalStateException");
    static final ClassDesc VOID = ClassDesc.of("java.lang", "Void");
    private static final ClassDesc THROWABLE = ClassDesc.of("java.lang", "Throwable");

    private static final ClassDesc EXPR = typed("Expr");
    static final ClassDesc EXPRESSIONS = typed("Expressions");
    private static final ClassDesc INVOCATION = typed("Invocation");
    private static final ClassDesc VOID_INVOCATION = typed("VoidInvocation");
    private static final ClassDesc VARARGS = typed("Varargs");
    private static final ClassDesc NODE = typed("Node");
    private static final ClassDesc NODE_CALL = NODE.nested("Call");
    private static final ClassDesc NODE_STATIC_CALL = NODE.nested("StaticCall");
    private static final ClassDesc NODE_PASSING = NODE.nested("Passing");
    private static final ClassDesc NODE_OPERAND = NODE.nested("Operand");
    static final ClassDesc VAR = typed("Var");
    static final ClassDesc BODY = typed("Body");
    static final ClassDesc VOID_BODY = typed("VoidBody");
    private static final ClassDesc BLOCK = typed("Block");
    static final ClassDesc TERMINATED = typed("Terminated");
    private static final ClassDesc EFFECT = typed("Effect");
    private static final ClassDesc ASSIGNMENT = typed("Assignment");
    private static final ClassDesc SCOPES = typed("Scopes");
    static final ClassDesc UPCAST = typed("Upcast");
    static final ClassDesc THIS = typed("This");
    private static final ClassDesc OVERRIDES = typed("Overrides");
    private static final ClassDesc DECLARATIONS = typed("Declarations");
    private static final ClassDesc MEMBER_BUILDER = typed("MemberBuilder");
    static final ClassDesc MEMBER_BODIES = typed("MemberBodies");
    private static final ClassDesc MEMBER_BODY_ARITIES = typed("MemberBodyArities");
    private static final ClassDesc SLOT_AWAITING = MEMBER_BODIES.nested("Slot", "Awaiting");
    private static final ClassDesc DECLARED = MEMBER_BODIES.nested("Declared");
    private static final ClassDesc TYPED_CLASS_BODIES = typed("TypedClassBodies");
    private static final ClassDesc TYPED_SUBCLASS_BODIES = typed("TypedSubclassBodies");
    private static final ClassDesc TYPED_SUBCLASS_BODY_ARITIES = typed("TypedSubclassBodyArities");
    private static final ClassDesc TYPED_SUBCLASS_BUILDER = typed("TypedSubclassBuilder");
    private static final ClassDesc SUBCLASS_HEADER = typed("SubclassHeader");
    private static final ClassDesc SUPER_SITE = typed("SuperSite");
    private static final ClassDesc VARIABLE_ARITY_DECLARATIONS = typed("VariableArityDeclarations");
    private static final ClassDesc OVERRIDING = typed("Overriding");
    private static final ClassDesc CLASS_DECLARATION = typed("ClassDeclaration");
    private static final ClassDesc INHERITANCE = typed("Inheritance");
    private static final ClassDesc SUPERCLASS = typed("Superclass");
    private static final ClassDesc STATIC_FIELD_DECLARATIONS = typed("StaticFieldDeclarations");
    private static final ClassDesc TYPED_RECORD_BUILDER = typed("TypedRecordBuilder");
    private static final ClassDesc TYPED_ENUM_BUILDER = typed("TypedEnumBuilder");
    private static final ClassDesc TARGET_CLASSPATH = facts("TargetClasspath");
    private static final ClassDesc CLASS_DESC = ClassDesc.of("java.lang.constant", "ClassDesc");
    private static final ClassDesc DOC_COMMENT = ClassDesc.of("me.supcheg.javafile.doc", "DocComment");

    private static final ClassDesc INVOCABLE = facts("Invocable");
    private static final ClassDesc MEMBER_TRAITS = facts("MemberTraits");
    private static final ClassDesc TYPE_TOKEN = facts("TypeToken");
    private static final ClassDesc REF_TOKEN = facts("RefToken");
    private static final ClassDesc ARRAY_TOKEN = facts("ArrayToken");
    private static final ClassDesc CLASS_TOKEN = facts("ClassToken");
    private static final ClassDesc FINAL_CLASS_TOKEN = facts("FinalClassToken");
    private static final ClassDesc UNSAFE_INVOCABLES = facts("UnsafeInvocables");
    private static final ClassDesc IMPLEMENTED_BY_OVERRIDE =
            facts("Implemented").nested("ByOverride");

    private TypedCodegen() {}

    static ClassDesc typed(String name) {
        return ClassDesc.of(TYPED, name);
    }

    static ClassDesc facts(String name) {
        return ClassDesc.of(FACTS, name);
    }

    /// Runs the generator.
    ///
    /// @param args the output directory of the families, that of their tests, and the mode, `write` or `check`
    public static void main(String[] args) {
        Stream.of(new Generated(Path.of(args[0]), generate()), new Generated(Path.of(args[1]), generateTests()))
                .forEach(args[2].equals("write") ? Generated::write : Generated::check);
    }

    /// The files generated into a directory, which holds them and nothing else.
    private record Generated(Path out, List<JavaFile> generated) {
        Map<Path, String> files() {
            return generated.stream()
                    .collect(Collectors.toMap(
                            file -> out.resolve(file.pathSuffix()), JavaFile::render, (_, last) -> last, TreeMap::new));
        }

        void write() {
            TypedCodegen.write(out, files());
        }

        void check() {
            TypedCodegen.check(out, files());
        }
    }

    /// Generates every file of the forms.
    ///
    /// @return the files
    public static List<JavaFile> generate() {
        return Stream.of(
                        Stream.of(
                                expressionArities(),
                                declarationArities(),
                                variableArityDeclarationArities(),
                                overridingArities(),
                                overrideArities(),
                                memberBodyArities(),
                                typedClassBodyArities(),
                                typedSubclassBodyArities(),
                                typedSubclassBuilderArities(),
                                subclassHeaderArities(),
                                superSiteArities(),
                                recordConstructorArities(),
                                enumConstructorArities()),
                        IntStream.rangeClosed(3, REACH.lambdas() + 1).mapToObj(TypedCodegen::function))
                .flatMap(files -> files)
                .toList();
    }

    /// Generates the tests of the forms.
    ///
    /// @return the files
    public static List<JavaFile> generateTests() {
        return Stream.concat(Stream.of(staticFormsTest()), ArityTests.files(MAX_ARITY).stream())
                .toList();
    }

    static final ClassDesc TEST = ClassDesc.of("org.junit.jupiter.api", "Test");
    static final ClassDesc ASSERTJ_ASSERTIONS = ClassDesc.of("org.assertj.core.api", "Assertions");
    static final ClassDesc CLASS = ClassDesc.of("java.lang", "Class");
    static final ClassDesc CONSTRUCTOR = ClassDesc.of("java.lang.reflect", "Constructor");
    private static final ClassDesc REFLECTIVE_OPERATION = ClassDesc.of("java.lang", "ReflectiveOperationException");

    /// The classes whose forms are all static, and are never constructed: `Expressions` and `Overrides`.
    private static final List<ClassDesc> STATIC_FORMS = List.of(EXPRESSIONS, OVERRIDES);

    /// The generated superclass of a class of static forms has a constructor that only an instance of the class runs,
    /// and the class is never constructed: this test constructs each.
    private static JavaFile staticFormsTest() {
        return JavaFile.class_(typed("StaticFormsTest"), cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of());
            cb.withDoc(d -> d.paragraph("The classes of static forms are constructed here, which runs the constructor"
                    + " of the generated superclass that holds their forms: nothing else constructs them."));
            cb.withVoidMethod("theClassesOfStaticFormsAreConstructed", mb -> {
                mb.withAnnotation(TEST);
                mb.withThrows(REFLECTIVE_OPERATION);
                mb.withBody(b -> b.forEach(
                        applied(CLASS, unbounded()),
                        "type",
                        staticCall(
                                LIST,
                                "of",
                                STATIC_FORMS.stream().map(Exprs::classLiteral).toList()),
                        each -> {
                            each.localVar(
                                    "constructor",
                                    applied(CONSTRUCTOR, unbounded()),
                                    field("type").call("getDeclaredConstructor"));
                            each.exprStatement(field("constructor").call("setAccessible", literal(true)));
                            each.exprStatement(staticCall(
                                            ASSERTJ_ASSERTIONS,
                                            "assertThat",
                                            field("constructor").call("newInstance"))
                                    .call("isInstanceOf", field("type")));
                        }));
            });
        });
    }

    private static void write(Path out, Map<Path, String> files) {
        try {
            if (Files.isDirectory(out)) {
                try (Stream<Path> existing = Files.walk(out)) {
                    for (Path path : existing.filter(Files::isRegularFile).toList()) {
                        Files.delete(path);
                    }
                }
            }
            for (Map.Entry<Path, String> file : files.entrySet()) {
                Files.createDirectories(file.getKey().getParent());
                Files.writeString(file.getKey(), file.getValue(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void check(Path out, Map<Path, String> files) {
        try {
            List<Path> onDisk;
            if (Files.isDirectory(out)) {
                try (Stream<Path> existing = Files.walk(out)) {
                    onDisk = existing.filter(Files::isRegularFile).sorted().toList();
                }
            } else {
                onDisk = List.of();
            }
            if (!onDisk.equals(List.copyOf(files.keySet()))) {
                throw new IllegalStateException("generated file set differs; run generateArities");
            }
            for (Map.Entry<Path, String> file : files.entrySet()) {
                String onDiskText =
                        Files.readString(file.getKey(), StandardCharsets.UTF_8).replace("\r\n", "\n");
                if (!onDiskText.equals(file.getValue().replace("\r\n", "\n"))) {
                    throw new IllegalStateException(file.getKey() + " is stale; run generateArities");
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // ------------------------------------------------------------------
    // Vocabulary: type variables, types, documentation
    // ------------------------------------------------------------------

    private static final List<String> ORDINALS = List.of(
            "first",
            "second",
            "third",
            "fourth",
            "fifth",
            "sixth",
            "seventh",
            "eighth",
            "ninth",
            "tenth",
            "eleventh",
            "twelfth",
            "thirteenth");

    /// The type variables of the parameters of a member of `n` parameters: `A1` to `An`.
    static List<String> as(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(i -> "A" + i).toList();
    }

    static List<String> concat(List<String> first, List<String> second) {
        return Stream.concat(first.stream(), second.stream()).toList();
    }

    /// The ordinal of the `i`-th of `n` things, with its space: none where there is one.
    static String ordinal(int i, int n) {
        return n == 1 ? "" : ORDINALS.get(i - 1) + " ";
    }

    private static String plural(int n, String what) {
        return n + " " + what + (n == 1 ? "" : "s");
    }

    static TypeRef tv(String name) {
        return Types.typeVar(name);
    }

    static TypeArg exact(String variable) {
        return Types.exact(tv(variable));
    }

    static TypeArg exactType(TypeRef type) {
        return Types.exact(type);
    }

    static TypeArg extending(TypeRef bound) {
        return Types.extendsBound(bound);
    }

    static TypeArg extending(String variable) {
        return Types.extendsBound(tv(variable));
    }

    static TypeArg supering(TypeRef bound) {
        return Types.superBound(bound);
    }

    static TypeArg supering(String variable) {
        return Types.superBound(tv(variable));
    }

    static TypeArg unbounded() {
        return Types.unbounded();
    }

    static TypeRef applied(ClassDesc raw, List<? extends TypeArg> args) {
        return args.isEmpty() ? Types.of(raw) : Types.parameterized(raw, args, new AnnotationUse[0]);
    }

    static TypeRef applied(ClassDesc raw, TypeArg... args) {
        return applied(raw, List.of(args));
    }

    /// The type applied to type variables.
    static TypeRef appliedTo(ClassDesc raw, String... variables) {
        return applied(raw, exacts(List.of(variables)));
    }

    static List<TypeArg> exacts(List<String> variables) {
        return variables.stream().map(TypedCodegen::exact).toList();
    }

    @SafeVarargs
    private static <T> List<T> listOf(List<? extends T> first, List<? extends T>... rest) {
        return Stream.concat(
                        Stream.of(first), IntStream.range(0, rest.length).<List<? extends T>>mapToObj(i -> rest[i]))
                .<T>flatMap(List::stream)
                .toList();
    }

    /// `Expr<? extends A1>`.
    private static TypeRef expr(String variable) {
        return applied(EXPR, extending(variable));
    }

    private static TypeRef typeToken(String variable) {
        return appliedTo(TYPE_TOKEN, variable);
    }

    private static TypeRef var(String variable) {
        return appliedTo(VAR, variable);
    }

    /// `ArrayToken<A, ?>`.
    private static TypeRef arrayToken() {
        return applied(ARRAY_TOKEN, exact("A"), unbounded());
    }

    /// `List<ClassToken<? extends Throwable>>`.
    private static TypeRef thrownType() {
        return applied(LIST, exactType(applied(CLASS_TOKEN, extending(Types.of(THROWABLE)))));
    }

    private static ClassOrInterfaceTypeRef bound(TypeRef type) {
        return (ClassOrInterfaceTypeRef) type;
    }

    /// A type variable of a method, with its bounds.
    private record TypeVar(String name, List<ClassOrInterfaceTypeRef> bounds) {
        static TypeVar of(String name, TypeRef... bounds) {
            return new TypeVar(name, Stream.of(bounds).map(TypedCodegen::bound).toList());
        }

        ClassOrInterfaceTypeRef[] boundsArray() {
            return bounds.toArray(ClassOrInterfaceTypeRef[]::new);
        }
    }

    private static List<TypeVar> typeVars(List<String> names) {
        return names.stream().map(name -> TypeVar.of(name)).toList();
    }

    private static List<TypeVar> typeVars(String... names) {
        return typeVars(List.of(names));
    }

    /// A documentation comment: its summary and its tags.
    private record Doc(String summary, List<Consumer<DocCommentBuilder>> tags) implements Consumer<DocCommentBuilder> {
        static Doc of(String summary) {
            return new Doc(summary, List.of());
        }

        private Doc with(Consumer<DocCommentBuilder> tag) {
            return new Doc(summary, Stream.concat(tags.stream(), Stream.of(tag)).toList());
        }

        Doc param(String name, String description) {
            return with(b -> b.param(name, description));
        }

        Doc typeParam(String name, String description) {
            return with(b -> b.typeParam(name, description));
        }

        Doc returns(String description) {
            return with(b -> b.returns(description));
        }

        Doc throws_(ClassDesc exception, String description) {
            return with(b -> b.throws_(exception, description));
        }

        /// The arguments `a1` to `an`, each described by its position.
        Doc arguments(int n, String what) {
            return IntStream.rangeClosed(1, n)
                    .boxed()
                    .reduce(this, (doc, i) -> doc.param("a" + i, "the " + ordinal(i, n) + what), (_, last) -> last);
        }

        /// The type variables `A1` to `An`, each described by its position.
        Doc typeParams(int n, String what) {
            return IntStream.rangeClosed(1, n)
                    .boxed()
                    .reduce(
                            this,
                            (doc, i) ->
                                    doc.typeParam("A" + i, "the " + what + " of the " + ordinal(i, n) + "parameter"),
                            (_, last) -> last);
        }

        @Override
        public void accept(DocCommentBuilder builder) {
            builder.paragraph(summary);
            tags.forEach(tag -> tag.accept(builder));
        }
    }

    // ------------------------------------------------------------------
    // Declaring classes and members
    // ------------------------------------------------------------------

    static void generated(ClassBuilder cb) {
        cb.withAnnotation(
                GENERATED, ab -> ab.withMember("value", AnnotationValues.literal(TypedCodegen.class.getName())));
    }

    static void generated(InterfaceBuilder ib) {
        ib.withAnnotation(
                GENERATED, ab -> ab.withMember("value", AnnotationValues.literal(TypedCodegen.class.getName())));
    }

    /// The package-private `abstract sealed` superclass of the forms of `permitted`.
    private static JavaFile arities(
            String name,
            List<String> typeParams,
            Optional<ClassOrInterfaceTypeRef> superclass,
            ClassDesc permitted,
            Consumer<ClassBuilder> members) {
        return JavaFile.class_(typed(name), cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of(Modifier.ABSTRACT));
            typeParams.forEach(p -> cb.withTypeParam(p));
            superclass.ifPresent(cb::withSuperclass);
            cb.withPermits(permitted);
            members.accept(cb);
        });
    }

    private static JavaFile arities(
            String name, List<String> typeParams, ClassDesc permitted, Consumer<ClassBuilder> members) {
        return arities(name, typeParams, Optional.empty(), permitted, members);
    }

    private static final Set<Modifier> PUBLIC = Set.of(Modifier.PUBLIC);
    private static final Set<Modifier> FINAL = Set.of(Modifier.PUBLIC, Modifier.FINAL);
    private static final Set<Modifier> STATIC = Set.of(Modifier.PUBLIC, Modifier.STATIC);

    /// A method of the forms.
    private static void method(
            ClassBuilder cb,
            Set<Modifier> modifiers,
            List<TypeVar> vars,
            Optional<TypeRef> result,
            String name,
            List<Param> params,
            Doc doc,
            Consumer<CodeBuilder> body) {
        Consumer<MethodBuilder> spec = mb -> {
            mb.withModifiers(modifiers.toArray(Modifier[]::new));
            vars.forEach(v -> mb.withTypeParam(v.name(), v.boundsArray()));
            params.forEach(mb::withParam);
            mb.withDoc(doc);
            mb.withBody(body);
        };
        result.ifPresentOrElse(type -> cb.withMethod(name, type, spec), () -> cb.withVoidMethod(name, spec));
    }

    private static void method(
            ClassBuilder cb,
            Set<Modifier> modifiers,
            List<TypeVar> vars,
            TypeRef result,
            String name,
            List<Param> params,
            Doc doc,
            Consumer<CodeBuilder> body) {
        method(cb, modifiers, vars, Optional.of(result), name, params, doc, body);
    }

    private static void voidMethod(
            ClassBuilder cb,
            Set<Modifier> modifiers,
            List<TypeVar> vars,
            String name,
            List<Param> params,
            Doc doc,
            Consumer<CodeBuilder> body) {
        method(cb, modifiers, vars, Optional.empty(), name, params, doc, body);
    }

    /// An abstract method of the package: what the generated forms need of the class that inherits them.
    private static void hook(
            ClassBuilder cb, List<TypeVar> vars, Optional<TypeRef> result, String name, Param... params) {
        Consumer<me.supcheg.javafile.builder.AbstractMethodBuilder> spec = amb -> {
            amb.withExactModifiers(Set.of(Modifier.ABSTRACT));
            vars.forEach(v -> amb.withTypeParam(v.name(), v.boundsArray()));
            for (Param param : params) {
                amb.withParam(param);
            }
        };
        result.ifPresentOrElse(
                type -> cb.withAbstractMethod(name, type, spec), () -> cb.withVoidAbstractMethod(name, spec));
    }

    private static void hook(ClassBuilder cb, List<TypeVar> vars, TypeRef result, String name, Param... params) {
        hook(cb, vars, Optional.of(result), name, params);
    }

    /// A package-private constructor that passes its parameters on to the constructor of the superclass.
    private static void passingConstructor(ClassBuilder cb, List<Param> params) {
        cb.accept(new ConstructorDecl(
                List.of(),
                Set.of(),
                params,
                new ConstructorBody.Explicit(
                        CodeBody.EMPTY,
                        new ConstructorInvocation.Super(
                                params.stream().map(p -> (Expr) field(p.name())).toList()),
                        CodeBody.EMPTY),
                List.of(),
                Optional.empty()));
    }

    static Param param(String name, TypeRef type) {
        return new Param(name, type);
    }

    /// `a1` to `an`, or `p1` to `pn`.
    static List<Expr> names(String prefix, int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> (Expr) field(prefix + i))
                .toList();
    }

    /// The arguments `a1` to `an` of a call, as `Expressions.arguments(a1, ..., an)` makes the operands of.
    private static Expr arguments(int n) {
        return staticCall(EXPRESSIONS, "arguments", names("a", n));
    }

    private static Expr each() {
        return staticField(NODE_PASSING, "EACH");
    }

    // ------------------------------------------------------------------
    // Functions of more arguments than java.util.function has
    // ------------------------------------------------------------------

    /// The function type of the `inputs`: that of `java.util.function` where there is one, else the generated one.
    private static TypeRef function(List<TypeArg> inputs, TypeArg result) {
        List<TypeArg> args = Stream.concat(inputs.stream(), Stream.of(result)).toList();
        return switch (inputs.size()) {
            case 0 -> applied(SUPPLIER, args);
            case 1 -> applied(FUNCTION, args);
            case 2 -> applied(BI_FUNCTION, args);
            default -> applied(typed("Function" + inputs.size()), args);
        };
    }

    /// The method of that function type.
    private static String applying(int inputs) {
        return inputs == 0 ? "get" : "apply";
    }

    /// `Function3` and its like: a function of `arguments` arguments.
    private static JavaFile function(int arguments) {
        return JavaFile.interface_(typed("Function" + arguments), ib -> {
            generated(ib);
            ib.withAnnotation(FUNCTIONAL_INTERFACE);
            IntStream.rangeClosed(1, arguments).forEach(i -> ib.withTypeParam("T" + i));
            ib.withTypeParam("R");
            ib.withDoc(d -> {
                d.paragraph("A function of " + arguments + " arguments, which `java.util.function` has none of: the"
                        + " shape of the generator's lambda that builds the body of a lambda of the generated code"
                        + " ([Expressions]).");
                IntStream.rangeClosed(1, arguments)
                        .forEach(i -> d.typeParam("T" + i, "the type of the " + ORDINALS.get(i - 1) + " argument"));
                d.typeParam("R", "the type of the result");
            });
            ib.withAbstractMethod("apply", tv("R"), amb -> {
                amb.withDoc(d -> {
                    d.paragraph("Applies the function.");
                    IntStream.rangeClosed(1, arguments)
                            .forEach(i -> d.param("t" + i, "the " + ORDINALS.get(i - 1) + " argument"));
                    d.returns("the result");
                });
                IntStream.rangeClosed(1, arguments).forEach(i -> amb.withParam("t" + i, tv("T" + i)));
            });
        });
    }

    // ------------------------------------------------------------------
    // Expressions: calls, instance creation, lambdas
    // ------------------------------------------------------------------

    /// The calls of the facts of members.
    private enum Call {
        CALL("call", "MethodRef", "method", true, "O", true, "an instance method"),
        VOID_CALL("voidCall", "VoidMethodRef", "method", true, "O", false, "an instance `void` method"),
        STATIC_CALL("staticCall", "StaticMethodRef", "method", false, "", true, "a static method"),
        VOID_STATIC_CALL("voidStaticCall", "VoidStaticMethodRef", "method", false, "", false, "a static `void` method"),
        NEW("new_", "CtorRef", "ctor", false, "O", false, "a constructor");

        final String name;
        final String prefix;
        final String fact;
        final boolean hasTarget;
        final String owner;
        final boolean hasResult;
        final String what;

        Call(String name, String prefix, String fact, boolean hasTarget, String owner, boolean hasResult, String what) {
            this.name = name;
            this.prefix = prefix;
            this.fact = fact;
            this.hasTarget = hasTarget;
            this.owner = owner;
            this.hasResult = hasResult;
            this.what = what;
        }

        /// The leading type variables of the fact: the owner, the result.
        List<String> leading() {
            return Stream.concat(
                            Stream.of(owner).filter(o -> !o.isEmpty()),
                            Stream.of("R").filter(_ -> hasResult))
                    .toList();
        }

        TypeRef returned() {
            return switch (this) {
                case CALL, STATIC_CALL -> appliedTo(INVOCATION, "R");
                case NEW -> appliedTo(INVOCATION, "O");
                case VOID_CALL, VOID_STATIC_CALL -> Types.of(VOID_INVOCATION);
            };
        }
    }

    private static JavaFile expressionArities() {
        return arities("ExpressionArities", List.of(), EXPRESSIONS, cb -> {
            for (Call call : Call.values()) {
                for (int n = 0; n <= REACH.calls(); n++) {
                    callForm(cb, call, n, false);
                    if (n >= 1) {
                        callForm(cb, call, n, true);
                    }
                }
            }
            for (int n = 0; n <= REACH.lambdas(); n++) {
                expressionLambda(cb, n, true);
                expressionLambda(cb, n, false);
            }
            for (int n = 0; n <= REACH.lambdas(); n++) {
                blockLambda(cb, n, true);
                blockLambda(cb, n, false);
            }
        });
    }

    /// `call(target, method, a1, ..., an)`, or with the arguments of the last parameter one by one.
    private static void callForm(ClassBuilder cb, Call call, int n, boolean spread) {
        List<String> as = as(n);
        int each = spread ? n - 1 : n;
        TypeRef fact = applied(
                facts(call.prefix + n),
                Stream.concat(call.leading().stream().map(TypedCodegen::exact), exacts(as).stream())
                        .toList());
        List<Param> params = listOf(
                call.hasTarget ? List.of(param("target", expr("O"))) : List.<Param>of(),
                List.of(param(call.fact, fact)),
                IntStream.rangeClosed(1, each)
                        .mapToObj(i -> param("a" + i, expr("A" + i)))
                        .toList(),
                spread ? List.of(param("rest", appliedTo(VARARGS, "A" + n))) : List.<Param>of());
        Expr node = spread ? spreadNode(call, each) : eachNode(call, n);
        Expr value =
                switch (call) {
                    case CALL, STATIC_CALL ->
                        newDiamond(INVOCATION, node, field(call.fact).call("result"));
                    case NEW -> newDiamond(INVOCATION, node, field(call.fact).call("owner"));
                    case VOID_CALL, VOID_STATIC_CALL -> new_(VOID_INVOCATION, node);
                };
        Doc doc = Doc.of("Calls " + call.what + " of " + plural(n, "parameter")
                        + (spread
                                ? ", the arguments of its last parameter, which is of variable arity, one by one"
                                        + " (see [#varargs])."
                                : "."))
                .param(call.fact, "the fact of the " + (call == Call.NEW ? "constructor" : "method"));
        Doc documented = (call.hasTarget ? doc.param("target", "the receiver") : doc).arguments(each, "argument");
        Doc described = (spread ? documented.param("rest", "the arguments of the last parameter") : documented)
                .typeParams(n, "type")
                .returns("the call");
        method(
                cb,
                STATIC,
                typeVars(concat(call.leading(), as)),
                call.returned(),
                call.name,
                params,
                described,
                b -> b.return_(value));
    }

    private static Expr eachNode(Call call, int n) {
        return switch (call) {
            case CALL, VOID_CALL ->
                new_(
                        NODE_CALL,
                        staticCall(EXPRESSIONS, "receiver", field("target"), field("method")),
                        field("method"),
                        arguments(n),
                        each());
            case STATIC_CALL, VOID_STATIC_CALL -> new_(NODE_STATIC_CALL, field("method"), arguments(n), each());
            case NEW -> staticCall(EXPRESSIONS, "instantiate", field("ctor"), arguments(n), each());
        };
    }

    private static Expr spreadNode(Call call, int leading) {
        return switch (call) {
            case CALL, VOID_CALL ->
                staticCall(
                        EXPRESSIONS, "callNode", field("target"), field("method"), field("rest"), arguments(leading));
            case STATIC_CALL, VOID_STATIC_CALL ->
                staticCall(EXPRESSIONS, "staticCallNode", field("method"), field("rest"), arguments(leading));
            case NEW -> staticCall(EXPRESSIONS, "newNode", field("ctor"), field("rest"), arguments(leading));
        };
    }

    /// The leading type arguments of the sam: the functional interface, the result.
    private static List<String> samVars(boolean hasResult) {
        return hasResult ? List.of("F", "R") : List.of("F");
    }

    private static TypeRef sam(int n, boolean hasResult) {
        return applied(facts((hasResult ? "Sam" : "VoidSam") + n), exacts(concat(samVars(hasResult), as(n))));
    }

    private static String parameterList(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(i -> "A" + i + " a" + i).collect(Collectors.joining(", "));
    }

    /// `lambda(sam, body)`: a lambda with an expression body.
    private static void expressionLambda(ClassBuilder cb, int n, boolean hasResult) {
        List<String> as = as(n);
        TypeArg value = hasResult ? extending(applied(EXPR, extending("R"))) : extending(Types.of(EFFECT));
        TypeRef body = function(as.stream().map(a -> supering(var(a))).toList(), value);
        List<Expr> parameters = names("p", n);
        Expr applied = field("body").call(applying(n), parameters);
        Expr lambdaValue = new_(
                EXPRESSIONS.nested("LambdaValue"),
                staticCall(LIST, "of", parameters),
                hasResult
                        ? staticCall(
                                EXPRESSIONS, "returned", applied, field("sam").call("method"))
                        : staticCall(ASSIGNMENT, "nodeOf", applied));
        Doc doc = Doc.of("A lambda with an expression body, `(" + parameterList(n) + ") -> "
                        + (hasResult ? "value" : "effect") + "`, of the functional interface "
                        + (hasResult ? "`sam` is the method of." : "whose `void` method `sam` is."))
                .param("sam", "the single abstract method of the functional interface")
                .param(
                        "body",
                        "gives the " + (hasResult ? "value" : "effect") + (n == 0 ? "" : ", given the parameters"))
                .typeParam("F", "the functional interface");
        Doc documented = (hasResult ? doc.typeParam("R", "the result type of its method") : doc)
                .typeParams(n, "type")
                .returns("the lambda")
                .throws_(
                        ILLEGAL_STATE,
                        "if the body uses a variable a lambda cannot capture, or can throw a checked exception the"
                                + " method does not declare");
        method(
                cb,
                STATIC,
                typeVars(concat(samVars(hasResult), as)),
                appliedTo(EXPR, "F"),
                "lambda",
                List.of(param("sam", sam(n, hasResult)), param("body", body)),
                documented,
                b -> b.return_(staticCall(
                        EXPRESSIONS,
                        "expressionLambda",
                        field("sam").call("owner"),
                        field("sam").call("method"),
                        Exprs.lambda(List.of("scope"), l -> {
                            IntStream.rangeClosed(1, n)
                                    .forEach(i -> l.localVar(
                                            "p" + i,
                                            var("A" + i),
                                            staticCall(
                                                    EXPRESSIONS,
                                                    "lambdaParameter",
                                                    field("sam").call("param" + i),
                                                    field("scope"))));
                            l.return_(lambdaValue);
                        }))));
    }

    /// `lambdaBlock(sam, body)`: a lambda with a block body.
    private static void blockLambda(ClassBuilder cb, int n, boolean hasResult) {
        List<String> as = as(n);
        ClassDesc blockType = hasResult ? BODY : VOID_BODY;
        TypeRef blockOf = hasResult ? appliedTo(BODY, "R") : Types.of(VOID_BODY);
        TypeArg ended =
                exactType(hasResult ? appliedTo(TERMINATED, "R") : applied(TERMINATED, exactType(Types.of(VOID))));
        TypeRef body = function(
                Stream.concat(Stream.of(supering(blockOf)), as.stream().map(a -> supering(var(a))))
                        .toList(),
                ended);
        List<Expr> parameters = names("p", n);
        Doc doc = Doc.of("A lambda with a block body, `(" + parameterList(n) + ") -> { ... }`, of the functional"
                        + " interface " + (hasResult ? "`sam` is the method of" : "whose `void` method `sam` is")
                        + ". The block ends as the body of a " + (hasResult ? "" : "`void` ") + "method does.")
                .param("sam", "the single abstract method of the functional interface")
                .param("body", "builds the block" + (n == 0 ? "" : ", given the parameters"))
                .typeParam("F", "the functional interface");
        Doc documented = (hasResult ? doc.typeParam("R", "the result type of its method") : doc)
                .typeParams(n, "type")
                .returns("the lambda")
                .throws_(
                        ILLEGAL_STATE,
                        "if a statement of the block uses a variable a lambda cannot capture or can throw a checked"
                                + " exception the method does not declare, where it is built; if the block ends with"
                                + " the token of another block");
        method(
                cb,
                STATIC,
                typeVars(concat(samVars(hasResult), as)),
                appliedTo(EXPR, "F"),
                "lambdaBlock",
                List.of(param("sam", sam(n, hasResult)), param("body", body)),
                documented,
                b -> {
                    b.localVar(
                            "block",
                            blockOf,
                            staticCall(
                                    blockType,
                                    "lambdaBody",
                                    staticCall(SCOPES, "enclosing"),
                                    field("sam").call("method").call("traits").call("throwsTypes")));
                    IntStream.rangeClosed(1, n)
                            .forEach(i -> b.localVar(
                                    "p" + i,
                                    var("A" + i),
                                    staticCall(
                                            EXPRESSIONS,
                                            "lambdaParameter",
                                            field("sam").call("param" + i),
                                            field("block"))));
                    b.return_(staticCall(
                            EXPRESSIONS,
                            "blockLambda",
                            field("sam").call("owner"),
                            field("sam").call("method"),
                            field("block"),
                            staticCall(LIST, "of", parameters),
                            Exprs.lambda(
                                    List.of("b"),
                                    field("body")
                                            .call(
                                                    "apply",
                                                    Stream.concat(Stream.of((Expr) field("b")), parameters.stream())
                                                            .toList()))));
                });
    }

    // ------------------------------------------------------------------
    // Declarations
    // ------------------------------------------------------------------

    /// The declarations of members by name and types.
    private enum Declare {
        CONSTRUCTOR("declareConstructor", "ctor", "CtorRef", false, false, "DEFAULT", "constructor"),
        METHOD("declareMethod", "method", "MethodRef", true, true, "FINAL", "method"),
        VOID_METHOD("declareVoidMethod", "voidMethod", "VoidMethodRef", true, false, "FINAL", "method"),
        STATIC_METHOD("declareStaticMethod", "staticMethod", "StaticMethodRef", true, true, "FINAL", "method"),
        VOID_STATIC_METHOD(
                "declareVoidStaticMethod", "voidStaticMethod", "VoidStaticMethodRef", true, false, "FINAL", "method");

        final String name;
        final String factory;
        final String prefix;
        final boolean named;
        final boolean hasResult;
        final String traits;
        final String what;

        Declare(
                String name,
                String factory,
                String prefix,
                boolean named,
                boolean hasResult,
                String traits,
                String what) {
            this.name = name;
            this.factory = factory;
            this.prefix = prefix;
            this.named = named;
            this.hasResult = hasResult;
            this.traits = traits;
            this.what = what;
        }

        /// Whether the fact is of the class being declared (`Self`) as its owner.
        boolean hasSelf() {
            return this == CONSTRUCTOR || this == METHOD || this == VOID_METHOD;
        }

        String summary() {
            return switch (this) {
                case CONSTRUCTOR -> "a `public` constructor";
                case METHOD -> "a `public` method returning `R`";
                case VOID_METHOD -> "a `public void` method";
                case STATIC_METHOD -> "a `public static` method returning `R`";
                case VOID_STATIC_METHOD -> "a `public static void` method";
            };
        }

        /// The type of the fact declared, of the type variables `as` for its parameters.
        TypeRef fact(List<String> as) {
            return applied(
                    facts(prefix + as.size()),
                    Stream.concat(
                                    Stream.of(exact("Self")).filter(_ -> hasSelf()),
                                    Stream.concat(Stream.of(exact("R")).filter(_ -> hasResult), exacts(as).stream()))
                            .toList());
        }

        /// The parameters before those of the member: its name, its result.
        List<Param> leading() {
            return listOf(
                    named ? List.of(param("name", Types.STRING)) : List.<Param>of(),
                    hasResult ? List.of(param("result", typeToken("R"))) : List.<Param>of());
        }

        List<Expr> factoryArgs() {
            return listOf(
                    List.of(call("builder").call("self")),
                    named ? List.of((Expr) field("name")) : List.<Expr>of(),
                    hasResult ? List.of((Expr) field("result")) : List.<Expr>of());
        }

        Expr traits() {
            return staticField(MEMBER_TRAITS, traits);
        }

        Doc doc() {
            Doc base = Doc.of("Declares " + summary() + ", to be defined by the bodies of the class.");
            Doc named = this.named ? base.param("name", "the method name") : base;
            return hasResult ? named.param("result", "the result type").typeParam("R", "the result type") : named;
        }
    }

    private static JavaFile declarationArities() {
        return arities("DeclarationArities", List.of("Self", "S"), DECLARATIONS, cb -> {
            hook(cb, List.of(), appliedTo(MEMBER_BUILDER, "Self", "S"), "builder");
            hook(cb, List.of(), thrownType(), "thrown");
            hook(cb, List.of(TypeVar.of("F", Types.of(INVOCABLE))), tv("F"), "declare", param("member", tv("F")));
            hook(cb, List.of(TypeVar.of("F", Types.of(INVOCABLE))), tv("F"), "overriding", param("own", tv("F")));
            hook(cb, List.of(), Types.of(MEMBER_TRAITS), "traits", param("traits", Types.of(MEMBER_TRAITS)));
            for (int n = 0; n <= REACH.declared(); n++) {
                declaration(cb, n, Declare.CONSTRUCTOR);
                declaration(cb, n, Declare.METHOD);
                declaration(cb, n, Declare.VOID_METHOD);
            }
            for (int n = 0; n <= REACH.declaredStatic(); n++) {
                declaration(cb, n, Declare.STATIC_METHOD);
                declaration(cb, n, Declare.VOID_STATIC_METHOD);
            }
            for (int n = 0; n <= REACH.declared(); n++) {
                for (OverrideKind override : OverrideKind.values()) {
                    declareOverride(cb, n, override, false);
                    declareOverride(cb, n, override, true);
                }
            }
        });
    }

    private static void declaration(ClassBuilder cb, int n, Declare declare) {
        List<String> as = as(n);
        List<Param> params = listOf(
                declare.leading(),
                IntStream.rangeClosed(1, n)
                        .mapToObj(i -> param("p" + i, typeToken("A" + i)))
                        .toList());
        List<Expr> args = listOf(declare.factoryArgs(), names("p", n), List.of(call("traits", declare.traits())));
        Doc doc = declare.doc();
        Doc documented = IntStream.rangeClosed(1, n)
                .boxed()
                .reduce(
                        doc,
                        (d, i) -> d.param("p" + i, "the " + ordinal(i, n) + "parameter type")
                                .typeParam("A" + i, "the " + ordinal(i, n) + "parameter type"),
                        (_, last) -> last)
                .returns("the fact of the " + declare.what)
                .throws_(ILLEGAL_ARGUMENT, "if javac would reject the " + declare.what + " (see [SignatureRegistry])")
                .throws_(ILLEGAL_STATE, "if the declarations of the class have ended");
        method(
                cb,
                PUBLIC,
                typeVars(concat(declare.hasResult ? List.of("R") : List.of(), as)),
                declare.fact(as),
                declare.name,
                params,
                documented,
                b -> b.return_(call("declare", staticCall(UNSAFE_INVOCABLES, declare.factory, args))));
    }

    /// The constructor forms of a record: `declareCompactConstructor`, `declareCanonicalConstructor` and
    /// `declareConstructor`, at every arity.
    private static JavaFile recordConstructorArities() {
        return arities(
                "RecordConstructorArities",
                List.of("Self"),
                Optional.of(bound(applied(STATIC_FIELD_DECLARATIONS, exact("Self")))),
                TYPED_RECORD_BUILDER,
                cb -> {
                    passingConstructor(
                            cb,
                            List.of(
                                    param("target", Types.of(TARGET_CLASSPATH)),
                                    param("desc", Types.of(CLASS_DESC)),
                                    param("kind", Types.STRING)));
                    hook(cb, List.of(), appliedTo(FINAL_CLASS_TOKEN, "Self"), "finalSelf");
                    hook(
                            cb,
                            List.of(TypeVar.of("F", Types.of(INVOCABLE))),
                            tv("F"),
                            "declareCompact",
                            param("ctor", tv("F")));
                    hook(
                            cb,
                            List.of(TypeVar.of("F", Types.of(INVOCABLE))),
                            tv("F"),
                            "declareCanonical",
                            param("ctor", tv("F")));
                    hook(
                            cb,
                            List.of(TypeVar.of("F", Types.of(INVOCABLE))),
                            tv("F"),
                            "declareAdditional",
                            param("ctor", tv("F")));
                    for (int n = 0; n <= REACH.declared(); n++) {
                        recordConstructor(cb, n, "declareCompactConstructor", "declareCompact", "compact");
                        recordConstructor(cb, n, "declareCanonicalConstructor", "declareCanonical", "canonical");
                        recordConstructor(cb, n, "declareConstructor", "declareAdditional", "additional");
                    }
                });
    }

    /// `declareCompactConstructor(a1, ..., an)` and its like: the fact of a constructor of the record, which
    /// the bodies define.
    private static void recordConstructor(ClassBuilder cb, int n, String name, String declare, String what) {
        List<String> as = as(n);
        List<Param> params = IntStream.rangeClosed(1, n)
                .mapToObj(i -> param("p" + i, typeToken("A" + i)))
                .toList();
        List<Expr> args =
                listOf(List.of(call("finalSelf")), names("p", n), List.of(staticField(MEMBER_TRAITS, "FINAL")));
        String summary =
                switch (what) {
                    case "compact" -> "the compact constructor";
                    case "canonical" -> "the canonical constructor";
                    default -> "an additional constructor";
                };
        Doc doc = Doc.of("Declares " + summary + " of the record, to be defined by the bodies of the record.");
        Doc documented = IntStream.rangeClosed(1, n)
                .boxed()
                .reduce(
                        doc,
                        (d, i) -> d.param("p" + i, "the " + ordinal(i, n) + "parameter type")
                                .typeParam("A" + i, "the type of the " + ordinal(i, n) + "parameter"),
                        (_, last) -> last)
                .returns("the fact of the constructor")
                .throws_(
                        ILLEGAL_ARGUMENT,
                        "if a canonical or compact constructor is declared already, or javac would reject the"
                                + " constructor")
                .throws_(ILLEGAL_STATE, "if the declarations of the record have ended");
        method(
                cb,
                PUBLIC,
                typeVars(as),
                applied(
                        facts("CtorRef" + n),
                        Stream.concat(Stream.of(exact("Self")), exacts(as).stream())
                                .toList()),
                name,
                params,
                documented,
                b -> b.return_(call(declare, staticCall(UNSAFE_INVOCABLES, "ctor", args))));
    }

    /// The constructor forms of an enum: `constructor`, at every arity.
    private static JavaFile enumConstructorArities() {
        return arities(
                "EnumConstructorArities",
                List.of("Self"),
                Optional.of(bound(applied(STATIC_FIELD_DECLARATIONS, exact("Self")))),
                TYPED_ENUM_BUILDER,
                cb -> {
                    passingConstructor(
                            cb,
                            List.of(
                                    param("target", Types.of(TARGET_CLASSPATH)),
                                    param("desc", Types.of(CLASS_DESC)),
                                    param("kind", Types.STRING)));
                    hook(cb, List.of(), Types.of(VOID_BODY), "constructorRoot");
                    hook(
                            cb,
                            List.of(),
                            Optional.empty(),
                            "declaredConstructor",
                            param("params", applied(LIST, unbounded())),
                            param("body", Types.of(VOID_BODY)));
                    hook(
                            cb,
                            List.of(),
                            Optional.empty(),
                            "declaredConstructor",
                            param("params", applied(LIST, unbounded())),
                            param("body", Types.of(VOID_BODY)),
                            param("doc", Types.of(DOC_COMMENT)));
                    for (int n = 0; n <= REACH.declared(); n++) {
                        enumConstructor(cb, n, false);
                        enumConstructor(cb, n, true);
                    }
                });
    }

    /// `constructor(a1, ..., an, body)`: a `private` constructor of the enum whose body the consumer builds.
    /// With `documented`, the first parameter is the documentation comment of the constructor.
    private static void enumConstructor(ClassBuilder cb, int n, boolean documented) {
        List<String> as = as(n);
        TypeRef body = applied(
                MEMBER_BODY_ARITIES.nested("InstanceBody" + n),
                listOf(List.of(exact("Self"), exactType(Types.of(VOID_BODY)), exactType(Types.of(VOID))), exacts(as)));
        List<Param> params = listOf(
                documented ? List.<Param>of(param("doc", Types.of(DOC_COMMENT))) : List.<Param>of(),
                IntStream.rangeClosed(1, n)
                        .mapToObj(i -> param("p" + i, typeToken("A" + i)))
                        .toList(),
                List.of(param("body", body)));
        Doc doc = Doc.of("Declares a `private` constructor of the enum, of " + plural(n, "parameter") + ".")
                .param("body", "the body, given its block, `this` and the parameters");
        if (documented) {
            doc = doc.param("doc", "the documentation comment");
        }
        Doc documentedDoc = IntStream.rangeClosed(1, n)
                .boxed()
                .reduce(
                        doc,
                        (d, i) -> d.param("p" + i, "the " + ordinal(i, n) + "parameter type")
                                .typeParam("A" + i, "the type of the " + ordinal(i, n) + "parameter"),
                        (_, last) -> last)
                .throws_(ILLEGAL_STATE, "if the members of the enum have begun");
        method(cb, PUBLIC, typeVars(as), Optional.empty(), "constructor", params, documentedDoc, b -> {
            b.exprStatement(call("requireHeader", add(literal("a constructor of "), call("described"))));
            b.localVar("root", Types.of(VOID_BODY), call("constructorRoot"));
            b.localVar("self", appliedTo(THIS, "Self"), new_(THIS, field("root"), call("self")));
            IntStream.rangeClosed(1, n)
                    .forEach(i -> b.localVar(
                            "v" + i, appliedTo(VAR, "A" + i), staticCall(VAR, "param", field("p" + i), field("root"))));
            List<Expr> buildArgs = listOf(
                    List.of(field("b"), field("self")),
                    IntStream.rangeClosed(1, n)
                            .mapToObj(i -> (Expr) field("v" + i))
                            .toList());
            b.exprStatement(staticCall(
                    MEMBER_BODIES,
                    "built",
                    field("root"),
                    Exprs.lambda(List.of("b"), field("body").call("build", buildArgs))));
            List<Expr> declared = new java.util.ArrayList<>();
            declared.add(staticCall(
                    LIST,
                    "of",
                    IntStream.rangeClosed(1, n)
                            .mapToObj(i -> (Expr) field("v" + i))
                            .toList()));
            declared.add(field("root"));
            if (documented) {
                declared.add(field("doc"));
            }
            b.exprStatement(call("declaredConstructor", declared));
        });
    }

    /// The ways a class overrides a method of a supertype.
    private enum OverrideKind {
        /// A method of the same result.
        PLAIN,
        /// A method of a narrower result, said by its token.
        NARROWED,
        /// A method whose result is the class itself, said by a witness.
        SELF,
        /// A `void` method.
        VOID;

        boolean hasResult() {
            return this != VOID;
        }

        String description() {
            return switch (this) {
                case PLAIN -> "a method";
                case NARROWED -> "a method with a method of a narrower result (JLS 8.4.5)";
                case SELF -> "a method with a method whose result is the class itself (JLS 8.4.5)";
                case VOID -> "a `void` method";
            };
        }
    }

    /// `declareOverride(method)` and its forms: overridden in the superclass, or in a supertype by a witness.
    private static void declareOverride(ClassBuilder cb, int n, OverrideKind override, boolean witnessed) {
        List<String> as = as(n);
        TypeArg owner = witnessed ? exact("O") : supering("S");
        TypeRef method = applied(
                facts((override.hasResult() ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(owner),
                                Stream.concat(
                                        Stream.of(exact("R")).filter(_ -> override.hasResult()), exacts(as).stream()))
                        .toList());
        String result =
                switch (override) {
                    case PLAIN -> "R";
                    case NARROWED -> "R2";
                    case SELF -> "Self";
                    case VOID -> "";
                };
        TypeRef returned = applied(
                facts((override.hasResult() ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(exact("Self")),
                                Stream.concat(
                                        Stream.of(result)
                                                .filter(_ -> override.hasResult())
                                                .map(TypedCodegen::exact),
                                        exacts(as).stream()))
                        .toList());
        List<Param> params = listOf(
                witnessed ? List.of(param("up", applied(UPCAST, exact("Self"), extending("O")))) : List.<Param>of(),
                List.of(param("method", method)),
                switch (override) {
                    case NARROWED -> List.of(param("result", appliedTo(REF_TOKEN, "R2")));
                    case SELF -> List.of(param("result", applied(UPCAST, exact("Self"), extending("R"))));
                    case PLAIN, VOID -> List.<Param>of();
                });
        List<TypeVar> vars = listOf(
                witnessed ? typeVars("O") : List.<TypeVar>of(),
                override.hasResult() ? typeVars("R") : List.<TypeVar>of(),
                override == OverrideKind.NARROWED ? List.of(TypeVar.of("R2", tv("R"))) : List.<TypeVar>of(),
                typeVars(as));
        Expr self = call("builder").call("self");
        Expr own =
                switch (override) {
                    case PLAIN, VOID -> staticCall(OVERRIDES, "own", self, field("method"), call("thrown"));
                    case NARROWED ->
                        staticCall(OVERRIDES, "own", self, field("method"), field("result"), call("thrown"));
                    case SELF -> staticCall(OVERRIDES, "ownOfSelf", self, field("method"), call("thrown"));
                };
        Doc doc = Doc.of("Overrides " + override.description() + " of " + plural(n, "parameter")
                        + (witnessed
                                ? " of a supertype the class has by the witness `up`."
                                : " the class inherits from its superclass: declares it anew, `@Override public`."))
                .param("method", "the fact of the overridden method");
        Doc documented = (witnessed ? doc.param("up", "the witness that the class is of the supertype") : doc);
        Doc withResult =
                switch (override) {
                    case NARROWED -> documented.param("result", "the result type of the method of the class");
                    case SELF -> documented.param("result", "the witness that the class is of the result type");
                    case PLAIN, VOID -> documented;
                };
        Doc tagged = (witnessed ? withResult.typeParam("O", "the type that has the method") : withResult);
        Doc typed = (override.hasResult() ? tagged.typeParam("R", "the result type of the overridden method") : tagged);
        method(
                cb,
                PUBLIC,
                vars,
                returned,
                "declareOverride",
                params,
                (override == OverrideKind.NARROWED
                                ? typed.typeParam("R2", "the result type of the method of the class")
                                : typed)
                        .typeParams(n, "type")
                        .returns("the fact of the overriding method")
                        .throws_(ILLEGAL_ARGUMENT, "if the method cannot be overridden, see [SignatureRegistry]")
                        .throws_(ILLEGAL_STATE, "if the declarations of the class have ended"),
                b -> b.return_(call("overriding", own)));
    }

    // ------------------------------------------------------------------
    // Declarations of members of variable arity
    // ------------------------------------------------------------------

    private static JavaFile variableArityDeclarationArities() {
        return arities("VariableArityDeclarationArities", List.of("Self", "S"), VARIABLE_ARITY_DECLARATIONS, cb -> {
            hook(cb, List.of(TypeVar.of("F", Types.of(INVOCABLE))), tv("F"), "declare", param("member", tv("F")));
            hook(cb, List.of(), appliedTo(FINAL_CLASS_TOKEN, "Self"), "self");
            hook(
                    cb,
                    List.of(),
                    Types.of(MEMBER_TRAITS),
                    "traits",
                    param("traits", Types.of(MEMBER_TRAITS)),
                    param("last", applied(ARRAY_TOKEN, unbounded(), unbounded())));
            for (int n = 1; n <= REACH.variableArity(); n++) {
                variableArity(cb, n, Declare.CONSTRUCTOR);
                variableArity(cb, n, Declare.METHOD);
                variableArity(cb, n, Declare.VOID_METHOD);
            }
            for (int n = 1; n <= REACH.variableArityStatic(); n++) {
                variableArity(cb, n, Declare.STATIC_METHOD);
                variableArity(cb, n, Declare.VOID_STATIC_METHOD);
            }
        });
    }

    private static void variableArity(ClassBuilder cb, int n, Declare declare) {
        List<String> before = as(n - 1);
        List<String> as = concat(before, List.of("A"));
        List<Param> params = listOf(
                declare.leading(),
                before.stream()
                        .map(a -> param("p" + a.substring(1), typeToken(a)))
                        .toList(),
                List.of(param("last", arrayToken())));
        List<Expr> args = listOf(
                List.of(call("self")),
                declare.named ? List.of((Expr) field("name")) : List.<Expr>of(),
                declare.hasResult ? List.of((Expr) field("result")) : List.<Expr>of(),
                names("p", n - 1),
                List.of(field("last"), call("traits", declare.traits(), field("last"))));
        Doc doc = declare.doc()
                .param("last", "the array type of the last parameter, which is of variable arity")
                .typeParam("A", "the array type");
        Doc documented = IntStream.rangeClosed(1, n - 1)
                .boxed()
                .reduce(
                        doc,
                        (d, i) -> d.param("p" + i, "the " + ordinal(i, n - 1) + "parameter type")
                                .typeParam("A" + i, "the " + ordinal(i, n - 1) + "parameter type"),
                        (_, last) -> last)
                .returns("the fact of the " + declare.what)
                .throws_(ILLEGAL_ARGUMENT, "if javac would reject the " + declare.what)
                .throws_(ILLEGAL_STATE, "if the declarations of the class have ended");
        method(
                cb,
                PUBLIC,
                typeVars(concat(declare.hasResult ? List.of("R") : List.of(), as)),
                declare.fact(as),
                declare.name,
                params,
                documented,
                b -> b.return_(call("declare", staticCall(UNSAFE_INVOCABLES, declare.factory, args))));
    }

    // ------------------------------------------------------------------
    // Overriding: the intentions of a header
    // ------------------------------------------------------------------

    private static JavaFile overridingArities() {
        return arities("OverridingArities", List.of("Self"), OVERRIDING, cb -> {
            hook(cb, List.of(), appliedTo(CLASS_DECLARATION, "Self"), "declaration");
            hook(cb, List.of(), thrownType(), "thrown");
            for (int n = 0; n <= REACH.defined(); n++) {
                for (OverrideKind override : OverrideKind.values()) {
                    overriding(cb, n, override);
                }
            }
        });
    }

    private static void overriding(ClassBuilder cb, int n, OverrideKind override) {
        List<String> as = as(n);
        TypeRef method = applied(
                facts((override.hasResult() ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(exact("O")),
                                Stream.concat(
                                        Stream.of(exact("R")).filter(_ -> override.hasResult()), exacts(as).stream()))
                        .toList());
        String result =
                switch (override) {
                    case PLAIN -> "R";
                    case NARROWED -> "R2";
                    case SELF -> "X";
                    case VOID -> "";
                };
        String family =
                switch (override) {
                    case PLAIN, NARROWED -> "Overriding";
                    case SELF -> "SelfOverriding";
                    case VOID -> "VoidOverriding";
                };
        TypeRef returned = applied(
                facts(family + n),
                Stream.concat(
                                Stream.of(exact("Self")),
                                Stream.concat(
                                        Stream.of(result)
                                                .filter(_ -> override.hasResult())
                                                .map(TypedCodegen::exact),
                                        exacts(as).stream()))
                        .toList());
        List<Param> params = listOf(
                List.of(param("method", method)),
                switch (override) {
                    case NARROWED -> List.of(param("result", appliedTo(REF_TOKEN, "R2")));
                    case SELF -> List.of(param("result", appliedTo(UPCAST, "Self", "X")));
                    case PLAIN, VOID -> List.<Param>of();
                });
        List<TypeVar> vars = listOf(
                typeVars("O"),
                override.hasResult() ? typeVars("R") : List.<TypeVar>of(),
                switch (override) {
                    case NARROWED -> List.of(TypeVar.of("R2", tv("R")));
                    case SELF -> List.of(TypeVar.of("X", tv("R")));
                    case PLAIN, VOID -> List.<TypeVar>of();
                },
                typeVars(as));
        Expr declaration = call("declaration");
        Expr self = declaration.call("self");
        Expr own =
                switch (override) {
                    case PLAIN, VOID -> staticCall(OVERRIDES, "own", self, field("method"), call("thrown"));
                    case NARROWED ->
                        staticCall(OVERRIDES, "own", self, field("method"), field("result"), call("thrown"));
                    case SELF -> staticCall(OVERRIDES, "ownOfSelf", self, field("method"), call("thrown"));
                };
        Doc doc = Doc.of("Says the class overrides " + override.description() + " of " + plural(n, "parameter") + ".")
                .param("method", "the fact of the overridden method");
        Doc withResult =
                switch (override) {
                    case NARROWED -> doc.param("result", "the result type of the method of the class");
                    case SELF -> doc.param("result", "the witness that the class is of the result type");
                    case PLAIN, VOID -> doc;
                };
        Doc typed =
                switch (override) {
                    case PLAIN ->
                        withResult
                                .typeParam("O", "the type that has the method")
                                .typeParam("R", "the result type of the overridden method");
                    case NARROWED ->
                        withResult
                                .typeParam("O", "the type that has the method")
                                .typeParam("R", "the result type of the overridden method")
                                .typeParam("R2", "the result type of the method of the class");
                    case SELF ->
                        withResult
                                .typeParam("O", "the type that has the method")
                                .typeParam("R", "the result type of the overridden method")
                                .typeParam("X", "the supertype of the class the witness is of");
                    case VOID -> withResult.typeParam("O", "the type that has the method");
                };
        method(
                cb,
                PUBLIC,
                vars,
                returned,
                "overriding",
                params,
                typed.typeParams(n, "type")
                        .returns("how the class has the method: by one of its own, `method()` of the result")
                        .throws_(
                                ILLEGAL_ARGUMENT,
                                "if the method is `final` or generic, the class overrides a method of that signature"
                                        + " already, or cannot override what it inherits under it")
                        .throws_(ILLEGAL_STATE, "if the header of the class has ended"),
                b -> b.return_(staticCall(
                        UNSAFE_INVOCABLES,
                        Character.toLowerCase(family.charAt(0)) + family.substring(1) + n,
                        new_(
                                IMPLEMENTED_BY_OVERRIDE,
                                field("method"),
                                declaration.call("intend", field("method"), own, call("thrown"))))));
    }

    // ------------------------------------------------------------------
    // The facts of the methods a class declares to override
    // ------------------------------------------------------------------

    private static JavaFile overrideArities() {
        return arities("OverrideArities", List.of(), OVERRIDES, cb -> {
            for (int n = 0; n <= REACH.defined(); n++) {
                for (OverrideKind override : OverrideKind.values()) {
                    own(cb, n, override);
                }
            }
        });
    }

    /// `Overrides.own(self, method, thrown)`: the fact of the method of the class that overrides `method`.
    private static void own(ClassBuilder cb, int n, OverrideKind override) {
        List<String> as = as(n);
        TypeArg resultOf =
                switch (override) {
                    case NARROWED -> supering("R");
                    case SELF -> unbounded();
                    case PLAIN, VOID -> exact("R");
                };
        TypeRef method = applied(
                facts((override.hasResult() ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(unbounded()),
                                Stream.concat(
                                        Stream.of(resultOf).filter(_ -> override.hasResult()), exacts(as).stream()))
                        .toList());
        String result =
                switch (override) {
                    case PLAIN, NARROWED -> "R";
                    case SELF -> "Self";
                    case VOID -> "";
                };
        TypeRef returned = applied(
                facts((override.hasResult() ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(exact("Self")),
                                Stream.concat(
                                        Stream.of(result)
                                                .filter(_ -> override.hasResult())
                                                .map(TypedCodegen::exact),
                                        exacts(as).stream()))
                        .toList());
        List<Param> params = listOf(
                List.of(param("self", appliedTo(FINAL_CLASS_TOKEN, "Self"))),
                List.of(param("method", method)),
                override == OverrideKind.NARROWED
                        ? List.of(param("result", appliedTo(REF_TOKEN, "R")))
                        : List.<Param>of(),
                List.of(param("thrown", thrownType())));
        List<TypeVar> vars = listOf(
                typeVars("Self"),
                override.hasResult() && override != OverrideKind.SELF ? typeVars("R") : List.<TypeVar>of(),
                typeVars(as));
        List<Expr> args = listOf(
                List.of(field("self"), field("method").call("name")),
                switch (override) {
                    case PLAIN -> List.of((Expr) field("method").call("result"));
                    case NARROWED -> List.of((Expr) field("result"));
                    case SELF -> List.of((Expr) field("self"));
                    case VOID -> List.<Expr>of();
                },
                IntStream.rangeClosed(1, n)
                        .mapToObj(i -> (Expr) field("method").call("param" + i))
                        .toList(),
                List.of(staticCall(OVERRIDES, "traits", field("method"), field("thrown"))));
        Doc doc = Doc.of("The method of `self` that overrides `method`"
                + switch (override) {
                    case PLAIN, VOID -> "";
                    case NARROWED -> " with the result `result`";
                    case SELF -> " with the class itself for its result";
                }
                + ", declaring `thrown`.");
        method(
                cb,
                STATIC,
                vars,
                returned,
                override == OverrideKind.SELF ? "ownOfSelf" : "own",
                params,
                doc,
                b -> b.return_(staticCall(UNSAFE_INVOCABLES, override.hasResult() ? "method" : "voidMethod", args)));
    }

    // ------------------------------------------------------------------
    // The bodies of members
    // ------------------------------------------------------------------

    private static TypeRef instanceBody(int n, TypeRef block, TypeRef result, List<String> as) {
        return applied(
                MEMBER_BODY_ARITIES.nested("InstanceBody" + n),
                Stream.concat(Stream.of(exact("Self"), exactType(block), exactType(result)), exacts(as).stream())
                        .toList());
    }

    private static JavaFile memberBodyArities() {
        return arities("MemberBodyArities", List.of("Self", "S"), MEMBER_BODIES, cb -> {
            hook(cb, List.of(), Types.of(SLOT_AWAITING), "definable", param("member", Types.of(INVOCABLE)));
            hook(
                    cb,
                    List.of(),
                    appliedTo(THIS, "Self"),
                    "thisOf",
                    param("root", applied(BLOCK, unbounded(), unbounded())));
            hook(
                    cb,
                    List.of(),
                    Types.of(NODE),
                    "superCallNode",
                    param("receiver", appliedTo(THIS, "Self")),
                    param("method", Types.of(INVOCABLE)),
                    param("args", applied(LIST, exactType(Types.of(NODE_OPERAND)))),
                    param("passing", Types.of(NODE_PASSING)));
            hook(
                    cb,
                    List.of(TypeVar.of("R"), TypeVar.of("B", applied(BLOCK, exact("R"), exact("B")))),
                    Optional.empty(),
                    "defineIn",
                    param("slot", Types.of(SLOT_AWAITING)),
                    param("root", tv("B")),
                    param("params", applied(LIST, exactType(applied(VAR, unbounded())))),
                    param("bodyFn", function(List.of(supering("B")), exactType(appliedTo(TERMINATED, "R")))));
            for (int n = 0; n <= REACH.defined(); n++) {
                defineInstance(cb, n, true);
                defineInstance(cb, n, false);
                superCall(cb, n, true, false);
                superCall(cb, n, false, false);
                if (n >= 1) {
                    superCall(cb, n, true, true);
                    superCall(cb, n, false, true);
                }
                instanceBodyInterface(cb, n);
            }
            for (int n = 0; n <= REACH.definedStatic(); n++) {
                defineStatic(cb, n, true);
                defineStatic(cb, n, false);
            }
        });
    }

    /// The nested interface of the body of an instance method or constructor of `n` parameters.
    private static void instanceBodyInterface(ClassBuilder cb, int n) {
        List<String> as = as(n);
        cb.withNestedInterface(MEMBER_BODY_ARITIES.nested("InstanceBody" + n), ib -> {
            ib.withAnnotation(FUNCTIONAL_INTERFACE);
            Stream.of("Self", "B", "R").forEach(v -> ib.withTypeParam(v));
            as.forEach(a -> ib.withTypeParam(a));
            ib.withDoc(d -> {
                d.paragraph("The body of an instance method or constructor of " + plural(n, "parameter") + ".");
                d.typeParam("Self", "the class");
                d.typeParam("B", "the body block");
                d.typeParam("R", "the result type, [Void] for `void`");
                IntStream.rangeClosed(1, n)
                        .forEach(i -> d.typeParam("A" + i, "the " + ordinal(i, n) + "parameter type"));
            });
            ib.withAbstractMethod("build", appliedTo(TERMINATED, "R"), amb -> {
                amb.withDoc(d -> {
                    d.paragraph("Builds the body.");
                    d.param("body", "the body block");
                    d.param("self", "`this`");
                    IntStream.rangeClosed(1, n).forEach(i -> d.param("p" + i, "the " + ordinal(i, n) + "parameter"));
                    d.returns("the proof that the body ended");
                });
                amb.withParam("body", tv("B"));
                amb.withParam("self", appliedTo(THIS, "Self"));
                IntStream.rangeClosed(1, n).forEach(i -> amb.withParam("p" + i, var("A" + i)));
            });
        });
    }

    /// The parameters of a member `n` of whose parameters are the variables `p1` to `pn`, the last as the member
    /// declares it, and the statement that makes them from the fact `member`.
    private static void parameters(CodeBuilder b, String member, int n) {
        IntStream.rangeClosed(1, n)
                .forEach(i -> b.localVar(
                        "p" + i,
                        var("A" + i),
                        i == n
                                ? staticCall(
                                        MEMBER_BODIES,
                                        "lastParameter",
                                        field("slot"),
                                        field(member).call("param" + i),
                                        field("root"))
                                : staticCall(VAR, "param", field(member).call("param" + i), field("root"))));
    }

    /// The statements that begin the definition of a member by `fact`: its slot, and the root of its body.
    private static void beginning(
            CodeBuilder b, String fact, ClassDesc root, TypeRef rootType, String label, boolean named) {
        b.localVar("slot", Types.of(SLOT_AWAITING), call("definable", field(fact)));
        b.localVar(
                "root",
                rootType,
                staticCall(
                        root,
                        "root",
                        named ? add(literal(label), field(fact).call("name")) : literal(label),
                        staticCall(MEMBER_BODIES, "declares", field(fact))));
    }

    /// `define(method, body)` of an instance method.
    private static void defineInstance(ClassBuilder cb, int n, boolean hasResult) {
        List<String> as = as(n);
        TypeRef method = applied(
                facts((hasResult ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(exact("Self")),
                                Stream.concat(Stream.of(exact("R")).filter(_ -> hasResult), exacts(as).stream()))
                        .toList());
        TypeRef block = hasResult ? appliedTo(BODY, "R") : Types.of(VOID_BODY);
        TypeRef body = instanceBody(n, block, hasResult ? tv("R") : Types.of(VOID), as);
        List<Expr> parameters = names("p", n);
        Doc doc = Doc.of("Defines a declared " + (hasResult ? "" : "`void` ") + "method; the body gets `this`"
                        + (n == 0 ? "." : " and the " + (n == 1 ? "parameter." : "parameters.")))
                .param("method", "the declared method")
                .param("body", "the body, given its block, `this`" + (n == 0 ? "" : " and the parameters"));
        Doc documented = (hasResult ? doc.typeParam("R", "the result type") : doc)
                .typeParams(n, "type")
                .throws_(
                        ILLEGAL_STATE,
                        "if the method is defined already or was not declared by this class; if the body reaches a"
                                + " `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter"
                                + " of the class out of its scope, or a type the file of the class cannot write");
        voidMethod(
                cb,
                FINAL,
                typeVars(concat(hasResult ? List.of("R") : List.of(), as)),
                "define",
                List.of(param("method", method), param("body", body)),
                documented,
                b -> {
                    beginning(b, "method", hasResult ? BODY : VOID_BODY, block, "body of method ", true);
                    b.localVar("self", appliedTo(THIS, "Self"), call("thisOf", field("root")));
                    parameters(b, "method", n);
                    b.exprStatement(call(
                            "defineIn",
                            field("slot"),
                            field("root"),
                            staticCall(LIST, "of", parameters),
                            Exprs.lambda(
                                    List.of("b"),
                                    field("body")
                                            .call(
                                                    "build",
                                                    Stream.concat(
                                                                    Stream.of((Expr) field("b"), field("self")),
                                                                    parameters.stream())
                                                            .toList()))));
                });
    }

    /// `define(method, body)` of a static method.
    private static void defineStatic(ClassBuilder cb, int n, boolean hasResult) {
        List<String> as = as(n);
        TypeRef method = applied(
                facts((hasResult ? "StaticMethodRef" : "VoidStaticMethodRef") + n),
                Stream.concat(Stream.of(exact("R")).filter(_ -> hasResult), exacts(as).stream())
                        .toList());
        TypeRef block = hasResult ? appliedTo(BODY, "R") : Types.of(VOID_BODY);
        TypeRef body = function(
                Stream.concat(Stream.of(exactType(block)), as.stream().map(a -> exactType(var(a))))
                        .toList(),
                exactType(hasResult ? appliedTo(TERMINATED, "R") : applied(TERMINATED, exactType(Types.of(VOID)))));
        List<Expr> parameters = names("p", n);
        Doc doc = Doc.of("Defines a declared static " + (hasResult ? "" : "`void` ") + "method; the body gets "
                        + (n == 0 ? "no `this`." : "the " + (n == 1 ? "parameter" : "parameters") + ", no `this`."))
                .param("method", "the declared method")
                .param("body", "the body, given its block" + (n == 0 ? "" : " and the parameters"));
        Doc documented = (hasResult ? doc.typeParam("R", "the result type") : doc)
                .typeParams(n, "type")
                .throws_(ILLEGAL_STATE, "as the definition of an instance method");
        voidMethod(
                cb,
                FINAL,
                typeVars(concat(hasResult ? List.of("R") : List.of(), as)),
                "define",
                List.of(param("method", method), param("body", body)),
                documented,
                b -> {
                    beginning(b, "method", hasResult ? BODY : VOID_BODY, block, "body of static method ", true);
                    parameters(b, "method", n);
                    b.exprStatement(call(
                            "defineIn",
                            field("slot"),
                            field("root"),
                            staticCall(LIST, "of", parameters),
                            Exprs.lambda(
                                    List.of("b"),
                                    field("body")
                                            .call(
                                                    "apply",
                                                    Stream.concat(Stream.of((Expr) field("b")), parameters.stream())
                                                            .toList()))));
                });
    }

    /// `superCall(self, method, ...)`: `super.method(...)`.
    private static void superCall(ClassBuilder cb, int n, boolean hasResult, boolean spread) {
        List<String> as = as(n);
        int each = spread ? n - 1 : n;
        TypeRef method = applied(
                facts((hasResult ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(supering("S")),
                                Stream.concat(Stream.of(exact("R")).filter(_ -> hasResult), exacts(as).stream()))
                        .toList());
        List<Param> params = listOf(
                List.of(param("self", appliedTo(THIS, "Self")), param("method", method)),
                IntStream.rangeClosed(1, each)
                        .mapToObj(i -> param("a" + i, expr("A" + i)))
                        .toList(),
                spread ? List.of(param("rest", appliedTo(VARARGS, "A" + n))) : List.<Param>of());
        Expr node = spread
                ? call(
                        "superCallNode",
                        field("self"),
                        field("method"),
                        staticCall(EXPRESSIONS, "spread", field("method"), field("rest"), arguments(each)),
                        staticCall(EXPRESSIONS, "spreading", field("rest")))
                : call("superCallNode", field("self"), field("method"), arguments(n), each());
        Expr value =
                hasResult ? newDiamond(INVOCATION, node, field("method").call("result")) : new_(VOID_INVOCATION, node);
        Doc doc = Doc.of("`super.method("
                        + IntStream.rangeClosed(1, each)
                                .mapToObj(i -> "a" + i + ", ")
                                .collect(Collectors.joining())
                        + (spread ? "..." : "") + ")`" + (hasResult ? "" : " of a `void` method")
                        + (spread
                                ? ", the arguments of the last parameter, which is of variable arity, one by one"
                                : "")
                        + ": the method as the superclass has it, whatever the class being declared overrides it with.")
                .param("self", "`this`")
                .param("method", "the fact of the method")
                .arguments(each, "argument");
        Doc documented = (spread ? doc.param("rest", "the arguments of the last parameter") : doc)
                .typeParams(n, "type")
                .returns("the call")
                .throws_(
                        ILLEGAL_ARGUMENT,
                        "if `self` is not `this` of this class, or the superclass has no such method with a body on the"
                                + " target classpath");
        method(
                cb,
                FINAL,
                typeVars(concat(hasResult ? List.of("R") : List.of(), as)),
                hasResult ? appliedTo(INVOCATION, "R") : Types.of(VOID_INVOCATION),
                "superCall",
                params,
                hasResult ? documented.typeParam("R", "the result type") : documented,
                b -> b.return_(value));
    }

    // ------------------------------------------------------------------
    // The bodies of constructors
    // ------------------------------------------------------------------

    private static TypeRef constructorRef(int n, List<String> as) {
        return applied(facts("CtorRef" + n), exacts(concat(List.of("Self"), as)));
    }

    private static JavaFile typedClassBodyArities() {
        return arities(
                "TypedClassBodyArities",
                List.of("Self"),
                Optional.of(bound(applied(MEMBER_BODIES, exact("Self"), exactType(Types.OBJECT)))),
                TYPED_CLASS_BODIES,
                cb -> {
                    passingConstructor(cb, List.of(param("declared", appliedTo(DECLARED, "Self"))));
                    IntStream.rangeClosed(0, REACH.defined()).forEach(n -> {
                        List<String> as = as(n);
                        List<Expr> parameters = names("p", n);
                        Doc doc = Doc.of("Defines a declared constructor; the body gets `this`"
                                        + (n == 0 ? "." : " and the " + (n == 1 ? "parameter." : "parameters.")))
                                .param("ctor", "the declared constructor")
                                .param(
                                        "body",
                                        "the body, given its block, `this`" + (n == 0 ? "" : " and the parameters"))
                                .typeParams(n, "type")
                                .throws_(ILLEGAL_STATE, "as a definition of a method does ([MemberBodies])");
                        voidMethod(
                                cb,
                                PUBLIC,
                                typeVars(as),
                                "define",
                                List.of(
                                        param("ctor", constructorRef(n, as)),
                                        param("body", instanceBody(n, Types.of(VOID_BODY), Types.of(VOID), as))),
                                doc,
                                b -> {
                                    beginning(b, "ctor", VOID_BODY, Types.of(VOID_BODY), "body of constructor", false);
                                    b.localVar("self", appliedTo(THIS, "Self"), call("thisOf", field("root")));
                                    parameters(b, "ctor", n);
                                    b.exprStatement(call(
                                            "defineIn",
                                            field("slot"),
                                            field("root"),
                                            staticCall(LIST, "of", parameters),
                                            Exprs.lambda(
                                                    List.of("b"),
                                                    field("body")
                                                            .call(
                                                                    "build",
                                                                    Stream.concat(
                                                                                    Stream.of(
                                                                                            (Expr) field("b"),
                                                                                            field("self")),
                                                                                    parameters.stream())
                                                                            .toList()))));
                                });
                    });
                });
    }

    private static TypeRef constructorBody(int n, List<String> as) {
        return applied(TYPED_SUBCLASS_BODY_ARITIES.nested("CtorBody" + n), exacts(concat(List.of("Self", "S"), as)));
    }

    private static JavaFile typedSubclassBodyArities() {
        return arities(
                "TypedSubclassBodyArities",
                List.of("Self", "S"),
                Optional.of(bound(appliedTo(MEMBER_BODIES, "Self", "S"))),
                TYPED_SUBCLASS_BODIES,
                cb -> {
                    passingConstructor(cb, List.of(param("declared", appliedTo(DECLARED, "Self"))));
                    IntStream.rangeClosed(0, REACH.defined()).forEach(n -> {
                        List<String> as = as(n);
                        List<Expr> parameters = names("p", n);
                        Doc doc = Doc.of("Defines a declared constructor of " + plural(n, "parameter")
                                        + ": its body begins with `super(...)`.")
                                .param("ctor", "the declared constructor")
                                .param(
                                        "body",
                                        "the body, given the place of its `super(...)`"
                                                + (n == 0 ? "" : " and the parameters"))
                                .typeParams(n, "type")
                                .throws_(
                                        ILLEGAL_STATE,
                                        "if the body does not begin with `super(...)`, and as a definition of a method"
                                                + " does ([MemberBodies])");
                        voidMethod(
                                cb,
                                PUBLIC,
                                typeVars(as),
                                "define",
                                List.of(param("ctor", constructorRef(n, as)), param("body", constructorBody(n, as))),
                                doc,
                                b -> {
                                    beginning(b, "ctor", VOID_BODY, Types.of(VOID_BODY), "body of constructor", false);
                                    b.localVar(
                                            "site",
                                            appliedTo(SUPER_SITE, "Self", "S"),
                                            call("superSite", field("root")));
                                    parameters(b, "ctor", n);
                                    b.exprStatement(staticCall(
                                            MEMBER_BODIES,
                                            "built",
                                            field("root"),
                                            Exprs.lambda(
                                                    List.of("b"),
                                                    field("body")
                                                            .call(
                                                                    "build",
                                                                    Stream.concat(
                                                                                    Stream.of((Expr) field("site")),
                                                                                    parameters.stream())
                                                                            .toList()))));
                                    b.exprStatement(call(
                                            "definedWithSuper",
                                            field("slot"),
                                            field("root"),
                                            field("site"),
                                            staticCall(LIST, "of", parameters)));
                                });
                        cb.withNestedInterface(TYPED_SUBCLASS_BODY_ARITIES.nested("CtorBody" + n), ib -> {
                            ib.withAnnotation(FUNCTIONAL_INTERFACE);
                            Stream.of("Self", "S").forEach(v -> ib.withTypeParam(v));
                            as.forEach(a -> ib.withTypeParam(a));
                            ib.withDoc(d -> {
                                d.paragraph("The body of a constructor of " + plural(n, "parameter")
                                        + " of a class that extends `S`.");
                                d.typeParam("Self", "the class");
                                d.typeParam("S", "its superclass");
                                IntStream.rangeClosed(1, n)
                                        .forEach(i -> d.typeParam("A" + i, "the " + ordinal(i, n) + "parameter type"));
                            });
                            ib.withAbstractMethod("build", terminatedVoid(), amb -> {
                                amb.withDoc(d -> {
                                    d.paragraph(
                                            "Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.");
                                    d.param("ctor", "the place of `super(...)`");
                                    IntStream.rangeClosed(1, n)
                                            .forEach(i -> d.param("p" + i, "the " + ordinal(i, n) + "parameter"));
                                    d.returns("the proof that the body ended, as `super_` gave it");
                                });
                                amb.withParam("ctor", appliedTo(SUPER_SITE, "Self", "S"));
                                IntStream.rangeClosed(1, n).forEach(i -> amb.withParam("p" + i, var("A" + i)));
                            });
                        });
                    });
                });
    }

    private static TypeRef terminatedVoid() {
        return applied(TERMINATED, exactType(Types.of(VOID)));
    }

    private static JavaFile typedSubclassBuilderArities() {
        return arities(
                "TypedSubclassBuilderArities",
                List.of("Self", "S"),
                Optional.of(bound(appliedTo(MEMBER_BUILDER, "Self", "S"))),
                TYPED_SUBCLASS_BUILDER,
                cb -> {
                    passingConstructor(
                            cb,
                            List.of(
                                    param("declared", appliedTo(CLASS_DECLARATION, "Self")),
                                    param("inherited", Types.of(INHERITANCE)),
                                    param("superclass", Types.of(SUPERCLASS))));
                    hook(cb, List.of(), appliedTo(TYPED_SUBCLASS_BODIES, "Self", "S"), "beginBodies");
                    IntStream.rangeClosed(0, REACH.defined()).forEach(n -> {
                        List<String> as = as(n);
                        Doc doc = Doc.of(
                                        "The declarations of the class end and the bodies of its members begin, with that"
                                                + " of a constructor of " + plural(n, "parameter") + ".")
                                .param("ctor", "a declared constructor of the class")
                                .param(
                                        "first",
                                        "its body, given the place of its `super(...)`"
                                                + (n == 0 ? "" : " and the parameters"))
                                .typeParams(n, "type")
                                .returns("the stage that defines the other declared members")
                                .throws_(
                                        ILLEGAL_STATE,
                                        "if the declarations have ended already, or leave undone what only a method of"
                                                + " the class can do; if the constructor was not declared by this class;"
                                                + " and as a definition of the bodies does");
                        method(
                                cb,
                                PUBLIC,
                                typeVars(as),
                                appliedTo(TYPED_SUBCLASS_BODIES, "Self", "S"),
                                "bodies",
                                List.of(param("ctor", constructorRef(n, as)), param("first", constructorBody(n, as))),
                                doc,
                                b -> {
                                    b.localVar(
                                            "bodies",
                                            appliedTo(TYPED_SUBCLASS_BODIES, "Self", "S"),
                                            call("beginBodies"));
                                    b.exprStatement(field("bodies").call("define", field("ctor"), field("first")));
                                    b.return_(field("bodies"));
                                });
                    });
                });
    }

    // ------------------------------------------------------------------
    // What a class has of its superclass
    // ------------------------------------------------------------------

    private static JavaFile subclassHeaderArities() {
        return arities(
                "SubclassHeaderArities",
                List.of("Self", "S"),
                Optional.of(bound(appliedTo(OVERRIDING, "Self"))),
                SUBCLASS_HEADER,
                cb -> {
                    for (int n = 0; n <= REACH.defined(); n++) {
                        inherited(cb, n, true);
                        inherited(cb, n, false);
                    }
                });
    }

    private static void inherited(ClassBuilder cb, int n, boolean hasResult) {
        List<String> as = as(n);
        TypeRef method = applied(
                facts((hasResult ? "MethodRef" : "VoidMethodRef") + n),
                Stream.concat(
                                Stream.of(supering("S")),
                                Stream.concat(Stream.of(exact("R")).filter(_ -> hasResult), exacts(as).stream()))
                        .toList());
        String family = (hasResult ? "" : "Void") + "Inherited";
        TypeRef returned = applied(
                facts(family + n),
                Stream.concat(
                                Stream.of(exact("Self")),
                                Stream.concat(Stream.of(exact("R")).filter(_ -> hasResult), exacts(as).stream()))
                        .toList());
        Doc doc = Doc.of("Says the class has a " + (hasResult ? "" : "`void` ") + "method of " + plural(n, "parameter")
                        + " by inheriting it from its superclass: what a step of the proof that the class implements an"
                        + " interface takes where the superclass has the method of the interface already.")
                .param("method", "the fact of the method of the superclass");
        Doc documented = (hasResult ? doc.typeParam("R", "the result type") : doc)
                .typeParams(n, "type")
                .returns("how the class has the method: by inheritance, with no method of its own")
                .throws_(
                        ILLEGAL_ARGUMENT,
                        "if the superclass has no such method on the target classpath, or has it abstract, `static` or"
                                + " not `public`, or otherwise than the fact tells")
                .throws_(ILLEGAL_STATE, "if the header of the class has ended");
        method(
                cb,
                PUBLIC,
                typeVars(concat(hasResult ? List.of("R") : List.of(), as)),
                returned,
                "inherited",
                List.of(param("method", method)),
                documented,
                b -> b.return_(staticCall(
                        UNSAFE_INVOCABLES,
                        Character.toLowerCase(family.charAt(0)) + family.substring(1) + n,
                        call("declaration").call("inherit", field("method")))));
    }

    private static JavaFile superSiteArities() {
        return arities("SuperSiteArities", List.of("Self", "S"), SUPER_SITE, cb -> {
            hook(
                    cb,
                    List.of(),
                    terminatedVoid(),
                    "call",
                    param("ctor", Types.of(INVOCABLE)),
                    param("args", applied(LIST, exactType(Types.of(NODE_OPERAND)))),
                    param("passing", Types.of(NODE_PASSING)),
                    param("rest", instanceBody(0, Types.of(VOID_BODY), Types.of(VOID), List.of())));
            for (String prefix : List.of("SuperCtorRef", "CtorRef")) {
                for (int n = 0; n <= REACH.defined(); n++) {
                    superForm(cb, prefix, n, false);
                    if (n >= 1) {
                        superForm(cb, prefix, n, true);
                    }
                }
            }
        });
    }

    /// `super_(ctor, a1, ..., an, rest)`.
    private static void superForm(ClassBuilder cb, String prefix, int n, boolean spread) {
        List<String> as = as(n);
        int each = spread ? n - 1 : n;
        TypeRef ctor = applied(facts(prefix + n), exacts(concat(List.of("S"), as)));
        TypeRef rest = instanceBody(0, Types.of(VOID_BODY), Types.of(VOID), List.of());
        List<Param> params = listOf(
                List.of(param("ctor", ctor)),
                IntStream.rangeClosed(1, each)
                        .mapToObj(i -> param("a" + i, expr("A" + i)))
                        .toList(),
                spread
                        ? List.of(param("rest", appliedTo(VARARGS, "A" + n)), param("body", rest))
                        : List.of(param("rest", rest)));
        Expr invoked = spread
                ? call(
                        "call",
                        field("ctor"),
                        staticCall(EXPRESSIONS, "spread", field("ctor"), field("rest"), arguments(each)),
                        staticCall(EXPRESSIONS, "spreading", field("rest")),
                        field("body"))
                : call("call", field("ctor"), arguments(n), each(), field("rest"));
        Doc doc = Doc.of("`super("
                        + IntStream.rangeClosed(1, each)
                                .mapToObj(i -> "a" + i + ", ")
                                .collect(Collectors.joining())
                        + (spread ? "..." : "") + ");` by "
                        + (prefix.equals("SuperCtorRef")
                                ? "a constructor a subclass alone calls"
                                : "a `public` constructor of a superclass that is not abstract")
                        + (spread
                                ? ", the arguments of its last parameter, which is of variable arity, one by one"
                                : "")
                        + ", then the rest of the body.")
                .param("ctor", "the constructor of the superclass")
                .arguments(each, "argument");
        Doc documented = (spread
                        ? doc.param("rest", "the arguments of the last parameter")
                                .param("body", "what follows the call, given its block and `this`")
                        : doc.param("rest", "what follows the call, given its block and `this`"))
                .typeParams(n, "type")
                .returns("the proof that the body ended")
                .throws_(
                        ILLEGAL_STATE,
                        "if `super_` was called already, the constructor of the superclass throws a checked exception"
                                + " the constructor does not declare, or an argument uses a variable out of scope or"
                                + " throws one");
        method(cb, PUBLIC, typeVars(as), terminatedVoid(), "super_", params, documented, b -> b.return_(invoked));
    }
}
