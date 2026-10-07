package gen.facts.p;

import gen.facts.p.Seq_Heritage.Held;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// What a class that extends or implements [p.Seq] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@NullMarked
public final class Seq_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Seq_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [p.Seq] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Coll"), Signature.of("add", Param.var(0)), List.of(), List.of(Types.typeVar("E")), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m1() {
            return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Seq"), Signature.of("of"), List.of(new TypeParam("T", List.of())), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.Seq"), List.of(Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of()), List.of());
        }
    }
}
