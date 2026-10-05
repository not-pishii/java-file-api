package gen.facts.p;

import gen.facts.p.Witness_.Canonical;
import gen.facts.p.Witness_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
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
import p.Witness;

/// The full metamodel of [Witness], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Witness] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Witness.class, fingerprint = "f7297e9bfe8d4b6f880e2bb9078ce1bcfc2a5bc28d1bd377f48ade5eb09ca19b", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Witness_ {
    /// The shape of [Witness] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Witness] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Witness_"), "f7297e9bfe8d4b6f880e2bb9078ce1bcfc2a5bc28d1bd377f48ade5eb09ca19b", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Witness"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Witness"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Witness], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Witness].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Witness open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Witness()\n";

        private Canonical() {
        }
    }

    /// The token of [Witness].
    public static final OpenClassToken<Witness> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Witness#Witness()].
    public static final CtorRef0<Witness> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Witness_() {
    }
}
