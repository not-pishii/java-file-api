/// A programmatic Java source file builder using JEP 467 markdown javadoc.
///
/// [me.supcheg.javafile.JavaFile] is the entry point: its static factories
/// build a source file containing a single top-level class, interface,
/// record, or enum declaration, using the builders in
/// [me.supcheg.javafile.builder]. A finished file can be rendered to text,
/// written to disk, or rebuilt member-by-member with a transform from
/// [me.supcheg.javafile.transform].
///
/// ```java
/// JavaFile file = JavaFile.of(ClassDesc.of("com.example", "Greeter"),
///         cb -> cb.withMethod("greet", Types.of(STRING),
///                 mb -> mb.withBody(b -> b.return_(b.literal("hi")))));
/// file.writeTo(Path.of("build/generated"));
/// ```
package me.supcheg.javafile;
