package gen.facts.p;

import gen.facts.p.Plain_.Canonical;
import gen.facts.p.Plain_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Plain;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Plain.class, fingerprint = "ebc0deb18271930149a045f8dee6f24080d8b54a3cbe619c13ba3d54bde4fc85", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Plain_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Plain_"), "ebc0deb18271930149a045f8dee6f24080d8b54a3cbe619c13ba3d54bde4fc85", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Plain"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Plain"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Plain open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable plain() -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); plain(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Plain()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Plain> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Plain> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final VoidMethodRef0<Plain> plain = UnsafeFacts.voidMethod(TOKEN, "plain", MemberTraits.OVERRIDABLE);

    private Plain_() {
    }
}
