package me.supcheg.javafile.facts.processor;

import org.junit.jupiter.api.Test;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/// Where [Diagnostics] reports: on the `@Facts` a message concerns, and on no element — never on a
/// missing one — if that is not there.
class DiagnosticsTest {

    /// Keeps what it is told, and whether it was told of an element.
    private static final class Told implements Messager {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void printMessage(Diagnostic.Kind kind, CharSequence message) {
            messages.add(kind + ": " + message);
        }

        @Override
        public void printMessage(Diagnostic.Kind kind, CharSequence message, Element element) {
            messages.add(kind + " on " + element + ": " + message);
        }

        @Override
        public void printMessage(
                Diagnostic.Kind kind, CharSequence message, Element element, AnnotationMirror annotation) {
            messages.add(kind + " on " + element + ", " + annotation + ": " + message);
        }

        @Override
        public void printMessage(
                Diagnostic.Kind kind,
                CharSequence message,
                Element element,
                AnnotationMirror annotation,
                AnnotationValue value) {
            messages.add(kind + " on " + element + ", " + annotation + ", " + value + ": " + message);
        }
    }

    @Test
    void aMessageWhoseElementIsNotThereIsOnNoElement() {
        Told told = new Told();
        Diagnostics diagnostics = new Diagnostics(told, false);

        diagnostics.error(Optional.empty(), "an error");
        diagnostics.warning(Optional.empty(), "a warning");
        diagnostics.report(new Diagnostics.Message.Error(Optional.empty(), "told as an error"));
        diagnostics.report(new Diagnostics.Message.Warning(Optional.empty(), "told as a warning"));

        assertThat(told.messages)
                .containsExactly(
                        "ERROR: an error",
                        "WARNING: a warning",
                        "ERROR: told as an error",
                        "WARNING: told as a warning");
    }
}
