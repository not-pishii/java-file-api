package gen.facts.p;

import gen.facts.p.Twins_.Canonical;
import gen.facts.p.Twins_.Data;
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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Twins;

/// The full metamodel of [Twins], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Twins] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.Tight`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Twins.class, fingerprint = "58afa9c4daf7e1d36325603458bc5573ee3b04fe8f2daf4dffb59f6f1de0ed08", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Twins_ {
    /// The shape of [Twins] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Twins] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Twins_"), "58afa9c4daf7e1d36325603458bc5573ee3b04fe8f2daf4dffb59f6f1de0ed08", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Twins"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("twin")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Twins"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Twins], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Twins].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Twins abstract-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method abstract twin() -> java.lang.String throws -\ntable abstract twin()\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Twins()\n";

        private Canonical() {
        }
    }

    /// The token of [Twins].
    public static final AbstractClassToken<Twins> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Twins#Twins()].
    public static final AbstractCtorRef0<Twins> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Twins#twin()], declared in `p.Tight`, which is not `public`.
    public static final MethodRef0<Twins, String> twin = UnsafeFacts.method(TOKEN, "twin", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    private Twins_() {
    }
}
