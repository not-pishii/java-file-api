/// Types used in generated code: `String`, `int[]`, `List<? extends T>`, and so on.
///
/// Create them with [me.supcheg.javafile.type.Types]. Classes are named with
/// `java.lang.constant.ClassDesc`, so you can refer to types that are not on
/// the classpath or do not exist yet (e.g. ones generated in the same round).
///
/// ```java
/// TypeRef listOfStrings = Types.parameterized(ClassDesc.of("java.util", "List"), Types.STRING);
/// ```
@NullMarked
package me.supcheg.javafile.type;

import org.jspecify.annotations.NullMarked;
