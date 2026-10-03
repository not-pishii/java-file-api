package gen.facts.p;

import gen.facts.p.Data_.Canonical;
import gen.facts.p.Data_.Data;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = p.Data.class, fingerprint = "2248f1b6867bbbe80217ef435a1dc78f36e2a1e3b409bf23b93a45790a535800", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Data_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Data_"), "2248f1b6867bbbe80217ef435a1dc78f36e2a1e3b409bf23b93a45790a535800", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Data"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("self"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Data"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Data open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable p.Canonical canonical\nmember method overridable self() -> p.Data throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); self(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Data()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<p.Data> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<p.Data, p.Canonical> canonical = UnsafeFacts.mutableField(TOKEN, "canonical", UnsafeFacts.<p.Canonical>openClassToken(Canonical_.Data.SHAPE));

    public static final CtorRef0<p.Data> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<p.Data, p.Data> self = UnsafeFacts.method(TOKEN, "self", TOKEN, MemberTraits.OVERRIDABLE);

    private Data_() {
    }
}
