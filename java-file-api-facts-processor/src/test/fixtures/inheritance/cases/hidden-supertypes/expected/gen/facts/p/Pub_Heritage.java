package gen.facts.p;

import gen.facts.p.Pub_Heritage.Held;
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
import me.supcheg.javafile.facts.Heritage.Declarer.OfInterface;
import me.supcheg.javafile.facts.Heritage.InterfaceMethod;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Named;
import me.supcheg.javafile.facts.Heritage.Named.Field;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.Heritage.Type.OfMember;
import me.supcheg.javafile.facts.Heritage.Variable;
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
import p.Pub;

/// What a class that extends or implements [Pub] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Pub_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Pub_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Pub] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18(), m19(), m20(), m21(), m22(), m23(), m24(), m25()), List.of(c0()), n0());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("api"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Pub"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of(ClassDesc.of("p.HiddenApi")));
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("beyond"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.PubApi"), InterfaceMethod.DEFAULT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("clone"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("dflt"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.HiddenApi"), InterfaceMethod.DEFAULT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("finalize"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("get"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Far"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("getClass"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded())))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("hashCode"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("near"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Near"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("notify"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m11() {
            return new Method<>(Signature.of("notifyAll"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m12() {
            return new Method<>(Signature.of("overridden"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Near"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of(ClassDesc.of("p.Far")));
        }

        private static Method<Declaration> m13() {
            return new Method<>(Signature.of("pack"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Far"), Visibility.PACKAGE, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m14() {
            return new Method<>(Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Far"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(new Variable(List.of())), List.of(new OfMember(0)), new Of<>(new OfMember(0)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m15() {
            return new Method<>(Signature.of("prot"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Far"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m16() {
            return new Method<>(Signature.of("pub"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Pub"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of(ClassDesc.of("p.PubApi")));
        }

        private static Method<Declaration> m17() {
            return new Method<>(Signature.of("redeclared"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Pub"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of(ClassDesc.of("p.Far")));
        }

        private static Method<Declaration> m18() {
            return new Method<>(Signature.of("self"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Far"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("p.Far"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m19() {
            return new Method<>(Signature.of("set", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Far"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m20() {
            return new Method<>(Signature.of("sfar"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Far"), Visibility.PUBLIC, ClassMethod.STATIC), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m21() {
            return new Method<>(Signature.of("snear"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Near"), Visibility.PUBLIC, ClassMethod.STATIC), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m22() {
            return new Method<>(Signature.of("toString"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m23() {
            return new Method<>(Signature.of("wait"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m24() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method<Declaration> m25() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Pub"), Arity.FIXED, List.of(), List.of(), List.of());
        }

        private static List<Named> n0() {
            return List.of(new Field("CONST", ClassDesc.of("p.HiddenApi"), Visibility.PUBLIC), new Field("FAR", ClassDesc.of("p.Far"), Visibility.PUBLIC), new Field("HID", ClassDesc.of("p.Far"), Visibility.PUBLIC), new Field("HID", ClassDesc.of("p.Near"), Visibility.PUBLIC), new Field("counter", ClassDesc.of("p.Far"), Visibility.PUBLIC), new Field("item", ClassDesc.of("p.Far"), Visibility.PUBLIC));
        }
    }

    /// Starts the proof that a class being declared extends [Pub], which has no abstract method: the proof is complete. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof
    final <Self> Complete<Self, Pub> extend(DeclaredToken<Pub> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
