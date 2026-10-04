package b.facts.p;

import b.facts.p.Other_.Canonical;
import b.facts.p.Other_.Data;
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
import p.Dep;
import p.Other;

/// The full metamodel of [Other], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Other] inherits has its fact in the metamodel of the supertype that declares it: [a.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Other.class, fingerprint = "351aa025857b573bc0d27f98b3f83353af92b1a78abbe21414bf5d28a3276ed3", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Other_ {
    /// The shape of [Other] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Other] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("b.facts.p.Other_"), "351aa025857b573bc0d27f98b3f83353af92b1a78abbe21414bf5d28a3276ed3", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Other"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("dep"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Other"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Other], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Other].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Other open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable dep() -> p.Dep throws -\ntable abstract -\ntable concrete clone(); dep(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Other()\n";

        private Canonical() {
        }
    }

    /// The token of [Other].
    public static final OpenClassToken<Other> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Other#Other()].
    public static final CtorRef0<Other> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Other#dep()].
    public static final MethodRef0<Other, Dep> dep = UnsafeFacts.method(TOKEN, "dep", UnsafeFacts.<Dep>openClassToken(a.facts.p.Dep_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Other_() {
    }
}
