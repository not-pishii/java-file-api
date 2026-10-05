package gen.facts.p;

import gen.facts.p.Impl_.Canonical;
import gen.facts.p.Impl_.Data;
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
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Impl;

/// The full metamodel of [Impl], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Impl] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PubConst_].
///
/// `p.HConst`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// These members have no fact:
///
/// - `field K of p.HConst`, which is ambiguous in p.Impl with field K of p.PubConst
/// - `field name of p.HConst`, which is ambiguous in p.Impl with field name of p.PubConst
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Impl.class, fingerprint = "832fe743a31922229ca3ed1e3741a63a2543649838f078c2718d2d29861914d9", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Impl_ {
    /// The shape of [Impl] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Impl] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Impl_"), "832fe743a31922229ca3ed1e3741a63a2543649838f078c2718d2d29861914d9", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Impl"), List.of(), List.of(ClassDesc.of("p.HConst"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Impl"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Impl], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Impl].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Impl open-class sealed=no\ntparams -\nsuperclasses p.HConst; java.lang.Object\ninterfaces p.PubConst\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant java.lang.String ONLY = \"only\"\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Impl()\n";

        private Canonical() {
        }
    }

    /// The token of [Impl].
    public static final OpenClassToken<Impl> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Impl#ONLY], declared in `p.HConst`, which is not `public`.
    public static final StaticFieldRef<String> ONLY = UnsafeFacts.constantField(TOKEN, "ONLY", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "only");

    /// The fact of [Impl#Impl()].
    public static final CtorRef0<Impl> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Impl_() {
    }
}
