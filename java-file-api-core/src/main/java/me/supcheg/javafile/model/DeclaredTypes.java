package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;

final class DeclaredTypes {

    private DeclaredTypes() {}

    // The descriptor of a type a declaration can declare: of a class or an interface, and each name of its
    // nesting chain a type identifier (JLS 3.8) — `var`, `record` and `int[]` name no declared type.
    static ClassDesc requireDeclarable(ClassDesc desc) {
        if (!desc.isClassOrInterface()) {
            throw new IllegalArgumentException("not a class or an interface: " + desc.displayName());
        }
        ClassDescNames.nestingChain(desc).forEach(Identifiers::requireTypeIdentifier);
        return desc;
    }
}
