package me.supcheg.javafile.filer;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.RenderableFile;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import java.io.IOException;

/// Writes a [JavaFile], [PackageInfoFile], or [ModuleFile] from an annotation processor.
///
/// ```java
/// JavaFileWriter.writeTo(file, processingEnv.getFiler(), annotatedElement);
/// ```
public final class JavaFileWriter {

    private JavaFileWriter() {}

    /// Writes `file` through `filer`.
    ///
    /// Pass the elements the file was generated from, so that incremental
    /// builds (e.g. Gradle) regenerate it when they change.
    ///
    /// @param file the source file to write
    /// @param filer the filer to write through
    /// @param originatingElements the elements that caused this file to be generated
    /// @throws IOException if the filer cannot create or write the source file
    public static void writeTo(RenderableFile file, Filer filer, Element... originatingElements) throws IOException {
        var source = filer.createSourceFile(qualifiedName(file), originatingElements);
        try (var writer = source.openWriter()) {
            writer.write(file.render());
        }
    }

    private static String qualifiedName(RenderableFile file) {
        return switch (file) {
            case JavaFile java -> java.qualifiedName();
            case ModuleFile _ -> "module-info";
            case PackageInfoFile packageInfo -> packageInfo.packageName() + ".package-info";
        };
    }
}
