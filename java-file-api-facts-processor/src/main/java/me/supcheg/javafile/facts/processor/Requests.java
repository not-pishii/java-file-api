package me.supcheg.javafile.facts.processor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        List<TypeElement> types = new ArrayList<>();
        boolean unresolved = false;
        for (AnnotationMirror annotation : element.getAnnotationMirrors()) {
            if (!((TypeElement) annotation.getAnnotationType().asElement())
                    .getQualifiedName()
                    .contentEquals(FACTS)) {
                continue;
            }
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry :
                    annotation.getElementValues().entrySet()) {
                if (!(entry.getValue().getValue() instanceof List<?> values)) {
                    unresolved = true;
                    continue;
                }
                for (Object item : values) {
                    AnnotationValue value = (AnnotationValue) item;
                    switch (literal(value.getValue())) {
                        case Literal.Type(TypeElement type) -> types.add(type);
                        case Literal.Unresolved ignored -> unresolved = true;
                        case Literal.Rejected(String reason) -> diagnostics.error(element, annotation, value, reason);
                    }
                }
            }
        }
        return new Reading(types, unresolved);
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
        if (type.getKind() == ElementKind.ANNOTATION_TYPE) {
            return new Literal.Rejected("annotation interface " + type.getQualifiedName() + " is not supported yet");
        }
        Optional<TypeElement> hidden = notPublic(type);
        if (hidden.isPresent()) {
            return new Literal.Rejected(
                    hidden.get().equals(type)
                            ? type.getQualifiedName() + " is not public"
                            : type.getQualifiedName() + " is nested in "
                                    + hidden.get().getQualifiedName() + ", which is not public");
        }
        if (dollar(type)) {
            return new Literal.Rejected(
                    type.getQualifiedName() + ": a class with $ in its simple name is not supported yet");
        }
        if (unnamedPackage(type)) {
            return new Literal.Rejected(type.getQualifiedName()
                    + " is in the unnamed package, which a metamodel in a named package cannot refer to");
        }
        return new Literal.Type(type);
    }

    /// The innermost type of `type` and its enclosing types that is not public.
    private static Optional<TypeElement> notPublic(TypeElement type) {
        Element current = type;
        while (current instanceof TypeElement element) {
            if (!element.getModifiers().contains(Modifier.PUBLIC)) {
                return Optional.of(element);
            }
            current = element.getEnclosingElement();
        }
        return Optional.empty();
    }

    /// Whether the simple name of the type or of an enclosing type has a
    /// `$`, which `java-file-api-core` reads as the separator of a member
    /// type in a [java.lang.constant.ClassDesc].
    private static boolean dollar(TypeElement type) {
        Element current = type;
        while (current instanceof TypeElement element) {
            if (element.getSimpleName().toString().contains("$")) {
                return true;
            }
            current = element.getEnclosingElement();
        }
        return false;
    }

    private static boolean unnamedPackage(TypeElement type) {
        Element current = type;
        while (!(current instanceof PackageElement pkg)) {
            current = current.getEnclosingElement();
        }
        return pkg.isUnnamed();
    }

    /// The types one `@Facts` asks for.
    ///
    /// @param types the types, in the order of the literals
    /// @param unresolved whether a literal names a type that does not exist yet
    record Reading(List<TypeElement> types, boolean unresolved) {}

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
