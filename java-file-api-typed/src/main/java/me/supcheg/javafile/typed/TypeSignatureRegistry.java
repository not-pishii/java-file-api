package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTableTemplate;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The member signatures of a type that is not a class being declared (§6.5): an interface, a record
/// or an enum. It rejects what javac would reject in the rendered type — two methods of one erased
/// signature, two constructors of one parameter list, two fields of one name — and it allows a method
/// declared by name to override the one of a supertype, which is how an interface redeclares a method
/// and how a record or enum implements one.
///
/// When the declarations end ([#requireComplete(boolean)]) a record or enum that leaves an abstract
/// method of a supertype unimplemented is rejected; an interface may leave it abstract. Methods that
/// cannot be had together are rejected for every kind.
final class TypeSignatureRegistry {
    private final String owner;
    private final DeclaredToken<?> self;
    private final Inheritance inherited;
    private final List<Subtyping.Bounded> variables;
    private List<Invocable> methods = List.of();
    private Set<MethodSignature> overrides = Set.of();
    private List<Invocable> constructors = List.of();
    private Set<String> fields = Set.of();

    /// @param owner the type being declared, for messages
    /// @param self the token of the type being declared
    /// @param inherited what the type inherits
    /// @param variables the type parameters of the type, each with its bounds
    TypeSignatureRegistry(
            String owner, DeclaredToken<?> self, Inheritance inherited, List<Subtyping.Bounded> variables) {
        this.owner = owner;
        this.self = self;
        this.inherited = inherited;
        this.variables = List.copyOf(variables);
    }

    /// Registers a field.
    ///
    /// @param name the field name
    /// @throws IllegalArgumentException if a field of that name is declared already
    void field(String name) {
        if (fields.contains(name)) {
            throw new IllegalArgumentException("field " + name + " of " + owner
                    + " is already declared: field names are unique in a type (JLS 8.3)");
        }
        fields = Stream.concat(fields.stream(), Stream.of(name)).collect(Collectors.toUnmodifiableSet());
    }

    /// Registers a method declared by its name. A method of an instance that has the signature of one
    /// the type inherits overrides it; a static or private one may not.
    ///
    /// @param member the member being declared
    /// @throws IllegalArgumentException if javac would reject the member
    void method(Invocable member) {
        MethodSignature signature = member.signature();
        methods.stream()
                .filter(declared -> declared.signature().equals(signature))
                .findFirst()
                .ifPresent(clash -> {
                    throw new IllegalArgumentException("the method " + member + " of " + owner
                            + " clashes with the method " + clash + " declared before: both erase to "
                            + signature + " (JLS 8.4.2)");
                });
        Optional<Inheritance.Group> group = inherited.group(signature);
        if (member.kind() == InvocableKind.STATIC_METHOD) {
            group.ifPresent(had -> {
                throw new IllegalArgumentException("the static method " + member + " of " + owner
                        + " has the signature of " + had.stands() + ", which the type inherits: a static"
                        + " method neither overrides nor hides an instance method (JLS 8.4.8.2)");
            });
        } else {
            group.ifPresent(had -> {
                SignatureRegistry.requireOverrides(owner, member, had, declaring());
                overrides =
                        Stream.concat(overrides.stream(), Stream.of(signature)).collect(Collectors.toUnmodifiableSet());
            });
        }
        inherited.bridged(signature).ifPresent(entry -> {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " clashes with " + entry
                    + ": name clash, it erases as that method or one it overrides does, and does not override"
                    + " it (JLS 8.4.8.3)");
        });
        methods = Stream.concat(methods.stream(), Stream.of(member)).toList();
    }

    /// Registers a constructor declared by its parameter list.
    ///
    /// @param constructor the constructor
    /// @throws IllegalArgumentException if a constructor of the same erased parameter types is declared
    ///     already
    void constructor(Invocable constructor) {
        List<ClassDesc> params = constructor.signature().params();
        constructors.stream()
                .filter(declared -> declared.signature().params().equals(params))
                .findFirst()
                .ifPresent(clash -> {
                    throw new IllegalArgumentException("the constructor " + constructor + " of " + owner
                            + " clashes with the constructor " + clash + " declared before: both erase to the"
                            + " parameters " + constructor.signature() + " (JLS 8.8.2)");
                });
        constructors =
                Stream.concat(constructors.stream(), Stream.of(constructor)).toList();
    }

    /// The methods the type inherits under a signature, if it inherits any.
    ///
    /// @param signature the erased signature
    /// @return the group
    Optional<Inheritance.Group> inherited(MethodSignature signature) {
        return inherited.group(signature);
    }

    /// Whether a method of the signature overrides one the type inherits.
    ///
    /// @param signature the erased signature
    /// @return `true` if it overrides
    boolean overrides(MethodSignature signature) {
        return overrides.contains(signature);
    }

    private Subtyping.Declaring declaring() {
        return inherited.declaring(self).knowing(variables);
    }

    /// Rejects declarations that end leaving undone what only a method of the type can do.
    ///
    /// @param allowAbstract whether the type may leave the abstract methods of its supertypes abstract:
    ///     `true` for an interface alone
    /// @throws IllegalStateException if the type inherits methods it cannot have together and overrides
    ///     none of them, or leaves an abstract method unimplemented where it may not
    void requireComplete(boolean allowAbstract) {
        inherited
                .groups()
                .filter(group -> !overrides.contains(group.signature()))
                .flatMap(group -> group.unsettled().stream())
                .findFirst()
                .ifPresent(why -> {
                    throw new IllegalStateException("the declarations of " + owner + " cannot end here: it inherits"
                            + " methods it cannot have together and overrides none of them: " + why
                            + " — a type that has a method from several supertypes that do not agree on it"
                            + " overrides it, and an override is declared before bodies()");
                });
        if (allowAbstract) {
            return;
        }
        inherited
                .groups()
                .filter(group -> !overrides.contains(group.signature()))
                .filter(group -> group.entries().stream().anyMatch(Inheritance.Entry::leftAbstract))
                .findFirst()
                .ifPresent(group -> {
                    throw new IllegalStateException("the declarations of " + owner + " cannot end here: it inherits"
                            + " the abstract method " + group.stands() + " and declares no method of its signature"
                            + " — implement it before bodies()");
                });
    }

    /// The methods and constructors of the type, as a token of it tells them.
    ///
    /// @return the method table template
    MethodTableTemplate table() {
        Map<Boolean, Set<MethodTableTemplate.Signature>> declared = methods.stream()
                .collect(Collectors.partitioningBy(
                        method -> method.kind() == InvocableKind.INSTANCE_METHOD,
                        Collectors.mapping(Invocable::declared, Collectors.toSet())));
        Map<Boolean, List<Inheritance.Group>> had = inherited
                .groups()
                .collect(Collectors.partitioningBy(group -> switch (group.standing()) {
                    case ABSTRACT, DEFAULT, CONCRETE, FINAL -> true;
                    case STATIC -> false;
                }));
        Set<MethodTableTemplate.Signature> constructorSignatures =
                constructors.stream().map(Invocable::declared).collect(Collectors.toSet());
        Set<MethodTableTemplate.Signature> variableArity = Stream.concat(
                        methods.stream()
                                .filter(method -> method.arity() instanceof Arity.Variable)
                                .map(Invocable::declared),
                        constructors.stream()
                                .filter(constructor -> constructor.arity() instanceof Arity.Variable)
                                .map(Invocable::declared))
                .collect(Collectors.toSet());
        had.values().stream()
                .flatMap(List::stream)
                .filter(group -> group.arity() instanceof Arity.Variable)
                .map(TypeSignatureRegistry::inherited)
                .forEach(variableArity::add);
        return new MethodTableTemplate(
                Set.of(),
                Stream.concat(
                                declared.get(true).stream(),
                                had.get(true).stream().map(TypeSignatureRegistry::inherited))
                        .collect(Collectors.toSet()),
                Stream.concat(
                                declared.get(false).stream(),
                                had.get(false).stream().map(TypeSignatureRegistry::inherited))
                        .collect(Collectors.toSet()),
                constructorSignatures,
                variableArity);
    }

    private static MethodTableTemplate.Signature inherited(Inheritance.Group group) {
        return new MethodTableTemplate.Signature(
                group.signature().name(),
                group.signature().params().stream()
                        .<MethodTableTemplate.Param>map(MethodTableTemplate.Param::fixed)
                        .toList());
    }
}
