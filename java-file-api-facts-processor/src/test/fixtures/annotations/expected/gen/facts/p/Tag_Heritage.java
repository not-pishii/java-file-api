package gen.facts.p;

import gen.facts.p.Tag_Heritage.Held;
import gen.facts.p.Tag_Heritage.Implement;
import gen.facts.p.Tag_Heritage.Implement1;
import gen.facts.p.Tag_Heritage.Implement10;
import gen.facts.p.Tag_Heritage.Implement11;
import gen.facts.p.Tag_Heritage.Implement12;
import gen.facts.p.Tag_Heritage.Implement13;
import gen.facts.p.Tag_Heritage.Implement2;
import gen.facts.p.Tag_Heritage.Implement3;
import gen.facts.p.Tag_Heritage.Implement4;
import gen.facts.p.Tag_Heritage.Implement5;
import gen.facts.p.Tag_Heritage.Implement6;
import gen.facts.p.Tag_Heritage.Implement7;
import gen.facts.p.Tag_Heritage.Implement8;
import gen.facts.p.Tag_Heritage.Implement9;
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
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Byte;
import me.supcheg.javafile.facts.Prim.Char;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Float;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.Prim.Short;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Level;
import p.Tag;

/// What a class that extends or implements [Tag] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Tag_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Tag_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Tag] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18(), m19(), m20(), m21(), m22(), m23(), m24()), List.of(), List.of());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("annotationType"), List.of(new Declaration(new OfInterface(ClassDesc.of("java.lang.annotation.Annotation"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.extendsBound(Types.of(ClassDesc.of("java.lang.annotation.Annotation")))), List.of()))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("array"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("big"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.LONG)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("clone"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("decimal"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.FLOAT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of(ClassDesc.of("java.lang.annotation.Annotation")));
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("finalize"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("flag"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("getClass"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("hashCode"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of(ClassDesc.of("java.lang.annotation.Annotation")));
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("letter"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.CHAR)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m11() {
            return new Method<>(Signature.of("levels"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.array(Types.of(ClassDesc.of("p.Level"))))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m12() {
            return new Method<>(Signature.of("medium"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.SHORT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m13() {
            return new Method<>(Signature.of("notify"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m14() {
            return new Method<>(Signature.of("notifyAll"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m15() {
            return new Method<>(Signature.of("primitive"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m16() {
            return new Method<>(Signature.of("rank"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m17() {
            return new Method<>(Signature.of("ratio"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.DOUBLE)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m18() {
            return new Method<>(Signature.of("small"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.BYTE)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m19() {
            return new Method<>(Signature.of("toString"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of(ClassDesc.of("java.lang.annotation.Annotation")));
        }

        private static Method<Declaration> m20() {
            return new Method<>(Signature.of("type"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m21() {
            return new Method<>(Signature.of("value"), List.of(new Declaration(new OfInterface(ClassDesc.of("p.Tag"), InterfaceMethod.ABSTRACT), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
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
    }

    /// Starts the proof that a class being declared implements [Tag]: a step per abstract method follows, in the order of their names. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    final <Self> Implement<Self> implement(DeclaredToken<Tag> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [Tag] after 0 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, Tag> steps;

        private Implement(Steps<Self, Tag> steps) {
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

    /// The proof that a class implements [Tag] after 1 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement1<Self> {
        private final Steps<Self, Tag> steps;

        private Implement1(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `array()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement2<Self> array(Implementation0<Self, ? extends Class<?>> how) {
            return new Implement2<>(this.steps.with(Held.HERITAGE.methods().get(1).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 2 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement2<Self> {
        private final Steps<Self, Tag> steps;

        private Implement2(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `big()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement3<Self> big(Implementation0<Self, ? extends Long> how) {
            return new Implement3<>(this.steps.with(Held.HERITAGE.methods().get(2).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 3 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement3<Self> {
        private final Steps<Self, Tag> steps;

        private Implement3(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `decimal()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement4<Self> decimal(Implementation0<Self, ? extends Float> how) {
            return new Implement4<>(this.steps.with(Held.HERITAGE.methods().get(4).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 4 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement4<Self> {
        private final Steps<Self, Tag> steps;

        private Implement4(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `flag()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement5<Self> flag(Implementation0<Self, ? extends Bool> how) {
            return new Implement5<>(this.steps.with(Held.HERITAGE.methods().get(7).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 5 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement5<Self> {
        private final Steps<Self, Tag> steps;

        private Implement5(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `letter()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement6<Self> letter(Implementation0<Self, ? extends Char> how) {
            return new Implement6<>(this.steps.with(Held.HERITAGE.methods().get(10).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 6 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement6<Self> {
        private final Steps<Self, Tag> steps;

        private Implement6(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `levels()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement7<Self> levels(Implementation0<Self, ? extends Level[]> how) {
            return new Implement7<>(this.steps.with(Held.HERITAGE.methods().get(11).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 7 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement7<Self> {
        private final Steps<Self, Tag> steps;

        private Implement7(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `medium()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement8<Self> medium(Implementation0<Self, ? extends Short> how) {
            return new Implement8<>(this.steps.with(Held.HERITAGE.methods().get(12).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 8 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement8<Self> {
        private final Steps<Self, Tag> steps;

        private Implement8(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `primitive()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement9<Self> primitive(Implementation0<Self, ? extends Class<?>> how) {
            return new Implement9<>(this.steps.with(Held.HERITAGE.methods().get(15).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 9 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement9<Self> {
        private final Steps<Self, Tag> steps;

        private Implement9(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `rank()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement10<Self> rank(Implementation0<Self, ? extends Int> how) {
            return new Implement10<>(this.steps.with(Held.HERITAGE.methods().get(16).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 10 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement10<Self> {
        private final Steps<Self, Tag> steps;

        private Implement10(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `ratio()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement11<Self> ratio(Implementation0<Self, ? extends Double> how) {
            return new Implement11<>(this.steps.with(Held.HERITAGE.methods().get(17).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 11 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement11<Self> {
        private final Steps<Self, Tag> steps;

        private Implement11(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `small()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement12<Self> small(Implementation0<Self, ? extends Byte> how) {
            return new Implement12<>(this.steps.with(Held.HERITAGE.methods().get(18).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 12 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement12<Self> {
        private final Steps<Self, Tag> steps;

        private Implement12(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `type()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the stage of the next step
        public Implement13<Self> type(Implementation0<Self, ? extends Class<?>> how) {
            return new Implement13<>(this.steps.with(Held.HERITAGE.methods().get(20).signature(), how));
        }
    }

    /// The proof that a class implements [Tag] after 13 of its 14 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement13<Self> {
        private final Steps<Self, Tag> steps;

        private Implement13(Steps<Self, Tag> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `value()`, declared in `Tag`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, Tag> value(Implementation0<Self, ? extends String> how) {
            return this.steps.with(Held.HERITAGE.methods().get(21).signature(), how).complete();
        }
    }
}
