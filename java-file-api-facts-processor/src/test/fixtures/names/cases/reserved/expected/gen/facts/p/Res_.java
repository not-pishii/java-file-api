package gen.facts.p;

import gen.facts.p.Res_.Canonical;
import gen.facts.p.Res_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Res;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Res.class, fingerprint = "cef8563efef8a280814f6634b59e452ac7b9ddcbae8d8c10a628313602e87fa4", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Res_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Res_"), "cef8563efef8a280814f6634b59e452ac7b9ddcbae8d8c10a628313602e87fa4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Res"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("count"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("sam", Param.fixed(ConstantDescs.CD_int)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Res"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Res open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int ANY\nmember field instance mutable int Canonical\nmember field instance mutable int Data\nmember field instance mutable int TOKEN\nmember field instance mutable int count\nmember field instance mutable int sam\nmember field instance mutable int switch_\nmember field instance mutable int token\nmember method overridable count() -> int throws -\nmember method overridable sam(int) -> void throws -\ntable abstract -\ntable concrete clone(); count(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); sam(int); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Res()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Res> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<Res, Int> ANY_ = UnsafeFacts.mutableField(TOKEN, "ANY", PrimitiveToken.INT);

    public static final MutableFieldRef<Res, Int> Canonical_ = UnsafeFacts.mutableField(TOKEN, "Canonical", PrimitiveToken.INT);

    public static final MutableFieldRef<Res, Int> Data_ = UnsafeFacts.mutableField(TOKEN, "Data", PrimitiveToken.INT);

    public static final MutableFieldRef<Res, Int> TOKEN_ = UnsafeFacts.mutableField(TOKEN, "TOKEN", PrimitiveToken.INT);

    public static final MutableFieldRef<Res, Int> count = UnsafeFacts.mutableField(TOKEN, "count", PrimitiveToken.INT);

    public static final MutableFieldRef<Res, Int> sam_ = UnsafeFacts.mutableField(TOKEN, "sam", PrimitiveToken.INT);

    public static final MutableFieldRef<Res, Int> switch__ = UnsafeFacts.mutableField(TOKEN, "switch_", PrimitiveToken.INT);

    public static final MutableFieldRef<Res, Int> token_ = UnsafeFacts.mutableField(TOKEN, "token", PrimitiveToken.INT);

    public static final CtorRef0<Res> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Res, Int> count_ = UnsafeFacts.method(TOKEN, "count", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<Res, Int> sam_int = UnsafeFacts.voidMethod(TOKEN, "sam", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private Res_() {
    }
}
