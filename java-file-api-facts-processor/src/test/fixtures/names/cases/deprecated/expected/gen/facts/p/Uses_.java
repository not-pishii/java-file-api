package gen.facts.p;

import gen.facts.p.Uses_.Canonical;
import gen.facts.p.Uses_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Old;
import p.Uses;

/// The full metamodel of [Uses], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Uses] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Uses.class, fingerprint = "43881df91e9e746ccb37bb2042e63f3c1f29521f26d5637b17c246eb09a1d0b0", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Uses_ {
    /// The shape of [Uses] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Uses] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Uses_"), "43881df91e9e746ccb37bb2042e63f3c1f29521f26d5637b17c246eb09a1d0b0", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Uses"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("old"), Signature.of("take", Param.fixed(ClassDesc.of("p.Old"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Uses"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Uses], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Uses].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Uses open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable old() -> p.Old throws -\nmember method overridable take(p.Old) -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); old(); take(p.Old); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Uses()\n";

        private Canonical() {
        }
    }

    /// The token of [Uses].
    public static final OpenClassToken<Uses> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Uses#Uses()].
    public static final CtorRef0<Uses> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Uses#old()].
    public static final MethodRef0<Uses, Old> old = UnsafeFacts.method(TOKEN, "old", UnsafeFacts.<Old>openClassToken(Old_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#take(Old)].
    public static final VoidMethodRef1<Uses, Old> take_Old = UnsafeFacts.voidMethod(TOKEN, "take", UnsafeFacts.<Old>openClassToken(Old_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Uses_() {
    }
}
