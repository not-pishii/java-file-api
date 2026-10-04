/// The one translator of `javax.lang.model` mirrors into the model of
/// generated code, shared by the `@Facts` processor, the check of metamodels
/// against the target classpath and the mirror source:
/// [me.supcheg.javafile.langmodel.mirror.MirrorTranslator] reads a type into
/// a [me.supcheg.javafile.langmodel.mirror.TypeModel], and
/// [me.supcheg.javafile.langmodel.mirror.Canonical] prints the model and
/// fingerprints it. [me.supcheg.javafile.langmodel.mirror.Conformance]
/// compares what a metamodel recorded with a model, and
/// [me.supcheg.javafile.langmodel.mirror.TargetClasspaths] is the target
/// classpath of a compilation, which does so for every metamodel the typed
/// layer meets. Nothing here makes facts.
@NullMarked
package me.supcheg.javafile.langmodel.mirror;

import org.jspecify.annotations.NullMarked;
