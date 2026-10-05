package gen.facts.p;

import gen.facts.p.Kept_.Canonical;
import gen.facts.p.Kept_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Kept;

/// The full metamodel of [Kept], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Kept] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// The members inherited from `p.BadMid`, which has no full metamodel, have no facts: the bounds of the type parameters of p.BadMid mention types that are not public: p.Secret.
///
/// `p.HiddenI`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Kept.class, fingerprint = "119fd3451e9383f0a8f746fb5b818357be569c9904d4bbac1420ddf451a0de76", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Kept_ {
    /// The shape of [Kept] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Kept] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Kept_"), "119fd3451e9383f0a8f746fb5b818357be569c9904d4bbac1420ddf451a0de76", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Kept"), List.of(), List.of(ClassDesc.of("p.BadMid"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.BadMid"), List.of(Types.exact(Types.of(ClassDesc.of("p.Secret"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("bad"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("more"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Kept"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Kept], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Kept].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Kept open-class sealed=no\ntparams -\nsuperclasses p.BadMid; java.lang.Object\ninterfaces p.HiddenI\nsupertypes p.BadMid<p.Secret>\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant java.lang.String K = \"k\"\nmember method overridable more() -> java.lang.String throws -\nmember method overridable run() -> java.lang.String throws -\ntable abstract -\ntable concrete bad(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); more(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Kept()\n";

        private Canonical() {
        }
    }

    /// The token of [Kept].
    public static final OpenClassToken<Kept> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Kept#K], declared in `p.HiddenI`, which is not `public`.
    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "k");

    /// The fact of [Kept#Kept()].
    public static final CtorRef0<Kept> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Kept#more()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Kept, String> more = UnsafeFacts.method(TOKEN, "more", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Kept#run()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Kept, String> run = UnsafeFacts.method(TOKEN, "run", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Kept_() {
    }
}
