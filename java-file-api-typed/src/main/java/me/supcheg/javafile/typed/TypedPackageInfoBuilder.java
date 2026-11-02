package me.supcheg.javafile.typed;

import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.PackageName;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.TargetClasspath;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

/// Declares a `package-info.java` (§6.5): the annotations of a package.
/// An annotation the annotation type does not allow on a package is rejected
/// where it is added.
public final class TypedPackageInfoBuilder {
    private final TargetClasspath target;
    private final String packageName;
    private final List<AnnotationRef<?>> annotations = new ArrayList<>();
    private boolean complete;

    TypedPackageInfoBuilder(TargetClasspath target, String packageName) {
        this.target = target;
        this.packageName = packageName;
    }

    /// Adds an annotation to the package declaration.
    ///
    /// @param annotation the annotation
    /// @return this builder
    /// @throws IllegalArgumentException if the annotation type cannot annotate a package
    /// @throws IllegalStateException if the declaration is complete
    public TypedPackageInfoBuilder withAnnotation(AnnotationRef<?> annotation) {
        if (complete) {
            throw new IllegalStateException("the declaration of package " + packageName + " is already complete");
        }
        if (!annotation.type().targets().isEmpty()
                && !annotation.type().targets().contains(ElementType.PACKAGE)) {
            throw new IllegalArgumentException(
                    "the annotation @" + annotation.type().token()
                            + " cannot annotate a package: its targets are "
                            + annotation.type().targets()
                            + " (JLS 9.6.4.1)");
        }
        target.verify(annotation.type().token());
        annotations.add(annotation);
        return this;
    }

    /// Refuses a member type of a `package-info.java`: the file declares the annotations of a package
    /// alone and no type (JLS 7.4.1).
    ///
    /// @param name the simple name of the member type
    /// @return never
    /// @throws IllegalArgumentException always
    // a refusal of what javac rejects: the tests call it to read the message, and it never returns
    @SuppressWarnings("DoNotCallSuggester")
    public java.lang.constant.ClassDesc nestedType(String name) {
        throw new IllegalArgumentException("cannot declare the member type " + name + " in the package-info.java"
                + " of package " + packageName + ": a package-info.java declares annotations alone (JLS 7.4.1)");
    }

    /// The declaration of the package is complete.
    ///
    /// @return the package-info file
    /// @throws IllegalStateException if the declaration is complete already
    PackageInfoFile complete() {
        if (complete) {
            throw new IllegalStateException("the declaration of package " + packageName + " is already complete");
        }
        complete = true;
        return PackageInfoFile.of(
                new PackageName.Named(packageName),
                annotations.stream()
                        .map(AnnotationRef::toUse)
                        .toArray(me.supcheg.javafile.annotation.AnnotationUse[]::new));
    }
}
