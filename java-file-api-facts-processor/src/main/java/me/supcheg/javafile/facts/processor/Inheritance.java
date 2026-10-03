package me.supcheg.javafile.facts.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// What a type extends and implements (Q13): a requested type inherits
/// members from these types, so each of them gets a full metamodel too, and
/// the members are reached through it (Q6(b)).
final class Inheritance {
    private Inheritance() {}

    /// The superclass and the superinterfaces of a type, and theirs in turn:
    /// each once, the nearer first. A supertype that does not exist is left
    /// out.
    ///
    /// @param type a class, interface, enum or record
    /// @return the supertypes
    static Stream<TypeElement> supertypes(TypeElement type) {
        return further(List.of(type), Set.of(type));
    }

    private static Stream<TypeElement> further(List<TypeElement> nearer, Set<TypeElement> seen) {
        List<TypeElement> next = nearer.stream()
                .flatMap(Inheritance::direct)
                .distinct()
                .filter(supertype -> !seen.contains(supertype))
                .toList();
        return next.isEmpty()
                ? Stream.empty()
                : Stream.concat(
                        next.stream(),
                        further(
                                next,
                                Stream.concat(seen.stream(), next.stream()).collect(Collectors.toSet())));
    }

    /// The superclass and the superinterfaces a type names itself, the
    /// superclass first. A supertype that does not exist is left out.
    ///
    /// @param type a class, interface, enum or record
    /// @return the direct supertypes
    static Stream<TypeElement> direct(TypeElement type) {
        return Stream.concat(Stream.of(type.getSuperclass()), type.getInterfaces().stream())
                .filter(supertype -> supertype.getKind() == TypeKind.DECLARED)
                .map(supertype -> (TypeElement) ((DeclaredType) supertype).asElement());
    }
}
