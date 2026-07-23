package me.supcheg.javafile.model;

/// A `requires` directive, e.g. `requires transitive java.sql;`.
///
/// @param moduleName the required module's name, dot-separated (e.g. `"java.sql"`)
/// @param isTransitive whether `transitive` is present
/// @param isStatic whether `static` (compile-time only) is present
public record RequiresDirective(String moduleName, boolean isTransitive, boolean isStatic) implements ModuleDirective {}
