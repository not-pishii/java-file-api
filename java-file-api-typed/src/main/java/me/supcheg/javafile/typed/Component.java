package me.supcheg.javafile.typed;

import me.supcheg.javafile.Identifiers;

import java.lang.constant.ClassDesc;

/// A record component for [Unit#record(ClassDesc, Component...)].
///
/// @param name the component name
/// @param type the erased component type
public record Component(String name, ClassDesc type) {
    public Component {
        name = Identifiers.requireValid(name);
        if (type.descriptorString().equals("V")) {
            throw new IllegalArgumentException("component type must not be void: " + name);
        }
    }
}
