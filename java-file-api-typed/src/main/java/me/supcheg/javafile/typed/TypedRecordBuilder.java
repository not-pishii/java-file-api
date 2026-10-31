package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.CtorRef4;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.model.RecordComponent;
import me.supcheg.javafile.model.RecordMember;
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
public final class TypedRecordBuilder<Self> extends TypeMemberDeclarations<Self> {
    private static final ClassDesc CD_RECORD = ClassDesc.of("java.lang", "Record");
    private static final ClassDesc CD_OBJECT = ConstantDescs.CD_Object;

    private final List<Component> components = new ArrayList<>();
    private final List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();

    TypedRecordBuilder(TargetClasspath target, ClassDesc desc) {
        super(target, desc, "record");
        FinalClassToken<Self> token = UnsafeFacts.<Self>declaredClassToken(
                new ClassTypeRef(desc), () -> List.of(CD_RECORD, CD_OBJECT), this::table);
        self(token);
    }

    @Override
    MemberTraits instanceTraits() {
        return MemberTraits.FINAL;
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

    /// Declares the compact constructor, `public Name { ... }`, which the bodies define.
    ///
    /// @return the fact of the canonical constructor
    /// @throws IllegalArgumentException if a canonical or compact constructor is declared already
    /// @throws IllegalStateException if the declarations have ended
    public CtorRef0<Self> declareCompactConstructor() {
        requireCanonical();
        CtorRef0<Self> ctor = ctor0();
        declaredCtor(ctor, Kind.COMPACT);
        return ctor;
    }

    public <A1> CtorRef1<Self, A1> declareCompactConstructor(TypeToken<A1> p1) {
        requireCanonical();
        CtorRef1<Self, A1> ctor = ctor1(p1);
        declaredCtor(ctor, Kind.COMPACT);
        return ctor;
    }

    public <A1, A2> CtorRef2<Self, A1, A2> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        requireCanonical();
        CtorRef2<Self, A1, A2> ctor = ctor2(p1, p2);
        declaredCtor(ctor, Kind.COMPACT);
        return ctor;
    }

    public <A1, A2, A3> CtorRef3<Self, A1, A2, A3> declareCompactConstructor(
            TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        requireCanonical();
        CtorRef3<Self, A1, A2, A3> ctor = ctor3(p1, p2, p3);
        declaredCtor(ctor, Kind.COMPACT);
        return ctor;
    }

    public <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> declareCompactConstructor(
            TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        requireCanonical();
        CtorRef4<Self, A1, A2, A3, A4> ctor = ctor4(p1, p2, p3, p4);
        declaredCtor(ctor, Kind.COMPACT);
        return ctor;
    }

    /// Declares the canonical constructor with an explicit parameter list, which the bodies define.
    ///
    /// @return the fact of the canonical constructor
    /// @throws IllegalArgumentException if a canonical or compact constructor is declared already
    /// @throws IllegalStateException if the declarations have ended
    public CtorRef0<Self> declareCanonicalConstructor() {
        requireCanonical();
        CtorRef0<Self> ctor = ctor0();
        declaredCtor(ctor, Kind.CANONICAL);
        return ctor;
    }

    public <A1> CtorRef1<Self, A1> declareCanonicalConstructor(TypeToken<A1> p1) {
        requireCanonical();
        CtorRef1<Self, A1> ctor = ctor1(p1);
        declaredCtor(ctor, Kind.CANONICAL);
        return ctor;
    }

    public <A1, A2> CtorRef2<Self, A1, A2> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        requireCanonical();
        CtorRef2<Self, A1, A2> ctor = ctor2(p1, p2);
        declaredCtor(ctor, Kind.CANONICAL);
        return ctor;
    }

    public <A1, A2, A3> CtorRef3<Self, A1, A2, A3> declareCanonicalConstructor(
            TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        requireCanonical();
        CtorRef3<Self, A1, A2, A3> ctor = ctor3(p1, p2, p3);
        declaredCtor(ctor, Kind.CANONICAL);
        return ctor;
    }

    public <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> declareCanonicalConstructor(
            TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        requireCanonical();
        CtorRef4<Self, A1, A2, A3, A4> ctor = ctor4(p1, p2, p3, p4);
        declaredCtor(ctor, Kind.CANONICAL);
        return ctor;
    }

    /// Declares an additional constructor, which the bodies define with `this(...)`.
    ///
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor of the parameter list is declared already
    /// @throws IllegalStateException if the declarations have ended
    public CtorRef0<Self> declareConstructor() {
        CtorRef0<Self> ctor = ctor0();
        declaredCtor(ctor, Kind.CONSTRUCTOR);
        return ctor;
    }

    public <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        CtorRef1<Self, A1> ctor = ctor1(p1);
        declaredCtor(ctor, Kind.CONSTRUCTOR);
        return ctor;
    }

    public <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        CtorRef2<Self, A1, A2> ctor = ctor2(p1, p2);
        declaredCtor(ctor, Kind.CONSTRUCTOR);
        return ctor;
    }

    public <A1, A2, A3> CtorRef3<Self, A1, A2, A3> declareConstructor(
            TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        CtorRef3<Self, A1, A2, A3> ctor = ctor3(p1, p2, p3);
        declaredCtor(ctor, Kind.CONSTRUCTOR);
        return ctor;
    }

    public <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> declareConstructor(
            TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        CtorRef4<Self, A1, A2, A3, A4> ctor = ctor4(p1, p2, p3, p4);
        declaredCtor(ctor, Kind.CONSTRUCTOR);
        return ctor;
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

    private CtorRef0<Self> ctor0() {
        return UnsafeInvocables.ctor((FinalClassToken<Self>) self(), MemberTraits.FINAL);
    }

    private <A1> CtorRef1<Self, A1> ctor1(TypeToken<A1> p1) {
        return UnsafeInvocables.ctor((FinalClassToken<Self>) self(), p1, MemberTraits.FINAL);
    }

    private <A1, A2> CtorRef2<Self, A1, A2> ctor2(TypeToken<A1> p1, TypeToken<A2> p2) {
        return UnsafeInvocables.ctor((FinalClassToken<Self>) self(), p1, p2, MemberTraits.FINAL);
    }

    private <A1, A2, A3> CtorRef3<Self, A1, A2, A3> ctor3(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return UnsafeInvocables.ctor((FinalClassToken<Self>) self(), p1, p2, p3, MemberTraits.FINAL);
    }

    private <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> ctor4(
            TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return UnsafeInvocables.ctor((FinalClassToken<Self>) self(), p1, p2, p3, p4, MemberTraits.FINAL);
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
        return JavaFile.record(desc(), rb -> {
            annotations().forEach(annotation -> rb.withAnnotation(annotation.toUse()));
            components.forEach(component -> rb.withComponent(new RecordComponent(
                    component.name(),
                    component.type().typeRef(),
                    component.annotations().stream().map(AnnotationRef::toUse).toList())));
            interfaces.forEach(rb::withInterface);
            members.forEach(member -> rb.accept((RecordMember) member));
        });
    }

    record Component(String name, TypeToken<?> type, List<AnnotationRef<?>> annotations) {
        Component {
            annotations = List.copyOf(annotations);
        }
    }
}
