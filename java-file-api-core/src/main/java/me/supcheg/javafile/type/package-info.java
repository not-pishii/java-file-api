/// References to Java types, independent of loaded classes.
///
/// A [me.supcheg.javafile.type.TypeRef] identifies a type by name via
/// `java.lang.constant.ClassDesc` rather than `java.lang.Class`, so generated
/// code may reference types that do not exist yet.
/// [me.supcheg.javafile.type.Types] is the entry point: factories for class,
/// array, parameterized, and primitive references, plus wildcard type
/// arguments.
///
/// ```java
/// TypeRef listOfStrings = Types.parameterized(
///         ClassDesc.of("java.util", "List"),
///         Types.exact(Types.of(ClassDesc.of("java.lang", "String"))));
/// ```
@NullMarked
package me.supcheg.javafile.type;

import org.jspecify.annotations.NullMarked;
