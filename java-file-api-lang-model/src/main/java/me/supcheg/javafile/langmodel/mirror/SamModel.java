package me.supcheg.javafile.langmodel.mirror;

/// The single abstract method of a functional interface, see
/// [MirrorTranslator#sam(javax.lang.model.element.TypeElement)].
///
/// @param method the method as a member of the interface; not `static`, and
/// [me.supcheg.javafile.facts.Overridability#ABSTRACT]
/// @param declared whether the method is among the members the interface has facts of
///     ([MirrorTranslator#members(javax.lang.model.element.TypeElement)]) — it declares the method, or
///     adopts it from a superinterface that is not `public` — rather than inherits it from an interface
///     that has a metamodel of its own; [TypeModel#members()] holds it too then, unless it is skipped
public record SamModel(MethodModel method, boolean declared) {}
