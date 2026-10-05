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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Outer.Pub;
import p.Vis;

/// The full metamodel of [Vis], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Vis] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `field hf`, which mentions types that are not public: p.Hidden
/// - `field hl`, which mentions types that are not public: p.Hidden
/// - `constructor Vis(p.Hidden)`, which mentions types that are not public: p.Hidden
/// - `method hidden()`, which mentions types that are not public: p.Hidden
/// - `method takes(p.Hidden)`, which mentions types that are not public: p.Hidden
/// - `method arr()`, which mentions types that are not public: p.Hidden
/// - `method nested(p.Outer.PkgNested)`, which mentions types that are not public: p.Outer$PkgNested
///
/// The `protected` members have no facts.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Vis.class, fingerprint = "2fd53e60e393d7953efe96cec86420d2c2e2bc7cd750f562e66d8fd19e1596e2", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Vis_ {
    /// The shape of [Vis] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Vis] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Vis_"), "2fd53e60e393d7953efe96cec86420d2c2e2bc7cd750f562e66d8fd19e1596e2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Vis"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("arr"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("nested", Param.fixed(ClassDesc.of("p.Outer$PkgNested"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ok"), Signature.of("pkgM"), Signature.of("protM"), Signature.of("pubM"), Signature.of("takes", Param.fixed(ClassDesc.of("p.Hidden"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sPub")), Set.of(Signature.of("Vis"), Signature.of("Vis", Param.fixed(ConstantDescs.CD_int)), Signature.of("Vis", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Vis", Param.fixed(ClassDesc.of("p.Hidden"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Vis], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Vis].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Vis open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int pub\nmember method overridable ok() -> p.Outer$Pub throws -\nmember method overridable pubM() -> void throws -\nmember method static sPub() -> void throws -\ntable abstract -\ntable concrete arr(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); hidden(); nested(p.Outer$PkgNested); notify(); notifyAll(); ok(); pkgM(); protM(); pubM(); takes(p.Hidden); toString(); wait(); wait(long); wait(long, int)\ntable static sPub()\ntable ctor Vis(); Vis(int); Vis(java.lang.String); Vis(p.Hidden)\n";

        private Canonical() {
        }
    }

    /// The token of [Vis].
    public static final OpenClassToken<Vis> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Vis#pub].
    public static final MutableFieldRef<Vis, Int> pub = UnsafeFacts.mutableField(TOKEN, "pub", PrimitiveToken.INT);

    /// The fact of [Vis#Vis()].
    public static final CtorRef0<Vis> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Vis#ok()].
    public static final MethodRef0<Vis, Pub> ok = UnsafeFacts.method(TOKEN, "ok", UnsafeFacts.<Pub>openClassToken(Outer_Pub_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Vis#pubM()].
    public static final VoidMethodRef0<Vis> pubM = UnsafeFacts.voidMethod(TOKEN, "pubM", MemberTraits.OVERRIDABLE);

    /// The fact of [Vis#sPub()].
    public static final VoidStaticMethodRef0 sPub = UnsafeFacts.voidStaticMethod(TOKEN, "sPub", MemberTraits.FINAL);

    private Vis_() {
    }
}
