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
import me.supcheg.javafile.code.MethodCallExpr;
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
    private static final ClassDesc TYPED_RECORD_BODIES = typed("TypedRecordBodies");
    private static final ClassDesc TYPED_INTERFACE_BUILDER = typed("TypedInterfaceBuilder");
    private static final ClassDesc TYPED_INTERFACE_BODIES = typed("TypedInterfaceBodies");
    private static final ClassDesc TYPED_TYPE_BODIES = typed("TypedTypeBodies");
    private static final ClassDesc TYPED_ENUM_CONSTANT_BODIES = typed("TypedEnumConstantBodies");
    private static final ClassDesc TYPED_ENUM_BUILDER = typed("TypedEnumBuilder");
    private static final ClassDesc TYPED_ENUM_BODIES = typed("TypedEnumBodies");
    private static final ClassDesc COMPILED_CLASSES = ClassDesc.of("me.supcheg.javafile.testing", "CompiledClasses");
    private static final ClassDesc NESTED_FORMS = typed("NestedForms");
    private static final ClassDesc UNSAFE_FACTS = facts("UnsafeFacts");
    private static final ClassDesc ENUM_CONSTANT_MEMBER =
            ClassDesc.of("me.supcheg.javafile.model", "EnumConstantMember");
    private static final ClassDesc MEMBER_KIND =
            typed("TypeDeclarationSupport").nested("Member").nested("Kind");
    private static final ClassDesc GENERIC_DECLARATIONS = typed("GenericDeclarations");
    private static final ClassDesc GENERIC_DECLARATION_ARITIES = typed("GenericDeclarationArities");
    private static final ClassDesc ENUM_CONSTANT_BODY_ARITIES = typed("EnumConstantBodyArities");
    private static final ClassDesc TYPE_PARAMETER = typed("TypeParameter");
    private static final ClassDesc CLASS_HEADER = typed("ClassHeader");
    private static final ClassDesc TYPE_DECLARATION_SUPPORT = typed("TypeDeclarationSupport");
    private static final ClassDesc TYPE_MEMBER_DECLARATIONS = typed("TypeMemberDeclarations");
    private static final ClassDesc TYPED_ANNOTATION_TYPE_BUILDER = typed("TypedAnnotationTypeBuilder");
    private static final ClassDesc NESTED_DECLARATIONS = typed("NestedDeclarations");
    private static final ClassDesc NESTED_CLASS_BODIES = NESTED_DECLARATIONS.nested("ClassBodies");
    private static final ClassDesc NESTED_INTERFACE_BODIES = NESTED_DECLARATIONS.nested("InterfaceBodies");
    private static final ClassDesc NESTED_RECORD_BODIES = NESTED_DECLARATIONS.nested("RecordBodies");
    private static final ClassDesc INTERFACE_TOKEN = facts("InterfaceToken");
    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc TYPE_VAR_TOKEN = facts("TypeVarToken");
    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc JAVA_FILE = ClassDesc.of("me.supcheg.javafile", "JavaFile");
    private static final ClassDesc TYPED_JAVA_FILE = typed("TypedJavaFile");
    private static final ClassDesc TYPED_CLASS_HEADER_SPEC = TYPED_JAVA_FILE.nested("TypedClassHeaderSpec");
    private static final ClassDesc TYPED_INTERFACE_SPEC = TYPED_JAVA_FILE.nested("TypedInterfaceSpec");
    private static final ClassDesc TYPED_ANNOTATION_TYPE_SPEC = TYPED_JAVA_FILE.nested("TypedAnnotationTypeSpec");
    private static final ClassDesc TYPED_ENUM_SPEC = TYPED_JAVA_FILE.nested("TypedEnumSpec");
    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang", "Override");
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
                                enumConstructorArities(),
                                genericDeclarationArities(),
                                memberBuilderNestedArities(),
                                typeMemberDeclarationArities(),
                                annotationTypeBuilderArities(),
                                enumConstantBodyArities()),
                        IntStream.rangeClosed(3, REACH.lambdas() + 1).mapToObj(TypedCodegen::function))
                .flatMap(files -> files)
                .toList();
    }

    /// Generates the tests of the forms.
    ///
    /// @return the files
    public static List<JavaFile> generateTests() {
        return Stream.concat(
                        Stream.of(staticFormsTest(), nestedGenericAritiesTest()), ArityTests.files(MAX_ARITY).stream())
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

    // ------------------------------------------------------------------
    // The tests of the member types of four to twelve type parameters
    // ------------------------------------------------------------------

    private static final String NESTED_GENERIC_PACKAGE = "me.supcheg.example";

    /// The generated test of the member types of four to twelve type parameters: a nested class, an
    /// interface and a record of each arity, inside a class, an interface, an annotation type and an enum
    /// constant body, each rendered, compiled under `-Xlint:all` in one run of javac, and run.
    private static JavaFile nestedGenericAritiesTest() {
        return JavaFile.class_(typed("NestedGenericAritiesTest"), cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of());
            cb.withDoc(d -> d.paragraph("The member types of every hierarchy at four to twelve type parameters:"
                    + " a nested class, an interface and a record inside a class, an interface, an annotation type"
                    + " and an enum constant body, each rendered, compiled under `-Xlint:all` and run."));
            IntStream.rangeClosed(4, MAX_ARITY).forEach(n -> nestedAritiesTest(cb, n));
        });
    }

    private static void nestedAritiesTest(ClassBuilder cb, int n) {
        String count = count(n);
        String method = "nestedTypesOf" + Character.toUpperCase(count.charAt(0)) + count.substring(1)
                + "TypeParametersInEveryHierarchyRenderCompileAndRun";
        cb.withVoidMethod(method, mb -> {
            mb.withAnnotation(TEST);
            mb.withThrows(THROWABLE);
            mb.withBody(b -> {
                b.localVar("classSpec", memberClassSpec(n));
                b.localVar("interfaceSpec", memberInterfaceSpec(n));
                b.localVar("recordSpec", memberRecordSpec(n));
                b.localVar("aClass", classHierarchy(n, "Nested" + n + "Class"));
                b.localVar("anInterface", interfaceHierarchy(n, "Nested" + n + "Interface"));
                b.localVar("anAnnotationType", annotationHierarchy(n, "Nested" + n + "Annotation"));
                b.localVar("anEnum", enumHierarchy(n, "Nested" + n + "Enum"));
                Stream.of("aClass", "anInterface", "anAnnotationType", "anEnum")
                        .forEach(holder -> assertMemberTypes(b, holder, n));
                b.localVar(
                        "compiled",
                        staticCall(
                                COMPILED_CLASSES,
                                "ofAll",
                                staticCall(
                                        LIST,
                                        "of",
                                        field("aClass"),
                                        field("anInterface"),
                                        field("anAnnotationType"),
                                        field("anEnum"))));
                IntStream.rangeClosed(0, 3).forEach(index -> runFirst(b, index, nestedBinaryName(n, index)));
            });
        });
    }

    /// The binary name of the nested class the test of `n` type parameters runs: of the class, the
    /// interface, the annotation type and, in the enum, the first constant's body.
    private static String nestedBinaryName(int n, int index) {
        String holder =
                switch (index) {
                    case 0 -> "Nested" + n + "Class";
                    case 1 -> "Nested" + n + "Interface";
                    case 2 -> "Nested" + n + "Annotation";
                    default -> "Nested" + n + "Enum$1";
                };
        return NESTED_GENERIC_PACKAGE + "." + holder + "$Box";
    }

    private static void assertMemberTypes(CodeBuilder b, String holder, int n) {
        b.exprStatement(
                staticCall(ASSERTJ_ASSERTIONS, "assertThat", field(holder).call("render"))
                        .call("contains", literal("public static final class Box" + variables(n) + " {"))
                        .call("contains", literal("public T" + n + " first(T" + n + " v0) {")));
        b.exprStatement(
                staticCall(ASSERTJ_ASSERTIONS, "assertThat", field(holder).call("render"))
                        .call("contains", literal("public interface One" + variables(n) + " {"))
                        .call("contains", literal("T" + n + " first();")));
        b.exprStatement(
                staticCall(ASSERTJ_ASSERTIONS, "assertThat", field(holder).call("render"))
                        .call("contains", literal("public record Pair" + variables(n) + "(T" + n + " first) {")));
    }

    private static void runFirst(CodeBuilder b, int index, String binaryName) {
        b.exprStatement(staticCall(
                        ASSERTJ_ASSERTIONS,
                        "assertThat",
                        staticCall(
                                NESTED_FORMS,
                                "first",
                                field("compiled").call("get", literal(index)),
                                literal(binaryName),
                                literal("x")))
                .call("isEqualTo", literal("x")));
    }

    /// `<T1, T2, ..., Tn>`: the type parameters as the member type writes them.
    private static String variables(int n) {
        return "<" + IntStream.rangeClosed(1, n).mapToObj(i -> "T" + i).collect(Collectors.joining(", ")) + ">";
    }

    /// `T1` to `Tn`: the type variables of the specification of a member type.
    private static List<String> typeVariables(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(i -> "T" + i).toList();
    }

    private static List<TypeArg> objects(int n) {
        return IntStream.range(0, n).mapToObj(_ -> exactType(Types.of(OBJECT))).toList();
    }

    /// `TypedJavaFile.typeParam("T1")` to `TypedJavaFile.typeParam("Tn")`.
    private static List<Expr> typeParams(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> (Expr) staticCall(TYPED_JAVA_FILE, "typeParam", literal("T" + i)))
                .toList();
    }

    /// `Box, typeParam("T1"), ..., typeParam("Tn"), classSpec` and its like.
    private static List<Expr> declaration(String name, int n, String spec) {
        return Stream.concat(
                        Stream.concat(Stream.of((Expr) literal(name)), typeParams(n).stream()), Stream.of(field(spec)))
                .toList();
    }

    private static void declaresNested(CodeBuilder b, Expr receiver, int n) {
        b.exprStatement(receiver.call(
                "declareNestedClass", declaration("Box", n, "classSpec").toArray(Expr[]::new)));
        b.exprStatement(receiver.call(
                "declareNestedInterface", declaration("One", n, "interfaceSpec").toArray(Expr[]::new)));
        b.exprStatement(receiver.call(
                "declareNestedRecord", declaration("Pair", n, "recordSpec").toArray(Expr[]::new)));
    }

    private static Expr classHierarchy(int n, String simple) {
        return staticCall(
                TYPED_JAVA_FILE,
                "declareClass",
                staticCall(UNSAFE_FACTS, "unverifiedClasspath"),
                staticCall(CLASS_DESC, "of", literal(NESTED_GENERIC_PACKAGE), literal(simple)),
                Exprs.newAnonymous(
                        Types.of(TYPED_CLASS_HEADER_SPEC),
                        List.of(),
                        acb -> acb.withMethod("build", applied(MEMBER_BODIES, exact("Self"), unbounded()), mb -> {
                            mb.withAnnotation(OVERRIDE);
                            mb.withTypeParam("Self");
                            mb.withParam("h", appliedTo(CLASS_HEADER, "Self"));
                            mb.withBody(b -> {
                                b.localVar(
                                        "members",
                                        field("h").call("extendsObject").call("members"));
                                declaresNested(b, field("members"), n);
                                b.return_(field("members").call("bodies"));
                            });
                        })));
    }

    private static Expr interfaceHierarchy(int n, String simple) {
        return staticCall(
                TYPED_JAVA_FILE,
                "interface_",
                staticCall(UNSAFE_FACTS, "unverifiedClasspath"),
                staticCall(CLASS_DESC, "of", literal(NESTED_GENERIC_PACKAGE), literal(simple)),
                Exprs.newAnonymous(
                        Types.of(TYPED_INTERFACE_SPEC),
                        List.of(),
                        acb -> acb.withMethod("build", appliedTo(TYPED_INTERFACE_BODIES, "Self"), mb -> {
                            mb.withAnnotation(OVERRIDE);
                            mb.withTypeParam("Self");
                            mb.withParam("ib", appliedTo(TYPED_INTERFACE_BUILDER, "Self"));
                            mb.withBody(b -> {
                                declaresNested(b, field("ib"), n);
                                b.return_(field("ib").call("bodies"));
                            });
                        })));
    }

    private static Expr annotationHierarchy(int n, String simple) {
        return staticCall(
                TYPED_JAVA_FILE,
                "annotationType",
                staticCall(UNSAFE_FACTS, "unverifiedClasspath"),
                staticCall(CLASS_DESC, "of", literal(NESTED_GENERIC_PACKAGE), literal(simple)),
                Exprs.newAnonymous(
                        Types.of(TYPED_ANNOTATION_TYPE_SPEC),
                        List.of(),
                        acb -> acb.withMethod("build", appliedTo(TYPED_ANNOTATION_TYPE_BUILDER, "Self"), mb -> {
                            mb.withAnnotation(OVERRIDE);
                            mb.withTypeParam("Self");
                            mb.withParam("ab", appliedTo(TYPED_ANNOTATION_TYPE_BUILDER, "Self"));
                            mb.withBody(b -> {
                                declaresNested(b, field("ab"), n);
                                b.return_(field("ab"));
                            });
                        })));
    }

    private static Expr enumHierarchy(int n, String simple) {
        return staticCall(
                TYPED_JAVA_FILE,
                "enum_",
                staticCall(UNSAFE_FACTS, "unverifiedClasspath"),
                staticCall(CLASS_DESC, "of", literal(NESTED_GENERIC_PACKAGE), literal(simple)),
                Exprs.newAnonymous(
                        Types.of(TYPED_ENUM_SPEC),
                        List.of(),
                        acb -> acb.withMethod("build", appliedTo(TYPED_ENUM_BODIES, "Self"), mb -> {
                            mb.withAnnotation(OVERRIDE);
                            mb.withTypeParam("Self");
                            mb.withParam("eb", appliedTo(TYPED_ENUM_BUILDER, "Self"));
                            mb.withBody(b -> {
                                b.exprStatement(nestedConstant("A", "declareNestedClass", "Box", n, "classSpec"));
                                b.exprStatement(
                                        nestedConstant("B", "declareNestedInterface", "One", n, "interfaceSpec"));
                                b.exprStatement(nestedConstant("C", "declareNestedRecord", "Pair", n, "recordSpec"));
                                b.return_(field("eb").call("bodies"));
                            });
                        })));
    }

    /// `eb.constant("A", body -> body.declareNestedClass("Box", p1, ..., pn, classSpec))`.
    private static MethodCallExpr nestedConstant(String constant, String method, String name, int n, String spec) {
        return field("eb")
                .call(
                        "constant",
                        literal(constant),
                        Exprs.lambda(
                                List.of("body"),
                                field("body")
                                        .call(method, declaration(name, n, spec).toArray(Expr[]::new))));
    }

    /// The specification of a member class of `n` type parameters, whose method `first` takes and
    /// returns the last of them.
    private static Expr memberClassSpec(int n) {
        List<String> ts = typeVariables(n);
        return Exprs.newAnonymous(
                bound(applied(GENERIC_DECLARATION_ARITIES.nested("TypedGenericClassSpec" + n), objects(n))),
                List.of(),
                acb -> acb.withMethod("build", applied(MEMBER_BODIES, exact("N"), unbounded()), mb -> {
                    mb.withAnnotation(OVERRIDE);
                    mb.withTypeParam("N");
                    ts.forEach(mb::withTypeParam);
                    mb.withParam("h", appliedTo(CLASS_HEADER, "N"));
                    IntStream.rangeClosed(1, n)
                            .forEach(i -> mb.withParam("t" + i, appliedTo(TYPE_VAR_TOKEN, ts.get(i - 1))));
                    mb.withBody(b -> {
                        b.localVar("members", field("h").call("extendsObject").call("members"));
                        b.localVar(
                                "first",
                                field("members")
                                        .call("declareMethod", literal("first"), field("t" + n), field("t" + n)));
                        b.localVar("bodies", field("members").call("bodies"));
                        b.exprStatement(field("bodies")
                                .call(
                                        "define",
                                        field("first"),
                                        Exprs.lambda(
                                                List.of("b", "self", "value"),
                                                field("b").call("return_", field("value")))));
                        b.return_(field("bodies"));
                    });
                }));
    }

    /// The specification of a member interface of `n` type parameters, whose method `first` returns the
    /// last of them.
    private static Expr memberInterfaceSpec(int n) {
        List<String> ts = typeVariables(n);
        return Exprs.newAnonymous(
                bound(applied(GENERIC_DECLARATION_ARITIES.nested("TypedGenericInterfaceSpec" + n), objects(n))),
                List.of(),
                acb -> acb.withMethod("build", appliedTo(TYPED_INTERFACE_BODIES, "I"), mb -> {
                    mb.withAnnotation(OVERRIDE);
                    mb.withTypeParam("I");
                    ts.forEach(mb::withTypeParam);
                    mb.withParam("ib", appliedTo(TYPED_INTERFACE_BUILDER, "I"));
                    IntStream.rangeClosed(1, n)
                            .forEach(i -> mb.withParam("t" + i, appliedTo(TYPE_VAR_TOKEN, ts.get(i - 1))));
                    mb.withBody(b -> {
                        b.exprStatement(field("ib").call("declareAbstractMethod", literal("first"), field("t" + n)));
                        b.return_(field("ib").call("bodies"));
                    });
                }));
    }

    /// The specification of a member record of `n` type parameters, with a component of the last of them.
    private static Expr memberRecordSpec(int n) {
        List<String> ts = typeVariables(n);
        return Exprs.newAnonymous(
                bound(applied(GENERIC_DECLARATION_ARITIES.nested("TypedGenericRecordSpec" + n), objects(n))),
                List.of(),
                acb -> acb.withMethod("build", appliedTo(TYPED_RECORD_BODIES, "R"), mb -> {
                    mb.withAnnotation(OVERRIDE);
                    mb.withTypeParam("R");
                    ts.forEach(mb::withTypeParam);
                    mb.withParam("rb", appliedTo(TYPED_RECORD_BUILDER, "R"));
                    IntStream.rangeClosed(1, n)
                            .forEach(i -> mb.withParam("t" + i, appliedTo(TYPE_VAR_TOKEN, ts.get(i - 1))));
                    mb.withBody(b -> {
                        b.exprStatement(field("rb").call("component", literal("first"), field("t" + n)));
                        b.return_(field("rb").call("bodies"));
                    });
                }));
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

    /// The words of the numbers a count of the forms reaches: `one`, `two`, ... .
    private static final List<String> COUNTS = List.of(
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen");

    /// The word of `n`, as the documentation of a form says it.
    static String count(int n) {
        return n < COUNTS.size() ? COUNTS.get(n) : Integer.toString(n);
    }

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

        /// The parameters `p1` to `pn`, each described by its position.
        Doc params(int n, String what) {
            return IntStream.rangeClosed(1, n)
                    .boxed()
                    .reduce(this, (doc, i) -> doc.param("p" + i, "the " + ordinal(i, n) + what), (_, last) -> last);
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

    // ------------------------------------------------------------------
    // The stages of a generic declaration
    // ------------------------------------------------------------------

    /// A kind of a type whose declaration takes type parameters: a class, an interface or a record.
    ///
    /// @param simple the word the generated stage types are named by
    /// @param entry the name of the entry point
    /// @param bridge the bridge interface of [GenericDeclarations] the entry point hands its spec to
    /// @param builder the first stage of the declaration, which receives the tokens
    /// @param bodies the stage the declaration ends in
    /// @param parameter the name of the first stage of the declaration
    /// @param wildcard whether the bodies are of a wildcard second type argument, as a class's are
    private record GenericKind(
            String simple,
            String entry,
            String bridge,
            ClassDesc builder,
            ClassDesc bodies,
            String parameter,
            boolean wildcard) {
        String noun() {
            return simple.toLowerCase();
        }

        String spec(int n) {
            return "TypedGeneric" + simple + "Spec" + n;
        }

        TypeRef bodiesType(String self) {
            return wildcard ? applied(bodies, exact(self), unbounded()) : appliedTo(bodies, self);
        }
    }

    private static final GenericKind GENERIC_CLASS =
            new GenericKind("Class", "declareClass", "ClassBodies", CLASS_HEADER, MEMBER_BODIES, "h", true);
    private static final GenericKind GENERIC_INTERFACE = new GenericKind(
            "Interface", "interface_", "InterfaceBodies", TYPED_INTERFACE_BUILDER, TYPED_INTERFACE_BODIES, "ib", false);
    private static final GenericKind GENERIC_RECORD =
            new GenericKind("Record", "record", "RecordBodies", TYPED_RECORD_BUILDER, TYPED_RECORD_BODIES, "rb", false);
    private static final List<GenericKind> GENERIC_KINDS = List.of(GENERIC_CLASS, GENERIC_INTERFACE, GENERIC_RECORD);

    /// The entry points of a generic class, interface and record, and their specification stages, for one
    /// to [MAX_ARITY] type parameters: `TypedJavaFile` extends the class, so the stage types are its nested
    /// types and the entry points are its static methods.
    private static JavaFile genericDeclarationArities() {
        return arities("GenericDeclarationArities", List.of(), TYPED_JAVA_FILE, cb -> {
            IntStream.rangeClosed(1, MAX_ARITY).forEach(n -> GENERIC_KINDS.forEach(kind -> genericSpec(cb, kind, n)));
            IntStream.rangeClosed(1, MAX_ARITY).forEach(n -> GENERIC_KINDS.forEach(kind -> genericEntry(cb, kind, n)));
        });
    }

    /// `TypedGenericClassSpec{n}` and its like: the specification a generic declaration takes.
    private static void genericSpec(ClassBuilder cb, GenericKind kind, int n) {
        cb.withNestedInterface(GENERIC_DECLARATION_ARITIES.nested(kind.spec(n)), ib -> {
            generated(ib);
            IntStream.rangeClosed(1, n).forEach(i -> ib.withTypeParam("B" + i));
            ib.withDoc(d -> {
                d.paragraph("The specification of a generic " + kind.noun() + " with " + count(n) + " type"
                        + (n == 1 ? " parameter" : " parameters") + ", whose leftmost "
                        + (n == 1 ? "bound is `B1`" : "bounds are " + bounds(n)) + ".");
                IntStream.rangeClosed(1, n)
                        .forEach(i -> d.typeParam(
                                "B" + i,
                                "the leftmost bound of the " + ordinal(i, n) + "type parameter, `Object` for none"));
            });
            ib.withAbstractMethod("build", kind.bodiesType("Self"), amb -> {
                amb.withDoc(d -> {
                    d.paragraph("Declares the " + kind.noun() + " through `" + kind.parameter
                            + "`, branded with a fresh `Self`, with the "
                            + (n == 1 ? "token of its type parameter" : "tokens of its type parameters") + ".");
                    d.param(
                            kind.parameter,
                            "the self-branded " + (kind == GENERIC_CLASS ? "first stage of the header" : "builder")
                                    + " of the " + kind.noun());
                    IntStream.rangeClosed(1, n)
                            .forEach(i -> d.param("t" + i, "the token of the " + ordinal(i, n) + "type parameter"));
                    d.typeParam("Self", "the brand of the " + kind.noun() + " being declared");
                    IntStream.rangeClosed(1, n)
                            .forEach(i -> d.typeParam("T" + i, "the brand of the " + ordinal(i, n) + "type parameter"));
                    d.returns(
                            "the bodies of the members: the stage the declaration of the " + kind.noun() + " ends in");
                });
                amb.withTypeParam("Self");
                IntStream.rangeClosed(1, n).forEach(i -> amb.withTypeParam("T" + i, bound(tv("B" + i))));
                amb.withParam(kind.parameter, appliedTo(kind.builder, "Self"));
                IntStream.rangeClosed(1, n).forEach(i -> amb.withParam("t" + i, appliedTo(TYPE_VAR_TOKEN, "T" + i)));
            });
        });
    }

    /// ``B1`, `B2` and `B3` ``: the leftmost bounds of the type parameters of a stage.
    private static String bounds(int n) {
        String joined =
                IntStream.rangeClosed(1, n).mapToObj(i -> "`B" + i + "`").collect(Collectors.joining(", "));
        int last = joined.lastIndexOf(", ");
        return last < 0 ? joined : joined.substring(0, last) + " and " + joined.substring(last + 2);
    }

    /// `declareClass(target, desc, p1, ..., pn, spec)` and its like: the entry point of a generic declaration.
    private static void genericEntry(ClassBuilder cb, GenericKind kind, int n) {
        List<String> bs = IntStream.rangeClosed(1, n).mapToObj(i -> "B" + i).toList();
        List<Param> params = listOf(
                List.of(param("target", Types.of(TARGET_CLASSPATH)), param("desc", Types.of(CLASS_DESC))),
                IntStream.rangeClosed(1, n)
                        .mapToObj(i -> param("p" + i, appliedTo(TYPE_PARAMETER, "B" + i)))
                        .toList(),
                List.of(param("spec", applied(GENERIC_DECLARATION_ARITIES.nested(kind.spec(n)), exacts(bs)))));
        String inScope = kind == GENERIC_CLASS
                ? "header, the members and the member types of the class"
                : kind == GENERIC_INTERFACE
                        ? "header, the members and the member types of the interface"
                        : "components, its header, its members and its member types";
        Doc doc = Doc.of("Declares a generic `public " + (kind.wildcard ? "final " : "") + kind.noun() + "` with "
                        + (n == 1 ? "one type parameter" : count(n) + " type parameters")
                        + ": the " + (n == 1 ? "token is" : "tokens are") + " in scope in the " + inScope + ".")
                .param("target", "the classpath the " + kind.noun() + " is compiled against")
                .param("desc", "the " + kind.noun() + "'s binary name")
                .params(n, "type parameter")
                .returns("the rendered " + kind.noun())
                .throws_(
                        ILLEGAL_ARGUMENT,
                        "if a type parameter has the name of another or of the " + kind.noun()
                                + ", or its bound is a raw type; or if `desc` describes a nested type, or a name of it"
                                + " cannot be written")
                .throws_(ILLEGAL_STATE, "if a member `spec` declared is not defined")
                .param(
                        "spec",
                        "declares the " + kind.noun() + ", given its "
                                + (kind == GENERIC_CLASS ? "header" : "builder")
                                + " and the tokens of its type parameters");
        for (int i = 1; i <= n; i++) {
            doc = doc.typeParam("B" + i, "the leftmost bound of the " + ordinal(i, n) + "type parameter");
        }
        method(
                cb,
                STATIC,
                typeVars(bs),
                Types.of(JAVA_FILE),
                kind.entry,
                params,
                doc,
                b -> b.return_(staticCall(
                        GENERIC_DECLARATIONS,
                        kind.entry,
                        field("target"),
                        field("desc"),
                        staticCall(LIST, "of", names("p", n)),
                        declarationBodies(kind, n))));
    }

    /// `new GenericDeclarations.ClassBodies() { ... }` and its like: what gives the specification the
    /// declaration and the tokens of its type parameters.
    private static Expr declarationBodies(GenericKind kind, int n) {
        String holder = kind == GENERIC_CLASS ? "declaration" : "builder";
        return Exprs.newAnonymous(
                Types.of(GENERIC_DECLARATIONS.nested(kind.bridge)),
                List.of(),
                acb -> acb.withMethod("build", kind.bodiesType("Self"), mb -> {
                    mb.withAnnotation(OVERRIDE);
                    mb.withTypeParam("Self");
                    mb.withParam(holder, appliedTo(kind == GENERIC_CLASS ? CLASS_DECLARATION : kind.builder, "Self"));
                    mb.withBody(b -> b.return_(field("spec")
                            .call(
                                    "build",
                                    listOf(
                                                    kind == GENERIC_CLASS
                                                            ? List.<Expr>of(newDiamond(CLASS_HEADER, field(holder)))
                                                            : List.<Expr>of(field(holder)),
                                                    IntStream.rangeClosed(1, n)
                                                            .mapToObj(i -> (Expr) staticCall(
                                                                    GENERIC_DECLARATIONS,
                                                                    "variable",
                                                                    field(holder),
                                                                    literal(i - 1)))
                                                            .toList())
                                            .toArray(Expr[]::new))));
                }));
    }

    // ------------------------------------------------------------------
    // The stages of the member types of four to twelve type parameters
    // ------------------------------------------------------------------

    /// A kind of member type that the types of §6.5 declare: a class, an interface or a record.
    ///
    /// @param method the name of the declaration method
    /// @param simple the word the generated specification stages are named by
    /// @param brand the type variable of the token the declaration returns
    /// @param token the fact of the member type
    /// @param adapter what declares the member type, given its declaration or builder
    /// @param hook the method of the hierarchy that declares the member type, of any arity
    /// @param declaring the name of the type variable of the adapter's generic method
    /// @param parameter the name of the parameter the specification takes the member type by
    /// @param parameterType the type of that parameter
    /// @param variables the name of what the tokens of the type parameters are taken from
    /// @param built the result of the adapter's method
    /// @param call the method of [NestedDeclarations] that declares the member type
    /// @param enclosing whether that method takes the declarations the member type is enclosed by
    private record NestedKind(
            String method,
            String simple,
            String brand,
            ClassDesc token,
            ClassDesc adapter,
            String hook,
            String declaring,
            String parameter,
            TypeRef parameterType,
            String variables,
            TypeRef built,
            String call,
            boolean enclosing) {
        String noun() {
            return simple.toLowerCase();
        }

        String spec(int n) {
            return "TypedGeneric" + simple + "Spec" + n;
        }

        String parameterDescription() {
            return enclosing ? "header" : "builder";
        }
    }

    private static final NestedKind NESTED_CLASS = new NestedKind(
            "declareNestedClass",
            "Class",
            "N",
            FINAL_CLASS_TOKEN,
            NESTED_CLASS_BODIES,
            "nestedClassToken",
            "N2",
            "h",
            appliedTo(CLASS_HEADER, "N2"),
            "declaration",
            applied(MEMBER_BODIES, exact("N2"), unbounded()),
            "class_",
            true);
    private static final NestedKind NESTED_INTERFACE = new NestedKind(
            "declareNestedInterface",
            "Interface",
            "I",
            INTERFACE_TOKEN,
            NESTED_INTERFACE_BODIES,
            "nestedInterfaceToken",
            "I2",
            "ib",
            appliedTo(TYPED_INTERFACE_BUILDER, "I2"),
            "ib",
            appliedTo(TYPED_INTERFACE_BODIES, "I2"),
            "interface_",
            false);
    private static final NestedKind NESTED_RECORD = new NestedKind(
            "declareNestedRecord",
            "Record",
            "R",
            FINAL_CLASS_TOKEN,
            NESTED_RECORD_BODIES,
            "nestedRecordToken",
            "R2",
            "rb",
            appliedTo(TYPED_RECORD_BUILDER, "R2"),
            "rb",
            appliedTo(TYPED_RECORD_BODIES, "R2"),
            "record",
            false);
    private static final List<NestedKind> NESTED_KINDS = List.of(NESTED_CLASS, NESTED_INTERFACE, NESTED_RECORD);

    /// The stages of the member types of a class, four to twelve type parameters, which `MemberBuilder`
    /// inherits: they build the specification into the declaration of a member type, which the hooks of
    /// the builder declare.
    private static JavaFile memberBuilderNestedArities() {
        return arities(
                "MemberBuilderArities",
                List.of("Self", "S"),
                Optional.of(bound(appliedTo(DECLARATIONS, "Self", "S"))),
                MEMBER_BUILDER,
                cb -> {
                    nestedHooks(cb);
                    IntStream.rangeClosed(4, MAX_ARITY)
                            .forEach(n -> NESTED_KINDS.forEach(kind -> nestedOverload(cb, kind, n, false)));
                });
    }

    /// The stages of the member types of an interface, a record or an enum, four to twelve type
    /// parameters, which `TypeMemberDeclarations` inherits: the declaration of the member type is
    /// completed and given to the type within the call.
    private static JavaFile typeMemberDeclarationArities() {
        return arities(
                "TypeMemberDeclarationArities",
                List.of("Self"),
                Optional.of(bound(appliedTo(TYPE_DECLARATION_SUPPORT, "Self"))),
                TYPE_MEMBER_DECLARATIONS,
                cb -> {
                    passingConstructor(
                            cb,
                            List.of(
                                    param("target", Types.of(TARGET_CLASSPATH)),
                                    param("desc", Types.of(CLASS_DESC)),
                                    param("kind", Types.of(STRING))));
                    IntStream.rangeClosed(4, MAX_ARITY)
                            .forEach(n -> NESTED_KINDS.forEach(kind -> nestedOverload(cb, kind, n, true)));
                });
    }

    /// The stages of the member types of an annotation type, four to twelve type parameters, which
    /// `TypedAnnotationTypeBuilder` inherits: each member type it builds, the hook of the builder adds.
    private static JavaFile annotationTypeBuilderArities() {
        return arities("AnnotationTypeBuilderArities", List.of("Self"), TYPED_ANNOTATION_TYPE_BUILDER, cb -> {
            nestedHooks(cb);
            IntStream.rangeClosed(4, MAX_ARITY)
                    .forEach(n -> NESTED_KINDS.forEach(kind -> nestedOverload(cb, kind, n, false)));
        });
    }

    /// The hooks of a hierarchy whose member types a specification declares: the declaration of the
    /// member type of any arity, which the generated stages of four to twelve call.
    private static void nestedHooks(ClassBuilder cb) {
        for (NestedKind kind : NESTED_KINDS) {
            hook(
                    cb,
                    List.of(TypeVar.of(kind.brand())),
                    applied(kind.token(), exact(kind.brand())),
                    kind.hook(),
                    param("name", Types.of(STRING)),
                    param("parameters", applied(LIST, exactType(applied(TYPE_PARAMETER, unbounded())))),
                    param("spec", Types.of(kind.adapter())));
        }
    }

    /// `declareNestedClass(name, p1, ..., pn, spec)` and its like: the declaration of a member type of
    /// `n` type parameters.
    ///
    /// @param direct whether the hierarchy declares the member type here, as `TypeMemberDeclarations`
    ///     does, rather than through a hook
    private static void nestedOverload(ClassBuilder cb, NestedKind kind, int n, boolean direct) {
        List<String> bs = IntStream.rangeClosed(1, n).mapToObj(i -> "B" + i).toList();
        Doc doc = Doc.of("Declares a generic member " + kind.noun() + " with " + count(n) + " type parameters.")
                .param("name", "the simple name of the member " + kind.noun())
                .params(n, "type parameter")
                .param(
                        "spec",
                        "declares the member " + kind.noun() + ", given its " + kind.parameterDescription()
                                + " and the tokens of its type parameters")
                .typeParam(kind.brand(), "the brand of the member " + kind.noun())
                .returns("the token of the member " + kind.noun())
                .throws_(
                        ILLEGAL_ARGUMENT,
                        "if a type parameter has the name of another or of the member " + kind.noun()
                                + ", or its bound is a raw type; or if `name` cannot name a type")
                .throws_(
                        ILLEGAL_STATE,
                        "if the declarations of the type have ended, or a member of the member " + kind.noun()
                                + " is not defined");
        for (int i = 1; i <= n; i++) {
            doc = doc.typeParam("B" + i, "the leftmost bound of the " + ordinal(i, n) + "type parameter");
        }
        List<TypeVar> vars = typeVars(concat(List.of(kind.brand()), bs));
        List<Param> params = listOf(
                List.of(param("name", Types.of(STRING))),
                IntStream.rangeClosed(1, n)
                        .mapToObj(i -> param("p" + i, appliedTo(TYPE_PARAMETER, "B" + i)))
                        .toList(),
                List.of(param("spec", applied(GENERIC_DECLARATION_ARITIES.nested(kind.spec(n)), exacts(bs)))));
        method(cb, PUBLIC, vars, applied(kind.token(), exact(kind.brand())), kind.method(), params, doc, b -> {
            Expr adapter = nestedAdapter(kind, n);
            Expr parameters = staticCall(LIST, "of", names("p", n));
            if (!direct) {
                b.return_(call(kind.hook(), field("name"), parameters, adapter));
                return;
            }
            b.exprStatement(
                    call("requireDeclaring", add(literal("a member " + kind.noun() + " of "), call("described"))));
            Expr initializer = staticCall(
                    NESTED_DECLARATIONS,
                    kind.call(),
                    Stream.concat(
                                    Stream.concat(
                                            Stream.of((Expr) call("target"), call("desc"), field("name"), parameters),
                                            kind.enclosing()
                                                    ? Stream.of((Expr) staticCall(LIST, "of"))
                                                    : Stream.<Expr>empty()),
                                    Stream.of(adapter))
                            .toList());
            b.localVar(
                    "nested",
                    applied(
                            NESTED_DECLARATIONS.nested("NestedType"),
                            exactType(applied(kind.token(), exact(kind.brand())))),
                    initializer);
            b.exprStatement(call("declaredNested", field("nested").call("type")));
            b.return_(field("nested").call("token"));
        });
    }

    /// `new NestedDeclarations.ClassBodies() { ... }` and its like: what gives the specification the
    /// tokens of the type parameters of the member type, by its index.
    private static Expr nestedAdapter(NestedKind kind, int n) {
        return Exprs.newAnonymous(
                Types.of(kind.adapter()),
                List.of(),
                acb -> acb.withMethod("build", kind.built(), mb -> {
                    mb.withAnnotation(OVERRIDE);
                    mb.withTypeParam(kind.declaring());
                    mb.withParam(kind.parameter(), kind.parameterType());
                    if (kind.enclosing()) {
                        mb.withParam("declaration", appliedTo(CLASS_DECLARATION, kind.declaring()));
                    }
                    mb.withBody(b -> b.return_(field("spec")
                            .call(
                                    "build",
                                    Stream.concat(
                                                    Stream.of((Expr) field(kind.parameter())),
                                                    IntStream.range(0, n).mapToObj(i -> (Expr) staticCall(
                                                            NESTED_DECLARATIONS,
                                                            "variable",
                                                            field(kind.variables()),
                                                            literal(i))))
                                            .toArray(Expr[]::new))));
                }));
    }

    // ------------------------------------------------------------------
    // The helper methods of the body of an enum constant
    // ------------------------------------------------------------------

    /// The helper methods of the body of an enum constant, of every arity, which `TypedEnumConstantBodies`
    /// inherits: `declareMethod`, `declareVoidMethod`, `declareStaticMethod` and `declareVoidStaticMethod`.
    private static JavaFile enumConstantBodyArities() {
        return arities(
                "EnumConstantBodyArities",
                List.of("Self"),
                Optional.of(
                        bound(applied(TYPED_TYPE_BODIES, exact("Self"), exactType(Types.of(ENUM_CONSTANT_MEMBER))))),
                TYPED_ENUM_CONSTANT_BODIES,
                cb -> {
                    cb.accept(new ConstructorDecl(
                            List.of(),
                            Set.of(),
                            List.of(
                                    param("owner", appliedTo(TYPED_ENUM_BUILDER, "Self")),
                                    param("abstractMethods", applied(LIST, exactType(Types.of(INVOCABLE))))),
                            new ConstructorBody.Explicit(
                                    CodeBody.EMPTY,
                                    new ConstructorInvocation.Super(List.of(
                                            field("owner"),
                                            staticCall(
                                                    TYPED_ENUM_CONSTANT_BODIES,
                                                    "declared",
                                                    field("owner"),
                                                    field("abstractMethods")))),
                                    CodeBody.EMPTY),
                            List.of(),
                            Optional.empty()));
                    hook(
                            cb,
                            List.of(TypeVar.of("F", Types.of(INVOCABLE))),
                            tv("F"),
                            "declaredHelper",
                            param("member", tv("F")),
                            param("kind", Types.of(MEMBER_KIND)));
                    nestedHooks(cb);
                    IntStream.rangeClosed(4, MAX_ARITY)
                            .forEach(n -> NESTED_KINDS.forEach(kind -> nestedOverload(cb, kind, n, false)));
                    for (int n = 0; n <= MAX_ARITY; n++) {
                        helper(cb, n, false, false);
                        helper(cb, n, true, false);
                        helper(cb, n, false, true);
                        helper(cb, n, true, true);
                    }
                    for (int n = 5; n <= MAX_ARITY; n++) {
                        defineHelper(cb, n, false, false);
                        defineHelper(cb, n, true, false);
                        defineHelper(cb, n, false, true);
                        defineHelper(cb, n, true, true);
                    }
                });
    }

    /// `declareMethod(name, result, p1, ..., pn)` and its like.
    ///
    /// @param voidResult whether the helper is `void`
    /// @param staticMember whether the helper is `static`
    private static void helper(ClassBuilder cb, int n, boolean voidResult, boolean staticMember) {
        List<String> as = as(n);
        String name = voidResult
                ? (staticMember ? "declareVoidStaticMethod" : "declareVoidMethod")
                : (staticMember ? "declareStaticMethod" : "declareMethod");
        String family = (voidResult ? "Void" : "") + (staticMember ? "Static" : "") + "MethodRef" + n;
        TypeRef returned = applied(
                facts(family),
                (staticMember
                                ? Stream.concat(Stream.of("R").filter(_ -> !voidResult), as.stream())
                                : Stream.concat(
                                        Stream.of("Self"),
                                        Stream.concat(Stream.of("R").filter(_ -> !voidResult), as.stream())))
                        .map(TypedCodegen::exact)
                        .toList());
        List<Param> params = listOf(
                List.of(param("name", Types.of(STRING))),
                !voidResult ? List.of(param("result", typeToken("R"))) : List.<Param>of(),
                IntStream.rangeClosed(1, n)
                        .mapToObj(i -> param("p" + i, typeToken("A" + i)))
                        .toList());
        List<String> vars = concat(voidResult ? List.of() : List.of("R"), as);
        List<Expr> args = listOf(
                List.of((Expr) call("self"), field("name")),
                !voidResult ? List.of((Expr) field("result")) : List.<Expr>of(),
                names("p", n),
                List.of(staticField(MEMBER_TRAITS, staticMember ? "FINAL" : "DEFAULT")));
        Doc doc = Doc.of("Declares a `public` "
                        + (staticMember ? "`static` " : "")
                        + (voidResult ? "`void` " : "")
                        + "helper method"
                        + (n == 0 ? "" : " of " + plural(n, "parameter"))
                        + " of the constant's body, which [#define] gives its body.")
                .param("name", "the method name")
                .params(n, "parameter type")
                .typeParams(n, "parameter type");
        Doc documented = (!voidResult ? doc.param("result", "the result type").typeParam("R", "the result type") : doc)
                .returns("the fact of the method")
                .throws_(ILLEGAL_ARGUMENT, "if the method has the signature of a method the constant overrides")
                .throws_(ILLEGAL_STATE, "if the declarations of the body have ended");
        method(
                cb,
                PUBLIC,
                typeVars(vars),
                returned,
                name,
                params,
                documented,
                b -> b.return_(call(
                        "declaredHelper",
                        staticCall(
                                UNSAFE_INVOCABLES,
                                voidResult
                                        ? (staticMember ? "voidStaticMethod" : "voidMethod")
                                        : (staticMember ? "staticMethod" : "method"),
                                args),
                        staticField(MEMBER_KIND, staticMember ? "STATIC" : "METHOD"))));
    }

    /// `define(method, body)` of a helper of the body of an enum constant, of five to twelve parameters:
    /// the body of a constant is defined by [TypedTypeBodies] up to four, as are the bodies of an
    /// interface, a record and an enum, and the helpers reach twelve.
    ///
    /// @param voidResult whether the helper is `void`
    /// @param staticMember whether the helper is `static`
    private static void defineHelper(ClassBuilder cb, int n, boolean voidResult, boolean staticMember) {
        List<String> as = as(n);
        String family = (voidResult ? "Void" : "") + (staticMember ? "Static" : "") + "MethodRef" + n;
        TypeRef methodType = applied(
                facts(family),
                (staticMember
                                ? Stream.concat(Stream.of("R").filter(_ -> !voidResult), as.stream())
                                : Stream.concat(
                                        Stream.of("Self"),
                                        Stream.concat(Stream.of("R").filter(_ -> !voidResult), as.stream())))
                        .map(TypedCodegen::exact)
                        .toList());
        TypeRef bodyType;
        if (staticMember) {
            List<TypeArg> inputs = Stream.concat(
                            Stream.of(exactType(voidResult ? Types.of(VOID_BODY) : appliedTo(BODY, "R"))),
                            as.stream().map(a -> exactType(var(a))))
                    .toList();
            bodyType = function(inputs, exactType(voidResult ? terminatedVoid() : appliedTo(TERMINATED, "R")));
        } else {
            bodyType = instanceBody(
                    n,
                    voidResult ? Types.of(VOID_BODY) : appliedTo(BODY, "R"),
                    voidResult ? Types.of(VOID) : tv("R"),
                    as);
        }
        List<TypeVar> vars = typeVars(concat(voidResult ? List.of() : List.of("R"), as));
        Doc doc = Doc.of("Defines the declared "
                        + (staticMember ? "static " : "")
                        + (voidResult ? "`void` " : "")
                        + "helper method of " + plural(n, "parameter")
                        + " of the constant's body.")
                .param("method", "the declared method")
                .param("body", "the body, given its block" + (staticMember ? "" : ", `this`") + " and the parameters")
                .params(n, "parameter type")
                .typeParams(n, "type parameter")
                .throws_(ILLEGAL_STATE, "as a definition of a method of a class does ([MemberBodies])");
        voidMethod(cb, FINAL, vars, "define", List.of(param("method", methodType), param("body", bodyType)), doc, b -> {
            b.localVar(
                    "slot",
                    Types.of(TYPED_TYPE_BODIES.nested("Slot").nested("Awaiting")),
                    call("definable", field("method")));
            b.localVar(
                    "root",
                    voidResult ? Types.of(VOID_BODY) : appliedTo(BODY, "R"),
                    staticCall(
                            voidResult ? VOID_BODY : BODY,
                            "root",
                            add(literal("body of method "), field("method").call("name")),
                            staticCall(MEMBER_BODIES, "declares", field("method"))));
            if (!staticMember) {
                b.localVar("self", appliedTo(THIS, "Self"), call("thisOf", field("root")));
            }
            IntStream.rangeClosed(1, n)
                    .forEach(i -> b.localVar(
                            "p" + i,
                            var("A" + i),
                            call("lastParameter", field("method").call("param" + i), field("root"))));
            List<Expr> arguments = listOf(
                    List.of((Expr) field("b")), staticMember ? List.<Expr>of() : List.of(field("self")), names("p", n));
            b.exprStatement(call(
                    "defineIn",
                    field("slot"),
                    field("root"),
                    staticCall(LIST, "of", names("p", n)),
                    staticField(TYPED_TYPE_BODIES.nested("Start"), "IMPLICIT"),
                    Exprs.lambda(List.of("b"), field("body").call(staticMember ? "apply" : "build", arguments))));
        });
    }
}
