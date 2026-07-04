package me.supcheg.javafile.filer;

import me.supcheg.javafile.JavaFile;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;

/// Writes a [JavaFile] through an annotation processing [Filer].
public final class JavaFileWriter {

    private JavaFileWriter() {}

    /// Renders `file` and writes it through `filer`, passing `originatingElements`
    /// so the annotation processing environment can track this generated file for
    /// incremental compilation.
    ///
    /// @param file the source file to write
    /// @param filer the filer to write through
    /// @param originatingElements the elements that caused this file to be generated
    /// @throws IOException if the filer cannot create or write the source file
    public static void writeTo(JavaFile file, Filer filer, Element... originatingElements) throws IOException {
        JavaFileObject source = filer.createSourceFile(file.qualifiedName(), originatingElements);
        try (Writer writer = source.openWriter()) {
            writer.write(file.render());
        }
    }
}
