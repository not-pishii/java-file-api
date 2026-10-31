package gen.facts.p;

import gen.facts.p.Author_Heritage.Held;
import gen.facts.p.Author_Heritage.Implement;
import gen.facts.p.Author_Heritage.Implement1;
import gen.facts.p.Author_Heritage.Implement2;
import gen.facts.p.Author_Heritage.Implement3;
import java.lang.annotation.Annotation;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.ClassMethod;
import me.supcheg.javafile.facts.Heritage.Declaration;
import me.supcheg.javafile.facts.Heritage.Declarer.OfClass;
import me.supcheg.javafile.facts.Heritage.Declarer.OfInterface;
import me.supcheg.javafile.facts.Heritage.InterfaceMethod;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Type.Fixed;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.Implementation0;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Author;
import p.Tag;

/// What a class that extends or implements [Author] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Author_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Author_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Author] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("annotationType"), List.of(new Declaration(new OfInterface(ClassDesc.of("java.lang.annotation.Annotation"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.extendsBound(Types.of(ClassDesc.of("java.lang.annotation.Annotation")))), List.of()))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("clone"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of(ClassDesc.of("java.lang.annotation.Annotation")));
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("finalize"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("getClass"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("hashCode"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of(ClassDesc.of("java.lang.annotation.Annotation")));
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("name"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Author"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("notify"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("notifyAll"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("tags"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Author"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.array(Types.of(ClassDesc.of("p.Tag"))))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("toString"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of(ClassDesc.of("java.lang.annotation.Annotation")));
        }

        private static Method<Declaration> m11() {
            return new Method<>(Signature.of("wait"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m12() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method<Declaration> m13() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Method<Declaration> m14() {
            return new Method<>(Signature.of("year"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Author"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [Author]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<Author> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Author] after 0 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, Author> steps;

        private Implement(Steps<Self, Author> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `annotationType()`, declared in `Annotation`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement1<Self> annotationType(Implementation0<Self, ? extends Class<? extends Annotation>> how) {
            return new Implement1<>(this.steps.with(Held.HERITAGE.methods().get(0).signature(), how));
        }
    }

    /// The proof that a class implements [Author] after 1 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement1<Self> {
        private final Steps<Self, Author> steps;

        private Implement1(Steps<Self, Author> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `name()`, declared in `Author`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement2<Self> name(Implementation0<Self, ? extends String> how) {
            return new Implement2<>(this.steps.with(Held.HERITAGE.methods().get(6).signature(), how));
        }
    }

    /// The proof that a class implements [Author] after 2 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement2<Self> {
        private final Steps<Self, Author> steps;

        private Implement2(Steps<Self, Author> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `tags()`, declared in `Author`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement3<Self> tags(Implementation0<Self, ? extends Tag[]> how) {
            return new Implement3<>(this.steps.with(Held.HERITAGE.methods().get(9).signature(), how));
        }
    }

    /// The proof that a class implements [Author] after 3 of its 4 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement3<Self> {
        private final Steps<Self, Author> steps;

        private Implement3(Steps<Self, Author> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `year()`, declared in `Author`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Author> year(Implementation0<Self, ? extends Int> how) {
            return this.steps.with(Held.HERITAGE.methods().get(14).signature(), how).complete();
        }
    }
}
