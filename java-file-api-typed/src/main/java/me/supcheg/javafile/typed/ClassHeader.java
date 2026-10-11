package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;

/// The first stage of the header of a class ([Header]): the class has a
/// name, and says what it extends.
///
/// @param <Self> the brand of the class being declared
public final class ClassHeader<Self> extends Header<Self> {

    ClassHeader(ClassDeclaration<Self> declaration) {
        super(declaration);
    }

    /// The class extends `Object`: it inherits the methods of `Object`, as the target classpath
    /// has them.
    ///
    /// @return the stage that names the superinterfaces of the class
    /// @throws IllegalArgumentException if a method the class said it overrides cannot override
    ///     the one of `Object` of its signature
    /// @throws IllegalStateException if the class has its superclass already
    public ObjectHeader<Self> extendsObject() {
        declaration().extendsObject();
        return new ObjectHeader<>(declaration());
    }

    /// Takes the fact of a `protected` method out of its wrapper before the class has its
    /// superclass, for the one thing that needs it there: `h.overriding(h.protected_(base.hook))`,
    /// the intention to override a `protected` abstract method, which the proof `extends_` takes
    /// has a step for. Among the members the builder of a subclass does this by its type
    /// ([SuperAccess#protected_]); here the superclass is not named yet, so that the class is a
    /// subclass of the owner of the member is checked when it is — by `extends_` or `extendsObject`,
    /// one of which every declaration comes to.
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalStateException if the class has its superclass already
    public <F extends MemberFact> F protected_(Protected<?, F> held) {
        return declaration().open(held);
    }

    /// The class extends a class, by the proof that it has every abstract method of it — what the
    /// metamodel of the class gives after a step per abstract method, each taking an intention of
    /// this header ([Overriding]), or at once where the class has none:
    ///
    /// ```java
    /// var list = new AbstractList_<>(String_.TOKEN);
    /// var get = h.overriding(list.get_int);
    /// var size = h.overriding(new AbstractCollection_<>(String_.TOKEN).size);
    /// var sh = h.extends_(list.extend(h.self()).get_int(get).size(size));
    /// ```
    ///
    /// The class is rendered with the type in its `extends` clause. The brand `Self` has no bound,
    /// so the class is of its superclass to the generator by the witness of the stage this gives
    /// ([SubclassHeader#asSuper()]) and by the `S` in its type, and by nothing else.
    ///
    /// The proof is held against the class on the target classpath as that of an interface is
    /// ([ObjectHeader#implements_]): an abstract method the target has and the proof has no step
    /// for is rejected, as is a step for a method the target has not. What the class inherits is
    /// the heritage of the superclass there — its methods as members, whoever declares them —
    /// and every method the class said it overrides is checked against it.
    ///
    /// @param proof the proof that the class has every abstract method of the superclass
    /// @param <S> the superclass
    /// @return the stage that names the superinterfaces of the class and begins its members
    /// @throws IllegalArgumentException if the type is an interface; is raw or has a wildcard type
    ///     argument (JLS 8.1.4); if the proof does not hold on the target classpath; if a step of it
    ///     took an implementation this header did not make; or if a method the class said it overrides
    ///     cannot override the one of the superclass (JLS 8.4.8.3)
    /// @throws IllegalStateException if the class has its superclass already
    /// @throws TargetClasspathMismatchException if the metamodel of the superclass, or of a type argument
    ///     of it, does not hold on the target classpath
    public <S> SubclassHeader<Self, S> extends_(Complete<Self, S> proof) {
        return new SubclassHeader<>(declaration(), declaration().extend(proof));
    }
}
