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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
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
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("R")), Types.extendsBound(Types.typeVar("V"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("T")), Types.exact(Types.typeVar("V"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
        }

        private static Method m1() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("java.util.function.Function"), Signature.of("apply", Param.var(0)), List.of(), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("R")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m2() {
            return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("V")), Types.extendsBound(Types.typeVar("T"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("V")), Types.exact(Types.typeVar("R"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
        }

        private static Method m3() {
            return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("java.util.function.Function"), Signature.of("identity"), List.of(new TypeParam("T", List.of())), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("T")), Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of()), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Function]: a step per abstract method follows, in the order of their names.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <T> a type argument of the type
    /// @param <R> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    public static <T, R, Self> Implement<T, R, Self> implement(DeclaredToken<Function<T, R>> type, RefToken<Self> self) {
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
            return this.steps.with(Signature.of("apply", Param.var(0)), how).complete();
        }
    }
}
