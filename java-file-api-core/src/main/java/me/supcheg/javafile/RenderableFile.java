package me.supcheg.javafile;

import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.render.StandardRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/// A generated source file: a [JavaFile], [PackageInfoFile], or [ModuleFile].
///
/// Get the source text with [#render()], or write it into a source directory
/// with [#writeTo(Path)]. In an annotation processor, use `JavaFileWriter`
/// from `java-file-api-lang-model` to write through the `Filer`.
public sealed interface RenderableFile permits JavaFile, PackageInfoFile, ModuleFile {

    /// Returns the file's source code.
    ///
    /// @return the complete source text, including the package and imports
    default String render() {
        return StandardRenderer.instance().render(renderMeta(), SourceRenderer.standardFormat());
    }

    /// Writes the file into the source directory `outputDir`, e.g.
    /// `outputDir/com/example/Greeter.java`, creating directories as needed.
    /// An existing file is overwritten.
    ///
    /// @param outputDir the source root to write into
    /// @throws IOException if the directories or file cannot be created or written
    default void writeTo(Path outputDir) throws IOException {
        var out = outputDir.resolve(pathSuffix()).toAbsolutePath();
        Files.createDirectories(out.getParent());
        Files.writeString(out, render());
    }

    /// This file's location relative to a source root, e.g.
    /// `me/supcheg/example/Messages.java` or `module-info.java`.
    ///
    /// @return the path this file is written to, relative to the source root
    Path pathSuffix();

    /// The file's contents in the form renderers consume; you rarely need it directly.
    ///
    /// @return this file's render metadata
    Meta renderMeta();

    /// The contents of a [RenderableFile], as passed to a [SourceRenderer].
    sealed interface Meta permits JavaFile.Meta, ModuleFile.Meta, PackageInfoFile.Meta {}
}
