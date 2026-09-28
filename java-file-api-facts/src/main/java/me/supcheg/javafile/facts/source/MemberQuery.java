package me.supcheg.javafile.facts.source;

import me.supcheg.javafile.facts.TypeNames;
import me.supcheg.javafile.facts.TypeToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/// What a [FactSource] is asked to prove: a member by kind, name, and full
/// signature. Nothing is resolved (§10) — the query names exactly one
/// overload.
///
/// @param kind the member kind
/// @param name the member name; ignored for a constructor
/// @param type the result type of a method, the type of a field, or empty
///             for a `void` method and a constructor
/// @param params the parameter types of a method or constructor, in order
public record MemberQuery(MemberKind kind, String name, Optional<TypeToken<?>> type, List<TypeToken<?>> params) {
    public MemberQuery {
        params = List.copyOf(params);
    }

    /// Asks for an instance method with a result.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param params the parameter types
    /// @return the query
    public static MemberQuery method(String name, TypeToken<?> result, TypeToken<?>... params) {
        return new MemberQuery(MemberKind.METHOD, name, Optional.of(result), List.of(params));
    }

    /// Asks for a `void` instance method.
    ///
    /// @param name the method name
    /// @param params the parameter types
    /// @return the query
    public static MemberQuery voidMethod(String name, TypeToken<?>... params) {
        return new MemberQuery(MemberKind.METHOD, name, Optional.empty(), List.of(params));
    }

    /// Asks for a static method with a result.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param params the parameter types
    /// @return the query
    public static MemberQuery staticMethod(String name, TypeToken<?> result, TypeToken<?>... params) {
        return new MemberQuery(MemberKind.STATIC_METHOD, name, Optional.of(result), List.of(params));
    }

    /// Asks for a `void` static method.
    ///
    /// @param name the method name
    /// @param params the parameter types
    /// @return the query
    public static MemberQuery voidStaticMethod(String name, TypeToken<?>... params) {
        return new MemberQuery(MemberKind.STATIC_METHOD, name, Optional.empty(), List.of(params));
    }

    /// Asks for a constructor.
    ///
    /// @param params the parameter types
    /// @return the query
    public static MemberQuery constructor(TypeToken<?>... params) {
        return new MemberQuery(MemberKind.CONSTRUCTOR, "<init>", Optional.empty(), List.of(params));
    }

    /// Asks for a field.
    ///
    /// @param kind one of the field kinds
    /// @param name the field name
    /// @param type the field type
    /// @return the query
    public static MemberQuery field(MemberKind kind, String name, TypeToken<?> type) {
        return new MemberQuery(kind, name, Optional.of(type), List.of());
    }

    @Override
    public String toString() {
        String typeName = type.map(t -> TypeNames.describe(t.typeRef())).orElse("void");
        String paramList =
                params.stream().map(p -> TypeNames.describe(p.typeRef())).collect(Collectors.joining(", ", "(", ")"));
        return switch (kind) {
            case METHOD -> "method " + typeName + " " + name + paramList;
            case STATIC_METHOD -> "static method " + typeName + " " + name + paramList;
            case CONSTRUCTOR -> "constructor" + paramList;
            case FIELD -> "field " + typeName + " " + name;
            case MUTABLE_FIELD -> "non-final field " + typeName + " " + name;
            case STATIC_FIELD -> "static field " + typeName + " " + name;
            case MUTABLE_STATIC_FIELD -> "non-final static field " + typeName + " " + name;
        };
    }
}
