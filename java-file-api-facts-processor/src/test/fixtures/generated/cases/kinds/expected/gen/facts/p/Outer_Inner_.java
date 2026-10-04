package gen.facts.p;

import gen.facts.p.Outer_Inner_.Canonical;
import gen.facts.p.Outer_Inner_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Outer.Inner;

/// The full metamodel of [Inner], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Inner] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Inner.class, fingerprint = "f18419e094873b6bec0c00fc4cec37b0ad23df5f9e2443a76098b7ad82d18978", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_Inner_ {
    /// The shape of [Inner] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Inner] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_Inner_"), "f18419e094873b6bec0c00fc4cec37b0ad23df5f9e2443a76098b7ad82d18978", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Outer$Inner"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Outer$Inner"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Inner], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Inner].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Outer$Inner open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Outer$Inner()\n";

        private Canonical() {
        }
    }

    /// The token of [Inner].
    public static final OpenClassToken<Inner> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Inner#Inner()].
    public static final CtorRef0<Inner> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Outer_Inner_() {
    }
}
