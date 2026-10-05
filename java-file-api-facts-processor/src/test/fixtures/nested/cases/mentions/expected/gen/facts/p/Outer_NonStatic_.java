package gen.facts.p;

import gen.facts.p.Outer_NonStatic_.Canonical;
import gen.facts.p.Outer_NonStatic_.Data;
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
import p.Outer.NonStatic;

/// The token-only metamodel of [NonStatic]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [NonStatic]: it is only mentioned in the signatures of [p.Outer]. For the facts of its members add `Outer.NonStatic.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = NonStatic.class, fingerprint = "517ca4179482c54eb0a073587e8adf3fca64356416a93dd5a2e030cbeefb9d9d", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_NonStatic_ {
    /// The shape of [NonStatic] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [NonStatic] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_NonStatic_"), "517ca4179482c54eb0a073587e8adf3fca64356416a93dd5a2e030cbeefb9d9d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Outer$NonStatic"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Outer$NonStatic"), Signature.of("Outer$NonStatic", Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [NonStatic], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [NonStatic].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Outer$NonStatic open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()\ntable static -\ntable ctor Outer$NonStatic(); Outer$NonStatic(int)\n";

        private Canonical() {
        }
    }

    /// The token of [NonStatic].
    public static final OpenClassToken<NonStatic> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Outer_NonStatic_() {
    }
}
