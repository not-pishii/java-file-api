package gen.facts.p;

import gen.facts.p.MBound_.Canonical;
import gen.facts.p.MBound_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.MBound;

/// The token-only metamodel of [MBound]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [MBound]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `MBound.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = MBound.class, fingerprint = "781b96897ccc528d4d14db218e61fb0943244718feefbcbbd396d0181256bc01", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class MBound_ {
    /// The shape of [MBound] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [MBound] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.MBound_"), "781b96897ccc528d4d14db218e61fb0943244718feefbcbbd396d0181256bc01", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.MBound"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [MBound], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [MBound].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.MBound interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [MBound].
    public static final InterfaceToken<MBound> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private MBound_() {
    }
}
