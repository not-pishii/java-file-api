package gen.facts.p;

import gen.facts.p.Greeter_Heritage.Held;
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
import me.supcheg.javafile.facts.Heritage.Named;
import me.supcheg.javafile.facts.Heritage.Named.Field;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
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
import p.Greeter;

/// What a class that extends or implements [Greeter] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Greeter_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Greeter_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Greeter] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17()), List.of(c0(), c1(), c2()), n0());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("arr", Param.fixed(ClassDesc.ofDescriptor("[[I")), Param.fixed(ClassDesc.ofDescriptor("[Lp/Greeter;"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Greeter"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.array(Types.array(PrimitiveTypeRef.INT))), new Fixed(Types.array(Types.of(ClassDesc.of("p.Greeter"))))), new Of<>(new Fixed(Types.array(PrimitiveTypeRef.INT))), List.of())), Set.of(List.of(ClassDesc.ofDescriptor("[[I"), ClassDesc.ofDescriptor("[Lp/Greeter;"))), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("clone"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("finalize"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("getClass"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded())))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("greet"), List.of(new Declaration(new OfClass(ClassDesc.of("p.Greeter"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Greeter"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.util.Locale"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Greeter"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.String"))), new Fixed(Types.of(ClassDesc.of("java.util.Locale")))), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of(ClassDesc.of("java.lang.String"), ClassDesc.of("java.util.Locale"))), List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("hashCode"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("log", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Greeter"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of());
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("main", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/String;"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Greeter"), Visibility.PUBLIC, ClassMethod.STATIC), Arity.FIXED, List.of(), List.of(new Fixed(Types.array(Types.of(ClassDesc.of("java.lang.String"))))), Result.nothing(), List.of())), Set.of(List.of(ClassDesc.ofDescriptor("[Ljava/lang/String;"))), List.of());
        }

        private static Method<Declaration> m11() {
            return new Method<>(Signature.of("notify"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m12() {
            return new Method<>(Signature.of("notifyAll"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m13() {
            return new Method<>(Signature.of("parse", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(new Declaration(new OfClass(ClassDesc.of("p.Greeter"), Visibility.PUBLIC, ClassMethod.STATIC), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of());
        }

        private static Method<Declaration> m14() {
            return new Method<>(Signature.of("toString"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m15() {
            return new Method<>(Signature.of("wait"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m16() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method<Declaration> m17() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Greeter"), Arity.FIXED, List.of(), List.of(), List.of());
        }

        private static Constructor c1() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Greeter", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.ofDescriptor("[Z"))), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.INT), new Fixed(PrimitiveTypeRef.LONG), new Fixed(Types.array(PrimitiveTypeRef.BOOLEAN))), List.of());
        }

        private static Constructor c2() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Greeter", Param.fixed(ClassDesc.of("java.lang.String"))), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of());
        }

        private static List<Named> n0() {
            return List.of(new Field("BOOL", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("BYTE", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("CHAR", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("DEFAULT", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("DOUBLE", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("FLOAT", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("FLOAT_INFINITY", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("FLOAT_NAN", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("INFINITY", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("INT", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("LONG", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("NAN", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("NEGATIVE_INFINITY", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("NEGATIVE_ZERO", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("NOT_CONSTANT", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("OBJECT", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("SHORT", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("count", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("counter", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("grid", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("label", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("mutable", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("name", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("numbers", ClassDesc.of("p.Greeter"), Visibility.PUBLIC), new Field("text", ClassDesc.of("p.Greeter"), Visibility.PUBLIC));
        }
    }

    /// Starts the proof that a class being declared extends [Greeter], which has no abstract method: the proof is complete. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof
    final <Self> Complete<Self, Greeter> extend(DeclaredToken<Greeter> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
