package me.supcheg.javafile;

import me.supcheg.javafile.builder.ModuleBuilder;
import me.supcheg.javafile.model.ExportsDirective;
import me.supcheg.javafile.model.ModuleDirective;
import me.supcheg.javafile.model.OpensDirective;
import me.supcheg.javafile.model.ProvidesDirective;
import me.supcheg.javafile.model.RequiresDirective;
import me.supcheg.javafile.model.UsesDirective;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/// A `module-info.java` source file: a module declaration and its directives,
/// with no package or type declaration.
///
/// Unlike [JavaFile] and [PackageInfoFile], this type never resolves types
/// through [me.supcheg.javafile.render.ImportManager]: `uses`/`provides`
/// directives render their service and implementation types as fully
/// qualified binary names directly, matching how real-world
/// `module-info.java` files are written (imports are almost never used
/// there).
public final class ModuleFile {

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

    /// Renders this file's module declaration to source text.
    ///
    /// @return the complete source text
    public String render() {
        StringBuilder sb = new StringBuilder();
        if (open) {
            sb.append("open ");
        }
        sb.append("module ").append(moduleName).append(" {\n");
        for (ModuleDirective directive : directives) {
            sb.append("    ").append(renderDirective(directive)).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    private static String renderDirective(ModuleDirective directive) {
        return switch (directive) {
            case RequiresDirective(var name, var transitive, var isStatic) ->
                "requires " + (transitive ? "transitive " : "") + (isStatic ? "static " : "") + name + ";";
            case ExportsDirective(var packageName, var to) ->
                "exports " + packageName + (to.isEmpty() ? "" : " to " + String.join(", ", to)) + ";";
            case OpensDirective(var packageName, var to) ->
                "opens " + packageName + (to.isEmpty() ? "" : " to " + String.join(", ", to)) + ";";
            case UsesDirective(var service) -> "uses " + binaryName(service) + ";";
            case ProvidesDirective(var service, var implementations) ->
                "provides "
                        + binaryName(service)
                        + " with "
                        + implementations.toList().stream()
                                .map(ModuleFile::binaryName)
                                .collect(Collectors.joining(", "))
                        + ";";
        };
    }

    private static String binaryName(ClassDesc desc) {
        return desc.packageName().isEmpty() ? desc.displayName() : desc.packageName() + "." + desc.displayName();
    }

    /// Writes this file's rendered source text as `module-info.java` directly
    /// under `outputDir` — a module descriptor lives at a module's source
    /// root, not inside a package directory.
    ///
    /// @param outputDir the source root to write into
    /// @throws IOException if the file cannot be written
    public void writeTo(Path outputDir) throws IOException {
        Files.writeString(outputDir.resolve("module-info.java"), render());
    }
}
