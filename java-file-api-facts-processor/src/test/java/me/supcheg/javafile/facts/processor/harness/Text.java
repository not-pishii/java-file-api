package me.supcheg.javafile.facts.processor.harness;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/// Text files as the fixtures hold them: UTF-8 with `\n` between lines,
/// whatever git made of the line ends at checkout.
final class Text {
    private Text() {}

    /// The text of a file, with every line end as `\n`.
    static String read(Path file) {
        try {
            return normalize(Files.readString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// The lines of a file that say something: not the blank ones and not
    /// the `#` comments; none if there is no such file.
    static List<String> entries(Path file) {
        return Files.isRegularFile(file)
                ? read(file)
                        .lines()
                        .map(String::strip)
                        .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                        .toList()
                : List.of();
    }

    /// Writes a file, and the directories it is in, with `\n` line ends.
    static void write(Path file, String text) {
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, normalize(text), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// `text` with every line end as `\n`.
    static String normalize(String text) {
        return text.replace("\r\n", "\n").replace('\r', '\n');
    }

    /// A relative path with `/` between its names, on every platform.
    static String path(Path relative) {
        return relative.toString().replace('\\', '/');
    }
}
