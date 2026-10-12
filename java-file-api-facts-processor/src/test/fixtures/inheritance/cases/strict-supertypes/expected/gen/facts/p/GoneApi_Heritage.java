package gen.facts.p;

import gen.facts.p.GoneApi_Heritage.Held;
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
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.GoneApi;

/// What a class that extends or implements [GoneApi] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class GoneApi_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public GoneApi_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [GoneApi] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("gone", Param.fixed(ClassDesc.of("p.Secret"))), List.of(new Declaration(new OfInterface(ClassDesc.of("p.GoneApi"), InterfaceMethod.DEFAULT), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("p.Secret")))), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.of("p.Secret"))), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [GoneApi], which has no abstract method: the proof is complete. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof
    final <Self> Complete<Self, GoneApi> implement(DeclaredToken<GoneApi> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
