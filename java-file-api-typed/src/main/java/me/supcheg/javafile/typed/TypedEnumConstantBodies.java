package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.VariableInitializer;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

/// Defines the class body of one enum constant (§6.5): the methods that
/// override the abstract methods of the enum, each by the fact the enum
/// declared. Every abstract method of the enum is overridden, which is what
/// makes a constant of an enum with abstract methods complete.
///
/// @param <Self> the brand of the enum being declared
public final class TypedEnumConstantBodies<Self> extends TypedTypeBodies<Self, EnumConstantMember> {
    private List<TypeDecl> nested = List.of();

    TypedEnumConstantBodies(TypedEnumBuilder<Self> owner, List<Invocable> abstractMethods) {
        super(owner, declared(owner, abstractMethods));
    }

    // ------------------------------------------------------------------
    // Member types
    // ------------------------------------------------------------------

    /// Declares a `public static final` member class of the constant's body.
    ///
    /// @param name the simple name of the member class
    /// @param spec declares the member class, given the first stage of its header
    /// @param <N> the brand of the member class
    /// @return the token of the member class
    public <N> FinalClassToken<N> declareNestedClass(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        NestedDeclarations.NestedType<FinalClassToken<N>> type =
                NestedDeclarations.class_(owner().target(), owner().desc(), name, List.of(), List.of(), false, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a `public` member interface of the constant's body, implicitly `static`.
    ///
    /// @param name the simple name of the member interface
    /// @param spec declares the interface, given its builder
    /// @param <I> the brand of the member interface
    /// @return the token of the member interface
    public <I> InterfaceToken<I> declareNestedInterface(String name, TypedJavaFile.TypedInterfaceSpec spec) {
        NestedDeclarations.NestedType<InterfaceToken<I>> type =
                NestedDeclarations.interface_(owner().target(), owner().desc(), name, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a `public` member record of the constant's body, implicitly `static`.
    ///
    /// @param name the simple name of the member record
    /// @param spec declares the record, given its builder
    /// @param <R> the brand of the member record
    /// @return the token of the member record
    public <R> FinalClassToken<R> declareNestedRecord(String name, TypedJavaFile.TypedRecordSpec spec) {
        NestedDeclarations.NestedType<FinalClassToken<R>> type =
                NestedDeclarations.record(owner().target(), owner().desc(), name, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a `public` member enum of the constant's body, implicitly `static`.
    ///
    /// @param name the simple name of the member enum
    /// @param spec declares the enum, given its builder
    /// @param <E> the brand of the member enum
    /// @return the token of the member enum
    public <E> EnumToken<E> declareNestedEnum(String name, TypedJavaFile.TypedEnumSpec spec) {
        NestedDeclarations.NestedType<EnumToken<E>> type =
                NestedDeclarations.enum_(owner().target(), owner().desc(), name, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Refuses a member annotation type of a constant's body: an annotation type cannot be declared
    /// there (JLS 9.6), unlike a member class, interface, record or enum.
    ///
    /// @param name the simple name of the member annotation type
    /// @param spec declares the annotation type, which is never called
    /// @return never
    /// @throws IllegalArgumentException always
    public java.lang.constant.ClassDesc declareNestedAnnotationType(
            String name, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        throw new IllegalArgumentException("cannot declare the member annotation type " + name + " of the body of a"
                + " constant of " + owner().described() + ": an annotation type cannot be declared in the body of"
                + " an anonymous class (JLS 9.6)");
    }

    @Override
    List<EnumConstantMember> extraMembers() {
        return List.copyOf(nested);
    }

    private static <Self> Declared declared(TypedEnumBuilder<Self> owner, List<Invocable> abstractMethods) {
        List<Member> members = abstractMethods.stream()
                .<Member>map(method -> new Member.Callable(
                        method,
                        Member.Kind.METHOD,
                        List.of(),
                        List.of(),
                        IntStream.range(0, method.params().size())
                                .mapToObj(_ -> List.<me.supcheg.javafile.facts.AnnotationRef<?>>of())
                                .toList(),
                        owner.scope("the body of " + method),
                        true,
                        Site.here()))
                .toList();
        return new Declared(owner.self(), owner.inherited(), members, owner::table);
    }

    /// Overrides an abstract method of the enum in this constant's body.
    ///
    /// @param method the fact of the abstract method
    /// @param body the body
    /// @param <R> the result type
    public <R> void override(
            me.supcheg.javafile.facts.MethodRef0<Self, R> method,
            MemberBodyArities.InstanceBody0<Self, Body<R>, R> body) {
        define(method, body);
    }

    public <R, A1> void override(
            me.supcheg.javafile.facts.MethodRef1<Self, R, A1> method,
            MemberBodyArities.InstanceBody1<Self, Body<R>, R, A1> body) {
        define(method, body);
    }

    public <R, A1, A2> void override(
            me.supcheg.javafile.facts.MethodRef2<Self, R, A1, A2> method,
            MemberBodyArities.InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        define(method, body);
    }

    public <R, A1, A2, A3> void override(
            me.supcheg.javafile.facts.MethodRef3<Self, R, A1, A2, A3> method,
            MemberBodyArities.InstanceBody3<Self, Body<R>, R, A1, A2, A3> body) {
        define(method, body);
    }

    public <R, A1, A2, A3, A4> void override(
            me.supcheg.javafile.facts.MethodRef4<Self, R, A1, A2, A3, A4> method,
            MemberBodyArities.InstanceBody4<Self, Body<R>, R, A1, A2, A3, A4> body) {
        define(method, body);
    }

    public void override(
            me.supcheg.javafile.facts.VoidMethodRef0<Self> method,
            MemberBodyArities.InstanceBody0<Self, VoidBody, Void> body) {
        define(method, body);
    }

    public <A1> void override(
            me.supcheg.javafile.facts.VoidMethodRef1<Self, A1> method,
            MemberBodyArities.InstanceBody1<Self, VoidBody, Void, A1> body) {
        define(method, body);
    }

    public <A1, A2> void override(
            me.supcheg.javafile.facts.VoidMethodRef2<Self, A1, A2> method,
            MemberBodyArities.InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        define(method, body);
    }

    public <A1, A2, A3> void override(
            me.supcheg.javafile.facts.VoidMethodRef3<Self, A1, A2, A3> method,
            MemberBodyArities.InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        define(method, body);
    }

    public <A1, A2, A3, A4> void override(
            me.supcheg.javafile.facts.VoidMethodRef4<Self, A1, A2, A3, A4> method,
            MemberBodyArities.InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        define(method, body);
    }

    @Override
    EnumConstantMember method(
            Member.Callable declared,
            CodeBody code,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        return new MethodDecl(
                declared.fact().name(),
                declared.fact().resultType().map(type -> type.typeRef()),
                annotations,
                Set.of(me.supcheg.javafile.model.Modifier.PUBLIC),
                List.of(),
                params,
                code,
                throwsClause,
                Optional.empty());
    }

    @Override
    EnumConstantMember abstractMethod(
            Member.Callable declared,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        throw new IllegalStateException("a constant body cannot declare an abstract method: " + declared.fact());
    }

    @Override
    EnumConstantMember constructor(
            Member.Callable declared,
            ConstructorBody body,
            List<Param> params,
            List<ClassOrInterfaceTypeRef> throwsClause,
            List<AnnotationUse> annotations) {
        throw new IllegalStateException("a constant body has no constructor: " + declared.fact());
    }

    @Override
    EnumConstantMember field(Member.Field declared, VariableInitializer initializer) {
        throw new IllegalStateException(
                "a constant body of the typed layer declares methods alone: " + declared.fact());
    }

    @Override
    EnumConstantMember constant(Member.Constant declared, VariableInitializer initializer) {
        throw new IllegalStateException(
                "a constant body of the typed layer declares methods alone: " + declared.fact());
    }

    @Override
    EnumConstantMember staticField(Member.StaticField declared, VariableInitializer initializer) {
        throw new IllegalStateException(
                "a constant body of the typed layer declares methods alone: " + declared.fact());
    }
}
