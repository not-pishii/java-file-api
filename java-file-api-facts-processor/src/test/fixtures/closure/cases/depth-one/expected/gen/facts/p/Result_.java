package gen.facts.p;

import gen.facts.p.Result_.Canonical;
import gen.facts.p.Result_.Data;
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
import p.Result;

/// The token-only metamodel of [Result]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Result]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Result.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Result.class, fingerprint = "2a88b909bfa13bc2fcc17532987a6b51ac1fb28878bf7ed640579b79e858485a", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Result_ {
    /// The shape of [Result] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Result] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Result_"), "2a88b909bfa13bc2fcc17532987a6b51ac1fb28878bf7ed640579b79e858485a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Result"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("deep"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Result"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Result], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Result].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Result open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); deep(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Result()\n";

        private Canonical() {
        }
    }

    /// The token of [Result].
    public static final OpenClassToken<Result> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Result_() {
    }
}
