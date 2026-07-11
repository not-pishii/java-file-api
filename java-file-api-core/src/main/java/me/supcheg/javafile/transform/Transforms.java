package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.InterfaceMember;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.RecordMember;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;

/// Entry point for applying a member-level transform to a declaration or code body.
///
/// Each `transform` overload rebuilds the source declaration through the
/// matching builder, feeding each existing member through the given
/// transform exactly once; the transform decides what ends up in the
/// result. The source value is never modified.
public final class Transforms {

    private Transforms() {}

    /// Rebuilds a class declaration, passing each member through `transform`.
    ///
    /// @param decl the source class declaration
    /// @param transform the transform applied to each member
    /// @return a new class declaration
    public static ClassDecl transform(ClassDecl decl, ClassTransform transform) {
        ClassBuilder builder = new ClassBuilder(decl.desc());
        // ClassBuilder always seeds PUBLIC by default and withModifiers only adds to that set; a decl
        // built without PUBLIC (only reachable by constructing ClassDecl directly, bypassing ClassBuilder)
        // cannot lose PUBLIC through this round-trip. This matches ClassBuilder's existing behavior.
        builder.withModifiers(decl.modifiers().toArray(new Modifier[0]));
        decl.typeParams()
                .forEach(p -> builder.withTypeParam(p.name(), p.bounds().toArray(new ClassOrInterfaceTypeRef[0])));
        decl.superclass().ifPresent(builder::withSuperclass);
        decl.interfaces().forEach(builder::withInterface);
        builder.permits(decl.permits().toArray(new ClassDesc[0]));
        for (ClassMember member : decl.members()) {
            transform.accept(builder, member);
        }
        return builder.build();
    }

    /// Rebuilds an enum declaration, passing each member through `transform`.
    ///
    /// @param decl the source enum declaration
    /// @param transform the transform applied to each member
    /// @return a new enum declaration
    public static EnumDecl transform(EnumDecl decl, ClassTransform transform) {
        EnumBuilder builder = new EnumBuilder(decl.desc());
        decl.constants().forEach(builder::withConstant);
        decl.interfaces().forEach(builder::withInterface);
        for (ClassMember member : decl.members()) {
            transform.accept(builder, member);
        }
        return builder.build();
    }

    /// Rebuilds an interface declaration, passing each member through `transform`.
    ///
    /// @param decl the source interface declaration
    /// @param transform the transform applied to each member
    /// @return a new interface declaration
    public static InterfaceDecl transform(InterfaceDecl decl, InterfaceTransform transform) {
        InterfaceBuilder builder = new InterfaceBuilder(decl.desc());
        decl.typeParams()
                .forEach(p -> builder.withTypeParam(p.name(), p.bounds().toArray(new ClassOrInterfaceTypeRef[0])));
        decl.extendsInterfaces().forEach(builder::withExtends);
        builder.permits(decl.permits().toArray(new ClassDesc[0]));
        for (InterfaceMember member : decl.members()) {
            transform.accept(builder, member);
        }
        return builder.build();
    }

    /// Rebuilds a record declaration, passing each member through `transform`.
    ///
    /// @param decl the source record declaration
    /// @param transform the transform applied to each member
    /// @return a new record declaration
    public static RecordDecl transform(RecordDecl decl, RecordTransform transform) {
        RecordBuilder builder = new RecordBuilder(decl.desc());
        decl.typeParams()
                .forEach(p -> builder.withTypeParam(p.name(), p.bounds().toArray(new ClassOrInterfaceTypeRef[0])));
        decl.components().forEach(c -> builder.withComponent(c.name(), c.type()));
        decl.interfaces().forEach(builder::withInterface);
        for (RecordMember member : decl.members()) {
            transform.accept(builder, member);
        }
        return builder.build();
    }

    /// Rebuilds a code body, passing each statement through `transform`.
    ///
    /// @param body the source code body
    /// @param transform the transform applied to each statement
    /// @return a new code body
    public static CodeBody transform(CodeBody body, CodeTransform transform) {
        CodeBuilder builder = new CodeBuilder();
        for (Stmt stmt : body.statements()) {
            transform.accept(builder, stmt);
        }
        return builder.build();
    }
}
