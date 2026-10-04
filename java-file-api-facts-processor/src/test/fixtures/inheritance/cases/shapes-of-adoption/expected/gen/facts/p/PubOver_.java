package gen.facts.p;

import gen.facts.p.PubOver_.Canonical;
import gen.facts.p.PubOver_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PubOver;

/// The full metamodel of [PubOver], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubOver] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HOver`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubOver.class, fingerprint = "e8ce78bb86298648e58df0672d9e0aabd6793de3145e385dbd9c9e880cc32f4d", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PubOver_ {
    /// The shape of [PubOver] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [PubOver] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubOver_"), "e8ce78bb86298648e58df0672d9e0aabd6793de3145e385dbd9c9e880cc32f4d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubOver"), List.of(), List.of(ClassDesc.of("p.HOver"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("take", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("take", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubOver"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [PubOver], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [PubOver].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.PubOver open-class sealed=no\ntparams -\nsuperclasses p.HOver; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable take(java.lang.Object) -> java.lang.String throws -\nmember method overridable take(java.lang.String) -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); take(java.lang.Object); take(java.lang.String); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor PubOver()\n";

        private Canonical() {
        }
    }

    /// The token of [PubOver].
    public static final OpenClassToken<PubOver> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubOver#PubOver()].
    public static final CtorRef0<PubOver> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [PubOver#take(Object)], declared in `p.HOver`, which is not `public`.
    public static final MethodRef1<PubOver, String, Object> take_Object = UnsafeFacts.method(TOKEN, "take", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubOver#take(String)].
    public static final MethodRef1<PubOver, String, String> take_String = UnsafeFacts.method(TOKEN, "take", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private PubOver_() {
    }
}
