package gen.facts.p;

import gen.facts.p.Comp_Heritage.Held;
import gen.facts.p.Comp_Heritage.Implement;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Comp;

/// What a class that extends or implements [Comp] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Comp_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Comp_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Comp] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Comp"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Comp"))), List.of(), List.of(Types.of(ClassDesc.of("p.Comp"))), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of(ClassDesc.of("p.Comp"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Comp]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<Comp> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Comp] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, Comp> steps;

        private Implement(Steps<Self, Comp> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `compareTo(p.Comp)`, declared in `Comp`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Comp> compareTo_Comp(Implementation1<Self, ? extends Int, Comp> how) {
            return this.steps.with(Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Comp"))), how).complete();
        }
    }
}
