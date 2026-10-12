package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Inherited0;
import me.supcheg.javafile.facts.Inherited1;
import me.supcheg.javafile.facts.Inherited2;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidInherited0;
import me.supcheg.javafile.facts.VoidInherited1;
import me.supcheg.javafile.facts.VoidInherited2;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;

import java.util.List;
import java.util.stream.Stream;

/// The stage of the header of a class that extends `S`, from
/// [ClassHeader#extends_]: the class names its superinterfaces, says which
/// methods of theirs its superclass implements for it ([#inherited]), then
/// begins its members with its first constructor ([SubclassOverriding#body]).
///
/// ```java
/// var sh = h.extends_(Thread_.extend(h.self()));
/// var asRunnable = sh.implements_(Runnable_.implement(h.self()).run(sh.inherited(Thread_.run)));
/// var cb = sh.body(sh.declareConstructor(), ctor -> ctor.super_(Thread_.new_, (b, self) -> b.end()));
/// ```
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class SubclassHeader<Self, S> extends SubclassOverriding<Self, S> {

    SubclassHeader(ClassDeclaration<Self> declaration, ExtendableClassToken<S> superclass) {
        super(declaration, superclass);
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
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

    /// The stage with a `throws` clause: the methods it says the class overrides, and the first
    /// constructor it ends the header with, declare `exceptions`.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the stage that declares `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingSubclassHeader<Self, S> throwing(ClassToken<? extends Throwable>... exceptions) {
        return new ThrowingSubclassHeader<>(declaration(), superclass(), List.of(exceptions));
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
    public final <E> ThrowingSubclassHeader<Self, S> throwing(
            Upcast<E, ? extends Throwable> witness,
            ClassToken<E> exception,
            ClassToken<? extends Throwable>... others) {
        return new ThrowingSubclassHeader<>(
                declaration(),
                superclass(),
                Stream.concat(Stream.of(Upcast.throwable(witness, exception)), Stream.of(others))
                        .toList());
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

    /// Says the class has a method by inheriting it from its superclass: what a step of the proof
    /// that the class implements an interface takes where the superclass has the method of the
    /// interface already — `Thread.run()` for `Runnable`. The fact is of the superclass or of a
    /// class above it, which javac holds the generator to; that the superclass has the method with
    /// a body, `public`, and as the fact tells it is checked here, and that it can stand for the
    /// method of the interface, where the step is given to `implements_`.
    ///
    /// @param method the fact of the method of the superclass
    /// @param <R> the result type
    /// @return how the class has the method: by inheritance, with no method of its own
    /// @throws IllegalArgumentException if the superclass has no such method on the target classpath, or
    ///     has it abstract, `static` or not `public`, or otherwise than the fact tells
    /// @throws IllegalStateException if the header of the class has ended
    public <R> Inherited0<Self, R> inherited(MethodRef0<? super S, R> method) {
        return UnsafeFacts.inherited0(declaration().inherit(method));
    }

    /// Says the class has a method with one parameter by inheriting it from its superclass, see
    /// [#inherited(MethodRef0)].
    ///
    /// @param method the fact of the method of the superclass
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return how the class has the method
    /// @throws IllegalArgumentException as [#inherited(MethodRef0)]
    /// @throws IllegalStateException if the header of the class has ended
    public <R, A1> Inherited1<Self, R, A1> inherited(MethodRef1<? super S, R, A1> method) {
        return UnsafeFacts.inherited1(declaration().inherit(method));
    }

    /// Says the class has a method with two parameters by inheriting it from its superclass, see
    /// [#inherited(MethodRef0)].
    ///
    /// @param method the fact of the method of the superclass
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return how the class has the method
    /// @throws IllegalArgumentException as [#inherited(MethodRef0)]
    /// @throws IllegalStateException if the header of the class has ended
    public <R, A1, A2> Inherited2<Self, R, A1, A2> inherited(MethodRef2<? super S, R, A1, A2> method) {
        return UnsafeFacts.inherited2(declaration().inherit(method));
    }

    /// Says the class has a `void` method by inheriting it from its superclass, see
    /// [#inherited(MethodRef0)].
    ///
    /// @param method the fact of the method of the superclass
    /// @return how the class has the method
    /// @throws IllegalArgumentException as [#inherited(MethodRef0)]
    /// @throws IllegalStateException if the header of the class has ended
    public VoidInherited0<Self> inherited(VoidMethodRef0<? super S> method) {
        return UnsafeFacts.voidInherited0(declaration().inherit(method));
    }

    /// Says the class has a `void` method with one parameter by inheriting it from its superclass,
    /// see [#inherited(MethodRef0)].
    ///
    /// @param method the fact of the method of the superclass
    /// @param <A1> the parameter type
    /// @return how the class has the method
    /// @throws IllegalArgumentException as [#inherited(MethodRef0)]
    /// @throws IllegalStateException if the header of the class has ended
    public <A1> VoidInherited1<Self, A1> inherited(VoidMethodRef1<? super S, A1> method) {
        return UnsafeFacts.voidInherited1(declaration().inherit(method));
    }

    /// Says the class has a `void` method with two parameters by inheriting it from its
    /// superclass, see [#inherited(MethodRef0)].
    ///
    /// @param method the fact of the method of the superclass
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return how the class has the method
    /// @throws IllegalArgumentException as [#inherited(MethodRef0)]
    /// @throws IllegalStateException if the header of the class has ended
    public <A1, A2> VoidInherited2<Self, A1, A2> inherited(VoidMethodRef2<? super S, A1, A2> method) {
        return UnsafeFacts.voidInherited2(declaration().inherit(method));
    }
}
