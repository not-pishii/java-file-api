package gen.facts.p;

import gen.facts.p.Canonical_.Canonical;
import gen.facts.p.Canonical_.Data;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;

/// The full metamodel of [p.Canonical], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [p.Canonical] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = p.Canonical.class, fingerprint = "400ea785a26fa74cfd4e098fb5d918cc40cfc9f6074e742f9144a4eea1747318", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Canonical_ {
    /// The shape of [p.Canonical] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [p.Canonical] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Canonical_"), "400ea785a26fa74cfd4e098fb5d918cc40cfc9f6074e742f9144a4eea1747318", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Canonical"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Canonical"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [p.Canonical], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [p.Canonical].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Canonical open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable p.Data data\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Canonical()\n";

        private Canonical() {
        }
    }

    /// The token of [p.Canonical].
    public static final OpenClassToken<p.Canonical> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [p.Canonical#data].
    public static final MutableFieldRef<p.Canonical, p.Data> data = UnsafeFacts.mutableField(TOKEN, "data", UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE));

    /// The fact of [p.Canonical#Canonical()].
    public static final CtorRef0<p.Canonical> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Canonical_() {
    }
}
