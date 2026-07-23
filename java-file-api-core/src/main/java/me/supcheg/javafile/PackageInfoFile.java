package me.supcheg.javafile;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.render.StandardRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/// A `package-info.java` source file: package-level annotations and the
/// package declaration, with no type declaration.
public final class PackageInfoFile {

    private final String packageName;
    private final List<AnnotationUse> annotations;

    private PackageInfoFile(String packageName, List<AnnotationUse> annotations) {
        this.packageName = packageName;
        this.annotations = List.copyOf(annotations);
    }

    /// Builds a `package-info.java` file for `packageName`, annotated with `annotations`.
    ///
    /// @param packageName the package being annotated
    /// @param annotations the package annotations, in order
    /// @return the finished file
    public static PackageInfoFile of(String packageName, AnnotationUse... annotations) {
        return new PackageInfoFile(packageName, List.of(annotations));
    }

    /// Renders this file's annotations, package declaration, and computed imports to source text.
    ///
    /// @return the complete source text
    public String render() {
        return StandardRenderer.instance().renderPackageInfo(packageName, annotations, SourceRenderer.standardFormat());
    }

    /// Writes this file's rendered source text as `package-info.java` under `outputDir`.
    ///
    /// @param outputDir the source root to write into
    /// @throws IOException if the directories or file cannot be created or written
    public void writeTo(Path outputDir) throws IOException {
        Path packageDir = packageName.isEmpty() ? outputDir : outputDir.resolve(packageName.replace('.', '/'));
        Files.createDirectories(packageDir);
        Files.writeString(packageDir.resolve("package-info.java"), render());
    }
}
