package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
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
        List<Found> found = found(element).toList();
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

    /// The class literals of `@Facts` on an element that name a type that does
    /// not exist, each with where it is, to report it there.
    ///
    /// @param element the annotated class, interface, enum, record or package
    /// @return the literals
    static Stream<Unresolved> unresolved(Element element) {
        return found(element)
                .filter(found -> found.literal() instanceof Literal.Unresolved)
                .map(found -> new Unresolved(found.annotation(), found.value()));
    }

    private static Stream<Found> found(Element element) {
        return element.getAnnotationMirrors().stream()
                .filter(annotation -> ((TypeElement)
                                annotation.getAnnotationType().asElement())
                        .getQualifiedName()
                        .contentEquals(FACTS))
                .flatMap(annotation ->
                        annotation.getElementValues().values().stream().flatMap(value -> literals(annotation, value)));
    }

    /// The literals of one value of `@Facts`: one per class literal; a value that is not a list of them
    /// is read as one literal.
    private static Stream<Found> literals(AnnotationMirror annotation, AnnotationValue value) {
        Stream<?> items = value.getValue() instanceof List<?> list ? list.stream() : Stream.of(value);
        return items.map(AnnotationValue.class::cast)
                .map(literal -> new Found(annotation, literal, literal(literal.getValue())));
    }

    private static Literal literal(Object value) {
        // javac has a string for the literal of a type that does not exist; an error type is as good
        if (!(value instanceof TypeMirror type) || type.getKind() == TypeKind.ERROR) {
            return new Literal.Unresolved();
        }
        return type instanceof DeclaredType declared
                ? declared((TypeElement) declared.asElement())
                : new Literal.Rejected("@Facts asks for classes, interfaces, enums and records, got " + type);
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

    /// A class literal of a type that does not exist.
    ///
    /// @param annotation the `@Facts` it is in
    /// @param value the literal, or the whole value if it is not a list of literals
    record Unresolved(AnnotationMirror annotation, AnnotationValue value) {}

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
