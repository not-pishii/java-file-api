package me.supcheg.javafile;

import me.supcheg.javafile.builder.ModuleBuilder;
import me.supcheg.javafile.model.ModuleDirective;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

/// A `module-info.java` source file: a module declaration and its directives,
/// with no package or type declaration.
///
/// Unlike [JavaFile] and [PackageInfoFile], this type never resolves types
/// through [me.supcheg.javafile.render.ImportManager]: `uses`/`provides`
/// directives render their service and implementation types as fully
/// qualified binary names directly, matching how real-world
/// `module-info.java` files are written (imports are almost never used
/// there).
public final class ModuleFile implements RenderableFile {
    private static final Path MODULE_INFO_JAVA = Path.of("module-info.java");

    private final String moduleName;
    private final boolean open;
    private final List<ModuleDirective> directives;

    private ModuleFile(String moduleName, boolean open, List<ModuleDirective> directives) {
        this.moduleName = moduleName;
        this.open = open;
        this.directives = directives;
    }

    /// Builds a `module-info.java` file declaring `moduleName`.
    ///
    /// @param moduleName the declared module's name, dot-separated
    /// @param spec receives the builder to populate the module's directives
    /// @return the finished file
    public static ModuleFile of(String moduleName, Consumer<ModuleBuilder> spec) {
        ModuleBuilder builder = new ModuleBuilder();
        spec.accept(builder);
        return new ModuleFile(moduleName, builder.isOpen(), builder.build());
    }

    @Override
    public Meta renderMeta() {
        return new Meta(open, moduleName, directives);
    }

    /// The render metadata for a [ModuleFile]: its module declaration and directives.
    ///
    /// @param open whether `open` is present on the module declaration
    /// @param moduleName the declared module's name, dot-separated
    /// @param directives the module's directives, in order
    public record Meta(boolean open, String moduleName, List<ModuleDirective> directives)
            implements RenderableFile.Meta {}

    @Override
    public Path pathSuffix() {
        return MODULE_INFO_JAVA;
    }
}
