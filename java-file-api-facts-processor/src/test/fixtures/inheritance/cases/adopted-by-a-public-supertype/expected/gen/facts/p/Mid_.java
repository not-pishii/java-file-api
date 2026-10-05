package gen.facts.p;

import gen.facts.p.Mid_.Canonical;
import gen.facts.p.Mid_.Data;
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
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Mid;

/// The full metamodel of [Mid]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Mid]: it is here as a supertype of [p.X], whose inherited members are called through this metamodel.
///
/// A member [Mid] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HiddenI`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mid.class, fingerprint = "8e926b75d9dd8f1460de2cd106f1c7c5ebdab59a0d023efc09234f02398e42c6", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Mid_ {
    /// The shape of [Mid] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Mid] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mid_"), "8e926b75d9dd8f1460de2cd106f1c7c5ebdab59a0d023efc09234f02398e42c6", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mid"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("mid"), Signature.of("more"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Mid"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Mid], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Mid].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Mid open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces p.HiddenI\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant java.lang.String K = \"k\"\nmember method overridable mid() -> void throws -\nmember method overridable more() -> java.lang.String throws -\nmember method overridable run() -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); mid(); more(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Mid()\n";

        private Canonical() {
        }
    }

    /// The token of [Mid].
    public static final OpenClassToken<Mid> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Mid#K], declared in `p.HiddenI`, which is not `public`.
    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "k");

    /// The fact of [Mid#Mid()].
    public static final CtorRef0<Mid> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Mid#mid()].
    public static final VoidMethodRef0<Mid> mid = UnsafeFacts.voidMethod(TOKEN, "mid", MemberTraits.OVERRIDABLE);

    /// The fact of [Mid#more()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Mid, String> more = UnsafeFacts.method(TOKEN, "more", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Mid#run()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Mid, String> run = UnsafeFacts.method(TOKEN, "run", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Mid_() {
    }
}
