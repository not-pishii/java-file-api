package gen.facts.p;

import gen.facts.p.Arr_Heritage.Held;
import gen.facts.p.Arr_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Declaration;
import me.supcheg.javafile.facts.Heritage.Declarer.OfInterface;
import me.supcheg.javafile.facts.Heritage.InterfaceMethod;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Array;
import me.supcheg.javafile.facts.Heritage.Type.OfType;
import me.supcheg.javafile.facts.Implementation1;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Arr;

/// What a class that extends or implements [Arr] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Arr_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Arr_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Arr] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("m", Param.var(0, 1)), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Arr"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(new Array(new OfType(0))), new Of<>(new Array(new OfType(0))), List.of())), Set.of(List.of(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Arr]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <T> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <T, Self> Implement<T, Self> implement(DeclaredToken<Arr<T>> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Arr] after 0 of its 1 steps.
    ///
    /// @param <T> a type argument of the type
    /// @param <Self> the class being declared
    public static final class Implement<T, Self> {
        private final Steps<Self, Arr<T>> steps;

        private Implement(Steps<Self, Arr<T>> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `m(#0[])`, declared in `Arr`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Arr<T>> m_TArray(Implementation1<Self, ? extends T[], T[]> how) {
            return this.steps.with(Held.HERITAGE.methods().get(0).signature(), how).complete();
        }
    }
}
