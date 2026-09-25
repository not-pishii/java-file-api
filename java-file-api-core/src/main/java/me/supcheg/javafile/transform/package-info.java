/// Changing an existing declaration: keep, replace, drop, or add members.
///
/// A transform is called once per member (or statement) and puts what should
/// remain into the builder it receives. The usual way to apply one is
/// [me.supcheg.javafile.JavaFile#transformClass(ClassTransform)] and its
/// siblings; [me.supcheg.javafile.transform.Transforms] works on bare
/// declarations and code bodies. The original is never modified.
///
/// ```java
/// JavaFile cleaned = file.transformClass((builder, member) -> {
///     if (!(member instanceof MethodDecl m && m.name().startsWith("debug"))) {
///         builder.accept(member);
///     }
/// });
/// ```
@NullMarked
package me.supcheg.javafile.transform;

import org.jspecify.annotations.NullMarked;
