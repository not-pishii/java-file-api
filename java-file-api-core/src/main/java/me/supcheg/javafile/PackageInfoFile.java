package me.supcheg.javafile;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/// A `package-info.java` source file: package-level annotations and the
/// package declaration, with no type declaration.
public final class PackageInfoFile implements RenderableFile {
    private static final Path PACKAGE_INFO_JAVA = Path.of("package-info.java");

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

    @Override
    public Meta renderMeta() {
        return new Meta(packageName, annotations);
    }

    /// The render metadata for a [PackageInfoFile]: its package and annotations.
    ///
    /// @param packageName the package being annotated
    /// @param annotations the package annotations, in order
    public record Meta(String packageName, List<AnnotationUse> annotations) implements RenderableFile.Meta {}

    @Override
    public Path pathSuffix() {
        return Stream.of(packageName.split("\\."))
                .map(Path::of)
                .reduce(Path::resolve)
                .map(package_ -> package_.resolve(PACKAGE_INFO_JAVA))
                .orElse(PACKAGE_INFO_JAVA);
    }
}
