package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
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
        return MirrorTranslator.refusal(type)
                .<Literal>map(Literal.Rejected::new)
                .orElseGet(() -> new Literal.Type(type));
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
