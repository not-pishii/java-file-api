package me.supcheg.javafile.render;

import java.lang.constant.ClassDesc;

/// Resolves a [ClassDesc] to the name it should be rendered as: a simple name
/// when it can be imported or is already in scope, a fully qualified name
/// otherwise. [ImportManager] is the only implementation.
interface TypeContext {
    String reference(ClassDesc desc);
}
