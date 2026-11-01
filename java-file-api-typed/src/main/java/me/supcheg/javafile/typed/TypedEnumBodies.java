package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.VariableInitializer;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumConstructorDecl;
import me.supcheg.javafile.model.EnumMember;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// Defines the members of a typed enum (§6.5), branded with `Self`: the
/// stage [TypedEnumBuilder#bodies()] gives. Methods and fields are defined
/// here, and the constructors — `private` and never callable from outside —
/// and the constants, whose arguments and class bodies are built once every
/// method of the enum is declared.
///
/// @param <Self> the brand of the enum being declared
public final class TypedEnumBodies<Self> extends TypedTypeBodies<Self, EnumMember> {
    private final List<TypedEnumBuilder.Constant<Self>> constants;
    private final List<Invocable> abstractMethods;
    private final List<TypedEnumBuilder.Constructor> constructors;

    TypedEnumBodies(
            TypedEnumBuilder<Self> owner,
            Declared declared,
            List<TypedEnumBuilder.Constant<Self>> constants,
            List<Invocable> abstractMethods,
            List<TypedEnumBuilder.Constructor> constructors) {
        super(owner, declared);
        this.constants = List.copyOf(constants);
        this.abstractMethods = List.copyOf(abstractMethods);
        this.constructors = List.copyOf(constructors);
    }

    @Override
    EnumMember method(
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
        if (declared.modifiers().contains(Modifier.NATIVE)) {
            return new me.supcheg.javafile.model.NativeMethodDecl(
                    declared.fact().name(),
                    declared.fact().resultType().map(type -> type.typeRef()),
                    List.of(),
                    params,
                    annotations,
                    Set.copyOf(modifiers),
                    throwsClause,
                    declared.doc());
        }
        return new MethodDecl(
                declared.fact().name(),
                declared.fact().resultType().map(type -> type.typeRef()),
                annotations,
                Set.copyOf(modifiers),
                List.of(),
                params,
                code,
                throwsClause,
                declared.doc());
    }

    @Override
    EnumMember abstractMethod(
            Member.Callable declared,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        return new AbstractMethodDecl(
                declared.fact().name(),
                declared.fact().resultType().map(type -> type.typeRef()),
                List.of(),
                params,
                annotations,
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                throwsClause,
                declared.doc());
    }

    @Override
    EnumMember constructor(
            Member.Callable declared,
            ConstructorBody body,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        throw new IllegalStateException(
                "an enum constructor is declared through the builder, not as a" + " method fact: " + declared.fact());
    }

    @Override
    EnumMember field(Member.Field declared, VariableInitializer initializer) {
        return new FieldDecl(
                declared.fact().name(),
                declared.fact().type().typeRef(),
                declared.annotations().stream()
                        .map(me.supcheg.javafile.facts.AnnotationRef::toUse)
                        .toList(),
                declared.modifiers(),
                Optional.of(initializer),
                declared.doc());
    }

    @Override
    EnumMember constant(Member.Constant declared, VariableInitializer initializer) {
        throw new IllegalStateException("an enum declares constants, not constant fields: " + declared.fact());
    }

    @Override
    EnumMember staticField(Member.StaticField declared, VariableInitializer initializer) {
        return new FieldDecl(
                declared.fact().name(),
                declared.fact().type().typeRef(),
                declared.annotations().stream()
                        .map(me.supcheg.javafile.facts.AnnotationRef::toUse)
                        .toList(),
                declared.modifiers(),
                Optional.of(initializer),
                declared.doc());
    }

    @Override
    List<EnumMember> extraMembers() {
        return List.copyOf(enumConstructors());
    }

    /// The constants of the enum, with their arguments and class bodies.
    ///
    /// @return the constants, in declaration order
    List<EnumConstant> enumConstants() {
        Lowering lowering = new Lowering(owner().target());
        return constants.stream()
                .map(constant -> new EnumConstant(
                        constant.name(),
                        List.of(),
                        constant.args().stream()
                                .map(arg -> lowering.lowerExpr(arg.node()))
                                .toList(),
                        body(constant),
                        constant.doc()))
                .toList();
    }

    private List<me.supcheg.javafile.model.EnumConstantMember> body(TypedEnumBuilder.Constant<Self> constant) {
        if (constant.body() == null) {
            if (!abstractMethods.isEmpty()) {
                throw new IllegalStateException("the constant " + constant.name() + " of " + owner().described()
                        + " has no body, and the enum declares the abstract methods " + abstractMethods
                        + ": every constant implements them in its body");
            }
            return List.of();
        }
        @SuppressWarnings("unchecked")
        TypedEnumBuilder<Self> enumBuilder = (TypedEnumBuilder<Self>) owner();
        TypedEnumConstantBodies<Self> body = new TypedEnumConstantBodies<>(enumBuilder, abstractMethods);
        constant.body().accept(body);
        return body.complete();
    }

    private List<EnumConstructorDecl> enumConstructors() {
        Lowering lowering = new Lowering(
                owner().target(),
                new Lowering.Place.InClass(
                        self(), owner().scope("the body of a constructor"), owner().bounded(), Set.of()),
                Optional.empty());
        return constructors.stream()
                .map(constructor -> {
                    List<Param> params = constructor.params().stream()
                            .map(param -> new Param(
                                    lowering.declareUpfront(param), param.type().typeRef()))
                            .toList();
                    CodeBody code = lowering.lowerBlock(constructor.body().instrs());
                    return new EnumConstructorDecl(List.of(), params, code, List.of(), constructor.doc());
                })
                .toList();
    }
}
