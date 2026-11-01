package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumMember;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// Declares an enum (§6.5), branded with `Self`: constants without arguments,
/// with arguments, and with a class body that overrides the abstract methods
/// of the enum; the interfaces it implements; instance and static methods and
/// fields. An enum is final and extends `Enum`: it extends no class, and
/// `extends` is not expressible here. Its constructors are `private` and
/// cannot be called from outside: no fact of one is a `CtorRef`, and `new_`
/// does not take it.
///
/// ```java
/// JavaFile file = TypedJavaFile.enum_(target, desc, new TypedJavaFile.TypedEnumSpec() {
///     public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
///         var op = eb.declareAbstractMethod("apply", PrimitiveToken.INT, PrimitiveToken.INT);
///         eb.constant("PLUS", (b, self, x) -> b.return_(x));
///         return eb.bodies();
///     }
/// });
/// ```
///
/// @param <Self> the brand of the enum being declared
public final class TypedEnumBuilder<Self> extends EnumConstructorArities<Self> {
    private final List<Constant<Self>> constants = new ArrayList<>();
    private final List<Invocable> abstractMethods = new ArrayList<>();
    private final List<Constructor> constructors = new ArrayList<>();
    private final List<me.supcheg.javafile.type.ClassOrInterfaceTypeRef> interfaces = new ArrayList<>();
    private TypedEnumBodies<Self> bodies;

    TypedEnumBuilder(TargetClasspath target, ClassDesc desc) {
        super(target, desc, "enum");
        self(UnsafeFacts.<Self>declaredEnumToken(new ClassTypeRef(desc), List.of(), this::table));
    }

    @Override
    void declaredMember(Member.Callable member) {
        if (member.kind() == Kind.ABSTRACT) {
            abstractMethods.add(member.fact());
        }
    }

    /// Adds a constant with no arguments and no constant-specific body.
    ///
    /// @param name the constant name
    /// @return this builder
    /// @throws IllegalStateException if the members have begun
    public TypedEnumBuilder<Self> constant(String name) {
        return constant(name, List.of(), null);
    }

    /// Adds a constant with constructor arguments and no constant-specific body.
    ///
    /// @param name the constant name
    /// @param args the constructor arguments, in order
    /// @return this builder
    /// @throws IllegalStateException if the members have begun
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final TypedEnumBuilder<Self> constant(String name, Expr<?>... args) {
        return constant(name, List.of(args), null);
    }

    /// Adds a constant with constructor arguments and no constant-specific body.
    ///
    /// @param name the constant name
    /// @param args the constructor arguments, in order
    /// @return this builder
    /// @throws IllegalStateException if the members have begun
    public TypedEnumBuilder<Self> constant(String name, List<? extends Expr<?>> args) {
        return constant(name, args, null);
    }

    /// Adds a constant with a constant-specific class body, which overrides the abstract methods of the
    /// enum.
    ///
    /// @param name the constant name
    /// @param body populates the body
    /// @return this builder
    /// @throws IllegalStateException if the members have begun
    public TypedEnumBuilder<Self> constant(String name, Consumer<TypedEnumConstantBodies<Self>> body) {
        return constant(name, List.of(), body);
    }

    /// Adds a constant with constructor arguments and a constant-specific class body.
    ///
    /// @param name the constant name
    /// @param args the constructor arguments, in order
    /// @param body populates the body, or `null` for none
    /// @return this builder
    /// @throws IllegalStateException if the members have begun
    public TypedEnumBuilder<Self> constant(
            String name, List<? extends Expr<?>> args, Consumer<TypedEnumConstantBodies<Self>> body) {
        requireHeader("constant " + name + " of " + described());
        constants.add(new Constant<>(name, List.copyOf(args), body));
        self(UnsafeFacts.<Self>declaredEnumToken(
                new ClassTypeRef(desc()), constants.stream().map(Constant::name).toList(), this::table));
        return this;
    }

    /// Adds an interface to the `implements` clause.
    ///
    /// @param supertype the interface, with its type arguments
    /// @param <I> the interface
    /// @return the witness that the enum is of `supertype`
    /// @throws IllegalArgumentException if the type is not an interface, is named already, or is out of scope
    /// @throws IllegalStateException if the members have begun
    public <I> Upcast<Self, I> implements_(InterfaceToken<I> supertype) {
        Upcast<Self, I> upcast = addSupertype(supertype);
        interfaces.add(supertype.typeRef());
        return upcast;
    }

    // The declarations of the enum's constructors, `constructor`, of every arity 0 to 12, are generated into the
    // superclass of this class (EnumConstructorArities, see TypedCodegen). They call the hooks below.

    @Override
    VoidBody constructorRoot() {
        return VoidBody.root(
                "body of constructor", new ExceptionScope.Declares(ExceptionScope.Boundary.CONSTRUCTOR, List.of()));
    }

    @Override
    void declaredConstructor(List<?> params, VoidBody body) {
        constructors.add(new Constructor(
                params.stream().<Var<?>>map(param -> (Var<?>) param).toList(), body));
    }

    /// The declarations of the enum end and the bodies of its members begin.
    ///
    /// @return the stage that defines the declared members
    /// @throws IllegalStateException if the declarations have ended, or leave an interface method
    ///     unimplemented
    public TypedEnumBodies<Self> bodies() {
        TypedEnumBodies<Self> built =
                bodies(declared -> new TypedEnumBodies<>(this, declared, constants, abstractMethods, constructors));
        bodies = built;
        return built;
    }

    @Override
    JavaFile file(List<?> members) {
        return JavaFile.enum_(desc(), eb -> populate(eb, members));
    }

    @Override
    TypeDecl nestedDecl(List<?> members) {
        EnumBuilder eb = new EnumBuilder(desc());
        populate(eb, members);
        return eb.build();
    }

    private void populate(EnumBuilder eb, List<?> members) {
        for (EnumConstant constant : bodies.enumConstants()) {
            eb.withConstant(constant);
        }
        interfaces.forEach(eb::withInterface);
        members.forEach(member -> eb.accept((EnumMember) member));
        declaredNested().forEach(eb::accept);
    }

    /// A constant as it was declared.
    record Constant<Self>(String name, List<Expr<?>> args, Consumer<TypedEnumConstantBodies<Self>> body) {
        Constant {
            args = List.copyOf(args);
        }
    }

    /// A constructor as it was declared: its parameters and the body built before the methods were all
    /// declared, which the bodies of the enum lower once they are.
    record Constructor(List<Var<?>> params, VoidBody body) {
        Constructor {
            params = List.copyOf(params);
        }
    }
}
