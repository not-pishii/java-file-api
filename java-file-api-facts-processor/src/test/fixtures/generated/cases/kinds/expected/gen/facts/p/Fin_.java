package gen.facts.p;

import gen.facts.p.Fin_.Canonical;
import gen.facts.p.Fin_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Fin;

/// The full metamodel of [Fin], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fin] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fin.class, fingerprint = "5a2afb4e5740e0ce8ca1b10bb0bfa84b69b732157665a8b547f51e6bcb8c4af2", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Fin_ {
    /// The shape of [Fin] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Fin] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fin_"), "5a2afb4e5740e0ce8ca1b10bb0bfa84b69b732157665a8b547f51e6bcb8c4af2", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Fin"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Fin"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Fin], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Fin].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Fin final-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Fin()\n";

        private Canonical() {
        }
    }

    /// The token of [Fin].
    public static final FinalClassToken<Fin> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [Fin#Fin()].
    public static final CtorRef0<Fin> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Fin_() {
    }
}
