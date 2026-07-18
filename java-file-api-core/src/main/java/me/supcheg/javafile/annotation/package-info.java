/// Use-site annotation values attachable to declarations.
///
/// [me.supcheg.javafile.annotation.AnnotationUse] models `@Foo(...)`; its
/// members are [me.supcheg.javafile.annotation.AnnotationValue]s, split into
/// [me.supcheg.javafile.annotation.SingleAnnotationValue] (literal, class,
/// enum, or nested annotation) and
/// [me.supcheg.javafile.annotation.ArrayValue] — Java forbids nested arrays,
/// so an array's elements are typed as `SingleAnnotationValue`, making that
/// invariant unrepresentable rather than runtime-checked.
/// [me.supcheg.javafile.annotation.AnnotationValues] is the entry point for
/// building values; [me.supcheg.javafile.annotation.AnnotationBuilder]
/// assembles an [me.supcheg.javafile.annotation.AnnotationUse], including
/// standalone nested uses.
///
/// ```java
/// AnnotationUse use = new AnnotationBuilder(ClassDesc.of("me.supcheg.meta", "ContractMeta"))
///         .withMember("value", AnnotationValues.literal("greeting"))
///         .build();
/// ```
@NullMarked
package me.supcheg.javafile.annotation;

import org.jspecify.annotations.NullMarked;
