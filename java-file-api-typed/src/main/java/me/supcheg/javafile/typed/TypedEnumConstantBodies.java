package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.VariableInitializer;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// Defines the class body of one enum constant (§6.5): the methods that
/// override the abstract methods of the enum, each by the fact the enum
/// declared; instance and static fields with their initializers; helper
/// methods the constant calls; and member types of every kind. Every abstract
/// method of the enum is overridden, which is what makes a constant of an enum
/// with abstract methods complete.
///
/// Java 25 accepts static fields and static methods in the body of an enum
/// constant — the body is an anonymous class, and an anonymous class may
/// declare static members since Java 16 — so this layer declares them too.
///
/// @param <Self> the brand of the enum being declared
public final class TypedEnumConstantBodies<Self> extends TypedTypeBodies<Self, EnumConstantMember> {
    private final TypeSignatureRegistry registry;
    private final Set<MethodSignature> bodySignatures;
    private final Set<String> localStaticNames = new HashSet<>();
    private List<TypeDecl> nested = List.of();
    private Optional<DocComment> pendingDoc = Optional.empty();

    TypedEnumConstantBodies(TypedEnumBuilder<Self> owner, List<Invocable> abstractMethods) {
        super(owner, declared(owner, abstractMethods));
        this.registry = new TypeSignatureRegistry(
                "the body of a constant of " + owner.described(), owner.self(), owner.inherited(), owner.bounded());
        Set<MethodSignature> signatures = new HashSet<>();
        abstractMethods.forEach(method -> signatures.add(method.signature()));
        this.bodySignatures = Set.copyOf(signatures);
    }

    // ------------------------------------------------------------------
    // Documentation
    // ------------------------------------------------------------------

    /// Documents the member of the constant's body declared next: a field or a method.
    ///
    /// @param doc the comment
    /// @return this stage
    /// @throws IllegalArgumentException if the text of `doc` contains `*/`
    public TypedEnumConstantBodies<Self> documented(DocComment doc) {
        this.pendingDoc = Optional.of(Docs.requireSafe(doc, "the body of a constant of " + owner().described()));
        return this;
    }

    /// Documents the member of the constant's body declared next with one paragraph of plain text.
    ///
    /// @param text the text
    /// @return this stage
    /// @throws IllegalArgumentException if `text` is empty, or contains `*/`
    public TypedEnumConstantBodies<Self> documented(String text) {
        return documented(Docs.of(text, "the body of a constant of " + owner().described()));
    }

    private Optional<DocComment> takeDoc() {
        Optional<DocComment> taken = pendingDoc;
        pendingDoc = Optional.empty();
        return taken;
    }

    // ------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------

    /// Declares a `public final` instance field of the constant's body, which [#initialize] gives its
    /// initializer.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field
    public <T> FieldRef<Self, T> declareField(String name, TypeToken<T> type) {
        return declareField(name, type, List.of(), takeDoc());
    }

    /// Declares a `public final` instance field of the constant's body that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> FieldRef<Self, T> declareField(String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return declareField(name, type, List.of(annotations), takeDoc());
    }

    /// Declares a documented `public final` instance field of the constant's body that declares
    /// `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> FieldRef<Self, T> declareField(
            String name, TypeToken<T> type, DocComment doc, AnnotationRef<?>... annotations) {
        return declareField(
                name, type, List.of(annotations), Optional.of(Docs.requireSafe(doc, fieldDescription(name))));
    }

    private <T> FieldRef<Self, T> declareField(
            String name, TypeToken<T> type, List<AnnotationRef<?>> annotations, Optional<DocComment> doc) {
        String what = fieldDescription(name);
        Scopes.requireNoneOpen(what);
        Tokens.requireNotRaw(type);
        owner().scope("the type of " + what).require(type);
        registry.field(name);
        annotations.forEach(
                annotation -> owner().target().verify(annotation.type().token()));
        FieldRef<Self, T> field = UnsafeFacts.field(self(), name, type);
        declaredField(new Member.Field(field, Set.of(Modifier.PUBLIC, Modifier.FINAL), annotations, doc, Site.here()));
        return field;
    }

    /// Declares a `public static final` field of the constant's body, which [#initialize] gives its
    /// initializer.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field
    public <T> StaticFieldRef<T> declareStaticField(String name, TypeToken<T> type) {
        return declareStaticField(name, type, List.of(), takeDoc());
    }

    /// Declares a `public static final` field of the constant's body that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> StaticFieldRef<T> declareStaticField(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return declareStaticField(name, type, List.of(annotations), takeDoc());
    }

    /// Declares a documented `public static final` field of the constant's body that declares
    /// `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> StaticFieldRef<T> declareStaticField(
            String name, TypeToken<T> type, DocComment doc, AnnotationRef<?>... annotations) {
        return declareStaticField(
                name,
                type,
                List.of(annotations),
                Optional.of(Docs.requireSafe(doc, "static " + fieldDescription(name))));
    }

    private <T> StaticFieldRef<T> declareStaticField(
            String name, TypeToken<T> type, List<AnnotationRef<?>> annotations, Optional<DocComment> doc) {
        String what = "static " + fieldDescription(name);
        Scopes.requireNoneOpen(what);
        Tokens.requireNotRaw(type);
        owner().staticScope("the type of " + what).require(type);
        registry.field(name);
        localStaticNames.add(name);
        annotations.forEach(
                annotation -> owner().target().verify(annotation.type().token()));
        StaticFieldRef<T> field = UnsafeFacts.staticField(self(), name, type);
        declaredStaticField(new Member.StaticField(
                field, Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL), annotations, doc, Site.here()));
        return field;
    }

    private String fieldDescription(String name) {
        return "field " + name + " of the body of a constant of " + owner().described();
    }

    @Override
    Set<String> localStaticMembers() {
        return Set.copyOf(localStaticNames);
    }

    // ------------------------------------------------------------------
    // Helper methods
    // ------------------------------------------------------------------

    /// Declares a `public` helper method of the constant's body, which [#define] gives its body.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declare(UnsafeInvocables.method(self(), name, result, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public` helper method with one parameter, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.method(self(), name, result, p1, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public` helper method with two parameters, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.method(self(), name, result, p1, p2, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public` helper method with three parameters, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(
                UnsafeInvocables.method(self(), name, result, p1, p2, p3, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public` helper method with four parameters, see
    /// [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the method
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(
                UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, MemberTraits.DEFAULT),
                Member.Kind.METHOD);
    }

    /// Declares a `public void` helper method of the constant's body, which [#define] gives its body.
    ///
    /// @param name the method name
    /// @return the fact of the method
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declare(UnsafeInvocables.voidMethod(self(), name, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public void` helper method with one parameter, see
    /// [#declareVoidMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.voidMethod(self(), name, p1, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public void` helper method with two parameters, see
    /// [#declareVoidMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.voidMethod(self(), name, p1, p2, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public void` helper method with three parameters, see
    /// [#declareVoidMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public void` helper method with four parameters, see
    /// [#declareVoidMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the method
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(
                UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, MemberTraits.DEFAULT), Member.Kind.METHOD);
    }

    /// Declares a `public static` helper method of the constant's body, which [#define] gives its body.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declare(UnsafeInvocables.staticMethod(self(), name, result, MemberTraits.FINAL), Member.Kind.STATIC);
    }

    /// Declares a `public static` helper method with one parameter, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.staticMethod(self(), name, result, p1, MemberTraits.FINAL), Member.Kind.STATIC);
    }

    /// Declares a `public static` helper method with two parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <R, A1, A2> StaticMethodRef2<R, A1, A2> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(
                UnsafeInvocables.staticMethod(self(), name, result, p1, p2, MemberTraits.FINAL), Member.Kind.STATIC);
    }

    /// Declares a `public static` helper method with three parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(
                UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, MemberTraits.FINAL),
                Member.Kind.STATIC);
    }

    /// Declares a `public static` helper method with four parameters, see
    /// [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the method
    public <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declareStaticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(
                UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, MemberTraits.FINAL),
                Member.Kind.STATIC);
    }

    /// Declares a `public static void` helper method of the constant's body, which [#define] gives its
    /// body.
    ///
    /// @param name the method name
    /// @return the fact of the method
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declare(UnsafeInvocables.voidStaticMethod(self(), name, MemberTraits.FINAL), Member.Kind.STATIC);
    }

    /// Declares a `public static void` helper method with one parameter, see
    /// [#declareVoidStaticMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.voidStaticMethod(self(), name, p1, MemberTraits.FINAL), Member.Kind.STATIC);
    }

    /// Declares a `public static void` helper method with two parameters, see
    /// [#declareVoidStaticMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, MemberTraits.FINAL), Member.Kind.STATIC);
    }

    /// Declares a `public static void` helper method with three parameters, see
    /// [#declareVoidStaticMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @return the fact of the method
    public <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(
                UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, MemberTraits.FINAL), Member.Kind.STATIC);
    }

    /// Declares a `public static void` helper method with four parameters, see
    /// [#declareVoidStaticMethod(String)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @return the fact of the method
    public <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidStaticMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(
                UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, MemberTraits.FINAL),
                Member.Kind.STATIC);
    }

    private <F extends Invocable> F declare(F member, Member.Kind kind) {
        String what = member.toString();
        Scopes.requireNoneOpen(what);
        if (bodySignatures.contains(member.signature())) {
            throw new IllegalArgumentException("the method " + member + " of the body of a constant of "
                    + owner().described() + " has the signature of a method the constant overrides: override it"
                    + " with override(method, body), and name a helper otherwise");
        }
        registry.method(member);
        if (kind == Member.Kind.STATIC) {
            localStaticNames.add(member.name());
        }
        TypeScope scope = kind == Member.Kind.STATIC
                ? owner().staticScope("the signature of " + what)
                : owner().scope("the signature of " + what);
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(type -> {
            Tokens.requireNotRaw(type);
            scope.require(type);
        });
        List<List<AnnotationRef<?>>> parameterAnnotations = IntStream.range(
                        0, member.params().size())
                .mapToObj(_ -> List.<AnnotationRef<?>>of())
                .toList();
        Member.Callable callable = new Member.Callable(
                member,
                kind,
                List.of(),
                List.of(),
                parameterAnnotations,
                scope,
                registry.overrides(member.signature()),
                Set.of(),
                takeDoc(),
                Site.here());
        declaredMethod(callable);
        return member;
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
                NestedDeclarations.class_(owner().target(), owner().desc(), name, List.of(), List.of(), spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a generic `public static final` member class of the constant's body with one type
    /// parameter; see [#declareNestedClass(String, TypedJavaFile.TypedClassHeaderSpec)].
    ///
    /// @param name the simple name of the member class
    /// @param p1 the type parameter
    /// @param spec declares the member class, given its header and the token of the type parameter
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member class
    public <N, B1> FinalClassToken<N> declareNestedClass(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericClassSpec1<B1> spec) {
        NestedDeclarations.NestedType<FinalClassToken<N>> type =
                NestedDeclarations.class_(owner().target(), owner().desc(), name, p1, spec);
        nested = Stream.concat(nested.stream(), Stream.of(type.type())).toList();
        return type.token();
    }

    /// Declares a generic `public static final` member class of the constant's body with two type
    /// parameters, see [#declareNestedClass(String, TypeParameter, TypedJavaFile.TypedGenericClassSpec1)].
    ///
    /// @param name the simple name of the member class
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the member class, given its header and the tokens of its type parameters
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member class
    public <N, B1, B2> FinalClassToken<N> declareNestedClass(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericClassSpec2<B1, B2> spec) {
        NestedDeclarations.NestedType<FinalClassToken<N>> type =
                NestedDeclarations.class_(owner().target(), owner().desc(), name, p1, p2, spec);
        nested = Stream.concat(nested.stream(), Stream.of(type.type())).toList();
        return type.token();
    }

    /// Declares a generic `public static final` member class of the constant's body with three type
    /// parameters, see [#declareNestedClass(String, TypeParameter, TypedJavaFile.TypedGenericClassSpec1)].
    ///
    /// @param name the simple name of the member class
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the member class, given its header and the tokens of its type parameters
    /// @param <N> the brand of the member class
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the token of the member class
    public <N, B1, B2, B3> FinalClassToken<N> declareNestedClass(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericClassSpec3<B1, B2, B3> spec) {
        NestedDeclarations.NestedType<FinalClassToken<N>> type =
                NestedDeclarations.class_(owner().target(), owner().desc(), name, p1, p2, p3, spec);
        nested = Stream.concat(nested.stream(), Stream.of(type.type())).toList();
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

    /// Declares a generic `public` member interface of the constant's body with one type parameter,
    /// implicitly `static`.
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the type parameter
    /// @param spec declares the interface, given its builder and the token of the type parameter
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member interface
    public <I, B1> InterfaceToken<I> declareNestedInterface(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericInterfaceSpec1<B1> spec) {
        NestedDeclarations.NestedType<InterfaceToken<I>> type =
                NestedDeclarations.interface_(owner().target(), owner().desc(), name, p1, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a generic `public` member interface of the constant's body with two type parameters,
    /// see [#declareNestedInterface(String, TypeParameter, TypedJavaFile.TypedGenericInterfaceSpec1)].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the interface, given its builder and the tokens of its type parameters
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member interface
    public <I, B1, B2> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericInterfaceSpec2<B1, B2> spec) {
        NestedDeclarations.NestedType<InterfaceToken<I>> type =
                NestedDeclarations.interface_(owner().target(), owner().desc(), name, p1, p2, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a generic `public` member interface of the constant's body with three type parameters,
    /// see [#declareNestedInterface(String, TypeParameter, TypedJavaFile.TypedGenericInterfaceSpec1)].
    ///
    /// @param name the simple name of the member interface
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the interface, given its builder and the tokens of its type parameters
    /// @param <I> the brand of the member interface
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the token of the member interface
    public <I, B1, B2, B3> InterfaceToken<I> declareNestedInterface(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericInterfaceSpec3<B1, B2, B3> spec) {
        NestedDeclarations.NestedType<InterfaceToken<I>> type =
                NestedDeclarations.interface_(owner().target(), owner().desc(), name, p1, p2, p3, spec);
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

    /// Declares a generic `public` member record of the constant's body with one type parameter,
    /// implicitly `static`.
    ///
    /// @param name the simple name of the member record
    /// @param p1 the type parameter
    /// @param spec declares the record, given its builder and the token of the type parameter
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the type parameter
    /// @return the token of the member record
    public <R, B1> FinalClassToken<R> declareNestedRecord(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedGenericRecordSpec1<B1> spec) {
        NestedDeclarations.NestedType<FinalClassToken<R>> type =
                NestedDeclarations.record(owner().target(), owner().desc(), name, p1, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a generic `public` member record of the constant's body with two type parameters,
    /// see [#declareNestedRecord(String, TypeParameter, TypedJavaFile.TypedGenericRecordSpec1)].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param spec declares the record, given its builder and the tokens of its type parameters
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @return the token of the member record
    public <R, B1, B2> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypedJavaFile.TypedGenericRecordSpec2<B1, B2> spec) {
        NestedDeclarations.NestedType<FinalClassToken<R>> type =
                NestedDeclarations.record(owner().target(), owner().desc(), name, p1, p2, spec);
        nested = java.util.stream.Stream.concat(nested.stream(), java.util.stream.Stream.of(type.type()))
                .toList();
        return type.token();
    }

    /// Declares a generic `public` member record of the constant's body with three type parameters,
    /// see [#declareNestedRecord(String, TypeParameter, TypedJavaFile.TypedGenericRecordSpec1)].
    ///
    /// @param name the simple name of the member record
    /// @param p1 the first type parameter
    /// @param p2 the second type parameter
    /// @param p3 the third type parameter
    /// @param spec declares the record, given its builder and the tokens of its type parameters
    /// @param <R> the brand of the member record
    /// @param <B1> the leftmost bound of the first type parameter
    /// @param <B2> the leftmost bound of the second type parameter
    /// @param <B3> the leftmost bound of the third type parameter
    /// @return the token of the member record
    public <R, B1, B2, B3> FinalClassToken<R> declareNestedRecord(
            String name,
            TypeParameter<B1> p1,
            TypeParameter<B2> p2,
            TypeParameter<B3> p3,
            TypedJavaFile.TypedGenericRecordSpec3<B1, B2, B3> spec) {
        NestedDeclarations.NestedType<FinalClassToken<R>> type =
                NestedDeclarations.record(owner().target(), owner().desc(), name, p1, p2, p3, spec);
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

    /// Refuses type parameters on a member enum of the constant's body: javac rejects `enum E<T>`
    /// (JLS 8.9).
    ///
    /// @param name the simple name of the member enum
    /// @param p1 the type parameter
    /// @param spec declares the enum, which is never called
    /// @param <E> the brand of the member enum
    /// @param <B1> the leftmost bound of the type parameter
    /// @return never
    /// @throws IllegalArgumentException always, naming the enum and the type parameter
    public <E, B1> EnumToken<E> declareNestedEnum(String name, TypeParameter<B1> p1, TypedJavaFile.TypedEnumSpec spec) {
        throw new IllegalArgumentException("cannot declare the member enum " + name + " of the body of a"
                + " constant of " + owner().described() + " with a type parameter: an enum has no type parameters"
                + " (JLS 8.9), and the typed layer declares only what javac accepts");
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

    /// Refuses a generic member annotation type of a constant's body: an annotation type cannot be declared
    /// there (JLS 9.6), and an annotation type has no type parameters either.
    ///
    /// @param name the simple name of the member annotation type
    /// @param p1 the type parameter
    /// @param spec declares the annotation type, which is never called
    /// @param <B1> the leftmost bound of the type parameter
    /// @return never
    /// @throws IllegalArgumentException always
    public <B1> java.lang.constant.ClassDesc declareNestedAnnotationType(
            String name, TypeParameter<B1> p1, TypedJavaFile.TypedAnnotationTypeSpec spec) {
        throw new IllegalArgumentException("cannot declare the member annotation type " + name + " of the body of a"
                + " constant of " + owner().described() + " with a type parameter: an annotation type cannot be"
                + " declared in the body of an anonymous class (JLS 9.6), and it has no type parameters");
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
                        Set.of(),
                        java.util.Optional.empty(),
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
        Set<Modifier> modifiers = new LinkedHashSet<>();
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
                List.of(),
                params,
                code,
                throwsClause,
                declared.doc());
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
        return new me.supcheg.javafile.model.FieldDecl(
                declared.fact().name(),
                declared.fact().type().typeRef(),
                declared.annotations().stream().map(AnnotationRef::toUse).toList(),
                declared.modifiers(),
                Optional.of(initializer),
                declared.doc());
    }

    @Override
    EnumConstantMember constant(Member.Constant declared, VariableInitializer initializer) {
        throw new IllegalStateException(
                "a constant body of the typed layer declares fields and methods, not a constant: " + declared.fact());
    }

    @Override
    EnumConstantMember staticField(Member.StaticField declared, VariableInitializer initializer) {
        return new me.supcheg.javafile.model.FieldDecl(
                declared.fact().name(),
                declared.fact().type().typeRef(),
                declared.annotations().stream().map(AnnotationRef::toUse).toList(),
                declared.modifiers(),
                Optional.of(initializer),
                declared.doc());
    }
}
