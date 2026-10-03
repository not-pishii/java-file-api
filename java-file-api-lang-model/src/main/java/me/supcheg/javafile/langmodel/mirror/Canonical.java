package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.DeclaredKind;
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
/// - [TypeModel#skipped()] and [TypeModel#nonPublicBoundTypes()] are left
///   out: they follow from the rest.
///
/// For `interface List<E> extends SequencedCollection<E>` without members:
///
/// ```
/// javafile-facts-canonical 2
/// type java.util.List interface sealed=no
/// tparams #0
/// superclasses -
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
/// With [MemberFilter#DECLARED_PUBLIC], `members declared-public` is
/// followed by a line per member:
///
/// ```
/// member ctor <^0 extends java.lang.Number>(^0, int[]) throws java.io.IOException
/// member field static constant java.lang.String DEFAULT = "hi"
/// member field instance final #0 value
/// member method abstract get(int) -> #0 throws -
/// member method static <^0> of(^0[]) -> java.util.List<^0> throws -
/// ```
///
/// A functional interface has a line for its single abstract method as a
/// member of it, declared or inherited, before the method table: what a
/// `sam` fact says, which a change of a supertype alone can change.
///
/// ```
/// sam apply(#0) -> #1 throws -
/// ```
public final class Canonical {

    /// The first line of every canonical form: the name and version of the
    /// format. A new version changes every fingerprint, so a metamodel of
    /// another version never passes as matching.
    public static final String HEADER = "javafile-facts-canonical 2";

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
                                "supertypes "
                                        + items(sorted(model.supertypes().supertypes().stream()
                                                .map(scope::type))),
                                "enum " + items(model.enumConstants()),
                                "members "
                                        + switch (model.filter()) {
                                            case NONE -> "none";
                                            case DECLARED_PUBLIC -> "declared-public";
                                        }),
                        sorted(model.members().stream().map(m -> member(m, model.typeParams()))).stream(),
                        model
                                .sam()
                                .map(sam -> "sam " + sam.name() + params(sam.params(), scope) + " -> "
                                        + sam.result().map(scope::type).orElse("void")
                                        + throwsClause(sam.throwsTypes(), scope))
                                .stream(),
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

    private static String member(MemberModel member, List<TypeParam> typeTypeParams) {
        return switch (member) {
            case MethodModel method -> {
                Scope scope = new Scope(typeTypeParams, method.typeParams());
                String mode = method.isStatic()
                        ? "static"
                        : method.overridability().name().toLowerCase(Locale.ROOT);
                yield "member method " + mode + " " + typeParamsPrefix(method.typeParams(), scope) + method.name()
                        + params(method.params(), scope) + " -> "
                        + method.result().map(scope::type).orElse("void") + throwsClause(method.throwsTypes(), scope);
            }
            case CtorModel ctor -> {
                Scope scope = new Scope(typeTypeParams, ctor.typeParams());
                String typeParams = ctor.typeParams().isEmpty()
                        ? ""
                        : " " + typeParamsPrefix(ctor.typeParams(), scope).strip();
                yield "member ctor" + typeParams + params(ctor.params(), scope)
                        + throwsClause(ctor.throwsTypes(), scope);
            }
            case FieldModel field -> {
                String prefix = "member field " + (field.isStatic() ? "static " : "instance ");
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
                        : String.join(", ", sorted(throwsTypes.stream().map(scope::type))));
    }

    private static List<String> table(Collection<MethodTableTemplate.Signature> signatures) {
        return sorted(signatures.stream()
                .map(s -> s.params().stream()
                        .map(p -> switch (p) {
                            case MethodTableTemplate.Fixed(ClassDesc erasure) -> erasure(erasure);
                            case MethodTableTemplate.Var(int index, int dimensions) ->
                                "#" + index + "[]".repeat(dimensions);
                        })
                        .collect(Collectors.joining(", ", s.name() + "(", ")"))));
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

    private static List<String> sorted(Stream<String> items) {
        return items.sorted().toList();
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
