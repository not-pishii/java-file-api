package me.supcheg.javafile.model;

import java.util.List;

/// An `opens` directive, e.g. `opens com.example.internal to com.example.framework;`.
///
/// @param packageName the opened package, dot-separated
/// @param to the modules the opening is qualified to, or empty for an unqualified opening; copied defensively
public record OpensDirective(String packageName, List<String> to) implements ModuleDirective {
    public OpensDirective {
        to = List.copyOf(to);
    }
}
