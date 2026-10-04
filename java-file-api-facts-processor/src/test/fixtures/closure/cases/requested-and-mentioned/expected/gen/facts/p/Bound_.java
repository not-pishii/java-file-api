package gen.facts.p;

import gen.facts.p.Bound_.Canonical;
import gen.facts.p.Bound_.Data;
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
import p.Bound;

/// The token-only metamodel of [Bound]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Bound]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Bound.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Bound.class, fingerprint = "71ab2b4a39374e0e5aca53a0cc3e5a9d76bd47a56654ac68c7f010515a345859", complete = false, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Bound_ {
    /// The shape of [Bound] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Bound] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Bound_"), "71ab2b4a39374e0e5aca53a0cc3e5a9d76bd47a56654ac68c7f010515a345859", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Bound"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Bound], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Bound].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Bound interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Bound].
    public static final InterfaceToken<Bound> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Bound_() {
    }
}
