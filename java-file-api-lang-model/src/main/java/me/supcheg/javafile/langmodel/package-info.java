/// Using types from an annotation processor in generated code.
///
/// [me.supcheg.javafile.langmodel.Descriptors] converts `TypeMirror` and
/// `TypeElement` into [me.supcheg.javafile.type.TypeRef] and
/// `java.lang.constant.ClassDesc`. Nested types and type variables are not
/// supported and throw an exception.
@NullMarked
package me.supcheg.javafile.langmodel;

import org.jspecify.annotations.NullMarked;
