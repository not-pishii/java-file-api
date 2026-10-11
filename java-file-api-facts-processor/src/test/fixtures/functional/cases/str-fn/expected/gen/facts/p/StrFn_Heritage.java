package gen.facts.p;

import gen.facts.p.StrFn_Heritage.Held;
import gen.facts.p.StrFn_Heritage.Implement;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.StrFn;

/// What a class that extends or implements [StrFn] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class StrFn_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public StrFn_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [StrFn] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.of(ClassDesc.of("java.lang.String"))), Types.extendsBound(Types.typeVar("V"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))), Types.exact(Types.typeVar("V"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
        }

        private static Method m1() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("java.util.function.Function"), Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m2() {
            return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("V")), Types.extendsBound(Types.of(ClassDesc.of("java.lang.String")))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("V")), Types.exact(Types.of(ClassDesc.of("java.lang.String")))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [StrFn]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<StrFn> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [StrFn] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, StrFn> steps;

        private Implement(Steps<Self, StrFn> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `apply(java.lang.String)`, declared in `Function`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, StrFn> apply_String(Implementation1<Self, ? extends String, String> how) {
            return this.steps.with(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String"))), how).complete();
        }
    }
}
