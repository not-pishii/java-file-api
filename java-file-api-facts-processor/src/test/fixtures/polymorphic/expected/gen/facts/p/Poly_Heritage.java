package gen.facts.p;

import gen.facts.p.Poly_Heritage.Held;
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
import p.Poly;

/// What a class that extends or implements [Poly] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Poly_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Poly_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Poly] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18()), List.of(c0(), c1()));

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("clamp", Param.fixed(ClassDesc.of("java.lang.Number"))), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("java.lang.Number")), new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("T"))))))), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Number"))), List.of());
        }

        private static Method m1() {
            return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
        }

        private static Method m2() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m3() {
            return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
        }

        private static Method m4() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
        }

        private static Method m5() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
        }

        private static Method m6() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m7() {
            return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Poly"), Signature.of("listOf", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), List.of(new TypeParam("T", List.of())), List.of(Types.array(Types.typeVar("T"))), Arity.VARIABLE, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.List"), List.of(Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), List.of());
        }

        private static Method m8() {
            return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Poly"), Signature.of("max", Param.fixed(ClassDesc.of("java.util.Collection"))), List.of(new TypeParam("T", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.superBound(Types.typeVar("T"))))))), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.Collection"), List.of(Types.extendsBound(Types.typeVar("T"))))), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.util.Collection"))), List.of());
        }

        private static Method m9() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("none"), List.of(new TypeParam("T", List.of())), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
        }

        private static Method m10() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
        }

        private static Method m11() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
        }

        private static Method m12() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("odd", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("TOKEN", List.of()), new TypeParam("Gen", List.of())), List.of(Types.typeVar("TOKEN"), Types.typeVar("Gen")), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method m13() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("run", Param.fixed(ClassDesc.of("java.lang.Class"))), List.of(new TypeParam("X", List.of(Types.of(ClassDesc.of("java.lang.Exception"))))), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.exact(Types.typeVar("X"))))), Arity.FIXED, Result.NOTHING, List.of(Types.typeVar("X"), Types.of(ClassDesc.of("java.io.IOException"))), Set.of(List.of(ClassDesc.of("java.lang.Class"))), List.of());
        }

        private static Method m14() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
        }

        private static Method m15() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
        }

        private static Method m16() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method m17() {
            return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Method m18() {
            return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Poly"), Signature.of("widen", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("A", List.of()), new TypeParam("B", List.of(Types.typeVar("A")))), List.of(Types.typeVar("B")), Arity.FIXED, new Of(Types.typeVar("A")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Poly"), List.of(), List.of(), Arity.FIXED, List.of());
        }

        private static Constructor c1() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Poly", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of())), List.of(Types.typeVar("T")), Arity.FIXED, List.of());
        }
    }

    /// Starts the proof that a class being declared extends [Poly], which has no abstract method: the proof is complete.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof
    public <Self> Complete<Self, Poly> extend(DeclaredToken<Poly> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
