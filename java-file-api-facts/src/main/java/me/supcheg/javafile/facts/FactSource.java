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
/// `ctor`/`superCtor` — ask the source's [MemberResolver] and introduce
/// the fact only when it proves the member; otherwise the resolver's
/// [FactLookupException] says what was looked for, what similar members
/// exist, and where the type came from.
///
/// A source gives the facts of `public` fields and methods alone: each is
/// `public` by what the resolver proved, not by assumption.
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

    /// What the resolver proves of a member. A field or a method has to be `public`: a source gives a
    /// fact as it is, and the fact of a `protected` field or method is held back in a [Protected], which
    /// a subclass alone opens — a resolver that proves one is refused, so no such fact gets out bare. A
    /// `protected` constructor has its fact unwrapped, with its access in its traits.
    ///
    /// @throws IllegalStateException if the resolver proves a field or method that is not `public`
    @Override
    Resolution resolve(MemberQuery query) {
        Resolution resolution = resolver.resolve(query);
        boolean reached =
                switch (resolution.traits().access()) {
                    case PUBLIC -> true;
                    case PROTECTED ->
                        switch (query.kind()) {
                            case CONSTRUCTOR -> true;
                            case METHOD, STATIC_METHOD, FIELD, MUTABLE_FIELD, STATIC_FIELD, MUTABLE_STATIC_FIELD ->
                                false;
                        };
                };
        if (!reached) {
            throw new IllegalStateException("the resolver of " + token + " proved " + query
                    + ", a protected member: a fact source gives the facts of public fields and methods alone");
        }
        return resolution;
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
        return new FieldRef<>(token, name, type, Access.PUBLIC);
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
        return new MutableFieldRef<>(token, name, type, Access.PUBLIC);
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
        return new StaticFieldRef<>(token, name, type, resolution.constantValue(), Access.PUBLIC);
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
        return new MutableStaticFieldRef<>(token, name, type, Access.PUBLIC);
    }

    @Override
    ConcreteClassToken<O> concreteClassToken() {
        if (token instanceof ConcreteClassToken<O> concrete) {
            return concrete;
        }
        throw new FactLookupException(
                "constructors for new",
                describeKind() + " " + token,
                token instanceof ExtendableClassToken<?> ? List.of("superCtor(...), for a subclass") : List.of());
    }

    @Override
    ExtendableClassToken<O> extendableClassToken() {
        if (token instanceof ExtendableClassToken<O> extendable) {
            return extendable;
        }
        throw new FactLookupException(
                "constructors for a subclass",
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
