package me.supcheg.javafile.annotation;

/// A value assigned to an annotation member: a single value
/// ([SingleAnnotationValue]) or an array initializer ([ArrayValue]).
///
/// Java forbids nested annotation arrays, so array elements are typed as
/// [SingleAnnotationValue] — an array of arrays is unrepresentable.
///
/// Implementations are immutable values. Use [AnnotationValues] to construct
/// instances rather than the permitted implementations directly.
public sealed interface AnnotationValue permits SingleAnnotationValue, ArrayValue {}
