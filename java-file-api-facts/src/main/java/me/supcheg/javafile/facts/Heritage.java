package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/// What a class that extends a type, or implements it, has to know of the
/// type beyond the facts of its members: every method a subclass inherits,
/// has to implement, or could clash with, every constructor that is not
/// `private`, and every name a field or a member type of the type takes in
/// the body of a subclass.
///
/// The facts of a metamodel name the `public` and `protected` members a type
/// declares; the rules of inheritance (JLS 8.1.1.1, 8.4.8, 9.4.1.3) speak of
/// every member a subclass inherits or could clash with — a method with
/// package access, one a supertype that is not `public` declares, one whose
/// signature no fact can tell. A fact names a member; the heritage tells
/// what holds of it.
///
/// A heritage is told by positions, not by names: a type of a signature
/// ([Type]) mentions a type parameter of the type as [Type.OfType] and one of
/// the member itself as [Type.OfMember], so the two are never taken for each
/// other, whatever they are called, and a type whose type parameters are
/// renamed tells the same heritage. What a class that names the type with
/// type arguments inherits is the heritage [Told#applied(List) applied] to
/// them, [Applied].
///
/// Only a type a class can extend or implement tells its heritage, and only
/// in its full metamodel: [Told]. Any other shape is [Untold].
public sealed interface Heritage permits Heritage.Untold, Heritage.Told {

    /// The heritage of a shape that tells none, [Untold].
    Untold UNTOLD = new Untold();

    /// No heritage: the shape is of a type that cannot be extended — a final
    /// class, a record, an enum —, or is not of a full metamodel, or was
    /// made by hand without one. Nothing extends or implements the type by
    /// such a shape.
    record Untold() implements Heritage {}

    /// The heritage of a type, in terms of the positions of its type parameters.
    ///
    /// @param methods the methods of the type, one [Method] per signature, see [Method]
    /// @param constructors the constructors the type declares that are not `private`; none for an interface
    /// @param named the fields and member types that are not `private`, of the type and of every
    ///              supertype of it: the names that mean them in the body of a subclass
    record Told(List<Method<Declaration>> methods, List<Constructor> constructors, List<Named> named)
            implements Heritage {

        /// @throws IllegalArgumentException if two methods have one signature
        public Told {
            methods = List.copyOf(methods);
            constructors = List.copyOf(constructors);
            named = List.copyOf(named);
            requireOnce(methods);
        }

        /// What a class that names the type with type arguments inherits: of the heritage of
        /// `List<E>`, that of `List<String>`. Every type parameter of the type is replaced by its
        /// argument, by position; a type parameter of a member is left where it is. The signatures
        /// and erasures do not depend on the arguments. Constructors are not inherited.
        ///
        /// @param arguments the type arguments, one per type parameter of the type, in order
        /// @return the heritage in terms of the arguments
        /// @throws IllegalArgumentException if a member mentions a type parameter there is no argument
        ///                                  for, or an exception type is replaced by a type that is
        ///                                  no class or type variable
        public Applied applied(List<TypeRef> arguments) {
            List<TypeRef> given = List.copyOf(arguments);
            return new Applied(
                    methods.stream()
                            .map(method -> new Method<>(
                                    method.signature(),
                                    method.declarations().stream()
                                            .map(declaration -> declaration.applied(given))
                                            .toList(),
                                    method.erasures(),
                                    method.overrides()))
                            .toList(),
                    named);
        }
    }

    /// What a class inherits of a type it names with type arguments: [Told#applied(List)].
    ///
    /// @param methods the methods, one [Method] per signature of the type
    /// @param named the fields and member types, see [Told#named()]
    record Applied(List<Method<Inherited>> methods, List<Named> named) {

        /// @throws IllegalArgumentException if two methods have one signature
        public Applied {
            methods = List.copyOf(methods);
            named = List.copyOf(named);
            requireOnce(methods);
        }
    }

    /// Who may name a member (JLS 6.6). A `private` member is no part of a
    /// heritage: it is not inherited.
    enum Visibility {
        /// `public`.
        PUBLIC,
        /// `protected`: a subclass reaches it.
        PROTECTED,
        /// Package access: a subclass of another package neither reaches nor overrides it.
        PACKAGE
    }

    /// What a method is to a class that inherits it.
    enum Dispatch {
        /// An abstract method, of a class or an interface: a concrete class has to implement it.
        ABSTRACT,
        /// A default method of an interface.
        DEFAULT,
        /// A method of a class with a body, which a subclass may override.
        CONCRETE,
        /// A `final` method, which no subclass overrides.
        FINAL,
        /// A `static` method of a class, which an instance method neither overrides nor is hidden by.
        STATIC
    }

    /// What a method a class declares is, [Declarer.OfClass].
    enum ClassMethod {
        /// `abstract`.
        ABSTRACT,
        /// With a body, neither `final` nor `static`.
        CONCRETE,
        /// `final`, and not `static`.
        FINAL,
        /// `static`.
        STATIC
    }

    /// What a method an interface declares is, [Declarer.OfInterface]. A
    /// `static` or `private` method of an interface is no part of a heritage:
    /// neither is inherited (JLS 8.4.8).
    enum InterfaceMethod {
        /// Without a body.
        ABSTRACT,
        /// `default`.
        DEFAULT
    }

    /// Who declares a method and as what: a class, with whatever access and
    /// `abstract`, `final` or `static`, or an interface, whose methods are
    /// `public` and abstract or default. What Java does not let a method be —
    /// a `protected` default one, a `final` one of an interface — no value is.
    sealed interface Declarer permits Declarer.OfClass, Declarer.OfInterface {

        /// The class or interface that declares the method.
        ///
        /// @return the type
        ClassDesc type();

        /// Who may name the method.
        ///
        /// @return the visibility; `PUBLIC` for a method of an interface
        Visibility visibility();

        /// What the method is to a class that inherits it alone.
        ///
        /// @return the dispatch
        Dispatch dispatch();

        /// A class declares the method.
        ///
        /// @param type the class
        /// @param visibility who may name the method
        /// @param method what the method is
        record OfClass(ClassDesc type, Visibility visibility, ClassMethod method) implements Declarer {
            /// @throws IllegalArgumentException if `type` is not a class or interface
            public OfClass {
                requireDeclarer(type);
            }

            @Override
            public Dispatch dispatch() {
                return switch (method) {
                    case ABSTRACT -> Dispatch.ABSTRACT;
                    case CONCRETE -> Dispatch.CONCRETE;
                    case FINAL -> Dispatch.FINAL;
                    case STATIC -> Dispatch.STATIC;
                };
            }
        }

        /// An interface declares the method.
        ///
        /// @param type the interface
        /// @param method what the method is
        record OfInterface(ClassDesc type, InterfaceMethod method) implements Declarer {
            /// @throws IllegalArgumentException if `type` is not a class or interface
            public OfInterface {
                requireDeclarer(type);
            }

            @Override
            public Visibility visibility() {
                return Visibility.PUBLIC;
            }

            @Override
            public Dispatch dispatch() {
                return switch (method) {
                    case ABSTRACT -> Dispatch.ABSTRACT;
                    case DEFAULT -> Dispatch.DEFAULT;
                };
            }
        }
    }

    /// Whether the last parameter of a method or constructor is of variable
    /// arity (JLS 8.4.1).
    enum Arity {
        /// Every parameter is a single argument.
        FIXED,
        /// The last parameter, an array, takes the arguments that are left: `T...`.
        VARIABLE
    }

    /// The result of a method.
    ///
    /// @param <T> how a type is told: [Type] by positions, [TypeRef] once applied
    sealed interface Result<T> permits Result.Nothing, Result.Of {

        /// The result of a `void` method.
        ///
        /// @param <T> how a type is told
        /// @return no result
        static <T> Result<T> nothing() {
            return new Nothing<>();
        }

        /// No result: the method is `void`.
        ///
        /// @param <T> how a type is told
        record Nothing<T>() implements Result<T> {}

        /// A result of a type.
        ///
        /// @param type the type, as a member of the type the heritage is of
        /// @param <T> how a type is told
        record Of<T>(T type) implements Result<T> {}
    }

    /// A type of a signature of a heritage, in terms of the positions of the
    /// type parameters in scope. A part that mentions none is [Fixed] as a
    /// whole — the form is normal: an [Array] or a [Parameterized] has a type
    /// parameter in it, which [#array(Type)] and [#parameterized(ClassDesc, List)]
    /// see to.
    sealed interface Type permits Type.Fixed, Type.OfType, Type.OfMember, Type.Array, Type.Parameterized {

        /// An array of `component`, in normal form.
        ///
        /// @param component the component type
        /// @return the array type
        static Type array(Type component) {
            return switch (component) {
                case Fixed(TypeRef type) -> new Fixed(new ArrayTypeRef(type));
                case OfType _, OfMember _, Array _, Parameterized _ -> new Array(component);
            };
        }

        /// The parameterization of `raw` with `arguments`, in normal form.
        ///
        /// @param raw the generic class or interface
        /// @param arguments its type arguments, at least one
        /// @return the parameterized type
        static Type parameterized(ClassDesc raw, List<Argument> arguments) {
            List<Optional<TypeArg>> fixed =
                    arguments.stream().map(Argument::fixed).toList();
            return fixed.stream().allMatch(Optional::isPresent)
                    ? new Fixed(new ParameterizedTypeRef(
                            raw, fixed.stream().map(Optional::orElseThrow).toList()))
                    : new Parameterized(raw, arguments);
        }

        /// The type with every type parameter of the type replaced by its argument.
        ///
        /// @param arguments the type arguments of the type, by position
        /// @return the type, which mentions no [OfType]
        /// @throws IllegalArgumentException if the type mentions a type parameter there is no argument for
        default Type applied(List<TypeRef> arguments) {
            return switch (this) {
                case Fixed fixed -> fixed;
                case OfMember member -> member;
                case OfType(int index) -> {
                    if (index >= arguments.size()) {
                        throw new IllegalArgumentException("a member mentions the type parameter #" + index
                                + " of the type, got " + arguments.size() + " type arguments");
                    }
                    yield new Fixed(arguments.get(index));
                }
                case Array(Type component) -> array(component.applied(arguments));
                case Parameterized(ClassDesc raw, List<Argument> given) ->
                    parameterized(
                            raw,
                            given.stream()
                                    .map(argument -> argument.applied(arguments))
                                    .toList());
            };
        }

        /// The type parameters of a member the type mentions, by position.
        ///
        /// @return the positions, each as often as it is mentioned
        default Stream<Integer> ofMember() {
            return switch (this) {
                case Fixed _, OfType _ -> Stream.empty();
                case OfMember(int index) -> Stream.of(index);
                case Array(Type component) -> component.ofMember();
                case Parameterized(ClassDesc _, List<Argument> given) ->
                    given.stream().flatMap(argument -> argument.type().stream()).flatMap(Type::ofMember);
            };
        }

        /// A type that mentions no type parameter in scope: a primitive, a class, a parameterization
        /// or an array of such — or, in an [Applied] heritage, what stands for a type parameter of the
        /// type, whatever it mentions.
        ///
        /// @param type the type
        record Fixed(TypeRef type) implements Type {}

        /// A type parameter of the type the heritage is of.
        ///
        /// @param index its position among the type parameters of the type
        record OfType(int index) implements Type {
            /// @throws IllegalArgumentException if `index` is negative
            public OfType {
                requirePosition(index);
            }
        }

        /// A type parameter of the member itself, a generic method or constructor.
        ///
        /// @param index its position among the type parameters of the member
        record OfMember(int index) implements Type {
            /// @throws IllegalArgumentException if `index` is negative
            public OfMember {
                requirePosition(index);
            }
        }

        /// An array whose component mentions a type parameter.
        ///
        /// @param component the component type
        record Array(Type component) implements Type {
            /// @throws IllegalArgumentException if `component` is [Fixed]: the array is then, see [Type#array(Type)]
            public Array {
                if (component instanceof Fixed) {
                    throw new IllegalArgumentException("an array of a fixed type is fixed, got " + component);
                }
            }
        }

        /// A parameterized type one of whose arguments mentions a type parameter.
        ///
        /// @param raw the generic class or interface
        /// @param arguments its type arguments
        record Parameterized(ClassDesc raw, List<Argument> arguments) implements Type {
            /// @throws IllegalArgumentException if no argument mentions a type parameter: the type is
            ///                                  then [Fixed], see [Type#parameterized(ClassDesc, List)]
            public Parameterized {
                requireDeclarer(raw);
                arguments = List.copyOf(arguments);
                if (arguments.stream().allMatch(argument -> argument.fixed().isPresent())) {
                    throw new IllegalArgumentException(
                            "a parameterized type of fixed arguments is fixed, got " + arguments);
                }
            }
        }
    }

    /// A type argument of a [Type.Parameterized].
    sealed interface Argument permits Argument.Exact, Argument.Extends, Argument.Super, Argument.Unbounded {

        /// The type the argument is made of.
        ///
        /// @return the type or bound; empty for `?`
        Optional<Type> type();

        /// The argument as one that mentions no type parameter, if it mentions none.
        ///
        /// @return the type argument
        default Optional<TypeArg> fixed() {
            return switch (this) {
                case Exact(Type.Fixed(TypeRef type)) -> Optional.of(new ExactTypeArg(type));
                case Extends(Type.Fixed(TypeRef bound)) -> Optional.of(new ExtendsTypeArg(bound));
                case Super(Type.Fixed(TypeRef bound)) -> Optional.of(new SuperTypeArg(bound));
                case Unbounded _ -> Optional.of(me.supcheg.javafile.type.Types.unbounded());
                case Exact _, Extends _, Super _ -> Optional.empty();
            };
        }

        /// The argument with every type parameter of the type replaced by its argument.
        ///
        /// @param arguments the type arguments of the type, by position
        /// @return the argument
        default Argument applied(List<TypeRef> arguments) {
            return switch (this) {
                case Exact(Type type) -> new Exact(type.applied(arguments));
                case Extends(Type bound) -> new Extends(bound.applied(arguments));
                case Super(Type bound) -> new Super(bound.applied(arguments));
                case Unbounded unbounded -> unbounded;
            };
        }

        /// A type argument that is a type.
        ///
        /// @param exact the type
        record Exact(Type exact) implements Argument {
            @Override
            public Optional<Type> type() {
                return Optional.of(exact);
            }
        }

        /// `? extends bound`.
        ///
        /// @param bound the upper bound
        record Extends(Type bound) implements Argument {
            @Override
            public Optional<Type> type() {
                return Optional.of(bound);
            }
        }

        /// `? super bound`.
        ///
        /// @param bound the lower bound
        record Super(Type bound) implements Argument {
            @Override
            public Optional<Type> type() {
                return Optional.of(bound);
            }
        }

        /// `?`.
        record Unbounded() implements Argument {
            @Override
            public Optional<Type> type() {
                return Optional.empty();
            }
        }
    }

    /// A type parameter of a generic method or constructor: it is known by
    /// its position, [Type.OfMember], and has no name.
    ///
    /// @param bounds its bounds; empty for `Object` alone
    record Variable(List<Type> bounds) {
        public Variable {
            bounds = List.copyOf(bounds);
        }
    }

    /// One method declaration among those a type has under a signature: who
    /// declares it and as what, and whether its last parameter is of variable
    /// arity. Told by positions it is a [Declaration]; applied to the type
    /// arguments of a supertype, an [Inherited].
    sealed interface Declared permits Declaration, Inherited {

        /// Who declares the method and as what.
        ///
        /// @return the declarer
        Declarer by();

        /// Whether the last parameter is of variable arity.
        ///
        /// @return the arity
        Arity arity();

        /// How many parameters the method has.
        ///
        /// @return the number of parameters
        int parameters();
    }

    /// A method declaration as a member of the type the heritage is of, in
    /// terms of the positions of the type parameters of the type and of its
    /// own.
    ///
    /// @param by who declares the method and as what
    /// @param arity whether the last parameter is of variable arity
    /// @param typeParams the type parameters of the method itself; empty unless generic
    /// @param params the parameter types, in order; that of a variable arity parameter is its array type
    /// @param result the result
    /// @param throwsTypes the exception types of the `throws` clause: classes and type parameters
    record Declaration(
            Declarer by,
            Arity arity,
            List<Variable> typeParams,
            List<Type> params,
            Result<Type> result,
            List<Type> throwsTypes)
            implements Declared {

        /// @throws IllegalArgumentException if a type mentions a type parameter the method does not
        ///                                  have, or the last parameter of a method of variable arity
        ///                                  is no array
        public Declaration {
            typeParams = List.copyOf(typeParams);
            params = List.copyOf(params);
            throwsTypes = List.copyOf(throwsTypes);
            String told = "a method of " + TypeNames.describe(by.type());
            requireOwn(
                    typeParams,
                    Stream.of(
                                    params.stream(),
                                    switch (result) {
                                        case Result.Nothing<Type> _ -> Stream.<Type>empty();
                                        case Result.Of<Type>(Type type) -> Stream.of(type);
                                    },
                                    throwsTypes.stream())
                            .flatMap(types -> types),
                    told);
            requireArity(arity, params, told);
        }

        @Override
        public int parameters() {
            return params.size();
        }

        /// The declaration as a class that names the type with type arguments inherits it.
        ///
        /// @param arguments the type arguments of the type, by position
        /// @return a [Plain] method, or a [Generic] one if the method has type parameters
        /// @throws IllegalArgumentException if a type mentions a type parameter there is no argument
        ///                                  for, or an exception type is replaced by a type that is
        ///                                  no class or type variable
        public Inherited applied(List<TypeRef> arguments) {
            Declaration applied = new Declaration(
                    by,
                    arity,
                    typeParams.stream()
                            .map(variable -> new Variable(variable.bounds().stream()
                                    .map(bound -> bound.applied(arguments))
                                    .toList()))
                            .toList(),
                    params.stream().map(param -> param.applied(arguments)).toList(),
                    switch (result) {
                        case Result.Nothing<Type> _ -> Result.nothing();
                        case Result.Of<Type>(Type type) -> new Result.Of<>(type.applied(arguments));
                    },
                    throwsTypes.stream()
                            .map(thrown -> thrown.applied(arguments))
                            .toList());
            return typeParams.isEmpty()
                    ? new Plain(
                            by,
                            arity,
                            applied.params().stream().map(Heritage::plain).toList(),
                            switch (applied.result()) {
                                case Result.Nothing<Type> _ -> Result.nothing();
                                case Result.Of<Type>(Type type) -> new Result.Of<>(plain(type));
                            },
                            applied.throwsTypes().stream()
                                    .map(thrown -> thrown(plain(thrown)))
                                    .toList())
                    : new Generic(applied);
        }
    }

    /// A method declaration as a class inherits it of a supertype it names
    /// with type arguments: [Plain], with the types of its signature, or
    /// [Generic].
    sealed interface Inherited extends Declared permits Plain, Generic {}

    /// An inherited method that is not generic.
    ///
    /// @param by who declares the method and as what
    /// @param arity whether the last parameter is of variable arity
    /// @param params the parameter types, in order; that of a variable arity parameter is its array type
    /// @param result the result
    /// @param throwsTypes the exception types of the `throws` clause: classes and type variables
    record Plain(
            Declarer by,
            Arity arity,
            List<TypeRef> params,
            Result<TypeRef> result,
            List<ClassOrInterfaceTypeRef> throwsTypes)
            implements Inherited {

        /// @throws IllegalArgumentException if the last parameter of a method of variable arity is no array
        public Plain {
            params = List.copyOf(params);
            throwsTypes = List.copyOf(throwsTypes);
            boolean valid =
                    switch (arity) {
                        case FIXED -> true;
                        case VARIABLE -> !params.isEmpty() && params.getLast() instanceof ArrayTypeRef;
                    };
            if (!valid) {
                throw new IllegalArgumentException("a method of " + TypeNames.describe(by.type())
                        + " is of variable arity, so its last parameter is an array");
            }
        }

        @Override
        public int parameters() {
            return params.size();
        }
    }

    /// An inherited generic method: its signature stays in terms of the
    /// positions of its own type parameters, and mentions those of the type no
    /// more — each is [Type.Fixed] to its argument. Two generic methods are of
    /// one signature if they are told alike, whatever their type parameters
    /// are called.
    ///
    /// @param told the method, with the type arguments in place
    record Generic(Declaration told) implements Inherited {

        /// @throws IllegalArgumentException if the method has no type parameters
        public Generic {
            if (told.typeParams().isEmpty()) {
                throw new IllegalArgumentException("a generic method has a type parameter");
            }
        }

        @Override
        public Declarer by() {
            return told.by();
        }

        @Override
        public Arity arity() {
            return told.arity();
        }

        @Override
        public int parameters() {
            return told.parameters();
        }
    }

    /// The methods a type has under one signature: one method to a class that
    /// extends or implements the type, which it inherits, overrides or has to
    /// implement.
    ///
    /// The declarations are every method of that signature, declared by the
    /// type or a supertype of it and not `private`, that none of the others
    /// overrides or hides for the type (JLS 8.4.8): mostly one. More are
    ///
    /// - abstract methods of unrelated superinterfaces;
    /// - an abstract method of a superclass beside a default or abstract one
    ///   of an interface, which the class does not implement (JLS 8.4.8.4);
    /// - a method with package access of a superclass, which a class of
    ///   another package neither inherits nor overrides, beside whatever else
    ///   has the signature — and alone, where nothing else has it: a subclass
    ///   in its package overrides it, and has to if it is abstract.
    ///
    /// A method that overrides has to observe every declaration: the result
    /// of each (JLS 8.4.5), the `throws` clause of each (JLS 8.4.8.3), the
    /// access of the most accessible one. The declaration whose result suits
    /// the others — a subtype of the result of each, as javac tells — is the
    /// first of those that do not have package access, which come first.
    ///
    /// @param signature the signature the method table of the type knows the method by
    ///                  ([TypeShape#methods()]): its identity among the methods of the type, and the one
    ///                  a fact of the method names ([Invocable#declared()])
    /// @param declarations the declarations, at least one
    /// @param erasures the erased parameter types of every declaration as it is declared and of every
    ///                 method one of them overrides as that one is declared, whatever the type
    ///                 arguments: the signatures javac makes its bridges of, and no method of a subclass
    ///                 that does not override the method may have (JLS 8.4.8.3)
    /// @param overrides the classes and interfaces that declare the methods the declarations override,
    ///                  sorted by binary name
    /// @param <D> how a declaration is told: [Declaration] by positions, [Inherited] once applied
    record Method<D extends Declared>(
            MethodTableTemplate.Signature signature,
            List<D> declarations,
            Set<List<ClassDesc>> erasures,
            List<ClassDesc> overrides) {

        /// @throws IllegalArgumentException if there is no declaration, one of `overrides` is not a
        ///                                  class or interface, a declaration does not have a
        ///                                  parameter per parameter of `signature`, an erasure does
        ///                                  not, or there is no erasure at all
        public Method {
            declarations = List.copyOf(declarations);
            erasures = Set.copyOf(erasures);
            overrides = List.copyOf(overrides);
            overrides.forEach(Heritage::requireDeclarer);
            String told = "method " + signature;
            if (declarations.isEmpty()) {
                throw new IllegalArgumentException(told + " is declared by a type");
            }
            int arityOf = signature.params().size();
            declarations.stream()
                    .filter(declaration -> declaration.parameters() != arityOf)
                    .findFirst()
                    .ifPresent(declaration -> {
                        throw new IllegalArgumentException(told + " of "
                                + TypeNames.describe(declaration.by().type()) + " has " + declaration.parameters()
                                + " parameters");
                    });
            if (erasures.isEmpty() || erasures.stream().anyMatch(erasure -> erasure.size() != arityOf)) {
                throw new IllegalArgumentException(told + " erases to its " + arityOf + " parameters, got " + erasures);
            }
        }

        /// The name of the method.
        ///
        /// @return the name
        public String name() {
            return signature.name();
        }

        /// What the method is to a class that extends or implements the type, whatever package the
        /// class is in (JLS 8.4.8, 8.4.8.4):
        ///
        /// - an abstract method with package access makes it `ABSTRACT`, whatever else there is: a
        ///   class of another package cannot implement it, and javac asks for it all the same;
        /// - else the declarations that do not have package access tell. The one of a class stands,
        ///   whatever it is — abstract beside a default method of an interface, the class has to
        ///   implement the method; `final`, none overrides it. Without one, the method is `ABSTRACT`
        ///   if an interface leaves it abstract, and `DEFAULT` otherwise;
        /// - with package access alone, the method is what the nearest class makes it.
        ///
        /// @return the dispatch
        public Dispatch standing() {
            boolean hiddenAbstract = declarations.stream()
                    .anyMatch(declaration ->
                            declaration.by() instanceof Declarer.OfClass(var _, var visibility, var method)
                                    && visibility == Visibility.PACKAGE
                                    && method == ClassMethod.ABSTRACT);
            if (hiddenAbstract) {
                return Dispatch.ABSTRACT;
            }
            List<Declarer> reached = declarations.stream()
                    .map(Declared::by)
                    .filter(by -> by.visibility() != Visibility.PACKAGE)
                    .toList();
            if (reached.isEmpty()) {
                return declarations.getFirst().by().dispatch();
            }
            return reached.stream()
                    .filter(Declarer.OfClass.class::isInstance)
                    .findFirst()
                    .map(Declarer::dispatch)
                    .orElseGet(() -> reached.stream().anyMatch(by -> by.dispatch() == Dispatch.ABSTRACT)
                            ? Dispatch.ABSTRACT
                            : Dispatch.DEFAULT);
        }
    }

    /// A constructor a type declares, in terms of the positions of the type
    /// parameters of the type and of its own.
    ///
    /// @param visibility who may call the constructor
    /// @param signature the signature the method table of the type knows the constructor by, under the
    ///                  simple name of the class
    /// @param arity whether the last parameter is of variable arity
    /// @param typeParams the type parameters of the constructor itself; usually none
    /// @param params the parameter types, in order; that of a variable arity parameter is its array type
    /// @param throwsTypes the exception types of the `throws` clause: classes and type parameters
    record Constructor(
            Visibility visibility,
            MethodTableTemplate.Signature signature,
            Arity arity,
            List<Variable> typeParams,
            List<Type> params,
            List<Type> throwsTypes) {

        /// @throws IllegalArgumentException if `signature` does not have a parameter per parameter of
        ///                                  `params`, a type mentions a type parameter the constructor
        ///                                  does not have, or the last parameter of a constructor of
        ///                                  variable arity is no array
        public Constructor {
            typeParams = List.copyOf(typeParams);
            params = List.copyOf(params);
            throwsTypes = List.copyOf(throwsTypes);
            String told = "constructor " + signature;
            if (signature.params().size() != params.size()) {
                throw new IllegalArgumentException(told + " has " + params.size() + " parameters");
            }
            requireOwn(typeParams, Stream.concat(params.stream(), throwsTypes.stream()), told);
            requireArity(arity, params, told);
        }
    }

    /// A name a field or a member type of the type, or of a supertype of it,
    /// takes in the body of a class that extends or implements the type: there
    /// the simple name means the member, not a type of that name the class
    /// imports (JLS 6.4.1, 6.4.2). A member with package access takes its name
    /// in a subclass of its package alone, which the heritage does not tell
    /// apart.
    sealed interface Named permits Named.Field, Named.MemberType {

        /// The simple name.
        ///
        /// @return the name
        String name();

        /// The class or interface that declares the member.
        ///
        /// @return the type
        ClassDesc declaredBy();

        /// Who may name the member.
        ///
        /// @return the visibility
        Visibility visibility();

        /// A field: as an expression its name means the field, so a type of that name is obscured
        /// where a qualified name starts with it (JLS 6.4.2).
        ///
        /// @param name the name of the field
        /// @param declaredBy the class or interface that declares it
        /// @param visibility who may name it
        record Field(String name, ClassDesc declaredBy, Visibility visibility) implements Named {
            /// @throws IllegalArgumentException if `declaredBy` is not a class or interface
            public Field {
                requireDeclarer(declaredBy);
            }
        }

        /// A member class or interface: its name shadows a type of that name everywhere in the body
        /// (JLS 6.4.1).
        ///
        /// @param name the simple name of the member type
        /// @param declaredBy the class or interface it is a member of
        /// @param visibility who may name it
        record MemberType(String name, ClassDesc declaredBy, Visibility visibility) implements Named {
            /// @throws IllegalArgumentException if `declaredBy` is not a class or interface
            public MemberType {
                requireDeclarer(declaredBy);
            }
        }
    }

    private static void requireOnce(List<? extends Method<?>> methods) {
        List<MethodTableTemplate.Signature> signatures = methods.stream()
                .<MethodTableTemplate.Signature>map(Method::signature)
                .toList();
        if (Set.copyOf(signatures).size() != signatures.size()) {
            throw new IllegalArgumentException("a heritage tells a method once, got " + signatures);
        }
    }

    /// A type that mentions no type parameter any more, as it is written.
    private static TypeRef plain(Type type) {
        return switch (type) {
            case Type.Fixed(TypeRef fixed) -> fixed;
            case Type.OfType _, Type.OfMember _, Type.Array _, Type.Parameterized _ ->
                throw new IllegalStateException("a type parameter is left in " + type);
        };
    }

    private static ClassOrInterfaceTypeRef thrown(TypeRef thrown) {
        return switch (thrown) {
            case ClassOrInterfaceTypeRef type -> type;
            case ArrayTypeRef array ->
                throw new IllegalArgumentException("an exception type is no array, got " + TypeNames.describe(array));
            case PrimitiveTypeRef primitive ->
                throw new IllegalArgumentException("an exception type is no primitive, got " + primitive.sourceName());
        };
    }

    private static void requirePosition(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("a type parameter is at a position from 0, got " + index);
        }
    }

    private static void requireDeclarer(ClassDesc declarer) {
        if (!declarer.isClassOrInterface()) {
            throw new IllegalArgumentException(
                    "a member is declared by a class or interface, got " + declarer.displayName());
        }
    }

    /// Rejects a member whose types mention a type parameter of its own that it does not have.
    private static void requireOwn(List<Variable> typeParams, Stream<Type> types, String told) {
        Stream.concat(types, typeParams.stream().flatMap(variable -> variable.bounds().stream()))
                .flatMap(Type::ofMember)
                .filter(index -> index >= typeParams.size())
                .findFirst()
                .ifPresent(index -> {
                    throw new IllegalArgumentException(
                            told + " mentions its type parameter ^" + index + ", and has " + typeParams.size());
                });
    }

    private static void requireArity(Arity arity, List<Type> params, String told) {
        boolean valid =
                switch (arity) {
                    case FIXED -> true;
                    case VARIABLE ->
                        !params.isEmpty()
                                && switch (params.getLast()) {
                                    case Type.Array _ -> true;
                                    case Type.Fixed(TypeRef type) -> type instanceof ArrayTypeRef;
                                    case Type.OfType _, Type.OfMember _, Type.Parameterized _ -> false;
                                };
                };
        if (!valid) {
            throw new IllegalArgumentException(told + " is of variable arity, so its last parameter is an array");
        }
    }
}
