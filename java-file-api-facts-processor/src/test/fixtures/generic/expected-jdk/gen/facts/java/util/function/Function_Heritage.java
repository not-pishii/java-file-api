package gen.facts.java.util.function;

import gen.facts.java.util.function.Function_Heritage.Held;
import gen.facts.java.util.function.Function_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Argument.Exact;
import me.supcheg.javafile.facts.Heritage.Argument.Extends;
import me.supcheg.javafile.facts.Heritage.Argument.Super;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Declaration;
import me.supcheg.javafile.facts.Heritage.Declarer.OfInterface;
import me.supcheg.javafile.facts.Heritage.InterfaceMethod;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.OfMember;
import me.supcheg.javafile.facts.Heritage.Type.OfType;
import me.supcheg.javafile.facts.Heritage.Type.Parameterized;
import me.supcheg.javafile.facts.Heritage.Variable;
import me.supcheg.javafile.facts.Implementation1;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// What a class that extends or implements [Function] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Function_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Function_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Function] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new Declaration(new OfInterface(ClassDesc.of("java.util.function.Function"), InterfaceMethod.DEFAULT), Arity.FIXED, List.of(new Variable(List.of())), List.of(new Parameterized(ClassDesc.of("java.util.function.Function"), List.of(new Super(new OfType(1)), new Extends(new OfMember(0))))), new Of<>(new Parameterized(ClassDesc.of("java.util.function.Function"), List.of(new Exact(new OfType(0)), new Exact(new OfMember(0))))), List.of())), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("apply", Param.var(0)), List.of(new Declaration(new OfInterface(ClassDesc.of("java.util.function.Function"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(new OfType(0)), new Of<>(new OfType(1)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new Declaration(new OfInterface(ClassDesc.of("java.util.function.Function"), InterfaceMethod.DEFAULT), Arity.FIXED, List.of(new Variable(List.of())), List.of(new Parameterized(ClassDesc.of("java.util.function.Function"), List.of(new Super(new OfMember(0)), new Extends(new OfType(0))))), new Of<>(new Parameterized(ClassDesc.of("java.util.function.Function"), List.of(new Exact(new OfMember(0)), new Exact(new OfType(1))))), List.of())), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Function]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <T> a type argument of the type
    /// @param <R> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <T, R, Self> Implement<T, R, Self> implement(DeclaredToken<Function<T, R>> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Function] after 0 of its 1 steps.
    ///
    /// @param <T> a type argument of the type
    /// @param <R> a type argument of the type
    /// @param <Self> the class being declared
    public static final class Implement<T, R, Self> {
        private final Steps<Self, Function<T, R>> steps;

        private Implement(Steps<Self, Function<T, R>> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `apply(#0)`, declared in `Function`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Function<T, R>> apply_T(Implementation1<Self, ? extends R, T> how) {
            return this.steps.with(Held.HERITAGE.methods().get(1).signature(), how).complete();
        }
    }
}
