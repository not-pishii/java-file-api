package me.supcheg.javafile.model;

import me.supcheg.javafile.PackageName;

import java.util.List;

/// An `opens` directive, e.g. `opens com.example.internal to com.example.framework;`.
///
/// @param packageName the opened package, dot-separated
/// @param to the modules the opening is qualified to, or empty for an unqualified opening
public record OpensDirective(PackageName.Named packageName, List<String> to) implements ModuleDirective {
    public OpensDirective {
        to = List.copyOf(to);
    }
}
