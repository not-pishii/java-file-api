package gen.facts.p;

import gen.facts.p.Overloaded_Heritage.Held;
import gen.facts.p.Overloaded_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
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
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.Heritage.Type.OfMember;
import me.supcheg.javafile.facts.Heritage.Variable;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidImplementation1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.jspecify.annotations.NullMarked;
import p.Overloaded;

/// What a class that extends or implements [Overloaded] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Overloaded_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Overloaded_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Overloaded] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("run", Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Overloaded"), InterfaceMethod.DEFAULT), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of())), Set.of(List.of(ConstantDescs.CD_int)), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("run", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Overloaded"), InterfaceMethod.DEFAULT), Arity.FIXED, List.of(new Variable(List.of())), List.of(new OfMember(0), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"), ConstantDescs.CD_int)), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("run", Param.fixed(ConstantDescs.CD_long)), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Overloaded"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), List.of())), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Overloaded]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<Overloaded> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Overloaded] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, Overloaded> steps;

        private Implement(Steps<Self, Overloaded> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `run(long)`, declared in `Overloaded`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Overloaded> run_long(VoidImplementation1<Self, Long> how) {
            return this.steps.with(Held.HERITAGE.methods().get(2).signature(), how).complete();
        }
    }
}
