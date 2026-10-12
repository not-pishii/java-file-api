package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.TypeVarRef;

import java.lang.constant.ClassDesc;

/// A token of a type variable of the generated code, e.g. the `T` of a
/// generated generic method.
///
/// Typed declarations introduce it through a CPS brand, so the phantom `T`
/// is a fresh type variable of the generator as well.
///
/// A type variable is a name with a scope, which the name alone does not
/// tell: the token says where it comes from ([Origin]), so that the typed
/// layer writes the type parameter of a class it declares in the body of
/// that class alone.
///
/// @param <T> the Java type variable this token stands for
public final class TypeVarToken<T> implements RefToken<T> {
    private final TypeVarRef typeRef;
    private final ClassDesc erasure;
    private final Origin origin;

    TypeVarToken(TypeVarRef typeRef, ClassDesc erasure, Origin origin) {
        this.typeRef = typeRef;
        this.erasure = erasure;
        this.origin = origin;
    }

    /// Where the type variable comes from.
    ///
    /// @return its origin
    public Origin origin() {
        return origin;
    }

    /// Where a type variable comes from, and so where it is in scope.
    public sealed interface Origin {
        /// A generator vouched that the variable is in scope where its token is used
        /// ([UnsafeFacts#typeVarToken]).
        record Vouched() implements Origin {}

        /// The variable is a type parameter of a declaration of the typed layer, in scope in that
        /// declaration alone.
        ///
        /// @param declaration the declaration
        record Declared(DeclarationIdentity declaration) implements Origin {}
    }

    @Override
    public TypeVarRef typeRef() {
        return typeRef;
    }

    @Override
    public ClassDesc erasure() {
        return erasure;
    }

    @Override
    public String toString() {
        return typeRef.name();
    }
}
