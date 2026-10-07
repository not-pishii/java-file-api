package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// What a class that extends a type, or implements it, has to know of the
/// type beyond the facts of its members: every method the type has that is
/// not `private`, declared or inherited, as a member of the type, and every
/// constructor that is not `private`.
///
/// The facts of a metamodel name the `public` and `protected` members a type
/// declares; the rules of inheritance (JLS 8.1.1.1, 8.4.8, 9.4.1.3) speak of
/// every member a subclass inherits or could clash with — a method with
/// package access, one a supertype that is not `public` declares, one whose
/// signature no fact can tell. A fact names a member; the heritage tells
/// what holds of it.
///
/// The types of a heritage are in terms of the type parameters of the type
/// ([TypeShape#typeParameters()]), by name.
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

    /// The heritage of a type.
    ///
    /// @param methods the methods of the type that are not `private`, instance and `static`, declared
    ///                and inherited — but not the `static` ones of its superinterfaces, which are not
    ///                inherited —, each once
    /// @param constructors the constructors the type declares that are not `private`; none for an interface
    record Told(List<Method> methods, List<Constructor> constructors) implements Heritage {

        /// @throws IllegalArgumentException if two methods have one signature
        public Told {
            methods = List.copyOf(methods);
            constructors = List.copyOf(constructors);
            List<MethodTableTemplate.Signature> signatures =
                    methods.stream().map(Method::signature).toList();
            if (Set.copyOf(signatures).size() != signatures.size()) {
                throw new IllegalArgumentException("a heritage tells a method once, got " + signatures);
            }
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
        /// A `static` method, which an instance method neither overrides nor is hidden by.
        STATIC
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
    sealed interface Result permits Result.Nothing, Result.Of {

        /// The result of a `void` method, [Nothing].
        Nothing NOTHING = new Nothing();

        /// No result: the method is `void`.
        record Nothing() implements Result {}

        /// A result of a type.
        ///
        /// @param type the type, as a member of the type the heritage is of
        record Of(TypeRef type) implements Result {}
    }

    /// A method of a type, declared or inherited, as a member of it.
    ///
    /// @param visibility who may name the method
    /// @param dispatch what the method is to a class that inherits it
    /// @param declaredBy the class or interface that declares the method
    /// @param signature the signature the method table of the type knows the method by
    ///                  ([TypeShape#methods()]): its identity among the methods of the type, and the one
    ///                  a fact of the method names ([Invocable#declared()])
    /// @param typeParams the type parameters of the method itself, with their bounds; empty unless generic
    /// @param params the parameter types, in order; that of a variable arity parameter is its array type
    /// @param arity whether the last parameter is of variable arity
    /// @param result the result
    /// @param throwsTypes the exception types of the `throws` clause: classes and type variables
    /// @param erasures the erased parameter types of the method as it is declared and of every method it
    ///                 overrides as that one is declared, whatever the type arguments: the signatures
    ///                 javac makes its bridges of, and no method of a subclass that does not override the
    ///                 method may have (JLS 8.4.8.3)
    /// @param overrides the classes and interfaces that declare the methods this one overrides, sorted
    ///                  by binary name
    record Method(
            Visibility visibility,
            Dispatch dispatch,
            ClassDesc declaredBy,
            MethodTableTemplate.Signature signature,
            List<TypeParam> typeParams,
            List<TypeRef> params,
            Arity arity,
            Result result,
            List<ClassOrInterfaceTypeRef> throwsTypes,
            Set<List<ClassDesc>> erasures,
            List<ClassDesc> overrides)
            implements Heritage.Member {

        /// @throws IllegalArgumentException if `declaredBy` or one of `overrides` is not a class or
        ///                                  interface, `signature` does not have a parameter per
        ///                                  parameter of `params`, an erasure does not, there is no
        ///                                  erasure at all, or the last parameter of a method of
        ///                                  variable arity is no array
        public Method {
            requireDeclarer(declaredBy);
            typeParams = List.copyOf(typeParams);
            params = List.copyOf(params);
            throwsTypes = List.copyOf(throwsTypes);
            erasures = Set.copyOf(erasures);
            overrides = List.copyOf(overrides);
            overrides.forEach(Heritage::requireDeclarer);
            String told = "method " + signature + " of " + TypeNames.describe(declaredBy);
            if (signature.params().size() != params.size()) {
                throw new IllegalArgumentException(told + " has " + params.size() + " parameters");
            }
            int arityOf = signature.params().size();
            if (erasures.isEmpty() || erasures.stream().anyMatch(erasure -> erasure.size() != arityOf)) {
                throw new IllegalArgumentException(
                        told + " erases to its " + params.size() + " parameters, got " + erasures);
            }
            requireArity(arity, params, told);
        }

        /// The name of the method.
        ///
        /// @return the name
        public String name() {
            return signature.name();
        }
    }

    /// A constructor a type declares.
    ///
    /// @param visibility who may call the constructor
    /// @param signature the signature the method table of the type knows the constructor by, under the
    ///                  simple name of the class
    /// @param typeParams the type parameters of the constructor itself, with their bounds; usually none
    /// @param params the parameter types, in order; that of a variable arity parameter is its array type
    /// @param arity whether the last parameter is of variable arity
    /// @param throwsTypes the exception types of the `throws` clause: classes and type variables
    record Constructor(
            Visibility visibility,
            MethodTableTemplate.Signature signature,
            List<TypeParam> typeParams,
            List<TypeRef> params,
            Arity arity,
            List<ClassOrInterfaceTypeRef> throwsTypes)
            implements Heritage.Member {

        /// @throws IllegalArgumentException if `signature` does not have a parameter per parameter of
        ///                                  `params`, or the last parameter of a constructor of variable
        ///                                  arity is no array
        public Constructor {
            typeParams = List.copyOf(typeParams);
            params = List.copyOf(params);
            throwsTypes = List.copyOf(throwsTypes);
            String told = "constructor " + signature;
            if (signature.params().size() != params.size()) {
                throw new IllegalArgumentException(told + " has " + params.size() + " parameters");
            }
            requireArity(arity, params, told);
        }
    }

    /// A member a heritage tells: a [Method] or a [Constructor].
    sealed interface Member permits Method, Constructor {

        /// Who may name the member.
        ///
        /// @return the visibility
        Visibility visibility();

        /// The signature the method table of the type knows the member by.
        ///
        /// @return the signature
        MethodTableTemplate.Signature signature();
    }

    private static void requireDeclarer(ClassDesc declarer) {
        if (!declarer.isClassOrInterface()) {
            throw new IllegalArgumentException(
                    "a member is declared by a class or interface, got " + declarer.displayName());
        }
    }

    private static void requireArity(Arity arity, List<TypeRef> params, String told) {
        boolean valid =
                switch (arity) {
                    case FIXED -> true;
                    case VARIABLE -> !params.isEmpty() && params.getLast() instanceof ArrayTypeRef;
                };
        if (!valid) {
            throw new IllegalArgumentException(told + " is of variable arity, so its last parameter is an array");
        }
    }
}
