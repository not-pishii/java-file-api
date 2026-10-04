package b.facts.p;

import b.facts.p.Uses_.Canonical;
import b.facts.p.Uses_.Data;
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
import me.supcheg.javafile.facts.MethodRef1;
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
import p.Box;
import p.Uses;

/// The full metamodel of [Uses], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Uses] inherits has its fact in the metamodel of the supertype that declares it: [a.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Uses.class, fingerprint = "b24892ed0741d462f6ddcfccd8ebdc5d8bd942154f2bd321a34fb311fb2f0494", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Uses_ {
    /// The shape of [Uses] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Uses] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("b.facts.p.Uses_"), "b24892ed0741d462f6ddcfccd8ebdc5d8bd942154f2bd321a34fb311fb2f0494", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Uses"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("any", Param.fixed(ClassDesc.of("p.Box"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("strings"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Uses"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Uses], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Uses].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Uses open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable any(p.Box<java.lang.Integer>) -> p.Box<?> throws -\nmember method overridable strings() -> p.Box<java.lang.String> throws -\ntable abstract -\ntable concrete any(p.Box); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); strings(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Uses()\n";

        private Canonical() {
        }
    }

    /// The token of [Uses].
    public static final OpenClassToken<Uses> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Uses#Uses()].
    public static final CtorRef0<Uses> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Uses#any(Box)].
    public static final MethodRef1<Uses, Box<?>, Box<Integer>> any_Box = UnsafeFacts.method(TOKEN, "any", UnsafeFacts.<Box<?>>openClassToken(a.facts.p.Box_.Data.SHAPE, TokenArg.unbounded()), UnsafeFacts.<Box<Integer>>openClassToken(a.facts.p.Box_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<Integer>finalClassToken(b.facts.java.lang.Integer_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#strings()].
    public static final MethodRef0<Uses, Box<String>> strings = UnsafeFacts.method(TOKEN, "strings", UnsafeFacts.<Box<String>>openClassToken(a.facts.p.Box_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(a.facts.java.lang.String_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    private Uses_() {
    }
}
