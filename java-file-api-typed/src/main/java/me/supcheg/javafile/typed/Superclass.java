package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.Invocable;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.stream.Stream;

/// The superclass of a class being declared: `Object`, which the class
/// extends by saying nothing, or the class it names in its `extends` clause.
/// Either way with its heritage as the target classpath has the class.
sealed interface Superclass {

    /// The token of the superclass.
    ///
    /// @return the token
    ClassToken<?> token();

    /// The superclass with its heritage.
    ///
    /// @return the heritage, in terms of the type arguments of the token
    Inheritance.Supertype heritage();

    /// The erased superclass chain of the class being declared: the superclass, then its own.
    ///
    /// @return the chain, ending in `java.lang.Object`
    default List<ClassDesc> chain() {
        return Stream.concat(Stream.of(token().erasure()), token().superclasses().stream())
                .toList();
    }

    /// The methods the superclass has, as a member of its own, under the signature of `fact`: what
    /// `super.m()` calls, and what the class has from the superclass without a method of its own.
    ///
    /// @param owner the class being declared, for messages
    /// @param fact the fact of a method of the superclass, or of a class above it
    /// @return the members of that signature, at least one, each as `fact` tells it
    /// @throws IllegalArgumentException if the superclass has no such method on the target classpath, or
    ///     has it with other parameters, another result or another `throws` clause than `fact` tells
    default List<Inheritance.Entry> member(String owner, Invocable fact) {
        if (!fact.traits().typeArgs().isEmpty()) {
            // The heritage tells the method by its type parameters, the fact by its type arguments.
            throw new IllegalArgumentException(owner + " does not have " + fact + " from its superclass " + token()
                    + " in a way the typed layer can hold against the fact: the method is generic, and a generic"
                    + " method of a superclass is neither called as super." + fact.name()
                    + "(...) nor had for one of an interface");
        }
        List<Inheritance.Entry> members = heritage().members(fact.signature()).toList();
        if (members.isEmpty()) {
            throw new IllegalArgumentException(owner + " does not have " + fact + " from its superclass " + token()
                    + ": on the target classpath the class has no method " + fact.signature());
        }
        members.forEach(member -> member.differsFrom(fact).ifPresent(difference -> {
            throw new IllegalArgumentException(owner + " does not have " + fact + " from its superclass " + token()
                    + " as the fact tells it: as a member of the superclass the method is " + member + ", and "
                    + difference + " — a class between overrides it; name the method by the fact of that class");
        }));
        return members;
    }

    /// `java.lang.Object`: the class has no `extends` clause.
    ///
    /// @param token `Object`
    /// @param heritage `Object` with its heritage
    record OfObject(ClassToken<?> token, Inheritance.Supertype heritage) implements Superclass {}

    /// The class of the `extends` clause.
    ///
    /// @param token the superclass
    /// @param heritage the superclass with its heritage
    record Named(ExtendableClassToken<?> token, Inheritance.Supertype heritage) implements Superclass {}
}
