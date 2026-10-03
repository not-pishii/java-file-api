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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = R.class, fingerprint = "801e6900fefde5c318dc08814355f0b400985e9ae82cef897271f6170ab97eef", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_R_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_R_"), "801e6900fefde5c318dc08814355f0b400985e9ae82cef897271f6170ab97eef", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Outer$R"), List.of(), List.of(ClassDesc.of("java.lang.Record"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Outer$R", Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Outer$R final-class sealed=no\ntparams -\nsuperclasses java.lang.Record; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(int) throws -\nmember method final equals(java.lang.Object) -> boolean throws -\nmember method final hashCode() -> int throws -\nmember method final toString() -> java.lang.String throws -\nmember method final x() -> int throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()\ntable static -\ntable ctor Outer$R(int)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<R> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    public static final CtorRef1<R, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    public static final MethodRef1<R, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.FINAL);

    public static final MethodRef0<R, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);

    public static final MethodRef0<R, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final MethodRef0<R, Int> x = UnsafeFacts.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.FINAL);

    private Outer_R_() {
    }
}
