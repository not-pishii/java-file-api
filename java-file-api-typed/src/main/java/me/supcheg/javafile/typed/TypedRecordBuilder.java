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
///         bodies.define(sum, (b, self) -> b.return_(addInt(call(self, x.accessor()), call(self, y.accessor()))));
///         return bodies;
///     }
/// });
/// ```
///
/// @param <Self> the brand of the record being declared
public final class TypedRecordBuilder<Self> extends RecordConstructorArities<Self> {
    private static final ClassDesc CD_RECORD = ClassDesc.of("java.lang", "Record");
    private static final ClassDesc CD_OBJECT = ConstantDescs.CD_Object;
    private static final java.util.function.Supplier<List<ClassDesc>> SUPERCLASSES =
            () -> List.of(CD_RECORD, CD_OBJECT);

    private final List<Component> components = new ArrayList<>();
    private final List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();

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
        requireHeader("component " + name + " of " + described());
        Tokens.requireNotRaw(type);
        scope("the type of component " + name).require(type);
        for (AnnotationRef<?> annotation : annotations) {
            target().verify(annotation.type().token());
        }
        var accessor = UnsafeInvocables.method(self(), name, type, MemberTraits.FINAL);
        var field = UnsafeFacts.field(self(), name, type);
        components.add(new Component(name, type, List.of(annotations)));
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
    // and `declareConstructor`, of every arity 0 to 12, are generated into the superclass of this class
    // (RecordConstructorArities, see TypedCodegen). They call the hooks below.

    @Override
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
        typeParams().forEach(rb::withTypeParam);
        components.forEach(component -> rb.withComponent(new RecordComponent(
                component.name(),
                component.type().typeRef(),
                component.annotations().stream().map(AnnotationRef::toUse).toList())));
        interfaces.forEach(rb::withInterface);
        members.forEach(member -> rb.accept((RecordMember) member));
        declaredNested().forEach(rb::accept);
    }

    record Component(String name, TypeToken<?> type, List<AnnotationRef<?>> annotations) {
        Component {
            annotations = List.copyOf(annotations);
        }
    }
}
