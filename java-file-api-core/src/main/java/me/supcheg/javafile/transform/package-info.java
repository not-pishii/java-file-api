/// Member-level transforms for rebuilding declarations and code bodies.
///
/// A transform ([me.supcheg.javafile.transform.ClassTransform],
/// [me.supcheg.javafile.transform.EnumTransform],
/// [me.supcheg.javafile.transform.InterfaceTransform],
/// [me.supcheg.javafile.transform.RecordTransform],
/// [me.supcheg.javafile.transform.CodeTransform]) is invoked once per
/// existing member or statement and decides whether to pass it through
/// unchanged, replace it, drop it, or add new members, by calling (or not
/// calling) `accept` on the builder it is given.
/// [me.supcheg.javafile.transform.Transforms] applies a transform to a
/// source declaration or body and returns a new one; the source is never
/// modified. [me.supcheg.javafile.JavaFile#transformClass(ClassTransform)]
/// and its siblings are the usual entry point.
package me.supcheg.javafile.transform;
