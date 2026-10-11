package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TypeVarToken;

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

    /// Names the class this one is about to extend, before it extends it: the stage that takes the
    /// facts of the `protected` members of `S` out, for the intentions the proof needs
    /// ([ExtendingHeader]). A class whose superclass has no `protected` abstract method goes
    /// straight to [#extends_].
    ///
    /// @param superclass the class, with its type arguments — `list.token` of a metamodel instance
    /// @param <S> the class
    /// @return the stage of a class about to extend `S`
    public <S> ExtendingHeader<Self, S> extending(ExtendableClassToken<S> superclass) {
        return new ExtendingHeader<>(declaration(), superclass);
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

    /// Gives a type parameter of the class one more bound, an interface: `<T extends Number &
    /// Comparable<T>>`. The leftmost bound of a type parameter is a type of the generator
    /// ([TypeParameter]); every other is said here, before the class names its superclass, and
    /// gives the witness by which an expression of the type parameter is one of the interface —
    /// as a supertype of the class gives one of the class ([Upcast]).
    ///
    /// ```java
    /// var comparable = new Comparable_<>(t);
    /// Upcast<T, Comparable<T>> asComparable = h.bound(t, comparable.token);
    /// ...
    /// call(asComparable.apply(mine), comparable.compareTo_T, other)
    /// ```
    ///
    /// A bound may mention the type parameter itself and the others of the class, which a
    /// leftmost bound cannot: `T extends Comparable<T>` is rendered `<T extends Object &
    /// Comparable<T>>` — the erasure of `T` is that of its leftmost bound (JLS 4.6), which the
    /// token of `T` was made with.
    ///
    /// The bounds of a type parameter are held to what a class with those supertypes is held to
    /// (JLS 4.4, 4.9): an interface is a bound once, a generic interface is not reached with other
    /// type arguments through two bounds, and the methods of the bounds do not clash. So a further
    /// bound needs the heritage of every bound — a leftmost bound that is a `final` class, has a
    /// wildcard type argument or has no full metamodel takes no further bound.
    ///
    /// @param variable the token of the type parameter, as the specification of the class was given it
    /// @param bound the interface, with its type arguments
    /// @param <T> the type parameter
    /// @param <B> the interface
    /// @return the witness that the type parameter is of the interface
    /// @throws IllegalArgumentException if `variable` is no type parameter of this class; `bound` is raw,
    ///     has a wildcard type argument or tells no heritage; is a bound of the type parameter already,
    ///     or a generic supertype of two bounds with other type arguments; or the methods of the bounds
    ///     clash
    /// @throws IllegalStateException if the class has its superclass already, or `bound` mentions a type
    ///     parameter of another class
    public <T, B> Upcast<T, B> bound(TypeVarToken<T> variable, InterfaceToken<B> bound) {
        return declaration().bound(variable, bound);
    }
}
