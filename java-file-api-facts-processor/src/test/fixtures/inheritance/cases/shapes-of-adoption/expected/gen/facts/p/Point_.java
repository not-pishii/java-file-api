package gen.facts.p;

import gen.facts.p.Point_.Canonical;
import gen.facts.p.Point_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Point;

/// The full metamodel of [Point], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Point] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [gen.facts.java.lang.Record_].
///
/// `p.HLabel`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Point.class, fingerprint = "fd55ddb514d2d0c562a2bca4ef7ce95fd0624318073ef81f1b873051e82b9fb0", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Point_ {
    /// The shape of [Point] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Point] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Point_"), "fd55ddb514d2d0c562a2bca4ef7ce95fd0624318073ef81f1b873051e82b9fb0", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Point"), List.of(), List.of(ClassDesc.of("java.lang.Record"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("label"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x"), Signature.of("y")), Set.of(), Set.of(Signature.of("Point", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Point], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Point].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Point final-class sealed=no\ntparams -\nsuperclasses java.lang.Record; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(int, int) throws -\nmember field static constant java.lang.String NAME = \"name\"\nmember method final equals(java.lang.Object) -> boolean throws -\nmember method final hashCode() -> int throws -\nmember method final label() -> java.lang.String throws -\nmember method final toString() -> java.lang.String throws -\nmember method final x() -> int throws -\nmember method final y() -> int throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); label(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x(); y()\ntable static -\ntable ctor Point(int, int)\n";

        private Canonical() {
        }
    }

    /// The token of [Point].
    public static final FinalClassToken<Point> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [Point#NAME], declared in `p.HLabel`, which is not `public`.
    public static final StaticFieldRef<String> NAME = UnsafeFacts.constantField(TOKEN, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "name");

    /// The fact of [Point#Point(int, int)].
    public static final CtorRef2<Point, Int, Int> new_int_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Point#equals(Object)].
    public static final MethodRef1<Point, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Point#hashCode()].
    public static final MethodRef0<Point, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Point#label()], declared in `p.HLabel`, which is not `public`.
    public static final MethodRef0<Point, String> label = UnsafeFacts.method(TOKEN, "label", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Point#toString()].
    public static final MethodRef0<Point, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Point#x()].
    public static final MethodRef0<Point, Int> x = UnsafeFacts.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Point#y()].
    public static final MethodRef0<Point, Int> y = UnsafeFacts.method(TOKEN, "y", PrimitiveToken.INT, MemberTraits.FINAL);

    private Point_() {
    }
}
