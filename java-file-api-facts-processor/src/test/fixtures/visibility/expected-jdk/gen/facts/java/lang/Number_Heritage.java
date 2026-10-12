package gen.facts.java.lang;

import gen.facts.java.lang.Number_Heritage.Extend;
import gen.facts.java.lang.Number_Heritage.Extend1;
import gen.facts.java.lang.Number_Heritage.Extend2;
import gen.facts.java.lang.Number_Heritage.Extend3;
import gen.facts.java.lang.Number_Heritage.Held;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.ClassMethod;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Declaration;
import me.supcheg.javafile.facts.Heritage.Declarer.OfClass;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.Implementation0;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Float;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// What a class that extends or implements [Number] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Number_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Number_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Number] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16()), List.of(c0()), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("byteValue"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Number"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.BYTE)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("clone"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("doubleValue"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Number"), Visibility.PUBLIC, ClassMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.DOUBLE)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("finalize"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("floatValue"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Number"), Visibility.PUBLIC, ClassMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.FLOAT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("getClass"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded())))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("hashCode"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("intValue"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Number"), Visibility.PUBLIC, ClassMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("longValue"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Number"), Visibility.PUBLIC, ClassMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.LONG)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("notify"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m11() {
            return new Method<>(Signature.of("notifyAll"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m12() {
            return new Method<>(Signature.of("shortValue"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Number"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.SHORT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m13() {
            return new Method<>(Signature.of("toString"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m14() {
            return new Method<>(Signature.of("wait"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m15() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method<Declaration> m16() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Number"), Arity.FIXED, List.of(), List.of(), List.of());
        }
    }

    /// Starts the proof that a class being declared extends [Number]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Extend<Self> extend(DeclaredToken<Number> type, RefToken<Self> self) {
        return new Extend<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class extends [Number] after 0 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Extend<Self> {
        private final Steps<Self, Number> steps;

        private Extend(Steps<Self, Number> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `doubleValue()`, declared in `Number`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Extend1<Self> doubleValue(Implementation0<Self, ? extends Double> how) {
            return new Extend1<>(this.steps.with(Held.HERITAGE.methods().get(2).signature(), how));
        }
    }

    /// The proof that a class extends [Number] after 1 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Extend1<Self> {
        private final Steps<Self, Number> steps;

        private Extend1(Steps<Self, Number> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `floatValue()`, declared in `Number`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Extend2<Self> floatValue(Implementation0<Self, ? extends Float> how) {
            return new Extend2<>(this.steps.with(Held.HERITAGE.methods().get(5).signature(), how));
        }
    }

    /// The proof that a class extends [Number] after 2 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Extend2<Self> {
        private final Steps<Self, Number> steps;

        private Extend2(Steps<Self, Number> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `intValue()`, declared in `Number`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Extend3<Self> intValue(Implementation0<Self, ? extends Int> how) {
            return new Extend3<>(this.steps.with(Held.HERITAGE.methods().get(8).signature(), how));
        }
    }

    /// The proof that a class extends [Number] after 3 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Extend3<Self> {
        private final Steps<Self, Number> steps;

        private Extend3(Steps<Self, Number> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `longValue()`, declared in `Number`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Number> longValue(Implementation0<Self, ? extends Long> how) {
            return this.steps.with(Held.HERITAGE.methods().get(9).signature(), how).complete();
        }
    }
}
