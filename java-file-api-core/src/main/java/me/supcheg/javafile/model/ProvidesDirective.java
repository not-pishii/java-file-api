package me.supcheg.javafile.model;

import me.supcheg.javafile.code.NonEmptyList;

import java.lang.constant.ClassDesc;

/// A `provides` directive, e.g. `provides com.example.api.Plugin with com.example.impl.DefaultPlugin;`.
///
/// @param service the provided service type
/// @param implementations the implementation types, in order; at least one, as JLS requires
public record ProvidesDirective(ClassDesc service, NonEmptyList<ClassDesc> implementations)
        implements ModuleDirective {}
