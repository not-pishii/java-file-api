package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.VariableInitializer;
import me.supcheg.javafile.model.CanonicalConstructorDecl;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.RecordMember;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

/// Defines the members of a typed record (§6.5), branded with `Self`: the
/// stage [TypedRecordBuilder#bodies()] gives. The canonical constructor is
/// defined here — compact or explicit — and so is each additional
/// constructor, which begins with `this(...)`. The accessors of the
/// components are generated: a body may call one by its fact.
///
/// @param <Self> the brand of the record being declared
public final class TypedRecordBodies<Self> extends TypedTypeBodies<Self, RecordMember> {
    private final List<TypedRecordBuilder.Component> components;

    TypedRecordBodies(
            TypedRecordBuilder<Self> owner, Declared declared, List<TypedRecordBuilder.Component> components) {
        super(owner, declared);
        this.components = List.copyOf(components);
    }

    @Override
    @Nullable
    String compactParameterName(Member.Callable declared, int index) {
        return switch (declared.kind()) {
            case COMPACT, CANONICAL -> components.get(index).name();
            case METHOD, ABSTRACT, DEFAULT, STATIC, PRIVATE, PRIVATE_STATIC, CONSTRUCTOR, ACCESSOR -> null;
        };
    }

    @Override
    RecordMember method(
            Member.Callable declared,
            CodeBody code,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        Set<Modifier> modifiers = new java.util.LinkedHashSet<>();
        modifiers.add(Modifier.PUBLIC);
        if (declared.kind() == Member.Kind.STATIC) {
            modifiers.add(Modifier.STATIC);
        }
        modifiers.addAll(declared.modifiers());
        return new MethodDecl(
                declared.fact().name(),
                declared.fact().resultType().map(type -> type.typeRef()),
                annotations,
                Set.copyOf(modifiers),
                Tokens.typeParams(declared.fact().traits().typeParams()),
                params,
                code,
                throwsClause,
                declared.doc());
    }

    @Override
    RecordMember abstractMethod(
            Member.Callable declared,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        throw new IllegalStateException("a record cannot declare an abstract method: " + declared.fact());
    }

    @Override
    RecordMember constructor(
            Member.Callable declared,
            ConstructorBody body,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        return switch (declared.kind()) {
            case COMPACT ->
                new CompactConstructorDecl(
                        annotations, Set.of(Modifier.PUBLIC), implicit(body, declared), throwsClause, declared.doc());
            case CANONICAL ->
                new CanonicalConstructorDecl(
                        annotations,
                        Set.of(Modifier.PUBLIC),
                        params,
                        implicit(body, declared),
                        throwsClause,
                        declared.doc());
            default ->
                new ConstructorDecl(annotations, Set.of(Modifier.PUBLIC), params, body, throwsClause, declared.doc());
        };
    }

    private static CodeBody implicit(ConstructorBody body, Member.Callable declared) {
        return switch (body) {
            case ConstructorBody.Implicit(var statements) -> statements;
            case ConstructorBody.Explicit _ ->
                throw new IllegalStateException("a canonical constructor of a record begins with no explicit"
                        + " invocation: " + declared.fact());
        };
    }

    @Override
    RecordMember field(Member.Field declared, VariableInitializer initializer) {
        throw new IllegalStateException("a record has no instance field of its own: " + declared.fact());
    }

    @Override
    RecordMember constant(Member.Constant declared, VariableInitializer initializer) {
        throw new IllegalStateException("a record declares no constant: " + declared.fact());
    }

    @Override
    RecordMember staticField(Member.StaticField declared, VariableInitializer initializer) {
        return new StaticFieldDecl(
                declared.fact().name(),
                declared.fact().type().typeRef(),
                declared.annotations().stream()
                        .map(me.supcheg.javafile.facts.AnnotationRef::toUse)
                        .toList(),
                initializer,
                declared.doc());
    }
}
