/// The annotations and constants that connect the `@Facts` processor (§5) to
/// the metamodels it generates.
///
/// - [me.supcheg.javafile.facts.meta.Facts] asks for the metamodels of types.
/// - [me.supcheg.javafile.facts.meta.GeneratedMetamodel] marks a generated
///   metamodel and records what it was generated from.
/// - [me.supcheg.javafile.facts.meta.GeneratedMetamodelPart] marks the classes
///   nested in a generated metamodel.
/// - [me.supcheg.javafile.facts.meta.MetamodelFormat] versions the shape of
///   the generated code.
///
/// The processor reads these annotations through `javax.lang.model`, so it
/// needs no class of this package at run time.
@NullMarked
package me.supcheg.javafile.facts.meta;

import org.jspecify.annotations.NullMarked;
