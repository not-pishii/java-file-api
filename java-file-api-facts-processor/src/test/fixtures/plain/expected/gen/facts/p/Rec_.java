package gen.facts.p;

import gen.facts.p.Rec_.Canonical;
import gen.facts.p.Rec_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef1;
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
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Rec;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Rec.class, fingerprint = "e7cdc9c76c5e4bd3582c947f80225ccf6cb193c7aa0d08e97b41714ce1b1c656", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Rec_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Rec_"), "e7cdc9c76c5e4bd3582c947f80225ccf6cb193c7aa0d08e97b41714ce1b1c656", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Rec"), List.of(), List.of(ClassDesc.of("java.lang.Record"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("label"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(Signature.of("of")), Set.of(Signature.of("Rec", Param.fixed(ConstantDescs.CD_int)), Signature.of("Rec", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Rec final-class sealed=no\ntparams -\nsuperclasses java.lang.Record; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(int) throws -\nmember ctor(int, java.lang.String) throws -\nmember method final equals(java.lang.Object) -> boolean throws -\nmember method final hashCode() -> int throws -\nmember method final label() -> java.lang.String throws -\nmember method final toString() -> java.lang.String throws -\nmember method final x() -> int throws -\nmember method static of() -> p.Rec throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); label(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()\ntable static of()\ntable ctor Rec(int); Rec(int, java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Rec> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    public static final CtorRef1<Rec, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    public static final CtorRef2<Rec, Int, String> new_int_String = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final MethodRef1<Rec, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.FINAL);

    public static final MethodRef0<Rec, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);

    public static final MethodRef0<Rec, String> label = UnsafeFacts.method(TOKEN, "label", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final StaticMethodRef0<Rec> of = UnsafeFacts.staticMethod(TOKEN, "of", TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Rec, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final MethodRef0<Rec, Int> x = UnsafeFacts.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.FINAL);

    private Rec_() {
    }
}
