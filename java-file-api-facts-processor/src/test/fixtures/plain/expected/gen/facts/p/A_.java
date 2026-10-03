package gen.facts.p;

import gen.facts.p.A_.Canonical;
import gen.facts.p.A_.Data;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.A;
import p.B;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = A.class, fingerprint = "1683b620e9a508f4c59857e77f8e05872cc54b9637036673825c9c328ba60865", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class A_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.A_"), "1683b620e9a508f4c59857e77f8e05872cc54b9637036673825c9c328ba60865", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.A"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("b"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("self"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("A"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.A open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable b() -> p.B throws -\nmember method overridable self() -> p.A throws -\ntable abstract -\ntable concrete b(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); self(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor A()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<A> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<A> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<A, B> b = UnsafeFacts.method(TOKEN, "b", UnsafeFacts.<B>openClassToken(B_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<A, A> self = UnsafeFacts.method(TOKEN, "self", TOKEN, MemberTraits.OVERRIDABLE);

    private A_() {
    }
}
