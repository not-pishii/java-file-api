package gen.facts.p;

import gen.facts.p.Low_.Canonical;
import gen.facts.p.Low_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Low;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Low.class, fingerprint = "7b95f41ef634cfb4e8afa655bcf299765e203708c7d118162f0fdff319c9d3a5", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Low_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Low_"), "7b95f41ef634cfb4e8afa655bcf299765e203708c7d118162f0fdff319c9d3a5", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Low"), List.of(), List.of(ClassDesc.of("p.High"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("seen"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Low"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Low open-class sealed=no\ntparams -\nsuperclasses p.High; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); seen(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Low()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Low> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Low> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Low_() {
    }
}
