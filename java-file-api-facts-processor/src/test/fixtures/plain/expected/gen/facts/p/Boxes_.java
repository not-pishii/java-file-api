package gen.facts.p;

import gen.facts.p.Boxes_.Canonical;
import gen.facts.p.Boxes_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
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
import p.Boxes;

/// The full metamodel of [Boxes], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Boxes] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Boxes.class, fingerprint = "f2e477d1d6359fab82eda1e5f97afcb4422cc512100ba3709d3a4eb5f8065364", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Boxes_ {
    /// The shape of [Boxes] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Boxes] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Boxes_"), "f2e477d1d6359fab82eda1e5f97afcb4422cc512100ba3709d3a4eb5f8065364", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Boxes"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("box", Param.fixed(ConstantDescs.CD_double), Param.fixed(ClassDesc.of("java.lang.Long")), Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.of("java.lang.Float")), Param.fixed(ConstantDescs.CD_float), Param.fixed(ClassDesc.of("java.lang.Byte")), Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Short")), Param.fixed(ConstantDescs.CD_short), Param.fixed(ClassDesc.of("java.lang.Integer")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.Character"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("flags", Param.fixed(ConstantDescs.CD_char), Param.fixed(ClassDesc.of("java.lang.Boolean")), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Boxes"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Boxes], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Boxes].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Boxes open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant double D = 1.0\nmember method overridable box(double, java.lang.Long, long, java.lang.Float, float, java.lang.Byte, byte, java.lang.Short, short, java.lang.Integer, int, java.lang.Character) -> java.lang.Double throws -\nmember method overridable flags(char, java.lang.Boolean, boolean) -> java.lang.Boolean throws -\ntable abstract -\ntable concrete box(double, java.lang.Long, long, java.lang.Float, float, java.lang.Byte, byte, java.lang.Short, short, java.lang.Integer, int, java.lang.Character); clone(); equals(java.lang.Object); finalize(); flags(char, java.lang.Boolean, boolean); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Boxes()\n";

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
