package me.supcheg.javafile.langmodel;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import java.lang.constant.ClassDesc;
import java.util.List;

/// A one-way bridge from `javax.lang.model` types to this library's [TypeRef]/[ClassDesc] model.
///
/// The bridge only covers top-level types: nested types are not
/// representable by [ClassDesc] the way this bridge constructs it, and
/// `TypeMirror` kinds outside primitives, arrays, and declared types (e.g.
/// type variables, wildcards used outside a type argument) are not
/// supported.
public final class Descriptors {

    private Descriptors() {}

    /// Converts a `javax.lang.model` type to a [TypeRef].
    ///
    /// @param mirror the type to convert
    /// @return the equivalent type reference
    /// @throws IllegalArgumentException if `mirror`'s kind is not a primitive, array, or declared type
    public static TypeRef toTypeRef(TypeMirror mirror) {
        return switch (mirror.getKind()) {
            case BOOLEAN -> PrimitiveTypeRef.BOOLEAN;
            case BYTE -> PrimitiveTypeRef.BYTE;
            case SHORT -> PrimitiveTypeRef.SHORT;
            case INT -> PrimitiveTypeRef.INT;
            case LONG -> PrimitiveTypeRef.LONG;
            case CHAR -> PrimitiveTypeRef.CHAR;
            case FLOAT -> PrimitiveTypeRef.FLOAT;
            case DOUBLE -> PrimitiveTypeRef.DOUBLE;
            case ARRAY -> Types.array(toTypeRef(((ArrayType) mirror).getComponentType()));
            case DECLARED -> toDeclaredTypeRef((DeclaredType) mirror);
            default -> throw new IllegalArgumentException("unsupported type mirror kind: " + mirror.getKind());
        };
    }

    private static TypeRef toDeclaredTypeRef(DeclaredType type) {
        ClassDesc raw = toClassDesc(type);
        List<? extends TypeMirror> args = type.getTypeArguments();
        if (args.isEmpty()) {
            return Types.of(raw);
        }
        TypeArg[] typeArgs = args.stream().map(Descriptors::toTypeArg).toArray(TypeArg[]::new);
        return Types.parameterized(raw, typeArgs);
    }

    private static TypeArg toTypeArg(TypeMirror mirror) {
        if (mirror.getKind() == TypeKind.WILDCARD) {
            WildcardType wildcard = (WildcardType) mirror;
            if (wildcard.getExtendsBound() != null) {
                return Types.extendsBound(toTypeRef(wildcard.getExtendsBound()));
            }
            if (wildcard.getSuperBound() != null) {
                return Types.superBound(toTypeRef(wildcard.getSuperBound()));
            }
            return Types.unbounded();
        }
        return Types.exact(toTypeRef(mirror));
    }

    /// Converts a declared type to a [ClassDesc].
    ///
    /// @param type the declared type to convert
    /// @return a descriptor with the type's package and simple name
    /// @throws UnsupportedOperationException if the type's element is a nested type
    public static ClassDesc toClassDesc(DeclaredType type) {
        return toClassDesc((TypeElement) type.asElement());
    }

    /// Converts a top-level type element to a [ClassDesc].
    ///
    /// @param element the element to convert
    /// @return a descriptor with the element's package and simple name
    /// @throws UnsupportedOperationException if the element is a nested type
    public static ClassDesc toClassDesc(TypeElement element) {
        if (element.getNestingKind() != NestingKind.TOP_LEVEL) {
            throw new UnsupportedOperationException(
                    "nested types are not supported by this bridge: " + element.getQualifiedName());
        }
        String qualifiedName = element.getQualifiedName().toString();
        String simpleName = element.getSimpleName().toString();
        String packageName = qualifiedName.equals(simpleName)
                ? ""
                : qualifiedName.substring(0, qualifiedName.length() - simpleName.length() - 1);
        return ClassDesc.of(packageName, simpleName);
    }
}
