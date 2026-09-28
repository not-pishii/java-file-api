/// The metagen skeleton (§5): reading class files with the ClassFile API.
///
/// Metagen will turn an opt-in set of classes into metamodel sources
/// (`List_`, `ObjectMapper_`, ...) written with `java-file-api-core`; that
/// generation and the Gradle freshness plugin are the next phase. For now the
/// module holds the input side — [me.supcheg.javafile.metagen.ClassSource]
/// and the internal class-file index with its "found similar" diagnostics.
@NullMarked
package me.supcheg.javafile.metagen;

import org.jspecify.annotations.NullMarked;
