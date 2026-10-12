package gen.facts.p;

import gen.facts.p.Names_Heritage.Held;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Argument.Exact;
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
import me.supcheg.javafile.facts.Heritage.Type.OfMember;
import me.supcheg.javafile.facts.Heritage.Type.OfType;
import me.supcheg.javafile.facts.Heritage.Type.Parameterized;
import me.supcheg.javafile.facts.Heritage.Variable;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Names;

/// What a class that extends or implements [Names] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Names_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Names_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Names] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(java.util.List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12()), java.util.List.of(c0()), n0());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("all"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("p.Names"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, java.util.List.of(), java.util.List.of(), new Of<>(new Parameterized(ClassDesc.of("java.util.List"), java.util.List.of(new Exact(new OfType(1))))), java.util.List.of())), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("clone"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, java.util.List.of(), java.util.List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), java.util.List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("data", Param.fixed(ClassDesc.of("java.lang.Object"))), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("p.Names"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, java.util.List.of(new Variable(java.util.List.of())), java.util.List.of(new OfMember(0)), new Of<>(new OfMember(0)), java.util.List.of())), Set.of(java.util.List.of(ClassDesc.of("java.lang.Object"))), java.util.List.of());
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, java.util.List.of(), java.util.List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), java.util.List.of())), Set.of(java.util.List.of(ClassDesc.of("java.lang.Object"))), java.util.List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("finalize"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, java.util.List.of(), java.util.List.of(), Result.nothing(), java.util.List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("getClass"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, java.util.List.of(), java.util.List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), java.util.List.of(Types.unbounded())))), java.util.List.of())), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("hashCode"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, java.util.List.of(), java.util.List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), java.util.List.of())), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("notify"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, java.util.List.of(), java.util.List.of(), Result.nothing(), java.util.List.of())), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("notifyAll"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, java.util.List.of(), java.util.List.of(), Result.nothing(), java.util.List.of())), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("toString"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, java.util.List.of(), java.util.List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), java.util.List.of())), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("wait"), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, java.util.List.of(), java.util.List.of(), Result.nothing(), java.util.List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(java.util.List.of()), java.util.List.of());
        }

        private static Method<Declaration> m11() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, java.util.List.of(), java.util.List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), java.util.List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(java.util.List.of(ConstantDescs.CD_long)), java.util.List.of());
        }

        private static Method<Declaration> m12() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), java.util.List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, java.util.List.of(), java.util.List.of(new Fixed(PrimitiveTypeRef.LONG), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), java.util.List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(java.util.List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), java.util.List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Names"), Arity.FIXED, java.util.List.of(), java.util.List.of(), java.util.List.of());
        }

        private static java.util.List<Named> n0() {
            return java.util.List.of(new Field("first", ClassDesc.of("p.Names"), Visibility.PUBLIC), new Field("token", ClassDesc.of("p.Names"), Visibility.PUBLIC));
        }
    }

    /// Starts the proof that a class being declared extends [Names], which has no abstract method: the proof is complete. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <List> a type argument of the type
    /// @param <UnsafeFacts> a type argument of the type
    /// @param <Token> a type argument of the type
    /// @param <Self> the class being declared
    /// @return the proof
    final <List, UnsafeFacts, Token, Self> Complete<Self, Names<List, UnsafeFacts, Token>> extend(DeclaredToken<Names<List, UnsafeFacts, Token>> type, RefToken<Self> self) {
        return me.supcheg.javafile.facts.UnsafeFacts.steps(type, self).complete();
    }
}
