/// The one translator of `javax.lang.model` mirrors into the model of
/// generated code, shared by the `@Facts` processor, the check of metamodels
/// against the target classpath and the mirror source:
/// [me.supcheg.javafile.langmodel.mirror.MirrorTranslator] reads a type into
/// a [me.supcheg.javafile.langmodel.mirror.TypeModel], and
/// [me.supcheg.javafile.langmodel.mirror.Canonical] prints the model and
/// fingerprints it. Nothing here makes facts.
@NullMarked
package me.supcheg.javafile.langmodel.mirror;

import org.jspecify.annotations.NullMarked;
