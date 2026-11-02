package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.stream.Stream;

/// The declaration of a generic method of a type that is not a class being declared
/// ([TypeMemberDeclarations]): an interface, a record or an enum. It is the stage in which the type
/// parameters of the member are declared, before the member itself is:
///
/// ```java
/// var id = ib.declareGenericMethod(typeParam("T"), new TypeGenericMethodArities.TypeMethodSpec1<>() {
///     @Override
///     public <F extends Invocable> F build(TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
///         return m.declareMethod("id", t, t);
///     }
/// });
/// ```
///
/// It declares what [TypeMemberDeclarations] declares — a method, an abstract one, a `default` one
/// of an interface, a `static` one, a `private` one — with the type parameters of the member in scope
/// in the signature and the body of that member alone (JLS 6.3). An enum declares methods and a
/// record does too; an annotation type declares elements alone, and no generic method.
///
/// @param <Self> the brand of the type being declared
public final class TypeGenericMethodDeclaration<Self> {

    /// What one type parameter of the member is declared with: the token the body uses, and its
    /// bounds, the leftmost first.
    private static final class Declared {
        private final TypeVarToken<?> token;
        private List<TypeToken<?>> bounds;

        Declared(TypeVarToken<?> token, List<TypeToken<?>> bounds) {
            this.token = token;
            this.bounds = bounds;
        }

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

    private final TypeMemberDeclarations<Self> builder;
    private final List<Declared> declared;

    TypeGenericMethodDeclaration(TypeMemberDeclarations<Self> builder, List<TypeParameter<?>> parameters) {
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

    /// The type parameters of the member, as the declaration writes them.
    ///
    /// @return the type parameters, in declaration order
    List<MethodTypeParameter> typeParams() {
        return declared.stream()
                .map(one -> new MethodTypeParameter(one.token, one.written()))
                .toList();
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

    /// Gives a type parameter of the member one more bound, an interface: `<T extends A & B>`, see
    /// [GenericMethodDeclaration#bound(TypeVarToken, InterfaceToken)].
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

    /// Bounds a type parameter of the member by an earlier one of its own, `<T, U extends T>`, see
    /// [GenericMethodDeclaration#bound(TypeVarToken, TypeVarToken)].
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
                || !leftmost.erasure().equals(ConstantDescs.CD_Object)) {
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

    private void requireOwn(TypeToken<?> bound, TypeVarToken<?> variable) {
        TypeScope.variables(bound)
                .filter(mentioned -> mentioned != variable)
                .filter(mentioned -> !variables().contains(mentioned))
                .findFirst()
                .ifPresent(mentioned -> {
                    throw new IllegalStateException("the bound " + bound + " of the type parameter " + variable
                            + " mentions " + mentioned + ", which is no type parameter of the member: a bound"
                            + " names a type parameter of the member alone, and the type it is declared in says"
                            + " its own bounds before its members");
                });
    }

    // ------------------------------------------------------------------
    // The methods of a type that is not a class
    // ------------------------------------------------------------------

    private <F extends Invocable> F declared(F member, Kind kind) {
        return builder.declaredAs(member, kind);
    }

    private MemberTraits traits(MemberTraits traits) {
        return new MemberTraits(
                List.copyOf(List.<ClassToken<? extends Throwable>>of()),
                traits.overridability(),
                traits.typeArgs(),
                traits.access(),
                traits.arity(),
                traits.annotations(),
                traits.parameterAnnotations(),
                typeParams());
    }

    /// Declares a `public` method returning `R`, with the type parameters of this member, `<T> T id(T)`.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public final <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(builder.self(), name, result, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /// Declares a `public void` method with the type parameters of this member.
    ///
    /// @param name the method name
    /// @return the fact of the method
    public final VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(builder.self(), name, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /// Declares a `public` method with one parameter and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /// Declares a `public void` method with one parameter and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(builder.self(), name, p1, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /// Declares a `public` method with two parameters and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /// Declares a `public void` method with two parameters and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /// Declares a `public` method with three parameters and the type parameters of this member.
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
    /// @return the fact of the method
    public final <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, traits(MemberTraits.FINAL)),
                Kind.METHOD);
    }

    /// Declares a `public void` method with three parameters and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public final <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /// Declares a `public` method with four parameters and the type parameters of this member.
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
    /// @return the fact of the method
    public final <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, p4, traits(MemberTraits.FINAL)),
                Kind.METHOD);
    }

    /// Declares a `public void` method with four parameters and the type parameters of this member.
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
    /// @return the fact of the method
    public final <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, p4, traits(MemberTraits.FINAL)),
                Kind.METHOD);
    }

    // ------------------------------------------------------------------
    // Abstract and default methods of an interface, and the methods of the other kinds
    // ------------------------------------------------------------------

    /// Declares a `public abstract` method with the type parameters of this member, `<T> T id(T)`; an
    /// interface declares it with no body, and a record or an enum implements the methods it inherits.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the abstract method
    public final <R> MethodRef0<Self, R> declareAbstractMethod(String name, TypeToken<R> result) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /// Declares a `public abstract void` method with the type parameters of this member.
    ///
    /// @param name the method name
    /// @return the fact of the abstract method
    public final VoidMethodRef0<Self> declareVoidAbstractMethod(String name) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /// Declares a `public abstract` method with one parameter and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the abstract method
    public final <R, A1> MethodRef1<Self, R, A1> declareAbstractMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, traits(MemberTraits.ABSTRACT)),
                Kind.ABSTRACT);
    }

    /// Declares a `public abstract void` method with one parameter and the type parameters of this
    /// member.
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the abstract method
    public final <A1> VoidMethodRef1<Self, A1> declareVoidAbstractMethod(String name, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /// Declares a `public abstract` method with two parameters and the type parameters of this member.
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
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, traits(MemberTraits.ABSTRACT)),
                Kind.ABSTRACT);
    }

    /// Declares a `public abstract void` method with two parameters and the type parameters of this
    /// member.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the abstract method
    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidAbstractMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, traits(MemberTraits.ABSTRACT)),
                Kind.ABSTRACT);
    }

    /// Declares a `public abstract` method with three parameters and the type parameters of this member.
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
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, traits(MemberTraits.ABSTRACT)),
                Kind.ABSTRACT);
    }

    /// Declares a `public abstract void` method with three parameters and the type parameters of this
    /// member.
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
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, traits(MemberTraits.ABSTRACT)),
                Kind.ABSTRACT);
    }

    /// Declares a `public abstract` method with four parameters and the type parameters of this member.
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
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, p3, p4, traits(MemberTraits.ABSTRACT)),
                Kind.ABSTRACT);
    }

    /// Declares a `public abstract void` method with four parameters and the type parameters of this
    /// member.
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
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, p3, p4, traits(MemberTraits.ABSTRACT)),
                Kind.ABSTRACT);
    }

    /// Declares a `default` method of an interface with the type parameters of this member, see
    /// [TypeMemberDeclarations#declareDefaultMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public final <R> MethodRef0<Self, R> declareDefaultMethod(String name, TypeToken<R> result) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /// Declares a `default void` method of an interface with the type parameters of this member.
    ///
    /// @param name the method name
    /// @return the fact of the method
    public final VoidMethodRef0<Self> declareVoidDefaultMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(builder.self(), name, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /// Declares a `default` method of an interface with one parameter and the type parameters of this
    /// member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <R, A1> MethodRef1<Self, R, A1> declareDefaultMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /// Declares a `default void` method of an interface with one parameter and the type parameters of
    /// this member.
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <A1> VoidMethodRef1<Self, A1> declareVoidDefaultMethod(String name, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /// Declares a `default` method of an interface with two parameters and the type parameters of this
    /// member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declareDefaultMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, traits(MemberTraits.DEFAULT)),
                Kind.DEFAULT);
    }

    /// Declares a `default void` method of an interface with two parameters and the type parameters of
    /// this member.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidDefaultMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /// Declares a `public static` method with the type parameters of this member, see
    /// [TypeMemberDeclarations#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public final <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declared(
                UnsafeInvocables.staticMethod(builder.self(), name, result, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /// Declares a `public static void` method with the type parameters of this member.
    ///
    /// @param name the method name
    /// @return the fact of the method
    public final VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declared(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /// Declares a `public static` method with one parameter and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, traits(MemberTraits.FINAL)),
                Kind.STATIC);
    }

    /// Declares a `public static void` method with one parameter and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /// Declares a `public static` method with two parameters and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <R, A1, A2> StaticMethodRef2<R, A1, A2> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, p2, traits(MemberTraits.FINAL)),
                Kind.STATIC);
    }

    /// Declares a `public static void` method with two parameters and the type parameters of this member.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, p2, traits(MemberTraits.FINAL)),
                Kind.STATIC);
    }

    /// Declares a `public static` method with three parameters and the type parameters of this member.
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
    /// @return the fact of the method
    public final <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, p2, p3, traits(MemberTraits.FINAL)),
                Kind.STATIC);
    }

    /// Declares a `public static void` method with three parameters and the type parameters of this
    /// member.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public final <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, p2, p3, traits(MemberTraits.FINAL)),
                Kind.STATIC);
    }

    /// Declares a `public static` method with four parameters and the type parameters of this member.
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
    /// @return the fact of the method
    public final <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(
                UnsafeInvocables.staticMethod(builder.self(), name, result, p1, p2, p3, p4, traits(MemberTraits.FINAL)),
                Kind.STATIC);
    }

    /// Declares a `public static void` method with four parameters and the type parameters of this
    /// member.
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
    /// @return the fact of the method
    public final <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(
                UnsafeInvocables.voidStaticMethod(builder.self(), name, p1, p2, p3, p4, traits(MemberTraits.FINAL)),
                Kind.STATIC);
    }

    /// Declares a `private` method of an interface with the type parameters of this member, see
    /// [TypeMemberDeclarations#declarePrivateMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public final <R> MethodRef0<Self, R> declarePrivateMethod(String name, TypeToken<R> result) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /// Declares a `private void` method of an interface with the type parameters of this member.
    ///
    /// @param name the method name
    /// @return the fact of the method
    public final VoidMethodRef0<Self> declareVoidPrivateMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(builder.self(), name, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /// Declares a `private` method of an interface with one parameter and the type parameters of this
    /// member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <R, A1> MethodRef1<Self, R, A1> declarePrivateMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /// Declares a `private void` method of an interface with one parameter and the type parameters of
    /// this member.
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public final <A1> VoidMethodRef1<Self, A1> declareVoidPrivateMethod(String name, TypeToken<A1> p1) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /// Declares a `private` method of an interface with two parameters and the type parameters of this
    /// member.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <R, A1, A2> MethodRef2<Self, R, A1, A2> declarePrivateMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.method(builder.self(), name, result, p1, p2, traits(MemberTraits.FINAL)),
                Kind.PRIVATE);
    }

    /// Declares a `private void` method of an interface with two parameters and the type parameters of
    /// this member.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public final <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidPrivateMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(
                UnsafeInvocables.voidMethod(builder.self(), name, p1, p2, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }
}
