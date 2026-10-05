package a.facts.p;

import a.facts.p.OnHid_.Canonical;
import a.facts.p.OnHid_.Data;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.OnHid;

/// The full metamodel of [OnHid], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [OnHid] inherits has its fact in the metamodel of the supertype that declares it: [a.facts.java.lang.Object_].
///
/// `p.HidBase`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = OnHid.class, fingerprint = "bcbee7b4b4ae93b1b868a65cfe46cb1ade3b780a7b6dbc4bf3a8bcc24470a3ad", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class OnHid_ {
    /// The shape of [OnHid] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [OnHid] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("a.facts.p.OnHid_"), "bcbee7b4b4ae93b1b868a65cfe46cb1ade3b780a7b6dbc4bf3a8bcc24470a3ad", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.OnHid"), List.of(), List.of(ClassDesc.of("p.HidBase"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("old"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("OnHid"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [OnHid], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [OnHid].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.OnHid open-class sealed=no\ntparams -\nsuperclasses p.HidBase; java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable old() -> int throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); old(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor OnHid()\n";

        private Canonical() {
        }
    }

    /// The token of [OnHid].
    public static final OpenClassToken<OnHid> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [OnHid#OnHid()].
    public static final CtorRef0<OnHid> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [OnHid#old()], declared in `p.HidBase`, which is not `public`.
    public static final MethodRef0<OnHid, Int> old = UnsafeFacts.method(TOKEN, "old", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private OnHid_() {
    }
}
