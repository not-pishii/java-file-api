/// Mutable builders that assemble the immutable declarations in [me.supcheg.javafile.model].
///
/// One builder exists per top-level declaration kind
/// ([me.supcheg.javafile.builder.ClassBuilder],
/// [me.supcheg.javafile.builder.InterfaceBuilder],
/// [me.supcheg.javafile.builder.RecordBuilder],
/// [me.supcheg.javafile.builder.EnumBuilder]), plus nested builders for
/// members that need their own configuration
/// ([me.supcheg.javafile.builder.MethodBuilder],
/// [me.supcheg.javafile.builder.FieldBuilder],
/// [me.supcheg.javafile.builder.ConstructorBuilder],
/// [me.supcheg.javafile.builder.EnumConstantBuilder]). Each top-level
/// builder implements the `Consumer` of its member type, so transforms in
/// [me.supcheg.javafile.transform] can feed pre-built members back through
/// [me.supcheg.javafile.builder.ClassBuilder#accept(me.supcheg.javafile.model.ClassMember)]
/// and its siblings. Builders are mutable and not thread-safe; the
/// declarations they produce are immutable.
///
/// [me.supcheg.javafile.JavaFile]'s static factories are the usual entry
/// point for creating a top-level builder.
package me.supcheg.javafile.builder;
