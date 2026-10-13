package gen.facts.p;

import gen.facts.p.Crowded_Heritage.Held;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
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
import p.Crowded;

/// What a class that extends or implements [Crowded] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Crowded_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public Crowded_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [Crowded] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10()), List.of(c0()), Stream.of(n0(), n1()).flatMap(List::stream).toList());

        private Held() {
        }

        private static Method<Declaration> m0() {
            return new Method<>(Signature.of("clone"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m1() {
            return new Method<>(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Object")))), new Of<>(new Fixed(PrimitiveTypeRef.BOOLEAN)), List.of())), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
        }

        private static Method<Declaration> m2() {
            return new Method<>(Signature.of("finalize"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PROTECTED, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.Throwable")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m3() {
            return new Method<>(Signature.of("getClass"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded())))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m4() {
            return new Method<>(Signature.of("hashCode"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(PrimitiveTypeRef.INT)), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m5() {
            return new Method<>(Signature.of("notify"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m6() {
            return new Method<>(Signature.of("notifyAll"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m7() {
            return new Method<>(Signature.of("toString"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.CONCRETE), Arity.FIXED, List.of(), List.of(), new Of<>(new Fixed(Types.of(ClassDesc.of("java.lang.String")))), List.of())), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m8() {
            return new Method<>(Signature.of("wait"), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of()), List.of());
        }

        private static Method<Declaration> m9() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
        }

        private static Method<Declaration> m10() {
            return new Method<>(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Declaration(new OfClass(ClassDesc.of("java.lang.Object"), Visibility.PUBLIC, ClassMethod.FINAL), Arity.FIXED, List.of(), List.of(new Fixed(PrimitiveTypeRef.LONG), new Fixed(PrimitiveTypeRef.INT)), Result.nothing(), List.of(new Fixed(Types.of(ClassDesc.of("java.lang.InterruptedException")))))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
        }

        private static Constructor c0() {
            return new Constructor(Visibility.PUBLIC, Signature.of("Crowded"), Arity.FIXED, List.of(), List.of(), List.of());
        }

        private static List<Named> n0() {
            return List.of(new Field("f0", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f1", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f10", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f100", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f101", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f102", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f103", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f104", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f105", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f106", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f107", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f108", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f109", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f11", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f110", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f111", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f112", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f113", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f114", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f115", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f116", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f117", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f118", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f119", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f12", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f120", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f121", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f122", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f123", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f124", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f125", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f126", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f127", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f128", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f129", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f13", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f130", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f131", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f132", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f133", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f134", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f135", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f136", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f137", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f138", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f139", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f14", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f140", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f141", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f142", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f143", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f144", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f145", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f146", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f147", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f148", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f149", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f15", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f150", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f151", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f152", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f153", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f154", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f155", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f156", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f157", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f158", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f159", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f16", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f160", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f161", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f162", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f163", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f164", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f165", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f166", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f167", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f168", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f169", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f17", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f170", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f171", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f172", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f173", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f174", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f175", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f176", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f177", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f178", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f179", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f18", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f180", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f181", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f182", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f183", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f184", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f185", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f186", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f187", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f188", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f189", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f19", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f190", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f191", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f192", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f193", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f194", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f195", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f196", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f197", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f198", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f199", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f2", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f20", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f200", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f21", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f22", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f23", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f24", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f25", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f26", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f27", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f28", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f29", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f3", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f30", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f31", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f32", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f33", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f34", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f35", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f36", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f37", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f38", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f39", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f4", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f40", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f41", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f42", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f43", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f44", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f45", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f46", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f47", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f48", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f49", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f5", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f50", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f51", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f52", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f53", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f54", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f55", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f56", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f57", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f58", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f59", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f6", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f60", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f61", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f62", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f63", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f64", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f65", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f66", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f67", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f68", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f69", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f7", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f70", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f71", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f72", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f73", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f74", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f75", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f76", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f77", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f78", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f79", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f8", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f80", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f81", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f82", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f83", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f84", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f85", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f86", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f87", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f88", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f89", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f9", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f90", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f91", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f92", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f93", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f94", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f95", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f96", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f97", ClassDesc.of("p.Crowded"), Visibility.PACKAGE), new Field("f98", ClassDesc.of("p.Crowded"), Visibility.PACKAGE));
        }

        private static List<Named> n1() {
            return List.of(new Field("f99", ClassDesc.of("p.Crowded"), Visibility.PACKAGE));
        }
    }

    /// Starts the proof that a class being declared extends [Crowded], which has no abstract method: the proof is complete. For the metamodel of the type, whose method of this name gives its own token: the entry of a generator.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof
    final <Self> Complete<Self, Crowded> extend(DeclaredToken<Crowded> type, RefToken<Self> self) {
        return UnsafeFacts.steps(type, self).complete();
    }
}
