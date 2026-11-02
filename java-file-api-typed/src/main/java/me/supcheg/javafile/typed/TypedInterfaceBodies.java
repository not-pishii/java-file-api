package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.VariableInitializer;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.InterfaceMember;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.PrivateMethodDecl;
import me.supcheg.javafile.model.StaticMethodDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// Defines the members of a typed interface (§6.5), branded with `Self`: the
/// stage [TypedInterfaceBuilder#bodies()] gives. An abstract method has no
/// body; a `default`, `static`, `private` or `private static` method is
/// defined here, and a constant is given its initializer, which must be a
/// constant expression (JLS 9.3.1).
///
/// @param <Self> the brand of the interface being declared
public final class TypedInterfaceBodies<Self> extends TypedTypeBodies<Self, InterfaceMember> {

    TypedInterfaceBodies(TypedInterfaceBuilder<Self> owner, Declared declared, List<ClassDesc> permits) {
        super(owner, declared);
    }

    @Override
    boolean requireConstantExpression() {
        return true;
    }

    @Override
    InterfaceMember method(
            Member.Callable declared,
            CodeBody code,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        return switch (declared.kind()) {
            case DEFAULT ->
                new DefaultMethodDecl(
                        declared.fact().name(),
                        declared.fact().resultType().map(type -> type.typeRef()),
                        annotations,
                        Tokens.typeParams(declared.fact().traits().typeParams()),
                        params,
                        code,
                        throwsClause,
                        declared.doc());
            case STATIC ->
                new StaticMethodDecl(
                        declared.fact().name(),
                        declared.fact().resultType().map(type -> type.typeRef()),
                        annotations,
                        Tokens.typeParams(declared.fact().traits().typeParams()),
                        params,
                        code,
                        throwsClause,
                        declared.doc());
            case PRIVATE ->
                new PrivateMethodDecl(
                        declared.fact().name(),
                        declared.fact().resultType().map(type -> type.typeRef()),
                        annotations,
                        Tokens.typeParams(declared.fact().traits().typeParams()),
                        params,
                        code,
                        false,
                        throwsClause,
                        declared.doc());
            case PRIVATE_STATIC ->
                new PrivateMethodDecl(
                        declared.fact().name(),
                        declared.fact().resultType().map(type -> type.typeRef()),
                        annotations,
                        Tokens.typeParams(declared.fact().traits().typeParams()),
                        params,
                        code,
                        true,
                        throwsClause,
                        declared.doc());
            case METHOD, ABSTRACT, CONSTRUCTOR, CANONICAL, COMPACT, ACCESSOR ->
                throw new IllegalStateException(
                        "an interface cannot declare " + declared.fact() + " of kind " + declared.kind());
        };
    }

    @Override
    InterfaceMember abstractMethod(
            Member.Callable declared,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        return new AbstractMethodDecl(
                declared.fact().name(),
                declared.fact().resultType().map(type -> type.typeRef()),
                Tokens.typeParams(declared.fact().traits().typeParams()),
                params,
                annotations,
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                throwsClause,
                declared.doc());
    }

    @Override
    InterfaceMember constructor(
            Member.Callable declared,
            ConstructorBody body,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        throw new IllegalStateException("an interface has no constructor: " + declared.fact());
    }

    @Override
    InterfaceMember field(Member.Field declared, VariableInitializer initializer) {
        throw new IllegalStateException("an interface declares constants, not fields: " + declared.fact());
    }

    @Override
    InterfaceMember constant(Member.Constant declared, VariableInitializer initializer) {
        return new ConstantDecl(
                declared.fact().name(),
                declared.fact().type().typeRef(),
                declared.annotations().stream()
                        .map(me.supcheg.javafile.facts.AnnotationRef::toUse)
                        .toList(),
                initializer,
                declared.doc());
    }

    @Override
    InterfaceMember staticField(Member.StaticField declared, VariableInitializer initializer) {
        throw new IllegalStateException("an interface declares constants, not static fields: " + declared.fact());
    }
}
