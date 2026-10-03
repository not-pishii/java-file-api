package gen.facts.p;

import gen.facts.p.Seen_.Canonical;
import gen.facts.p.Seen_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Seen;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Seen.class, fingerprint = "2ed4e2deaacd4e618191e77610f99ac686f21fc3e2af4fcca3499fe47ba74c1f", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Seen_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Seen_"), "2ed4e2deaacd4e618191e77610f99ac686f21fc3e2af4fcca3499fe47ba74c1f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Seen"), List.of(), List.of(ClassDesc.of("p.Unseen"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("beyond"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("unseen"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Seen"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Seen open-class sealed=no\ntparams -\nsuperclasses p.Unseen; java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete beyond(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); unseen(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Seen()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Seen> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Seen_() {
    }
}
