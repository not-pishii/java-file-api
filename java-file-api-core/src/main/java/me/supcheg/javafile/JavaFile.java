package me.supcheg.javafile;

import me.supcheg.javafile.builder.AnnotationTypeBuilder;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.transform.ClassTransform;
import me.supcheg.javafile.transform.EnumTransform;
import me.supcheg.javafile.transform.InterfaceTransform;
import me.supcheg.javafile.transform.RecordTransform;
import me.supcheg.javafile.transform.Transforms;

import java.lang.constant.ClassDesc;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

/// A source file containing a single top-level type declaration.
///
/// A `JavaFile` wraps exactly one of [ClassDecl], [InterfaceDecl],
/// [RecordDecl], [EnumDecl], or [me.supcheg.javafile.model.AnnotationTypeDecl],
/// created via the matching static factory ([#of(ClassDesc,Consumer)],
/// [#interface_(ClassDesc,Consumer)], [#record(ClassDesc,Consumer)],
/// [#enum_(ClassDesc,Consumer)], [#annotationType(ClassDesc,Consumer)]). The
/// `transform*` methods only accept a transform matching the wrapped kind;
/// calling the wrong one throws.
///
/// Instances are immutable; every method that produces a modified file
/// returns a new instance.
public final class JavaFile implements RenderableFile {

    private final String packageName;
    private final String simpleName;
    private final TypeDecl typeDecl;

    private JavaFile(String packageName, String simpleName, TypeDecl typeDecl) {
        this.packageName = packageName;
        this.simpleName = simpleName;
        this.typeDecl = typeDecl;
    }

    /// Builds a source file containing a single top-level class declaration.
    ///
    /// The builder passed to `spec` starts with the `public` modifier already set;
    /// [ClassBuilder#withModifiers(Modifier...)] adds to that set.
    ///
    /// @param desc the class to declare; its package and simple name determine the file location
    /// @param spec receives the builder to populate the class declaration
    /// @return the finished source file
    public static JavaFile of(ClassDesc desc, Consumer<ClassBuilder> spec) {
        ClassBuilder builder = new ClassBuilder(desc);
        spec.accept(builder);
        return new JavaFile(desc.packageName(), desc.displayName(), builder.build());
    }

    /// Builds a source file containing a single top-level interface declaration.
    ///
    /// @param desc the interface to declare; its package and simple name determine the file location
    /// @param spec receives the builder to populate the interface declaration
    /// @return the finished source file
    public static JavaFile interface_(ClassDesc desc, Consumer<InterfaceBuilder> spec) {
        InterfaceBuilder builder = new InterfaceBuilder(desc);
        spec.accept(builder);
        return new JavaFile(desc.packageName(), desc.displayName(), builder.build());
    }

    /// Builds a source file containing a single top-level record declaration.
    ///
    /// @param desc the record to declare; its package and simple name determine the file location
    /// @param spec receives the builder to populate the record declaration
    /// @return the finished source file
    public static JavaFile record(ClassDesc desc, Consumer<RecordBuilder> spec) {
        RecordBuilder builder = new RecordBuilder(desc);
        spec.accept(builder);
        return new JavaFile(desc.packageName(), desc.displayName(), builder.build());
    }

    /// Builds a source file containing a single top-level enum declaration.
    ///
    /// @param desc the enum to declare; its package and simple name determine the file location
    /// @param spec receives the builder to populate the enum declaration
    /// @return the finished source file
    public static JavaFile enum_(ClassDesc desc, Consumer<EnumBuilder> spec) {
        EnumBuilder builder = new EnumBuilder(desc);
        spec.accept(builder);
        return new JavaFile(desc.packageName(), desc.displayName(), builder.build());
    }

    /// Builds a source file containing a single top-level annotation type declaration.
    ///
    /// @param desc the annotation type to declare; its package and simple name determine the file location
    /// @param spec receives the builder to populate the annotation type declaration
    /// @return the finished source file
    public static JavaFile annotationType(ClassDesc desc, Consumer<AnnotationTypeBuilder> spec) {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(desc);
        spec.accept(builder);
        return new JavaFile(desc.packageName(), desc.displayName(), builder.build());
    }

    /// The file's package name.
    ///
    /// @return the package name, or the empty string for the unnamed package
    public String packageName() {
        return packageName;
    }

    /// The declared type's simple name.
    ///
    /// @return the simple name
    public String simpleName() {
        return simpleName;
    }

    /// The declared type's fully qualified name.
    ///
    /// @return `packageName + "." + simpleName`, or just `simpleName` for the unnamed package
    public String qualifiedName() {
        return packageName.isEmpty() ? simpleName : packageName + "." + simpleName;
    }

    @Override
    public Meta renderMeta() {
        return new Meta(packageName, typeDecl);
    }

    /// The render metadata for a [JavaFile]: its package and wrapped type declaration.
    ///
    /// @param packageName the file's package
    /// @param typeDecl the top-level type declaration to render
    public record Meta(String packageName, TypeDecl typeDecl) implements RenderableFile.Meta {}

    @Override
    public Path pathSuffix() {
        var filename = Path.of(simpleName + ".java");
        return Stream.of(packageName.split("\\."))
                .map(Path::of)
                .reduce(Path::resolve)
                .map(package_ -> package_.resolve(filename))
                .orElse(filename);
    }

    /// Rebuilds this file's class declaration by applying `transform` to each member.
    ///
    /// @param transform the transform applied to each member
    /// @return a new file wrapping the transformed class declaration
    /// @throws IllegalStateException if this file does not wrap a class declaration
    public JavaFile transformClass(ClassTransform transform) {
        if (typeDecl instanceof ClassDecl c) {
            return new JavaFile(packageName, simpleName, Transforms.transform(c, transform));
        }
        throw new IllegalStateException("this JavaFile does not wrap a class declaration");
    }

    /// Rebuilds this file's interface declaration by applying `transform` to each member.
    ///
    /// @param transform the transform applied to each member
    /// @return a new file wrapping the transformed interface declaration
    /// @throws IllegalStateException if this file does not wrap an interface declaration
    public JavaFile transformInterface(InterfaceTransform transform) {
        if (typeDecl instanceof InterfaceDecl i) {
            return new JavaFile(packageName, simpleName, Transforms.transform(i, transform));
        }
        throw new IllegalStateException("this JavaFile does not wrap an interface declaration");
    }

    /// Rebuilds this file's record declaration by applying `transform` to each member.
    ///
    /// @param transform the transform applied to each member
    /// @return a new file wrapping the transformed record declaration
    /// @throws IllegalStateException if this file does not wrap a record declaration
    public JavaFile transformRecord(RecordTransform transform) {
        if (typeDecl instanceof RecordDecl r) {
            return new JavaFile(packageName, simpleName, Transforms.transform(r, transform));
        }
        throw new IllegalStateException("this JavaFile does not wrap a record declaration");
    }

    /// Rebuilds this file's enum declaration by applying `transform` to each member.
    ///
    /// @param transform the transform applied to each member
    /// @return a new file wrapping the transformed enum declaration
    /// @throws IllegalStateException if this file does not wrap an enum declaration
    /// @throws IllegalArgumentException if `transform` passes a member kind an enum constant body cannot contain
    ///         (e.g. a constructor) while processing a constant's body
    public JavaFile transformEnum(EnumTransform transform) {
        if (typeDecl instanceof EnumDecl e) {
            return new JavaFile(packageName, simpleName, Transforms.transform(e, transform));
        }
        throw new IllegalStateException("this JavaFile does not wrap an enum declaration");
    }
}
