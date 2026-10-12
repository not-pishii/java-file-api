package gen.facts.p;

import gen.facts.p.Box_Heritage.Held;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Argument.Exact;
import me.supcheg.javafile.facts.Heritage.Argument.Extends;
import me.supcheg.javafile.facts.Heritage.Argument.Super;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.ClassMethod;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Declaration;
import me.supcheg.javafile.facts.Heritage.Declarer.OfClass;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Named;
import me.supcheg.javafile.facts.Heritage.Named.Field;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Array;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.Heritage.Type.OfType;
import me.supcheg.javafile.facts.Heritage.Type.Parameterized;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Box;

/// What a class that extends or implements [Box] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Box_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Box_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Box] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18(), m19(), m20(), m21(), m22(), m23(), m24()), List.of(c0(), c1()), n0());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Parameterized(ClassDesc.of("java.util.Collection"), List.of(new Extends(new OfType(0))))), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.of("java.util.Collection"))), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("asList"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Parameterized(ClassDesc.of("java.util.List"), List.of(new Exact(new OfType(0))))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("clone"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("drainTo", Param.fixed(ClassDesc.of("java.util.Collection"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Parameterized(ClassDesc.of("java.util.Collection"), List.of(new Super(new OfType(0))))), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.of("java.util.Collection"))), List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("finalize"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("get"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new OfType(0)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("getClass"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded())))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("hashCode"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("ints"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.array(PrimitiveTypeRef.INT))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("nested"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Parameterized(ClassDesc.of("p.Box"), List.of(new Exact(new Parameterized(ClassDesc.of("p.Box"), List.of(new Exact(new OfType(0)))))))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m11() {
            return new Method<>(Signature.of("notify"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m12() {
            return new Method<>(Signature.of("notifyAll"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m13() {
            return new Method<>(Signature.of("ofString"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.STATIC), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m14() {
            return new Method<>(Signature.of("raw", Param.fixed(ClassDesc.of("java.util.Map"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.util.Map")))), new Of<>(new Fixed(Types.of(ClassDesc.of("java.util.List")))), List.of())), Set.of(List.of(ClassDesc.of("java.util.Map"))), List.of());
        }

        private static Method<Declaration> m15() {
            return new Method<>(Signature.of("rawSelf"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("p.Box")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m16() {
            return new Method<>(Signature.of("sameAs", Param.fixed(ClassDesc.of("p.Box"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.unbounded())))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("p.Box"))), List.of());
        }

        private static Method<Declaration> m17() {
            return new Method<>(Signature.of("self"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Parameterized(ClassDesc.of("p.Box"), List.of(new Exact(new OfType(0))))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m18() {
            return new Method<>(Signature.of("set", Param.var(0)), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new OfType(0)), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m19() {
            return new Method<>(Signature.of("size", Param.fixed(ClassDesc.of("p.Box"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.STATIC), Arity.FIXED, List.of(), List.of(new Fixed(new ParameterizedTypeRef(ClassDesc.of("p.Box"), List.of(Types.unbounded())))), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of(ClassDesc.of("p.Box"))), List.of());
        }

        private static Method<Declaration> m20() {
            return new Method<>(Signature.of("toArray", Param.var(0, 1)), List.of(new Declaration(new OfClass(ClassDesc.of("p.Box"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Array(new OfType(0))), new Of<>(new Array(new OfType(0))), List.of())), Set.of(List.of(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), List.of());
        }

        private static Method<Declaration> m21() {
            return new Method<>(Signature.of("toString"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m22() {
            return new Method<>(Signature.of("wait"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m23() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method<Declaration> m24() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Box", Param.var(0)), Arity.FIXED, List.of(), List.of(new OfType(0)), List.of());
        }

        private static Constructor c1() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Box"), Arity.FIXED, List.of(), List.of(), List.of());
        }

        private static List<Named> n0() {
            return List.of(new Field("NAME", ClassDesc.of("p.Box"), Visibility.PUBLIC), new Field("count", ClassDesc.of("p.Box"), Visibility.PUBLIC), new Field("initial", ClassDesc.of("p.Box"), Visibility.PUBLIC), new Field("value", ClassDesc.of("p.Box"), Visibility.PUBLIC));
        }
    }

    /// Starts the proof that a class being declared extends [Box], which has no abstract method: the proof is complete. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <T> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the proof
    final <T, Self> Complete<Self, Box<T>> extend(DeclaredToken<Box<T>> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
