package gen.facts.p;

import gen.facts.p.Data_.Canonical;
import gen.facts.p.Data_.Data;
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

/// The full metamodel of [p.Data], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [p.Data] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = p.Data.class, fingerprint = "2a901784912c1044aac2ea5126c9cb032ebbee53c7a6befa7cf98b294c300cee", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Data_ {
    /// The shape of [p.Data] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [p.Data] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Data_"), "2a901784912c1044aac2ea5126c9cb032ebbee53c7a6befa7cf98b294c300cee", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Data"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Data"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [p.Data], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [p.Data].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Data open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Data()\n";

        private Canonical() {
        }
    }

    /// The token of [p.Data].
    public static final OpenClassToken<p.Data> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [p.Data#Data()].
    public static final CtorRef0<p.Data> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Data_() {
    }
}
