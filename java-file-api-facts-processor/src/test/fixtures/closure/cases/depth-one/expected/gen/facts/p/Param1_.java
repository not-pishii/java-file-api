package gen.facts.p;

import gen.facts.p.Param1_.Canonical;
import gen.facts.p.Param1_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Param1;

/// The token-only metamodel of [Param1]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Param1]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Param1.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Param1.class, fingerprint = "eda76178871487bfee9298383697e56b47c522b7061e5b74392a55eaf58dc5f8", complete = false, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Param1_ {
    /// The shape of [Param1] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Param1] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Param1_"), "eda76178871487bfee9298383697e56b47c522b7061e5b74392a55eaf58dc5f8", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Param1"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Param1"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Param1], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Param1].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Param1 open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Param1()\n";

        private Canonical() {
        }
    }

    /// The token of [Param1].
    public static final OpenClassToken<Param1> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Param1_() {
    }
}
