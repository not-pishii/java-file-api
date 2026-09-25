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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/// Adds directives to a `module-info.java`.
///
/// Obtained from [me.supcheg.javafile.ModuleFile#of(String,Consumer)].
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
    public ModuleBuilder withOpen() {
        this.open = true;
        return this;
    }

    /// Adds a plain `requires` directive.
    ///
    /// @param moduleName the required module's name
    /// @return this builder
    public ModuleBuilder withRequires(String moduleName) {
        directives.add(new RequiresDirective(moduleName, false, false));
        return this;
    }

    /// Adds a `requires transitive` directive.
    ///
    /// @param moduleName the required module's name
    /// @return this builder
    public ModuleBuilder withRequiresTransitive(String moduleName) {
        directives.add(new RequiresDirective(moduleName, true, false));
        return this;
    }

    /// Adds a `requires static` directive.
    ///
    /// @param moduleName the required module's name
    /// @return this builder
    public ModuleBuilder withRequiresStatic(String moduleName) {
        directives.add(new RequiresDirective(moduleName, false, true));
        return this;
    }

    /// Adds an unqualified `exports` directive.
    ///
    /// @param packageName the exported package
    /// @return this builder
    public ModuleBuilder withExports(String packageName) {
        directives.add(new ExportsDirective(packageName, List.of()));
        return this;
    }

    /// Adds a qualified `exports ... to ...` directive.
    ///
    /// @param packageName the exported package
    /// @param to the modules the export is qualified to
    /// @return this builder
    public ModuleBuilder withExportsTo(String packageName, String... to) {
        directives.add(new ExportsDirective(packageName, List.of(to)));
        return this;
    }

    /// Adds an unqualified `opens` directive.
    ///
    /// @param packageName the opened package
    /// @return this builder
    public ModuleBuilder withOpens(String packageName) {
        directives.add(new OpensDirective(packageName, List.of()));
        return this;
    }

    /// Adds a qualified `opens ... to ...` directive.
    ///
    /// @param packageName the opened package
    /// @param to the modules the opening is qualified to
    /// @return this builder
    public ModuleBuilder withOpensTo(String packageName, String... to) {
        directives.add(new OpensDirective(packageName, List.of(to)));
        return this;
    }

    /// Adds a `uses` directive.
    ///
    /// @param service the consumed service type's binary name (e.g. `"com.example.api.Plugin"`)
    /// @return this builder
    public ModuleBuilder withUses(String service) {
        directives.add(new UsesDirective(ClassDesc.of(service)));
        return this;
    }

    /// Adds a `uses` directive. Use this overload for a nested service type.
    ///
    /// @param service the consumed service type
    /// @return this builder
    public ModuleBuilder withUses(ClassDesc service) {
        directives.add(new UsesDirective(service));
        return this;
    }

    /// Adds a `provides ... with ...` directive.
    ///
    /// @param service the provided service type's binary name
    /// @param implementations the implementation types' binary names, in order; at least one
    /// @return this builder
    /// @throws IllegalArgumentException if `implementations` is empty
    public ModuleBuilder withProvides(String service, String... implementations) {
        List<ClassDesc> impls = new ArrayList<>(implementations.length);
        for (String impl : implementations) {
            impls.add(ClassDesc.of(impl));
        }
        directives.add(new ProvidesDirective(ClassDesc.of(service), NonEmptyList.copyOf(impls)));
        return this;
    }

    /// Adds a `provides ... with ...` directive. Use this overload when a
    /// service or implementation is a nested type.
    ///
    /// @param service the provided service type
    /// @param implementations the implementation types, in order; at least one
    /// @return this builder
    /// @throws IllegalArgumentException if `implementations` is empty
    public ModuleBuilder withProvides(ClassDesc service, ClassDesc... implementations) {
        directives.add(new ProvidesDirective(service, NonEmptyList.copyOf(List.of(implementations))));
        return this;
    }

    /// Whether the module is declared `open`.
    ///
    /// @return `true` if the module should render as `open`
    public boolean isOpen() {
        return open;
    }

    /// Returns the directives added so far.
    ///
    /// @return the finished directive list
    /// @throws IllegalArgumentException if an `open` module has `opens`
    ///         directives, or the same module is required twice
    public List<ModuleDirective> build() {
        requireNoOpensInOpenModule();
        requireNoDuplicateRequires();
        return List.copyOf(directives);
    }

    private void requireNoOpensInOpenModule() {
        if (!open) {
            return;
        }
        for (ModuleDirective directive : directives) {
            if (directive instanceof OpensDirective) {
                throw new IllegalArgumentException("an open module cannot declare explicit 'opens' directives");
            }
        }
    }

    private void requireNoDuplicateRequires() {
        Set<String> seen = new HashSet<>();
        for (ModuleDirective directive : directives) {
            if (directive instanceof RequiresDirective requires && !seen.add(requires.moduleName())) {
                throw new IllegalArgumentException(
                        "duplicate 'requires' directive for module: " + requires.moduleName());
            }
        }
    }
}
