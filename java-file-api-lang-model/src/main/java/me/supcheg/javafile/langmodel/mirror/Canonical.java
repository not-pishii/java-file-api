package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.Invariants;
import me.supcheg.javafile.annotation.AnnotationMember;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValue;
import me.supcheg.javafile.annotation.ArrayValue;
import me.supcheg.javafile.annotation.ClassValue;
import me.supcheg.javafile.annotation.EnumValue;
import me.supcheg.javafile.annotation.LiteralValue;
import me.supcheg.javafile.annotation.NestedAnnotationValue;
import me.supcheg.javafile.code.BooleanLiteral;
import me.supcheg.javafile.code.DoubleLiteral;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LongLiteral;
import me.supcheg.javafile.code.StringLiteral;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.langmodel.mirror.FieldModel.Mutability;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.constant.ClassDesc;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The canonical form of a [TypeModel] and its fingerprint: the SHA-256 of
/// the form, which a generated metamodel records and the check against the
/// target classpath compares first.
///
/// The form is text, one item per line — a name as it is, which may be of any letters, and the value of
/// a constant in printable ASCII, the rest of it escaped — and is digested as UTF-8. It is one item per
/// line, and depends only on what the
/// model means:
///
/// - type variables are by position — `#0` for the first type parameter of
///   the type, `^0` for the first of a member — so renaming one changes
///   nothing;
/// - members, supertypes, exceptions and the method table are sorted, so the
///   order of declarations and of `Elements` does not matter;
/// - the superclass chain, type parameters and enum constants keep their
///   order, which has meaning;
/// - `superclasses` and `interfaces` are every type a value of the type is
///   assignable to, erased — what the compiler of a generator relied on
///   where it took an expression of the type for one of a supertype —, and
///   `supertypes` the parameterized ones in terms of the type parameters;
/// - [TypeModel#skipped()] and [TypeModel#nonPublicBoundTypes()] are left
///   out: they follow from the rest.
///
/// For `interface List<E> extends SequencedCollection<E>` without members:
///
/// ```
/// javafile-facts-canonical 12
/// type java.util.List interface sealed=no nesting=top
/// tparams #0
/// superclasses -
/// interfaces java.lang.Iterable; java.util.Collection; java.util.SequencedCollection
/// supertypes java.lang.Iterable<#0>; java.util.Collection<#0>; java.util.SequencedCollection<#0>
/// enum -
/// members none
/// table abstract add(#0); get(int); size(); …
/// table concrete equals(java.lang.Object); stream(); …
/// table static copyOf(java.util.Collection); of(); of(java.lang.Object[]); …
/// table ctor -
/// ```
///
/// In the method table a parameter that is a type parameter of the type,
/// or an array of one, is by position too, `add(#0)` and `toArray(#0[])`,
/// and any other is erased; a constructor is under the simple name of its
/// class: `table ctor ArrayList(); ArrayList(int)`.
///
/// With [MemberFilter#DECLARED_ACCESSIBLE], `members declared-accessible` is
/// followed by a line per member: of those the type declares, and of those
/// it adopts from its supertypes that are not `public`
/// ([MirrorTranslator#members(javax.lang.model.element.TypeElement)]), as
/// members of the type — so a change of such a supertype changes the
/// fingerprint of the type that tells its members. A line tells the access
/// of its member, `public` or `protected`: who may use the fact.
///
/// ```
/// member ctor protected <^0 extends java.lang.Number>(^0, int[]) throws java.io.IOException
/// member field public static constant java.lang.String DEFAULT = "hi"
/// member field public instance final #0 value
/// member method public abstract get(int) -> #0 throws -
/// member method public static <^0> of(^0... @SafeVarargs) -> java.util.List<^0> throws -
/// member method public static format(java.lang.String, java.lang.Object...) -> java.lang.String throws -
/// ```
///
/// A functional interface has a line for its single abstract method as a
/// member of it, declared or inherited, before the method table: what a
/// `sam` fact says, which a change of a supertype alone can change.
///
/// ```
/// sam apply(#0) -> #1 throws -
/// ```
///
/// The `type` line tells what a class that extends the type has to know of
/// where it is declared ([Nesting]): `nesting=inner` of a class that is
/// extended with an instance of the class it is nested in. A `sealed` type
/// tells the subtypes it permits too, sorted by binary name:
/// `permits=java.lang.Integer; java.lang.Long`.
///
/// A type a class can extend or implement tells its heritage with its
/// members ([TypeModel#heritage()]): a line per constructor, per signature
/// of a method a class that extends or implements it inherits, and per
/// field and member type whose name it would see, see
/// [#heritage(Heritage.Told)]. Like the method table, the lines are
/// what the type is, not what a fact says of it: they are in the
/// fingerprint, and are not compared one by one ([Conformance]).
public final class Canonical {

    /// The first line of every canonical form: the name and version of the
    /// format. A new version changes every fingerprint, so a metamodel of
    /// another version never passes as matching.
    public static final String HEADER = "javafile-facts-canonical 12";

    private static final Scope NO_VARIABLES = new Scope(List.of(), List.of());

    private final String text;
    private final String fingerprint;

    private Canonical(String text) {
        this.text = text;
        this.fingerprint = sha256(text);
    }

    /// The canonical form of a type.
    ///
    /// @param model the type
    /// @return the canonical form
    /// @throws IllegalArgumentException if the model mentions a type variable it does not declare
    public static Canonical of(TypeModel model) {
        Scope scope = new Scope(model.typeParams(), List.of());
        String text = Stream.of(
                        Stream.of(
                                HEADER,
                                "type " + binaryName(model.desc()) + " " + kind(model.kind()) + " sealed="
                                        + (model.sealed() ? "yes" : "no") + " nesting="
                                        + switch (model.nesting()) {
                                            case TOP_LEVEL -> "top";
                                            case STATIC_MEMBER -> "static";
                                            case INNER -> "inner";
                                        }
                                        + (model.sealed()
                                                ? " permits="
                                                        + items(model.permitted().stream()
                                                                .map(Canonical::binaryName)
                                                                .sorted()
                                                                .toList())
                                                : ""),
                                "annotations "
                                        + items(model.annotations().stream()
                                                .map(use -> annotation(use))
                                                .toList()),
                                "annotation " + annotationModel(model),
                                "tparams " + items(typeParams(model.typeParams(), "#", scope)),
                                "superclasses "
                                        + items(model.superclasses().stream()
                                                .map(Canonical::binaryName)
                                                .toList()),
                                "interfaces "
                                        + items(model.interfaces().stream()
                                                .map(Canonical::binaryName)
                                                .toList()),
                                "supertypes "
                                        + items(model.supertypes().supertypes().stream()
                                                .map(scope::type)
                                                .sorted()
                                                .toList()),
                                "enum " + items(model.enumConstants()),
                                "members "
                                        + switch (model.filter()) {
                                            case NONE -> "none";
                                            case DECLARED_ACCESSIBLE -> "declared-accessible";
                                        }),
                        model.members().stream()
                                .map(m -> member(m, model.typeParams()))
                                .sorted(),
                        model
                                .sam()
                                .map(sam -> "sam " + sam.name() + params(sam.params(), scope) + " -> "
                                        + sam.result().map(scope::type).orElse("void")
                                        + throwsClause(sam.throwsTypes(), scope))
                                .stream(),
                        switch (model.heritage()) {
                            case Heritage.Told told -> heritage(told).stream();
                            case Heritage.Untold _ -> Stream.<String>empty();
                        },
                        Stream.of(
                                "table abstract "
                                        + items(table(
                                                model.methods().abstractMethods(),
                                                model.methods().variableArity())),
                                "table concrete "
                                        + items(table(
                                                model.methods().concreteMethods(),
                                                model.methods().variableArity())),
                                "table static "
                                        + items(table(
                                                model.methods().staticMethods(),
                                                model.methods().variableArity())),
                                "table ctor "
                                        + items(table(
                                                model.methods().constructors(),
                                                model.methods().variableArity()))))
                .flatMap(lines -> lines)
                .collect(Collectors.joining("\n", "", "\n"));
        return new Canonical(text);
    }

    /// The canonical form.
    ///
    /// @return the text, ending with a line break
    public String text() {
        return text;
    }

    /// The SHA-256 of the UTF-8 [#text()], as a
    /// `ShapeOrigin.Metamodel` records it.
    ///
    /// @return 64 lowercase hex digits
    public String fingerprint() {
        return fingerprint;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return o instanceof Canonical other && text.equals(other.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }

    /// The lines that tell a heritage, sorted: one per constructor, per
    /// signature of a method a class that extends or implements the type
    /// inherits, has to implement or could clash with, and per field and
    /// member type whose name such a class sees.
    ///
    /// ```
    /// inherit ctor Base(#0, int[]) protected (#0, int...) throws java.io.IOException
    /// inherit field protected p.Base count
    /// inherit method add(#0) erased (java.lang.Object) overrides p.I by class public final p.B (#0) -> int ...
    /// inherit method run() erased () overrides - by class public abstract p.A () -> void throws - | ...
    /// inherit method to(java.lang.Object[]) erased (java.lang.Object[]) overrides - by interface default ...
    /// inherit type public p.Base Entry
    /// ```
    ///
    /// A line of a method tells its signature as the method table has it, the
    /// erasures of its parameters as the declarations and the methods they
    /// override declare them, the types that declare those, and after `by`
    /// every declaration the type has under the signature: declared by a
    /// class, with who may name it (`public`, `protected`, `package`) and
    /// what it is (`abstract`, `concrete`, `final`, `static`), or by an
    /// interface, `abstract` or `default`; the type that declares it, its
    /// parameters and result as a member of the type, a parameter of
    /// variable arity as `T...`, and what it throws. A type parameter of the
    /// type is `#0`, one of the member `^0`, by position.
    ///
    /// @param heritage the heritage
    /// @return the lines, sorted
    /// @throws IllegalArgumentException if a fixed type of the heritage mentions a type variable
    public static List<String> heritage(Heritage.Told heritage) {
        return Stream.of(
                        heritage.constructors().stream()
                                .map(constructor -> "inherit ctor " + signature(constructor.signature()) + " "
                                        + visibility(constructor.visibility()) + " "
                                        + variables(constructor.typeParams())
                                        + told(constructor.params(), constructor.arity())
                                        + thrown(constructor.throwsTypes())),
                        heritage.methods().stream()
                                .map(method -> "inherit method " + signature(method.signature()) + " erased "
                                        + items(method.erasures().stream()
                                                .map(erasure -> erasure.stream()
                                                        .map(Canonical::erasure)
                                                        .collect(Collectors.joining(", ", "(", ")")))
                                                .sorted()
                                                .toList())
                                        + " overrides "
                                        + items(method.overrides().stream()
                                                .map(Canonical::binaryName)
                                                .toList())
                                        + " by "
                                        + method.declarations().stream()
                                                .map(Canonical::declaration)
                                                .collect(Collectors.joining(" | "))),
                        heritage.named().stream().map(named -> switch (named) {
                            case Heritage.Named.Field(
                                    String name,
                                    ClassDesc declaredBy,
                                    Heritage.Visibility visibility) ->
                                "inherit field " + visibility(visibility) + " " + binaryName(declaredBy) + " " + name;
                            case Heritage.Named.MemberType(
                                    String name,
                                    ClassDesc declaredBy,
                                    Heritage.Visibility visibility) ->
                                "inherit type " + visibility(visibility) + " " + binaryName(declaredBy) + " " + name;
                        }))
                .flatMap(lines -> lines)
                .sorted()
                .toList();
    }

    private static String declaration(Heritage.Declaration declaration) {
        return switch (declaration.by()) {
                    case Heritage.Declarer.OfClass(
                            ClassDesc type,
                            Heritage.Visibility visibility,
                            Heritage.ClassMethod method) ->
                        "class " + visibility(visibility) + " "
                                + switch (method) {
                                    case ABSTRACT -> "abstract";
                                    case CONCRETE -> "concrete";
                                    case FINAL -> "final";
                                    case STATIC -> "static";
                                }
                                + " " + binaryName(type);
                    case Heritage.Declarer.OfInterface(ClassDesc type, Heritage.InterfaceMethod method) ->
                        "interface "
                                + switch (method) {
                                    case ABSTRACT -> "abstract";
                                    case DEFAULT -> "default";
                                }
                                + " " + binaryName(type);
                }
                + " " + variables(declaration.typeParams())
                + told(declaration.params(), declaration.arity()) + " -> "
                + switch (declaration.result()) {
                    case Heritage.Result.Nothing<Heritage.Type> _ -> "void";
                    case Heritage.Result.Of<Heritage.Type>(Heritage.Type type) -> type(type);
                }
                + thrown(declaration.throwsTypes());
    }

    private static String visibility(Heritage.Visibility visibility) {
        return switch (visibility) {
            case PUBLIC -> "public";
            case PROTECTED -> "protected";
            case PACKAGE -> "package";
        };
    }

    /// The type parameters of a member of a heritage, each by its position, with its bounds.
    private static String variables(List<Heritage.Variable> variables) {
        return variables.isEmpty()
                ? ""
                : IntStream.range(0, variables.size())
                        .mapToObj(i -> {
                            List<Heritage.Type> bounds = variables.get(i).bounds();
                            return "^" + i
                                    + (bounds.isEmpty()
                                            ? ""
                                            : bounds.stream()
                                                    .map(Canonical::type)
                                                    .collect(Collectors.joining(" & ", " extends ", "")));
                        })
                        .collect(Collectors.joining(", ", "<", "> "));
    }

    /// The parameters of a member of a heritage: the last of variable arity as `T...`.
    private static String told(List<Heritage.Type> params, Arity arity) {
        return variable(params.stream().map(Canonical::type).collect(Collectors.joining(", ", "(", ")")), arity);
    }

    /// The parameters of a method or constructor, the last of which is an array if the arity is variable:
    /// `(int, T...)`, and `(int, T... @SafeVarargs)` of a declaration that vouches for the array.
    private static String variable(String params, Arity arity) {
        return switch (arity) {
            case Arity.Fixed _ -> params;
            case Arity.Variable(Arity.Safety safety) ->
                params.substring(0, params.length() - "[])".length())
                        + switch (safety) {
                            case UNCHECKED -> "...)";
                            case SAFE_VARARGS -> "... @SafeVarargs)";
                        };
        };
    }

    private static String thrown(List<Heritage.Type> throwsTypes) {
        return " throws "
                + (throwsTypes.isEmpty()
                        ? "-"
                        : throwsTypes.stream().map(Canonical::type).sorted().collect(Collectors.joining(", ")));
    }

    /// A type of a heritage: its type parameters by position, as [Scope] writes those of a member model.
    private static String type(Heritage.Type type) {
        return switch (type) {
            case Heritage.Type.Fixed(TypeRef fixed) -> NO_VARIABLES.type(fixed);
            case Heritage.Type.OfType(int index) -> "#" + index;
            case Heritage.Type.OfMember(int index) -> "^" + index;
            case Heritage.Type.Array(Heritage.Type component) -> type(component) + "[]";
            case Heritage.Type.Parameterized(ClassDesc raw, List<Heritage.Argument> arguments) ->
                arguments.stream()
                        .map(argument -> switch (argument) {
                            case Heritage.Argument.Exact(Heritage.Type exact) -> type(exact);
                            case Heritage.Argument.Extends(Heritage.Type bound) -> "? extends " + type(bound);
                            case Heritage.Argument.Super(Heritage.Type bound) -> "? super " + type(bound);
                            case Heritage.Argument.Unbounded _ -> "?";
                        })
                        .collect(Collectors.joining(", ", binaryName(raw) + "<", ">"));
        };
    }

    private static String signature(MethodTableTemplate.Signature signature) {
        return table(List.of(signature), Set.of()).getFirst();
    }

    private static String member(MemberModel member, List<TypeParam> typeTypeParams) {
        return switch (member) {
            case MethodModel method -> {
                Scope scope = new Scope(typeTypeParams, method.typeParams());
                String mode = method.isStatic()
                        ? "static"
                        : method.overridability().name().toLowerCase(Locale.ROOT);
                yield "member method " + access(method.access()) + " " + mode + " "
                        + typeParamsPrefix(method.typeParams(), scope) + method.name()
                        + variable(params(method.params(), method.parameterAnnotations(), scope), method.arity())
                        + " -> " + method.result().map(scope::type).orElse("void")
                        + throwsClause(method.throwsTypes(), scope) + annotations(method.annotations());
            }
            case CtorModel ctor -> {
                Scope scope = new Scope(typeTypeParams, ctor.typeParams());
                yield "member ctor " + access(ctor.access()) + " "
                        + typeParamsPrefix(ctor.typeParams(), scope).strip()
                        + variable(params(ctor.params(), ctor.parameterAnnotations(), scope), ctor.arity())
                        + throwsClause(ctor.throwsTypes(), scope) + annotations(ctor.annotations());
            }
            case FieldModel field -> {
                String prefix =
                        "member field " + access(field.access()) + (field.isStatic() ? " static " : " instance ");
                String declaration = new Scope(typeTypeParams, List.of()).type(field.type()) + " " + field.name();
                String declared =
                        switch (field.mutability()) {
                            case Mutability.Mutable _ -> prefix + "mutable " + declaration;
                            case Mutability.Final _ -> prefix + "final " + declaration;
                            case Mutability.Constant(ConstantValue value) ->
                                prefix + "constant " + declaration + " = " + literal(value);
                        };
                yield declared + annotations(field.annotations());
            }
        };
    }

    /// The annotations of a declaration, as a suffix, or nothing if it has none.
    private static String annotations(List<AnnotationUse> annotations) {
        return annotations.isEmpty()
                ? ""
                : " annotations "
                        + items(annotations.stream().map(Canonical::annotation).toList());
    }

    private static String annotation(AnnotationUse use) {
        String name = "@" + binaryName(use.type());
        List<AnnotationMember> members = use.members();
        if (members.isEmpty()) {
            return name;
        }
        if (members.size() == 1 && members.getFirst().name().equals("value")) {
            return name + "(" + annotationValue(members.getFirst().value()) + ")";
        }
        return name + "("
                + members.stream()
                        .map(member -> member.name() + " = " + annotationValue(member.value()))
                        .collect(Collectors.joining(", "))
                + ")";
    }

    private static String annotationValue(AnnotationValue value) {
        return switch (value) {
            case LiteralValue(var literal) -> literal(literal);
            case ClassValue(var type) -> classLiteral(type) + ".class";
            case EnumValue(var type, var constant) -> binaryName(type) + "." + constant;
            case NestedAnnotationValue(var nested) -> annotation(nested);
            case ArrayValue(var elements) ->
                elements.stream().map(Canonical::annotationValue).collect(Collectors.joining("; ", "{", "}"));
        };
    }

    private static String classLiteral(ClassDesc type) {
        ClassDesc component = type.componentType();
        if (component != null) {
            // an array is the only type with a component
            return classLiteral(component) + "[]";
        }
        return type.isPrimitive() ? type.displayName() : binaryName(type);
    }

    private static String literal(me.supcheg.javafile.code.ConstantLiteral literal) {
        return switch (literal) {
            case StringLiteral(var value) -> literal(new ConstantValue.OfString(value));
            case IntLiteral(var value) -> Integer.toString(value);
            case LongLiteral(var value) -> value + "L";
            case me.supcheg.javafile.code.FloatLiteral(var value) -> value + "f";
            case DoubleLiteral(var value) -> Double.toString(value);
            case BooleanLiteral(var value) -> Boolean.toString(value);
        };
    }

    /// The elements and metadata of an annotation type, or `-` for a type
    /// that is no annotation type.
    private static String annotationModel(TypeModel model) {
        return model.annotation()
                .map(annotation -> items(annotation.elements().stream()
                                .map(element -> element.name() + ": " + NO_VARIABLES.type(element.type())
                                        + element.defaultValue()
                                                .map(value -> " = " + annotationValue(value))
                                                .orElse(""))
                                .toList())
                        + "; retention " + annotation.retention()
                        + "; targets "
                        + items(annotation.targets().stream()
                                .map(ElementType::name)
                                .sorted()
                                .toList())
                        + "; repeatable " + (annotation.repeatable() ? "yes" : "no"))
                .orElse("-");
    }

    private static String typeParamsPrefix(List<TypeParam> typeParams, Scope scope) {
        return typeParams.isEmpty()
                ? ""
                : typeParams(typeParams, "^", scope).stream().collect(Collectors.joining(", ", "<", "> "));
    }

    private static List<String> typeParams(List<TypeParam> typeParams, String prefix, Scope scope) {
        return IntStream.range(0, typeParams.size())
                .mapToObj(i -> {
                    List<String> bounds =
                            typeParams.get(i).bounds().stream().map(scope::type).toList();
                    return prefix + i + (bounds.isEmpty() ? "" : " extends " + String.join(" & ", bounds));
                })
                .toList();
    }

    private static String params(List<TypeRef> params, Scope scope) {
        return params.stream().map(scope::type).collect(Collectors.joining(", ", "(", ")"));
    }

    /// The parameters of a member, each with the annotations it declares
    /// before its type: `(@p.A java.lang.String, int)`.
    private static String params(List<TypeRef> params, List<List<AnnotationUse>> annotations, Scope scope) {
        return IntStream.range(0, params.size())
                .mapToObj(i -> {
                    List<AnnotationUse> declared = annotations.get(i);
                    String prefix = declared.isEmpty()
                            ? ""
                            : declared.stream().map(Canonical::annotation).collect(Collectors.joining(" ", "", " "));
                    return prefix + scope.type(params.get(i));
                })
                .collect(Collectors.joining(", ", "(", ")"));
    }

    private static String throwsClause(List<? extends TypeRef> throwsTypes, Scope scope) {
        return " throws "
                + (throwsTypes.isEmpty()
                        ? "-"
                        : throwsTypes.stream().map(scope::type).sorted().collect(Collectors.joining(", ")));
    }

    private static List<String> table(
            Collection<MethodTableTemplate.Signature> signatures, Set<MethodTableTemplate.Signature> variableArity) {
        return signatures.stream()
                .map(s -> {
                    List<String> params = s.params().stream()
                            .map(p -> switch (p) {
                                case MethodTableTemplate.Fixed(ClassDesc erasure) -> erasure(erasure);
                                case MethodTableTemplate.Var(int index, int dimensions) ->
                                    "#" + index + "[]".repeat(dimensions);
                            })
                            .collect(Collectors.toCollection(ArrayList::new));
                    if (variableArity.contains(s)) {
                        int last = params.size() - 1;
                        params.set(last, params.get(last) + "...");
                    }
                    return s.name() + "(" + String.join(", ", params) + ")";
                })
                .sorted()
                .toList();
    }

    private static String access(Access access) {
        return switch (access) {
            case PUBLIC -> "public";
            case PROTECTED -> "protected";
        };
    }

    private static String kind(DeclaredKind kind) {
        return switch (kind) {
            case DeclaredKind.FinalClass _ -> "final-class";
            case DeclaredKind.OpenClass _ -> "open-class";
            case DeclaredKind.AbstractClass _ -> "abstract-class";
            case DeclaredKind.Interface _ -> "interface";
            case DeclaredKind.EnumClass _ -> "enum";
        };
    }

    private static String literal(ConstantValue constant) {
        return switch (constant) {
            case ConstantValue.OfString(String value) ->
                value.chars().mapToObj(Canonical::escape).collect(Collectors.joining("", "\"", "\""));
            case ConstantValue.OfChar(char value) -> "'" + escape(value) + "'";
            case ConstantValue.OfLong(long value) -> value + "L";
            case ConstantValue.OfFloat(float value) -> value + "f";
            case ConstantValue.OfBoolean(boolean value) -> Boolean.toString(value);
            case ConstantValue.OfByte(byte value) -> Byte.toString(value);
            case ConstantValue.OfShort(short value) -> Short.toString(value);
            case ConstantValue.OfInt(int value) -> Integer.toString(value);
            case ConstantValue.OfDouble(double value) -> Double.toString(value);
        };
    }

    /// Printable ASCII but quotes and backslashes as is, the rest as a
    /// Unicode escape: the form stays ASCII, one item per line.
    private static String escape(int c) {
        return c >= ' ' && c < 0x7f && c != '"' && c != '\'' && c != '\\'
                ? String.valueOf((char) c)
                : String.format(Locale.ROOT, "\\u%04x", c);
    }

    private static String erasure(ClassDesc erasure) {
        ClassDesc component = erasure.componentType();
        if (component != null) {
            // an array is the only type with a component
            return erasure(component) + "[]";
        }
        return erasure.isPrimitive() ? erasure.displayName() : binaryName(erasure);
    }

    private static String binaryName(ClassDesc desc) {
        String descriptor = desc.descriptorString();
        return descriptor.substring(1, descriptor.length() - 1).replace('/', '.');
    }

    private static String items(List<String> items) {
        return items.isEmpty() ? "-" : String.join("; ", items);
    }

    /// The digest of a text, in hex: by SHA-256, which every Java platform supports.
    private static String sha256(String text) {
        MessageDigest digest =
                Invariants.sure(() -> MessageDigest.getInstance("SHA-256"), "every Java platform supports SHA-256");
        return HexFormat.of().formatHex(digest.digest(text.getBytes(StandardCharsets.UTF_8)));
    }

    /// Positional names of the type variables in scope: those of a member
    /// shadow those of the type, as in Java.
    private record Scope(List<TypeParam> typeTypeParams, List<TypeParam> memberTypeParams) {

        String type(TypeRef type) {
            return switch (type) {
                case PrimitiveTypeRef primitive -> primitive.sourceName();
                case ArrayTypeRef array -> prefixed(array.annotations()) + type(array.component()) + "[]";
                case ClassTypeRef cls -> prefixed(cls.annotations()) + binaryName(cls.desc());
                case ParameterizedTypeRef parameterized ->
                    prefixed(parameterized.annotations())
                            + parameterized.args().stream()
                                    .map(this::argument)
                                    .collect(Collectors.joining(", ", binaryName(parameterized.raw()) + "<", ">"));
                case TypeVarRef variable -> prefixed(variable.annotations()) + variable(variable.name());
            };
        }

        private static String prefixed(List<AnnotationUse> annotations) {
            return annotations.isEmpty()
                    ? ""
                    : annotations.stream().map(Canonical::annotation).collect(Collectors.joining(" ", "", " "));
        }

        private String argument(TypeArg argument) {
            return switch (argument) {
                case ExactTypeArg exact -> type(exact.type());
                case ExtendsTypeArg bound -> "? extends " + type(bound.bound());
                case SuperTypeArg bound -> "? super " + type(bound.bound());
                case UnboundedTypeArg _ -> "?";
            };
        }

        private String variable(String name) {
            return position(memberTypeParams, name)
                    .map(i -> "^" + i)
                    .or(() -> position(typeTypeParams, name).map(i -> "#" + i))
                    .orElseThrow(() -> new IllegalArgumentException("type variable " + name + " is not declared"));
        }

        private static Optional<Integer> position(List<TypeParam> typeParams, String name) {
            return IntStream.range(0, typeParams.size())
                    .filter(i -> typeParams.get(i).name().equals(name))
                    .boxed()
                    .findFirst();
        }
    }
}
