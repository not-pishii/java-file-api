package gen.facts.p;

import gen.facts.p.Field1_.Canonical;
import gen.facts.p.Field1_.Data;
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
import p.Field1;

/// The token-only metamodel of [Field1]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Field1]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Field1.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Field1.class, fingerprint = "50bd50947b2878bfcfaf686ba9d5281dee3eb6ce44f1580b4c925b74535e9c92", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Field1_ {
    /// The shape of [Field1] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Field1] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Field1_"), "50bd50947b2878bfcfaf686ba9d5281dee3eb6ce44f1580b4c925b74535e9c92", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Field1"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Field1"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Field1], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Field1].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Field1 open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Field1()\n";

        private Canonical() {
        }
    }

    /// The token of [Field1].
    public static final OpenClassToken<Field1> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Field1_() {
    }
}
