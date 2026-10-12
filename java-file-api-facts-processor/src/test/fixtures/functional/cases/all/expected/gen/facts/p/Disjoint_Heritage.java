package gen.facts.p;

import gen.facts.p.Disjoint_Heritage.Held;
import gen.facts.p.Disjoint_Heritage.Implement;
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
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidImplementation0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Disjoint;

/// What a class that extends or implements [Disjoint] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Disjoint_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Disjoint_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Disjoint] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("m"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.IoA"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.io.IOException"))))), new Declaration(new OfInterface(ClassDesc.of("p.SqlB"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.sql.SQLException")))))), Set.of(List.of()), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Disjoint]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<Disjoint> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Disjoint] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, Disjoint> steps;

        private Implement(Steps<Self, Disjoint> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `m()`, declared in `IoA and SqlB`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Disjoint> m(VoidImplementation0<Self> how) {
            return this.steps.with(Held.HERITAGE.methods().get(0).signature(), how).complete();
        }
    }
}
