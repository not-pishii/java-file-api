package me.supcheg.javafile.facts.processor.harness;

import org.opentest4j.AssertionFailedError;

import javax.tools.Diagnostic;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// An error javac must report for a file of `use-fails/`, as a comment of
/// the file tells it on the line of the error:
///
/// ```java
/// Object sorted = new Sorted_<Object>(Object_.TOKEN); // error: is not within bounds of type-variable T
/// ```
///
/// The file must fail for these reasons and no other: every such line has
/// an error whose message contains the text, and javac reports no error
/// elsewhere. A file that fails for another reason — a typo, a missing
/// import — fails the test.
///
/// @param line the line the error is on, from 1
/// @param text what the message of the error contains
record Rejection(long line, String text) {
    private static final Pattern COMMENT = Pattern.compile("//\\s*error:\\s*(.+?)\\s*$");

    /// The errors the comments of a source expect.
    static List<Rejection> expected(Source source) {
        List<String> lines = source.text().lines().toList();
        return IntStream.range(0, lines.size())
                .boxed()
                .flatMap(index -> {
                    Matcher comment = COMMENT.matcher(lines.get(index));
                    return comment.find() ? Stream.of(new Rejection(index + 1, comment.group(1))) : Stream.empty();
                })
                .toList();
    }

    /// Fails the test unless javac rejected the source as its comments tell.
    ///
    /// @param source a file of `use-fails/`
    /// @param compiled what came of compiling it
    static void verify(Source source, Compiled compiled) {
        List<Rejection> expected = expected(source);
        List<Diagnosed> errors = compiled.diagnostics().stream()
                .filter(diagnostic -> diagnostic.kind() == Diagnostic.Kind.ERROR)
                .toList();
        List<String> complaints = Stream.of(
                        Stream.of("no line has an `// error: …` comment").filter(_ -> expected.isEmpty()),
                        Stream.of("it compiles").filter(_ -> compiled.succeeded()),
                        expected.stream()
                                .filter(rejection -> errors.stream().noneMatch(rejection::isOf))
                                .map(rejection ->
                                        "line " + rejection.line() + ": no error with \"" + rejection.text() + "\""),
                        errors.stream()
                                .filter(error -> expected.stream().noneMatch(rejection -> rejection.isOf(error)))
                                .map(error -> "an error that is not expected: "
                                        + error.render().stripTrailing()))
                .flatMap(complaint -> complaint)
                .toList();
        if (!complaints.isEmpty()) {
            throw new AssertionFailedError(source.path() + " is not rejected as its comments tell:\n"
                    + complaints.stream()
                            .map(complaint -> "  " + complaint + "\n")
                            .collect(Collectors.joining()));
        }
    }

    private boolean isOf(Diagnosed error) {
        return error.position().filter(position -> position.line() == line).isPresent()
                && error.message().contains(text);
    }
}
