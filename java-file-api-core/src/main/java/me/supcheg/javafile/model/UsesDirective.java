package me.supcheg.javafile.model;

import java.lang.constant.ClassDesc;

/// A `uses` directive, e.g. `uses com.example.api.Plugin;`.
///
/// @param service the consumed service type
public record UsesDirective(ClassDesc service) implements ModuleDirective {}
