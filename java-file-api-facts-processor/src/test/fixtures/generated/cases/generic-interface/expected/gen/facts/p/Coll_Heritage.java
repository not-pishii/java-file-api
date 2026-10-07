package gen.facts.p;

import gen.facts.p.Coll_Heritage.Held;
import gen.facts.p.Coll_Heritage.Implement;
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
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Coll;

/// What a class that extends or implements [Coll] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Coll_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Coll_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Coll] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Coll"), Signature.of("add", Param.var(0)), List.of(), List.of(Types.typeVar("E")), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Coll]: a step per abstract method follows, in the order of their names.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <E> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    public static <E, Self> Implement<E, Self> implement(DeclaredToken<Coll<E>> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Coll] after 0 of its 1 steps.
    ///
    /// @param <E> a type argument of the type
    /// @param <Self> the class being declared
    public static final class Implement<E, Self> {
        private final Steps<Self, Coll<E>> steps;

        private Implement(Steps<Self, Coll<E>> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `add(#0)`, declared in `Coll`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Coll<E>> add_E(Implementation1<Self, ? extends Bool, E> how) {
            return this.steps.with(Signature.of("add", Param.var(0)), how).complete();
        }
    }
}
