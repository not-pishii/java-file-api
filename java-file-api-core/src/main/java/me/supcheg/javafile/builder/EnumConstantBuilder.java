package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocCommentBuilder;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.type.TypeRef;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// Configures an enum constant: its constructor arguments and an optional
/// body, e.g. `PLUS("+") { ... }`.
///
/// Obtained from [EnumBuilder#withConstant(String,Consumer)].
///
/// Instances are not thread-safe.
public final class EnumConstantBuilder {

    private @Nullable DocComment doc;
    private final List<AnnotationUse> annotations = new ArrayList<>();
    private final List<Expr> args = new ArrayList<>();
    private final List<EnumConstantMember> body = new ArrayList<>();

    EnumConstantBuilder() {}

    /// Adds a marker annotation, e.g. `@Deprecated`.
    ///
    /// @param type the annotation type
    /// @return this builder
    public EnumConstantBuilder withAnnotation(ClassDesc type) {
        annotations.add(new AnnotationUse(type, List.of()));
        return this;
    }

    /// Adds an annotation, populated via an [AnnotationBuilder].
    ///
    /// @param type the annotation type
    /// @param spec receives the builder to populate the annotation's members
    /// @return this builder
    public EnumConstantBuilder withAnnotation(ClassDesc type, Consumer<? super AnnotationBuilder> spec) {
        AnnotationBuilder ab = new AnnotationBuilder(type);
        spec.accept(ab);
        annotations.add(ab.build());
        return this;
    }

    /// Adds a pre-built annotation.
    ///
    /// @param annotation the annotation to add
    /// @return this builder
    public EnumConstantBuilder withAnnotation(AnnotationUse annotation) {
        annotations.add(annotation);
        return this;
    }

    /// Sets the documentation comment.
    ///
    /// @param doc the comment
    /// @return this builder
    public EnumConstantBuilder withDoc(DocComment doc) {
        this.doc = doc;
        return this;
    }

    /// Sets the documentation comment, populated via a [DocCommentBuilder].
    ///
    /// @param spec receives the builder to populate the comment
    /// @return this builder
    /// @throws IllegalArgumentException if `spec` adds neither a description nor a tag
    public EnumConstantBuilder withDoc(Consumer<? super DocCommentBuilder> spec) {
        return withDoc(DocComment.of(spec));
    }

    /// Sets the arguments passed to the enum's constructor for this constant.
    ///
    /// @param args the constructor arguments, in order
    /// @return this builder
    public EnumConstantBuilder withArgs(Expr... args) {
        this.args.addAll(List.of(args));
        return this;
    }

    /// Sets the arguments passed to the enum's constructor for this constant.
    ///
    /// @param args the constructor arguments, in order
    /// @return this builder
    public EnumConstantBuilder withArgs(List<? extends Expr> args) {
        this.args.addAll(args);
        return this;
    }

    /// Adds a method to this constant's constant-specific class body.
    ///
    /// @param name the method name
    /// @param returnType the method's return type
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public EnumConstantBuilder withMethod(String name, TypeRef returnType, Consumer<? super MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.of(returnType));
        spec.accept(mb);
        body.add(mb.build());
        return this;
    }

    /// Adds a `void` method to this constant's constant-specific class body.
    ///
    /// @param name the method name
    /// @param spec receives the builder to populate the method
    /// @return this builder
    public EnumConstantBuilder withVoidMethod(String name, Consumer<? super MethodBuilder> spec) {
        MethodBuilder mb = new MethodBuilder(name, Optional.empty());
        spec.accept(mb);
        body.add(mb.build());
        return this;
    }

    EnumConstant build(String name) {
        return new EnumConstant(
                name, List.copyOf(annotations), List.copyOf(args), List.copyOf(body), Optional.ofNullable(doc));
    }
}
