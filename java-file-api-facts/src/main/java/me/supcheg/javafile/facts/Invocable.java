package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

/// The untyped view of a method or constructor fact, shared by the typed
/// arity families (`MethodRefN`, `VoidMethodRefN`, `StaticMethodRefN`,
/// `VoidStaticMethodRefN`, `CtorRefN`). Lowering reads it; generator code
/// uses the typed families.
public interface Invocable {

    /// What kind of member the fact is.
    ///
    /// @return the kind
    InvocableKind kind();

    /// The type declaring or inheriting the member.
    ///
    /// @return the owner token
    DeclaredToken<?> owner();

    /// The member name; for a constructor, the simple name of its class.
    ///
    /// @return the name
    String name();

    /// The result type, or empty for a `void` method and a constructor.
    ///
    /// @return the result token
    Optional<TypeToken<?>> resultType();

    /// The parameter types, in order, as members of [#owner()].
    ///
    /// @return the parameter tokens
    List<TypeToken<?>> params();

    /// Throws-set, overridability, and explicit type arguments.
    ///
    /// @return the traits
    MemberTraits traits();

    /// The erased signature of the member as a member of [#owner()].
    ///
    /// @return the signature
    default MethodSignature signature() {
        return new MethodSignature(
                name(), params().stream().map(TypeToken::erasure).toList());
    }
}
