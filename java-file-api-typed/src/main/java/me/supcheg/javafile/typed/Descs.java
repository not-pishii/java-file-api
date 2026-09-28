package me.supcheg.javafile.typed;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.Optional;
import java.util.stream.Collectors;

final class Descs {
    private Descs() {}

    static String name(ClassDesc desc) {
        if (desc.isArray()) {
            return name(desc.componentType()) + "[]";
        }
        if (desc.isPrimitive() || desc.packageName().isEmpty()) {
            return desc.displayName();
        }
        return desc.packageName() + "." + desc.displayName();
    }

    static String method(ClassDesc owner, String name, MethodTypeDesc type, boolean isStatic) {
        return (isStatic ? "static " : "") + name(type.returnType()) + " " + name(owner) + "." + name + params(type);
    }

    static String field(ClassDesc owner, String name, ClassDesc type, boolean isStatic) {
        return (isStatic ? "static " : "") + name(type) + " " + name(owner) + "." + name;
    }

    static String ctor(ClassDesc owner, MethodTypeDesc type) {
        return "new " + name(owner) + params(type);
    }

    private static String params(MethodTypeDesc type) {
        return type.parameterList().stream().map(Descs::name).collect(Collectors.joining(", ", "(", ")"));
    }

    static TypeRef typeRef(ClassDesc desc) {
        if (desc.isArray()) {
            return Types.array(typeRef(desc.componentType()));
        }
        if (desc.isClassOrInterface()) {
            return Types.of(desc);
        }
        return switch (desc.descriptorString()) {
            case "I" -> PrimitiveTypeRef.INT;
            case "J" -> PrimitiveTypeRef.LONG;
            case "D" -> PrimitiveTypeRef.DOUBLE;
            case "F" -> PrimitiveTypeRef.FLOAT;
            case "Z" -> PrimitiveTypeRef.BOOLEAN;
            case "B" -> PrimitiveTypeRef.BYTE;
            case "S" -> PrimitiveTypeRef.SHORT;
            case "C" -> PrimitiveTypeRef.CHAR;
            default -> throw new IllegalArgumentException("void is not a value type");
        };
    }

    static Optional<TypeRef> returnRef(MethodTypeDesc type) {
        return type.returnType().descriptorString().equals("V")
                ? Optional.empty()
                : Optional.of(typeRef(type.returnType()));
    }
}
