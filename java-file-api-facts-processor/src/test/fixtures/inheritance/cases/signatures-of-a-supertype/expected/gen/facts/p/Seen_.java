package gen.facts.p;

import gen.facts.p.Seen_.Canonical;
import gen.facts.p.Seen_.Data;
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
import p.Seen;

/// The token-only metamodel of [Seen]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Seen]: it is only mentioned in the signatures of [p.High]. For the facts of its members add `Seen.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Seen.class, fingerprint = "485f38468228ab53fed0c0220c96ee1e690de467e99c5e081ea3490a46362f9e", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Seen_ {
    /// The shape of [Seen] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Seen] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Seen_"), "485f38468228ab53fed0c0220c96ee1e690de467e99c5e081ea3490a46362f9e", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Seen"), List.of(), List.of(ClassDesc.of("p.Unseen"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("beyond"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("unseen"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Seen"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Seen], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Seen].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Seen open-class sealed=no\ntparams -\nsuperclasses p.Unseen; java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete beyond(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); unseen(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Seen()\n";

        private Canonical() {
        }
    }

    /// The token of [Seen].
    public static final OpenClassToken<Seen> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Seen_() {
    }
}
