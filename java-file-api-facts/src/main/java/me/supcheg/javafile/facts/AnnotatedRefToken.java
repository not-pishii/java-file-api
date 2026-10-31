package me.supcheg.javafile.facts;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A reference type with type-use annotations on it, e.g. `@NonNull String`
/// (JLS 9.7.4), made by [UnsafeFacts#annotated(RefToken, AnnotationUse...)].
///
/// The token stands for the same Java type as the one it annotates: its
/// erasure and its facts are those of that token. Only the type it is
/// written as carries the annotations, so a cast or a declaration of it is
/// rendered `@NonNull String`. A primitive type has no such form.
///
/// @param <T> the Java type this token stands for
public final class AnnotatedRefToken<T> implements RefToken<T> {
    private final RefToken<T> token;
    private final List<AnnotationUse> annotations;
    private final TypeRef typeRef;

    AnnotatedRefToken(RefToken<T> token, List<AnnotationUse> annotations) {
        this.token = token;
        this.annotations = List.copyOf(annotations);
        this.typeRef = annotated(token.typeRef());
    }

    private TypeRef annotated(TypeRef type) {
        if (type instanceof ClassTypeRef cls) {
            return new ClassTypeRef(cls.desc(), annotations);
        }
        if (type instanceof ParameterizedTypeRef parameterized) {
            return new ParameterizedTypeRef(parameterized.raw(), parameterized.args(), annotations);
        }
        if (type instanceof ArrayTypeRef array) {
            return new ArrayTypeRef(array.component(), annotations);
        }
        // a primitive is no reference type, and no RefToken is one: the false arm is not reached
        return type instanceof TypeVarRef variable ? new TypeVarRef(variable.name(), annotations) : type;
    }

    /// The token the annotations are on.
    ///
    /// @return the token
    public RefToken<T> token() {
        return token;
    }

    /// The type-use annotations, in order.
    ///
    /// @return the annotations
    public List<AnnotationUse> annotations() {
        return annotations;
    }

    @Override
    public TypeRef typeRef() {
        return typeRef;
    }

    @Override
    public ClassDesc erasure() {
        return token.erasure();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AnnotatedRefToken<?> other
                && token.equals(other.token)
                && annotations.equals(other.annotations);
    }

    @Override
    public int hashCode() {
        return token.hashCode() * 31 + annotations.hashCode();
    }

    @Override
    public String toString() {
        return annotations.isEmpty() ? token.toString() : annotations + " " + token;
    }
}
