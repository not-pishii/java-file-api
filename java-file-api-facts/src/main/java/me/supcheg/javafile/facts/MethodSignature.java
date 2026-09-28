package me.supcheg.javafile.facts;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.stream.Collectors;

/// The erased signature of a method (JLS 8.4.2): its name and the erasures of
/// its parameter types. Two methods with equal signatures override or clash.
///
/// @param name the method name
/// @param params the erased parameter types, in order
public record MethodSignature(String name, List<ClassDesc> params) {
    public MethodSignature {
        params = List.copyOf(params);
    }

    @Override
    public String toString() {
        return params.stream().map(TypeNames::describe).collect(Collectors.joining(", ", name + "(", ")"));
    }
}
