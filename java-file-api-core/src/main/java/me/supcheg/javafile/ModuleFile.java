package me.supcheg.javafile;

import me.supcheg.javafile.builder.ModuleBuilder;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.model.ModuleDirective;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A `module-info.java` file.
///
/// ```java
/// ModuleFile module = ModuleFile.of("com.example.app", mb -> mb
///         .withRequires("java.sql")
///         .withExports("com.example.api"));
/// ```
///
/// Types in `uses` and `provides`, and in the documentation comment of the
/// module, are always written fully qualified; no imports are generated.
public final class ModuleFile implements RenderableFile {
    private static final Path MODULE_INFO_JAVA = Path.of("module-info.java");

    private final String moduleName;
    private final boolean open;
    private final List<ModuleDirective> directives;
    private final Optional<DocComment> doc;

    private ModuleFile(String moduleName, boolean open, List<ModuleDirective> directives, Optional<DocComment> doc) {
        this.moduleName = moduleName;
        this.open = open;
        this.directives = directives;
        this.doc = doc;
    }

    /// Builds a `module-info.java` file declaring `moduleName`.
    ///
    /// @param moduleName the declared module's name, dot-separated
    /// @param spec receives the builder to populate the module's directives
    /// @return the finished file
    public static ModuleFile of(String moduleName, Consumer<? super ModuleBuilder> spec) {
        ModuleBuilder builder = new ModuleBuilder();
        spec.accept(builder);
        return new ModuleFile(moduleName, builder.isOpen(), builder.build(), builder.doc());
    }

    @Override
    public Meta renderMeta() {
        return new Meta(open, moduleName, directives, doc);
    }

    /// The declaration and directives of a [ModuleFile]; you rarely need it directly.
    ///
    /// @param open whether `open` is present on the module declaration
    /// @param moduleName the declared module's name, dot-separated
    /// @param directives the module's directives, in order
    /// @param doc the documentation comment of the module, if any
    public record Meta(boolean open, String moduleName, List<ModuleDirective> directives, Optional<DocComment> doc)
            implements RenderableFile.Meta {}

    @Override
    public Path pathSuffix() {
        return MODULE_INFO_JAVA;
    }
}
