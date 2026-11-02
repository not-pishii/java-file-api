package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/// The declaration of a generic method of a type that is not a class being declared
/// ([TypeMemberDeclarations]): an interface, a record or an enum. It is the stage in which the type
/// parameters of the member are declared, before the member itself is:
///
/// ```java
/// var id = ib.declareGenericMethod(typeParam("T"), new GenericMethodArities.TypeMethodSpec1<>() {
///     @Override
///     public <F extends Invocable> F build(TypeGenericMethodDeclaration<Self> m, TypeVarToken<Object> t) {
///         return m.declareMethod("id", t, t);
///     }
/// });
/// ```
///
/// It declares what [TypeMemberDeclarations] declares — a method, an abstract one, a `default` one of an
/// interface, a `static` one, a `private` one — with the type parameters of the member in scope in the
/// signature and the body of that member alone (JLS 6.3). An enum declares methods and a record does too;
/// an annotation type declares elements alone, and no generic method.
///
/// The leftmost bound of a type parameter is the token [TypeParameter] was made with; each further bound
/// is said by [#bound(TypeVarToken, InterfaceToken)], before the member is declared, as the header of a
/// generic class says the bounds of its own type parameters ([ClassHeader#bound]).
///
/// @param <Self> the brand of the type being declared
public final class TypeGenericMethodDeclaration<Self> extends TypeGenericDeclarations<Self> {

    /// What one type parameter of the member is declared with: the token the body uses, and its bounds,
    /// the leftmost first.
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

    @Override
    TypeMemberDeclarations<Self> declarations() {
        return builder;
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }

    @Override
    List<AnnotationRef<?>> annotations() {
        return List.of();
    }

    @Override
    List<List<AnnotationRef<?>>> parameterAnnotations() {
        return List.of();
    }

    @Override
    Set<Modifier> modifiers() {
        return Set.of();
    }

    @Override
    Optional<DocComment> doc() {
        return Optional.empty();
    }

    /// The type parameters of the member, as the declaration writes them.
    ///
    /// @return the type parameters, in declaration order
    @Override
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
}
