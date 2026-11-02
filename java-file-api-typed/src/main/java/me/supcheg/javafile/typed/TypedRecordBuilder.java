package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.model.RecordComponent;
import me.supcheg.javafile.model.RecordMember;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;

/// Declares a record (§6.5), branded with `Self`: its components, the
/// canonical constructor (explicit or compact), additional constructors that
/// delegate to it, accessors, instance and static methods, and the interfaces
/// it implements. A record is final: it extends no class, and `extends` is
/// not expressible here.
///
/// ```java
/// JavaFile file = TypedJavaFile.record(target, desc, new TypedJavaFile.TypedRecordSpec() {
///     public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
///         var x = rb.component("x", PrimitiveToken.INT);
///         var y = rb.component("y", PrimitiveToken.INT);
///         var sum = rb.declareMethod("sum", PrimitiveToken.INT);
///         var bodies = rb.bodies();
///         bodies.define(sum, (b, self) -> b.return_(addInt(self.call(x.accessor()), self.call(y.accessor()))));
///         return bodies;
///     }
/// });
/// ```
///
/// @param <Self> the brand of the record being declared
public final class TypedRecordBuilder<Self> extends RecordConstructorArities<Self> {
    private static final ClassDesc CD_RECORD = ClassDesc.of("java.lang", "Record");
    private static final ClassDesc CD_OBJECT = ConstantDescs.CD_Object;
    // the supplier is what the fact of the class is built with, and a record has `Record` and `Object` above it
    @SuppressWarnings("UnnecessaryLambda")
    private static final java.util.function.Supplier<List<ClassDesc>> SUPERCLASSES =
            () -> List.of(CD_RECORD, CD_OBJECT);

    private final List<Component> components = new ArrayList<>();
    private final List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();
    private java.util.Optional<me.supcheg.javafile.doc.DocComment> doc = java.util.Optional.empty();

    TypedRecordBuilder(
            TargetClasspath target,
            ClassDesc desc,
            List<TypeParameter<?>> parameters,
            List<DeclarationIdentity> enclosing) {
        super(target, desc, "record");
        declareParameters(parameters);
        FinalClassToken<Self> token = parameters.isEmpty()
                ? UnsafeFacts.<Self>declaredClassToken(new ClassTypeRef(desc), SUPERCLASSES, this::table)
                : UnsafeFacts.<Self>declaredClassToken(desc, typeParams(), variables(), SUPERCLASSES, this::table);
        self(token);
    }

    @Override
    MemberTraits instanceTraits() {
        return MemberTraits.FINAL;
    }

    @Override
    boolean mutableStaticFields() {
        return false;
    }

    @Override
    boolean allowNativeMethod() {
        return false;
    }

    /// Documents the record, e.g. a sentence of what it is for.
    ///
    /// @param doc the comment
    /// @return this builder
    /// @throws IllegalArgumentException if the text of `doc` contains `*/`
    public TypedRecordBuilder<Self> withDoc(me.supcheg.javafile.doc.DocComment doc) {
        this.doc = java.util.Optional.of(Docs.requireSafe(doc, described()));
        return this;
    }

    /// Documents the record with one paragraph of plain text.
    ///
    /// @param text the text
    /// @return this builder
    /// @throws IllegalArgumentException if `text` is empty, or contains `*/`
    public TypedRecordBuilder<Self> withDoc(String text) {
        return withDoc(Docs.of(text, described()));
    }

    /// Refuses an instance initializer block: javac rejects `{ ... }` in a record body, which a compact
    /// constructor replaces (JLS 8.10.4).
    ///
    /// @return never
    /// @throws IllegalArgumentException always, naming the record
    // a refusal of what javac rejects: the tests call it to read the message, and it never returns
    @SuppressWarnings("DoNotCallSuggester")
    public final InstanceInitializerBlock declareInitializerBlock() {
        throw new IllegalArgumentException("cannot declare an instance initializer block of " + described()
                + ": a record has no instance initializer block - its compact constructor replaces it"
                + " (JLS 8.10.4), and the typed layer declares only what javac accepts");
    }

    /// Declares a static initializer block, `static { ... }`, which the bodies of the record define exactly
    /// once. It runs once, when the record is initialized, in source order among the static field initializers
    /// and the other blocks; it has no `this` and no instance member, and throws no checked exception (JLS
    /// 11.2.3).
    ///
    /// @return the handle of the declared block
    /// @throws IllegalStateException if the declarations of the record have ended
    public final StaticInitializerBlock declareStaticInitializerBlock() {
        return staticInitializerBlock();
    }

    /// Gives a type parameter of the record one more bound, an interface, before its components:
    /// `record R<T extends A & B>(T value)`. The leftmost bound is the token `typeParam` was given;
    /// each further bound is said here, and gives the witness by which an expression of the type
    /// parameter is one of it.
    ///
    /// @param variable the token of the type parameter, as the specification was given it
    /// @param bound the interface, with its type arguments
    /// @param <T> the type parameter
    /// @param <B> the interface
    /// @return the witness that the type parameter is of the interface
    /// @throws IllegalArgumentException if `variable` is no type parameter of this record; the bound
    ///     is raw, has a wildcard type argument or is a bound already; or the bounds clash
    /// @throws IllegalStateException if the components have begun, or the bound is out of scope
    public <T, B> Upcast<T, B> bound(TypeVarToken<T> variable, InterfaceToken<B> bound) {
        return addBound(variable, bound);
    }

    /// Adds a component, in declaration order.
    ///
    /// @param name the component name
    /// @param type the component type
    /// @param <T> the component type
    /// @return the component, with the fact of its accessor and its private field
    /// @throws IllegalArgumentException if the type is out of scope, or the name cannot be a component name
    /// @throws IllegalStateException if the members have begun
    public <T> TypedRecordComponent<Self, T> component(String name, TypeToken<T> type) {
        return component(name, type, new AnnotationRef<?>[0]);
    }

    /// Adds a component that declares `annotations`, in declaration order.
    ///
    /// @param name the component name
    /// @param type the component type
    /// @param annotations the annotations, in order
    /// @param <T> the component type
    /// @return the component, with the fact of its accessor and its private field
    /// @throws IllegalArgumentException if the type is out of scope, or an annotation cannot annotate a
    ///     component
    /// @throws IllegalStateException if the members have begun
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> TypedRecordComponent<Self, T> component(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return component(name, type, java.util.Optional.empty(), List.of(annotations));
    }

    /// Adds a documented component.
    ///
    /// @param name the component name
    /// @param type the component type
    /// @param doc the documentation comment
    /// @param <T> the component type
    /// @return the component, with the fact of its accessor and its private field
    /// @throws IllegalArgumentException if the type is out of scope, or the text of `doc` contains `*/`
    /// @throws IllegalStateException if the members have begun
    public <T> TypedRecordComponent<Self, T> component(
            String name, TypeToken<T> type, me.supcheg.javafile.doc.DocComment doc) {
        return component(name, type, doc, new AnnotationRef<?>[0]);
    }

    /// Adds a documented component that declares `annotations`.
    ///
    /// @param name the component name
    /// @param type the component type
    /// @param doc the documentation comment
    /// @param annotations the annotations, in order
    /// @param <T> the component type
    /// @return the component, with the fact of its accessor and its private field
    /// @throws IllegalArgumentException if the type is out of scope, an annotation cannot annotate a
    ///     component, or the text of `doc` contains `*/`
    /// @throws IllegalStateException if the members have begun
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> TypedRecordComponent<Self, T> component(
            String name, TypeToken<T> type, me.supcheg.javafile.doc.DocComment doc, AnnotationRef<?>... annotations) {
        return component(name, type, java.util.Optional.of(doc), List.of(annotations));
    }

    private <T> TypedRecordComponent<Self, T> component(
            String name,
            TypeToken<T> type,
            java.util.Optional<me.supcheg.javafile.doc.DocComment> doc,
            List<AnnotationRef<?>> annotations) {
        requireHeader("component " + name + " of " + described());
        Tokens.requireNotRaw(type);
        scope("the type of component " + name).require(type);
        for (AnnotationRef<?> annotation : annotations) {
            target().verify(annotation.type().token());
        }
        var accessor = UnsafeInvocables.method(self(), name, type, MemberTraits.FINAL);
        var field = UnsafeFacts.field(self(), name, type);
        components.add(new Component(
                name,
                type,
                annotations,
                doc.map(comment -> Docs.requireSafe(comment, "component " + name + " of " + described()))));
        componentDeclared();
        return new TypedRecordComponent<>(name, type, accessor, field);
    }

    /// Adds an interface to the `implements` clause.
    ///
    /// @param supertype the interface, with its type arguments
    /// @param <I> the interface
    /// @return the witness that the record is of `supertype`
    /// @throws IllegalArgumentException if the type is not an interface, is named already, or is out of scope
    /// @throws IllegalStateException if the members have begun
    public <I> Upcast<Self, I> implements_(InterfaceToken<I> supertype) {
        Upcast<Self, I> upcast = addSupertype(supertype);
        interfaces.add(supertype.typeRef());
        return upcast;
    }

    // The declarations of the record's constructors, `declareCompactConstructor`, `declareCanonicalConstructor`
    // and `declareConstructor`, of every arity 0 to 16, are generated into the superclass of this class
    // (RecordConstructorArities, see TypedCodegen). They call the hooks below.

    @Override
    // the class being declared is the class whose token the builder holds
    @SuppressWarnings("unchecked")
    FinalClassToken<Self> finalSelf() {
        return (FinalClassToken<Self>) self();
    }

    @Override
    <F extends Invocable> F declareCompact(F ctor) {
        requireCanonical();
        return declaredCtor(ctor, Kind.COMPACT);
    }

    @Override
    <F extends Invocable> F declareCanonical(F ctor) {
        requireCanonical();
        return declaredCtor(ctor, Kind.CANONICAL);
    }

    @Override
    <F extends Invocable> F declareAdditional(F ctor) {
        return declaredCtor(ctor, Kind.CONSTRUCTOR);
    }

    private void requireCanonical() {
        if (declaredMembers().stream()
                .anyMatch(member -> member instanceof Member.Callable callable
                        && (callable.kind() == Kind.CANONICAL || callable.kind() == Kind.COMPACT))) {
            throw new IllegalArgumentException(
                    described() + " declares its canonical constructor already: a" + " record has one");
        }
    }

    private <F extends Invocable> F declaredCtor(F ctor, Kind kind) {
        return declare(ctor, kind, List.of(), List.of(), List.of());
    }

    /// The declarations of the record end and the bodies of its members begin.
    ///
    /// @return the stage that defines the declared members
    /// @throws IllegalStateException if the declarations have ended, or leave an interface method
    ///     unimplemented
    public TypedRecordBodies<Self> bodies() {
        return bodies(declared -> new TypedRecordBodies<>(this, declared, components));
    }

    @Override
    JavaFile file(List<?> members) {
        return JavaFile.record(desc(), rb -> populate(rb, members));
    }

    @Override
    TypeDecl nestedDecl(List<?> members) {
        RecordBuilder rb = new RecordBuilder(desc());
        populate(rb, members);
        return rb.build();
    }

    private void populate(RecordBuilder rb, List<?> members) {
        doc.ifPresent(rb::withDoc);
        typeParams().forEach(rb::withTypeParam);
        components.forEach(component -> rb.withComponent(new RecordComponent(
                component.name(),
                component.type().typeRef(),
                component.annotations().stream().map(AnnotationRef::toUse).toList(),
                component.doc())));
        interfaces.forEach(rb::withInterface);
        members.forEach(member -> rb.accept((RecordMember) member));
        declaredNested().forEach(rb);
    }

    record Component(
            String name,
            TypeToken<?> type,
            List<AnnotationRef<?>> annotations,
            java.util.Optional<me.supcheg.javafile.doc.DocComment> doc) {
        Component {
            annotations = List.copyOf(annotations);
        }
    }
}
