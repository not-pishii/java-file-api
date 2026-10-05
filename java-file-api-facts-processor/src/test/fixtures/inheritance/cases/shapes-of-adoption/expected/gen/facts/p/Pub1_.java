package gen.facts.p;

import gen.facts.p.Pub1_.Canonical;
import gen.facts.p.Pub1_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Pub1;

/// The full metamodel of [Pub1]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Pub1]: it is here as a supertype of [p.Pub2], whose inherited members are called through this metamodel.
///
/// A member [Pub1] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.H0`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Pub1.class, fingerprint = "0fc6e8b73199e0b4c758444bf964d37248c4385768855c76b47ff6b01bb1b8cf", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Pub1_ {
    /// The shape of [Pub1] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Pub1] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Pub1_"), "0fc6e8b73199e0b4c758444bf964d37248c4385768855c76b47ff6b01bb1b8cf", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Pub1"), List.of(), List.of(ClassDesc.of("p.H0"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("one"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("zero")), Set.of(), Set.of(Signature.of("Pub1"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Pub1], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Pub1].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Pub1 open-class sealed=no\ntparams -\nsuperclasses p.H0; java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable one() -> java.lang.String throws -\nmember method overridable zero() -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); one(); toString(); wait(); wait(long); wait(long, int); zero()\ntable static -\ntable ctor Pub1()\n";

        private Canonical() {
        }
    }

    /// The token of [Pub1].
    public static final OpenClassToken<Pub1> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Pub1#Pub1()].
    public static final CtorRef0<Pub1> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Pub1#one()].
    public static final MethodRef0<Pub1, String> one = UnsafeFacts.method(TOKEN, "one", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Pub1#zero()], declared in `p.H0`, which is not `public`.
    public static final MethodRef0<Pub1, String> zero = UnsafeFacts.method(TOKEN, "zero", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Pub1_() {
    }
}
