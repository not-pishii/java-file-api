package me.supcheg.javafile.facts.processor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.Optional;

/// Reports what the processor finds through javac's [Messager], each
/// message attached to the `@Facts` it concerns where there is one, so the
/// IDE and the build point at the annotation.
final class Diagnostics {
    private final Messager messager;
    private final boolean strict;

    /// @param messager the messager of the compilation
    /// @param strict whether a skipped member is an error, see [Options#strict()]
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

    /// Reports that a member gets no fact (Q10): a warning, or an error
    /// under `-Ajavafile.facts.strict=true`.
    ///
    /// @param element the `@Facts` that asked for the member's type
    /// @param message the message
    void skipped(Optional<? extends Element> element, String message) {
        if (strict) {
            error(element, message);
        } else {
            warning(element, message);
        }
    }
}
