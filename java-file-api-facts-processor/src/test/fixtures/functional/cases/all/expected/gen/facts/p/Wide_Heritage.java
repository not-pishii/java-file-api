package gen.facts.p;

import gen.facts.p.Wide_Heritage.Held;
import gen.facts.p.Wide_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidImplementation0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Wide;

/// What a class that extends or implements [Wide] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Wide_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Wide_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Wide] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("p.Wide"), Signature.of("a"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of(ClassDesc.of("p.Two")));
        }

        private static Method m1() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Two"), Signature.of("b"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Wide]: a step per abstract method follows, in the order of their names.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    public <Self> Implement<Self> implement(DeclaredToken<Wide> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Wide] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, Wide> steps;

        private Implement(Steps<Self, Wide> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `b()`, declared in `Two`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Wide> b(VoidImplementation0<Self> how) {
            return this.steps.with(Signature.of("b"), how).complete();
        }
    }
}
