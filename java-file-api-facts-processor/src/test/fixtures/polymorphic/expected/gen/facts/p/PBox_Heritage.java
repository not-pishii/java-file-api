package gen.facts.p;

import gen.facts.p.PBox_Heritage.Held;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.PBox;

/// What a class that extends or implements [PBox] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class PBox_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public PBox_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [PBox] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15()), List.of(c0()));

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
        }

        private static Method m1() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m2() {
            return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
        }

        private static Method m3() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
        }

        private static Method m4() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
        }

        private static Method m5() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.PBox"), Signature.of("map", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("R", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("T")), Types.extendsBound(Types.typeVar("R"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.PBox"), List.of(Types.exact(Types.typeVar("R"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
        }

        private static Method m6() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
        }

        private static Method m7() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
        }

        private static Method m8() {
            return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.PBox"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.PBox"), List.of(Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m9() {
            return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.PBox"), Signature.of("ofEnum", Param.fixed(ClassDesc.of("java.lang.Class"))), List.of(new TypeParam("E", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.typeVar("E"))))))), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.exact(Types.typeVar("E"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.PBox"), List.of(Types.exact(Types.typeVar("E"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Class"))), List.of());
        }

        private static Method m10() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.PBox"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("U", List.of(Types.typeVar("T")))), List.of(Types.typeVar("U")), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m11() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.PBox"), Signature.of("shadow", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m12() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
        }

        private static Method m13() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
        }

        private static Method m14() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method m15() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("PBox"), List.of(), List.of(), Arity.FIXED, List.of());
        }
    }

    /// Starts the proof that a class being declared extends [PBox], which has no abstract method: the proof is complete. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <T> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the proof
    final <T, Self> Complete<Self, PBox<T>> extend(DeclaredToken<PBox<T>> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
