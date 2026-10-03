package me.supcheg.javafile.facts.processor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/// Reads the types `@Facts` asks for (mini-spec §1.1, §8).
///
/// The annotation is read through its mirror, by name: the processor needs
/// no class of `me.supcheg.javafile.facts.meta` at run time. A class literal
/// of a type that does not exist yet — one another processor generates in
/// this round — reads as the string `"<error>"`; the element is then read
/// again, by name, in the next round.
final class Requests {

    /// The qualified name of `@Facts`.
    static final String FACTS = "me.supcheg.javafile.facts.meta.Facts";

    private Requests() {}

    /// Reads `@Facts` of an element, reporting the literals that name no
    /// type a metamodel can be made of.
    ///
    /// @param element the annotated class, interface, enum, record or package
    /// @param diagnostics where to report
    /// @return the requested types and whether some literal is not resolved yet
    static Reading read(Element element, Diagnostics diagnostics) {
        List<Found> found = element.getAnnotationMirrors().stream()
                .filter(annotation -> ((TypeElement)
                                annotation.getAnnotationType().asElement())
                        .getQualifiedName()
                        .contentEquals(FACTS))
                .flatMap(annotation ->
                        annotation.getElementValues().values().stream().flatMap(value -> literals(annotation, value)))
                .toList();
        found.forEach(f -> {
            if (f.literal() instanceof Literal.Rejected(String reason)) {
                diagnostics.error(element, f.annotation(), f.value(), reason);
            }
        });
        return new Reading(
                found.stream()
                        .flatMap(f -> f.literal() instanceof Literal.Type(TypeElement type)
                                ? Stream.of(type)
                                : Stream.empty())
                        .toList(),
                found.stream().anyMatch(f -> f.literal() instanceof Literal.Unresolved));
    }

    /// The literals of one value of `@Facts`: one per class literal, or an unresolved one if the
    /// value is not a list of them.
    private static Stream<Found> literals(AnnotationMirror annotation, AnnotationValue value) {
        return value.getValue() instanceof List<?> items
                ? items.stream().map(item -> {
                    AnnotationValue literal = (AnnotationValue) item;
                    return new Found(annotation, literal, literal(literal.getValue()));
                })
                : Stream.of(new Found(annotation, value, new Literal.Unresolved()));
    }

    private static Literal literal(Object value) {
        if (!(value instanceof TypeMirror type)) {
            return new Literal.Unresolved();
        }
        return switch (type.getKind()) {
            case DECLARED -> declared((TypeElement) ((DeclaredType) type).asElement());
            case ERROR -> new Literal.Unresolved();
            default -> new Literal.Rejected("@Facts asks for classes, interfaces, enums and records, got " + type);
        };
    }

    private static Literal declared(TypeElement type) {
        return refusal(type).<Literal>map(Literal.Rejected::new).orElseGet(() -> new Literal.Type(type));
    }

    /// Why `@Facts` cannot ask for a type: an annotation interface, a type
    /// that is not `public` or is nested in one, a class with `$` in its
    /// simple name, a type in the unnamed package.
    ///
    /// @param type a class, interface, enum, record or annotation interface
    /// @return the reason, a sentence about the type; empty if a metamodel can be asked for
    static Optional<String> refusal(TypeElement type) {
        if (type.getKind() == ElementKind.ANNOTATION_TYPE) {
            return Optional.of("annotation interface " + type.getQualifiedName() + " is not supported yet");
        }
        Optional<TypeElement> hidden = notPublic(type);
        if (hidden.isPresent()) {
            return Optional.of(
                    hidden.get().equals(type)
                            ? type.getQualifiedName() + " is not public"
                            : type.getQualifiedName() + " is nested in "
                                    + hidden.get().getQualifiedName() + ", which is not public");
        }
        if (dollar(type)) {
            return Optional.of(type.getQualifiedName() + ": a class with $ in its simple name is not supported yet");
        }
        if (unnamedPackage(type)) {
            return Optional.of(type.getQualifiedName()
                    + " is in the unnamed package, which a metamodel in a named package cannot refer to");
        }
        return Optional.empty();
    }

    /// The innermost type of `type` and its enclosing types that is not public.
    private static Optional<TypeElement> notPublic(TypeElement type) {
        return enclosing(type)
                .filter(element -> !element.getModifiers().contains(Modifier.PUBLIC))
                .findFirst();
    }

    /// Whether the simple name of the type or of an enclosing type has a
    /// `$`, which `java-file-api-core` reads as the separator of a member
    /// type in a [java.lang.constant.ClassDesc].
    private static boolean dollar(TypeElement type) {
        return enclosing(type)
                .anyMatch(element -> element.getSimpleName().toString().contains("$"));
    }

    private static boolean unnamedPackage(TypeElement type) {
        return Stream.<Element>iterate(type, Element::getEnclosingElement)
                .filter(PackageElement.class::isInstance)
                .map(PackageElement.class::cast)
                .findFirst()
                .orElseThrow()
                .isUnnamed();
    }

    /// `type` and the types that enclose it, the innermost first.
    private static Stream<TypeElement> enclosing(TypeElement type) {
        return Stream.<Element>iterate(type, element -> element instanceof TypeElement, Element::getEnclosingElement)
                .map(TypeElement.class::cast);
    }

    /// The types one `@Facts` asks for.
    ///
    /// @param types the types, in the order of the literals
    /// @param unresolved whether a literal names a type that does not exist yet
    record Reading(List<TypeElement> types, boolean unresolved) {}

    /// A literal and where it is, to report it.
    private record Found(AnnotationMirror annotation, AnnotationValue value, Literal literal) {}

    /// One class literal of `@Facts`.
    private sealed interface Literal {

        /// A type a metamodel can be made of.
        record Type(TypeElement type) implements Literal {}

        /// A type that does not exist yet.
        record Unresolved() implements Literal {}

        /// Not a type a metamodel can be made of.
        record Rejected(String reason) implements Literal {}
    }
}
