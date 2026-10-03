package b.facts.p;

import b.facts.p.OnHid_.Canonical;
import b.facts.p.OnHid_.Data;
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
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.OnHid;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = OnHid.class, fingerprint = "0186ba774a998b92577bd307c7ba007fdecea6da09fec81fcc086904701495e3", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class OnHid_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("b.facts.p.OnHid_"), "0186ba774a998b92577bd307c7ba007fdecea6da09fec81fcc086904701495e3", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.OnHid"), List.of(), List.of(ClassDesc.of("p.HidBase"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("renamed"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("OnHid"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.OnHid open-class sealed=no\ntparams -\nsuperclasses p.HidBase; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable renamed() -> int throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); renamed(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor OnHid()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<OnHid> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<OnHid> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<OnHid, Int> renamed = UnsafeFacts.method(TOKEN, "renamed", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private OnHid_() {
    }
}
