package gen.facts.p;

import gen.facts.p.Empty_Heritage.Held;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Empty;

/// What a class that extends or implements [Empty] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Empty_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Empty_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Empty] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(), List.of());

        private Held() {
        }
    }

    /// Starts the proof that a class being declared implements [Empty], which has no abstract method: the proof is complete.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof
    public <Self> Complete<Self, Empty> implement(DeclaredToken<Empty> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
