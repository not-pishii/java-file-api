package gen.facts.p;

import gen.facts.p.ByHidden_.Canonical;
import gen.facts.p.ByHidden_.Data;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.ByHidden;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ByHidden.class, fingerprint = "951415dcb7ea9777fe4f8ba9b08636b9da8a0d8cc2d5023ab8421f30b3e8acaa", complete = false, format = 5)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class ByHidden_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.ByHidden_"), "951415dcb7ea9777fe4f8ba9b08636b9da8a0d8cc2d5023ab8421f30b3e8acaa", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.ByHidden"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Hidden"))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("ByHidden"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.ByHidden open-class sealed=no\ntparams #0 extends p.Hidden\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor ByHidden()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<ByHidden> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private ByHidden_() {
    }
}
