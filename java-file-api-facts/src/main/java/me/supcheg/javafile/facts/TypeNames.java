package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.lang.constant.ClassDesc;
import java.util.stream.Collectors;

/// Readable, fully qualified type names for diagnostics.
public final class TypeNames {
    private TypeNames() {}

    /// Describes a descriptor, e.g. `java.util.List` or `int[]`.
    ///
    /// @param desc the descriptor
    /// @return the qualified name
    public static String describe(ClassDesc desc) {
        if (desc.isArray()) {
            return describe(desc.componentType()) + "[]";
        }
        if (desc.isPrimitive() || desc.packageName().isEmpty()) {
            return desc.displayName();
        }
        return desc.packageName() + "." + desc.displayName();
    }

    /// Describes a type, e.g. `java.util.List<java.lang.String>`.
    ///
    /// @param type the type
    /// @return the qualified name
    public static String describe(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> primitive.sourceName();
            case ArrayTypeRef array -> describe(array.component()) + "[]";
            case ClassTypeRef cls -> describe(cls.desc());
            case TypeVarRef var -> var.name();
            case ParameterizedTypeRef parameterized ->
                parameterized.args().stream()
                        .map(TypeNames::describe)
                        .collect(Collectors.joining(", ", describe(parameterized.raw()) + "<", ">"));
        };
    }

    private static String describe(TypeArg arg) {
        return switch (arg) {
            case ExactTypeArg exact -> describe(exact.type());
            case ExtendsTypeArg bounded -> "? extends " + describe(bounded.bound());
            case SuperTypeArg bounded -> "? super " + describe(bounded.bound());
            case UnboundedTypeArg ignored -> "?";
        };
    }
}
