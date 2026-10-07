package gen.facts.java.io;

import gen.facts.java.io.Serializable_Heritage.Held;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// What a class that extends or implements [java.io.Serializable] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@NullMarked
public final class Serializable_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Serializable_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [java.io.Serializable] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(), List.of());

        private Held() {
        }
    }
}
