package gen.facts.p;

import gen.facts.p.Def_Heritage.Held;
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
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Def;

/// What a class that extends or implements [Def] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Def_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Def_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Def] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("p.Def"), Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of(ClassDesc.of("p.Fn")));
        }
    }

    /// Starts the proof that a class being declared implements [Def], which has no abstract method: the proof is complete.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof
    public <Self> Complete<Self, Def> implement(DeclaredToken<Def> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
