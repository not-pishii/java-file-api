package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.InterfaceMember;
import me.supcheg.javafile.type.ClassTypeRef;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

/// Declares an interface (§6.5), branded with `Self`: abstract, default,
/// static and private methods, constants, `extends` of several interfaces,
/// and `sealed` with `permits`.
///
/// ```java
/// JavaFile file = TypedJavaFile.interface_(target, desc, new TypedJavaFile.TypedInterfaceSpec() {
///     public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
///         var area = ib.declareAbstractMethod("area", PrimitiveToken.DOUBLE);
///         var twice = ib.declarePrivateMethod("twice", PrimitiveToken.INT, PrimitiveToken.INT);
///         var bodies = ib.bodies();
///         bodies.define(twice, (b, self, x) -> b.return_(mulInt(x, literal(2))));
///         return bodies;
///     }
/// });
/// ```
///
/// An interface has no constructor and extends no class: [#declareConstructor]
/// is not there, and [TypedInterfaceBuilder#extends_] takes an interface token
/// alone. A method declared by name that has the signature of one a
/// superinterface declares overrides it and is rendered `@Override`; the
/// bodies reject a `default` method of two superinterfaces that disagree
/// without an override.
///
/// @param <Self> the brand of the interface being declared
public final class TypedInterfaceBuilder<Self> extends TypeMemberDeclarations<Self> {
    private final List<ClassDesc> permits = new ArrayList<>();
    private final List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> extendsInterfaces = new ArrayList<>();

    TypedInterfaceBuilder(TargetClasspath target, ClassDesc desc) {
        super(target, desc, "interface");
        self(UnsafeFacts.<Self>declaredInterfaceToken(new ClassTypeRef(desc), this::table));
    }

    @Override
    boolean allowAbstract() {
        return true;
    }

    /// Adds an interface to the `extends` clause.
    ///
    /// @param supertype the interface, with its type arguments
    /// @param <I> the interface
    /// @return the witness that this interface is of `supertype`
    /// @throws IllegalArgumentException if the type is not an interface, is named already, or is out of scope
    /// @throws IllegalStateException if the members have begun
    public <I> Upcast<Self, I> extends_(InterfaceToken<I> supertype) {
        Upcast<Self, I> upcast = addSupertype(supertype);
        extendsInterfaces.add(supertype.typeRef());
        return upcast;
    }

    /// Names a subtype in the `permits` clause, which makes the interface `sealed`.
    ///
    /// @param subtype the permitted subtype
    /// @return this builder
    /// @throws IllegalStateException if the members have begun
    public TypedInterfaceBuilder<Self> permits(ClassDesc subtype) {
        requireHeader("a permitted subtype of " + described());
        permits.add(subtype);
        return this;
    }

    /// Names subtypes in the `permits` clause, which makes the interface `sealed`.
    ///
    /// @param subtypes the permitted subtypes, in order
    /// @return this builder
    /// @throws IllegalStateException if the members have begun
    public TypedInterfaceBuilder<Self> permits(ClassDesc... subtypes) {
        for (ClassDesc subtype : subtypes) {
            permits(subtype);
        }
        return this;
    }

    /// The declarations of the interface end and the bodies of its members begin.
    ///
    /// @return the stage that defines the declared members
    /// @throws IllegalStateException if the declarations have ended, or leave a `default` method of two
    ///     superinterfaces unsettled
    public TypedInterfaceBodies<Self> bodies() {
        return bodies(declared -> new TypedInterfaceBodies<>(this, declared, List.copyOf(permits)));
    }

    @Override
    JavaFile file(List<?> members) {
        return JavaFile.interface_(desc(), ib -> {
            annotations().forEach(annotation -> ib.withAnnotation(annotation.toUse()));
            extendsInterfaces.forEach(ib::withExtends);
            permits.forEach(ib::withPermits);
            members.forEach(member -> ib.accept((InterfaceMember) member));
        });
    }
}
