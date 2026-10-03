package gen.facts.p;

import gen.facts.p.Pub1_.Canonical;
import gen.facts.p.Pub1_.Data;
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
import p.Pub1;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Pub1.class, fingerprint = "a6a91f558cd5d65237f4a917ba7f3560c9036bc66562e60d670b075cfdd1d158", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Pub1_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Pub1_"), "a6a91f558cd5d65237f4a917ba7f3560c9036bc66562e60d670b075cfdd1d158", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Pub1"), List.of(), List.of(ClassDesc.of("p.H0"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("one"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("zero")), Set.of(), Set.of(Signature.of("Pub1"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Pub1 open-class sealed=no\ntparams -\nsuperclasses p.H0; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable one() -> java.lang.String throws -\nmember method overridable zero() -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); one(); toString(); wait(); wait(long); wait(long, int); zero()\ntable static -\ntable ctor Pub1()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Pub1> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Pub1> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Pub1, String> one = UnsafeFacts.method(TOKEN, "one", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Pub1, String> zero = UnsafeFacts.method(TOKEN, "zero", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Pub1_() {
    }
}
