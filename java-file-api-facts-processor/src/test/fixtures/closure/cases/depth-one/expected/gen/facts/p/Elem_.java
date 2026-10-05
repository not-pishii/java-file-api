package gen.facts.p;

import gen.facts.p.Elem_.Canonical;
import gen.facts.p.Elem_.Data;
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
import p.Elem;

/// The token-only metamodel of [Elem]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Elem]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Elem.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Elem.class, fingerprint = "785e1952ff304aff44f0654ff305d7e707efccc45b18682ca507f1c3fd3d35d1", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Elem_ {
    /// The shape of [Elem] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Elem] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Elem_"), "785e1952ff304aff44f0654ff305d7e707efccc45b18682ca507f1c3fd3d35d1", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Elem"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Elem"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Elem], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Elem].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Elem open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Elem()\n";

        private Canonical() {
        }
    }

    /// The token of [Elem].
    public static final OpenClassToken<Elem> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Elem_() {
    }
}
