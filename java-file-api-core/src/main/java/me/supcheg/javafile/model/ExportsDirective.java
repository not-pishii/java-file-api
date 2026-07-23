package me.supcheg.javafile.model;

import java.util.List;

/// An `exports` directive, e.g. `exports com.example.api to com.example.client;`.
///
/// @param packageName the exported package, dot-separated
/// @param to the modules the export is qualified to, or empty for an unqualified export; copied defensively
public record ExportsDirective(String packageName, List<String> to) implements ModuleDirective {
    public ExportsDirective {
        to = List.copyOf(to);
    }
}
