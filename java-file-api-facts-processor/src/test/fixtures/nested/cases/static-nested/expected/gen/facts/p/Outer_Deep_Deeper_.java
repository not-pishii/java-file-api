package gen.facts.p;

import gen.facts.p.Outer_Deep_Deeper_.Canonical;
import gen.facts.p.Outer_Deep_Deeper_.Data;
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
import p.Outer.Deep.Deeper;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Deeper.class, fingerprint = "3cd804c38060e58c20a455f5baac5ef7de50ce4ab59bb7d52c6be79ff2ef996f", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_Deep_Deeper_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_Deep_Deeper_"), "3cd804c38060e58c20a455f5baac5ef7de50ce4ab59bb7d52c6be79ff2ef996f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Outer$Deep$Deeper"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Outer$Deep$Deeper"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Outer$Deep$Deeper open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Outer$Deep$Deeper()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Deeper> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Deeper> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Outer_Deep_Deeper_() {
    }
}
