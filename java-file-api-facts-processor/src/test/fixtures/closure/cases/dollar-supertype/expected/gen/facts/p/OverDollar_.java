package gen.facts.p;

import gen.facts.p.OverDollar_.Canonical;
import gen.facts.p.OverDollar_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.OverDollar;

/// The full metamodel of [OverDollar], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// The members inherited from `p.Dol$lar`, which has no full metamodel, have no facts: p.Dol$lar: a class with $ in its simple name is not supported yet.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = OverDollar.class, fingerprint = "c2ed6a3fe7a1b6f66cd5ccefa6d2d9ae12912508deefebafb857342388d4bd02", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class OverDollar_ {
    /// The shape of [OverDollar] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [OverDollar] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.OverDollar_"), "c2ed6a3fe7a1b6f66cd5ccefa6d2d9ae12912508deefebafb857342388d4bd02", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.OverDollar"), List.of(), List.of(ClassDesc.of("p.Dol$lar"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("own"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("OverDollar"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [OverDollar], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [OverDollar].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.OverDollar open-class sealed=no\ntparams -\nsuperclasses p.Dol$lar; java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable own() -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); own(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor OverDollar()\n";

        private Canonical() {
        }
    }

    /// The token of [OverDollar].
    public static final OpenClassToken<OverDollar> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [OverDollar#OverDollar()].
    public static final CtorRef0<OverDollar> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [OverDollar#own()].
    public static final VoidMethodRef0<OverDollar> own = UnsafeFacts.voidMethod(TOKEN, "own", MemberTraits.OVERRIDABLE);

    private OverDollar_() {
    }
}
