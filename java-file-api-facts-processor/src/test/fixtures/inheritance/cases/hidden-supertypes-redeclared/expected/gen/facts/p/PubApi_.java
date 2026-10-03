package gen.facts.p;

import gen.facts.p.PubApi_.Canonical;
import gen.facts.p.PubApi_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PubApi;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubApi.class, fingerprint = "ed0032bc2c8a4555eb5284f10795b2338ac65b4aba40365af6395075c8a95498", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PubApi_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubApi_"), "ed0032bc2c8a4555eb5284f10795b2338ac65b4aba40365af6395075c8a95498", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PubApi"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("pub")), Set.of(Signature.of("beyond"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.PubApi interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract pub() -> java.lang.String throws -\nmember method overridable beyond() -> java.lang.String throws -\nsam pub() -> java.lang.String throws -\ntable abstract pub()\ntable concrete beyond(); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<PubApi> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final MethodRef0<PubApi, String> beyond = UnsafeFacts.method(TOKEN, "beyond", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<PubApi, String> pub = UnsafeFacts.method(TOKEN, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    public static final Sam0<PubApi, String> sam = UnsafeFacts.sam(pub);

    private PubApi_() {
    }
}
