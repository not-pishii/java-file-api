package gen.facts.p;

import gen.facts.p.Lower_.Canonical;
import gen.facts.p.Lower_.Data;
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
import p.Lower;

/// The token-only metamodel of [Lower]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Lower]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Lower.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Lower.class, fingerprint = "45054cd1d440e20b6749d9ffd04fbc08344b25f608c28ee28364ff94917d1068", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Lower_ {
    /// The shape of [Lower] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Lower] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Lower_"), "45054cd1d440e20b6749d9ffd04fbc08344b25f608c28ee28364ff94917d1068", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Lower"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Lower"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Lower], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Lower].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Lower open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Lower()\n";

        private Canonical() {
        }
    }

    /// The token of [Lower].
    public static final OpenClassToken<Lower> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Lower_() {
    }
}
