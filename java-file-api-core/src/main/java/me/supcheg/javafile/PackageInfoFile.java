package me.supcheg.javafile;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.doc.DocComment;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/// A `package-info.java` file: a package declaration with its annotations.
///
/// ```java
/// PackageInfoFile info = PackageInfoFile.of("com.example.api",
///         new AnnotationBuilder(ClassDesc.of("org.jspecify.annotations", "NullMarked")).build());
/// ```
public final class PackageInfoFile implements RenderableFile {
    private static final Path PACKAGE_INFO_JAVA = Path.of("package-info.java");

    private final String packageName;
    private final List<AnnotationUse> annotations;
    private final Optional<DocComment> doc;

    private PackageInfoFile(String packageName, List<AnnotationUse> annotations, Optional<DocComment> doc) {
        this.packageName = packageName;
        this.annotations = List.copyOf(annotations);
        this.doc = doc;
    }

    /// Builds a `package-info.java` file for `packageName`, annotated with `annotations`.
    ///
    /// @param packageName the package being annotated
    /// @param annotations the package annotations, in order
    /// @return the finished file
    public static PackageInfoFile of(String packageName, AnnotationUse... annotations) {
        return new PackageInfoFile(packageName, List.of(annotations), Optional.empty());
    }

    /// Returns a copy of this file with a documentation comment on the package.
    ///
    /// @param doc the comment
    /// @return a new file with the comment before the annotations
    public PackageInfoFile withDoc(DocComment doc) {
        return new PackageInfoFile(packageName, annotations, Optional.of(doc));
    }

    /// The annotated package's name.
    ///
    /// @return the package name
    public String packageName() {
        return packageName;
    }

    @Override
    public Meta renderMeta() {
        return new Meta(packageName, annotations, doc);
    }

    /// The package and annotations of a [PackageInfoFile]; you rarely need it directly.
    ///
    /// @param packageName the package being annotated
    /// @param annotations the package annotations, in order
    /// @param doc the documentation comment of the package, if any
    public record Meta(String packageName, List<AnnotationUse> annotations, Optional<DocComment> doc)
            implements RenderableFile.Meta {}

    @Override
    public Path pathSuffix() {
        return Stream.of(packageName.split("\\."))
                .map(Path::of)
                .reduce(Path::resolve)
                .map(package_ -> package_.resolve(PACKAGE_INFO_JAVA))
                .orElse(PACKAGE_INFO_JAVA);
    }
}
