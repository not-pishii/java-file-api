package gen.facts.p;

import gen.facts.p.Mentions_.Canonical;
import gen.facts.p.Mentions_.Data;
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
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.ByDollar;
import p.ByHidden;
import p.ByMarker;
import p.Mentions;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mentions.class, fingerprint = "1aa73f43710011d497393cd472e86214490cdcbd30c785ca0c7325d5f0325c71", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Mentions_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mentions_"), "1aa73f43710011d497393cd472e86214490cdcbd30c785ca0c7325d5f0325c71", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mentions"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("dollar"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("marker"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Mentions"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Mentions open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable dollar() -> p.ByDollar<?> throws -\nmember method overridable hidden() -> p.ByHidden<?> throws -\nmember method overridable marker() -> p.ByMarker<?> throws -\ntable abstract -\ntable concrete clone(); dollar(); equals(java.lang.Object); finalize(); getClass(); hashCode(); hidden(); marker(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Mentions()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Mentions> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Mentions> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Mentions, ByDollar<?>> dollar = UnsafeFacts.method(TOKEN, "dollar", UnsafeFacts.<ByDollar<?>>openClassToken(ByDollar_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Mentions, ByHidden<?>> hidden = UnsafeFacts.method(TOKEN, "hidden", UnsafeFacts.<ByHidden<?>>openClassToken(ByHidden_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Mentions, ByMarker<?>> marker = UnsafeFacts.method(TOKEN, "marker", UnsafeFacts.<ByMarker<?>>openClassToken(ByMarker_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    private Mentions_() {
    }
}
