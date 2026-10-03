package gen.facts.p;

import gen.facts.p.Other_.Canonical;
import gen.facts.p.Other_.Data;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Greeter;
import p.Other;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Other.class, fingerprint = "85c86099b39e69dfe7f125a09dd608a426bf2d37ab93e0e3f64a0b3b57385b4e", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Other_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Other_"), "85c86099b39e69dfe7f125a09dd608a426bf2d37ab93e0e3f64a0b3b57385b4e", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Other"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("greeter"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other", Param.fixed(ClassDesc.of("p.Greeter"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Other"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Other open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable greeter() -> p.Greeter throws -\nmember method overridable other(p.Greeter) -> p.Other throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); greeter(); hashCode(); notify(); notifyAll(); other(p.Greeter); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Other()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Other> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Other> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Other, Greeter> greeter = UnsafeFacts.method(TOKEN, "greeter", UnsafeFacts.<Greeter>openClassToken(Greeter_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef1<Other, Other, Greeter> other_Greeter = UnsafeFacts.method(TOKEN, "other", TOKEN, UnsafeFacts.<Greeter>openClassToken(Greeter_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Other_() {
    }
}
