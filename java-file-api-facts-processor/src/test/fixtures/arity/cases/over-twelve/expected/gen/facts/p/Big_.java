package gen.facts.p;

import gen.facts.p.Big_.Canonical;
import gen.facts.p.Big_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Big;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Big.class, fingerprint = "e4b7eda8a46610fdb09c8b776a5101728b6eb2b19b3e13869b804e13c3f2c542", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Big_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Big_"), "e4b7eda8a46610fdb09c8b776a5101728b6eb2b19b3e13869b804e13c3f2c542", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Big"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ok"), Signature.of("thirteen", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toString"), Signature.of("twelve", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("staticThirteen", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("Big", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("Big", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Big open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(int, int, int, int, int, int, int, int, int, int, int, int) throws -\nmember method overridable ok() -> void throws -\nmember method overridable twelve(int, int, int, int, int, int, int, int, int, int, int, int) -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); ok(); thirteen(int, int, int, int, int, int, int, int, int, int, int, int, int); toString(); twelve(int, int, int, int, int, int, int, int, int, int, int, int); wait(); wait(long); wait(long, int)\ntable static staticThirteen(int, int, int, int, int, int, int, int, int, int, int, int, int)\ntable ctor Big(int, int, int, int, int, int, int, int, int, int, int, int); Big(int, int, int, int, int, int, int, int, int, int, int, int, int)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Big> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef12<Big, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int> new_int_int_int_int_int_int_int_int_int_int_int_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    public static final VoidMethodRef0<Big> ok = UnsafeFacts.voidMethod(TOKEN, "ok", MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef12<Big, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int> twelve_int_int_int_int_int_int_int_int_int_int_int_int = UnsafeFacts.voidMethod(TOKEN, "twelve", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private Big_() {
    }
}
