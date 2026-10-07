package gen.facts.p;

import gen.facts.p.Iface_Heritage.Held;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// What a class that extends or implements [p.Iface] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@NullMarked
public final class Iface_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Iface_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [p.Iface] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Iface"), Signature.of("empty"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Iface"))), List.of(), Set.of(List.of()), List.of());
        }

        private static Method m1() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Iface"), Signature.of("run"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
        }

        private static Method m2() {
            return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("p.Iface"), Signature.of("size"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
        }
    }
}
