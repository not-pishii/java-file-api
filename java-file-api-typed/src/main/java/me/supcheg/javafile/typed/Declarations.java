package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Set;

/// The declarations of the methods and constructors of a class being
/// declared (§6.5): what the builder of its members ([MemberBuilder])
/// declares of them itself, and what its `throwing` declares with a `throws`
/// clause ([ThrowingDeclarations]). The two differ in the clause alone.
///
/// **A declaration has no body.** It gives the fact of the member; the body
/// is given to the stage after, [MemberBodies], which the builder of the
/// members ends in. So every member of the class is declared before any body
/// is built, and a body is lowered where it is defined, against all the
/// class has.
///
/// **Overriding is by the fact.** A method the class inherits is overridden
/// by naming its fact, never by declaring a method of its name:
/// `declareOverride(Object_.toString)` declares `@Override public String
/// toString()`. A fact of the superclass `S` or of a class above it is taken
/// as it is — javac holds the generator to it, `S` being the type the class
/// was declared to extend; the method of an interface by the witness that
/// the class is of the interface, `declareOverride(up, fact)`. The witness is
/// asked for its type alone: that the compiler of the generator takes it is
/// the proof that the class is of a type that has the method, and nothing of
/// it is read. What the class inherits under the signature is held against
/// the method where it is declared ([SignatureRegistry]).
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public abstract sealed class Declarations<Self, S> extends DeclarationArities<Self, S>
        permits MemberBuilder, ThrowingDeclarations, AnnotatedDeclarations, ModifiedDeclarations {

    Declarations() {}

    /// The annotations a member declared here declares.
    ///
    /// @return the annotations, in order
    List<AnnotationRef<?>> annotations() {
        return List.of();
    }

    /// The annotations of the parameters of a member declared here, one list
    /// per parameter of its arity.
    ///
    /// @return the annotations of each parameter
    List<List<AnnotationRef<?>>> parameterAnnotations() {
        return List.of();
    }

    /// The modifiers a member declared here is rendered with, beside those
    /// its form fixes.
    ///
    /// @return the modifiers
    Set<Modifier> modifiers() {
        return Set.of();
    }

    // The declarations of constructors, methods and static methods by name and types (`declareConstructor`,
    // `declareMethod`, `declareVoidMethod`, `declareStaticMethod`, `declareVoidStaticMethod`) and of the overrides
    // (`declareOverride`), of every arity, are generated into the superclass of this class (DeclarationArities, see
    // TypedCodegen). They call the hooks below.

    @Override
    <F extends Invocable> F declare(F member) {
        return builder().declare(member, thrown(), annotations(), parameterAnnotations(), modifiers());
    }

    /// The declarations of the members that declare `annotations`, the
    /// methods and constructors of any arity: a callable is rendered with
    /// them, and a parameter with those [#annotatedParameter(int,
    /// AnnotationRef...)] gives it.
    ///
    /// ```java
    /// var read = cb.annotated(Deprecated_.annotation().build()).declareMethod("read", INT);
    /// ```
    ///
    /// @param annotations the annotations, in order
    /// @return the declarations that annotate the members they declare
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final AnnotatedDeclarations<Self, S> annotated(AnnotationRef<?>... annotations) {
        return new AnnotatedDeclarations<>(this, List.of(annotations), java.util.Map.of());
    }

    /// The declarations of the members whose parameters declare the
    /// annotations of a list: a parameter of an arity of the member is
    /// rendered with those of its index.
    ///
    /// @param parameter the index of the parameter
    /// @param annotations the annotations of the parameter, in order
    /// @return the declarations that annotate the parameters of the members they declare
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final AnnotatedDeclarations<Self, S> annotatedParameter(int parameter, AnnotationRef<?>... annotations) {
        return new AnnotatedDeclarations<>(this, List.of(), java.util.Map.of(parameter, List.of(annotations)));
    }

    /// The declarations of the methods of the class that are rendered with
    /// `modifiers` beside those their form fixes:
    ///
    /// ```java
    /// var spin = cb.modified(Modifier.SYNCHRONIZED).declareVoidMethod("spin");
    /// var read = cb.modified(Modifier.NATIVE).declareMethod("read", PrimitiveToken.INT);
    /// ```
    ///
    /// A `synchronized` method has a body, as any method declared here, and the
    /// bodies define it. A `native` method has none: the declaration is whole,
    /// its fact is there to call, and the bodies neither define it nor wait for
    /// it. What a member can be is held to its form when it is declared: a
    /// constructor is neither `synchronized` nor `native`, and a `native`
    /// method is never `abstract`; the core model refuses every other
    /// combination.
    ///
    /// @param modifiers the modifiers to add, in any order
    /// @return the declarations that render the members they declare with them
    /// @throws IllegalArgumentException if a modifier cannot be added to the member declared here
    public final ModifiedDeclarations<Self, S> modified(Modifier... modifiers) {
        return new ModifiedDeclarations<>(this, List.of(modifiers));
    }

    @Override
    <F extends Invocable> F overriding(F own) {
        return builder().declareOverride(own, thrown());
    }

    /// The traits of a member declared here: `traits`, with the `throws` clause.
    @Override
    MemberTraits traits(MemberTraits traits) {
        return new MemberTraits(
                List.copyOf(thrown()), traits.overridability(), traits.typeArgs(), traits.access(), traits.arity());
    }

    // ------------------------------------------------------------------
    // Members of variable arity
    // ------------------------------------------------------------------

    /// The declarations whose last parameter is of variable arity, `T... v`; the body sees it as the array `T[]`.
    ///
    /// @return the declarations of the members of variable arity
    public final VariableArityDeclarations<Self, S> variableArity() {
        return new VariableArityDeclarations<>(this);
    }
}
