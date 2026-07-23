package me.supcheg.javafile.model;

/// A single directive inside a `module-info.java` declaration.
public sealed interface ModuleDirective
        permits RequiresDirective, ExportsDirective, OpensDirective, UsesDirective, ProvidesDirective {}
