/// Annotations applied to generated code, e.g. `@Deprecated(since = "2.0")`.
///
/// Most of the time you add annotations through a declaration builder's
/// `withAnnotation` methods. Use [me.supcheg.javafile.annotation.AnnotationBuilder]
/// to build a standalone [me.supcheg.javafile.annotation.AnnotationUse], and
/// [me.supcheg.javafile.annotation.AnnotationValues] to create member values.
///
/// ```java
/// AnnotationUse use = new AnnotationBuilder(ClassDesc.of("com.example", "Route"))
///         .withMember("value", AnnotationValues.literal("/users"))
///         .build();                                  // @Route("/users")
/// ```
@NullMarked
package me.supcheg.javafile.annotation;

import org.jspecify.annotations.NullMarked;
