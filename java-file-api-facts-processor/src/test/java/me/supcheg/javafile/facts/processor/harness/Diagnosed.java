package me.supcheg.javafile.facts.processor.harness;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Locale;
import java.util.Optional;

/// What javac or a processor said in a compilation.
///
/// @param kind whether it is an error, a warning or a note
/// @param position where in the sources it points, if anywhere
/// @param message what it says
public record Diagnosed(Diagnostic.Kind kind, Optional<Position> position, String message) {

    /// A place in a source file.
    ///
    /// @param file the path of the file in its source root, `gen/G.java`
    /// @param line the line, from 1
    /// @param column the column, from 1
    public record Position(String file, long line, long column) {}

    static Diagnosed of(Diagnostic<? extends JavaFileObject> diagnostic) {
        return new Diagnosed(
                diagnostic.getKind(),
                Optional.ofNullable(diagnostic.getSource())
                        .filter(_ -> diagnostic.getLineNumber() != Diagnostic.NOPOS)
                        .map(source -> new Position(
                                source.toUri().getPath().replaceFirst("^/", ""),
                                diagnostic.getLineNumber(),
                                diagnostic.getColumnNumber())),
                Text.normalize(diagnostic.getMessage(Locale.ENGLISH)));
    }

    /// The diagnostic as `diagnostics.txt` of a fixture holds it and as
    /// javac prints it: `gen/G.java:3:1: warning: message`, or
    /// `error: message` for one that points nowhere; the lines of a
    /// message after the first are indented.
    ///
    /// @return the lines, each ended with `\n`
    public String render() {
        return position.map(p -> p.file() + ":" + p.line() + ":" + p.column() + ": ")
                        .orElse("")
                + kind.name().toLowerCase(Locale.ROOT)
                + ": "
                + message.replace("\n", "\n    ")
                + "\n";
    }
}
