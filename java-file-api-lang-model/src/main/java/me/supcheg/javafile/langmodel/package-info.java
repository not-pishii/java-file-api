/// A one-way bridge from `javax.lang.model` types to this library's type model.
///
/// [me.supcheg.javafile.langmodel.Descriptors] is the entry point: it
/// converts `TypeMirror`/`TypeElement` values, as seen by an annotation
/// processor, into [me.supcheg.javafile.type.TypeRef] and
/// `java.lang.constant.ClassDesc`. The bridge only covers top-level types
/// and the type mirror kinds this library can represent; unsupported input
/// is rejected rather than approximated.
package me.supcheg.javafile.langmodel;
