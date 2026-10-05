package gen.facts.p;

import gen.facts.p.PubAbs_.Canonical;
import gen.facts.p.PubAbs_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.PubAbs;

/// The full metamodel of [PubAbs], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubAbs] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PStr_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubAbs.class, fingerprint = "eef718bc37226c0a4eea8e548e25b8eb6ffa4f8f4d190bc954f5cbfffdf60435", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PubAbs_ {
    /// The shape of [PubAbs] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubAbs] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubAbs_"), "eef718bc37226c0a4eea8e548e25b8eb6ffa4f8f4d190bc954f5cbfffdf60435", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.PubAbs"), List.of(), List.of(ClassDesc.of("p.HAbs"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubAbs"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [PubAbs], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubAbs].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.PubAbs abstract-class sealed=no\ntparams -\nsuperclasses p.HAbs; java.lang.Object\ninterfaces p.PStr\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract get()\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor PubAbs()\n";

        private Canonical() {
        }
    }

    /// The token of [PubAbs].
    public static final AbstractClassToken<PubAbs> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [PubAbs#PubAbs()].
    public static final AbstractCtorRef0<PubAbs> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    private PubAbs_() {
    }
}
