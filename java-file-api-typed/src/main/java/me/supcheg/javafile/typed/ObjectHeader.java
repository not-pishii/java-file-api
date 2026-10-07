package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;

/// The stage of the header of a class that extends `Object` ([Header]): the
/// class names its superinterfaces, then begins its members.
///
/// @param <Self> the brand of the class being declared
public final class ObjectHeader<Self> extends Header<Self> {

    ObjectHeader(ClassDeclaration<Self> declaration) {
        super(declaration);
    }

    /// The class implements an interface, by the proof that it has every abstract method of it —
    /// what the metamodel of the interface gives after a step per abstract method, each taking
    /// an intention of this header ([Overriding]):
    ///
    /// ```java
    /// var cmp = new Comparable_<>(h.self());
    /// var compareTo = h.overriding(cmp.compareTo_T);
    /// var oh = h.extendsObject();
    /// var asComparable = oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
    /// ```
    ///
    /// The class is rendered with the interface in its `implements` clause, in the order the
    /// interfaces are named. The brand `Self` has no bound, so the class is of the interface to
    /// the generator by the witness this gives, and by nothing else.
    ///
    /// The proof is of the interface as the generator was compiled against it, and is held here
    /// against the interface on the target classpath: an abstract method the target has and the
    /// proof has no step for is rejected, as is a step for a method the target has not. What the
    /// class inherits from the interface is merged with what it inherits already ([Inheritance]),
    /// and every method the class said it overrides is checked against it.
    ///
    /// @param proof the proof that the class has every abstract method of the interface
    /// @param <I> the interface
    /// @return the witness that the class is of the interface
    /// @throws IllegalArgumentException if the type is a class; is raw or has a wildcard type
    ///     argument (JLS 8.1.5); is a supertype of the class already, or a generic supertype of both
    ///     with other type arguments; if the proof does not hold on the target classpath; if a step
    ///     of it took an implementation this header did not make; if the methods of the interface
    ///     clash with those the class inherits already; or if a method the class said it overrides
    ///     cannot override one of the interface (JLS 8.4.8.3)
    /// @throws IllegalStateException if the members of the class have begun
    /// @throws TargetClasspathMismatchException if the metamodel of the interface, or of a type
    ///     argument of it, does not hold on the target classpath
    public <I> Upcast<Self, I> implements_(Complete<Self, I> proof) {
        return declaration().implement(proof);
    }

    /// Ends the header: the members of the class follow.
    ///
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method no supertype of it has
    /// @throws IllegalStateException if the members have begun already
    public TypedClassBuilder<Self> body() {
        return declaration().body();
    }
}
