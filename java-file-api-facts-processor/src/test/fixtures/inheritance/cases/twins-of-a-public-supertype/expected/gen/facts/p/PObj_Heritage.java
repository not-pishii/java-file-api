package gen.facts.p;

import gen.facts.p.PObj_Heritage.Held;
import gen.facts.p.PObj_Heritage.Implement;
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
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.Implementation0;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.PObj;

/// What a class that extends or implements [PObj] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class PObj_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public PObj_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [PObj] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("get"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.PObj"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of())), Set.of(List.of()), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [PObj]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<PObj> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [PObj] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, PObj> steps;

        private Implement(Steps<Self, PObj> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `get()`, declared in `PObj`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, PObj> get(Implementation0<Self, ? extends Object> how) {
            return this.steps.with(Held.HERITAGE.methods().get(0).signature(), how).complete();
        }
    }
}
