package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.ExtendableClassToken;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;

import java.util.List;

/// The stage of the header of a class that is about to extend `S`, from
/// [ClassHeader#extending]: the superclass is named and not extended yet, so
/// that what the proof needs of it can be said — the intention to override a
/// `protected` abstract method, whose fact only a subclass of `S` takes out.
///
/// ```java
/// var eh = h.extending(Shape_.TOKEN);
/// var describe = eh.overriding(eh.protected_(Shape_.describe_String));
/// var sh = eh.extends_(Shape_.extend(h.self()).area(area).describe_String(describe));
/// ```
///
/// @param <Self> the brand of the class being declared
/// @param <S> the class it is about to extend
public final class ExtendingHeader<Self, S> extends Overriding<Self> {
    private final ClassDeclaration<Self> declaration;
    private final ExtendableClassToken<S> superclass;

    ExtendingHeader(ClassDeclaration<Self> declaration, ExtendableClassToken<S> superclass) {
        this.declaration = declaration;
        this.superclass = superclass;
    }

    @Override
    ClassDeclaration<Self> declaration() {
        return declaration;
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }

    /// Takes the fact of a `protected` member of `S`, or of a class above it, out of its
    /// wrapper, as the builder of the members does ([SuperAccess#protected_]): for
    /// `eh.overriding(eh.protected_(fact))`, the intention the proof has a step for.
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalStateException if the class has its superclass already
    public <F extends MemberFact> F protected_(Protected<? super S, F> held) {
        return declaration.open(held);
    }

    /// The class extends `S`, see [ClassHeader#extends_].
    ///
    /// @param proof the proof that the class has every abstract method of `S`
    /// @return the stage that names the superinterfaces of the class and begins its members
    /// @throws IllegalArgumentException as [ClassHeader#extends_], and if the proof is of another
    ///     parameterization of the class than this stage was given
    /// @throws IllegalStateException if the class has its superclass already
    /// @throws TargetClasspathMismatchException if the metamodel of the superclass does not hold on the
    ///     target classpath
    public SubclassHeader<Self, S> extends_(Complete<Self, S> proof) {
        if (!Tokens.sameType(proof.supertype(), superclass)) {
            throw new IllegalArgumentException(declaration.described() + " was said to extend " + superclass
                    + ", and the proof is of " + proof.supertype());
        }
        return new SubclassHeader<>(declaration, declaration.extend(proof));
    }
}
