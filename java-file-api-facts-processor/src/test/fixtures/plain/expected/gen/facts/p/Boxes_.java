package gen.facts.p;

import gen.facts.p.Boxes_.Canonical;
import gen.facts.p.Boxes_.Data;
import gen.facts.p.Boxes_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef12;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Char;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Boxes;

/// The full metamodel of [Boxes], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Boxes] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Boxes.class, fingerprint = "75f2a4800e20062790987455e4c834d2692c0e8487a6bf2a33fbf68fe7abea77", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Boxes_ {
    /// The shape of [Boxes] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Boxes] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Boxes_"), "75f2a4800e20062790987455e4c834d2692c0e8487a6bf2a33fbf68fe7abea77", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Boxes"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("box", Param.fixed(ConstantDescs.CD_double), Param.fixed(ClassDesc.of("java.lang.Long")), Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.of("java.lang.Float")), Param.fixed(ConstantDescs.CD_float), Param.fixed(ClassDesc.of("java.lang.Byte")), Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Short")), Param.fixed(ConstantDescs.CD_short), Param.fixed(ClassDesc.of("java.lang.Integer")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.Character"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("flags", Param.fixed(ConstantDescs.CD_char), Param.fixed(ClassDesc.of("java.lang.Boolean")), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Boxes"))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Boxes] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Boxes] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12()), List.of(c0()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Boxes"), Signature.of("box", Param.fixed(ConstantDescs.CD_double), Param.fixed(ClassDesc.of("java.lang.Long")), Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.of("java.lang.Float")), Param.fixed(ConstantDescs.CD_float), Param.fixed(ClassDesc.of("java.lang.Byte")), Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Short")), Param.fixed(ConstantDescs.CD_short), Param.fixed(ClassDesc.of("java.lang.Integer")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.Character"))), List.of(), List.of(PrimitiveTypeRef.DOUBLE, Types.of(ClassDesc.of("java.lang.Long")), PrimitiveTypeRef.LONG, Types.of(ClassDesc.of("java.lang.Float")), PrimitiveTypeRef.FLOAT, Types.of(ClassDesc.of("java.lang.Byte")), PrimitiveTypeRef.BYTE, Types.of(ClassDesc.of("java.lang.Short")), PrimitiveTypeRef.SHORT, Types.of(ClassDesc.of("java.lang.Integer")), PrimitiveTypeRef.INT, Types.of(ClassDesc.of("java.lang.Character"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Double"))), List.of(), Set.of(List.of(ConstantDescs.CD_double, ClassDesc.of("java.lang.Long"), ConstantDescs.CD_long, ClassDesc.of("java.lang.Float"), ConstantDescs.CD_float, ClassDesc.of("java.lang.Byte"), ConstantDescs.CD_byte, ClassDesc.of("java.lang.Short"), ConstantDescs.CD_short, ClassDesc.of("java.lang.Integer"), ConstantDescs.CD_int, ClassDesc.of("java.lang.Character"))), List.of());
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
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Boxes"), Signature.of("flags", Param.fixed(ConstantDescs.CD_char), Param.fixed(ClassDesc.of("java.lang.Boolean")), Param.fixed(ConstantDescs.CD_boolean)), List.of(), List.of(PrimitiveTypeRef.CHAR, Types.of(ClassDesc.of("java.lang.Boolean")), PrimitiveTypeRef.BOOLEAN), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Boolean"))), List.of(), Set.of(List.of(ConstantDescs.CD_char, ClassDesc.of("java.lang.Boolean"), ConstantDescs.CD_boolean)), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Boxes"), List.of(), List.of(), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Boxes], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Boxes].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Boxes open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant double D = 1.0
        member method public overridable box(double, java.lang.Long, long, java.lang.Float, float, java.lang.Byte, byte, java.lang.Short, short, java.lang.Integer, int, java.lang.Character) -> java.lang.Double throws -
        member method public overridable flags(char, java.lang.Boolean, boolean) -> java.lang.Boolean throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Boxes box(double, java.lang.Long, long, java.lang.Float, float, java.lang.Byte, byte, java.lang.Short, short, java.lang.Integer, int, java.lang.Character) -> java.lang.Double throws - erased (double, java.lang.Long, long, java.lang.Float, float, java.lang.Byte, byte, java.lang.Short, short, java.lang.Integer, int, java.lang.Character) overrides -
        inherit method public concrete p.Boxes flags(char, java.lang.Boolean, boolean) -> java.lang.Boolean throws - erased (char, java.lang.Boolean, boolean) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete box(double, java.lang.Long, long, java.lang.Float, float, java.lang.Byte, byte, java.lang.Short, short, java.lang.Integer, int, java.lang.Character); clone(); equals(java.lang.Object); finalize(); flags(char, java.lang.Boolean, boolean); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Boxes()
        """;

        private Canonical() {
        }
    }

    /// The token of [Boxes].
    public static final OpenClassToken<Boxes> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Boxes#D].
    public static final StaticFieldRef<Double> D = UnsafeFacts.constantField(TOKEN, "D", PrimitiveToken.DOUBLE, 1.0);

    /// The fact of [Boxes#Boxes()].
    public static final CtorRef0<Boxes> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Boxes#box(double, Long, long, Float, float, Byte, byte, Short, short, Integer, int, Character)].
    public static final MethodRef12<Boxes, java.lang.Double, Double, Long, me.supcheg.javafile.facts.Prim.Long, Float, me.supcheg.javafile.facts.Prim.Float, Byte, me.supcheg.javafile.facts.Prim.Byte, Short, me.supcheg.javafile.facts.Prim.Short, Integer, Int, Character> box_double_Long_long_Float_float_Byte_byte_Short_short_Integer_int_Character = UnsafeFacts.method(TOKEN, "box", UnsafeFacts.<java.lang.Double>finalClassToken(gen.facts.java.lang.Double_.Data.SHAPE), PrimitiveToken.DOUBLE, UnsafeFacts.<Long>finalClassToken(gen.facts.java.lang.Long_.Data.SHAPE), PrimitiveToken.LONG, UnsafeFacts.<Float>finalClassToken(gen.facts.java.lang.Float_.Data.SHAPE), PrimitiveToken.FLOAT, UnsafeFacts.<Byte>finalClassToken(gen.facts.java.lang.Byte_.Data.SHAPE), PrimitiveToken.BYTE, UnsafeFacts.<Short>finalClassToken(gen.facts.java.lang.Short_.Data.SHAPE), PrimitiveToken.SHORT, UnsafeFacts.<Integer>finalClassToken(gen.facts.java.lang.Integer_.Data.SHAPE), PrimitiveToken.INT, UnsafeFacts.<Character>finalClassToken(gen.facts.java.lang.Character_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Boxes#flags(char, Boolean, boolean)].
    public static final MethodRef3<Boxes, Boolean, Char, Boolean, Bool> flags_char_Boolean_boolean = UnsafeFacts.method(TOKEN, "flags", UnsafeFacts.<Boolean>finalClassToken(gen.facts.java.lang.Boolean_.Data.SHAPE), PrimitiveToken.CHAR, UnsafeFacts.<Boolean>finalClassToken(gen.facts.java.lang.Boolean_.Data.SHAPE), PrimitiveToken.BOOLEAN, MemberTraits.OVERRIDABLE);

    private Boxes_() {
    }
}
