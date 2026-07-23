package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.model.ExportsDirective;
import me.supcheg.javafile.model.ModuleDirective;
import me.supcheg.javafile.model.OpensDirective;
import me.supcheg.javafile.model.ProvidesDirective;
import me.supcheg.javafile.model.RequiresDirective;
import me.supcheg.javafile.model.UsesDirective;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

/// A mutable builder for a `module-info.java` declaration's directives.
///
/// Instances are not thread-safe.
public final class ModuleBuilder {

    private final List<ModuleDirective> directives = new ArrayList<>();
    private boolean open;

    /// Creates an empty module builder.
    public ModuleBuilder() {}

    /// Marks the module as `open`.
    ///
    /// @return this builder
    public ModuleBuilder open() {
        this.open = true;
        return this;
    }

    /// Adds a plain `requires` directive.
    ///
    /// @param moduleName the required module's name
    /// @return this builder
    public ModuleBuilder requires(String moduleName) {
        directives.add(new RequiresDirective(moduleName, false, false));
        return this;
    }

    /// Adds a `requires transitive` directive.
    ///
    /// @param moduleName the required module's name
    /// @return this builder
    public ModuleBuilder requiresTransitive(String moduleName) {
        directives.add(new RequiresDirective(moduleName, true, false));
        return this;
    }

    /// Adds a `requires static` directive.
    ///
    /// @param moduleName the required module's name
    /// @return this builder
    public ModuleBuilder requiresStatic(String moduleName) {
        directives.add(new RequiresDirective(moduleName, false, true));
        return this;
    }

    /// Adds an unqualified `exports` directive.
    ///
    /// @param packageName the exported package
    /// @return this builder
    public ModuleBuilder exports(String packageName) {
        directives.add(new ExportsDirective(packageName, List.of()));
        return this;
    }

    /// Adds a qualified `exports ... to ...` directive.
    ///
    /// @param packageName the exported package
    /// @param to the modules the export is qualified to
    /// @return this builder
    public ModuleBuilder exportsTo(String packageName, String... to) {
        directives.add(new ExportsDirective(packageName, List.of(to)));
        return this;
    }

    /// Adds an unqualified `opens` directive.
    ///
    /// @param packageName the opened package
    /// @return this builder
    public ModuleBuilder opens(String packageName) {
        directives.add(new OpensDirective(packageName, List.of()));
        return this;
    }

    /// Adds a qualified `opens ... to ...` directive.
    ///
    /// @param packageName the opened package
    /// @param to the modules the opening is qualified to
    /// @return this builder
    public ModuleBuilder opensTo(String packageName, String... to) {
        directives.add(new OpensDirective(packageName, List.of(to)));
        return this;
    }

    /// Adds a `uses` directive.
    ///
    /// @param service the consumed service type's binary name (e.g. `"com.example.api.Plugin"`)
    /// @return this builder
    public ModuleBuilder uses(String service) {
        directives.add(new UsesDirective(ClassDesc.of(service)));
        return this;
    }

    /// Adds a `provides ... with ...` directive.
    ///
    /// @param service the provided service type's binary name
    /// @param implementations the implementation types' binary names, in order; at least one
    /// @return this builder
    /// @throws IllegalArgumentException if `implementations` is empty
    public ModuleBuilder provides(String service, String... implementations) {
        List<ClassDesc> impls = new ArrayList<>(implementations.length);
        for (String impl : implementations) {
            impls.add(ClassDesc.of(impl));
        }
        directives.add(new ProvidesDirective(ClassDesc.of(service), NonEmptyList.copyOf(impls)));
        return this;
    }

    /// Whether [#open()] was called.
    ///
    /// @return `true` if the module should render as `open`
    public boolean isOpen() {
        return open;
    }

    /// Snapshots the accumulated directives.
    ///
    /// @return the finished directive list
    public List<ModuleDirective> build() {
        return List.copyOf(directives);
    }
}
