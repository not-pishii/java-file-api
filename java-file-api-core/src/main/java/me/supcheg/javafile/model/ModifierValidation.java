package me.supcheg.javafile.model;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

final class ModifierValidation {

    private static final Set<Modifier> ACCESS_MODIFIERS =
            EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE);

    private ModifierValidation() {}

    static Set<Modifier> requireValidTopLevel(Set<Modifier> modifiers, Set<Modifier> allowed, String declKind) {
        for (Modifier m : modifiers) {
            if (!allowed.contains(m)) {
                throw new IllegalArgumentException(declKind + " cannot be '" + m + "'");
            }
        }
        long accessCount = modifiers.stream().filter(ACCESS_MODIFIERS::contains).count();
        if (accessCount > 1) {
            throw new IllegalArgumentException(declKind + " cannot combine more than one access modifier");
        }
        if (modifiers.contains(Modifier.ABSTRACT) && modifiers.contains(Modifier.FINAL)) {
            throw new IllegalArgumentException(declKind + " cannot be both abstract and final");
        }
        return modifiers;
    }

    static Set<Modifier> requireValidMember(Set<Modifier> modifiers, Set<Modifier> allowed, String declKind) {
        for (Modifier m : modifiers) {
            if (!allowed.contains(m)) {
                throw new IllegalArgumentException(declKind + " cannot be '" + m + "'");
            }
        }
        long accessCount = modifiers.stream().filter(ACCESS_MODIFIERS::contains).count();
        if (accessCount > 1) {
            throw new IllegalArgumentException(declKind + " cannot combine more than one access modifier");
        }
        if (modifiers.contains(Modifier.ABSTRACT)
                && (modifiers.contains(Modifier.FINAL)
                        || modifiers.contains(Modifier.STATIC)
                        || modifiers.contains(Modifier.PRIVATE))) {
            throw new IllegalArgumentException(declKind + " cannot combine abstract with final, static, or private");
        }
        return modifiers;
    }

    static List<Param> requireVarargsOnlyLast(List<Param> params) {
        for (int i = 0; i < params.size() - 1; i++) {
            if (params.get(i).varargs()) {
                throw new IllegalArgumentException("only the last parameter may be varargs: "
                        + params.get(i).name());
            }
        }
        return params;
    }
}
