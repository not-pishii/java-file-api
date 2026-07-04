/// Writes [me.supcheg.javafile.JavaFile]s through an annotation processing `Filer`.
///
/// [me.supcheg.javafile.filer.JavaFileWriter] is the entry point: it renders
/// a file and writes it via `javax.annotation.processing.Filer`, passing
/// originating elements through so the annotation processing environment can
/// track the generated file for incremental compilation.
package me.supcheg.javafile.filer;
