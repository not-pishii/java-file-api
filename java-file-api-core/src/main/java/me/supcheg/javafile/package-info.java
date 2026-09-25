/// Generates Java source files.
///
/// Start with [me.supcheg.javafile.JavaFile] for a class, interface, record,
/// enum, or annotation type; [me.supcheg.javafile.PackageInfoFile] and
/// [me.supcheg.javafile.ModuleFile] cover `package-info.java` and
/// `module-info.java`. Get the result with `render()` or `writeTo(Path)`.
///
/// ```java
/// import static me.supcheg.javafile.code.Exprs.literal;
///
/// JavaFile file = JavaFile.class_(ClassDesc.of("com.example", "Greeter"),
///         cb -> cb.withMethod("greet", Types.STRING,
///                 mb -> mb.withBody(b -> b.return_(literal("hi")))));
/// file.writeTo(Path.of("build/generated"));   // build/generated/com/example/Greeter.java
/// ```
@NullMarked
package me.supcheg.javafile;

import org.jspecify.annotations.NullMarked;
