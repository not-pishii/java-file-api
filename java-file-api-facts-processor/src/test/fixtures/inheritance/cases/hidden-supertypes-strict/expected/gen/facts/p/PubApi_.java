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

/// The full metamodel of [PubApi]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [PubApi]: it is here as a supertype of [p.Pub], whose inherited members are called through this metamodel.
///
/// A member [PubApi] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubApi.class, fingerprint = "b77aad8f2b353331193eedc514b3fe38b7c85a4e71b8bf443457f84bcd328e1f", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PubApi_ {
    /// The shape of [PubApi] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [PubApi] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubApi_"), "b77aad8f2b353331193eedc514b3fe38b7c85a4e71b8bf443457f84bcd328e1f", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PubApi"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("pub")), Set.of(Signature.of("beyond"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [PubApi], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [PubApi].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.PubApi interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract pub() -> java.lang.String throws -\nmember method overridable beyond() -> java.lang.String throws -\nsam pub() -> java.lang.String throws -\ntable abstract pub()\ntable concrete beyond(); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [PubApi].
    public static final InterfaceToken<PubApi> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [PubApi#beyond()].
    public static final MethodRef0<PubApi, String> beyond = UnsafeFacts.method(TOKEN, "beyond", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubApi#pub()].
    public static final MethodRef0<PubApi, String> pub = UnsafeFacts.method(TOKEN, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [PubApi#pub()], which a lambda implements.
    public static final Sam0<PubApi, String> sam = UnsafeFacts.sam(pub);

    private PubApi_() {
    }
}
