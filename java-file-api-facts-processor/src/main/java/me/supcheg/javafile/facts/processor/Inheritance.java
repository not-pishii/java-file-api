package me.supcheg.javafile.facts.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// What a type extends and implements, and which of those types it has
/// `public` members from (Q6(b)): a full metamodel has the facts of the
/// members its type declares, so a member the type inherits is a fact of the
/// metamodel of the supertype that declares it.
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

    private static Stream<TypeElement> direct(TypeElement type) {
        return Stream.concat(Stream.of(type.getSuperclass()), type.getInterfaces().stream())
                .filter(supertype -> supertype.getKind() == TypeKind.DECLARED)
                .map(supertype -> (TypeElement) ((DeclaredType) supertype).asElement());
    }

    /// The supertypes that declare the `public` fields and methods a type
    /// inherits. A member the type declares again — a method it overrides, a
    /// field or a `static` method it hides — is not inherited, nor is one a
    /// nearer supertype declares again inherited from the farther one.
    ///
    /// The abstract methods a functional interface inherits do not count:
    /// they are its single abstract method, which is a fact of its own
    /// metamodel, `sam`, declared or inherited.
    ///
    /// @param type a class, interface, enum or record
    /// @param functional whether the metamodel of the type has a `sam`
    /// @param elements the element utilities of the compilation
    /// @return the supertypes
    static Set<TypeElement> declarers(TypeElement type, boolean functional, Elements elements) {
        Collection<? extends List<? extends Element>> namesakes = elements.getAllMembers(type).stream()
                .filter(member -> member.getKind() == ElementKind.FIELD || member.getKind() == ElementKind.METHOD)
                .collect(Collectors.groupingBy(Element::getSimpleName))
                .values();
        return namesakes.stream()
                .flatMap(same -> same.stream()
                        .filter(member -> member.getModifiers().contains(Modifier.PUBLIC))
                        .filter(member -> !member.getEnclosingElement().equals(type))
                        .filter(member -> !(functional && member.getModifiers().contains(Modifier.ABSTRACT)))
                        .filter(member ->
                                same.stream().noneMatch(other -> other != member && elements.hides(other, member))))
                .map(member -> (TypeElement) member.getEnclosingElement())
                .collect(Collectors.toSet());
    }
}
