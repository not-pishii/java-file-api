package gen.facts.p;

import gen.facts.p.Vis_.Canonical;
import gen.facts.p.Vis_.Data;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Outer.Pub;
import p.Vis;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Vis.class, fingerprint = "014b01458158c86515110bf1bbaa542131eae79ee537036041286eb7f28ffad2", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Vis_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Vis_"), "014b01458158c86515110bf1bbaa542131eae79ee537036041286eb7f28ffad2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Vis"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("arr"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("nested", Param.fixed(ClassDesc.of("p.Outer$PkgNested"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ok"), Signature.of("pkgM"), Signature.of("protM"), Signature.of("pubM"), Signature.of("takes", Param.fixed(ClassDesc.of("p.Hidden"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sPub")), Set.of(Signature.of("Vis"), Signature.of("Vis", Param.fixed(ConstantDescs.CD_int)), Signature.of("Vis", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Vis", Param.fixed(ClassDesc.of("p.Hidden"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Vis open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int pub\nmember method overridable ok() -> p.Outer$Pub throws -\nmember method overridable pubM() -> void throws -\nmember method static sPub() -> void throws -\ntable abstract -\ntable concrete arr(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); hidden(); nested(p.Outer$PkgNested); notify(); notifyAll(); ok(); pkgM(); protM(); pubM(); takes(p.Hidden); toString(); wait(); wait(long); wait(long, int)\ntable static sPub()\ntable ctor Vis(); Vis(int); Vis(java.lang.String); Vis(p.Hidden)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Vis> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<Vis, Int> pub = UnsafeFacts.mutableField(TOKEN, "pub", PrimitiveToken.INT);

    public static final CtorRef0<Vis> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Vis, Pub> ok = UnsafeFacts.method(TOKEN, "ok", UnsafeFacts.<Pub>openClassToken(Outer_Pub_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef0<Vis> pubM = UnsafeFacts.voidMethod(TOKEN, "pubM", MemberTraits.OVERRIDABLE);

    public static final VoidStaticMethodRef0 sPub = UnsafeFacts.voidStaticMethod(TOKEN, "sPub", MemberTraits.FINAL);

    private Vis_() {
    }
}
