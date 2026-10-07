package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Access;
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

import java.lang.constant.ClassDesc;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The canonical form of a [TypeModel] and its fingerprint: the SHA-256 of
/// the form, which a generated metamodel records and the check against the
/// target classpath compares first.
///
/// The form is ASCII text, one item per line, and depends only on what the
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
/// javafile-facts-canonical 7
/// type java.util.List interface sealed=no
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
/// member method public static <^0> of(^0[]) -> java.util.List<^0> throws -
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
/// A type a class can extend or implement tells its heritage with its
/// members ([TypeModel#heritage()]): a line per constructor and per method
/// a class that extends or implements it inherits, see
/// [#heritage(Heritage.Told, List)]. Like the method table, the lines are
/// what the type is, not what a fact says of it: they are in the
/// fingerprint, and are not compared one by one ([Conformance]).
public final class Canonical {

    /// The first line of every canonical form: the name and version of the
    /// format. A new version changes every fingerprint, so a metamodel of
    /// another version never passes as matching.
    public static final String HEADER = "javafile-facts-canonical 7";

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
                                        + (model.sealed() ? "yes" : "no"),
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
                            case Heritage.Told told -> heritage(told, model.typeParams()).stream();
                            case Heritage.Untold _ -> Stream.<String>empty();
                        },
                        Stream.of(
                                "table abstract " + items(table(model.methods().abstractMethods())),
                                "table concrete " + items(table(model.methods().concreteMethods())),
                                "table static " + items(table(model.methods().staticMethods())),
                                "table ctor " + items(table(model.methods().constructors()))))
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
    public boolean equals(Object o) {
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

    /// The lines that tell a heritage, one per member, sorted: the
    /// constructors and the methods a class that extends or implements the
    /// type inherits or could clash with.
    ///
    /// ```
    /// inherit ctor protected (#0, int...) throws java.io.IOException
    /// inherit method public abstract p.Api size() -> int throws - erased () overrides -
    /// inherit method public concrete p.Base add(#0) -> boolean throws - erased (java.lang.Object) overrides p.Api
    /// inherit method public default p.Api <^0> to(^0[]) -> ^0 throws - erased (java.lang.Object[]) overrides -
    /// ```
    ///
    /// A line tells who may name the member (`public`, `protected`,
    /// `package`), and of a method what it is to a class that inherits it
    /// (`abstract`, `default`, `concrete`, `final`, `static`), the type that
    /// declares it, its signature and result as a member of the type, a
    /// parameter of variable arity as `T...`, what it throws, the erasures
    /// of its parameters as it and the methods it overrides declare them,
    /// and the types that declare those.
    ///
    /// @param heritage the heritage
    /// @param typeParams the type parameters of the type the heritage is of
    /// @return the lines, sorted
    /// @throws IllegalArgumentException if the heritage mentions a type variable that is not declared
    public static List<String> heritage(Heritage.Told heritage, List<TypeParam> typeParams) {
        return Stream.concat(
                        heritage.constructors().stream().map(constructor -> {
                            Scope scope = new Scope(typeParams, constructor.typeParams());
                            return "inherit ctor " + visibility(constructor.visibility()) + " "
                                    + typeParamsPrefix(constructor.typeParams(), scope)
                                            .strip()
                                    + params(constructor.params(), constructor.arity(), scope)
                                    + throwsClause(constructor.throwsTypes(), scope);
                        }),
                        heritage.methods().stream().map(method -> {
                            Scope scope = new Scope(typeParams, method.typeParams());
                            return "inherit method " + visibility(method.visibility()) + " "
                                    + method.dispatch().name().toLowerCase(Locale.ROOT) + " "
                                    + binaryName(method.declaredBy()) + " "
                                    + typeParamsPrefix(method.typeParams(), scope) + method.name()
                                    + params(method.params(), method.arity(), scope) + " -> "
                                    + switch (method.result()) {
                                        case Heritage.Result.Nothing _ -> "void";
                                        case Heritage.Result.Of(TypeRef type) -> scope.type(type);
                                    }
                                    + throwsClause(method.throwsTypes(), scope) + " erased "
                                    + items(method.erasures().stream()
                                            .map(erasure -> erasure.stream()
                                                    .map(Canonical::erasure)
                                                    .collect(Collectors.joining(", ", "(", ")")))
                                            .sorted()
                                            .toList())
                                    + " overrides "
                                    + items(method.overrides().stream()
                                            .map(Canonical::binaryName)
                                            .toList());
                        }))
                .sorted()
                .toList();
    }

    private static String visibility(Heritage.Visibility visibility) {
        return switch (visibility) {
            case PUBLIC -> "public";
            case PROTECTED -> "protected";
            case PACKAGE -> "package";
        };
    }

    /// The parameters of a member of a heritage: the last of variable arity as `T...`.
    private static String params(List<TypeRef> params, Heritage.Arity arity, Scope scope) {
        String fixed = params(params, scope);
        return switch (arity) {
            case FIXED -> fixed;
            case VARIABLE -> fixed.substring(0, fixed.length() - "[])".length()) + "...)";
        };
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
                        + params(method.params(), scope) + " -> "
                        + method.result().map(scope::type).orElse("void") + throwsClause(method.throwsTypes(), scope);
            }
            case CtorModel ctor -> {
                Scope scope = new Scope(typeTypeParams, ctor.typeParams());
                yield "member ctor " + access(ctor.access()) + " "
                        + typeParamsPrefix(ctor.typeParams(), scope).strip() + params(ctor.params(), scope)
                        + throwsClause(ctor.throwsTypes(), scope);
            }
            case FieldModel field -> {
                String prefix =
                        "member field " + access(field.access()) + (field.isStatic() ? " static " : " instance ");
                String declaration = new Scope(typeTypeParams, List.of()).type(field.type()) + " " + field.name();
                yield switch (field.mutability()) {
                    case Mutability.Mutable _ -> prefix + "mutable " + declaration;
                    case Mutability.Final _ -> prefix + "final " + declaration;
                    case Mutability.Constant(Object value) ->
                        prefix + "constant " + declaration + " = " + literal(value);
                };
            }
        };
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

    private static String throwsClause(List<? extends TypeRef> throwsTypes, Scope scope) {
        return " throws "
                + (throwsTypes.isEmpty()
                        ? "-"
                        : throwsTypes.stream().map(scope::type).sorted().collect(Collectors.joining(", ")));
    }

    private static List<String> table(Collection<MethodTableTemplate.Signature> signatures) {
        return signatures.stream()
                .map(s -> s.params().stream()
                        .map(p -> switch (p) {
                            case MethodTableTemplate.Fixed(ClassDesc erasure) -> erasure(erasure);
                            case MethodTableTemplate.Var(int index, int dimensions) ->
                                "#" + index + "[]".repeat(dimensions);
                        })
                        .collect(Collectors.joining(", ", s.name() + "(", ")")))
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

    private static String literal(Object constant) {
        return switch (constant) {
            case String string ->
                string.chars().mapToObj(Canonical::escape).collect(Collectors.joining("", "\"", "\""));
            case Character character -> "'" + escape(character) + "'";
            case Long value -> value + "L";
            case Float value -> value + "f";
            default -> constant.toString();
        };
    }

    /// Printable ASCII but quotes and backslashes as is, the rest as a
    /// Unicode escape: the form stays ASCII, one item per line.
    private static String escape(int c) {
        return c >= ' ' && c < 0x7f && c != '"' && c != '\'' && c != '\\'
                ? String.valueOf((char) c)
                : String.format("\\u%04x", c);
    }

    private static String erasure(ClassDesc erasure) {
        if (erasure.isArray()) {
            return erasure(erasure.componentType()) + "[]";
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

    private static String sha256(String text) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("every Java platform supports SHA-256", e);
        }
    }

    /// Positional names of the type variables in scope: those of a member
    /// shadow those of the type, as in Java.
    private record Scope(List<TypeParam> typeTypeParams, List<TypeParam> memberTypeParams) {

        String type(TypeRef type) {
            return switch (type) {
                case PrimitiveTypeRef primitive -> primitive.sourceName();
                case ArrayTypeRef array -> type(array.component()) + "[]";
                case ClassTypeRef cls -> binaryName(cls.desc());
                case ParameterizedTypeRef parameterized ->
                    parameterized.args().stream()
                            .map(this::argument)
                            .collect(Collectors.joining(", ", binaryName(parameterized.raw()) + "<", ">"));
                case TypeVarRef variable -> variable(variable.name());
            };
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
