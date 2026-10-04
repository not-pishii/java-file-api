package gen.facts.p;

import gen.facts.p.Gen_St_.Canonical;
import gen.facts.p.Gen_St_.Data;
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
import p.Gen.St;

/// The full metamodel of [St], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [St] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = St.class, fingerprint = "8780bfb9ac44ab8a2555036c7aa877d81004fe28950f96051ec813c957653945", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Gen_St_ {
    /// The shape of [St] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [St] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Gen_St_"), "8780bfb9ac44ab8a2555036c7aa877d81004fe28950f96051ec813c957653945", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Gen$St"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Gen$St"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [St], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [St].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Gen$St open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Gen$St()\n";

        private Canonical() {
        }
    }

    /// The token of [St].
    public static final OpenClassToken<St> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [St#St()].
    public static final CtorRef0<St> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Gen_St_() {
    }
}
