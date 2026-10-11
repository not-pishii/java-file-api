package gen.facts.p;

import gen.facts.p.Grid_Heritage.Held;
import gen.facts.p.Grid_Heritage.Implement;
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
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.Implementation1;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Grid;

/// What a class that extends or implements [Grid] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Grid_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Grid_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Grid] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Grid"), Signature.of("row", Param.var(1, 2)), List.of(), List.of(Types.array(Types.array(Types.typeVar("B")))), Arity.FIXED, new Of(Types.array(Types.typeVar("A"))), List.of(), Set.of(List.of(ClassDesc.ofDescriptor("[[Ljava/lang/Object;"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Grid]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <A> a type argument of the type
    /// @param <B> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <A, B, Self> Implement<A, B, Self> implement(DeclaredToken<Grid<A, B>> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Grid] after 0 of its 1 steps.
    ///
    /// @param <A> a type argument of the type
    /// @param <B> a type argument of the type
    /// @param <Self> the class being declared
    public static final class Implement<A, B, Self> {
        private final Steps<Self, Grid<A, B>> steps;

        private Implement(Steps<Self, Grid<A, B>> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `row(#1[][])`, declared in `Grid`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Grid<A, B>> row_BArrayArray(Implementation1<Self, ? extends A[], B[][]> how) {
            return this.steps.with(Signature.of("row", Param.var(1, 2)), how).complete();
        }
    }
}
