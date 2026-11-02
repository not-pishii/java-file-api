package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
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
        private List<TypeToken<?>> bounds;

        Declared(TypeVarToken<?> token, List<TypeToken<?>> bounds) {
            this.token = token;
            this.bounds = bounds;
        }

        /// The bounds as the declaration writes them: every one is a class, an interface or a type
        /// variable, as javac requires of a bound.
        List<ClassOrInterfaceTypeRef> written() {
            return bounds.stream()
                    .map(bound -> switch (bound.typeRef()) {
                        case ClassOrInterfaceTypeRef written -> written;
                        case me.supcheg.javafile.type.TypeRef other ->
                            throw new IllegalStateException("the bound " + bound + " of the type parameter " + token
                                    + " is no class or interface: javac writes a class, an interface or a type"
                                    + " parameter there (JLS 4.4)");
                    })
                    .toList();
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
                .map(one -> new MethodTypeParameter(one.token, one.written()))
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
        Declared mine = mine(variable);
        if (mine.bounds.stream().anyMatch(one -> one.typeRef().equals(bound.typeRef()))) {
            throw new IllegalArgumentException(
                    "the type parameter " + variable + " has the bound " + bound + " already: a bound is said once");
        }
        Tokens.requireNotRaw(bound);
        requireDeclaredBefore(variable, declared.indexOf(mine), bound);
        requireOwn(bound, variable);
        mine.bounds = Stream.concat(mine.bounds.stream(), Stream.of(bound)).toList();
        return new Upcast<>(variable, bound);
    }

    /// Holds a further bound to the type parameters declared before the one it bounds: `<T, U extends
    /// Comparable<T>>` is legal, and `<T extends Comparable<U>, U>` is not — a type parameter of a
    /// bound is the one itself or an earlier one (JLS 4.4).
    private void requireDeclaredBefore(TypeVarToken<?> variable, int mineAt, TypeToken<?> bound) {
        TypeScope.variables(bound).filter(mentioned -> mentioned != variable).forEach(mentioned -> {
            if (indexOf(mentioned) > mineAt) {
                throw new IllegalStateException("the bound " + bound + " of the type parameter " + variable
                        + " mentions " + mentioned + ", which is declared after it: a bound names the type"
                        + " parameter itself or one declared before it (JLS 4.4)");
            }
        });
    }

    /// The position of a type parameter of the member among its type parameters, or `-1`.
    private int indexOf(TypeVarToken<?> token) {
        for (int i = 0; i < declared.size(); i++) {
            if (declared.get(i).token == token) {
                return i;
            }
        }
        return -1;
    }

    /// Bounds a type parameter of the member by an earlier one of its own, `<T, U extends T>`: the
    /// leftmost bound of `variable` is `bound` itself, so `variable` was declared without one. javac
    /// allows a type variable as a bound in the leftmost position alone — it may not be followed by
    /// another bound (JLS 4.4) — and requires it to be an earlier type parameter of the same member,
    /// which `bound` is.
    ///
    /// @param variable the token of the type parameter, as the specification was given it
    /// @param bound the token of an earlier type parameter of the member
    /// @param <T> the type parameter
    /// @param <B> the earlier type parameter
    /// @return the witness that the type parameter is of the earlier one
    /// @throws IllegalArgumentException if `variable` is no type parameter of this member, `bound` is no
    ///     earlier one, or `variable` has a bound already
    /// @throws IllegalStateException if `bound` is of no type parameter of this member
    public <T, B> Upcast<T, B> bound(TypeVarToken<T> variable, TypeVarToken<B> bound) {
        Declared mine = mine(variable);
        int mineAt = declared.indexOf(mine);
        int boundAt = declared.indexOf(declared.stream()
                .filter(one -> one.token == bound)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("the bound " + bound + " of the type parameter " + variable
                        + " is no type parameter of the member: javac takes a type parameter of the same member as a"
                        + " bound, declared before the one it bounds (JLS 4.4)")));
        if (boundAt >= mineAt) {
            throw new IllegalStateException("the type parameter " + variable + " cannot be bounded by " + bound
                    + ": a bound names a type parameter declared before the one it bounds (JLS 4.4)");
        }
        if (!(mine.bounds.getFirst() instanceof me.supcheg.javafile.facts.DeclaredToken<?> leftmost)
                || !leftmost.erasure().equals(java.lang.constant.ConstantDescs.CD_Object)) {
            throw new IllegalArgumentException("the type parameter " + variable + " has the leftmost bound "
                    + mine.bounds.getFirst() + " already: a type variable as a bound is the leftmost one, and is"
                    + " followed by no other (JLS 4.4)");
        }
        mine.bounds = List.of(bound);
        return new Upcast<>(variable, bound);
    }

    private Declared mine(TypeVarToken<?> variable) {
        return declared.stream()
                .filter(one -> one.token == variable)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("the type parameter " + variable
                        + " is no type parameter of the member being declared: a member bounds its own"));
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

    // ------------------------------------------------------------------
    // Abstract methods
    // ------------------------------------------------------------------

    /// Declares a `public abstract` method with the type parameters of this member, `<T> T id(T)`, which
    /// has no body: an abstract class declares it, and a concrete subclass implements it with type
    /// parameters that match — the names may differ, the bounds may not (JLS 8.4.8.3).
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the abstract method
    /// @throws IllegalArgumentException if the class is not abstract, or javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public final <R> MethodRef0<Self, R> declareAbstractMethod(String name, TypeToken<R> result) {
        return builder.declaredAbstract(
                UnsafeInvocables.method(builder.self(), name, result, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract void` method with the type parameters of this member, see
    /// [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @return the fact of the abstract method
    /// @throws IllegalArgumentException if the class is not abstract, or javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public final VoidMethodRef0<Self> declareVoidAbstractMethod(String name) {
        return builder.declaredAbstract(
                UnsafeInvocables.voidMethod(builder.self(), name, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract` method with one parameter and the type parameters of this member,
    /// see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the abstract method
    public final <R, A1> MethodRef1<Self, R, A1> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return builder.declaredAbstract(
                UnsafeInvocables.method(builder.self(), name, result, p1, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract void` method with one parameter and the type parameters of this
    /// member, see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the abstract method
    public final <A1> VoidMethodRef1<Self, A1> declareVoidAbstractMethod(String name, TypeToken<A1> p1) {
        return builder.declaredAbstract(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract` method with two parameters and the type parameters of this member,
    /// see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return builder.declaredAbstract(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract void` method with two parameters and the type parameters of this
    /// member, see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the abstract method
    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return builder.declaredAbstract(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract` method with three parameters and the type parameters of this member,
    /// see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return builder.declaredAbstract(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract void` method with three parameters and the type parameters of this
    /// member, see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the abstract method
    public final <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return builder.declaredAbstract(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract` method with four parameters and the type parameters of this member,
    /// see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the abstract method
    public final <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return builder.declaredAbstract(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, p4, traits(MemberTraits.ABSTRACT)));
    }

    /// Declares a `public abstract void` method with four parameters and the type parameters of this
    /// member, see [#declareAbstractMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the abstract method
    public final <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return builder.declaredAbstract(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, p4, traits(MemberTraits.ABSTRACT)));
    }
}
