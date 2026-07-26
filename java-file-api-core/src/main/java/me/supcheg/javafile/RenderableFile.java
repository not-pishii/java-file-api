package me.supcheg.javafile;

import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.render.StandardRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/// A source file that can be rendered to Java source text and written to disk.
///
/// [JavaFile], [ModuleFile], and [PackageInfoFile] are the only kinds of
/// renderable file — a top-level type declaration, a `module-info.java`
/// module declaration, and a `package-info.java` package declaration,
/// respectively. Rendering is delegated to [StandardRenderer], which
/// dispatches on the concrete [Meta] returned by [#renderMeta()]; [#pathSuffix()]
/// determines where [#writeTo(Path)] places the rendered file relative to a
/// source root.
public sealed interface RenderableFile permits JavaFile, PackageInfoFile, ModuleFile {

    /// Renders this file's declaration to source text.
    ///
    /// @return the complete source text
    default String render() {
        return StandardRenderer.instance().render(renderMeta(), SourceRenderer.standardFormat());
    }

    /// Writes this file's rendered source text under `outputDir`, at the
    /// location given by [#pathSuffix()], creating any missing directories
    /// as needed.
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

    /// The type-specific data [StandardRenderer] needs to render this file.
    ///
    /// @return this file's render metadata
    Meta renderMeta();

    /// The type-specific data a [SourceRenderer] renders a [RenderableFile] from.
    sealed interface Meta permits JavaFile.Meta, ModuleFile.Meta, PackageInfoFile.Meta {}
}
