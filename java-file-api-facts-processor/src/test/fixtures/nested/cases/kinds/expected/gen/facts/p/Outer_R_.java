package gen.facts.p;

import gen.facts.p.Outer_R_.Canonical;
import gen.facts.p.Outer_R_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef1;
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
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Outer.R;

/// The full metamodel of [R], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [R] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Record_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = R.class, fingerprint = "95dcbb42d16dcf219310b8fb9b0130652ad048fb0e8fb4a75c23d77e15c8785a", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_R_ {
    /// The shape of [R] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [R] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_R_"), "95dcbb42d16dcf219310b8fb9b0130652ad048fb0e8fb4a75c23d77e15c8785a", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Outer$R"), List.of(), List.of(ClassDesc.of("java.lang.Record"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Outer$R", Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [R], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [R].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Outer$R final-class sealed=no\ntparams -\nsuperclasses java.lang.Record; java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(int) throws -\nmember method final equals(java.lang.Object) -> boolean throws -\nmember method final hashCode() -> int throws -\nmember method final toString() -> java.lang.String throws -\nmember method final x() -> int throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()\ntable static -\ntable ctor Outer$R(int)\n";

        private Canonical() {
        }
    }

    /// The token of [R].
    public static final FinalClassToken<R> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [R#R(int)].
    public static final CtorRef1<R, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [R#equals(Object)].
    public static final MethodRef1<R, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [R#hashCode()].
    public static final MethodRef0<R, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [R#toString()].
    public static final MethodRef0<R, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [R#x()].
    public static final MethodRef0<R, Int> x = UnsafeFacts.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.FINAL);

    private Outer_R_() {
    }
}
