package gen.facts.java.lang.constant;

import gen.facts.java.lang.constant.ConstantDesc_Heritage.Held;
import gen.facts.java.lang.constant.ConstantDesc_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDesc;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Declaration;
import me.supcheg.javafile.facts.Heritage.Declarer.OfInterface;
import me.supcheg.javafile.facts.Heritage.InterfaceMethod;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.Implementation1;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// What a class that extends or implements [ConstantDesc] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class ConstantDesc_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public ConstantDesc_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [ConstantDesc] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), List.of(new Declaration(new OfInterface(ClassDesc.of("java.lang.constant.ConstantDesc"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup")))), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.ReflectiveOperationException")))))), Set.of(List.of(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [ConstantDesc]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<ConstantDesc> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [ConstantDesc] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, ConstantDesc> steps;

        private Implement(Steps<Self, ConstantDesc> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup)`, declared in `ConstantDesc`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, ConstantDesc> resolveConstantDesc_MethodHandles_Lookup(Implementation1<Self, ? extends Object, Lookup> how) {
            return this.steps.with(Held.HERITAGE.methods().get(0).signature(), how).complete();
        }
    }
}
