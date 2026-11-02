package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.RecordComponent;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeNames;
import me.supcheg.javafile.facts.TypeToken;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/// Makes patterns for `instanceof` and a pattern `switch` (JLS 14.30), and
/// checks them against the facts of the types they name.
///
/// ```java
/// PatternBinding<Prim.Int> x = Patterns.binding(PrimitiveToken.INT);
/// PatternBinding<Prim.Int> y = Patterns.binding(PrimitiveToken.INT);
/// // Point(int x, int y)
/// TypedPattern<Point> point = Patterns.record(Point_.TOKEN, Patterns.type(x), Patterns.type(y));
/// // Line(Point(int x1, int y1), Point(int x2, int y2))
/// TypedPattern<Line> line = Patterns.record(
///         Line_.TOKEN,
///         Patterns.record(Point_.TOKEN, Patterns.type(x1), Patterns.type(y1)),
///         Patterns.record(Point_.TOKEN, Patterns.type(x2), Patterns.type(y2)));
/// ```
///
/// A record pattern is checked where it is made: the type is a record, it has
/// one pattern per component, and each pattern matches a type the component
/// has (JLS 14.30.1). The components are the fact of the record's metamodel
/// ([me.supcheg.javafile.facts.RecordShape]); a metamodel generated before a
/// record's facts told them gives it none, and the target classpath check of
/// lowering reports the mismatch of the format.
///
/// The components of a generic record are the types its type arguments give
/// them: the token of `Box<String>` deconstructs `T value` as a `String`
/// value, and the pattern of the component is checked against that type. The
/// token carries the type arguments, so a token that carries none — the raw
/// token — and a wildcard type argument leave a component's type undetermined,
/// and a pattern of the record is refused rather than rendered with a type the
/// code does not have.
public final class Patterns {
    private Patterns() {}

    /// A binding of a type pattern, `T name`: the variable is made when the
    /// pattern is matched, and is in scope in the branch it opens.
    ///
    /// @param type the type of the value bound
    /// @param <T> the type of the value bound
    /// @return the binding
    public static <T> PatternBinding<T> binding(TypeToken<T> type) {
        return new PatternBinding<>(type);
    }

    /// `T name`: a type pattern that binds the value of `binding`.
    ///
    /// @param binding the binding
    /// @param <T> the matched type
    /// @return the pattern
    public static <T> TypedPattern<T> type(PatternBinding<T> binding) {
        return new TypedPattern.Type<>(binding.type(), binding);
    }

    /// `R(p1, ..., pn)`: a record deconstruction of `record`, one pattern per component, in declaration
    /// order.
    ///
    /// @param record the record
    /// @param components the patterns of the components
    /// @param <T> the record type
    /// @return the pattern
    /// @throws IllegalArgumentException if `record` is not a record, has another number of components, or is
    ///         generic and its token carries a raw or a wildcard type argument; if a component pattern
    ///         matches a type the component does not have
    public static <T> TypedPattern<T> record(DeclaredToken<T> record, TypedPattern<?>... components) {
        return record(record, List.of(components));
    }

    /// `R(p1, ..., pn)`: a record deconstruction of `record`, one pattern per component, in declaration
    /// order.
    ///
    /// @param record the record
    /// @param components the patterns of the components
    /// @param <T> the record type
    /// @return the pattern
    /// @throws IllegalArgumentException if `record` is not a record, has another number of components, or is
    ///         generic and its token carries a raw or a wildcard type argument; if a component pattern
    ///         matches a type the component does not have
    public static <T> TypedPattern<T> record(DeclaredToken<T> record, List<TypedPattern<?>> components) {
        List<RecordComponent> declared = componentsOf(record);
        List<TypedPattern<?>> given = List.copyOf(components);
        if (declared.size() != given.size()) {
            throw new IllegalArgumentException("a record pattern of " + record + " has " + given.size()
                    + " component pattern(s), but the record declares " + declared.size() + ": " + names(declared)
                    + "; a record pattern has one pattern per component (JLS 14.30.1)");
        }
        for (int i = 0; i < declared.size(); i++) {
            RecordComponent component = declared.get(i);
            TypedPattern<?> pattern = given.get(i);
            if (!isApplicable(pattern.matched(), component.type())) {
                throw new IllegalArgumentException("a record pattern of " + record + " matches the component "
                        + i + ", " + component.name() + ", of the type " + TypeNames.describe(component.type())
                        + " with a pattern of " + pattern.matched() + ": a component pattern matches the type of"
                        + " its component, or a subtype of it (JLS 14.30.1)");
            }
        }
        return new TypedPattern.Deconstruction<>(record, given);
    }

    /// The components of the record a record pattern deconstructs, in the types
    /// the token's type arguments give them.
    ///
    /// @param record the record
    /// @return the components, in declaration order
    /// @throws IllegalArgumentException if `record` is not a record; if the record is generic and its token
    ///         carries no type argument, or a wildcard one
    private static List<RecordComponent> componentsOf(DeclaredToken<?> record) {
        RecordShape shape = record.shape().recordShape();
        if (!(shape instanceof RecordShape.Of(var components))) {
            throw new IllegalArgumentException("a record pattern names a record, but " + record
                    + " is not one: a type pattern matches it, and a record deconstruction has a record to"
                    + " deconstruct (JLS 14.30.1)");
        }
        if (record.shape().typeParameters().isEmpty()) {
            return components;
        }
        List<TokenArg> arguments = record.typeArguments();
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("a record pattern of " + record + " is not expressed: the record is"
                    + " generic, and its token carries no type argument, so the components of its pattern have"
                    + " no type the type arguments give them; take a token of the record with a type argument"
                    + " for each of its type parameters");
        }
        Map<String, TypeRef> env = new HashMap<>();
        List<TypeParam> parameters = record.shape().typeParameters();
        for (int i = 0; i < arguments.size(); i++) {
            String parameter = parameters.get(i).name();
            if (!(arguments.get(i) instanceof TokenArg.Exact(RefToken<?> argument))) {
                throw new IllegalArgumentException("a record pattern of " + record + " is not expressed: the type"
                        + " argument #" + i + " of its token is a wildcard, which leaves the type of the type"
                        + " parameter " + parameter + " undetermined; take a token of the record with a type"
                        + " argument for each of its type parameters");
            }
            env.put(parameter, argument.typeRef());
        }
        return components.stream()
                .map(component -> new RecordComponent(component.name(), substituted(component.type(), env)))
                .toList();
    }

    /// `type` with the type parameters of `env` replaced: the type of a component of a generic record, in
    /// terms of the type arguments its token carries.
    private static TypeRef substituted(TypeRef type, Map<String, TypeRef> env) {
        return switch (type) {
            case TypeVarRef variable -> env.getOrDefault(variable.name(), variable);
            case ArrayTypeRef array -> new ArrayTypeRef(substituted(array.component(), env), array.annotations());
            case ParameterizedTypeRef parameterized ->
                new ParameterizedTypeRef(
                        parameterized.raw(),
                        parameterized.args().stream()
                                .map(argument -> substituted(argument, env))
                                .toList(),
                        parameterized.annotations());
            case ClassTypeRef _, PrimitiveTypeRef _ -> type;
        };
    }

    /// `argument` with the type parameters of `env` replaced, in the bound of a wildcard.
    private static TypeArg substituted(TypeArg argument, Map<String, TypeRef> env) {
        return switch (argument) {
            case ExactTypeArg(TypeRef type) -> new ExactTypeArg(substituted(type, env));
            case ExtendsTypeArg(TypeRef bound) -> new ExtendsTypeArg(substituted(bound, env));
            case SuperTypeArg(TypeRef bound) -> new SuperTypeArg(substituted(bound, env));
            case UnboundedTypeArg _ -> argument;
        };
    }

    /// Whether a pattern of `pattern` matches a value of the component type `component`: its type is the
    /// component's, or a subtype of it that the facts tell — a primitive is itself exactly, and an array or
    /// a parameterization is itself exactly.
    ///
    /// @param pattern the type the pattern matches
    /// @param component the type of the component
    /// @return whether it does
    static boolean isApplicable(TypeToken<?> pattern, TypeRef component) {
        TypeToken<?> type = Tokens.annotated(pattern);
        if (type.typeRef().equals(component)) {
            return true;
        }
        return switch (component) {
            case PrimitiveTypeRef _, ArrayTypeRef _, TypeVarRef _ -> false;
            case ParameterizedTypeRef _ -> false;
            case ClassTypeRef cls ->
                !(type instanceof me.supcheg.javafile.facts.PrimitiveToken<?, ?, ?>)
                        && Tokens.isSubtypeOf(type, cls.desc());
        };
    }

    /// Whether every value of the type `sub` is a value of the type `sup`: for a case of a `switch`, whose
    /// pattern must match a subtype of the selector's type, and for a case that a broader one precedes.
    ///
    /// Conservative where the facts say too little: a parameterization is itself exactly, and a type whose
    /// facts do not tell the supertype is no subtype of it.
    ///
    /// @param sub the token of the subtype
    /// @param sup the token of the supertype
    /// @return whether it is
    static boolean isSubtype(TypeToken<?> sub, TypeToken<?> sup) {
        TypeToken<?> subtype = Tokens.annotated(sub);
        TypeToken<?> supertype = Tokens.annotated(sup);
        if (Tokens.sameType(subtype, supertype)) {
            return true;
        }
        if (supertype instanceof DeclaredToken<?> declared) {
            return !(subtype instanceof me.supcheg.javafile.facts.PrimitiveToken<?, ?, ?>)
                    && !(declared.typeRef() instanceof ParameterizedTypeRef)
                    && Tokens.isSubtypeOf(subtype, declared.erasure());
        }
        // a case pattern and the selector are declared types: a primitive, a type variable and an array
        // are no types of a case, and the subtype proof of a declared type is the one that decides
        return false;
    }

    /// Whether a pattern of `pattern` may cover every value the permitted
    /// subtype `permitted` of the selector has: the permitted subtype is the
    /// pattern's own type, or the pattern's type is a sealed type that
    /// permits it. A union of subtypes of a permitted subtype is not proven.
    ///
    /// @param pattern the type the pattern matches
    /// @param permitted the permitted subtype of the selector
    /// @return whether it may
    static boolean coversPermitted(TypeToken<?> pattern, ClassDesc permitted) {
        TypeToken<?> type = Tokens.annotated(pattern);
        if (permitted.equals(type.erasure())) {
            return true;
        }
        if (!(type instanceof DeclaredToken<?> declared)) {
            return false;
        }
        return declared.shape().permitted().stream().anyMatch(permitted::equals);
    }

    /// Rejects a top-level pattern of a primitive type: a type pattern is a
    /// reference type test, and a primitive is matched by the component
    /// pattern of a record pattern alone (JLS 14.30.1).
    ///
    /// @param type the type the pattern matches
    /// @param where the construct, for the message
    /// @throws IllegalArgumentException if `type` is primitive
    static void requireReference(TypeToken<?> type, String where) {
        if (type instanceof me.supcheg.javafile.facts.PrimitiveToken<?, ?, ?> primitive) {
            throw new IllegalArgumentException(where + " matches the primitive type " + primitive
                    + ", which no value is tested against: a type pattern matches a reference type, and a"
                    + " primitive is matched by the component pattern of a record pattern (JLS 14.30.1)");
        }
    }

    /// Makes the variables of every binding of `pattern`, in `scope`: the branch the pattern opens.
    ///
    /// @param pattern the pattern
    /// @param scope the block the bindings are in scope in
    /// @throws IllegalStateException if a binding was made already
    static void bind(TypedPattern<?> pattern, Block<?, ?> scope) {
        pattern.bindings().forEach(binding -> binding.bind(scope));
    }

    private static String names(List<RecordComponent> components) {
        return components.isEmpty()
                ? "none"
                : components.stream().map(RecordComponent::name).collect(Collectors.joining(", "));
    }
}
