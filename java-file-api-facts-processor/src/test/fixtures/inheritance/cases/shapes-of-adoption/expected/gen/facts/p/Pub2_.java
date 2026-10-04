package gen.facts.p;

import gen.facts.p.Pub2_.Canonical;
import gen.facts.p.Pub2_.Data;
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
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Pub2;

/// The full metamodel of [Pub2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Pub2] inherits has its fact in the metamodel of the supertype that declares it: [Pub1_].
///
/// `p.HBetween`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Pub2.class, fingerprint = "23e2b30f41e36769c000eb8a004b6ce19bc7dfc94cda3337a8c33d45c8f49a63", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Pub2_ {
    /// The shape of [Pub2] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Pub2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Pub2_"), "23e2b30f41e36769c000eb8a004b6ce19bc7dfc94cda3337a8c33d45c8f49a63", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Pub2"), List.of(), List.of(ClassDesc.of("p.HBetween"), ClassDesc.of("p.Pub1"), ClassDesc.of("p.H0"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("between"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("one"), Signature.of("toString"), Signature.of("two"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("zero")), Set.of(), Set.of(Signature.of("Pub2"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Pub2], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Pub2].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Pub2 open-class sealed=no\ntparams -\nsuperclasses p.HBetween; p.Pub1; p.H0; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable between() -> java.lang.String throws -\nmember method overridable two() -> java.lang.String throws -\ntable abstract -\ntable concrete between(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); one(); toString(); two(); wait(); wait(long); wait(long, int); zero()\ntable static -\ntable ctor Pub2()\n";

        private Canonical() {
        }
    }

    /// The token of [Pub2].
    public static final OpenClassToken<Pub2> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Pub2#Pub2()].
    public static final CtorRef0<Pub2> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Pub2#between()], declared in `p.HBetween`, which is not `public`.
    public static final MethodRef0<Pub2, String> between = UnsafeFacts.method(TOKEN, "between", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub2#two()].
    public static final MethodRef0<Pub2, String> two = UnsafeFacts.method(TOKEN, "two", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Pub2_() {
    }
}
