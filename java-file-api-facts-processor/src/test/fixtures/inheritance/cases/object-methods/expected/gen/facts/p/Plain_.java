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

/// The full metamodel of [Plain], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Plain] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Plain.class, fingerprint = "f061374a9f3071a25c2e7bf229c2e7012733838a2cee2d1def4d207a61593d25", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Plain_ {
    /// The shape of [Plain] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Plain] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Plain_"), "f061374a9f3071a25c2e7bf229c2e7012733838a2cee2d1def4d207a61593d25", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Plain"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Plain"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Plain], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Plain].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Plain open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable plain() -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); plain(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Plain()\n";

        private Canonical() {
        }
    }

    /// The token of [Plain].
    public static final OpenClassToken<Plain> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Plain#Plain()].
    public static final CtorRef0<Plain> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Plain#plain()].
    public static final VoidMethodRef0<Plain> plain = UnsafeFacts.voidMethod(TOKEN, "plain", MemberTraits.OVERRIDABLE);

    private Plain_() {
    }
}
