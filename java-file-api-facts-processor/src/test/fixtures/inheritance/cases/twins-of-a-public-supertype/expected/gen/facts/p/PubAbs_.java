package gen.facts.p;

import gen.facts.p.PubAbs_.Canonical;
import gen.facts.p.PubAbs_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PubAbs;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubAbs.class, fingerprint = "b2aa06f6513b7abbf0215250aeda7ba1f2c5c00927c89efe86be64622a474c83", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PubAbs_ {
    public static final class Data {
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubAbs_"), "b2aa06f6513b7abbf0215250aeda7ba1f2c5c00927c89efe86be64622a474c83", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.PubAbs"), List.of(), List.of(ClassDesc.of("p.HAbs"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubAbs"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.PubAbs abstract-class sealed=no\ntparams -\nsuperclasses p.HAbs; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract get()\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor PubAbs()\n";

        private Canonical() {
        }
    }

    public static final AbstractClassToken<PubAbs> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    public static final AbstractCtorRef0<PubAbs> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    private PubAbs_() {
    }
}
