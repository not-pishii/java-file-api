package me.supcheg.javafile.facts.processor.harness;

import com.google.testing.compile.JavaFileObjects;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// A Java source file to compile.
///
/// @param name the qualified name of its top-level class, or of `package-info`
/// @param text the source
public record Source(String name, String text) {
    private static final Pattern PACKAGE = Pattern.compile("\\bpackage\\s+([\\w.]+)\\s*;");
    private static final Pattern TYPE = Pattern.compile("\\b(?:class|interface|enum|record)\\s+([\\w$]+)");

    /// A source written in a test, named after its package and the first
    /// type it declares, or `package-info` if it declares none.
    ///
    /// @param text the source
    /// @return the source file
    public static Source of(String text) {
        Matcher pkg = PACKAGE.matcher(text);
        String prefix = pkg.find() ? pkg.group(1) + "." : "";
        Matcher type = TYPE.matcher(text);
        return new Source(prefix + (type.find() ? type.group(1) : "package-info"), text);
    }

    /// The `.java` files of a source root, each named after its path,
    /// in the order of their names; none if there is no such directory.
    ///
    /// @param root the directory of the unnamed package
    /// @return the source files
    public static List<Source> in(Path root) {
        if (!Files.isDirectory(root)) {
            return List.of();
        }
        try (Stream<Path> files = Files.walk(root)) {
            return files.filter(Files::isRegularFile)
                    .map(root::relativize)
                    .map(Text::path)
                    .filter(path -> path.endsWith(".java"))
                    .sorted()
                    .map(path -> new Source(
                            path.substring(0, path.length() - ".java".length()).replace('/', '.'),
                            Text.read(root.resolve(path))))
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// The path of the file in its source root, `p/q/T.java`.
    ///
    /// @return the path, with `/` between its names
    /// The sources of several directories as one library, a version of
    /// it laid over it: of the files of one path the one of the earlier
    /// directory, and none if that one is empty — a type the version has
    /// taken out.
    ///
    /// @param directories the directories, the version first and the library last
    /// @return the sources, sorted by name
    public static List<Source> overlaid(Path... directories) {
        return Stream.of(directories)
                .flatMap(directory -> in(directory).stream())
                .collect(Collectors.toMap(Source::name, Function.identity(), (first, _) -> first))
                .values()
                .stream()
                .filter(source -> !source.text().isBlank())
                .sorted(Comparator.comparing(Source::name))
                .toList();
    }

    public String path() {
        return name.replace('.', '/') + ".java";
    }

    /// The simple name of the top-level class.
    ///
    /// @return the name after the last dot
    public String simpleName() {
        return name.substring(name.lastIndexOf('.') + 1);
    }

    JavaFileObject file() {
        return JavaFileObjects.forSourceString(name, text);
    }
}
