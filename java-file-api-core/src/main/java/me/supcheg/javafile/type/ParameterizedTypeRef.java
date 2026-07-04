package me.supcheg.javafile.type;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A reference to a generic type applied to type arguments, e.g. `List<String>`.
///
/// @param raw the generic type's raw class or interface
/// @param args the type arguments applied to `raw`; defensively copied into an
///             unmodifiable list
public record ParameterizedTypeRef(ClassDesc raw, List<TypeArg> args) implements TypeRef {
    public ParameterizedTypeRef {
        args = List.copyOf(args);
    }
}
