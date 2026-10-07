package gen.facts.p;

import gen.facts.p.Grid_Heritage.Held;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// What a class that extends or implements [p.Grid] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@NullMarked
public final class Grid_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Grid_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [p.Grid] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Grid"), Signature.of("row", Param.var(1, 2)), List.of(), List.of(Types.array(Types.array(Types.typeVar("B")))), Arity.FIXED, new Of(Types.array(Types.typeVar("A"))), List.of(), Set.of(List.of(ClassDesc.ofDescriptor("[[Ljava/lang/Object;"))), List.of());
        }
    }
}
