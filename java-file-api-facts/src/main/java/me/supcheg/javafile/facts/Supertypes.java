package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.lang.constant.ClassDesc;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/// The parameterized supertypes of a generic class or interface, written in
/// terms of its own type parameters: for `ArrayList<E>`, the type parameters
/// `[E]` and the supertypes `AbstractList<E>`, `List<E>`, `Collection<E>`,
/// `Iterable<E>`, and so on.
///
/// Runtime data generics cannot carry: the typed layer reads it to tell a
/// checked cast to a parameterized type (JLS 5.1.6.2), such as
/// `(ArrayList<String>) listOfStrings`, from an unchecked one. A token that
/// records [#NONE] is never the target of such a cast.
///
/// @param typeParameters the type parameters of the generic type, in declaration order
/// @param supertypes every parameterized proper supertype, transitively, one per generic
///                   class or interface; their type variables are among `typeParameters`
public record Supertypes(List<TypeVarRef> typeParameters, List<ParameterizedTypeRef> supertypes) {

    /// No recorded supertypes: casts to a parameterization of the type are
    /// checked only when it is reifiable.
    public static final Supertypes NONE = new Supertypes(List.of(), List.of());

    /// @throws IllegalArgumentException if two type parameters share a name,
    ///                                  two supertypes share a generic type,
    ///                                  or a supertype names an undeclared type variable
    public Supertypes {
        typeParameters = List.copyOf(typeParameters);
        supertypes = List.copyOf(supertypes);
        Set<String> names = new HashSet<>();
        for (TypeVarRef parameter : typeParameters) {
            if (!names.add(parameter.name())) {
                throw new IllegalArgumentException("duplicate type parameter " + parameter.name());
            }
        }
        Set<ClassDesc> raws = new HashSet<>();
        for (ParameterizedTypeRef supertype : supertypes) {
            if (!raws.add(supertype.raw())) {
                throw new IllegalArgumentException(
                        "two supertypes of generic type " + supertype.raw().displayName());
            }
            TypeVariables.requireDeclared(supertype, names, "a supertype");
        }
    }

    /// The supertype whose generic class or interface is `raw`, if recorded.
    ///
    /// @param raw the generic class or interface
    /// @return the supertype, in terms of [#typeParameters()]
    public Optional<ParameterizedTypeRef> supertype(ClassDesc raw) {
        return supertypes.stream().filter(s -> s.raw().equals(raw)).findFirst();
    }

    /// The supertype whose generic class or interface is `raw`, with the
    /// type parameters replaced by `arguments`.
    ///
    /// @param raw the generic class or interface
    /// @param arguments the types standing for [#typeParameters()], in order
    /// @return the substituted supertype, if recorded
    /// @throws IllegalArgumentException if `arguments` does not match [#typeParameters()] in size
    public Optional<ParameterizedTypeRef> supertype(ClassDesc raw, List<TypeRef> arguments) {
        if (arguments.size() != typeParameters.size()) {
            throw new IllegalArgumentException(
                    "expected " + typeParameters.size() + " type arguments, got " + arguments.size());
        }
        Map<String, TypeRef> env = typeParameters.stream()
                .collect(Collectors.toMap(TypeVarRef::name, p -> arguments.get(typeParameters.indexOf(p))));
        return supertype(raw).map(s -> (ParameterizedTypeRef) substitute(s, env));
    }

    private static TypeRef substitute(TypeRef type, Map<String, TypeRef> env) {
        return switch (type) {
            case TypeVarRef var -> env.getOrDefault(var.name(), var);
            case ParameterizedTypeRef p ->
                new ParameterizedTypeRef(
                        p.raw(), p.args().stream().map(a -> substitute(a, env)).toList(), p.annotations());
            case ArrayTypeRef array -> new ArrayTypeRef(substitute(array.component(), env), array.annotations());
            case ClassTypeRef cls -> cls;
            case PrimitiveTypeRef primitive -> primitive;
        };
    }

    private static TypeArg substitute(TypeArg arg, Map<String, TypeRef> env) {
        return switch (arg) {
            case ExactTypeArg exact -> new ExactTypeArg(substitute(exact.type(), env));
            case ExtendsTypeArg bound -> new ExtendsTypeArg(substitute(bound.bound(), env));
            case SuperTypeArg bound -> new SuperTypeArg(substitute(bound.bound(), env));
            case UnboundedTypeArg unbounded -> unbounded;
        };
    }
}
