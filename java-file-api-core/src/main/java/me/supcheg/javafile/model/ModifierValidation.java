package me.supcheg.javafile.model;

import java.lang.constant.ClassDesc;
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
        if (modifiers.contains(Modifier.NON_SEALED) && modifiers.contains(Modifier.FINAL)) {
            throw new IllegalArgumentException(declKind + " cannot be both final and non-sealed");
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
                        || modifiers.contains(Modifier.PRIVATE)
                        || modifiers.contains(Modifier.SYNCHRONIZED)
                        || modifiers.contains(Modifier.NATIVE))) {
            // JLS 8.4.3.6: an abstract method is never static, final, private,
            // synchronized or native.
            throw new IllegalArgumentException(
                    declKind + " cannot combine abstract with final, static, private, synchronized, or native");
        }
        if (modifiers.contains(Modifier.VOLATILE)
                && (modifiers.contains(Modifier.FINAL) || modifiers.contains(Modifier.STATIC))) {
            // JLS 8.3.1.4: a volatile field is never final. The typed layer also
            // keeps it an instance field, which the task's model makes of it.
            throw new IllegalArgumentException(declKind + " cannot combine volatile with final or static");
        }
        if (modifiers.contains(Modifier.TRANSIENT) && modifiers.contains(Modifier.STATIC)) {
            // A transient field is part of the state of an instance; the typed
            // layer renders no static one.
            throw new IllegalArgumentException(declKind + " cannot combine transient with static");
        }
        return modifiers;
    }

    /// Holds a `permits` list to what makes a declaration `sealed`: a non-empty
    /// list is no combination with `final` or `non-sealed` (JLS 8.1.1.2,
    /// 9.1.1.4), and a subtype is named once.
    static List<ClassDesc> requireValidPermits(Set<Modifier> modifiers, List<ClassDesc> permits, String declKind) {
        if (permits.isEmpty()) {
            return permits;
        }
        if (modifiers.contains(Modifier.FINAL)) {
            throw new IllegalArgumentException(declKind + " cannot be both final and sealed");
        }
        if (modifiers.contains(Modifier.NON_SEALED)) {
            throw new IllegalArgumentException(declKind + " cannot be both non-sealed and sealed");
        }
        for (int i = 0; i < permits.size(); i++) {
            ClassDesc permit = permits.get(i);
            if (permits.subList(0, i).contains(permit)) {
                throw new IllegalArgumentException(declKind + " cannot permit " + permit.displayName() + " twice");
            }
        }
        return permits;
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
