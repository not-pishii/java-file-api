package gen.facts.p;

import gen.facts.p.Canonical_.Canonical;
import gen.facts.p.Canonical_.Data;
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

/// The token-only metamodel of [p.Canonical]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [p.Canonical]: it is only mentioned in the signatures of [p.List]. For the facts of its members add `Canonical.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = p.Canonical.class, fingerprint = "d50b9cd46f3c5b9730ea239ccf335f67e119c7b9a01752f589c83ed8ea18cf7a", complete = false, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Canonical_ {
    /// The shape of [p.Canonical] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [p.Canonical] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Canonical_"), "d50b9cd46f3c5b9730ea239ccf335f67e119c7b9a01752f589c83ed8ea18cf7a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Canonical"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Canonical"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [p.Canonical], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [p.Canonical].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Canonical open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Canonical()\n";

        private Canonical() {
        }
    }

    /// The token of [p.Canonical].
    public static final OpenClassToken<p.Canonical> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Canonical_() {
    }
}
