package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;

import java.util.ArrayList;
import java.util.List;

/// The stage of the header of a class that extends `S`, from
/// [ClassHeader#extends_]: the class names its superinterfaces, says which
/// methods of theirs its superclass implements for it ([#inherited]), then
/// begins its members ([#members()]).
///
/// ```java
/// var sh = h.extends_(Thread_.extend(h.self()));
/// var asRunnable = sh.implements_(Runnable_.implement(h.self()).run(sh.inherited(Thread_.run)));
/// var cb = sh.members();
/// var make = cb.declareConstructor();
/// var bodies = cb.bodies(make, ctor -> ctor.super_(Thread_.new_, (b, self) -> b.end()));
/// ```
///
/// **A subclass has a constructor.** The stage this one ends in declares the
/// constructors of the class with its other members, and its bodies begin
/// with that of one ([TypedSubclassBuilder#bodies(CtorRef0,
/// TypedSubclassBodies.CtorBody0)]): a class that extends `S` and calls no
/// constructor of `S` is not written.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class SubclassHeader<Self, S> extends SubclassHeaderArities<Self, S> {
    private final ClassDeclaration<Self> declaration;
    private final ExtendableClassToken<S> superclass;

    SubclassHeader(ClassDeclaration<Self> declaration, ExtendableClassToken<S> superclass) {
        this.declaration = declaration;
        this.superclass = superclass;
    }

    @Override
    ClassDeclaration<Self> declaration() {
        return declaration;
    }

    private ExtendableClassToken<S> superclass() {
        return superclass;
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }

    /// Ends the header: the declarations of the members of the class follow.
    ///
    /// @return the builder of the members
    /// @throws IllegalArgumentException if the class said it overrides a method no supertype of it has
    /// @throws IllegalStateException if the members have begun already
    public TypedSubclassBuilder<Self, S> members() {
        return declaration.members(superclass);
    }

    /// The fact of the class being declared, see [Header#self()].
    ///
    /// @return the self token
    public FinalClassToken<Self> self() {
        return declaration().self();
    }

    /// The witness that the class is of its superclass: what [TypedSubclassBuilder#asSuper()]
    /// gives among the members, here for what the header says of a class that is an exception
    /// ([#throwing(Upcast, ClassToken, ClassToken[])]).
    ///
    /// @return the witness
    public Upcast<Self, S> asSuper() {
        return new Upcast<>(declaration().self(), superclass());
    }

    /// The stage with a `throws` clause: the methods it says the class overrides declare `exceptions`.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the stage that declares `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingOverriding<Self> throwing(ClassToken<? extends Throwable>... exceptions) {
        return throwing(List.of(exceptions));
    }

    /// The stage with a `throws` clause that declares the exceptions of a list, which an element of a
    /// stream gives: [#throwing(ClassToken[])] renders the same. An empty list is no exception.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the stage that declares `exceptions`
    public final ThrowingOverriding<Self> throwing(List<? extends ClassToken<? extends Throwable>> exceptions) {
        return new ThrowingOverriding<>(declaration(), List.copyOf(exceptions));
    }

    /// The stage with a `throws` clause that names a class known to be an exception by a witness
    /// alone — the class being declared, which has no bound to tell it: `sh.throwing(sh.asSuper(),
    /// h.self())` of a class that extends `Exception`.
    ///
    /// @param witness the witness that `exception` is a `Throwable`
    /// @param exception the class the witness is of
    /// @param others the other exception types of the clause, after it
    /// @param <E> the class the witness is of
    /// @return the stage that declares the exceptions
    /// @throws IllegalArgumentException if the witness is of another class than `exception`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <E> ThrowingOverriding<Self> throwing(
            Upcast<E, ? extends Throwable> witness,
            ClassToken<E> exception,
            ClassToken<? extends Throwable>... others) {
        return throwing(witness, exception, List.of(others));
    }

    /// The stage with a `throws` clause of the others of a list, which an element of a stream gives:
    /// [#throwing(Upcast, ClassToken, ClassToken[])] renders the same. An empty list is no other exception.
    ///
    /// @param witness the witness that `exception` is a `Throwable`
    /// @param exception the class the witness is of
    /// @param others the other exception types of the clause, after it
    /// @param <E> the class the witness is of
    /// @return the stage that declares the exceptions
    /// @throws IllegalArgumentException if the witness is of another class than `exception`
    public final <E> ThrowingOverriding<Self> throwing(
            Upcast<E, ? extends Throwable> witness,
            ClassToken<E> exception,
            List<? extends ClassToken<? extends Throwable>> others) {
        List<ClassToken<? extends Throwable>> declared = new ArrayList<>(others.size() + 1);
        declared.add(Upcast.throwable(witness, exception));
        declared.addAll(others);
        return new ThrowingOverriding<>(declaration(), List.copyOf(declared));
    }

    /// The class implements an interface, by the proof that it has every abstract method of it;
    /// see [ObjectHeader#implements_]. A step of the proof takes a method the class overrides
    /// ([Overriding]) or one its superclass implements for it ([#inherited]).
    ///
    /// @param proof the proof that the class has every abstract method of the interface
    /// @param <I> the interface
    /// @return the witness that the class is of the interface
    /// @throws IllegalArgumentException as [ObjectHeader#implements_] tells, and if a method of the
    ///     superclass a step took cannot implement the method of the interface: of other parameters
    ///     or another result, or declaring a checked exception it does not (JLS 8.4.8.3)
    /// @throws IllegalStateException if the members of the class have begun
    /// @throws TargetClasspathMismatchException if the metamodel of the interface, or of a type
    ///     argument of it, does not hold on the target classpath
    public <I> Upcast<Self, I> implements_(Complete<Self, I> proof) {
        return declaration().implement(proof);
    }

    // `inherited(method)`, of the fact of a method of the superclass of any number of parameters, is generated into
    // the superclass of this class (SubclassHeaderArities, see TypedCodegen). It says the class has the method by
    // inheriting it: what a step of the proof that the class implements an interface takes where the superclass has
    // the method of the interface already — `Thread.run()` for `Runnable`. That the superclass has the method with a
    // body, `public`, and as the fact tells it is checked there, and that it can stand for the method of the
    // interface, where the step is given to `implements_`.
}
