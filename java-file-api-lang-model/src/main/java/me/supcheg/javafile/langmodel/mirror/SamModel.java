package me.supcheg.javafile.langmodel.mirror;

/// The single abstract method of a functional interface, see
/// [MirrorTranslator#sam(javax.lang.model.element.TypeElement)].
///
/// @param method the method as a member of the interface; not `static`, and
/// [me.supcheg.javafile.facts.Overridability#ABSTRACT]
/// @param declared whether the interface itself declares the method rather than inherits it, in which
///     case [TypeModel#members()] holds it too, unless it is skipped
public record SamModel(MethodModel method, boolean declared) {}
