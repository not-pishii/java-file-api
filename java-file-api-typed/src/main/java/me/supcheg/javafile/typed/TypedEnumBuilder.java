package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumMember;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody1;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody2;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody3;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody4;
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
public final class TypedEnumBuilder<Self> extends StaticFieldDeclarations<Self> {
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

    /// Declares a `private` constructor of the enum, with no parameters.
    ///
    /// @param body the body, given its block and `this`
    /// @throws IllegalStateException if the declarations have ended
    public void constructor(InstanceBody0<Self, VoidBody, Void> body) {
        constructor(List.of(), body);
    }

    /// Declares a `private` constructor of the enum with one parameter.
    ///
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @throws IllegalStateException if the declarations have ended
    public <A1> void constructor(TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This<>(root, self());
        Var<A1> v1 = Var.param(p1, root);
        MemberBodies.built(root, b -> body.build(b, self, v1));
        constructors.add(new Constructor(List.of(v1), root));
    }

    /// Declares a `private` constructor of the enum with two parameters.
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @throws IllegalStateException if the declarations have ended
    public <A1, A2> void constructor(
            TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This<>(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        MemberBodies.built(root, b -> body.build(b, self, v1, v2));
        constructors.add(new Constructor(List.of(v1, v2), root));
    }

    /// Declares a `private` constructor of the enum with three parameters.
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @throws IllegalStateException if the declarations have ended
    public <A1, A2, A3> void constructor(
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            TypeToken<A3> p3,
            InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This<>(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        MemberBodies.built(root, b -> body.build(b, self, v1, v2, v3));
        constructors.add(new Constructor(List.of(v1, v2, v3), root));
    }

    /// Declares a `private` constructor of the enum with four parameters.
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param p3 the third parameter type
    /// @param p4 the fourth parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @param <A3> the third parameter type
    /// @param <A4> the fourth parameter type
    /// @throws IllegalStateException if the declarations have ended
    public <A1, A2, A3, A4> void constructor(
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            TypeToken<A3> p3,
            TypeToken<A4> p4,
            InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This<>(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        MemberBodies.built(root, b -> body.build(b, self, v1, v2, v3, v4));
        constructors.add(new Constructor(List.of(v1, v2, v3, v4), root));
    }

    private void constructor(List<TypeToken<?>> params, InstanceBody0<Self, VoidBody, Void> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This<>(root, self());
        MemberBodies.built(root, b -> body.build(b, self));
        constructors.add(new Constructor(List.of(), root));
    }

    private VoidBody constructorRoot() {
        return VoidBody.root(
                "body of constructor", new ExceptionScope.Declares(ExceptionScope.Boundary.CONSTRUCTOR, List.of()));
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
        return JavaFile.enum_(desc(), eb -> {
            for (EnumConstant constant : bodies.enumConstants()) {
                eb.withConstant(constant);
            }
            interfaces.forEach(eb::withInterface);
            members.forEach(member -> eb.accept((EnumMember) member));
        });
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
