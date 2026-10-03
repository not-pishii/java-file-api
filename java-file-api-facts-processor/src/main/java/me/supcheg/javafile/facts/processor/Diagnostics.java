package me.supcheg.javafile.facts.processor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Optional;

/// Reports what the processor finds through javac's [Messager], each
/// message attached to the `@Facts` it concerns where there is one, so the
/// IDE and the build point at the annotation.
final class Diagnostics {
    private final Messager messager;
    private final boolean strict;

    /// @param messager the messager of the compilation
    /// @param strict whether a member skipped in a type `@Facts` asks for is an error, see [Options#strict()]
    Diagnostics(Messager messager, boolean strict) {
        this.messager = messager;
        this.strict = strict;
    }

    /// Reports an error that concerns no element.
    ///
    /// @param message the message
    void error(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    /// Reports an error on an element, or on none if it no longer exists.
    ///
    /// @param element the element
    /// @param message the message
    void error(Optional<? extends Element> element, String message) {
        element.ifPresentOrElse(
                e -> messager.printMessage(Diagnostic.Kind.ERROR, message, e),
                () -> messager.printMessage(Diagnostic.Kind.ERROR, message));
    }

    /// Reports an error on one value of an annotation.
    ///
    /// @param element the annotated element
    /// @param annotation the annotation
    /// @param value the value
    /// @param message the message
    void error(Element element, AnnotationMirror annotation, AnnotationValue value, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element, annotation, value);
    }

    /// Reports a warning on an element, or on none if it no longer exists.
    ///
    /// @param element the element
    /// @param message the message
    void warning(Optional<? extends Element> element, String message) {
        element.ifPresentOrElse(
                e -> messager.printMessage(Diagnostic.Kind.WARNING, message, e),
                () -> messager.printMessage(Diagnostic.Kind.WARNING, message));
    }

    /// Reports that members of a type get no fact (Q10): a warning, or
    /// under `-Ajavafile.facts.strict=true` an error if `@Facts` asks for
    /// the type. Of a type that is in the graph only as a supertype of what
    /// `@Facts` asks for it is a warning whatever the option (Q13): nobody
    /// asked for the facts of that type, and nothing can be done about them
    /// in `@Facts`.
    ///
    /// @param element the `@Facts` the type is there for
    /// @param message the message
    /// @param reasons why the type whose members have no fact is in the graph, see [TypeGraph#reasons]
    void skipped(Optional<? extends Element> element, String message, List<TypeGraph.Reason> reasons) {
        if (strict && reasons.stream().anyMatch(TypeGraph.Reason.Asked.class::isInstance)) {
            error(element, message);
        } else {
            warning(element, message);
        }
    }
}
