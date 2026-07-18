package me.supcheg.javafile.model;

import java.util.EnumSet;
import java.util.Set;

/// Rejects [Modifier] combinations that JLS never allows for a given
/// declaration kind — the runtime escape hatch for a constraint types are
/// disproportionate to fully encode: full per-context sealed modifier
/// hierarchies would multiply the type count far beyond the small,
/// statically-known set of illegal combinations this rejects.
final class ModifierValidation {

    private static final Set<Modifier> ACCESS_MODIFIERS =
            EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE);

    private ModifierValidation() {}

    /// Validates a top-level type declaration's modifiers.
    ///
    /// @param modifiers the declared modifiers
    /// @param allowed the modifiers legal for this declaration kind
    /// @param declKind a human-readable declaration kind, e.g. `"class"`, for the error message
    /// @return `modifiers`, unchanged
    /// @throws IllegalArgumentException if `modifiers` contains a modifier not in `allowed`,
    ///         more than one access modifier, or both `abstract` and `final`
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
}
