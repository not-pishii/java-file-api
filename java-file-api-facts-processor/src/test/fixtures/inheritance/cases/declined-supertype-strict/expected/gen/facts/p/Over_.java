package gen.facts.p;

import gen.facts.p.Over_.Canonical;
import gen.facts.p.Over_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Bounded;
import p.Over;

/// The full metamodel of [Over], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// The members inherited from `p.Bounded`, which has no full metamodel, have no facts: the bounds of the type parameters of p.Bounded mention types that are not public: p.Secret.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Over.class, fingerprint = "646483b2f8018fe713459910d7baeaca7456220597f8fe459c5b41ba43b963e1", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Over_ {
    /// The shape of [Over] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Over] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Over_"), "646483b2f8018fe713459910d7baeaca7456220597f8fe459c5b41ba43b963e1", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Over"), List.of(), List.of(ClassDesc.of("p.Bounded"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Bounded"), List.of(Types.exact(Types.of(ClassDesc.of("p.Secret"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("lost"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("same"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Over"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Over], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Over].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Over open-class sealed=no\ntparams -\nsuperclasses p.Bounded; java.lang.Object\nsupertypes p.Bounded<p.Secret>\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable same() -> p.Bounded<?> throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); lost(); notify(); notifyAll(); same(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Over()\n";

        private Canonical() {
        }
    }

    /// The token of [Over].
    public static final OpenClassToken<Over> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Over#Over()].
    public static final CtorRef0<Over> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Over#same()].
    public static final MethodRef0<Over, Bounded<?>> same = UnsafeFacts.method(TOKEN, "same", UnsafeFacts.<Bounded<?>>openClassToken(Bounded_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    private Over_() {
    }
}
