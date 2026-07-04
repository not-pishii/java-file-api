/// Renders declarations from [me.supcheg.javafile.model] and
/// [me.supcheg.javafile.code] into Java source text.
///
/// [me.supcheg.javafile.render.SourceRenderer] is the only public entry
/// point: it renders a package declaration, computed imports, and a
/// top-level type declaration into one file's text. The remaining classes
/// are package-private renderer internals split by concern (type
/// references, expressions and statements, type declarations) plus an
/// import-claiming helper.
package me.supcheg.javafile.render;
