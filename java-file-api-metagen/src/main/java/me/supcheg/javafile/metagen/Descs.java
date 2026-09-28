package me.supcheg.javafile.metagen;

import me.supcheg.javafile.facts.TypeNames;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.stream.Collectors;

/// Readable descriptions of class-file members for diagnostics.
final class Descs {
    private Descs() {}

    static String name(ClassDesc desc) {
        return TypeNames.describe(desc);
    }

    static String describe(ClassDesc owner, IndexedMember.Kind kind, String name, String descriptor, boolean isStatic) {
        return switch (kind) {
            case METHOD -> {
                MethodTypeDesc type = MethodTypeDesc.ofDescriptor(descriptor);
                yield (isStatic ? "static " : "") + name(type.returnType()) + " " + name(owner) + "." + name
                        + params(type);
            }
            case FIELD ->
                (isStatic ? "static " : "") + name(ClassDesc.ofDescriptor(descriptor)) + " " + name(owner) + "." + name;
            case CTOR -> "new " + name(owner) + params(MethodTypeDesc.ofDescriptor(descriptor));
        };
    }

    private static String params(MethodTypeDesc type) {
        return type.parameterList().stream().map(Descs::name).collect(Collectors.joining(", ", "(", ")"));
    }
}
