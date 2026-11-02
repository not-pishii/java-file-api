package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// Helpers of the generated arity families.
final class Invocables {
    private Invocables() {}

    static String requireMethodName(String name) {
        return Identifiers.requireValid(name);
    }

    /// The type of a parameter in a fact.
    static <T> TypeToken<T> token(FactParam<T> param) {
        return switch (param) {
            case TypeToken<T> token -> token;
            case DeclaredParam<T>(TypeToken<T> token, MethodTableTemplate.Param _) -> token;
        };
    }

    /// The parameters of a member of `owner` as it declares them. A
    /// parameter declared by a type parameter of the type of `owner` must be
    /// of the type `owner` gives for it.
    static List<MethodTableTemplate.Param> declared(DeclaredToken<?> owner, FactParam<?>... params) {
        return Stream.of(params).map(param -> declared(owner, param)).toList();
    }

    private static MethodTableTemplate.Param declared(DeclaredToken<?> owner, FactParam<?> param) {
        return switch (param) {
            case TypeToken<?> token -> new MethodTableTemplate.Fixed(token.erasure());
            case DeclaredParam<?>(var token, var declared) ->
                switch (declared) {
                    case MethodTableTemplate.Fixed fixed -> fixed;
                    case MethodTableTemplate.Var variable -> {
                        List<ClassDesc> arguments = owner.argumentErasures();
                        if (variable.index() >= arguments.size()
                                || !variable.instantiate(arguments).equals(token.erasure())) {
                            throw new IllegalArgumentException("a parameter declared " + variable + " of " + owner
                                    + " is "
                                    + (variable.index() < arguments.size()
                                            ? TypeNames.describe(variable.instantiate(arguments))
                                            : "of no type parameter")
                                    + ", not " + token);
                        }
                        yield variable;
                    }
                };
        };
    }

    static <F> InterfaceToken<F> requireSam(DeclaredToken<F> owner, Invocable method) {
        if (!(owner instanceof InterfaceToken<F> iface)) {
            throw new IllegalArgumentException(describe(method) + " is not a method of an interface");
        }
        if (!iface.methods().abstractMethods().equals(Set.of(method.signature()))) {
            throw new IllegalArgumentException(describe(method) + " is not the single abstract method of " + iface
                    + ", whose abstract methods are " + iface.methods().abstractMethods());
        }
        return iface;
    }

    /// A constructor a subclass constructor calls, as [#describe] tells one `new` calls.
    static String describeSuper(Invocable constructor) {
        return "super " + describe(constructor).substring("new ".length());
    }

    /// The method a class declares to override a method of a supertype, as the fact its
    /// `OverridingN` types it by: of the class, with the result and the parameters of the
    /// implementation.
    // the generated `OverridingN` classes narrow the fact to the family their signature declares, and the
    // arity of the method table guarantees the family: the cast is the one place it is made
    @SuppressWarnings({"unchecked", "TypeParameterUnusedInFormals"})
    static <F extends Invocable> F own(Implemented.ByOverride how) {
        return (F) how.own();
    }

    /// How a class has a method, as an `ImplementationN` tells it.
    static String describe(Implemented how) {
        return switch (how) {
            case Implemented.ByOverride(Invocable overridden, Invocable own) -> own + " overriding " + overridden;
            case Implemented.ByInheritance(Invocable inherited) -> "inherited " + inherited;
        };
    }

    static String describe(Invocable invocable) {
        String params = invocable.params().stream().map(Object::toString).collect(Collectors.joining(", ", "(", ")"));
        return switch (invocable.kind()) {
            case CONSTRUCTOR -> "new " + invocable.owner() + params;
            case STATIC_METHOD ->
                "static " + result(invocable) + " " + invocable.owner() + "." + invocable.name() + params;
            case INSTANCE_METHOD -> result(invocable) + " " + invocable.owner() + "." + invocable.name() + params;
        };
    }

    /// The traits of a call of `member` with the explicit type arguments `typeArgs`.
    ///
    /// A fact of a declared generic method says how many type parameters it has
    /// ([MemberTraits#typeParams()]); one of a target generic method says it by the type arguments it
    /// was instantiated with ([MemberTraits#typeArgs()]). A call of any other member takes none.
    ///
    /// @param member the member the call is of, for messages
    /// @param traits the traits of its fact
    /// @param typeArgs the explicit type arguments of the call, in order
    /// @return `traits` with `typeArgs`
    /// @throws IllegalArgumentException if the member is not generic, or has another number of type parameters
    static MemberTraits withTypeArgs(Invocable member, MemberTraits traits, RefToken<?>[] typeArgs) {
        int declared = traits.typeParams().isEmpty()
                ? traits.typeArgs().size()
                : traits.typeParams().size();
        if (declared == 0) {
            throw new IllegalArgumentException("cannot call " + describe(member) + " with the explicit type"
                    + " arguments " + java.util.Arrays.toString(typeArgs) + ": the method has no type parameter"
                    + " of its own, and a call of it takes none");
        }
        if (typeArgs.length != declared) {
            throw new IllegalArgumentException("cannot call " + describe(member) + " with " + typeArgs.length
                    + " explicit type arguments: the method declares " + declared + " type parameter"
                    + (declared == 1 ? "" : "s") + ", and a call of it takes one type argument per one of them");
        }
        return traits.withTypeArgs(typeArgs);
    }

    private static String result(Invocable invocable) {
        return invocable.resultType().map(Object::toString).orElse("void");
    }
}
