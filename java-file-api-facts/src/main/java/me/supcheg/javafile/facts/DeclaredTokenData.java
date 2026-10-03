package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.List;

/// The state shared by the declared token classes: a [TypeShape] applied to
/// type arguments.
abstract class DeclaredTokenData {
    private final TypeShape<?> shape;
    private final ClassOrInterfaceTypeRef typeRef;
    private final List<ClassDesc> arguments;
    private volatile @Nullable MethodTable methods;

    DeclaredTokenData(TypeShape<?> shape, List<TokenArg> args) {
        this(shape, shape.typeRef(args), shape.erasures(args));
    }

    /// @param typeRef the shape's type, raw or with one type argument per type parameter
    /// @param arguments the erasures the shape's method table is instantiated with
    DeclaredTokenData(TypeShape<?> shape, ClassOrInterfaceTypeRef typeRef, List<ClassDesc> arguments) {
        this.shape = shape;
        this.typeRef = typeRef;
        this.arguments = List.copyOf(arguments);
    }

    public final TypeShape<?> shape() {
        return shape;
    }

    public final ClassOrInterfaceTypeRef typeRef() {
        return typeRef;
    }

    public final ClassDesc erasure() {
        return shape.desc();
    }

    /// The supertypes of the shape, except for a raw type, whose supertypes
    /// are erased (JLS 4.8).
    public final Supertypes supertypes() {
        boolean raw = !(typeRef instanceof ParameterizedTypeRef)
                && !shape.typeParameters().isEmpty();
        return raw ? Supertypes.NONE : shape.supertypes();
    }

    public final List<ClassDesc> argumentErasures() {
        return arguments;
    }

    /// The shape's method table instantiated with the erasures of the type
    /// arguments, computed once it is known.
    public final MethodTable methods() {
        MethodTable table = methods;
        if (table == null) {
            table = shape.methods().instantiate(arguments);
            methods = table;
        }
        return table;
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && o.getClass() == getClass() && typeRef.equals(((DeclaredTokenData) o).typeRef);
    }

    @Override
    public final int hashCode() {
        return typeRef.hashCode();
    }

    @Override
    public final String toString() {
        return TypeNames.describe(typeRef);
    }
}
