package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;

import java.util.List;
import java.util.stream.Stream;

/// The declaration of a generic method or constructor of a class being declared ([MemberBuilder]):
/// the stage in which the type parameters of the member are declared, before the member itself is.
///
/// ```java
/// var id = cb.declareGenericMethod(typeParam("T"), new GenericMethodArities.MethodSpec1<>() {
///     @Override
///     public <F extends Invocable> F build(GenericMethodDeclaration<Self, S> m, TypeVarToken<Object> t) {
///         return m.declareMethod("id", t, t);
///     }
/// });
/// ```
///
/// It declares what [Declarations] declares — a method, a `static` one, a `void` one, a constructor,
/// with a `throws` clause, documented, annotated, of variable arity — and the type parameters of the
/// member are in scope in the signature and the body of that member alone (JLS 6.3): not in another
/// member of the class, and not in a field or a member type.
///
/// The leftmost bound of a type parameter is the token [TypeParameter] was made with; each further
/// bound is said by [#bound(TypeVarToken, InterfaceToken)], before the member is declared, as the
/// header of a generic class says the bounds of its own type parameters ([ClassHeader#bound]).
///
/// A bound names a type parameter of the member alone: a type parameter of the class is said before
/// the member tells whether it is `static`, which has none in scope (JLS 8.1.2).
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class GenericMethodDeclaration<Self, S> extends Declarations<Self, S> {

    /// What one type parameter of the member is declared with: the token the body uses, and its
    /// bounds, the leftmost first.
    private static final class Declared {
        private final TypeVarToken<?> token;
        private List<DeclaredToken<?>> bounds;

        Declared(TypeVarToken<?> token, List<DeclaredToken<?>> bounds) {
            this.token = token;
            this.bounds = bounds;
        }
    }

    private final MemberBuilder<Self, S> builder;
    private final List<Declared> declared;

    GenericMethodDeclaration(MemberBuilder<Self, S> builder, List<TypeParameter<?>> parameters) {
        this.builder = builder;
        DeclarationIdentity identity = UnsafeFacts.declarationIdentity("a generic member of " + builder.described());
        this.declared = parameters.stream()
                .map(parameter -> new Declared(
                        UnsafeFacts.declaredTypeVarToken(
                                Types.typeVar(parameter.name()),
                                parameter.bound().erasure(),
                                identity),
                        List.of(parameter.bound())))
                .toList();
        declared.forEach(one -> one.bounds.forEach(bound -> {
            Tokens.requireNotRaw(bound);
            requireOwn(bound, one.token);
        }));
    }

    /// The type parameters of the member, as the declaration writes them, with the bounds said so far.
    ///
    /// @return the type parameters, in declaration order
    @Override
    List<MethodTypeParameter> typeParams() {
        return declared.stream()
                .map(one -> new MethodTypeParameter(
                        one.token,
                        one.bounds.stream()
                                .<ClassOrInterfaceTypeRef>map(DeclaredToken::typeRef)
                                .toList()))
                .toList();
    }

    @Override
    MemberBuilder<Self, S> builder() {
        return builder;
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }

    /// The declarations of the members that declare `exceptions` in their `throws` clause, of this member:
    /// `m.throwing(IOException_.TOKEN).declareMethod("read", String_.TOKEN)`. The clause is part of the fact,
    /// as it is for a member declared by [MemberBuilder#throwing].
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the declarations of the members that declare `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingDeclarations<Self, S> throwing(ClassToken<? extends Throwable>... exceptions) {
        return new ThrowingDeclarations<>(this, List.of(exceptions));
    }

    /// The token of a type parameter of the member, as the specification is given it.
    ///
    /// @param index its index among the type parameters, from `0`
    /// @param <T> the leftmost bound of the type parameter
    /// @return the token
    @SuppressWarnings("unchecked")
    public <T> TypeVarToken<T> variable(int index) {
        return (TypeVarToken<T>) declared.get(index).token;
    }

    private List<TypeVarToken<?>> variables() {
        return declared.stream().<TypeVarToken<?>>map(one -> one.token).toList();
    }

    /// Gives a type parameter of the member one more bound, an interface, before the member is
    /// declared: `<T extends A & B>`. The leftmost bound is the token [TypeParameter] was given;
    /// each further bound is said here, and gives the witness by which an expression of the type
    /// parameter is one of it.
    ///
    /// @param variable the token of the type parameter, as the specification was given it
    /// @param bound the interface, with its type arguments
    /// @param <T> the type parameter
    /// @param <B> the interface
    /// @return the witness that the type parameter is of the interface
    /// @throws IllegalArgumentException if `variable` is no type parameter of this member, or the bound
    ///     is a bound already
    /// @throws IllegalStateException if the bound is raw, or names a type parameter out of its scope
    public <T, B> Upcast<T, B> bound(TypeVarToken<T> variable, InterfaceToken<B> bound) {
        Declared mine = declared.stream()
                .filter(one -> one.token == variable)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("the type parameter " + variable
                        + " is no type parameter of the member being declared: a member bounds its own"));
        if (mine.bounds.stream().anyMatch(one -> one.typeRef().equals(bound.typeRef()))) {
            throw new IllegalArgumentException(
                    "the type parameter " + variable + " has the bound " + bound + " already: a bound is said once");
        }
        Tokens.requireNotRaw(bound);
        requireOwn(bound, variable);
        mine.bounds = Stream.concat(mine.bounds.stream(), Stream.of(bound)).toList();
        return new Upcast<>(variable, bound);
    }

    /// Holds a bound to the type parameters of the member: the token of one of them is in scope, and a
    /// type parameter of the class is not, for the bound is said before the member tells whether it is
    /// `static`, which has no type parameter of the class in scope (JLS 8.1.2).
    private void requireOwn(me.supcheg.javafile.facts.TypeToken<?> bound, TypeVarToken<?> variable) {
        TypeScope.variables(bound)
                .filter(mentioned -> mentioned != variable)
                .filter(mentioned -> !variables().contains(mentioned))
                .findFirst()
                .ifPresent(mentioned -> {
                    throw new IllegalStateException("the bound " + bound + " of the type parameter " + variable
                            + " mentions " + mentioned + ", which is no type parameter of the member: a bound is"
                            + " said before the member tells whether it is static, and a static member has no"
                            + " type parameter of the class in scope (JLS 8.1.2) — state the bound in terms of"
                            + " the type parameters of the member");
                });
    }
}
