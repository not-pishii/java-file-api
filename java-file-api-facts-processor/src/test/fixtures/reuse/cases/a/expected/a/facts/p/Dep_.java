package a.facts.p;

import a.facts.p.Dep_.Canonical;
import a.facts.p.Dep_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Dep;

/// The token-only metamodel of [Dep]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Dep]: it is only mentioned in the signatures of [p.Svc]. For the facts of its members add `Dep.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Dep.class, fingerprint = "37996b5eec4f166c876894c450a6b97628467c8d9cd84127a23597b8bf7b8af9", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Dep_ {
    /// The shape of [Dep] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Dep] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("a.facts.p.Dep_"), "37996b5eec4f166c876894c450a6b97628467c8d9cd84127a23597b8bf7b8af9", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Dep"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Dep"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Dep], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Dep].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Dep open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()\ntable static -\ntable ctor Dep()\n";

        private Canonical() {
        }
    }

    /// The token of [Dep].
    public static final OpenClassToken<Dep> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Dep_() {
    }
}
