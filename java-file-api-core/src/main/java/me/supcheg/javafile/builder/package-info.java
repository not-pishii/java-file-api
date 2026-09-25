/// Builders that describe the contents of a declaration: members, modifiers,
/// supertypes, annotations.
///
/// You don't create the top-level builders yourself:
/// [me.supcheg.javafile.JavaFile]'s factories pass one to your lambda, and
/// its `with*` methods pass nested builders for methods, fields, constructors,
/// and so on. Builders are not thread-safe.
///
/// ```java
/// JavaFile.class_(ClassDesc.of("com.example", "Greeter"), cb -> cb
///         .withMethod("greet", Types.STRING, mb -> mb
///                 .withBody(b -> b.return_(literal("hi")))));
/// ```
@NullMarked
package me.supcheg.javafile.builder;

import org.jspecify.annotations.NullMarked;
