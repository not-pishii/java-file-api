package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.source.MemberKind;
import me.supcheg.javafile.facts.source.MemberQuery;
import me.supcheg.javafile.facts.source.MemberResolver;
import me.supcheg.javafile.facts.source.Resolution;

import java.util.List;

/// A runtime fact source (§3.8): proves members of a type by full signature,
/// or fails fast.
///
/// The typed lookups — `method("greet", STRING, STRING)` returning a
/// `MethodRef1<O, String, String>`, and so on for every arity family and
/// `ctor`/`abstractCtor` — ask the source's [MemberResolver] and introduce
/// the fact only when it proves the member; otherwise the resolver's
/// [FactLookupException] says what was looked for, what similar members
/// exist, and where the type came from.
///
/// The class is final and has no public constructor: a source is created by
/// [UnsafeFacts#factSource(DeclaredToken, MemberResolver)], so a source that
/// proves everything is visible to the audit of the guarantee (§1). The only
/// runtime source is the mirror source of `java-file-api-lang-model`;
/// metamodels do not take part — they are plain Java code checked by javac.
///
/// @param <O> the type whose members are looked up
public final class FactSource<O> extends InvocableLookup<O> {
    private final DeclaredToken<O> token;
    private final MemberResolver resolver;

    FactSource(DeclaredToken<O> token, MemberResolver resolver) {
        this.token = token;
        this.resolver = resolver;
    }

    /// The type whose members are looked up.
    ///
    /// @return the type token
    @Override
    public DeclaredToken<O> token() {
        return token;
    }

    @Override
    Resolution resolve(MemberQuery query) {
        return resolver.resolve(query);
    }

    /// Proves a readable instance field.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such field
    public <T> FieldRef<O, T> field(String name, TypeToken<T> type) {
        resolve(MemberQuery.field(MemberKind.FIELD, name, type));
        return new FieldRef<>(token, name, type);
    }

    /// Proves a non-`final` instance field.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such non-final field
    public <T> MutableFieldRef<O, T> mutableField(String name, TypeToken<T> type) {
        resolve(MemberQuery.field(MemberKind.MUTABLE_FIELD, name, type));
        return new MutableFieldRef<>(token, name, type);
    }

    /// Proves a readable static field, recording its value if it is a
    /// constant variable.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such static field
    /// @throws IllegalArgumentException if the resolved constant value does
    ///     not fit `type`
    public <T> StaticFieldRef<T> staticField(String name, TypeToken<T> type) {
        Resolution resolution = resolve(MemberQuery.field(MemberKind.STATIC_FIELD, name, type));
        return new StaticFieldRef<>(token, name, type, resolution.constantValue());
    }

    /// Proves a non-`final` static field.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such non-final static field
    public <T> MutableStaticFieldRef<T> mutableStaticField(String name, TypeToken<T> type) {
        resolve(MemberQuery.field(MemberKind.MUTABLE_STATIC_FIELD, name, type));
        return new MutableStaticFieldRef<>(token, name, type);
    }

    @Override
    ConcreteClassToken<O> concreteClassToken() {
        if (token instanceof ConcreteClassToken<O> concrete) {
            return concrete;
        }
        throw new FactLookupException(
                "constructors for new",
                describeKind() + " " + token,
                token instanceof AbstractClassToken<?> ? List.of("abstractCtor(...), for a subclass") : List.of());
    }

    @Override
    AbstractClassToken<O> abstractClassToken() {
        if (token instanceof AbstractClassToken<O> abstractClass) {
            return abstractClass;
        }
        throw new FactLookupException(
                "constructors of an abstract class",
                describeKind() + " " + token,
                token instanceof ConcreteClassToken<?> ? List.of("ctor(...)") : List.of());
    }

    private String describeKind() {
        return switch (token) {
            case InterfaceToken<?> ignored -> "interface";
            case EnumToken<?> ignored -> "enum";
            case AbstractClassToken<?> ignored -> "abstract class";
            case ConcreteClassToken<?> ignored -> "class";
        };
    }
}
