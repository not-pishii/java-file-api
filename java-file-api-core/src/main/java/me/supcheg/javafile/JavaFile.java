package me.supcheg.javafile;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.render.StandardRenderer;
import me.supcheg.javafile.transform.ClassTransform;
import me.supcheg.javafile.transform.EnumTransform;
import me.supcheg.javafile.transform.InterfaceTransform;
import me.supcheg.javafile.transform.RecordTransform;
import me.supcheg.javafile.transform.Transforms;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

/// A source file containing a single top-level type declaration.
///
/// A `JavaFile` wraps exactly one of [ClassDecl], [InterfaceDecl],
/// [RecordDecl], or [EnumDecl], created via the matching static factory
/// ([#of(ClassDesc,Consumer)], [#interface_(ClassDesc,Consumer)],
/// [#record(ClassDesc,Consumer)], [#enum_(ClassDesc,Consumer)]). The
/// `transform*` methods only accept a transform matching the wrapped kind;
/// calling the wrong one throws.
///
/// Instances are immutable; every method that produces a modified file
/// returns a new instance.
public final class JavaFile {

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

    /// Renders this file's package declaration, computed imports, and type declaration to source text.
    ///
    /// @return the complete source text
    public String render() {
        return StandardRenderer.instance().render(packageName, typeDecl, SourceRenderer.standardFormat());
    }

    /// Writes this file's rendered source text under `outputDir`, creating the
    /// package's directories inside it as needed.
    ///
    /// @param outputDir the source root to write into
    /// @throws IOException if the directories or file cannot be created or written
    public void writeTo(Path outputDir) throws IOException {
        Path packageDir = packageName.isEmpty() ? outputDir : outputDir.resolve(packageName.replace('.', '/'));
        Files.createDirectories(packageDir);
        Path file = packageDir.resolve(simpleName + ".java");
        Files.writeString(file, render());
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
    public JavaFile transformEnum(EnumTransform transform) {
        if (typeDecl instanceof EnumDecl e) {
            return new JavaFile(packageName, simpleName, Transforms.transform(e, transform));
        }
        throw new IllegalStateException("this JavaFile does not wrap an enum declaration");
    }
}
