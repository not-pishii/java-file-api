package me.supcheg.javafile.typed;

import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.builder.ModuleBuilder;

import java.lang.constant.ClassDesc;

/// Declares a `module-info.java` (§6.5): its `requires`, `exports`, `opens`,
/// `uses` and `provides` directives. A module and a package have no fact: they
/// are named by the strings the core takes. A service and an implementation
/// are types, named by their `ClassDesc`.
///
/// The core can express every directive of JLS 7.7: nothing of the module
/// declaration is missing from the typed layer.
public final class TypedModuleBuilder {
    private final String moduleName;
    private final ModuleBuilder builder;
    private boolean complete;

    TypedModuleBuilder(String moduleName) {
        this.moduleName = moduleName;
        this.builder = new ModuleBuilder();
    }

    /// Marks the module `open`.
    ///
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder open() {
        requireDeclaring("open");
        builder.withOpen();
        return this;
    }

    /// Adds a `requires` directive.
    ///
    /// @param module the required module's name
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder requires(String module) {
        requireDeclaring("requires " + module);
        builder.withRequires(module);
        return this;
    }

    /// Adds a `requires transitive` directive.
    ///
    /// @param module the required module's name
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder requiresTransitive(String module) {
        requireDeclaring("requires transitive " + module);
        builder.withRequiresTransitive(module);
        return this;
    }

    /// Adds a `requires static` directive.
    ///
    /// @param module the required module's name
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder requiresStatic(String module) {
        requireDeclaring("requires static " + module);
        builder.withRequiresStatic(module);
        return this;
    }

    /// Adds an unqualified `exports` directive.
    ///
    /// @param packageName the exported package
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder exports(String packageName) {
        requireDeclaring("exports " + packageName);
        builder.withExports(packageName);
        return this;
    }

    /// Adds a qualified `exports ... to ...` directive.
    ///
    /// @param packageName the exported package
    /// @param modules the modules the export is qualified to
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder exportsTo(String packageName, String... modules) {
        requireDeclaring("exports " + packageName + " to " + String.join(", ", modules));
        builder.withExportsTo(packageName, modules);
        return this;
    }

    /// Adds an unqualified `opens` directive.
    ///
    /// @param packageName the opened package
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder opens(String packageName) {
        requireDeclaring("opens " + packageName);
        builder.withOpens(packageName);
        return this;
    }

    /// Adds a qualified `opens ... to ...` directive.
    ///
    /// @param packageName the opened package
    /// @param modules the modules the opening is qualified to
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder opensTo(String packageName, String... modules) {
        requireDeclaring("opens " + packageName + " to " + String.join(", ", modules));
        builder.withOpensTo(packageName, modules);
        return this;
    }

    /// Adds a `uses` directive.
    ///
    /// @param service the consumed service type
    /// @return this builder
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder uses(ClassDesc service) {
        requireDeclaring("uses " + service.displayName());
        builder.withUses(service);
        return this;
    }

    /// Adds a `provides ... with ...` directive.
    ///
    /// @param service the provided service type
    /// @param implementations the implementation types, in order; at least one
    /// @return this builder
    /// @throws IllegalArgumentException if `implementations` is empty
    /// @throws IllegalStateException if the declaration is complete
    public TypedModuleBuilder provides(ClassDesc service, ClassDesc... implementations) {
        requireDeclaring("provides " + service.displayName());
        builder.withProvides(service, implementations);
        return this;
    }

    private void requireDeclaring(String what) {
        if (complete) {
            throw new IllegalStateException(
                    "cannot declare " + what + ": the declaration of module " + moduleName + " is already complete");
        }
    }

    /// Refuses a member type of a `module-info.java`: the file declares the directives of a module
    /// alone and no type (JLS 7.7).
    ///
    /// @param name the simple name of the member type
    /// @return never
    /// @throws IllegalArgumentException always
    public ClassDesc nestedType(String name) {
        throw new IllegalArgumentException("cannot declare the member type " + name + " in the module-info.java"
                + " of module " + moduleName + ": a module-info.java declares directives alone (JLS 7.7)");
    }

    /// The declaration of the module is complete.
    ///
    /// @return the module-info file
    /// @throws IllegalStateException if the declaration is complete already
    ModuleFile complete() {
        if (complete) {
            throw new IllegalStateException("the declaration of module " + moduleName + " is already complete");
        }
        complete = true;
        return ModuleFile.of(moduleName, builder);
    }
}
